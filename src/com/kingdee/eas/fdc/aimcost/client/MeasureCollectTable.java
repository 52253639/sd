package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureDefaultSplitTypeSetCollection;
import com.kingdee.eas.fdc.aimcost.MeasureDefaultSplitTypeSetFactory;
import com.kingdee.eas.fdc.aimcost.MeasureDefaultSplitTypeSetInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.aimcost.client.AimMeasureCostEditUI.Item;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.util.SysUtil;

public class MeasureCollectTable {
	private KDTable table = null;
	private AimMeasureCostEditUI measureCostEditUI = null;
	private PlanIndexInfo planIndexInfo = null;
	private PlanIndexEntryCollection entrys = null;

	public MeasureCollectTable(AimMeasureCostEditUI measureCostEditUI) throws EASBizException, BOSException {
		this.measureCostEditUI = measureCostEditUI;
		isBuildPartLogic=measureCostEditUI.isBuildPartLogic();
		// 取数据方式改成从界面取数
		// planIndexInfo=loadData(headId);
		// entrys=getEntrys(planIndexInfo);
		this.table = new KDTable();
		table.setName("测算汇总表");
		table.getStyleAttributes().setLocked(true);
		// initTable();
		// fillTable();
		// setUnionData();
		table.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
			public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
				tableEditStopped(e);
			}
		});
	}

	private PlanIndexEntryCollection getEntrys(PlanIndexInfo planIndexInfo) {
		allSellArea=PlanIndexTable.getAllSellArea(planIndexInfo);
		allBuildArea=PlanIndexTable.getAllBuildArea(planIndexInfo);
		allConstructArea=PlanIndexTable.getAllConstructArea(planIndexInfo);
		PlanIndexEntryCollection entrys = new PlanIndexEntryCollection();
		for (int i = 0; i < planIndexInfo.getEntrys().size(); i++) {
			PlanIndexEntryInfo entry = planIndexInfo.getEntrys().get(i);
			if (entry.isIsProduct()) {
				entrys.add(entry);
			}
		}
		return entrys;
	}
	private boolean isBuildPartLogic = false;
	private boolean isBuildPartLogic(){
		return isBuildPartLogic;
	}
	private BigDecimal allBuildArea=FDCHelper.ZERO;
	private BigDecimal getAllBuildArea(){
		return allBuildArea;
	}
	
	private BigDecimal allSellArea=FDCHelper.ZERO;
	private BigDecimal getAllSellArea(){
		return allSellArea;
	}
	private BigDecimal allConstructArea=FDCHelper.ZERO;
	private BigDecimal getAllConstructArea(){
		return allConstructArea;
	}
	
	private BigDecimal getCustomIndex(Item item, String productId){
		BigDecimal value = FDCHelper.ZERO;
		CustomPlanIndexEntryInfo entry = measureCostEditUI.getPlanIndexTable().getCustomPlanIndexEntryInfo(item.key, productId);
		if(entry!=null){
			value = entry.getValue();
		}
		return value;
	}
	
	private BigDecimal getCustomSumIndex(){
		BigDecimal sum=FDCHelper.ZERO;
		CustomPlanIndexEntryCollection entrys = measureCostEditUI.getPlanIndexTable().getCustomPlanIndexs("productId");
		for(Iterator iter=entrys.iterator();iter.hasNext();){
			CustomPlanIndexEntryInfo entry = (CustomPlanIndexEntryInfo)iter.next();
			sum = FDCHelper.add(sum, entry.getValue());
		}
		return sum;
	}
	
	
	private BigDecimal getSumArea(String splitType){
		BigDecimal sum=FDCHelper.ZERO;
		for (int i = 0; i < entrys.size(); i++) {
			if (entrys.get(i).isIsSplit()) {
				sum = sum.add(FDCHelper.toBigDecimal(entrys.get(i).getBigDecimal(splitType)));
			}
		}
		return sum;
	}
	public KDTable getTable() {
		return table;
	}

	private void initTable() {
		if(entrys==null) return;
		this.table.removeColumns();
		int entrysSize = entrys.size();
		int colums = entrysSize * 4 + 7;
		// this.table=new KDTable(colums,2,0);
		this.table.createBlankTable(colums, 2, 0);
		table.getColumn(0).setKey("acctNumber");
		table.getColumn(1).setKey("acctName");
		int base = 2;//科目名称下一列，即数据列
		for (int i = 0; i < entrysSize; i++) {
			table.getColumn(base + i).setKey("buildAvg" + i);
			FDCHelper.formatTableNumber(table, "buildAvg" + i);
		}
		table.getColumn(base + entrysSize).setKey("buildAvg");
		FDCHelper.formatTableNumber(table, "buildAvg");
		
		base = base + entrysSize + 1;
		for (int i = 0; i < entrysSize; i++) {
			table.getColumn(base + i).setKey("constructAvg" + i);
			FDCHelper.formatTableNumber(table, "constructAvg" + i);
		}
		table.getColumn(base + entrysSize).setKey("constructAvg");
		FDCHelper.formatTableNumber(table, "constructAvg");
		
		base = base + entrysSize + 1;
		for (int i = 0; i < entrysSize; i++) {
			table.getColumn(base + i).setKey("avg" + i);
			FDCHelper.formatTableNumber(table, "avg" + i);
		}
		table.getColumn(base + entrysSize).setKey("avg");
		FDCHelper.formatTableNumber(table, "avg");

		base = base + entrysSize + 1;
		for (int i = 0; i < entrysSize; i++) {
			table.getColumn(base + i).setKey("split" + i);
			FDCHelper.formatTableNumber(table, "split" + i);
		}
		table.getColumn(base + entrysSize).setKey("total");
		FDCHelper.formatTableNumber(table, "total");
		table.getColumn(colums - 1).setKey("splitType");

		// title
		IRow headRow = table.getHeadRow(0);
		headRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		// headRow.getStyleAttributes().setBold(true);
		headRow.getCell("acctNumber").setValue("科目编码");
		headRow.getCell("acctName").setValue("科目名称");
		headRow.getCell("buildAvg").setValue("建筑面积单位成本");
		headRow.getCell("constructAvg").setValue("实际建设面积单位成本");
		headRow.getCell("avg").setValue("可售面积单位成本");
		headRow.getCell("total").setValue("总成本");
		headRow.getCell("splitType").setValue("分配方式");
		headRow = table.getHeadRow(1);
		headRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		// headRow.getStyleAttributes().setBold(true);
		for (int i = 0; i < entrysSize; i++) {
			String name = entrys.get(i).getProduct().getName();
			headRow.getCell("buildAvg" + i).setValue(name);
			headRow.getCell("constructAvg" + i).setValue(name);
			headRow.getCell("avg" + i).setValue(name);
			headRow.getCell("split" + i).setValue(name);
			headRow.getCell("split" + i).setUserObject(entrys.get(i).getProduct().getId().toString());
		}
		headRow.getCell("buildAvg").setValue("平均");
		headRow.getCell("constructAvg").setValue("平均");
		headRow.getCell("avg").setValue("平均");
		headRow.getCell("total").setValue("合计");
		// merge
		KDTMergeManager mm = table.getHeadMergeManager();
		// mm.setMergeMode(KDTMergeManager.FREE_MERGE);
		
		// 科目编码
		mm.mergeBlock(0, 0, 1, 0);
		// 科目名称
		mm.mergeBlock(0, 1, 1, 1);
		// 分配方式
		mm.mergeBlock(0, colums - 1, 1, colums - 1);
		// 建筑面积单位成本
		mm.mergeBlock(0, 2, 0, 2 + entrysSize);
		// 可售面积单位成本(加1是因为建筑面积单位成本多了平均列，总成本加2类同 by hpw)
		mm.mergeBlock(0, 2 + entrysSize + 1, 0, 2 + entrysSize * 2 + 1);
		
		mm.mergeBlock(0, 2 + entrysSize * 2 + 2, 0, 2 + entrysSize * 3 + 2);
		// 总成本
		mm.mergeBlock(0, 2 + entrysSize * 3 + 3, 0, colums - 2);
		// table.getColumn("splitType").setEditor(measureCostEditUI.getIndexEditor(CostAccountTypeEnum.MAIN,
		// null));
		// 冻结
		table.getViewManager().setFreezeView(-1, 2);
	}

	private void fillTable() {
		TreeModel costAcctTree = measureCostEditUI.getCostAcctTree();
		table.removeRows();
		table.setUserObject(null);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		table.getTreeColumn().setDepth(maxLevel + 1);
		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
			fillNode(child);
		}

		String oprtState = measureCostEditUI.getOprtState();
		if (oprtState!=null&&!oprtState.equals(OprtState.ADDNEW) && !oprtState.equals(OprtState.EDIT)) {
			for (int i = 0; i < table.getRowCount(); i++) {
				if (table.getCell(i, "splitType").getEditor() != null && table.getCell(i, "splitType").getEditor().getComponent() != null) {
					table.getCell(i, "splitType").getEditor().getComponent().setEnabled(false);
				}
			}
		}
	}

	private void fillNode(DefaultMutableTreeNode node) {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
//			MsgBox.showError("成本科目的级别太多!");
			return;
		}
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		// row.getStyleAttributes().setLocked(true);
		// row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		row.setUserObject(costAcct);
		String longNumber = costAcct.getLongNumber();
		longNumber = longNumber.replace('!', '.');
		row.getCell("acctNumber").setValue(longNumber);
		row.getCell("acctName").setValue(costAcct.getName());
		if (node.isLeaf()) {
			loadRow(row);
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode((DefaultMutableTreeNode) node.getChildAt(i));
			}
		}
	}

	private void loadRow(IRow row) {
		Object obj = row.getUserObject();
		if (!(obj instanceof CostAccountInfo)) {
			return;
		}
		CostAccountInfo costAcct = (CostAccountInfo) obj;

		String key = costAcct.getId().toString();
		Map measureCostMap = measureCostEditUI.getMeasureCostMap();
		if (costAcct.getType() == CostAccountTypeEnum.SIX) {
			// 取得分摊标准后进行分摊-六大类
			MeasureEntryCollection coll = (MeasureEntryCollection) measureCostMap.get(key);
			String splitType = null;
			//选择自定义指标 by hpw 2010.08.27 
//			row.getCell("splitType").setEditor(getSplitTypeEditor());
			row.getCell("splitType").setEditor(getCollectSplitTypeEditor());
			row.getCell("splitType").getStyleAttributes().setLocked(false);

			row.getCell("splitType").getStyleAttributes().setLocked(false);

			if (coll != null && coll.size() > 0) {
				BigDecimal total = FDCHelper.ZERO;
				for (int i = 0; i < coll.size(); i++) {
					MeasureEntryInfo info = coll.get(i);
					total = total.add(FDCHelper.toBigDecimal(info.getAmount()));
					// System.out.println("Total:"+total);
					if (splitType == null)
						splitType = info.getNumber();
				}
				
				if(total.signum()==0){
					return;
				}
				row.getCell("total").setValue(total);
				if (splitType != null) {
					if (isManSplit(splitType)) {
						manSplit(splitType, row);
					} else {
						row.getCell("splitType").setValue(getSelectItem(splitType));
						splitSIX(row);
					}
				}

			}

			if (splitType == null) {
				setDefaultSplitType(row);
			}
		} else {
			// 直接指定-主体建安
			row.getCell("splitType").setValue("直接归属");
			BigDecimal allTotal = FDCHelper.ZERO;
			BigDecimal allSellArea = FDCHelper.ZERO;
			BigDecimal allBuildArea = FDCHelper.ZERO;
			BigDecimal allconstructArea = FDCHelper.ZERO;
			for (int i = 0; i < entrys.size(); i++) {
				PlanIndexEntryInfo entry = entrys.get(i);
				BigDecimal sellArea = entry.getSellArea();
				BigDecimal buildArea = entry.getBuildArea();
				BigDecimal constructArea=entry.getConstructArea();
				if (FDCHelper.toBigDecimal(sellArea).compareTo(FDCHelper.ZERO) == 0) {
					sellArea = null;
				}
				if (FDCHelper.toBigDecimal(buildArea).compareTo(FDCHelper.ZERO) == 0) {
					buildArea = null;
				}
				if (FDCHelper.toBigDecimal(constructArea).compareTo(FDCHelper.ZERO) == 0) {
					constructArea = null;
				}
				String productKey = key + entry.getProduct().getId().toString();
				MeasureEntryCollection coll = (MeasureEntryCollection) measureCostMap.get(productKey);
				if (coll != null && coll.size() > 0) {
					BigDecimal total = FDCHelper.ZERO;
					for (int j = 0; j < coll.size(); j++) {
						MeasureEntryInfo info = coll.get(j);
						if (info == null)
							continue;
						total = total.add(FDCHelper.toBigDecimal(info.getAmount()));
					}
					allTotal = allTotal.add(total);
					row.getCell("split" + i).setValue(total);
					if (sellArea != null) {
						BigDecimal avg = total.divide(sellArea, 2, BigDecimal.ROUND_HALF_UP);
						row.getCell("avg" + i).setValue(avg);
					}
					if (buildArea != null) {
						BigDecimal avg = total.divide(buildArea, 2, BigDecimal.ROUND_HALF_UP);
						row.getCell("buildAvg" + i).setValue(avg);
					}
					if (constructArea != null) {
						BigDecimal avg = total.divide(constructArea, 2, BigDecimal.ROUND_HALF_UP);
						row.getCell("constructAvg" + i).setValue(avg);
					}
				}

				if (entrys.get(i).getType() != null && entrys.get(i).getType() == PlanIndexTypeEnum.parking) {
					// 停车不包括在内
					continue;
				}
				//TODO 
//				if (entrys.get(i).getType() != null && entrys.get(i).getType() == PlanIndexTypeEnum.publicBuild) {
//					// 公共配套不包括在内
//					continue;
//				}
				
//				allSellArea = allSellArea.add(FDCHelper.toBigDecimal(sellArea));
//				allBuildArea = allBuildArea.add(FDCHelper.toBigDecimal(buildArea));
			}
			if (allTotal.compareTo(FDCHelper.ZERO) == 0)
				return;
			row.getCell("total").setValue(allTotal);
//			if(isBuildPartLogic()){
//				//与之前所有可售计算一致，包括停车
//				allSellArea = getAllSellArea();
//				allBuildArea = getAllBuildArea();
//			}
//			if (FDCHelper.toBigDecimal(allSellArea).compareTo(FDCHelper.ZERO) != 0) {
//				BigDecimal avg = allTotal.divide(allSellArea, 2, BigDecimal.ROUND_HALF_UP);
//				row.getCell("avg").setValue(avg);
//			}
//			if (FDCHelper.toBigDecimal(allBuildArea).compareTo(FDCHelper.ZERO) != 0) {
//				BigDecimal avg = allTotal.divide(allBuildArea, 2, BigDecimal.ROUND_HALF_UP);
//				row.getCell("buildAvg").setValue(avg);
//			}
		}

	}

	public void refresh() {
		planIndexInfo = measureCostEditUI.getPlanIndexTable().getPlanIndexInfo();
		entrys = getEntrys(planIndexInfo);
		measureCostEditUI.refreshMeasureCostMap();
		//刷新汇总指标 by hpw
		editor = measureCostEditUI.getCollectIndexEditor();
		initTable();
		fillTable();
		setUnionData();
	}

	/**
	 * refresh all date of six table and product table
	 */

	private KDTDefaultCellEditor editor = null;

	KDTDefaultCellEditor getCollectSplitTypeEditor() {
		if (editor != null)
			return editor;
		editor = measureCostEditUI.getCollectIndexEditor();
		return editor;
	}
	
	KDTDefaultCellEditor getSplitTypeEditor() {
		if (editor != null)
			return editor;
		Object[] items = Item.SPLITITEMS;
		KDComboBox box = new KDComboBox(items);

		editor = new KDTDefaultCellEditor(box);
		return editor;
	}

	private Item getSelectItem(String key) {
		if (editor == null)
			return null;
		KDComboBox box = (KDComboBox) editor.getComponent();
		for (int i = 0; i < box.getItemCount(); i++) {
			Item item = (Item) box.getItemAt(i);

			if (key.equals(item.key)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() {
		// if(true) return;
		// String[] cols = new String[] { "price", "sumPrice", "sellPart" };
		String[] cols = new String[entrys.size() * 4 + 4];
		for (int i = 0; i < entrys.size(); i++) {
			cols[i] = "buildAvg" + i;
			cols[entrys.size() + 1 + i] = "constructAvg" + i;
			cols[entrys.size()*2 + 2 + i] = "avg" + i;
			cols[entrys.size()*3 + 3 + i] = "split" + i;
		}
		cols[entrys.size()] = "buildAvg";
		cols[entrys.size() * 2 + 1] = "constructAvg";
		cols[entrys.size() * 3 + 2] = "avg";
		cols[entrys.size() * 4 + 3] = "total";
		// cols=new String[]{"total"};
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() instanceof CostAccountInfo) {
				CostAccountInfo acct = (CostAccountInfo) row.getUserObject();
				if (acct.isIsLeaf())
					continue;
				// 设置汇总行
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() instanceof CostAccountInfo) {
						acct = (CostAccountInfo) rowAfter.getUserObject();
						if (acct.isIsLeaf()) {
							aimRowList.add(rowAfter);
						}
					}

				}
				for (int j = 0; j < cols.length; j++) {
					BigDecimal sum = null;
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell(cols[j]).getValue();
						if (value != null) {
							// if (sum == null) {
							// sum = FMConstants.ZERO;
							// }
							// if (value instanceof BigDecimal) {
							// sum = sum.add((BigDecimal) value);
							// } else if (value instanceof Integer) {
							// sum = sum.add(new BigDecimal(((Integer) value)
							// .toString()));
							// }
							sum = FDCHelper.toBigDecimal(sum, 2).add(FDCHelper.toBigDecimal(value, 2));
						}
					}
					row.getCell(cols[j]).setValue(sum);
				}
			}
		}
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				try {
					AimCostClientHelper.setTotalCostRow(getTable(),getColumns());
				} catch (Exception e) {
					measureCostEditUI.handUIException(e);
				}
			}
		});
		afterSetUnionData();
	}
	
	//
	private void afterSetUnionData(){
		BigDecimal allSellArea = getAllSellArea();
		BigDecimal allBuildArea = getAllBuildArea();
		BigDecimal allConstructArea = getAllConstructArea();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() instanceof CostAccountInfo) {
				BigDecimal allTotal = (BigDecimal)row.getCell("total").getValue();//总成本_合计（含六类公摊及期间费）
				if(allTotal==null){
					continue;
				}
				if (FDCHelper.toBigDecimal(allSellArea).compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal avg = allTotal.divide(allSellArea, 2, BigDecimal.ROUND_HALF_UP);
					row.getCell("avg").setValue(avg);
				}
				if (FDCHelper.toBigDecimal(allBuildArea).compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal avg = allTotal.divide(allBuildArea, 2, BigDecimal.ROUND_HALF_UP);
					row.getCell("buildAvg").setValue(avg);
				}
				if (FDCHelper.toBigDecimal(allConstructArea).compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal avg = allTotal.divide(allConstructArea, 2, BigDecimal.ROUND_HALF_UP);
					row.getCell("constructAvg").setValue(avg);
				}
			}
		}
	}
	private void tableEditStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if(oldValue==null&&newValue==null){
			return;
		}
		if (oldValue != null && newValue != null && oldValue.toString().equals(newValue.toString())) {
			return;
		}
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		IRow row = table.getRow(rowIndex);
		if (table.getColumnKey(columnIndex).equals("splitType")) {
			Object obj = row.getUserObject();
			if (obj instanceof CostAccountInfo && ((CostAccountInfo) obj).getType() == CostAccountTypeEnum.SIX) {
				// 取得分摊标准后进行分摊-六大类
				splitSIX(row);
				setUnionData();
			}
		}
		if (table.getColumnKey(columnIndex).startsWith("split") 
				&& !table.getColumnKey(columnIndex).equals("split")
				&&!table.getColumnKey(columnIndex).equals("splitType")) {
			// 处理指定分摊时的录入
			Object obj = row.getCell("splitType").getValue();
			if (obj instanceof Item && ((Item) obj).key.equals("man")) {
				if(e.getValue()!=null&&e.getValue().toString().indexOf("%")>0){
					BigDecimal total=(BigDecimal) row.getCell("total").getValue();
					row.getCell(columnIndex).setValue(FDCHelper.divide(FDCHelper.multiply(e.getValue().toString().replace("%", ""),total), new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
				}
				row.getCell(columnIndex).setValue(row.getCell(columnIndex).getValue());
				int index = Integer.parseInt(table.getColumnKey(columnIndex).substring("split".length()));
				BigDecimal sellArea = entrys.get(index).getSellArea();
				BigDecimal buildArea = entrys.get(index).getBuildArea();
				BigDecimal constructArea = entrys.get(index).getConstructArea();
				if (FDCHelper.toBigDecimal(sellArea).signum() != 0) {
					row.getCell("avg" + index).setValue(FDCHelper.toBigDecimal(newValue).divide(sellArea, 2, BigDecimal.ROUND_HALF_UP));
				}
				if (FDCHelper.toBigDecimal(buildArea).signum() != 0) {
					row.getCell("buildAvg" + index).setValue(FDCHelper.toBigDecimal(newValue).divide(buildArea, 2, BigDecimal.ROUND_HALF_UP));
				}
				if (FDCHelper.toBigDecimal(constructArea).signum() != 0) {
					row.getCell("constructAvg" + index).setValue(FDCHelper.toBigDecimal(newValue).divide(constructArea, 2, BigDecimal.ROUND_HALF_UP));
				}
				setUnionData();
			}
		}
		
		measureCostEditUI.setDataChange(true);
	}

	public Map getSplitTypes() {
		Map map = new HashMap();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			Object value = row.getCell("splitType").getValue();
			String splitType = null;
			if (value instanceof Item) {
				Item item = (Item) value;
				splitType = item.key;
			}
			if (splitType == null)
				continue;

			Object obj = row.getUserObject();
			if (obj instanceof CostAccountInfo) {

				CostAccountInfo acct = (CostAccountInfo) obj;
				if (acct.isIsLeaf() && acct.getType() == CostAccountTypeEnum.SIX) {
					if (splitType.equals("man")) {
						for (int k = 0; k < entrys.size(); k++) {
							if (!entrys.get(k).isIsSplit()) {
								continue;
							}
							// 存在产品添加了，但表结构没有新建的情况，这种情况暂时做简单处理
							if (row.getCell("split" + k) == null)
								break;
							Object v = row.getCell("split" + k).getValue();
							String productId = entrys.get(k).getProduct().getId().toString();
							if (productId == null) {
								break;
							}

							splitType = splitType + "[]" + productId + "|" + FDCHelper.toBigDecimal(v).toString();
						}
					}
					map.put(acct.getId().toString(), splitType);
				}
			}
		}
		return map;
	}

	private void setDefaultSplitType(IRow row) {
		Object obj = row.getUserObject();
		if (obj instanceof CostAccountInfo && ((CostAccountInfo) obj).isIsLeaf()) {
			Object key = getDefaultCostAcctMap().get(((CostAccountInfo) obj).getLongNumber());
			if (key == null) {
				row.getCell("splitType").setValue(null);
			} else {
				row.getCell("splitType").setValue(getSelectItem(key.toString()));
			}
		}
	}

	private HashMap defaultCostAcctMap = null;

	private HashMap getDefaultCostAcctMap() {
		if (defaultCostAcctMap == null) {
			defaultCostAcctMap = new HashMap();
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("costAccount.longNumber");
			view.getSelector().add("splitType");
			try {
				MeasureDefaultSplitTypeSetCollection c = MeasureDefaultSplitTypeSetFactory.getRemoteInstance().getMeasureDefaultSplitTypeSetCollection(view);
				for (Iterator iter = c.iterator(); iter.hasNext();) {
					MeasureDefaultSplitTypeSetInfo info = (MeasureDefaultSplitTypeSetInfo) iter.next();
					defaultCostAcctMap.put(info.getCostAccount().getLongNumber(), info.getSplitType());

				}
			} catch (Exception e) {
				measureCostEditUI.handUIException(e);
			}
		}

		return defaultCostAcctMap;
	}

	private int getProductColumnIndex(String productId) {
		IRow headRow = table.getHeadRow(0);
		for (int i = 0; i < this.getTable().getColumnCount(); i++) {
			if (headRow.getCell(i) != null && headRow.getCell(i).getValue() != null && headRow.getCell(i).getValue().equals(productId)) {
				return i;
			}
		}
		return -1;
	}

	private String getColumnProdcutId(String key) {
		IRow headRow = table.getHeadRow(1);
		if (headRow.getCell(key) != null && headRow.getCell(key).getUserObject() != null) {
			return (String) headRow.getCell(key).getUserObject();
		}
		return null;
	}

	/**
	 * 拆分六类公摊科目
	 * 
	 * @param splitType
	 * @param row
	 */
	private void splitSIX(IRow row) {
		Object value = row.getCell("splitType").getValue();

		if (!(value instanceof Item)) {
			return;
		}

		String splitType = ((Item) value).key;

		value = row.getCell("total").getValue();
		BigDecimal total = FDCHelper.toBigDecimal(value);
		if (total.signum() == 0||splitType==null) {
			for (int i = 0; i < entrys.size(); i++) {
				row.getCell("buildAvg"+i).setValue(null);
				row.getCell("constructAvg"+i).setValue(null);
				row.getCell("avg"+i).setValue(null);
				row.getCell("split"+i).setValue(null);
			}
			row.getCell("constructAvg").setValue(null);
			row.getCell("buildAvg").setValue(null);
			row.getCell("avg").setValue(null);
			row.getCell("total").setValue(null);
			return;
		}
		
		Object obj = row.getCell("splitType").getValue();
		boolean  isManSplit=isManSplit(splitType);
		boolean  isCustomIndex=((Item) obj).isCustomIndex();


		if (isManSplit) {
			manSplit(splitType, row);
			for (int i = 0; i < entrys.size(); i++) {
				row.getCell("buildAvg" + i).setValue(null);
				row.getCell("constructAvg"+i).setValue(null);
				row.getCell("avg" + i).setValue(null);
				row.getCell("split" + i).setValue(null);
			}
		} else if (isCustomIndex) {
			BigDecimal sum = getCustomSumIndex();
			BigDecimal tmp = FDCHelper.ZERO;
			int lastIndex = 0;// 最后一项分摊值
			for (int i = 0; i < entrys.size(); i++) {
				if (!entrys.get(i).isIsSplit()) {
					row.getCell("split" + i).setValue(null);
					row.getCell("avg" + i).setValue(null);
					row.getCell("buildAvg" + i).setValue(null);
					row.getCell("constructAvg"+i).setValue(null);
					if (i == entrys.size() - 1) {
						// 最后一项不分摊的话,将分摊的差值归到之前分摊的最后一项
						BigDecimal dif = total.subtract(tmp);
						if (lastIndex > 0) {
							BigDecimal amount = FDCHelper.toBigDecimal(row.getCell(lastIndex).getValue());
							amount = amount.add(dif);
							row.getCell(lastIndex).setValue(amount);
						}
					}
					continue;
				}
				BigDecimal splitAmt = FDCHelper.ZERO;
				BigDecimal amt = FDCHelper.ZERO;
				if (i == entrys.size() - 1) {
					// 最后一个分摊
					splitAmt = total.subtract(tmp);
				} else {
					lastIndex = table.getColumnIndex("split" + i);
					String productId = entrys.get(i).getProduct().getId().toString();
					amt = getCustomIndex((Item) obj,productId);
					splitAmt = FDCHelper.toBigDecimal(amt).multiply(total).divide(sum, 2, BigDecimal.ROUND_HALF_UP);
					tmp = tmp.add(splitAmt);
				}
				row.getCell("split" + i).setValue(splitAmt);

				amt = FDCHelper.toBigDecimal(entrys.get(i).getSellArea());
				if (amt.compareTo(FDCHelper.ZERO) == 0)
					continue;
				BigDecimal avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
				row.getCell("avg" + i).setValue(avgAmt);

				amt = FDCHelper.toBigDecimal(entrys.get(i).getBuildArea());
				if (amt.compareTo(FDCHelper.ZERO) == 0)
					continue;
				avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
				row.getCell("buildAvg" + i).setValue(avgAmt);
				
				amt = FDCHelper.toBigDecimal(entrys.get(i).getConstructArea());
				if (amt.compareTo(FDCHelper.ZERO) == 0)
					continue;
				avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
				row.getCell("constructAvg" + i).setValue(avgAmt);

			}
		} else {
			BigDecimal sum = getSumArea(splitType);
			if (sum.signum() > 0) {
				BigDecimal tmp = FDCHelper.ZERO;
				int lastIndex = 0;// 最后一项分摊值
				for (int i = 0; i < entrys.size(); i++) {
					if (!entrys.get(i).isIsSplit()) {
						row.getCell("split" + i).setValue(null);
						row.getCell("avg" + i).setValue(null);
						row.getCell("buildAvg" + i).setValue(null);
						row.getCell("constructAvg" + i).setValue(null);
						if (i == entrys.size() - 1) {
							// 最后一项不分摊的话,将分摊的差值归到之前分摊的最后一项
							BigDecimal dif = total.subtract(tmp);
							if (lastIndex > 0) {
								BigDecimal amount = FDCHelper.toBigDecimal(row.getCell(lastIndex).getValue());
								amount = amount.add(dif);
								row.getCell(lastIndex).setValue(amount);
							}
						}
						continue;
					}
					BigDecimal splitAmt = FDCHelper.ZERO;
					BigDecimal amt = FDCHelper.ZERO;
					if (i == entrys.size() - 1) {
						// 最后一个分摊
						splitAmt = total.subtract(tmp);
					} else {
						lastIndex = table.getColumnIndex("split" + i);
						amt = entrys.get(i).getBigDecimal(splitType);
						splitAmt = FDCHelper.toBigDecimal(amt).multiply(total).divide(sum, 2, BigDecimal.ROUND_HALF_UP);
						tmp = tmp.add(splitAmt);
					}
					row.getCell("split" + i).setValue(splitAmt);

					amt = FDCHelper.toBigDecimal(entrys.get(i).getSellArea());
//					if (amt.compareTo(FDCHelper.ZERO) == 0)
//						continue;
//					BigDecimal avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
//					row.getCell("avg" + i).setValue(avgAmt);
					BigDecimal avgAmt=FDCHelper.ZERO;
					if (amt.compareTo(FDCHelper.ZERO) != 0){
						
						avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
						row.getCell("avg" + i).setValue(avgAmt);
					}
					
					amt = FDCHelper.toBigDecimal(entrys.get(i).getBuildArea());
					if (amt.compareTo(FDCHelper.ZERO) == 0)
						continue;
					avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
//					row.getCell("buildAvg" + i).setValue(avgAmt);
					
					if (amt.compareTo(FDCHelper.ZERO) != 0){
						
						avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
						row.getCell("buildAvg" + i).setValue(avgAmt);
					}
					
					amt = FDCHelper.toBigDecimal(entrys.get(i).getConstructArea());
					if (amt.compareTo(FDCHelper.ZERO) == 0)
						continue;
					avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
//					row.getCell("buildAvg" + i).setValue(avgAmt);
					
					if (amt.compareTo(FDCHelper.ZERO) != 0){
						
						avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
						row.getCell("constructAvg" + i).setValue(avgAmt);
					}
					
				}

			} else {
				for (int i = 0; i < entrys.size(); i++) {
					row.getCell("buildAvg" + i).setValue(null);
					row.getCell("constructAvg" + i).setValue(null);
					row.getCell("avg" + i).setValue(null);
					row.getCell("split" + i).setValue(null);
				}
			}
			BigDecimal sumSellArea = getAllSellArea();
			BigDecimal sumBuildArea = getAllBuildArea();
			BigDecimal sumConstructArea = getAllConstructArea();
			if (sumSellArea.signum() != 0) {
				row.getCell("avg").setValue(total.divide(sumSellArea, 2, BigDecimal.ROUND_HALF_UP));
			}
			if (sumBuildArea.signum() != 0) {
				row.getCell("buildAvg").setValue(total.divide(sumBuildArea, 2, BigDecimal.ROUND_HALF_UP));
			}
			if (sumConstructArea.signum() != 0) {
				row.getCell("constructAvg").setValue(total.divide(sumConstructArea, 2, BigDecimal.ROUND_HALF_UP));
			}
		}
			

	}
	
	private void manSplit(String splitType,IRow row){
		String[] split = splitType.split("\\[\\]");
		row.getCell("splitType").setValue(getSelectItem(split[0]));
		if (split.length > 1) {
			HashMap map = new HashMap();
			for (int i = 1; i < split.length; i++) {
				String s[] = split[i].split("\\|");

				if (s.length == 2) {
					map.put(s[0], s[1]);
				}
			}
			for (int i = 0; i < entrys.size(); i++) {
				BigDecimal splitAmt = FDCHelper.toBigDecimal(map.get(getColumnProdcutId("split" + i)));
				row.getCell("split" + i).setValue(splitAmt);
				row.getCell("split" + i).getStyleAttributes().setLocked(false);

				BigDecimal amt = FDCHelper.toBigDecimal(entrys.get(i).getSellArea());
				if (amt.compareTo(FDCHelper.ZERO) == 0)
					continue;
				BigDecimal avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
				row.getCell("avg" + i).setValue(avgAmt);
				
				amt = FDCHelper.toBigDecimal(entrys.get(i).getBuildArea());
				if (amt.compareTo(FDCHelper.ZERO) == 0)
					continue;
				avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
				row.getCell("buildAvg" + i).setValue(avgAmt);
				
				amt = FDCHelper.toBigDecimal(entrys.get(i).getConstructArea());
				if (amt.compareTo(FDCHelper.ZERO) == 0)
					continue;
				avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
				row.getCell("constructAvg" + i).setValue(avgAmt);
			}
		}

		BigDecimal sumSellArea = FDCHelper.toBigDecimal(getAllSellArea());
		BigDecimal sumBuildArea = FDCHelper.toBigDecimal(getAllBuildArea());
		BigDecimal sumconstructArea = FDCHelper.toBigDecimal(getAllConstructArea());
		BigDecimal total = FDCHelper.toBigDecimal(row.getCell("total").getValue());
		if (sumSellArea.compareTo(FDCHelper.ZERO) != 0) {
			row.getCell("avg").setValue(total.divide(sumSellArea, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (sumBuildArea.compareTo(FDCHelper.ZERO) != 0) {
			row.getCell("buildAvg").setValue(total.divide(sumBuildArea, 2, BigDecimal.ROUND_HALF_UP));
		}
		if (sumconstructArea.compareTo(FDCHelper.ZERO) != 0) {
			row.getCell("constructAvg").setValue(total.divide(sumconstructArea, 2, BigDecimal.ROUND_HALF_UP));
		}
		//初始加载及分摊方式改变的时候统一设置状态
		for (int i = 0; i < entrys.size(); i++) {
			//查看状态不能修改
			String oprtState = measureCostEditUI.getOprtState();
			if (oprtState!=null&&!oprtState.equals(OprtState.ADDNEW) && !oprtState.equals(OprtState.EDIT)) {
				row.getCell("split" + i).getStyleAttributes().setLocked(true);
				continue;
			}
			if (!entrys.get(i).isIsSplit()) {
				row.getCell("split" + i).getStyleAttributes().setLocked(true);
			}else{
				row.getCell("split" + i).getStyleAttributes().setLocked(false);
			}
		}
	}
	
	private boolean isManSplit(String splitType){
		return splitType.startsWith("man");
	}
	
	private String[] getColumns(){
		int size=1;
		if(entrys!=null&&entrys.size()>0){
			size=size+entrys.size();
		}
		String[] columns=new String[size];
		columns[0]="total";
		for(int i=1;i<size;i++){
			columns[i]="split"+(i-1);
		}
		
		return columns;
	}
	
	void clear(){
		EventListener[] listeners = this.table.getListeners(KDTEditListener.class);
		for(int i=0;i<listeners.length;i++){
			this.table.removeKDTEditListener((KDTEditListener)listeners[i]);
		}
		
		listeners = this.table.getListeners(KDTMouseListener.class);
		for(int i=0;i<listeners.length;i++){
			this.table.removeKDTMouseListener((KDTMouseListener)listeners[i]);
		}
		this.table.setBeforeAction(null);
		this.table.setAfterAction(null);
		this.table.removeAll();
		this.editor=null;
		this.table=null;
		this.measureCostEditUI=null;
		this.planIndexInfo = null;
		this.entrys = null;
	}
}
