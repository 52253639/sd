/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ShareProjectListUI extends AbstractShareProjectListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ShareProjectListUI.class);

	protected Boolean isMultiSelect = Boolean.FALSE;
	protected MoneySysTypeEnum moneySysTypeEnum = null;

	/**
	 * output class constructor
	 */
	public ShareProjectListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionView.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRefresh.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
	}

	protected void initTree() throws Exception {
		DefaultTreeModel root = null;
		root = FDCTreeHelper
				.getSaleOrgTreeForAll(actionOnLoad, OrgConstants.SALE_VIEW_ID,
						OrgConstants.DEF_CU_ID, false, false);
		if (root != null) {
			treeMain.setModel(root);
			this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain
					.getModel().getRoot());
		} else {
			treeMain.setModel(null);
			return;
		}

		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		treeMain.setSelectionRow(0); // 默认选择根节点
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			btnYes_actionPerformed(null);
		}
	}
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
		this.actionView.setEnabled(true);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo orgStructure = (OrgStructureInfo) node
						.getUserObject();
				if (orgStructure.isIsLeaf()) {
					String allSpIdStr = FDCTreeHelper
							.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(
									node, "OrgStructure").keySet());
					if (allSpIdStr.trim().length() == 0) {
						allSpIdStr = "'nullnull'";
					}
					filter.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", allSpIdStr,
									CompareType.INNER));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("id", null, CompareType.EQUALS));
				}
			}
		}
		filter.getFilterItems().add(
				new FilterItemInfo("level", "1", CompareType.EQUALS));
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
	}

	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		if (isMultiSelect.booleanValue()) {
			SellProjectCollection projects = new SellProjectCollection();
			for (int i = 0; i < this.tblMain.getSelectManager().size(); i++) {
				IRow row = tblMain.getRow(i);
				if (row == null) {
					MsgBox.showInfo("请正确选择项目！");
					return;
				} else {
					String id = row.getCell("id").getValue().toString();

					SellProjectInfo sellProject = querySellProjectInfo(id);
					projects.add(sellProject);
				}
			}
			if (projects.size() == 0) {
				MsgBox.showInfo("请正确选择项目！");
				return;
			} else {
				this.getUIContext().put("sellProject", projects);
				this.destroyWindow();
			}
		} else {
			String id = "";
			int activeRowIndex = this.tblMain.getSelectManager()
					.getActiveRowIndex();
			IRow row = tblMain.getRow(activeRowIndex);
			if (row == null) {
				MsgBox.showInfo("请正确选择项目！");
				return;
			} else {
				id = row.getCell("id").getValue().toString();
				SellProjectInfo sellProject = querySellProjectInfo(id);
				this.getUIContext().put("sellProject", sellProject);
				this.destroyWindow();
			}
		}
	}

	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		this.destroyWindow();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return SellProjectFactory.getRemoteInstance();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	public static SellProjectInfo querySellProjectInfo(String id)
			throws EASBizException, BOSException, UuidException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("id");
		sels.add("name");
		sels.add("number");
		sels.add("simpleName");
		sels.add("description");
		SellProjectInfo sellProjectInfo = SellProjectFactory
				.getRemoteInstance().getSellProjectInfo(
						new ObjectUuidPK(BOSUuid.read(id)), sels);
		return sellProjectInfo;
	}

}