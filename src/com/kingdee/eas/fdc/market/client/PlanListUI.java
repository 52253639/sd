/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.market.CycleEnum;
import com.kingdee.eas.fdc.market.MovementPlanFactory;
import com.kingdee.eas.fdc.market.MovementPlanInfo;
import com.kingdee.eas.fdc.market.PlanEntryCollection;
import com.kingdee.eas.fdc.market.PlanEntryFactory;
import com.kingdee.eas.fdc.market.PlanFactory;
import com.kingdee.eas.fdc.market.PlanInfo;
import com.kingdee.eas.fdc.market.PlanTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PlanListUI extends AbstractPlanListUI {
	private static final Logger logger = CoreUIObject.getLogger(PlanListUI.class);

	String resClass = "com.kingdee.eas.fdc.market.client.PlanTypeResource.";

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	private String allCycleIds = "null"; // 所包含所有的活动类型id

	private String allProjectIds = "null"; // 所包含所有的销售项目id

	/**
	 * output class constructor
	 */
	public PlanListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {

		super.tblMain_tableClicked(e);
		int i = e.getRowIndex();
		if (i < 1) {
			return;
		}
		String id = this.getSelectedKeyValue();
		PlanInfo planInfo = PlanFactory.getRemoteInstance().getPlanInfo(new ObjectUuidPK(BOSUuid.read(id)));
		FDCBillStateEnum billState = planInfo.getState();
		if (FDCBillStateEnum.AUDITTED.equals(billState)) {
			this.actionUnAudit.setEnabled(true);
			this.actionAudit.setEnabled(false);
		} else if (FDCBillStateEnum.SAVED.equals(billState)) {
			this.actionUnAudit.setEnabled(false);
			this.actionAudit.setEnabled(false);
		} else if (FDCBillStateEnum.SUBMITTED.equals(billState)) {
			this.actionUnAudit.setEnabled(false);
			this.actionAudit.setEnabled(true);
		} else {
			this.actionUnAudit.setEnabled(false);
			this.actionAudit.setEnabled(true);
		}
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		this.checkSelected();
		String selectId = this.getSelectedKeyValue();
		if (this.tblTrackRecord.getRowCount() > 0)
			this.tblTrackRecord.removeRows();
		if (selectId != null) {
			reflashTrackRecordTable(selectId);
		}
		String id = this.getSelectedKeyValue();
		PlanInfo planInfo = PlanFactory.getRemoteInstance().getPlanInfo(new ObjectUuidPK(BOSUuid.read(id)));
		FDCBillStateEnum billState = planInfo.getState();
		if (FDCBillStateEnum.AUDITTED.equals(billState)) {
			this.actionUnAudit.setEnabled(true);
			this.actionAudit.setEnabled(false);
		} else if (FDCBillStateEnum.SAVED.equals(billState)) {
			this.actionUnAudit.setEnabled(false);
			this.actionAudit.setEnabled(false);
		} else if (FDCBillStateEnum.SUBMITTED.equals(billState)) {
			this.actionUnAudit.setEnabled(false);
			this.actionAudit.setEnabled(true);
		} else {
			this.actionUnAudit.setEnabled(false);
			this.actionAudit.setEnabled(true);
		}

	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		TreePath path = kDProjectTree.getSelectionPath();

		if (path == null) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "请选择具体项目！");
			com.kingdee.eas.util.SysUtil.abort();
		}

		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path.getLastPathComponent();

		if (!(treenode.getUserObject() instanceof SellProjectInfo)) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "请选择具体项目！");
			com.kingdee.eas.util.SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionView_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		String id = this.getSelectedKeyValue();
		PlanInfo planInfo = PlanFactory.getRemoteInstance().getPlanInfo(new ObjectUuidPK(BOSUuid.read(id)));
		FDCBillStateEnum billState = planInfo.getState();
		if (FDCBillStateEnum.AUDITTED.equals(billState)) {
			MsgBox.showInfo("该单据已经是审核状态，不能修改！");
			return;
		}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String id = this.getSelectedKeyValue();
		if (id == null || "".equals(id) || " ".equals(id)) {
			MsgBox.showInfo("该单据ID为空，请检查！");
			return;
		}
		PlanInfo planInfo = PlanFactory.getRemoteInstance().getPlanInfo(new ObjectUuidPK(BOSUuid.read(id)));
		FDCBillStateEnum billState = planInfo.getState();
		if (FDCBillStateEnum.AUDITTED.equals(billState)) {
			MsgBox.showInfo("该单据已经是审核状态，不能删除！");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
//		super.actionAttachment_actionPerformed(e);
		boolean isEdit=false;
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = this.getSelectedKeyValue();
        checkSelected();
        if (boID == null)
        {
            return;
        }
        if(getBillStatePropertyName()!=null){
        	int rowIdx=getMainTable().getSelectManager().getActiveRowIndex();
        	//因为之前将单据状态state写成了audited，故重写父类。
        	ICell cell = getMainTable().getCell(rowIdx, "audited");
        	Object obj=cell.getValue();
        	if(obj!=null&&
        			(obj.toString().equals(FDCBillStateEnum.SAVED.toString())
        			||obj.toString().equals(FDCBillStateEnum.SUBMITTED.toString())
        			||obj.toString().equals(FDCBillStateEnum.AUDITTING.toString())
        			||obj.toString().equals(BillStatusEnum.SAVE.toString())
        			||obj.toString().equals(BillStatusEnum.SUBMIT.toString())
        			||obj.toString().equals(BillStatusEnum.AUDITING.toString()))){
        		isEdit=true;
        	}else{
        		isEdit=false;
        	}
			
        }
        acm.showAttachmentListUIByBoID(boID, this,isEdit);
	}

	/**
	 * output actionExportData_actionPerformed
	 */
	public void actionExportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportData_actionPerformed(e);
	}

	/**
	 * output actionToExcel_actionPerformed
	 */
	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		super.actionToExcel_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception {
		super.actionPublishReport_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionQueryScheme_actionPerformed
	 */
	public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception {
		super.actionQueryScheme_actionPerformed(e);
	}

	/**
	 * output actionTDPrint_actionPerformed
	 */
	public void actionTDPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionTDPrint_actionPerformed(e);
	}

	/**
	 * output actionTDPrintPreview_actionPerformed
	 */
	public void actionTDPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionTDPrintPreview_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.PlanFactory.getRemoteInstance();
	}

	/**
	 * output getKeyFieldName method
	 */
	protected String getKeyFieldName() {
		return "id";
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.PlanInfo objectValue = new com.kingdee.eas.fdc.market.PlanInfo();

		return objectValue;
	}

	protected boolean initDefaultFilter() {
		return false;
	}

	protected String getGroupEditUIName() {
		// TODO 自动生成方法存根
		return PlanTypeEditUI.class.getName();
	}

	protected String getQueryFieldName() {
		// TODO 自动生成方法存根
		return "planType.id";
	}

	protected ITreeBase getTreeInterface() throws Exception {
		// TODO 自动生成方法存根
		return PlanTypeFactory.getRemoteInstance();
	}

	protected String getRootName() {
		return "计划类型";
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		// TODO 自动生成方法存根
		return null;
	}

	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return UIFactoryName.NEWWIN;
		} else {
			return UIFactoryName.NEWTAB;
		}
	}

	// 根据所选商机的id刷新跟踪记录表
	private void reflashTrackRecordTable(String selectId) throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("Parent.id", selectId));
		view.setFilter(filter);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("MovementPlan.*"));
		selector.add(new SelectorItemInfo("MovementPlan.sellProject.*"));
		selector.add(new SelectorItemInfo("MovementPlan.media.*"));
		selector.add(new SelectorItemInfo("MovementPlan.mmType.*"));
		view.setSelector(selector);
		PlanEntryCollection trackColl = PlanEntryFactory.getRemoteInstance().getPlanEntryCollection(view);
		this.tblTrackRecord.removeRows();
		for (int i = 0; i < trackColl.size(); i++) {
			MovementPlanInfo trackInfo = (MovementPlanInfo) trackColl.get(i).getMovementPlan();
			IRow row = this.tblTrackRecord.addRow();
			if (trackInfo == null)
				return;
			row.setUserObject(trackInfo);
			row.getCell("id").setValue(trackInfo.getId().toString());
			row.getCell("number").setValue(trackInfo.getNumber());
			row.getCell("name").setValue(trackInfo.getName());
			// row.getCell("media").setValue(trackInfo.getMedia());
			row.getCell("mmType").setValue(trackInfo.getMmType());
			SellProjectInfo sellProject = trackInfo.getSellProject();
			if (sellProject != null) {
				row.getCell("sellProject").setValue(sellProject.getName());
			}
			row.getCell("schemeType").setValue(trackInfo.getSchemeType());
			row.getCell("belongSystem").setValue(trackInfo.getBelongSystem());
			// row.getCell("predictVisit").setValue(String.valueOf(trackInfo.
			// getPredictVisit()));
			// row.getCell("predictBargain").setValue(String.valueOf(trackInfo.
			// getPredictBargain()));
			// row.getCell("predictCall").setValue(String.valueOf(trackInfo.
			// getPredictCall()));
			row.getCell("totalMoney").setValue(trackInfo.getTotalMoney());
		}
	}

	public void onLoad() throws Exception {
		this.btnAudit.setEnabled(false);
		this.btnUnAudit.setEnabled(false);
		this.btnBlankOut.setEnabled(true);
		super.onLoad();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			actionCancel.setEnabled(false);
			actionCancelCancel.setEnabled(false);
		}
		// 初始化营销单元树

		MarketClientHelper.getCycleTree(this.kDCycleTree);
		// this.treeMarketUnit.setSelectionRow(0);
		// 初始化项目树
		this.kDProjectTree.setModel(FDCTreeHelper.getSellProjectTree(actionOnLoad, null)); // SHEHelper
		this.kDProjectTree.expandAllNodes(true, (TreeNode) this.kDProjectTree.getModel().getRoot());
		this.tblMain.getColumn("sellProject.id").getStyleAttributes().setHided(true);
		this.actionAuditResult.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
	}

	public void initUIContentLayout() {
		super.initUIContentLayout();
		splitPaneRight.setDividerLocation(350);
		splitPaneLeft.setDividerLocation(350);

	}

	/**
	 * output kDTree1_valueChanged method
	 */
	protected void treeSellProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		TreePath path = kDProjectTree.getSelectionPath();
		if (path == null) {
			return;
		}

		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path.getLastPathComponent();

		if (treenode.getUserObject() != null && treenode.getUserObject() instanceof SellProjectInfo) {

			getUIContext().put("project", treenode.getUserObject());

		}
		this.execQuery();
		this.tblTrackRecord.removeRows();
	}

	/**
	 * output kDTree2_valueChanged method
	 */
	protected void cycleTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		TreePath path = kDCycleTree.getSelectionPath();
		if (path == null) {
			return;
		}

		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path.getLastPathComponent();

		if (treenode.getUserObject() != null && treenode.getUserObject() instanceof CycleEnum) {

			getUIContext().put("cycleType", treenode.getUserObject());

		}
		this.execQuery();
		this.tblTrackRecord.removeRows();
	}

	protected void tblTrackRecord_tableClickedEvent(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if (e.getType() != 1)
				return;

			MovementPlanInfo trackInfo = (MovementPlanInfo) this.tblTrackRecord.getRow(e.getRowIndex()).getUserObject();
			if (trackInfo == null)
				return;

			CommerceHelper.openTheUIWindow(this, MovementPlanEditUI.class.getName(), trackInfo.getId().toString());
		}
	}

	protected void tblMain_tableSelectChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		String selectId = this.getSelectedKeyValue();

		if (this.tblTrackRecord.getRowCount() > 0)
			this.tblTrackRecord.removeRows();

		if (selectId != null) {
			reflashTrackRecordTable(selectId);
			// if(trackColl.size()>0) this.tblTrackRecord.refresh();
		}
	}

	/**
	 * 查询节点下所有的活动类型
	 * 
	 * @param treeNode
	 */
	private void getAllMarketIds(TreeNode treeNode) {

		if (treeNode.isLeaf()) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
			if (thisNode.getUserObject() != null) {
				CycleEnum selected = (CycleEnum) thisNode.getUserObject();
				// thisNode.getUserObject().toString().substring(0,thisNode.
				// toString().length()-2)
				// CycleEnum selected=(CycleEnum) mstrName;
				allCycleIds += "," + selected.getValue();
			}
		} else {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllMarketIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	/**
	 * 查询所有的销售项目id
	 * 
	 * @param treeNode
	 */
	private void getAllProjectIds(TreeNode treeNode) {
		if (treeNode.isLeaf()) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
			if (thisNode.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo project = (SellProjectInfo) thisNode.getUserObject();
				allProjectIds += "," + project.getId().toString();
			}
		} else {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllProjectIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {

		TreeNode marketNode = (TreeNode) this.kDCycleTree.getLastSelectedPathComponent();
		TreeNode projectNode = (TreeNode) this.kDProjectTree.getLastSelectedPathComponent();

		allCycleIds = "null";
		if (marketNode != null)
			getAllMarketIds(marketNode);
		else
			getAllMarketIds((TreeNode) this.kDCycleTree.getModel().getRoot());

		allProjectIds = "null";
		if (projectNode != null)
			getAllProjectIds(projectNode);
		else
			getAllProjectIds((TreeNode) this.kDProjectTree.getModel().getRoot());

		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("cycleType", allCycleIds, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", allProjectIds, CompareType.INCLUDE));
		if (viewInfo.getFilter() == null)
			viewInfo.setFilter(filter);
		else {
			try {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected FilterInfo getDefaultFilterForQuery() {
		FilterInfo filter = new FilterInfo();
		return filter;
	}

	/**
	 * 
	 * 描述：当点击审核按钮时触发此事件，首先是查看是否选中行，然后是判断单据状态，如果该单据处于提交状态，才可以审核
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.fdc.pm.client.AbstractDesignTaskBookListUI#actionAudit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {

		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		List list = new ArrayList();
		for (int i = 0; i < this.tblTrackRecord.getRowCount(); i++) {
			list.add(this.tblTrackRecord.getCell(i, 0).getValue());
		}

		if (rowIndex == -1) {

			MsgBox.showInfo("请选择行!");

			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);

		BizEnumValueInfo billState = (BizEnumValueInfo) row.getCell("audited").getValue();

		if (FDCBillStateEnum.AUDITTED_VALUE.equalsIgnoreCase((String) billState.getValue())) {
			MsgBox.showInfo("该单据已经是审核状态，不能进行重复操作！");

			return;
		}

		if (!FDCBillStateEnum.SUBMITTED_VALUE.equalsIgnoreCase((String) billState.getValue())) {
			MsgBox.showInfo("该单据不是提交状态，不能进行审批操作！");

			return;
		}

		super.actionAudit_actionPerformed(e);

		if (MsgBox.showConfirm2New(this, "确认对该单据进行审批操作吗?") == MsgBox.YES) {

			String id = (String) row.getCell(this.getKeyFieldName()).getValue();
			PlanFactory.getRemoteInstance().audit(BOSUuid.read(id));
			MovementPlanFactory.getRemoteInstance().audit(list);
			this.refresh(null);
		}
	}

	/**
	 * 
	 * 描述：当点击反审核按钮时触发此事件，首先是查看是否选中行，然后是判断单据状态，如果已经通过审核，则可以进行反审核操作
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.fdc.pm.client.AbstractDesignTaskBookListUI#actionUnAudit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {

		super.actionUnAudit_actionPerformed(e);

		List list = new ArrayList();
		for (int i = 0; i < this.tblTrackRecord.getRowCount(); i++) {

			list.add(this.tblTrackRecord.getCell(i, 0).getValue());
		}

		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();

		if (rowIndex == -1) {

			MsgBox.showInfo("请选择行!");

			return;
		}

		IRow row = this.tblMain.getRow(rowIndex);

		BizEnumValueInfo billState = (BizEnumValueInfo) row.getCell("audited").getValue();

		if (FDCBillStateEnum.SAVED_VALUE.equalsIgnoreCase((String) billState.getValue())) {
			MsgBox.showInfo("该单据不是审核状态，不能进行反审批操作!");
			return;
		}

		String id = (String) row.getCell(this.getKeyFieldName()).getValue();

		if (MsgBox.showConfirm2New(this, "确认对该单据进行反审核操作吗?") == MsgBox.YES) {
			PlanFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
			MovementPlanFactory.getRemoteInstance().unAudit(list);
			this.refresh(null);
		}
	}

	/**
	 * 
	 * 描述：当点击作废按钮时触发此事件，首先是查看是否选中行，然后是判断单据状态，如果是保存或提交则可以作废
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.fdc.pm.client.AbstractDesignTaskBookListUI#actionBlankOut_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception {

		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();

		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}

		IRow row = this.tblMain.getRow(rowIndex);

		BizEnumValueInfo billState = (BizEnumValueInfo) row.getCell("audited").getValue();

		if (!FDCBillStateEnum.SAVED_VALUE.equalsIgnoreCase((String) billState.getValue()) && !FDCBillStateEnum.SUBMITTED_VALUE.equalsIgnoreCase((String) billState.getValue())) {
			MsgBox.showInfo("只有保存或者提交的单据才可以进行作废操作!");
			return;
		}

		String id = (String) row.getCell(this.getKeyFieldName()).getValue();

		if (MsgBox.showConfirm2New(this, "作废后不可恢复，请确认是否作废?") == MsgBox.YES) {
			PlanInfo planInfo = PlanFactory.getRemoteInstance().getPlanInfo(new ObjectUuidPK(BOSUuid.read(id)));
			planInfo.setState(FDCBillStateEnum.INVALID);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("state");
			PlanFactory.getRemoteInstance().updatePartial(planInfo, sels);
			ProcessInstInfo instInfo = null;
			ProcessInstInfo[] procInsts = null;
			IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
			procInsts = service.getProcessInstanceByHoldedObjectId(planInfo.getId().toString());
			for (int j = 0; j < procInsts.length; j++) {
				if ("open.running".equals(procInsts[j].getState())) {
					instInfo = procInsts[j];
					service.abortProcessInst(instInfo.getProcInstId());
				}
			}
			this.refresh(null);
		}
		super.actionBlankOut_actionPerformed(e);
	}

}