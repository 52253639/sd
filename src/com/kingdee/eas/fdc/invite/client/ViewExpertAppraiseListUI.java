/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.ITextIconDisplayStyle;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectStateEnum;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 专家评标结果汇总
 */
public class ViewExpertAppraiseListUI extends AbstractViewExpertAppraiseListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ViewExpertAppraiseListUI.class);

	/**
	 * output class constructor
	 */
	public ViewExpertAppraiseListUI() throws Exception {
		super();
	}

	/**
	 * output treeInviteType_valueChanged method
	 */
	protected void treeInviteType_valueChanged(
			javax.swing.event.TreeSelectionEvent e) throws Exception {
		super.treeInviteType_valueChanged(e);
		treeSelectChange();
	}

	/**
	 * output treeOrg_valueChanged method
	 */
	protected void treeOrg_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		super.treeOrg_valueChanged(e);
		treeSelectChange();
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		int index = tblMain.getSelectManager().getActiveRowIndex();
		if (index > -1) {
			IRow row = tblMain.getRow(index);
			if (row.getCell("isExpertAppraise").getValue() != null) {
				Boolean isHas = (Boolean) (row.getCell("isExpertAppraise")
						.getValue());
				if (!isHas.booleanValue()) {
					FDCMsgBox.showWarning(this, "请选择有专家评标的招标立项查看专家评标结果汇总");
					abort();
				}
			}
		} else {
			FDCMsgBox.showWarning(this, "请选择行");
			abort();
		}

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, this.getSelectedKeyValue());
		IUIWindow selectUI = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(ViewExpertAppraiseResultUI.class.getName(), uiContext,
						null, "View");
		selectUI.show();
	}

	protected String getKeyFieldName() {
		return "id";
	}
	protected String getSelectedInviteProjectId()
    {
    	String keyFiledName="id";
        int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
        return ListUiHelper.getSelectedKeyValue(selectRows,this.tblMain,keyFiledName);
    }

	protected ICoreBase getBizInterface() throws Exception {
		return InviteProjectFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.setIsNeedDefaultFilter(true);
		this.btnQuery.setVisible(true);

		this.actionAddNew.setVisible(false);
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setVisible(false);

		this.actionAttachment.setVisible(false);
		this.actionAttachment.setEnabled(false);

		this.btnRemove.setVisible(false);
		this.btnRemove.setEnabled(false);
		this.btnView.setText("查看专家评标结果");
		this.btnView.setToolTipText("查看专家评标结果");
		this.btnView.setTextIconDisStyle(ITextIconDisplayStyle.BOTH_TEXT_ICON);

		this.actionViewDoProccess.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionWorkflowList.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionRefresh.setVisible(false);

		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);

		Component[] components = this.menuWorkFlow.getPopupMenu().getComponents();

		for (int i = components.length - 1; i >= 0; --i) {
			this.menuWorkFlow.remove(i);
		}

		this.actionWorkFlowG.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);

		this.actionRemove.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.menuItemSwitchView.setVisible(false);

		this.menuItemRemove.setVisible(false);
		this.contBill.setEnableActive(false);

		Component[] tools = this.menuTool.getPopupMenu().getComponents();
		for (int i = tools.length - 1; i >= 0; --i) {
			this.menuTool.remove(i);
		}

		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTool.setVisible(false);
		this.menuWorkFlow.setVisible(false);

		FDCHelper.formatTableDate(tblMain, "createTime");
	}

	protected String getEditUIName() {
		return InviteProjectEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	/**
	 * 左边的树选择发生变化，重新构造查询条件执行
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void treeSelectChange() throws EASBizException, BOSException {
		DefaultKingdeeTreeNode OrgUnitNode = getSelectedTreeNode(treeOrg);
		DefaultKingdeeTreeNode inviteTypeNode = getSelectedTreeNode(treeInviteType);

		Object orgUnit = null;
		if (OrgUnitNode != null) {
			orgUnit = OrgUnitNode.getUserObject();
		}
		Object inviteType = null;
		if (inviteTypeNode != null) {
			inviteType = inviteTypeNode.getUserObject();
		}

		// 重新设置过滤条件
		mainQuery.setFilter(getTreeSelectFilter(orgUnit, inviteType));
		execQuery();

		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
		}

		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeOrg
				.getLastSelectedPathComponent();
		if (orgNode == null) {
			return;
		}
		OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
		String orgId = org.getUnit().getId().toString();

	}

	/**
	 * 获取指定树当前所选的节点
	 * 
	 * @param selectTree
	 * @return
	 */
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree
					.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}

	/**
	 * 将组织架构树所选生成的过滤条件和招标类型树所选生成的过滤条件重新融合车一个新的过滤条件
	 * 
	 * @param paraOrgUnit
	 * @param paraInviteType
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected FilterInfo getTreeSelectFilter(Object paraOrgUnit,
			Object paraInviteType) throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();

		Set stateSet = new HashSet();
		stateSet.add(InviteProjectStateEnum.SAVED_VALUE);
		stateSet.add(InviteProjectStateEnum.SUBMITED_VALUE);

		filter.getFilterItems().add(
				new FilterItemInfo("inviteProjectState", stateSet,
						CompareType.NOTINCLUDE));
		/**
		 * 组织架构树:财务组织
		 */
		if (paraOrgUnit instanceof OrgStructureInfo) {
			OrgStructureInfo orgUnit = (OrgStructureInfo) paraOrgUnit;
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.longNumber", orgUnit
							.getLongNumber()));
		}

		/**
		 * 招标类型
		 */
		FilterInfo typeFilter = new FilterInfo();
		if (paraInviteType != null
				&& (paraInviteType instanceof com.kingdee.eas.fdc.invite.InviteTypeInfo)) {
			InviteTypeInfo typeInfo = (InviteTypeInfo) paraInviteType;
			BOSUuid id = typeInfo.getId();

			Set idSet = getInviteTypeIdSet(id);
			typeFilter.getFilterItems().add(
					new FilterItemInfo("inviteProject.inviteType.id", idSet,
							CompareType.INCLUDE));

			if (filter != null && typeFilter != null) {
				filter.mergeFilter(typeFilter, "and");
			}
		}

		return filter;
	}

	protected EntityViewInfo getInitDefaultSolution() {

		return getDefaultEntityViewInfo();
	}

	protected boolean isAllowDefaultSolutionNull() {
		return true;

	}
	protected FilterInfo getMainFilter() throws Exception {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeOrg
				.getLastSelectedPathComponent();
		if (orgNode != null
				&& orgNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
			String orgId = org.getUnit().getId().toString();
			filter.getFilterItems()
					.add(new FilterItemInfo("orgUnit.id", orgId));
		}
		DefaultKingdeeTreeNode inviteNode = (DefaultKingdeeTreeNode) treeInviteType
				.getLastSelectedPathComponent();
		if (inviteNode != null
				&& inviteNode.getUserObject() instanceof InviteTypeInfo) {
			InviteTypeInfo inviteType = (InviteTypeInfo) inviteNode
					.getUserObject();
			Set idSet = this.genLeafInviteTypeIdSet(inviteType.getId()
					.toString());
			filter.getFilterItems().add(
					new FilterItemInfo("inviteType.id", idSet,
							CompareType.INCLUDE));
		}
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		return filter;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {

		FilterInfo filter = null;
		try {
			filter = this.getMainFilter();
			if (this.getDialog() != null) {
				FilterInfo commFilter = this.getDialog().getCommonFilter();
				if (filter != null && commFilter != null)
					filter.mergeFilter(commFilter, "and");
			} else {
				QuerySolutionInfo querySolution = this.getQuerySolutionInfo();
				if (querySolution != null
						&& querySolution.getEntityViewInfo() != null) {
					EntityViewInfo ev = Util.getInnerFilterInfo(querySolution);
					if (ev.getFilter() != null) {
						filter.mergeFilter(ev.getFilter(), "and");
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		viewInfo.setFilter(filter);

		return super.getQueryExecutor(queryPK, viewInfo);
	}	

	/**
	 * 通过所选的招标类型树中的一个的ID，获取该节点以及子节点的ID集合。 主要是通过长编码进行匹配查找
	 * 
	 * @param id
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private Set getInviteTypeIdSet(BOSUuid id) throws EASBizException,
			BOSException {
		Set idSet = new HashSet();
		IObjectPK pk = new ObjectUuidPK(id);
		InviteTypeInfo parentTypeInfo = InviteTypeFactory.getRemoteInstance()
				.getInviteTypeInfo(pk);

		String longNumber = parentTypeInfo.getLongNumber();
		EntityViewInfo view = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber + "!%",	CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber)); filter.setMaskString("#0 or #1");
		view.setFilter(filter);

		com.kingdee.eas.fdc.invite.InviteTypeCollection typeCols = InviteTypeFactory
				.getRemoteInstance().getInviteTypeCollection(view);

		Iterator iter = typeCols.iterator();
		while (iter.hasNext()) {
			InviteTypeInfo tmp = (InviteTypeInfo) iter.next();
			idSet.add(tmp.getId());
		}

		return idSet;
	}
	
	/**
	 * 在填入表格之前做预处理。
	 * @author owen_wen 2010-11-02
	 */
	protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		try {
			rowSet.beforeFirst();
			while (rowSet.next()) {
				Date prjDate = rowSet.getDate("prjDate");
				Date createTime = rowSet.getDate("createTime");
				Date auditTime = rowSet.getDate("auditTime");
				
				// 这三个字段在Query中使用toChar转换日期格式在DB2中不兼容，所以Query中不能硬拼接，改在这里转换格式	
				rowSet.updateString("prjDate", FDCDateHelper.formatDate2(prjDate));
				rowSet.updateString("createTime", FDCDateHelper.formatDate2(createTime));
				rowSet.updateString("auditTime", FDCDateHelper.formatDate2(auditTime));
			}
			rowSet.beforeFirst();
		} catch (SQLException e) {
			this.handleException(e);
		}
	}

	public KDTable getBillListTable() {
		// TODO Auto-generated method stub
		return null;
	}
}