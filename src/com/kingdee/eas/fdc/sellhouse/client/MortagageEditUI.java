/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomMortagageCollection;
import com.kingdee.eas.fdc.sellhouse.RoomMortagageFactory;
import com.kingdee.eas.fdc.sellhouse.RoomMortagageInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class MortagageEditUI extends AbstractMortagageEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(MortagageEditUI.class);

	public void loadFields() {
		super.loadFields();
		RoomMortagageInfo mor = this.editData;
		if (mor.getRoom() != null) {
			this.txtRoomNumber.setText(mor.getRoom().getNumber());
		}
		if (this.getUIContext().get("rooms") != null) {
			this.txtRoomNumber.setText("多个房间");
		}

	}

	protected void f7Bank_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Bank_dataChanged(e);
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		if (sellProject == null) {
			sellProject = this.editData.getRoom().getBuilding()
					.getSellProject();
		}
		BankInfo bank = (BankInfo) this.f7Bank.getValue();
		if (bank != null) {
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("*");
			view.getSelector().add("room.*");
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("room.building.sellProject.id",
							sellProject.getId().toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE));
			filter.getFilterItems().add(
					new FilterItemInfo("bank.id", bank.getId().toString()));
			RoomMortagageCollection mors = RoomMortagageFactory
					.getRemoteInstance().getRoomMortagageCollection(view);
			BigDecimal morTotalPrices = FDCHelper.ZERO;
			BigDecimal roomArea = FDCHelper.ZERO;
			BigDecimal buildingArea = FDCHelper.ZERO;
			for (int i = 0; i < mors.size(); i++) {
				RoomInfo room = mors.get(i).getRoom();
				morTotalPrices = morTotalPrices.add(room.getStandardTotalAmount()==null? FDCHelper.ZERO:room.getStandardTotalAmount());
				roomArea = roomArea.add(room.getRoomArea()==null? FDCHelper.ZERO :room.getRoomArea());
				buildingArea = buildingArea.add(room.getBuildingArea()==null? FDCHelper.ZERO:room.getBuildingArea());
			}
			this.txtMorCount.setValue(new Integer(mors.size()));
			this.txtMorTotalPrices.setValue(morTotalPrices);
			this.txtMorRoomArea.setValue(roomArea);
			this.txtMorBuildingArea.setValue(buildingArea);
		} else {
			this.txtMorCount.setValue(null);
			this.txtMorTotalPrices.setValue(null);
			this.txtMorRoomArea.setValue(null);
			this.txtMorBuildingArea.setValue(null);
		}
	}

	public void onLoad() throws Exception {
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.txtMorCount.setEnabled(false);
		this.txtMorTotalPrices.setEnabled(false);
		this.txtMorRoomArea.setEnabled(false);
		this.txtMorBuildingArea.setEnabled(false);
		this.txtNumber.setRequired(true);
		this.f7Bank.setRequired(true);
		this.pkMortagageDate.setRequired(true);
		
		setTxtFormat(this.txtMorCount);
		setTxtFormat(this.txtMorTotalPrices);
		setTxtFormat(this.txtMorRoomArea);
		setTxtFormat(this.txtMorBuildingArea);
		
		super.onLoad();
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionSubmit.setEnabled(false);
		}
	}

	private void setTxtFormat(KDFormattedTextField txtField){
		txtField.setRemoveingZeroInDispaly(false);
		txtField.setRemoveingZeroInEdit(false);
		txtField.setNegatived(false);
		txtField.setPrecision(2);
		txtField.setHorizontalAlignment(JTextField.RIGHT);
		txtField.setSupportedEmpty(true);
	}
	
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output class constructor
	 */
	public MortagageEditUI() throws Exception {
		super();
	}

	/**
	 * 对于已结算的期间，不允许进行收款及修改
	 * */
	private void verifyBalance() {
		Date bizDate = (Date) this.pkMortagageDate.getValue();
		Date balanceEndDate = null;
		SellProjectInfo sellProject = null;
		RoomMortagageInfo info = (RoomMortagageInfo) this.editData;
		if (info.getRoom() != null && info.getRoom().getBuilding() != null && info.getRoom().getBuilding().getSellProject() != null) {
			sellProject = info.getRoom().getBuilding().getSellProject();
		}
		if (sellProject != null) {
			try {
				balanceEndDate = getLastEndDate(sellProject.getId().toString());
			} catch (Exception e) {
				handleException(e);
				e.printStackTrace();
			}
			SHEHelper.verifyBalance(bizDate, balanceEndDate);
		}
	}
	
	
	/***
	 * 按销售项目取上次结算的截止日期。
	 * **/

	private Date getLastEndDate(String sellProjectId) throws Exception {
		Date lastEndDate = null;
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql("select FBalanceEndDate from T_SHE_SellProject where FID = ?");
		detailBuilder.addParam(sellProjectId);
		try {
			IRowSet detailSet = detailBuilder.executeQuery();
			if (detailSet.next()) {
				lastEndDate = detailSet.getDate("FBalanceEndDate");
			}
		} catch (Exception e) {
			handleException(e);
			e.printStackTrace();
		}
		return lastEndDate;
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(StringUtils.isEmpty(this.txtNumber.getText())){
			MsgBox.showInfo("抵押序号必须录入!");
			return;
		}
		if(this.f7Bank.getValue()==null){
			MsgBox.showInfo("抵押银行必须录入!");
			return;
		}
		if(this.pkMortagageDate.getValue()==null){
			MsgBox.showInfo("抵押日期必须录入!");
			return;
		}
		verifyBalance();
		if (this.getUIContext().get("rooms") != null) {
			this.setOprtState("VIEW");
			RoomCollection rooms = (RoomCollection) this.getUIContext().get(
					"rooms");
			CoreBaseCollection mors = new CoreBaseCollection();
			for (int i = 0; i < rooms.size(); i++) {
				RoomMortagageInfo mor = (RoomMortagageInfo) this
						.createNewData();
				mor.setId(BOSUuid.create(mor.getBOSType()));
				mor.setRoom(rooms.get(i));
				mor.setNumber(this.txtNumber.getText());
				mor.setDescription(this.txtDescription.getText());
				mor.setBank((BankInfo) this.f7Bank.getValue());
				mor.setMortagageDate((Date) this.pkMortagageDate.getValue());
				mors.add(mor);
			}
			RoomMortagageFactory.getRemoteInstance().submit(mors);
			List list = new ArrayList();
			for (int i = 0; i < rooms.size(); i++) {
				RoomInfo room = rooms.get(i);
				list.add(Arrays.asList(new Object[] { Boolean.TRUE,
						room.getId().toString(), }));
			}
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.executeBatch(
					"update T_SHE_ROOM set FISMortagaged=? where fid=?", list);
			this.showSubmitSuccess();
			this.actionSubmit.setEnabled(false);
		} else {
			this.setOprtState("EDIT");
			super.actionSubmit_actionPerformed(e);
			RoomInfo room = this.editData.getRoom();
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("isMortagaged");
			room.setIsMortagaged(true);
			RoomFactory.getRemoteInstance().updatePartial(room, sels);
			this.destroyWindow();
		}

	}

	protected IObjectValue createNewData() {
		RoomMortagageInfo mor = new RoomMortagageInfo();
		mor.setMortagageDate(new Date());
		Timestamp currentTime = new Timestamp(new Date().getTime());
		try {
			//currentTime = FDCCommonServerHelper.getServerTimeStamp();
			currentTime = FDCSQLFacadeFactory.getRemoteInstance().getServerTime();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		mor.setCreateTime(currentTime);
		if (this.getUIContext().get("room") != null) {
			mor.setRoom((RoomInfo) this.getUIContext().get("room"));
		}
		mor.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		mor.setIsEnabled(true);
		return mor;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("room.*");
		sels.add("bank.*");
		sels.add("room.building.sellProject.id");
		return sels;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomMortagageFactory.getRemoteInstance();
	}

}