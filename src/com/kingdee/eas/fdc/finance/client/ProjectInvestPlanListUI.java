/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.IProjectInvestPlan;
import com.kingdee.eas.fdc.finance.ProjectInvestPlanCollection;
import com.kingdee.eas.fdc.finance.ProjectInvestPlanFactory;
import com.kingdee.eas.fdc.finance.ProjectInvestPlanInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProjectInvestPlanListUI extends AbstractProjectInvestPlanListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ProjectInvestPlanListUI.class);

	/**
	 * output class constructor
	 */
	public ProjectInvestPlanListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		if (!SysContext.getSysContext().getCurrentOrgUnit()
				.isIsCompanyOrgUnit()) {
			FDCMsgBox.showError("当前组织不是财务组织，不能进入！");
			abort();
		}
		ProjectTreeBuilder builder = new ProjectTreeBuilder();
		builder.build(this, projectTree, actionOnLoad);
		projectTree.setShowsRootHandles(true);
		projectTree.setSelectionRow(0);

		this.kDSplitPane1.setDividerLocation(280);
		initButtonStyle();
	}

	protected void initButtonStyle() {
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);

		this.actionQuery.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionPublish.setEnabled(true);
		this.actionRecension.setEnabled(true);

		// this.menuBiz.setVisible(false);

	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * 状态决定操作
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		actionEdit.setEnabled(false);
		actionRemove.setEnabled(false);
		actionAudit.setEnabled(false);
		actionUnAudit.setEnabled(false);
		actionPublish.setEnabled(false);
		actionRecension.setEnabled(false);

		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			BizEnumValueDTO dto = (BizEnumValueDTO) row.getCell("state")
					.getValue();
			if (dto != null) {
				if (FDCBillStateEnum.SAVED_VALUE.equals(dto.getValue())) {
					actionEdit.setEnabled(true);
					actionRemove.setEnabled(true);
				} else if (FDCBillStateEnum.SUBMITTED_VALUE.equals(dto
						.getValue())) {
					actionEdit.setEnabled(true);
					actionRemove.setEnabled(true);
					actionAudit.setEnabled(true);
				} else if (FDCBillStateEnum.AUDITTING_VALUE.equals(dto
						.getValue())) {
				} else if (FDCBillStateEnum.AUDITTED_VALUE.equals(dto
						.getValue())) {
					actionPublish.setEnabled(true);
					actionUnAudit.setEnabled(true);
				} else if (FDCBillStateEnum.PUBLISH_VALUE
						.equals(dto.getValue())) {
				} else if (FDCBillStateEnum.BACK_VALUE.equals(dto.getValue())) {
					actionRecension.setEnabled(true);
				}

			}
		}
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
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
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
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
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
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
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if (isHavePlanByProjectId(getProject().getId().toString())) {
			super.actionAddNew_actionPerformed(e);
		} else {
			FDCMsgBox.showError("当前工程项目已存在项目全生命周期资金投入计划，不能重复新增");
			abort();
		}

	}

	protected String getSelectedKeyValue() {
		// TODO Auto-generated method stub
		return super.getSelectedKeyValue();
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		super.actionView_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();

		if (FDCUtils.isRunningWorkflow(id)) {
			MsgBox.showWarning(this, "单据已在工作流中，不可修改!");
			SysUtil.abort();
		}
		String state = tblMain.getCell(
				tblMain.getSelectManager().getActiveRowIndex(), "state")
				.getValue().toString();
		if (isCanOpert(id, getCanRemoveListStatus())) {
			super.actionEdit_actionPerformed(e);
		} else {
			FDCMsgBox.showError("只有保存或提交的单据才能修改！");
			abort();
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
		String state = tblMain.getCell(
				tblMain.getSelectManager().getActiveRowIndex(), "state")
				.getValue().toString();
		if (isCanOpert(id, getCanRemoveListStatus())) {
			IProjectInvestPlan ip = ProjectInvestPlanFactory
					.getRemoteInstance();
			ProjectInvestPlanInfo info = ip
					.getProjectInvestPlanInfo(new ObjectUuidPK(id));
			BigDecimal versionNumber = info.getVersionNumber();
			String number = info.getNumber();
			String project = info.getProject().getId().toString();
			super.actionRemove_actionPerformed(e);
			// 修改旧版本状态
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder
					.appendSql("update T_FNC_ProjectInvestPlan set fstate='4AUDITTED' "
							+ "where FProjectID='"
							+ project
							+ "' and fnumber='"
							+ number
							+ "' "
							+ "and FVersionNumber="
							+ versionNumber.subtract(new BigDecimal("0.1"))
							+ "");
			builder.execute();
			this.refresh(null);
		} else {
			FDCMsgBox.showError("当前单据为" + state + "状态,不能对其进行删除！");
			abort();
		}
	}

	protected CurProjectInfo getProject() {
		CurProjectInfo curProject = null;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.projectTree
				.getLastSelectedPathComponent();
		if (node.isLeaf() && node != null
				&& node.getUserObject() instanceof CurProjectInfo) {
			curProject = ((CurProjectInfo) node.getUserObject());
		} else {
			FDCMsgBox.showWarning("必须选择明细的工程项目才能编制投入计划！");
			abort();
		}
		return curProject;
	}

	/***
	 * 获取项目树中的所有叶子节点的ID
	 */
	private void getLeafIds(DefaultKingdeeTreeNode node, Set idSet) {
		if (node.isLeaf()) {
			if (node.getUserObject() instanceof CurProjectInfo) {
				idSet.add(((CurProjectInfo) node.getUserObject()).getId()
						.toString());
			}
			if (node.getUserObject() instanceof OrgStructureInfo) {
				idSet.add(((OrgStructureInfo) node.getUserObject()).getId()
						.toString());
			}
			if (node.getUserObject() instanceof FullOrgUnitInfo) {
				idSet.add(((FullOrgUnitInfo) node.getUserObject()).getId()
						.toString());
			}
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode) node
						.getChildAt(i);
				getLeafIds(childNode, idSet);
			}
		}
	}

	/**
	 * 得到当前节点的所有包含的项目ID
	 * 
	 * @return
	 */
	protected Set getSelectedProjectIds() {
		Set idSet = new HashSet();
		DefaultKingdeeTreeNode node = getSelectNode(projectTree);
		if (node != null) {
			getLeafIds(node, idSet);
		}
		return idSet;
	}

	/**
	 * 得到当前项目树中被选中的节点
	 * 
	 * @param tree
	 * @return
	 */
	protected DefaultKingdeeTreeNode getSelectNode(KDTree tree) {
		DefaultKingdeeTreeNode node = null;
		if (tree.getLastSelectedPathComponent() != null) {
			node = (DefaultKingdeeTreeNode) tree.getLastSelectedPathComponent();
		}
		return node;
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
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
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
	public void actionImportData_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionExportData_actionPerformed
	 */
	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception {
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
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e)
			throws Exception {
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
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionQueryScheme_actionPerformed
	 */
	public void actionQueryScheme_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionQueryScheme_actionPerformed(e);
	}

	/**
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	/**
	 * output actionCopyTo_actionPerformed
	 */
	public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyTo_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceDown_actionPerformed(e);
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionMultiapprove_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}

	/**
	 * output actionSendSmsMessage_actionPerformed
	 */
	public void actionSendSmsMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendSmsMessage_actionPerformed(e);
	}

	/**
	 * output actionSignature_actionPerformed
	 */
	public void actionSignature_actionPerformed(ActionEvent e) throws Exception {
		super.actionSignature_actionPerformed(e);
	}

	/**
	 * output actionWorkflowList_actionPerformed
	 */
	public void actionWorkflowList_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionWorkflowList_actionPerformed(e);
	}

	/**
	 * output actoinViewSignature_actionPerformed
	 */
	public void actoinViewSignature_actionPerformed(ActionEvent e)
			throws Exception {
		super.actoinViewSignature_actionPerformed(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return ProjectInvestPlanFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return ProjectInvestPlanEditUI.class.getName();
	}

	protected String getEditUIModal() {
		// TODO Auto-generated method stub
		return UIFactoryName.NEWTAB;
	}

	/***
	 * 自动带入明细的工程项目
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		// TODO Auto-generated method stub
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionRecension)) {
			uiContext.put("recension", "true");
		} else {
			uiContext.put("recension", "false");
		}
		super.prepareUIContext(uiContext, e);
		if (e.getActionCommand().endsWith("AddNew")) {
			CurProjectInfo curProject = getProject();
			uiContext.put("curProject", curProject);
		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		// super.actionAudit_actionPerformed(e);
		checkSelected();

		if (tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(),
				"id").getValue() != null) {
			String state = tblMain.getCell(
					tblMain.getSelectManager().getActiveRowIndex(), "state")
					.getValue().toString().trim();
			String id = tblMain.getCell(
					tblMain.getSelectManager().getActiveRowIndex(), "id")
					.getValue().toString();
			FDCClientUtils.checkBillInWorkflow(this, id);
			if (isCanOpert(id, getCanAuditListStatus())) {
				ProjectInvestPlanFactory.getRemoteInstance().audit(
						BOSUuid.read(id));
			} else {
				FDCMsgBox.showError("当前单据状态为" + state + "状态,不能对其进行审批！");
				abort();
			}
			this.refresh(null);
		}
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		// super.actionUnAudit_actionPerformed(e);
		checkSelected();
		String id = tblMain.getCell(
				tblMain.getSelectManager().getActiveRowIndex(), "id")
				.getValue().toString();
		String state = tblMain.getCell(
				tblMain.getSelectManager().getActiveRowIndex(), "state")
				.getValue().toString().trim();
		if (tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(),
				"id").getValue() != null) {
			if (isCanOpert(id, getCanUnAuditListStatus())) {
				ProjectInvestPlanFactory.getRemoteInstance().unAudit(
						BOSUuid.read(id));
			} else {
				FDCMsgBox.showError("当前单据为" + state + "状态,不能对其进行反审批！");
				abort();
			}
			this.refresh(null);
		}
	}

	protected void projectTree_valueChanged(TreeSelectionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.projectTree_valueChanged(e);
		tblMain.removeRows();
		this.refresh(null);
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		// TODO Auto-generated method stub
		// if(viewInfo != null){
		// FilterInfo filter = viewInfo.getFilter();
		// filter.getFilterItems().add(new
		// FilterItemInfo("project.id",getSelectedProjectIds
		// (),CompareType.INCLUDE));
		// try {
		// viewInfo.getFilter().mergeFilter(filter, "and");
		// } catch (BOSException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		FilterInfo filter = getMainFilter();
		if (viewInfo != null) {
			viewInfo.setFilter(filter);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected boolean isHavePlanByProjectId(String projectId) {
		boolean isHavePlan = true;
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("project.id", projectId));
		viewInfo.setFilter(filter);
		ProjectInvestPlanCollection cols = null;
		try {
			cols = ProjectInvestPlanFactory.getRemoteInstance()
					.getProjectInvestPlanCollection(viewInfo);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (cols != null && cols.size() > 0) {
			isHavePlan = false;
		}
		return isHavePlan;
	}

	protected boolean isCanOpert(String id, List canStatus)
			throws EASBizException, BOSException {
		boolean isOk = false;
		ProjectInvestPlanInfo inf = ProjectInvestPlanFactory
				.getRemoteInstance().getProjectInvestPlanInfo(
						new ObjectUuidPK(id));
		if (!canStatus.isEmpty()) {
			isOk = canStatus.contains(inf.getState().getValue());
		}
		return isOk;
	}

	public List getCanAuditListStatus() {
		List statusList = new ArrayList();
		statusList.add(FDCBillStateEnum.SUBMITTED_VALUE);
		return statusList;
	}

	public List getCanRemoveListStatus() {
		List statusList = new ArrayList();
		statusList.add(FDCBillStateEnum.SUBMITTED_VALUE);
		statusList.add(FDCBillStateEnum.SAVED_VALUE);
		return statusList;
	}

	public List getCanUnAuditListStatus() {
		List statusList = new ArrayList();
		statusList.add(FDCBillStateEnum.AUDITTED_VALUE);
		return statusList;
	}

	protected FilterInfo getMainFilter() {
		FilterInfo filter = new FilterInfo();
		Set projectSet = new HashSet();
		projectSet = getSelectedProjectIds();
		if (projectSet.size() > 0)
			filter.getFilterItems().add(
					new FilterItemInfo("project.id", projectSet,
							CompareType.INCLUDE));
		filter.getFilterItems().add(
				new FilterItemInfo("cu.longnumber", SysContext.getSysContext()
						.getCurrentCtrlUnit().getLongNumber()
						+ "%", CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit.longnumber", SysContext
						.getSysContext().getCurrentOrgUnit().getLongNumber()
						+ "%", CompareType.LIKE));
		return filter;
	}

	/**
	 * 上报
	 */
	public void actionPublish_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(),
				"id").getValue() != null) {
			String state = tblMain.getCell(
					tblMain.getSelectManager().getActiveRowIndex(), "state")
					.getValue().toString().trim();
			String id = tblMain.getCell(
					tblMain.getSelectManager().getActiveRowIndex(), "id")
					.getValue().toString();
			FDCClientUtils.checkBillInWorkflow(this, id);
			if (isCanOpert(id, getCanUnAuditListStatus())) {
				if (MsgBox.showConfirm2("是否确定上报？") == 0) {
					IProjectInvestPlan ip = ProjectInvestPlanFactory
							.getRemoteInstance();
					ip.setPublish(id);

				}
			} else {
				FDCMsgBox.showError("只有已审批的单据才能上报！");
				abort();
			}
			this.refresh(null);
		}
	}

	/**
	 * 修订
	 */
	public void actionRecension_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		CoreBillBaseInfo billInfo = ProjectInvestPlanFactory
				.getRemoteInstance().getCoreBillBaseInfo(
						new ObjectUuidPK(getSelectedKeyValue()));
		String billState = billInfo.getString(getBillStatePropertyName());
		if (billState.equals(FDCBillStateEnum.BACK_VALUE)) {
			super.actionEdit_actionPerformed(e);
		} else {
			MsgBox.showWarning("只有被打回的单据才能修订！");
			abort();
		}
	}
}