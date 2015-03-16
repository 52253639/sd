/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.RoomPropertyMainFactory;
import com.kingdee.eas.fdc.tenancy.RoomPropertyMainInfo;
import com.kingdee.eas.fdc.tenancy.RoomPropertyReportInfo;
import com.kingdee.eas.fdc.tenancy.RoomPropertyTypeEnum;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class RoomPropertyEditUI extends AbstractRoomPropertyEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomPropertyEditUI.class);
    
    /**
     * output class constructor
     */
    public RoomPropertyEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {	
    	RoomPropertyMainInfo mainInfo = this.editData;
        super.storeFields();
        mainInfo.setPropertyOjbectType((RoomPropertyTypeEnum)this.propertyObjectComboBox.getSelectedItem());
    }
    
    
	public void onLoad() throws Exception {
		super.onLoad();
	}
    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {	
    	
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

	
	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		RoomPropertyMainInfo roomProperty = new RoomPropertyMainInfo();
		return roomProperty;
	}

	
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		
		return RoomPropertyMainFactory.getRemoteInstance();
	}
	
	protected void isUseF7(int i){
		switch (i) {
		case 1:
			this.contRoomResPropertyObject.setEnabled(true);
			this.contRoomResPropertyObject.setVisible(true);
			this.contRoomResBuilding.setEnabled(false);
			this.contRoomResBuilding.setVisible(false);
			this.contRoomResBuildingFloor.setEnabled(false);
			this.contRoomResBuildingFloor.setVisible(false);
			this.contRoomResRoom.setEnabled(false);
			this.contRoomResRoom.setVisible(false);
			this.f7RoomResPropertyObject.setEnabled(true);
			this.f7RoomResPropertyObject.setVisible(true);
			this.f7RoomResBuilding.setEnabled(false);
			this.f7RoomResBuilding.setVisible(false);
			this.f7RoomResBuildingFloor.setEnabled(false);
			this.f7RoomResBuildingFloor.setVisible(false);
			this.f7RoomResRoom.setEnabled(false);
			this.f7RoomResRoom.setVisible(false);
			break;
		case 2:
			this.contRoomResPropertyObject.setEnabled(false);
			this.contRoomResPropertyObject.setVisible(false);
			this.contRoomResBuilding.setEnabled(true);
			this.contRoomResBuilding.setVisible(true);
			this.contRoomResBuildingFloor.setEnabled(false);
			this.contRoomResBuildingFloor.setVisible(false);
			this.contRoomResRoom.setEnabled(false);
			this.contRoomResRoom.setVisible(false);
			this.f7RoomResPropertyObject.setEnabled(false);
			this.f7RoomResPropertyObject.setVisible(false);
			this.f7RoomResBuilding.setEnabled(true);
			this.f7RoomResBuilding.setVisible(true);
			this.f7RoomResBuildingFloor.setEnabled(false);
			this.f7RoomResBuildingFloor.setVisible(false);
			this.f7RoomResRoom.setEnabled(false);
			this.f7RoomResRoom.setVisible(false);
			break;
		case 3:
			this.contRoomResPropertyObject.setEnabled(false);
			this.contRoomResPropertyObject.setVisible(false);
			this.contRoomResBuilding.setEnabled(false);
			this.contRoomResBuilding.setVisible(false);
			this.contRoomResBuildingFloor.setEnabled(true);
			this.contRoomResBuildingFloor.setVisible(true);
			this.contRoomResRoom.setEnabled(false);
			this.contRoomResRoom.setVisible(false);
			this.f7RoomResPropertyObject.setEnabled(false);
			this.f7RoomResPropertyObject.setVisible(false);
			this.f7RoomResBuilding.setEnabled(false);
			this.f7RoomResBuilding.setVisible(false);
			this.f7RoomResBuildingFloor.setEnabled(true);
			this.f7RoomResBuildingFloor.setVisible(true);
			this.f7RoomResRoom.setEnabled(false);
			this.f7RoomResRoom.setVisible(false);
			break;
		case 4:
			this.contRoomResPropertyObject.setEnabled(false);
			this.contRoomResPropertyObject.setVisible(false);
			this.contRoomResBuilding.setEnabled(false);
			this.contRoomResBuilding.setVisible(false);
			this.contRoomResBuildingFloor.setEnabled(false);
			this.contRoomResBuildingFloor.setVisible(false);
			this.contRoomResRoom.setEnabled(true);
			this.contRoomResRoom.setVisible(true);
			this.f7RoomResPropertyObject.setEnabled(false);
			this.f7RoomResPropertyObject.setVisible(false);
			this.f7RoomResBuilding.setEnabled(false);
			this.f7RoomResBuilding.setVisible(false);
			this.f7RoomResBuildingFloor.setEnabled(false);
			this.f7RoomResBuildingFloor.setVisible(false);
			this.f7RoomResRoom.setEnabled(true);
			this.f7RoomResRoom.setVisible(true);
			break;
		}
			
			
		
		
	}
	
	public void propertyObjectComboBox_actionPerformed(ActionEvent e) throws Exception{
	      //write your code here
		super.propertyObjectComboBox_actionPerformed(e);
		if(RoomPropertyTypeEnum.PROJECTTYPE.equals((RoomPropertyTypeEnum)this.propertyObjectComboBox.getSelectedItem())){
			isUseF7(1);
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sels = new SelectorItemCollection();
			view.setSelector(sels);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name",null,CompareType.ISNOT));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.name",null,CompareType.ISNOT));
			view.setFilter(filter);
			this.f7RoomResPropertyObject.setEntityViewInfo(view);
