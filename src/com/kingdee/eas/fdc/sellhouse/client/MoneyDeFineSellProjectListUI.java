//
//package com.kingdee.eas.fdc.sellhouse.client;
//
//
//
//import java.awt.event.ActionEvent;
//import java.util.Date;
//
//import javax.swing.event.TreeSelectionEvent;
//
//import org.apache.log4j.Logger;
//import com.kingdee.bos.ctrl.kdf.table.IRow;
//import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
//import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
//import com.kingdee.bos.ctrl.swing.KDTree;
//import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
//import com.kingdee.bos.dao.query.IQueryExecutor;
//import com.kingdee.bos.metadata.IMetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
//import com.kingdee.bos.metadata.entity.FilterInfo;
//import com.kingdee.bos.metadata.entity.FilterItemInfo;
//import com.kingdee.bos.metadata.entity.SelectorItemInfo;
//import com.kingdee.bos.ui.face.CoreUIObject;
//import com.kingdee.eas.basedata.assistant.PeriodInfo;
//import com.kingdee.eas.basedata.assistant.PeriodUtils;
//import com.kingdee.eas.basedata.org.OrgStructureInfo;
//import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
//import com.kingdee.eas.common.client.SysContext;
//import com.kingdee.eas.common.client.UIContext;
//import com.kingdee.eas.common.client.UIFactoryName;
//import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
//import com.kingdee.eas.fdc.basedata.FDCHelper;
//import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
//import com.kingdee.eas.fdc.propertymgmt.AdvanceReceiveFactory;
//import com.kingdee.eas.fdc.propertymgmt.AdvanceReceiveMatchFactory;
//import com.kingdee.eas.fdc.propertymgmt.client.PPMHelper;
//import com.kingdee.eas.fdc.propertymgmt.client.PPMMeterHelper;
//import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
//import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
//import com.kingdee.eas.fdc.sellhouse.MoneyDefineSellProjectFactory;
//import com.kingdee.eas.fdc.sellhouse.MoneyDefineSellProjectInfo;
//import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
//import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
//import com.kingdee.eas.framework.ICoreBase;
//import com.kingdee.eas.util.SysUtil;
//import com.kingdee.eas.util.client.MsgBox;
//
///**
// * output class name
// */
//public class MoneyDeFineSellProjectListUI extends AbstractMoneyDeFineSellProjectListUI {
//
//	private static final Logger logger = CoreUIObject.getLogger(MoneyDeFineSellProjectListUI.class);
//	private SellProjectInfo sellProject = null;
//	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
//
//
//	protected String[] getLocateNames() {
//		String[] localNames = new String[7];
//		localNames[0] = "moneyDefine.name";
//		localNames[1] = "state";
//		localNames[2] = "remark";
//		localNames[3] = "createTime";
//		localNames[4] = "lastUpdateTime";
//		localNames[5] = "creator.name";
//		localNames[6] = "lastUpdateUser.name";
//		return localNames;
//	}
//	public void onLoad() throws Exception {
//		super.onLoad();
//		this.setUITitle("预收款冲抵规则");
//		tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATETIME_FMT);
//		tblMain.getColumn("lastUpdateTime").getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATETIME_FMT);
//		initButtonController();
//	}
//	private void initButtonController() {
//	 	
//		if(!saleOrg.isIsBizUnit()){
//	 		this.btnAddNew.setEnabled(false);
//	 		this.btnEdit.setEnabled(false);
//	 		this.btnRemove.setEnabled(false);
//	 		this.btnCancel.setVisible(false);
//	 		this.btnCancelCancel.setVisible(false);
//	 		
//	 		
//	 		this.menuItemAddNew.setEnabled(false);
//	 		this.menuItemEdit.setEnabled(false);
//	 		this.menuItemRemove.setEnabled(false);
//	 		this.menuItemCancel.setEnabled(false);
//	 		this.menuItemCancelCancel.setEnabled(false);
//	 	}
//	}
//
//	protected void _tblMain_tableSelectChanged2(KDTSelectEvent e) throws Exception {
//		// TODO Auto-generated method stub
//		super._tblMain_tableSelectChanged2(e);
//		if(PPMMeterHelper.isOrgPermission()){
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//		}
////		initButtonController();
//	}
//
//	/**
//	 * output class constructor
//	 */
//	public MoneyDeFineSellProjectListUI() throws Exception {
//		super();
//	}
//
//	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTree1.getLastSelectedPathComponent();
//		if (node == null) {
//			return;
//		}
//		if (node.getUserObject() instanceof String) {
//			FDCMsgBox.showInfo("请选择具体销售项目！");
//			SysUtil.abort();
//		}
//		if (node.getUserObject() instanceof SellProjectInfo) {
//			sellProject = (SellProjectInfo) node.getUserObject();
//			uiContext.put("sellProject", sellProject);
//		}
//		if(node.getUserObject() instanceof OrgStructureInfo){
//			OrgStructureInfo orgStructure = (OrgStructureInfo)node.getUserObject();
//			uiContext.put("orgStructure", orgStructure);
//		}
//		
//		super.prepareUIContext(uiContext, e);
//	}
//
//
//
//	protected String getEditUIModal() {
//		return UIFactoryName.MODEL;
//	}
//
//	protected ICoreBase getBizInterface() throws Exception {
//		return MoneyDefineSellProjectFactory.getRemoteInstance();
//	}
//
//	protected String getEditUIName() {
//		return MoneyDefineSellProjectEditUI.class.getName();
//	}
//
//
//	
//	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {		
//		super.actionEdit_actionPerformed(e);
//	}
//	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
//		//删除冲抵规则之前先判断其是否被引用 hz
//		FilterInfo filterinfo1  = new FilterInfo();
//		FilterInfo filterinfo2  = new FilterInfo();
//		filterinfo1.getFilterItems().add(new FilterItemInfo("moneyDefineSellProject.id",getSelectedKeyValue().toString()));
//		filterinfo2.getFilterItems().add(new FilterItemInfo("offsetRule.id",getSelectedKeyValue().toString()));
//		if (AdvanceReceiveMatchFactory.getRemoteInstance().exists(filterinfo1) || AdvanceReceiveFactory.getRemoteInstance().exists(filterinfo2)){
//			FDCMsgBox.showWarning("该冲抵规则被引用，不能删除！");
//			SysUtil.abort();
//		}		
//		super.actionRemove_actionPerformed(e);
//	}
//	public void actionCancel_actionPerformed(ActionEvent e) throws Exception
//	{
//		super.actionCancel_actionPerformed(e);
//	}
//	public void actionCancelCancel_actionPerformed(ActionEvent e)
//			throws Exception
//	{
//		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
//		if (activeRowIndex < 0)
//			return;
//		
////		IRow row = this.tblMain.getRow(activeRowIndex);		
////		String moneyId = (String)row.getCell("moneyDefine.id").getValue();
////		String projectId = (String)row.getCell("sellProject.id").getValue();
////		
////		FilterInfo filter = new FilterInfo();
////		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",projectId));
////		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id",moneyId));
////		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
////		
////		boolean debug = MoneyDefineSellProjectFactory.getRemoteInstance().exists(filter);
////		
////		if(debug)
////		{
////			MsgBox.showWarning("在同一个项目下不能同时启用相同预收款名称的冲抵规则！");
////			this.abort();
////		}
//		
//		super.actionCancelCancel_actionPerformed(e);
//	}
//	protected KDTree getTreeCtrl()
//	{
//		return this.kDTree1;
//	}
//
//	protected String getTreeType()
//	{
//		return PPMHelper.TREE_TYPE_SELLPROJECT;
//	}
//
//	protected FDCDataBaseInfo getBaseDataInfo()
//	{
//		return new MoneyDefineSellProjectInfo();
//	}
//
//	protected boolean isSystemDefaultData(int activeRowIndex)
//	{
//		return false;
//	}
//	
//	
//	//===================================
//	
//	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
//		.getLastSelectedPathComponent();
//		//by tim_gao 原先使用的 PPMMeterHelper 把方法移到SHEHelper里了 2011-4-11
//		if (SHEHelper.checkCurrentBizFIOrg() != null) {
//			if(node.getUserObject() instanceof SellProjectInfo){
//				SellProjectInfo sellproject = (SellProjectInfo) node.getUserObject();
//				String sellprojectId = sellproject.getId().toString();
//				String orgUnitId = sellproject.getOrgUnit().getId().toString();
//				filter.getFilterItems().add( new FilterItemInfo("sellproject.id", sellprojectId));
//				filter.getFilterItems().add( new FilterItemInfo("orgUnit.id", orgUnitId));
//			}
//			if (node.getUserObject() instanceof OrgStructureInfo){
//				OrgStructureInfo orgStructInfo = (OrgStructureInfo)node.getUserObject();
//				String orgUnitId =orgStructInfo.getUnit().getId().toString();
//				filter.getFilterItems().add( new FilterItemInfo("orgUnit.id", orgUnitId));
//			}
//		} 
//		view.setFilter(filter);
//		return super.getQueryExecutor(queryPK, view);
//		
//	}
//	private void handlerButtonState() {
//		//在非销售实体组织组织下，新增、修改、删除、打印、打印预览、启用、禁用批按钮都为灰显
//		if (!saleOrg.isIsBizUnit()) {
//			actionAddNew.setEnabled(false);
//			actionEdit.setEnabled(false);
//			actionRemove.setEnabled(false);
//			actionPrint.setEnabled(false);
//			actionPrintPreview.setEnabled(false);
//			actionCancel.setEnabled(false);
//			actionCancelCancel.setEnabled(false);
//		}
//	}
//	
//	protected boolean isCreatTreeByPPMInfo() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception
//	{
//		super.treeMain_valueChanged(e);
//		handlerButtonState();
////		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
////		.getLastSelectedPathComponent();
////		if (node == null) {
////			return;
////		}
////
////		boolean isBizUnit = saleOrg.isIsBizUnit();
////		boolean isBuildingUnitNode = node.getUserObject() instanceof BuildingUnitInfo;
////		boolean isSubAreaNode = node.getUserObject() instanceof SubareaInfo;
////		boolean isBuildingLeafNode = node.getUserObject() instanceof BuildingInfo && node.isLeaf();
////		boolean isProjectNode = node.getUserObject() instanceof SellProjectInfo;
////		isAddableNode = isBuildingUnitNode || isSubAreaNode || isBuildingLeafNode || isProjectNode ;
////		this.actionAddNew.setEnabled(isBizUnit && isAddableNode);
////		
////		node.getUserObject();
////		EntityViewInfo ev = new EntityViewInfo();
////		FilterInfo filterInfo = new FilterInfo();
////		filterInfo.getFilterItems().add(new FilterItemInfo("sellproject.id","3fEJZTlqSQ2wdQEKnsbDzS/75aw="));
////		ev.setFilter(filterInfo);
////		removeQueryFilterAndSorter(mainQuery);
//////		if(!isProjectNode){//如果不是销售项目的时候，则为根节点
////			mainQuery = ev;
//////		}
////		
////		
////		this.execQuery();
//		
//		
//	}
//}