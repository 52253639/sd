/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class SHEOrgUnitListUI extends AbstractSHEOrgUnitListUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6642999741330891438L;
	private static final Logger logger = CoreUIObject
			.getLogger(SHEOrgUnitListUI.class);

	protected Boolean isMultiSelect = Boolean.FALSE;
	private static final String DEF_CU = "00000000-0000-0000-0000-00000000000632B85C74";
	public static final String FULLORGUNIT="fullOrgUnit";

	

	/**
	 * output class constructor
	 */
	public SHEOrgUnitListUI() throws Exception {
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
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	protected void initTree() throws Exception {
		TreeModel root = null;

		root = FDCTreeHelper
				.getSaleOrgTreeForAll(actionOnLoad, OrgConstants.SALE_VIEW_ID,
						OrgConstants.DEF_CU_ID, false, false);
		if (root != null) {
			treeMain.setModel(root);
			//treeMain.expandOnLevel(1);
			this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
					.getRoot());
		} else {
			treeMain.setModel(null);
			return;
		}
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionView.setVisible(false);
		this.actionRemove.setVisible(false);

		if (this.getUIContext().get("isMultiSelect") != null) {
			this.isMultiSelect = (Boolean) this.getUIContext().get(
					"isMultiSelect");
		}

		if (isMultiSelect.booleanValue()) {
			this.tblMain.getSelectManager().setSelectMode(
					KDTSelectManager.MULTIPLE_CELL_SELECT);
		} else {
			this.tblMain.getSelectManager().setSelectMode(
					KDTSelectManager.ROW_SELECT);
		}

		this.setUITitle("销售组织");
		this.actionEdit.setVisible(false);
		this.actionRefresh.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);

		this.tblMain.getSelectManager().select(-1,-1);
		KDTableUtil.setSelectedRow(this.tblMain, -1);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		this.execQuery();
	}

	protected void btnConfim_actionPerformed(ActionEvent e) throws Exception {
		// super.btnConfim_actionPerformed(e);
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows == null || selectRows.length <= 0) {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			SysUtil.abort();
		}
		Object[] object = new Object[selectRows.length];
		for (int i = 0; i < selectRows.length; i++) {
			int select = selectRows[i];
			IRow row = this.tblMain.getRow(select);
			if (row == null) {
				continue;
			}
			FullOrgUnitInfo org = new FullOrgUnitInfo();
			if(row.getCell("unit.id").getValue()!=null){
				org.setId(BOSUuid.read(row.getCell("unit.id").getValue().toString()));
			}
			if(row.getCell("unit.name").getValue()!=null){
				org.setName(row.getCell("unit.name").getValue().toString());
			}
			if(row.getCell("unit.number").getValue()!=null){
				org.setNumber(row.getCell("unit.number").getValue().toString());
			}
			if(row.getCell("unit.simpleName").getValue()!=null){
				org.setSimpleName(row.getCell("unit.simpleName").getValue().toString());
			}
			if(row.getCell("unit.description").getValue()!=null){
				org.setDescription(row.getCell("unit.description").getValue().toString());
			}
			object[i] = org;
		}
		if(object!=null && object.length>0){
			Map uiContext = getUIContext();
			uiContext.put(FULLORGUNIT, object);
		}
		this.uiWindow.close();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo selectInfo = (OrgStructureInfo) node
						.getUserObject();

				if (DEF_CU.equals(selectInfo.getId().toString())) {
					filter.getFilterItems().add(
							new FilterItemInfo("tree.id",
									OrgConstants.SALE_VIEW_ID,
									CompareType.EQUALS));
				} else {

					String allSpIdStr = FDCTreeHelper
							.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(
									node, "OrgStructure").keySet());
					if (allSpIdStr.trim().length() == 0) {
						allSpIdStr = "'nullnull'";
					}

					filter.getFilterItems().add(
							new FilterItemInfo("unit.id", allSpIdStr,
									CompareType.INNER));
					filter.getFilterItems().add(
							new FilterItemInfo("tree.id",
									OrgConstants.SALE_VIEW_ID,
									CompareType.EQUALS));
				}
			}
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("tree.id", OrgConstants.SALE_VIEW_ID,
							CompareType.EQUALS));
		}

		filter.getFilterItems().add(
				new FilterItemInfo("unit.partSale.isBizUnit", Boolean.TRUE,
						CompareType.EQUALS));

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
	
	public void btnQuit_actionPerformed(java.awt.event.ActionEvent e){
		this.uiWindow.close();
	}
}