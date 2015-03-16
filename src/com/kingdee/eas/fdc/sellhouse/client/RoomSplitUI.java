/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.math.BigDecimal;

import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomSplitUI extends AbstractRoomSplitUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomSplitUI.class);

	/**
	 * output class constructor
	 */
	public RoomSplitUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	private void verify() throws EASBizException, BOSException {
		RoomInfo room = (RoomInfo) this.tblRooms.getUserObject();
		for (int i = 0; i < this.tblRooms.getRowCount() - 1; i++) {
			IRow row = this.tblRooms.getRow(i);

			BigDecimal num = (BigDecimal) row.getCell("serialNumber").getValue();
			if (num == null) {
				MsgBox.showInfo("序号不能为空!");
				this.abort();
			}
			String number = (String) row.getCell("number").getValue();
			if (number == null) {
				MsgBox.showInfo("房间编号不能为空!");
				this.abort();
			}
			String roomNo = (String) row.getCell("roomNo").getValue();

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building", room.getBuilding().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("unit", new Integer(room.getUnit())));
			filter.getFilterItems().add(new FilterItemInfo("floor", new Integer(room.getFloor())));
			filter.getFilterItems().add(new FilterItemInfo("id", room.getId().toString(), CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("serialNumber", new Integer(num.intValue())));
			if (RoomFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("序号:" + num.intValue() + "已存在!");
				this.abort();
			}
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building", room.getBuilding().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			filter.getFilterItems().add(new FilterItemInfo("id", room.getId().toString(), CompareType.NOTEQUALS));
			if (RoomFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("预测房号:" + number + "已存在!");
				this.abort();
			}
			if (roomNo != null && roomNo.length() > 0) {
				if (roomNo.length() > 44) {
					MsgBox.showInfo("实测房号长度不能大于44个字符！");
					this.abort();
				}
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("building", room.getBuilding().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("roomNo", roomNo));
				filter.getFilterItems().add(new FilterItemInfo("id", room.getId().toString(), CompareType.NOTEQUALS));
				if (RoomFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("实测房号:" + roomNo + "已存在!");
					this.abort();
				}
			}

			BigDecimal buildingArea = (BigDecimal) row.getCell("buildingArea").getValue();
			BigDecimal roomArea = (BigDecimal) row.getCell("roomArea").getValue();
			if (buildingArea == null) {
				buildingArea = FDCHelper.ZERO;
			}
			if (roomArea == null) {
				roomArea = FDCHelper.ZERO;
			}
			BigDecimal appArea = buildingArea.subtract(roomArea);
			if (appArea.compareTo(FDCHelper.ZERO) < 0) {
				MsgBox.showInfo("房间编号:" + number + "套内面积不能大于建筑面积!");
				this.abort();
			}
		}
		IRow sumRow = this.tblRooms.getRow(this.tblRooms.getRowCount() - 1);

		BigDecimal buildingArea = this.txtBuildingArea.getBigDecimalValue();
		if (buildingArea == null) {
			buildingArea = FDCHelper.ZERO;
		}
		if (((BigDecimal) sumRow.getCell("buildingArea").getValue()).compareTo(buildingArea) != 0) {
			MsgBox.showInfo("建筑面积汇总值不等于原房间!");
			this.abort();
		}

		BigDecimal roomArea = this.txtRoomArea.getBigDecimalValue();
		if (roomArea == null) {
			roomArea = FDCHelper.ZERO;
		}
		if (((BigDecimal) sumRow.getCell("roomArea").getValue()).compareTo(roomArea) != 0) {
			MsgBox.showInfo("套内面积汇总值不等于原房间!");
			this.abort();
		}
		
		//by tim_gao 计租面积
		BigDecimal tenancyArea = this.txtTenancyArea.getBigDecimalValue();
		if (tenancyArea == null) {
			tenancyArea = FDCHelper.ZERO;
		}
		if (((BigDecimal) sumRow.getCell("tenancyArea").getValue()).compareTo(tenancyArea) != 0) {
			MsgBox.showInfo("计租面积汇总值不等于原房间!");
			this.abort();
		}

		BigDecimal actBuildingArea = this.txtActualBuildingArea.getBigDecimalValue();
		if (actBuildingArea == null) {
			actBuildingArea = FDCHelper.ZERO;
		}
		if (((BigDecimal) sumRow.getCell("actualBuildingArea").getValue()).compareTo(actBuildingArea) != 0) {
			MsgBox.showInfo("实测建筑面积汇总值不等于原房间!");
			this.abort();
		}

		BigDecimal actRoomArea = this.txtActualRoomArea.getBigDecimalValue();
		if (actRoomArea == null) {
			actRoomArea = FDCHelper.ZERO;
		}
		if (((BigDecimal) sumRow.getCell("actualRoomArea").getValue()).compareTo(actRoomArea) != 0) {
			MsgBox.showInfo("实测套内面积汇总值不等于原房间!");
			this.abort();
		}

		BigDecimal apportionArea = this.txtApportionArea.getBigDecimalValue();
		if (apportionArea == null) {
			apportionArea = FDCHelper.ZERO;
		}
		if (((BigDecimal) sumRow.getCell("apportionArea").getValue()).compareTo(apportionArea) != 0) {
			MsgBox.showInfo("分摊面积汇总值不等于原房间!");
			this.abort();
		}

		BigDecimal balconyArea = this.txtBalconyArea.getBigDecimalValue();
		if (balconyArea == null) {
			balconyArea = FDCHelper.ZERO;
		}
		if (((BigDecimal) sumRow.getCell("balconyArea").getValue()).compareTo(balconyArea) != 0) {
			MsgBox.showInfo("阳台面积汇总值不等于原房间!");
			this.abort();
		}

		BigDecimal guardenArea = this.txtGuardenArea.getBigDecimalValue();
		if (guardenArea == null) {
			guardenArea = FDCHelper.ZERO;
		}
		if (((BigDecimal) sumRow.getCell("guardenArea").getValue()).compareTo(guardenArea) != 0) {
			MsgBox.showInfo("花园面积汇总值不等于原房间!");
			this.abort();
		}

		BigDecimal carbarnArea = this.txtCarbarnArea.getBigDecimalValue();
		if (carbarnArea == null) {
			carbarnArea = FDCHelper.ZERO;
		}
		if (((BigDecimal) sumRow.getCell("carbarnArea").getValue()).compareTo(carbarnArea) != 0) {
			MsgBox.showInfo("车库面积汇总值不等于原房间!");
			this.abort();
		}

		BigDecimal useArea = this.txtUseArea.getBigDecimalValue();
		if (useArea == null) {
			useArea = FDCHelper.ZERO;
		}
		if (((BigDecimal) sumRow.getCell("useArea").getValue()).compareTo(useArea) != 0) {
			MsgBox.showInfo("使用面积汇总值不等于原房间!");
			this.abort();
		}

		BigDecimal flatArea = this.txtFlatArea.getBigDecimalValue();
		if (flatArea == null) {
			flatArea = FDCHelper.ZERO;
		}
		if (((BigDecimal) sumRow.getCell("flatArea").getValue()).compareTo(flatArea) != 0) {
			MsgBox.showInfo("平台面积汇总值不等于原房间!");
			this.abort();
		}
	}

	/**
	 * output btnYes_actionPerformed method
	 */
	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.btnYes_actionPerformed(e);
		verify();
		RoomInfo room = (RoomInfo) this.tblRooms.getUserObject();
		RoomFactory.getRemoteInstance().delete(new ObjectUuidPK(room.getId()));
		for (int i = 0; i < this.tblRooms.getRowCount() - 1; i++) {
			RoomInfo newRoom = (RoomInfo) room.clone();
			newRoom.setId(BOSUuid.create(newRoom.getBOSType()));
			IRow row = this.tblRooms.getRow(i);

			newRoom.setSerialNumber(((BigDecimal) row.getCell("serialNumber").getValue()).intValue());
			newRoom.setEndSerialNumber(((BigDecimal) row.getCell("serialNumber").getValue()).intValue());
			newRoom.setNumber((String) row.getCell("number").getValue());
			newRoom.setRoomNo((String) row.getCell("roomNo").getValue());
			newRoom.setRoomPropNo((String) row.getCell("roomPropNo").getValue());

			newRoom.setName((String) row.getCell("number").getValue());
			newRoom.setBuildingArea((BigDecimal) row.getCell("buildingArea").getValue());
			newRoom.setRoomArea((BigDecimal) row.getCell("roomArea").getValue());
			newRoom.setActualBuildingArea((BigDecimal) row.getCell("actualBuildingArea").getValue());
			newRoom.setActualRoomArea((BigDecimal) row.getCell("actualRoomArea").getValue());
			//by tim_gao 2010-12-15  加入计租面积
			newRoom.setTenancyArea((BigDecimal) row.getCell("tenancyArea").getValue());
			
			newRoom.setApportionArea((BigDecimal) row.getCell("apportionArea").getValue());
			newRoom.setBalconyArea((BigDecimal) row.getCell("balconyArea").getValue());
			newRoom.setGuardenArea((BigDecimal) row.getCell("guardenArea").getValue());
			newRoom.setCarbarnArea((BigDecimal) row.getCell("carbarnArea").getValue());
			newRoom.setUseArea((BigDecimal) row.getCell("useArea").getValue());
			newRoom.setFlatArea((BigDecimal) row.getCell("flatArea").getValue());
			// 将租赁的定价清掉
			newRoom.setRentType(null);
			newRoom.setStandardRent(null);
			newRoom.setStandardRentPrice(null);

			RoomFactory.getRemoteInstance().submit(newRoom);
		}
		this.getUIContext().put("isEdit", Boolean.TRUE);
		this.destroyWindow();
	}

	private void setTxtFormat(KDFormattedTextField txtField) {
		txtField.setRemoveingZeroInDispaly(false);
		txtField.setRemoveingZeroInEdit(false);
		txtField.setNegatived(false);
		txtField.setPrecision(3);
		txtField.setHorizontalAlignment(JTextField.RIGHT);
		txtField.setSupportedEmpty(true);
	}

	public void onLoad() throws Exception {
		setTxtFormat(this.txtBuildingArea);
		setTxtFormat(this.txtRoomArea);
		setTxtFormat(this.txtApportionArea);
		setTxtFormat(this.txtBalconyArea);
		setTxtFormat(this.txtGuardenArea);
		setTxtFormat(this.txtCarbarnArea);
		setTxtFormat(this.txtUseArea);
		setTxtFormat(this.txtFlatArea);
		setTxtFormat(this.txtActualBuildingArea);
		setTxtFormat(this.txtActualRoomArea);
		setTxtFormat(this.txtTenancyArea);
		
		super.onLoad();
		tblRooms.checkParsed();
		tblRooms.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		int snumberIndex = tblRooms.getColumn("serialNumber").getColumnIndex();
		tblRooms.getColumn(snumberIndex).getStyleAttributes().setLocked(false);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(snumberIndex);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		tblRooms.getColumn(snumberIndex).setEditor(numberEditor);
		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(3);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		//by tim_gao 从计租面积开始
		int bAreaIndex;
		if(null==tblRooms.getColumn("tenancyArea")||("").equals(tblRooms.getColumn("tenancyArea"))){
			bAreaIndex=0;
		}else{
		bAreaIndex = tblRooms.getColumn("tenancyArea").getColumnIndex();
		}
		for (int i = bAreaIndex; i < this.tblRooms.getColumnCount(); i++) {

			tblRooms.getColumn(i).setEditor(numberEditor);
			tblRooms.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			tblRooms.getColumn(i).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
			tblRooms.getColumn(i).getStyleAttributes().setLocked(false);
		}
		tblRooms.getColumn("apportionArea").getStyleAttributes().setLocked(true);
		String roomId = (String) this.getUIContext().get("roomId");
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("building.*");
		sels.add("buildingFloor.*");

		RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId)), sels);
		this.tblRooms.setUserObject(room);
		this.txtNumber.setText(room.getNumber());
		this.txtSerialNumber.setText(String.valueOf(room.getSerialNumber()));
		this.txtBuildingArea.setValue(room.getBuildingArea());
		this.txtRoomArea.setValue(room.getRoomArea());
		this.txtApportionArea.setValue(room.getApportionArea());
		this.txtBalconyArea.setValue(room.getBalconyArea());
		this.txtGuardenArea.setValue(room.getGuardenArea());
		this.txtCarbarnArea.setValue(room.getCarbarnArea());
		this.txtUseArea.setValue(room.getUseArea());
		this.txtFlatArea.setValue(room.getFlatArea());
		this.txtActualBuildingArea.setValue(room.getActualBuildingArea());
		this.txtActualRoomArea.setValue(room.getActualRoomArea());
		this.txtTenancyArea.setValue(room.getTenancyArea());

		// 如果面积为空或为0，则不显示拆分的房间列的面积，确认时也不用校验
		if (room.getBuildingArea() == null || room.getBuildingArea().compareTo(FDCHelper.ZERO) == 0)
			this.tblRooms.getColumn("buildingArea").getStyleAttributes().setHided(true);
		if (room.getRoomArea() == null || room.getRoomArea().compareTo(FDCHelper.ZERO) == 0)
			this.tblRooms.getColumn("roomArea").getStyleAttributes().setHided(true);
		if (room.getApportionArea() == null || room.getApportionArea().compareTo(FDCHelper.ZERO) == 0)
			this.tblRooms.getColumn("apportionArea").getStyleAttributes().setHided(true);
		if (room.getBalconyArea() == null || room.getBalconyArea().compareTo(FDCHelper.ZERO) == 0)
			this.tblRooms.getColumn("balconyArea").getStyleAttributes().setHided(true);
		if (room.getGuardenArea() == null || room.getGuardenArea().compareTo(FDCHelper.ZERO) == 0)
			this.tblRooms.getColumn("guardenArea").getStyleAttributes().setHided(true);
		if (room.getCarbarnArea() == null || room.getCarbarnArea().compareTo(FDCHelper.ZERO) == 0)
			this.tblRooms.getColumn("carbarnArea").getStyleAttributes().setHided(true);
		if (room.getUseArea() == null || room.getUseArea().compareTo(FDCHelper.ZERO) == 0)
			this.tblRooms.getColumn("useArea").getStyleAttributes().setHided(true);
		if (room.getFlatArea() == null || room.getFlatArea().compareTo(FDCHelper.ZERO) == 0)
			this.tblRooms.getColumn("flatArea").getStyleAttributes().setHided(true);
		if (room.getActualBuildingArea() == null || room.getActualBuildingArea().compareTo(FDCHelper.ZERO) == 0)
			this.tblRooms.getColumn("actualBuildingArea").getStyleAttributes().setHided(true);
		if (room.getActualRoomArea() == null || room.getActualRoomArea().compareTo(FDCHelper.ZERO) == 0)
			this.tblRooms.getColumn("actualRoomArea").getStyleAttributes().setHided(true);
		//by tim_gao
		if (room.getTenancyArea() == null || room.getTenancyArea().compareTo(FDCHelper.ZERO) == 0)
			this.tblRooms.getColumn("tenancyArea").getStyleAttributes().setHided(true);
		
		SpinnerNumberModel model = new SpinnerNumberModel(2, 2, 20, 1);
		spiSplitCount.setModel(model);
		setTable();
	}

	protected void spiSplitCount_stateChanged(ChangeEvent e) throws Exception {
		super.spiSplitCount_stateChanged(e);
		setTable();
	}

	private void setTable() throws EASBizException, BOSException {
		RoomInfo room = (RoomInfo) this.tblRooms.getUserObject();
		this.tblRooms.removeRows();
		Integer count = (Integer) this.spiSplitCount.getValue();
		
		int curNumber = room.getSerialNumber() - 1;
		for (int i = 0; i < count.intValue(); i++) {
			IRow row = this.tblRooms.addRow();
			curNumber = getUsableSerialNumber(curNumber);
			row.getCell("serialNumber").setValue(new BigDecimal(curNumber));
			//by tim_gao 得到平均拆分房间的值
			getAverRoomArea(count,i,room);
			updateNumber(i);
			BigDecimal builidngArea =(BigDecimal)row.getCell("buildingArea").getValue();
			BigDecimal roomArea =(BigDecimal)row.getCell("roomArea").getValue();
			if(builidngArea==null|| roomArea==null){
				row.getCell("apportionArea").setValue(FDCHelper.ZERO);
			}
		}
		IRow sumRow = this.tblRooms.addRow();
		tblRooms.getGroupManager().setTotalize(true);
		sumRow.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		sumRow.getStyleAttributes().setLocked(true);
		sumRow.getStyleAttributes().setHided(true);
		sumRow.getCell("number").setValue("合计");
		setUnionData();
		tblRooms.getRow(1).getCell("apportionArea").getValue();
	}
	
	/**
	 * @author Tim_gao
	 * @date 2010-12-15
	 * @description 根据拆分个数带出房间面积的平均数
	 */
	public void getAverRoomArea(Integer rowCount,int rowIndex,RoomInfo room){
		if(!(rowIndex==rowCount.intValue()-1)){//当不等于最后一行的时候 每行位总数的均值
			if (room.getBuildingArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getBuildingArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("buildingArea").setValue(FDCHelper.divide(room.getBuildingArea(), rowCount,3,4));}
			if (room.getRoomArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getRoomArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("roomArea").setValue(FDCHelper.divide(room.getRoomArea(), rowCount,3,4));}
			if (room.getApportionArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getApportionArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("apportionArea").setValue(FDCHelper.divide(room.getApportionArea(), rowCount,3,4));}
			if (room.getBalconyArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getBalconyArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("balconyArea").setValue(FDCHelper.divide(room.getBalconyArea(), rowCount,3,4));}
			if (room.getGuardenArea() != null ){
				if( FDCHelper.ZERO.compareTo((BigDecimal)room.getGuardenArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("guardenArea").setValue(FDCHelper.divide(room.getGuardenArea(), rowCount,3,4));}
			if (room.getCarbarnArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getCarbarnArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("carbarnArea").setValue(FDCHelper.divide((BigDecimal)room.getCarbarnArea(), rowCount,3,4));}
			if (room.getUseArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getUseArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("useArea").setValue(FDCHelper.divide(room.getUseArea(), rowCount,3,4));}
			if (room.getFlatArea() != null ){
				if(FDCHelper.ZERO.compareTo( (BigDecimal)room.getFlatArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("flatArea").setValue(FDCHelper.divide(room.getFlatArea(), rowCount,3,4));}
			if (room.getActualBuildingArea() != null  ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getActualBuildingArea())!= 0)
				tblRooms.getRow(rowIndex).getCell("actualBuildingArea").setValue(FDCHelper.divide(room.getActualBuildingArea(), rowCount,3,4));}
			if (room.getActualRoomArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getActualRoomArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("actualRoomArea").setValue(FDCHelper.divide(room.getActualRoomArea(), rowCount,3,4));}
			if (room.getTenancyArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getTenancyArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("tenancyArea").setValue(FDCHelper.divide(room.getTenancyArea(), rowCount,3,4));}
		}
		else{//当为最后一行是，本行= 总行 - 之前行的值之和; 
			if (room.getBuildingArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getBuildingArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("buildingArea").setValue(room.getBuildingArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getBuildingArea(), rowCount,3,4),new Integer(rowIndex))));}
			if (room.getRoomArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getRoomArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("roomArea").setValue(room.getRoomArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getRoomArea(), rowCount,3,4),new Integer(rowIndex))));}
			if (room.getApportionArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getApportionArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("apportionArea").setValue(room.getApportionArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getApportionArea(), rowCount,3,4),new Integer(rowIndex))));}
			if (room.getBalconyArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getBalconyArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("balconyArea").setValue(room.getBalconyArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getBalconyArea(), rowCount,3,4),new Integer(rowIndex))));}
			if (room.getGuardenArea() != null ){
				if( FDCHelper.ZERO.compareTo((BigDecimal)room.getGuardenArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("guardenArea").setValue(room.getGuardenArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getGuardenArea(), rowCount,3,4),new Integer(rowIndex))));}
			if (room.getCarbarnArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getCarbarnArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("carbarnArea").setValue(room.getCarbarnArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getCarbarnArea(), rowCount,3,4),new Integer(rowIndex))));}
			if (room.getUseArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getUseArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("useArea").setValue(room.getUseArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getUseArea(), rowCount,3,4),new Integer(rowIndex))));}
			if (room.getFlatArea() != null ){
				if(FDCHelper.ZERO.compareTo( (BigDecimal)room.getFlatArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("flatArea").setValue(room.getFlatArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getFlatArea(), rowCount,3,4),new Integer(rowIndex))));}
			if (room.getActualBuildingArea() != null  ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getActualBuildingArea())!= 0)
				tblRooms.getRow(rowIndex).getCell("actualBuildingArea").setValue(room.getActualBuildingArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getActualBuildingArea(), rowCount,3,4),new Integer(rowIndex))));}
			if (room.getActualRoomArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getActualRoomArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("actualRoomArea").setValue(room.getActualRoomArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getActualRoomArea(), rowCount,3,4),new Integer(rowIndex))));}
			//by tim_gao 计租面积拆分 2011-2-22
			if (room.getTenancyArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getTenancyArea()) != 0)
				tblRooms.getRow(rowIndex).getCell("tenancyArea").setValue(room.getTenancyArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getTenancyArea() ,rowCount,3,4),new Integer(rowIndex))));}
		}
	}
	
	public int getUsableSerialNumber(int curNumber) throws EASBizException, BOSException {
		RoomInfo room = (RoomInfo) this.tblRooms.getUserObject();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("building");
		sel.add("unit");
		sel.add("buildUnit.id");
		sel.add("floor");
		sel.add("buildingFloor.id");
		sel.add("tenancyArea");
		room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId().toString()), sel);

		curNumber++;
		if (curNumber <= room.getEndSerialNumber()) {
			return curNumber;
		} else {
			FilterInfo filter = new FilterInfo();
			if (room.getBuildUnit() != null) {
				filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", room.getBuildUnit().getId().toString()));
			}
			if (room.getBuildingFloor() != null) {
				filter.getFilterItems().add(new FilterItemInfo("buildingFloor.id", room.getBuildingFloor().getId().toString()));
			}
			filter.getFilterItems().add(new FilterItemInfo("building", room.getBuilding().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("id", room.getId().toString(), CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("serialNumber", new Integer(curNumber), CompareType.LESS_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("endSerialNumber", new Integer(curNumber), CompareType.GREATER_EQUALS));

			if (RoomFactory.getRemoteInstance().exists(filter)) {
				return this.getUsableSerialNumber(curNumber);
			} else {
				return curNumber;
			}
		}
	}

	/**
	 * output btnNo_actionPerformed method
	 */
	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.btnNo_actionPerformed(e);
		this.destroyWindow();
	}

	public static boolean showEditUI(CoreUI ui, String roomId) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("roomId", roomId);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSplitUI.class.getName(), uiContext, null, "EDIT");
		uiWindow.show();
		Object isEdit = uiWindow.getUIObject().getUIContext().get("isEdit");
		if (isEdit != null) {
			return true;
		}
		return false;
	}

	protected void tblRooms_editStopped(KDTEditEvent e) throws Exception {
		super.tblRooms_editStopped(e);
		this.tblRooms.getScriptManager().run();
		int rowIndex = e.getRowIndex();
		int bAreaIndex = tblRooms.getColumn("buildingArea").getColumnIndex();
		int rAreaIndex = tblRooms.getColumn("roomArea").getColumnIndex();
		int aAreaIndex = tblRooms.getColumn("apportionArea").getColumnIndex();

		if (e.getColIndex() == 0) {
			updateNumber(rowIndex);
		} else {
			if (e.getColIndex() == bAreaIndex || e.getColIndex() == rAreaIndex) {
				IRow row = this.tblRooms.getRow(rowIndex);
				BigDecimal buildingArea = (BigDecimal) row.getCell(bAreaIndex).getValue();
				BigDecimal roomArea = (BigDecimal) row.getCell(rAreaIndex).getValue();
				if (buildingArea == null) {
					buildingArea = FDCHelper.ZERO;
				}
				if (roomArea == null) {
					roomArea = FDCHelper.ZERO;
				}
				BigDecimal appArea = buildingArea.subtract(roomArea);
				row.getCell(aAreaIndex).setValue(appArea);
			}
			setUnionData();
		}
	}

	private void updateNumber(int rowIndex) {
		IRow row = this.tblRooms.getRow(rowIndex);
		if (row.getCell("serialNumber").getValue() == null) {
			return;
		}
		int num = ((BigDecimal) row.getCell("serialNumber").getValue()).intValue();
		String strNum = num + "";
		if (num < 10) {
			strNum = "0" + strNum;
		}
		RoomInfo room = (RoomInfo) this.tblRooms.getUserObject();
		BuildingInfo build = room.getBuilding();
		String number = "";
		if (build.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
			if (room.getBuildingFloor() != null && room.getBuildingFloor().getFloorAlias() != null) {
				number = room.getUnit() + "-" + room.getBuildingFloor().getFloorAlias() + "" + strNum;
			} else {
				number = room.getUnit() + "-" + room.getFloor() + "" + strNum;
			}
		} else if (build.getCodingType().equals(CodingTypeEnum.FloorNum)) {
			if (room.getBuildingFloor() != null && room.getBuildingFloor().getFloorAlias() != null) {
				number = room.getBuildingFloor().getFloorAlias() + "" + strNum;
			} else {
				number = room.getFloor() + "" + strNum;
			}
		} else if (build.getCodingType().equals(CodingTypeEnum.Num)) {
			number = strNum;
		}
		row.getCell("number").setValue(number);
		row.getCell("roomPropNo").setValue(number); // 拆分后初始物业房号与预测房号保持一致
		// luxiaoling 091124
	}

	private void setUnionData() {
		int bAreaIndex;
		if(null==tblRooms.getColumn("tenancyArea")||("").equals(tblRooms.getColumn("tenancyArea"))){
			bAreaIndex=0;
		}else{
		bAreaIndex = tblRooms.getColumn("tenancyArea").getColumnIndex();
		}
		for (int i = bAreaIndex; i < this.tblRooms.getColumnCount(); i++) {
			String key = this.tblRooms.getColumnKey(i);
			BigDecimal sum = FDCHelper.ZERO;
			for (int j = 0; j < this.tblRooms.getRowCount() - 1; j++) {
				BigDecimal value = (BigDecimal) FDCHelper.toBigDecimal(this.tblRooms.getCell(j, key).getValue());
				if (value != null) {
					sum = sum.add(value);
				}
			}
			this.tblRooms.getRow(this.tblRooms.getRowCount() - 1).getCell(key).setValue(sum);
		}
	}
}