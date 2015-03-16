package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.aimcost.DyProductTypeGetter;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fm.common.FMConstants;

public class DynamicProductTableFill {
	public static final String DYNAMIC = "Dynamic";

	public static final String DYNAMIC_SELL = "DynamicSell";

	public static final String NO_HAPPEN = "NoHappen";

	public static final String HAS_HAPPEN = "HasHappen";

	public static final String AIM = "Aim";

	public static final String AIM_SELL = "AimSell";

	public static final String APPORTION_TYPE = "apportionType";

	public KDTable table = new KDTable();

	private DyCostSplitDataGetter dyGetter;

	private AimCostSplitDataGetter aimGetter;

	private HappenDataGetter happenGetter;

	private DyProductTypeGetter productTypeGetter;

	private String objectId;

	public DynamicProductTableFill(String objectId) {
		this.objectId = objectId;
		try {
			this.fillTable();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

	public static KDTable getTable(String objectId) {
		DynamicProductTableFill tableFill = new DynamicProductTableFill(
				objectId);
		return tableFill.table;
	}

	public void fillTable() throws Exception {
		this.productTypeGetter = new DyProductTypeGetter(objectId);
		
		IColumn col = table.addColumn();
		col.setKey("acctId");
		col=table.addColumn();
		col.setKey("amount");
		col=table.addColumn();
		col.setKey(APPORTION_TYPE);
		Map prodcutMap = this.productTypeGetter.getSortedProductMap();
		String[] resName = new String[] { AIM_SELL, AIM, HAS_HAPPEN,
				NO_HAPPEN, DYNAMIC_SELL, DYNAMIC };
		Set keySet = prodcutMap.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String number = (String) iter.next();
			ProductTypeInfo product = (ProductTypeInfo) prodcutMap.get(number);
			for (int j = 0; j < 6; j++) {
				String key = product.getId().toString() + resName[j];
				col = table.addColumn();
				col.setKey(key);
			}
		}
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		FilterInfo acctFilter = new FilterInfo();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("curProject.id", objectId));
		} else {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", objectId));
		}
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		TreeModel costAcctTree = FDCClientHelper.createDataTree(
				CostAccountFactory.getRemoteInstance(), acctFilter);
		this.aimGetter = new AimCostSplitDataGetter(objectId);
		this.happenGetter = new HappenDataGetter(objectId, false, false,true);
		this.dyGetter = new DyCostSplitDataGetter(objectId, aimGetter,
				happenGetter);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
				.getRoot();
		for (int k = 0; k < root.getChildCount(); k++) {
			fillNode(table, (DefaultMutableTreeNode) root.getChildAt(k));
		}
		setUnionData();
	}

	public void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
