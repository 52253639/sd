package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.RefundmentMoneyTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AreaCompensateRevListFactory;
import com.kingdee.eas.fdc.sellhouse.AreaCompensateRevListInfo;
import com.kingdee.eas.fdc.sellhouse.BooleanEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CalcWayEnum;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListCollection;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListFactory;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListHisFactory;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListHisInfo;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListInfo;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.CompensateTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IBuildingCompensate;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomTransferFactory;
import com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellHouseException;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class RoomAreaCompensateControllerBean extends
		AbstractRoomAreaCompensateControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomAreaCompensateControllerBean");
	/**
	 * 添加面积补差时套内实测面积和建筑实测面积为空的异常常量
	 */
	private final String COMPENSATEAREA_BUILDINGAREA = "COMPENSATEAREA_ERROR_NULL_BUILDINGAREA";
	private final String COMPENSATEAREA_ROOMAREA = "COMPENSATEAREA_ERROR_NULL_ROOMAREA";

	public final static BOSUuid CURRENCY = BOSUuid
			.read("dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC");

	protected void _batchSave(Context ctx, List idList, Map valueMap)
			throws BOSException, EASBizException {
		FilterInfo filterexist = new FilterInfo();
		// RoomAreaCompensateCollection roomAreaCompensateColl = new
		// RoomAreaCompensateCollection();
		IRoom iRoom = RoomFactory.getLocalInstance(ctx);
		Date reportDate = (Date) valueMap.get("reportDate");
		CompensateSchemeInfo scheme = null;
		if (valueMap.get("scheme") != null)
			scheme = (CompensateSchemeInfo) valueMap.get("scheme");
		String description = (String) valueMap.get("description");
		UserInfo transactor = (UserInfo) valueMap.get("transactor");
		UserInfo creator = ContextUtil.getCurrentUserInfo(ctx);
		Timestamp creatTime = new Timestamp(new Date().getTime());
		for (int i = 0, size = idList.size(); i < size; i++) {
			String roomId = idList.get(i).toString();
			HashMap value = (HashMap) valueMap.get(roomId);
			BigDecimal compensateAmt = FDCHelper.toBigDecimal(value
					.get("compensateAmt"));
			BigDecimal compensateRate = FDCHelper.toBigDecimal(value
					.get("compensateRate"));
			String number = value.get("ruleNumber").toString();
			boolean isCalcByScheme = Boolean.valueOf(
					value.get("isCalcByScheme").toString()).booleanValue();

			filterexist = new FilterInfo();
			filterexist.getFilterItems().add(
					new FilterItemInfo("room.id", roomId));
			if (super.exists(ctx, filterexist)) {
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filterexist);
				RoomAreaCompensateInfo roomAreaCompensate = super
						.getRoomAreaCompensateCollection(ctx, view).get(0);
				// RoomInfo room = roomAreaCompensate.getRoom();
				RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(BOSUuid
						.read(roomId)));
				if (isCalcByScheme)
					roomAreaCompensate.setScheme(scheme);
				else
					roomAreaCompensate.setScheme(null);
				if (roomAreaCompensate.getCompensateState() == null)
					roomAreaCompensate
							.setCompensateState(RoomCompensateStateEnum.COMSUBMIT);
				roomAreaCompensate.setCompensateRate(compensateRate);
				roomAreaCompensate.setCompensateAmount(compensateAmt);
				roomAreaCompensate.setIsCalcByScheme(isCalcByScheme);
				roomAreaCompensate.setDescription(description);
				roomAreaCompensate.setTransactor(transactor);
				roomAreaCompensate.setScheme(scheme);
				roomAreaCompensate.setCompensateDate(reportDate);
				// 增加编号 by renliang at 2010-11-11
				roomAreaCompensate.setNumber(number);
				BigDecimal latestAmount = FDCHelper.ZERO;
				if (room.getDealTotalAmount() != null)
					latestAmount = room.getDealTotalAmount();
				roomAreaCompensate.setLatestAmount(latestAmount
						.add(compensateAmt));

				updateRoomCompensateInfo(ctx, room, roomAreaCompensate,
						RoomCompensateStateEnum.COMSUBMIT);

				super._save(ctx, roomAreaCompensate);
			} else {
				RoomAreaCompensateInfo roomAreaCompensate = new RoomAreaCompensateInfo();
				RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(BOSUuid
						.read(roomId)));

				roomAreaCompensate.setRoom(room);
				if (isCalcByScheme)
					roomAreaCompensate.setScheme(scheme);
				else
					roomAreaCompensate.setScheme(null);
				roomAreaCompensate
						.setCompensateState(RoomCompensateStateEnum.COMSUBMIT);
				roomAreaCompensate.setCompensateRate(compensateRate);
				roomAreaCompensate.setCompensateAmount(compensateAmt);
				roomAreaCompensate.setIsCalcByScheme(isCalcByScheme);
				roomAreaCompensate.setDescription(description);
				roomAreaCompensate.setTransactor(transactor);
				roomAreaCompensate.setCreator(creator);
				roomAreaCompensate.setCreateTime(creatTime);
				// 增加编号 by renliang at 2010-11-11
				roomAreaCompensate.setNumber(number);
				BigDecimal latestAmount = FDCHelper.ZERO;
				if (room.getDealTotalAmount() != null)
					latestAmount = room.getDealTotalAmount();
				roomAreaCompensate.setLatestAmount(latestAmount
						.add(compensateAmt));

				updateRoomCompensateInfo(ctx, room, roomAreaCompensate,
						RoomCompensateStateEnum.COMSUBMIT);

				super._save(ctx, roomAreaCompensate);
				// roomAreaCompensateColl.add(roomAreaCompensate);
			}
		}

		// if (roomAreaCompensateColl.size() > 0)
		// super._addnew(ctx, roomAreaCompensateColl);
	}

	private void updateRoomCompensateInfo(Context ctx, RoomInfo room,
			RoomAreaCompensateInfo compSateinfo, RoomCompensateStateEnum state)
			throws BOSException, EASBizException {
		if (room == null) {
			logger.error("逻辑错误，补差的房间为空啊！！！！");
			return;
		}
		room.setRoomCompensateState(state);
		room.setAreaCompensateAmount(compSateinfo.getCompensateAmount());
		room.setLastAreaCompensate(compSateinfo);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("roomCompensateState");
		selector.add("areaCompensateAmount");
		selector.add("lastAreaCompensate");
		RoomFactory.getLocalInstance(ctx).updatePartial(room, selector);
	}

	// public void unAudit(Context ctx, List idList) throws BOSException,
	// EASBizException {
	//		
	// super.unAudit(ctx, idList);
	// }

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {

		RoomAreaCompensateInfo roomCom = RoomAreaCompensateFactory
				.getLocalInstance(ctx).getRoomAreaCompensateInfo(
						"select compensateState,compensateAmount,room.* where id = '"
								+ billId.toString() + "'");

		roomCom.setCompensateState(RoomCompensateStateEnum.COMAUDITTED);
		roomCom.setState(FDCBillStateEnum.AUDITTED);
		roomCom.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		roomCom.setAuditTime(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("compensateState");
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");

		// 增加补差的应收明细
		AreaCompensateRevListInfo areaCopsRevInfo = new AreaCompensateRevListInfo();
		areaCopsRevInfo.setHead(roomCom);
		MoneyDefineCollection monDefineColl = MoneyDefineFactory
				.getLocalInstance(ctx).getMoneyDefineCollection(
						"select id where moneyType = '"
								+ MoneyTypeEnum.COMPENSATEAMOUNT_VALUE + "'");
		if (monDefineColl.size() > 0)
			areaCopsRevInfo.setMoneyDefine(monDefineColl.get(0));
		BigDecimal compensateAmount = roomCom.getCompensateAmount();
		if (compensateAmount == null)
			compensateAmount = FDCHelper.ZERO;
		areaCopsRevInfo.setIsCanRevBeyond(false);
		if (compensateAmount.compareTo(FDCHelper.ZERO) < 0) {
			areaCopsRevInfo
					.setRefundmentMoneyType(RefundmentMoneyTypeEnum.AppRefundment); // 付款时应该是应退退款
			areaCopsRevInfo.setLimitAmount(compensateAmount.negate());
		} else {
			areaCopsRevInfo
					.setRefundmentMoneyType(RefundmentMoneyTypeEnum.AppRevRefundment);
			areaCopsRevInfo.setAppAmount(compensateAmount);
		}
		areaCopsRevInfo.setRevMoneyType(RevMoneyTypeEnum.AppRev);
		AreaCompensateRevListFactory.getLocalInstance(ctx).addnew(
				areaCopsRevInfo);

		RoomInfo room = roomCom.getRoom();
		room.setRoomCompensateState(RoomCompensateStateEnum.COMAUDITTED);
		room.setAreaCompensateAmount(roomCom.getCompensateAmount());
		room.setLastAreaCompensate(roomCom);

		// 反写房间信息
		SelectorItemCollection selector1 = new SelectorItemCollection();
		selector.add("areaCompensateAmount");
		selector.add("roomCompensateState");
		selector.add("lastAreaCompensate");
		RoomFactory.getLocalInstance(ctx).updatePartial(room, selector1);

		super._updatePartial(ctx, roomCom, selector);
	}

	/*
	 * add by zkyan
	 * 
	 * @see
	 * com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_unAudit(com.kingdee
	 * .bos.Context, java.util.List)
	 */
	// StringBuffer msg = new StringBuffer();
	protected void _unAudit(Context ctx, List idList) throws BOSException,
			EASBizException {
		if (idList.size() != 0) {
			for (int i = 0; i < idList.size(); i++) {
				String billId = (String) idList.get(i);
				RoomAreaCompensateInfo roomCom = RoomAreaCompensateFactory
						.getLocalInstance(ctx).getRoomAreaCompensateInfo(
								"select compensateState,compensateAmount,room.* where id = '"
										+ billId.toString() + "'");

				if (!RoomCompensateStateEnum.COMAUDITTED.equals(roomCom
						.getCompensateState())) {
					// if(idList.size()>1){
					// msg.append("第").append(i+1).append("行记录不符合条件未审批成功\n");
					// }
				} else {
					roomCom
							.setCompensateState(RoomCompensateStateEnum.COMSUBMIT);
					roomCom.setState(FDCBillStateEnum.SUBMITTED);
					roomCom.setAuditor(null);
					roomCom.setAuditTime(null);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("compensateState");
					selector.add("state");
					selector.add("auditor");
					selector.add("auditTime");

					// FDCSQLBuilder builder = new FDCSQLBuilder();
					// builder.appendSql(
					// "delete * from T_SHE_AreaCompensateRevList where fheadid =?"
					// );
					// builder.addParam(billId);
					// builder.execute();

					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("head.id", billId));
					AreaCompensateRevListFactory.getLocalInstance(ctx).delete(
							filter);

					RoomInfo room = roomCom.getRoom();
					room
							.setRoomCompensateState(RoomCompensateStateEnum.COMSUBMIT);
					room.setAreaCompensateAmount(null);
					room.setLastAreaCompensate(null);

					// 反写房间信息
					SelectorItemCollection selector1 = new SelectorItemCollection();
					selector.add("areaCompensateAmount");
					selector.add("roomCompensateState");
					selector.add("lastAreaCompensate");
					RoomFactory.getLocalInstance(ctx).updatePartial(room,
							selector1);
					super._updatePartial(ctx, roomCom, selector);
					// if(idList.size()>1){
					// msg.append("第").append(i+1).append("行记录审批成功\n");
					// }
				}
			}
			// if(msg.length()>0){
			// throw new EASBizException(new
			// NumericExceptionSubItem("021",msg.toString()));
			// // MsgBox.showDetailAndOK(null, "反审批结束，点击查看详细信息", msg.toString(),
			// 1);
			// }
		}

	}

	// 暂存
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RoomAreaCompensateInfo info = (RoomAreaCompensateInfo) model;
		IRoom iRoom = RoomFactory.getLocalInstance(ctx);
		if (info.getRoom() != null && info.getRoom().getId() != null
				&& info.getCompensateAmount() != null) {
			RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(info.getRoom()
					.getId()));
			updateRoomCompensateInfo(ctx, room, null,
					RoomCompensateStateEnum.NOCOMPENSATE);
			info.setLatestAmount(FDCHelper.toBigDecimal(
					room.getDealTotalAmount()).add(
					FDCHelper.toBigDecimal(info.getCompensateAmount())));
			if (info.getCompensateState() == null)
				info.setCompensateState(RoomCompensateStateEnum.NOCOMPENSATE);
		}
		return super._save(ctx, info);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RoomAreaCompensateInfo info = (RoomAreaCompensateInfo) model;
		IRoom iRoom = RoomFactory.getLocalInstance(ctx);
		if (info.getRoom() != null && info.getRoom().getId() != null
				&& info.getCompensateAmount() != null) {
			RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(info.getRoom()
					.getId()));

			updateRoomCompensateInfo(ctx, room, info,
					RoomCompensateStateEnum.COMSUBMIT);
			info.setLatestAmount(FDCHelper.toBigDecimal(
					room.getDealTotalAmount()).add(
					FDCHelper.toBigDecimal(info.getCompensateAmount())));
			if (info.getCompensateState() == null)
				info.setCompensateState(RoomCompensateStateEnum.COMSUBMIT);
		}
		return super._submit(ctx, info);
	}

	// 房间的补差结果
	class RoomCompenSateResult {
		private BigDecimal compensateRate; // 差异率
		private BigDecimal compensateAmt; // 补差金额

		public void setCompensateRate(BigDecimal compensateRate) {
			this.compensateRate = compensateRate;
		}

		public BigDecimal getCompensateRate() {
			return compensateRate;
		}

		public void setCompensateAmt(BigDecimal compensateAmt) {
			this.compensateAmt = compensateAmt;
		}

		public BigDecimal getCompensateAmt() {
			return compensateAmt;
		}
	}

	// 补差率是否正补差明细的条件范围内
	private boolean isInCompenSateRange(BigDecimal compensateRate,
			CompensateSchemeEntryInfo comEntry) {
		if (compensateRate == null || comEntry == null)
			return false;
		if (comEntry.getMinCompareType() == null
				|| comEntry.getMinValue() == null
				|| comEntry.getMaxCompareType() == null
				|| comEntry.getMaxValue() == null)
			return false;
		if (comEntry.getMinCompareType().equals(SHECompareTypeEnum.EQUAL)) {
			if (compensateRate.compareTo(comEntry.getMinValue()) != 0)
				return false;
		} else if (comEntry.getMinCompareType().equals(SHECompareTypeEnum.LESS)) {
			if (compensateRate.compareTo(comEntry.getMinValue()) <= 0)
				return false;
		} else if (comEntry.getMinCompareType().equals(
				SHECompareTypeEnum.LESSEQUAL)) {
			if (compensateRate.compareTo(comEntry.getMinValue()) < 0)
				return false;
		}
		if (comEntry.getMaxCompareType().equals(SHECompareTypeEnum.EQUAL)) {
			if (compensateRate.compareTo(comEntry.getMaxValue()) != 0)
				return false;
		} else if (comEntry.getMaxCompareType().equals(SHECompareTypeEnum.LESS)) {
			if (compensateRate.compareTo(comEntry.getMaxValue()) >= 0)
				return false;
		} else if (comEntry.getMaxCompareType().equals(
				SHECompareTypeEnum.LESSEQUAL)) {
			if (compensateRate.compareTo(comEntry.getMaxValue()) > 0)
				return false;
		}
		return true;
	}

	// 获取房间的补差结果
	// roomId 房间id ; schemeInfo 补差方案 (对象中要包含分录情况)
	private RoomCompenSateResult getRoomCompenSateResult(Context ctx,
			String roomId, CompensateSchemeInfo schemeInfo)
			throws BOSException, EASBizException {
		RoomCompenSateResult roomComResult = new RoomCompenSateResult();
		roomComResult.setCompensateAmt(new BigDecimal(0.00));
		roomComResult.setCompensateRate(new BigDecimal(0.00));

		RoomInfo roomInfo = RoomFactory
				.getLocalInstance(ctx)
				.getRoomInfo(
						"select buildingArea,actualBuildingArea,roomArea,actualRoomArea,lastPurchase.dealBuildPrice"
								+ ",lastPurchase.dealRoomPrice where id = '"
								+ roomId + "'");
		if (roomInfo == null)
			return roomComResult;
		// if (roomInfo.getLastPurchase() == null)
		// return roomComResult;

		BigDecimal comDealRoomPrice = new BigDecimal("0.00"); // 待比较的房间成交价格
		BigDecimal comRoomArea = new BigDecimal("0.00"); // 待比较的房间原面积
		BigDecimal comActualRoomArea = new BigDecimal("0.00"); // 待比较的房间实际面积

		if (roomInfo.getActualBuildingArea() == null
				|| roomInfo.getActualRoomArea() == null) {
			throw new BOSException(COMPENSATEAREA_BUILDINGAREA);
		}

		// 判断是按照套内还是建筑面积来计算
		if (schemeInfo.getCompensateType() == CompensateTypeEnum.BUILDINGAREA) {
			comRoomArea = roomInfo.getBuildingArea();
			comActualRoomArea = roomInfo.getActualBuildingArea();
			if (roomInfo.getLastPurchase() != null) {
				comDealRoomPrice = roomInfo.getLastPurchase()
						.getDealBuildPrice();
			}

		} else if (schemeInfo.getCompensateType() == CompensateTypeEnum.ROOMAREA) {
			comRoomArea = roomInfo.getRoomArea();
			comActualRoomArea = roomInfo.getActualRoomArea();
			// comDealRoomPrice = roomInfo.getLastPurchase().getDealRoomPrice();
			if (roomInfo.getLastPurchase() != null) {
				comDealRoomPrice = roomInfo.getLastPurchase()
						.getDealBuildPrice();
			}
		} else if (schemeInfo.getCompensateType() == CompensateTypeEnum.ROOMTYPE) {
			if (roomInfo.isIsCalByRoomArea()) {
				comRoomArea = roomInfo.getRoomArea();
				comActualRoomArea = roomInfo.getActualRoomArea();
				if (roomInfo.getLastPurchase() != null) {
					comDealRoomPrice = roomInfo.getLastPurchase()
							.getDealBuildPrice();
				}
			} else {
				comRoomArea = roomInfo.getBuildingArea();
				comActualRoomArea = roomInfo.getActualBuildingArea();
				if (roomInfo.getLastPurchase() != null) {
					comDealRoomPrice = roomInfo.getLastPurchase()
							.getDealBuildPrice();
				}
			}
		}
		if (comRoomArea.compareTo(new BigDecimal(0.00)) == 0)
			return roomComResult;
		if (comActualRoomArea.compareTo(new BigDecimal(0.00)) == 0)
			return roomComResult;
		// if (comDealRoomPrice.compareTo(new BigDecimal(0.00)) == 0)
		// return roomComResult;

		comRoomArea = comRoomArea.setScale(2, BigDecimal.ROUND_HALF_UP);
		comActualRoomArea = comActualRoomArea.setScale(2,
				BigDecimal.ROUND_HALF_UP);
		comDealRoomPrice = comDealRoomPrice.setScale(2,
				BigDecimal.ROUND_HALF_UP);
		BigDecimal compensateRate = new BigDecimal(0.00); // 补差率
		BigDecimal compensateAmt = new BigDecimal(0.00); // 补差金额
		// compensateRate =
		// comActualRoomArea.subtract(comRoomArea).divide(comRoomArea, 10,
		// BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
		compensateRate = FDCHelper.divide(comActualRoomArea
				.subtract(comRoomArea), comRoomArea, 10,
				BigDecimal.ROUND_HALF_UP);
		compensateRate = FDCHelper.multiply(compensateRate, new BigDecimal(
				"100"));

		if (compensateRate.compareTo(new BigDecimal(0.00)) == 0)
			return roomComResult;

		// 查找补差率在方案分录中的哪个明细范围中
		// 按照最高标准补差 的一定要找到一个匹配的要补差明细，否则不用补差；按分段计算补差的只要包含即要算入
		CompensateSchemeEntryCollection comEntrys = schemeInfo.getEntrys();
		for (int i = 0; i < comEntrys.size(); i++) {
			CompensateSchemeEntryInfo comEntry = comEntrys.get(i);
			if (comEntry.getIsCompensate() == null
					|| comEntry.getIsCompensate().equals(BooleanEnum.NO))
				continue;
			if (comEntry.getFactor() == null
					|| comEntry.getFactor().compareTo(new BigDecimal(0.00)) == 0)
				continue;
			if (schemeInfo.getCalcWay().equals(CalcWayEnum.SUPREME)) {
				if (isInCompenSateRange(compensateRate, comEntry)) {
					compensateAmt = compensateRate.multiply(comRoomArea)
							.multiply(comDealRoomPrice).multiply(
									comEntry.getFactor());
					break;
				}
			} else if (schemeInfo.getCalcWay().equals(CalcWayEnum.APART)) {
				if (compensateRate.compareTo(new BigDecimal(0.00)) > 0) {
					if (comEntry.getMaxValue().compareTo(new BigDecimal(0.00)) < 0)
						continue;
					BigDecimal minValue = comEntry.getMinValue();
					if (minValue.compareTo(new BigDecimal(0.00)) < 0)
						minValue = new BigDecimal(0.00);
					if (isInCompenSateRange(compensateRate, comEntry)) {
						BigDecimal addComAmt = compensateRate
								.subtract(minValue).multiply(comRoomArea)
								.multiply(comDealRoomPrice).multiply(
										comEntry.getFactor());
						compensateAmt = compensateAmt.add(addComAmt);
					} else if (compensateRate.compareTo(comEntry.getMaxValue()) >= 0) {
						compensateAmt = compensateAmt.add(comEntry
								.getMaxValue().subtract(minValue).multiply(
										comRoomArea).multiply(comDealRoomPrice)
								.multiply(comEntry.getFactor()));
					}
				} else if (compensateRate.compareTo(new BigDecimal(0.00)) < 0) {
					if (comEntry.getMinValue().compareTo(new BigDecimal(0.00)) > 0)
						continue;
					BigDecimal maxValue = comEntry.getMaxValue();
					if (maxValue.compareTo(new BigDecimal(0.00)) > 0)
						maxValue = new BigDecimal(0.00);
					if (isInCompenSateRange(compensateRate, comEntry)) {
						BigDecimal addComAmt = compensateRate
								.subtract(maxValue).multiply(comRoomArea)
								.multiply(comDealRoomPrice).multiply(
										comEntry.getFactor());
						compensateAmt = compensateAmt.add(addComAmt);
					} else if (compensateRate.compareTo(comEntry.getMinValue()) <= 0) {
						compensateAmt = compensateAmt.add(comEntry
								.getMinValue().subtract(maxValue).multiply(
										comRoomArea).multiply(comDealRoomPrice)
								.multiply(comEntry.getFactor()));
					}
				}
			}
		}

		int decimalCount = schemeInfo.getDecimalCount();
		int decimalType = 4;
		if (schemeInfo.getDecimalType() != null)
			decimalType = (new Integer((String) schemeInfo.getDecimalType()
					.getValue())).intValue();
		roomComResult.setCompensateRate(compensateRate.setScale(2,
				BigDecimal.ROUND_HALF_UP));
		roomComResult.setCompensateAmt(compensateAmt.divide(
				new BigDecimal(100), decimalCount, decimalType));
		return roomComResult;
	}

	/**
	 * 计算补差 idList 房间id集合 schemeId 补差方案id return : value.put("roomId", roomId);
	 * value.put("compensateRate", compensateRate); 差异率
	 * value.put("compensateAmt", compensateAmt); 补差金额
	 */
	protected Map _calcAmount(Context ctx, List idList, String schemeId)
			throws BOSException, EASBizException {
		Map values = new HashMap();

		CompensateSchemeInfo scheme = CompensateSchemeFactory.getLocalInstance(
				ctx).getCompensateSchemeInfo(
				"select calcWay,compensateType,decimalCount,decimalType,entrys.* where id ='"
						+ schemeId + "'");
		/* 每个房间所对应的补差 */
		for (int i = 0, size = idList.size(); i < size; i++) {
			Map value = new HashMap();

			String roomId = idList.get(i).toString();
			RoomCompenSateResult roomComResust = getRoomCompenSateResult(ctx,
					roomId, scheme);
			value.put("roomId", roomId);
			value.put("compensateRate", roomComResust.getCompensateRate());
			value.put("compensateAmt", roomComResust.getCompensateAmt());

			values.put(roomId, value);
		}
		return values;
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		RoomAreaCompensateInfo info = super.getRoomAreaCompensateInfo(ctx, pk);
		if (!info.getCompensateState()
				.equals(RoomCompensateStateEnum.COMSUBMIT)) {
			throw new SellHouseException(SellHouseException.STATEWRONG);
		}
		if (info.getRoom() != null) {
			info.getRoom().setRoomCompensateState(
					RoomCompensateStateEnum.NOCOMPENSATE);
			info.getRoom().setAreaCompensateAmount(null);
			info.getRoom().setLastAreaCompensate(null);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("roomCompensateState");
			selector.add("areaCompensateAmount");
			selector.add("lastAreaCompensate");
			RoomFactory.getLocalInstance(ctx).updatePartial(info.getRoom(),
					selector);
		}
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		for (int i = 0; i < arrayPK.length; i++) {
			RoomAreaCompensateInfo info = super.getRoomAreaCompensateInfo(ctx,
					arrayPK[i]);
			if (!info.getCompensateState().equals(
					RoomCompensateStateEnum.COMSUBMIT)) {
				throw new SellHouseException(SellHouseException.STATEWRONG);
			}
			if (info.getRoom() != null) {
				info.getRoom().setRoomCompensateState(
						RoomCompensateStateEnum.NOCOMPENSATE);
				info.getRoom().setAreaCompensateAmount(null);
				info.getRoom().setLastAreaCompensate(null);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("roomCompensateState");
				selector.add("areaCompensateAmount");
				selector.add("lastAreaCompensate");
				RoomFactory.getLocalInstance(ctx).updatePartial(info.getRoom(),
						selector);
			}
		}
		super._delete(ctx, arrayPK);
	}

	protected boolean isUseName() {
		return false;
	}

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);
		if (FDCBillInfo.getNumber() != null) {
			super.checkBill(ctx, model);
		}
	}

	protected void _submitToWorkFlow(Context ctx, String buildingId)
			throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("building.id", buildingId));
		IBuildingCompensate ibuilding = BuildingCompensateFactory
				.getLocalInstance(ctx);
		if (ibuilding.exists(filter)) {
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			BuildingCompensateInfo building = ibuilding
					.getBuildingCompensateCollection(view).get(0);
			if (building.getOrgUnit() == null) {
				building.setOrgUnit(ContextUtil.getCurrentOrgUnit(ctx)
						.castToFullOrgUnitInfo());
			}
			ibuilding.submit(building);
		} else {
			BuildingCompensateInfo building = new BuildingCompensateInfo();
			building
					.setBuilding(BuildingFactory.getLocalInstance(ctx)
							.getBuildingInfo(
									new ObjectUuidPK(BOSUuid.read(buildingId))));
			building.setOrgUnit(ContextUtil.getCurrentOrgUnit(ctx)
					.castToFullOrgUnitInfo());
			ibuilding.submit(building);
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

	protected IRowSet _getRoomInfoList(Context ctx, String filterStr)
			throws BOSException, EASBizException {

		StringBuffer mainSql = new StringBuffer();
		mainSql
				.append(" select roomId,roomName,customerName,phone,comId,comNumber,comDate,state,schemeName,mainAmount,attAmount,actAmount,");
		mainSql
				.append(" refAmount,referAmount,rate,lastAMount,handlerName,roomDesc,createtime,createorName,auditTime,auditorName,floor from ( ");
		mainSql.append(" select ");
		mainSql.append(" distinct");
		mainSql.append(" room.fid roomId,");
		mainSql.append(" room.fname_l2 roomName,");
		mainSql.append(" signManage.fcustomernames customerName,");
		mainSql.append(" signManage.fcustomerPhone phone,");
		mainSql.append(" roomarea.fid comId,");
		mainSql.append(" roomarea.fnumber comNumber,");
		mainSql.append(" roomarea.FCompensateDate comDate,");
		mainSql.append(" roomarea.FCompensateState state,");
		mainSql.append(" scheme.fname schemeName,");
		mainSql.append(" roomList.FMainAmount mainAmount,");
		mainSql.append(" roomList.FAttAmount attAmount,");
		mainSql.append(" roomList.FActAmount actAmount,");
		mainSql.append(" roomList.FRefAmount refAmount,");
		mainSql.append(" roomarea.FCompensateAmount referAmount,");
		mainSql.append(" roomList.FCompensateRate rate,");
		mainSql.append(" roomList.FLastAmount lastAMount,");
		mainSql.append(" handler.fname_l2 handlerName,");
		mainSql.append(" roomarea.fdescription roomDesc,");
		mainSql.append(" roomarea.fcreatetime createtime,");
		mainSql.append(" createor.fname_l2 createorName,");
		mainSql.append(" roomarea.faudittime auditTime,");
		mainSql.append(" auditor.fname_l2 auditorName,");
		mainSql.append(" room.ffloor floor ");
		mainSql.append(" from t_she_room room");
		mainSql
				.append(" inner join T_SHE_SignManage signManage on room.fid =signManage.froomid");
		mainSql
				.append(" left join T_SHE_CompensateRoomList roomList on room.fid = roomList.froomid");
		mainSql
				.append(" left join T_SHE_RoomAreaCompensate roomarea on roomList.fheadid = roomarea.fid");
		mainSql
				.append(" left join T_SHE_CompensateScheme scheme on roomarea.FSchemeID = scheme.fid");
		mainSql
				.append(" left join T_PM_User handler on  roomarea.FTransactorID = handler.fid");
		mainSql
				.append(" left join T_PM_User createor on roomarea.fcreatorid = createor.fid");
		mainSql
				.append(" left join T_PM_User auditor on roomarea.fauditorid = auditor.fid");
		mainSql
				.append(" left join T_SHE_Building building on room.FBuildingID = building.fid ");
		mainSql
				.append(" left join T_SHE_BuildingUnit buildingUnit on buildingUnit.FBuildingID = building.fid");
		mainSql
				.append(" left join T_SHE_Subarea subArea on building.FSubareaID = subArea.fid ");
		mainSql
				.append(" left join t_she_sellproject sellProject on building.FSellProjectID = sellProject.fid");
		mainSql.append(" where signManage.fbizState ='SignAudit' ");
		if (filterStr != null && filterStr.length() > 0) {
			mainSql.append(filterStr);
		}
		mainSql.append(" ) as tempRoom ");
		mainSql.append(" order by tempRoom.floor asc");

		return DbUtil.executeQuery(ctx, mainSql.toString());
	}

	protected IRowSet _getCompenstateRoomInfo(Context ctx, String roomId)
			throws BOSException, EASBizException {
		StringBuffer mainSql = new StringBuffer();
		mainSql.append(" select ");
		mainSql.append(" room.fid as roomId, ");
		mainSql.append(" roomArea.fid as compensateId, ");
		mainSql.append(" sellProject.fid as sellProjectId, ");
		mainSql.append(" sellProject.fname_l2 as sellProjectName, ");
		mainSql.append(" sellProject.fnumber as sellProjectNumber, ");
		mainSql.append(" room.fname_l2 as roomName, ");
		mainSql.append(" signMan.fcustomernames as customerName, ");
		mainSql.append(" signMan.fcustomerphone as customerPhone, ");
		mainSql.append(" saleMan.fid as saleManId, ");
		mainSql.append(" saleMan.fname_l2 as saleManName, ");
		mainSql.append(" saleMan.fnumber as saleManNumber, ");
		mainSql.append(" payType.fid as payTypeId, ");
		mainSql.append(" payType.fname_l2 as payTypeName, ");
		mainSql.append(" payType.fnumber as payTypeNumber, ");
		mainSql.append(" signMan.fbizdate as signDate, ");
		mainSql.append(" signMan.fbiznumber as conNumber, ");
		mainSql.append(" signMan.FSellAmount as sellAmount, ");
		mainSql.append(" room.fbuildingarea as buildArea, ");
		mainSql.append(" room.froomarea as roomArea, ");
		mainSql.append(" room.FActualBuildingArea as actBuildArea, ");
		mainSql.append(" room.FActualRoomArea as actRoomArea, ");
		mainSql.append(" room.fbuildingarea as conbuildArea, ");
		mainSql.append(" room.froomarea as conroomArea, ");
		mainSql.append(" roomArea.fnumber as roomAreaNumber, ");
		mainSql.append(" roomArea.FCompensateDate as compDate, ");
		mainSql.append(" hanlder.fid as hanlderId, ");
		mainSql.append(" hanlder.fname_l2 as hanlderName, ");
		mainSql.append(" hanlder.fnumber as hanlderNumber, ");
		mainSql.append(" scheme.fid as schemeId, ");
		mainSql.append(" scheme.fnumber as schemeNumber, ");
		mainSql.append(" roomList.FMainAmount as mainAmount, ");
		mainSql.append(" roomList.fattamount as attAmount, ");
		mainSql.append(" roomList.FRefAmount as refAmount, ");
		mainSql.append(" roomList.factamount as actAmount, ");
		mainSql.append(" roomList.FCompensateRate as rate, ");
		mainSql.append(" roomList.FLastAmount as lastAmount, ");
		mainSql.append(" roomArea.fdescription as description ");
		mainSql.append(" from t_she_roomareacompensate roomArea ");
		mainSql
				.append(" left join t_she_compensateroomlist roomList on roomArea.fid = roomList.fheadid ");
		mainSql
				.append(" left join t_she_signmanage signMan on roomList.fsignid = signMan.fid ");
		mainSql
				.append(" left join t_she_room room on signMan.froomid = room.fid ");
		mainSql
				.append(" left join t_she_sellproject sellProject on signMan.fsellprojectid = sellProject.fid ");
		mainSql
				.append(" left join t_pm_user saleMan on signMan.fsalesmanid = saleMan.fid ");
		mainSql
				.append(" left join T_SHE_SHEPayType payType on signMan.fpaytypeid = payType.fid ");
		mainSql
				.append(" left join t_pm_user hanlder on roomArea.FTransactorID = hanlder.fid ");
		mainSql
				.append(" left join T_SHE_CompensateScheme scheme on roomArea.fschemeid = scheme.fid ");
		mainSql.append(" where 1=1 ");
		if (roomId != null && roomId.length() > 0) {
			mainSql.append(" and room.fid='" + roomId + "'");
		}
		return DbUtil.executeQuery(ctx, mainSql.toString());
	}

	protected void _setNullify(Context ctx, String idList) throws BOSException,
			EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("compensateState"));
		selector.add(new SelectorItemInfo("auditor"));
		selector.add(new SelectorItemInfo("auditTime"));
		RoomAreaCompensateInfo model = new RoomAreaCompensateInfo();
		model.setId(BOSUuid.read(idList));
		model.setAuditor(null);
		model.setAuditTime(null);
		model.setCompensateState(RoomCompensateStateEnum.COMSTOP);
		RoomAreaCompensateFactory.getLocalInstance(ctx).updatePartial(model,
				selector);

		backDataToHis(ctx, idList);

		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
		sql.appendSql("delete from t_she_compensateroomlist where fheadid='"
				+ idList + "'");
		sql.execute();

	}

	private void backDataToHis(Context ctx, String idList) throws BOSException {
		CompensateRoomListCollection CompensateRoomListColl = CompensateRoomListFactory
				.getLocalInstance(ctx)
				.getCompensateRoomListCollection(
						"select id,head.id,sign.id,room.id,refAmount,lastAmount,actAmount,mainAmount,attAmount,compensateRate where head.id='"
								+ idList + "'");

		if (CompensateRoomListColl != null && CompensateRoomListColl.size() > 0) {
			try {

				for (int i = 0; i < CompensateRoomListColl.size(); i++) {
					CompensateRoomListInfo info = (CompensateRoomListInfo) CompensateRoomListColl
							.get(i);

					CompensateRoomListHisInfo model = new CompensateRoomListHisInfo();
					model.setHead(info.getHead());
					model.setSign(info.getSign());
					model.setRoom(info.getRoom());
					if (info.getMainAmount() != null) {
						model.setMainAmount(info.getMainAmount());
					}
					if (info.getAttAmount() != null) {
						model.setAttAmount(info.getAttAmount());
					}
					if (info.getRefAmount() != null) {
						model.setRefAmount(info.getRefAmount());
					}

					if (info.getActAmount() != null) {
						model.setActAmount(info.getActAmount());
					}
					if (info.getLastAmount() != null) {
						model.setLastAmount(info.getLastAmount());
					}

					if (info.getCompensateRate() != null) {
						model.setCompensateRate(info.getCompensateRate());
					}

					CompensateRoomListHisFactory.getLocalInstance(ctx).addnew(
							model);

				}

			} catch (EASBizException ex) {
				logger.error(ex.getMessage() + "更新补差办理历史信息失败!");
				throw new BOSException(ex.getMessage() + "更新补差办理历史信息失败!");
			}
		}
	}

	protected Map _calcAmountForSHE(Context ctx, List roomList, String schemeId)
			throws BOSException, EASBizException {
		Map values = new HashMap();

		CompensateSchemeInfo scheme = CompensateSchemeFactory.getLocalInstance(
				ctx).getCompensateSchemeInfo(
				"select calcWay,compensateType,decimalCount,decimalType,entrys.* where id ='"
						+ schemeId + "'");
		/* 每个房间所对应的补差 */
		for (int i = 0, size = roomList.size(); i < size; i++) {
			Map value = new HashMap();

			String roomId = roomList.get(i).toString();
			RoomCompenSateResult roomComResustMain = getRoomCompenSateResultForSHE(
					ctx, roomId, null, scheme, false);
			value.put("roomId", roomId);
			value.put("compensateRate", roomComResustMain.getCompensateRate());
			value
					.put("compensateAmtMain", roomComResustMain
							.getCompensateAmt());
			RoomAttachmentEntryCollection attRoom = RoomAttachmentEntryFactory
					.getLocalInstance(ctx).getRoomAttachmentEntryCollection(
							"select id,room.id,room.name,room.number where head='"
									+ roomId + "'");
			BigDecimal attTotal = FDCHelper.ZERO;
			for (int j = 0; j < attRoom.size(); j++) {
				RoomAttachmentEntryInfo info = attRoom.get(j);
				if (info != null && info.getRoom() != null
						&& info.getRoom().getId() != null) {
					RoomCompenSateResult roomComResustAtt = getRoomCompenSateResultForSHE(
							ctx, roomId, info.getRoom().getId().toString(),
							scheme, true);
					attTotal = FDCHelper.add(attTotal, roomComResustAtt
							.getCompensateAmt());
				}
			}
			value.put("compensateAmtAtt", attTotal);
			values.put(roomId, value);
		}
		return values;
	}

	private RoomCompenSateResult getRoomCompenSateResultForSHE(Context ctx,
			String roomId, String attRoomId, CompensateSchemeInfo schemeInfo,
			boolean isAttCalc) throws BOSException, EASBizException {
		RoomCompenSateResult roomComResult = new RoomCompenSateResult();
		roomComResult.setCompensateAmt(new BigDecimal(0.00));
		roomComResult.setCompensateRate(new BigDecimal(0.00));

		RoomInfo roomInfo = null;

		if (isAttCalc) {
			roomInfo = RoomFactory
					.getLocalInstance(ctx)
					.getRoomInfo(
							"select buildingArea,actualBuildingArea,roomArea,actualRoomArea,lastPurchase.dealBuildPrice"
									+ ",lastPurchase.dealRoomPrice where id = '"
									+ attRoomId + "'");
		} else {
			roomInfo = RoomFactory
					.getLocalInstance(ctx)
					.getRoomInfo(
							"select buildingArea,actualBuildingArea,roomArea,actualRoomArea,lastPurchase.dealBuildPrice"
									+ ",lastPurchase.dealRoomPrice where id = '"
									+ roomId + "'");
		}

		if (roomInfo == null)
			return roomComResult;

		SignManageInfo signInfo = null;
		SignManageCollection signColl = null;
		signColl = SignManageFactory.getLocalInstance(ctx)
				.getSignManageCollection(
						"select id,dealBuildPrice,dealRoomPrice where bizState='SignAudit' and room.id='"
								+ roomId + "'");

		if (signColl == null || signColl.size() <= 0) {
			return roomComResult;
		} else {
			signInfo = signColl.get(0);
		}

		BigDecimal comDealRoomPrice = FDCHelper.ZERO; // 待比较的房间成交价格
		BigDecimal comRoomArea = FDCHelper.ZERO; // 待比较的房间原面积
		BigDecimal comActualRoomArea = FDCHelper.ZERO; // 待比较的房间实际面积

		if (roomInfo.getActualBuildingArea() == null) {
			throw new BOSException(COMPENSATEAREA_BUILDINGAREA);
		} else if (roomInfo.getActualRoomArea() == null) {
			throw new BOSException(COMPENSATEAREA_ROOMAREA);
		}

		// 判断是按照套内还是建筑面积来计算
		if (schemeInfo.getCompensateType() == CompensateTypeEnum.BUILDINGAREA) {
			comRoomArea = roomInfo.getBuildingArea();
			comActualRoomArea = roomInfo.getActualBuildingArea();
			if (signInfo != null) {
				comDealRoomPrice = signInfo.getDealBuildPrice();
			}

		} else if (schemeInfo.getCompensateType() == CompensateTypeEnum.ROOMAREA) {
			comRoomArea = roomInfo.getRoomArea();
			comActualRoomArea = roomInfo.getActualRoomArea();

			if (signInfo != null) {
				comDealRoomPrice = signInfo.getDealRoomPrice();
			}
		} else if (schemeInfo.getCompensateType() == CompensateTypeEnum.ROOMTYPE) {
			if (roomInfo.isIsCalByRoomArea()) {
				comRoomArea = roomInfo.getRoomArea();
				comActualRoomArea = roomInfo.getActualRoomArea();
				if (signInfo != null) {
					comDealRoomPrice = signInfo.getDealBuildPrice();
				}
			} else {
				comRoomArea = roomInfo.getBuildingArea();
				comActualRoomArea = roomInfo.getActualBuildingArea();
				if (signInfo != null) {
					comDealRoomPrice = signInfo.getDealBuildPrice();
				}
			}
		}
		if (comRoomArea.compareTo(new BigDecimal(0.00)) == 0) {
			return roomComResult;
		}

		if (comActualRoomArea.compareTo(new BigDecimal(0.00)) == 0) {
			return roomComResult;
		}

		if (comDealRoomPrice.compareTo(new BigDecimal(0.00)) == 0) {
			return roomComResult;
		}
		comRoomArea = comRoomArea.setScale(2, BigDecimal.ROUND_HALF_UP);
		comActualRoomArea = comActualRoomArea.setScale(2,
				BigDecimal.ROUND_HALF_UP);
		comDealRoomPrice = comDealRoomPrice.setScale(2,
				BigDecimal.ROUND_HALF_UP);
		BigDecimal compensateRate = new BigDecimal(0.00); // 补差率
		BigDecimal compensateAmt = new BigDecimal(0.00); // 补差金额
		compensateRate = FDCHelper.divide(comActualRoomArea
				.subtract(comRoomArea), comRoomArea, 10,
				BigDecimal.ROUND_HALF_UP);
		compensateRate = FDCHelper.multiply(compensateRate, new BigDecimal(
				"100"));

		if (compensateRate.compareTo(new BigDecimal(0.00)) == 0)
			return roomComResult;

		// 查找补差率在方案分录中的哪个明细范围中
		// 按照最高标准补差 的一定要找到一个匹配的要补差明细，否则不用补差；按分段计算补差的只要包含即要算入
		CompensateSchemeEntryCollection comEntrys = schemeInfo.getEntrys();
		for (int i = 0; i < comEntrys.size(); i++) {
			CompensateSchemeEntryInfo comEntry = comEntrys.get(i);
			if (comEntry.getIsCompensate() == null
					|| comEntry.getIsCompensate().equals(BooleanEnum.NO))
				continue;
			if (comEntry.getFactor() == null
					|| comEntry.getFactor().compareTo(new BigDecimal(0.00)) == 0)
				continue;
			if (schemeInfo.getCalcWay().equals(CalcWayEnum.SUPREME)) {
				if (isInCompenSateRange(compensateRate, comEntry)) {
					compensateAmt = compensateRate.multiply(comRoomArea)
							.multiply(comDealRoomPrice).multiply(
									comEntry.getFactor());
					break;
				}
			} else if (schemeInfo.getCalcWay().equals(CalcWayEnum.APART)) {
				if (compensateRate.compareTo(new BigDecimal(0.00)) > 0) {
					if (comEntry.getMaxValue().compareTo(new BigDecimal(0.00)) < 0)
						continue;
					BigDecimal minValue = comEntry.getMinValue();
					if (minValue.compareTo(new BigDecimal(0.00)) < 0)
						minValue = new BigDecimal(0.00);
					if (isInCompenSateRange(compensateRate, comEntry)) {
						BigDecimal addComAmt = compensateRate
								.subtract(minValue).multiply(comRoomArea)
								.multiply(comDealRoomPrice).multiply(
										comEntry.getFactor());
						compensateAmt = compensateAmt.add(addComAmt);
					} else if (compensateRate.compareTo(comEntry.getMaxValue()) >= 0) {
						compensateAmt = compensateAmt.add(comEntry
								.getMaxValue().subtract(minValue).multiply(
										comRoomArea).multiply(comDealRoomPrice)
								.multiply(comEntry.getFactor()));
					}
				} else if (compensateRate.compareTo(new BigDecimal(0.00)) < 0) {
					if (comEntry.getMinValue().compareTo(new BigDecimal(0.00)) > 0)
						continue;
					BigDecimal maxValue = comEntry.getMaxValue();
					if (maxValue.compareTo(new BigDecimal(0.00)) > 0)
						maxValue = new BigDecimal(0.00);
					if (isInCompenSateRange(compensateRate, comEntry)) {
						BigDecimal addComAmt = compensateRate
								.subtract(maxValue).multiply(comRoomArea)
								.multiply(comDealRoomPrice).multiply(
										comEntry.getFactor());
						compensateAmt = compensateAmt.add(addComAmt);
					} else if (compensateRate.compareTo(comEntry.getMinValue()) <= 0) {
						compensateAmt = compensateAmt.add(comEntry
								.getMinValue().subtract(maxValue).multiply(
										comRoomArea).multiply(comDealRoomPrice)
								.multiply(comEntry.getFactor()));
					}
				}
			}
		}

		int decimalCount = schemeInfo.getDecimalCount();
		int decimalType = 4;
		if (schemeInfo.getDecimalType() != null)
			decimalType = (new Integer((String) schemeInfo.getDecimalType()
					.getValue())).intValue();
		roomComResult.setCompensateRate(compensateRate.setScale(2,
				BigDecimal.ROUND_HALF_UP));
		roomComResult.setCompensateAmt(compensateAmt.divide(
				new BigDecimal(100), decimalCount, decimalType));
		return roomComResult;
	}

	protected void _unAuditAndCalcSellAmount(Context ctx, String id)
			throws BOSException, EASBizException {

		// 计算
		CompensateRoomListCollection coll = CompensateRoomListFactory
		.getLocalInstance(ctx).getCompensateRoomListCollection(
				"select id,room.id,room.number,room.name,actAmount,lastAmount,head.appDate where head.id='"
						+ id + "'");
		// 得到房间id的字符串
		StringBuffer roomId = new StringBuffer();
		List roomIdList = new ArrayList();
		for (int i = 0; i < coll.size(); i++) {
			CompensateRoomListInfo info = coll.get(i);
			if (info != null && info.getRoom() != null) {
				roomIdList.add(info.getRoom());
				roomId.append(info.getRoom().getId().toString());
				if (i != coll.size() - 1) {
					roomId.append(",");
				}
			}
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo roomFilter = new FilterInfo();
		roomFilter.getFilterItems().add(new FilterItemInfo("room.id", roomId.toString(),CompareType.INCLUDE));
		roomFilter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.SIGNAUDIT_VALUE,CompareType.EQUALS));
		view.setFilter(roomFilter);
		SelectorItemCollection roomColl = new SelectorItemCollection();
		roomColl.add(new SelectorItemInfo("id"));
		roomColl.add(new SelectorItemInfo("room.id"));
		roomColl.add(new SelectorItemInfo("signPayListEntry.*"));
		roomColl.add(new SelectorItemInfo("signPayListEntry.moneyDefine.*"));
		view.setSelector(roomColl);
		SignManageCollection signColl = SignManageFactory.getLocalInstance(ctx).getSignManageCollection(view);

