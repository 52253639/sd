/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.market.MarketManageChargeCollection;
import com.kingdee.eas.fdc.market.MarketManageCollection;
import com.kingdee.eas.fdc.market.MarketManageFactory;
import com.kingdee.eas.fdc.market.MarketTypeCollection;
import com.kingdee.eas.fdc.market.MarketTypeFactory;
import com.kingdee.eas.fdc.market.MarketTypeInfo;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeCollection;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.HopedRoomsAnalyseFilterUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ChargeEffectAnalyseRptUI extends AbstractChargeEffectAnalyseRptUI {
	private static final Logger logger = CoreUIObject.getLogger(ChargeEffectAnalyseRptUI.class);
	private ChargeEffectAnalyseFilterUI filterUI = null;
	private CommonQueryDialog commonQueryDialog = null;
	String[][] receiveType = null;
	Map sellProMarketTypeMap = null; // 销售项目id和活动类型id
	Map sellProMarketMap = null; // 销售项目id和营销活动id 对应 Row

	Map orgRows = null; // 组织id
	Map orgProRows = null; // 组织id+销售项目id
	Map orgProMarTypeRows = null; // 组织id+销售项目id+活动类型Id
	Map marketRows = null; // 活动id

	private String allMarketIds = null;
	private OrgStructureInfo curOrgUnitInfo = null;
	private ItemAction[] hideAction = { this.actionEdit, this.actionAddNew, this.actionRemove, this.actionLocate, this.actionView };

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		try {
			commonQueryDialog.addUserPanel(this.getFilterUI());
		} catch (Exception e) {
			handleException(e);
			e.printStackTrace();
		}
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	protected void execQuery() {

		try {
			initTableHead();
		} catch (BOSException e) {
			handleException(e);
		} catch (SQLException e) {
			handleException(e);
			e.printStackTrace();
		} catch (Exception e) {
			handleException(e);
			e.printStackTrace();
		}
		initTableSellPro();

	}

	/**
	 * 根据销售项目数构建表体中的销售项目
	 */
	private void initTableSellPro() {
		try {
			TreeModel sellProTree = SHEHelper.getSellProjectTree(this.actionOnLoad, null);
			DefaultMutableTreeNode sellProRoot = (DefaultMutableTreeNode) sellProTree.getRoot();
			orgRows = new HashMap();
			orgProRows = new HashMap();
			orgProMarTypeRows = new HashMap();
			marketRows = new HashMap();
			allMarketIds = null;
			curOrgUnitInfo = null;
			this.tblMain.removeRows();
			this.getValidedMarketIds();
			fillNode(this.tblMain, sellProRoot);
			fillFilterDayRate(marketRows);
			fillMarketCommerceData(marketRows);
			fillEffectctPayAmount(marketRows);
			fillEffectctDealArea(marketRows);
			fillMarketEffectPurchaseCount(marketRows);
			int marketRowssize = marketRows.size();
			Iterator marketIter = marketRows.entrySet().iterator();
			for (int i = 0; i < marketRowssize; i++) {
				Map.Entry entry = (Map.Entry) marketIter.next();
				String tempkey = (String) entry.getKey();
				IRow row = (IRow) entry.getValue();
				fillMarketReceiveFactValue(tempkey, row);
				fillMarketReceivePlanValue(tempkey, row);
			}
			setUnionData();
			setChargeRate();
			this.tblMain.getTreeColumn().setDepth(sellProRoot.getDepth() + 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node) throws BOSException, SQLException, EASBizException, Exception {
		IRow row = table.addRow();
		for (int i = 2; i < table.getColumnCount(); i++) {
			row.getCell(i).setValue(FDCHelper.ZERO);
		}
		row.setTreeLevel(node.getLevel());
		int level = node.getLevel();
		String space = "";
		for (int i = 0; i < node.getLevel(); i++) {
			space += " ";
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			// 设置项目统计数据
			SellProjectInfo sellPro = (SellProjectInfo) node.getUserObject();
			row.getCell("sellProjectName").setValue(space + sellPro.getName());
			this.orgProRows.put(curOrgUnitInfo.toString() + sellPro.getId().toString(), row);
			row.getCell("id").setValue(curOrgUnitInfo.toString() + sellPro.getId().toString());
			// 按项目设置活动类型统计数据
			sellProMarketTypeMap = getSellProjectMarketType(sellPro, curOrgUnitInfo);
			int mapsize = sellProMarketTypeMap.size();
			Iterator keyValuePairs1 = sellProMarketTypeMap.entrySet().iterator();
			for (int i = 0; i < mapsize; i++) {
				Map.Entry entry = (Map.Entry) keyValuePairs1.next();
				String tempkey = (String) entry.getKey();
				String tempValue = (String) entry.getValue();
				row = table.addRow();
				row.setTreeLevel(level + 1);
				for (int ii = 2; ii < table.getColumnCount(); ii++) {
					row.getCell(ii).setValue(FDCHelper.ZERO);
				}
				row.getCell("sellProjectName").setValue(space + "  " + tempValue);
				tempkey = sellPro.getId().toString() + tempkey;
				this.orgProMarTypeRows.put(curOrgUnitInfo.toString() + tempkey, row);
				row.getCell("id").setValue(curOrgUnitInfo.toString() + tempkey);
				sellProMarketTypeMap.get(tempkey);
				// 按项目活动类型设置活动统计数据
				sellProMarketMap = getSellProjectMarket(tempkey, curOrgUnitInfo);
				int marketMapSize = sellProMarketMap.size();
				Iterator keyValueMarket = sellProMarketMap.entrySet().iterator();
				for (int j = 0; j < marketMapSize; j++) {
					Map.Entry entryMarket = (Map.Entry) keyValueMarket.next();
					String marketKey = (String) entryMarket.getKey();
					String marketValue = (String) entryMarket.getValue();
					row = table.addRow();
					row.setUserObject("1234");
					for (int ii = 2; ii < table.getColumnCount(); ii++) {
						row.getCell(ii).setValue(FDCHelper.ZERO);
					}
					row.setTreeLevel(level + 2);
					row.getCell("sellProjectName").setValue(space + "    " + marketValue);
					row.getCell("id").setValue(marketKey);
					this.marketRows.put(marketKey, row);
					marketKey = tempkey + marketKey;
					sellProMarketMap.get(marketKey);
				}
			}

		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			// 按组织设置统计数据。
			curOrgUnitInfo = (OrgStructureInfo) node.getUserObject();
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("sellProjectName").setValue(space + node);
			orgRows.put(curOrgUnitInfo.getLongNumber(), row);
			row.getCell("id").setValue(curOrgUnitInfo.getLongNumber());
		}
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node.getChildAt(i));
			}
		}
	}

	private void fillFilterDayRate(Map marketRow) throws BOSException, SQLException, EASBizException, Exception {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		Date beginDate = null;
		Date endDate = null;
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql(" select fid,FPlanTotalMoney,FFactTotalMoney, ");
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll")) {
			beginDate = this.getBeginQueryDate();
			endDate = getDayEnd(this.getEndQueryDate());
			detailBuilder.appendSql(" case when FBeginDate>= ? AND FEndDate<= ?  ");
			detailBuilder.addParam(FDCDateHelper.getSqlDate(beginDate));
			detailBuilder.addParam(FDCDateHelper.getSqlDate(endDate));
			detailBuilder.appendSql("      THEN (abs(DATEDIFF(dd,FBeginDate,FEndDate))+1)/(abs(DATEDIFF(dd,FBeginDate,FEndDate))+1) ");
			detailBuilder.appendSql("      when FBeginDate<= ? AND FEndDate<= ?  ");
			detailBuilder.addParam(FDCDateHelper.getSqlDate(beginDate));
			detailBuilder.addParam(FDCDateHelper.getSqlDate(endDate));
			detailBuilder.appendSql("      THEN (abs(DATEDIFF(dd,?,FEndDate))+1)/(abs(DATEDIFF(dd,FBeginDate,FEndDate))+1) ");
			detailBuilder.addParam(FDCDateHelper.getSqlDate(beginDate));
			detailBuilder.appendSql("      when FBeginDate>= ? AND FEndDate>= ?  ");
			detailBuilder.addParam(FDCDateHelper.getSqlDate(beginDate));
			detailBuilder.addParam(FDCDateHelper.getSqlDate(endDate));
			detailBuilder.appendSql("      THEN (abs(DATEDIFF(dd,FBeginDate,?))+1)/(abs(DATEDIFF(dd,FBeginDate,FEndDate))+1) ");
			detailBuilder.addParam(FDCDateHelper.getSqlDate(endDate));
			detailBuilder.appendSql("      when FBeginDate<= ? AND FEndDate>= ?  ");
			detailBuilder.addParam(FDCDateHelper.getSqlDate(beginDate));
			detailBuilder.addParam(FDCDateHelper.getSqlDate(endDate));
			detailBuilder.appendSql("      THEN (abs(DATEDIFF(dd,?,?))+1)/(abs(DATEDIFF(dd,FBeginDate,FEndDate))+1) ");
			detailBuilder.addParam(FDCDateHelper.getSqlDate(beginDate));
			detailBuilder.addParam(FDCDateHelper.getSqlDate(endDate));
			detailBuilder.appendSql("      else 1 end as filterDayRate  ");
		} else {
			detailBuilder.appendSql("     1 as filterDayRate  ");
		}
		detailBuilder.appendSql(" from T_MAR_MarketManage  ");
		if (allMarketIds != null) {
			detailBuilder.appendSql(" WHERE FID IN (" + allMarketIds + ")");
		}
		IRowSet detailSet = detailBuilder.executeQuery();
		while (detailSet.next()) {
			String marketId = null;
			BigDecimal filterDayRate = FDCHelper.toBigDecimal("1");
			BigDecimal planTotalMoney = FDCHelper.ZERO;
			BigDecimal factTotalMoney = FDCHelper.ZERO;
			if (detailSet.getString("FID") != null) {
				marketId = detailSet.getString("FID");
			}
			if (detailSet.getString("FPlanTotalMoney") != null) {
				planTotalMoney = FDCHelper.toBigDecimal(detailSet.getString("FPlanTotalMoney"));
			}
			if (detailSet.getString("FFactTotalMoney") != null) {
				factTotalMoney = FDCHelper.toBigDecimal(detailSet.getString("FFactTotalMoney"));
			}
			if (detailSet.getString("filterDayRate") != null) {
				filterDayRate = FDCHelper.toBigDecimal(detailSet.getString("filterDayRate"));
			}

			if (marketId != null) {
				IRow row = (IRow) marketRow.get(marketId);
				if (row == null)
					continue;
				planTotalMoney = planTotalMoney.multiply(filterDayRate);
				factTotalMoney = factTotalMoney.multiply(filterDayRate);
				row.getCell("filterDayRate").setValue(filterDayRate);
				row.getCell("planCharge").setValue(planTotalMoney);
				row.getCell("factCharge").setValue(factTotalMoney);

			}
		}
	}

	private void fillMarketCommerceData(Map marketRow) throws Exception {
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql(" select AA.FMarketManageID,count(distinct  AA.FCommerceChanceID) as commerCount   ");
		detailBuilder.appendSql(" from  T_SHE_TrackRecord AA inner JOIN T_MAR_MarketManage BB ON AA.FMarketManageID=BB.FID  ");
		detailBuilder.appendSql(" where  AA.FMarketManageID is not null  AND AA.FSellProjectID=BB.FSELLPROJECT");
		detailBuilder.appendSql(" and AA. FTrackType='TR3' and AA.FCommerceChanceID is not null   ");
		appendUtilDateFilterSql(detailBuilder, "AA.FEventDate");
		if (allMarketIds != null) {
			detailBuilder.appendSql(" AND AA.FMarketManageID IN (" + allMarketIds + ")");
		}
		detailBuilder.appendSql(" group by AA.FMarketManageID ");
		IRowSet detailSet = detailBuilder.executeQuery();
		while (detailSet.next()) {
			String marketId = null;
			BigDecimal commerceCount = FDCHelper.ZERO;
			if (detailSet.getString("FMarketManageID") != null) {
				marketId = detailSet.getString("FMarketManageID");
			}
			if (detailSet.getString("commerCount") != null) {
				commerceCount = FDCHelper.toBigDecimal(detailSet.getString("commerCount"));
			}
			if (marketId != null) {
				IRow row = (IRow) marketRow.get(marketId);
				if (row == null)
					continue;
				row.getCell("factConstactCommerce").setValue(commerceCount);
			}
		}
	}

	private void setChargeRate() throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row == null)
				return;
			BigDecimal dealAmount = FDCHelper.ZERO;
			dealAmount = FDCHelper.toBigDecimal(row.getCell("contractAmount").getValue());
			BigDecimal factChargeAmount = FDCHelper.ZERO;
			factChargeAmount = FDCHelper.toBigDecimal(row.getCell("factCharge").getValue());
			if (FDCHelper.ZERO.compareTo(factChargeAmount) != 0 && FDCHelper.ZERO.compareTo(dealAmount) != 0) {
				dealAmount = dealAmount.divide(factChargeAmount, 4, BigDecimal.ROUND_HALF_UP);
				row.getCell("chargeRate").setValue(dealAmount);
			} else {
				row.getCell("chargeRate").setValue(FDCHelper.ZERO);
			}
		}
	}

	/**
	 * 汇总楼栋的数据到分区，项目，组织上
	 * */
	public void setUnionData() throws Exception {
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < tblMain.getRowCount(); j++) {
					IRow rowAfter = tblMain.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int j = 2; j < this.tblMain.getColumnCount(); j++) {
					BigDecimal aimCost = FMConstants.ZERO;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(j).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								aimCost = aimCost.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								aimCost = aimCost.add(new BigDecimal(((Integer) value).toString()));
							}
						}
					}
					row.getCell(j).setValue(aimCost);
					row.getCell("chargeRate").setValue(null);
				}
			}
		}
	}

	private void fillMarketReceivePlanValue(String marketId, IRow row) throws Exception {
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql("  select MarketManageEffect.FPlanValue as FPlanValue    ");
		detailBuilder.appendSql("  from T_SHE_ReceptionType ReceptionType left join  ");
		detailBuilder.appendSql("  T_MAR_MarketManageEffect MarketManageEffect ON     ");
		detailBuilder.appendSql("  ReceptionType.FName_l2= MarketManageEffect.FPredictName  ");
		detailBuilder.appendSql("  where MarketManageEffect.FParentID IS NOT NULL ");
		detailBuilder.appendSql("  AND MarketManageEffect.FParentID= '" + marketId + "'   ");
		detailBuilder.appendSql("  order by ReceptionType.FNumber ");
		IRowSet detailSet = detailBuilder.executeQuery();
		for (int i = 0; i < receiveType.length; i++) {
			row.getCell("receptTypePlanCountSum" + i).setValue(FDCHelper.ZERO);
		}
		int m = 0;
		while (detailSet.next()) {
			BigDecimal planValue = FDCHelper.ZERO;
			if (detailSet.getString("FPlanValue") != null && detailSet.getString("FPlanValue") != "") {
				planValue = FDCHelper.toBigDecimal(detailSet.getString("FPlanValue"));
				BigDecimal dayRate = FDCHelper.ZERO;
				dayRate = FDCHelper.toBigDecimal(row.getCell("filterDayRate").getValue());
				planValue = planValue.multiply(dayRate);
			}
			row.getCell("receptTypePlanCountSum" + m).setValue(planValue);
			m++;
		}
	}

	private void fillMarketReceiveFactValue(String marketId, IRow row) throws Exception {
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql("  select AA.FFactValue AS FFactValue  ");
		detailBuilder.appendSql("   from T_SHE_ReceptionType ReceptionType   LEFT JOIN  ");
		detailBuilder.appendSql("  (select FReceptionTypeID,count(FID) as FFactValue ");
		detailBuilder.appendSql("  from T_SHE_TrackRecord ");
		detailBuilder.appendSql("  where FMarketManageID is not null");
		detailBuilder.appendSql(" AND FMarketManageID= '" + marketId + "'   ");
		appendUtilDateFilterSql(detailBuilder, "FEventDate");
		detailBuilder.appendSql("  group by FReceptionTypeID) AA  ON ");
		detailBuilder.appendSql("  ReceptionType.FID=AA.FReceptionTypeID");
		detailBuilder.appendSql("  order by ReceptionType.FNumber");
		IRowSet detailSet = detailBuilder.executeQuery();
		for (int i = 0; i < receiveType.length; i++) {
			row.getCell("receptTypeFactCountSum" + i).setValue(FDCHelper.ZERO);
		}
		int i = 0;
		while (detailSet.next()) {
			BigDecimal factValue = FDCHelper.ZERO;
			if (detailSet.getString("FFactValue") != null && detailSet.getString("FFactValue") != "") {
				factValue = FDCHelper.toBigDecimal(detailSet.getString("FFactValue"));
			}
			row.getCell("receptTypeFactCountSum" + i).setValue(factValue);
			i++;
		}
	}

	private void fillMarketEffectPurchaseCount(Map marketRow) throws Exception {
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql("  select TrackRecord.FMarketManageID, ");
		detailBuilder.appendSql("  count(distinct Purchase.FID) as purchaseCount, ");
		detailBuilder.appendSql("  sum(ISNULL(Purchase.FDealAmount,0)) as dealAmount ");
		detailBuilder.appendSql("  from T_SHE_TrackRecord  TrackRecord ");
		detailBuilder.appendSql("  inner JOIN T_MAR_MarketManage MarketManage ON TrackRecord.FMarketManageID=MarketManage.FID ");
		detailBuilder.appendSql("  left join T_SHE_Purchase Purchase on  ");
		detailBuilder.appendSql("  TrackRecord.FRelationContract=Purchase.FID  ");
		detailBuilder.appendSql("  AND TrackRecord.FTrackResult='S30'      ");
		detailBuilder.appendSql("  where TrackRecord.FMarketManageID is not null  ");
		detailBuilder.appendSql("  AND TrackRecord.FSellProjectID=MarketManage.FSELLPROJECT  ");
		appendUtilDateFilterSql(detailBuilder, "TrackRecord.FEventDate");
		if (allMarketIds != null) {
			detailBuilder.appendSql(" AND TrackRecord.FMarketManageID IN (" + allMarketIds + ")");
		}
		detailBuilder.appendSql(" group by TrackRecord.FMarketManageID ");
		IRowSet detailSet = detailBuilder.executeQuery();
		while (detailSet.next()) {
			String marketId = null;
			BigDecimal purchaseCount = FDCHelper.ZERO;
			BigDecimal dealAmount = FDCHelper.ZERO;
			if (detailSet.getString("FMarketManageID") != null) {
				marketId = detailSet.getString("FMarketManageID");
			}
			if (detailSet.getString("purchaseCount") != null) {
				purchaseCount = FDCHelper.toBigDecimal(detailSet.getString("purchaseCount"));
			}
			if (detailSet.getString("dealAmount") != null) {
				dealAmount = FDCHelper.toBigDecimal(detailSet.getString("dealAmount"));
			}
			if (marketId != null) {
				IRow row = (IRow) marketRow.get(marketId);
				if (row == null)
					continue;
				row.getCell("signContractCount").setValue(purchaseCount);
				row.getCell("contractAmount").setValue(dealAmount);
			}
		}
	}

	private void fillEffectctDealArea(Map marketRow) throws Exception {
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql("  select TrackRecord.FMarketManageID, ");
		detailBuilder.appendSql("  sum(ISNULL(room.FBuildingArea,0)) as dealArea   ");
		detailBuilder.appendSql("  from T_SHE_TrackRecord  TrackRecord ");
		detailBuilder.appendSql("  inner JOIN T_MAR_MarketManage MarketManage ON TrackRecord.FMarketManageID=MarketManage.FID ");
		detailBuilder.appendSql("  left join T_SHE_Purchase Purchase on  ");
		detailBuilder.appendSql("            TrackRecord.FRelationContract=Purchase.FID  ");
		detailBuilder.appendSql("        AND TrackRecord.FTrackResult='S30'      ");
		detailBuilder.appendSql("  left join T_SHE_Room Room on  ");
		detailBuilder.appendSql("       Purchase.FRoomID = Room.FId  ");
		detailBuilder.appendSql("  where TrackRecord.FMarketManageID is not null  ");
		detailBuilder.appendSql("  AND TrackRecord.FSellProjectID=MarketManage.FSELLPROJECT  ");
		appendUtilDateFilterSql(detailBuilder, "TrackRecord.FEventDate");
		if (allMarketIds != null) {
			detailBuilder.appendSql(" AND TrackRecord.FMarketManageID IN (" + allMarketIds + ")");
		}
		detailBuilder.appendSql(" group by TrackRecord.FMarketManageID ");
		IRowSet detailSet = detailBuilder.executeQuery();
		while (detailSet.next()) {
			String marketId = null;
			BigDecimal dealArea = FDCHelper.ZERO;
			if (detailSet.getString("FMarketManageID") != null) {
				marketId = detailSet.getString("FMarketManageID");
			}
			if (detailSet.getString("dealArea") != null) {
				dealArea = FDCHelper.toBigDecimal(detailSet.getString("dealArea"));
			}
			if (marketId != null) {
				IRow row = (IRow) marketRow.get(marketId);
				if (row == null)
					continue;
				row.getCell("dealArea").setValue(dealArea);
			}
		}
	}

	private void fillEffectctPayAmount(Map marketRow) throws Exception {
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql("  select TrackRecord.FMarketManageID, ");
		detailBuilder.appendSql("  sum(ISNULL(payListEntry.FActPayAmount,0)) as actPayAmount ");
		detailBuilder.appendSql("  from T_SHE_TrackRecord  TrackRecord ");
		detailBuilder.appendSql("  inner JOIN T_MAR_MarketManage MarketManage ON TrackRecord.FMarketManageID=MarketManage.FID ");
		detailBuilder.appendSql("  left join T_SHE_Purchase Purchase on  ");
		detailBuilder.appendSql("            TrackRecord.FRelationContract=Purchase.FID  ");
		detailBuilder.appendSql("        AND TrackRecord.FTrackResult='S30'      ");
		detailBuilder.appendSql("  left join T_SHE_PurchasePayListEntry payListEntry on  ");
		detailBuilder.appendSql("       payListEntry.FHeadID = Purchase.FID   ");
		detailBuilder.appendSql("  where TrackRecord.FMarketManageID is not null  ");
		detailBuilder.appendSql("  AND TrackRecord.FSellProjectID=MarketManage.FSELLPROJECT  ");
		appendUtilDateFilterSql(detailBuilder, "TrackRecord.FEventDate");
		if (allMarketIds != null) {
			detailBuilder.appendSql(" AND TrackRecord.FMarketManageID IN (" + allMarketIds + ")");
		}
		detailBuilder.appendSql(" group by TrackRecord.FMarketManageID ");
		IRowSet detailSet = detailBuilder.executeQuery();
		while (detailSet.next()) {
			String marketId = null;
			BigDecimal actPayAmount = FDCHelper.ZERO;
			if (detailSet.getString("FMarketManageID") != null) {
				marketId = detailSet.getString("FMarketManageID");
			}
			if (detailSet.getString("actPayAmount") != null) {
				actPayAmount = FDCHelper.toBigDecimal(detailSet.getString("actPayAmount"));
			}
			if (marketId != null) {
				IRow row = (IRow) marketRow.get(marketId);
				if (row == null)
					continue;
				row.getCell("actPaidAmount").setValue(actPayAmount);

			}
		}
	}

	public static java.util.Date getDayEnd(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public void onShow() throws Exception {
		super.onShow();
		int indexNum = this.tblMain.getColumn("sellProjectName").getColumnIndex();
		this.tblMain.getViewManager().setFreezeView(-1, indexNum + 1);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		FDCClientHelper.setActionVisible(hideAction, false);
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);

	}

	private void getReceptTypeString() throws BOSException, SQLException, Exception {
		receiveType = null;
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql(" select FID,FName_l2 as FName from T_SHE_ReceptionType order by  FNumber    ");
		IRowSet detailSet = detailBuilder.executeQuery();
		receiveType = new String[detailSet.size()][14];
		int tableIndex = 0;
		while (detailSet.next()) {
			if (detailSet.getString("FID") != null) {
				receiveType[tableIndex][0] = detailSet.getString("FID");
			}
			if (detailSet.getString("FName") != null) {
				receiveType[tableIndex][1] = detailSet.getString("FName");
			}
			tableIndex++;
		}
	}

	// 获取销售项目编号和对应的营销活动类型编号映射关系
	private HashMap getSellProjectMarketType(SellProjectInfo sellProject, OrgStructureInfo orgInfo) throws BOSException, SQLException, Exception {
		HashMap sellProMarketType = new HashMap();
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql(" SELECT DISTINCT   ");
		detailBuilder.appendSql(" MarketType.FID AS marketTypeId,   ");
		detailBuilder.appendSql(" MarketType.FName_l2 AS marketTypeName  ");
		detailBuilder.appendSql(" FROM T_MAR_MarketManage MarketManage INNER JOIN  T_MAR_MarketType MarketType   ");
		detailBuilder.appendSql(" ON MarketManage.FMarkettypeID=MarketType.FID   ");
		detailBuilder.appendSql(" WHERE  MarketManage.FSellProject='" + sellProject.getId().toString() + "' ");
		detailBuilder.appendSql(" AND   MarketManage.FOrgName LIKE '%" + orgInfo.toString() + "'");
		if (allMarketIds != null) {
			detailBuilder.appendSql(" AND MarketManage.FID IN (" + allMarketIds + ")");
		}
		IRowSet detailSet = detailBuilder.executeQuery();
		while (detailSet.next()) {
			if (detailSet.getString("marketTypeId") != null && detailSet.getString("marketTypeName") != null) {
				sellProMarketType.put(detailSet.getString("marketTypeId"), detailSet.getString("marketTypeName"));
			}
		}
		return sellProMarketType;
	}

	// 获取销售项目编号和对应的营销活动映射关系
	private HashMap getSellProjectMarket(String sellProMarketTypeId, OrgStructureInfo orgInfo) throws BOSException, SQLException, Exception {
		HashMap sellProMarket = new HashMap();
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql(" SELECT DISTINCT  FID, FName ");
		detailBuilder.appendSql(" FROM T_MAR_MarketManage ");
		detailBuilder.appendSql(" WHERE  FSellProject||FMarkettypeID='" + sellProMarketTypeId + "' ");
		detailBuilder.appendSql(" AND   FOrgName LIKE '%" + orgInfo.toString() + "'");
		if (allMarketIds != null) {
			detailBuilder.appendSql(" AND FID IN (" + allMarketIds + ")");
		}
		IRowSet detailSet = detailBuilder.executeQuery();
		while (detailSet.next()) {
			if (detailSet.getString("FID") != null && detailSet.getString("FName") != null) {
				sellProMarket.put(detailSet.getString("FID"), detailSet.getString("FName"));
			}
		}
		return sellProMarket;
	}

	private void initTableHead() throws BOSException, SQLException, Exception {
		getReceptTypeString();
		this.tblMain.removeRows();
		this.tblMain.removeHeadRows();
		this.tblMain.removeColumns();
		IRow headRow0 = this.tblMain.addHeadRow();
		IRow headRow1 = this.tblMain.addHeadRow();
		IColumn columnSellProjectId = this.tblMain.addColumn();
		columnSellProjectId.setKey("id");
		headRow0.getCell("id").setValue("ID");
		headRow1.getCell("id").setValue("ID");
		this.tblMain.getHeadMergeManager().mergeBlock(0, columnSellProjectId.getColumnIndex(), 1, columnSellProjectId.getColumnIndex());
		columnSellProjectId.getStyleAttributes().setHided(true);

		IColumn columnSellProject = this.tblMain.addColumn();
		columnSellProject.setKey("sellProjectName");
		headRow0.getCell("sellProjectName").setValue("名称");
		headRow1.getCell("sellProjectName").setValue("名称");
		columnSellProject.setWidth(200);

		this.tblMain.getHeadMergeManager().mergeBlock(0, columnSellProject.getColumnIndex(), 1, columnSellProject.getColumnIndex());
		if (receiveType != null) { // 构建表头
			for (int i = 0; i < receiveType.length; i++) {
				IColumn columnTrack = this.tblMain.addColumn();
				columnTrack.setKey("receptTypePlanCountSum" + i);
				headRow0.getCell("receptTypePlanCountSum" + i).setValue("接待方式预计数");
				headRow1.getCell("receptTypePlanCountSum" + i).setValue(receiveType[i][1]);
				this.tblMain.getColumn("receptTypePlanCountSum" + i).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
				this.tblMain.getColumn("receptTypePlanCountSum" + i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
			int left = this.tblMain.getColumnIndex("receptTypePlanCountSum0");
			int right = this.tblMain.getColumnIndex("receptTypePlanCountSum" + (receiveType.length - 1));
			this.tblMain.getHeadMergeManager().mergeBlock(0, left, 0, right);
			for (int i = 0; i < receiveType.length; i++) {
				IColumn columnTrack = this.tblMain.addColumn();
				columnTrack.setKey("receptTypeFactCountSum" + i);
				headRow0.getCell("receptTypeFactCountSum" + i).setValue("接待方式实际数");
				headRow1.getCell("receptTypeFactCountSum" + i).setValue(receiveType[i][1]);
				this.tblMain.getColumn("receptTypeFactCountSum" + i).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
				this.tblMain.getColumn("receptTypeFactCountSum" + i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
			left = this.tblMain.getColumnIndex("receptTypeFactCountSum0");
			right = this.tblMain.getColumnIndex("receptTypeFactCountSum" + (receiveType.length - 1));
			this.tblMain.getHeadMergeManager().mergeBlock(0, left, 0, right);
		}

		IColumn columnTrack = this.tblMain.addColumn();
		columnTrack.setKey("factConstactCommerce");
		headRow0.getCell("factConstactCommerce").setValue("实际关联商机");
		headRow1.getCell("factConstactCommerce").setValue("实际关联商机");
		this.tblMain.getHeadMergeManager().mergeBlock(0, this.tblMain.getColumnIndex("factConstactCommerce"), 1, this.tblMain.getColumnIndex("factConstactCommerce"));
		this.tblMain.getColumn("factConstactCommerce").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
		this.tblMain.getColumn("factConstactCommerce").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		columnTrack = this.tblMain.addColumn();
		columnTrack.setWidth(150);
		columnTrack.setKey("planCharge");
		headRow0.getCell("planCharge").setValue("预计费用");
		headRow1.getCell("planCharge").setValue("预计费用");
		this.tblMain.getHeadMergeManager().mergeBlock(0, this.tblMain.getColumnIndex("planCharge"), 1, this.tblMain.getColumnIndex("planCharge"));
		this.tblMain.getColumn("planCharge").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("planCharge").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		columnTrack = this.tblMain.addColumn();
		columnTrack.setWidth(150);
		columnTrack.setKey("factCharge");
		headRow0.getCell("factCharge").setValue("实际费用");
		headRow1.getCell("factCharge").setValue("实际费用");
		this.tblMain.getHeadMergeManager().mergeBlock(0, this.tblMain.getColumnIndex("factCharge"), 1, this.tblMain.getColumnIndex("factCharge"));
		this.tblMain.getColumn("factCharge").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("factCharge").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		columnTrack = this.tblMain.addColumn();
		columnTrack.setKey("signContractCount");
		headRow0.getCell("signContractCount").setValue("推广效果");
		headRow1.getCell("signContractCount").setValue("成交套数");
		this.tblMain.getColumn("signContractCount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
		this.tblMain.getColumn("signContractCount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		columnTrack = this.tblMain.addColumn();
		columnTrack.setKey("dealArea");
		headRow0.getCell("dealArea").setValue("推广效果");
		headRow1.getCell("dealArea").setValue("成交面积");
		this.tblMain.getColumn("dealArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("dealArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		columnTrack = this.tblMain.addColumn();
		columnTrack.setKey("contractAmount");
		headRow0.getCell("contractAmount").setValue("推广效果");
		headRow1.getCell("contractAmount").setValue("合同金额");
		this.tblMain.getColumn("contractAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("contractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		columnTrack = this.tblMain.addColumn();
		columnTrack.setKey("actPaidAmount");
		headRow0.getCell("actPaidAmount").setValue("推广效果");
		headRow1.getCell("actPaidAmount").setValue("收款金额");
		this.tblMain.getColumn("actPaidAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("actPaidAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		columnTrack = this.tblMain.addColumn();
		columnTrack.setKey("chargeRate");
		headRow0.getCell("chargeRate").setValue("推广效果");
		headRow1.getCell("chargeRate").setValue("费效比例");
		this.tblMain.getHeadMergeManager().mergeBlock(0, this.tblMain.getColumnIndex("signContractCount"), 0, this.tblMain.getColumnIndex("chargeRate"));
		this.tblMain.getColumn("chargeRate").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(4));
		this.tblMain.getColumn("chargeRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		columnTrack = this.tblMain.addColumn();
		columnTrack.setKey("filterDayRate");
		headRow0.getCell("filterDayRate").setValue("日期比率");
		headRow1.getCell("filterDayRate").setValue("日期比率");
		this.tblMain.getHeadMergeManager().mergeBlock(0, this.tblMain.getColumnIndex("filterDayRate"), 1, this.tblMain.getColumnIndex("filterDayRate"));
		this.tblMain.getColumn("filterDayRate").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("filterDayRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		columnTrack.getStyleAttributes().setHided(true);
		this.tblMain.checkParsed();

	}

	/**
	 * output class constructor
	 */
	public ChargeEffectAnalyseRptUI() throws Exception {
		super();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected boolean initDefaultFilter() {
		return true;
	}

	protected boolean isAllowDefaultSolutionNull() {
		return false;
	}

	protected void initTableParams() {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	private ChargeEffectAnalyseFilterUI getFilterUI() throws Exception {
		if (this.filterUI == null) {
			this.filterUI = new ChargeEffectAnalyseFilterUI(this, this.actionOnLoad);
		}
		return this.filterUI;
	}

	/**
	 * 添加时间过滤
	 * 
	 * @param builder
	 * @param proName
	 * @throws Exception
	 */
	private void appendDateFilterSql(FDCSQLBuilder builder, String proName) throws Exception {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll")) {
			Date beginDate = this.getBeginQueryDate();
			if (beginDate != null) {
				builder.appendSql(" and " + proName + " >= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			Date endDate = this.getEndQueryDate();
			if (endDate != null) {
				builder.appendSql(" and " + proName + " < ? ");
				builder.addParam(FDCDateHelper.getSqlDate(endDate));
			}
		}
	}

	private void appendUtilDateFilterSql(FDCSQLBuilder builder, String proName) throws Exception {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll")) {
			Date endDate = this.getEndQueryDate();
			if (endDate != null) {
				builder.appendSql(" and " + proName + " < ? ");
				builder.addParam(FDCDateHelper.getSqlDate(FDCHelper.getNextDay(endDate)));
			}
		}
	}

	/**
	 * 添加时间过滤
	 * 
	 * @param builder
	 * @param proName
	 * @throws Exception
	 */
	private void getValidedMarketIds() throws Exception {
		allMarketIds = null;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll")) {
			builder.appendSql("select distinct FID from T_MAR_MarketManage   ");
			Date beginDate = this.getBeginQueryDate();
			Date endDate = getDayEnd(this.getEndQueryDate());
			if (beginDate != null && endDate != null) {
				builder.appendSql("  WHERE (FBeginDate>= ? and FBeginDate<= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
				builder.addParam(FDCDateHelper.getSqlDate(endDate));
				builder.appendSql("         and FEndDate  >= ? and FEndDate  <= ?) ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
				builder.addParam(FDCDateHelper.getSqlDate(endDate));

				builder.appendSql("   OR (FBeginDate< ?   ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
				builder.appendSql("         and FEndDate  >= ? and FEndDate  <= ?)   ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
				builder.addParam(FDCDateHelper.getSqlDate(endDate));

				builder.appendSql("   OR (FBeginDate>=?   ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
				builder.appendSql("         and FEndDate  >= ? and FBeginDate  <= ?) ");
				builder.addParam(FDCDateHelper.getSqlDate(endDate));
				builder.addParam(FDCDateHelper.getSqlDate(endDate));

				builder.appendSql("   OR (FBeginDate<?   ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
				builder.appendSql("         and FEndDate  >?) ");
				builder.addParam(FDCDateHelper.getSqlDate(endDate));
			}
			IRowSet tableSet = builder.executeQuery();
			while (tableSet.next()) {
				if (tableSet.getString("FID") != null && tableSet.getString("FID") != "") {
					if (allMarketIds != null) {
						allMarketIds = allMarketIds + ",'" + tableSet.getString("FID") + "'";
					} else {
						allMarketIds = "'" + tableSet.getString("FID") + "'";
					}
				}
			}
		}
	}

	private Date getBeginQueryDate() throws Exception {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return this.getFilterUI().getBeginQueryDate(para);
	}

	private Date getEndQueryDate() throws Exception {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return this.getFilterUI().getEndQueryDate(para);
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		return;
	}
	public int getRowCountFromDB(){
		return -1;
	}

}