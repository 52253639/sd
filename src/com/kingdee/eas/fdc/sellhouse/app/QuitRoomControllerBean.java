package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.AreaCompensateRevListFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.FeeFromTypeEnum;
import com.kingdee.eas.fdc.sellhouse.GenFdcTrasfBillFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceCollection;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class QuitRoomControllerBean extends AbstractQuitRoomControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.QuitRoomControllerBean");
	
	private void _checkNameDup() {
	}
	
	/**  退房提交和审批时的一些校验，检验是否能够退房 */
	private void vertifyBeforSubmitAndAudit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		//是否存在入伙单
		QuitRoomInfo quitInfo = (QuitRoomInfo)model;
		if(RoomJoinFactory.getLocalInstance(ctx).exists("where room.id='"+quitInfo.getRoom().getId()+"'")){
			throw new EASBizException(new NumericExceptionSubItem("100","该退房单的房间已存在入伙单，不允再做退房操作！"));
		}
		//是否存在产权办理单
		if(RoomPropertyBookFactory.getLocalInstance(ctx).exists("where room.id='"+quitInfo.getRoom().getId()+"'")){
			throw new EASBizException(new NumericExceptionSubItem("100","该退房单的房间已存在产权办理单，不允再做退房操作！"));
		}
		//是否存在面积补差单
		if(RoomAreaCompensateFactory.getLocalInstance(ctx).exists("where room.id='"+quitInfo.getRoom().getId()+"'")){
			throw new EASBizException(new NumericExceptionSubItem("100","该退房单的房间已存在面积补差单，不允再做退房操作！"));
		} 
	}
	
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		vertifyBeforSubmitAndAudit(ctx,model);
		
		return super._submit(ctx, model);
	}
	
	
	protected boolean _exeQuit(Context ctx, String id) throws BOSException,
			EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("room.*");
		sels.add("quitEntrys.*");
		QuitRoomInfo quit = (QuitRoomInfo) this.getValue(ctx, new ObjectUuidPK(
				BOSUuid.read(id)), sels);
		if (quit.getState().equals(FDCBillStateEnum.AUDITTED)) {
			return true;
		}
		
		if (!quit.getState().equals(FDCBillStateEnum.AUDITTING)) {
			if (!quit.getState().equals(FDCBillStateEnum.SUBMITTED)) {
				throw new EASBizException(new NumericExceptionSubItem("100","非提交状态的单据不能审批！"));
			}	
		}	
	
		vertifyBeforSubmitAndAudit(ctx,quit);
		
		RoomInfo oldRoom = quit.getRoom();

		String blankSignContractSql = "update T_SHE_RoomSignContract set FIsBlankOut=?,FState = ?  where FRoomID=? ";
		DbUtil.execute(ctx, blankSignContractSql, new Object[]{new Integer(1),new String("9INVALID"), oldRoom.getId().toString()});
		//-----------------------------------------------------
		
		// 删除存在的售后单据
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", oldRoom.getId().toString()));
		RoomJoinFactory.getLocalInstance(ctx).delete(filter);

		RoomPropertyBookFactory.getLocalInstance(ctx).delete(filter);

		AreaCompensateRevListFactory.getLocalInstance(ctx).delete("where head.room.id='"+oldRoom.getId()+"'");
		RoomAreaCompensateFactory.getLocalInstance(ctx).delete(filter);

		String purId = quit.getPurchase().getId().toString();
		PurchaseInfo purchase = PurchaseFactory.getLocalInstance(ctx).getPurchaseInfo(new ObjectUuidPK(purId));
		// 更改房间状态
		SheRoomStateFactory.setRoomOnShowState(ctx, oldRoom, purchase,true,false);
		
		//更新单价和标准总价	-- 退房调价
		if (quit.isIsAdjustPrice()) {
			BigDecimal newBuildingPrice = quit.getNewBuildingPrice();
			BigDecimal newRoomPrice = quit.getNewRoomPrice();
			oldRoom.setBuildPrice(newBuildingPrice);
			oldRoom.setRoomPrice(newRoomPrice);
			BigDecimal standardAmount = null;
			if(oldRoom.isIsCalByRoomArea()){
				standardAmount = oldRoom.getRoomArea().multiply(newRoomPrice);
			}else{
				standardAmount = oldRoom.getBuildingArea().multiply(newBuildingPrice);
			}
			oldRoom.setStandardTotalAmount(standardAmount);
			SelectorItemCollection roomSels = new SelectorItemCollection();
			roomSels.add("buildPrice");
			roomSels.add("roomPrice");
			roomSels.add("standardTotalAmount");
			RoomFactory.getLocalInstance(ctx).updatePartial(oldRoom, roomSels);
		}
		
		//-------------作废关联的收据和发票--------------
		/**
		 * 根据刘威德需求确认，取消此业务逻辑
		 * update by renliang at 2011-1-19
		 */
		//cancelCheque(ctx, oldRoom);
		//------------------
		
		//---- 修改各付款明细的可退金额.将退房单分录上的可退金额反写到对应的认购单的明细上
		QuitPayListEntryCollection quitEntrys = quit.getQuitEntrys();
		for(int i=0; i<quitEntrys.size(); i++){
			QuitPayListEntryInfo quitEntry = quitEntrys.get(i);
			String payIdStr = quitEntry.getPayListId();
			FeeFromTypeEnum feeType = quitEntry.getFeeFromType();
			
			
			BigDecimal canRefundmentAmount = quitEntry.getCanRefundmentAmount();
			if(canRefundmentAmount == null){
				canRefundmentAmount = FDCHelper.ZERO;
			}			
			
			if(feeType!=null && feeType.equals(FeeFromTypeEnum.ShouldPayList)) {
				if(canRefundmentAmount.compareTo(FDCHelper.ZERO) == 0) continue;
				
				PurchasePayListEntryInfo payEntryInfo = PurchasePayListEntryFactory.getLocalInstance(ctx)
						.getPurchasePayListEntryInfo("select hasRefundmentAmount where id='"+payIdStr+"'");
				if(payEntryInfo!=null) {
					//是预定金的时候是不用反写的 eirc_wang 2010.08.18
//					MoneyTypeEnum monType = payEntryInfo.getMoneyDefine().getMoneyType();
//					if(monType!=null && !monType.equals(MoneyTypeEnum.PreconcertMoney)) {
						payEntryInfo.setLimitAmount(canRefundmentAmount.add(payEntryInfo.getHasRefundmentAmount()));
						payEntryInfo.setIsRemainCanRefundment(false);
						SelectorItemCollection updateSels = new SelectorItemCollection();
						updateSels.add("limitAmount"); 
						updateSels.add("isRemainCanRefundment");
						PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(payEntryInfo, updateSels);
				}
//				}
			}else if(feeType!=null && feeType.equals(FeeFromTypeEnum.ElsePayList)) {
				PurchaseElsePayListEntryInfo payEntryInfo = PurchaseElsePayListEntryFactory.getLocalInstance(ctx)
						.getPurchaseElsePayListEntryInfo("select * where id='"+payIdStr+"'");
				if(payEntryInfo!=null) {
					//之前可能发生过已退、已转或已调
					canRefundmentAmount = canRefundmentAmount.add(payEntryInfo.getHasRefundmentAmount()).add(payEntryInfo.getHasTransferredAmount()).add(payEntryInfo.getHasAdjustedAmount());
					payEntryInfo.setIsRemainCanRefundment(false);
					SelectorItemCollection updateSels = new SelectorItemCollection();
					updateSels.add("isRemainCanRefundment");
					updateSels.add("limitAmount");
					payEntryInfo.setLimitAmount(canRefundmentAmount);
					
					PurchaseElsePayListEntryFactory.getLocalInstance(ctx).updatePartial(payEntryInfo, updateSels);
				}
			}
		}
		//--------------
		
		//退房审批,自动将费用金额和费用款项写入认购单的其他应收分录.其他分录增加单据关联,
		if(quit.getFeeAmount().compareTo((new BigDecimal(0)))!=0) {
			PurchaseElsePayListEntryInfo newElseEntry = new PurchaseElsePayListEntryInfo();
			newElseEntry.setHead(purchase);
			newElseEntry.setMoneyDefine(quit.getFeeMoneyDefine());
			
			CompanyOrgUnitInfo currentCompany = ContextUtil.getCurrentFIUnit(ctx);
			CurrencyInfo baseCurrency = CurrencyFactory.getLocalInstance(ctx)
					.getCurrencyInfo(new ObjectUuidPK(BOSUuid.read(currentCompany.getBaseCurrency().getId().toString())));
			newElseEntry.setCurrency(baseCurrency);
			newElseEntry.setAppAmount(quit.getFeeAmount());
			newElseEntry.setAppDate(new Date());
			newElseEntry.setRevMoneyType(RevMoneyTypeEnum.AppRev);
			newElseEntry.setFeeMoneyType(FeeMoneyTypeEnum.Fee);
			newElseEntry.setSeq(purchase.getElsePayListEntry().size());
			newElseEntry.setIsRemainCanRefundment(false);
			PurchaseElsePayListEntryFactory.getLocalInstance(ctx).addnew(newElseEntry);
		}
		
		//退房审批，自动将按揭管理里相关房间的单据状态改为 退房终止  by Pope
		String str="update T_SHE_RoomLoan set FAFMortgagedState = 6 where FRoomID = '"+oldRoom.getId()+"'";
		DbUtil.execute(ctx, str);
		
		return true;
	}

	/**
	 * 作废该房间关联的收据和发票
	 * */
	private void cancelCheque(Context ctx, RoomInfo oldRoom) throws BOSException {
		FDCReceivingBillCollection fdcs = FDCReceivingBillFactory.getLocalInstance(ctx)
					.getFDCReceivingBillCollection("select receipt where room.id='"+oldRoom.getId()+"'");
		
		Set chequeIds = new HashSet();
		for(int i=0; i<fdcs.size(); i++){
			FDCReceivingBillInfo fdc = fdcs.get(i);
			ChequeInfo cheque = fdc.getReceipt();
			if(cheque != null){
				chequeIds.add(cheque.getId().toString());	
			}
		}
		
		InvoiceCollection invoices = InvoiceFactory.getLocalInstance(ctx)
					.getInvoiceCollection("select cheQue where room.id='"+oldRoom.getId()+"'");
		for(int i=0; i<invoices.size(); i++){
			InvoiceInfo invoice = invoices.get(i);
			ChequeInfo cheque = invoice.getCheque();
			if(cheque != null){
				chequeIds.add(cheque.getId().toString());
			}
		}
		
		if(!chequeIds.isEmpty()){
			FDCSQLBuilder build = new FDCSQLBuilder(ctx); 
			build.appendSql("update T_SHE_Cheque set fstatus=");
			build.appendParam(new Integer(ChequeStatusEnum.CANCELLED_VALUE));
			build.appendSql(" where fstatus!=");
			build.appendParam(new Integer(ChequeStatusEnum.CANCELLED_VALUE));
			build.appendSql(" and ");
			build.appendParam("fid", chequeIds.toArray());
			build.execute();
		}
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		this.exeQuit(ctx, billId.toString());
		super._audit(ctx, billId);
	}

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		FDCBillInfo billInfo = ((FDCBillInfo) model);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
								.getId()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}

	}

	// 设置组织
	protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo)
			throws EASBizException, BOSException {
		if (fDCBillInfo.getOrgUnit() == null) {
			FullOrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx)
					.castToFullOrgUnitInfo();
			fDCBillInfo.setOrgUnit(orgUnit);
		}
	}

	protected String getCurrentOrgId(Context ctx) {
		SaleOrgUnitInfo org = ContextUtil.getCurrentSaleUnit(ctx);
		String curOrgId = org.getId().toString();
		return curOrgId;
	}

	protected void _receiveBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	protected void _settleMent(Context ctx, BOSUuid billId)	throws BOSException, EASBizException {
		SelectorItemCollection sc = new SelectorItemCollection();
		sc.add("*");
		sc.add("quitEntrys.*");
		sc.add("quitEntrys.moneyDefine.id");
		sc.add("quitEntrys.moneyDefine.name");
		sc.add("quitEntrys.moneyDefine.revBillType.*");
		// 退款生成收款单
		QuitRoomInfo qrInfo = this.getQuitRoomInfo(ctx,new ObjectUuidPK(billId), sc);
		if(qrInfo==null)	throw new EASBizException(new NumericExceptionSubItem("100","找不到该退房单！"));	
		
		if(!qrInfo.getState().equals(FDCBillStateEnum.AUDITTED))  
			throw new EASBizException(new NumericExceptionSubItem("100","结算前必须先审批！"));
			
		if(qrInfo.getIsBlance()==1)
			throw new EASBizException(new NumericExceptionSubItem("100","已经结算过了！"));
			
		if(qrInfo.getFeeAmount().compareTo(new BigDecimal(0))!=0) {//MsgBox.showInfo("无结算费用，不需结算！");  直接给结算成功
			if(qrInfo.getFeeMoneyDefine()==null)  
				throw new EASBizException(new NumericExceptionSubItem("100","请先设置‘费用款项’！"));

			if(qrInfo.getFeeAccount()==null) 
				throw new EASBizException(new NumericExceptionSubItem("100","请先设置‘费用科目’！"));
				

			//生成转款单
			FDCReceivingBillInfo fdcRevBillInfo = null;
			String feeRevListId = null;		//从其它应收明细中查找为费用的那笔  (退房审批时插入禁区的)			
			for(int i=0;i<qrInfo.getQuitEntrys().size();i++)  {
				QuitPayListEntryInfo quitEntry = qrInfo.getQuitEntrys().get(i);		//对手续费>0的款项才结算
				BigDecimal actPayAmount = quitEntry.getActPayAmount();
				if(actPayAmount!=null && actPayAmount.compareTo(new BigDecimal(0))>0) {
					BigDecimal canRefundAmount = quitEntry.getCanRefundmentAmount();
					if(canRefundAmount==null) canRefundAmount = new BigDecimal(0);
					BigDecimal maxCanRefund = quitEntry.getMaxCanRefundAmount();
					if(maxCanRefund==null)	maxCanRefund = new BigDecimal(0);
					BigDecimal feeCost = maxCanRefund.subtract(canRefundAmount);		//手续费=最大可退-可退金 
					if(feeCost.compareTo(new BigDecimal(0))>0) {
						if(fdcRevBillInfo==null)	fdcRevBillInfo = GenFdcTrasfBillFactory.genTrasfBill(ctx, quitEntry.getPayListId());
						
						if(feeRevListId==null)	{
							PurchaseElsePayListEntryCollection elsePurColl = PurchaseElsePayListEntryFactory.getLocalInstance(ctx)
											.getPurchaseElsePayListEntryCollection("select id where head.id='"+qrInfo.getPurchase().getId()+"' " +
													"and feeMoneyType='"+FeeMoneyTypeEnum.FEE_VALUE+"' and appAmount = "+qrInfo.getFeeAmount()+" ");
							if(elsePurColl.size()==1) {
								feeRevListId = elsePurColl.get(0).getId().toString();
							}else{
								throw new EASBizException(new NumericExceptionSubItem("100","无法定位到退房的认购单的其它费用明细！"));	
							}
						}
						//feeFromType
						RevListTypeEnum fromRevType= RevListTypeEnum.purchaseRev;
						if(quitEntry.getFeeFromType() !=null && quitEntry.getFeeFromType().equals(FeeFromTypeEnum.ElsePayList)) 
							fromRevType = RevListTypeEnum.purElseRev;
						GenFdcTrasfBillFactory.setTrasfEntry(ctx, fdcRevBillInfo, feeCost,null
										, qrInfo.getFeeMoneyDefine(),qrInfo.getFeeAccount(), feeRevListId, RevListTypeEnum.purElseRev
										, quitEntry.getMoneyDefine(), quitEntry.getPayListId(),fromRevType);						
					}
				}			 
			}
			if(fdcRevBillInfo!=null)	GenFdcTrasfBillFactory.submitNewTrasfBill(ctx, fdcRevBillInfo, "com.kingdee.eas.fdc.sellhouse.app.SheRevNoHandle");
		}
		
		qrInfo.setIsBlance(1);
		sc = new SelectorItemCollection();
		sc.add("isBlance");
		this.updatePartial(ctx,qrInfo, sc);
	}
	
	
}