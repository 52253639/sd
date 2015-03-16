/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomOrderStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellOrderFactory;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.fdc.sellhouse.SellOrderPlanEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SellOrderPlanEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SellOrderRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SellOrderRoomEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public class SellOrderEditUI extends AbstractSellOrderEditUI
{
	private static final Logger logger = CoreUIObject
			.getLogger(SellOrderEditUI.class);

	/**
	 * output class constructor
	 */
	public SellOrderEditUI() throws Exception
	{
		super();
	}

	public void loadFields()
	{
		super.loadFields();
		SHEHelper.setNumberEnabled(editData,getOprtState(),txtNumber);
		SellOrderInfo sellOrder = (SellOrderInfo) this.editData;
		this.txtProjectNumber.setText(sellOrder.getProject().getNumber());
		this.txtProjectName.setText(sellOrder.getProject().getName());
		this.txtNumber.setText(sellOrder.getNumber());
		this.txtName.setText(sellOrder.getName());
		this.pkOrderDate.setValue(sellOrder.getOrderDate());
		this.txtDes.setText(sellOrder.getDescription());
		SellOrderRoomEntryCollection roomEntrys = sellOrder.getRoomEntrys();
		this.tblRooms.removeRows();
		for (int i = 0; i < roomEntrys.size(); i++)
		{
			SellOrderRoomEntryInfo roomEntry = roomEntrys.get(i);
			RoomInfo room = roomEntry.getRoom();
			IRow row = this.tblRooms.addRow();
			row.setUserObject(roomEntry);
			row.getCell("building").setValue(room.getBuilding());
			row.getCell("unit").setValue(room.getBuildUnit());
			row.getCell("roomNumber").setValue(room.getDisplayName());
			row.getCell("roomNo").setValue(room.getRoomNo());
			// 增加建筑面积（buildingArea）、套内面积（roomArea）、建筑单价（buildPrice）、
			// 套内单价（roomPrice）、是否按套内定价（isCalByRoomArea） alert by xwb
			row.getCell("buildingArea").setValue(room.getBuildingArea());
			row.getCell("roomArea").setValue(room.getRoomArea());
			row.getCell("buildPrice").setValue(room.getBuildPrice());
			row.getCell("roomPrice").setValue(room.getRoomPrice());
			row.getCell("isCalByRoomArea").setValue(
					new Boolean(room.isIsCalByRoomArea()));
			row.getCell("state").setValue(roomEntry.getState());
		}
		this.setTotalAvgPrice();
		// xwb
		setRoomCountSum();
		this.txtPlanRevAmount.setValue(sellOrder.getPlanRevAmount());

		SellOrderPlanEntryCollection planEntrys = sellOrder.getPlanEntrys();
		this.tblPlan.removeRows();
		for (int i = 0; i < planEntrys.size(); i++)
		{
			SellOrderPlanEntryInfo planEntry = planEntrys.get(i);
			IRow row = this.tblPlan.addRow();
			row.setUserObject(planEntry);
			row.getCell("date").setValue(planEntry.getPlanDate());
			row.getCell("pro").setValue(planEntry.getPro());
			row.getCell("des").setValue(planEntry.getDescription());
			updateRowPlanRev(row);
			updateRowActRev(row);
		}
		if (this.getOprtState().equals("VIEW"))
		{
			this.btnAddRoom.setEnabled(false);
			this.actionAddRoomByList.setEnabled(false);
			this.btnRemoveRoom.setEnabled(false);
			this.btnAddPlan.setEnabled(false);
			this.btnRemovePlan.setEnabled(false);
			this.tblPlan.getStyleAttributes().setLocked(true);
		}
		
		if(this.getOprtState().equals("EDIT")){
			if(sellOrder.isIsEnabled()){//如果已执行,不让删除房间
				this.btnRemoveRoom.setEnabled(false);
			}
		}
	}

	private void updateRowPlanRev(IRow row)
	{
		BigDecimal sum = this.txtPlanRevAmount.getBigDecimalValue();
		if (sum == null)
		{
			sum = FDCHelper.ZERO;
		}
		BigDecimal pro = (BigDecimal) row.getCell("pro").getValue();
		if (pro == null)
		{
			pro = FDCHelper.ZERO;
		}
		BigDecimal planAmount = sum.multiply(pro).divide(new BigDecimal("100"),
				2, BigDecimal.ROUND_HALF_UP);
		row.getCell("planAmount").setValue(planAmount);
	}

	private void updateRowActRev(IRow row)
	{
		Set idSet = getRoomIdSet();
		if (idSet.size() == 0)
		{
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("billStatus", BillStatusEnum.SAVE,
						CompareType.NOTEQUALS));

		filter.getFilterItems().add(
				new FilterItemInfo("fdcReceiveBill.room.id", idSet,
						CompareType.INCLUDE));
		Date revDate = (Date) row.getCell("date").getValue();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("bizDate", revDate,
								CompareType.LESS_EQUALS));
		try
		{
			ReceivingBillCollection revs = ReceivingBillFactory
					.getRemoteInstance().getReceivingBillCollection(view);
			BigDecimal actRevAmount = FDCHelper.ZERO;
			for (int j = 0; j < revs.size(); j++)
			{
				ReceivingBillInfo rev = revs.get(j);
				BigDecimal amount = rev.getAmount();
				actRevAmount = actRevAmount.add(amount);
			}
			row.getCell("actAmount").setValue(actRevAmount);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
	}

	private Set getRoomIdSet()
	{
		Set idSet = new HashSet();
		for (int i = 0; i < this.tblRooms.getRowCount(); i++)
		{
			IRow row = this.tblRooms.getRow(i);
			SellOrderRoomEntryInfo roomEntry = (SellOrderRoomEntryInfo) row
					.getUserObject();
			RoomInfo room = roomEntry.getRoom();
			idSet.add(room.getId().toString());
		}
		return idSet;
	}

	private void setTotalAvgPrice()
	{
		BigDecimal sumSum = FDCHelper.ZERO;
		BigDecimal sumBuildingArea = FDCHelper.ZERO;
		BigDecimal sumRoomArea = FDCHelper.ZERO;
		for (int i = 0; i < this.tblRooms.getRowCount(); i++)
		{
			IRow row = this.tblRooms.getRow(i);
			SellOrderRoomEntryInfo roomEntry = (SellOrderRoomEntryInfo) row
					.getUserObject();
			RoomInfo room = roomEntry.getRoom();
			BigDecimal sumAmount = room.getStandardTotalAmount();
			if (sumAmount != null)
			{
				sumSum = sumSum.add(sumAmount);
			}
			BigDecimal buildingArea = room.getBuildingArea();
			if (buildingArea != null)
			{
				sumBuildingArea = sumBuildingArea.add(buildingArea);
			}

			BigDecimal roomArea = room.getRoomArea();
			if (roomArea != null)
			{
				sumRoomArea = sumRoomArea.add(roomArea);
			}
		}

		this.txtTotalPrice.setValue(sumSum);
//		this.txtPlanRevAmount.setValue(sumSum);
		this.txtBuildingAreaSum.setValue(sumBuildingArea);
		if (sumBuildingArea != null
				&& sumBuildingArea.compareTo(FDCHelper.ZERO) != 0)
		{
			this.txtBuildingAvgPrice.setValue(sumSum.divide(sumBuildingArea, 2,
					BigDecimal.ROUND_HALF_UP));
			// 总建筑面积 xwb
		}
		this.txtRoomAreaSum.setValue(sumRoomArea);
		if (sumRoomArea != null && sumRoomArea.compareTo(FDCHelper.ZERO) != 0)
		{
			this.txtRoomAvgPrice.setValue(sumSum.divide(sumRoomArea, 2,
					BigDecimal.ROUND_HALF_UP));
			// 总套内面积 xwb
		}
	}

	// 总套数 xwb
	private void setRoomCountSum()
	{
		Double count = new Double("0");
		if (this.tblRooms.getRowCount() > 0)
			count = new Double(String.valueOf(this.tblRooms.getRowCount()));
		this.txtRoomCountSum.setValue(count);
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
		
		prmtRoomSelect.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSelectByListQuery");
		prmtRoomSelect.setSelectorCollection(selectors);
		prmtRoomSelect.setEntityViewInfo(view);
		prmtRoomSelect.setEnabledMultiSelection(true);
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

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
		SellOrderInfo sellOrder = (SellOrderInfo) this.editData;
		
		super.actionEdit_actionPerformed(e);
		this.btnAddRoom.setEnabled(true);
		this.btnRemoveRoom.setEnabled(true);
		this.btnAddPlan.setEnabled(true);
		this.btnRemovePlan.setEnabled(true);
		this.tblPlan.getColumn("date").getStyleAttributes().setLocked(false);
		this.tblPlan.getColumn("pro").getStyleAttributes().setLocked(false);
		this.tblPlan.getColumn("des").getStyleAttributes().setLocked(false);
		
		if (sellOrder.isIsEnabled())
		{
			this.btnRemoveRoom.setEnabled(false);
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionAddNew_actionPerformed(e);
		this.btnAddRoom.setEnabled(true);
		this.btnRemoveRoom.setEnabled(true);
		this.btnAddPlan.setEnabled(true);
		this.btnRemovePlan.setEnabled(true);
		this.tblPlan.getColumn("date").getStyleAttributes().setLocked(false);
		this.tblPlan.getColumn("pro").getStyleAttributes().setLocked(false);
		this.tblPlan.getColumn("des").getStyleAttributes().setLocked(false);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
		SellOrderInfo sellOrder = (SellOrderInfo) this.editData;
		sellOrder.setNumber(this.txtNumber.getText());
		sellOrder.setName(this.txtName.getText());
		sellOrder.setOrderDate((Date) this.pkOrderDate.getValue());
		sellOrder.getRoomEntrys().clear();
		for (int j = 0; j < this.tblRooms.getRowCount(); j++)
		{
			SellOrderRoomEntryInfo roomEntry = (SellOrderRoomEntryInfo) this.tblRooms
					.getRow(j).getUserObject();
			sellOrder.getRoomEntrys().add(roomEntry);
		}
		sellOrder.getPlanEntrys().clear();
		for (int j = 0; j < this.tblPlan.getRowCount(); j++)
		{
			SellOrderPlanEntryInfo planEntry = (SellOrderPlanEntryInfo) this.tblPlan
					.getRow(j).getUserObject();
			IRow row = this.tblPlan.getRow(j);
			planEntry.setPlanDate((Date) row.getCell("date").getValue());
			planEntry.setPro((BigDecimal) row.getCell("pro").getValue());
			planEntry.setDescription((String) row.getCell("des").getValue());
			sellOrder.getPlanEntrys().add(planEntry);
		}
		sellOrder.setPlanRevAmount(this.txtPlanRevAmount.getBigDecimalValue());
		sellOrder.setDescription(this.txtDes.getText());
	}

	// 设置表格数据格式
	private void setTblColNum()
	{
		IColumn column = tblRooms.getColumn("buildingArea");
		column.getStyleAttributes().setNumberFormat("0.00");
		column = tblRooms.getColumn("roomArea");
		column.getStyleAttributes().setNumberFormat("0.00");
		column = tblRooms.getColumn("buildPrice");
		column.getStyleAttributes().setNumberFormat("0.00");
		column = tblRooms.getColumn("roomPrice");
		column.getStyleAttributes().setNumberFormat("0.00");
	}

	public void onLoad() throws Exception
	{
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionRemove.setVisible(false);
		txtProjectNumber.setEnabled(false);
		txtProjectName.setEnabled(false);
		// xwb tbl "是否按套内定价"表格设置
		this.tblRooms.checkParsed();
		tblRooms.getStyleAttributes().setLocked(false);
		IRow headRow = this.tblRooms.getHeadRow(0);
		IColumn headColumn = tblRooms.addColumn();
		headColumn.setKey("isCalByRoomArea");
		ICell headCell = headRow.getCell("isCalByRoomArea");
		headCell.setValue("是否按套内定价");
		// KDCheckBox box =new KDCheckBox();
		KDComboBox box = new KDComboBox();
		ICellEditor cellEditor = new KDTDefaultCellEditor(box);
		headColumn.setEditor(cellEditor);
		// 设置表格格式
		setTblColNum();
		this.tblRooms.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		this.tblRooms.getStyleAttributes().setLocked(true);
		this.tblPlan.checkParsed();
		this.tblPlan.getSelectManager().setSelectMode(
				KDTSelectManager.CELL_SELECT);
		this.tblPlan.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblPlan.getColumn("date").getStyleAttributes().setLocked(false);
		String formatString = "yyyy-MM-dd";
		this.tblPlan.getColumn("date").getStyleAttributes().setNumberFormat(
				formatString);
		KDDatePicker pk = new KDDatePicker();
		pk.setRequired(true);
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		this.tblPlan.getColumn("date").setEditor(dateEditor);
		this.tblPlan.getColumn("date").getStyleAttributes().setBackground(
				FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblPlan.getColumn("pro").getStyleAttributes().setLocked(false);
		this.tblPlan.getColumn("pro").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		this.tblPlan.getColumn("pro").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		formattedTextField.setRequired(true);
		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(
				formattedTextField);
		this.tblPlan.getColumn("pro").setEditor(numberEditor);
		this.tblPlan.getColumn("pro").getStyleAttributes().setBackground(
				FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblPlan.getColumn("planAmount").getStyleAttributes().setLocked(
				true);
		this.tblPlan.getColumn("planAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPlan.getColumn("planAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPlan.getColumn("actAmount").getStyleAttributes()
				.setLocked(true);
		this.tblPlan.getColumn("actAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPlan.getColumn("actAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPlan.getColumn("des").getStyleAttributes().setLocked(false);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblPlan.getColumn("des").setEditor(txtEditor);

		this.txtBuildingAvgPrice.setRemoveingZeroInDispaly(false);
		this.txtBuildingAvgPrice.setRemoveingZeroInEdit(false);
		this.txtBuildingAvgPrice.setNegatived(false);
		this.txtBuildingAvgPrice.setPrecision(2);
		this.txtBuildingAvgPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingAvgPrice.setSupportedEmpty(true);

		this.txtRoomAvgPrice.setRemoveingZeroInDispaly(false);
		this.txtRoomAvgPrice.setRemoveingZeroInEdit(false);
		this.txtRoomAvgPrice.setNegatived(false);
		this.txtRoomAvgPrice.setPrecision(2);
		this.txtRoomAvgPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRoomAvgPrice.setSupportedEmpty(true);

		this.txtTotalPrice.setRemoveingZeroInDispaly(false);
		this.txtTotalPrice.setRemoveingZeroInEdit(false);
		this.txtTotalPrice.setNegatived(false);
		this.txtTotalPrice.setPrecision(2);
		this.txtTotalPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtTotalPrice.setSupportedEmpty(true);

		this.txtPlanRevAmount.setRemoveingZeroInDispaly(false);
		this.txtPlanRevAmount.setRemoveingZeroInEdit(false);
		this.txtPlanRevAmount.setNegatived(false);
		this.txtPlanRevAmount.setPrecision(2);
		this.txtPlanRevAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtPlanRevAmount.setSupportedEmpty(true);
		this.txtPlanRevAmount.setRequired(true);
		// alter by xwb
		this.txtRoomAreaSum.setRemoveingZeroInDispaly(false);
		this.txtRoomAreaSum.setRemoveingZeroInEdit(false);
		this.txtRoomAreaSum.setNegatived(false);
		this.txtRoomAreaSum.setPrecision(2);
		this.txtRoomAreaSum.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRoomAreaSum.setSupportedEmpty(true);
		this.txtRoomAreaSum.setEnabled(false);

		this.txtRoomCountSum.setRemoveingZeroInDispaly(false);
		this.txtRoomCountSum.setRemoveingZeroInEdit(false);
		this.txtRoomCountSum.setNegatived(false);
		this.txtRoomCountSum.setPrecision(2);
		this.txtRoomCountSum.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRoomCountSum.setSupportedEmpty(true);
		this.txtRoomCountSum.setEnabled(false);

		this.txtBuildingAreaSum.setRemoveingZeroInDispaly(false);
		this.txtBuildingAreaSum.setRemoveingZeroInEdit(false);
		this.txtBuildingAreaSum.setNegatived(false);
		this.txtBuildingAreaSum.setPrecision(2);
		this.txtBuildingAreaSum.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingAreaSum.setSupportedEmpty(true);
		this.txtBuildingAreaSum.setEnabled(false);

		// 增加部分项的字体颜色显示
		Font font = new Font("SimSun", Font.BOLD, 15);
		Color color = Color.BLUE;
		// this.contOrderDate.getBoundLabel().setFont(font);
		this.contTotalPrice.getBoundLabel().setFont(font);
		this.contBuildingAvgPrice.getBoundLabel().setFont(font);
		this.contRoomAvgPrice.getBoundLabel().setFont(font);
		this.contRoomCountSum.getBoundLabel().setFont(font);
		this.contBuildingAreaSum.getBoundLabel().setFont(font);
		this.contRoomAreaSum.getBoundLabel().setFont(font);

		// this.contOrderDate.getBoundLabel().setForeground(color);
		this.contTotalPrice.getBoundLabel().setForeground(color);
		this.contBuildingAvgPrice.getBoundLabel().setForeground(color);
		this.contRoomAvgPrice.getBoundLabel().setForeground(color);
		this.contRoomCountSum.getBoundLabel().setForeground(color);
		this.contBuildingAreaSum.getBoundLabel().setForeground(color);
		this.contRoomAreaSum.getBoundLabel().setForeground(color);

		this.pkOrderDate.setFont(font);
		this.txtTotalPrice.setFont(font);
		this.txtBuildingAvgPrice.setFont(font);
		this.txtRoomAvgPrice.setFont(font);
		this.txtRoomCountSum.setFont(font);
		this.txtBuildingAreaSum.setFont(font);
		this.txtRoomAreaSum.setFont(font);

		super.onLoad();
		
		initPrmtRoomSelect();
		this.actionAddRoomByList.setEnabled(true);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
	{
		if (this.tblRooms.getRowCount() == 0)
		{
			MsgBox.showInfo("推盘房间必须录入！");
			abort();
		}
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() &&  StringUtils.isEmpty(this.txtNumber.getText()))
		{
			MsgBox.showInfo("编码必须录入！");
			abort();
		}
		if (StringUtils.isEmpty(this.txtName.getText()))
		{
			MsgBox.showInfo("名称必须录入！");
			abort();
		}
		if (this.txtPlanRevAmount.getBigDecimalValue() == null
				|| this.txtPlanRevAmount.getBigDecimalValue().equals(
						FDCHelper.ZERO))
		{
			MsgBox.showInfo("预计收款总额必须录入！");
			abort();
		}
		Date orderDate = (Date) this.pkOrderDate.getValue();
		
		BigDecimal tempTotal = FDCHelper.ZERO;
		for (int j = 0; j < this.tblPlan.getRowCount(); j++)
		{
			IRow row = this.tblPlan.getRow(j);
			Date date = (Date) row.getCell("date").getValue();
			if (date == null)
			{
				MsgBox.showInfo("收款计划第" + (j + 1) + "行,截止日期必须录入！");
				abort();
			}
			if (orderDate != null)
			{
				//忽略时分秒进行比较 
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				orderDate = sf.parse(sf.format(orderDate));
				if (date.before(orderDate))
				{
					MsgBox.showInfo("收款计划第" + (j + 1) + "行,截止日期不能在推盘日期之前！");
					abort();
				}
			}
			BigDecimal pro = (BigDecimal) row.getCell("pro").getValue();
			
			
			if (pro == null || pro.compareTo(FDCHelper.ZERO) == 0)
			{
				MsgBox.showInfo("收款计划第" + (j + 1) + "行,收款比例必须录入！");
				abort();
			}
			if (pro.compareTo(new BigDecimal(100)) > 0)
			{
				MsgBox.showInfo("收款计划第" + (j + 1) + "行,收款比例不能大于100%！");
				abort();
			}
			
			tempTotal = tempTotal.add(pro);
		}
		if(tempTotal.compareTo(new BigDecimal(100))>0)
		{
			if(MsgBox.showConfirm2("收款比列总和超过 100% ，是否保存？") == 0)
			{
				
			}
			else
			{
				abort();
			}
		}
		
		this.setOprtState("EDIT");
		super.actionSubmit_actionPerformed(e);
	}
	
	/**
	 * 描述：列表方式添加数据
	 */
	public void actionAddRoomByList_actionPerformed(ActionEvent e)
			throws Exception {
		
//		SellOrderInfo sellOrder = (SellOrderInfo) this.editData;
//		
//		EntityViewInfo view = prmtRoomSelect.getEntityViewInfo();
//		
//		FilterInfo filter = view.getFilter();
		
////		//销售项目
//		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellOrder.getProject().getId()));

//		prmtRoomSelect.setEntityViewInfo(view);
		//按钮触发F7
		prmtRoomSelect.setDataBySelector();
		Object[] rooms = (Object[]) prmtRoomSelect.getValue();
		prmtRoomSelect.setValue(null);
		if (rooms == null || rooms.length == 0 || rooms[0] == null) {
			return;
		}
		for (int i = 0; i < rooms.length; i++) {
			RoomInfo room = (RoomInfo) rooms[i];
			if (!room.getSellState().equals(RoomSellStateEnum.Init)) {
				MsgBox.showInfo(room.getDisplayName() + " 不是初始状态!");
				return;
			}
			if (!room.isIsForSHE()) {
				MsgBox.showInfo(room.getDisplayName() + " 没有售楼属性!");
				return;
			}
			if (isExist(room)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经在列表中!");
				continue;
			}
			SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
					.get("sellProject");
			if (!room.getBuilding().getSellProject().getId().equals(
					sellProject.getId())) {
				MsgBox.showInfo(room.getDisplayName() + " 不属于项目:"
						+ sellProject.getName());
				return;
			}
			SellOrderRoomEntryInfo roomEntry = new SellOrderRoomEntryInfo();
			roomEntry.setRoom(room);
			roomEntry.setState(RoomOrderStateEnum.noExecute);
			IRow row = this.tblRooms.addRow();
			row.setUserObject(roomEntry);
			row.getCell("building").setValue(room.getBuilding());
			row.getCell("unit").setValue(room.getBuildUnit());
			row.getCell("roomNumber").setValue(room.getDisplayName());
			row.getCell("roomNo").setValue(room.getRoomNo());
			// 增加建筑面积（buildingArea）、套内面积（roomArea）、建筑单价（buildPrice）、
			// 套内单价（roomPrice）、是否按套内定价（isCalByRoomArea） alert by xwb
			row.getCell("buildingArea").setValue(room.getBuildingArea());
			row.getCell("roomArea").setValue(room.getRoomArea());
			row.getCell("buildPrice").setValue(room.getBuildPrice());
			row.getCell("roomPrice").setValue(room.getRoomPrice());
			row.getCell("state").setValue(roomEntry.getState());
			row.getCell("isCalByRoomArea").setValue(
					new Boolean(room.isIsCalByRoomArea()));
		}
		setTotalAvgPrice();
		// xwb
		setRoomCountSum();

		this.txtPlanRevAmount.setValue(this.txtTotalPrice.getBigDecimalValue());
		 
	
	}
	

	public SelectorItemCollection getSelectors()
	{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("project.*");
		sels.add("planEntrys.*");
		sels.add("roomEntrys.room.*");
		sels.add("roomEntrys.room.buildUnit.name");
		sels.add("roomEntrys.state");
		sels.add("roomEntrys.room.building.name");
		return sels;
	}

	
	
	protected IObjectValue createNewData()
	{
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		SellOrderInfo sellOrderInfo = new SellOrderInfo();
		sellOrderInfo.setIsEnabled(false);
		sellOrderInfo.setOrderDate(new Date());
		sellOrderInfo.setProject(sellProject);
		
		sellOrderInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return sellOrderInfo;
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return SellOrderFactory.getRemoteInstance();
	}

	protected void btnAddRoom_actionPerformed(ActionEvent e) throws Exception
	{
		super.btnAddRoom_actionPerformed(e);
		RoomCollection rooms = RoomSelectUI
				.showMultiRoomSelectUI(this, null, null,MoneySysTypeEnum.SalehouseSys);
		if (rooms == null)
		{
			return;
		}
		for (int i = 0; i < rooms.size(); i++)
		{
			RoomInfo room = rooms.get(i);
			if (!room.getSellState().equals(RoomSellStateEnum.Init))
			{
				MsgBox.showInfo(room.getDisplayName() + " 不是初始状态!");
				return;
			}
			if (!room.isIsForSHE())
			{
				MsgBox.showInfo(room.getDisplayName() + " 没有售楼属性!");
				return;
			}
			if (isExist(room))
			{
				MsgBox.showInfo(room.getDisplayName() + " 已经在列表中!");
				continue;
			}
			SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
					.get("sellProject");
			if (!room.getBuilding().getSellProject().getId().equals(
					sellProject.getId()))
			{
				MsgBox.showInfo(room.getDisplayName() + " 不属于项目:"
						+ sellProject.getName());
				return;
			}
			SellOrderRoomEntryInfo roomEntry = new SellOrderRoomEntryInfo();
			roomEntry.setRoom(room);
			roomEntry.setState(RoomOrderStateEnum.noExecute);
			IRow row = this.tblRooms.addRow();
			row.setUserObject(roomEntry);
			row.getCell("building").setValue(room.getBuilding());
			row.getCell("unit").setValue(room.getBuildUnit());
			row.getCell("roomNumber").setValue(room.getDisplayName());
			row.getCell("roomNo").setValue(room.getRoomNo());
			// 增加建筑面积（buildingArea）、套内面积（roomArea）、建筑单价（buildPrice）、
			// 套内单价（roomPrice）、是否按套内定价（isCalByRoomArea） alert by xwb
			row.getCell("buildingArea").setValue(room.getBuildingArea());
			row.getCell("roomArea").setValue(room.getRoomArea());
			row.getCell("buildPrice").setValue(room.getBuildPrice());
			row.getCell("roomPrice").setValue(room.getRoomPrice());
			row.getCell("state").setValue(roomEntry.getState());
			row.getCell("isCalByRoomArea").setValue(
					new Boolean(room.isIsCalByRoomArea()));
		}
		setTotalAvgPrice();
		// xwb
		setRoomCountSum();
		
		this.txtPlanRevAmount.setValue(this.txtTotalPrice.getBigDecimalValue());
		
//		for (int i = 0; i < this.tblPlan.getRowCount(); i++)
//		{
//			IRow row = this.tblPlan.getRow(i);
//			this.updateRowPlanRev(row);
//			this.updateRowActRev(row);
//		}
	}

	private boolean isExist(RoomInfo room)
	{
		for (int j = 0; j < this.tblRooms.getRowCount(); j++)
		{
			SellOrderRoomEntryInfo roomEntry = (SellOrderRoomEntryInfo) this.tblRooms
					.getRow(j).getUserObject();
			if (roomEntry.getRoom().getId().toString().equals(
					room.getId().toString()))
			{
				return true;
			}
		}
		return false;
	}

	protected void btnRemoveRoom_actionPerformed(ActionEvent e)
			throws Exception
	{
		super.btnRemoveRoom_actionPerformed(e);
		int activeRowIndex = this.tblRooms.getSelectManager()
				.getActiveRowIndex();
		this.tblRooms.removeRow(activeRowIndex);
		setTotalAvgPrice();
		// xwb
		setRoomCountSum();
		
		this.txtPlanRevAmount.setValue(this.txtTotalPrice.getBigDecimalValue());
		
//		for (int i = 0; i < this.tblPlan.getRowCount(); i++)
//		{
//			IRow row = this.tblPlan.getRow(i);
//			this.updateRowPlanRev(row);
//			this.updateRowActRev(row);
//		}
	}

	protected void txtPlanRevAmount_dataChanged(DataChangeEvent e) throws Exception {
		super.txtPlanRevAmount_dataChanged(e);
		BigDecimal planRevAmount = this.txtPlanRevAmount.getBigDecimalValue();
		for (int i = 0; i < this.tblPlan.getRowCount(); i++)
		{
			IRow row = this.tblPlan.getRow(i);
			this.updateRowPlanRev(row);
			this.updateRowActRev(row);
		}
	}
	
	protected void tblPlan_editStopped(KDTEditEvent e) throws Exception
	{
		super.tblPlan_editStopped(e);
		int row = e.getRowIndex();
		if (row < 0)
		{
			return;
		}
		if (e.getColIndex() == this.tblPlan.getColumnIndex("date"))
		{
			this.updateRowActRev(this.tblPlan.getRow(e.getRowIndex()));
		} else if (e.getColIndex() == this.tblPlan.getColumnIndex("pro"))
		{
			this.updateRowPlanRev(this.tblPlan.getRow(e.getRowIndex()));
		}
	}

	protected void btnAddPlan_actionPerformed(ActionEvent e) throws Exception
	{
		super.btnAddPlan_actionPerformed(e);
		IRow row = this.tblPlan.addRow();
		SellOrderPlanEntryInfo entry = new SellOrderPlanEntryInfo();
		row.setUserObject(entry);
	}

	protected void btnRemovePlan_actionPerformed(ActionEvent e)
			throws Exception
	{
		super.btnRemovePlan_actionPerformed(e);
		int activeRowIndex = this.tblPlan.getSelectManager()
				.getActiveRowIndex();
		this.tblPlan.removeRow(activeRowIndex);
	}
}