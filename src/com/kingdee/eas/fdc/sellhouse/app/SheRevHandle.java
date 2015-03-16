package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.app.AbstractRevHandle;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.AreaCompensateRevListFactory;
import com.kingdee.eas.fdc.sellhouse.AreaCompensateRevListInfo;
import com.kingdee.eas.fdc.sellhouse.CusRevListFactory;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.ISincerityPurchase;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PreMoneySetting;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiptTypeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class SheRevHandle extends AbstractRevHandle {
	protected String[] getNeededFieldNames(RevListTypeEnum revListType) {
		return new String[]{"actRevAmount", "hasRefundmentAmount", "hasToFeeAmount", "hasTransferredAmount", "appAmount", "actRevDate"};
	}

	public void doBeforeRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException{
		RevBillTypeEnum  revBillType = rev.getRevBillType();
		if(revBillType==null) return;
		
		RevBizTypeEnum revBizType = rev.getRevBizType();
		if(revBizType==null) return;
		if(revBizType.equals(RevBizTypeEnum.purchase)) {
			if(revBillType.equals(RevBillTypeEnum.gathering)) {
				//对于认购单的第一次收款，要查看该房间是否有其他已付款的认购单，如果没有则触发其他提交状态认购单“未付款作废”
				handleWhenTheFirstRev(ctx,rev.getPurchaseObj().getId().toString());				
				
			}else if(revBillType.equals(RevBillTypeEnum.refundment)) {
	
			}else if(revBillType.equals(RevBillTypeEnum.transfer)) {
				handleWhenTheFirstRev(ctx,rev.getPurchaseObj().getId().toString());		
			}
			
		}
	}
	
	/**
	 收款对象是认购单时：
	 	当认购单状态时预订复核或认购申请状态时，只要收足了预订金的最低标准金额，就会设置房间为预订状态
	 	当认购单为认购审批状态时，只要收齐了第一笔明细款后，就会设置房间的为认购状态
*/	
	public void doAfterRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException {
		RevBillTypeEnum  revBillType = rev.getRevBillType();
		if(revBillType==null) return;
		
		RevBizTypeEnum revBizType = rev.getRevBizType();
		if(revBizType==null) return;
		
		if(revBizType.equals(RevBizTypeEnum.purchase)) {			
			updateRoomStateWhenPurRev(ctx,rev);
			
/*			if(revBillType.equals(RevBillTypeEnum.gathering)) {
			}else if(revBillType.equals(RevBillTypeEnum.refundment)) {
			}else if(revBillType.equals(RevBillTypeEnum.transfer)) {
			}else if(revBillType.equals(RevBillTypeEnum.adjust)) {
			}
*/
		}else if(revBizType.equals(RevBizTypeEnum.areaCompensate)) {
			updateAreaCompensateState(ctx,rev);			
		}else if(revBizType.equals(RevBizTypeEnum.sincerity)) {
			SincerityPurchaseInfo sinInfo = rev.getSincerityObj();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isRev");
			selector.add("revDate");
			sinInfo.setIsRev(true);
			sinInfo.setRevDate(new Date());
			SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(sinInfo, selector);
		}
					
	}
	
	
	public void doOfDelRev(Context ctx, FDCReceivingBillInfo oldFdcRev)	throws BOSException, EASBizException {
		RevBillTypeEnum  revBillType = oldFdcRev.getRevBillType();
		if(revBillType==null) return;
		
		RevBizTypeEnum revBizType = oldFdcRev.getRevBizType();
		if(revBizType==null) return;
		if(revBizType.equals(RevBizTypeEnum.purchase)) {
				updateRoomStateWhenPurRev(ctx,oldFdcRev);
		}else if(revBizType.equals(RevBizTypeEnum.areaCompensate)) {
			updateAreaCompensateState(ctx,oldFdcRev);
		}else if(revBizType.equals(RevBizTypeEnum.sincerity)) {
			SincerityPurchaseInfo sinInfo = oldFdcRev.getSincerityObj();
			ISincerityPurchase iSinPur = SincerityPurchaseFactory.getLocalInstance(ctx);
			//诚意认购单的明细不存在实收大于0的，则代表未收款 
			if(!SincerReceiveEntryFactory.getLocalInstance(ctx).exists("where head.id='"+sinInfo.getId()+"' and actRevAmount > 0 ")) {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("isRev");
				selector.add("revDate");
				sinInfo.setIsRev(false);
				sinInfo.setRevDate(null);
				iSinPur.updatePartial(sinInfo, selector);
			}
		}

	}
	
	//只要收款大于0,且补差单为补差已审批状态,则更新为已付款状态
	//只要收款等于0,且补差单为补差已付款状态,则更新为已审批状态	
	private void updateAreaCompensateState(Context ctx,FDCReceivingBillInfo rev)throws BOSException, EASBizException {
		if(rev.getEntries().size()==0) return;
		FDCReceivingBillEntryInfo revEntryInfo = rev.getEntries().get(0);
		String revListId = revEntryInfo.getRevListId();
		AreaCompensateRevListInfo revListInfo = AreaCompensateRevListFactory.getLocalInstance(ctx)
					.getAreaCompensateRevListInfo("select head.compensateState,* where id='"+revListId+"'");
		RoomAreaCompensateInfo compInfo = revListInfo.getHead();
		if((rev.getRevBillType().equals(RevBillTypeEnum.gathering) &&  revListInfo.getActRevAmount().compareTo(FDCHelper.ZERO)>0) 
				|| (rev.getRevBillType().equals(RevBillTypeEnum.refundment) &&  revListInfo.getHasRefundmentAmount().compareTo(FDCHelper.ZERO)>0)) {
			if(compInfo.getCompensateState().equals(RoomCompensateStateEnum.COMAUDITTED)){
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("compensateState");
				compInfo.setCompensateState(RoomCompensateStateEnum.COMRECEIVED);
				RoomAreaCompensateFactory.getLocalInstance(ctx).updatePartial(compInfo, selector);
			}				
		}else{				
			if(compInfo.getCompensateState().equals(RoomCompensateStateEnum.COMRECEIVED)){
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("compensateState");
				compInfo.setCompensateState(RoomCompensateStateEnum.COMAUDITTED);
				RoomAreaCompensateFactory.getLocalInstance(ctx).updatePartial(compInfo, selector);
			}
		}
	}
	
	
	
	
	private void handleWhenTheFirstRev(Context ctx,String purIdStr) throws EASBizException, BOSException{
		PurchaseInfo purInfo = PurchaseFactory.getLocalInstance(ctx).getPurchaseInfo(
				"select state,purchaseState,room.id,payListEntry.seq,payListEntry.ActRevAmount,elsePayListEntry.ActRevAmount where id='"+ purIdStr +"' ");
		if(!purInfo.getPurchaseState().equals(PurchaseStateEnum.PrePurchaseCheck) 
				&& !purInfo.getPurchaseState().equals(PurchaseStateEnum.PurchaseApply)
				&& !purInfo.getPurchaseState().equals(PurchaseStateEnum.PurchaseAuditing)
				&& !purInfo.getPurchaseState().equals(PurchaseStateEnum.PurchaseAudit))
			return;
		boolean isFirstRev = true;
		for(int i=0;i<purInfo.getPayListEntry().size();i++) {
			PurchasePayListEntryInfo payEntry = purInfo.getPayListEntry().get(i);
			if(payEntry.getActRevAmount().compareTo(new BigDecimal(0))>0) {
				isFirstRev = false;
				break;
			}
		}
		if(isFirstRev) {
			for(int i=0;i<purInfo.getElsePayListEntry().size();i++) {
				PurchaseElsePayListEntryInfo elseEntry = purInfo.getElsePayListEntry().get(i);
				if(elseEntry.getActRevAmount().compareTo(new BigDecimal(0))>0) {
					isFirstRev = false;
					break;
				}
			}	
		}
		if(isFirstRev) {
			//其他的对改房间有效的收过款的认购单
			PurchasePayListEntryCollection otherValidPayEntryColl =PurchasePayListEntryFactory.getLocalInstance(ctx).getPurchasePayListEntryCollection(
					"where head.id != '"+purInfo.getId()+"' and head.room.id='"+purInfo.getRoom().getId()+"' and ActRevAmount>0 " +
							"and (head.purchaseState = '"+PurchaseStateEnum.PREPURCHASEAPPLY_VALUE+"' or head.purchaseState = '"+PurchaseStateEnum.PREPURCHASECHECK_VALUE+"' or head.purchaseState ='"+PurchaseStateEnum.PURCHASEAPPLY_VALUE+"' " +
							" or  head.purchaseState ='"+PurchaseStateEnum.PURCHASEAUDITING_VALUE+"' or head.purchaseState ='"+PurchaseStateEnum.PURCHASEAUDIT_VALUE+"') " );

			PurchaseElsePayListEntryCollection otherValidElseEntryColl =PurchaseElsePayListEntryFactory.getLocalInstance(ctx).getPurchaseElsePayListEntryCollection(
					"where head.id != '"+purInfo.getId()+"' and head.room.id='"+purInfo.getRoom().getId()+"' and ActRevAmount>0 " +
							"and (head.purchaseState = '"+PurchaseStateEnum.PREPURCHASEAPPLY_VALUE+"' or head.purchaseState = '"+PurchaseStateEnum.PREPURCHASECHECK_VALUE+"' or head.purchaseState ='"+PurchaseStateEnum.PURCHASEAPPLY_VALUE+"' " +
							" or  head.purchaseState ='"+PurchaseStateEnum.PURCHASEAUDITING_VALUE+"' or head.purchaseState ='"+PurchaseStateEnum.PURCHASEAUDIT_VALUE+"') " );
			if(otherValidPayEntryColl.size()>0 || otherValidElseEntryColl.size()>0) {
				throw new EASBizException(new NumericExceptionSubItem("100", "该房间已存在其它有效的已收款的认购单，该认购单不能再提交收款！"));
			}
			
			PurchaseCollection otherValidPurColl = PurchaseFactory.getLocalInstance(ctx).getPurchaseCollection(
					"select id where id != '"+purInfo.getId()+"' and room.id='"+purInfo.getRoom().getId()+"' " +
							"and (purchaseState = '"+PurchaseStateEnum.PREPURCHASEAPPLY_VALUE+"' or purchaseState = '"+PurchaseStateEnum.PREPURCHASECHECK_VALUE+"' or purchaseState ='"+PurchaseStateEnum.PURCHASEAPPLY_VALUE+"' " +
							" or  purchaseState ='"+PurchaseStateEnum.PURCHASEAUDITING_VALUE+"' or purchaseState ='"+PurchaseStateEnum.PURCHASEAUDIT_VALUE+"') " );
			//未收款作废其它的有效的认购单
			for(int i=0;i<otherValidPurColl.size();i++) {
				PurchaseInfo tempInfo = otherValidPurColl.get(i);
				tempInfo.setPurchaseState(PurchaseStateEnum.NoPayBlankOut);
				CRMHelper.abortProcessWorkflow(ctx, tempInfo.getId().toString());
				
				SelectorItemCollection seletor = new SelectorItemCollection();
				seletor.add("PurchaseState");
				PurchaseFactory.getLocalInstance(ctx).updatePartial(tempInfo, seletor);
			}
		}			
	}
	
	//根据房间的所属项目查询出项目下的“认购业务以实际收款为准”参数是否启动
	public boolean isEnabledIsActGathering(Context ctx,PurchaseInfo purInfo){
		//by zgy  增加参数判断
		RoomDisplaySetting setting1 = new RoomDisplaySetting(ctx);
		HashMap functionSetMap = (HashMap)setting1.getFunctionSetMap();
		FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(purInfo.getSellProject().getId().toString());
		/**
		 * 当拿不到这个参数的时候funcSet是为空的，一定要做非空的判断
		 * new update by renliang at 2011-3-17
		 */
		
		if(funcSet!=null && funcSet.getIsActGathering()!=null){
			return funcSet.getIsActGathering().booleanValue();
		}		
		
		return false;
	}
			
	


	/**
	 收款对象是认购单时：
	 	房间状态正向变化-->
	 	当认购单状态时预订复核或认购申请状态时(房间为待售状态)，只要收足了预订金的最低标准金额，就会设置房间为预订状态;	 	
	 	当认购单为认购审批状态时(房间为待售或预订状态)，只要收齐了第一笔明细款后，就会设置房间的为认购状态；
	 	当认购单为认购审批状态时(房间为待售)，只要收第一笔明细款，未收起的情况下，就会设置房间的为预订状态；
	 	房间状态反向变化-->
	 	当认购单状态是预订复核或认购申请状态时(房间为预订状态)，只要不足预订金的最低标准金额，就会设置房间为待售状态;
	 	当认购单为认购审批状态时(房间为认购状态)，只要未收齐了第一笔明细款，就会设置房间的为预订状态；
	 	
	 	增加参数控制 ：  by zgy 2010-12-15
	 	认购业务以实际收款为准是否勾选，如果勾选保持原来业务不变，不勾选 ，收款与房间状态无关，这里就不反写房间的状态。
	 */
	private void updateRoomStateWhenPurRev(Context ctx,FDCReceivingBillInfo rev)throws BOSException, EASBizException {
		PurchaseInfo purInfo = rev.getPurchaseObj();		
		if(purInfo==null) return;
		Date bizDate = rev.getBizDate();
		if(bizDate==null) bizDate = new Date();	//预订、认购、签约等日期以收款日期为准 
		
		purInfo = PurchaseFactory.getLocalInstance(ctx).getPurchaseInfo(
					"select purchaseState,prePurLevelAmount,prePurchaseAmount,sellProject.id,dealPrice,dealAmount,sellType " +
					",room.SellState,room.buildingArea,room.actualBuildingArea,payListEntry.*,payListEntry.moneyDefine.moneyType " +
					",room.building.sellProject.id where id = '"+purInfo.getId()+"'");
		PurchaseStateEnum purState = purInfo.getPurchaseState();
		if(purState==null) return;
		RoomInfo roomInfo = purInfo.getRoom(); 
		if(roomInfo==null) return;
		if(!isEnabledIsActGathering(ctx,purInfo)){
			PurchasePayListEntryInfo firstEntryInfo = null;
			BigDecimal allRemainAmount = new BigDecimal(0);	//总的剩余金额 
			PurchasePayListEntryCollection payListColl = purInfo.getPayListEntry(); 
			CRMHelper.sortCollection(payListColl, "seq", true);
			for(int i=0;i<payListColl.size();i++) {
				PurchasePayListEntryInfo entryInfo = payListColl.get(i); 
				if(firstEntryInfo==null && !entryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney))  {
					firstEntryInfo = entryInfo;
				}
				allRemainAmount = allRemainAmount.add(entryInfo.getAllRemainAmount());
			}
			if(firstEntryInfo!=null) {
				boolean isFirstFinish = false;
				if(firstEntryInfo.getActRevAmount().compareTo(new BigDecimal(0))>0) {
					if(firstEntryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.EarnestMoney)) { //若是定金则比较应收和实收
						if(firstEntryInfo.getActRevAmount().compareTo(firstEntryInfo.getAppAmount())>=0) 
							isFirstFinish = true;
					}else if(firstEntryInfo.getAllRemainAmount().compareTo(firstEntryInfo.getAppAmount())>=0){
						isFirstFinish = true;
					}
				}
				if(isFirstFinish){ //第一笔款收完时 变认购			
					String sql = "update T_SHE_Room set FReceiptTypeState ='FirstRe' where fid = '"+roomInfo.getId().toString()+"'";
					DbUtil.execute(ctx, sql);
				}else{  //第一笔款未收完   变预订
					if(allRemainAmount.compareTo(new BigDecimal(0))>0) {	//还有剩余收款
						String sql = "update T_SHE_Room set FReceiptTypeState = null where fid = '"+roomInfo.getId().toString()+"'";
						DbUtil.execute(ctx, sql);
					}else{	
						String sql = "update T_SHE_Room set FReceiptTypeState = null where fid = '"+roomInfo.getId().toString()+"'";
						DbUtil.execute(ctx, sql);
					}
				}
			}
			return;
		}
		if(purState.equals(PurchaseStateEnum.PrePurchaseCheck) || purState.equals(PurchaseStateEnum.PurchaseApply)) {
			if(!roomInfo.getSellState().equals(RoomSellStateEnum.OnShow) && !roomInfo.getSellState().equals(RoomSellStateEnum.SincerPurchase) && !roomInfo.getSellState().equals(RoomSellStateEnum.PrePurchase)) return;
			
			
			BigDecimal allPreRevAmount = new BigDecimal(0);		//收到预订金的明细的现有收款合计
			for(int i=0;i<purInfo.getPayListEntry().size();i++) {	
				PurchasePayListEntryInfo entryInfo = purInfo.getPayListEntry().get(i);
				MoneyTypeEnum monType = entryInfo.getMoneyDefine().getMoneyType();
				if(monType!=null && monType.equals(MoneyTypeEnum.PreconcertMoney)) {
					allPreRevAmount = allPreRevAmount.add(entryInfo.getAllRemainAmount());
				}
			}
			if(allPreRevAmount.compareTo(new BigDecimal(0))<0) return;
			BigDecimal preLevalAmount = CRMHelper.getBigDecimal(purInfo.getPrePurLevelAmount());	//预订金的最低标准
			if(preLevalAmount.compareTo(FDCHelper.ZERO)==0) {
				SellProjectInfo spInfo = purInfo.getSellProject();
				if(spInfo==null) return;
				RoomDisplaySetting setting = new RoomDisplaySetting(ctx);
				Map preMoneySetMap = setting.getPreMoneySetMap();
				PreMoneySetting preMoneySet = (PreMoneySetting)preMoneySetMap.get(spInfo.getId().toString());
				if(preMoneySet!=null) preLevalAmount = preMoneySet.getPreLevelAmount();
			}			
			
			// && preLevalAmount.compareTo(FDCHelper.ZERO)>0
			if(roomInfo.getSellState().equals(RoomSellStateEnum.OnShow) || roomInfo.getSellState().equals(RoomSellStateEnum.SincerPurchase)) {	//房间由待售变为预订状态			
				if(allPreRevAmount.compareTo(preLevalAmount)>=0) {  //有最低标准,且预定金合计大于于它或者无预订金最低标准时	
					SheRoomStateFactory.setRoomPrePurState(ctx, roomInfo, purInfo, bizDate);
				}
			}else if(roomInfo.getSellState().equals(RoomSellStateEnum.PrePurchase)) {//房间由预订变为待售状态
				if(allPreRevAmount.compareTo(preLevalAmount)<0 || allPreRevAmount.compareTo(FDCHelper.ZERO)==0) {  
					SheRoomStateFactory.setRoomOnShowState(ctx, roomInfo, purInfo, false, false);
				}
			}
		}else if(purState.equals(PurchaseStateEnum.PurchaseAudit)) {		
			if(!roomInfo.getSellState().equals(RoomSellStateEnum.OnShow) && !roomInfo.getSellState().equals(RoomSellStateEnum.SincerPurchase) && !roomInfo.getSellState().equals(RoomSellStateEnum.PrePurchase)
					&& !roomInfo.getSellState().equals(RoomSellStateEnum.Purchase)) return;
			
			PurchasePayListEntryInfo firstEntryInfo = null;
			BigDecimal allRemainAmount = new BigDecimal(0);	//总的剩余金额 
			PurchasePayListEntryCollection payListColl = purInfo.getPayListEntry(); 
			CRMHelper.sortCollection(payListColl, "seq", true);
			for(int i=0;i<payListColl.size();i++) {
				PurchasePayListEntryInfo entryInfo = payListColl.get(i); 
				if(firstEntryInfo==null && !entryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney))  {
					firstEntryInfo = entryInfo;
				}
				allRemainAmount = allRemainAmount.add(entryInfo.getAllRemainAmount());
			}
			if(firstEntryInfo!=null) {
				boolean isFirstFinish = false;
				if(firstEntryInfo.getActRevAmount().compareTo(new BigDecimal(0))>0) {
					if(firstEntryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.EarnestMoney)) { //若是定金则比较应收和实收
						if(firstEntryInfo.getActRevAmount().compareTo(firstEntryInfo.getAppAmount())>=0) 
							isFirstFinish = true;
					}else if(firstEntryInfo.getAllRemainAmount().compareTo(firstEntryInfo.getAppAmount())>=0){
						isFirstFinish = true;
					}
				}
				if(isFirstFinish){ //第一笔款收完时 变认购			
					if(roomInfo.getSellState().equals(RoomSellStateEnum.OnShow) || roomInfo.getSellState().equals(RoomSellStateEnum.SincerPurchase) || roomInfo.getSellState().equals(RoomSellStateEnum.PrePurchase)){
						SheRoomStateFactory.setRoomPurState(ctx, roomInfo, purInfo, bizDate);  //待售或预订变认购状态		
					}
				}else{  //第一笔款未收完   变预订
					if(allRemainAmount.compareTo(new BigDecimal(0))>0) {	//还有剩余收款
						if(roomInfo.getSellState().equals(RoomSellStateEnum.OnShow) || roomInfo.getSellState().equals(RoomSellStateEnum.SincerPurchase) || roomInfo.getSellState().equals(RoomSellStateEnum.Purchase)){
							SheRoomStateFactory.setRoomPrePurState(ctx, roomInfo, purInfo, bizDate);  
						}
					}else{
						if(roomInfo.getSellState().equals(RoomSellStateEnum.PrePurchase) || roomInfo.getSellState().equals(RoomSellStateEnum.Purchase)){
							SheRoomStateFactory.setRoomOnShowState(ctx, roomInfo, purInfo,false,false);
						}
					}
				}
				
			}
		}
	
	}
	
	

	public ICoreBase getRevListBizInterface(Context ctx, RevListTypeEnum revListType) throws BOSException {
		if(revListType==null) return null;
		if(revListType.equals(RevListTypeEnum.sincerityPur)) {
			return ctx==null?SincerReceiveEntryFactory.getRemoteInstance():SincerReceiveEntryFactory.getLocalInstance(ctx);
		}else if(revListType.equals(RevListTypeEnum.purchaseRev)) {
			return ctx==null?PurchasePayListEntryFactory.getRemoteInstance():PurchasePayListEntryFactory.getLocalInstance(ctx);
		}else if(revListType.equals(RevListTypeEnum.purElseRev)) {
			return ctx==null?PurchaseElsePayListEntryFactory.getRemoteInstance():PurchaseElsePayListEntryFactory.getLocalInstance(ctx);
		}else if(revListType.equals(RevListTypeEnum.fdcCustomerRev)) {
			return ctx==null?CusRevListFactory.getRemoteInstance():CusRevListFactory.getLocalInstance(ctx);
		}else if(revListType.equals(RevListTypeEnum.areaCompensate)) {
			return ctx==null?AreaCompensateRevListFactory.getRemoteInstance():AreaCompensateRevListFactory.getLocalInstance(ctx);
		}
		
		return null;
	}


}
