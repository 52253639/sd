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

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.MeasureCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IncomeAccountCollection;
import com.kingdee.eas.fdc.basedata.IncomeAccountFactory;
import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;
import com.kingdee.eas.fdc.basedata.MeasureStageCollection;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public class MeasureStageCostUI extends AbstractMeasureStageCostUI
{
    private static final Logger logger = CoreUIObject.getLogger(MeasureStageCostUI.class);
    
    private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	private RetValue retValue = null;
	private MeasureStageCollection stages = null;
	
	private Map acctMaps = new HashMap();//用于缓存成本科目map
    private Map acctMap = new HashMap();
    private Map incomeAcctMap = new HashMap();
    private Map settleSplits = null;
    
	//界线行
	private int sideIndex=0;
	//动态列
	private int dynIndex=3;
	//计算列 
	private int calIndex=0;
	
	/**
     * 科目长编码即可
     * 开发成本
     */
    private String devAcct="devAcct";
    /**
     * 开发间接费用
     */
    private String indirectAcct="indirectAcct";
    /**
     * 管理费用
     */
    private String adminAcct="adminAcct";
    /**
     * 土地费用
     */
    private String landAcct="landAcct";
    /**
     * 销售费用
     */
    private String saleAcct="saleAcct";
    
    /**
     * output class constructor
     */
    public MeasureStageCostUI() throws Exception
    {
        super();
    }

    protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}
    
    // 删除动态列
	private void removeColumn() {
		for(int i=tblMain.getColumnCount()-1;i>=dynIndex;i--){
			tblMain.removeColumn(i);
		}
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		btnView.setVisible(false);
		btnAddNew.setVisible(false);
		btnEdit.setVisible(false);
		btnRemove.setVisible(false);
		btnLocate.setVisible(false);
		menuEdit.setVisible(false);
		menuItemView.setVisible(false);
		menuItemAddNew.setVisible(false);
		menuItemEdit.setVisible(false);
		menuItemRemove.setVisible(false);
		menuItemLocate.setVisible(false);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",OrgConstants.DEF_CU_ID));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		initAcct(filter); 
	}
	private static final String INCOMEACCT="IncomeAccount";
	private static final String AMOUNT="amt";
	private static final String SELLPART="sellPart";
	private static final String REASON="reason";
	
	private String measureStr="工程目标成本测算";
	private String amountStr="合价（万元）";
	private String sellPartStr="可售单方（元/m2）";
	private String orienteStr="估算（定位)";
	private String designStr="概算（方案）";
	private String constrStr="预算（施工图）";
	private String reasionStr="变化原因";
	private String settleStr="结算";
	private String descStr="成本指标分类及计算说明";
	private String desc1Str="工程量及单价计算说明";
	private String desc2Str="对应合同";
	
	private String settleKey="settle";
	String descKey="desc";
	String programKey="program";
	
    private void initMainTable() throws BOSException {
    	removeColumn();
    	//目前只有三个阶段
    	IColumn column;
    	int colIndex;
    	for(int i=0;i<stages.size();i++){
    		MeasureStageInfo stage = (MeasureStageInfo)stages.get(i);
    		String stageId = stage.getId().toString();
    		column = tblMain.addColumn();
    		colIndex=column.getColumnIndex();
    		column.setKey(stageId+AMOUNT);
    		FDCHelper.formatTableNumber(tblMain, colIndex, FDCHelper.strDataFormat4);
    		tblMain.getHeadRow(0).getCell(colIndex).setValue(measureStr);
    		tblMain.getHeadRow(1).getCell(colIndex).setValue(getMeasureStageStr(stage));
    		tblMain.getHeadRow(2).getCell(colIndex).setValue(amountStr);
    		
    		column = tblMain.addColumn();
    		colIndex=column.getColumnIndex();
    		column.setKey(stageId+SELLPART);
    		column.setWidth(120);
    		FDCHelper.formatTableNumber(tblMain, stageId+SELLPART);
    		tblMain.getHeadRow(0).getCell(colIndex).setValue(measureStr);
    		tblMain.getHeadRow(1).getCell(colIndex).setValue(getMeasureStageStr(stage));
    		tblMain.getHeadRow(2).getCell(colIndex).setValue(sellPartStr);
    		if(i>0){
    			column = tblMain.addColumn();
    			colIndex=column.getColumnIndex();
    			column.setKey(stageId+REASON);
    			tblMain.getHeadRow(0).getCell(colIndex).setValue(measureStr);
    			tblMain.getHeadRow(1).getCell(colIndex).setValue(getMeasureStageStr(stage));
    			tblMain.getHeadRow(2).getCell(colIndex).setValue(reasionStr);
    			
    		}
    	}
    	
    	column = tblMain.addColumn();
		colIndex = column.getColumnIndex();
		column.setKey(settleKey + AMOUNT);
		FDCHelper.formatTableNumber(tblMain, colIndex, FDCHelper.strDataFormat4);
		tblMain.getHeadRow(0).getCell(colIndex).setValue(measureStr);
		tblMain.getHeadRow(1).getCell(colIndex).setValue(settleStr);
		tblMain.getHeadRow(2).getCell(colIndex).setValue(amountStr);

		column = tblMain.addColumn();
		colIndex = column.getColumnIndex();
		column.setKey(settleKey + SELLPART);
		column.setWidth(120);
		FDCHelper.formatTableNumber(tblMain, settleKey + SELLPART);
		tblMain.getHeadRow(0).getCell(colIndex).setValue(measureStr);
		tblMain.getHeadRow(1).getCell(colIndex).setValue(settleStr);
		tblMain.getHeadRow(2).getCell(colIndex).setValue(sellPartStr);
				
    	column = tblMain.addColumn();
    	colIndex=column.getColumnIndex();
    	column.setKey("desc1");
    	column.setWidth(150);
//    	KDTableHelper.autoFitColumnWidth(tblMain, colIndex);
    	tblMain.getHeadRow(0).getCell(colIndex).setValue(descStr);
		tblMain.getHeadRow(1).getCell(colIndex).setValue(desc1Str);
		tblMain.getHeadRow(2).getCell(colIndex).setValue(desc1Str);
    	
    	column = tblMain.addColumn();
    	colIndex=column.getColumnIndex();
    	column.setKey("desc2");
    	column.setWidth(120);
    	tblMain.getHeadRow(0).getCell(colIndex).setValue(descStr);
		tblMain.getHeadRow(1).getCell(colIndex).setValue(desc2Str);
		tblMain.getHeadRow(2).getCell(colIndex).setValue(desc2Str);
		
    	tblMain.getHeadMergeManager().mergeBlock(1, 3, 1, tblMain.getColumnCount()-1, KDTMergeManager.FREE_COLUMN_MERGE);
    	tblMain.getHeadMergeManager().mergeBlock(0, 3, 0, tblMain.getColumnCount()-1, KDTMergeManager.FREE_COLUMN_MERGE);
    	tblMain.getHeadMergeManager().mergeBlock(1, tblMain.getColumnCount()-2, 2, tblMain.getColumnCount()-1, KDTMergeManager.FREE_ROW_MERGE);
    	
    }
    private String getMeasureStageStr(MeasureStageInfo stage){
    	return "目标成本测算-"+stage.getName();
    }
    protected void initTree() throws Exception {
    	
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}
    /**
	 * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
	 * 
	 */
	protected String getKeyFieldName() {
		return "acctNumber";
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {}
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
	}
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
    	String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			tblMain.removeRows();
			removeColumn();
			return;
		}
    	execQuery();
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			tblMain.removeRows();
			removeColumn();
			return;
		}
		execQuery();
	}
    private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node==null||node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo && ((CurProjectInfo)node.getUserObject()).isIsLeaf()) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		} 
		return null;
	}
	private void fetchAndFill(String selectObjId) throws EASBizException,BOSException, Exception {
		LongTimeDialog dialog = UITools.getDialog(this);
		if(dialog==null)
			return;
        dialog.setLongTimeTask(new ILongTimeTask() {
            public Object exec() throws Exception {
            	String selectObjId = getSelectObjId(); 
            	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
        		.getLastSelectedPathComponent();
        		if (node.isLeaf()&&node.getUserObject() instanceof CurProjectInfo) {
        			if (selectObjId == null) {
        				return null;
        			}
        			fetchData(selectObjId);
        			initMainTable();
        			fillMainTable(selectObjId);
        			int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
        			tblMain.getViewManager().freeze(0, acctNameIndex);
        		} 
                return null;
            }

            public void afterExec(Object result) throws Exception {
                
            }
        });
        try {

			if (dialog != null)
				dialog.show();
		} catch (Exception e) {
			logger.info(e);
		}
	}
	protected boolean initDefaultFilter() {
		return true;
	}
	
	private Map initIncomeAcct(FilterInfo incomeAcctFilter) throws BOSException {
		Map acctMap=new HashMap();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sel.add(new SelectorItemInfo("curProject.longNumber"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit.longNumber"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(incomeAcctFilter);
		IncomeAccountCollection accts = IncomeAccountFactory.getRemoteInstance()
				.getIncomeAccountCollection(view);
		for (int i = 0; i < accts.size(); i++) {
			IncomeAccountInfo info = accts.get(i);
			incomeAcctMap.put(info.getId().toString(), info);
		}
		return acctMap;
	}
	private Map initAcct(FilterInfo acctFilter) throws BOSException {
		Map acctMap=new HashMap();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sel.add(new SelectorItemInfo("curProject.longNumber"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit.longNumber"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
		CostAccountCollection accts = CostAccountFactory.getRemoteInstance()
				.getCostAccountCollection(view);
		for (int i = 0; i < accts.size(); i++) {
			CostAccountInfo info = accts.get(i);
			acctMap.put(info.getId().toString(), info);
		}
		return acctMap;
	}
	public void fillMainTable(String objectId) throws Exception {
		tblMain.removeRows();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", objectId));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		TreeModel costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), filter);
		if (acctMaps.containsKey(objectId)) {
			acctMap = (Map) acctMaps.get(objectId);
		} else {
			acctMap = this.initAcct(filter);
			acctMaps.put(objectId, acctMap);
		}
		TreeModel incomeAcctTree = FDCClientHelper.createDataTree(IncomeAccountFactory.getRemoteInstance(), filter);
		if (acctMaps.containsKey(objectId+INCOMEACCT)) {
			incomeAcctMap = (Map) acctMaps.get(objectId+INCOMEACCT);
		} else {
			incomeAcctMap = this.initIncomeAcct(filter);
			acctMaps.put(objectId+INCOMEACCT, incomeAcctMap);
		}
		
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) incomeAcctTree.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		tblMain.getTreeColumn().setDepth(maxLevel);
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode0((DefaultMutableTreeNode) root.getChildAt(i));
		}
		sideIndex=tblMain.getRowCount();//成本科目起始行
//		IRow row = tblMain.addRow();
//		sideIndex=row.getRowIndex();
//		tblMain.getMergeManager().mergeBlock(row.getRowIndex(), 0, row.getRowIndex(), tblMain.getColumnCount()-1);
		root = (DefaultKingdeeTreeNode) costAcctTree
				.getRoot();
		childrens = root.depthFirstEnumeration();
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
					.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		this.tblMain.getTreeColumn().setDepth(maxLevel);
		
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode((DefaultMutableTreeNode) root.getChildAt(i));
		}
		setUnionData(tblMain);
		addCalcRows(tblMain);
		calData(tblMain);
	}
	private void addCalcRows(KDTable table) throws Exception{
		calIndex = table.getRowCount();
		IRow row = table.addRow();
		row.getStyleAttributes().setBackground(FDCColorConstants.totalColor);
		row.getCell(1).setValue("总收入");
		row.getCell(2).setValue("总收入");
		
		row = table.addRow();
		row.getStyleAttributes().setBackground(FDCColorConstants.totalColor);
		row.getCell(1).setValue("总成本");
		row.getCell(2).setValue("总成本");
		
		row = table.addRow();
		row.getStyleAttributes().setBackground(FDCColorConstants.totalColor);
		row.getCell(1).setValue("可控成本");
		row.getCell(2).setValue("可控成本");
		
		row = table.addRow();
		row.getStyleAttributes().setBackground(FDCColorConstants.totalColor);
		row.getCell(1).setValue("净利润");
		row.getCell(2).setValue("净利润");
		
		row = table.addRow();
		row.getStyleAttributes().setBackground(FDCColorConstants.totalColor);
		row.getCell(1).setValue("经营性现金净流量");
		row.getCell(2).setValue("经营性现金净流量");
		
		row = table.addRow();
		row.getStyleAttributes().setBackground(FDCColorConstants.totalColor);
		row.getCell(1).setValue("投资现金净流量");
		row.getCell(2).setValue("投资现金净流量");
		
		row = table.addRow();
		row.getStyleAttributes().setBackground(FDCColorConstants.totalColor);
		row.getCell(1).setValue("投资利润率");
		row.getCell(2).setValue("投资利润率");
		
		row = table.addRow();
		row.getStyleAttributes().setBackground(FDCColorConstants.totalColor);
		row.getCell(1).setValue("销售利润率");
		row.getCell(2).setValue("销售利润率");
		
		row = table.addRow();
		row.getStyleAttributes().setBackground(FDCColorConstants.totalColor);
		row.getCell(1).setValue("费用利润率");
		row.getCell(2).setValue("费用利润率");
	}

	private void fillNode0(DefaultMutableTreeNode node) throws BOSException, SQLException, EASBizException {
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (!proNode.isLeaf()) {
			return;
		}
		IncomeAccountInfo incomeAcct = (IncomeAccountInfo) node.getUserObject();
		if (incomeAcct == null) {
			return;
		}
		String acctId = incomeAcct.getId().toString();
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("id").setValue(acctId);
		row.getCell("acctNumber").setValue(incomeAcct.getLongNumber().replace('!', '.'));
		row.getCell("acctName").setValue(incomeAcct.getName());
		
		if (node.isLeaf()) {
			row.setUserObject(incomeAcct);
			for(int i=0;i<stages.size();i++){
				MeasureStageInfo stage = (MeasureStageInfo)stages.get(i);
	    		String stageId = stage.getId().toString();
	    		String key = acctId+stageId;
	    		BigDecimal amount = FDCHelper.toBigDecimal(retValue.getBigDecimal(key));
	    		BigDecimal amount4 = FDCHelper.divide(amount,FDCHelper.TEN_THOUSAND,4,BigDecimal.ROUND_HALF_UP);
	    		if(FDCHelper.toBigDecimal(amount4).compareTo(FDCHelper.ZERO)!=0){
	    			row.getCell(stageId+AMOUNT).setValue(amount4);
	    		}
	    		BigDecimal sellPart=FDCHelper.divide(amount,retValue.getBigDecimal(stageId),2,BigDecimal.ROUND_HALF_UP);
	    		if(FDCHelper.toBigDecimal(sellPart).compareTo(FDCHelper.ZERO)!=0){
	    			row.getCell(stageId+SELLPART).setValue(sellPart);
	    		}
	    		if(i>0){
	    			String reason = retValue.getString(key+"reason");
					row.getCell(stageId+REASON).setValue(reason);
	    		}
			}
			
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode0((DefaultMutableTreeNode) node.getChildAt(i));
		}
	}
	
	private void fillNode(DefaultMutableTreeNode node) throws BOSException, SQLException, EASBizException {
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (!proNode.isLeaf()) {
			return;
		}
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		String acctId = costAcct.getId().toString();
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("id").setValue(acctId);
		row.getCell("acctNumber").setValue(costAcct.getLongNumber().replace('!', '.'));
		row.getCell("acctName").setValue(costAcct.getName());
		if (node.isLeaf()) {
			row.setUserObject(costAcct);
			for (int i = 0; i < stages.size(); i++) {
				
				MeasureStageInfo stage = (MeasureStageInfo) stages.get(i);
				String stageId = stage.getId().toString();
				String key = acctId + stageId;
				BigDecimal amount = FDCHelper.toBigDecimal(retValue.getBigDecimal(key));
				BigDecimal amount4 = FDCHelper.divide(amount, FDCHelper.TEN_THOUSAND, 4, BigDecimal.ROUND_HALF_UP);
				if(FDCHelper.toBigDecimal(amount4).compareTo(FDCHelper.ZERO)!=0){
					row.getCell(stageId + AMOUNT).setValue(amount4);
				}
				BigDecimal sellPart = FDCHelper.divide(amount, FDCHelper.toBigDecimal(retValue.getBigDecimal(stageId)), 2, BigDecimal.ROUND_HALF_UP);
				if(FDCHelper.toBigDecimal(sellPart).compareTo(FDCHelper.ZERO)!=0){
					row.getCell(stageId + SELLPART).setValue(sellPart);
				}
				if(i>0){
					String reason = retValue.getString(key+"reason");
					row.getCell(stageId+REASON).setValue(reason);
				}
			}
			if(settleSplits.containsKey(acctId)){
				BigDecimal settleSplit = FDCHelper.toBigDecimal(settleSplits.get(acctId));
				BigDecimal settleSplit4 = FDCHelper.divide(settleSplit, FDCHelper.TEN_THOUSAND, 4, BigDecimal.ROUND_HALF_UP);
				row.getCell(settleKey+AMOUNT).setValue(settleSplit4);
				BigDecimal  sellArea= FDCHelper.toBigDecimal(retValue.getBigDecimal(MeasureStageInfo.CONSTRUCT));
				BigDecimal sellPart = FDCHelper.divide(settleSplit, sellArea, 2, BigDecimal.ROUND_HALF_UP);
				row.getCell(settleKey+SELLPART).setValue(sellPart);
			}
			if(retValue.get(acctId+descKey)!=null){
				row.getCell("desc1").setValue(retValue.get(acctId+descKey));
			}
			if(retValue.get(acctId+programKey)!=null){
				row.getCell("desc2").setValue(retValue.get(acctId+programKey));
			}
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode((DefaultMutableTreeNode) node.getChildAt(i));
		}
		
	}

	protected List getUnionColumns(){
		List columns = new ArrayList();
		for(int i=0;i<stages.size();i++){
    		MeasureStageInfo stage = (MeasureStageInfo)stages.get(i);
    		String stageId = stage.getId().toString();
    		columns.add(stageId+AMOUNT);
    	}
		columns.add(settleKey + AMOUNT);
		columns.add(settleKey + SELLPART);
				
		return columns;
	}
	
	
	protected void setUnionData(KDTable table) {
		List columns = getUnionColumns();
		for (int i = 0; i < table.getRowCount()-9; i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if(rowAfter.getUserObject()!=null){
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < columns.size(); k++) {
					String colName = (String) columns.get(k);
					BigDecimal amount = FDCHelper.ZERO;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(
										((Integer) value).toString()));
							}
						}
					}
					if(FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO)!=0){
						row.getCell(colName).setValue(amount);
					}
				}
				
			}
		}
	}
	
	/**
	 * 表最后9行数据计算
	 * @param table
	 */
	
	private void calData(KDTable table){
		Map incomeData = new HashMap();
		Map costData = new HashMap();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			String acctNumber=(String)row.getCell("acctNumber").getValue();
			if (i < sideIndex) {

				if (row.getTreeLevel() == 0) {
					for (int j = 0; j < stages.size(); j++) {
						MeasureStageInfo info = stages.get(j);
						String stageId = info.getId().toString();
						BigDecimal amt = (BigDecimal) row.getCell(stageId + AMOUNT).getValue();
						incomeData.put(stageId, FDCHelper.add(incomeData.get(stageId), amt));
					}
				}
			}else{
				if (row.getUserObject()!=null) {
					for (int j = 0; j < stages.size(); j++) {
						MeasureStageInfo info = stages.get(j);
						String stageId = info.getId().toString();
						BigDecimal amt = (BigDecimal) row.getCell(stageId + AMOUNT).getValue();
						costData.put(stageId, FDCHelper.add(costData.get(stageId), amt));
						if(acctNumber.startsWith(devAcct)){
							costData.put(stageId+devAcct, FDCHelper.add(costData.get(stageId+devAcct), amt));
						}
						if(acctNumber.startsWith(indirectAcct)){
							costData.put(stageId+indirectAcct, FDCHelper.add(costData.get(stageId+indirectAcct), amt));
						}
						if(acctNumber.startsWith(adminAcct)){
							costData.put(stageId+adminAcct, FDCHelper.add(costData.get(stageId+adminAcct), amt));
						}
						if(acctNumber.startsWith(landAcct)){
							costData.put(stageId+landAcct, FDCHelper.add(costData.get(stageId+landAcct), amt));
						}
						if(acctNumber.startsWith(saleAcct)){
							costData.put(stageId+saleAcct, FDCHelper.add(costData.get(stageId+saleAcct), amt));
						}
					}
				}
			}
			
			
		}
		
		for (int j = 0; j < stages.size(); j++) {
			MeasureStageInfo info = stages.get(j);
			String stageId = info.getId().toString();

			// 总收入=所有明细收入科目的汇总值
			BigDecimal amt0 = FDCHelper.toBigDecimal(incomeData.get(stageId));
			table.getRow(calIndex).getCell(stageId + AMOUNT).setValue(amt0);

			// 总成本=所有明细成本科目汇总值
			BigDecimal amt1 = FDCHelper.toBigDecimal(costData.get(stageId));
			table.getRow(calIndex + 1).getCell(stageId + AMOUNT).setValue(amt1);

			// 可控成本=开发成本+开发间接费用-土地成本
			BigDecimal amt2 = FDCHelper.subtract(FDCHelper.add(costData.get(stageId + devAcct), costData.get(stageId + indirectAcct)), costData.get(stageId + landAcct));
			table.getRow(calIndex + 2).getCell(stageId + AMOUNT).setValue(amt2);

			// 净利润=总收入-总支出
			BigDecimal amt3 = FDCHelper.subtract(incomeData.get(stageId), costData.get(stageId));
			table.getRow(calIndex + 3).getCell(stageId + AMOUNT).setValue(amt3);

			// 经营性现金净流量=总收入-开发成本-开发间接费用
			BigDecimal amt4 = FDCHelper.subtract(incomeData.get(stageId), FDCHelper.add(costData.get(stageId + devAcct), costData.get(stageId + indirectAcct)));
			table.getRow(calIndex + 4).getCell(stageId + AMOUNT).setValue(amt4);

			// 投资现金净流量=开发成本+开发间接费用
			BigDecimal amt5 = FDCHelper.add(costData.get(stageId + devAcct), costData.get(stageId + indirectAcct));
			table.getRow(calIndex + 5).getCell(stageId + AMOUNT).setValue(amt5);

			// 投资利润率=净利润/投资现金净流量
			BigDecimal amt6 = FDCHelper.divide(amt3, amt5, 4, BigDecimal.ROUND_HALF_UP);
			table.getRow(calIndex + 6).getCell(stageId + AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.strPropFormat2);
			table.getRow(calIndex + 6).getCell(stageId + AMOUNT).setValue(amt6);

			// 销售利润率=净利润/总收入
			BigDecimal amt7 = FDCHelper.divide(amt3, amt0, 4, BigDecimal.ROUND_HALF_UP);
			table.getRow(calIndex + 7).getCell(stageId + AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.strPropFormat2);
			table.getRow(calIndex + 7).getCell(stageId + AMOUNT).setValue(amt7);

			// 费用利润率=净利润/（管理费+营销成本）
			BigDecimal amt8 = FDCHelper.divide(amt3, FDCHelper.add(costData.get(stageId + adminAcct), costData.get(stageId + saleAcct)), 4, BigDecimal.ROUND_HALF_UP);
			table.getRow(calIndex + 8).getCell(stageId + AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.strPropFormat2);
			table.getRow(calIndex + 8).getCell(stageId + AMOUNT).setValue(amt8);
		}
	}
	
	private void fetchData(String prjId) throws BOSException,EASBizException{
		ParamValue paramValue = new ParamValue();
		paramValue.setString("prjId", prjId);
		retValue = MeasureCostRptFacadeFactory.getRemoteInstance().getMeasureCosts(paramValue);
		stages = (MeasureStageCollection) retValue.get("stages");
		settleSplits=(Map)retValue.get("settleSplits");
		
	}
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setUiObject(null);
		commonQueryDialog.setShowFieldCompare(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new MeasureStageCostFilterUI();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}	
	protected FDCCustomerParams objParam = new FDCCustomerParams();
	protected FDCCustomerParams getParas() {
		return objParam; 
	}
	protected void execQuery() {
		try {
			CustomerParams customerParams = getFilterUI().getCustomerParams();
			objParam = new FDCCustomerParams(customerParams);
			if(mainQuery!=null&&mainQuery.getFilter()!=null){
				//默认参数
				objParam=new FDCCustomerParams();
				if (mainQuery.getFilter() != null) {
					FilterInfo filter = mainQuery.getFilter();
					for (Iterator iter = filter.getFilterItems().iterator(); iter.hasNext();) {
						FilterItemInfo item = (FilterItemInfo) iter.next();
						if (item.getPropertyName().equals(devAcct)) {
							if(item.getCompareValue() instanceof String){
								devAcct =((String)item.getCompareValue()).replace('!', '.');
							}
						}
						if (item.getPropertyName().equals(indirectAcct)) {
							if(item.getCompareValue() instanceof String){
								indirectAcct = ((String)item.getCompareValue()).replace('!', '.');
							}
						}
						if (item.getPropertyName().equals(adminAcct)) {
							if(item.getCompareValue() instanceof String){
								adminAcct = ((String)item.getCompareValue()).replace('!', '.');
							}
						}
						if (item.getPropertyName().equals(landAcct)) {
							if(item.getCompareValue() instanceof String){
								landAcct = ((String)item.getCompareValue()).replace('!', '.');
							}
						}
						if (item.getPropertyName().equals(saleAcct)) {
							if(item.getCompareValue() instanceof String){
								saleAcct = ((String)item.getCompareValue()).replace('!', '.');
							}
						}
					}
				}
			}
			fetchAndFill(getSelectObjId());
			initUserConfig();
		} catch (Exception e) {
			handUIException(e);
		}
	}
	
	public boolean destroyWindow() {
		final boolean isDestroyWindow = super.destroyWindow();
		if(isDestroyWindow){
			clear();
		}
		return isDestroyWindow;
	}
	private void clear(){
		if(acctMap!=null){
			this.acctMap.clear();
		}
		if(acctMaps!=null){
			this.acctMaps.clear();
		}
		if(incomeAcctMap!=null){
			this.incomeAcctMap.clear();
		}
		if(settleSplits!=null){
			this.settleSplits.clear();
		}
		if(stages!=null){
			this.stages.clear();
		}
	}
}