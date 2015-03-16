/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.ISortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTCell;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetInfo;
import com.kingdee.eas.fdc.sellhouse.CalculateMethodEnum;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomPriceBillEditUI extends AbstractRoomPriceBillEditUI {

	private static final Logger logger = CoreUIObject
	.getLogger(RoomPriceBillEditUI.class);

	BuildingPriceSetInfo priceSet = new BuildingPriceSetInfo();

	BuildingPriceSetCollection priceSetCollection = new BuildingPriceSetCollection();

	protected void setAuditButtonStatus(String oprtType) {

	}

	public void onShow() throws Exception {
		super.onShow();
		if (this.txtNumber.isEnabled()) {
			this.txtNumber.requestFocus();
		} else {
			this.txtName.requestFocus();
		}
		//		for(int i=0;i<tblRooms.getRowCount();i++)
		//		{xia
		//			tblRooms.getRow(i).getCell("priceType").setValue(PriceTypeForPriceBillEnum.AreaPrice);
		//			
		//		}
		//		if(RoomPriceTypeEnum.BatchPrice.equals((RoomPriceTypeEnum)this.comboPriceBillType.getSelectedItem()))
		//		{
		//			for(int i=0;i<tblRooms.getRowCount();i++)
		//			{
		//				tblRooms.getRow(i).getCell("priceType").setValue(PriceTypeForPriceBillEnum.AreaPrice);
		//				
		//			}
		//		}
		//		if(RoomPriceTypeEnum.SolitudePrice.equals((RoomPriceTypeEnum)this.comboPriceBillType.getSelectedItem()))
		//		{
		//			for(int i=0;i<tblRooms.getRowCount();i++)
		//			{
		//				tblRooms.getRow(i).getCell("priceType").setValue(PriceTypeForPriceBillEnum.AreaPrice);
		//				
		//			}
		//		}
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = new KDTable();
		table.addColumns(9);
		for(int i=0;i<tblRooms.getRowCount();i++)
		{

			IRow irow = (IRow)tblRooms.getRow(i).clone();
			table.addRow(i,irow);
		}
		this.getRoomPrice(table);
		table.getPrintManager().print();
		super.actionPrint_actionPerformed(e);
	}

	protected void chkIsAutoToInteger_actionPerformed(ActionEvent e) throws Exception {
		if(isFirstLoad) return;
		boolean isAutoToInteger = this.chkIsAutoToInteger.isSelected();
		setToIntegerVisable(isAutoToInteger);

		refreshRowData();
	}

	private void refreshRowData() {
		for(int i=0; i<this.tblRooms.getRowCount(); i++){
			IRow row = this.tblRooms.getRow(i);
			updateRowData(row);
		}
		setStatPrice();
		
		
		updateTotalRows();
	}

	protected void comboToIntegerType_itemStateChanged(ItemEvent e) throws Exception {
		refreshRowData();
	}

	protected void comboDigit_itemStateChanged(ItemEvent e) throws Exception {
		refreshRowData();
	}

	private void setToIntegerVisable(boolean isAutoToInteger) {
		this.contToIntegerType.setVisible(isAutoToInteger);
		this.contDigit.setVisible(isAutoToInteger);
	}

	private void getRoomPrice(KDTable table) {
		table.removeHeadRows();
		IRow row = table.addHeadRow();
		row.getCell(0).setValue("定价单编号");
		row.getCell(1).setValue(this.txtNumber.getText());
		row.getCell(2).setValue("名称");
		row.getCell(3).setValue(this.txtName.getText());
		row.getCell(4).setValue("");
		row.getCell(5).setValue("制单时间");
		row.getCell(6).setValue(this.pkCreateDate.getText());
		row.getCell(7).setValue("制单人");
		row.getCell(8).setValue(this.prmtCreator.getValue());
		IRow row1 = table.addHeadRow();
		row1.getCell(0).setValue("总价");
		row1.getCell(1).setValue(this.txtTotalPrice.getNumberValue());
		row1.getCell(2).setValue("总套数");
		row1.getCell(3).setValue(this.txtTotalCount.getNumberValue());
		row1.getCell(4).setValue("");
		row1.getCell(5).setValue("总建筑面积");
		row1.getCell(6).setValue(this.txtTotalBuildingArea.getNumberValue());
		row1.getCell(7).setValue("总套内面积");
		row1.getCell(8).setValue(this.txtTotalRoomArea.getNumberValue());
		IRow row2 = table.addHeadRow();
		row2.getCell(0).setValue("建筑均价");
		row2.getCell(1).setValue(this.txtBuildingAvgPrice.getNumberValue());
		row2.getCell(2).setValue("套内均价");
		row2.getCell(3).setValue(this.txtRoomAvgPrice.getNumberValue());
		row2.getCell(4).setValue("");
		row2.getCell(5).setValue("最高建筑单价");
		row2.getCell(6).setValue(this.txtHighestBuildingPrice.getNumberValue());
		row2.getCell(7).setValue("最高套内单价");
		row2.getCell(8).setValue(this.txtHighestRoomPrice.getNumberValue());
		IRow row3 = table.addHeadRow();
		row3.getCell(0).setValue("最底建筑单价");
		row3.getCell(1).setValue(this.txtLowestBuildingPrice.getNumberValue());
		row3.getCell(2).setValue("最底套内单价");
		row3.getCell(3).setValue(this.txtLowestRoomPrice.getNumberValue());
		IRow row4 = table.addHeadRow();
		row4.getCell(0).setValue("楼栋");
		row4.getCell(1).setValue("单元");
		row4.getCell(2).setValue("预测房号");
		row4.getCell(3).setValue("标准总价");
		row4.getCell(4).setValue("建筑面积");
		row4.getCell(5).setValue("建筑单价");
		row4.getCell(6).setValue("是否按套内面积");
		row4.getCell(7).setValue("套内面积");
		row4.getCell(8).setValue("套内单价");
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
	throws Exception {
		KDTable table = new KDTable();
		table.addColumns(9);
		for(int i=0;i<tblRooms.getRowCount();i++)
		{

			IRow irow = (IRow)tblRooms.getRow(i).clone();
			table.addRow(i,irow);
		}
		this.getRoomPrice(table);
		table.getPrintManager().printPreview();
		super.actionPrintPreview_actionPerformed(e);
	}

	public void onLoad() throws Exception {
		this.tblRooms.checkParsed();
		this.tblRooms.getColumn("buildingArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("buildingArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRooms.getColumn("buildingPrice").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("buildingPrice").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRooms.getColumn("roomArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("roomArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRooms.getColumn("roomPrice").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("roomPrice").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRooms.getColumn("sumAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("sumAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRooms.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblRooms.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);

		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblRooms.getColumn("roomPrice").setEditor(numberEditor);
		this.tblRooms.getColumn("buildingPrice").setEditor(numberEditor);
		this.tblRooms.getColumn("sumAmount").setEditor(numberEditor);
		this.tblRooms.getColumn("isCalByRoom").getStyleAttributes().setLocked(
				true);
		this.tblRooms.getColumn("room").getStyleAttributes().setLocked(true);
		this.tblRooms.getColumn("sumAmount").getStyleAttributes().setLocked(
				true);
		this.tblRooms.getColumn("roomArea").getStyleAttributes()
		.setLocked(true);
		this.tblRooms.getColumn("buildingArea").getStyleAttributes().setLocked(
				true);
		this.tblRooms.getColumn("roomNo").getStyleAttributes().setLocked(true);

		this.pkCreateDate.setEnabled(false);
		this.txtTotalPrice.setRemoveingZeroInDispaly(false);
		this.txtTotalPrice.setRemoveingZeroInEdit(false);
		this.txtTotalPrice.setPrecision(2);
		this.txtTotalPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtTotalPrice.setSupportedEmpty(true);
		this.txtTotalPrice.setEnabled(false);
		this.txtTotalPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);

		this.txtTotalBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtTotalBuildingArea.setRemoveingZeroInEdit(false);
		this.txtTotalBuildingArea.setPrecision(2);
		this.txtTotalBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtTotalBuildingArea.setSupportedEmpty(true);
		this.txtTotalBuildingArea.setEnabled(false);

		this.txtTotalRoomArea.setRemoveingZeroInDispaly(false);
		this.txtTotalRoomArea.setRemoveingZeroInEdit(false);
		this.txtTotalRoomArea.setPrecision(2);
		this.txtTotalRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtTotalRoomArea.setSupportedEmpty(true);
		this.txtTotalRoomArea.setEnabled(false);

		this.txtBuildingAvgPrice.setRemoveingZeroInDispaly(false);
		this.txtBuildingAvgPrice.setRemoveingZeroInEdit(false);
		this.txtBuildingAvgPrice.setPrecision(2);
		this.txtBuildingAvgPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingAvgPrice.setSupportedEmpty(true);
		this.txtBuildingAvgPrice.setEnabled(false);

		this.txtRoomAvgPrice.setRemoveingZeroInDispaly(false);
		this.txtRoomAvgPrice.setRemoveingZeroInEdit(false);
		this.txtRoomAvgPrice.setPrecision(2);
		this.txtRoomAvgPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRoomAvgPrice.setSupportedEmpty(true);
		this.txtRoomAvgPrice.setEnabled(false);

		this.txtTotalCount.setRemoveingZeroInDispaly(false);
		this.txtTotalCount.setRemoveingZeroInEdit(false);
		this.txtTotalCount.setPrecision(2);
		this.txtTotalCount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtTotalCount.setSupportedEmpty(true);
		this.txtTotalCount.setEnabled(false);

		this.txtHighestBuildingPrice.setRemoveingZeroInDispaly(false);
		this.txtHighestBuildingPrice.setRemoveingZeroInEdit(false);
		this.txtHighestBuildingPrice.setPrecision(2);
		this.txtHighestBuildingPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtHighestBuildingPrice.setSupportedEmpty(true);
		this.txtHighestBuildingPrice.setEnabled(false);

		this.txtHighestRoomPrice.setRemoveingZeroInDispaly(false);
		this.txtHighestRoomPrice.setRemoveingZeroInEdit(false);
		this.txtHighestRoomPrice.setPrecision(2);
		this.txtHighestRoomPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtHighestRoomPrice.setSupportedEmpty(true);
		this.txtHighestRoomPrice.setEnabled(false);

		this.txtLowestBuildingPrice.setRemoveingZeroInDispaly(false);
		this.txtLowestBuildingPrice.setRemoveingZeroInEdit(false);
		this.txtLowestBuildingPrice.setPrecision(2);
		this.txtLowestBuildingPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtLowestBuildingPrice.setSupportedEmpty(true);
		this.txtLowestBuildingPrice.setEnabled(false);

		this.txtLowestRoomPrice.setRemoveingZeroInDispaly(false);
		this.txtLowestRoomPrice.setRemoveingZeroInEdit(false);
		this.txtLowestRoomPrice.setPrecision(2);
		this.txtLowestRoomPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtLowestRoomPrice.setSupportedEmpty(true);
		this.txtLowestRoomPrice.setEnabled(false);

		Font font = new Font("SimSun", Font.BOLD, 15);
		Color color = Color.BLUE;
		this.contTotalPrice.getBoundLabel().setFont(font);
		this.contBuildingAvgPrice.getBoundLabel().setFont(font);
		this.contTotalBuildingArea.getBoundLabel().setFont(font);
		this.contTotalRoomArea.getBoundLabel().setFont(font);
		this.contRoomAvgPrice.getBoundLabel().setFont(font);
		this.contTotalCount.getBoundLabel().setFont(font);
		this.contHighestBuildingPrice.getBoundLabel().setFont(font);
		this.contHighestRoomPrice.getBoundLabel().setFont(font);
		this.contLowestRoomPrice.getBoundLabel().setFont(font);
		this.contLowestBuildingPrice.getBoundLabel().setFont(font);

		this.contTotalPrice.getBoundLabel().setForeground(color);
		this.contBuildingAvgPrice.getBoundLabel().setForeground(color);
		this.contTotalBuildingArea.getBoundLabel().setForeground(color);
		this.contTotalRoomArea.getBoundLabel().setForeground(color);
		this.contRoomAvgPrice.getBoundLabel().setForeground(color);
		this.contTotalCount.getBoundLabel().setForeground(color);
		this.contHighestBuildingPrice.getBoundLabel().setForeground(color);
		this.contHighestRoomPrice.getBoundLabel().setForeground(color);
		this.contLowestRoomPrice.getBoundLabel().setForeground(color);
		this.contLowestBuildingPrice.getBoundLabel().setForeground(color);

		this.txtTotalPrice.setFont(font);
		this.txtBuildingAvgPrice.setFont(font);
		this.txtTotalBuildingArea.setFont(font);
		this.txtTotalRoomArea.setFont(font);
		this.txtRoomAvgPrice.setFont(font);
		this.txtTotalCount.setFont(font);
		this.txtHighestBuildingPrice.setFont(font);
		this.txtHighestRoomPrice.setFont(font);
		this.txtLowestRoomPrice.setFont(font);
		this.txtLowestBuildingPrice.setFont(font);
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		super.onLoad();

		// this.contTotalPrice.getBoundLabel().setForeground(color);

		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

		//---定价单增加sellProject的字段，增加相应过滤  zhicheng_jin  090225
		/*
		 filter.getFilterItems().add(
		 new FilterItemInfo("orgUnit.id", saleOrg.getId().toString()));
		 view.setFilter(filter);
		 SellProjectCollection sellProjects = SellProjectFactory
		 .getRemoteInstance().getSellProjectCollection(view);
		 Set idSet = new HashSet();
		 for (int i = 0; i < sellProjects.size(); i++) {
		 idSet.add(sellProjects.get(i).getId().toString());
		 }
		 filter = new FilterInfo();
		 filter.getFilterItems()
		 .add(
		 new FilterItemInfo("sellProject.id", idSet,
		 CompareType.INCLUDE));
		 */						

		SellProjectInfo sellPro = this.editData.getSellProject();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(sellPro != null)filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellPro.getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("sellProject.isForSHE", "true"));
		//------------------------
		view.setFilter(filter);
		this.txtName.setRequired(true);
		this.f7Building.setEntityViewInfo(view);

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
		this.f7Building.setSelectorCollection(sels);

		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionCopyFrom.setEnabled(false);
		this.actionAddRoomByList.setEnabled(true);
		//this.btnAddRoom.setVisible(false);
		//this.btnDelRoom.setVisible(false);
		//this.btnScheme.setVisible(false);
		if(this.getOprtState().equals("VIEW"))
		{
			this.btnImportPrice.setEnabled(false);
			this.btnScheme.setEnabled(false);
			this.btnAddRoom.setEnabled(false);
			this.btnAddRoomByList.setEnabled(false);
			this.btnDelRoom.setEnabled(false);
		}
		
		initPrmtRoomSelect();
		InitTableRoomsPriceTypeCell();
		
	}

	protected void comboPriceBillType_itemStateChanged(ItemEvent e) throws Exception {
		super.comboPriceBillType_itemStateChanged(e);
		this.tblRooms.removeRows();
		if(RoomPriceTypeEnum.BatchPrice.equals((RoomPriceTypeEnum)this.comboPriceBillType.getSelectedItem()))
		{
			this.btnAddRoom.setVisible(false);
			this.btnAddRoomByList.setVisible(false);
			this.btnDelRoom.setVisible(false);
			this.btnScheme.setVisible(true);
			this.contBuilding.setVisible(true);
			this.f7Building.setValue(null);
			this.f7Building.setRequired(true);
			this.setStatPrice();
		}else
		{
			this.btnAddRoom.setVisible(true);
			this.btnAddRoomByList.setVisible(true);
			this.btnDelRoom.setVisible(true);
			this.btnScheme.setVisible(false);
			this.contBuilding.setVisible(false);
			this.f7Building.setRequired(false);
			this.setStatPrice();
		}

	}

	protected void btnAddRoom_actionPerformed(ActionEvent e) throws Exception {
		RoomCollection rooms = RoomSelectUI.showMultiRoomSelectUI(this, null, null, MoneySysTypeEnum.SalehouseSys,null,this.editData.getSellProject());
		
		if (rooms == null) {
			return;
		}
		//对选择结果进行排序 xiaoao_liu
		CRMHelper.sortCollection(rooms, "displayname", true);
		
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			if (!room.isIsAreaAudited()) {
				MsgBox.showInfo(room.getDisplayName() + " 未进行售前复核!");
				continue;
			}
			if(room.isIsForSHE()==false)
			{
				MsgBox.showInfo(room.getDisplayName() + " 非售楼房间!");
				continue;
			}
			if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经预定!");
				continue;
			}
			if (room.getSellState().equals(RoomSellStateEnum.Purchase)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经认购!");
				continue;
			}
			if (room.getSellState().equals(RoomSellStateEnum.Sign)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经签约!");
				continue;
			}
			if (isExist(room)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经在列表中!");
				continue;
			}
			RoomPriceBillEntryInfo entry = new RoomPriceBillEntryInfo();
			entry.setBuildPrice(room.getBuildPrice());
			entry.setBuildingArea(room.getBuildingArea());
			entry.setRoomPrice(room.getRoomPrice());
			entry.setRoomArea(room.getRoomArea());
//			修改房间的定价类型为界面上选择的定价类型 xiaoao_liu
			entry.setPriceType((PriceTypeForPriceBillEnum)this.comboTopriceType.getSelectedItem());
//			if(room.isIsCalByRoomArea())
//				entry.setPriceType(PriceTypeForPriceBillEnum.UseRoomArea);
//			else
//				entry.setPriceType(PriceTypeForPriceBillEnum.UseBuildingArea);			
			entry.setIsAdjust(true);
			entry.setRoom(room);
			this.addRowByEntry(entry);
			super.btnAddRoom_actionPerformed(e);
		}

		setStatPrice();

		//按房间编号排序
//		orderByRoomNumber();
		
		deleteTotalRows();
		addTotalRows();
	}



	private boolean isExist(RoomInfo room) {
		for (int j = 0; j < this.tblRooms.getRowCount(); j++) {
			
			//添加汇总行
			if(this.tblRooms.getRow(j).getUserObject() instanceof String)
			{
				continue;
			}
			RoomPriceBillEntryInfo roomEntry = (RoomPriceBillEntryInfo) this.tblRooms
			.getRow(j).getUserObject();
			if (roomEntry.getRoom().getId().toString().equals(
					room.getId().toString())) {
				return true;
			}
		}
		return false;
	}

	private void initPrmtRoomSelect() throws EASBizException, BOSException{

		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

		EntityViewInfo view = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("isForSHE",Boolean.TRUE));
		filterItems.add(new FilterItemInfo("sellProject.isForSHE",Boolean.TRUE));
		filterItems.add(new FilterItemInfo("sellProject.id",getSellProjectIds(),CompareType.INCLUDE));
		//		filterItems.add(new FilterItemInfo("sellProject.orgUnit.id",saleOrg.getId().toString()));
		filterItems.add(new FilterItemInfo("sellProject.id",editData.getSellProject().getId()));

		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo("sellProject.name"));
		sorter.add(new SorterItemInfo("building.subarea.name"));
		sorter.add(new SorterItemInfo("number"));
		view.setFilter(filter);

		view.setSorter(sorter);

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");
		selectors.add("building.*");
		selectors.add("roomModel.*");
		selectors.add("building.sellProject.*");
		selectors.add("buildUnit.name");

		prmtRoomSelect.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSelectByListQuery");
		prmtRoomSelect.setSelectorCollection(selectors);
		prmtRoomSelect.setEntityViewInfo(view);
		prmtRoomSelect.setEnabledMultiSelection(true);

		prmtRoomSelect.getQueryAgent().resetRuntimeEntityView();

	}


	/**
	 * 描述：获取当前用户所有的销售项目
	 * @return
	 * @throws BOSException
	 */
	private Set getSellProjectIds() throws BOSException{

		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

		Set ids = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		view.getSorter().add(new SorterItemInfo("number"));	
		FilterInfo filter = new FilterInfo();
		try {
			String proIdSql = CommerceHelper.getPermitProjectIdSql(SysContext.getSysContext().getCurrentUserInfo());
			filter.getFilterItems().add(new FilterItemInfo("id", proIdSql,CompareType.INNER));
		} catch (EASBizException e) {
			e.printStackTrace();
			SysUtil.abort();
		}				
		filter.getFilterItems().add(new FilterItemInfo("isForSHE", Boolean.TRUE));

		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("buildings.id");
		view.getSelector().add("buildings.name");
		view.getSelector().add("buildings.number");
		view.getSelector().add("buildings.unitCount");
		view.getSelector().add("buildings.codingType");
		view.getSelector().add("buildings.buildingHeight");
		view.getSelector().add("buildings.joinInDate");
		view.getSelector().add("buildings.completeDate");
		view.getSelector().add("buildings.floorCount");
		view.getSelector().add("buildings.buildingTerraArea");
		view.getSelector().add("buildings.conditionType");
		view.getSelector().add("buildings.conditionStandard");
		view.getSelector().add("buildings.administrativeNumber");
		view.getSelector().add("buildings.sellProject.id");
		view.getSelector().add("buildings.buildingProperty.id");
		view.getSelector().add("buildings.constructPart.id");
		view.getSelector().add("buildings.buildingStructure.id");
		view.getSelector().add("buildings.productType.id");	
		view.getSelector().add("buildings.subarea.id");		
		view.getSelector().add("orgUnitShareList.orgUnit.id");   // 共享给其他组织的销售项目		

		List allSellProList = new ArrayList();
		SellProjectCollection sellProjects = SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
		for(int i=0;i<sellProjects.size();i++) {
			allSellProList.add(sellProjects.get(i));
		}

		for (int i = 0; i < sellProjects.size(); i++) {     //共享给其他组织的情况
			SellProjectInfo sellProjectInfo = (SellProjectInfo)sellProjects.get(i);
			if(sellProjectInfo.getOrgUnitShareList().size()>0) {
				for(int j=0;j<sellProjectInfo.getOrgUnitShareList().size();j++) {
					SellProjectInfo addNewSellProInfo = (SellProjectInfo)sellProjectInfo.clone();
					addNewSellProInfo.setOrgUnit(sellProjectInfo.getOrgUnitShareList().get(j).getOrgUnit());
					allSellProList.add(addNewSellProInfo);
				}
			}			
		}

		for (int i = 0; i < allSellProList.size(); i++) {
			SellProjectInfo sellProjectInfo = (SellProjectInfo)allSellProList.get(i);
			if(sellProjectInfo.getOrgUnit().getId().toString().equals(saleOrg.getId().toString())){
				ids.add(sellProjectInfo.getId().toString());
			}
		}
		return ids;
	}

	/**
	 * 描述：以列表方式显示增加房间
	 */
	public void actionAddRoomByList_actionPerformed(ActionEvent e)
	throws Exception {
		//		EntityViewInfo view = prmtRoomSelect.getEntityViewInfo();
		//		
		//		FilterInfo filter = view.getFilter();
		//		
		//		//销售项目
		//		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",
		//				this.editData.getSellProject().getId().toString()));
		//分区
		//		 filterItems.add(new
		//		 FilterItemInfo("building.subarea.id",subarea.getId().toString()));

		//楼栋
		//		 filterItems.add(new FilterItemInfo("building.id",
		//		 editData.getBuildings()));

		//按钮触发F7
		prmtRoomSelect.setDataBySelector();

		Object[] rooms = (Object[]) prmtRoomSelect.getValue();
		prmtRoomSelect.setValue(null);
		if (rooms == null || rooms.length == 0 || rooms[0] == null) {
			return;
		}
		for (int i = 0; i < rooms.length; i++) {
			RoomInfo room = (RoomInfo) rooms[i];
			if (!room.isIsAreaAudited()) {
				MsgBox.showInfo(room.getDisplayName()+ " 未进行售前复核!");
				continue;
			}
			if (room.isIsForSHE() == false) {
				MsgBox.showInfo(room.getDisplayName() + " 非售楼房间!");
				continue;
			}
			if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经预定!");
				continue;
			}
			if (room.getSellState().equals(RoomSellStateEnum.Purchase)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经认购!");
				continue;
			}
			if (room.getSellState().equals(RoomSellStateEnum.Sign)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经签约!");
				continue;
			}
			if (isExist(room)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经在列表中!");
				continue;
			}
			RoomPriceBillEntryInfo entry = new RoomPriceBillEntryInfo();
			entry.setBuildPrice(room.getBuildPrice());
			entry.setBuildingArea(room.getBuildingArea());
			entry.setRoomPrice(room.getRoomPrice());
			entry.setRoomArea(room.getRoomArea());
//			修改房间的定价类型为界面上选择的定价类型 xiaoao_liu
			entry.setPriceType((PriceTypeForPriceBillEnum)this.comboTopriceType.getSelectedItem());
//			if(room.isIsCalByRoomArea())
//				entry.setPriceType(PriceTypeForPriceBillEnum.UseRoomArea);
//			else
//				entry.setPriceType(PriceTypeForPriceBillEnum.UseBuildingArea);
			entry.setIsAdjust(true);
			entry.setRoom(room);
			this.addRowByEntry(entry);
		}

		setStatPrice();

		//按房间编号排序
