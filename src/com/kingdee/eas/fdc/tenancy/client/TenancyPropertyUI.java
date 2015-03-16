/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.propertymgmt.ChargeStandardInfo;
import com.kingdee.eas.fdc.propertymgmt.PpmDevFileMaintenanceInfo;
import com.kingdee.eas.fdc.propertymgmt.RoomChargeCollection;
import com.kingdee.eas.fdc.propertymgmt.RoomChargeInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.EquipmentEntryCollection;
import com.kingdee.eas.fdc.tenancy.EquipmentEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomChargeEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomChargeEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class TenancyPropertyUI extends AbstractTenancyPropertyUI
{
    private static final Logger logger = CoreUIObject.getLogger(TenancyPropertyUI.class);
    
    private TenancyRoomEntryInfo tenRoom;
    
//    private ItemAction actionAddLinkman;
    
    /**
     * output class constructor
     */
    public TenancyPropertyUI() throws Exception
    {
        super();
//        actionAddLinkman = new ItemAction();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	this.tblChargeItem.getStyleAttributes().setLocked(true);
    	this.tblEquipment.getStyleAttributes().setLocked(true);
    	
    	initWorkBtn();
    }
    
    private void initWorkBtn() {
    	ItemAction actionAddEquipment = new ItemAction(){
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddEquipment_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
    	};
    	ItemAction actionRmEquipment = new ItemAction(){
			public void actionPerformed(ActionEvent e) {
				try {
					actionRmEquipment_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
    	};
    	ItemAction actionAddChargeItem = new ItemAction(){
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddChargeItem_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
    	};
    	ItemAction actionBatchAddChargeItem = new ItemAction(){
			public void actionPerformed(ActionEvent e) {
				try {
					actionBatchAddChargeItem_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
    	};
    	ItemAction actionRmChargeItem = new ItemAction(){
			public void actionPerformed(ActionEvent e) {
				try {
					actionRmChargeItem_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
    	};
    	
    	btnAddEquipment = initWorkBtn1(actionAddEquipment, "imgTbtn_sortstandard", this.containerEquipment, "添加");
    	btnRmEquipment = initWorkBtn1(actionRmEquipment, "imgTbtn_sortstandard", this.containerEquipment, "删除");
    	btnAddChargeItem = initWorkBtn1(actionAddChargeItem, "imgTbtn_sortstandard", this.containerChargeItem, "添加");
//    	btnBatchAddChargeItem = initWorkBtn1(actionBatchAddChargeItem, "imgTbtn_sortstandard", this.containerChargeItem, "批量添加");
    	btnRmChargeItem = initWorkBtn1(actionRmChargeItem, "imgTbtn_sortstandard", this.containerChargeItem, "删除");
    }
    
    private KDWorkButton btnAddEquipment = null;
    private KDWorkButton btnRmEquipment = null;
    private KDWorkButton btnAddChargeItem = null;
    private KDWorkButton btnBatchAddChargeItem = null;
    private KDWorkButton btnRmChargeItem = null;
    
    private KDWorkButton initWorkBtn1(Action action, String iconName, KDContainer parentContainer, String text) {
    	action.putValue(Action.SMALL_ICON, EASResource.getIcon(iconName));
    	KDWorkButton btn = new KDWorkButton();
    	btn = (KDWorkButton) parentContainer.add(action);
    	btn.setToolTipText(text);
    	btn.setText(text);
    	return btn;
	}

	protected void actionAddEquipment_actionPerformed(ActionEvent e)
    		throws Exception {
    	KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.propertymgmt.app.PpmDevFileMaintenanceQuery")));
		
//		EntityViewInfo view = new EntityViewInfo();
//		dlg.setEntityViewInfo(view);
		dlg.show();
		Object[] objects = (Object[])dlg.getData();
		if (objects != null  &&  objects.length > 0) {
			for(int i=0; i<objects.length; i++){
				PpmDevFileMaintenanceInfo dev = (PpmDevFileMaintenanceInfo) objects[i];
				
				EquipmentEntryInfo eq = new EquipmentEntryInfo();
				eq.setDev(dev);
				eq.setTenancyRoom(tenRoom);
				
				addRow(eq);
			}
		}
    }

	private void addRow(EquipmentEntryInfo eq) {
		this.tblEquipment.checkParsed();
		IRow row = this.tblEquipment.addRow();
		row.setUserObject(eq);
		PpmDevFileMaintenanceInfo dev = eq.getDev();
		
		row.getCell("number").setValue(dev.getNumber());
		row.getCell("name").setValue(dev.getName());
		row.getCell("simpleName").setValue(dev.getSimpleName());
		row.getCell("deviceType.name").setValue(dev.getDeviceType()==null? null:dev.getDeviceType().getName());
		row.getCell("setplace").setValue(dev.getSetplace());
		row.getCell("specstype").setValue(dev.getSpecstype());
		row.getCell("ismeasuredev").setValue(Boolean.valueOf(dev.isIsmeasuredev()));
		row.getCell("manufacturer").setValue(dev.getManufacturer());
		row.getCell("repaircorp.name").setValue(dev.getRepaircorp()==null? null : dev.getRepaircorp().getName());
		row.getCell("purchaseday").setValue(dev.getPurchaseday());
		row.getCell("useday").setValue(dev.getUseday());
		row.getCell("status").setValue(dev.getStatus());
		row.getCell("dutyperson.name").setValue(dev.getDutyperson()==null?null:dev.getDutyperson().getName());
	}
    
    protected void actionRmEquipment_actionPerformed(ActionEvent e)
    		throws Exception {
    	this.rmSelectRow(this.tblEquipment);
    }
    
    private void rmSelectRow(KDTable table){
    	int activeRowIndex = table.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = table.getRowCount();
		}
		table.removeRow(activeRowIndex);
    }
    
    protected void actionBatchAddChargeItem_actionPerformed(ActionEvent e)
    		throws Exception {
    	BuildingInfo buildingInfo = tenRoom.getRoom().getBuilding();
    	SellProjectInfo sellProject = buildingInfo.getSellProject();
    	int unit = tenRoom.getRoom().getUnit();
    	
    	UIContext context = new UIContext(this.getUIContext());
    	context.put("sellProject", sellProject);
    	context.put("building", buildingInfo);
    	context.put("unit", new Integer(unit));
    	
    	IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TenRoomChargeBatchAddUI.class.getName(), context, null, "ADDNEW");
    	ui.show();
    	
    	TenRoomChargeBatchAddUI tenBatchChargeSelUI = (TenRoomChargeBatchAddUI) ui.getUIObject();
    	RoomChargeCollection roomCharges = tenBatchChargeSelUI.getRoomCharges();
    	if(roomCharges == null || roomCharges.isEmpty()){
    		return;
    	}
    	
    	for(int i=0; i<roomCharges.size(); i++){
    		TenancyRoomChargeEntryInfo tenRoomCharge = TenancyPropertyHelper.roomCharge2TenRoomCharge(roomCharges.get(i), tenRoom);
        	if(tenRoomCharge == null){
        		return;
        	}
        	addRow(tenRoomCharge);
    	}
    }
    
    protected void actionAddChargeItem_actionPerformed(ActionEvent e)
    		throws Exception {
		BuildingInfo buildingInfo = tenRoom.getRoom().getBuilding();
		UIContext context = new UIContext(this.getUIContext());
		context.put("sellProject", buildingInfo.getSellProject());
		context.put("room", tenRoom.getRoom());

		IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TenRoomChargeSelectUI.class.getName(), context, null, "ADDNEW");
		ui.show();

		TenRoomChargeSelectUI tenRoomChargeSelUI = (TenRoomChargeSelectUI) ui.getUIObject();
		RoomChargeInfo roomCharge = tenRoomChargeSelUI.getRoomCharge();
		
		TenancyRoomChargeEntryInfo tenRoomCharge = TenancyPropertyHelper.roomCharge2TenRoomCharge(roomCharge, tenRoom);
		if (tenRoomCharge == null) {
			return;
		}

		addRow(tenRoomCharge);
	}

	private void addRow(TenancyRoomChargeEntryInfo tenRoomCharge) {
		this.tblChargeItem.checkParsed();
		IRow row = this.tblChargeItem.addRow();
    	row.setUserObject(tenRoomCharge);
    	row.getCell("chargeItem").setValue(tenRoomCharge.getChargeItem() == null ? null : tenRoomCharge.getChargeItem().getName());
    	row.getCell("feeQuantity").setValue(tenRoomCharge.getFeeQuantity());
    	row.getCell("priceFactor").setValue(tenRoomCharge.getFeeQuantity());
    	
    	boolean isUseStandard = tenRoomCharge.isUseStandard();
    	ChargeStandardInfo chargeStandard = tenRoomCharge.getChargeStandard();
    	
    	if(isUseStandard){//如果使用的收费标准，则取收费标准上的信息
    		if(chargeStandard != null){
    			StringBuffer sb = new StringBuffer();
    	    	sb.append(chargeStandard.getUnitPrice());
    	    	sb.append("/");
    	    	sb.append(chargeStandard.getPricePeriod());
    	    	row.getCell("unitPrice").setValue(sb.toString());
    	    	
    	    	row.getCell("chargePeriod").setValue(chargeStandard.getChargePeriod());
    	    	row.getCell("chargePeriodTp").setValue(chargeStandard.getChargePeriodTp());
    	    	
    	    	sb = new StringBuffer();
    	    	sb.append(chargeStandard.getChargeDateType());
    	    	sb.append(chargeStandard.getChargeTimeLimit());
    	    	sb.append(chargeStandard.getChargeDateUnit());
    	    	row.getCell("chargeDate").setValue(sb.toString());
    	    	
    	    	sb = new StringBuffer();
    	    	sb.append(chargeStandard.getLateFeeRate());
    	    	sb.append("/");
    	    	sb.append(chargeStandard.getLateFeeUnit());
    	    	row.getCell("lateFeeRate").setValue(sb.toString());
    		}
    	}else{
    		StringBuffer sb = new StringBuffer();
        	sb.append(tenRoomCharge.getUnitPrice());
        	sb.append("/");
        	sb.append(tenRoomCharge.getPricePeriod());
        	row.getCell("unitPrice").setValue(sb.toString());
        	
        	row.getCell("chargePeriod").setValue(tenRoomCharge.getChargePeriod());
        	row.getCell("chargePeriodTp").setValue(tenRoomCharge.getChargePeriodTp());
        	
        	sb = new StringBuffer();
        	sb.append(tenRoomCharge.getChargeDateType());
        	sb.append(tenRoomCharge.getChargeTimeLimit());
        	sb.append(tenRoomCharge.getChargeDateUnit());
        	row.getCell("chargeDate").setValue(sb.toString());
        	
        	sb = new StringBuffer();
        	sb.append(tenRoomCharge.getLateFeeRate());
        	sb.append("/");
        	sb.append(tenRoomCharge.getLateFeeUnit());
        	row.getCell("lateFeeRate").setValue(sb.toString());
    	}
	}
    
    protected void actionRmChargeItem_actionPerformed(ActionEvent e)
    		throws Exception {
    	this.rmSelectRow(this.tblChargeItem);
    }
    
    public void setRoomInfo(TenancyRoomEntryInfo tenRoom){
    	this.tenRoom = tenRoom;
    }
    
    public void loadTenEntrys(){
    	TenancyRoomEntryInfo tenRoom = this.tenRoom;
    	TenancyRoomChargeEntryCollection charges = tenRoom.getTenRoomCharges();
    	for(int i=0; i<charges.size(); i++){
    		addRow(charges.get(i));
    	}
    	
    	EquipmentEntryCollection eqs = tenRoom.getEquipments();
    	for(int i=0; i<eqs.size(); i++){
    		addRow(eqs.get(i));
    	}
    }
    
    public void storeTenEntrys(){
    	TenancyRoomEntryInfo tenRoom = this.tenRoom;
    	TenancyRoomChargeEntryCollection charges = new TenancyRoomChargeEntryCollection();
    	for(int i=0; i<this.tblChargeItem.getRowCount(); i++){
    		IRow row = this.tblChargeItem.getRow(i);
    		TenancyRoomChargeEntryInfo entry = (TenancyRoomChargeEntryInfo) row.getUserObject();
    		charges.add(entry);
    	}
    	tenRoom.getTenRoomCharges().clear();
    	tenRoom.getTenRoomCharges().addCollection(charges);
    	
    	EquipmentEntryCollection eqs = new EquipmentEntryCollection();
    	for(int i=0; i<this.tblEquipment.getRowCount(); i++){
    		IRow row = this.tblEquipment.getRow(i);
    		EquipmentEntryInfo eq = (EquipmentEntryInfo) row.getUserObject();
    		eqs.add(eq);
    	}
    	tenRoom.getEquipments().clear();
    	tenRoom.getEquipments().addCollection(eqs);
    }
    
    public void setEditable(boolean editable){
    	if(btnAddEquipment != null) btnAddEquipment.setEnabled(editable);
    	if(btnRmEquipment != null) btnRmEquipment.setEnabled(editable);
    	if(btnAddChargeItem != null) btnAddChargeItem.setEnabled(editable);
    	if(btnBatchAddChargeItem != null) btnBatchAddChargeItem.setEnabled(editable);
    	if(btnRmChargeItem != null) btnRmChargeItem.setEnabled(editable);
    }
    
}