/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.print.ui.component.TableCell;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AgioBillFactory;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;
import com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AgioRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioRoomEntryInfo;
//import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AgioEditUI extends AbstractAgioEditUI {
	private static final Logger logger = CoreUIObject.getLogger(AgioEditUI.class);

	/**
	 * output class constructor
	 */
	public AgioEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		AgioBillInfo agioBill = (AgioBillInfo) this.editData;
		agioBill.setIsEnabled(false);
		agioBill.getRoomEntry().clear();
		for (int j = 0; j < this.kdtRoomEntry.getRowCount(); j++) {
			AgioRoomEntryInfo roomEntry = (AgioRoomEntryInfo) this.kdtRoomEntry.getRow(j).getUserObject();
			agioBill.getRoomEntry().add(roomEntry);
		}
	}

	protected IObjectValue createNewData() {
		AgioBillInfo agio = new AgioBillInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		agio.setProject(sellProject);
		agio.setCalType(AgioCalTypeEnum.Dazhe);
		agio.setIsEspecial(false);
		agio.setIsEnabled(false);
		agio.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		agio.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		agio.setBookedDate(new Date());
		return agio;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AgioBillFactory.getRemoteInstance();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		this.storeFields();
		this.initOldData(this.editData);

		// 判断，如果当前是查看状态，则判断该 折扣单有没有被引用，有引用则不允许修改
		if (this.oprtState.equalsIgnoreCase("VIEW")) {
			this.btnAddRoom.setEnabled(false);
			this.btnDeleteRoom.setEnabled(false);			
		}

		this.actionAttachment.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionNextPerson.setVisible(false);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		this.actionAttachment.setVisible(true);
        this.btnAttachment.setVisible(true);
	}

	protected void fetchInitData() throws Exception {

	}

	protected void setAuditButtonStatus(String oprtType) {

	}

	private void initControl() {
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionWorkFlowG.setVisible(true);
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);

		this.actionCopy.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSubmit.setVisible(true);
		this.actionPre.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		
		this.kdtRoomEntry.checkParsed();
		this.kdtRoomEntry.getStyleAttributes().setLocked(true);
		IColumn column = this.kdtRoomEntry.addColumn();
		column.setKey("building");
		column = this.kdtRoomEntry.addColumn();
		column.setKey("unit");
		column = this.kdtRoomEntry.addColumn();
		column.setKey("roomNumber");
		column = this.kdtRoomEntry.addColumn();
		column.setKey("buildingArea");
		column = this.kdtRoomEntry.addColumn();
		column.setKey("buildingPrice");
		column = this.kdtRoomEntry.addColumn();
		column.setKey("standardTotalAmount");
		column = this.kdtRoomEntry.addColumn();
		column.setKey("baseStandardPrice");
		IRow headRow = this.kdtRoomEntry.getHeadRow(0);
		headRow.getCell("building").setValue("楼栋");
		headRow.getCell("unit").setValue("单元");
		headRow.getCell("roomNumber").setValue("房间编码");
		headRow.getCell("buildingArea").setValue("建筑面积");
		headRow.getCell("buildingPrice").setValue("建筑单价");
		headRow.getCell("standardTotalAmount").setValue("标准总价");
		headRow.getCell("baseStandardPrice").setValue("房间底价");
		this.chkIsEspecial.setVisible(false);
		this.txtAmount.setRequired(true);
		this.txtPro.setRequired(true);

		this.txtPro.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		this.txtPro.setRemoveingZeroInDispaly(true);
		this.txtPro.setRemoveingZeroInEdit(true);
		this.txtPro.setPrecision(2);
		this.txtPro.setNegatived(true);
		this.txtPro.setHorizontalAlignment(JTextField.RIGHT);
		this.txtPro.setSupportedEmpty(true);

		this.txtAmount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		this.txtPro.setRemoveingZeroInDispaly(true);
		this.txtAmount.setRemoveingZeroInEdit(true);
		this.txtAmount.setPrecision(2);
		this.txtAmount.setNegatived(true);
		this.txtAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtAmount.setSupportedEmpty(true);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);

	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (this.txtName.getText() == null || this.txtName.getText().trim().equals("")) {
			MsgBox.showError("名称不能为空!");
			this.abort();
		}
		Date startDate = (Date) this.pkStartDate.getValue();
		Date cancelDate = (Date) this.pkCancelDate.getValue();
		if (startDate != null && cancelDate != null) {
			if (startDate.after(cancelDate)) {
				MsgBox.showError("生效日期不能在失效日期之后!");
				this.abort();
			}
		}
		/**
		 * 根据提单R101231-135来取消此校验
		 * update by renliang at 2011-1-11
		 */
		/*if (startDate != null) {
			// Timestamp timeStamp = FDCCommonServerHelper.getServerTimeStamp();
			Timestamp timeStamp = FDCSQLFacadeFactory.getRemoteInstance().getServerTime();
			if (timeStamp.after(FDCHelper.getNextDay(startDate))) {
				MsgBox.showError("生效日期不能小于当期日期!");
				this.abort();
			}
		}*/
		if (txtAmount.isEnabled()) {
			BigDecimal amount = txtAmount.getBigDecimalValue();
			if (amount == null || amount.equals(FDCHelper.ZERO)) {
				MsgBox.showError("当前计算方式,金额必须录入!");
				this.abort();
			}
		}
		if (txtPro.isEnabled()) {
			BigDecimal pro = txtPro.getBigDecimalValue();
			if (pro == null || pro.equals(FDCHelper.ZERO)) {
				MsgBox.showError("当前计算方式,百分比必须录入!");
				this.abort();
			}
			if (pro.compareTo(new BigDecimal(100)) >= 0) {
				MsgBox.showError("百分比录入错误!");
				this.abort();
			}
		}
	}

	public void loadFields() {
		super.loadFields();
		//SHEHelper.setNumberEnabled(editData, this.getOprtState(), txtNumber);

		AgioBillInfo agioBill = (AgioBillInfo) this.editData;
		AgioRoomEntryCollection roomEntrys = agioBill.getRoomEntry();
		this.kdtRoomEntry.removeRows();
		for (int i = 0; i < roomEntrys.size(); i++) {
			AgioRoomEntryInfo roomEntry = roomEntrys.get(i);
			RoomInfo room = roomEntry.getRoom();
			IRow row = this.kdtRoomEntry.addRow();
			row.setUserObject(roomEntry);
			row.getCell("building").setValue(room.getBuilding());
			row.getCell("unit").setValue(room.getBuildUnit());
			row.getCell("roomNumber").setValue(room.getDisplayName());
			row.getCell("buildingArea").setValue(room.getBuildingArea());
			row.getCell("buildingPrice").setValue(room.getBuildPrice());
			row.getCell("standardTotalAmount").setValue(room.getStandardTotalAmount());
			row.getCell("baseStandardPrice").setValue(room.getBaseStandardPrice());
		}
		CRMClientHelper.getFootRow(this.kdtRoomEntry, new String[]{"buildingArea","standardTotalAmount","baseStandardPrice"});
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
//		String id = editData.getId().toString();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("agio.id", id));
//		if (PurchaseAgioEntryFactory.getRemoteInstance().exists(filter)) {
//			MsgBox.showInfo("该折扣已被引用，不能修改！");
//			SysUtil.abort();
//
//		}
		super.actionEdit_actionPerformed(e);
		this.btnAddRoom.setEnabled(true);
		this.btnDeleteRoom.setEnabled(true);
		AgioCalTypeEnum agioType = (AgioCalTypeEnum) this.comboCalType.getSelectedItem();
		if (agioType.equals(AgioCalTypeEnum.JianDian) || agioType.equals(AgioCalTypeEnum.Dazhe)) {
			this.txtAmount.setEnabled(false);
			this.txtAmount.setValue(null);
			this.txtPro.setEnabled(true);
		} else {
			this.txtAmount.setEnabled(true);
			this.txtPro.setEnabled(false);
			this.txtPro.setValue(null);
		}
	}

	public boolean isModify() {
		return super.isModify();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("roomEntry.room.*");
		sels.add("roomEntry.room.building.name");
		sels.add("roomEntry.room.buildUnit.name");
		return sels;
	}

	protected void btnAddRoom_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddRoom_actionPerformed(e);
		RoomCollection rooms = RoomSelectUIForNewSHE.showMultiRoomSelectUI(this, null, null, MoneySysTypeEnum.SalehouseSys,null,editData.getProject());
		if (rooms == null) {
			return;
		}
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			if (isExist(room)) {
				MsgBox.showInfo(room.getDisplayName() + " 已经在列表中!");
				continue;
			}
			AgioRoomEntryInfo roomEntry = new AgioRoomEntryInfo();
			roomEntry.setRoom(room);
			IRow row = this.kdtRoomEntry.addRow();
			row.setUserObject(roomEntry);
			row.getCell("building").setValue(room.getBuilding());
			row.getCell("unit").setValue(room.getBuildUnit());
			row.getCell("roomNumber").setValue(room.getDisplayName());
			row.getCell("buildingArea").setValue(room.getBuildingArea());
			row.getCell("buildingPrice").setValue(room.getBuildPrice());
			row.getCell("standardTotalAmount").setValue(room.getStandardTotalAmount());
			row.getCell("baseStandardPrice").setValue(room.getBaseStandardPrice());
		}
		CRMClientHelper.getFootRow(this.kdtRoomEntry, new String[]{"buildingArea","standardTotalAmount","baseStandardPrice"});
	}

	private boolean isExist(RoomInfo room) {
		for (int j = 0; j < this.kdtRoomEntry.getRowCount(); j++) {
			AgioRoomEntryInfo roomEntry = (AgioRoomEntryInfo) this.kdtRoomEntry.getRow(j).getUserObject();
			if (roomEntry.getRoom().getId().toString().equals(room.getId().toString())) {
				return true;
			}
		}
		return false;
	}
	public boolean confirmRemove(Component comp) {
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	protected void btnDeleteRoom_actionPerformed(ActionEvent e) throws Exception {
		if ((this.kdtRoomEntry.getSelectManager().size() == 0)){
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
			return;
		}
		if (confirmRemove()) {
			KDTSelectManager selectManager = this.kdtRoomEntry.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (this.kdtRoomEntry.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null){
				return;
			}
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				this.kdtRoomEntry.removeRow(rowIndex);
			}
			if (this.kdtRoomEntry.getRow(0) != null){
				this.kdtRoomEntry.getSelectManager().select(0, 0);
			}
		}
		CRMClientHelper.getFootRow(this.kdtRoomEntry, new String[]{"buildingArea","standardTotalAmount","baseStandardPrice"});
	}

	protected void comboCalType_actionPerformed(ActionEvent e) throws Exception {
		super.comboCalType_actionPerformed(e);
		AgioCalTypeEnum agioType = (AgioCalTypeEnum) this.comboCalType.getSelectedItem();
		if (agioType.equals(AgioCalTypeEnum.JianDian) || agioType.equals(AgioCalTypeEnum.Dazhe)) {
			this.txtAmount.setEnabled(false);
			this.txtAmount.setValue(null);
			this.txtPro.setEnabled(true);
		} else {
			this.txtAmount.setEnabled(true);
			this.txtPro.setEnabled(false);
			this.txtPro.setValue(null);
		}
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

}