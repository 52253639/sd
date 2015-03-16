/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.ProjectCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProjectCostAnalysisUI extends AbstractProjectCostAnalysisUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectCostAnalysisUI.class);
    
    // 科目下一列
	private int base = 2;

	// 组合列-列数
	private int columnSize = 3;

	// 初始化数据
	private RetValue retValue;

	// 目标成本测算
	private MeasureCostCollection measureCosts;

	// 目标成本修订
	private AimCostCollection aimCosts;
	
	// 最终版本项目可售面积
	private BigDecimal allSellArea = FDCHelper.ZERO;

	// 最终版本项目建筑面积
	private BigDecimal allBuildArea = FDCHelper.ZERO;;
	
	// 动态指标
	private BigDecimal sellAreaValue = FDCHelper.ZERO;
	private BigDecimal buildAreaValue= FDCHelper.ZERO;
	
	// 测算最终版本列
	private int measureIndex;
	// 修订最终版本列
	private int reverseIndex;
	
    /**
     * output class constructor
     */
    public ProjectCostAnalysisUI() throws Exception
    {
        super();
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
		LongTimeDialog dialog = UITools.getDialog(this);
		if (dialog == null)
			return;
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				String selectObjId = getSelectObjId();
				if (selectObjId == null || !isSelectLeafPrj()) {
					tblMain.removeColumns();
					return null;
				}
				clear();
				fetchData(selectObjId);
				resetTableHeadColumn();
				fillTable();
				return null;
			}

			public void afterExec(Object result) throws Exception {

			}
		});
		if (dialog != null)
			dialog.show();
    }

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}
	protected void fillTable() throws Exception {
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
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode(table, (DefaultMutableTreeNode) root.getChildAt(i));
		}
		setUnionData();
	}
	private void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			MsgBox.showError("too many costAccount level!");
			return;
		}
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getStyleAttributes().setLocked(true);
		String longNumber = costAcct.getLongNumber();
		row.getCell("acctNumber").setValue(longNumber.replace('!', '.'));
		row.getCell("acctName").setValue(costAcct.getName());
		RetValue costValues = (RetValue)retValue.get("costValues");
		if (node.isLeaf()) {
			row.setUserObject(costAcct);
			for (int i = 0; i < measureCosts.size(); i++) {
				MeasureCostInfo measureCost = measureCosts.get(i);
				if(measureCost.getId()==null){
					continue;
				}
				BigDecimal amount = (BigDecimal) getMeasureEntrys().get(
						measureCost.getId().toString() + longNumber);
				row.getCell("measureCost" + i).setValue(amount);
				BigDecimal buildArea = (BigDecimal)tblMain.getColumn(row.getCell("buildAmt" + i).getColumnIndex()).getUserObject();
				BigDecimal sellArea = (BigDecimal)tblMain.getColumn(row.getCell("sellAmt" + i).getColumnIndex()).getUserObject();
				
				BigDecimal buildAmt = FDCHelper.ZERO;
				if (FDCHelper.toBigDecimal(buildArea).signum() != 0) {
					buildAmt = FDCHelper.divide(amount,buildArea, 2,
							BigDecimal.ROUND_HALF_UP);
				}
				if (buildAmt.signum() == 0) {
					buildAmt = null;
				}
				row.getCell("buildAmt" + i).setValue(buildAmt);
				BigDecimal sellAmt = FDCHelper.ZERO;
				if (FDCHelper.toBigDecimal(sellArea).signum() != 0) {
					sellAmt = FDCHelper.divide(amount, sellArea, 2,
							BigDecimal.ROUND_HALF_UP);
				}
				if (sellAmt.signum() == 0) {
					sellAmt = null;
				}
				row.getCell("sellAmt" + i).setValue(sellAmt);
			}
			for (int i = 0; i < aimCosts.size(); i++) {
				AimCostInfo aimCost = aimCosts.get(i);
				if(aimCost.getId()==null){
					continue;
				}
				String key = "aimCost" + i + i;
				BigDecimal amount = (BigDecimal) getCostEntrys().get(
						aimCost.getId().toString() + longNumber);
				row.getCell(key).setValue(amount);
				
				BigDecimal buildArea = allSellArea;
				BigDecimal sellArea = allBuildArea;
				
				BigDecimal buildAmt = FDCHelper.ZERO;
				if (FDCHelper.toBigDecimal(buildArea).signum() != 0) {
					buildAmt = FDCHelper.divide(amount, buildArea, 2,
							BigDecimal.ROUND_HALF_UP);
				}
				if(buildAmt.signum()==0){
					buildAmt = null;
				}
				row.getCell("buildAmt" + i + i).setValue(buildAmt);
				BigDecimal sellAmt = FDCHelper.ZERO;
				if (FDCHelper.toBigDecimal(sellArea).signum() != 0) {
					sellAmt = FDCHelper.divide(amount, sellArea, 2,
							BigDecimal.ROUND_HALF_UP);
				}
				if (sellAmt.signum() == 0) {
					sellAmt = null;
				}
				row.getCell("sellAmt" + i + i).setValue(sellAmt);
			}
			RetValue costValue = (RetValue)costValues.get(longNumber);
			
			if(costValue!=null){
				// 动态成本
				BigDecimal dynCost = costValue.getBigDecimal("dynCost");
				if(FDCHelper.toBigDecimal(dynCost).signum()==0){
					dynCost = null;
				}
				row.getCell("dynCost").setValue(dynCost);
				
				BigDecimal buildAmt = FDCHelper.ZERO;
				if (FDCHelper.toBigDecimal(buildAreaValue).signum() != 0) {
					buildAmt = FDCHelper.divide(dynCost, buildAreaValue, 2,
							BigDecimal.ROUND_HALF_UP);
				}
				if(buildAmt.signum()==0){
					buildAmt = null;
				}
				row.getCell("dynBuildAmt").setValue(buildAmt);
				BigDecimal sellAmt = FDCHelper.ZERO;
				if (FDCHelper.toBigDecimal(sellAreaValue).signum() != 0) {
					sellAmt = FDCHelper.divide(dynCost, sellAreaValue, 2,
							BigDecimal.ROUND_HALF_UP);
				}
				if (sellAmt.signum() == 0) {
					sellAmt = null;
				}
				row.getCell("dynSellAmt").setValue(sellAmt);
				row.getCell("rate_aimCost").setValue(costValue.getBigDecimal("rate_aimCost"));
				row.getCell("rate_dyn_reverse").setValue(costValue.getBigDecimal("rate_dyn_reverse"));
				row.getCell("rate_dyn_measure").setValue(costValue.getBigDecimal("rate_dyn_measure"));
			}
			
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
		}
	}
	
	protected void setUnionData() {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		/*String measureId = "";
		String reverseId = "";
		MeasureCostInfo lastMeasure = (MeasureCostInfo) table.getHeadRow(0)
				.getUserObject();
		if (lastMeasure != null) {
			measureId = lastMeasure.getId().toString();
		}
		AimCostInfo lastReverse = (AimCostInfo) table.getHeadRow(1)
				.getUserObject();
		if (lastReverse != null) {
			reverseId = lastReverse.getId().toString();
		}*/

		for (int i = 0; i < measureCosts.size(); i++) {
			amountColumns.add("measureCost" + i);
			// 增加build*,sell*是为了计算下级向上级汇总
			amountColumns.add("buildAmt" + i); 
			amountColumns.add("sellAmt" +i);
		}
		for(int i=0;i<aimCosts.size();i++){
			amountColumns.add("aimCost" + i+i);
			amountColumns.add("buildAmt" + i+i);
			amountColumns.add("sellAmt" +i+i);
		}
		amountColumns.add("dynCost");
		amountColumns.add("dynBuildAmt");
		amountColumns.add("dynSellAmt");
		
		amountColumns.add("rate_aimCost");
		amountColumns.add("rate_dyn_reverse");
		amountColumns.add("rate_dyn_measure");
		
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
					// 是否需要向上汇总,单方及比率单独计算不需要向上汇总
					boolean isUnionUp = false;
					
					if(colName.startsWith("buildAmt") || colName.startsWith("dynBuildAmt")){
						// 此报报表是动态列，不支持设置格式，所以可通过列索引确定
						int idx =  row.getCell(colName).getColumnIndex()-1;
						Object allCost = row.getCell(idx).getValue();
						BigDecimal buildArea = (BigDecimal)tblMain.getColumn(row.getCell(colName).getColumnIndex()).getUserObject();
						if(colName.startsWith("dynBuildAmt")){
							buildArea = buildAreaValue;
						}
						if(allCost != null && FDCHelper.toBigDecimal(buildArea).compareTo(FDCHelper.ZERO) != 0){
							amount = FDCHelper.toBigDecimal(allCost).divide(buildArea, 2, BigDecimal.ROUND_HALF_UP);
						}
						isUnionUp = true;
					}
					if(colName.startsWith("sellAmt") || colName.startsWith("dynSellAmt")){
						int idx =  row.getCell(colName).getColumnIndex()-2;
						Object allCost = row.getCell(idx).getValue();
						BigDecimal sellArea = (BigDecimal)tblMain.getColumn(row.getCell(colName).getColumnIndex()).getUserObject();
						if(colName.startsWith("dynSellAmt")){
							sellArea = sellAreaValue;
						}
						if(allCost != null && FDCHelper.toBigDecimal(sellArea).compareTo(FDCHelper.ZERO) != 0){
							amount = FDCHelper.toBigDecimal(allCost).divide(sellArea, 2, BigDecimal.ROUND_HALF_UP);
						}
						isUnionUp = true;
					}
					if(measureIndex!=0){ // 没有最终版本目标成本测算，则没有目标成本(修订)
						BigDecimal lastMeasureAmt = FDCHelper.toBigDecimal(row.getCell(measureIndex).getValue());
						BigDecimal lastReverseAmt = FDCHelper.toBigDecimal(reverseIndex==0?FDCHelper.ZERO:row.getCell(reverseIndex).getValue());
						BigDecimal dynCost = FDCHelper.toBigDecimal(row.getCell("dynCost").getValue());
						if(colName.equals("rate_aimCost")){
							amount = getDifRate(lastReverseAmt, lastMeasureAmt);
							isUnionUp = true;
						}
						if(colName.equals("rate_dyn_reverse")){
							amount = getDifRate(dynCost, lastReverseAmt);
							isUnionUp = true;
						}
						if(colName.equals("rate_dyn_measure")){
							amount = getDifRate(dynCost, lastMeasureAmt);
							isUnionUp = true;
						}
					}
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null&&!isUnionUp) {
							amount = amount.add(FDCHelper.toBigDecimal(value));
						}
					}
					if(FDCHelper.toBigDecimal(amount).signum()==0){
						if(!colName.startsWith("rate")){
							amount = null;
						}
					}
					row.getCell(colName).setValue(amount);
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
		
		// 直接取：IRow footRow=table.getFootRow(0);报错 20091218反馈处理
		KDTFootManager footRowManager= table.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){
			String total=AimCostClientHelper.getRes("totalCost");
			footRowManager = new KDTFootManager(table);
			footRowManager.addFootView();
			table.setFootManager(footRowManager);
			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			table.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			table.getIndexColumn().setWidth(60);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			//设置到第一个可视行
			footRowManager.addIndexText(0, total);
		}else{
			footRow=table.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=table.addFootRow(1);
			};
		}
		
		BigDecimal dynCost = FDCHelper.toBigDecimal(footRow.getCell("dynCost").getValue());
		BigDecimal buildAmt = FDCHelper.ZERO;
		if (FDCHelper.toBigDecimal(buildAreaValue).signum() != 0) {
			buildAmt = FDCHelper.divide(dynCost, buildAreaValue, 2,
					BigDecimal.ROUND_HALF_UP);
		}
		if(buildAmt.signum()==0){
			buildAmt = null;
		}
		footRow.getCell("dynBuildAmt").setValue(buildAmt);
		BigDecimal sellAmt = FDCHelper.ZERO;
		if (FDCHelper.toBigDecimal(sellAreaValue).signum() != 0) {
			sellAmt = FDCHelper.divide(dynCost, sellAreaValue, 2,
					BigDecimal.ROUND_HALF_UP);
		}
		if (sellAmt.signum() == 0) {
			sellAmt = null;
		}
		footRow.getCell("dynSellAmt").setValue(sellAmt);
		
		BigDecimal lastMeasureAmt = FDCHelper.toBigDecimal(footRow.getCell(measureIndex).getValue());
		BigDecimal lastReverseAmt = FDCHelper.toBigDecimal(reverseIndex==0?FDCHelper.ZERO:footRow.getCell(reverseIndex).getValue());
		footRow.getCell("rate_aimCost").setValue(getDifRate(lastReverseAmt, lastMeasureAmt));
		footRow.getCell("rate_dyn_reverse").setValue(getDifRate(dynCost, lastReverseAmt));
		footRow.getCell("rate_dyn_measure").setValue(getDifRate(dynCost, lastMeasureAmt));
	}

	private BigDecimal getDifRate(BigDecimal obj1, BigDecimal obj2){
		BigDecimal difAmt = FDCHelper.subtract(obj1, obj2);
		if(FDCHelper.toBigDecimal(obj2).signum()!=0){
			return FDCHelper.divide(difAmt, obj2, 4, BigDecimal.ROUND_HALF_UP);
		}
		return null;
		
	}
	protected void fetchData(String prjId) throws Exception {
		System.out.println("==============================================: "+new java.util.Date());
		ParamValue paramValue = new ParamValue();
		Set leafPrjIDs = new HashSet();
		leafPrjIDs.add(prjId);
		paramValue.put("leafPrjIDs", leafPrjIDs);
		retValue = ProjectCostRptFacadeFactory.getRemoteInstance().getProjectCostAnalysis(paramValue);
		
		// 指标
		Map indexValues = ProjectHelper.getIndexValueByProjectId(null,prjId);
		allBuildArea = FDCHelper.toBigDecimal(indexValues
				.get(ApportionTypeInfo.buildAreaType
						+ ProjectStageEnum.AIMCOST_VALUE), 2);
		allSellArea = FDCHelper.toBigDecimal(indexValues
				.get(ApportionTypeInfo.sellAreaType
						+ ProjectStageEnum.AIMCOST_VALUE), 2);
		RetValue areaValue = (RetValue)retValue.get("areaValue");
		sellAreaValue = FDCHelper.toBigDecimal(areaValue.getBigDecimal(ApportionTypeInfo.sellAreaType));
		buildAreaValue= FDCHelper.toBigDecimal(areaValue.getBigDecimal(ApportionTypeInfo.buildAreaType));
		System.out.println("==============================================: "+new java.util.Date());
	}
	private Map getMeasureEntrys(){
		return (Map)retValue.get("measureEntrysMap");
	}
	
	private Map getCostEntrys(){
		return (Map)retValue.get("costEntrysMap");
	}
	
	protected void initTable() {
		KDTable table = this.tblMain;
		table.checkParsed();
		table.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getViewManager().setFreezeView(-1, 2);
		table.setRefresh(false);
		tblMain.addHeadRow(1, (IRow) tblMain.getHeadRow(0).clone());
		for (int i = 0; i < tblMain.getColumnCount(); i++) {
			tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
		tblMain.setColumnMoveable(true);
	}

	private void resetTableHeadColumn() {
		KDTable table = this.tblMain;
		table.removeColumns();
		table.createBlankTable(base, 2, 0);
		table.getColumn(0).setKey("acctNumber");
		table.getColumn(1).setKey("acctName");
		IRow headRow = table.getHeadRow(0);
		headRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		headRow.getCell("acctNumber").setValue("科目编码");
		headRow.getCell("acctName").setValue("科目名称");
		headRow = table.getHeadRow(1);
		headRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		headRow.getCell("acctNumber").setValue("科目编码");
		headRow.getCell("acctName").setValue("科目名称");
		measureCosts = (MeasureCostCollection) retValue.get("measureCosts");
		FDCHelper.sortObjectCollection((AbstractObjectCollection)measureCosts,new Comparator(){
			
			public int compare(Object o1, Object o2) {
				if(o1==null&&o2==null){
					return 0;
				}
				if(o1!=null&&o2==null){
					return 1;
				}
				if(o1==null&&o2!=null){
					return -1;
				}
				MeasureCostInfo info1 = (MeasureCostInfo)o1;
				MeasureCostInfo info2 = (MeasureCostInfo)o2;
				return compareNumber(info1,info2);
			}
			private int compareNumber(MeasureCostInfo info1 ,MeasureCostInfo info2){
				BigDecimal curNumber1 = FDCHelper.toBigDecimal(info1.getVersionNumber());
				BigDecimal curNumber2 = FDCHelper.toBigDecimal(info2.getVersionNumber());
				if(curNumber1.compareTo(curNumber2) >0){
					return 1;
				}
				if(curNumber1.compareTo(curNumber2) <0){
					return -1;
				}
				return 0;
			}
		});
		aimCosts = (AimCostCollection) retValue.get("aimCosts");
		FDCHelper.sortObjectCollection((AbstractObjectCollection)aimCosts,new Comparator(){
			
			public int compare(Object o1, Object o2) {
				if(o1==null&&o2==null){
					return 0;
				}
				if(o1!=null&&o2==null){
					return 1;
				}
				if(o1==null&&o2!=null){
					return -1;
				}
				AimCostInfo info1 = (AimCostInfo)o1;
				AimCostInfo info2 = (AimCostInfo)o2;
				return compareNumber(info1,info2);
			}
			private int compareNumber(AimCostInfo info1 ,AimCostInfo info2){
				BigDecimal curNumber1 = FDCHelper.toBigDecimal(info1.getVersionNumber());
				BigDecimal curNumber2 = FDCHelper.toBigDecimal(info2.getVersionNumber());
				if(curNumber1.compareTo(curNumber2) >0){
					return 1;
				}
				if(curNumber1.compareTo(curNumber2) <0){
					return -1;
				}
				return 0;
			}
		});
		RetValue indexValues = (RetValue)retValue.get("indexValues");
		
		int addSize = (measureCosts.size()+aimCosts.size()) * 3;
		table.addColumns(addSize);
		StringBuffer title = null;
		for (int i = 0; i < measureCosts.size(); i++) {
			MeasureCostInfo head = measureCosts.get(i);
			if(head.isIsLastVersion()){
				table.getHeadRow(0).setUserObject(head);
				measureIndex = base + i * 3 + 0;
			}
			title = new StringBuffer("目标成本测算");
			if(head!=null&&head.getVersionNumber()!=null){
				String number = head.getVersionNumber();
				title.append("-");
				title.append(number.replace('!', '.'));
				if(head.getVersionName()!=null){
					title.append("-");
					title.append(head.getVersionName());
				}
				RetValue indexValue = (RetValue)indexValues.get(head.getId().toString());
				if(indexValue!=null){
					table.getColumn(base + i * 3 + 1).setUserObject(indexValue.getBigDecimal("buildArea"));
					table.getColumn(base + i * 3 + 2).setUserObject(indexValue.getBigDecimal("sellArea"));
				}else{
					
				}
			}
			for (int j = 0; j < 3; j++) {
				table.getHeadRow(0).getCell(base + i * 3 + j).setValue(
						title.toString());
			}
			table.getColumn(base + i * 3 + 0).setKey("measureCost" + i);
			table.getColumn(base + i * 3 + 1).setKey("buildAmt" + i);
			table.getColumn(base + i * 3 + 2).setKey("sellAmt" + i);
			table.getHeadRow(1).getCell(base + i * 3 + 0).setValue("目标成本");
			table.getHeadRow(1).getCell(base + i * 3 + 1).setValue("建筑单方");
			table.getHeadRow(1).getCell(base + i * 3 + 2).setValue("可售单方");

		}
		
		for (int i = 0; i < aimCosts.size(); i++) {
			AimCostInfo aimCost = aimCosts.get(i);
			if(aimCost.isIsLastVersion()){
				table.getHeadRow(1).setUserObject(aimCost);
				reverseIndex = base + measureCosts.size()*3 + i * 3 + 0;
			}
			title = new StringBuffer("目标成本修订");
			if(aimCost!=null&&aimCost.getVersionNumber()!=null){
				title.append("-");
				title.append(aimCost.getVersionNumber().replace('!', '.'));
				if(aimCost.getVersionName()!=null){
					title.append("-");
					title.append(aimCost.getVersionName());
				}
				// 取最终版本
				table.getColumn(base + measureCosts.size()*3+ i * 3 + 1).setUserObject(allBuildArea);
				table.getColumn(base + measureCosts.size()*3 + i * 3 + 2).setUserObject(allSellArea);
				
			}
			for (int j = 0; j < 3; j++) {
				table.getHeadRow(0).getCell(base + measureCosts.size()*3 + i * 3 + j).setValue(
						title.toString());
			}
			table.getColumn(base + measureCosts.size()*3+ i * 3 + 0).setKey("aimCost" + i + i);
			table.getColumn(base + measureCosts.size()*3 + i * 3 + 1).setKey("buildAmt" + i + i);
			table.getColumn(base + measureCosts.size()*3 + i * 3 + 2).setKey("sellAmt" + i + i);
			table.getHeadRow(1).getCell(base + measureCosts.size()*3 + i * 3 + 0).setValue("目标成本");
			table.getHeadRow(1).getCell(base + measureCosts.size()*3+ i * 3 + 1).setValue("建筑单方");
			table.getHeadRow(1).getCell(base + measureCosts.size()*3 + i * 3 + 2).setValue("可售单方");
			
		}
		
		// 动态成本
		IColumn column = table.addColumn();
		column.setKey("dynCost");
		column = table.addColumn();
		column.setKey("dynBuildAmt");
		column = table.addColumn();
		column.setKey("dynSellAmt");
		
		// 比率
		column = table.addColumn();
		column.setKey("rate_aimCost");
		column = table.addColumn();
		column.setKey("rate_dyn_reverse");
		column = table.addColumn();
		column.setKey("rate_dyn_measure");
		
		for (int i = 0; i < columnSize; i++) {
			table.getHeadRow(0).getCell(base + addSize + i).setValue("动态成本");
		}
		for (int i = 0; i < columnSize; i++) {
			table.getHeadRow(0).getCell(base + addSize + columnSize + i).setValue("比率");
		}
		FDCHelper.formatTableNumber(table, base, table.getColumnCount()-4);
		KDTMergeManager mm = table.getHeadMergeManager();
		mm.mergeBlock(0, 0, 0, table.getColumnCount()-1,KDTMergeManager.FREE_MERGE);
		mm.mergeBlock(0, 0, 1, table.getColumnCount()-1,KDTMergeManager.FREE_MERGE);
		column = table.addColumn();
		column.setKey("id");
		column.getStyleAttributes().setHided(true);
		table.getHeadRow(1).getCell("dynCost").setValue("动态成本");
		table.getHeadRow(1).getCell("dynBuildAmt").setValue("建筑单方");
		table.getHeadRow(1).getCell("dynSellAmt").setValue("可售单方");
		
		table.getHeadRow(1).getCell("rate_aimCost").setValue("目标成本修订-目标成本测算变化比率");
		table.getHeadRow(1).getCell("rate_dyn_reverse").setValue("动态成本-目标成本修订变化比率");
		table.getHeadRow(1).getCell("rate_dyn_measure").setValue("动态成本-目标成本测算变化比率");
		
		FDCHelper.formatTableNumber(tblMain, "rate_aimCost","##0.00%");
		FDCHelper.formatTableNumber(tblMain, "rate_dyn_reverse","##0.00%");
		FDCHelper.formatTableNumber(tblMain, "rate_dyn_measure","##0.00%");
	}
	public void onLoad() throws Exception {
		super.onLoad();
		
		actionAddNew.setEnabled(false);
		actionAddNew.setVisible(false);
		actionEdit.setEnabled(false);
		actionEdit.setVisible(false);
		actionRemove.setEnabled(false);
		actionRemove.setVisible(false);
		actionQuery.setEnabled(false);
		actionQuery.setVisible(false);
		actionLocate.setEnabled(false);
		actionLocate.setVisible(false);
		actionView.setEnabled(false);
		actionView.setVisible(false);
		
		menuEdit.setEnabled(false);
		menuEdit.setVisible(false);
		
		tblMain.removeColumns();
	}
	
	protected String getKeyFieldName() {
		// TODO Auto-generated method stub
		return super.getKeyFieldName();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	}
	
	/**
	 * 清除缓存数据 
	 */
	private void clear() {
		if (retValue != null) {
			retValue.clear();
		}
		if (measureCosts != null) {
			measureCosts.clear();
		}
		if (aimCosts != null) {
			aimCosts.clear();
		}
		retValue = null;
		measureCosts = null;
		aimCosts = null;

		if (measureIndex > 0) {
			measureIndex = 0;
		}
		if (reverseIndex > 0) {
			reverseIndex = 0;
		}
		allBuildArea = FDCHelper.ZERO;
		allSellArea = FDCHelper.ZERO;
	}
	
	public int getRowCountFromDB() {
//		super.getRowCountFromDB();
		return -1;
	}
}