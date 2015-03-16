package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;

import com.kingdee.eas.base.netctrl.client.FilterItem;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.RoomTransferFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.util.NumericExceptionSubItem;
public class SignManageControllerBean extends AbstractSignManageControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.SignManageControllerBean");
    
    
    protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	SignManageInfo info=new SignManageInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("state");
		info.setId(billId);
		info.setBizState(TransactionStateEnum.SIGNAPPLE);
		info.setState(FDCBillStateEnum.SUBMITTED);
		_updatePartial(ctx, info, selector);
	}
    protected void _setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	SignManageInfo info=new SignManageInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		info.setId(billId);
		info.setBizState(TransactionStateEnum.SIGNAUDITING);
		info.setState(FDCBillStateEnum.AUDITTING);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new Date());
		_updatePartial(ctx, info, selector);
	}
    protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	SignManageInfo info = ((SignManageInfo) model);
		if(info.getBizState() == null)info.setBizState(TransactionStateEnum.SIGNSAVED);
		super._save(ctx, pk, model);
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		SignManageInfo info = ((SignManageInfo) model);
		if(info.getBizState() == null)info.setBizState(TransactionStateEnum.SIGNSAVED);
		return super._save(ctx, model);
	}
	private void checkHasBankPayment(Context ctx, String billId,String warning) throws EASBizException, BOSException{
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("signManager.id", billId));
		if(BankPaymentEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","单据已经产生银行放款单业务，不能进行"+warning+"操作！"));
		}
    }
  
	protected void _invalid(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		
		checkHasBankPayment(ctx,billId.toString(),"作废");
		SHEManageHelper.checkHasSHERevBill(ctx,billId.toString(),"作废");
		
		SignManageInfo info=this.getSignManageInfo(ctx, new ObjectUuidPK(billId));
		SelectorItemCollection selector = new SelectorItemCollection();
		
		selector.add("bizState");
		selector.add("state");
		selector.add("isValid");
		info.setState(FDCBillStateEnum.INVALID);
		info.setBizState(TransactionStateEnum.SIGNNULLIFY);
		info.setIsValid(true);
		_updatePartial(ctx, info, selector);
		
		SHEManageHelper.updatePayActRevAmountAndBizState(ctx, info.getSrcId(), true, TransactionStateEnum.TOSIGN);
		SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),true,RoomSellStateEnum.Sign);
		
		RoomSellStateEnum roomState=SHEManageHelper.toOldTransaction(ctx,info.getTransactionID());
		if(roomState!=null){
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("sellState");
			for(int i=0;i<info.getSignRoomAttachmentEntry().size();i++){
				RoomInfo attRoom=info.getSignRoomAttachmentEntry().get(i).getRoom();
				attRoom.setSellState(roomState);
				RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, sels);
			}
		}
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		SignManageInfo info=new SignManageInfo();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		info.setId(billId);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new Date());
		info.setBizState(TransactionStateEnum.SIGNAUDIT);
		info.setState(FDCBillStateEnum.AUDITTED);
		_updatePartial(ctx, info, selector);
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		ObjectUuidPK pk=new ObjectUuidPK(billId);
		SignManageInfo info=this.getSignManageInfo(ctx, pk);
		if(!ContextUtil.getCurrentUserInfo(ctx).getNumber().equals("ppl")){
			if(info.getAreaCompensate()!=null&&info.getAreaCompensate().compareTo(FDCHelper.ZERO)>0){
				throw new EASBizException(new NumericExceptionSubItem("100","签约单已存在面积补差，不能进行反审批操作！"));
			}
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("billId", info.getId()));
			if(DefaultAmountMangerFactory.getLocalInstance(ctx).exists(filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","签约单存在违约金，不能进行反审批操作！"));
			}
		}
		checkChangeManage(ctx,billId.toString());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		info.setId(billId);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setBizState(TransactionStateEnum.SIGNAPPLE);
		info.setState(FDCBillStateEnum.SUBMITTED);
		_updatePartial(ctx, info, selector);
	}
	private IObjectPK submitAction(Context ctx, IObjectValue model) throws EASBizException, BOSException{
		IObjectPK pk=null;
		SignManageInfo info=(SignManageInfo)model;
		info.setBizState(TransactionStateEnum.SIGNAPPLE);
		
		RoomInfo room=info.getRoom();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("sellState");
		
		room.setSellState(RoomSellStateEnum.Sign);
		RoomFactory.getLocalInstance(ctx).updatePartial(room, selector);
		
		for(int i=0;i<info.getSignRoomAttachmentEntry().size();i++){
			RoomInfo attRoom=info.getSignRoomAttachmentEntry().get(i).getRoom();
			attRoom.setSellState(RoomSellStateEnum.Sign);
			RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, selector);
		}
		SHEManageHelper.updatePayActRevAmountAndBizState(ctx,info.getSrcId(),false,TransactionStateEnum.TOSIGN);
		SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),false,RoomSellStateEnum.Sign);
		pk=super._submit(ctx, model);
		SHEManageHelper.updateTransaction(ctx,info,RoomSellStateEnum.Sign,true);
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
		SignManageInfo info=(SignManageInfo)getValue(ctx, pk);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sourceBillId", pk.toString()));
		if(RoomTransferFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","房间已结转，不能进行删除操作！"));
		}
		if(TransactionStateEnum.SIGNAPPLE.equals(info.getBizState())||
				TransactionStateEnum.SIGNNULLIFY.equals(info.getBizState())){
			checkHasBankPayment(ctx,pk.toString(),"删除");
			SHEManageHelper.checkHasSHERevBill(ctx,pk.toString(),"删除");
		}
		RoomSellStateEnum roomState=null;
		if(!TransactionStateEnum.SIGNNULLIFY.equals(info.getBizState())&&!TransactionStateEnum.SIGNSAVED.equals(info.getBizState())){
			roomState=SHEManageHelper.toOldTransaction(ctx,info.getTransactionID());
		}
		if(TransactionStateEnum.SIGNAPPLE.equals(info.getBizState())){
			SHEManageHelper.updatePayActRevAmountAndBizState(ctx, info.getSrcId(), true, TransactionStateEnum.TOSIGN);
			SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),true,RoomSellStateEnum.Sign);
			
			if(roomState!=null){
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("sellState");
				for(int i=0;i<info.getSignRoomAttachmentEntry().size();i++){
					RoomInfo attRoom=info.getSignRoomAttachmentEntry().get(i).getRoom();
					attRoom.setSellState(roomState);
					RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, sels);
				}
			}
		}
		super._delete(ctx, pk);
		
	}
	protected void _onRecord(Context ctx, BOSUuid id,Date date,String contractNumber) throws BOSException, EASBizException {
		SignManageInfo info = (SignManageInfo) getValue(ctx, new ObjectUuidPK(id));
		
		info.setIsOnRecord(true);
		info.setOnRecordDate(date);
		info.setBizNumber(contractNumber);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("isOnRecord");
		sels.add("onRecordDate");
		sels.add("bizNumber");
		_updatePartial(ctx, info, sels);
	}

	protected void _unOnRecord(Context ctx, BOSUuid id) throws BOSException, EASBizException {
		SignManageInfo info = (SignManageInfo) getValue(ctx, new ObjectUuidPK(id));
		
		info.setIsOnRecord(false);
		info.setOnRecordDate(null);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("isOnRecord");
		sels.add("onRecordDate");
		
		_updatePartial(ctx, info, sels);
		
	}

}