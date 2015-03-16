/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
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
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomBindUI extends AbstractRoomBindUI {
	private static final String COL_MERGE_AMOUNT = "mergeAmount";
	private static final String COL_IS_MERGE_TO_PURCHASE = "isMergeToPurchase";
	private static final String COL_ROOM_NUMBER = "roomNumber";
	private static final String COL_UNIT = "unit";
	private static final String COL_BUILDING = "building";

	private static final Logger logger = CoreUIObject
			.getLogger(RoomBindUI.class);
	
	//目前房间绑定和认购时选择并入合同的绑定均调用该界面，用该变量用以区分
	private boolean isPurchaseBind = false;
	/**
	 * output class constructor
	 */
	public RoomBindUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();		
	}

	private void addRowWithRoomAttachementEntry(RoomAttachmentEntryInfo entry) {
		IRow row = this.tblRooms.addRow();
		row.setUserObject(entry);
		RoomInfo entryRoom = entry.getRoom();
		
		
		row.getCell(COL_BUILDING).setValue(entryRoom.getBuilding().getName());
		row.getCell(COL_UNIT).setValue(entryRoom.getBuildUnit()==null?"":entryRoom.getBuildUnit().getName());
		row.getCell(COL_ROOM_NUMBER).setUserObject(entryRoom);
		row.getCell(COL_ROOM_NUMBER).setValue(entryRoom.getNumber());
		
		if(isPurchaseBind){
			row.getCell(COL_IS_MERGE_TO_PURCHASE).setValue(new Boolean(false));
			row.getCell(COL_MERGE_AMOUNT).getStyleAttributes().setLocked(true);
		}
	}
	
	private void addRowWithPurchaseRoomAttachmentEntry(PurchaseRoomAttachmentEntryInfo purchaseRoom){
		IRow row = this.tblRooms.addRow();
		row.setUserObject(purchaseRoom.getAttachmentEntry());
		RoomInfo entryRoom = purchaseRoom.getAttachmentEntry().getRoom();
		row.getCell(COL_BUILDING).setValue(entryRoom.getBuilding().getName());
		row.getCell(COL_UNIT).setValue(entryRoom.getBuildUnit()==null?"":entryRoom.getBuildUnit().getName());
		row.getCell(COL_ROOM_NUMBER).setUserObject(entryRoom);
		row.getCell(COL_ROOM_NUMBER).setValue(entryRoom.getNumber());
		row.getCell(COL_IS_MERGE_TO_PURCHASE).setValue(Boolean.TRUE);
		row.getCell(COL_MERGE_AMOUNT).setValue(purchaseRoom.getMergeAmount());
	}

	private RoomInfo getRoomInfo(String roomId) {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("building.*");
		sels.add("buildUnit.*");
		sels.add("building.sellProject.*");
		RoomInfo room = null;
		try {
			room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId)), sels);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (UuidException e) {
			e.printStackTrace();
		}		
		
		try {
			//如果附属房产被拆分，对应的房间可能会不存在
			RoomAttachmentEntryCollection  roomAttEntryColl = RoomAttachmentEntryFactory.getRemoteInstance()
					.getRoomAttachmentEntryCollection("select room.*,room.building.* where head.id='"+roomId+"' and room.name is not null"); 
			room.getAttachmentEntry().clear();
			room.getAttachmentEntry().addCollection(roomAttEntryColl);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return room;
	}
	 /**
    *
    * <p>@author tim_gao
    * @description  增加售楼的判断
    * if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
           			
           		}else{
           			
           		}<p>
    */
	public void onLoad() throws Exception {
		isPurchaseBind = ((Boolean) this.getUIContext().get("isPurchaseBind")).booleanValue();
		initControls();
		super.onLoad();
		PurchaseRoomAttachmentEntryCollection srcPurchaseRooms = (PurchaseRoomAttachmentEntryCollection) this.getUIContext().get("srcPurchaseRooms");
		String roomId = (String) this.getUIContext().get("roomId");
		RoomInfo room = getRoomInfo(roomId);
		RoomAttachmentEntryCollection entrys = room.getAttachmentEntry();
		
		this.tblRooms.setUserObject(room);
		this.txtProject.setText(room.getBuilding().getSellProject().getName());
		this.txtBuilding.setText(room.getBuilding().getName());
		this.txtRoomNumber.setText(room.getNumber());
		this.tblRooms.getColumn("roomNo").getStyleAttributes().setHided(true);
		if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
			
		}else{
			this.txtRoomNo.setText(room.getRoomNo());
			this.tblRooms.getColumn("roomNo").getStyleAttributes().setHided(false);
		}
		
		
		if(isPurchaseBind  &&  srcPurchaseRooms != null){
			RoomAttachmentEntryCollection remainEntrys = new RoomAttachmentEntryCollection();
			for (int i = 0; i < entrys.size(); i++) {
				RoomAttachmentEntryInfo entry = entrys.get(i);
				if(!RoomSellStateEnum.OnShow.equals(entry.getRoom().getSellState())){//待售的的才能被作为附属房产认购
					continue;
				}
				
				boolean isIncluded = false;
				for(int j=0; j<srcPurchaseRooms.size(); j++){
					PurchaseRoomAttachmentEntryInfo purchaseRoom = srcPurchaseRooms.get(j);
					if(purchaseRoom.getAttachmentEntry().getId().toString().equals(entry.getId().toString())){
						isIncluded = true;
						break;
					}
				}
				if(!isIncluded){
					remainEntrys.add(entry);
				}
			}
			
			for (int i = 0; i < srcPurchaseRooms.size(); i++) {
				PurchaseRoomAttachmentEntryInfo purchaseRoom = srcPurchaseRooms.get(i);
				addRowWithPurchaseRoomAttachmentEntry(purchaseRoom);
			}
			
			for (int i = 0; i < remainEntrys.size(); i++) {
				RoomAttachmentEntryInfo entry = remainEntrys.get(i);
				addRowWithRoomAttachementEntry(entry);
			}
		}else{
			for (int i = 0; i < entrys.size(); i++) {
				RoomAttachmentEntryInfo entry = entrys.get(i);	
				addRowWithRoomAttachementEntry(entry);
			}
		}
		
	
	}

	/**
	 * 初始化一些控件状态
	 * */
	private void initControls() {
		tblRooms.checkParsed();
		tblRooms.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		tblRooms.getColumn(COL_MERGE_AMOUNT).setEditor(numberEditor);
		tblRooms.getColumn(COL_MERGE_AMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblRooms.getColumn(COL_MERGE_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		tblRooms.getColumn(COL_BUILDING).getStyleAttributes().setLocked(true);
		tblRooms.getColumn(COL_UNIT).getStyleAttributes().setLocked(true);
		tblRooms.getColumn(COL_ROOM_NUMBER).getStyleAttributes().setLocked(true);
		tblRooms.getColumn("roomNo").getStyleAttributes().setLocked(true);
		tblRooms.getColumn(COL_IS_MERGE_TO_PURCHASE).getStyleAttributes().setLocked(true);
		
		tblRooms.getColumn(COL_IS_MERGE_TO_PURCHASE).getStyleAttributes().setHided(!isPurchaseBind);
		tblRooms.getColumn(COL_MERGE_AMOUNT).getStyleAttributes().setHided(!isPurchaseBind);
		
	
		tblRooms.getColumn(COL_UNIT).getStyleAttributes().setHided(true);
		
		btnAddBind.setVisible(!isPurchaseBind);
		btnRemoveBind.setVisible(!isPurchaseBind);
	}

	/**
	 * output btnNo_actionPerformed method
	 */
	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnNo_actionPerformed(e);
		this.destroyWindow();
	}

	/**
	 * output btnYes_actionPerformed method
	 */
	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnYes_actionPerformed(e);
		if(isPurchaseBind){
			verify();
			PurchaseRoomAttachmentEntryCollection list = new PurchaseRoomAttachmentEntryCollection();
			for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
				IRow row = (IRow) tblRooms.getRow(i);
				boolean isMergeToPurchase = ((Boolean)row.getCell(COL_IS_MERGE_TO_PURCHASE).getValue()).booleanValue();
				if(isMergeToPurchase){
					RoomAttachmentEntryInfo entry = (RoomAttachmentEntryInfo) row.getUserObject();
					PurchaseRoomAttachmentEntryInfo purchaseRoomEntry = new PurchaseRoomAttachmentEntryInfo();
					purchaseRoomEntry.setAttachmentEntry(entry);
					purchaseRoomEntry.setMergeAmount((BigDecimal) row.getCell(COL_MERGE_AMOUNT).getValue());
					list.add(purchaseRoomEntry);
				}
			}
			this.getUIContext().put("targetPurchaseRooms", list);
		}else{
			RoomInfo room = (RoomInfo) this.tblRooms.getUserObject();
			room.getAttachmentEntry().clear();
			for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
				IRow row = this.tblRooms.getRow(i);
				RoomAttachmentEntryInfo entry = (RoomAttachmentEntryInfo) row
						.getUserObject();
				entry.setRoom((RoomInfo) row.getCell(COL_ROOM_NUMBER).getUserObject());
				room.getAttachmentEntry().add(entry);
			}
			RoomFactory.getRemoteInstance().submit(room);
			this.getUIContext().put("isEdit", Boolean.TRUE);
		}
		
		
		
		this.destroyWindow();
	}

	private void verify() {
		for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
			IRow row = this.tblRooms.getRow(i);
			RoomInfo room = (RoomInfo) row.getCell(COL_ROOM_NUMBER)
					.getUserObject();
			try {
				room = RoomFactory.getRemoteInstance().getRoomInfo(
						new ObjectUuidPK(room.getId()));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			boolean isMergeToPurchase = ((Boolean) row.getCell(
					COL_IS_MERGE_TO_PURCHASE).getValue()).booleanValue();
			if (isMergeToPurchase) {
				BigDecimal mergeAmount = (BigDecimal) row
						.getCell(COL_MERGE_AMOUNT).getValue();
				if (mergeAmount != null) {
					BigDecimal roomAmount = room.getStandardTotalAmount();
					if (roomAmount == null) {
						roomAmount = FDCHelper.ZERO;
					}
					if (mergeAmount.compareTo(roomAmount) > 0) {
						MsgBox.showInfo("房间" + room.getNumber()
								+ " 并入金额大于房间自身定价!");
						this.abort();
					}
				}
			}
		}
	}

	/**
	 * output btnAddBind_actionPerformed method
	 */
	 /**
    *
    * <p>@author tim_gao
    * @description  增加售楼的判断
    * if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
           			
           		}else{
           			
           		}<p>
    */
	protected void btnAddBind_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnAddBind_actionPerformed(e);
		RoomInfo room = (RoomInfo) this.tblRooms.getUserObject();
		RoomInfo entryRoom = RoomSelectUIForNewSHE.showOneRoomSelectUI(this, room
				.getBuilding(), room.getBuildUnit(),MoneySysTypeEnum.SalehouseSys,null,room.getBuilding().getSellProject());
		if (entryRoom == null) {
			return;
		}
		/*
		 * 房间出售后仍可修改绑定,以实现认购后,修改房间绑定再走认购变更流程.增加认购附属房产的功能 zhicheng_jin  090226
		if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)
				|| room.getSellState().equals(RoomSellStateEnum.Purchase)
				|| room.getSellState().equals(RoomSellStateEnum.Sign)) {
			MsgBox.showInfo("房间已售!");
			return;
		}
		*/
		if (entryRoom.getHouseProperty().equals(HousePropertyEnum.NoAttachment)) {
			MsgBox.showInfo("不能绑定非附属房产!");
			return;
		}
		for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
			RoomInfo aRoom = (RoomInfo) tblRooms.getRow(i)
					.getCell(COL_ROOM_NUMBER).getUserObject();
			if (aRoom.getId().toString().equals(entryRoom.getId().toString())) {
				MsgBox.showInfo("该房间已经绑定!");
				return;
			}
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("room.id", entryRoom.getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", room.getId().toString(),
						CompareType.NOTEQUALS));
		if (RoomAttachmentEntryFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("该房间已经被其他房间绑定!");
			return;
		}
		/*
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("room.id", entryRoom.getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.ChangeRoomBlankOut,
						CompareType.NOTEQUALS));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("purchaseState",
								PurchaseStateEnum.ManualBlankOut,
								CompareType.NOTEQUALS));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("purchaseState",
								PurchaseStateEnum.NoPayBlankOut,
								CompareType.NOTEQUALS));
		if (PurchaseFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("房间已经被认购,请先作废认购单!");
			return;
		}
		------------------------------------------- */
		
		IRow row = this.tblRooms.addRow();
		row.setUserObject(new RoomAttachmentEntryInfo());
		row.getCell(COL_BUILDING).setValue(entryRoom.getBuilding().getName());
		row.getCell(COL_UNIT).setValue(room.getBuildUnit()==null?"":room.getBuildUnit().getName());  //entryRoom
		row.getCell(COL_ROOM_NUMBER).setUserObject(entryRoom);
		row.getCell(COL_ROOM_NUMBER).setValue(entryRoom.getNumber());
		if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
			
		}else{
			row.getCell("roomNo").setValue(entryRoom.getRoomNo());
		}
		
		
		row.getCell(COL_IS_MERGE_TO_PURCHASE).setValue(new Boolean(false));
		row.getCell(COL_MERGE_AMOUNT).setValue(null);
	}

	/**
	 * output btnRemoveBind_actionPerformed method
	 */
	protected void btnRemoveBind_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnRemoveBind_actionPerformed(e);
		int index = this.tblRooms.getSelectManager().getActiveRowIndex();
		if (index >= 0) {
			this.tblRooms.removeRow(index);
		}
	}
	
	/**
	 * 打开附属房间界面，目前房间绑定和认购时选择并入合同的绑定均调用该界面
	 * @param ui
	 * @param roomId
	 * @param isPurchaseBind 是否是认购编辑附属房间
	 * @param list 认购编辑时传入的附属房间分录
	 * @return 打开的UIWindow
	 * */
	private static IUIWindow showEditUI(CoreUI ui, String roomId,
			Boolean isPurchaseBind, PurchaseRoomAttachmentEntryCollection list) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("roomId", roomId);
		uiContext.put("isPurchaseBind", isPurchaseBind);
		uiContext.put("srcPurchaseRooms", list);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomBindUI.class.getName(), uiContext, null, "EDIT");
		uiWindow.show();
		return uiWindow;
	}
	
	/**
	 * 供认购单上编辑附属房产调用
	 * @param ui
	 * @param roomId
	 * @param list 控件上暂存的分录集合
	 * @return 编辑后的分录集合.如果用户未点击确定按钮执行操作则返回null;如果用户设置的分录条数为0,返回length为0的分录集合
	 * */
	public static PurchaseRoomAttachmentEntryCollection showEditUI(CoreUI ui, String roomId, PurchaseRoomAttachmentEntryCollection list) throws UIException {
		IUIWindow uiWindow = showEditUI(ui, roomId, Boolean.TRUE, list);
		Object purchaseAttachRooms = uiWindow.getUIObject().getUIContext().get("targetPurchaseRooms");
		return purchaseAttachRooms == null ? null : (PurchaseRoomAttachmentEntryCollection)purchaseAttachRooms;
	}
	
	/**
	 * 供房间绑定操作调用
	 * @param ui
	 * @param roomId
	 * @return 是否有进行房间绑定的修改
	 * */
	public static boolean showEditUI(CoreUI ui, String roomId) throws UIException {
		IUIWindow uiWindow = showEditUI(ui, roomId, Boolean.FALSE, null);
		Object isEdit = uiWindow.getUIObject().getUIContext().get("isEdit");
		if (isEdit != null) {
			return true;
		}
		return false;
	}
	
	protected void tblRooms_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblRooms_tableClicked(e);
		if (e.getButton() == 1 && e.getClickCount() == 1) {
			if (e.getColIndex() == 4) {
				IRow row = this.tblRooms.getRow(e.getRowIndex());
				if(row==null){
					return;
				}
				Boolean isMerge = (Boolean) row.getCell(4).getValue();
				row.getCell(4).setValue(new Boolean(!isMerge.booleanValue()));
				if (!isMerge.booleanValue()) {
					row.getCell(COL_MERGE_AMOUNT).getStyleAttributes().setLocked(
							false);
					RoomAttachmentEntryInfo entry = (RoomAttachmentEntryInfo) row.getUserObject();
					if(entry != null  &&  entry.getRoom() != null  &&  entry.getRoom().getStandardTotalAmount() != null){
						BigDecimal am = entry.getRoom().getStandardTotalAmount();
						row.getCell(COL_MERGE_AMOUNT).setValue(am);
					}
				} else {
					row.getCell(COL_MERGE_AMOUNT).getStyleAttributes().setLocked(
							true);
					row.getCell(COL_MERGE_AMOUNT).setValue(null);
				}
			}
		}
	}
}