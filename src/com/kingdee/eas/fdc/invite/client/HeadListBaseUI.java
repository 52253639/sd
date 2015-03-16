/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.invite.HeadTypeCollection;
import com.kingdee.eas.fdc.invite.HeadTypeFactory;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class HeadListBaseUI extends AbstractHeadListBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(HeadListBaseUI.class);
    public OrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();
    /**
     * output class constructor
     */
    public HeadListBaseUI() throws Exception
    {
        super();
    }

    protected void fillTable() throws Exception {
		this.mainQuery.setFilter(this.getMainFilter());
		this.tblMain.removeRows();
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
		super.tblMain_doRequestRowSet(e);
	}

	protected void treeHeadType_valueChanged(TreeSelectionEvent e)
			throws Exception {
		super.treeHeadType_valueChanged(e);
		fillTable();
	}

	protected void treeOrg_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeOrg
				.getLastSelectedPathComponent();
		if (orgNode != null) {
			OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
			String orgId = org.getUnit().getId().toString();
			if (currentOrg.getId().toString().equals(orgId)) {
				setEditActionState(true);
			} else {
				setEditActionState(false);
			}
		} else {
			setEditActionState(false);
		}
		fillTable();
	}

	protected void setEditActionState(boolean fState) {
		this.actionAddNew.setEnabled(fState);
		this.actionEdit.setEnabled(fState);
		this.actionRemove.setEnabled(fState);
		this.actionAttachment.setEnabled(fState);
		this.actionImportData.setEnabled(fState);
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
		DefaultKingdeeTreeNode headNode = (DefaultKingdeeTreeNode) treeHeadType
				.getLastSelectedPathComponent();
		if (headNode != null
				&& headNode.getUserObject() instanceof HeadTypeInfo) {
			HeadTypeInfo headType = (HeadTypeInfo) headNode
					.getUserObject();
			// filter.getFilterItems().add(
			// new FilterItemInfo("inviteType.id", inviteType.getId()
			// .toString()));
			Set idSet = this.genLeafHeadTypeIdSet(headType.getId()
					.toString());
			filter.getFilterItems().add(
					new FilterItemInfo("headType.id", idSet,
							CompareType.INCLUDE));
		}
		return filter;
	}

	public Set genLeafHeadTypeIdSet(String id) throws Exception {

		Set idSet = new HashSet();
		idSet.add(id);
		HeadTypeInfo typeInfo = HeadTypeFactory.getRemoteInstance()
				.getHeadTypeInfo(new ObjectUuidPK(BOSUuid.read(id)));
		String longNumber = typeInfo.getLongNumber();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",
						CompareType.LIKE));
		view.setFilter(filter);

		HeadTypeCollection headTypeCollection = HeadTypeFactory
				.getRemoteInstance().getHeadTypeCollection(view);

		for (Iterator iter = headTypeCollection.iterator(); iter.hasNext();) {
			HeadTypeInfo element = (HeadTypeInfo) iter.next();
			idSet.add(element.getId().toString());
		}
		return idSet;
	}

	public void onLoad() throws Exception {
		if (currentOrg == null) {
			MsgBox.showWarning("组织为空不能进入!");
			abort();
		}
		if (!currentOrg.isIsCompanyOrgUnit()) {
			MsgBox.showWarning(this, "非财务组织不能进入!");
			abort();
		}

		super.onLoad();
		this.tblMain.setColumnMoveable(true);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		initTable();
		buildOrgTree();
		buildHeadTypeTree();
		DefaultKingdeeTreeNode orgRoot = (DefaultKingdeeTreeNode) ((TreeModel) this.treeOrg
				.getModel()).getRoot();
		DefaultKingdeeTreeNode node = this.findNode(orgRoot, this.currentOrg
				.getId().toString());
		this.removeAllBrotherNode(node);
		this.treeOrg.setSelectionNode(node);
		this.treeOrg.expandAllNodes(true, orgRoot);
		this.treeHeadType.setSelectionRow(0);
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		this.tblMain.setAutoscrolls(true);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionQuery.setVisible(false);
		treeOrg_valueChanged(null);
	}

	public void removeAllBrotherNode(DefaultKingdeeTreeNode node) {
		if (node == null) {
			return;
		}

		DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node
				.getParent();
		if (parent == null) {
			return;
		}
		this.treeOrg.removeAllChildrenFromParent(parent);
		this.treeOrg.addNodeInto(node, parent);
		this.removeAllBrotherNode(parent);
	}

	protected void initTable() {
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
	}

	protected void setActionState() {

	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeOrg
				.getLastSelectedPathComponent();
		uiContext.put("org", ((OrgStructureInfo) orgNode.getUserObject())
				.getUnit());
		DefaultKingdeeTreeNode typeNode = (DefaultKingdeeTreeNode) treeHeadType
				.getLastSelectedPathComponent();
		uiContext.put("type", typeNode.getUserObject());
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
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	private void buildHeadTypeTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		TreeModel model = FDCClientHelper.createDataTree(HeadTypeFactory
				.getRemoteInstance(), filter, "所有类型");
		this.treeHeadType.setModel(model);
	}

	private void buildOrgTree() throws Exception {
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,
				"", SysContext.getSysContext().getCurrentFIUnit().getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
		this.treeOrg.setModel(orgTreeModel);
	}

	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node,
			String id) {
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if (projectInfo.getId().toString().equals(id)) {
				return node;
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			FullOrgUnitInfo info = oui.getUnit();
			if (info.getId().toString().equals(id)) {
				return node;
			}
		}

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode findNode = findNode(
					(DefaultKingdeeTreeNode) node.getChildAt(i), id);
			if (findNode != null) {
				return findNode;
			}

		}
		return null;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIName() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}
}