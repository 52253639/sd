/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustFactory;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PriceAdjustListUI extends AbstractPriceAdjustListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PriceAdjustListUI.class);

	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	/**
	 * output class constructor
	 */
	public PriceAdjustListUI() throws Exception {
		super();
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			if (saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
			}
		} else {
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		try {
			FilterInfo sellProFilter = new FilterInfo();
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();				
				sellProFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
			} else {
				sellProFilter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(sellProFilter, "and");
			} else {
				viewInfo.setFilter(sellProFilter);
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
	}
	
	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state")
				.getValue();
		if (state.getValue().equals(FDCBillStateEnum.SAVED_VALUE)) {
			MsgBox.showInfo("调价单没有提交!");
			return;
		}
		if (state.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
			MsgBox.showInfo("调价单已经审核!");
			return;
		}
		if (state.getValue().equals(FDCBillStateEnum.INVALID_VALUE)) {
			MsgBox.showInfo("调价单已经作废!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		PriceAdjustFactory.getRemoteInstance().audit(BOSUuid.read(id));
		MsgBox.showInfo("审批成功!");
		this.refresh(null);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName == null) {
			MsgBox.showInfo("调价单没有审批!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		PriceAdjustFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
		this.refresh(null);
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
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PriceAdjustFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return PriceAdjustEditUI.class.getName();
	}

	public void onLoad() throws Exception {
		this.actionUnAudit.setVisible(false);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
		String formatString = "yyyy-MM-dd";
		tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat(
				formatString);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionExecute.setEnabled(false);
		}
		this.actionCreateTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.menuEdit.remove(menuItemCopyTo);
		this.actionAttachment.setVisible(false);
		this.actionAuditResult.setVisible(false);
	}

	protected FilterInfo getDefaultFilterForQuery() {
		Set idSet = SHEHelper.getLeafCompanyIdSet(this.actionOnLoad);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit.id", idSet, CompareType.INCLUDE));
		return filter;
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

	public void actionExecute_actionPerformed(ActionEvent e) throws Exception {
		super.actionExecute_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		Boolean isExecuted = (Boolean) row.getCell("isExecuted").getValue();
		if (isExecuted.booleanValue()) {
			MsgBox.showInfo("调价单已经执行!");
			return;
		}
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName == null) {
			MsgBox.showInfo("调价单没有审批!");
			return;
		}
		if (MsgBox.showConfirm2New(this, "是否执行?") == MsgBox.YES) {
			String id = (String) row.getCell(this.getKeyFieldName()).getValue();
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("entrys.*");
			sels.add("entrys.room.*");
			PriceAdjustInfo bill = (PriceAdjustInfo) PriceAdjustFactory
					.getRemoteInstance().getPriceAdjustInfo(
							new ObjectUuidPK(BOSUuid.read(id)), sels);

			PriceAdjustEntryCollection entrys = bill.getEntrys();
			for (int i = 0; i < entrys.size(); i++) {
				PriceAdjustEntryInfo entry = entrys.get(i);
				RoomInfo room = entry.getRoom();
				// 房间已售
				if (room.getSellState().equals(RoomSellStateEnum.Purchase)
						|| room.getSellState().equals(RoomSellStateEnum.Sign)) {
					MsgBox.showInfo("调价单中房间" + room.getNumber()
							+ " 调价审批中被销售,将自动跳过!");
				}
				boolean isEdit = false;
				// 套内面积改变
				BigDecimal entryRoomArea = entry.getOldRoomArea();
				if (entryRoomArea == null) {
					entryRoomArea = FDCHelper.ZERO;
				}
				BigDecimal roomArea = room.getRoomArea();
				if (roomArea == null) {
					roomArea = FDCHelper.ZERO;
				}
				if (roomArea.compareTo(entryRoomArea) != 0) {
					isEdit = true;
				}
				// 建筑面积改变
				BigDecimal entryBuildingArea = entry.getOldBuildingArea();
				if (entryBuildingArea == null) {
					entryBuildingArea = FDCHelper.ZERO;
				}
				BigDecimal buildingArea = room.getBuildingArea();
				if (buildingArea == null) {
					buildingArea = FDCHelper.ZERO;
				}
				if (buildingArea.compareTo(entryBuildingArea) != 0) {
					isEdit = true;
				}
				if (isEdit) {
					MsgBox.showInfo("调价单中房间" + room.getNumber()
							+ " 定价后面积变化,将自动跳过!");
				}
			}
			if (PriceAdjustFactory.getRemoteInstance().execute(id)) {
				MsgBox.showInfo("执行成功!");
				this.refresh(null);
			} else {
				MsgBox.showInfo("调价单没有审批!");
			}
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName != null) {
			MsgBox.showInfo("调价单已经审批,不能删除!");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName != null) {
			MsgBox.showInfo("调价单已经审批,不能修改!");
			return;
		}
		super.actionEdit_actionPerformed(arg0);
	}
}