//		orderByRoomNumber();
		
		deleteTotalRows();
		addTotalRows();
	}

	protected void btnDelRoom_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblRooms.getSelectManager()
		.getActiveRowIndex();
		if(activeRowIndex >=0 &&tblRooms.getRow(activeRowIndex).getUserObject() instanceof String)
		{
			return;
		}
		else
		{
			this.tblRooms.removeRow(activeRowIndex);
			super.btnDelRoom_actionPerformed(e);
			setStatPrice();

			//添加汇总行
			deleteTotalRows();
			addTotalRows();
		}
	}
	
	protected void deleteTotalRows()
	{
		for(int rowIndex = tblRooms.getRowCount()-1; rowIndex >= 0; --rowIndex)
		{
			IRow tmpRow = tblRooms.getRow(rowIndex);
			if(tmpRow.getUserObject() instanceof String)
			{
				tblRooms.removeRow(rowIndex);
			}
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable()) {
			if (StringUtil.isEmptyString(this.txtNumber.getText())) {
				MsgBox.showInfo("编码必须录入!");
				this.abort();
			}
		}
		if (StringUtil.isEmptyString(this.txtName.getText())) {
			MsgBox.showInfo("名称必须录入!");
			this.abort();
		}
		//		if (this.f7Building.getValue() == null) {
		//			MsgBox.showInfo("楼栋必须录入!");
		//			this.abort();
		//		}
		super.verifyInput(e);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		//		setStatPrice();// 调用该函数主要进行总价验证
		//		if (this.priceSet.getPriceScheme() != null) {
		//			BuildingPriceSetFactory.getRemoteInstance().submit(this.priceSet);
		//		}
		/**************************************为了实现根据按套内定价为钩选状态的值*************************************/
		/**
		 * 用于已经启用了定价类型方式，所以是否按照套内定价功能可以取消，并不影响使用
		 */
		//updateRowData(tblRooms.getRow(rowIndex));
		/*SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("entrys.isCalByRoomArea"));
		String pk_ID = (String) this.getUIContext().get("ID");
		IObjectPK  pk =  new ObjectStringPK(pk_ID);
		RoomPriceBillInfo roomInfo = RoomPriceBillFactory.getRemoteInstance().getRoomPriceBillInfo(pk,sic);
		RoomPriceBillEntryCollection entrys =  roomInfo.getEntrys();
		ArrayList list = new ArrayList();
		for(int i=0;i<tblRooms.getRowCount();i++)
		{
			for(int j=0;j<tblRooms.getColumnCount();j++)
			{
				if((PriceTypeForPriceBillEnum.UsableAreaPrice).equals(tblRooms.getCell(i,j).getValue()))
				{
					RoomPriceBillEntryInfo entry = this.editData.getEntrys().get(i);
					boolean flag = entry.getBoolean("isCalByRoomArea");
					if(flag==false){
						entry.setIsCalByRoomArea(true);
					}
					//	entry.setIsAdjust(false);
					tblRooms.getCell(i,j).setValue(PriceTypeForPriceBillEnum.UsableAreaPrice);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("isCalByRoomArea");
					//	selector.add("isAdjust");
					RoomPriceBillEntryFactory.getRemoteInstance().updatePartial(entry, selector);

					RoomPriceBillFactory.getRemoteInstance().updateIsCalByRoomArea(entry);

				}
			}
			//			if(tblRooms.get.getCell("priceType").getValue().equals(PriceTypeForPriceBillEnum.UsableAreaPrice))
			//			{
			//				RoomPriceBillEntryInfo entry = this.editData.getEntrys().get(i);
			//				entry.setIsCalByRoomArea(true);
			//			}
		}*/
		/*
		 * 增加与底价比较的判断，单价不能低于底价
		 * xiaoao_liu
		 */
		
		for (int i = 0; i < this.tblRooms.getRowCount(); i++)
		{
			IRow row = this.tblRooms.getRow(i);
			if(i<this.tblRooms.getRowCount()-1){
				if(row.getCell("roomId").getValue()!=null){
			String roomId = row.getCell("roomId").getValue().toString();
			
			EntityViewInfo evi = new EntityViewInfo();
	        FilterInfo filterInfo = new FilterInfo();
	        filterInfo.getFilterItems().add(new FilterItemInfo("id", roomId, CompareType.EQUALS));
	        evi.setFilter(filterInfo);
	     
	        //查询的字段
	        SelectorItemCollection coll = new SelectorItemCollection();
	        coll.add(new SelectorItemInfo("id"));
	        coll.add(new SelectorItemInfo("isBasePriceAudited"));
	        coll.add(new SelectorItemInfo("baseRoomPrice "));
	        coll.add(new SelectorItemInfo("baseBuildingPrice"));
	        coll.add(new SelectorItemInfo("isCalByRoomArea"));
	        coll.add(new SelectorItemInfo("buildingArea"));
	        coll.add(new SelectorItemInfo("roomArea "));

	        evi.setSelector(coll);
	        IRoom restRevDao = RoomFactory.getRemoteInstance();
	        //满足条件的集合
	        RoomCollection collection = restRevDao.getRoomCollection(evi);
	        RoomInfo room=collection.get(0);
	        if(PriceTypeForPriceBillEnum.UseBuildingArea.equals(row.getCell("priceType").getValue())) 
			{
				
				BigDecimal buildingPrice =(BigDecimal) row.getCell("buildingPrice").getValue();
				
				if(room.isIsBasePriceAudited()){
					if(buildingPrice.compareTo(room.getBaseBuildingPrice())==-1){
						MsgBox.showInfo("第" + (i + 1) + "行,建筑单价不能低于房间底价!");
						SysUtil.abort();
					}
				}
			}
			if(PriceTypeForPriceBillEnum.UseRoomArea.equals(row.getCell("priceType").getValue())) 
			{
				BigDecimal roomPrice =(BigDecimal) row.getCell("roomPrice").getValue();
				if(room.isIsBasePriceAudited()){
					if(roomPrice.compareTo(room.getBaseRoomPrice())==-1){
						MsgBox.showInfo("第" + (i + 1) + "行,套内单价不能低于房间底价!");
						SysUtil.abort();
					}
				}
			}
			if(PriceTypeForPriceBillEnum.UseStandPrice.equals(row.getCell("priceType").getValue())) 
			{
				Object sumAmount = (Object)row.getCell("sumAmount").getValue();
				BigDecimal Amount =KDTableTools.getBigDecimal(sumAmount);
				BigDecimal roomBasePrice=null;
				if(room.isIsCalByRoomArea()){
					if(room.getRoomArea()!=null && room.getBaseRoomPrice()!=null){
						roomBasePrice = room.getRoomArea().multiply(room.getBaseRoomPrice());
					}
				}else{
					if(room.getBuildingArea()!=null && room.getBaseBuildingPrice()!=null){
						roomBasePrice = room.getBuildingArea().multiply(room.getBaseBuildingPrice());
					}
				}
				if(room.isIsBasePriceAudited()){
					if(Amount.compareTo(roomBasePrice)==-1){
						MsgBox.showInfo("第" + (i + 1) + "行,定价单标准总价不能低于房间底价总价!");
						SysUtil.abort();
					}
				}
				}
			 }
		   }
		}

		super.actionSave_actionPerformed(e);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception 
	{
		setStatPrice();// 调用该函数主要进行总价验证

		for (int i = 0; i < this.tblRooms.getRowCount(); i++)
		{
			IRow row = this.tblRooms.getRow(i);
			/*
			 * 增加与底价比较的判断，单价不能低于底价
			 * xiaoao_liu
			 */
			if(i<this.tblRooms.getRowCount()-1){
				if(row.getCell("roomId").getValue()!=null){
				String roomId = row.getCell("roomId").getValue().toString();
				
				EntityViewInfo evi = new EntityViewInfo();
		        FilterInfo filterInfo = new FilterInfo();
		        filterInfo.getFilterItems().add(new FilterItemInfo("id", roomId, CompareType.EQUALS));
		        evi.setFilter(filterInfo);
		     
		        //查询的字段
		        SelectorItemCollection coll = new SelectorItemCollection();
		        coll.add(new SelectorItemInfo("id"));
		        coll.add(new SelectorItemInfo("isBasePriceAudited"));
		        coll.add(new SelectorItemInfo("baseRoomPrice "));
		        coll.add(new SelectorItemInfo("baseBuildingPrice"));
		        coll.add(new SelectorItemInfo("isCalByRoomArea"));
		        coll.add(new SelectorItemInfo("buildingArea"));
		        coll.add(new SelectorItemInfo("roomArea "));

		        evi.setSelector(coll);
		        IRoom restRevDao = RoomFactory.getRemoteInstance();
		        //满足条件的集合
		        RoomCollection collection = restRevDao.getRoomCollection(evi);
		        RoomInfo room=collection.get(0);
		        if(PriceTypeForPriceBillEnum.UseBuildingArea.equals(row.getCell("priceType").getValue())) 
				{
					
					BigDecimal buildingPrice =(BigDecimal) row.getCell("buildingPrice").getValue();
					
					if(room.isIsBasePriceAudited()){
						if(buildingPrice.compareTo(room.getBaseBuildingPrice())==-1){
							MsgBox.showInfo("第" + (i + 1) + "行,建筑单价不能低于房间底价!");
							SysUtil.abort();
						}
					}
				}
				if(PriceTypeForPriceBillEnum.UseRoomArea.equals(row.getCell("priceType").getValue())) 
				{
					BigDecimal roomPrice =(BigDecimal) row.getCell("roomPrice").getValue();
					if(room.isIsBasePriceAudited()){
						if(roomPrice.compareTo(room.getBaseRoomPrice())==-1){
							MsgBox.showInfo("第" + (i + 1) + "行,套内单价不能低于房间底价!");
							SysUtil.abort();
						}
					}
				}
				if(PriceTypeForPriceBillEnum.UseStandPrice.equals(row.getCell("priceType").getValue())) 
				{
					Object sumAmount = (Object)row.getCell("sumAmount").getValue();
					BigDecimal Amount =KDTableTools.getBigDecimal(sumAmount);
					BigDecimal roomBasePrice=null;
					if(room.isIsCalByRoomArea()){
						if(room.getRoomArea()!=null && room.getBaseRoomPrice()!=null){
							roomBasePrice = room.getRoomArea().multiply(room.getBaseRoomPrice());
						}
					}else{
						if(room.getBuildingArea()!=null && room.getBaseBuildingPrice()!=null){
							roomBasePrice = room.getBuildingArea().multiply(room.getBaseBuildingPrice());
						}
					}
					if(room.isIsBasePriceAudited()){
						if(Amount.compareTo(roomBasePrice)==-1){
							MsgBox.showInfo("第" + (i + 1) + "行,定价单标准总价不能低于房间底价总价!");
							SysUtil.abort();
						}
					}
				}
				}
			}
				BigDecimal sumAmount = (BigDecimal) row.getCell("sumAmount").getValue();
			if (sumAmount == null || sumAmount.compareTo(FDCHelper.ZERO) == 0) 
			{
				MsgBox.showInfo("第" + (i + 1) + "行,标准总价为0!");
				return;
			}
		}


		this.setOprtState("EDIT");
		if (this.priceSet.getPriceScheme() != null) {
			BuildingPriceSetFactory.getRemoteInstance().submit(this.priceSet);
		}	
		super.actionSubmit_actionPerformed(e);
		setStatPrice();
	}

	/**
	 * output class constructor
	 */
	public RoomPriceBillEditUI() throws Exception {
		super();
	}

	private boolean isFirstLoad = false;

	public void loadFields() {
		isFirstLoad = true;
		super.loadFields();
		RoomPriceBillInfo roomPriceBillInfo = this.editData;
		RoomPriceTypeEnum priceEnum = roomPriceBillInfo.getPriceBillType();
		if(priceEnum==null)
		{
			priceEnum = RoomPriceTypeEnum.BatchPrice;
		}
		this.comboPriceBillType.setSelectedItem(priceEnum);
		if(RoomPriceTypeEnum.BatchPrice.equals(roomPriceBillInfo.getPriceBillType()) || 
				RoomPriceTypeEnum.BatchPrice.equals((RoomPriceTypeEnum)this.comboPriceBillType.getSelectedItem()))
		{
			BuildingEntryCollection buildingEntryColl = this.editData
			.getBuildingEntrys();
			this.tblRooms.removeRows();
			this.f7Building.setRequired(true);
			BuildingInfo[] buildingInfo = null;
			if (!buildingEntryColl.isEmpty()) {
				buildingInfo = new BuildingInfo[buildingEntryColl.size()];
			}

			for (int i = 0; i < buildingEntryColl.size(); i++) {
				buildingInfo[i] = buildingEntryColl.get(i).getBuilding();
			}
			this.f7Building.setValue(buildingInfo);

			this.setPriceSetScheme(buildingInfo);
			this.setStatPrice();
			this.btnAddRoom.setVisible(false);
			this.btnAddRoomByList.setVisible(false);
			this.btnDelRoom.setVisible(false);

		}else
		{
			RoomPriceBillEntryCollection entryColl = this.editData.getEntrys();
			this.tblRooms.removeRows();
			this.f7Building.setRequired(false);
			for(int i=0;i<entryColl.size();i++)
			{
				RoomPriceBillEntryInfo entryInfo = entryColl.get(i);
				this.addRowByEntry(entryInfo);
			}
			this.setStatPrice();
			this.btnScheme.setVisible(false);
		}	
		if (this.editData.getState() != null
				&& !this.editData.getState().equals(FDCBillStateEnum.SAVED)) {
			this.actionSave.setEnabled(false);
		}

		setToIntegerVisable(this.editData.isIsAutoToInteger());
		
		//添加合计行
		deleteTotalRows();
		addTotalRows();
		
		isFirstLoad = false;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("creator.*");
		sels.add("buildingEntrys.*");
		sels.add("buildingEntrys.building.*");
		sels.add("entrys.room.*");		
		//add by yaoweiwen 2009-12-26 yaoweiwen start
		sels.add("entrys.room.noise.*");
		sels.add("entrys.room.eyeSignht.*");
		sels.add("entrys.room.productDetail.*");		
		//		sels.add("entrys.room.decoraStd.*");
		//		sels.add("entrys.room.posseStd.*");
		//end			
		sels.add("entrys.room.buildUnit.name");
		sels.add("entrys.room.building.sellProject.id");
		sels.add("entrys.*");
		sels.add("buildingEntrys.building.sellProject.*");
		sels.add("entrys.room.building.*");
		//		sels.add("sellProject.*");
		return sels;
	}
	//定价方式导入时调用的方法
	private void addRowByEntry(RoomPriceBillEntryInfo entry) {
		IRow row = this.tblRooms.addRow();
		row.setUserObject(entry);
		setRowLock(row);
		
		BigDecimal buildPrice = entry.getBuildPrice();
		BigDecimal roomPrice = entry.getRoomPrice();
		BigDecimal buildingArea = entry.getBuildingArea();
		BigDecimal roomArea = entry.getRoomArea();
		BigDecimal sumAmount = entry.getStandAmount();
		if(sumAmount==null) sumAmount = new BigDecimal(0);
		
		RoomInfo room = entry.getRoom();
		boolean isAdjust = entry.isIsAdjust();
		if (!isAdjust) {
			// 不参与此次调整
			buildPrice = room.getBuildPrice();
			roomPrice = room.getRoomPrice();
			buildingArea = room.getBuildingArea();
			roomArea = room.getRoomArea();
			sumAmount = room.getStandardTotalAmount();
		}

		row.getCell("building").setValue(room.getBuilding().getName());
		row.getCell("unit").setValue(room.getBuildUnit());
		row.getCell("room").setValue(room.getDisplayName());
		row.getCell("roomNo").setValue(room.getRoomNo());
		row.getCell("buildingArea").setValue(buildingArea);
		row.getCell("buildingPrice").setValue(buildPrice);
		row.getCell("roomArea").setValue(roomArea);
		row.getCell("roomPrice").setValue(roomPrice);
		
		row.getCell("roomId").setValue(room.getId());//(roomPrice);
		
		row.getCell("sumAmount").setValue(sumAmount.setScale(2,BigDecimal.ROUND_HALF_UP));
		
		PriceTypeForPriceBillEnum priceTypeEnum = entry.getPriceType();
		if(priceTypeEnum==null) {//要处理下旧数据问题  
			if(entry.isIsCalByRoomArea())
				priceTypeEnum = PriceTypeForPriceBillEnum.UseRoomArea;
			else
				priceTypeEnum = PriceTypeForPriceBillEnum.UseBuildingArea;
		}	
		row.getCell("priceType").setValue(priceTypeEnum);

		updateRowData(row);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		RoomPriceBillInfo bill = this.editData;
		bill.setPriceBillType((RoomPriceTypeEnum)this.comboPriceBillType.getSelectedItem());
		RoomPriceBillEntryCollection entrys = new RoomPriceBillEntryCollection();
		if(RoomPriceTypeEnum.BatchPrice.equals((RoomPriceTypeEnum)this.comboPriceBillType.getSelectedItem()))
		{
			BuildingEntryCollection buildingEntryColl = new BuildingEntryCollection();

			// 存楼栋字符串
			StringBuffer buildingNames = new StringBuffer();

			// 存楼栋分录
			Object building[] = (Object[]) this.f7Building.getValue();

			if (building == null) {
				return;
			}
			for (int i = 0; i < building.length; i++) {
				BuildingEntryInfo buildingEntryInfo = new BuildingEntryInfo();
				buildingEntryInfo.setBuilding((BuildingInfo) building[i]);

				buildingEntryColl.add(buildingEntryInfo);

				if(i != 0){
					buildingNames.append(",");
				}

				buildingNames.append(((BuildingInfo) building[i]).getSellProject()
						+ "-" + ((BuildingInfo) building[i]).getName());
			}

			if(buildingNames.length() > 400){
				MsgBox.showInfo("楼栋数量过多！");
				this.abort();
			}

			bill.setBuildings(buildingNames.toString());

			for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
				IRow row = this.tblRooms.getRow(i);
				if(!(row.getUserObject() instanceof RoomPriceBillEntryInfo)) continue;
					
				if(row.getCell("roomPrice").getValue()==null){
					row.getCell("roomPrice").setValue(new BigDecimal(0));
				}
				if(row.getCell("buildingPrice").getValue()==null){
					row.getCell("buildingPrice").setValue(new BigDecimal(0));
				}
				
				RoomPriceBillEntryInfo entry = (RoomPriceBillEntryInfo)row.getUserObject();
				entry.setBuildingArea((BigDecimal) row.getCell("buildingArea").getValue());
				entry.setBuildPrice(SHEHelper.setScale((BigDecimal) row.getCell("buildingPrice").getValue()));
				entry.setRoomArea((BigDecimal) row.getCell("roomArea").getValue());
				entry.setRoomPrice(SHEHelper.setScale((BigDecimal) row.getCell("roomPrice").getValue()));
				entry.setStandAmount(SHEHelper.setScale((BigDecimal)row.getCell("sumAmount").getValue()));
				PriceTypeForPriceBillEnum priceTypeEnum = (PriceTypeForPriceBillEnum)row.getCell("priceType").getValue();
				entry.setPriceType(priceTypeEnum);
				entrys.add(entry);
			}
			bill.getEntrys().clear();
			bill.getEntrys().addCollection(entrys);

			bill.getBuildingEntrys().clear();
			bill.getBuildingEntrys().addCollection(buildingEntryColl);
		}else	{
			for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
				IRow row = this.tblRooms.getRow(i);
				if(!(row.getUserObject() instanceof RoomPriceBillEntryInfo)) continue;

				RoomPriceBillEntryInfo entry = (RoomPriceBillEntryInfo)row.getUserObject();
				entry.setBuildingArea((BigDecimal) row.getCell("buildingArea").getValue());
				entry.setBuildPrice(SHEHelper.setScale((BigDecimal) row.getCell("buildingPrice").getValue()));
				entry.setRoomArea((BigDecimal) row.getCell("roomArea").getValue());
				entry.setRoomPrice(SHEHelper.setScale((BigDecimal) row.getCell("roomPrice").getValue()));
				entry.setStandAmount(SHEHelper.setScale((BigDecimal)row.getCell("sumAmount").getValue()));
				PriceTypeForPriceBillEnum priceTypeEnum = (PriceTypeForPriceBillEnum)row.getCell("priceType").getValue();
				entry.setPriceType(priceTypeEnum);
				entrys.add(entry);
			}
			bill.getEntrys().clear();
			bill.getEntrys().addCollection(entrys);
		}
	}


	/**
	 * 测试方法
	 */
	protected void kDWorkButton1_actionPerformed(ActionEvent e)
	throws Exception {
		// super.kDWorkButton1_actionPerformed(e);
		if (this.f7Building.getValue() == null) {
			MsgBox.showInfo("请先选择楼栋!");
			return;
		}

		// 弹出界面
		BuildingPriceSetInfo ret = RoomPriceSchemeSetUI.showUI(this,
				this.priceSet, this.getOprtState());

		if (ret == null) {
			return;
		}
		if (this.getOprtState().equals("VIEW")
				|| this.getOprtState().equals("FINDVIEW")) {
			return;
		}
		this.priceSet = ret;
		BigDecimal basePointAmount = this.priceSet.getBasePointAmount();
		PriceSetSchemeInfo scheme = this.priceSet.getPriceScheme();
		BuildingPriceSetEntryCollection setEntrys = this.priceSet
		.getPriceSetEntry();
		if (scheme.isIsImportDeal()) {

		}
		for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
			IRow row = this.tblRooms.getRow(i);
			RoomPriceBillEntryInfo roomEntry = (RoomPriceBillEntryInfo) row
			.getUserObject();
			RoomInfo room = roomEntry.getRoom();
			if (!roomEntry.isIsAdjust()) {
				continue;
			}
			BigDecimal price = getCalRoomPrice(room, basePointAmount, setEntrys);
			if (price.compareTo(FDCHelper.ZERO) < 0) {
				MsgBox.showInfo("方案计算有房间为负数,请修改方案!");
				return;
			}
			if (scheme.isIsCalByRoomArea()) {
				row.getCell("isCalByRoom").setValue(Boolean.TRUE);
				row.getCell("roomPrice").setValue(price);
				row.getCell("buildingPrice").getStyleAttributes().setLocked(
						true);
				row.getCell("roomPrice").getStyleAttributes().setLocked(false);
			} else {
				row.getCell("isCalByRoom").setValue(Boolean.FALSE);
				row.getCell("buildingPrice").setValue(price);
				row.getCell("buildingPrice").getStyleAttributes().setLocked(
						false);
				row.getCell("roomPrice").getStyleAttributes().setLocked(true);
			}
			updateRowData(row);
		}
		setStatPrice();

	}

	/**
	 * output btnScheme_actionPerformed method
	 */
	protected void btnScheme_actionPerformed(java.awt.event.ActionEvent e)
	throws Exception {
		if (this.f7Building.getValue() == null) {
			MsgBox.showInfo("请先选择楼栋!");
			return;
		}
		/*
		 * 弹出界面，得到每个页签上面的 价格方案 信息集合
		 */
		BuildingPriceSetCollection priceSetColl = RoomPriceSchemeBatchSetUI
		.showUI(this, priceSetCollection, f7Building.getValue(), this
				.getOprtState());

		if (priceSetColl == null) {
			return;
		}
		/*
		 * 根据 集合 中的每个方案信息来修改 表格数据
		 */
		for (int j = 0; j < priceSetColl.size(); j++) {
			this.priceSet = priceSetColl.get(j);
			BigDecimal basePointAmount = this.priceSet.getBasePointAmount();
			PriceSetSchemeInfo scheme = this.priceSet.getPriceScheme();
			BuildingPriceSetEntryCollection setEntrys = this.priceSet
			.getPriceSetEntry();
			if (scheme.isIsImportDeal()) {

			}

			// 得到表格中的每一行
			for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
				IRow row = this.tblRooms.getRow(i);
				
				//跳过合计行
				if(row.getUserObject() instanceof String)
				{
					continue;
				}
				RoomPriceBillEntryInfo roomEntry = (RoomPriceBillEntryInfo) row
				.getUserObject();

				if(roomEntry == null)
				{
					continue;
				}
				RoomInfo room = roomEntry.getRoom();

				// 判断改行的这个房间，是否是属于这个楼栋的。
				if (priceSet.getBuilding().toString().equalsIgnoreCase(
						room.getBuilding().toString())) {

					if (!roomEntry.isIsAdjust()) {
						continue;
					}
					BigDecimal price = getCalRoomPrice(room, basePointAmount,
							setEntrys);
					if (price.compareTo(FDCHelper.ZERO) < 0) {
						MsgBox.showInfo("方案计算有房间为负数,请修改方案!");
						return;
					}
					if (scheme.isIsCalByRoomArea()) {
						row.getCell("isCalByRoom").setValue(Boolean.TRUE);
						row.getCell("roomPrice").setValue(price);
						row.getCell("buildingPrice").getStyleAttributes()
						.setLocked(true);
						row.getCell("roomPrice").getStyleAttributes()
						.setLocked(false);
					} else {
						row.getCell("isCalByRoom").setValue(Boolean.FALSE);
						row.getCell("buildingPrice").setValue(price);
						row.getCell("buildingPrice").getStyleAttributes()
						.setLocked(false);
						row.getCell("roomPrice").getStyleAttributes()
						.setLocked(true);
					}
					updateRowData(row);
				}
			}

		}
		// 设置 表格上方 文本框中 信息
		setStatPrice();

		/**
		 * unit 列记录的是起点
		 * room 列记录的是终点
		 * 更新合计行的值
		 */
		updateTotalRows();
	}

	private BigDecimal getCalRoomPrice(RoomInfo room,
			BigDecimal basePointAmount,
			BuildingPriceSetEntryCollection setEntrys) {
		BigDecimal price = basePointAmount;
		if (price == null) {
			price = FDCHelper.ZERO;
		}
		Map buildingSetMap = new TreeMap();
		Map schemeEntryMap = new TreeMap();
		for (int j = 0; j < setEntrys.size(); j++) {
			BuildingPriceSetEntryInfo buildingSetEntry = setEntrys.get(j);
			String entryId = buildingSetEntry.getSchemeEntry().getId()
			.toString();
			if (buildingSetMap.containsKey(entryId)) {
				BuildingPriceSetEntryCollection buildingSetEntrys = (BuildingPriceSetEntryCollection) buildingSetMap
				.get(entryId);
				buildingSetEntrys.add(buildingSetEntry);
			} else {
				BuildingPriceSetEntryCollection buildingSetEntrys = new BuildingPriceSetEntryCollection();
				buildingSetEntrys.add(buildingSetEntry);
				buildingSetMap.put(entryId, buildingSetEntrys);
			}
			schemeEntryMap.put(entryId, buildingSetEntry.getSchemeEntry());
		}

		Set keys = buildingSetMap.keySet();
		BigDecimal offset = FDCHelper.ZERO;
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			PriceSetSchemeEntryInfo entry = (PriceSetSchemeEntryInfo) schemeEntryMap
			.get(key);
			BuildingPriceSetEntryCollection setValues = (BuildingPriceSetEntryCollection) buildingSetMap
			.get(key);
			String field = entry.getFactorField();
			if (field == null) {
				continue;
			}
			Object value = room.get(field);
			if (field.equals("houseProperty")) {
				value = room.getHouseProperty();
			}
			if (value instanceof BigDecimal) {
				if (value == null) {
					value = FDCHelper.ZERO;
				}
				for (int j = 0; j < setValues.size(); j++) {
					BuildingPriceSetEntryInfo valueEntry = setValues.get(j);
					if (valueEntry.getFactorContentA().compareTo((BigDecimal) value) == 0) {
						if (entry.getCalculateMethod().equals(
								CalculateMethodEnum.Coefficient)) {
							price = price.multiply(valueEntry.getValue());
						} else {
							offset = offset.add(valueEntry.getValue());
						}
						break;
					}
				}
			} else if (value instanceof Integer) {
				BigDecimal bValue = new BigDecimal(((Integer) value).intValue());
				for (int j = 0; j < setValues.size(); j++) {
					BuildingPriceSetEntryInfo valueEntry = setValues.get(j);
					if (valueEntry.getFactorContentA().compareTo(bValue) == 0) {
						if (entry.getCalculateMethod().equals(
								CalculateMethodEnum.Coefficient)) {
							price = price.multiply(valueEntry.getValue());
						} else {
							offset = offset.add(valueEntry.getValue());
						}
						break;
					}
				}
			} else {
				if (value == null) {
					continue;
				}
				String valueS = value.toString();
				for (int j = 0; j < setValues.size(); j++) {
					BuildingPriceSetEntryInfo valueEntry = setValues.get(j);
					if (valueEntry.getFactorContentS().equals(valueS)) {
						if (entry.getCalculateMethod().equals(
								CalculateMethodEnum.Coefficient)) {
							price = price.multiply(valueEntry.getValue());
						} else {
							offset = offset.add(valueEntry.getValue());
						}
						break;
					}
				}
			}
		}
		price = price.add(offset);
		return price;
	}

	protected IObjectValue createNewData() {
		RoomPriceBillInfo priceBill = new RoomPriceBillInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		if(sellProject == null){
			logger.error("新增定价单时传入的销售项目不能为空!");
			this.abort();
		}
		priceBill.setSellProject(sellProject);
		priceBill.setIsExecuted(false);
		priceBill.setIsInvalid(false);
		try {
			// priceBill.setCreateTime(FDCCommonServerHelper.getServerTimeStamp());
			priceBill.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
		} catch (BOSException e) {
			this.handleException(e);
		}
		priceBill.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		priceBill.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		priceBill.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		priceBill.setBookedDate(new Date());

		priceBill.setIsAutoToInteger(false);
		priceBill.setToIntegerType(ToIntegerTypeEnum.AbnegateZero);
		priceBill.setDigit(DigitEnum.EntryDigit);
		return priceBill;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomPriceBillFactory.getRemoteInstance();
	}
	//去掉原来的方法在下面的选择框里更新数据
	protected void tblRooms_editStopped(KDTEditEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.tblRooms_editStopped(e);
		int rowIndex = e.getRowIndex();
		//updateRowData(tblRooms.getRow(rowIndex));
		IRow row = tblRooms.getRow(rowIndex);
		

		if(PriceTypeForPriceBillEnum.UseBuildingArea.equals(row.getCell("priceType").getValue())) 
		{
			BigDecimal buildingArea = (BigDecimal) tblRooms.getRow(rowIndex).getCell("buildingArea").getValue();
			BigDecimal buildingPrice =(BigDecimal) tblRooms.getRow(rowIndex).getCell("buildingPrice").getValue();
			BigDecimal roomArea =KDTableTools.getBigDecimal(tblRooms.getRow(rowIndex).getCell("roomArea").getValue());

			if(buildingArea!=null && buildingPrice!=null){
				BigDecimal buildingsumAmount=buildingArea.multiply(buildingPrice);
				tblRooms.getRow(rowIndex).getCell("sumAmount").setValue(buildingsumAmount.setScale(2,BigDecimal.ROUND_HALF_UP));
				BigDecimal roomPrice = buildingsumAmount.divide(roomArea,2,BigDecimal.ROUND_HALF_UP);
				if(roomPrice.compareTo(FDCHelper.ZERO) != 0){
					tblRooms.getRow(rowIndex).getCell("roomPrice").setValue(roomPrice);
				}
				setStatPrice();
			}
		}
		if(PriceTypeForPriceBillEnum.UseRoomArea.equals(row.getCell("priceType").getValue())) 
		{
			BigDecimal roomArea = (BigDecimal) tblRooms.getRow(rowIndex).getCell("roomArea").getValue();
			BigDecimal roomPrice =(BigDecimal) tblRooms.getRow(rowIndex).getCell("roomPrice").getValue();
			BigDecimal buildingArea =FDCNumberHelper.toBigDecimal(tblRooms.getRow(rowIndex).getCell("buildingArea").getValue());

			if(roomArea!=null && roomPrice!=null){
				BigDecimal roomsumAmount=roomArea.multiply(roomPrice);
				BigDecimal buildPrice = roomsumAmount.divide(buildingArea,2,BigDecimal.ROUND_HALF_UP);
				tblRooms.getRow(rowIndex).getCell("sumAmount").setValue(roomsumAmount.setScale(2,BigDecimal.ROUND_HALF_UP));
				tblRooms.getRow(rowIndex).getCell("buildingPrice").setValue(buildPrice);
				setStatPrice();
			}
		}
		if(PriceTypeForPriceBillEnum.UseStandPrice.equals(row.getCell("priceType").getValue())) 
		{
			BigDecimal roomArea = (BigDecimal) tblRooms.getRow(rowIndex).getCell("roomArea").getValue();
			BigDecimal buildingArea = (BigDecimal) tblRooms.getRow(rowIndex).getCell("buildingArea").getValue();
			updateSumAmount(row);
			Object sumAmount = (Object)row.getCell("sumAmount").getValue();
			BigDecimal Amount =KDTableTools.getBigDecimal(sumAmount);

			tblRooms.getRow(rowIndex).getCell("roomPrice").setValue((Amount.divide(roomArea,2,BigDecimal.ROUND_HALF_UP)));
			tblRooms.getRow(rowIndex).getCell("buildingPrice").setValue((Amount.divide(buildingArea,2,BigDecimal.ROUND_HALF_UP)));
			setStatPrice();
		}
		RoomPriceBillEntryInfo roomEntry = (RoomPriceBillEntryInfo) row.getUserObject();
		if(roomEntry.getBoolean("isadjust")==false)
		{
			row.getCell("roomPrice").getStyleAttributes().setLocked(true);
			row.getCell("buildingPrice").getStyleAttributes().setLocked(true);
			row.getCell("sumAmount").getStyleAttributes().setLocked(true);
			row.getCell("roomArea").getStyleAttributes().setLocked(true);
			row.getCell("buildingArea").getStyleAttributes().setLocked(true);
		}
		
		//更新合计行
		updateTotalRows();
	}
	

	public void setBTlocked(RoomPriceBillEntryCollection entrys,IRow row)
	{
		for(int i=0;i<entrys.size();i++)
		{
			RoomPriceBillEntryInfo entry = entrys.get(i);
			if(entry.getBoolean("isadjust")==false)
			{
				row.getCell("roomPrice").getStyleAttributes().setLocked(true);
				row.getCell("buildingPrice").getStyleAttributes().setLocked(true);
				row.getCell("sumAmount").getStyleAttributes().setLocked(true);
				row.getCell("roomArea").getStyleAttributes().setLocked(true);
				row.getCell("buildingArea").getStyleAttributes().setLocked(true);
			}
		}
	}
	private void updateSumAmount(IRow row)
	{
		BigDecimal sumAmount =FDCHelper.ZERO;
		if(row.getCell("sumAmount").getValue()==null)
		{
			row.getCell("sumAmount").setValue(new BigDecimal(0));
		}
		else{
			row.getCell("sumAmount").setValue(row.getCell("sumAmount").getValue());
		}

	}
	private BigDecimal getSumAmount(IRow row)
	{
		updateSumAmount(row);
		BigDecimal sumAmount = (BigDecimal) row.getCell("sumAmount").getValue();
		return sumAmount;
	}
	private void updateRowData(IRow row) {
		if(!(row.getCell("priceType").getValue() instanceof PriceTypeForPriceBillEnum)) return;
		
		PriceTypeForPriceBillEnum priceTypeEnum = (PriceTypeForPriceBillEnum)row.getCell("priceType").getValue();
		
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal roomArea = (BigDecimal) row.getCell("roomArea").getValue();		//套内面积
			if(roomArea==null) roomArea = new BigDecimal(0);
		BigDecimal buildingArea = (BigDecimal) row.getCell("buildingArea").getValue();	//建筑面积
			if(buildingArea==null)  buildingArea = new BigDecimal(0);

		if(row.getCell("roomPrice").getValue()==null){
			row.getCell("roomPrice").setValue(new BigDecimal(0));
		}
		if(row.getCell("buildingPrice").getValue()==null){
			row.getCell("buildingPrice").setValue(new BigDecimal(0));
		}		

		if (priceTypeEnum.equals(PriceTypeForPriceBillEnum.UseRoomArea)) {	
			//按套内面积定价   标准总价 = 套内面积×套内单价
			//				建筑单价 = （标准总价）/建筑单价
			amount = roomArea.multiply((BigDecimal) row.getCell("roomPrice").getValue());
			amount = SHEHelper.getAmountAfterToInteger(amount, this.chkIsAutoToInteger.isSelected(), (ToIntegerTypeEnum)this.comboToIntegerType.getSelectedItem(), (DigitEnum)this.comboDigit.getSelectedItem());
			if (buildingArea.compareTo(FDCHelper.ZERO) != 0) 
				row.getCell("buildingPrice").setValue(amount.divide(buildingArea, 2,BigDecimal.ROUND_HALF_UP));
			
			row.getCell("sumAmount").setValue(amount.setScale(2,BigDecimal.ROUND_HALF_UP));
		} else if(priceTypeEnum.equals(PriceTypeForPriceBillEnum.UseBuildingArea)){			
			//按建筑面积定价  标准总价 = 建筑面积×建筑单价
			//				套内单价 = （标准总价）/套内单价
			amount = buildingArea.multiply((BigDecimal) row.getCell("buildingPrice").getValue());
			amount = SHEHelper.getAmountAfterToInteger(amount, this.chkIsAutoToInteger.isSelected(), (ToIntegerTypeEnum)this.comboToIntegerType.getSelectedItem(), (DigitEnum)this.comboDigit.getSelectedItem());
			if (roomArea.compareTo(FDCHelper.ZERO) != 0) 
				row.getCell("roomPrice").setValue(amount.divide(roomArea, 2,BigDecimal.ROUND_HALF_UP));
					
			row.getCell("sumAmount").setValue(amount.setScale(2, BigDecimal.ROUND_HALF_UP));	//  
		}else if(priceTypeEnum.equals(PriceTypeForPriceBillEnum.UseStandPrice)){	
			//按标准总价定价  套内单价 = （标准总价）/套内面积
			//				建筑单价 = （标准总价）/建筑面积
			if(row.getCell("sumAmount").getValue()!=null && row.getCell("sumAmount").getValue() instanceof BigDecimal)
				amount = (BigDecimal)row.getCell("sumAmount").getValue();
			if (buildingArea.compareTo(FDCHelper.ZERO) != 0) 
				row.getCell("buildingPrice").setValue(amount.divide(buildingArea, 2,BigDecimal.ROUND_HALF_UP));
			if (roomArea.compareTo(FDCHelper.ZERO) != 0) 
				row.getCell("roomPrice").setValue(amount.divide(roomArea, 2,BigDecimal.ROUND_HALF_UP));
		}
		
	}

	private void setStatPrice() {
		BigDecimal sumSum = FDCHelper.ZERO;
		BigDecimal sumBuildingArea = FDCHelper.ZERO;
		BigDecimal sumRoomArea = FDCHelper.ZERO;
		BigDecimal highestBuildingPrice = FDCHelper.ZERO;
		BigDecimal highestRoomPrice = FDCHelper.ZERO;

		BigDecimal lowestBuildingPrice = FDCHelper.ZERO;
		BigDecimal lowestRoomPrice = FDCHelper.ZERO;

		// 将表格的第一行数据赋给
		if (tblRooms.getRow(0) != null
				&& tblRooms.getRow(0).getCell("buildingPrice").getValue() != null)
			lowestBuildingPrice = (BigDecimal) tblRooms.getRow(0).getCell(
			"buildingPrice").getValue();

		if (tblRooms.getRow(0) != null
				&& tblRooms.getRow(0).getCell("roomPrice").getValue() != null)
			lowestRoomPrice = (BigDecimal) tblRooms.getRow(0).getCell(
			"roomPrice").getValue();

		for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
			IRow row = this.tblRooms.getRow(i);
			BigDecimal sumAmount = 	KDTableTools.getBigDecimal(row.getCell("sumAmount")
					.getValue());
			if (sumAmount != null && row.getCell("room").getValue()!=null && !"".equals(row.getCell("room").getValue().toString())) {
				sumSum = sumSum.add(sumAmount);
			}
			BigDecimal buildingArea = (BigDecimal) row.getCell("buildingArea")
			.getValue();
			if (buildingArea != null && row.getCell("room").getValue()!=null && !"".equals(row.getCell("room").getValue().toString())) {
				sumBuildingArea = sumBuildingArea.add(buildingArea);
			}

			BigDecimal roomArea = (BigDecimal) row.getCell("roomArea")
			.getValue();
			if (roomArea != null && row.getCell("room").getValue()!=null && !"".equals(row.getCell("room").getValue().toString())) {
				sumRoomArea = sumRoomArea.add(roomArea);
			}

			BigDecimal buildingPrice = (BigDecimal) row
			.getCell("buildingPrice").getValue();

			if (buildingPrice != null
					&& buildingPrice.compareTo(highestBuildingPrice) > 0) {
				highestBuildingPrice = buildingPrice;
			}
			BigDecimal roomPrice = (BigDecimal) row.getCell("roomPrice")
			.getValue();
			if (roomPrice != null && roomPrice.compareTo(highestRoomPrice) > 0) {
				highestRoomPrice = roomPrice;
			}
			// 判断最低价
			if (buildingPrice != null
					&& buildingPrice.compareTo(lowestBuildingPrice) < 0) {
				lowestBuildingPrice = buildingPrice;
			}
			if (roomPrice != null && roomPrice.compareTo(lowestRoomPrice) < 0) {
				lowestRoomPrice = roomPrice;
			}
		}
		this.txtTotalPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtHighestBuildingPrice
		.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtHighestRoomPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtLowestBuildingPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtLowestRoomPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtBuildingAvgPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtRoomAvgPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);

		if (FDCHelper.MAX_TOTAL_VALUE2.compareTo(sumSum) < 0) {
			MsgBox.showInfo(this, "定价后总价超出系统限制，计算结果错误，请修改。");
			this.abort();
		}
		this.txtTotalPrice.setValue(sumSum.divide(FDCHelper.ONE,2,BigDecimal.ROUND_HALF_UP));
		this.txtTotalBuildingArea.setValue(sumBuildingArea.divide(FDCHelper.ONE,2,BigDecimal.ROUND_HALF_UP));
		this.txtTotalRoomArea.setValue(sumRoomArea.divide(FDCHelper.ONE,2,BigDecimal.ROUND_HALF_UP));
		this.txtHighestBuildingPrice.setValue(highestBuildingPrice.divide(FDCHelper.ONE,2,BigDecimal.ROUND_HALF_UP));
		this.txtHighestRoomPrice.setValue(highestRoomPrice.divide(FDCHelper.ONE,2,BigDecimal.ROUND_HALF_UP));

		this.txtLowestBuildingPrice.setValue(lowestBuildingPrice.divide(FDCHelper.ONE,2,BigDecimal.ROUND_HALF_UP));
		this.txtLowestRoomPrice.setValue(lowestRoomPrice.divide(FDCHelper.ONE,2,BigDecimal.ROUND_HALF_UP));

		if (sumBuildingArea != null
				&& sumBuildingArea.compareTo(FDCHelper.ZERO) != 0) {
			this.txtBuildingAvgPrice.setValue(sumSum.divide(sumBuildingArea, 2,
					BigDecimal.ROUND_HALF_UP));
		} else {
			this.txtBuildingAvgPrice.setValue(null);
		}


		if (sumRoomArea != null && sumRoomArea.compareTo(FDCHelper.ZERO) != 0) {
			this.txtRoomAvgPrice.setValue(sumSum.divide(sumRoomArea, 2,
					BigDecimal.ROUND_HALF_UP));
		} else {
			this.txtRoomAvgPrice.setValue(null);
		}
		int totalCount = 0;
		for(int i=0; i<tblRooms.getRowCount(); i++){
			if(tblRooms.getRow(i).getCell("room").getValue()!=null && !"".equals(tblRooms.getRow(i).getCell("room").getValue().toString())){
				totalCount++;
			}
		}
		this.txtTotalCount.setValue(new Integer(totalCount));