//			com\kingdee\eas\fdc\tenancy\app\RoomPropertyReportProjectQuery
			
			
		
		}else if(RoomPropertyTypeEnum.BUILDINGUNITTYPE.equals((RoomPropertyTypeEnum)

				this.propertyObjectComboBox.getSelectedItem())){
			isUseF7(2);
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.name",null,CompareType.ISNOT));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.name",null,CompareType.ISNOT));
			filter.getFilterItems().add(new FilterItemInfo("name",null,CompareType.ISNOT));
			view.setFilter(filter);
//			SelectorItemCollection sels = new SelectorItemCollection();
//			sels.add("name");
//			sels.add("number");
//			sels.add("orgUnit.name");
//			sels.add("sellProject.name");
//			sels.add("sellProject.number");
//			view.setSelector(sels);
//			com\kingdee\eas\fdc\tenancy\app\RoomPropertyReportBuildingQuery
			this.f7RoomResBuilding.setEntityViewInfo(view);
			
			
		}
//		else if(RoomPropertyTypeEnum.BUILDINGFLOORTYPE.equals((RoomPropertyTypeEnum)this.propertyObjectComboBox.getSelectedItem())){
//			isUseF7(3);
//			EntityViewInfo view = new EntityViewInfo();
//			SelectorItemCollection sels = new SelectorItemCollection();
//			sels.add("*");
//			view.setSelector(sels);
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("sellProject.name",null,CompareType.ISNOT));
//			filter.getFilterItems().add(new FilterItemInfo("orgUnit.name",null,CompareType.ISNOT));
//			filter.getFilterItems().add(new FilterItemInfo("building.name",null,CompareType.ISNOT));
//			filter.getFilterItems().add(new FilterItemInfo("floor",null,CompareType.ISNOT));
////			view.setFilter(filter);
//			
////			sels.add("sellProject.name");
////			sels.add("sellProject.number");
////			sels.add("orgUnit.name");
////			sels.add("building.*");
////			sels.add("building.number");
////			sels.add("subarea.name");
//		
////			com\kingdee\eas\fdc\tenancy\app\RoomPropertyReportBuildingFloorQuery
//			
//			this.f7RoomResBuildingFloor.setEntityViewInfo(view);
//		
//		
//		}
		else if(RoomPropertyTypeEnum.ROOMTYPE.equals((RoomPropertyTypeEnum)this.propertyObjectComboBox.getSelectedItem())){
			isUseF7(4);
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.name",null,CompareType.ISNOT));
			filter.getFilterItems().add(new FilterItemInfo("name",null,CompareType.ISNOT));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.name",null,CompareType.ISNOT));
			filter.getFilterItems().add(new FilterItemInfo("building.name",null,CompareType.ISNOT));
			view.setFilter(filter);
//			SelectorItemCollection sels = new SelectorItemCollection();
//			sels.add("sellProject.name");
//			sels.add("sellProject.number");
//			sels.add("orgUnit.name");
//			sels.add("building.name");
//			sels.add("building.number");
//			sels.add("subarea.name");
//			sels.add("floor");
//			sels.add("name");
//		view.setSelector(sels);
			
			this.f7RoomResRoom.setEntityViewInfo(view);
//			com\kingdee\eas\fdc\tenancy\app\RoomPropertyReportRoomQuery
		
		}
	}
	protected void setTableTblRoomRes(String resName){
		IRow row =this.kdtRoomProepertyEntry.addRow();
		row.getCell("roomResName").setValue(resName);
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		this.kdtRoomProepertyEntry.getColumn("certifiedTime").setEditor(dateEditor);
		
	}
	 /**
     * output f7RoomResPropertyObject_dataChanged method
     * @description 对项目f7房源查询
     */
    protected void f7RoomResPropertyObject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.f7RoomResPropertyObject_dataChanged(e);
    	this.kdtRoomProepertyEntry.removeRows();
		Object[] obj = (Object[])this.f7RoomResPropertyObject.getValue();
		for(int i =0 ;i<obj.length;i++){
			SellProjectInfo propertyProject =(SellProjectInfo)obj[i];
			if(obj[i]!=null)
			setTableTblRoomRes(propertyProject.getName());
		}
    }

    /**
     * output f7RoomResBuilding_dataChanged method
     *  @description 对楼栋f7房源查询
     */
    protected void f7RoomResBuilding_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.f7RoomResBuilding_dataChanged(e);
    	this.kdtRoomProepertyEntry.removeRows();
		Object[] obj = (Object[])this.f7RoomResBuilding.getValue();
		for(int i =0 ;i<obj.length;i++){
			BuildingInfo propertyProject =(BuildingInfo)obj[i];
			setTableTblRoomRes(propertyProject.getName());
		}
    }

    /**
     * output f7RoomResBuildingFloor_dataChanged method
     *  @description 对楼层f7房源查询
     */
