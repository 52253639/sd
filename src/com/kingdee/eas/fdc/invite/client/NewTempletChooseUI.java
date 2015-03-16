/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.NewListTempletFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;

/**
 * output class name
 */
public class NewTempletChooseUI extends AbstractNewTempletChooseUI
{
	private static final Logger logger = CoreUIObject.getLogger(NewTempletChooseUI.class);
	CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext()
	.getCurrentCostUnit();
	/**
	 * output class constructor
	 */
	public NewTempletChooseUI() throws Exception
	{
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
	{
		if (e.getClickCount() == 2) {
			this.btnYes_actionPerformed(null);
		}
	}

	/**
	 * output btnYes_actionPerformed method
	 */
	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		String templetId = this.getSelectedKeyValue();
		if (templetId != null) {
			this.getUIContext().put("templetId", templetId);
			this.destroyWindow();
		}
	}

	/**
	 * output btnNo_actionPerformed method
	 */
	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		super.btnNo_actionPerformed(e);
		this.getUIContext().put("templetId", null);
		this.destroyWindow();
	}

	public static String showChooseUI(CoreUI ui, InviteTypeInfo inviteType)
	throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("inviteType", inviteType);
//		创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(NewTempletChooseUI.class.getName(), uiContext, null,
		"View");
		uiWindow.show();
		String templetId = (String) uiWindow.getUIObject().getUIContext().get(
		"templetId");
		return templetId;
	}

	protected String getEditUIName() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return NewListTempletFactory.getRemoteInstance();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	public void onLoad() throws Exception {
		InviteTypeInfo inviteType = (InviteTypeInfo) this.getUIContext().get(
		"inviteType");
		FilterInfo filter = new FilterInfo();
		if (inviteType != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("inviteType.id", inviteType.getId()
							.toString()));
		}
		Set idSet = getAllParentIds();
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit.id", idSet, CompareType.INCLUDE));
		this.mainQuery = new EntityViewInfo();
		this.mainQuery.setFilter(filter);
		SorterItemInfo sorter = new SorterItemInfo("orgUnit.id");
		sorter.setSortType(SortType.ASCEND);
		this.mainQuery.getSorter().add(sorter);	
		SorterItemInfo sorter_1 = new SorterItemInfo("oriorg.id");
		sorter.setSortType(SortType.ASCEND);
		this.mainQuery.getSorter().add(sorter_1);
		this.mainQuery.setIngorePreOrders(true);
		
		logger.debug("entityView:" + mainQuery);

		super.onLoad();
		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionView.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionView.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionQuery.setEnabled(false);
		logger.debug("rowCount:" + tblMain.getRowCount());
	}

	public Set getAllParentIds() throws Exception {
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,
				"", null, null, null);
		DefaultKingdeeTreeNode orgRoot = (DefaultKingdeeTreeNode) orgTreeModel
		.getRoot();
		DefaultKingdeeTreeNode node = this.findNode(orgRoot, this.currentOrg
				.getId().toString());
		Set idSet = new HashSet();
		idSet.add(currentOrg.getId().toString());
		while (node.getParent() != null) {
			DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node
			.getParent();
			OrgStructureInfo oui = (OrgStructureInfo) parent.getUserObject();
			FullOrgUnitInfo info = oui.getUnit();
			idSet.add(info.getId().toString());
			node=parent;
		}
		return idSet;
	}
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
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
}