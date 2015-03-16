/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ProofOfPaymentFactory;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ProofOfPaymentListUI extends AbstractProofOfPaymentListUI {

	private static final Logger logger = CoreUIObject.getLogger(ProofOfPaymentListUI.class);
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	private String allBuildingIds = null; // 所包含楼栋id
	private SellProjectInfo sellProject = null;

	/**
	 * output class constructor
	 */
	public ProofOfPaymentListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProofOfPaymentFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return ProofOfPaymentEditUI.class.getName();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		if (this.treeMain.getRowCount() > 0) {
			this.treeMain.setSelectionRow(0);
		}

	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof Integer) { // 已作废
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			sellProject = building.getSellProject();
		} else if (node.getUserObject() instanceof BuildingUnitInfo) {
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			sellProject = building.getSellProject();
		} else if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
				sellProject = building.getSellProject();
			}
		}
		else if(node.getUserObject() instanceof SellProjectInfo){
			sellProject = (SellProjectInfo) node.getUserObject();
		}
		if (node != null) {
			this.getAllBuildingIds(node);
			this.execQuery();
		} else {
			return;
		}
	}

	/**
	 * 查询所有的楼栋id
	 * 
	 * @param treeNode
	 */
	private void getAllBuildingIds(TreeNode treeNode) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		allBuildingIds = null;
		if (thisNode.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) thisNode.getUserObject();
			if (building != null) {
				allBuildingIds = allBuildingIds + "," + building.getId().toString();
			}
		}
		if (!treeNode.isLeaf()) {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllBuildingIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			FilterInfo roomFilter = new FilterInfo();
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node != null) {
				if (node.getUserObject() instanceof Integer) { // 已作废
					Integer unit = (Integer) node.getUserObject();
					BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
					String buildingId = building.getId().toString();
					roomFilter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
					roomFilter.getFilterItems().add(new FilterItemInfo("unit", unit));
				} else if (node.getUserObject() instanceof BuildingUnitInfo) {
					BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
					BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
					String buildingId = building.getId().toString();
					roomFilter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
					roomFilter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
				} else if (node.getUserObject() instanceof BuildingInfo) {
					BuildingInfo building = (BuildingInfo) node.getUserObject();
					String buildingId = building.getId().toString();
					roomFilter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
				} else if (node != null && node.getUserObject() instanceof SubareaInfo) {
					SubareaInfo subInfo = (SubareaInfo) node.getUserObject();
					roomFilter.getFilterItems().add(new FilterItemInfo("building.subarea.id", subInfo.getId().toString()));
					if (subInfo.getSellProject() != null) {
						SellProjectInfo selectPro = (SellProjectInfo) subInfo.getSellProject();
						if (selectPro != null) {
							roomFilter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", selectPro.getId().toString()));
						}
					}

				}

				else {
					roomFilter.getFilterItems().add(new FilterItemInfo("id", null));
				}
			}

			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(roomFilter);
			view.getSelector().add("id");
			RoomCollection rooms = RoomFactory.getRemoteInstance().getRoomCollection(view);
			Set idSet = new HashSet();
			idSet.add("null");
			for (int i = 0; i < rooms.size(); i++) {
				idSet.add(rooms.get(i).getId().toString());
			}

			FilterInfo filter = new FilterInfo();
			// 增加项目节点的情况
			if (node != null && node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo selectPro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("purchaseBill.sellProject.id", selectPro.getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("purchaseBill.room.id", idSet, CompareType.INCLUDE));
			}

			viewInfo = (EntityViewInfo) this.mainQuery.clone();

			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		this.actionAttachment.setVisible(false);
		this.btnAddNew.setEnabled(true);
		this.btnEdit.setEnabled(true);
		this.menuItemAddNew.setVisible(true);
		this.menuItemAddNew.setEnabled(true);

		this.btnPrintPreview.setVisible(true);
		this.btnPrint.setVisible(true);
		this.btnPrintPreview.setEnabled(true);
		this.btnPrint.setEnabled(true);

		this.menuItemPrintPreview.setVisible(true);
		this.menuItemPrint.setVisible(true);
		this.menuItemPrintPreview.setEnabled(true);
		this.menuItemPrint.setEnabled(true);

		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);

		this.menuBiz.setEnabled(true);
		this.menuBiz.setVisible(true);
		this.actionPaymentPrint.setEnabled(true);
		this.actionPaymentPrintPreview.setEnabled(true);
		this.menuPaymentPrintPreview.setVisible(true);
		this.menuPaymentPrint.setVisible(true);
		this.menuPaymentPrintPreview.setEnabled(true);
		this.menuPaymentPrint.setEnabled(true);

		if (this.treeMain.getRowCount() > 0) {
			this.treeMain.setSelectionRow(0);
		}
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("sellProject", sellProject);
		uiContext.put("sourceName", "proofOfPayment");
		super.prepareUIContext(uiContext, e);
	}

	public void actionPaymentPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		String payMentId = this.getSelectedKeyValue();
		PaymentPrintDataProvider data = new PaymentPrintDataProvider(payMentId, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ProofOfPaymentQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/ProofOfPayment", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPaymentPrintPreview_actionPerformed(e);
	}

	public void actionPaymentPrint_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String payMentId = this.getSelectedKeyValue();
		PaymentPrintDataProvider data = new PaymentPrintDataProvider(payMentId, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ProofOfPaymentQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/ProofOfPayment", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPaymentPrint_actionPerformed(e);
	}
}