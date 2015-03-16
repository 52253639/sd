package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import java.util.Date;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.util.NumericExceptionSubItem;
public class PurchaseManageControllerBean extends AbstractPurchaseManageControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.PurchaseManageControllerBean");


    protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	PurchaseManageInfo info=new PurchaseManageInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("state");
		info.setId(billId);
		info.setBizState(TransactionStateEnum.PURAPPLE);
		info.setState(FDCBillStateEnum.SUBMITTED);
		_updatePartial(ctx, info, selector);
	}
    protected void _setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	PurchaseManageInfo info=new PurchaseManageInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		info.setId(billId);
		info.setBizState(TransactionStateEnum.PURAUDITING);
		info.setState(FDCBillStateEnum.AUDITTING);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new Date());
		_updatePartial(ctx, info, selector);
	}

    protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	PurchaseManageInfo info = ((PurchaseManageInfo) model);
		if(info.getBizState() == null)info.setBizState(TransactionStateEnum.PURSAVED);
		super._save(ctx, pk, model);
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		PurchaseManageInfo info = ((PurchaseManageInfo) model);
		if(info.getBizState() == null)info.setBizState(TransactionStateEnum.PURSAVED);
		return super._save(ctx, model);
	}
	protected void _invalid(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	SHEManageHelper.checkHasSHERevBill(ctx,billId.toString(),"×÷·Ï");
    	
		PurchaseManageInfo info=this.getPurchaseManageInfo(ctx, new ObjectUuidPK(billId));
		SelectorItemCollection selector = new SelectorItemCollection();
		
		selector.add("bizState");
		selector.add("state");
		selector.add("isValid");
		info.setState(FDCBillStateEnum.INVALID);
		info.setBizState(TransactionStateEnum.PURNULLIFY);
		info.setIsValid(true);
		_updatePartial(ctx, info, selector);
		
		SHEManageHelper.updatePayActRevAmountAndBizState(ctx, info.getSrcId(), true, TransactionStateEnum.TOPUR);
		SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),true,RoomSellStateEnum.Purchase);
	
		RoomSellStateEnum roomState=SHEManageHelper.toOldTransaction(ctx,info.getTransactionID());
		
		if(roomState!=null){
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("sellState");
			for(int i=0;i<info.getPurRoomAttachmentEntry().size();i++){
				RoomInfo attRoom=info.getPurRoomAttachmentEntry().get(i).getRoom();
				attRoom.setSellState(roomState);
				RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, sels);
			}
		}
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		ObjectUuidPK pk=new ObjectUuidPK(billId);
		PurchaseManageInfo info=(PurchaseManageInfo)this.getValue(ctx, pk);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new Date());
		info.setBizState(TransactionStateEnum.PURAUDIT);
		info.setState(FDCBillStateEnum.AUDITTED);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		_updatePartial(ctx, info, selector);
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		checkChangeManage(ctx,billId.toString());
		
		ObjectUuidPK pk=new ObjectUuidPK(billId);
		PurchaseManageInfo info=(PurchaseManageInfo)this.getValue(ctx, pk);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setBizState(TransactionStateEnum.PURAPPLE);
		info.setState(FDCBillStateEnum.SUBMITTED);
		
		_updatePartial(ctx, info, selector);
	}
	private IObjectPK submitAction(Context ctx, IObjectValue model) throws EASBizException, BOSException{
		IObjectPK pk=null;
		PurchaseManageInfo info=(PurchaseManageInfo)model;
		info.setBizState(TransactionStateEnum.PURAPPLE);
		RoomInfo room=info.getRoom();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("sellState");
		room.setSellState(RoomSellStateEnum.Purchase);
		RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
		
		for(int i=0;i<info.getPurRoomAttachmentEntry().size();i++){
			RoomInfo attRoom=info.getPurRoomAttachmentEntry().get(i).getRoom();
			attRoom.setSellState(RoomSellStateEnum.Purchase);
			RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, sels);
		}
		SHEManageHelper.updatePayActRevAmountAndBizState(ctx, info.getSrcId(), false, TransactionStateEnum.TOPUR);
		SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),false,RoomSellStateEnum.Purchase);
		pk=super._submit(ctx, model);
		SHEManageHelper.updateTransaction(ctx, info,RoomSellStateEnum.Purchase,true);
		return pk;
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
		return submitAction(ctx,model);
	}
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		submitAction(ctx,model);
		super._submit(ctx, pk, model);
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		PurchaseManageInfo info=(PurchaseManageInfo)getValue(ctx, pk);
		
		if(TransactionStateEnum.PURAPPLE.equals(info.getBizState())||
				TransactionStateEnum.PURNULLIFY.equals(info.getBizState())){
			SHEManageHelper.checkHasSHERevBill(ctx,pk.toString(),"É¾³ý");
		}
		RoomSellStateEnum roomState=null;
		if(!TransactionStateEnum.PURNULLIFY.equals(info.getBizState())&&!TransactionStateEnum.PURSAVED.equals(info.getBizState())){
			roomState=SHEManageHelper.toOldTransaction(ctx,info.getTransactionID());
		}
		if(TransactionStateEnum.PURAPPLE.equals(info.getBizState())){
			SHEManageHelper.updatePayActRevAmountAndBizState(ctx, info.getSrcId(), true, TransactionStateEnum.TOPUR);
			SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),true,RoomSellStateEnum.Purchase);
			
			if(roomState!=null){
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("sellState");
				for(int i=0;i<info.getPurRoomAttachmentEntry().size();i++){
					RoomInfo attRoom=info.getPurRoomAttachmentEntry().get(i).getRoom();
					attRoom.setSellState(roomState);
					RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, sels);
				}
			}
		}
		super._delete(ctx, pk);
		
	}
}