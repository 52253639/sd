package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo;
import com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEComHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPriceHistoryEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPriceHistoryEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.util.app.ContextUtil;

public class PriceAdjustControllerBean extends
		AbstractPriceAdjustControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.PriceAdjustControllerBean");

	protected boolean _execute(Context ctx, String id) throws BOSException,
			EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("entrys.*");
		sels.add("entrys.room.*");
		PriceAdjustInfo bill = (PriceAdjustInfo) this.getValue(ctx,
				new ObjectUuidPK(BOSUuid.read(id)), sels);
		if (bill.getAuditor() == null) {
			return false;
		}
		
		boolean isToInteger = bill.isIsAutoToInteger();
		ToIntegerTypeEnum toIntegerType = bill.getToIntegerType();
		DigitEnum digit = bill.getDigit();
		
		PriceAdjustEntryCollection entrys = bill.getEntrys();
		for (int i = 0; i < entrys.size(); i++) {
			PriceAdjustEntryInfo entry = entrys.get(i);
			RoomInfo room = entry.getRoom();
			// 房间已售
			if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)
					|| room.getSellState().equals(RoomSellStateEnum.Purchase)
					|| room.getSellState().equals(RoomSellStateEnum.Sign)) {
				continue;
			}
			// 套内面积改变
			BigDecimal entryRoomArea = entry.getOldRoomArea();
			if (entryRoomArea == null) {
				entryRoomArea = FDCHelper.ZERO;
			}
			BigDecimal roomArea = room.getRoomArea();
			if (roomArea == null) {
				roomArea = FDCHelper.ZERO;
			}
			if (roomArea.compareTo(entryRoomArea) != 0) {
				continue;
			}
			// 建筑面积改变
			BigDecimal entryBuildingArea = entry.getOldBuildingArea();
			if (entryBuildingArea == null) {
				entryBuildingArea = FDCHelper.ZERO;
			}
			BigDecimal buildingArea = room.getBuildingArea();
			if (buildingArea == null) {
				buildingArea = FDCHelper.ZERO;
			}
			if (buildingArea.compareTo(entryBuildingArea) != 0) {
				continue;
			}

			BigDecimal newBuldingPrice = entry.getNewBuildingPrice();
			room.setBuildPrice(newBuldingPrice);
			BigDecimal newRoomPrice = entry.getNewRoomPrice();
			room.setRoomPrice(newRoomPrice);
			BigDecimal totalAmount = FDCHelper.ZERO;
			if (entry.isIsCalByRoomNew()) {
				if (newRoomPrice != null) {
					totalAmount = roomArea.multiply(newRoomPrice);
				}
			} else {
				if (newBuldingPrice != null) {
					totalAmount = buildingArea.multiply(newBuldingPrice);
				}
			}
			
			//增加取整的操作
			totalAmount = SHEComHelper.getAmountAfterToInteger(totalAmount, isToInteger, toIntegerType, digit);
			
			//--只保留2位小数 zhicheng_jin  090120
			totalAmount = totalAmount.divide(FDCHelper.ONE, 2, BigDecimal.ROUND_HALF_UP);
			//--------
			room.setStandardTotalAmount(totalAmount);
			sels = new SelectorItemCollection();
			sels.add("buildPrice");
			sels.add("roomPrice");
			sels.add("standardTotalAmount");
			// sels.add("isCalByRoomArea");
			RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
			
			this.addToSHEPriceHistoryEntry(ctx,bill,totalAmount,entry);
		}
		bill.setIsExecuted(true);
		sels = new SelectorItemCollection();
		sels.add("isExecuted");
		this.updatePartial(ctx, bill, sels);
		return true;
	}
	
	/**
	 * 将定价信息放入到定价历史记录里面去
	 * @param ctx
	 * @param bill
	 * @param totalAmount
	 * @param entry
	 * @throws EASBizException
	 * @throws BOSException
	 * @author laiquan_luo
	 */
	private void addToSHEPriceHistoryEntry(Context ctx,PriceAdjustInfo bill,BigDecimal totalAmount,PriceAdjustEntryInfo entry) throws EASBizException, BOSException
	{
		SHEPriceHistoryEntryInfo history = new SHEPriceHistoryEntryInfo();
		
		//history.setId(BOSUuid.create(history.getBOSType()));
		history.setOrgUnit(ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo());
		history.setPriceAdjustBill(bill);
		history.setStandardTotalPrice(totalAmount);
		history.setBuildPrice(entry.getNewBuildingPrice());
		history.setRoomPrice(entry.getNewRoomPrice());
		history.setIsCalByRoomArea(entry.isIsCalByRoomNew());
		history.setHead(entry.getRoom());
		history.setPriceType(PriceBillTypeEnum.AdjustPrice);
		history.setBizDate(new Date());
		history.setNumber(String.valueOf(new Date().getTime()));
		history.setName(String.valueOf(new Date().getTime()));
		
		SHEPriceHistoryEntryFactory.getLocalInstance(ctx).submit(history);
	}

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		PriceAdjustInfo billInfo = (PriceAdjustInfo) model;
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

		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("name", billInfo.getName()));
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
			throw new ContractException(ContractException.NAME_DUP);
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
}