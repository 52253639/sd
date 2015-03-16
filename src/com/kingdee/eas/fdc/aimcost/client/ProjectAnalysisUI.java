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

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 工程项目分析表
 * @author pengwei_hou
 * @date 2009-07-16
 */
public class ProjectAnalysisUI extends AbstractProjectAnalysisUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectAnalysisUI.class);
    
    /** 接口中严格规定了调用initTable()导致子类第一次加载时没有数据 */
	private boolean isFirstLoad = true;

	/** 初始化数据 */
	private Map initMap = new HashMap();

	/** 科目 */
	private Map acctMap = null;

	/** 工程项目 */
	private CurProjectCollection CurProjectColls = null;

	/** 第一版已审批目标成本数据 */
	private Map firstAimCostData = null;

	/** 最新版已审批目标成本数据 */
	private Map lastAimCostData = null;

	/** 合同执行情况表数据 */
	private Map conInfosData = null;

	/** 合同类型 */
	private Map conTypeData = null;

	private int acctEndIndex = 0;

	/** 科目编码列 */
	private int numberIndex = 2;

	/** 科目名称列 */
	private int nameIndex = 3;

	/** 动态金额列第一列 */
	private int dynIndex = 4;

	private int maxLevel = 0;
	
    /**
     * output class constructor
     */
    public ProjectAnalysisUI() throws Exception
    {
        super();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	
        super.tblMain_tableClicked(e);
        
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
//    	super.tblMain_tableSelectChanged(e);
    }
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionView_actionPerformed(e);
    }

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionRefresh_actionPerformed(e);
    	fetchData();
		initTable();
		fillAcct(getMainTable());
		fillConInfos(getMainTable());
    }

	protected void initTable(){
		KDTMergeManager  mm = getMainTable().getMergeManager();
		getMainTable().checkParsed(true);
		getMainTable().addColumns(4);
		getMainTable().getColumn(1).setKey("id");
		getMainTable().getColumn(numberIndex).setKey("acctNumber");
		getMainTable().getColumn(nameIndex).setKey("acctName");
		
		//R110519-0372：工程项目分析表的前两列需要冻结
		tblMain.getViewManager().freeze(-1, dynIndex);
		
		for (int j=0;j<CurProjectColls.size();j++) {
			CurProjectInfo info = CurProjectColls.get(j);
			for(int i=0;i<5;i++){
				IColumn column = getMainTable().addColumn();
				int colIdx = column.getColumnIndex();
				getMainTable().getHeadRow(0).getCell(colIdx).setValue(info.getName());
				String projId = info.getId().toString();
				column.setKey(projId+i);
			}
			
		}
		for(int i=0;i<5;i++){
			IColumn column = getMainTable().addColumn();
			int colIdx = column.getColumnIndex();
			getMainTable().getHeadRow(0).getCell(colIdx).setValue("合计");
			column.setKey("total"+i);
		}
		IRow row = getMainTable().addRow();
		row.getCell("id").setValue("概算总金额");
		row.getCell("acctNumber").setValue("概算总金额");
		row.getCell("acctName").setValue("概算总金额");
		mm.mergeBlock(row.getRowIndex(), numberIndex, row.getRowIndex(), nameIndex);
		row = getMainTable().addRow();
		row.getCell("acctNumber").setValue("科目编码");
		row.getCell("acctName").setValue("科目名称");
		row.getCell(numberIndex).setValue("项目名称");
		row.getCell(nameIndex).setValue("项目名称");
		row.getStyleAttributes().setBackground(getMainTable().getHeadRow(0).getStyleAttributes().getBackground());
		int rowIndex = row.getRowIndex();
		for(int i=dynIndex;i<getMainTable().getColumnCount();i=i+5){
			row.getCell(i).setValue("原始金额");
			row.getCell(i+1).setValue("原始金额");
			row.getCell(i+2).setValue("修订金额");
			row.getCell(i+3).setValue("修订金额");
			row.getCell(i+4).setValue("补充金额");
			mm.mergeBlock(rowIndex-1, i, rowIndex-1, i+4);
			mm.mergeBlock(rowIndex, i, rowIndex, i+1);
			mm.mergeBlock(rowIndex, i+2, rowIndex, i+3);
		}
		FDCHelper.formatTableNumber(getMainTable(), dynIndex, getMainTable().getColumnCount());
		getMainTable().getHeadMergeManager().mergeBlock(0, 0, 0, getMainTable().getColumnCount()-1,KDTMergeManager.FREE_MERGE);
		getMainTable().getColumn("id").getStyleAttributes().setHided(true);
		getMainTable().getStyleAttributes().setLocked(true);
		getMainTable().getColumn(0).setKey("item");
		getMainTable().getColumn(0).setWidth(25);
		//TODO 文字垂直怎么搞啊
//		getMainTable().getColumn(0).getStyleAttributes().setVerticalAlign(VerticalAlignment.TOP);
		getMainTable().getColumn(0).getStyleAttributes().setWrapText(true);

	}

	protected void fetchData() throws Exception{
		Map param = new HashMap();
		Map nullMap = new HashMap();
		//TODO 当前组织下的所有工程项目
		//初始化数据
		initMap = FDCCostRptFacadeFactory.getRemoteInstance().getProjectAnalysisCost(param);
		if(initMap.get("projectData")!=null){
			CurProjectColls =  (CurProjectCollection) initMap.get("projectData");
		}else{
			CurProjectColls = new CurProjectCollection();
		}
		if(initMap.get("firstAimCostData")!=null){
			firstAimCostData = (Map)initMap.get("firstAimCostData");
		}else{
			firstAimCostData = nullMap;
		}
		if(initMap.get("lastAimCostData")!=null){
			lastAimCostData = (Map)initMap.get("lastAimCostData");
		}else{
			lastAimCostData = nullMap;
		}
		if(initMap.get("conInfosData")!=null){
			conInfosData = (Map)initMap.get("conInfosData");
		}else{
			conInfosData = nullMap;
		}
	}
	public void onLoad() throws Exception{
		super.onLoad();
		fetchData();
		initTable();
		fillAcct(getMainTable());
		fillConInfos(getMainTable());
		this.actionExport.setEnabled(true);
		tHelper.getDisabledTables().add(tblMain);
	}

	private void fillAcct(KDTable table) {
//		Set idSet = initProjectIDSet();
//		idSet.remove("colls");
		FilterInfo acctFilter = new FilterInfo();
		//经时小鸿，杨晓确定报表科目应取集团级
		acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",OrgConstants.DEF_CU_ID));
//		acctFilter.getFilterItems().add(
//				new FilterItemInfo("fullOrgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString()));
//		acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id",idSet, CompareType.INCLUDE));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnterDB", new Integer(1)));
		TreeModel costAcctTree = null;
		try {
			// 根据当前指定项目条件构造科目树
			costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory
					.getRemoteInstance(), acctFilter);
			 this.initAcct(acctFilter);

			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
					.getRoot();
			Enumeration childrens = root.depthFirstEnumeration();
			while (childrens.hasMoreElements()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
						.nextElement();
				if (node.getUserObject() != null && node.getLevel() > maxLevel) {
					maxLevel = node.getLevel();
				}
			}
			table.getTreeColumn().setDepth(maxLevel);
			 for (int i = 0; i < root.getChildCount(); i++) {
				 fillNode((DefaultMutableTreeNode) root.getChildAt(i), table);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		acctEndIndex = table.getRowCount();
		setUnionData();
	}

	private void fillNode(DefaultMutableTreeNode node, KDTable table)
			throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}

		String acctId = costAcct.getId().toString();
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("item").setValue("概算");
		row.getCell("id").setValue(acctId);
		row.getCell("acctNumber").setValue(
				costAcct.getLongNumber().replace('!', '.'));
		row.getCell("acctName").setValue(costAcct.getName());
		int columnCount = table.getColumnCount();
		KDTMergeManager  mm = getMainTable().getMergeManager();
		BigDecimal firstAmtTotal = FDCHelper.ZERO;
		BigDecimal lastAmtTotal = FDCHelper.ZERO;
		for (int i = dynIndex; i < columnCount-5; i=i+5) {
			if (node.isLeaf()) {
				row.setUserObject(costAcct);
				
					String columnKey = table.getColumn(i).getKey();
					int length = columnKey.length();
					String projectID = columnKey.substring(0, length-1);
					String key = costAcct.getLongNumber()+"_"+projectID;
					//原始金额
					BigDecimal firstAmt = (BigDecimal) this.firstAimCostData.get(key);
					row.getCell(i).setValue(firstAmt);
					row.getCell(i+1).setValue(firstAmt);
					//修订金额
					BigDecimal lastAmt = (BigDecimal) this.lastAimCostData.get(key);
					row.getCell(i+2).setValue(lastAmt);
					row.getCell(i+3).setValue(lastAmt);
					//补充金额列 = 修订金额 - 原始金额
					BigDecimal supplyAmt = FDCHelper.subtract(lastAmt, firstAmt);
					row.getCell(i+4).setValue(supplyAmt);
					
					firstAmtTotal = FDCHelper.add(firstAmtTotal, firstAmt);
					lastAmtTotal = FDCHelper.add(lastAmtTotal, lastAmt);
					row.getCell("total0").setValue(firstAmtTotal);
					row.getCell("total1").setValue(firstAmtTotal);
					row.getCell("total2").setValue(lastAmtTotal);
					row.getCell("total3").setValue(lastAmtTotal);
					row.getCell("total4").setValue(FDCHelper.subtract(lastAmtTotal, firstAmtTotal));
					row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			}
			mm.mergeBlock(row.getRowIndex(), i, row.getRowIndex(), i+1, KDTMergeManager.FREE_COLUMN_MERGE);
			mm.mergeBlock(row.getRowIndex(), i+2, row.getRowIndex(), i+3,KDTMergeManager.FREE_COLUMN_MERGE);
		}
		for(int i=table.getColumnCount()-5;i<table.getColumnCount();i++){
			mm.mergeBlock(row.getRowIndex(), i, row.getRowIndex(), i+1, KDTMergeManager.FREE_COLUMN_MERGE);
			mm.mergeBlock(row.getRowIndex(), i+2, row.getRowIndex(), i+3,KDTMergeManager.FREE_COLUMN_MERGE);
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode((DefaultMutableTreeNode) node.getChildAt(i), table);
		}
	}

	private void fillConInfos(KDTable table) {
		initConTypeTable(table);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		TreeModel conTypeTree = null;
		try {
			// 根据当前指定项目条件构造树
			conTypeTree = FDCClientHelper.createDataTree(ContractTypeFactory.getRemoteInstance(), filter,"合同类型");
			initConType(filter);
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) conTypeTree
					.getRoot();
			Enumeration childrens = root.depthFirstEnumeration();
			while (childrens.hasMoreElements()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
						.nextElement();
				if (node.getUserObject() != null && node.getLevel() > maxLevel) {
					maxLevel = node.getLevel();
				}
			}
			table.getTreeColumn().setDepth(maxLevel);
			 for (int i = 0; i < root.getChildCount(); i++) {
				 fillTypeNode((DefaultMutableTreeNode) root.getChildAt(i), table);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		setCalAmt(table);
		
		setConTypeUnionData(getMainTable());
	}

	private void setCalAmt(KDTable table) {
		for (int i = dynIndex; i < table.getColumnCount() - 5; i = i + 5) {
			String columnKey = table.getColumn(i).getKey();
			int length = columnKey.length();
			String projectID = columnKey.substring(0, length-1);
			BigDecimal conAmt = (BigDecimal) conInfosData.get("last_"
					+ projectID);
			BigDecimal payAmt = (BigDecimal) conInfosData.get("pay_"
					+ projectID);
			BigDecimal assAmt = (BigDecimal) lastAimCostData.get("ass_"
					+ projectID);
			// 占概算% = 已签署合同合计 / 概算总金额
			String percent = null;
			if (assAmt != null) {
				percent = FDCHelper.toBigDecimal(conAmt, 4).divide(assAmt,
						BigDecimal.ROUND_HALF_UP).multiply(
						FDCHelper.ONE_HUNDRED).toString();
			}
			for (int j = 0; j < 5; j++) {
				table.getCell(0, i + j).setValue(assAmt);
				table.getCell(this.acctEndIndex + 1, i + j).setValue(conAmt);
				table.getCell(this.acctEndIndex + 2, i + j).setValue(percent);
				table.getCell(table.getRowCount()-1, i + j-1).setValue(payAmt);
			}
//			table.getCell(0, i + 3).setValue(assAmt);
//			table.getCell(acctEndIndex + 1, i + 3).setValue(conAmt);
//			table.getCell(acctEndIndex + 2, i + 4).setValue(percent);
//			table.getCell(table.getRowCount()-1, i + 3).setValue(payAmt);
		}
		String percent = null;
		if (lastAimCostData.get("assAllPrj") != null) {
			percent = FDCHelper.divide(conInfosData.get("lastAllPrj"),
					lastAimCostData.get("assAllPrj")).multiply(
					FDCHelper.ONE_HUNDRED).toString();
			percent = FDCHelper.toBigDecimal(conInfosData.get("lastAllPrj"), 4).divide(FDCHelper.toBigDecimal(lastAimCostData.get("assAllPrj")),
					BigDecimal.ROUND_HALF_UP).multiply(
					FDCHelper.ONE_HUNDRED).toString();
		}	
		for(int i=table.getColumnCount()-5;i<table.getColumnCount();i++){
			table.getCell(0, i).setValue(lastAimCostData.get("assAllPrj"));
			table.getCell(acctEndIndex+1, i).setValue(conInfosData.get("lastAllPrj"));
			table.getCell(table.getRowCount()-1, i).setValue(conInfosData.get("allPay"));
			table.getCell(acctEndIndex+2, i).setValue(percent);
			
		}
	}

	/**
	 * 初始化合同信息执行
	 * @param table
	 */
	private void initConTypeTable(KDTable table) {
		KDTMergeManager  mm = getMainTable().getMergeManager();
		IRow row = null;
		row = table.addRow();
		mm.mergeBlock(row.getRowIndex(), 1, row.getRowIndex(), table.getColumnCount()-1);
		row = table.addRow();
		row.getCell(numberIndex).setValue("已签署合同合计");
		row.getCell(nameIndex).setValue("已签署合同合计");
		row.getCell(numberIndex).getStyleAttributes().setBackground(getMainTable().getHeadRow(0).getStyleAttributes().getBackground());
		row.getCell(nameIndex).getStyleAttributes().setBackground(getMainTable().getHeadRow(0).getStyleAttributes().getBackground());
		row = table.addRow();
		row.getCell(numberIndex).setValue("占概算%");
		row.getCell(nameIndex).setValue("占概算%");
		row.getCell(numberIndex).getStyleAttributes().setBackground(getMainTable().getHeadRow(0).getStyleAttributes().getBackground());
		row.getCell(nameIndex).getStyleAttributes().setBackground(getMainTable().getHeadRow(0).getStyleAttributes().getBackground());
		row = table.addRow();
//		row.getCell("item").setValue("合同执行情况");
		row.getCell(numberIndex).setValue("合同类型");
		row.getCell(nameIndex).setValue("合同类型");
		row.getStyleAttributes().setBackground(getMainTable().getHeadRow(0).getStyleAttributes().getBackground());
		row.getStyleAttributes().setBackground(getMainTable().getHeadRow(0).getStyleAttributes().getBackground());
		int rowIndex = row.getRowIndex();
		mm.mergeBlock(rowIndex-2, 2, rowIndex, dynIndex-1,KDTMergeManager.FREE_MERGE);
		IRow payRow = table.addRow();
		payRow.getCell(numberIndex).setValue("支付合计");
		payRow.getCell(nameIndex).setValue("支付合计");
		mm.mergeBlock(payRow.getRowIndex(), numberIndex, payRow.getRowIndex(), nameIndex,KDTMergeManager.FREE_MERGE);
		payRow.getCell(numberIndex).getStyleAttributes().setBackground(getMainTable().getHeadRow(0).getStyleAttributes().getBackground());
		payRow.getCell(nameIndex).getStyleAttributes().setBackground(getMainTable().getHeadRow(0).getStyleAttributes().getBackground());
		for(int i=dynIndex;i<getMainTable().getColumnCount();i=i+5){
			row.getCell(i).setValue("合同金额");
			row.getCell(i+1).setValue("合同最新造价");
			row.getCell(i+2).setValue("变动金额");
			row.getCell(i+3).setValue("已支付金额");
			row.getCell(i+4).setValue("未支付金额");
			mm.mergeBlock(rowIndex-2, i, rowIndex-2, i+4);
			mm.mergeBlock(rowIndex-1, i, rowIndex-1, i+4);
			mm.mergeBlock(rowIndex+1, i, rowIndex+1, i+4);
		}
	}
	
	private void fillTypeNode(DefaultMutableTreeNode node, KDTable table)
			throws BOSException, SQLException, EASBizException {
		ContractTypeInfo typeInfo = (ContractTypeInfo) node.getUserObject();
		if (typeInfo == null) {
			return;
		}

		String typeID = typeInfo.getId().toString();
		IRow row = table.addRow(table.getRowCount() - 1);
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("item").setValue("合同执行情况");
		row.getCell("id").setValue(typeID);
		row.getCell("acctNumber").setValue(
				typeInfo.getLongNumber().replace('!', '.'));
		row.getCell("acctName").setValue(typeInfo.getName());
		int columnCount = table.getColumnCount();
		row.setUserObject(typeInfo);
		Object[] info = null;
		for (int i = dynIndex; i < columnCount - 5; i = i + 5) {
			String columnKey = table.getColumn(i).getKey();
			int length = columnKey.length();
			String projectID = columnKey.substring(0, length-1);
			info = (Object[]) this.conInfosData.get(typeID + "_" + projectID);
			if (info != null) {
				row.getCell(i).setValue(info[0]);
				row.getCell(i + 1).setValue(info[1]);
				row.getCell(i + 2).setValue(info[2]);
				row.getCell(i + 3).setValue(info[3]);
				row.getCell(i + 4).setValue(info[4]);
			}
			BigDecimal conAmt = (BigDecimal) conInfosData.get("last_"
					+ projectID);
			BigDecimal assAmt = (BigDecimal) lastAimCostData.get("ass_"
					+ projectID);
			// 占概算% = 已签署合同合计 / 概算总金额
			String percent = null;
			if (assAmt != null) {
				percent = FDCHelper.toBigDecimal(conAmt,4).divide(
						assAmt, BigDecimal.ROUND_HALF_UP).multiply(
						FDCHelper.ONE_HUNDRED)+"%";
			}
			table.getCell(this.acctEndIndex + 2, i).setValue(percent);
			table.getCell(this.acctEndIndex + 2, i + 1).setValue(percent);
			table.getCell(this.acctEndIndex + 2, i + 2).setValue(percent);
			table.getCell(this.acctEndIndex + 2, i + 3).setValue(percent);
			table.getCell(this.acctEndIndex + 2, i + 4).setValue(percent);

		}
		Object[] tatal = (Object[]) this.conInfosData.get(typeID);
		if (tatal != null) {
			row.getCell("total0").setValue(tatal[0]);
			row.getCell("total1").setValue(tatal[1]);
			row.getCell("total2").setValue(tatal[2]);
			row.getCell("total3").setValue(tatal[3]);
			row.getCell("total4").setValue(tatal[4]);
		}
//		row.getStyleAttributes().setBackground(FDCTableHelper.daySubTotalColor);
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillTypeNode((DefaultMutableTreeNode) node.getChildAt(i), table);
		}
		this.getMainTable().getMergeManager().mergeBlock(0, 0, getMainTable().getRowCount()-1, 0, KDTMergeManager.FREE_MERGE);
	}
	
	private void initConType(FilterInfo filter) throws BOSException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("parent.id"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sic);
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		ContractTypeCollection typeColls = ContractTypeFactory.getRemoteInstance().getContractTypeCollection(view);
		conTypeData = new HashMap();
		for (Iterator iter = typeColls.iterator(); iter.hasNext();) {
			ContractTypeInfo type = (ContractTypeInfo)iter.next();
			conTypeData.put(type.getId().toString(), type);
		}
	}
	
	
	/**
	 * 科目树构造
	 */
	private void initAcct(FilterInfo filter) throws BOSException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sic.add(new SelectorItemInfo("curProject.longNumber"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.longNumber"));
		sic.add(new SelectorItemInfo("isLeaf"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sic);
		view.setFilter(filter);
		CostAccountCollection accts = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		acctMap = new HashMap();
		for (int i = 0; i < accts.size(); i++) {
			CostAccountInfo info = accts.get(i);
			this.acctMap.put(info.getId().toString(), info);
		}
	}
	private Set initProjectIDSet(){
//		return (Set)initMap.get("projectIDSet");
		return null;
	}
	private void setConTypeUnionData(KDTable table){
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		for (int j = dynIndex; j < table.getColumnCount(); j++) {
			amountColumns.add(tblMain.getColumn(j).getKey());
		}
		for (int i = this.acctEndIndex+4; i < table.getRowCount()-1; i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() != null) {// 非叶结点
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount()-1; j++) {
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
					boolean hasData = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if (hasData) {
						if(FDCHelper.toBigDecimal(amount).signum()==0){
							amount=null;
						}
						row.getCell(colName).setValue(amount);
					}
				}
			}
		}
		
	}
	
	protected void setUnionData(){
		KDTable table=getMainTable();
		List columns=new ArrayList();
		for(int i=dynIndex;i<table.getColumnCount();i++){
			columns.add(table.getColumnKey(i));
		}
		String cols[]=new String[columns.size()];
		columns.toArray(cols);
		
		for(int i=2;i<acctEndIndex;i++){
			IRow row = table.getRow(i);
			int level=row.getTreeLevel();
			if(level==0){
				_setUnionSubRow(row, columns);
			}
		}
	}
	
	/**
	 * 对一个科目树进行递归汇总
	 * @param row
	 */
	private void _setUnionSubRow(IRow row,List sumCols) {
		KDTable table=getMainTable();
		Object obj=row.getUserObject();
		int level=row.getTreeLevel();
		if(obj instanceof CostAccountInfo){
			//明细行
			return;
		}
		List subRows=new ArrayList();
		for(int i=row.getRowIndex()+1;i<acctEndIndex;i++){
			IRow tmpRow=table.getRow(i);
			if(tmpRow.getTreeLevel()==level+1){
				_setUnionSubRow(tmpRow, sumCols);
				subRows.add(tmpRow);
			}
			if(tmpRow.getTreeLevel()<=level){
				break;
			}
		}
		sumRow(row, subRows, sumCols);
	
	}
	
	/**
	 * 将subRows按cols列汇总到row
	 * @param row
	 * @param subRows
	 * @param cols
	 */
	private void sumRow(IRow row,List subRows,List cols){
		if(cols.size()==0){
			return;
		}
		for(Iterator iter=cols.iterator();iter.hasNext();){
			//先清空再汇总
			row.getCell((String)iter.next()).setValue(null);
		}

		for(Iterator iter=subRows.iterator();iter.hasNext();){
			IRow tmpRow=(IRow)iter.next();
			for(Iterator iter2=cols.iterator();iter2.hasNext();){
				String key=(String)iter2.next();
				setCellValue(row.getCell(key), FDCNumberHelper.add(row.getCell(key).getValue(), tmpRow.getCell(key).getValue()));
			}
		}
	
	}
	private void setCellValue(com.kingdee.bos.ctrl.kdf.table.ICell cell,BigDecimal value){
		if(FDCHelper.toBigDecimal(value).signum()==0){
			value=null;
		}
		cell.setValue(value);
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}
	protected void initWorkButton(){
		super.initWorkButton();
		FDCClientHelper.setActionEnable(new ItemAction[] { this.actionAddNew,
				this.actionEdit, this.actionRemove, this.actionQuery,
				this.actionLocate, this.actionAttachment, this.actionExport,
				this.actionView}, false);
		
//		this.menuView.setVisible(false);
//		this.menuView.setEnabled(false);
		this.menuEdit.setVisible(false);
		this.menuEdit.setEnabled(false);
	}
	
	public int getRowCountFromDB() {
//		super.getRowCountFromDB();
		return -1;
	}
	
	public void onShow() throws Exception {
		super.onShow();
		//R110519-0372：工程项目分析表的前两列需要冻结
		tblMain.getViewManager().freeze(-1, dynIndex);
	}
}