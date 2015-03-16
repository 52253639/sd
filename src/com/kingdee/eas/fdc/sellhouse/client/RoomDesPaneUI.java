/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDesInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomDesPaneUI extends AbstractRoomDesPaneUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomDesPaneUI.class);
    
    private static SellProjectInfo sellPro = null;
    
    /**
     * output class constructor
     */
    public RoomDesPaneUI() throws Exception
    {
        super();
        
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
        
        RoomDesInfo roomDes = (RoomDesInfo)this.editData;
        
        roomDes.setProductType( (ProductTypeInfo) this.prmtProductType.getValue());
        roomDes.setDirection((HopedDirectionInfo) this.prmtDirection.getValue());
        roomDes.setNewEyeSight( (RoomAssistantInfo) this.prmtEyeSight.getValue());
        roomDes.setNewRoomUsage( (RoomAssistantInfo) this.prmtRoomUsage.getValue());
        roomDes.setBuildingProperty(  (BuildingPropertyInfo) this.prmtBuildingProperty.getValue());
        roomDes.setNewPossstd(   (RoomAssistantInfo) this.prmtPossStd.getValue());
        roomDes.setNewSight((RoomAssistantInfo) this.prmtSight.getValue());
        roomDes.setRoomModel((RoomModelInfo) this.prmtRoomModel.getValue());
        roomDes.setBuildStruct(  (BuildingStructureInfo) this.prmtBuildingStruct.getValue());
        roomDes.setNewNoise( (RoomAssistantInfo) this.prmtNoise.getValue());
        roomDes.setPlanBuildingArea( (BigDecimal) this.txtFormattedPlanBuildingArea.getValue(BigDecimal.class));
        roomDes.setPlanRoomArea( (BigDecimal) this.txtFormattedPlanRoomArea.getValue(BigDecimal.class));
        roomDes.setCarbarnArea( (BigDecimal) this.txtFormattedCarbarnArea.getValue(BigDecimal.class));
        roomDes.setNewDecorastd((RoomAssistantInfo) this.prmtDecoraStd.getValue());
        
        roomDes.setFlatArea( (BigDecimal) this.txtFormattedPlateArea.getValue(BigDecimal.class));
        roomDes.setBuildingArea( (BigDecimal) this.txtFormattedBuildingArea.getValue(BigDecimal.class));
        roomDes.setRoomArea( (BigDecimal) this.txtFormattedRoomArea.getValue(BigDecimal.class));
        
        roomDes.setBalconyArea( (BigDecimal) this.txtFormattedBalconyArea.getValue(BigDecimal.class));
        roomDes.setUserArea( (BigDecimal) this.txtFormattedUseArea.getValue(BigDecimal.class));
        roomDes.setActualBuildingArea( (BigDecimal) this.txtFormattedActualBuildingArea.getValue(BigDecimal.class));
        roomDes.setActualRoomArea( (BigDecimal) this.txtFormattedActualRoomArea.getValue(BigDecimal.class));
        roomDes.setGuardenArea( (BigDecimal) this.txtFormattedGardenArea.getValue(BigDecimal.class));
        
        roomDes.setIbasement((BigDecimal) this.txtFormattedIbasement.getValue(BigDecimal.class));  //add by shilei
        roomDes.setIbaInnside((BigDecimal) this.txtFormattedInnside.getValue(BigDecimal.class));
        roomDes.setIbaInnside((BigDecimal) this.txtFormattedIbameasured.getValue(BigDecimal.class));
        roomDes.setInsside((BigDecimal)this.txtFormattedIbaInnside.getValue(BigDecimal.class));
 
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
       super.loadFields();
       RoomDesInfo roomDes = (RoomDesInfo)this.editData;
       if(roomDes!=null){
    	   this.prmtProductType.setValue(roomDes.getProductType());
           this.prmtDirection.setValue(roomDes.getDirection());
           this.prmtEyeSight.setValue(roomDes.getNewEyeSight());
           this.prmtRoomUsage.setValue(roomDes.getNewRoomUsage());
           this.prmtBuildingProperty.setValue(roomDes.getBuildingProperty());
           this.prmtPossStd.setValue(roomDes.getNewPossstd());
           this.prmtSight.setValue(roomDes.getNewSight());
           this.prmtDecoraStd.setValue(roomDes.getNewDecorastd());
           this.prmtRoomModel.setValue(roomDes.getRoomModel());
           this.prmtBuildingStruct.setValue(roomDes.getBuildStruct());
           this.prmtNoise.setValue(roomDes.getNewNoise());
           this.txtFormattedPlanBuildingArea.setValue(roomDes.getPlanBuildingArea());
           this.txtFormattedPlanRoomArea.setValue(roomDes.getPlanRoomArea());
           this.txtFormattedCarbarnArea.setValue(roomDes.getCarbarnArea());
           this.txtFormattedPlateArea.setValue(roomDes.getFlatArea());
           this.txtFormattedBuildingArea.setValue(roomDes.getBuildingArea());
           this.txtFormattedRoomArea.setValue(roomDes.getRoomArea());
           this.txtFormattedBalconyArea.setValue(roomDes.getBalconyArea());
           this.txtFormattedUseArea.setValue(roomDes.getUserArea());
           this.txtFormattedActualBuildingArea.setValue(roomDes.getActualBuildingArea());
           this.txtFormattedActualRoomArea.setValue(roomDes.getActualRoomArea());
           this.txtFormattedGardenArea.setValue(roomDes.getGuardenArea());
           this.txtFormattedIbaInnside.setValue(roomDes.getIbaInnside()); //add by shilei
           this.txtFormattedIbameasured.setValue(roomDes.getIbameasured());
           this.txtFormattedIbasement.setValue(roomDes.getIbasement());
           this.txtFormattedInnside.setValue(roomDes.getInsside());
       }
      
       
    }
    //空实现
    
    protected void checkIsOUSealUp() throws Exception
    {
    	
    }
    public void initAll() throws BOSException, EASBizException{
    	
    	FilterInfo sellProjectFilter=new FilterInfo();
    	sellProjectFilter.getFilterItems().add(new FilterItemInfo("sellProject.id",((SellProjectInfo)SHEManageHelper.getParentSellProject(null, sellPro)).getId()));
    	sellProjectFilter.getFilterItems().add(new FilterItemInfo("orgUnit.id",SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId()));
    	sellProjectFilter.getFilterItems().add(new FilterItemInfo("sellProject.id",null,CompareType.IS));
    	
    	sellProjectFilter.setMaskString("#0 or (#1 and #2)");
    	//户型
    	if(null!= sellPro){
    		String queryInfoRoomModel = "com.kingdee.eas.fdc.sellhouse.app.RoomModelForSHEQuery";
    		/**
    		 * query信息错误,无法过滤出户型的信息
    		 */
    		this.prmtRoomModel.setQueryInfo(queryInfoRoomModel);
    		this.prmtRoomModel.setEntityViewInfo(SHEHelper.getModelBySellProjectView(sellPro));
    	}
    	
    	//视野 
    	EntityViewInfo viewEysSight = new EntityViewInfo();
    	FilterInfo filterEysSight = new FilterInfo();
    	filterEysSight.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
     	filterEysSight.getFilterItems().add(new FilterItemInfo("type.id","pj3Lru9+QVqK/PZoIaNxYLyNrUg="));
     	filterEysSight.mergeFilter(sellProjectFilter, "and");
    	viewEysSight.setFilter(filterEysSight);
    	this.prmtEyeSight.setEntityViewInfo(viewEysSight);
    	
    	//景观
    	EntityViewInfo viewSight = new EntityViewInfo();
    	FilterInfo filterSight = new FilterInfo();
    	filterSight.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterSight.getFilterItems().add(new FilterItemInfo("type.id","aDya2NbITr+ymXXU7/kCW7yNrUg="));
    	filterSight.mergeFilter(sellProjectFilter, "and");
    	viewSight.setFilter(filterSight);
       	this.prmtSight.setEntityViewInfo(viewSight);
    	
       	//噪音
    	EntityViewInfo viewNose = new EntityViewInfo();
    	FilterInfo filterNose = new FilterInfo();
    	filterNose.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterNose.getFilterItems().add(new FilterItemInfo("type.id","Cr7p6ri/QWWy+vdLTV1erLyNrUg="));
    	filterNose.mergeFilter(sellProjectFilter, "and");
    	viewNose.setFilter(filterNose);
     	this.prmtNoise.setEntityViewInfo(viewNose);
    	
     	//建筑结构
    	EntityViewInfo viewBuilStruct = new EntityViewInfo();
    	FilterInfo filterBuilStruct = new FilterInfo();
    	filterBuilStruct.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	viewBuilStruct.setFilter(filterBuilStruct);
     	this.prmtBuildingStruct.setEntityViewInfo(viewBuilStruct);
    	
     	//装修标准
    	EntityViewInfo viewDecoraStd = new EntityViewInfo();
    	FilterInfo filterDecoraStd = new FilterInfo();
    	filterDecoraStd.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterDecoraStd.getFilterItems().add(new FilterItemInfo("type.id","YPBFahQ6TY+RUDdxyiElfryNrUg="));
    	filterDecoraStd.mergeFilter(sellProjectFilter, "and");
    	viewDecoraStd.setFilter(filterDecoraStd);
    	this.prmtDecoraStd.setEntityViewInfo(viewDecoraStd);
    
//    	this.prmtRoomModel.setEntityViewInfo(view);
    	
    	
      
//      	this.prmtRoomModel.setEntityViewInfo(view);
    	//交房标准
    	EntityViewInfo viewPossStd = new EntityViewInfo();
    	FilterInfo filterPossStd  = new FilterInfo();
    	filterPossStd .getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterPossStd.getFilterItems().add(new FilterItemInfo("type.id","ByHexvLVSEusji3qeetJHLyNrUg="));
    	filterPossStd.mergeFilter(sellProjectFilter, "and");
    	viewPossStd .setFilter(filterPossStd );
      	this.prmtPossStd.setEntityViewInfo(viewPossStd);
   
     	//房间用途
    	EntityViewInfo viewRoomUsage = new EntityViewInfo();
    	FilterInfo filterRoomUsage = new FilterInfo();
    	filterRoomUsage.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterRoomUsage.getFilterItems().add(new FilterItemInfo("type.id","zzYdGSPQSuWiAJXK3JKjcbyNrUg="));
    	filterRoomUsage.mergeFilter(sellProjectFilter, "and");
    	viewRoomUsage.setFilter(filterRoomUsage);
      	this.prmtRoomUsage.setEntityViewInfo(viewRoomUsage);

    	//有效 
    	EntityViewInfo view =new EntityViewInfo();
    	FilterInfo filter =new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	view.setFilter(filter);
    	this.prmtDirection.setEntityViewInfo(view);
    	//楼栋属性
    	this.prmtBuildingProperty.setEntityViewInfo(view);
    	
    	//产品类型
    	this.prmtProductType.setEntityViewInfo(view);
    }
    
    public void onLoad() throws Exception {
		super.onLoad();
		initAll();
		setTxtPrecistion();
    }
  public void setTxtPrecistion(){
    	
		this.txtFormattedUseArea.setRemoveingZeroInDispaly(false);
		this.txtFormattedUseArea.setRemoveingZeroInEdit(false);
		this.txtFormattedUseArea.setNegatived(false);
		this.txtFormattedUseArea.setPrecision(3);
		this.txtFormattedUseArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedUseArea.setSupportedEmpty(true);
		
		this.txtFormattedPlanBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtFormattedPlanBuildingArea.setRemoveingZeroInEdit(false);
		this.txtFormattedPlanBuildingArea.setNegatived(false);
		this.txtFormattedPlanBuildingArea.setPrecision(3);
		this.txtFormattedPlanBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedPlanBuildingArea.setSupportedEmpty(true);
		
		this.txtFormattedBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtFormattedBuildingArea.setRemoveingZeroInEdit(false);
		this.txtFormattedBuildingArea.setNegatived(false);
		this.txtFormattedBuildingArea.setPrecision(3);
		this.txtFormattedBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedBuildingArea.setSupportedEmpty(true);
		
		this.txtFormattedPlanRoomArea.setRemoveingZeroInDispaly(false);
		this.txtFormattedPlanRoomArea.setRemoveingZeroInEdit(false);
		this.txtFormattedPlanRoomArea.setNegatived(false);
		this.txtFormattedPlanRoomArea.setPrecision(3);
		this.txtFormattedPlanRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedPlanRoomArea.setSupportedEmpty(true);
		
		this.txtFormattedGardenArea.setRemoveingZeroInDispaly(false);
		this.txtFormattedGardenArea.setRemoveingZeroInEdit(false);
		this.txtFormattedGardenArea.setNegatived(false);
		this.txtFormattedGardenArea.setPrecision(3);
		this.txtFormattedGardenArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedGardenArea.setSupportedEmpty(true);
		
		this.txtFormattedPlateArea.setRemoveingZeroInDispaly(false);
		this.txtFormattedPlateArea.setRemoveingZeroInEdit(false);
		this.txtFormattedPlateArea.setNegatived(false);
		this.txtFormattedPlateArea.setPrecision(3);
		this.txtFormattedPlateArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedPlateArea.setSupportedEmpty(true);
		
		this.txtFormattedCarbarnArea.setRemoveingZeroInDispaly(false);
		this.txtFormattedCarbarnArea.setRemoveingZeroInEdit(false);
		this.txtFormattedCarbarnArea.setNegatived(false);
		this.txtFormattedCarbarnArea.setPrecision(3);
		this.txtFormattedCarbarnArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedCarbarnArea.setSupportedEmpty(true);
		
		this.txtFormattedRoomArea.setRemoveingZeroInDispaly(false);
		this.txtFormattedRoomArea.setRemoveingZeroInEdit(false);
		this.txtFormattedRoomArea.setNegatived(false);
		this.txtFormattedRoomArea.setPrecision(3);
		this.txtFormattedRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedRoomArea.setSupportedEmpty(true);
		
		this.txtFormattedBalconyArea.setRemoveingZeroInDispaly(false);
		this.txtFormattedBalconyArea.setRemoveingZeroInEdit(false);
		this.txtFormattedBalconyArea.setNegatived(false);
		this.txtFormattedBalconyArea.setPrecision(3);
		this.txtFormattedBalconyArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedBalconyArea.setSupportedEmpty(true);
		
		this.txtFormattedActualRoomArea.setRemoveingZeroInDispaly(false);
		this.txtFormattedActualRoomArea.setRemoveingZeroInEdit(false);
		this.txtFormattedActualRoomArea.setNegatived(false);
		this.txtFormattedActualRoomArea.setPrecision(3);
		this.txtFormattedActualRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedActualRoomArea.setSupportedEmpty(true);
		
		this.txtFormattedActualBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtFormattedActualBuildingArea.setRemoveingZeroInEdit(false);
		this.txtFormattedActualBuildingArea.setNegatived(false);
		this.txtFormattedActualBuildingArea.setPrecision(3);
		this.txtFormattedActualBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedActualBuildingArea.setSupportedEmpty(true);
		
		this.txtFormattedIbasement.setRemoveingZeroInDispaly(false); //add by shilei
		this.txtFormattedIbasement.setRemoveingZeroInEdit(false);
		this.txtFormattedIbasement.setPrecision(3);
		this.txtFormattedIbasement.setSupportedEmpty(true);
		this.txtFormattedIbasement.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedIbasement.setSupportedEmpty(true);
		
		this.txtFormattedInnside.setRemoveingZeroInDispaly(false); //add by shilei
		this.txtFormattedInnside.setRemoveingZeroInEdit(false);
		this.txtFormattedInnside.setPrecision(3);
		this.txtFormattedInnside.setSupportedEmpty(true);
		this.txtFormattedInnside.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedInnside.setSupportedEmpty(true);
		
		this.txtFormattedIbameasured.setRemoveingZeroInDispaly(false); //add by shilei
		this.txtFormattedIbameasured.setRemoveingZeroInEdit(false);
		this.txtFormattedIbameasured.setPrecision(3);
		this.txtFormattedIbameasured.setSupportedEmpty(true);
		this.txtFormattedIbameasured.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedIbameasured.setSupportedEmpty(true);
		
		this.txtFormattedIbaInnside.setRemoveingZeroInDispaly(false); //add by shilei
		this.txtFormattedIbaInnside.setRemoveingZeroInEdit(false);
		this.txtFormattedIbaInnside.setPrecision(3);
		this.txtFormattedIbaInnside.setSupportedEmpty(true);
		this.txtFormattedIbaInnside.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFormattedIbaInnside.setSupportedEmpty(true);
		
		
    	
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
    	RoomDesInfo roomDes =null;
    	if(null!= this.getUIContext().get("Data")){
    		 roomDes = (RoomDesInfo)this.getUIContext().get("Data");
    	}
    	if(null!=roomDes.getId()){
    		try {
				roomDes = RoomDesFactory.getRemoteInstance().getRoomDesInfo(new ObjectUuidPK(BOSUuid.read(roomDes.getId().toString())),SHEHelper.getRoomDesSelector());
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				logger.warn("加载房间定义信息失败！ ");
				FDCMsgBox.showWarning("加载房间定义信息失败！ ");
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				logger.warn("加载房间定义信息失败！ ");
				FDCMsgBox.showWarning("加载房间定义信息失败！ ");
			} catch (UuidException e) {
				// TODO Auto-generated catch block
				logger.warn("加载房间定义信息失败！ ");
				FDCMsgBox.showWarning("加载房间定义信息失败！ ");
			}
    	}
    	if(null!= this.getUIContext().get("SellProject")){
    		sellPro = (SellProjectInfo)this.getUIContext().get("SellProject");
    	}
        return roomDes;
    }

	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}