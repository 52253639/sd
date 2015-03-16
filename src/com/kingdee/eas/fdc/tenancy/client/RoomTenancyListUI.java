/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;

import javax.swing.tree.TreeNode;
import org.apache.log4j.Logger;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.ISortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.RoomEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SimpleKDTSortManager;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class RoomTenancyListUI extends AbstractRoomTenancyListUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomTenancyListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public RoomTenancyListUI() throws Exception {
		super();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected String getEditUIName() {
		return RoomEditUI.class.getName();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());

	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionQuery.setVisible(false);
		this.tblMain.getColumn("tenancyRoomArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
		// this.tblMain.getColumn("actualBuildingArea").getStyleAttributes()
		// .setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomRentPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("buildingArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("roomArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("standardRentPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("standardRent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("tenancyArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("rentPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		// UI的排序对未绑定字段的Table忽略，这里重简单实现排序
		SimpleKDTSortManager.setTableSortable(tblMain);
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.HEAD_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
			// 重写，使用SimpleSortManager排序
			return;
		}
		super.tblMain_tableClicked(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}

	protected String getLongNumberFieldName() {
		return "number";
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("building.name");
		view.getSelector().add("building.sellProject.name");
		view.getSelector().add("building.subarea.name");
		view.getSelector().add("roomModel.name");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		setColumnNotHidden();
		if (node.getUserObject() instanceof Integer) {	//已作废
			Integer unit = (Integer) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
			filter.getFilterItems().add(new FilterItemInfo("unit", unit));
			tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
			tblMain.getColumn("buildingName").getStyleAttributes().setHided(true);
			tblMain.getColumn("unit").getStyleAttributes().setHided(true);
		}else if (node.getUserObject() instanceof BuildingUnitInfo) {	
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
			filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
			tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
			tblMain.getColumn("buildingName").getStyleAttributes().setHided(true);
			tblMain.getColumn("unit").getStyleAttributes().setHided(true);
		} else if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
			view.getSorter().add(new SorterItemInfo("unit"));
			if (building.getUnitCount() == 0) {
				tblMain.getColumn("unit").getStyleAttributes().setHided(true);
			}
			tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
			tblMain.getColumn("buildingName").getStyleAttributes().setHided(true);
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			String subareaId = subarea.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.subarea.id", subareaId));
			view.getSorter().add(new SorterItemInfo("building.id"));
			view.getSorter().add(new SorterItemInfo("unit"));

			tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
		} else if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			String sellProjectId = sellProject.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", sellProjectId));
			view.getSorter().add(new SorterItemInfo("building.subarea.id"));
			view.getSorter().add(new SorterItemInfo("building.id"));
			view.getSorter().add(new SorterItemInfo("unit"));
			if (sellProject.getSubarea() == null || sellProject.getSubarea().isEmpty()) {
				tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
			}
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
			this.tblMain.removeRows();
			return;
		}
		view.getSorter().add(new SorterItemInfo("number"));
		this.tblMain.removeRows();
		RoomCollection rooms = RoomFactory.getRemoteInstance().getRoomCollection(view);
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			IRow row = this.tblMain.addRow();
			row.setUserObject(room);
			if (room.getBuilding().getSubarea() != null) {
				row.getCell("subarea").setValue(room.getBuilding().getSubarea().getName());
			}
			row.getCell("id").setValue(room.getId().toString());
			row.getCell("buildingName").setValue(room.getBuilding().getName());
			row.getCell("unit").setValue(new Integer(room.getUnit()));
			row.getCell("number").setValue(room.getNumber());
			row.getCell("roomModelName").setValue(room.getRoomModel().getName());
			row.getCell("rentType").setValue(room.getRentType());
			row.getCell("tenancyModel").setValue(room.getTenancyModel());
			if (room.isIsActualAreaAudited() && room.getActualBuildingArea() != null) {
				row.getCell("buildingArea").setValue(room.getActualBuildingArea());
			} else {
				row.getCell("buildingArea").setValue(room.getBuildingArea());
			}
			if (room.isIsActualAreaAudited() && room.getActualRoomArea() != null) {
				row.getCell("roomArea").setValue(room.getActualRoomArea());
			} else {
				row.getCell("roomArea").setValue(room.getRoomArea());
			}
			row.getCell("standardRent").setValue(room.getStandardRent());
			row.getCell("standardRentPrice").setValue(room.getStandardRentPrice());
			row.getCell("roomRentPrice").setValue(room.getRoomRentPrice());
			row.getCell("tenancyState").setValue(room.getTenancyState());
			row.getCell("tenancyArea").setValue(room.getTenancyArea());
			BigDecimal tenancyAmount = FDCHelper.ZERO;
			BigDecimal tenancyArea = row.getCell("tenancyArea").getValue() == null ? FDCHelper.ZERO : new BigDecimal(row.getCell("tenancyArea").getValue().toString());
			BigDecimal standardRent = row.getCell("standardRent").getValue() == null ? FDCHelper.ZERO : new BigDecimal(row.getCell("standardRent").getValue().toString());
			if (tenancyArea != null) {
				if(tenancyArea.compareTo(new BigDecimal("0.00"))==0){
				}else{
					tenancyAmount = standardRent.divide(tenancyArea, 2, BigDecimal.ROUND_HALF_UP);
					row.getCell("rentPrice").setValue(tenancyAmount);
				}
			}else{
				row.getCell("rentPrice").setValue(null);
			}
		}
	}

	private void setColumnNotHidden() {
		tblMain.getColumn("subarea").getStyleAttributes().setHided(false);
		tblMain.getColumn("buildingName").getStyleAttributes().setHided(false);
		tblMain.getColumn("unit").getStyleAttributes().setHided(false);
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {

	}

	// public void refreshListForOrder() throws Exception{
	// this.treeMain_valueChanged(null);
	// }

	// 修改刷新报错的问题 zhicheng_jin 090305
	protected void refresh(ActionEvent e) throws Exception {
		this.treeMain_valueChanged(null);
	}
}