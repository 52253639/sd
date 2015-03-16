/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomUsageFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomUsageListUI extends AbstractRoomUsageListUI {
	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	private static final Logger logger = CoreUIObject.getLogger(RoomUsageListUI.class);

	/**
	 * output class constructor
	 */
	public RoomUsageListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		if (node != null && node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			String sellProjectId = sellProject.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
			if (saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
				this.actionAddNew.setEnabled(true);
				this.actionEdit.setEnabled(true);
				this.actionRemove.setEnabled(true);
			}

		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

	protected String getEditUIName() {
		return RoomUsageEditUI.class.getName();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode sellProjectNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		uiContext.put("sellProject", sellProjectNode.getUserObject());
		super.prepareUIContext(uiContext, e);
	}

	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		}
		return UIFactoryName.MODEL;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomUsageFactory.getRemoteInstance();
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionView_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {

		this.checkSelected();
		ArrayList idValues = this.getSelectedIdValues();
		if(idValues != null && !idValues.isEmpty()){
			String id = idValues.get(0).toString();
			if (id != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("roomUsage.id", id));
				if (RoomDesFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("已被房间定义引用，不能删除！");
					SysUtil.abort();
				}
				if (RoomFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("已被房间引用，不能删除！");
					SysUtil.abort();
				}
				super.actionRemove_actionPerformed(e);
			}
		}

	}
}