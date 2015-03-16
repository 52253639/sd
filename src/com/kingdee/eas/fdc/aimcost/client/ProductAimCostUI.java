/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.AimProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.ProjectCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProductAimCostUI extends AbstractProductAimCostUI {

	private CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();

	private AimCostSplitDataGetter aimGetter;

	private AimProductTypeGetter productTypeGetter;
	
	private RetValue retValueNotLeaf;
	
	// 数据对象变化时，刷新界面状态
	protected void setActionState() {
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	/**
	 * 屏蔽回车键
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {

	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		setApporAction();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
				tblMain.getViewManager().freeze(0, acctNameIndex);
		}});
		final Object obj = getUIContext().get("MainMenuName");
		if (!FDCHelper.isEmpty(obj)) {
			setUITitle(obj.toString());
		}
	}

	private void initControl() {
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionLocate.setEnabled(false);
	}

	private void setRowColor(IRow row, Color color) {
		List amountColumns = new ArrayList();
		amountColumns.add("amount");
		String[] productTypeIds = this.productTypeGetter.getProductTypeIds();
		for (int i = 0; i < productTypeIds.length; i++) {
			String productTypeId = productTypeIds[i];
			amountColumns.add(productTypeId + "sell");
			amountColumns.add(productTypeId + "all");
		}
		for (int k = 0; k < amountColumns.size(); k++) {
			String colName = (String) amountColumns.get(k);
			row.getCell(colName).getStyleAttributes().setFontColor(color);
		}
	}
	/**
	 * 描述：设置父科目汇总数<p>
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionDataNotLeaf() throws BOSException{
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		amountColumns.add("amount");
		ProductTypeCollection prodcutMap = (ProductTypeCollection)this.retValueNotLeaf.get("ProductTypeCollection");
		RetValue productSellAreaValue = (RetValue)retValueNotLeaf.get("ProductSellAreaValue");
		String projectStage = ProjectStageEnum.DYNCOST_VALUE;
		Map productApproMap = new HashMap();
		for (Iterator iter = prodcutMap.iterator(); iter.hasNext();) {
			ProductTypeInfo product = (ProductTypeInfo) iter.next();
			String productTypeId = product.getId().toString();
			/**
			 * 描述:加入顺序调整先all再sell，保障循环时先计算总成本汇总再根据汇总后的总成本计算可售单方
			 * 可售单方=总成本/可售单方指标	 
			 */
			amountColumns.add(productTypeId + "all");
			amountColumns.add(productTypeId + "sell");
			BigDecimal apporValue = FDCHelper.toBigDecimal(productSellAreaValue.getBigDecimal(productTypeId+projectStage));
			productApproMap.put(productTypeId+"sell", apporValue);
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
					boolean isSellColumn = false;
					if(colName.endsWith("sell")){
						BigDecimal apporValue = FDCHelper.toBigDecimal(productApproMap.get(colName));
						String key = colName.substring(0,colName.length()-4)+"all";
						Object allCost = row.getCell(key).getValue();
						if(allCost != null && FDCHelper.toBigDecimal(apporValue).compareTo(FDCHelper.ZERO) != 0){
							amount = FDCHelper.toBigDecimal(allCost).divide(apporValue, 2, BigDecimal.ROUND_HALF_UP);
							hasData = true;
						}
						isSellColumn = true;
					}
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null
								&& rowAdd.getCell(colName).getStyleAttributes()
										.getFontColor().equals(Color.RED)) {
							isRed = true;
						}
						if (value != null && !isSellColumn) {
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
		if(amountColumns.size()>0){
			String[] columns=new String[amountColumns.size()];
			amountColumns.toArray(columns);
			try{
				AimCostClientHelper.setTotalCostRow(table, columns);
			}catch(Exception e){
				handUIException(e);
			}
		}
	}
	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() throws Exception{
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		amountColumns.add("amount");
		String[] productTypeIds = this.productTypeGetter.getProductTypeIds();
		String apportionId = ApportionTypeInfo.sellAreaType;
		Map productMap = new HashMap();
		for (int i = 0; i < productTypeIds.length; i++) {
			String productTypeId = productTypeIds[i];
			/**
			 * 描述:加入顺序调整先all再sell，保障循环时先计算总成本汇总再根据汇总后的总成本计算可售单方
			 * 可售单方=总成本/可售单方指标	 
			 */
			amountColumns.add(productTypeId + "all");
			amountColumns.add(productTypeId + "sell");
			BigDecimal apporValue = this.productTypeGetter.getProductApprotion(
					productTypeId, apportionId);
			productMap.put(productTypeId + "sell", apporValue);
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
					boolean isSellColumn = false;
					if(colName.endsWith("sell")){
						BigDecimal apporValue = FDCHelper.toBigDecimal(productMap.get(colName));
						String key = colName.substring(0,colName.length()-4)+"all";
						Object allCost = row.getCell(key).getValue();
						if(allCost != null && FDCHelper.toBigDecimal(apporValue).compareTo(FDCHelper.ZERO) != 0){
							amount = FDCHelper.toBigDecimal(allCost).divide(apporValue, 2, BigDecimal.ROUND_HALF_UP);
							hasData = true;
						}
						isSellColumn = true;
					}
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null
								&& rowAdd.getCell(colName).getStyleAttributes()
										.getFontColor().equals(Color.RED)) {
							isRed = true;
						}
						if (value != null && !isSellColumn) {
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
		if(amountColumns.size()>0){
			String[] columns=new String[amountColumns.size()];
			amountColumns.toArray(columns);
			try{
				AimCostClientHelper.setTotalCostRow(table, columns);
			}catch(Exception e){
				handUIException(e);
			}
		}
		
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		if(rowIndex<0){
			return;
		}
		IRow row = tblMain.getRow(rowIndex);

		if (tblMain.getColumnKey(columnIndex)!=null&&tblMain.getColumnKey(columnIndex).equals("isChoose")) {
			List rowList = new ArrayList();
			Boolean isChoose = (Boolean) row.getCell("isChoose").getValue();
			if (row.getCell("isChoose").getUserObject() == null) {
				isChoose = Boolean.valueOf(!isChoose.booleanValue());
				row.getCell("isChoose").setValue(isChoose);
			}
			if (rowIndex >= tblMain.getRowCount() - 1) {
				return;
			}
			int level = row.getTreeLevel();
			for (int i = rowIndex + 1; i < tblMain.getRowCount(); i++) {
				if (tblMain.getRow(i).getTreeLevel() > level) {
					rowList.add(tblMain.getRow(i));
				} else {
					break;
				}
			}
			for (int i = 0; i < rowList.size(); i++) {
				IRow childRow = (IRow) rowList.get(i);
				if (childRow.getCell("isChoose").getUserObject() == null) {
					childRow.getCell("isChoose").setValue(isChoose);
				}
			}
		}
	}
	public void fillTableNotLeaf() throws Exception{
		if(retValueNotLeaf==null)
			return;
		CostAccountCollection costAccounts = (CostAccountCollection)retValueNotLeaf.get("CostAccountCollection");
		ProductTypeCollection prodcutMap = (ProductTypeCollection)retValueNotLeaf.get("ProductTypeCollection");
		RetValue costValues = (RetValue)retValueNotLeaf.get("costValues");
		RetValue productCostValues = (RetValue)retValueNotLeaf.get("productCostValues");
		RetValue productSellAreaValue = (RetValue)retValueNotLeaf.get("ProductSellAreaValue");
		
		this.tblMain.removeRows();
		tblMain.getTreeColumn().setDepth(retValueNotLeaf.getInt("maxLevel"));
		if(costAccounts!=null){
			for(Iterator it=costAccounts.iterator();it.hasNext();){
				CostAccountInfo costAccountInfo = (CostAccountInfo)it.next();
				IRow row = tblMain.addRow();
				row.setTreeLevel(costAccountInfo.getLevel() - 1);
				String longNumber = costAccountInfo.getLongNumber();
				longNumber = longNumber.replace('!', '.');
				row.getCell("acctNumber").setValue(longNumber);
				row.getCell("acctName").setValue(costAccountInfo.getName());
				if(costAccountInfo.isIsLeaf()){
					row.setUserObject(costAccountInfo);
					RetValue costValue = (RetValue)costValues.get(costAccountInfo.getLongNumber());
					RetValue productCostValue = (RetValue)productCostValues.get(costAccountInfo.getLongNumber());
					if(costValue !=null ){ 
						BigDecimal aimCostAmt = costValue.getBigDecimal("aimCostAmt");
						row.getCell("amount").setValue(aimCostAmt);
					}
					if(productCostValue==null) continue;
					for (Iterator iter = prodcutMap.iterator(); iter.hasNext();) {
						ProductTypeInfo product = (ProductTypeInfo) iter.next();
						String productTypeID = product.getId().toString();
						String key = productTypeID;
						BigDecimal aimAimCostAmt = productCostValue.getBigDecimal(key+"aimAimCostAmt");
//						BigDecimal aimAimSaleUnitAmt = productCostValue.getBigDecimal(key+"aimAimSaleUnitAmt");
						BigDecimal approValue = FDCHelper.toBigDecimal(productSellAreaValue.getBigDecimal(key+ProjectStageEnum.DYNCOST_VALUE));
						row.getCell(productTypeID+"all").setValue(aimAimCostAmt);
						if(FDCHelper.toBigDecimal(aimAimCostAmt).signum()!=0&&FDCHelper.toBigDecimal(approValue).signum()!=0){
							BigDecimal aimAimSaleUnitAmt = aimAimCostAmt.divide(approValue, 2,BigDecimal.ROUND_HALF_UP);
							row.getCell(productTypeID+"sell").setValue(aimAimSaleUnitAmt);
						}
					}
				}else{
					row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
				}
			}
		}
		setUnionDataNotLeaf();
	}
	
	public void fillTable() throws Exception {
		TimeTools.getInstance().reset();
		TimeTools.getInstance().msValuePrintln("start fillTable:");
		KDTable table = this.tblMain;
		table.removeRows();
		String objectId = this.getSelectObjId();
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
		AcctAccreditHelper.handAcctAccreditFilter(null, objectId, acctFilter);
		TreeModel costAcctTree = FDCClientHelper.createDataTree(
				CostAccountFactory.getRemoteInstance(), acctFilter);

		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// view.setFilter(filter);
		// filter.getFilterItems().add(new FilterItemInfo("orgOrProId",
		// objectId));
		// filter.getFilterItems().add(
		// new FilterItemInfo("isLastVersion", new Integer(1)));
		// view.getSorter().add(new SorterItemInfo("id"));
		// view.getSelector().add("*");
		// view.getSelector().add("creator.name");
		// view.getSelector().add("auditor.name");
		// view.getSelector().add("costEntry.*");
		// view.getSelector().add("costEntry.product.*");
		// AimCostCollection aimCostCollection = AimCostFactory
		// .getRemoteInstance().getAimCostCollection(view);
		// Set costEntryIdSet = new HashSet();
		// if (aimCostCollection.size() > 0) {
		// AimCostInfo aimCost = aimCostCollection.get(0);
		// CostEntryCollection costEntryCollection = aimCost.getCostEntry();
		// this.aimGetter=new
		// for (int i = 0; i < costEntryCollection.size(); i++) {
		// CostEntryInfo info = costEntryCollection.get(i);
		// costEntryIdSet.add(info.getId().toString());
		// CostAccountInfo costAccount = info.getCostAccount();
		// if (aimCostEntryMap.containsKey(costAccount.getId().toString())) {
		// CostEntryCollection coll = (CostEntryCollection) aimCostEntryMap
		// .get(costAccount.getId().toString());
		// coll.add(info);
		// } else {
		// CostEntryCollection newColl = new CostEntryCollection();
		// newColl.add(info);
		// aimCostEntryMap
		// .put(costAccount.getId().toString(), newColl);
		// }
		// }
		// }
		TimeTools.getInstance().msValuePrintln("start aimGetter:");
		this.aimGetter = new AimCostSplitDataGetter(objectId,productTypeGetter);
		TimeTools.getInstance().msValuePrintln("start initProductSplitData:");
		this.aimGetter.initProductSplitData();
		TimeTools.getInstance().msValuePrintln("end aimGetter:");
		// if (costEntryIdSet.size() > 0) {
		// view.getSelector().clear();
		// view.getSelector().add("*");
		// view.getSelector().add("apportionType.*");
		// view.getFilter().getFilterItems().clear();
		// view.getFilter().getFilterItems().add(
		// new FilterItemInfo("costEntryId", costEntryIdSet,
		// CompareType.INCLUDE));
		// AimCostProductSplitEntryCollection splits =
		// AimCostProductSplitEntryFactory
		// .getRemoteInstance().getAimCostProductSplitEntryCollection(
		// view);
		// splitEntryMap.clear();
		// for (int i = 0; i < splits.size(); i++) {
		// AimCostProductSplitEntryInfo info = splits.get(i);
		// String costEntryId = info.getCostEntryId();
		// if (splitEntryMap.containsKey(costEntryId)) {
		// AimCostProductSplitEntryCollection coll =
		// (AimCostProductSplitEntryCollection) splitEntryMap
		// .get(costEntryId);
		// coll.add(info);
		// } else {
		// AimCostProductSplitEntryCollection newColl = new
		// AimCostProductSplitEntryCollection();
		// newColl.add(info);
		// splitEntryMap.put(costEntryId, newColl);
		// }
		// }
		// }
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
				.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
					.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		table.getTreeColumn().setDepth(maxLevel);
		TimeTools.getInstance().msValuePrintln("start fillNode:");
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode(table, (DefaultMutableTreeNode) root.getChildAt(i));
		}
		TimeTools.getInstance().msValuePrintln("end fillNode:");
		this.setUnionData();
		TimeTools.getInstance().msValuePrintln("end fillTable:");
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			MsgBox.showError("成本科目的级别太多!");
			return;
		}
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getStyleAttributes().setLocked(true);
		row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		String longNumber = costAcct.getLongNumber();
		longNumber = longNumber.replace('!', '.');
		row.getCell("acctNumber").setValue(longNumber);
		row.getCell("acctName").setValue(costAcct.getName());
		row.getCell("isChoose").setValue(Boolean.FALSE);
		if (node.isLeaf()) {
			CostEntryCollection coll = this.aimGetter.getCostEntrys(costAcct
					.getId().toString());
			if (coll != null && coll.size() > 0) {
				for (int i = 0; i < coll.size(); i++) {
					CostEntryInfo info = coll.get(i);
					if(info == null){
						continue;
					}
					IRow entryRow = table.addRow();
					entryRow.setUserObject(info);
					entryRow.setTreeLevel(node.getLevel());
					entryRow.getCell("acctName").setValue(info.getEntryName());
					entryRow.getCell("amount").setValue(info.getCostAmount());
					entryRow.getCell("isChoose").setValue(Boolean.FALSE);
					AimCostProductSplitEntryCollection cashe = this.aimGetter
							.getProductSplitEntry(info.getId().toString());
					Map databaseData = new HashMap();
					if (cashe != null) {
						for (int j = 0; j < cashe.size(); j++) {
							AimCostProductSplitEntryInfo split = cashe.get(j);
							databaseData.put(split.getProduct().getId()
									.toString(), split.getSplitAmount());
						}
					}
					if (info.getProduct() != null) {
						databaseData.put(info.getProduct().getId().toString(),
								info.getCostAmount());
					}
					entryRow.getCell("acctNumber").setUserObject(databaseData);
					AimCostProductSplitEntryCollection splits = getRowSetting(entryRow);
					calculateRowData(entryRow, splits);
				}
			}
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
		}
	}

	private AimCostProductSplitEntryCollection getRowSetting(IRow entryRow) {
		CostEntryInfo entry = (CostEntryInfo) entryRow.getUserObject();
		AimCostProductSplitEntryCollection splits = this.aimGetter
				.createSetting(entry);
		return splits;
	}

	private void calculateRowData(IRow entryRow,
			AimCostProductSplitEntryCollection splits) throws BOSException {
		// 清空行上旧内容
//		if(true) return;
		String[] productTypeIds = this.productTypeGetter.getProductTypeIds();
		for (int i = 0; i < productTypeIds.length; i++) {
			String productTypeId = productTypeIds[i];
			entryRow.getCell(productTypeId + "sell").setValue(null);
			entryRow.getCell(productTypeId + "all").setValue(null);
		}
		if (splits.size() <= 0) {
			entryRow.getCell("acctName").setUserObject(new HashMap());
			entryRow.getCell("apportionType").setUserObject(new HashMap());
			this.setRowColor(entryRow, Color.BLUE);
			return;
		}
		CostEntryInfo entry = (CostEntryInfo) entryRow.getUserObject();
		AimCostProductSplitEntryInfo split = splits.get(0);
		ApportionTypeInfo apportionType = split.getApportionType();
		if (apportionType != null) {
			entryRow.getCell("apportionType").setValue(apportionType.getName());
		} else {
			entryRow.getCell("apportionType").setValue(
					AimCostHandler.getResource("DirectOwn"));
		}

		if (entryRow.getCell("apportionType").getUserObject() != null) {
			Map map = (Map) entryRow.getCell("apportionType").getUserObject();
			map.put("newId", apportionType.getId().toString());
			String apporId = (String) map.get("oldId");
			if (!apportionType.getId().toString().equals(apporId)) {
				entryRow.getCell("apportionType").getStyleAttributes()
						.setFontColor(Color.RED);
			} else {
				entryRow.getCell("apportionType").getStyleAttributes()
						.setFontColor(Color.BLACK);
			}
		} else {
			Map map = new HashMap();
			if (apportionType != null) {
				map.put("oldId", apportionType.getId().toString());
			} else {
				map.put("oldId", "direct");
				AimCostProductSplitEntryCollection cashe = this.aimGetter
						.getProductSplitEntry(entry.getId().toString());
				if (cashe != null) {
					entryRow.getCell("apportionType").getStyleAttributes()
							.setFontColor(Color.RED);
				}
				// if (splits.size() > 0) {
				// entryRow.getCell("apportionType").getStyleAttributes()
				// .setFontColor(Color.RED);
				// }
			}
			entryRow.getCell("apportionType").setUserObject(map);

		}

		Map calculateData = this.aimGetter.getAimProductData(entry);
		Set keySet = calculateData.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String prodId = (String) iter.next();
			BigDecimal splitAmount = (BigDecimal) calculateData.get(prodId);
			if (entryRow.getCell(prodId + "all") != null) {
				entryRow.getCell(prodId + "all").setValue(splitAmount);
			}
		}
		// if (splits.size() > 1) {
		// } else {
		// // 单一产品直接分摊
		// String productSplitId = splits.get(0).getProduct().getId()
		// .toString();
		// calculateData.put(productSplitId, entry.getCostAmount());
		// if (entryRow.getCell(productSplitId + "all") != null) {
		// entryRow.getCell(productSplitId + "all").setValue(
		// entry.getCostAmount());
		// }
		// }
		if (splits.get(0).getApportionType() == null) {
			// calculateData = new HashMap();
			entryRow.getCell("isChoose").setUserObject("NotChoose");
			entryRow.getCell("isChoose").setValue(null);
			entryRow.getCell("isChoose").getStyleAttributes().setBackground(
					Color.cyan);
		}
		// 设置可售面积列
		setSellAmountbySplit(entryRow, calculateData);
		entryRow.getCell("acctName").setUserObject(calculateData);
		if (isEaquelDatabase(entryRow)) {
			this.setRowColor(entryRow, Color.BLACK);
		} else {
			this.setRowColor(entryRow, Color.RED);
		}
	}

	private void setSellAmountbySplit(IRow entryRow, Map calculateData) throws BOSException {
		Set keySet = calculateData.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			String apportionId = ApportionTypeInfo.sellAreaType;
			BigDecimal apporValue = this.productTypeGetter.getProductApprotion(
					productId, apportionId);
			BigDecimal value = null;
			if (entryRow.getCell(productId + "all") != null) {
				value = (BigDecimal) entryRow.getCell(productId + "all")
						.getValue();
			}
			if (value != null && apporValue.compareTo(FDCHelper.ZERO) != 0) {
				entryRow.getCell(productId + "sell").setValue(
						value.divide(apporValue, 2, BigDecimal.ROUND_HALF_UP));
			}
		}
	}

	private boolean isEaquelDatabase(IRow entryRow) {
//		if(true) return true;
		//TODO 可以减少循环
		Map databaseData = (Map) entryRow.getCell("acctNumber").getUserObject();
		Map splitData = (Map) entryRow.getCell("acctName").getUserObject();
//		Map verifyData = new HashMap();
		if (splitData == null) {
			splitData = new HashMap();
		}
		Set splitSet = splitData.keySet();
		for (Iterator iter = splitSet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			BigDecimal amount1 = (BigDecimal) splitData.get(productId);
			BigDecimal amount2 = (BigDecimal) databaseData.get(productId);
			if(FDCHelper.toBigDecimal(amount1).compareTo(FDCHelper.toBigDecimal(amount2))!=0){
				return false;
			}
//			verifyData.put(productId, splitData.get(productId));
		}
		
		
/*		Set splitSet = splitData.keySet();
		for (Iterator iter = splitSet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			verifyData.put(productId, splitData.get(productId));
		}

		Set dataSet = databaseData.keySet();
		for (Iterator iter = dataSet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			BigDecimal amount1 = (BigDecimal) verifyData.get(productId);
			if (amount1 == null) {
				amount1 = FDCHelper.ZERO;
			}
			BigDecimal amount2 = (BigDecimal) databaseData.get(productId);
			if (amount2 == null) {
				amount2 = FDCHelper.ZERO;
			}
			verifyData.put(productId, amount1.subtract(amount2));
		}
		splitSet = verifyData.keySet();
		for (Iterator iter = splitSet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			BigDecimal amount = (BigDecimal) verifyData.get(productId);
			if (amount.compareTo(FDCHelper.ZERO) != 0) {
				return false;
			}
		}*/
		return true;
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	protected void initTree() throws Exception {
		this.initTable();
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {

			}
			FullOrgUnitInfo info = oui.getUnit();
			return info.getId().toString();
		}
		return null;
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		this.verifySaved();
		fetchAndFill(selectObjId);
		initUserConfig();
//		int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
//		tblMain.getViewManager().freeze(0, acctNameIndex);
	}
	private void fetchAndFill(String selectObjId) throws EASBizException,
			BOSException, Exception {
		LongTimeDialog dialog = UITools.getDialog(this);
		if (dialog == null)
			return;
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				String selectObjId = getSelectObjId();
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
						.getLastSelectedPathComponent();
				if (node.isLeaf()
						&& node.getUserObject() instanceof CurProjectInfo) {
					productTypeGetter = new AimProductTypeGetter(selectObjId,
							ProjectStageEnum.DYNCOST);
					resetProductCol();
					setApporAction();
					fillTable();
				} else {
					Set leafPrjIds = FDCClientHelper
							.getProjectLeafsOfNode(node);
					boolean isLeafPrj = node.getUserObject() instanceof CurProjectInfo;
					fetchDataNotLeaf(selectObjId, leafPrjIds, isLeafPrj);
					resetProductColNotLeaf();
					setApporAction();
					fillTableNotLeaf();
				}
				return null;
			}

			public void afterExec(Object result) throws Exception {

			}
		});
		dialog.show();
	}

	private void setApporAction() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (currentOrg == null || !currentOrg.isIsBizUnit()) {
//			this.actionSubmit.setEnabled(false);
//			this.actionApportion.setEnabled(false);
//			this.actionRevert.setEnabled(false);
//			this.tblMain.getColumn("isChoose").getStyleAttributes().setHided(
//					true);
		}
		if (!node.isLeaf()) {
			this.actionSubmit.setEnabled(false);
			this.actionApportion.setEnabled(false);
			this.actionRevert.setEnabled(false);
		} else {
			this.actionSubmit.setEnabled(true);
			this.actionApportion.setEnabled(true);
			this.actionRevert.setEnabled(true);
		}
		if (node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
			tblMain.getColumn("isChoose").getStyleAttributes().setHided(false);
			tblMain.getColumn("apportionType").getStyleAttributes().setHided(false);
		}else{
			tblMain.getColumn("isChoose").getStyleAttributes().setHided(true);
			tblMain.getColumn("apportionType").getStyleAttributes().setHided(true);
		}
	}

	private void resetProductCol() {
		int columnCount = tblMain.getColumnCount();
		for (int i = 0; i < columnCount - 5; i++) {
			tblMain.removeColumn(5);
		}
		Map prodcutMap = this.productTypeGetter.getSortedProductMap();
		int i = 0;
		Set keySet = prodcutMap.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String number = (String) iter.next();
			ProductTypeInfo product = (ProductTypeInfo) prodcutMap.get(number);
			IColumn column = tblMain.addColumn();
			String key = product.getId().toString() + "sell";
			column.setKey(key);
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
			tblMain.getHeadRow(1).getCell(key).setValue(
					AimCostHandler.getResource("SellPart"));
			column = tblMain.addColumn();
			key = product.getId().toString() + "all";
			column.setKey(key);
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
			tblMain.getHeadRow(1).getCell(key).setValue(
					AimCostHandler.getResource("AllCost"));
			tblMain.getHeadMergeManager().mergeBlock(0, 5 + i * 2, 0,
					5 + 1 + i * 2);
			i++;
		}
	}

	private void resetProductColNotLeaf() {
		int columnCount = tblMain.getColumnCount();
		for (int i = 0; i < columnCount - 5; i++) {
			tblMain.removeColumn(5);
		}
		ProductTypeCollection prodcutMap = (ProductTypeCollection)this.retValueNotLeaf.get("ProductTypeCollection");
		int i = 0;
		for (Iterator iter = prodcutMap.iterator(); iter.hasNext();) {
			ProductTypeInfo product = (ProductTypeInfo) iter.next();
			IColumn column = tblMain.addColumn();
			String key = product.getId().toString() + "sell";
			column.setKey(key);
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
			tblMain.getHeadRow(1).getCell(key).setValue(
					AimCostHandler.getResource("SellPart"));
			column = tblMain.addColumn();
			key = product.getId().toString() + "all";
			column.setKey(key);
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
			tblMain.getHeadRow(1).getCell(key).setValue(
					AimCostHandler.getResource("AllCost"));
			tblMain.getHeadMergeManager().mergeBlock(0, 5 + i * 2, 0,
					5 + 1 + i * 2);
			i++;
		}
	}
	
	private void verifySaved() throws EASBizException, BOSException {
		boolean isEdited = false;
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() != null) {
				isEdited = !this.isEaquelDatabase(row);
				if (row.getCell("apportionType").getStyleAttributes()
						.getFontColor().equals(Color.RED)) {
					isEdited = true;
				}
				if (isEdited) {
					break;
				}
			}
		}
		if (isEdited) {
			if (MsgBox.showConfirm2New(this, AimCostHandler.getResource("NoSave")) == MsgBox.YES) {
//				submitData();
				this.btnSubmit.doClick();
			}
		}
	}

	/**
	 * 设置表格属性
	 */
	private void initTable() {
		KDTable table = this.tblMain;
		table.checkParsed();
		table.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getViewManager().setFreezeView(-1, 2);
		table.setRefresh(false);
		table.getColumn("apportionType").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.CENTER);
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		table.getColumn("acctNumber").getStyleAttributes().setLocked(true);
//		Color lockColor = new Color(0xF0AAD9);
		table.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("amount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.addHeadRow(1, (IRow) tblMain.getHeadRow(0).clone());
		for (int i = 0; i < tblMain.getColumnCount(); i++) {
			tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
		tblMain.setColumnMoveable(true);
	}

	/**
	 * output class constructor
	 */
	public ProductAimCostUI() throws Exception {
		super();
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		TimeTools.getInstance().reset();
		TimeTools.getInstance().msValuePrintln("start submit");
		submitData();
		TimeTools.getInstance().msValuePrintln("end submit");
		this.setMessageText(EASResource
				.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
		this.showMessage();
		fillTable();
	}

	private void submitData() throws BOSException, EASBizException {
		/*
		 *key=costEntryid value=AimCostProductSplitEntryCollection
		 */
		Map costEntryMap=new HashMap();
		
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() != null) {
				boolean isEdited = !this.isEaquelDatabase(row);
				if (row.getCell("apportionType").getStyleAttributes()
						.getFontColor().equals(Color.RED)) {
					isEdited = true;
				}
				if (isEdited) {
					CostEntryInfo entry = (CostEntryInfo) row.getUserObject();
					String entryId = entry.getId().toString();
					AimCostProductSplitEntryCollection rowSetting = this
							.getRowSetting(row);
					Map calData = (Map) row.getCell("acctName").getUserObject();
					AimCostProductSplitEntryCollection newColl=new AimCostProductSplitEntryCollection();
					for (int j = 0; j < rowSetting.size(); j++) {
						AimCostProductSplitEntryInfo info = rowSetting.get(j);
						info.setCostEntryId(entryId);
						info.setSplitAmount((BigDecimal) calData.get(info
								.getProduct().getId().toString()));
//						info.setDescription("isChoose");
						if(info.getApportionType()!=null){
							newColl.add((AimCostProductSplitEntryInfo)info.clone());
						}
					}
					if(newColl.size()>0){
						costEntryMap.put(entryId, newColl);
					}

				}
			}
		}
		String prjId=getSelectObjId();
		if(prjId!=null){
			costEntryMap.put("prjId", prjId);
		}
		FDCCostRptFacadeFactory.getRemoteInstance().submitAimProductCost(costEntryMap);
		CacheServiceFactory.getInstance().discardType(new AimCostProductSplitEntryInfo().getBOSType());
	}

	/**
	 * output actionApportion_actionPerformed
	 */
	public void actionApportion_actionPerformed(ActionEvent e) throws Exception {
		List rows = new ArrayList();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() != null) {
				if (row.getCell("isChoose").getValue() != null
						&& ((Boolean) row.getCell("isChoose").getValue())
								.booleanValue()) {
					rows.add(row);
				}
			}
		}
		if (rows.size() == 0) {
			this.setMessageText(AimCostHandler.getResource("NoDetailRow"));
			this.showMessage();
			return;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("projectStage", ProjectStageEnum.DYNCOST);
		if (rows.size() == 1) {
			IRow selectRow = (IRow) rows.get(0);
			// 准备参数
			AimCostProductSplitEntryCollection entrys = getRowSetting(selectRow);
			uiContext.put("splits", entrys);
		} else {
			uiContext.put("splits", new AimCostProductSplitEntryCollection());
		}
		String selectObjId = this.getSelectObjId();
		BOSObjectType bosType = BOSUuid.read(selectObjId).getType();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			uiContext.put("projectId", selectObjId);
			IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(AimCostProductSplitSettingUI.class.getName(),
							uiContext, null, "ADDNEW");
			uiWin.show();
			Map context = ((AimCostProductSplitSettingUI) uiWin.getUIObject())
					.getUIContext();
			if (context.get("isOK") == null) {
				return;
			}
			for (int i = 0; i < rows.size(); i++) {
				IRow row = (IRow) rows.get(i);
				CostEntryInfo entry = (CostEntryInfo) row.getUserObject();
				AimCostProductSplitEntryCollection col = (AimCostProductSplitEntryCollection) context
						.get("splits");
				this.aimGetter.updateProductMap(entry.getId().toString(), col);
				this.calculateRowData(row, col);
				this.setUnionData();
			}
		}
	}

	/**
	 * output actionRevert_actionPerformed
	 */
	public void actionRevert_actionPerformed(ActionEvent e) throws Exception {
		this.fillTable();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnApportion.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.btnRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
		setButtonDefaultStyl(this.btnSubmit);
		this.menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.menuItemApportion.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.menuItemRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		this.verifySaved();
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		TimeTools.getInstance().reset();
		fetchAndFill(selectObjId); 
		initUserConfig();
	}

	public boolean destroyWindow() {
		try {
			this.verifySaved();
		} catch (EASBizException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return super.destroyWindow();
	}
	public void onShow() throws Exception {
		super.onShow();
/*		int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
//		tblMain.getViewManager().setFreezeView(-1, acctNameIndex);
		tblMain.getViewManager().freeze(0, acctNameIndex);*/
		
	}

	private void fetchDataNotLeaf(String objId,Set leafPrjIds,boolean selectObjIsPrj) throws EASBizException, BOSException{
		TimeTools.getInstance().msValuePrintln("start fetchData");
		ParamValue paramValue = new ParamValue();
		paramValue.put("selectObjID", objId);
		paramValue.put("leafPrjIDs", leafPrjIds);
		paramValue.put("selectObjIsPrj", Boolean.valueOf(selectObjIsPrj));
		retValueNotLeaf = ProjectCostRptFacadeFactory.getRemoteInstance().getCollectionProductDynAimCost(paramValue);
		TimeTools.getInstance().msValuePrintln("end fetchData");
	}
	//导出报错问题：报表手工取数不用query
	public int getRowCountFromDB() {
//		super.getRowCountFromDB();
		return -1;
	}
}