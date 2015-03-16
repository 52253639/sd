/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
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
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustFactory;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo;
import com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
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
public class PriceAdjustEditUI extends AbstractPriceAdjustEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PriceAdjustEditUI.class);
	
	
	/**
	 * output class constructor
	 */
	public PriceAdjustEditUI() throws Exception {
		super();
	}

	protected void setAuditButtonStatus(String oprtType) {

	}
	
	protected void chkIsAutoToInteger_actionPerformed(ActionEvent e) throws Exception {
		boolean isAutoToInteger = this.chkIsAutoToInteger.isSelected();
		setToIntegerVisable(isAutoToInteger);
		
		refreshRowData();
	}
	
	private void refreshRowData() {
		for(int i=0; i<this.tblRoom.getRowCount(); i++){
			IRow row = this.tblRoom.getRow(i);
			updateRowData(row);
		}
		setStatPrice();
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
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
		KDTable table = new KDTable();
		table.addColumns(12);
		for(int i=0;i<tblRoom.getRowCount();i++)
		{
			
			IRow irow = (IRow)tblRoom.getRow(i).clone();
			table.addRow(i,irow);
		}
		this.getPriceAdjust(table);
		table.getPrintManager().print();
	    super.actionPrint_actionPerformed(e);
    }
	
	private void getPriceAdjust(KDTable table)
	{
		table.removeHeadRows();
		IRow row2 = table.addHeadRow();
		row2.getCell(0).setValue("调价单编号");
		row2.getCell(1).setValue(this.txtNumber.getText());
		IRow row = table.addHeadRow();
		row.getCell(0).setValue("总套数");
		row.getCell(1).setValue(this.txtTotalCount.getNumberValue());
		row.getCell(2).setValue("制单时间");
		row.getCell(3).setValue(this.pkCreateTime.getText());
		row.getCell(4).setValue("调后建筑均价");
		row.getCell(5).setValue(this.txtNewBuildingAvgPrice.getNumberValue());
		row.getCell(6).setValue("制单人");
		row.getCell(7).setValue(this.f7Creator.getValue());
		row.getCell(8).setValue("总建筑面积");
		row.getCell(9).setValue(this.txtTotalBuildingArea.getNumberValue());
		row.getCell(10).setValue("总套内面积");
		IRow row1 = table.addHeadRow();
		row1.getCell(0).setValue("名称");
		row1.getCell(1).setValue(this.txtName.getText());
		
		
		
		
		row1.getCell(2).setValue("调前总价");
		row1.getCell(3).setValue(this.txtOldTotalPrice.getNumberValue());
		row1.getCell(4).setValue("调后总价");
		row1.getCell(5).setValue(this.txtNewTotalPrice.getNumberValue());
		row1.getCell(6).setValue("调前建筑均价");
		row1.getCell(7).setValue(this.txtOldBuildingAvgPrice.getNumberValue());
		row1.getCell(8).setValue("调前套内均价");
		row1.getCell(9).setValue(this.txtOldRoomAvgPrice.getNumberValue());
		row1.getCell(10).setValue("调后套内均价");
		row1.getCell(11).setValue(this.txtNewRoomAvgPrice.getNumberValue());
		
		
		IRow row4 = table.addHeadRow();
		row4.getCell(0).setValue("楼栋");
		row4.getCell(1).setValue("单元");
		row4.getCell(2).setValue("房间编码");
		row4.getCell(3).setValue("总价");
		row4.getCell(4).setValue("调后总价");
		row4.getCell(5).setValue("建筑面积");
		row4.getCell(6).setValue("建筑单价");
		row4.getCell(7).setValue("调后建筑单价");
		row4.getCell(8).setValue("按套内面积计算");
		row4.getCell(9).setValue("套内面积");
		row4.getCell(10).setValue("套内单价");
		row4.getCell(11).setValue("调后套内单价");
	}
	
	
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
		KDTable table = new KDTable();
		table.addColumns(12);
		for(int i=0;i<tblRoom.getRowCount();i++)
		{
			
			IRow irow = (IRow)tblRoom.getRow(i).clone();
			table.addRow(i,irow);
		}
		this.getPriceAdjust(table);
		table.getPrintManager().printPreview();
	    super.actionPrintPreview_actionPerformed(e);
    }

	private void addRowByEntry(PriceAdjustEntryInfo entry) {
		IRow row = this.tblRoom.addRow();
		row.setUserObject(entry);
		boolean newIsCalByRoomArea = entry.isIsCalByRoomNew();
		boolean oldIsCalByRoomArea = entry.isIsCalByRoomOld();
		BigDecimal oldBuildingArea = entry.getOldBuildingArea();
		BigDecimal oldRoomArea = entry.getOldRoomArea();
		BigDecimal newBuildingArea = entry.getNewBuildingArea();
		BigDecimal newRoomArea = entry.getNewRoomArea();
		BigDecimal oldBuildingPrice = entry.getOldBuildingPrice();
		BigDecimal oldRoomPrice = entry.getOldRoomPrice();
		BigDecimal newBuildingPrice = entry.getNewBuildingPrice();
		BigDecimal newRoomPrice = entry.getNewRoomPrice();
		BigDecimal oldTotalAmount = FDCHelper.ZERO;
//		add new fields by jd
		BigDecimal buildingPriceBalance = entry.getBuildingPriceBalance();
		BigDecimal buildingPriceBalanceScale = entry.getBuildingPriceBalanceScale();
		BigDecimal roomPriceBalance = entry.getRoomPriceBalanceScale();
		BigDecimal roomPriceBalanceScale = entry.getRoomPriceBalanceScale();
		
		if (oldIsCalByRoomArea) {
			if (oldRoomArea == null) {
				oldRoomArea = FDCHelper.ZERO;
			}
			if (oldRoomPrice != null) {
				oldTotalAmount = oldRoomArea.multiply(oldRoomPrice);
			}
		} else {
			if (oldBuildingArea == null) {
				oldBuildingArea = FDCHelper.ZERO;
			}
			if (oldBuildingPrice != null) {
				oldTotalAmount = oldBuildingArea.multiply(oldBuildingPrice);
			}
		}

		RoomInfo room = entry.getRoom();
		row.getCell("building").setValue(room.getBuilding().getName());
		if(room.getBuildUnit() != null)
		{
			row.getCell("unit").setValue(room.getBuildUnit().getName());
		}
		row.getCell("roomNumber").setValue(room.getDisplayName());

		row.getCell("isCalByRoom").setValue(new Boolean(newIsCalByRoomArea));
		row.getCell("buildingArea").setValue(newBuildingArea);
		row.getCell("roomArea").setValue(newRoomArea);
		row.getCell("oldBuildingPrice").setValue(oldBuildingPrice);
		row.getCell("oldRoomPrice").setValue(oldRoomPrice);
		row.getCell("newBuildingPrice").setValue(newBuildingPrice);
		row.getCell("newRoomPrice").setValue(newRoomPrice);
		row.getCell("oldSumAmount").setValue(oldTotalAmount);
		
		row.getCell("buildingPriceBalance").setValue(buildingPriceBalance);
		row.getCell("buildingPriceBalanceScale").setValue(buildingPriceBalanceScale);
		row.getCell("roomPriceBalance").setValue(roomPriceBalance);
		row.getCell("roomPriceBalanceScale").setValue(roomPriceBalanceScale);
		row.getCell("roomId").setValue(room.getId());
		this.updateRowData(row);
	}
	
	protected IObjectValue getValue(IObjectPK pk) throws Exception 
	{
		PriceAdjustInfo priceAdjust = (PriceAdjustInfo)super.getValue(pk);
		
		FilterInfo entryFilter = new FilterInfo();
		entryFilter.getFilterItems().add(new FilterItemInfo("head", pk.toString()));
		
		SelectorItemCollection sel = new SelectorItemCollection();
    	sel.add(new SelectorItemInfo("*"));
    	sel.add(new SelectorItemInfo("room.*"));
    	sel.add(new SelectorItemInfo("room.building.*"));
    	sel.add(new SelectorItemInfo("room.buildUnit.*"));
    	
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(entryFilter);
		
		PriceAdjustEntryCollection priceCollection = PriceAdjustEntryFactory.getRemoteInstance().getPriceAdjustEntryCollection(view);
		priceAdjust.getEntrys().addCollection(priceCollection);
		
		return priceAdjust;
	}

	public void loadFields() {
		super.loadFields();
		PriceAdjustInfo adjust = this.editData;
		PriceAdjustEntryCollection roomEntrys = adjust.getEntrys();
		this.tblRoom.removeRows();
		for (int i = 0; i < roomEntrys.size(); i++) {
			PriceAdjustEntryInfo entry = roomEntrys.get(i);
			this.addRowByEntry(entry);
		}
		this.setStatPrice(true);
		if (this.getOprtState().equals("VIEW")) {
			this.btnAddRoom.setEnabled(false);
			this.btnRemoveRoom.setEnabled(false);
			this.btnImportPrice.setEnabled(false);
			tblRoom.getColumn("newBuildingPrice").getStyleAttributes()
					.setLocked(true);
			tblRoom.getColumn("newRoomPrice").getStyleAttributes().setLocked(
					true);
		}
		if (adjust.getState() != null
				&& !adjust.getState().equals(FDCBillStateEnum.SAVED)) {
			this.actionSave.setEnabled(false);
		}
		
		setToIntegerVisable(this.editData.isIsAutoToInteger());
		
		//添加合计行
		deleteTotalRows();
		sortCurRows(tblRoom);
		addTotalRows();
	}

	//覆写了getValue方法，因而这里没有任何用处了
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
//		sels.add("state");
//		sels.add("sellProject.*");
//		sels.add("entrys.*");
//		sels.add("entrys.room.*");
		sels.add("auditor.*");
		sels.add("entrys.room.building.name");		
		sels.add("entrys.room.buildUnit.name");
		return sels;
	}

	public void onShow() throws Exception {
		super.onShow();
		if (this.txtNumber.isEnabled()) {
			this.txtNumber.requestFocus();
		} else {
			this.txtName.requestFocus();
		}
		
		this.totalAmountBalanceScale.setPrecision(2); //why放onload里面就不行？
	}

	public void onLoad() throws Exception {
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);

		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		
		
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.txtTotalCount.setRemoveingZeroInDispaly(false);
		this.txtTotalCount.setRemoveingZeroInEdit(false);
		this.txtTotalCount.setPrecision(2);
		this.txtTotalCount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtTotalCount.setSupportedEmpty(true);
		this.txtTotalCount.setEnabled(false);

		this.txtOldTotalPrice.setRemoveingZeroInDispaly(false);
		this.txtOldTotalPrice.setRemoveingZeroInEdit(false);
		this.txtOldTotalPrice.setPrecision(2);
		this.txtOldTotalPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtOldTotalPrice.setSupportedEmpty(true);
		this.txtOldTotalPrice.setEnabled(false);
		this.txtOldTotalPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		
		this.txtNewTotalPrice.setRemoveingZeroInDispaly(false);
		this.txtNewTotalPrice.setRemoveingZeroInEdit(false);
		this.txtNewTotalPrice.setPrecision(2);
		this.txtNewTotalPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtNewTotalPrice.setSupportedEmpty(true);
		this.txtNewTotalPrice.setEnabled(false);
		this.txtNewTotalPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);

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

		this.txtOldBuildingAvgPrice.setRemoveingZeroInDispaly(false);
		this.txtOldBuildingAvgPrice.setRemoveingZeroInEdit(false);
		this.txtOldBuildingAvgPrice.setPrecision(2);
		this.txtOldBuildingAvgPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtOldBuildingAvgPrice.setSupportedEmpty(true);
		this.txtOldBuildingAvgPrice.setEnabled(false);

		this.txtNewBuildingAvgPrice.setRemoveingZeroInDispaly(false);
		this.txtNewBuildingAvgPrice.setRemoveingZeroInEdit(false);
		this.txtNewBuildingAvgPrice.setPrecision(2);
		this.txtNewBuildingAvgPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtNewBuildingAvgPrice.setSupportedEmpty(true);
		this.txtNewBuildingAvgPrice.setEnabled(false);

		this.txtOldRoomAvgPrice.setRemoveingZeroInDispaly(false);
		this.txtOldRoomAvgPrice.setRemoveingZeroInEdit(false);
		this.txtOldRoomAvgPrice.setPrecision(2);
		this.txtOldRoomAvgPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtOldRoomAvgPrice.setSupportedEmpty(true);
		this.txtOldRoomAvgPrice.setEnabled(false);

		this.txtNewRoomAvgPrice.setRemoveingZeroInDispaly(false);
		this.txtNewRoomAvgPrice.setRemoveingZeroInEdit(false);
		this.txtNewRoomAvgPrice.setPrecision(2);
		this.txtNewRoomAvgPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtNewRoomAvgPrice.setSupportedEmpty(true);
		this.txtNewRoomAvgPrice.setEnabled(false);
		
		this.totalAmountBalance.setRemoveingZeroInDispaly(false);
		this.totalAmountBalance.setRemoveingZeroInEdit(false);
		this.totalAmountBalance.setPrecision(2);
		this.totalAmountBalance.setHorizontalAlignment(JTextField.RIGHT);
		this.totalAmountBalance.setSupportedEmpty(true);
		this.totalAmountBalance.setEnabled(false);
		
		this.totalAmountBalanceScale.setRemoveingZeroInDispaly(false);
		this.totalAmountBalanceScale.setRemoveingZeroInEdit(false);
		this.totalAmountBalanceScale.setPrecision(4);
		this.totalAmountBalanceScale.setHorizontalAlignment(JTextField.RIGHT);
		this.totalAmountBalanceScale.setSupportedEmpty(true);
		this.totalAmountBalanceScale.setEnabled(false);
		
		

		Font font = new Font("SimSun", Font.BOLD, 15);
		Color color = Color.BLUE;
		
		this.txtTotalCount.setFont(font);
		this.txtOldTotalPrice.setFont(font);
		this.txtNewTotalPrice.setFont(font);
		this.txtTotalBuildingArea.setFont(font);
		this.txtTotalRoomArea.setFont(font);
		this.txtOldBuildingAvgPrice.setFont(font);
		this.txtNewBuildingAvgPrice.setFont(font);
		this.txtOldRoomAvgPrice.setFont(font);
		this.txtNewRoomAvgPrice.setFont(font);
		this.totalAmountBalance.setFont(font);
		this.totalAmountBalanceScale.setFont(font);

		this.contTotalCount.getBoundLabel().setFont(font);
		this.contOldTotalPrice.getBoundLabel().setFont(font);
		this.contNewTotalPrice.getBoundLabel().setFont(font);
		this.contTotalBuildingArea.getBoundLabel().setFont(font);
		this.contTotalRoomArea.getBoundLabel().setFont(font);
		this.contOldBuildingAvgPrice.getBoundLabel().setFont(font);
		this.contNewBuildingAvgPrice.getBoundLabel().setFont(font);
		this.contOldRoomAvgPrice.getBoundLabel().setFont(font);
		this.contNewRoomAvgPrice.getBoundLabel().setFont(font);		
		this.totalAmountBalance.getBoundLabel().setFont(font);
		this.totalAmountBalanceScale.getBoundLabel().setFont(font);
		
		this.contTotalCount.getBoundLabel().setForeground(color);
		this.contOldTotalPrice.getBoundLabel().setForeground(color);
		this.contNewTotalPrice.getBoundLabel().setForeground(color);
		this.contTotalBuildingArea.getBoundLabel().setForeground(color);
		this.contTotalRoomArea.getBoundLabel().setForeground(color);
		this.contOldBuildingAvgPrice.getBoundLabel().setForeground(color);
		this.contNewBuildingAvgPrice.getBoundLabel().setForeground(color);
		this.contOldRoomAvgPrice.getBoundLabel().setForeground(color);
		this.contNewRoomAvgPrice.getBoundLabel().setForeground(color);
		this.totalAmountBalance.getBoundLabel().setForeground(color);
		this.totalAmountBalanceScale.getBoundLabel().setForeground(color);
		
		initTable();
		super.onLoad();
		if(this.getOprtState().equals("VIEW")  ||  "FINDVIEW".equals(this.getOprtState())){
			this.btnImportPrice.setEnabled(false);
			this.btnAddRoom.setEnabled(false);
			this.btnRemoveRoom.setEnabled(false);
			this.tblRoom.getStyleAttributes().setLocked(true);
		}
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			tblRoom.getColumn("newBuildingPrice").getStyleAttributes()
					.setLocked(true);
			tblRoom.getColumn("newRoomPrice").getStyleAttributes().setLocked(
					true);
		}
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		this.btnPrint.setVisible(true);
		this.btnPrint.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		
		
		//SimpleKDTSortManager.setTableSortable(tblRoom);
		
		this.actionAddRoomByList.setEnabled(true);
		initPrmtRoomSelect();
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
		
		//显示不出单元
		selectors.add("buildUnit.*");
		
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

	protected void getAttachMentItem(KDTable table) {
	}

	public KDMenuItem getAttachMenuItem(KDTable table) {
		return null;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtil.isEmptyString(this.txtNumber.getText())) {
			MsgBox.showInfo("编码必须录入!");
			this.abort();
		}
		if (StringUtil.isEmptyString(this.txtName.getText())) {
			MsgBox.showInfo("名称必须录入!");
			this.abort();
		}
		super.verifyInput(e);
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		
	
		/*
		 * 增加与底价比较的判断，单价不能低于底价
		 * xiaoao_liu
		 */
		for (int i = 0; i < this.tblRoom.getRowCount(); i++)
		{
			IRow row = this.tblRoom.getRow(i);
			
			if(i<this.tblRoom.getRowCount()-1){
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

			        evi.setSelector(coll);
			        IRoom restRevDao = RoomFactory.getRemoteInstance();
			        //满足条件的集合
			        RoomCollection collection = restRevDao.getRoomCollection(evi);
			        RoomInfo room=collection.get(0);  
			        if( row.getCell("isCalByRoom").getValue().toString().equals("false")) 
					{
						
						BigDecimal buildingPrice =(BigDecimal) row.getCell("newBuildingPrice").getValue();
						
						if(room.isIsBasePriceAudited()){
							if(buildingPrice.compareTo(room.getBaseBuildingPrice())==-1){
								MsgBox.showInfo("第" + (i + 1) + "行,建筑单价不能低于房间底价!");
								SysUtil.abort();
							}
						}
					}else{
						BigDecimal roomPrice =(BigDecimal) row.getCell("newRoomPrice").getValue();
						if(room.isIsBasePriceAudited()){
							if(roomPrice.compareTo(room.getBaseRoomPrice())==-1){
								MsgBox.showInfo("第" + (i + 1) + "行,套内单价不能低于房间底价!");
								SysUtil.abort();
							}
						}
					}
			    }
			}
		}

		super.actionSave_actionPerformed(e);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			BigDecimal sumAmount = (BigDecimal) row.getCell("newSumAmount")
					.getValue();
			/*
			 * 增加与底价比较的判断，单价不能低于底价
			 * xiaoao_liu
			 */
			if(i<this.tblRoom.getRowCount()-1){
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

			        evi.setSelector(coll);
			        IRoom restRevDao = RoomFactory.getRemoteInstance();
			        //满足条件的集合
			        RoomCollection collection = restRevDao.getRoomCollection(evi);
			        RoomInfo room=collection.get(0);  
			        if( row.getCell("isCalByRoom").getValue().toString().equals("false")) 
					{
						
						BigDecimal buildingPrice =(BigDecimal) row.getCell("newBuildingPrice").getValue();
						
						if(room.isIsBasePriceAudited()){
							if(buildingPrice.compareTo(room.getBaseBuildingPrice())==-1){
								MsgBox.showInfo("第" + (i + 1) + "行,建筑单价不能低于房间底价!");
								SysUtil.abort();
							}
						}
					}else{
						BigDecimal roomPrice =(BigDecimal) row.getCell("newRoomPrice").getValue();
						if(room.isIsBasePriceAudited()){
							if(roomPrice.compareTo(room.getBaseRoomPrice())==-1){
								MsgBox.showInfo("第" + (i + 1) + "行,套内单价不能低于房间底价!");
								SysUtil.abort();
							}
						}
					}
				}
			}
           
			
			if (sumAmount == null || sumAmount.compareTo(FDCHelper.ZERO) == 0) {
				MsgBox.showInfo("第" + (i + 1) + "行,调后标准总价为0!");
				return;
			}
		}
		setStatPrice();//再设置一次，主要是为了验证调后总额是否超出范围
		this.setOprtState("EDIT");
		super.actionSubmit_actionPerformed(e);
	}

	private void initTable() {
		tblRoom.checkParsed();
		tblRoom.getStyleAttributes().setLocked(true);
		tblRoom.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		tblRoom.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		IColumn column = this.tblRoom.addColumn();
		column.setKey("building");
		column.setWidth(80);
		column = this.tblRoom.addColumn();
		column.setKey("unit");
		column.setWidth(40);
		column = this.tblRoom.addColumn();
		column.setKey("roomNumber");
		column.setWidth(80);
		column = this.tblRoom.addColumn();
		column.setKey("oldSumAmount");
		column.setWidth(80);
		column = this.tblRoom.addColumn();
		column.setKey("newSumAmount");
		column.setWidth(80);
		column = this.tblRoom.addColumn();
		column.setKey("buildingArea");
		column.setWidth(80);
		column = this.tblRoom.addColumn();
		column.setKey("oldBuildingPrice");
		column.setWidth(80);
		column = this.tblRoom.addColumn();
		column.setKey("newBuildingPrice");
		column.setWidth(80);
		column = this.tblRoom.addColumn();
		column.setKey("isCalByRoom");
		column.setWidth(80);
		column = this.tblRoom.addColumn();
		column.setKey("roomArea");
		column.setWidth(80);
		column = this.tblRoom.addColumn();
		column.setKey("oldRoomPrice");
		column.setWidth(80);
		column = this.tblRoom.addColumn();
		column.setKey("newRoomPrice");
		column.setWidth(80);
//		增加一列
		column = this.tblRoom.addColumn();
		column.setKey("roomId");
		column.setWidth(80);
		
//		增加新字段
		column = this.tblRoom.addColumn();
		column.setKey("buildingPriceBalance");
		column.setWidth(90);
		
		column = this.tblRoom.addColumn();
		column.setKey("buildingPriceBalanceScale");
		column.setWidth(110);
		
		column = this.tblRoom.addColumn();
		column.setKey("roomPriceBalance");
		column.setWidth(90);
		
		column = this.tblRoom.addColumn();
		column.setKey("roomPriceBalanceScale");
		column.setWidth(110);
		
		IRow headRow = this.tblRoom.addHeadRow();
		headRow.getCell("building").setValue("楼栋");
		headRow.getCell("unit").setValue("单元");
		headRow.getCell("roomNumber").setValue("房间编码");
		headRow.getCell("oldSumAmount").setValue("总价");
		headRow.getCell("newSumAmount").setValue("调后总价");
		headRow.getCell("buildingArea").setValue("建筑面积");
		headRow.getCell("oldBuildingPrice").setValue("建筑单价");
		headRow.getCell("newBuildingPrice").setValue("调后建筑单价");
		headRow.getCell("isCalByRoom").setValue("按套内面积计算");
		headRow.getCell("roomArea").setValue("套内面积");
		headRow.getCell("oldRoomPrice").setValue("套内单价");
		headRow.getCell("newRoomPrice").setValue("调后套内单价");
		
		headRow.getCell("buildingPriceBalance").setValue("建筑单价差额");
		headRow.getCell("buildingPriceBalanceScale").setValue("建筑单价差额比例(%)");
		headRow.getCell("roomPriceBalance").setValue("套内单价差额");
		headRow.getCell("roomPriceBalanceScale").setValue("套内单价差额比例(%)");
		
		headRow.getCell("roomId").setValue("房间ID");

		tblRoom.getColumn("oldSumAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblRoom.getColumn("newSumAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblRoom.getColumn("buildingArea").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblRoom.getColumn("oldBuildingPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		tblRoom.getColumn("newBuildingPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		tblRoom.getColumn("roomArea").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblRoom.getColumn("oldRoomPrice").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblRoom.getColumn("newRoomPrice").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblRoom.getColumn("buildingPriceBalance").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblRoom.getColumn("buildingPriceBalanceScale").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblRoom.getColumn("roomPriceBalance").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblRoom.getColumn("roomPriceBalanceScale").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		
		tblRoom.getColumn("roomId").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));

		tblRoom.getColumn("oldSumAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblRoom.getColumn("newSumAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblRoom.getColumn("buildingArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblRoom.getColumn("oldBuildingPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblRoom.getColumn("newBuildingPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblRoom.getColumn("roomArea").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblRoom.getColumn("oldRoomPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblRoom.getColumn("newRoomPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblRoom.getColumn("buildingPriceBalance").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblRoom.getColumn("buildingPriceBalanceScale").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblRoom.getColumn("roomPriceBalance").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblRoom.getColumn("roomPriceBalanceScale").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblRoom.getColumn("roomId").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblRoom.getColumn("roomId").getStyleAttributes().setHided(true);

		// 设置可编辑的列
		tblRoom.getColumn("newBuildingPrice").getStyleAttributes().setLocked(
				false);
		tblRoom.getColumn("newRoomPrice").getStyleAttributes().setLocked(false);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		tblRoom.getColumn("newBuildingPrice").setEditor(numberEditor);
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		tblRoom.getColumn("newRoomPrice").setEditor(numberEditor);

	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		UserInfo auditor = this.editData.getAuditor();
		if (auditor != null) {
			MsgBox.showInfo("调价单已经审批,不能修改!");
			return;
		}
		super.actionEdit_actionPerformed(e);
		this.btnAddRoom.setEnabled(true);
		this.btnRemoveRoom.setEnabled(true);
		this.btnImportPrice.setEnabled(true);
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			updateRowData(row);
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		this.btnAddRoom.setEnabled(true);
		this.btnRemoveRoom.setEnabled(true);
		this.btnImportPrice.setEnabled(true);
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			updateRowData(row);
		}
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
				MsgBox.showInfo(room.getDisplayName() + " 未进行售前复核!");
				break;
			}
			if (room.isIsForSHE() == false) {
				MsgBox.showInfo(room.getDisplayName() + " 非售楼房间!");
				break;
			}
			if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经预定!");
				break;
			}
			if (room.getSellState().equals(RoomSellStateEnum.Purchase)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经认购!");
				break;
			}
			if (room.getSellState().equals(RoomSellStateEnum.Sign)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经签约!");
				break;
			}
			if (isExist(room)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经在列表中!");
				continue;
			}
			PriceAdjustEntryInfo entry = new PriceAdjustEntryInfo();
			entry.setOldRoomPrice(room.getRoomPrice());
			entry.setOldBuildingPrice(room.getBuildPrice());
			entry.setOldRoomArea(room.getRoomArea());
			entry.setOldBuildingArea(room.getBuildingArea());
			entry.setNewRoomPrice(room.getRoomPrice());
			entry.setNewBuildingPrice(room.getBuildPrice());
			entry.setNewRoomArea(room.getRoomArea());
			entry.setNewBuildingArea(room.getBuildingArea());
			entry.setIsCalByRoomOld(room.isIsCalByRoomArea());
			entry.setIsCalByRoomNew(room.isIsCalByRoomArea());
			entry.setRoom(room);
			this.addRowByEntry(entry);
		}

		setStatPrice();
		
		//添加合计行
		deleteTotalRows();
		sortCurRows(tblRoom);
		addTotalRows();
	}

	protected void btnAddRoom_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddRoom_actionPerformed(e);
