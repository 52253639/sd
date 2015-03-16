/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.SHEProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SHEProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class SHEProjectListUI extends AbstractSHEProjectListUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6051185897476599698L;
	private static final Logger logger = CoreUIObject
			.getLogger(SHEProjectListUI.class);

	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	private String rootStr = "";

	/**
	 * output class constructor
	 */
	public SHEProjectListUI() throws Exception {
		super();
	}

	protected void initTree() throws Exception {
		// super.initTree();
		DefaultKingdeeTreeNode oldSelected = null;
		Object objTree = this.treeMain.getLastSelectedPathComponent();
		if (objTree != null) {
			oldSelected = (DefaultKingdeeTreeNode) objTree;
		}
		this.treeMain.setModel(FDCTreeHelper.getSHEProjectTree(actionOnLoad,
				FDCTreeHelper.SHEPROJECT));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		if (oldSelected != null)
			this.treeMain.setSelectionNode(oldSelected);
		else
			this.treeMain
					.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain
							.getModel().getRoot());

	}

	protected void refresh(ActionEvent e) throws Exception {
		// super.refresh(e);
		this.execQuery();
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if (this.rootStr.trim().length() > 0) {
			this.getUIContext().put("IDSTR", this.rootStr);
		}

		super.actionAddNew_actionPerformed(e);
		this.initTree();
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
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
		if (saleOrg.isIsBizUnit()) {
			int activeRowIndex = this.tblMain.getSelectManager()
					.getActiveRowIndex();
			IRow row = tblMain.getRow(activeRowIndex);
			if (row == null) {
				return;
			}
			boolean result = ((Boolean) row.getCell("isEnabled").getValue())
					.booleanValue();
			if (result) {
				this.actionCancelCancel.setEnabled(false);
				this.actionCancel.setEnabled(true);
			} else {
				this.actionCancelCancel.setEnabled(true);
				this.actionCancel.setEnabled(false);
			}
		} else {
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(false);
		}

	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SHEProjectInfo) {
			if (saleOrg.isIsBizUnit()) {
				FDCClientHelper.setActionEnableAndNotSetVisible(
						new ItemAction[] { actionAddNew, actionEdit,
								actionRemove, this.actionImport }, true);

				TreePath path = this.treeMain.getSelectionPath();
				if (path == null) {
					return;
				}
				DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path
						.getLastPathComponent();
				if (treenode.getUserObject() != null
						&& treenode.getUserObject() instanceof SHEProjectInfo) {
					SHEProjectInfo sheProjectInfo = (SHEProjectInfo) treenode
							.getUserObject();
					if (sheProjectInfo != null) {
						getUIContext().put("sheProject", sheProjectInfo);
					}
				}
			} else {
				FDCClientHelper.setActionEnableAndNotSetVisible(
						new ItemAction[] { actionAddNew, actionEdit,
								actionRemove, this.actionImport }, false);
				this.actionImport.setEnabled(false);
				this.actionCancelCancel.setEnabled(false);
				this.actionCancel.setEnabled(false);
			}
		} else {
			/*
			 * FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[]
			 * { actionAddNew, actionEdit, actionRemove, this.actionCancel,
			 * this.actionCancelCancel, this.actionImport }, false);
			 * this.actionImport.setEnabled(false);
			 * this.actionCancelCancel.setEnabled(false);
			 * this.actionCancel.setEnabled(false);
			 */
		}

		this.execQuery();

	}

	protected ITreeBase getTreeInterface() throws Exception {
		return SHEProjectFactory.getRemoteInstance();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SHEProjectFactory.getRemoteInstance();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected String getEditUIName() {
		return SHEProjectEditUI.class.getName();
	}

	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		SHEProjectInfo objectValue = new SHEProjectInfo();
		return objectValue;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SHEProjectInfo) {
				String allSpIdStr = FDCTreeHelper
						.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node,
								"SHEProject").keySet());
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

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		uiContext.put("IDSTR", this.rootStr);
		super.prepareUIContext(uiContext, e);
	}

	protected String[] getLocateNames() {

		String[] locateNames = new String[4];
		locateNames[0] = "number";
		locateNames[1] = "name";
		locateNames[2] = "project";
		locateNames[3] = "orgUnit";
		return locateNames;
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCancel_actionPerformed(e);
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();

		IRow row = tblMain.getRow(activeRowIndex);
		if (row == null) {
			FDCMsgBox.showWarning(this, "请选择记录!");
			this.abort();
		}
		try {
			BOSUuid id = BOSUuid.read(row.getCell("id").getValue().toString());
			SHEProjectFactory.getRemoteInstance().updateEnable(id, false);
			this.actionCancelCancel.setEnabled(true);
			this.actionCancel.setEnabled(false);
			FDCMsgBox.showWarning(this, "禁用成功!");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		this.refresh(e);
	}

	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionCancelCancel_actionPerformed(e);
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();

		IRow row = tblMain.getRow(activeRowIndex);
		if (row == null) {
			FDCMsgBox.showWarning(this, "请选择记录!");
			this.abort();
		}
		try {
			BOSUuid id = BOSUuid.read(row.getCell("id").getValue().toString());
			SHEProjectFactory.getRemoteInstance().updateEnable(id, true);
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(true);
			FDCMsgBox.showWarning(this, "启用成功!");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		this.refresh(e);
	}

	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory
				.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK(
				"com.kingdee.eas.fdc.sellhouse.app.SellProjectForSHEQuery")));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		dlg.setEntityViewInfo(view);
		// 设置多选
		dlg.setEnabledMultiSelection(false);
		dlg.show();
		Object[] object = (Object[]) dlg.getData();
		if (object != null && object.length > 0) {
			SellProjectInfo info = (SellProjectInfo) object[0];
			if (info != null && info.getId() != null) {

				FilterInfo newFilter1 = new FilterInfo();
				newFilter1.getFilterItems().add(
						new FilterItemInfo("number", info.getNumber()
								.toString(), CompareType.EQUALS));
				newFilter1.getFilterItems().add(
						new FilterItemInfo("name", info.getName().toString(),
								CompareType.EQUALS));
				newFilter1.setMaskString("#0 or #1");

				boolean result1 = SHEProjectFactory.getRemoteInstance().exists(
						newFilter1);
				if (result1) {
					FDCMsgBox.showWarning(this, "编码或者名称重复，引入失败，请检查!");
					this.abort();
				}

				FilterInfo newFilter = new FilterInfo();
				newFilter.getFilterItems().add(
						new FilterItemInfo("srcId", info.getId().toString(),
								CompareType.EQUALS));

				boolean result = SHEProjectFactory.getRemoteInstance().exists(
						newFilter);

				if (!result) {
					try {
						SHEProjectFactory.getRemoteInstance()
								.updateSellProjectToSHEProject(
										info.getId().toString());
						FDCMsgBox.showWarning(this, "引入成功!");
					} catch (Exception ex) {
						logger.error(ex.getMessage() + "引入失败！");
					}
				} else {
					FDCMsgBox.showWarning(this, "该项目已经存在，不能重复被引入!");
					this.abort();
				}

			}
		}
		this.initTree();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionImport.setEnabled(true);
		this.btnImport.setIcon(EASResource.getIcon("imgTbtn_input"));
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);

		if (saleOrg.isIsBizUnit()) {
			int activeRowIndex = this.tblMain.getSelectManager()
					.getActiveRowIndex();

			if (activeRowIndex == -1) {
				IRow row = tblMain.getRow(0);
				if (row == null) {
					return;
				}
				if (row.getCell("isEnabled").getValue() != null) {
					boolean result = ((Boolean) row.getCell("isEnabled")
							.getValue()).booleanValue();
					if (result) {
						this.actionCancelCancel.setEnabled(false);
						this.actionCancel.setEnabled(true);
					} else {
						this.actionCancelCancel.setEnabled(true);
						this.actionCancel.setEnabled(false);
					}
				}

			}
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
		} else {
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionImport.setEnabled(false);
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}

	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {

		if (this.rootStr.trim().length() > 0) {
			this.getUIContext().put("IDSTR", this.rootStr);
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		if (this.rootStr.trim().length() > 0) {
			this.getUIContext().put("IDSTR", this.rootStr);
		}
		super.actionView_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = "";
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		IRow row = tblMain.getRow(activeRowIndex);
		if (row == null) {
			return;
		}

		id = row.getCell("id").getValue().toString();

		if (!"".equals(id)) {
			SHEProjectInfo info = SHEProjectFactory.getRemoteInstance()
					.getSHEProjectInfo("select id,isUse where id='" + id + "'");
			if (info.isIsUse()) {
				FDCMsgBox.showWarning(this, "项目已经被引用，不能删除!");
				this.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
		initTree();
	}
}