/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import javax.swing.tree.TreeNode;
import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SellProjectListUI extends AbstractSellProjectListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SellProjectListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	/**
	 * output class constructor
	 */
	public SellProjectListUI() throws Exception {
		super();
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
		// super.tblMain_tableSelectChanged(e);
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		// super.treeMain_valueChanged(e);
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (orgNode == null) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		if (orgNode != null
				&& orgNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
			String orgId = org.getUnit().getId().toString();
			filter.getFilterItems()
					.add(new FilterItemInfo("orgUnit.id", orgId));
			filter.getFilterItems()
			.add(new FilterItemInfo("orgUnitShareList.orgUnit.id", orgId));
			filter.setMaskString("#0 or #1");
		} else {
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", null));
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.menuTool.removeAll();
		this.menuHelp.removeAll();
		this.menuBar.add(menuTool);
		this.menuTool.add(menuItemSendMessage);
		this.menuTool.add(menuItemCalculator);
		this.menuBar.add(menuHelp);
		this.menuHelp.add(menuItemHelp);
		menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemAbout);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        this.menuFile.remove(menuItemExitCurrent);
        this.menuFile.add(menuItemExitCurrent);
		this.actionQuery.setVisible(false);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSaleOrgTree(this.actionOnLoad));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	protected String getEditUIName() {
		return SellProjectEditUI.class.getName();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		uiContext.put("org", ((OrgStructureInfo) orgNode.getUserObject())
				.getUnit());
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SellProjectFactory.getRemoteInstance();
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", id));
			if (BuildingFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经建立楼栋信息,不能删除!");
				return;
			}
		}
		checkSelected();
		if (confirmRemove()) {
//			prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
//		String id = this.getSelectedKeyValue();
//		if (id != null) {
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("sellProject.id", id));
//			if (BuildingFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("已经建立楼栋信息,不能修改!");
//				return;
//			}
//		}
		super.actionEdit_actionPerformed(e);
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
}