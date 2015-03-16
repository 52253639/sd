package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.tenancy.FlagAtTermEnum;
import com.kingdee.eas.fdc.tenancy.HandleStateEnum;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.QuitTenancyInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;

public class QuitTenancyControllerBean extends AbstractQuitTenancyControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.QuitTenancyControllerBean");
    
    protected boolean isUseName() {
    	return false;
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	//合同退租单提交后，合同变为退租中状态
    	QuitTenancyInfo quitTen = (QuitTenancyInfo) model;
    	TenancyBillInfo tenancy = quitTen.getTenancyBill();
    	tenancy.setTenancyState(TenancyBillStateEnum.QuitTenancying);
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("tenancyState");
    	TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancy, sels);
    	
    	return super._submit(ctx, model);
    }
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	//删除提交状态的退租单后，需要将原合同置为原执行状态
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("tenancyBill.id");
    	
    	QuitTenancyInfo quitTen = getQuitTenancyInfo(ctx, pk, sels);
    	TenancyBillInfo tenancy = quitTen.getTenancyBill();
    	if(tenancy == null){
    		logger.error("退租单的租赁合同为空. quitTenId" + quitTen.getId());
    	}
    	String tenId = tenancy.getId().toString();
    	
    	SelectorItemCollection tenSels = new SelectorItemCollection();
    	tenSels.add("id");
    	tenSels.add("tenancyState");
    	tenSels.add("tenancyRoomList.id");
    	tenSels.add("tenancyRoomList.handleState");
    	
    	tenancy = TenancyBillFactory.getLocalInstance(ctx).getTenancyBillInfo(new ObjectUuidPK(tenId), tenSels);
    	
    	//只有已审批，执行中，部分执行中的合同才能退租,删除退租后，根据交房情况设置
    	TenancyRoomEntryCollection tenRooms = tenancy.getTenancyRoomList();
    	TenAttachResourceEntryCollection tenAttachs = tenancy.getTenAttachResourceList();
    	int deliveryRoomAndAttachCount = 0;//原合同已交房的数量（包含房间和配套资源）
    	deliveryRoomAndAttachCount = addDeliveryCount(tenRooms, deliveryRoomAndAttachCount);
    	deliveryRoomAndAttachCount = addDeliveryCount(tenAttachs, deliveryRoomAndAttachCount);
    	
    	TenancyBillStateEnum bornTenState = null;
    	if(deliveryRoomAndAttachCount == 0){//如果一个房子都没交，原合同之前是审批状态
    		bornTenState = TenancyBillStateEnum.Audited;
    	}else if(deliveryRoomAndAttachCount == (tenRooms.size() + tenAttachs.size())){//如果房子全交了，原合同之前是执行中状态
    		bornTenState = TenancyBillStateEnum.Executing;
    	}else{//如果只交了1部分，原合同之前是部分执行状态
    		bornTenState = TenancyBillStateEnum.PartExecuted;
    	}
    	tenancy.setTenancyState(bornTenState);
    	SelectorItemCollection updateBillStateSels = new SelectorItemCollection();
    	updateBillStateSels.add("tenancyState");
    	TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancy, updateBillStateSels);
    	
    	super._delete(ctx, pk);
    }

	private int addDeliveryCount(IObjectCollection tenObjs, int deliveryRoomAndAttachCount) {
		for(int i=0; i<tenObjs.size(); i++){
			ITenancyEntryInfo tenObj = (ITenancyEntryInfo) tenObjs.getObject(i);
			HandleStateEnum handleState = tenObj.getHandleState();
    		if(HandleStateEnum.HandlingRoom.equals(handleState) || HandleStateEnum.livingHouse.equals(handleState)){
    			deliveryRoomAndAttachCount++;
    		}
    	}
		return deliveryRoomAndAttachCount;
	}
    
    /** 审批操作 */
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	QuitTenancyInfo quitTen = getQuitTenancyInfo(ctx, new ObjectUuidPK(billId.toString()));
    	
    	TenancyBillInfo ten = quitTen.getTenancyBill();
    	
    	if(ten == null){
    		throw new BOSException("退租单没找到原合同！");
    	}
    	
    	ten = TenancyHelper.getTenancyBillInfo(ctx, ten.getId().toString());
    	
    	//调整原合同的剩余押金和应付金额
    	TenancyHelper.adjustDepositeAndPayList(ctx, quitTen.getQuitDate(), ten, true);
    	
    	//原合同剩余押金减去扣除的金额//TODO 扣款暂未实现，后面会针对房间扣款,故下面这句不用调了。
//    	TenancyHelper.deductAmountOfRemainDepositAmount(ctx, quitTen.getDeductAmount(), ten);
    	
    	//更新原合同和原合同房间分录的到期标记
       	FlagAtTermEnum quitTenType = quitTen.getQuitTenancyType();
    	ten.setFlagAtTerm(quitTenType);
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("flagAtTerm");
    	
    	TenancyRoomEntryCollection tenRooms = ten.getTenancyRoomList();
    	TenAttachResourceEntryCollection tenAttachs = ten.getTenAttachResourceList();
    	boolean allNotDelieve = true;//所有房间均未交房,即房间为未交房状态
    	for(int i=0; i<tenRooms.size(); i++){
    		TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
    		if(tenRoom.getHandleState() != null  &&  !HandleStateEnum.NoHandleRoom.equals(tenRoom.getHandleState())){
    			allNotDelieve = false;
    		}
    		
    		tenRoom.setFlagAtTerm(quitTenType);
    		TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenRoom, sels);
    	}
    	
    	for(int i=0; i<tenAttachs.size(); i++){
    		TenAttachResourceEntryInfo tenAttach = tenAttachs.get(i);
    		if(tenAttach.getHandleState() != null  &&  !HandleStateEnum.NoHandleRoom.equals(tenAttach.getHandleState())){
    			allNotDelieve = false;
    		}
    		
    		tenAttach.setFlagAtTerm(quitTenType);
    		TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttach, sels);
    	}
    	
    	//如果原合同的所有房间均未交房，则直接终止原合同
    	if(allNotDelieve){
    		ten.setTenancyState(TenancyBillStateEnum.Expiration);
    		sels.add("tenancyState");
    	}
    	TenancyBillFactory.getLocalInstance(ctx).updatePartial(ten, sels);
    	
    	super._audit(ctx, billId);
    }
    
}