/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.AttachResourceInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.eas.fdc.tenancy.AttachResourceFactory;
import com.kingdee.eas.fdc.tenancy.AttachSourceTypeEnum;
import com.kingdee.eas.fdc.tenancy.AttachResourceEnum;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

/**
 * output class name
 */
public class AttachResourceEditUI extends AbstractAttachResourceEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AttachResourceEditUI.class);
    
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    /**
     * output class constructor
     */
    public AttachResourceEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
		initControl();
	    super.onLoad();
		this.storeFields();
		this.initOldData(this.editData);
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
		}
	}
    
    private void initControl() {
    	this.menuBiz.setVisible(false);
    	this.menuHelp.setVisible(false);
    	this.menuTool.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionAttachment.setEnabled(false);
		this.actionCalculator.setVisible(false);
		this.actionCopy.setVisible(false);
		this.txtSellProjectNumber.setEnabled(false);
		this.txtSellProjectName.setEnabled(false);
		this.actionAddNew.setVisible(false);
		this.actionRemove.setVisible(false);
		this.txtName.setRequired(true);
		this.txtNumber.setRequired(true);
		if (STATUS_EDIT.equals(this.getOprtState())) {
			this.f7Building.setEnabled(false);
	    	this.txtSubareaName.setEditable(false);
	    	this.txtNumber.setEditable(true);
	    	this.actionRemove.setVisible(false);
		}if(STATUS_VIEW.equals(this.getOprtState()))
		{
			this.f7Building.setEnabled(false);
			this.txtSubareaName.setEnabled(false);
		}
	}

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
        AttachResourceInfo attachInfo = (AttachResourceInfo)this.editData;
        this.txtSellProjectNumber.setText(attachInfo.getSellProject().getNumber());
        this.txtSellProjectName.setText(attachInfo.getSellProject().getName());
        if(attachInfo.getSubarea()!=null)
        {
        	this.txtSubareaName.setText(attachInfo.getSubarea().getName());
        	this.txtSubareaName.setEditable(false);
        }
        else
        {
        	this.txtSubareaName.setText(null);
        	this.txtSubareaName.setEditable(false);
        }
        
        this.txtNumber.setText(attachInfo.getNumber());
        this.f7Building.setValue(attachInfo.getBuilding());
        this.txtName.setText(attachInfo.getName());
        //this.comboAttachType.setSelectedItem(attachInfo.getAttachType());
        //this.comboAttachState.setSelectedItem(attachInfo.getAttachState());
        this.txtDescription.setText(attachInfo.getDescription());
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
        AttachResourceInfo attachInfo = (AttachResourceInfo)this.editData;
        attachInfo.setSellProject(attachInfo.getSellProject());
        attachInfo.setSubarea(attachInfo.getSubarea());
        attachInfo.setBuilding((BuildingInfo)this.f7Building.getValue());
        attachInfo.setDescription(this.txtDescription.getText());
        attachInfo.setNumber(this.txtNumber.getText());
        attachInfo.setName(this.txtName.getText());
        //attachInfo.setAttachType((AttachSourceTypeEnum)this.comboAttachType.getSelectedItem());
       // attachInfo.setAttachState((AttachResourceEnum)this.comboAttachState.getSelectedItem());
    }

    public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("*");
		selectors.add("building.*");
		selectors.add("building.sellproject.*");
		selectors.add("building.subarea.*");
		selectors.add("subarea.*");
		selectors.add("subarea.sellproject.*");
		selectors.add("sellProject.*");
		selectors.add("sellProject.subarea.*");
		//selectors.add("sellProject.buildings.*");
		return selectors;
	}

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable()
    {        
        return null;
	}    

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return new com.kingdee.eas.fdc.tenancy.AttachResourceInfo();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.tenancy.AttachResourceInfo objectValue = new com.kingdee.eas.fdc.tenancy.AttachResourceInfo();
        SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
        EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("sellProject.id", sellProject.getId()
						.toString()));
		this.f7Building.setEntityViewInfo(view);
		SubareaInfo subAreaInfo = (SubareaInfo) this.getUIContext().get("subarea");
        if(subAreaInfo == null)
        {
        	objectValue.setSubarea(null);
        }else
        {
        	objectValue.setSubarea(subAreaInfo);
    		filter.getFilterItems().add(
    				new FilterItemInfo("subarea.id", subAreaInfo.getId()
    						.toString()));
    		this.f7Building.setEntityViewInfo(view);
        }
        BuildingInfo buildingInfo = (BuildingInfo) this.getUIContext().get("buildingInfo");
        if(buildingInfo==null)
        {
        	objectValue.setBuilding(buildingInfo);
        }else
        {
        	objectValue.setBuilding(buildingInfo);
        	this.f7Building.setValue(buildingInfo);
        	this.f7Building.setEnabled(false);
        }
        objectValue.setSellProject(sellProject);
        objectValue.setTenancyState(TenancyStateEnum.unTenancy);
        objectValue.setAttachState(AttachResourceEnum.NOTATTACHRESOURCE);
        //objectValue.setAttachType((AttachSourceTypeEnum)this.comboAttachType.getSelectedItem());
        return objectValue;
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.storeFields();
		this.initOldData(this.editData);
	}
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	if (!saleOrg.isIsBizUnit()) {
    		MsgBox.showInfo("非销售实体不能修改！");
    		this.abort();
		}
		super.actionEdit_actionPerformed(e);
		this.f7Building.setEnabled(false);
    	this.txtSubareaName.setEditable(false);
    	this.txtNumber.setEditable(true);
		this.storeFields();
		this.initOldData(this.editData);
	}
    
	protected ICoreBase getBizInterface() throws Exception {
		return AttachResourceFactory.getRemoteInstance();
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(StringUtils.isEmpty(this.txtNumber.getText()))
		{
			MsgBox.showInfo("配套资源编码必须录入！");
			abort();
		}if(StringUtils.isEmpty(this.txtName.getText()))
		{
			MsgBox.showInfo("配套资源名称必须录入！");
			abort();
		}if(this.txtDescription.getText().length()>255)
		{
			MsgBox.showInfo("描述不能超过255个字符！");
			abort();
		}
	}

}