//    protected void f7RoomResBuildingFloor_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
//    {
//    	super.f7RoomResBuildingFloor_dataChanged(e);
//    	this.kdtRoomProepertyEntry.removeRows();
//		Object[] obj = (Object[])this.f7RoomResBuildingFloor.getValue();
//		for(int i =0 ;i<obj.length;i++){
//			BuildingFloorEntryInfo propertyProject =(BuildingFloorEntryInfo)obj[i];
//			setTableTblRoomRes(propertyProject.getBuilding().getName()+"-"+propertyProject.getFloor());
//		}
//    }

    /**
     * output f7RoomResRoom_dataChanged method
     *  @description 对房屋f7房源查询
     */
    protected void f7RoomResRoom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.f7RoomResRoom_dataChanged(e);
    	this.kdtRoomProepertyEntry.removeRows();
		Object[] obj = (Object[])this.f7RoomResRoom.getValue();
		for(int i =0 ;i<obj.length;i++){
			RoomInfo propertyProject =(RoomInfo)obj[i];
			setTableTblRoomRes(propertyProject.getName());
			
		}
    }
    
    public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("propertyOjbectType");
		sels.add("createTime");
		sels.add("creator");
		return sels;
    }
	public void loadFields() {
		RoomPropertyMainInfo mainInfo = this.editData;
		super.loadFields();
		this.pkCreateTime.setValue(mainInfo.getCreateTime());
		this.propertyObjectComboBox.setSelectedItem(mainInfo.getPropertyOjbectType());
	}
	
}