//		this.txtTotalCount.setValue(new Integer(this.tblRooms.getRowCount()));
		
	}
	//去掉原来的方法在下面的选择框里更新数据
	protected void tblRooms_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
			if (this.getOprtState().equals("VIEW")) {
				return;
			}
			int rowIndex = e.getRowIndex();
			int colIndex = e.getColIndex();
			if (rowIndex == -1 || colIndex == -1) {
				return;
			}
		}
	}

	protected void f7Building_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Building_dataChanged(e);
		//		if(isFirstLoad) return;

		this.tblRooms.removeRows();
		comboTopriceType.setSelectedItem(PriceTypeForPriceBillEnum.UseBuildingArea);
		Object[] tempBuilding = (Object[]) this.f7Building.getValue();
		if (tempBuilding == null) {
			this.priceSet = new BuildingPriceSetInfo();
			return;
		}

		BuildingInfo[] building = new BuildingInfo[tempBuilding.length];
		for (int i = 0; i < tempBuilding.length; i++) {
			building[i] = (BuildingInfo) tempBuilding[i];
		}

		Set idSet = new HashSet();
		for (int i = 0; i < building.length; i++) {
			idSet.add(building[i].getId().toString());
		}
		setPriceSetScheme(building);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("building.*");
		view.getSelector().add("buildUnit.name");
		view.getSelector().add("buildUnit.number");
		view.getSelector().add("buildUnit.seq");
		view.getSelector().add("buildingProperty.*");
		view.getSelector().add("roomModel.*");
		view.getSelector().add("sellOrder.*");
		view.getSelector().add("direction.*");
		view.getSelector().add("sight.*");
		//add by yaoweiwen 2009-12-26 start
		view.getSelector().add("noise.*");
		view.getSelector().add("eyeSignht.*");
		view.getSelector().add("productDetail.*");		
		//		view.getSelector().add("decoraStd.*");		
		//		view.getSelector().add("posseStd.*");	
		//end
		view.getSorter().add(new SorterItemInfo("displayname"));
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("building.id", idSet, CompareType.INCLUDE));
		
		//增加了对认购的筛选，xiaoao_liu		
		
		///filter.getFilterItems().add(new FilterItemInfo("sellState","'"+RoomSellStateEnum.SINCERPURCHASE_VALUE+"','"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.INIT_VALUE+"','"+RoomSellStateEnum.ONSHOW_VALUE+"','"+RoomSellStateEnum.KEEPDOWN_VALUE+"'",CompareType.INNER));
		Map entryMap = new HashMap();
		RoomPriceBillEntryCollection entrys = this.editData.getEntrys();
		for (int i = 0; i < entrys.size(); i++) {
			RoomPriceBillEntryInfo entry = entrys.get(i);
			entryMap.put(entry.getRoom().getId().toString(), entry);
		}
		RoomCollection rooms = RoomFactory.getRemoteInstance()
		.getRoomCollection(view);
		CRMHelper.sortCollection(rooms, "displayname", true);

		// 在未进行售前复核前，不允许定价
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			if (!room.isIsAreaAudited()) {
				// MsgBox.showInfo("该楼栋有房间未进行售前复核,不能进行定价!");
				if(isFirstLoad) continue;
				MsgBox.showInfo("所选楼栋中有房间未进行售前复核,不能进行定价");
				f7Building.setValue(null);
				return;
			}
		}

		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			RoomPriceBillEntryInfo entry = (RoomPriceBillEntryInfo) entryMap
			.get(room.getId().toString());
			if (entry == null) {
				// 首次新增
				entry = new RoomPriceBillEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				entry.setRoom(room);
				if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)
						|| room.getSellState().equals(
								RoomSellStateEnum.Purchase)
								|| room.getSellState().equals(RoomSellStateEnum.Sign)) {
					entry.setIsAdjust(false);
				} else {
					entry.setIsAdjust(true);
				}

				entry.setBuildingArea(room.getBuildingArea());
				entry.setRoomArea(room.getRoomArea());
				entry.setBuildPrice(room.getBuildPrice());
				entry.setRoomPrice(room.getRoomPrice());
			}
			addRowByEntry(entry);
		}
		setStatPrice();
		if (this.getOprtState().equals("VIEW")
				|| this.getOprtState().equals("FINDVIEW")) {
			this.tblRooms.getStyleAttributes().setLocked(true);
		}

		//按房间编号排序       这个排序没有对有负号的房间排序，所以注释掉，xiaoao_liu
