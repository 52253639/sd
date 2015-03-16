package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.EventTypeFactory;
import com.kingdee.eas.fdc.sellhouse.EventTypeInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomACCFundStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomLoanStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.TrackPhaseEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectFactory;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.util.UuidException;

public class RoomStateFactory {
	private static Logger logger = Logger
	.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomStateFactory");


	public static void setRoomOnShow(Context ctx, String roomId, boolean isQuitRoom)
	throws EASBizException, BOSException {
		setRoomOnShow(ctx, roomId, isQuitRoom, false);
	}

	/**
	 * 把房间状态置为待售状态
	 * isQuitRoom 是否是退房业务
	 * isAdjust 是否是调整业务
	 */	
	public static void setRoomOnShow(Context ctx, String roomId, boolean isQuitRoom, boolean isAdjust)
	throws EASBizException, BOSException {
		SelectorItemCollection roomSels = new SelectorItemCollection();
		roomSels.add("sellState");
		roomSels.add("dealPrice");
		roomSels.add("dealTotalAmount");
		roomSels.add("lastPurchase");
		roomSels.add("lastSignContract");
		roomSels.add("toPrePurchaseDate");
		roomSels.add("toPurchaseDate");
		roomSels.add("toSignDate");
		roomSels.add("toSaleDate");
		roomSels.add("roomLoanState");
		roomSels.add("roomJoinState");
		roomSels.add("roomACCFundState");
		roomSels.add("roomBookState");
		roomSels.add("roomCompensateState");

		roomSels.add("areaCompensateAmount");
		roomSels.add("sellAmount");

		roomSels.add("saleArea");
		roomSels.add("isActualAreaAudited");
		roomSels.add("buildingArea");
		roomSels.add("actualBuildingArea");

		RoomInfo room = RoomFactory.getLocalInstance(ctx).getRoomInfo(
				new ObjectUuidPK(BOSUuid.read(roomId)), roomSels);
		// 更改认购单状态
		PurchaseInfo purchase = room.getLastPurchase();
		if(isQuitRoom && purchase != null){
			purchase.setPurchaseState(PurchaseStateEnum.QuitRoomBlankOut);
			purchase.setState(FDCBillStateEnum.INVALID);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("purchaseState");
			sels.add("state");
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purchase, sels);
		}
		
