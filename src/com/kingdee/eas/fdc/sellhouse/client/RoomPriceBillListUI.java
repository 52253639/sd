/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
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
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomPriceBillListUI extends AbstractRoomPriceBillListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomPriceBillListUI.class);

	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	
	/**
	 * output class constructor
	 */
	public RoomPriceBillListUI() throws Exception {
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
	
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);
		String formatString = "yyyy-MM-dd";
		this.tblMain.getColumn("createTime").getStyleAttributes()
				.setNumberFormat(formatString);
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		this.actionUnAudit.setVisible(false);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionExecute.setEnabled(false);
		}
		this.menuEdit.remove(menuItemCopyTo);
		this.actionCreateTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionAuditResult.setVisible(false);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("building.*");
		sels.add("*");
		return sels;
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected String getEditUIName() {
		return RoomPriceBillEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomPriceBillFactory.getRemoteInstance();
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
			MsgBox.showInfo("定价单没有提交!");
			return;
		}
		if (state.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
			MsgBox.showInfo("定价单已经审核!");
			return;
		}
		if (state.getValue().equals(FDCBillStateEnum.INVALID_VALUE)) {
			MsgBox.showInfo("定价单已经作废!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		RoomPriceBillFactory.getRemoteInstance().audit(BOSUuid.read(id));
		this.refresh(null);
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
			MsgBox.showInfo("定价单已经执行!");
			return;
		}
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName == null) {
			MsgBox.showInfo("定价单没有审核!");
			return;
		}

		if (MsgBox.showConfirm2New(this, "是否执行?") == MsgBox.YES) {
			String id = (String) row.getCell(this.getKeyFieldName()).getValue();
//			SelectorItemCollection sels = new SelectorItemCollection();
//			sels.add("*");
//			sels.add("entrys.*");
//			sels.add("entrys.room.*");
//			sels.add("entrys.room.sellState");
//			RoomPriceBillInfo bill = (RoomPriceBillInfo) RoomPriceBillFactory
//					.getRemoteInstance().getRoomPriceBillInfo(
//							new ObjectUuidPK(BOSUuid.read(id)), sels);
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.id", id));
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("room.*");
			view.getSelector().add("room.sellState");
			
			RoomPriceBillEntryCollection entrys = RoomPriceBillEntryFactory.getRemoteInstance().getRoomPriceBillEntryCollection(view);
			for (int i = 0; i < entrys.size(); i++) {
				RoomPriceBillEntryInfo entry = entrys.get(i);
				RoomInfo room = entry.getRoom();
				// 房间已售
				if (entry.isIsAdjust()) {
					if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)
							|| room.getSellState().equals(RoomSellStateEnum.Purchase)
							|| room.getSellState().equals(
									RoomSellStateEnum.Sign)) {
						MsgBox.showInfo("定价单中房间" + room.getNumber()
								+ " 调价审批中被销售,将自动跳过!");
						continue;
					}
				}
				
				if (!entry.isIsAdjust()) {
					continue;
				}
				
				//-提供面积反复核功能后，这里需判断该房间是否售前复核 for BT337524 by zhicheng_jin
				if(!room.isIsAreaAudited()){
					MsgBox.showInfo("定价单中房间" + room.getNumber()
							+ " 没有售前复核,将自动跳过!");
					continue;
				}
				//---
				
				// 套内面积改变
				boolean isEdit = false;
				BigDecimal entryRoomArea = entry.getRoomArea();
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
				BigDecimal entryBuildingArea = entry.getBuildingArea();
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
					MsgBox.showInfo("定价单中房间" + room.getNumber()
							+ " 定价后面积变化,将自动跳过!");
				}
			}
			if (RoomPriceBillFactory.getRemoteInstance().execute(id)) {
				MsgBox.showInfo("执行成功!");
				this.refresh(null);
			} else {
				MsgBox.showInfo("定价单没有审核!");
			}
		}
	}

	protected void refresh(ActionEvent e) throws Exception {
		super.refresh(e);
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
			MsgBox.showInfo("定价单没有审核!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		RoomPriceBillFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
		this.refresh(null);
	}

	protected FilterInfo getDefaultFilterForQuery() {
		Set idSet = SHEHelper.getLeafCompanyIdSet(this.actionOnLoad);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit.id", idSet, CompareType.INCLUDE));
		return filter;
	}

	protected void execQuery() {
		super.execQuery();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName != null) {
			MsgBox.showInfo("定价单已经审核不能修改!");
			return;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID",this.getSelectedIdValues());
		super.actionEdit_actionPerformed(e);
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
			MsgBox.showInfo("定价单已经审核不能删除!");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}
}