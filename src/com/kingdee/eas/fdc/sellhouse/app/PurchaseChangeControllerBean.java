package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.ISHEPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

public class PurchaseChangeControllerBean extends
		AbstractPurchaseChangeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.PurchaseChangeControllerBean");

	protected void _setState(Context ctx, BOSUuid id, FDCBillStateEnum state)
			throws BOSException, EASBizException {
		PurchaseChangeInfo changeInfo = new PurchaseChangeInfo();
		changeInfo.setId(id);
		changeInfo.setState(state);
		SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
		selectorItemCollection.add("state");
		try {
			PurchaseChangeFactory.getLocalInstance(ctx).updatePartial(
					changeInfo, selectorItemCollection);
		} catch (Exception e) {
			throw new BOSException(e);
		}
	}

	protected void checkNameDup(Context ctx, FDCBillInfo billInfo) throws BOSException, EASBizException {
		
	}
	
	protected void _setAuditing(Context ctx, BOSUuid id) throws BOSException,
			EASBizException {
		_setState(ctx, id, FDCBillStateEnum.AUDITTING);
	}

	// base1是有数据的 base2是空的
	void changeObjectValue(CoreBaseInfo base1, CoreBaseInfo base2) {
		Enumeration enumeration = base1.keys();
		while (enumeration.hasMoreElements()) {
			String obj = enumeration.nextElement().toString();
			if (!"id".equals(obj)) {
				base2.put(obj, base1.get(obj));
			}
		}
	}

	protected void _setAudited(Context ctx, BOSUuid id) throws BOSException,
			EASBizException {
		_setState(ctx, id, FDCBillStateEnum.AUDITTED);
		SelectorItemCollection tsels = new SelectorItemCollection();
		tsels.add("purchase");
		tsels.add("reqDiscount");
		tsels.add("newDiscount");
		tsels.add("purchase.purchaseState");
		PurchaseChangeInfo pc = this.getPurchaseChangeInfo(ctx, new ObjectUuidPK(id), tsels);
		PurchaseInfo purchase = pc.getPurchase();

		//---不走工作流审批时，设置核准特殊折扣为申请的特殊折扣 zhicheng_jin 090611
		/*  本来 存在核准特殊折扣 和 申请的特殊折扣 两种的， 核准的字段是在审批时录入的，但后期被谁给改掉了 ，因而此段已无用  20100301 jeegan
		 */
		if(pc.getNewDiscount() == null){
			SelectorItemCollection chgSels = new SelectorItemCollection();
			pc.setNewDiscount(pc.getReqDiscount());
			chgSels.add("newDiscount");
			PurchaseChangeFactory.getLocalInstance(ctx).updatePartial(pc, chgSels);
		}
		//--------
		
		//---正在变更的认购单,已经被后台未付款作废,审批时不应该再反写认购单状态  zhicheng_jin 081229
		PurchaseStateEnum purchaseState = purchase.getPurchaseState();
		if(PurchaseStateEnum.ChangeRoomBlankOut.equals(purchaseState) ||
				PurchaseStateEnum.QuitRoomBlankOut.equals(purchaseState) ||
				PurchaseStateEnum.NoPayBlankOut.equals(purchaseState) ||
				PurchaseStateEnum.ManualBlankOut.equals(purchaseState) ||
				PurchaseStateEnum.AdjustBlankOut.equals(purchaseState)){
			return;
		}
		//-------------
		
		pc.setAuditTime(new Date());
		pc.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		SelectorItemCollection auditSels = new SelectorItemCollection();
		auditSels.add("auditTime");
		auditSels.add("auditor");
		_updatePartial(ctx, pc, auditSels);
		
		purchase.setPurchaseState(PurchaseStateEnum.PurchaseAudit);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("purchaseState");
		PurchaseFactory.getLocalInstance(ctx).updatePartial(purchase, sels);
	}

	protected void _purchaseChangeToPurchase(Context ctx, BOSUuid id)
			throws BOSException, EASBizException {
		SelectorItemCollection selectorItemCol = new SelectorItemCollection();
		selectorItemCol.add("*");
		selectorItemCol.add("newRoomAttachmentEntry.*");
		selectorItemCol.add("newDiscountEntrys.*");
		selectorItemCol.add("newPayListEntrys.*");
		selectorItemCol.add("purchase.*");
		selectorItemCol.add("purchase.room.*");
		selectorItemCol.add("purchase.purchaseState");
		PurchaseChangeInfo changeInfo = PurchaseChangeFactory.getLocalInstance(
				ctx).getPurchaseChangeInfo(new ObjectUuidPK(id.toString()),
						selectorItemCol);

		// 反写数据
		SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
		PurchaseInfo purchaseInfo = changeInfo.getPurchase();

		CoreBaseCollection purchaseAgioEntryCollection = new CoreBaseCollection();
		for (int i = 0; i < changeInfo.getNewDiscountEntrys().size(); i++) {
			PurchaseAgioEntryInfo purchaseAgioEntryInfo = new PurchaseAgioEntryInfo();
			changeObjectValue(changeInfo.getNewDiscountEntrys().get(i),
					purchaseAgioEntryInfo);
			purchaseAgioEntryInfo.setHead(purchaseInfo);
			purchaseAgioEntryCollection.add(purchaseAgioEntryInfo);
		}
		//这里有问题，不知道在做什么  需要后面继续搞明白
//		PurchaseAgioEntryFactory.getLocalInstance(ctx).delete(
//				"where head = '" + purchaseInfo.getId().toString() + "'");
//		PurchaseAgioEntryFactory.getLocalInstance(ctx).save(
//				purchaseAgioEntryCollection);

		//是否底价销售
		purchaseInfo.setIsBasePriceSell(changeInfo.isNewIsBasePriceSell());
		//-- 取整方式 zhicheng_jin  090304
		purchaseInfo.setIsAutoToInteger(changeInfo.isNewIsAutoToInteger());
		purchaseInfo.setToIntegerType(changeInfo.getNewToIntegerType());
		purchaseInfo.setDigit(changeInfo.getNewDigit());
		selectorItemCollection.add("isAutoToInteger");
		selectorItemCollection.add("isBasePriceSell");
		selectorItemCollection.add("toIntegerType");
		selectorItemCollection.add("digit");
		//-----------

		CoreBaseCollection attachmentEntryCollection = new CoreBaseCollection();
		for (int i = 0; i < changeInfo.getNewRoomAttachmentEntry().size(); i++) {
			PurchaseRoomAttachmentEntryInfo purchaseRoomAttachmentEntryInfo = new PurchaseRoomAttachmentEntryInfo();
			changeObjectValue(changeInfo.getNewRoomAttachmentEntry().get(i),
					purchaseRoomAttachmentEntryInfo);
			purchaseRoomAttachmentEntryInfo.setHead(purchaseInfo);
			attachmentEntryCollection.add(purchaseRoomAttachmentEntryInfo);
		}
		PurchaseRoomAttachmentEntryFactory.getLocalInstance(ctx).delete(
				"where head = '" + purchaseInfo.getId().toString() + "'");
		PurchaseRoomAttachmentEntryFactory.getLocalInstance(ctx).save(
				attachmentEntryCollection);
		// 附属房产金额
		purchaseInfo.setAttachmentAmount(changeInfo.getNewAttachmentAmount());
		selectorItemCollection.add("attachmentAmount");

		String newIdsStr = "'nullnull'";

		for (int i = 0; i < changeInfo.getNewPayListEntrys().size(); i++) {
			PurchasePayListEntryInfo purchasePayListEntryInfo = new PurchasePayListEntryInfo();
			changeObjectValue(changeInfo.getNewPayListEntrys().get(i),purchasePayListEntryInfo);
			purchasePayListEntryInfo.setHead(purchaseInfo);
			//--- 为保证已有收款的付款明细ID不变,前面将认购分录ID的值放入了变更分录number上,现在从里面取出
			String number = changeInfo.getNewPayListEntrys().get(i).getNumber();			
			if(!StringUtils.isEmpty(number)){
				purchasePayListEntryInfo = PurchasePayListEntryFactory.getLocalInstance(ctx)
										.getPurchasePayListEntryInfo("select * where id ='"+number+"'"); 
				newIdsStr += ",'"+number+"'";
			}else{//新增
				BOSUuid newId = BOSUuid.create(purchasePayListEntryInfo.getBOSType());
				purchasePayListEntryInfo.setId(newId);
				newIdsStr += ",'"+newId.toString()+"'";
				PurchasePayListEntryFactory.getLocalInstance(ctx).addnew(purchasePayListEntryInfo);
				continue;
			}
			
			purchasePayListEntryInfo.setIsRemainCanRefundment(false);
			purchasePayListEntryInfo.setAppAmount(changeInfo.getNewPayListEntrys().get(i).getAppAmount());
			purchasePayListEntryInfo.setDescription(changeInfo.getNewPayListEntrys().get(i).getDescription());
			purchasePayListEntryInfo.setAppDate(changeInfo.getNewPayListEntrys().get(i).getAppDate());
			purchasePayListEntryInfo.setMoneyDefine(changeInfo.getNewPayListEntrys().get(i).getMoneyDefine());
			//变更后认购单可能存在多收的情况，此时在审批时更新下可退金额			
			BigDecimal canRefundmentAmount = purchasePayListEntryInfo.getAllRemainAmount().subtract(purchasePayListEntryInfo.getAppAmount()); 
			//purchasePayListEntryInfo.setLimitAmount(canRefundmentAmount); 这里可能之前退过、转过或调整过
			canRefundmentAmount = canRefundmentAmount.add(purchasePayListEntryInfo.getHasRefundmentAmount()).add(purchasePayListEntryInfo.getHasTransferredAmount()).add(purchasePayListEntryInfo.getHasAdjustedAmount());
			purchasePayListEntryInfo.setLimitAmount(canRefundmentAmount);			

			SelectorItemCollection updateTemp = new SelectorItemCollection();
			updateTemp.add("limitAmount");
			updateTemp.add("moneyDefine");
			updateTemp.add("appDate");
			updateTemp.add("isRemainCanRefundment");
			updateTemp.add("appAmount");
			updateTemp.add("description");
			PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(purchasePayListEntryInfo, updateTemp);
		}
		PurchasePayListEntryFactory.getLocalInstance(ctx).delete(
				"where head = '" + purchaseInfo.getId().toString() + "' and id not in("+newIdsStr+")" );
		
		// 付款方案
		purchaseInfo.setPayType(changeInfo.getNewPayType());
		selectorItemCollection.add("payType.id");
		// 装修金额
		purchaseInfo.setFitmentAmount(changeInfo.getNewFitmentAmount());
		selectorItemCollection.add("FitmentAmount");
		// 装修单价
		RoomInfo roomInfo = changeInfo.getPurchase().getRoom();
		boolean isLocaleSell = SellTypeEnum.LocaleSell.equals(changeInfo
				.getNewSellType());
		BigDecimal area = getValidArea(roomInfo, isLocaleSell);
		if (area.compareTo(FDCHelper.ZERO) != 0) {
			if (changeInfo.getNewFitmentAmount() != null) {
				purchaseInfo.setFitmentPrice(changeInfo.getNewFitmentAmount()
						.divide(area, 2, BigDecimal.ROUND_HALF_UP));
			} else {
				purchaseInfo.setFitmentPrice(null);
			}
		} else {
			purchaseInfo.setFitmentPrice(null);
		}
		selectorItemCollection.add("fitmentPrice");
		// 装修金额是否入合同
		purchaseInfo.setIsFitmentToContract(changeInfo
				.isNewIsFitmentToContract());
		selectorItemCollection.add("isFitmentToContract");
		// 销售方式
		if (changeInfo.getNewSellType() != null) {
			purchaseInfo.setSellType(changeInfo.getNewSellType());
		}
		selectorItemCollection.add("sellType");
		// 面积补差金额
		purchaseInfo.setAreaCompensateAmount(changeInfo
				.getNewCompensateAmount());
		selectorItemCollection.add("areaCompensateAmount");
		// 按揭贷款金额
		purchaseInfo.setLoanAmount(changeInfo.getNewLoanAmount());
		selectorItemCollection.add("loanAmount");
		// 公积金金额
		purchaseInfo.setAccumulationFundAmount(changeInfo
				.getNewAccuFundAmount());
		selectorItemCollection.add("accumulationFundAmount");
		purchaseInfo.setSpecialAgioType(changeInfo.getNewSpecialAgioType());
		selectorItemCollection.add("specialAgioType");
		// 特殊折扣
		if (changeInfo.getNewDiscount() != null) {
			purchaseInfo.setSpecialAgio(changeInfo.getNewDiscount());
		}else if (changeInfo.getReqDiscount() != null) {
			purchaseInfo.setSpecialAgio(changeInfo.getReqDiscount());
		}
		//---特殊折扣不提供折上折功能，所以如果没有折时清空特殊折扣--jinzhicheng  081129
		else{
			purchaseInfo.setSpecialAgio(null);
		}
		//-------------------
		
		selectorItemCollection.add("specialAgio");
		// 总成交价格
		purchaseInfo.setDealAmount(changeInfo.getNewDealAmount());
		selectorItemCollection.add("dealAmount");
		// 总合同价格
		purchaseInfo.setContractTotalAmount(changeInfo.getNewContractAmount());
		selectorItemCollection.add("contractTotalAmount");
		
		purchaseInfo.setDealPrice(changeInfo.getNewDealPrice());
		purchaseInfo.setContractBuildPrice(changeInfo.getNewBuildingPrice());
		purchaseInfo.setContractRoomPrice(changeInfo.getNewRoomPrice());
		purchaseInfo.setDealBuildPrice(changeInfo.getNewDealBuildingPrice());
		purchaseInfo.setPurchaseDate(changeInfo.getNewPurchaseDate());
		purchaseInfo.setPlanSignDate(changeInfo.getNewPlanSignDate());
		// 成交建筑单价
//		area = getValidArea(roomInfo, isLocaleSell);
//		BigDecimal[] buildAndRoomArea = getBuildAndRoomArea(roomInfo,
//				isLocaleSell);
//
//		if (area != null && area.compareTo(FDCHelper.ZERO) != 0) {
//			BigDecimal dealPrice = changeInfo.getNewDealAmount().divide(area,
//					2, BigDecimal.ROUND_HALF_UP);
//			purchaseInfo.setDealPrice(dealPrice);
//		}
//		if (buildAndRoomArea[0] != null
//				&& buildAndRoomArea[0].compareTo(FDCHelper.ZERO) != 0) {
//			purchaseInfo.setContractBuildPrice((changeInfo
//					.getNewContractAmount().divide(buildAndRoomArea[0], 2,
//					BigDecimal.ROUND_HALF_UP)));
//			purchaseInfo.setDealBuildPrice(changeInfo.getNewDealAmount()
//					.divide(buildAndRoomArea[0], 2, BigDecimal.ROUND_HALF_UP));
//		}
//		if (buildAndRoomArea[1] != null
//				&& buildAndRoomArea[1].compareTo(FDCHelper.ZERO) != 0) {
//			purchaseInfo.setContractRoomPrice((changeInfo
//					.getNewContractAmount().divide(buildAndRoomArea[1], 2,
//					BigDecimal.ROUND_HALF_UP)));
//			purchaseInfo.setDealRoomPrice(changeInfo.getNewDealAmount().divide(
//					buildAndRoomArea[1], 2, BigDecimal.ROUND_HALF_UP));
//		}
		
		
		selectorItemCollection.add("dealBuildPrice");
		selectorItemCollection.add("dealRoomPrice");
		selectorItemCollection.add("contractBuildPrice");
		selectorItemCollection.add("contractRoomPrice");
		selectorItemCollection.add("dealPrice");
		selectorItemCollection.add("purchaseDate");
		selectorItemCollection.add("planSignDate");
		

		PurchaseFactory.getLocalInstance(ctx).updatePartial(purchaseInfo,
				selectorItemCollection);

		//---正在变更的认购单,已经被后台未付款作废,审批时不应该再反写房间价格等信息  zhicheng_jin 081229
		PurchaseStateEnum purchaseState = changeInfo.getPurchase().getPurchaseState();
		if(PurchaseStateEnum.ChangeRoomBlankOut.equals(purchaseState) ||
				PurchaseStateEnum.QuitRoomBlankOut.equals(purchaseState) ||
				PurchaseStateEnum.NoPayBlankOut.equals(purchaseState) ||
				PurchaseStateEnum.ManualBlankOut.equals(purchaseState) ||
				PurchaseStateEnum.AdjustBlankOut.equals(purchaseState)){
			return;
		}
		//-------------
		//---未收款的房间相应的认购单变更审批时,不应反写房间价格等信息 zhicheng_jin 081231 ---
		RoomSellStateEnum roomSellState = purchaseInfo.getRoom().getSellState();
		if(!(RoomSellStateEnum.PrePurchase.equals(roomSellState) ||
				RoomSellStateEnum.Purchase.equals(roomSellState) ||
				RoomSellStateEnum.Sign.equals(roomSellState))){
			return;
		}
		//-------------
		
		//更新房间价格附属房产等信息
		RoomStateFactory.setRoomSellState(ctx, null, purchaseInfo.getId().toString());
	}

	/**
	 * 获得建筑和套内面积,由isLocaleSell决定是否为实测面积
	 * 
	 * @param room
	 * @param isLocaleSell
	 *            是否现售
	 * @return 长度为2的数组,分别表示建筑和套内的面积
	 */
	private BigDecimal[] getBuildAndRoomArea(RoomInfo room, boolean isLocaleSell) {
		BigDecimal[] areas = new BigDecimal[2];
		if (isLocaleSell) {
			areas[0] = room.getActualBuildingArea();
			areas[1] = room.getActualRoomArea();
		} else {
			areas[0] = room.getBuildingArea();
			areas[1] = room.getRoomArea();
		}
		if (areas[0] == null) {
			areas[0] = FDCHelper.ZERO;
		}
		if (areas[1] == null) {
			areas[1] = FDCHelper.ZERO;
		}
		return areas;
	}

	/**
	 * 获取房间的有效面积
	 * 
	 * @param room
	 * @param isLocaleSell
	 *            是否是现售
	 * @return 用来进行计算的有效面积
	 */
	private BigDecimal getValidArea(RoomInfo room, boolean isLocaleSell) {
		boolean isCalByRoomArea = room.isIsCalByRoomArea();
		BigDecimal area = null;
		if (isCalByRoomArea) {
			// 如果使现售,成交单价按照实测面积计算
			if (isLocaleSell) {
				area = room.getActualRoomArea();
			} else {
				area = room.getRoomArea();
			}
		} else {
			if (isLocaleSell) {
				area = room.getActualBuildingArea();
			} else {
				area = room.getBuildingArea();
			}
		}
		if (area == null) {
			area = FDCHelper.ZERO;
		}
		return area;
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		_setAudited(ctx, billId);
		_purchaseChangeToPurchase(ctx, billId);
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._unAudit(ctx, billId);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		PurchaseChangeInfo pc = (PurchaseChangeInfo) model;
		PurchaseInfo purchase = pc.getPurchase();
		purchase.setPurchaseState(PurchaseStateEnum.PurchaseChange);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("purchaseState");
		PurchaseFactory.getLocalInstance(ctx).updatePartial(purchase, sels);
		return super._submit(ctx, model);
	}
}