//		orderByRoomNumber();
		
		//添加楼栋汇总行，且不能编辑
		addTotalRows();
	}

	private void updateTotalRows()
	{
		for(int rowIndex = tblRooms.getRowCount()-1; rowIndex >=0; --rowIndex)
		{
			IRow tmpRow = tblRooms.getRow(rowIndex);
			if(tmpRow.getUserObject() instanceof String)
			{
				Integer start = (Integer)tmpRow.getCell("unit").getUserObject();
				Integer end = (Integer)tmpRow.getCell("room").getUserObject();

				int startIndex = start.intValue();
				int endIndex = end.intValue();


				BigDecimal tmpSumAmt = FDCHelper.ZERO;
				BigDecimal tmpBuildArea = FDCHelper.ZERO;
				BigDecimal tmpRoomArea = FDCHelper.ZERO;

				for(int tmpIndex = startIndex; tmpIndex <= endIndex; ++tmpIndex)
				{
					tmpSumAmt = FDCHelper.add(tmpSumAmt, tblRooms.getRow(tmpIndex).getCell("sumAmount").getValue());
					tmpBuildArea = FDCHelper.add(tmpBuildArea, tblRooms.getRow(tmpIndex).getCell("buildingArea").getValue());
					tmpRoomArea = FDCHelper.add(tmpRoomArea, tblRooms.getRow(tmpIndex).getCell("roomArea").getValue());
				}

				tmpRow.getCell("sumAmount").setValue(tmpSumAmt);
				tmpRow.getCell("buildingArea").setValue(tmpBuildArea);

				tmpRow.getCell("roomArea").setValue(tmpRoomArea);
				tmpRow.getCell("buildingPrice").setValue(FDCHelper.divide(tmpSumAmt, tmpBuildArea));
				tmpRow.getCell("roomPrice").setValue(FDCHelper.divide(tmpSumAmt, tmpRoomArea));
			}
		}
	}

	private void addTotalRows()
	{
		if(tblRooms.getRowCount() > 0)
		{
			if(tblRooms.getRowCount() == 1)
			{
				//获取楼栋
				if(tblRooms.getRow(0).getCell("building").getValue() != null)
				{
					IRow tmpRow = tblRooms.addRow();
					tmpRow.getStyleAttributes().setLocked(true);
					tmpRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);


					String tmpStr = String.valueOf(tblRooms.getRow(0).getCell("building").getValue());
					tmpStr = tmpStr+"合计";
					tmpRow.getCell("building").setValue(tmpStr);
					tmpRow.setUserObject(tmpStr);

					//计算标准总价
					tmpRow.getCell("sumAmount").setValue(FDCHelper.toBigDecimal(tblRooms.getRow(0).getCell("sumAmount").getValue()));

					//计算建筑面积
					tmpRow.getCell("buildingArea").setValue(FDCHelper.toBigDecimal(tblRooms.getRow(0).getCell("buildingArea").getValue()));

					//计算建筑单价
					tmpRow.getCell("buildingPrice").setValue(FDCHelper.divide(tmpRow.getCell("sumAmount").getValue(), 
							tmpRow.getCell("buildingArea").getValue()));

					//计算套内面积
					tmpRow.getCell("roomArea").setValue(FDCHelper.toBigDecimal(tblRooms.getRow(0).getCell("roomArea").getValue()));

					//计算套内单价
					tmpRow.getCell("roomPrice").setValue(FDCHelper.divide(tmpRow.getCell("sumAmount").getValue(), 
							tmpRow.getCell("roomArea").getValue()));

					//用单元和预测放号列的userObj存储开始行和终止行
					tmpRow.getCell("unit").setUserObject(new Integer(0));
					tmpRow.getCell("room").setUserObject(new Integer(0));
				}
			}
			else
			{
				int flagIndex = 0;
				while(flagIndex <=tblRooms.getRowCount()-1)
				{
					IRow flagRow = tblRooms.getRow(flagIndex);

					if(flagRow.getCell("building").getValue() != null)
					{
						String flagBuild = String.valueOf(flagRow.getCell("building").getValue());

						int rowIndex = flagIndex+1;
						while(rowIndex <=tblRooms.getRowCount()-1)
						{
							IRow tmpRow = tblRooms.getRow(rowIndex);
							String tmpBuild = String.valueOf(tmpRow.getCell("building").getValue());
							if(tmpBuild.equals(flagBuild))
							{
								++rowIndex;
							}
							else
							{
								break;
							}
						}

						//求和
						IRow tmpSum = tblRooms.addRow(rowIndex);
						tmpSum.setUserObject(flagBuild+"合计");

						tmpSum.getStyleAttributes().setLocked(true);
						tmpSum.getStyleAttributes().setBackground(FDCTableHelper.totalColor);

						tmpSum.getCell("unit").setUserObject(new Integer(flagIndex));
						tmpSum.getCell("room").setUserObject(new Integer(rowIndex-1));

						BigDecimal tmpSumAmt = FDCHelper.ZERO;
						BigDecimal tmpBuildArea = FDCHelper.ZERO;
						BigDecimal tmpRoomArea = FDCHelper.ZERO;

						for(int tmpIndex = flagIndex; tmpIndex < rowIndex; ++tmpIndex)
						{
							tmpSumAmt = FDCHelper.add(tmpSumAmt, tblRooms.getRow(tmpIndex).getCell("sumAmount").getValue());
							tmpBuildArea = FDCHelper.add(tmpBuildArea, tblRooms.getRow(tmpIndex).getCell("buildingArea").getValue());
							tmpRoomArea = FDCHelper.add(tmpRoomArea, tblRooms.getRow(tmpIndex).getCell("roomArea").getValue());
						}

						tmpSum.getCell("building").setValue(flagBuild+"合计");
						tmpSum.getCell("sumAmount").setValue(tmpSumAmt);
						tmpSum.getCell("buildingArea").setValue(tmpBuildArea);

						tmpSum.getCell("roomArea").setValue(tmpRoomArea);
						tmpSum.getCell("buildingPrice").setValue(FDCHelper.divide(tmpSumAmt, tmpBuildArea));
						tmpSum.getCell("roomPrice").setValue(FDCHelper.divide(tmpSumAmt, tmpRoomArea));

						flagIndex = rowIndex+1;
					}
					else
					{
						++flagIndex;
					}
				}
			}
		}
	}

	/**
	 * 当f7中选到楼栋的时候，就找出楼栋对应的定价方案
	 * 
	 * @param building
	 */
	private void setPriceSetScheme(BuildingInfo[] building) {
		if (building == null) {
			return;
		}
		if (priceSetCollection != null)
			priceSetCollection.clear();

		/*
		 * 过来的楼栋有一些是有定价方案，有一些是没有的，分别考虑
		 */
		for (int i = 0; i < building.length; i++) {
			// 根据楼栋ID找出该楼栋所对应的定价方案
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("*");
			view.getSelector().add("building.sellProject.id");
			view.getSelector().add("priceSetEntry.*");
			view.getSelector().add("priceSetEntry.schemeEntry.*");

			view.getSorter().add(new SorterItemInfo("number"));
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", building[i].getId()
							.toString()));
			BuildingPriceSetCollection priceSets = null;

			try {
				priceSets = BuildingPriceSetFactory.getRemoteInstance()
				.getBuildingPriceSetCollection(view);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			// 如果该楼栋有对应的定价方案
			if (priceSets.size() >= 1) {
				this.priceSet = priceSets.get(0);
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("*");
				sels.add("entrys.*");

				PriceSetSchemeInfo scheme = null;
				try {
					scheme = PriceSetSchemeFactory.getRemoteInstance()
					.getPriceSetSchemeInfo(
							new ObjectUuidPK(priceSet.getPriceScheme()
									.getId()), sels);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
				this.priceSet.setPriceScheme(scheme);

				this.priceSet.setBuilding(building[i]);
				// 存放进集合
				priceSetCollection.add(priceSet);

			} // 如果没有对应的定价方案，则存进楼栋信息进去
			else {
				BuildingPriceSetInfo temp = new BuildingPriceSetInfo();
				temp.setBuilding(building[i]);
				priceSetCollection.add(temp);
			}
		}

		/*
		 * Set idSet = new HashSet(); for(int i=0;i<building.length;i++) {
		 * idSet.add(building[i].getId().toString()); }
		 * 
		 * EntityViewInfo view = new EntityViewInfo();
		 * view.getSelector().add("*");
		 * view.getSelector().add("building.sellProject.id");
		 * view.getSelector().add("priceSetEntry.*");
		 * view.getSelector().add("priceSetEntry.schemeEntry.*");
		 * 
		 * view.getSorter().add(new SorterItemInfo("number")); FilterInfo filter =
		 * new FilterInfo(); view.setFilter(filter);
		 * filter.getFilterItems().add( new
		 * FilterItemInfo("building.id",idSet,CompareType.INCLUDE));
		 * BuildingPriceSetCollection priceSets = null;
		 * 
		 * try { priceSets = BuildingPriceSetFactory.getRemoteInstance()
		 * .getBuildingPriceSetCollection(view); } catch (BOSException e) {
		 * e.printStackTrace(); } if (priceSets.size() >= 1) { for (int i = 0; i <
		 * priceSets.size(); i++) { this.priceSet = priceSets.get(i);
		 * SelectorItemCollection sels = new SelectorItemCollection();
		 * sels.add("*"); sels.add("entrys.*");
		 * 
		 * PriceSetSchemeInfo scheme = null; try { scheme =
		 * PriceSetSchemeFactory.getRemoteInstance() .getPriceSetSchemeInfo( new
		 * ObjectUuidPK(priceSet.getPriceScheme() .getId()), sels); } catch
		 * (EASBizException e) { e.printStackTrace(); } catch (BOSException e) {
		 * e.printStackTrace(); } this.priceSet.setPriceScheme(scheme);
		 * 
		 * this.priceSet.setBuilding(building[i]); //存放进集合
		 * priceSetCollection.add(priceSet); } } else { //多个楼栋 for (int i = 0; i <
		 * building.length; i++) { BuildingPriceSetInfo temp = new
		 * BuildingPriceSetInfo(); temp.setBuilding(building[i]);
		 * priceSetCollection.add(temp); } }
		 */
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAddNew_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		if (this.editData.getAuditor() != null) {
			MsgBox.showInfo("定价单已经审核不能修改!");
			return;
		}
		for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
			IRow row = this.tblRooms.getRow(i);
			setRowLock(row);
		}
		this.btnAddRoom.setEnabled(true);
		this.btnAddRoomByList.setVisible(true);
		this.btnAddRoomByList.setEnabled(true);
		this.btnDelRoom.setEnabled(true);
		this.btnImportPrice.setEnabled(true);
		this.btnScheme.setEnabled(true);
		super.actionEdit_actionPerformed(arg0);
	}

	private void setRowLock(IRow row) {
		if(!(row.getUserObject() instanceof RoomPriceBillEntryInfo)) return;
		
		RoomPriceBillEntryInfo entry = (RoomPriceBillEntryInfo) row.getUserObject();
		
		RoomInfo room = entry.getRoom();
		boolean isAdjust = entry.isIsAdjust();
		if (!isAdjust) {
			row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		}

		// row.getCell("building").setValue(room.getBuilding().getName());
		// row.getCell("unit").setValue(room.getBuildUnit());

		row.getCell("building").getStyleAttributes().setLocked(true);
		row.getCell("unit").getStyleAttributes().setLocked(true);
		
		row.getCell("roomPrice").getStyleAttributes().setLocked(true);
		row.getCell("buildingPrice").getStyleAttributes().setLocked(true);
		
		PriceTypeForPriceBillEnum priceTypeEnum = entry.getPriceType();
		if (priceTypeEnum!=null && priceTypeEnum.equals(PriceTypeForPriceBillEnum.UseRoomArea)) {
			if (isAdjust) {
				row.getCell("roomPrice").getStyleAttributes().setLocked(false);
			} else {
				row.getCell("roomPrice").getStyleAttributes().setLocked(true);
			}
		} else if(priceTypeEnum!=null && priceTypeEnum.equals(PriceTypeForPriceBillEnum.UseBuildingArea)) {
			if (isAdjust) {
				row.getCell("buildingPrice").getStyleAttributes().setLocked(false);
			} else {
				row.getCell("buildingPrice").getStyleAttributes().setLocked(true);
			}
		}else{
			if (isAdjust) {
				row.getCell("sumAmount").getStyleAttributes().setLocked(false);
			} else {
				row.getCell("sumAmount").getStyleAttributes().setLocked(true);
			}
		}
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void btnImportPrice_actionPerformed(ActionEvent e)
	throws Exception {
		super.btnImportPrice_actionPerformed(e);
		String fileName = SHEHelper.showExcelSelectDlg(this);
		this.tblRooms.getColumn("priceType").getStyleAttributes().setLocked(true);
		int count = SHEHelper.importExcelToTable2(fileName, this.tblRooms, 1, 3);
		this.tblRooms.getColumn("priceType").getStyleAttributes().setLocked(false);
		MsgBox.showInfo("已经成功导入" + count + "条数据!");
		for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
			IRow row = this.tblRooms.getRow(i);
			
			//跳过合计行
			if(row.getCell("priceType").getValue() == null)
			{
				continue;
			}
			
			updateRowData(row);
		}

		//添加汇总行
		deleteTotalRows();
		addTotalRows();
	}

	protected KDTable getDetailTable() {
		// TODO 自动生成方法存根
		return null;
	}

	//当逐行编辑定价类型表格所作的操作
	public void updateRowForIndex(Object obj) throws BOSException, EASBizException
	{
		IRow changeRow = this.tblRooms.getRow(tblRooms.getSelectManager().getActiveRowIndex());

		if(obj.equals(PriceTypeForPriceBillEnum.UseBuildingArea) && obj!=null){
			BigDecimal area = (BigDecimal)changeRow.getCell("buildingArea").getValue();
			BigDecimal price =(BigDecimal)changeRow.getCell("buildingPrice").getValue();
			BigDecimal roomArea =(BigDecimal)changeRow.getCell("roomArea").getValue();
			if(area!=null && price!=null){
				BigDecimal sumAmount=area.multiply(price);
				BigDecimal roomPrice = sumAmount.divide(roomArea,2,BigDecimal.ROUND_HALF_UP);
				changeRow.getCell("sumAmount").setValue(sumAmount.setScale(2,BigDecimal.ROUND_HALF_UP));
				changeRow.getCell("roomPrice").setValue(roomPrice);
				changeRow.getCell("roomPrice").getStyleAttributes().setLocked(true);
				changeRow.getCell("buildingPrice").getStyleAttributes().setLocked(false);
				changeRow.getCell("sumAmount").getStyleAttributes().setLocked(true);
				setStatPrice();
			}
		}
		if(obj.equals(PriceTypeForPriceBillEnum.UseRoomArea) && obj!=null){
			BigDecimal area = (BigDecimal)changeRow.getCell("roomArea").getValue();
			BigDecimal price =(BigDecimal)changeRow.getCell("roomPrice").getValue();
			BigDecimal buildingArea =(BigDecimal)changeRow.getCell("buildingArea").getValue();
			if(area!=null && price!=null){
				BigDecimal sumAmount=area.multiply(price);
				BigDecimal buildingPrice = sumAmount.divide(buildingArea,2,BigDecimal.ROUND_HALF_UP);
				changeRow.getCell("sumAmount").setValue(sumAmount.setScale(2,BigDecimal.ROUND_HALF_UP));
				changeRow.getCell("buildingPrice").setValue(buildingPrice);
				changeRow.getCell("roomPrice").getStyleAttributes().setLocked(false);
				changeRow.getCell("buildingPrice").getStyleAttributes().setLocked(true);
				changeRow.getCell("sumAmount").getStyleAttributes().setLocked(true);
				setStatPrice();			
			}
		}
		if(obj.equals(PriceTypeForPriceBillEnum.UseStandPrice) && obj!=null){
			Object Amount = null; 
			Amount = (Object)changeRow.getCell("sumAmount").getValue();
			BigDecimal sumAmount =KDTableTools.getBigDecimal(Amount);
			BigDecimal buildingPrice =(BigDecimal)changeRow.getCell("buildingArea").getValue();
			if(sumAmount!=null && buildingPrice!=null){ 
				changeRow.getCell("buildingPrice").setValue(sumAmount.divide(buildingPrice,2,BigDecimal.ROUND_HALF_UP));
				changeRow.getCell("sumAmount").getStyleAttributes().setLocked(false);
				changeRow.getCell("buildingPrice").getStyleAttributes().setLocked(true);
				changeRow.getCell("roomPrice").getStyleAttributes().setLocked(true);
				setStatPrice();
			}
		}

	}
	//当在表头编辑定价类型时
	public void updateRowForAll(Object obj) throws EASBizException, BOSException
	{	
		//for(int i=0;i<PriceTypeForPriceBillEnum.getEnumList().size();i++){
		if(obj.equals(PriceTypeForPriceBillEnum.UseBuildingArea) && obj!=null){
			for(int j=0;j<tblRooms.getRowCount();j++){
				BigDecimal area = (BigDecimal) tblRooms.getRow(j).getCell("buildingArea").getValue();
				BigDecimal price =(BigDecimal) tblRooms.getRow(j).getCell("buildingPrice").getValue();
				if(area!=null && price!=null){
					BigDecimal sumAmount=area.multiply(price).setScale(2,BigDecimal.ROUND_HALF_UP);
					tblRooms.getRow(j).getCell("sumAmount").setValue(sumAmount);
					
					//添加汇总行
					if(tblRooms.getRow(j).getUserObject() instanceof String)
					{
						continue;
					}
					RoomPriceBillEntryInfo roomEntry  = (RoomPriceBillEntryInfo) tblRooms.getRow(j).getUserObject();
					if(roomEntry.getBoolean("isadjust")==false)
					{
						tblRooms.getRow(j).getCell("buildingPrice").getStyleAttributes().setLocked(true);
					}else{
						tblRooms.getRow(j).getCell("buildingPrice").getStyleAttributes().setLocked(false);
					}
					tblRooms.getRow(j).getCell("roomPrice").getStyleAttributes().setLocked(true);
					tblRooms.getRow(j).getCell("sumAmount").getStyleAttributes().setLocked(true);
					setStatPrice();
				}				
			}
		}

		if(obj.equals(PriceTypeForPriceBillEnum.UseRoomArea) && obj!=null){
			for(int j=0;j<tblRooms.getRowCount();j++){
				BigDecimal area = (BigDecimal) tblRooms.getRow(j).getCell("roomArea").getValue();
				BigDecimal price =(BigDecimal) tblRooms.getRow(j).getCell("roomPrice").getValue();
				if(area!=null && price!=null){
					BigDecimal sumAmount=area.multiply(price).setScale(2,BigDecimal.ROUND_HALF_UP);
					tblRooms.getRow(j).getCell("sumAmount").setValue(sumAmount);
					
					//添加汇总行
					if(tblRooms.getRow(j).getUserObject() instanceof String)
					{
						continue;
					}
					
					RoomPriceBillEntryInfo roomEntry  = (RoomPriceBillEntryInfo) tblRooms.getRow(j).getUserObject();
					if(roomEntry.getBoolean("isadjust")==false)
					{
						tblRooms.getRow(j).getCell("roomPrice").getStyleAttributes().setLocked(true);
					}
					else{
						tblRooms.getRow(j).getCell("roomPrice").getStyleAttributes().setLocked(false);
					}
					tblRooms.getRow(j).getCell("buildingPrice").getStyleAttributes().setLocked(true);
					tblRooms.getRow(j).getCell("sumAmount").getStyleAttributes().setLocked(true);
					setStatPrice();
				}
			}
		}


		if(obj.equals(PriceTypeForPriceBillEnum.UseStandPrice) && obj!=null){
			for(int j=0;j<tblRooms.getRowCount();j++){
				BigDecimal sumAmount = (BigDecimal) KDTableTools.getBigDecimal(tblRooms.getRow(j).getCell("sumAmount").getValue());
				BigDecimal buildingArea =(BigDecimal) tblRooms.getRow(j).getCell("buildingArea").getValue();
				BigDecimal roomarea = (BigDecimal) tblRooms.getRow(j).getCell("roomArea").getValue();
				if(sumAmount!=null && buildingArea!=null){ 
					tblRooms.getRow(j).getCell("buildingPrice").setValue(sumAmount.divide(buildingArea,2,BigDecimal.ROUND_HALF_UP));
					tblRooms.getRow(j).getCell("roomPrice").setValue(sumAmount.divide(roomarea,2,BigDecimal.ROUND_HALF_UP));
					
					//添加汇总行
					if(tblRooms.getRow(j).getUserObject() instanceof String)
					{
						continue;
					}
					
					RoomPriceBillEntryInfo roomEntry  = (RoomPriceBillEntryInfo) tblRooms.getRow(j).getUserObject();
					if(roomEntry.getBoolean("isadjust")==false)
					{
						tblRooms.getRow(j).getCell("sumAmount").getStyleAttributes().setLocked(true);
					}else{
						tblRooms.getRow(j).getCell("sumAmount").getStyleAttributes().setLocked(false);
					}
					tblRooms.getRow(j).getCell("buildingPrice").getStyleAttributes().setLocked(true);
					tblRooms.getRow(j).getCell("roomPrice").getStyleAttributes().setLocked(true);
					setStatPrice();
				}
			}
		}

	}

	//根据选择 的 COMBOX进行 相应 设置
	public void InitTableRoomsPriceTypeCell()
	{
		KDTableTools.setHideFields(this.tblRooms,new String[]{"isCalByRoom"}, true);
		KDComboBox comboPriceType = new KDComboBox();
		comboPriceType.addItem(PriceTypeForPriceBillEnum.UseRoomArea);
		comboPriceType.addItem(PriceTypeForPriceBillEnum.UseBuildingArea);
		comboPriceType.addItem(PriceTypeForPriceBillEnum.UseStandPrice);
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(comboPriceType);
		this.tblRooms.getColumn("priceType").setEditor(cellEditor);
		com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender kdtEntrysOVR = new ObjectValueRender();
		kdtEntrysOVR
		.setFormat(new com.kingdee.bos.ctrl.extendcontrols.BizDataFormat(
				"$number$"));
		this.tblRooms.getColumn("priceType").setRenderer(kdtEntrysOVR);
		comboPriceType.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				Object obj = e.getItem();
				try {
					if(e.getStateChange()==1)
							updateRowForIndex(obj);
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (EASBizException e2) {
					e2.printStackTrace();
				}
			}
		}
		);
	}
	//根据选择的定价类型进行调整
	public void comboTopriceType_itemStateChanged(java.awt.event.ItemEvent e) throws EASBizException, BOSException{
		if(e.getStateChange()!=1) return;
		
		for(int i=0;i<tblRooms.getRowCount();i++){
			if(tblRooms.getRow(i).getUserObject() instanceof String)
			{
				continue;
			}
			tblRooms.getCell(i, "priceType").setValue(e.getItem());
			RoomPriceBillEntryInfo roomEntry  = (RoomPriceBillEntryInfo) tblRooms.getRow(i).getUserObject();
			if(roomEntry.getBoolean("isadjust")==false)
			{
				tblRooms.getRow(i).getCell("roomPrice").getStyleAttributes().setLocked(true);
				tblRooms.getRow(i).getCell("buildingPrice").getStyleAttributes().setLocked(true);
				tblRooms.getRow(i).getCell("sumAmount").getStyleAttributes().setLocked(true);
				tblRooms.getRow(i).getCell("roomArea").getStyleAttributes().setLocked(true);
				tblRooms.getRow(i).getCell("buildingArea").getStyleAttributes().setLocked(true);
			}
		}
		updateRowForAll(e.getItem());
	}
	
	
	//按楼栋 单元 房间编码 排序
	private void orderByRoomNumber() {
		List rows = this.tblRooms.getBody().getRows();
		Collections.sort(rows, new TableCellCompare(this.tblRooms.getColumnIndex("building"),this.tblRooms.getColumnIndex("unit"),
				this.tblRooms.getColumnIndex("room"), KDTSortManager.SORT_ASCEND));
		this.tblRooms.setRefresh(true);
	}
	
	
	
	
	
	class TableCellCompare implements Comparator
	{
		int bulidIndex,unitIndex,roomIndex;
		int sortType;
		Collator comparator = Collator.getInstance(Locale.getDefault());
		
		public TableCellCompare(int bIndex,int uIndex,int rIndex,int sType){
			bulidIndex = bIndex;
			unitIndex = uIndex;
			roomIndex = rIndex;
			sortType = sType;
		}
		
		
		public int compare(Object kdtRow1, Object kdtRow2) {
			int result = doCompare(kdtRow1, kdtRow2);
			if (sortType == ISortManager.SORT_ASCEND)
			{
				return result;
			}
			else
			{
				if (result < 0)
				{
					return 1;
				}
				else if (result > 0)
				{
					return -1;
				}
				else
				{
					return result;
				}
			}
		}
		
		
		public int doCompare(Object kdtRow1, Object kdtRow2)
		{
			String o1 = "", o2 = "";
			KDTCell cellBuild = ((KDTRow) kdtRow1).getCell(bulidIndex);
			KDTCell cellUnit = ((KDTRow) kdtRow1).getCell(unitIndex);
			KDTCell roomUnit = ((KDTRow) kdtRow1).getCell(roomIndex);
			if(cellBuild!=null) 
				o1 += cellBuild.getValue(); 
			if(cellUnit!=null)
				o1 += cellUnit.getValue();
			if(roomUnit!=null)
				o1 += roomUnit.getValue();
			cellBuild = ((KDTRow) kdtRow2).getCell(bulidIndex);
			cellUnit = ((KDTRow) kdtRow2).getCell(unitIndex);
			roomUnit = ((KDTRow) kdtRow2).getCell(roomIndex);
			if(cellBuild!=null) 
				o2 += cellBuild.getValue(); 
			if(cellUnit!=null)
				o2 += cellUnit.getValue();
			if(roomUnit!=null)
				o2 += roomUnit.getValue();

			// 使用Collator实现了中文排序;但可能会有部分汉字不会按拼音排序
			// 这是因为:
			// 在简体中文中我们使用比较多的字符集是 GB2312-80，简称为 GB2312，这个字符集包含了目前最常用的汉字共计 6736 个。其中的汉字分为两大类：
			// 常用汉字,次常用汉字;常用汉字按照汉语拼音来排序，而次常用汉字按照笔画部首进行排序。
			return comparator.compare(o1, o2);

		}
		
		
		
		
		
	}
	
	
	
}