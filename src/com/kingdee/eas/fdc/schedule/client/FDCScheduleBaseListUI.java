/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.Action;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FDCScheduleBaseListUI extends AbstractFDCScheduleBaseListUI {
	private static final Logger logger = CoreUIObject.getLogger(FDCScheduleBaseListUI.class);

	/**
	 * output class constructor
	 */
	public FDCScheduleBaseListUI() throws Exception {
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
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
		updateButtonStatus();
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
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
		IRow selectedRow = FDCTableHelper.getSelectedRow(tblMain);
		BizEnumValueInfo value = (BizEnumValueInfo) selectedRow.getCell("state").getValue();
		if (value != null && (value.getValue().equals(ScheduleStateEnum.SAVED_VALUE) || value.getValue().equals(ScheduleStateEnum.SUBMITTED_VALUE))) {
			super.actionEdit_actionPerformed(e);
		} else {
			FDCMsgBox.showWarning(this, "单据状态不适合此操作！");
		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkBillState(getStateForAudit(), "selectRightRowForAudit");
		audit(ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName()));
		showOprtOKMsgAndRefresh();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkBillState(getStateForUnAudit(), "selectRightRowForUnAudit");
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());
		// 检查引用
		for (Iterator iter = selectedIdValues.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			checkRef(id);
		}

		unAudit(selectedIdValues);
		showOprtOKMsgAndRefresh();
	}

	/**
	 * output actionClose_actionPerformed method
	 */
	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());
		FDCScheduleFactory.getRemoteInstance().close(new HashSet(selectedIdValues));
		refreshList();
	}

	public void actionUnClose_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());
		FDCScheduleFactory.getRemoteInstance().unClose(new HashSet(selectedIdValues));
		refreshList();
	}

	public void onLoad() throws Exception {
		buildTree();
		super.onLoad();
		treeMain.expandRow(0);
		treeMain.setSelectionRow(0);
		this.afterInit = true;
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
	}

	public void onShow() throws Exception {
		super.onShow();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		viewInfo = (EntityViewInfo) viewInfo.clone();
		try {
			if (viewInfo.getFilter() == null) {
				viewInfo.setFilter(getMainFilter());
			} else {
				viewInfo.getFilter().mergeFilter(getMainFilter(), "and");
			}
		} catch (Exception e) {
			this.handUIException(e);
		}
		IQueryExecutor exec = super.getQueryExecutor(queryPK, viewInfo);
		exec.option().isIgnoreOrder = false;
		return exec;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		actionUnAudit.setEnabled(true);
		actionClose.setEnabled(true);
		actionCancel.setVisible(false);
		actionQuery.setVisible(false);
		actionClose.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_close"));
		actionUnClose.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_fclose"));
		actionAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_AUDIT);
		actionUnAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_UNAUDIT);
		actionCreateTo.setVisible(false);
		actionCopyTo.setVisible(false);
		// 启用改成执行
		actionCancelCancel.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_datatask_execute"));
	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();
		actionQuery.setVisible(false);
		actionTraceDown.setVisible(false);
		actionTraceUp.setVisible(false);
	}

	protected FilterInfo getMainFilter() throws Exception {
		// 做工程项目隔离及部门隔离
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		Set prjIdSet = getProjectLeafsOfNode(node);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", prjIdSet, CompareType.INCLUDE));
		// 不做部门的隔离
		// filter.getFilterItems().add(new
		// FilterItemInfo("adminDept.id",currentOrgId));
		return filter;
	}

	/**
	 * 获取选择节点的所有下级明细工程项目
	 * 
	 * @return
	 */
	protected Set getProjectLeafsOfNode(DefaultKingdeeTreeNode node) {
		Set idSet = new HashSet();
		if (node != null) {
			if (node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo prj = (CurProjectInfo) node.getUserObject();
				if (prj.isIsLeaf()) {
					idSet.add(prj.getId().toString());
					return idSet;
				}
			}
			// 直接遍历树去到节点可以保证一致性
			Enumeration childrens = node.depthFirstEnumeration();
			while (childrens.hasMoreElements()) {
				DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode) childrens.nextElement();
				if (childNode.getUserObject() instanceof CurProjectInfo) {
					CurProjectInfo prj = (CurProjectInfo) childNode.getUserObject();
					if (prj.isIsLeaf()) {
						idSet.add(prj.getId().toString());
					}
				}
			}
		}
		if (idSet.size() == 0) {
			// 如果选择的节点下没有任何明细的工程项目节点则随便加个节点保证过滤不出来任何数据
			idSet.add(OrgConstants.DEF_CU_ID);
		}
		return idSet;
	}

	protected void audit(List ids) throws Exception {
		FDCScheduleFactory.getRemoteInstance().audit(new HashSet(ids));
	}

	protected void unAudit(List ids) throws Exception {
		FDCScheduleFactory.getRemoteInstance().unAudit(new HashSet(ids));
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return FDCScheduleFactory.getRemoteInstance();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCScheduleFactory.getRemoteInstance();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWWIN;
	}

	protected String getEditUIName() {
		return FDCScheduleBaseEditUI.class.getName();
	}

	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}

	protected void checkBillState(String state, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBaseInfo element = (CoreBaseInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());

			if (!element.getString(getBillStatePropertyName()).equals(state)) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}

	/**
	 * 
	 * 描述：检查是否有关联对象(删除前调用)
	 * 
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 */
	protected void checkRef(String id) {

	}

	/**
	 * 
	 * 描述：审核操作的单据前置状态
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}

	/**
	 * 
	 * 描述：反审核操作的单据前置状态
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}

	/**
	 * 
	 * 描述：单据状态属性名称，基类提供缺省实现
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 */
	protected String getBillStatePropertyName() {
		return "state";
	}

	protected void buildTree() throws Exception {
		OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		try {
			// 用财务组织构造树保证下级组织能够看到其他的项目
			SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentFIUnit());
			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

			projectTreeBuilder.build(this, treeMain, actionOnLoad);
		} finally {
			SysContext.getSysContext().setCurrentOrgUnit(currentOrgUnit);
		}
		treeMain.setShowsRootHandles(true);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		ItemAction action = getActionFromActionEvent(e);
		if (action.equals(actionAddNew)) {
			CurProjectInfo prj = getSelectProject();
			if (prj == null) {
				FDCMsgBox.showWarning(this, "请选择工程项目!");
				SysUtil.abort();
			}
			uiContext.put("CurProject", prj);
		}
		super.prepareUIContext(uiContext, e);
	}

	/**
	 * 得到当前选择的对象工程项目,组织ID,或Null
	 * 
	 * @return 当前选择的对象工程项目,组织ID,或Null
	 */
	protected Object getSelectObj() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {
				return null;
			}
			FullOrgUnitInfo info = oui.getUnit();
			return info;
		}
		return null;
	}

	/**
	 * 选择的工程项目
	 * 
	 * @return
	 */
	protected CurProjectInfo getSelectProject() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			return null;
		}
		return null;
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null) {
			this.refresh(null);
		}
	}

	private boolean afterInit = false;

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		if (!afterInit) {
			return;
		}
		super.actionQuery_actionPerformed(e);
	}

	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		if (!isTableTreeRow()) {
			return;
		}
		getMainTable().getTreeColumn().setDepth(2);
		for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
			IRow row = getMainTable().getRow(i);
			if (Boolean.TRUE.equals(row.getCell("isLatestVer").getValue())) {
				row.setTreeLevel(0);
				row.setCollapse(true);
			} else {
				row.setTreeLevel(1);
				row.getStyleAttributes().setHided(true);
			}
		}
	}

	protected boolean isTableTreeRow() {
		return false;
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCancelCancel_actionPerformed(e);
		checkSelected();
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());
		FDCScheduleFactory.getRemoteInstance().checkScheduleState(new HashSet(selectedIdValues));
		if (FDCMsgBox.YES != FDCMsgBox.showConfirm2New(this, "执行后的计划不能进行逆向操作， 是否确认计划开始执行？")) {
			SysUtil.abort();
		}
		FDCScheduleFactory.getRemoteInstance().cancelCancel(new HashSet(selectedIdValues));
		refreshList();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("state");
		return sic;
	}
}