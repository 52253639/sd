/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.QuitTenancyCollection;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.QuitTenancyInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class QuitTenDetailListUI extends AbstractQuitTenDetailListUI {
	private static final Logger logger = CoreUIObject
	.getLogger(QuitTenDetailListUI.class);

	public QuitTenDetailListUI() throws Exception {
		super();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getBuildingTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnQuery.setEnabled(true);
		this.actionAddNew.setVisible(false);
		this.menuEdit.setVisible(false);
		// this.tblMain.checkParsed();
	}

	protected void execQuery() {
		try {
			fillData();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		execQuery();
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		execQuery();
	}

	private void fillData() throws BOSException {
		this.tblMain.removeRows();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		this.tblMain.getColumn("sellProject").getStyleAttributes().setHided(
				true);
		this.tblMain.getColumn("subArea").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("building").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("unit").getStyleAttributes().setHided(false);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo roomFilter = new FilterInfo();

		if (node.getUserObject() instanceof Integer) { // 单元
			Integer unit = (Integer) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			roomFilter.getFilterItems().add(
					new FilterItemInfo("room.building.id", building.getId()
							.toString()));
			roomFilter.getFilterItems().add(new FilterItemInfo("unit", unit));

			hideTheColumns(new String[] { "sellProject", "subArea", "building",
			"unit" });

			// setTheViewSorter(view,new String[]{ "number" });
		} else if (node.getUserObject() instanceof BuildingInfo) { // 楼栋
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			roomFilter.getFilterItems().add(
					new FilterItemInfo("room.building.id", building.getId()
							.toString()));

			hideTheColumns(new String[] { "sellProject", "subArea", "building",
					building.getUnitCount() == 0 ? "unit" : "" });

			// setTheViewSorter(view,new String[]{
			// building.getUnitCount()==0?"":"unit","number" });
		} else if (node.getUserObject() instanceof SubareaInfo) { // 分区
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			roomFilter.getFilterItems().add(
					new FilterItemInfo("room.building.subarea.id", subarea
							.getId().toString()));

			hideTheColumns(new String[] { "sellProject", "subArea" });

			// setTheViewSorter(view,new String[]{ "building.id","unit","number"
			// });
		} else if (node.getUserObject() instanceof SellProjectInfo) { // 销售项目
			SellProjectInfo sellProject = (SellProjectInfo) node
			.getUserObject();
			roomFilter.getFilterItems().add(
					new FilterItemInfo("room.building.sellProject.id",
							sellProject.getId().toString()));
			//this.tblMain.getColumn("sellProject").getStyleAttributes().setHided
			// (false);
			// setTheViewSorter(view,new String[]{
			// "building.sellProject.id","building.id","unit","number" });
		} else {
			return;
		}
		roomFilter.getFilterItems().add(
				new FilterItemInfo("actQuitTenDate", null,
						CompareType.NOTEQUALS));
		view.setFilter(roomFilter);
		view.getSelector().add("id");
		view.getSelector().add("actQuitTenDate");
		view.getSelector().add("flagAtTerm");
		view.getSelector().add("startDate");
		view.getSelector().add("endDate");
		view.getSelector().add("standardRentType");
		view.getSelector().add("standardRoomRent");
		view.getSelector().add("dealRentType");
		view.getSelector().add("dealRoomRent");
		view.getSelector().add("room.id");
		view.getSelector().add("room.building.name");
		view.getSelector().add("room.building.subarea.name");
		view.getSelector().add("room.floor");
		view.getSelector().add("room.unit");
		view.getSelector().add("room.number");
		view.getSelector().add("buildingArea");
		view.getSelector().add("tenancy.id");
		view.getSelector().add("tenancy.sellProject.name");
		TenancyRoomEntryCollection quitTen = TenancyRoomEntryFactory
		.getRemoteInstance().getTenancyRoomEntryCollection(view);
		BigDecimal allStandardRent = FDCHelper.ZERO;
		BigDecimal allDealRent = FDCHelper.ZERO;
		BigDecimal allBuildingAear = FDCHelper.ZERO;
		for (int i = 0; i < quitTen.size(); i++) {
			IRow row = tblMain.addRow(i);
			TenancyRoomEntryInfo tenInfo = (TenancyRoomEntryInfo) quitTen
			.get(i);
			if (tblMain.getColumn("subArea").getStyleAttributes().isHided()) {
			} else {
				if (tenInfo.getRoom().getBuilding().getSubarea() == null) {
					this.tblMain.getColumn("subArea").getStyleAttributes()
					.setHided(true);
				} else {
					row.getCell("subArea").setValue(
							tenInfo.getRoom().getBuilding().getSubarea()
							.getName());
				}

			}
			if (tblMain.getColumn("building").getStyleAttributes().isHided()) {

			} else {
				if (tenInfo.getRoom().getBuilding() == null) {
					this.tblMain.getColumn("building").getStyleAttributes()
					.setHided(true);
				} else {
					row.getCell("building").setValue(
							tenInfo.getRoom().getBuilding().getName());
				}
			}
			if (tblMain.getColumn("unit").getStyleAttributes().isHided()) {

			} else {
				if (tenInfo.getRoom().getUnit() == 0) {
					this.tblMain.getColumn("unit").getStyleAttributes()
					.setHided(true);
				} else {
					row.getCell("unit").setValue(
							new Integer(tenInfo.getRoom().getUnit()));
				}
			}

			row.getCell("floor").setValue(
					new Integer(tenInfo.getRoom().getFloor()));
			row.getCell("roomNumber").setValue(tenInfo.getRoom().getNumber());
			BigDecimal buildingArea = tenInfo.getBuildingArea();
			row.getCell("buildingArea").setValue(buildingArea);
			allBuildingAear = allBuildingAear.add(buildingArea);
			row.getCell("quitTenDate").setValue(tenInfo.getActQuitTenDate());
			row.getCell("flagAtTerm").setValue(tenInfo.getFlagAtTerm());

			row.getCell("startTenancy").setValue(tenInfo.getStartDate());
			row.getCell("endTenancy").setValue(tenInfo.getEndDate());
			row.getCell("standardRentType").setValue(
					tenInfo.getStandardRentType());
			BigDecimal standardRent = tenInfo.getStandardRoomRent();
			allStandardRent = allStandardRent.add(standardRent);
			row.getCell("standardRent").setValue(standardRent);
			row.getCell("dealTotalRentType")
			.setValue(tenInfo.getDealRentType());
			BigDecimal dealRent = tenInfo.getStandardRoomRent();
			row.getCell("dealTotalRent").setValue(dealRent);
			allDealRent = allDealRent.add(standardRent);
			String tenBillID = tenInfo.getTenancy().getId().toString();
			QuitTenancyCollection infoColl = null;
			QuitTenancyInfo info = null;
			EntityViewInfo view1 = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyBill.id", tenBillID,
							CompareType.EQUALS));
			view1.setFilter(filter);
			view1.getSelector().add("number");
			infoColl = QuitTenancyFactory.getRemoteInstance()
			.getQuitTenancyCollection(view1);
			for (int j = 0; j < infoColl.size(); j++) {
				info = infoColl.get(i);
			}
			row.getCell("quitTenNumber").setValue(info.getNumber());
		}
		// if(quitTen.size()>0)
		// {
		// IRow row = tblMain.addRow(quitTen.size());
		// row.getCell("floor").setValue("退租总套数合计");
		// row.getCell("buildingArea").setValue("退租面积合计");
		// row.getCell("standardRent").setValue("标准租金合计");
		// row.getCell("dealTotalRent").setValue("成交租金合计");
		// IRow row1 = tblMain.addRow(quitTen.size()+1);
		// row1.getCell("floor").setValue(new Integer(quitTen.size()));
		// row1.getCell("buildingArea").setValue(allBuildingAear);
		// row1.getCell("standardRent").setValue(allStandardRent);
		// row1.getCell("dealTotalRent").setValue(allDealRent);
		// row.getCell("floor").getStyleAttributes().setFontColor(Color.RED);
		//row.getCell("buildingArea").getStyleAttributes().setFontColor(Color.RED
		// );
		//row.getCell("standardRent").getStyleAttributes().setFontColor(Color.RED
		// );
		// row.getCell("dealTotalRent").getStyleAttributes().setFontColor(Color.
		// RED);
		// }
	}

	/**
	 * 隐藏指定的列 (列名有 : subArea,building,unit,number)
	 * 
	 * @param colNames
	 */
	private void hideTheColumns(String[] colNames) {
		if (colNames != null && colNames.length > 0) {
			for (int i = 0, j = colNames.length; i < j; i++) {
				if (colNames[i] != null && !colNames[i].equals(""))
					this.tblMain.getColumn(colNames[i]).getStyleAttributes()
					.setHided(true);
			}
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		// if (e.getType() == KDTStyleConstants.HEAD_ROW
		// && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1){
		// //重写，使用SimpleSortManager排序
		// return;
		// }
		// super.tblMain_tableClicked(e);
	}

	public void storeFields() {
		super.storeFields();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
}