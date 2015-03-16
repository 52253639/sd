/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.RoomSelectUI;
import com.kingdee.eas.fdc.tenancy.TenancyOrderFactory;
import com.kingdee.eas.fdc.tenancy.TenancyOrderInfo;
import com.kingdee.eas.fdc.tenancy.TenancyOrderRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyOrderRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class TenancyOrderEditUI extends AbstractTenancyOrderEditUI {
	private static final Logger logger = CoreUIObject.getLogger(TenancyOrderEditUI.class);

	SellProjectInfo sellPro; // ��ǰ��������Ŀ
	List allRoomList = new ArrayList(); // ��¼�еķ����BOSUuid�б�

	/**
	 * output class constructor
	 */
	public TenancyOrderEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();

		// �ѷ����б������ⷿ���¼��ȥ
		TenancyOrderInfo tenOrder = (TenancyOrderInfo) this.editData;

		TenancyOrderRoomEntryCollection roomEntrys = tenOrder.getRoomEntrys();
		roomEntrys.clear();
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			TenancyOrderRoomEntryInfo roomEntry = (TenancyOrderRoomEntryInfo) tblRoom.getRow(i).getUserObject();
			Object longNumberOb = tblRoom.getRow(i).getCell("roomLongNumber").getValue();
			roomEntry.setRoomLongNumber(longNumberOb == null ? "" : longNumberOb.toString());
			roomEntrys.add(roomEntry);
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sec = super.getSelectors();
		sec.add(new SelectorItemInfo("roomEntrys.roomLongNumber"));
		sec.add(new SelectorItemInfo("roomEntrys.room.*"));
		sec.add(new SelectorItemInfo("roomEntrys.room.building.*"));
		return sec;
	}

	/**
	 * output btnAddRoom_actionPerformed method
	 */
	protected void btnAddRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		RoomCollection rooms = RoomSelectUI.showMultiRoomSelectUI(this, null, null, MoneySysTypeEnum.TenancySys, null, this.editData.getSellProject()); //
		if (rooms == null || rooms.size() < 1)
			return;

		// ���˲�����Ҫ��ķ���
		RoomCollection okRooms = new RoomCollection();
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			if (room.getTenancyState() != null && !room.getTenancyState().equals(TenancyStateEnum.unTenancy)) {
				buff.append("����:" + room.getNumber() + "�Ѳ���δ����״̬!\n");
				continue;
			}
			if (!room.getBuilding().getSellProject().getId().equals(this.sellPro.getId())) {
				buff.append("����:" + room.getNumber() + "�����ڱ�������Ŀ'" + sellPro.getName() + "'!\n");
				continue;
			}
			if (!room.isIsForTen()) {
				MsgBox.showInfo(room.getNumber() + " û����������!");
				return;
			}
			if (this.allRoomList.contains(room.getId())) {
				buff.append("����:" + room.getNumber() + "�Ѿ����б�����!\n");
				continue;
			}
			if(room.getTenancyArea()==null || room.getTenancyArea().compareTo(new BigDecimal(0))==0)
			{
				MsgBox.showInfo(room.getNumber()+" δ¼�����������߼������Ϊ0");
				return;
			}
			okRooms.add(room);
		}

		if (!buff.toString().equals("")) {
			if (okRooms.size() > 0)
				buff.append("ʣ�µķ���Ҫ��ķ��佫�����!");
			MsgBox.showInfo(buff.toString());
		}

		if (okRooms.size() > 0) {
			TenancyOrderInfo tenOrder = (TenancyOrderInfo) this.editData;
			for (int i = 0; i < okRooms.size(); i++) {
				RoomInfo thisRoom = okRooms.get(i);
				TenancyOrderRoomEntryInfo roomEntry = new TenancyOrderRoomEntryInfo();
				roomEntry.setRoom(thisRoom);
				IRow row = this.tblRoom.addRow();

				roomEntry.setTenancyOrder(tenOrder);
				String roomLongNumber = thisRoom.getBuilding() + "-" + thisRoom.getUnit() + "-" + thisRoom.getNumber();
				roomEntry.setRoomLongNumber(roomLongNumber);
				row.setUserObject(roomEntry);

				row.getCell("building").setValue(thisRoom.getBuilding().getName());
				row.getCell("unit").setValue(new Integer(thisRoom.getUnit()));
				row.getCell("roomNumber").setValue(thisRoom.getNumber());
				row.getCell("buildingArea").setValue(thisRoom.getBuildingArea() == null ? FDCHelper.ZERO : thisRoom.getBuildingArea().divide(new BigDecimal("1"), 3, BigDecimal.ROUND_HALF_UP));
				row.getCell("roomArea").setValue(thisRoom.getRoomArea() == null ? FDCHelper.ZERO : thisRoom.getRoomArea().divide(new BigDecimal("1"),3, BigDecimal.ROUND_HALF_UP));
				row.getCell("tenancyArea").setValue(thisRoom.getTenancyArea() == null ? FDCHelper.ZERO : thisRoom.getTenancyArea().divide(new BigDecimal("1"), 3, BigDecimal.ROUND_HALF_UP));
				if (thisRoom.getStandardRent() == null){
					row.getCell("rentNum").setValue(new BigDecimal("0.00"));
				}else{
					row.getCell("rentNum").setValue(thisRoom.getStandardRent());
				}
				row.getCell("roomLongNumber").setValue(roomLongNumber);

				this.allRoomList.add(thisRoom.getId()); // ���ӵ�����list
			}
			this.CountAndSetTheRoomNums(); // ����ͳ��һ��
		}

	}

	/**
	 * output btnDeleteRoom_actionPerformed method
	 */
	protected void btnDeleteRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		/*
		 * //super.btnDeleteRoom_actionPerformed(e); int index =
		 * this.tblRoom.getSelectManager().getActiveRowIndex();
		 * 
		 * if(index>=0) { IRow row = tblRoom.getRow(index);
		 * TenancyOrderRoomEntryInfo roomEntry =
		 * (TenancyOrderRoomEntryInfo)row.getUserObject(); //ֻ����δ�����״̬�ķ������ɾ��
		 * RoomInfo room = roomEntry.getRoom(); if(room!=null &&
		 * room.getTenancyState()!=null &&
		 * room.getTenancyState().equals(TenancyStateEnum.unTenancy)) {
		 * this.allRoomList.remove(room.getId()); //�ӷ����б���ɾ��
		 * this.CountAndSetTheRoomNums(); //����ͳ��һ�� tblRoom.removeRow(index);
		 * }else{ MsgBox.showInfo("�÷���ĳ���״̬���Ǵ���״̬,����ɾ��!"); }
		 * 
		 * }
		 */

		int index = this.tblRoom.getSelectManager().getActiveRowIndex();

		if (index < 0) {
			return;
		}

		IRow row = tblRoom.getRow(index);
		TenancyOrderRoomEntryInfo roomEntry = (TenancyOrderRoomEntryInfo) row.getUserObject();
		// ֻ����δ�����״̬�ķ������ɾ��
		RoomInfo room = roomEntry.getRoom();
		if (room != null && room.getTenancyState() != null && !TenancyStateEnum.unTenancy.equals(room.getTenancyState())) {
			MsgBox.showInfo("�÷���ĳ���״̬���Ǵ���״̬,����ɾ��!");
			return;
		}
		if (room != null){
			this.allRoomList.remove(room.getId()); // �ӷ����б���ɾ��
		}
		tblRoom.removeRow(index);
		this.CountAndSetTheRoomNums(); // ����ͳ��һ��
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		CheckInput();
		super.actionSubmit_actionPerformed(e);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		CheckInput();
		super.actionSave_actionPerformed(e);
	}

	private void CheckInput() {
		StringBuffer buff = new StringBuffer();
		if (this.txtNumber.isEnabled()) {
			if (StringUtils.isEmpty(this.txtNumber.getText()))
				buff.append("�������¼�룡\n");
		}
		if (StringUtils.isEmpty(this.txtName.getText()))
			buff.append("���Ʊ���¼�룡\n");
		if (this.tblRoom.getRowCount() < 1)
			buff.append("����ѡ������һ��Ҫ����ķ��䣡\n");

		if (!buff.toString().equals("")) {
			MsgBox.showInfo(buff.toString());
			this.abort();
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TenancyOrderFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null; // this.tblRoom;
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		TenancyOrderInfo tenOrder = new TenancyOrderInfo();
		tenOrder.setOrderDate(new Date());
		tenOrder.setSellProject(sellPro);
		tenOrder.setCU(SysContext.getSysContext().getCurrentCtrlUnit());

		return tenOrder;
	}

	public void loadFields() {
		super.loadFields();

		// ��room����ע������,�������������
		if (!this.getOprtState().equals(OprtState.ADDNEW)) {

			TenancyOrderInfo tenOrder = (TenancyOrderInfo) this.editData;
			TenancyOrderRoomEntryCollection roomEntrys = tenOrder.getRoomEntrys();
			if (roomEntrys != null && roomEntrys.size() > 0) {
				this.tblRoom.removeRows();
				for (int i = 0; i < roomEntrys.size(); i++) {
					TenancyOrderRoomEntryInfo roomEntry = roomEntrys.get(i);

					RoomInfo room = roomEntry.getRoom();

					IRow row = this.tblRoom.addRow();
					row.setUserObject(roomEntry);
					if (room != null) {
						// building unit roomNumber buildingArea roomArea
						// rentNum
						row.getCell("building").setValue(room.getBuilding());
						row.getCell("unit").setValue(new Integer(room.getUnit()));
						row.getCell("roomNumber").setValue(room.getNumber());
						row.getCell("buildingArea").setValue(room.getBuildingArea() == null ? FDCHelper.ZERO : room.getBuildingArea().divide(new BigDecimal("1"), 3, BigDecimal.ROUND_HALF_UP));
						row.getCell("roomArea").setValue(room.getRoomArea() == null ? FDCHelper.ZERO : room.getRoomArea().divide(new BigDecimal("1"), 3, BigDecimal.ROUND_HALF_UP));
						row.getCell("tenancyArea").setValue(room.getTenancyArea() == null ? FDCHelper.ZERO : room.getTenancyArea().divide(new BigDecimal("1"), 3, BigDecimal.ROUND_HALF_UP));
						if (room.getStandardRent() != null) {
							row.getCell("rentNum").setValue(room.getStandardRent().divide(new BigDecimal("1"), 3, BigDecimal.ROUND_HALF_UP));
						} else {
							row.getCell("rentNum").setValue(new BigDecimal("0.00"));
						}
					}
					row.getCell("roomLongNumber").setValue(roomEntry.getRoomLongNumber());
				}
			}

			CountAndSetTheRoomNums();
		} else {
			this.tblRoom.removeRows();
			this.txtTotalArea.setValue(null);
			this.txtTotalRent.setValue(null);
			this.txtTotalRoomNum.setValue(null);
			this.txtAveragePrice.setValue(null);
		}

	}

	// ͳ�Ʒ������������������;���
	private void CountAndSetTheRoomNums() {
		BigDecimal sumBdArea = FDCHelper.ZERO;
		BigDecimal sumRmRent = FDCHelper.ZERO;
		BigDecimal avgPrice = FDCHelper.ZERO;
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = tblRoom.getRow(i);
			TenancyOrderRoomEntryInfo tenOrder = (TenancyOrderRoomEntryInfo) row.getUserObject();
			RoomInfo room = tenOrder.getRoom();
			if (room == null) {
				continue;
			}
			BigDecimal bdArea = FDCHelper.toBigDecimal(room.getTenancyArea());
			if (bdArea != null)
				sumBdArea = sumBdArea.add(bdArea);
			BigDecimal rmRent = FDCHelper.toBigDecimal(room.getStandardRent());
			if (rmRent != null)
				sumRmRent = sumRmRent.add(rmRent);
		}
		if (sumBdArea.compareTo(new BigDecimal("0")) != 0)
			avgPrice = sumRmRent.divide(sumBdArea, 3, BigDecimal.ROUND_HALF_UP);

		sumBdArea = sumBdArea.divide(new BigDecimal("1"), 3, BigDecimal.ROUND_HALF_UP);
		avgPrice = avgPrice.divide(new BigDecimal("1"), 3, BigDecimal.ROUND_HALF_UP);

		this.txtTotalArea.setValue(sumBdArea);
		this.txtTotalRent.setValue(sumRmRent);
		this.txtTotalRoomNum.setValue(new BigDecimal(this.tblRoom.getRowCount()));
		this.txtAveragePrice.setValue(avgPrice);
	}

	public void onLoad() throws Exception {
		sellPro = (SellProjectInfo) this.getUIContext().get("sellProject");

		// this.btnSave.setVisible(false);
		this.chkIsEnabled.setEnabled(false);
		this.txtSellProjectName.setEnabled(false);
		this.txtSellProjectNumber.setEnabled(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);

		tblRoom.checkParsed();
		// tblRoom.getStyleAttributes().setLocked(false);
		tblRoom.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblRoom.getStyleAttributes().setLocked(true);

		if (this.getOprtState().equals(OprtState.VIEW)) {
			this.btnAddRoom.setEnabled(false);
			this.btnDeleteRoom.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.pkOrderDate.setEnabled(false);
		} else {
			this.btnAddRoom.setEnabled(true);
			this.btnDeleteRoom.setEnabled(true);
		}

		Font font = new Font("SimSun", Font.BOLD, 15);
		Color color = Color.BLUE;
		this.contAveragePrice.getBoundLabel().setFont(font);
		this.contAveragePrice.getBoundLabel().setForeground(color);
		this.contTotalArea.getBoundLabel().setFont(font);
		this.contTotalArea.getBoundLabel().setForeground(color);
		this.contTotalRent.getBoundLabel().setFont(font);
		this.contTotalRent.getBoundLabel().setForeground(color);
		this.contTotalRoomNum.getBoundLabel().setFont(font);
		this.contTotalRoomNum.getBoundLabel().setForeground(color);
		this.txtAveragePrice.setFont(font);
		this.txtTotalArea.setFont(font);
		this.txtTotalRent.setFont(font);
		this.txtTotalRoomNum.setFont(font);
		this.txtDescription.setMaxLength(80);

		super.onLoad();

		this.actionSubmit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionAudit.setVisible(false);
		
		//by tim_gao С����󾫶�3λ
		this.txtTotalArea.setPrecision(3);
		
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);

		this.btnAddRoom.setEnabled(true);
		this.btnDeleteRoom.setEnabled(true);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.isIsEnabled())
		{
			MsgBox.showError("�����Ѿ�ִ��,�����޸�!");
    		return;
		}
		super.actionEdit_actionPerformed(e);

		this.btnAddRoom.setEnabled(true);
		this.btnDeleteRoom.setEnabled(true);
		this.pkOrderDate.setEnabled(true);
	}

}