//		RoomCollection rooms = RoomSelectUI
//				.showMultiRoomSelectUI(this, null, 0);
		
		RoomCollection rooms = RoomSelectUI.showMultiRoomSelectUI(this, null, null, MoneySysTypeEnum.SalehouseSys,null,this.editData.getSellProject());
		if (rooms == null) {
			return;
		}
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			if (!room.isIsAreaAudited()) {
				MsgBox.showInfo(room.getDisplayName() + " 未进行售前复核!");
				break;
			}
			if(room.isIsForSHE()==false)
			{
				MsgBox.showInfo(room.getDisplayName() + " 非售楼房间!");
				break;
			}
			if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经预定!");
				break;
			}
			if (room.getSellState().equals(RoomSellStateEnum.Purchase)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经认购!");
				break;
			}
			if (room.getSellState().equals(RoomSellStateEnum.Sign)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经签约!");
				break;
			}
			if (isExist(room)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经在列表中!");
				continue;
			}
			PriceAdjustEntryInfo entry = new PriceAdjustEntryInfo();
			entry.setOldRoomPrice(room.getRoomPrice());
			entry.setOldBuildingPrice(room.getBuildPrice());
			entry.setOldRoomArea(room.getRoomArea());
			entry.setOldBuildingArea(room.getBuildingArea());
			entry.setNewRoomPrice(room.getRoomPrice());
			entry.setNewBuildingPrice(room.getBuildPrice());
			entry.setNewRoomArea(room.getRoomArea());
			entry.setNewBuildingArea(room.getBuildingArea());
			entry.setIsCalByRoomOld(room.isIsCalByRoomArea());
			entry.setIsCalByRoomNew(room.isIsCalByRoomArea());
			entry.setRoom(room);
			this.addRowByEntry(entry);
		}
		this.setStatPrice();
		
		//添加汇总行合计
		deleteTotalRows();
		sortCurRows(tblRoom);
		addTotalRows();
	}

	private boolean isExist(RoomInfo room) {
		for (int j = 0; j < this.tblRoom.getRowCount(); j++) {
			if(this.tblRoom.getRow(j).getUserObject() instanceof String)
			{
				continue;
			}
			PriceAdjustEntryInfo roomEntry = (PriceAdjustEntryInfo) this.tblRoom
					.getRow(j).getUserObject();
			if (roomEntry.getRoom().getId().toString().equals(
					room.getId().toString())) {
				return true;
			}
		}
		return false;
	}

	protected void btnRemoveRoom_actionPerformed(ActionEvent e)
			throws Exception {
		
		int activeRowIndex = this.tblRoom.getSelectManager()
				.getActiveRowIndex();
		
		if(activeRowIndex >=0 &&tblRoom.getRow(activeRowIndex).getUserObject() instanceof String)
		{
			return;
		}
		else
		{
			this.tblRoom.removeRow(activeRowIndex);
			super.btnRemoveRoom_actionPerformed(e);
			this.setStatPrice();
			
			//添加汇总行合计
			deleteTotalRows();
			addTotalRows();
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		PriceAdjustInfo adjust = this.editData;
		adjust.getEntrys().clear();
		for (int j = 0; j < this.tblRoom.getRowCount(); j++) {
			IRow row = this.tblRoom.getRow(j);
			if(this.tblRoom.getRow(j).getUserObject() instanceof String)
			{
				continue;
			}
			PriceAdjustEntryInfo entry = (PriceAdjustEntryInfo) this.tblRoom
					.getRow(j).getUserObject();
			entry.setNewBuildingArea((BigDecimal) row.getCell("buildingArea")
					.getValue());
			entry.setNewBuildingPrice(SHEHelper.setScale((BigDecimal) row.getCell(
					"newBuildingPrice").getValue()));
			entry.setNewRoomArea((BigDecimal) row.getCell("roomArea")
					.getValue());
			entry.setNewRoomPrice(SHEHelper.setScale((BigDecimal) row.getCell("newRoomPrice")
					.getValue()));
			entry.setIsCalByRoomNew(((Boolean) row.getCell("isCalByRoom")
					.getValue()).booleanValue());
			adjust.getEntrys().add(entry);
		}
	}

	protected IObjectValue createNewData() {
		PriceAdjustInfo adjust = new PriceAdjustInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		if(sellProject == null){
			logger.error("销售项目不能为空！");
			this.abort();
		}
		adjust.setSellProject(sellProject);
		adjust.setIsExecuted(false);
		Timestamp currentTime = new Timestamp(new Date().getTime());
		try {
			//currentTime = FDCCommonServerHelper.getServerTimeStamp();
			currentTime = FDCSQLFacadeFactory.getRemoteInstance().getServerTime();
		} catch (BOSException e) {
			this.handleException(e);
		}
		adjust.setCreateTime(currentTime);
		adjust.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		adjust.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		adjust.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		adjust.setBookedDate(new Date());
		
		adjust.setIsAutoToInteger(false);
		adjust.setToIntegerType(ToIntegerTypeEnum.AbnegateZero);
		adjust.setDigit(DigitEnum.EntryDigit);
		return adjust;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PriceAdjustFactory.getRemoteInstance();
	}

	private void updateRowData(IRow row) {
		if(row.getCell("isCalByRoom").getValue() == null)
		{
			return;
		}
		boolean isCalByRoomArea = ((Boolean) row.getCell("isCalByRoom")
				.getValue()).booleanValue();
		BigDecimal newAmount = FDCHelper.ZERO;
		BigDecimal roomArea = (BigDecimal) row.getCell("roomArea").getValue();
		BigDecimal buildingArea = (BigDecimal) row.getCell("buildingArea")
				.getValue();
		BigDecimal oldBuildingPrice = (BigDecimal) row.getCell("oldBuildingPrice").getValue();
		BigDecimal oldRoomPrice = (BigDecimal) row.getCell("oldRoomPrice").getValue() ;
		if (isCalByRoomArea) {
			if (roomArea != null && row.getCell("newRoomPrice").getValue() != null) {
				newAmount = roomArea.multiply((BigDecimal) row.getCell("newRoomPrice").getValue());
				newAmount = SHEHelper.getAmountAfterToInteger(newAmount, this.chkIsAutoToInteger.isSelected(), (ToIntegerTypeEnum)this.comboToIntegerType.getSelectedItem(), (DigitEnum)this.comboDigit.getSelectedItem());
				if (buildingArea != null && buildingArea.compareTo(FDCHelper.ZERO) != 0) {
					row.getCell("newBuildingPrice").setValue( newAmount.divide(buildingArea, 2, BigDecimal.ROUND_HALF_UP));
					
					row.getCell("buildingPriceBalance").setValue(FDCHelper.subtract(row.getCell("newBuildingPrice").getValue(), oldBuildingPrice));
					row.getCell("roomPriceBalance").setValue(FDCHelper.subtract(row.getCell("newRoomPrice").getValue(), oldRoomPrice));
					row.getCell("buildingPriceBalanceScale").setValue(FDCHelper.divide(FDCHelper.multiply(FDCHelper.ONE_HUNDRED, row.getCell("buildingPriceBalance").getValue()), oldBuildingPrice, 2, BigDecimal.ROUND_HALF_UP));
					row.getCell("roomPriceBalanceScale").setValue(FDCHelper.divide(FDCHelper.multiply(row.getCell("roomPriceBalance").getValue(), FDCHelper.ONE_HUNDRED),oldRoomPrice, 2, BigDecimal.ROUND_HALF_UP));
				
				}
			}
			row.getCell("newBuildingPrice").getStyleAttributes()
					.setLocked(true);
			row.getCell("newBuildingPrice").getStyleAttributes().setBackground(
					Color.WHITE);
			row.getCell("newRoomPrice").getStyleAttributes().setLocked(false);
			row.getCell("newRoomPrice").getStyleAttributes().setBackground(
					FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		} else {
			if (buildingArea != null && row.getCell("newBuildingPrice").getValue() != null) {
				newAmount = buildingArea.multiply((BigDecimal) row.getCell("newBuildingPrice").getValue());
				newAmount = SHEHelper.getAmountAfterToInteger(newAmount, this.chkIsAutoToInteger.isSelected(), (ToIntegerTypeEnum)this.comboToIntegerType.getSelectedItem(), (DigitEnum)this.comboDigit.getSelectedItem());
				if (roomArea != null && roomArea.compareTo(FDCHelper.ZERO) != 0) {
					row.getCell("newRoomPrice").setValue(newAmount.divide(roomArea, 2, BigDecimal.ROUND_HALF_UP));
					
					row.getCell("buildingPriceBalance").setValue(FDCHelper.subtract(row.getCell("newBuildingPrice").getValue(), oldBuildingPrice));
					row.getCell("roomPriceBalance").setValue(FDCHelper.subtract(row.getCell("newRoomPrice").getValue(), oldRoomPrice));
					row.getCell("buildingPriceBalanceScale").setValue(FDCHelper.divide(FDCHelper.multiply(FDCHelper.ONE_HUNDRED, row.getCell("buildingPriceBalance").getValue()), oldBuildingPrice, 2, BigDecimal.ROUND_HALF_UP));
					row.getCell("roomPriceBalanceScale").setValue(FDCHelper.divide(FDCHelper.multiply(row.getCell("roomPriceBalance").getValue(), FDCHelper.ONE_HUNDRED),oldRoomPrice, 2, BigDecimal.ROUND_HALF_UP));
					
				}
			}
			row.getCell("newRoomPrice").getStyleAttributes().setLocked(true);
			row.getCell("newBuildingPrice").getStyleAttributes().setLocked(
					false);
			row.getCell("newRoomPrice").getStyleAttributes().setBackground(
					Color.WHITE);
			row.getCell("newBuildingPrice").getStyleAttributes().setBackground(
					FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		}
		
		
		updateColor(new ICell[]{row.getCell("buildingPriceBalance"),row.getCell("buildingPriceBalanceScale"), row.getCell("roomPriceBalance"), row.getCell("roomPriceBalanceScale")});
		row.getCell("newSumAmount").setValue(newAmount);
	}

	private void updateColor(ICell[] cells) {
		if(cells == null) return;
		for (int i = 0; i < cells.length; i++) {
			ICell cell = cells[i];
			if(cell.getValue()==null) {
				break;
			}
			if(FDCHelper.ZERO.compareTo((BigDecimal)cell.getValue()) > 0){
				cell.getStyleAttributes().setFontColor(Color.RED);
			}else if (FDCHelper.ZERO.compareTo((BigDecimal)cell.getValue()) < 0){
				cell.getStyleAttributes().setFontColor(Color.BLUE);
			}else {
				cell.getStyleAttributes().setFontColor(Color.BLACK);
			}
		}
		
	}

	private void setStatPrice() {
		setStatPrice(false);
	}
	
	/**
	 * 设置统计价格
	 * @param isActionOnload
	 * 			是否是在onload中调用该函数
	 * */
	private void setStatPrice(boolean isActionOnload) {
		BigDecimal oldSumSum = FDCHelper.ZERO;
		BigDecimal newSumSum = FDCHelper.ZERO;
		BigDecimal sumBuildingArea = FDCHelper.ZERO;
		BigDecimal sumRoomArea = FDCHelper.ZERO;
		int totalCount = 0;
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			if( row.getCell("roomNumber").getValue()!=null && !"".equals(row.getCell("roomNumber").getValue().toString())){
				totalCount++;
			}
			BigDecimal oldSumAmount = (BigDecimal) row.getCell("oldSumAmount")
					.getValue();
			if (oldSumAmount != null && row.getCell("roomNumber").getValue()!=null && !"".equals(row.getCell("roomNumber").getValue().toString())) {
				oldSumSum = oldSumSum.add(oldSumAmount);
			}
			BigDecimal newSumAmount = (BigDecimal) row.getCell("newSumAmount")
					.getValue();
			if (newSumAmount != null && row.getCell("roomNumber").getValue()!=null && !"".equals(row.getCell("roomNumber").getValue().toString())) {
				newSumSum = newSumSum.add(newSumAmount);
			}
			BigDecimal buildingArea = (BigDecimal) row.getCell("buildingArea")
					.getValue();
			if (buildingArea != null && row.getCell("roomNumber").getValue()!=null && !"".equals(row.getCell("roomNumber").getValue().toString())) {
				sumBuildingArea = sumBuildingArea.add(buildingArea);
			}

			BigDecimal roomArea = (BigDecimal) row.getCell("roomArea")
					.getValue();
			if (roomArea != null && row.getCell("roomNumber").getValue()!=null && !"".equals(row.getCell("roomNumber").getValue().toString())) {
				sumRoomArea = sumRoomArea.add(roomArea);
			}
		}
		this.txtOldTotalPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtNewTotalPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtNewBuildingAvgPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtNewRoomAvgPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtOldTotalPrice.setValue(oldSumSum.divide(FDCHelper.ONE,2,BigDecimal.ROUND_HALF_UP));
		if(!isActionOnload  &&  FDCHelper.MAX_TOTAL_VALUE2.compareTo(newSumSum) < 0){
			MsgBox.showInfo(this, "调后总价超出系统限制，计算结果错误，请修改。");
			this.abort();
		}
		this.txtNewTotalPrice.setValue(newSumSum.divide(FDCHelper.ONE,2,BigDecimal.ROUND_HALF_UP));
		this.txtTotalBuildingArea.setValue(sumBuildingArea.divide(FDCHelper.ONE,2,BigDecimal.ROUND_HALF_UP));
		this.txtTotalRoomArea.setValue(sumRoomArea.divide(FDCHelper.ONE,2,BigDecimal.ROUND_HALF_UP));
		
		this.totalAmountBalance.setValue(FDCHelper.subtract(this.txtNewTotalPrice.getBigDecimalValue(),this.txtOldTotalPrice.getBigDecimalValue()));
		this.totalAmountBalanceScale.setValue(FDCHelper.divide(FDCHelper.multiply(FDCHelper.ONE_HUNDRED, this.totalAmountBalance.getBigDecimalValue()), this.txtOldTotalPrice.getBigDecimalValue(), 2, BigDecimal.ROUND_HALF_UP));
	
		updateColor(new KDNumberTextField[]{totalAmountBalance, totalAmountBalanceScale});
		
		if(FDCHelper.ZERO.compareTo(this.totalAmountBalance.getBigDecimalValue()) > 0){
			this.totalAmountBalance.setForeground(Color.RED);
		}else if(FDCHelper.ZERO.compareTo(this.totalAmountBalance.getBigDecimalValue()) < 0){
			this.totalAmountBalance.setForeground(Color.BLUE);
		}
		
		
		if (sumBuildingArea != null
				&& sumBuildingArea.compareTo(FDCHelper.ZERO) != 0) {
			this.txtOldBuildingAvgPrice.setValue(oldSumSum.divide(
					sumBuildingArea, 2, BigDecimal.ROUND_HALF_UP));
		} else {
			this.txtOldBuildingAvgPrice.setValue(null);
		}
		if (sumRoomArea != null && sumRoomArea.compareTo(FDCHelper.ZERO) != 0) {
			this.txtOldRoomAvgPrice.setValue(oldSumSum.divide(sumRoomArea, 2,
					BigDecimal.ROUND_HALF_UP));
		} else {
			this.txtOldRoomAvgPrice.setValue(null);
		}
		if (sumBuildingArea != null
				&& sumBuildingArea.compareTo(FDCHelper.ZERO) != 0) {
			this.txtNewBuildingAvgPrice.setValue(newSumSum.divide(
					sumBuildingArea, 2, BigDecimal.ROUND_HALF_UP));
		} else {
			this.txtNewBuildingAvgPrice.setValue(null);
		}
		if (sumRoomArea != null && sumRoomArea.compareTo(FDCHelper.ZERO) != 0) {
			this.txtNewRoomAvgPrice.setValue(newSumSum.divide(sumRoomArea, 2,
					BigDecimal.ROUND_HALF_UP));
		} else {
			this.txtNewRoomAvgPrice.setValue(null);
		}
		this.txtTotalCount.setValue(new Integer(totalCount));
//		this.txtTotalCount.setValue(new Integer(this.tblRoom.getRowCount()));
	}

	private void updateColor(KDNumberTextField[] numberTextFields) {
		if(numberTextFields == null) return;
		for (int i = 0; i < numberTextFields.length; i++) {
			KDNumberTextField field = numberTextFields[i];
			if(field.getValue()==null) {
				break;
			}
			if(FDCHelper.ZERO.compareTo(field.getBigDecimalValue()) > 0){
				field.setCustomForegroundColor(Color.RED);
			}else if (FDCHelper.ZERO.compareTo(field.getBigDecimalValue()) < 0){
				field.setCustomForegroundColor(Color.BLUE);
			}else {
				field.setCustomForegroundColor(Color.BLACK);
			}
		}
	}

	protected void tblRoom_editStopped(KDTEditEvent e) throws Exception {
		super.tblRoom_editStopped(e);
		int rowIndex = e.getRowIndex();
		IRow row = tblRoom.getRow(rowIndex);
		updateRowData(row);
		this.setStatPrice();
		
		updateTotalRows();
	}

	protected void tblRoom_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
			if (true) {
				return;
			}
			if (this.getOprtState().equals("VIEW")) {
				return;
			}
			int rowIndex = e.getRowIndex();
			int colIndex = e.getColIndex();
			if (this.tblRoom.getColumnKey(colIndex).equals("isCalByRoom")) {
				IRow row = this.tblRoom.getRow(rowIndex);
				boolean isCalByRoomArea = ((Boolean) row.getCell("isCalByRoom")
						.getValue()).booleanValue();
				row.getCell("isCalByRoom").setValue(
						new Boolean(!isCalByRoomArea));
				updateRowData(row);
			}
			setStatPrice();
		}
	}

	protected void btnImportPrice_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnImportPrice_actionPerformed(e);
		String fileName = SHEHelper.showExcelSelectDlg(this);
		int count = SHEHelper.importExcelToTable2(fileName, this.tblRoom, 1, 3);
		MsgBox.showInfo("已经成功导入" + count + "条数据!");
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			
			if(row.getUserObject() instanceof String)
			{
				continue;
			}
			boolean isCalByRoomArea = ((Boolean) row.getCell("isCalByRoom")
					.getValue()).booleanValue();
			if (isCalByRoomArea) {
				BigDecimal newRoomPrice = (BigDecimal) row.getCell(
						"newRoomPrice").getValue();
				if (newRoomPrice != null
						&& newRoomPrice.compareTo(FDCHelper.ZERO) < 0) {
					row.getCell("newRoomPrice").setValue(FDCHelper.ZERO);
				}
			} else {
				BigDecimal newBuildingPrice = (BigDecimal) row.getCell(
						"newBuildingPrice").getValue();
				if (newBuildingPrice != null
						&& newBuildingPrice.compareTo(FDCHelper.ZERO) < 0) {
					row.getCell("newBuildingPrice").setValue(FDCHelper.ZERO);
				}
			}
			updateRowData(row);
		}
		
		//添加汇总行合计
		deleteTotalRows();
		addTotalRows();
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected KDTable getDetailTable() {
		// TODO 自动生成方法存根
		return null;
	}
	
	private void addTotalRows()
	{
		if(tblRoom.getRowCount() > 0)
		{
			if(tblRoom.getRowCount() == 1)
			{
				//获取楼栋
				if(tblRoom.getRow(0).getCell("building").getValue() != null)
				{
					IRow tmpRow = tblRoom.addRow();
					tmpRow.getStyleAttributes().setLocked(true);
					tmpRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
					tmpRow.getCell("newBuildingPrice").getStyleAttributes().setLocked(true);
					tmpRow.getCell("newRoomPrice").getStyleAttributes().setLocked(true);


					String tmpStr = String.valueOf(tblRoom.getRow(0).getCell("building").getValue());
					tmpStr = tmpStr+"合计";
					tmpRow.getCell("building").setValue(tmpStr);
					tmpRow.setUserObject(tmpStr);

					//计算总价
					tmpRow.getCell("oldSumAmount").setValue(FDCHelper.toBigDecimal(tblRoom.getRow(0).getCell("oldSumAmount").getValue()));
					
					//计算调后总价
					tmpRow.getCell("newSumAmount").setValue(FDCHelper.toBigDecimal(tblRoom.getRow(0).getCell("newSumAmount").getValue()));

					//计算建筑面积
					tmpRow.getCell("buildingArea").setValue(FDCHelper.toBigDecimal(tblRoom.getRow(0).getCell("buildingArea").getValue()));

					//计算建筑单价
					tmpRow.getCell("oldBuildingPrice").setValue(FDCHelper.divide(tmpRow.getCell("oldSumAmount").getValue(), 
							tmpRow.getCell("buildingArea").getValue()));
					
					//计算调后建筑单价
					tmpRow.getCell("newBuildingPrice").setValue(FDCHelper.divide(tmpRow.getCell("newSumAmount").getValue(), 
							tmpRow.getCell("buildingArea").getValue()));

					//计算套内面积
					tmpRow.getCell("roomArea").setValue(FDCHelper.toBigDecimal(tblRoom.getRow(0).getCell("roomArea").getValue()));

					//计算套内单价
					tmpRow.getCell("oldRoomPrice").setValue(FDCHelper.divide(tmpRow.getCell("oldSumAmount").getValue(), 
							tmpRow.getCell("roomArea").getValue()));
					
					//计算条后套内单价
					tmpRow.getCell("newRoomPrice").setValue(FDCHelper.divide(tmpRow.getCell("newSumAmount").getValue(), 
							tmpRow.getCell("roomArea").getValue()));

					//用单元和预测放号列的userObj存储开始行和终止行
					tmpRow.getCell("unit").setUserObject(new Integer(0));
					tmpRow.getCell("roomNumber").setUserObject(new Integer(0));
				}
			}
			else
			{
				int flagIndex = 0;
				while(flagIndex <=tblRoom.getRowCount()-1)
				{
					IRow flagRow = tblRoom.getRow(flagIndex);

					if(flagRow.getCell("building").getValue() != null)
					{
						String flagBuild = String.valueOf(flagRow.getCell("building").getValue());

						int rowIndex = flagIndex+1;
						while(rowIndex <=tblRoom.getRowCount()-1)
						{
							IRow tmpRow = tblRoom.getRow(rowIndex);
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
						IRow tmpSum = tblRoom.addRow(rowIndex);
						tmpSum.setUserObject(flagBuild+"合计");

						tmpSum.getStyleAttributes().setLocked(true);
						tmpSum.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
						tmpSum.getCell("newBuildingPrice").getStyleAttributes().setLocked(true);
						tmpSum.getCell("newRoomPrice").getStyleAttributes().setLocked(true);

						tmpSum.getCell("unit").setUserObject(new Integer(flagIndex));
						tmpSum.getCell("roomNumber").setUserObject(new Integer(rowIndex-1));

						BigDecimal tmpOldSumAmt = FDCHelper.ZERO;
						BigDecimal tmpNewSumAmt = FDCHelper.ZERO;
						
						BigDecimal tmpBuildArea = FDCHelper.ZERO;
						BigDecimal tmpRoomArea = FDCHelper.ZERO;

						for(int tmpIndex = flagIndex; tmpIndex < rowIndex; ++tmpIndex)
						{
							tmpOldSumAmt = FDCHelper.add(tmpOldSumAmt, tblRoom.getRow(tmpIndex).getCell("oldSumAmount").getValue());
							tmpNewSumAmt = FDCHelper.add(tmpNewSumAmt, tblRoom.getRow(tmpIndex).getCell("newSumAmount").getValue());
							tmpBuildArea = FDCHelper.add(tmpBuildArea, tblRoom.getRow(tmpIndex).getCell("buildingArea").getValue());
							tmpRoomArea = FDCHelper.add(tmpRoomArea, tblRoom.getRow(tmpIndex).getCell("roomArea").getValue());
						}

						tmpSum.getCell("building").setValue(flagBuild+"合计");
						
						tmpSum.getCell("oldSumAmount").setValue(tmpOldSumAmt);
						tmpSum.getCell("newSumAmount").setValue(tmpNewSumAmt);
						
						tmpSum.getCell("buildingArea").setValue(tmpBuildArea);
						tmpSum.getCell("roomArea").setValue(tmpRoomArea);
						
						tmpSum.getCell("oldBuildingPrice").setValue(FDCHelper.divide(tmpOldSumAmt, tmpBuildArea));
						tmpSum.getCell("oldRoomPrice").setValue(FDCHelper.divide(tmpOldSumAmt, tmpRoomArea));
						
						tmpSum.getCell("newBuildingPrice").setValue(FDCHelper.divide(tmpNewSumAmt, tmpBuildArea));
						tmpSum.getCell("newRoomPrice").setValue(FDCHelper.divide(tmpNewSumAmt, tmpRoomArea));

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
	
	protected void deleteTotalRows()
	{
		for(int rowIndex = tblRoom.getRowCount()-1; rowIndex >= 0; --rowIndex)
		{
			IRow tmpRow = tblRoom.getRow(rowIndex);
			if(tmpRow.getUserObject() instanceof String)
			{
				tblRoom.removeRow(rowIndex);
			}
		}
	}
	
	private void updateTotalRows()
	{
		for(int rowIndex = tblRoom.getRowCount()-1; rowIndex >=0; --rowIndex)
		{
			IRow tmpRow = tblRoom.getRow(rowIndex);
			if(tmpRow.getUserObject() instanceof String)
			{
				Integer start = (Integer)tmpRow.getCell("unit").getUserObject();
				Integer end = (Integer)tmpRow.getCell("roomNumber").getUserObject();

				int startIndex = start.intValue();
				int endIndex = end.intValue();


				BigDecimal tmpOldSumAmt = FDCHelper.ZERO;
				BigDecimal tmpNewSumAmt = FDCHelper.ZERO;
				
				BigDecimal tmpBuildArea = FDCHelper.ZERO;
				BigDecimal tmpRoomArea = FDCHelper.ZERO;
				
//				BigDecimal tmpBuildPriceBalance = FDCHelper.ZERO;
//				BigDecimal tmpRoomPriceBalance = FDCHelper.ZERO;
//				BigDecimal tmpBuildingPriceBalanceScale = FDCHelper.ZERO;
//				BigDecimal tmpRoomPriceBalanceScale = FDCHelper.ZERO;

				for(int tmpIndex = startIndex; tmpIndex <= endIndex; ++tmpIndex)
				{
					tmpOldSumAmt = FDCHelper.add(tmpOldSumAmt, tblRoom.getRow(tmpIndex).getCell("oldSumAmount").getValue());
					tmpNewSumAmt = FDCHelper.add(tmpNewSumAmt, tblRoom.getRow(tmpIndex).getCell("newSumAmount").getValue());
					tmpBuildArea = FDCHelper.add(tmpBuildArea, tblRoom.getRow(tmpIndex).getCell("buildingArea").getValue());
					tmpRoomArea = FDCHelper.add(tmpRoomArea, tblRoom.getRow(tmpIndex).getCell("roomArea").getValue());
					
//					tmpBuildPriceBalance = FDCHelper.add(tmpBuildPriceBalance, tblRoom.getRow(tmpIndex).getCell("buildingPriceBalance").getValue());
//					tmpRoomPriceBalance = FDCHelper.add(tmpRoomPriceBalance, tblRoom.getRow(tmpIndex).getCell("roomPriceBalance").getValue());
//					tmpBuildingPriceBalanceScale = FDCHelper.add(tmpBuildingPriceBalanceScale, tblRoom.getRow(tmpIndex).getCell("buildingPriceBalanceScale").getValue());
//					tmpRoomPriceBalanceScale = FDCHelper.add(tmpRoomPriceBalanceScale, tblRoom.getRow(tmpIndex).getCell("roomPriceBalanceScale").getValue());
				
				}

				
				tmpRow.getCell("oldSumAmount").setValue(tmpOldSumAmt);
				tmpRow.getCell("newSumAmount").setValue(tmpNewSumAmt);
				
				tmpRow.getCell("buildingArea").setValue(tmpBuildArea);
				tmpRow.getCell("roomArea").setValue(tmpRoomArea);
				
				tmpRow.getCell("oldBuildingPrice").setValue(FDCHelper.divide(tmpOldSumAmt, tmpBuildArea));
				tmpRow.getCell("oldRoomPrice").setValue(FDCHelper.divide(tmpOldSumAmt, tmpRoomArea));
				
				tmpRow.getCell("newBuildingPrice").setValue(FDCHelper.divide(tmpNewSumAmt, tmpBuildArea));
				tmpRow.getCell("newRoomPrice").setValue(FDCHelper.divide(tmpNewSumAmt, tmpRoomArea));
			}
		}
	}
	
	protected void initListener() {
		// TODO Auto-generated method stub
		//super.initListener();
	}
	
	private Collator comparator = Collator.getInstance(Locale.getDefault());
	
	private int doCompare(IRow tmpRow1, IRow tmpRow2)
	{
		//building, unit, roomNumber
		int flag = 0;
		String str1 = getCompareStr(tmpRow1);
		String str2 = getCompareStr(tmpRow2);
		
		flag = comparator.compare(str1, str2);
		
		return flag;
	}
	
	private String getCompareStr(IRow tmpRow)
	{
		String tmpStr = new String();
		
		if(tmpRow.getCell("building").getValue() != null)
		{
			tmpStr += String.valueOf(tmpRow.getCell("building").getValue());
		}
		else
		{
			tmpStr += "栋";
		}
		
		if(tmpRow.getCell("unit").getValue() != null)
		{
			tmpStr += String.valueOf(tmpRow.getCell("unit").getValue());
		}
		else
		{
			tmpStr += "单元";
		}
		
		if(tmpRow.getCell("roomNumber").getValue() != null)
		{
			tmpStr += String.valueOf(tmpRow.getCell("roomNumber").getValue());
		}
		
		return tmpStr;
	}
	
	protected void sortCurRows(KDTable table)
	{
		int cout = table.getRowCount();
		IRow[] rowArray = new IRow[cout];
		for(int rowIndex = 0; rowIndex < table.getRowCount(); ++rowIndex)
		{
			rowArray[rowIndex] = table.getRow(rowIndex);
		}
		
		tblRoom.removeRows();
		
		for(int rowIndex = 1; rowIndex < rowArray.length; ++rowIndex)
		{
			IRow tmpRow = rowArray[rowIndex];
			
			int flag = rowIndex-1;
			while(flag >=0)
			{
				IRow tmpFlagRow = rowArray[flag];
				if(doCompare(tmpRow, tmpFlagRow) >= 0)
				{
					break;
				}
				else
				{
					rowArray[flag+1] = rowArray[flag];
					--flag;
				}
			}
			
			rowArray[flag+1] = tmpRow;
		}
		
		for(int rowIndex = 0; rowIndex < rowArray.length; ++rowIndex)
		{
			table.addRow(rowIndex, rowArray[rowIndex]);
		}
	}
}