//		// 价差签约单中的补差款明细是否已经收过款了！
//		checkIsDelete(ctx, roomId.toString(), signColl);

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("compensateState"));
		selector.add(new SelectorItemInfo("auditor"));
		selector.add(new SelectorItemInfo("auditTime"));
		RoomAreaCompensateInfo model = new RoomAreaCompensateInfo();
		model.setId(BOSUuid.read(id));
		model.setAuditor(null);
		model.setAuditTime(null);
		model.setCompensateState(RoomCompensateStateEnum.COMSUBMIT);
		RoomAreaCompensateFactory.getLocalInstance(ctx).updatePartial(model,selector);

		List roomList = new ArrayList();
		for (int i = 0; i < coll.size(); i++) {
			CompensateRoomListInfo info = coll.get(i);
			if (info != null) {
				RoomSignCompensateInfo signInfo = new RoomSignCompensateInfo();
				signInfo.setRoomId(info.getRoom().getId().toString());
				signInfo.setActAmount(info.getActAmount());
				signInfo.setSellTotal(info.getLastAmount());
				roomList.add(signInfo);
			}
		}
		if (roomList.size() > 0) {
			try {
				String sql = "update T_SHE_SignManage set FSellAmount=?,FAreaCompensate=? where FRoomID=? and fbizState ='SignAudit'";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < roomList.size(); i++) {
					RoomSignCompensateInfo info = (RoomSignCompensateInfo) roomList.get(i);
					sqlBuilder.addParam(FDCHelper.subtract(info.getSellTotal(),info.getActAmount()));
					sqlBuilder.addParam(null);
					sqlBuilder.addParam(info.getRoomId());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新房间信息失败!");
				throw new BOSException(ex.getMessage() + "更新房间信息失败!");
			}
		}
		_deleteBillFromSign(ctx, roomId.toString(), signColl);
		_deleteRoomCompensateForView(ctx, roomIdList,id);
	}

	protected void _setAuditing(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("compensateState"));
		RoomAreaCompensateInfo model = new RoomAreaCompensateInfo();
		model.setId(billId);
		model.setCompensateState(RoomCompensateStateEnum.COMAUDITTING);
		RoomAreaCompensateFactory.getLocalInstance(ctx).updatePartial(model,
				selector);
	}

	protected void _setSubmit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("compensateState"));
		RoomAreaCompensateInfo model = new RoomAreaCompensateInfo();
		model.setId(billId);
		model.setCompensateState(RoomCompensateStateEnum.COMSUBMIT);
		RoomAreaCompensateFactory.getLocalInstance(ctx).updatePartial(model,
				selector);
	}

	class RoomSignCompensateInfo {
		private String roomId;
		private BigDecimal actAmount;
		private BigDecimal sellTotal;

		public String getRoomId() {
			return roomId;
		}

		public void setRoomId(String roomId) {
			this.roomId = roomId;
		}

		public BigDecimal getActAmount() {
			return actAmount;
		}

		public void setActAmount(BigDecimal actAmount) {
			this.actAmount = actAmount;
		}

		public BigDecimal getSellTotal() {
			return sellTotal;
		}

		public void setSellTotal(BigDecimal sellTotal) {
			this.sellTotal = sellTotal;
		}

	}

	protected void _auditAndCalcSellAmount(Context ctx, String id)
			throws BOSException, EASBizException {
		// 审批
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("compensateState"));
		selector.add(new SelectorItemInfo("auditor"));
		selector.add(new SelectorItemInfo("auditTime"));
		RoomAreaCompensateInfo model = new RoomAreaCompensateInfo();
		model.setId(BOSUuid.read(id));
		model.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		model.setAuditTime(FDCSQLFacadeFactory.getLocalInstance(ctx)
				.getServerTime());
		model.setCompensateState(RoomCompensateStateEnum.COMAUDITTED);
		RoomAreaCompensateFactory.getLocalInstance(ctx).updatePartial(model,
				selector);

		// 计算
		CompensateRoomListCollection coll = CompensateRoomListFactory
				.getLocalInstance(ctx).getCompensateRoomListCollection(
						"select id,room.id,room.number,room.name,actAmount,lastAmount,head.appDate where head.id='"
								+ id + "'");

		List roomList = new ArrayList();
		List roomIdList = new ArrayList();
		for (int i = 0; i < coll.size(); i++) {
			CompensateRoomListInfo info = coll.get(i);
			if (info != null) {
				RoomSignCompensateInfo signInfo = new RoomSignCompensateInfo();
				signInfo.setRoomId(info.getRoom().getId().toString());
				signInfo.setActAmount(info.getActAmount());
				signInfo.setSellTotal(info.getLastAmount());
				roomList.add(signInfo);
				roomIdList.add(info.getRoom());
			}
		}

		if (roomList.size() > 0) {
			try {
				String sql = "update T_SHE_SignManage set FSellAmount=?,FAreaCompensate=? where FRoomID=? and fbizState ='SignAudit'";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < roomList.size(); i++) {
					RoomSignCompensateInfo info = (RoomSignCompensateInfo) roomList.get(i);
					sqlBuilder.addParam(info.getSellTotal());
					sqlBuilder.addParam(info.getActAmount());
					sqlBuilder.addParam(info.getRoomId());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新房间信息失败!");
				throw new BOSException(ex.getMessage() + "更新房间信息失败!");
			}

		}
		// 在签约单的收款单明细中生成一条收款明细
		_createBillForSign(ctx, coll);
		_createRoomCompensateForView(ctx, roomIdList, id);
	}

	private String getDateString() {
		String temp = "";
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		temp = sf.format(date);
		return temp;
	}

	protected void _deleteCompensateInfo(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		this.recycleNumber(ctx, new ObjectUuidPK(billId));

		StringBuffer ds1 = null;
		StringBuffer ds2 = null;
		StringBuffer ds3 = null;
		
		MoneyDefineInfo moneyInfo = null;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", "面积补差款"));
		// 如果在款项类别中不存在补差款类型，则新生成一项
		if (MoneyDefineFactory.getLocalInstance(ctx).exists(filter)) {
			MoneyDefineCollection moneyColl = MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineCollection("select id,name,number where name='面积补差款'");

			for (int i = 0; i < moneyColl.size(); i++) {
				moneyInfo = moneyColl.get(i);
				break;
			}
			
			CompensateRoomListCollection coll = CompensateRoomListFactory.getLocalInstance(ctx).getCompensateRoomListCollection(
					"select id,room.id,room.name,room.number actAmount,lastAmount where head.id='"+ billId + "'");
			// 得到房间id的字符串
			StringBuffer roomId = new StringBuffer();
			List roomIdList = new ArrayList();
			for (int i = 0; i < coll.size(); i++) {
				CompensateRoomListInfo info = coll.get(i);
				if (info != null && info.getRoom() != null) {
					FilterInfo chfilter = new FilterInfo();
					chfilter.getFilterItems().add(new FilterItemInfo("sourceBillId", info.getId().toString()));
					if(RoomTransferFactory.getLocalInstance(ctx).exists(chfilter)){
						throw new EASBizException(new NumericExceptionSubItem("100","房间"+info.getRoom().getName()+"已结转，不能进行删除操作！"));
					}
					roomIdList.add(info.getRoom());
					roomId.append(info.getRoom().getId().toString());
					if (i != coll.size() - 1) {
						roomId.append(",");
					}
				}
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo roomFilter = new FilterInfo();
			roomFilter.getFilterItems().add(new FilterItemInfo("room.id", roomId.toString(),CompareType.INCLUDE));
			roomFilter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.SIGNAUDIT_VALUE,CompareType.EQUALS));
			view.setFilter(roomFilter);
			SelectorItemCollection roomColl = new SelectorItemCollection();
			roomColl.add(new SelectorItemInfo("transactionID"));
			roomColl.add(new SelectorItemInfo("id"));
			roomColl.add(new SelectorItemInfo("room.id"));
			roomColl.add(new SelectorItemInfo("room.name"));
			roomColl.add(new SelectorItemInfo("signPayListEntry.*"));
			roomColl.add(new SelectorItemInfo("signPayListEntry.moneyDefine.*"));
			view.setSelector(roomColl);
			SignManageCollection signColl = SignManageFactory.getLocalInstance(ctx).getSignManageCollection(view);
			
			for (int i = 0; i < signColl.size(); i++) {
				SignManageInfo sign = signColl.get(i);
				if (sign != null) {
					if (sign.getTransactionID() == null)continue;
					
					String payListId =null;
					String tranPayListId=null;
					for(int j=0;j<sign.getSignPayListEntry().size();j++){
						if(sign.getSignPayListEntry().get(j).getMoneyDefine()!=null
								&&sign.getSignPayListEntry().get(j).getMoneyDefine().getId().toString().equals(moneyInfo.getId().toString())){
							payListId=sign.getSignPayListEntry().get(j).getId().toString();
							if(sign.getSignPayListEntry().get(j).getTanPayListEntryId()!=null){
								TranBusinessOverViewInfo tran=TranBusinessOverViewFactory.getLocalInstance(ctx).getTranBusinessOverViewInfo(new ObjectUuidPK(sign.getSignPayListEntry().get(j).getTanPayListEntryId()));
								if(tran.getActRevAmount()!=null&&tran.getActRevAmount().compareTo(FDCHelper.ZERO)!=0){
									throw new EASBizException(new NumericExceptionSubItem("100",sign.getRoom().getName()+"面积补差款已经收款，禁止删除！"));
								}
								tranPayListId=sign.getSignPayListEntry().get(j).getTanPayListEntryId().toString();
							}
						}
					}
					if(payListId!=null){
						ds1= new StringBuffer();

						ds1.append("delete from T_SHE_SignPayListEntry where fid=");
						ds1.append("'" + payListId + "'");
						
						if(tranPayListId!=null){
							ds2= new StringBuffer();

							ds2.append("delete from T_SHE_TranBusinessOverView where fid=");
							ds2.append("'" + tranPayListId + "'");
						}
						ds3 = new StringBuffer();

						ds3.append("delete from T_SHE_TranBusinessOverView where fheadid=");
						ds3.append("'" + sign.getTransactionID() + "' and fbusinessName='"+SHEManageHelper.AREACOMPENSATE+"'");
						ds3.append(" and fType='"+BusTypeEnum.BUSINESS_VALUE+"'");
					}
				}
			}
		}
		StringBuffer mainT = new StringBuffer();

		mainT.append("delete from T_SHE_RoomAreaCompensate where fid=");
		mainT.append("'" + billId + "'");

		StringBuffer attT = new StringBuffer();

		attT.append("delete from T_SHE_CompensateRoomList where fheadid=");
		attT.append("'" + billId + "'");

		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		sqlBuilder.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		sqlBuilder.addBatch(mainT.toString());
		sqlBuilder.addBatch(attT.toString());
		if(ds1!=null){
			sqlBuilder.addBatch(ds1.toString());
		}
		if(ds2!=null){
			sqlBuilder.addBatch(ds2.toString());
		}
		if(ds3!=null){
			sqlBuilder.addBatch(ds3.toString());
		}
		sqlBuilder.executeBatch();
	}

	protected void _createBillForSign(Context ctx,
			CompensateRoomListCollection compColl) throws BOSException,
			EASBizException {
		Map roomPrice = new HashMap();
		MoneyDefineInfo moneyInfo = null;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", "面积补差款"));
		// 如果在款项类别中不存在补差款类型，则新生成一项
		if (!MoneyDefineFactory.getLocalInstance(ctx).exists(filter)) {
			MoneyDefineInfo model = new MoneyDefineInfo();
			model.setName("面积补差款");
			model.setNumber(getDateString());
			model.setMoneyType(MoneyTypeEnum.HouseAmount);
			model.setSysType(MoneySysTypeEnum.SalehouseSys);
			model.setIsGroup(false);
			CtrlUnitInfo ctrlUnit = new CtrlUnitInfo();
			ctrlUnit.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
			model.setCU(ctrlUnit);
			MoneyDefineFactory.getLocalInstance(ctx).addnew(model);
		}

		MoneyDefineCollection moneyColl = MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineCollection("select id,name,number where name='面积补差款'");

		for (int i = 0; i < moneyColl.size(); i++) {
			moneyInfo = moneyColl.get(i);
			break;
		}
		StringBuffer roomId = new StringBuffer();
		Date appDate=null;
		for (int i = 0; i < compColl.size(); i++) {
			CompensateRoomListInfo info = compColl.get(i);
			if (info != null && info.getRoom() != null) {
				roomId.append(info.getRoom().getId().toString());
				roomPrice.put(info.getRoom().getId().toString(), info.getActAmount());
				if (i != compColl.size() - 1) {
					roomId.append(",");
				}
			}
			appDate=info.getHead().getAppDate();
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo roomFilter = new FilterInfo();
		roomFilter.getFilterItems().add(new FilterItemInfo("room.id", roomId.toString(),CompareType.INCLUDE));
		roomFilter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.SIGNAUDIT_VALUE,CompareType.EQUALS));
		view.setFilter(roomFilter);
		SelectorItemCollection roomColl = new SelectorItemCollection();
		roomColl.add(new SelectorItemInfo("transactionID"));
		roomColl.add(new SelectorItemInfo("id"));
		roomColl.add(new SelectorItemInfo("room.id"));
		roomColl.add(new SelectorItemInfo("transactionID"));
		roomColl.add(new SelectorItemInfo("signPayListEntry.moneyDefine.*"));
		roomColl.add(new SelectorItemInfo("signPayListEntry.*"));
		view.setSelector(roomColl);
		SignManageCollection signColl = SignManageFactory.getLocalInstance(ctx).getSignManageCollection(view);
		BigDecimal actAmount = FDCHelper.ZERO;
		for (int i = 0; i < signColl.size(); i++) {
			SignManageInfo sign = signColl.get(i);
			boolean isAddnew=true;
			if (sign != null) {
				if (sign.getTransactionID() == null)continue;
				
				SignPayListEntryInfo entry=null;
				for(int j=0;j<sign.getSignPayListEntry().size();j++){
					if(sign.getSignPayListEntry().get(j).getMoneyDefine()!=null
							&&sign.getSignPayListEntry().get(j).getMoneyDefine().getId().toString().equals(moneyInfo.getId().toString())){
						isAddnew=false;
						entry=sign.getSignPayListEntry().get(j);
					}
				}
				if(isAddnew){
					TransactionInfo tranInfo = TransactionFactory.getLocalInstance(ctx).getTransactionInfo(new ObjectUuidPK(sign.getTransactionID()));
					TranBusinessOverViewInfo tran = new TranBusinessOverViewInfo();
					tran.setHead(tranInfo);
					tran.setId(BOSUuid.create(tran.getBOSType()));
					tran.setAppDate(appDate);
					tran.setMoneyDefine(moneyInfo);
					CurrencyInfo currency = getCurrencyInfo(ctx);
					if (currency != null) {
						tran.setCurrency(currency);
					}
					if (!roomPrice.isEmpty()) {
						actAmount = (BigDecimal) roomPrice.get(sign.getRoom().getId().toString());
						tran.setAppAmount(actAmount);
					}
					tran.setDescription("该记录来自面积补差收款!");
					tran.setDesc("该记录来自面积补差收款!");
					tran.setBusinessName(moneyInfo.getName());
//					tran.setFinishDate(FDCSQLFacadeFactory.getLocalInstance(ctx).getServerTime());
					tran.setType(BusTypeEnum.PAY);
					tran.setIsFinish(false);
					TranBusinessOverViewFactory.getLocalInstance(ctx).addnew(tran);
					
					SignPayListEntryInfo entryModel = new SignPayListEntryInfo();
					entryModel.setHead(sign);
					SHEManageHelper.setPayListEntry(entryModel, tran, true);
					entryModel.setTanPayListEntryId(tran.getId());
					entryModel.setDescription("该明细来自面积补差收款!");

					SignPayListEntryFactory.getLocalInstance(ctx).addnew(entryModel);
				}else{
					SelectorItemCollection sel = new SelectorItemCollection();
					sel.add("appAmount");
					sel.add("appDate");
					
					if (!roomPrice.isEmpty()) {
						actAmount = (BigDecimal) roomPrice.get(sign.getRoom().getId().toString());
						entry.setAppAmount(actAmount);
						entry.setAppDate(appDate);
						SignPayListEntryFactory.getLocalInstance(ctx).updatePartial(entry, sel);
					}
					if(entry.getTanPayListEntryId()!=null){
						TranBusinessOverViewInfo tran=TranBusinessOverViewFactory.getLocalInstance(ctx).getTranBusinessOverViewInfo(new ObjectUuidPK(entry.getTanPayListEntryId()));
						tran.setAppAmount(actAmount);
						tran.setAppDate(appDate);
						TranBusinessOverViewFactory.getLocalInstance(ctx).updatePartial(tran, sel);
					}
				}
			}
		}
	}

	private static CurrencyInfo getCurrencyInfo(Context ctx)
			throws EASBizException, BOSException {
		if (CurrencyFactory.getLocalInstance(ctx).exists(
				new ObjectUuidPK(CURRENCY))) {
			return CurrencyFactory.getLocalInstance(ctx).getCurrencyInfo(
					new ObjectUuidPK(CURRENCY));
		} else {
			return null;
		}
	}

	private int getSeq(Context ctx, String id) {
		int number = 0;

		try {
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			sql.appendSql("select max(fseq) as seq from T_SHE_SignPayListEntry where fheadid='"+ id + "'");
			IRowSet rs = sql.executeQuery();
			if (rs.next()) {
				number = rs.getInt("seq");
			}
		} catch (Exception ex) {
		}
		return number;
	}

	protected void _deleteBillFromSign(Context ctx, String roomId,SignManageCollection signColl) throws BOSException, EASBizException {
		MoneyDefineInfo moneyInfo = null;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", "面积补差款"));
		if (!MoneyDefineFactory.getLocalInstance(ctx).exists(filter)) {
			return;
		}
		MoneyDefineCollection moneyColl = MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineCollection("select id,name,number where name='面积补差款'");

		for (int i = 0; i < moneyColl.size(); i++) {
			moneyInfo = moneyColl.get(i);
			break;
		}
		for (int i = 0; i < signColl.size(); i++) {
			SignManageInfo sign = signColl.get(i);
			if (sign != null) {
				if (sign.getTransactionID() == null)continue;
				
				for(int j=0;j<sign.getSignPayListEntry().size();j++){
					if(sign.getSignPayListEntry().get(j).getMoneyDefine()!=null
							&&sign.getSignPayListEntry().get(j).getMoneyDefine().getId().toString().equals(moneyInfo.getId().toString())){
						SignPayListEntryInfo entry=sign.getSignPayListEntry().get(j);
						SelectorItemCollection sel = new SelectorItemCollection();
						sel.add("appAmount");
						entry.setAppAmount(FDCHelper.ZERO);
						SignPayListEntryFactory.getLocalInstance(ctx).updatePartial(entry, sel);
						if(entry.getTanPayListEntryId()!=null){
							TranBusinessOverViewInfo tran=TranBusinessOverViewFactory.getLocalInstance(ctx).getTranBusinessOverViewInfo(new ObjectUuidPK(entry.getTanPayListEntryId()));
							tran.setAppAmount(FDCHelper.ZERO);
							TranBusinessOverViewFactory.getLocalInstance(ctx).updatePartial(tran, sel);
						}
					}
				}
			}
		}
	}
	private void checkIsDelete(Context ctx, String roomId,
			SignManageCollection signColl) throws BOSException, EASBizException {
		String msg = "";
		for (int i = 0; i < signColl.size(); i++) {
			SignManageInfo sign = signColl.get(i);
			if (sign != null && sign.getSignPayListEntry() != null) {
				for (int j = 0; j < sign.getSignPayListEntry().size(); j++) {
					SignPayListEntryInfo entryInfo = sign.getSignPayListEntry()
							.get(j);
					if (entryInfo != null) {
						if (entryInfo.getMoneyDefine().getMoneyType() != null
								&& entryInfo.getMoneyDefine().getMoneyType()
										.equals(MoneyTypeEnum.CompensateAmount)) {
							if (entryInfo.getActRevAmount() != null
									&& entryInfo.getActRevAmount().compareTo(
											FDCHelper.ZERO) > 0) {
								msg = msg + "id:'"
										+ entryInfo.getId().toString()
										+ "'roomAreaCompensate has receviced!";
							}
						}
					}
				}
			}
		}

		if (!"".equals(msg) && msg.length() > 0) {
			throw new BOSException("roomAreaCompensate is error!");
		}
	}

	/**
	 * 处理业务明细中的面积补差记录
	 */
	protected void _createRoomCompensateForView(Context ctx, List roomIdList,
			String compId) throws BOSException, EASBizException {

		if (roomIdList != null && roomIdList.size() > 0) {
			RoomAreaCompensateInfo info = RoomAreaCompensateFactory
					.getLocalInstance(ctx).getRoomAreaCompensateInfo(
							"select id,appDate where id='" + compId + "'");
			for (int i = 0; i < roomIdList.size(); i++) {
				RoomInfo room = (RoomInfo) roomIdList.get(i);
				if (room != null) {
					if (info != null && info.getAppDate() != null) {
						updateTransactionOverView(ctx, room,
								SHEManageHelper.AREACOMPENSATE,info
								.getAppDate(),FDCSQLFacadeFactory.getLocalInstance(ctx)
								.getServerTime());
					}
				}
			}
		}

	}

	protected void _deleteRoomCompensateForView(Context ctx, List roomIdList,
			String compId) throws BOSException, EASBizException {
		if (roomIdList != null && roomIdList.size() > 0) {
			for (int i = 0; i < roomIdList.size(); i++) {
				RoomInfo room = (RoomInfo) roomIdList.get(i);
				if (room != null) {
					updateTransactionOverView(ctx, room,
							SHEManageHelper.AREACOMPENSATE, null, null);
				}
			}
		}
	}

	private void updateTransactionOverView(Context ctx, RoomInfo room,
			String serviceType, Date promiseFinishDate, Date actualFinishDate) {
		try {
			TransactionInfo transactionInfo = getTransactionInfo(ctx, room);
			TranBusinessOverViewCollection overViewCol = transactionInfo
					.getTranBusinessOverView();
			if (overViewCol != null && !overViewCol.isEmpty()) {
				for (int i = 0; i < overViewCol.size(); i++) {
					TranBusinessOverViewInfo overViewInfo = overViewCol.get(i);
					if (overViewInfo.getType().equals(BusTypeEnum.BUSINESS)
							&& overViewInfo.getBusinessName().equals(
									serviceType)) {
						if (actualFinishDate != null) { // 更新为完成状态
							overViewInfo.setActualFinishDate(actualFinishDate);
							overViewInfo.setFinishDate(promiseFinishDate);
							overViewInfo.setIsFinish(true);
						} else { // 恢复为未完成状态
							overViewInfo.setActualFinishDate(null);
							overViewInfo.setIsFinish(false);
						}
						if (ctx == null) {
							TransactionFactory.getRemoteInstance().update(
									new ObjectUuidPK(transactionInfo.getId()
											.toString()), transactionInfo);
						} else {
							TransactionFactory.getLocalInstance(ctx).update(
									new ObjectUuidPK(transactionInfo.getId()
											.toString()), transactionInfo);
						}

						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static TransactionInfo getTransactionInfo(Context ctx, RoomInfo room)
			throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("tranDate");
		view.getSelector().add("preLink");
		view.getSelector().add("currentLink");
		view.getSelector().add("billId");
		view.getSelector().add("tranBusinessOverView.*");

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("isValid", new Boolean(false)));

		view.setFilter(filter);
		TransactionCollection transactionCol = null;
		if (ctx == null) {
			transactionCol = TransactionFactory.getRemoteInstance()
					.getTransactionCollection(view);
		} else {
			transactionCol = TransactionFactory.getLocalInstance(ctx)
					.getTransactionCollection(view);
		}

		if (transactionCol != null && !transactionCol.isEmpty()) {
			return transactionCol.get(0);
		} else {
			return null;
		}
	}

}