		if(isAdjust && purchase != null){
			purchase.setPurchaseState(PurchaseStateEnum.AdjustBlankOut);
			purchase.setState(FDCBillStateEnum.INVALID);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("purchaseState");
			sels.add("state");
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purchase, sels);
		}

		// 更改房间状态
		room.setSellState(RoomSellStateEnum.OnShow);
		room.setDealPrice(null);
		room.setDealTotalAmount(null);
		room.setLastPurchase(null);
		room.setLastSignContract(null);
		room.setToPrePurchaseDate(null);
		room.setToPurchaseDate(null);
		room.setToSaleDate(null);
		room.setToSignDate(null);

		room.setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
		room.setRoomJoinState(RoomJoinStateEnum.NOTJOIN);
		room.setRoomACCFundState(RoomACCFundStateEnum.NOTFUND);
		room.setRoomBookState(RoomBookStateEnum.NOTBOOKED);
		room.setRoomCompensateState(RoomCompensateStateEnum.NOCOMPENSATE);
		room.setAreaCompensateAmount(null);
		room.setSellAmount(null);

		if(room.isIsActualAreaAudited()){
			room.setSaleArea(room.getActualBuildingArea());
		}else{
			room.setSaleArea(room.getBuildingArea());
		}

		RoomFactory.getLocalInstance(ctx).updatePartial(room, roomSels);

		// 更改房间绑定属性
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("attachmentEntry.room.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("attachmentEntry.head.id", room.getId().toString()));
		PurchaseRoomAttachmentEntryCollection attachmentEntry = PurchaseRoomAttachmentEntryFactory
		.getLocalInstance(ctx).getPurchaseRoomAttachmentEntryCollection(view);
		for (int i = 0; i < attachmentEntry.size(); i++) {
			PurchaseRoomAttachmentEntryInfo info = attachmentEntry.get(i);
			RoomInfo aRoom = info.getAttachmentEntry().getRoom();
			aRoom.setSellState(RoomSellStateEnum.OnShow);
			aRoom.setToPrePurchaseDate(null);
			aRoom.setToPurchaseDate(null);
			aRoom.setToSignDate(null);
			aRoom.setToSaleDate(null);
			aRoom.setDealPrice(null);
			aRoom.setDealTotalAmount(null);
			aRoom.setAreaCompensateAmount(null);
			aRoom.setSellAmount(null);
			RoomFactory.getLocalInstance(ctx)
			.updatePartial(aRoom, roomSels);
		}
	}



	public static void setRoomSellState(Context ctx,
			RoomSellStateEnum newState, String purchaseId)
	throws EASBizException, BOSException {		
		setRoomSellState(ctx,newState,purchaseId,null);
	}


	/**
	 * 修改房间状态
	 * @param newState
	 * 			新的房间状态,如果为Null,则表示房间状态不改变(主要针对变更审批操作) by zhicheng_jin 081124
	 * 
	 * 为了把收款单的单据日期反写道房间的认购日期中，因而增加了toPurchaseDate字段   by jeegan 081218
	 * toPurchaseDate 指定的转认购日期
	 * */
	public static void setRoomSellState(Context ctx,
			RoomSellStateEnum newState, String purchaseId,Date toPurchaseDate)
	throws EASBizException, BOSException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("room.*");
		sels.add("salesman.*");
		sels.add("customerInfo.*");
		sels.add("customerInfo.customer.*");
		PurchaseInfo purchaseInfo = PurchaseFactory.getLocalInstance(ctx)
				.getPurchaseInfo(new ObjectUuidPK(BOSUuid.read(purchaseId)),sels);
		
		int operateType = -1;
		RoomInfo room = purchaseInfo.getRoom();
		room.setLastPurchase(purchaseInfo);
		RoomSellStateEnum oldState = room.getSellState();
		if(newState == null){
			newState = oldState;
		}
		
		toPurchaseDate = toPurchaseDate == null ? new Date() : toPurchaseDate;
		boolean isChangeState = !newState.equals(oldState);
		if (isChangeState) {
			if (newState.equals(RoomSellStateEnum.PrePurchase)) {
				if (oldState.equals(RoomSellStateEnum.OnShow)) {
					blankOutElesPurchase(ctx, purchaseInfo);
					if(room.getToPrePurchaseDate() == null){//如果已存在预定日期，以前面的日期为准
						room.setToPrePurchaseDate(toPurchaseDate);	
					}
					// --- 房间增加toSaleDate字段后,明确toPrePurchaseDate和toPurchaseDate的含义    zhicheng_jin  090313
					//					room.setToPurchaseDate(new Date());
					if(room.getToSaleDate() == null){
						room.setToSaleDate(toPurchaseDate);
					}
					//------------
				} else if (oldState.equals(RoomSellStateEnum.Purchase)) {
					room.setToPurchaseDate(null);
					if(room.getToPrePurchaseDate() == null)					{
						room.setToPrePurchaseDate(toPurchaseDate);
						room.setToSaleDate(toPurchaseDate);
					}

					//如果房间新状态为预定，但是老状态为认购，说明分录已经写到认购单里了。
					//现在改为预定是否要把认购单分单删掉(孙海亮说不删，代码暂时注销掉)
//					FilterInfo filter3 = new FilterInfo();
//					filter3.getFilterItems().add(
//							new FilterItemInfo("head.id",purchaseInfo.getId().toString()));
//					PurDistillCommisionEntryFactory.getLocalInstance(ctx).delete(filter3);
//
//					EntityViewInfo view = new EntityViewInfo();
//					view.getSelector().add("fdcReceiveBill.*");
//					view.getSelector().add("fdcReceiveBill.distillCommisionEntry.*");
//					FilterInfo filter = new FilterInfo();
//					filter.getFilterItems().add(
//							new FilterItemInfo("fdcReceiveBill.purchase.id", purchaseInfo
//									.getId().toString()));
//					filter.getFilterItems().add(
//							new FilterItemInfo("billStatus", "12"));
//					filter.getFilterItems().add(
//							new FilterItemInfo("billStatus", "14"));
//					filter.setMaskString("#0 and (#1 or #2)");
//					view.setFilter(filter);
//					ReceivingBillCollection recColl = ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection(view);
//					for(int i=0;i<recColl.size();i++)
//					{
//						FDCReceiveBillInfo fdcRecInfo = recColl.get(i).getFdcReceiveBill();
//						FilterInfo filter2 = new FilterInfo();
//						filter2.getFilterItems().add(
//								new FilterItemInfo("head.id",fdcRecInfo.getId().toString()));
//						ReceiveDistillCommisionEntryFactory.getLocalInstance(ctx).delete(filter2);
//					}

				}
			} else if (newState.equals(RoomSellStateEnum.Purchase)) {
				if (oldState.equals(RoomSellStateEnum.OnShow)) {
					blankOutElesPurchase(ctx, purchaseInfo);
				}

				//取收款日期而不取当前日期，是因为有收款单补录数据的情况，必须以收款单日期为准
				//				else if (oldState.equals(RoomSellStateEnum.PrePurchase)) {
				//					room.setToPurchaseDate(new Date());
				//				}
				if(toPurchaseDate!=null){
					if(room.getToPurchaseDate() == null){
						room.setToPurchaseDate(toPurchaseDate);
					}
					if(room.getToSaleDate() == null){
						room.setToSaleDate(toPurchaseDate);
					}
				}
				//当认购房间状态第一次转为认购状态的时候把分单分录保存到认购单上
				if(oldState.equals(RoomSellStateEnum.OnShow) || oldState.equals(RoomSellStateEnum.PrePurchase))
				{
					doPurDistillBiz(ctx,purchaseInfo);
				}

				operateType = SUBSCRIBE;
			} else if (newState.equals(RoomSellStateEnum.Sign)) {//现在变更房间状态为签约状态,其实是在签约提交的时候处理的,未调用该函数
				// if (room.getSellState().equals(RoomSellStateEnum.OnShow)) {
				// throw new BOSException("onShow room can't be siged
				// directly!");
				// } else
				if (oldState.equals(RoomSellStateEnum.PrePurchase)) {
					throw new BOSException(
					"prePurchase room can't be siged directly!");
				} else if (oldState.equals(RoomSellStateEnum.Purchase)) {
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("purchase.id", purchaseInfo
									.getId().toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("isBlankOut", Boolean.FALSE));
					view.setFilter(filter);
					RoomSignContractCollection signs = RoomSignContractFactory
					.getLocalInstance(ctx)
					.getRoomSignContractCollection(view);
					if (signs.size() == 1) {
						RoomSignContractInfo sign = signs.get(0);
						room.setLastSignContract(sign);
						room.setToSignDate(new Date());
					} else {
						newState = RoomSellStateEnum.Purchase;
					}
				}
				operateType = SIGN_UP;
			} else if(newState.equals(RoomSellStateEnum.OnShow)){//如果房间变为待售状态，修改房间的业务日期为空
				//如果要修改房间为onshow状态，请调用setRoomOnshow()函数
				throw new BOSException("系统逻辑错误.");
			}
		}
		
		
		room.setSellState(newState);
		room.setDealTotalAmount(purchaseInfo.getDealAmount());
		BigDecimal compensateAmount = room.getAreaCompensateAmount();
		if(compensateAmount == null){
			compensateAmount = FDCHelper.ZERO;
		}
		BigDecimal dealAmount = purchaseInfo.getDealAmount();
		if(dealAmount == null){
			dealAmount = FDCHelper.ZERO;
		}
		room.setSellAmount(dealAmount.add(compensateAmount));

		//根据认购单的现售和预售修改销售面积
		if(SellTypeEnum.PreSell.equals(purchaseInfo.getSellType())){
			room.setSaleArea(room.getBuildingArea());
		}else{
			room.setSaleArea(room.getActualBuildingArea());
		}
		
		room.setDealPrice(purchaseInfo.getDealPrice());

		//		room.setAreaCompensateAmount(purchaseInfo.getAreaCompensateAmount());
		sels = new SelectorItemCollection();
		sels.add("sellState");
		sels.add("dealPrice");
		sels.add("dealTotalAmount");
		sels.add("sellAmount");
		sels.add("toSignDate");
		sels.add("toPrePurchaseDate");
		sels.add("toPurchaseDate");
		sels.add("toSaleDate");
		sels.add("lastPurchase");
		sels.add("lastSignContract");
		sels.add("saleArea");
		//		sels.add("areaCompensateAmount");
		RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
		// 更改绑定房间属性
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("attachmentEntry.room.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("attachmentEntry.head.id", room.getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", purchaseInfo.getId().toString()));
		PurchaseRoomAttachmentEntryCollection attachmentEntry = PurchaseRoomAttachmentEntryFactory
		.getLocalInstance(ctx).getPurchaseRoomAttachmentEntryCollection(view);
		for (int i = 0; i < attachmentEntry.size(); i++) {
			PurchaseRoomAttachmentEntryInfo info = attachmentEntry.get(i);
			RoomInfo aRoom = info.getAttachmentEntry().getRoom();
			aRoom.setSellState(newState);
			aRoom.setToPrePurchaseDate(room.getToPrePurchaseDate());
			aRoom.setToPurchaseDate(room.getToPurchaseDate());
			aRoom.setToSignDate(room.getToSignDate());
			aRoom.setToSaleDate(room.getToSaleDate());

			//-----------修改并入合同的附属房产的成交总价和成交单价 jinzhicheng 081011
			//TODO 有点问题,附属房产若没有实测复核,且认购单为现购,则无法计算附属房产的成交单价
			BigDecimal mergeAmount = info.getMergeAmount();
			if (mergeAmount == null) {
				mergeAmount = FDCHelper.ZERO;
			}
			aRoom.setDealTotalAmount(mergeAmount);
			aRoom.setSellAmount(mergeAmount);
			boolean isCalByRoomArea = room.isIsCalByRoomArea();
			BigDecimal area = null;
			if (isCalByRoomArea) {
				// 如果使现售,成交单价按照实测面积计算
				if (SellTypeEnum.LocaleSell.equals(purchaseInfo.getSellType())) {
					area = room.getActualRoomArea();
				} else {
					area = room.getRoomArea();
				}
			} else {
				if (SellTypeEnum.LocaleSell.equals(purchaseInfo.getSellType())) {
					area = room.getActualBuildingArea();
				} else {
					area = room.getBuildingArea();
				}
			}
			if (area != null && area.compareTo(FDCHelper.ZERO) != 0) {
				aRoom.setDealPrice(mergeAmount.divide(area, 2,
						BigDecimal.ROUND_HALF_UP));
			}
			//---------------------------------------

			RoomFactory.getLocalInstance(ctx).updatePartial(aRoom, sels);
		}


		//如果修改了房间的3个时间，同时也修改认购单上的相应时间
		if(isChangeState && (newState.equals(RoomSellStateEnum.Purchase) || newState.equals(RoomSellStateEnum.PrePurchase))){
			purchaseInfo.setToPrePurchaseDate(room.getToPrePurchaseDate());
			purchaseInfo.setToPurchaseDate(room.getToPurchaseDate());
			purchaseInfo.setToSaleDate(room.getToSaleDate());

			SelectorItemCollection purSels = new SelectorItemCollection();
			purSels.add("toPrePurchaseDate");
			purSels.add("toPurchaseDate");
			purSels.add("toSaleDate");

			PurchaseFactory.getLocalInstance(ctx).updatePartial(purchaseInfo, purSels);
		}
	}

	// ------------------操作类别定义---------------------
	public static final int SINCERITY_SUBSCRIBE = 10;// 诚意认购

	public static final int SUBSCRIBE = 11;// 认购

	public static final int SIGN_UP = 12;// 签约

	public static final int CANCEL_ROOM = 13;// 退房

	public static final int CHANGE_ROOM = 14;// 换房(暂无该功能)

	public static final int JOIN = 15;// 入伙

	/**
	 * 增加客户跟进记录(嵌入到签约，认购等事件中)
	 * 
	 * @param ctx
	 *            当前上下文
	 * @param customerInfo
	 *            发生操作的客户
	 * @param type
	 *            操作类别,参见上面的类别定义
	 */
	public static void addTrackRecord(Context ctx,
			FDCCustomerInfo customerInfo, int type) {
		//-- 跟进记录全由用户录入,不再进行反写操作  zhicheng_jin  090312
		if(true) return;
		//-----
		String eventTypeId = null;
		String msg = null;
		if (type == SINCERITY_SUBSCRIBE) {
			eventTypeId = "NCHuCQEbEADgAAyqwKgSwvNIuHk=";
			msg = "诚意认购";
		} else if (type == SUBSCRIBE) {
			eventTypeId = "dpNwoAEbEADgABOhwKgSwvNIuHk=";
			msg = "认购";
		} else if (type == SIGN_UP) {
			eventTypeId = "dpNwoAEbEADgABOlwKgSwvNIuHk=";
			msg = "签约";
		} else if (type == CANCEL_ROOM) {
			eventTypeId = "dpNwoAEbEADgABOowKgSwvNIuHk=";
			msg = "退房";
		} else if (type == CHANGE_ROOM) {
			eventTypeId = "dpNwoAEbEADgABOswKgSwvNIuHk=";
			msg = "换房";
		} else if (type == JOIN) {
			eventTypeId = "dpNwoAEbEADgABOvwKgSwvNIuHk=";
			msg = "入伙";
		} else {
			return;
		}

		EventTypeInfo eventTypeInfo = null;
		try {
			eventTypeInfo = EventTypeFactory.getLocalInstance(ctx)
			.getEventTypeInfo(
					new ObjectUuidPK(BOSUuid.read(eventTypeId)));
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (UuidException e) {
			e.printStackTrace();
		}
		if (eventTypeInfo == null) {
			logger.warn("客户事件类型预设数据出错  type:" + type + "  eventTypeId:"
					+ eventTypeId);
			return;
		}

		TrackRecordInfo recordInfo = new TrackRecordInfo();
		recordInfo.setHead(customerInfo);
		recordInfo.setEventType(eventTypeInfo);
		recordInfo.setEventDate(new Date());
		recordInfo.setCreateTime(new Timestamp(new Date().getTime()));
		recordInfo.setDescription(msg);

		try {
			TrackRecordFactory.getLocalInstance(ctx).addnew(recordInfo);
		} catch (EASBizException e) {
			logger.warn("反写客户跟进记录异常：" + e.getMessage());
			logger.debug("", e);
		} catch (BOSException e) {
			logger.warn("反写客户跟进记录异常：" + e.getMessage());
			logger.debug("", e);
		}
	}

	
	/**作废掉其他有效的认购单，并终止其工作流  */
	private static void blankOutElesPurchase(Context ctx,
			PurchaseInfo purchaseInfo) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("room.id", purchaseInfo.getRoom().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.ChangeRoomBlankOut,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.ManualBlankOut,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.NoPayBlankOut,	CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.QuitRoomBlankOut,	CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.AdjustBlankOut,CompareType.NOTEQUALS));
		PurchaseCollection purchases = PurchaseFactory.getLocalInstance(ctx).getPurchaseCollection(view);
		for (int i = 0; i < purchases.size(); i++) {
			PurchaseInfo purchase = purchases.get(i);
			if (purchase.getId().toString().equals(purchaseInfo.getId().toString())) {
				continue;
			}
			
			CRMHelper.abortProcessWorkflow(ctx, purchase.getId().toString());

			purchase.setPurchaseState(PurchaseStateEnum.NoPayBlankOut);
			purchase.setState(FDCBillStateEnum.INVALID);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("purchaseState");
			sels.add("state");
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purchase, sels);
		}

	}

	//如果当前营销存在上级营销单元，并且拥有间接提佣的负责人存在，那么把该负责人写入到分单分录
	private static void setDistillCommision(Context ctx,CoreBaseCollection purCommisionColl,MarketingUnitInfo marketInfo,int i,PurchaseInfo purchaseInfo,Date toPurchaseDate) throws BOSException, EASBizException
	{
		if(marketInfo.getParent()==null)
		{
			return;
		}else
		{
			MarketingUnitInfo marketUnitInfo = marketInfo.getParent();
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("member.*");
			marketUnitInfo = MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitInfo(new ObjectUuidPK(marketUnitInfo.getId()), sels);
			List dutyList = getDutyPerson(ctx, marketUnitInfo,toPurchaseDate);
			if(dutyList.size()>0)
			{
				i++;
			}
			for(int k=0;k<dutyList.size();k++)
			{
				PurDistillCommisionEntryInfo purCommisionInfo2 = new PurDistillCommisionEntryInfo();
				MarketingUnitMemberInfo markMemberInfo = (MarketingUnitMemberInfo)dutyList.get(k);
				purCommisionInfo2.setUser(markMemberInfo.getMember());
				purCommisionInfo2.setSharePercent(new BigDecimal(0));
				purCommisionInfo2.setTakePercentage(markMemberInfo.getTakePercentage());
				purCommisionInfo2.setDistillCommisionLevel(i);
				purCommisionInfo2.setMarketUnit(markMemberInfo.getHead());
				purCommisionInfo2.setIsAchieveCommision(false);
				purCommisionInfo2.setHead(purchaseInfo);
				purCommisionColl.add(purCommisionInfo2);
			}
			setDistillCommision(ctx,purCommisionColl,marketUnitInfo,i,purchaseInfo,toPurchaseDate);
		}


	}

	//同一个营销人员在同一个项目下只有一种职能，也就是同一个人在同一个项目里每一个职能都只能在一个营销单元里
	private static MarketingUnitInfo getMarketingUnit(Context ctx,UserInfo salesMan,SellProjectInfo project,Date toPurchaseDate) throws BOSException
	{	
		Set marketIDSet = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();	
		view.getSelector().add("head.*");
		filter.getFilterItems().
		add(new FilterItemInfo("member.id",salesMan.getId().toString()));
		//		filter.getFilterItems()
		//		.add(new FilterItemInfo("sellProject.sellProject.id", project.getId().toString()));
		filter.getFilterItems()
		.add(new FilterItemInfo("isSellFunction",Boolean.TRUE));
		//上岗日期在转认购日期之前并且没有离职的职员
		filter.getFilterItems()
		.add(new FilterItemInfo("accessionDate",toPurchaseDate,CompareType.LESS_EQUALS));
		filter.getFilterItems()
		.add(new FilterItemInfo("dimissionDate",null));
		filter.getFilterItems().
		add(new FilterItemInfo("dimissionDate",toPurchaseDate,CompareType.GREATER_EQUALS));
		filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		view.setFilter(filter);

		MarketingUnitMemberCollection marketMemberColl = MarketingUnitMemberFactory.getLocalInstance(ctx).getMarketingUnitMemberCollection(view);
		for(int i=0;i<marketMemberColl.size();i++)
		{
			MarketingUnitInfo marketInfo = marketMemberColl.get(i).getHead();
			marketIDSet.add(marketInfo.getId().toString());
		}

		EntityViewInfo view2 = new EntityViewInfo();
		FilterInfo filter2 = new FilterInfo();
		view2.getSelector().add("head.*");
		view2.getSelector().add("head.member.*");
		filter2.getFilterItems().
		add(new FilterItemInfo("head.id",marketIDSet,CompareType.INCLUDE));
		filter2.getFilterItems()
		.add(new FilterItemInfo("sellProject.id", project.getId().toString()));
		view2.setFilter(filter2);
		MarketingUnitSellProjectCollection marketProjectColl = MarketingUnitSellProjectFactory.getLocalInstance(ctx).getMarketingUnitSellProjectCollection(view2);
		//		MarketingUnitCollection marketColl=MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitCollection(view);
		MarketingUnitInfo marketInfo = null;
		if(marketProjectColl.size()!=0)
		{
			marketInfo = new MarketingUnitInfo();
			marketInfo = marketProjectColl.get(0).getHead();
		}
		return marketInfo;
	}

	//找出营销单元里是否计提属性为true的负责人列表
	private static List getDutyPerson(Context ctx,MarketingUnitInfo marketInfo,Date toPurchaseDate) throws BOSException
	{
		List dutyList = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("*");
		view.getSelector().add("member.*");
		view.getSelector().add("head.*");
		filter.getFilterItems().
		add(new FilterItemInfo("head.id",marketInfo.getId().toString()));
		filter.getFilterItems().
		add(new FilterItemInfo("isDuty",Boolean.TRUE));//是否是负责人
		filter.getFilterItems().
		add(new FilterItemInfo("isSharePercent",Boolean.TRUE));//是否计提
		filter.getFilterItems()
		.add(new FilterItemInfo("accessionDate",toPurchaseDate,CompareType.LESS_EQUALS));//上岗日期在转认购日期之前
		filter.getFilterItems().
		add(new FilterItemInfo("dimissionDate",null));//离职日期为空，说明还在职或者是离职日期晚于转认购日期
		filter.getFilterItems().
		add(new FilterItemInfo("dimissionDate",toPurchaseDate,CompareType.GREATER_EQUALS));
		filter.setMaskString("#0 and #1 and #2 and #3 and (#4 or #5)");
		view.setFilter(filter);

		MarketingUnitMemberCollection markMemberColl = MarketingUnitMemberFactory.
		getLocalInstance(ctx).getMarketingUnitMemberCollection(view);
		for(int i=0;i<markMemberColl.size();i++)
		{
			MarketingUnitMemberInfo markMemberInfo = markMemberColl.get(i);
			dutyList.add(markMemberInfo);
		}
		return dutyList;
	}

	
	/*
	 * 当认购房间状态第一次转为认购状态的时候把分单分录保存到认购单上
	 */
	private static void doPurDistillBiz(Context ctx,PurchaseInfo purchaseInfo) throws BOSException, EASBizException {
		Date toPurchaseDate = purchaseInfo.getToPurchaseDate();
		if(toPurchaseDate == null) toPurchaseDate = new Date();
		UserInfo salesMan = purchaseInfo.getSalesman();
		SellProjectInfo project = purchaseInfo.getSellProject();
		//同一个营销人员在同一个项目下只有一种职能，也就是同一个人在同一个项目里每一个职能都只能在一个营销单元里
		MarketingUnitInfo marketInfo = getMarketingUnit(ctx,salesMan,project,toPurchaseDate);
		if(marketInfo==null){
			logger.error("营销单元为空，说明该顾问已离职或者该顾问对本项目没有销售职能，但他竟然录了认购单");
			//throw new BOSException("服务器端程序错误，已记录日志！");
		}else
		{
			int i=0;					
			//取得营销人员的提佣比例
			//PurDistillCommisionEntryCollection purCommisionColl = new PurDistillCommisionEntryCollection();
			CoreBaseCollection purCommisionColl = new CoreBaseCollection(); 
			PurDistillCommisionEntryInfo purCommisionInfo = new PurDistillCommisionEntryInfo();
			MarketingUnitMemberInfo merketMemberInfo = new MarketingUnitMemberInfo();
			SelectorItemCollection sels2 = new SelectorItemCollection();
			sels2.add("*");
			sels2.add("member.*");
			marketInfo = MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitInfo(new ObjectUuidPK(marketInfo.getId()), sels2);
			MarketingUnitMemberCollection marketMemberColl = marketInfo.getMember();
			for(int m=0;m<marketMemberColl.size();m++)
			{
				MarketingUnitMemberInfo markMemberInfo = marketMemberColl.get(m);
				if(markMemberInfo.getMember().getId().toString().equals(salesMan.getId().toString()))
				{
					merketMemberInfo = markMemberInfo;
				}
			}
			purCommisionInfo.setUser(salesMan);
			//比例暂时为0.根据另外的取数规则来取
			purCommisionInfo.setSharePercent(new BigDecimal(0));
			//营销顾问为成员的时候
//			if(merketMemberInfo.isIsDuty()==false)
//			{
				//分单比例根据营销单元的设置来取
				purCommisionInfo.setTakePercentage(new BigDecimal(100));
				//提佣级次，根据该营销单元的上级营销单元个数来定
				purCommisionInfo.setDistillCommisionLevel(i);
				//间接提佣分组,业务员业绩行此处为空,负责人行存为所在营销单元,
				//业务员也不为空了，把所在营销单元写上去 2009-11-09
				purCommisionInfo.setMarketUnit(marketInfo);
				//是否完成提佣,默认全为false
				purCommisionInfo.setIsAchieveCommision(false);
				purCommisionInfo.setHead(purchaseInfo);
				purCommisionColl.add(purCommisionInfo);

				//在营销单元里的负责人并且是否计提为真，并且在职的负责人的提佣比例也写入分录，如果该负责人
				//已经离职，那么查看他的离职日期是否在转认购日期之前，如果是则不需要写入，否则也需要写入分录中
				List dutyList = getDutyPerson(ctx,marketInfo,toPurchaseDate);
				if(dutyList.size()>0)
				{
					i++;
				}
				for(int k=0;k<dutyList.size();k++)
				{
					PurDistillCommisionEntryInfo purCommisionInfo2 = new PurDistillCommisionEntryInfo();
					MarketingUnitMemberInfo markMemberInfo = (MarketingUnitMemberInfo)dutyList.get(k);
					purCommisionInfo2.setUser(markMemberInfo.getMember());
					purCommisionInfo2.setSharePercent(new BigDecimal(0));
					purCommisionInfo2.setTakePercentage(markMemberInfo.getTakePercentage());
					purCommisionInfo2.setDistillCommisionLevel(i);
					purCommisionInfo2.setMarketUnit(markMemberInfo.getHead());
					purCommisionInfo2.setIsAchieveCommision(false);
					purCommisionInfo2.setHead(purchaseInfo);
					purCommisionColl.add(purCommisionInfo2);
				}

				//如果该营销单元的上级营销单元不为空，那么找到上级营销单元有间接提佣属性的负责人						
				setDistillCommision(ctx,purCommisionColl,marketInfo,i,purchaseInfo,toPurchaseDate);

			//}
			//营销顾问为负责人的时候，本级营销单元负责人还能拿提佣，所有就不需要判断是成员还是负责人了
//			else//营销顾问为负责人的时候，本级营销单元的负责人不应该拿他的提佣
//			{
//				//分单比例根据营销单元的设置来取
//				purCommisionInfo.setTakePercentage(new BigDecimal(100));
//				//提佣级次，根据该营销单元的上级营销单元个数来定
//				purCommisionInfo.setDistillCommisionLevel(i);
//				//间接提佣分组,业务员业绩行此处为空,负责人行存为所在营销单元
//				purCommisionInfo.setMarketUnit(null);
//				//是否完成提佣,默认全为false
//				purCommisionInfo.setIsAchieveCommision(false);
//				purCommisionInfo.setHead(purchaseInfo);
//				purCommisionColl.add(purCommisionInfo);
//
//				//如果该营销单元的上级营销单元不为空，那么找到上级营销单元有间接提佣属性的负责人						
//				setDistillCommision(ctx,purCommisionColl,marketInfo,i,purchaseInfo,toPurchaseDate);
//			}					
			PurDistillCommisionEntryFactory.getLocalInstance(ctx).addnew(purCommisionColl);
		}				
	
	}
	

}