//		String longNumber = costAcct.getLongNumber();
//		longNumber = longNumber.replace('!', '.');
		row.getCell("acctId").setValue(costAcct.getId().toString());
		if (node.isLeaf()) {
			row.setUserObject(costAcct);
			BigDecimal adjustAmount = null;
			BigDecimal dynamicAmount = null;
			DynamicCostInfo dynamic = this.dyGetter.getDynamicInfo(costAcct
					.getId().toString());
			// 动态成本跟节点要求汇总
			if (dynamic != null) {
				adjustAmount = dynamic.getAdjustSumAmount();
			}
			BigDecimal aimAmount = this.aimGetter.getAimCost(costAcct.getId()
					.toString());
			if (adjustAmount == null) {
				adjustAmount = FDCHelper.ZERO;
			}
			dynamicAmount = aimAmount.add(adjustAmount);
			if (dynamicAmount.compareTo(FDCHelper.ZERO) != 0) {
				row.getCell("amount").setValue(dynamicAmount);
			}
			fillAimData(row);
			DynamicCostProductSplitEntryCollection splits = getRowSetting(row);
			calculateRowData(row, splits);
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
		}
	}

	private void calculateRowData(IRow entryRow,
			DynamicCostProductSplitEntryCollection splits) throws BOSException {
		// 没有拆分方案
		if (splits.size() <= 0) {
			return;
		}
		DynamicCostProductSplitEntryInfo firstSplit = splits.get(0);
		ApportionTypeInfo apportionType = firstSplit.getApportionType();
		// 有分摊方案条目，但分摊类型为空，表示没有分摊过，只是有指定分摊
		if (apportionType != null) {
			entryRow.getCell(APPORTION_TYPE).setValue(apportionType.getId().toString());
		}
		CostAccountInfo acct = (CostAccountInfo) entryRow.getUserObject();
		Map dyCalculateData = this.dyGetter.getDyProductData(acct.getId()
				.toString(), splits);
		Set keySet = dyCalculateData.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String prodId = (String) iter.next();
			BigDecimal finalAmount = (BigDecimal) dyCalculateData.get(prodId);
			entryRow.getCell(prodId + DYNAMIC).setValue(finalAmount);
		}
		// 设置可售面积列
		setSellAmountbySplit(entryRow, dyCalculateData, false);
		Map happenCalculateData = this.dyGetter.getHasHappenProductData(acct
				.getId().toString(), splits);
		keySet = happenCalculateData.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String prodId = (String) iter.next();
			BigDecimal finalAmount = (BigDecimal) happenCalculateData
					.get(prodId);
			entryRow.getCell(prodId + HAS_HAPPEN).setValue(finalAmount);
			BigDecimal dyAmount = (BigDecimal) dyCalculateData.get(prodId);
			entryRow.getCell(prodId + NO_HAPPEN).setValue(
					dyAmount.subtract(finalAmount));
		}
	}

	private DynamicCostProductSplitEntryCollection getRowSetting(IRow entryRow) throws BOSException {
		CostAccountInfo acct = (CostAccountInfo) entryRow.getUserObject();
		String acctId = acct.getId().toString();
		Map aimProductMap = this.aimGetter.getProductMap(acctId);
		DynamicCostInfo dynamic = this.dyGetter.getDynamicInfo(acct.getId()
				.toString());
		DynamicCostProductSplitEntryCollection cashe = new DynamicCostProductSplitEntryCollection();
		AdjustRecordEntryCollection adjustEntrys = new AdjustRecordEntryCollection();
		if (dynamic != null) {
			cashe = this.dyGetter.getProductSplitEntry(dynamic.getId()
					.toString());
			adjustEntrys = dynamic.getAdjustEntrys();
		}
		HappenDataInfo happen = this.happenGetter.getHappenInfo(acctId);
		Map hasHappenMap = new HashMap();
		if (happen != null && happen.getProductAmountMap() != null) {
			hasHappenMap = happen.getProductAmountMap();
		}
		Map adjustMap = this.dyGetter.getAdjustDirectMap(adjustEntrys);
		DynamicCostProductSplitEntryCollection splits = this.dyGetter
				.createSetting(cashe, aimProductMap, adjustMap, hasHappenMap);
		return splits;
	}

	private void fillAimData(IRow row) throws BOSException, SQLException {
		String acctId = ((CostAccountInfo) row.getUserObject()).getId()
				.toString();

		if (this.productTypeGetter.getProductTypeSize() > 0) {
			Map productAim = this.aimGetter.getProductMap(acctId);

			Set keySet = productAim.keySet();
			for (Iterator iter = keySet.iterator(); iter.hasNext();) {
				String productId = (String) iter.next();
				BigDecimal value = (BigDecimal) productAim.get(productId);
				if (row.getCell(productId + AIM) != null) {
					row.getCell(productId + AIM).setValue(value);
				}
			}
			this.setSellAmountbySplit(row, productAim, true);

		}
	}

	private void setSellAmountbySplit(IRow entryRow, Map calculateData,
			boolean isAimCost) throws BOSException {
		Set keySet = calculateData.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			String apportionId = ApportionTypeInfo.sellAreaType;
			BigDecimal area =   this.productTypeGetter.getProductApprotion(
					productId, apportionId);
			String colName = "";
			if (isAimCost) {
				colName = AIM;
			} else {
				colName = DYNAMIC;
			}
			BigDecimal value = null;
			if (entryRow.getCell(productId + colName) != null) {
				value = (BigDecimal) entryRow.getCell(productId + colName)
						.getValue();
			}
			if (value != null && area != null
					&& area.compareTo(FDCHelper.ZERO) != 0) {
				entryRow.getCell(productId + colName + "Sell").setValue(
						value.divide(area, 2, BigDecimal.ROUND_HALF_UP));
			}

		}
	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() {
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		amountColumns.add("amount");
		String[] productTypeIds = this.productTypeGetter.getProductTypeIds();
		for (int i = 0; i < productTypeIds.length; i++) {
			String productTypeId = productTypeIds[i];
			amountColumns.add(productTypeId + AIM_SELL);
			amountColumns.add(productTypeId + AIM);
			amountColumns.add(productTypeId + HAS_HAPPEN);
			amountColumns.add(productTypeId + NO_HAPPEN);
			amountColumns.add(productTypeId + DYNAMIC_SELL);
			amountColumns.add(productTypeId + DYNAMIC);
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FMConstants.ZERO;
					boolean isRed = false;
					boolean hasData = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null
								&& rowAdd.getCell(colName).getStyleAttributes()
										.getFontColor().equals(Color.RED)) {
							isRed = true;
						}
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(
										((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if (isRed) {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.RED);
					} else {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.BLACK);
					}
					if (hasData) {
						row.getCell(colName).setValue(amount);
					} else {
						row.getCell(colName).setValue(null);
					}
				}
			}
		}
	}

}
