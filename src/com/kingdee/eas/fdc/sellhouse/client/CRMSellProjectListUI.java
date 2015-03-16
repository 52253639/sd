/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public class CRMSellProjectListUI extends AbstractCRMSellProjectListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CRMSellProjectListUI.class);
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	// private Map map = FDCSysContext.getInstance().getOrgMap();
	/**
	 * output class constructor
	 */
	public CRMSellProjectListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		// if
		// (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId
		// ().toString())) {
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		} else {
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
		}
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
	}

	protected void initTree() throws Exception {
		super.initTree();
		this.treeMain.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain
				.getModel().getRoot());
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	protected String getRootName() {
		return "集团";
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.execQuery();
		initTree();
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProjectInfo = (SellProjectInfo) node
					.getUserObject();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", sellProjectInfo
							.getId().toString()));
			BuildingCollection buildingColl = BuildingFactory
					.getRemoteInstance().getBuildingCollection(view);

			if (buildingColl != null && buildingColl.size() > 0) {
				FDCMsgBox.showWarning(this, "该项目下已挂接楼栋，不能再增加下级!");
				this.abort();
			}

		}
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * 监听每一笔记录是否可用,从而设置按钮是否可用
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			// if
			// (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit(
			// ).getId().toString())) {
			if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
				actionAddNew.setEnabled(false);
				actionEdit.setEnabled(false);
				actionRemove.setEnabled(false);
			}
		}
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		// super.treeMain_valueChanged(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			// if
			// (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit(
			// ).getId().toString())) {
			if (saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
				FDCClientHelper.setActionEnableAndNotSetVisible(
						new ItemAction[] { actionAddNew, actionEdit,
								actionRemove }, true);
				TreePath path = this.treeMain.getSelectionPath();
				if (path == null) {
					return;
				}
				DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path
						.getLastPathComponent();
				if (treenode.getUserObject() != null
						&& treenode.getUserObject() instanceof SellProjectInfo) {
					SellProjectInfo sellProjectInfo = (SellProjectInfo) treenode
							.getUserObject();
					if (sellProjectInfo != null) {
						getUIContext().put("sellProject", sellProjectInfo);
					}
				}
			} else {
				FDCClientHelper.setActionEnableAndNotSetVisible(
						new ItemAction[] { actionAddNew, actionEdit,
								actionRemove }, false);
			}
		}
		this.execQuery();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				String allSpIdStr = FDCTreeHelper
						.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node,
								"SellProject").keySet());
				if (allSpIdStr.trim().length() == 0) {
					allSpIdStr = "'nullnull'";
				}
				filter.getFilterItems()
						.add(
								new FilterItemInfo("id", allSpIdStr,
										CompareType.INNER));
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				String orgUnitIdStr = FDCTreeHelper
						.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node,
								"OrgStructure").keySet());
				if (orgUnitIdStr.trim().length() == 0) {
					orgUnitIdStr = "'nullnull'";
				}
				filter.getFilterItems().add(
						new FilterItemInfo("orgUnit.id", orgUnitIdStr,
								CompareType.INNER));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("id", null, CompareType.NOTEQUALS));
			}
		}
		// 合并查询条件
		viewInfo = (EntityViewInfo) mainQuery.clone();
		try {
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.getQueryExecutor(queryPK, viewInfo);
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
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String id = "";
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		IRow row = tblMain.getRow(activeRowIndex);
		if (row == null) {
			return;
		}
		id = row.getCell("id").getValue().toString();
		if (id != null && !id.equals("")) {
			SellProjectInfo info = SellProjectFactory.getRemoteInstance()
					.getSellProjectInfo(
							"select id,isForSHE,isLeaf where id='" + id + "'");
			if (info.isIsForSHE()) {
				FDCMsgBox.showWarning(this, "本记录已被项目建立引用，不能删除!");
				this.abort();
			} else if (!info.isIsLeaf()) {
				FDCMsgBox.showWarning(this, "本记录具有子节点，不能删除!");
				this.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
		initTree();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		String orgUnitIdStr = "";
		String orgUnitIdStrRoot = "";
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				orgUnitIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper
						.getAllObjectIdMap(node, "SellProject").keySet());
				orgUnitIdStrRoot = FDCTreeHelper
						.getStringFromSet(FDCTreeHelper
								.getAllObjectIdMapForRoot(node, "SellProject")
								.keySet());
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				orgUnitIdStr = "";
				orgUnitIdStrRoot = "";

			}
		}
		if (!"".equals(orgUnitIdStrRoot) && orgUnitIdStrRoot.length() > 0) {
			orgUnitIdStr = orgUnitIdStr + "," + orgUnitIdStrRoot;
		}
		if (!"".equals(orgUnitIdStr) && orgUnitIdStr.length() > 0) {
			uiContext.put("IDSTR", orgUnitIdStr);
		} else {
			uiContext.put("IDSTR", null);
		}

	}

	/**
	 * 定位
	 */
	protected String[] getLocateNames() {
		return new String[] { "number", "name", "simpleName", "project.name" };
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected String getEditUIName() {
		return CRMSellProjectEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SellProjectFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		return new SellProjectInfo();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return SellProjectFactory.getRemoteInstance();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected FilterInfo getDefaultFilterForTree() {

		return super.getDefaultFilterForTree();
	}

	protected boolean isIgnoreTreeCUFilter() {
		return true;
	}
}