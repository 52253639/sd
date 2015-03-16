/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.market.MarketManageFactory;
import com.kingdee.eas.fdc.market.MarketTypeInfo;
import com.kingdee.eas.fdc.market.MediaFactory;
import com.kingdee.eas.fdc.market.MediaInfo;
import com.kingdee.eas.fdc.market.MovementPlanFactory;
import com.kingdee.eas.fdc.market.MovementPlanInfo;
import com.kingdee.eas.fdc.market.SchemeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.DayInitDataBldListUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class MovementPlanListUI extends AbstractMovementPlanListUI {
	private static final Logger logger = CoreUIObject.getLogger(MovementPlanListUI.class);
	private String allMarketIds = "null"; // 所包含所有的活动类型id
	private String allProjectIds = "null"; // 所包含所有的销售项目id

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public MovementPlanListUI() throws Exception {
		super();
	}
	protected OrgType getMainBizOrgType()
    {
        return OrgType.Sale;
    }

	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
		String id = this.getSelectedKeyValue();
		if (id == null)
			return;
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", id);
		uiContext.put("isCopy", id);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(MovementPlanEditUI.class.getName(), uiContext, null, OprtState.EDIT);
		uiWindow.show();

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
		//int i = e.getRowIndex();
//		这里的判断使新增的时候第一个不能够修改其按钮状态  xiaoao_liu
		/*if(i<1){
			return;
		}*/
		int i  = this.tblMain.getSelectManager().getActiveRowIndex();
		if(i<0){
			return;
		}
		
		IRow row = tblMain.getRow(i);
		String str = row.getCell("schemeType").getValue().toString();
		String id = this.getSelectedKeyValue();
		MovementPlanInfo movementPlanInfo = MovementPlanFactory.getRemoteInstance().getMovementPlanInfo(new ObjectUuidPK(BOSUuid.read(id)));
		FDCBillStateEnum billState = movementPlanInfo.getState();
		if (FDCBillStateEnum.AUDITTED.equals(billState)) {
			this.btnAudit.setEnabled(false);
			this.btnUnAudit.setEnabled(true);
		} else if (FDCBillStateEnum.SUBMITTED.equals(billState)) {
			this.btnAudit.setEnabled(true);
			this.btnUnAudit.setEnabled(false);
		} else if (FDCBillStateEnum.SAVED.equals(billState)) {
			this.btnAudit.setEnabled(false);
			this.btnUnAudit.setEnabled(false);
		} else {
			this.btnAudit.setEnabled(false);
			this.btnUnAudit.setEnabled(false);
		}
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
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
	 * 
	 * 描述：新增判断是否选中具体销售项目，如果没选中则给出提示
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.framework.client.AbstractListUI#actionAddNew_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {

		TreePath projectPath = kDTree1.getSelectionPath();

		if (projectPath == null) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "请选择具体项目！");
			com.kingdee.eas.util.SysUtil.abort();
		}

		DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode) projectPath.getLastPathComponent();

		if (!(projectNode.getUserObject() instanceof SellProjectInfo)) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "请选择具体项目！");
			com.kingdee.eas.util.SysUtil.abort();
		}

		TreePath marketPath = kDTree2.getSelectionPath();

		if (marketPath == null) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "请选择具体活动类型！");
			com.kingdee.eas.util.SysUtil.abort();
		}

		DefaultKingdeeTreeNode marketNode = (DefaultKingdeeTreeNode) marketPath.getLastPathComponent();
		if (marketNode.isRoot()) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "请先录入活动类型！");
			com.kingdee.eas.util.SysUtil.abort();
		}
		if (!marketNode.isLeaf()) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "请选择具体活动类型！");
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
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		String id = this.getSelectedKeyValue();
		MovementPlanInfo movementPlanInfo = MovementPlanFactory.getRemoteInstance().getMovementPlanInfo(new ObjectUuidPK(BOSUuid.read(id)));
		FDCBillStateEnum billState = movementPlanInfo.getState();
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
		if(id==null||"".equals(id)||" ".equals(id)){
			MsgBox.showInfo("该单据ID为空，请检查！");
			return;
		}
		MovementPlanInfo movementPlanInfo = MovementPlanFactory.getRemoteInstance().getMovementPlanInfo(new ObjectUuidPK(BOSUuid.read(id)));
		FDCBillStateEnum billState = movementPlanInfo.getState();
		if (FDCBillStateEnum.AUDITTED.equals(billState)) {
			MsgBox.showInfo("该单据已经是审核状态，不能删除！");
			return;
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("marketPlan.id", id));
		if (MarketManageFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("该活动方案已被营销活动关联，不能删除！");
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
		super.actionAttachment_actionPerformed(e);
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
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {

		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionAudit_actionPerformed
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		String id = this.getSelectedKeyValue();
		MovementPlanInfo movementPlanInfo = MovementPlanFactory.getRemoteInstance().getMovementPlanInfo(new ObjectUuidPK(BOSUuid.read(id)));
		FDCBillStateEnum billState = movementPlanInfo.getState();
		if (!FDCBillStateEnum.SUBMITTED.equals(billState)) {
			MsgBox.showInfo("该单据不是提交状态，不能进行审批操作！");
			return;
		}
		if (MsgBox.showConfirm2New(this, "确认对该单据进行审批操作吗?") == MsgBox.YES) {
			MovementPlanFactory.getRemoteInstance().audit(BOSUuid.read(id));
			this.refresh(null);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(true);
		}
	}

	/**
	 * output actionWorkflowG_actionPerformed
	 */
	public void actionWorkflowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkflowG_actionPerformed(e);
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
		return com.kingdee.eas.fdc.market.MovementPlanFactory.getRemoteInstance();
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
		com.kingdee.eas.fdc.market.MovementPlanInfo objectValue = new com.kingdee.eas.fdc.market.MovementPlanInfo();

		return objectValue;
	}

	/**
	 * 
	 * 描述：<方法描述>使新增页面以页签的形式打开
	 * 
	 * @author:ZhangFeng 创建时间：2009-3-13
	 *                   <p>
	 */

	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return UIFactoryName.NEWWIN;
		} else {
			return UIFactoryName.NEWTAB;
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();

		this.menuBiz.setVisible(false);// 业务
		this.menuTool.setVisible(false);// 工具
		this.btnWorkFlowG.setVisible(false);// 流程图
		this.btnAuditResult.setVisible(false);
		this.btnUnAudit.setVisible(true);
		this.btnAudit.setVisible(true);
		this.actionCopy.setVisible(true);
		this.actionCopy.setEnabled(true);

		tblMain.removeRows();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.btnWorkFlowG.setVisible(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionCopy.setEnabled(false);
		}

		// 初始化活动类型树

		MarketClientHelper.getMarketTypeTree(kDTree2);
		// 初始化销售项目树
		this.kDTree1.setModel(SHEHelper.getSellProjectTree(this.actionAbout));
		this.kDTree1.expandAllNodes(true, (TreeNode) this.kDTree2.getModel().getRoot());
		this.tblMain.getColumn("orgName").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("bizDate").getStyleAttributes().setHided(false);
		this.tblMain.getMergeManager().setMergeMode(this.tblMain.getMergeManager().NO_MERGE);
		loadTableValue();
		this.tblMain.getColumn("state").setMergeable(false);
		this.tblMain.getColumn("totalMoney").getStyleAttributes().setNumberFormat("#,###.00");
		this.tblMain.getColumn("totalMoney").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
	}

	public void loadTableValue() {
		IRow row = tblMain.getRow(0);
		if (row != null) {
			String str = String.valueOf(row.getCell("schemeType").getValue());
//			if (str.equals("独立方案")) {
				this.btnUnAudit.setVisible(true);
				this.btnAudit.setVisible(true);
				this.btnAudit.setEnabled(false);
				this.btnUnAudit.setEnabled(false);
				String id = row.getCell("id").getValue().toString();
				MovementPlanInfo movementPlanInfo = new MovementPlanInfo();
				try {
					movementPlanInfo = MovementPlanFactory.getRemoteInstance().getMovementPlanInfo(new ObjectUuidPK(BOSUuid.read(id)));
				} catch (EASBizException e) {
					handleException(e);
					e.printStackTrace();
				} catch (BOSException e) {
					handleException(e);
					e.printStackTrace();
				} catch (UuidException e) {
					handleException(e);
					e.printStackTrace();
				}
				FDCBillStateEnum billState = movementPlanInfo.getState();
				if (FDCBillStateEnum.AUDITTED.equals(billState)) {
					this.btnAudit.setEnabled(false);
					this.btnUnAudit.setEnabled(true);
				} else if (FDCBillStateEnum.SUBMITTED.equals(billState)) {
					this.btnAudit.setEnabled(true);
					this.btnUnAudit.setEnabled(false);
				} else if (FDCBillStateEnum.SAVED.equals(billState)) {
					this.btnAudit.setEnabled(false);
					this.btnUnAudit.setEnabled(false);
				} else {
					this.btnAudit.setEnabled(false);
					this.btnUnAudit.setEnabled(false);
				}
//			} else {
//				this.btnUnAudit.setEnabled(false);
//				this.btnAudit.setEnabled(false);
//			}
		}
	}

	public void initUIContentLayout() {
		super.initUIContentLayout();
		kDSplitPaneLeft.setDividerLocation(350);

	}

	/**
	 * 
	 * 描述：营销项目树节点变化事件，并且把选中的营销项目存入UI，是单据新增页面自动存值
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.fdc.market.client.AbstractMarketManageListUI#sellProjectTree_valueChanged(javax.swing.event.TreeSelectionEvent)
	 */

	protected void kDTree1_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {

		TreePath path = kDTree1.getSelectionPath();
		if (path == null) {
			return;
		}

		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path.getLastPathComponent();

		if (treenode.getUserObject() != null && treenode.getUserObject() instanceof SellProjectInfo) {

			getUIContext().put("project", treenode.getUserObject());

		}
		this.execQuery();
		loadTableValue();
	}

	/**
	 * 
	 * 描述：活动类型树节点变化事件
	 * 
	 * @author:ZhangFeng
	 */
	protected void kDTree2_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {

		TreePath path = kDTree2.getSelectionPath();
		if (path == null) {
			return;
		}

		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path.getLastPathComponent();

		if (treenode.getUserObject() != null && treenode.getUserObject() instanceof MarketTypeInfo) {

			getUIContext().put("marketType", treenode.getUserObject());

		}

		this.execQuery();
		loadTableValue();
	}

	/**
	 * 查询节点下所有的活动类型
	 * 
	 * @param treeNode
	 */
	private void getAllMarketIds(TreeNode treeNode) {
		if (treeNode.isLeaf()) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
			if (thisNode.getUserObject() instanceof MarketTypeInfo) {
				MarketTypeInfo market = (MarketTypeInfo) thisNode.getUserObject();
				if (allMarketIds != null) {
					allMarketIds += "," + market.getId().toString();
				} else {
					allMarketIds = market.getId().toString();
				}
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
				if (allProjectIds != null) {
					allProjectIds += "," + project.getId().toString();
				} else {
					allProjectIds = project.getId().toString();
				}

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

		TreeNode marketNode = (TreeNode) this.kDTree2.getLastSelectedPathComponent();
		TreeNode projectNode = (TreeNode) this.kDTree1.getLastSelectedPathComponent();

		allMarketIds = "null";
		if (marketNode != null)
			getAllMarketIds(marketNode);
		else
			getAllMarketIds((TreeNode) this.kDTree2.getModel().getRoot());

		allProjectIds = "null";
		if (projectNode != null)
			getAllProjectIds(projectNode);
		else
			getAllProjectIds((TreeNode) this.kDTree1.getModel().getRoot());

		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("mmType.id", allMarketIds, CompareType.INCLUDE));
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

	protected boolean initDefaultFilter() {
		return false;
	}

	/**
	 * output actionUnAudit_actionPerformed method
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		/**
		 * 
		 * 描述：当点击反审核按钮时触发此事件，首先是查看是否选中行，然后是判断单据状态，如果已经通过审核，则可以进行反审核操作
		 * 
		 * @author:ZhangFeng
		 * @see com.kingdee.eas.fdc.pm.client.AbstractDesignTaskBookListUI#actionUnAudit_actionPerformed(java.awt.event.ActionEvent)
		 */

		super.actionUnAudit_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		String id = this.getSelectedKeyValue();
		MovementPlanInfo movementPlanInfo = MovementPlanFactory.getRemoteInstance().getMovementPlanInfo(new ObjectUuidPK(BOSUuid.read(id)));
		FDCBillStateEnum billState = movementPlanInfo.getState();
		if (!FDCBillStateEnum.AUDITTED.equals(billState)) {
			MsgBox.showInfo("该单据不是审核状态，不能进行反审批操作!");
			return;
		}
		if (MsgBox.showConfirm2New(this, "确认对该单据进行反审核操作吗?") == MsgBox.YES) {
			MovementPlanFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
			this.refresh(null);
			this.actionAudit.setEnabled(true);
			this.actionUnAudit.setEnabled(false);
		}
	}

}