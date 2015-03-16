package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.BasePriceControlFactory;
import com.kingdee.eas.fdc.sellhouse.BasePriceControlInfo;
import com.kingdee.eas.fdc.sellhouse.BasePriceRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BasePriceRoomEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class BasePriceControlControllerBean extends
		AbstractBasePriceControlControllerBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2130365328173638664L;
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.BasePriceControlControllerBean");

	protected void _updateRoom(Context ctx, List roomList) throws BOSException,
			EASBizException {

		if (roomList != null && roomList.size() > 0) {
			try {
				String sql = "update t_she_room set FBaseStandardPrice=?,FBaseBuildingPrice=?,FBaseRoomPrice=? where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < roomList.size(); i++) {
					RoomInfo info = (RoomInfo) roomList.get(i);
					sqlBuilder.addParam(info.getBaseStandardPrice());
					sqlBuilder.addParam(info.getBaseBuildingPrice());
					sqlBuilder.addParam(info.getBaseRoomPrice());
					sqlBuilder.addParam(info.getId().toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新房间信息失败!");
				throw new BOSException(ex.getMessage() + "更新房间信息失败!");
			}

		}
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		BasePriceControlInfo info = (BasePriceControlInfo) model;
		BasePriceRoomEntryCollection coll = info.getRoomEntry();
		List roomList = new ArrayList();
		for (int i = 0; i < coll.size(); i++) {
			BasePriceRoomEntryInfo entryInfo = coll.get(i);
			if (entryInfo != null) {
				RoomInfo room = entryInfo.getRoom();
				if (room != null) {
					roomList.add(room);
				}
			}

		}
		_updateRoom(ctx, roomList);
		return super._submit(ctx, model);
	}

	protected List _getRoomInfoList(Context ctx, String filterString)
			throws BOSException, EASBizException {

		List roomList = new ArrayList();

		StringBuffer mainSql = new StringBuffer();
		mainSql.append("select");
		mainSql.append(" room.fid roomId, ");
		mainSql.append(" room.FBuildingArea buildArea, ");
		mainSql.append(" room.FRoomArea roomArea, ");
		mainSql.append(" room.FBuildPrice buildPrice, ");
		mainSql.append(" room.FRoomPrice roomPrice, ");
		mainSql.append(" room.FStandardTotalAmount standardAmount, ");
		mainSql.append(" room.FBaseStandardPrice basestandardPrice, ");
		mainSql.append(" room.FBaseBuildingPrice basebuildPrice, ");
		mainSql.append(" room.FBaseRoomPrice baseRoomPrice, ");
		mainSql.append(" room.FdisplayName number, ");
		mainSql.append(" room.froomno roomNo,");
		mainSql.append(" room.FSellState sellState,");
		mainSql.append(" room.FSellType sellType,");
		mainSql.append(" building.fid buildingId ");
		mainSql.append(" from t_she_room room ");
		mainSql
				.append(" left join T_SHE_Building building on room.FBuildingID = building.fid ");
		mainSql
				.append(" left join T_SHE_BuildingUnit buildingUnit on room.FBuildUnitID = buildingUnit.fid");
		mainSql
				.append(" left join T_SHE_Subarea subArea on building.FSubareaID = subArea.fid ");
		mainSql
				.append(" left join t_she_sellproject sellProject on building.FSellProjectID = sellProject.fid ");
		mainSql.append(" where 1=1 ");
		if (filterString!=null && filterString.length() > 0) {
			mainSql.append(filterString);
		}
		
		mainSql.append("order by room.FFloor asc,room.fnumber asc");

		try {
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			sql.appendSql(mainSql.toString());
			IRowSet rs = sql.executeQuery();
			while (rs.next()) {
				RoomInfo room = new RoomInfo();
				room.setId(BOSUuid.read(rs.getString("roomId")));
				if (rs.getBigDecimal("buildArea") != null) {
					room.setBuildingArea(rs.getBigDecimal("buildArea"));
				}
				if (rs.getBigDecimal("roomArea") != null) {
					room.setRoomArea(rs.getBigDecimal("roomArea"));
				}
				if (rs.getBigDecimal("buildPrice") != null) {
					room.setBuildPrice(rs.getBigDecimal("buildPrice"));
				}
				if (rs.getBigDecimal("roomPrice") != null) {
					room.setRoomPrice(rs.getBigDecimal("roomPrice"));
				}
				if (rs.getBigDecimal("standardAmount") != null) {
					room.setStandardTotalAmount(rs
							.getBigDecimal("standardAmount"));
				}
				if (rs.getBigDecimal("basestandardPrice") != null) {
					room.setBaseStandardPrice(rs
							.getBigDecimal("basestandardPrice"));
				}
				if (rs.getBigDecimal("basebuildPrice") != null) {
					room.setBaseBuildingPrice(rs
							.getBigDecimal("basebuildPrice"));
				}
				if (rs.getBigDecimal("baseRoomPrice") != null) {
					room.setBaseRoomPrice(rs.getBigDecimal("baseRoomPrice"));
				}
				if (rs.getString("number") != null) {
					room.setDisplayName(rs.getString("number"));
				}
				if (rs.getString("roomNo") != null) {
					room.setRoomNo(rs.getString("roomNo"));
				}
				if(rs.getString("sellState")!=null){
					if(rs.getString("sellState").equals(RoomSellStateEnum.INIT_VALUE)){
						room.setSellState(RoomSellStateEnum.Init);
					}else if(rs.getString("sellState").equals(RoomSellStateEnum.ONSHOW_VALUE)){
						room.setSellState(RoomSellStateEnum.OnShow);
					}else if(rs.getString("sellState").equals(RoomSellStateEnum.KEEPDOWN_VALUE)){
						room.setSellState(RoomSellStateEnum.KeepDown);
					}else if(rs.getString("sellState").equals(RoomSellStateEnum.PREPURCHASE_VALUE)){
						room.setSellState(RoomSellStateEnum.PrePurchase);
					}else if(rs.getString("sellState").equals(RoomSellStateEnum.PURCHASE_VALUE)){
						room.setSellState(RoomSellStateEnum.Purchase);
					}else if(rs.getString("sellState").equals(RoomSellStateEnum.SIGN_VALUE)){
						room.setSellState(RoomSellStateEnum.Sign);
					}else if(rs.getString("sellState").equals(RoomSellStateEnum.SINCERPURCHASE_VALUE)){
						room.setSellState(RoomSellStateEnum.SincerPurchase);
					}else if(rs.getString("sellState").equals(RoomSellStateEnum.PRICECHANGE_VALUE)){
						room.setSellState(RoomSellStateEnum.priceChange);
					}else if(rs.getString("sellState").equals(RoomSellStateEnum.CHANGEROOM_VALUE)){
						room.setSellState(RoomSellStateEnum.changeRoom);
					}else if(rs.getString("sellState").equals(RoomSellStateEnum.QUITROOM_VALUE)){
						room.setSellState(RoomSellStateEnum.quitRoom);
					}else if(rs.getString("sellState").equals(RoomSellStateEnum.CHANGENAME_VALUE)){
						room.setSellState(RoomSellStateEnum.ChangeName);
					}
				}
				if (rs.getString("sellType") != null) {
					if (rs.getString("sellType").equals(
							SellTypeEnum.PRESELL_VALUE)) {
						room.setSellType(SellTypeEnum.PreSell);
					} else if (rs.getString("sellType").equals(
							SellTypeEnum.LOCALESELL_VALUE)) {
						room.setSellType(SellTypeEnum.LocaleSell);
					} else if (rs.getString("sellType").equals(
							SellTypeEnum.PLANNINGSELL_VALUE)) {
						room.setSellType(SellTypeEnum.PlanningSell);
					}
				}
			
				if (rs.getString("buildingId") != null) {
					BuildingInfo build = new BuildingInfo();
					build.setId(BOSUuid.read(rs.getString("buildingId")
							.toString()));
					room.setBuilding(build);
				}
				roomList.add(room);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage() + "获得房屋信息失败!");
		}

		return roomList;
	}

	protected void _auditBasePrice(Context ctx, BOSUuid id)
			throws BOSException, EASBizException {

		UserInfo user = ContextUtil.getCurrentUserInfo(ctx);
		Timestamp sp = FDCSQLFacadeFactory.getLocalInstance(ctx)
				.getServerTime();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("state"));
		selector.add(new SelectorItemInfo("auditor"));
		selector.add(new SelectorItemInfo("auditTime"));
		BasePriceControlInfo model = new BasePriceControlInfo();
		model.setId(id);
		model.setState(FDCBillStateEnum.AUDITTED);
		model.setAuditor(user);
		model.setAuditTime(sp);
		try {
			BasePriceControlFactory.getLocalInstance(ctx).updatePartial(model,
					selector);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "审批失败!");
		}
	}

	protected void _setSubmit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("state"));
		BasePriceControlInfo model = new BasePriceControlInfo();
		model.setId(billId);
		model.setState(FDCBillStateEnum.SUBMITTED);
		try {
			BasePriceControlFactory.getLocalInstance(ctx).updatePartial(model,
					selector);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "反审批失败!");
		}
		
	}

	protected void _unAuditBasePrice(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("state"));
		selector.add(new SelectorItemInfo("auditor"));
		selector.add(new SelectorItemInfo("auditTime"));
		BasePriceControlInfo model = new BasePriceControlInfo();
		model.setId(billId);
		model.setState(FDCBillStateEnum.SUBMITTED);
		model.setAuditor(null);
		model.setAuditTime(null);
		try {
			BasePriceControlFactory.getLocalInstance(ctx).updatePartial(model,
					selector);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "反审批失败!");
		}
	}

	protected void _setAuditing(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("state"));
		BasePriceControlInfo model = new BasePriceControlInfo();
		model.setId(billId);
		model.setState(FDCBillStateEnum.AUDITTING);
		try {
			BasePriceControlFactory.getLocalInstance(ctx).updatePartial(model,
					selector);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "反审批失败!");
		}
		
	}
}