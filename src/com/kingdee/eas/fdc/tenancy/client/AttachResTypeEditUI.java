/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.AttachResTypeFactory;
import com.kingdee.eas.fdc.tenancy.AttachResTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AttachResTypeEditUI extends AbstractAttachResTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AttachResTypeEditUI.class);

    public AttachResTypeEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	this.actionRemove.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.txtName.setRequired(true);
		this.txtNumber.setRequired(true);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	if(this.txtName.getText().length()==0){
    		MsgBox.showInfo("名称不能为空。");
    		abort();
    	}
    	if(this.txtName.getText().length()>80){
    		MsgBox.showInfo("名称不能超过80。");
    		abort();
    	}
    	if(this.txtNumber.getText().length()==0){
    		MsgBox.showInfo("编码不能为空。");
    		abort();
    	}
    	if(this.txtNumber.getText().length()>80){
    		MsgBox.showInfo("编码不能超过80。");
    		abort();
    	}
    	if(this.txtDescription.getText().length() > 255){
    		MsgBox.showInfo("描述长度不能超过255。");
			abort();
    	}
    	if(this.txtSimpleName.getText().length() > 80){
    		MsgBox.showInfo("简称长度不能超过80。");
			abort();
    	}
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", editData.getName()));
		if(STATUS_EDIT.equals(this.getOprtState()) && editData.getId() != null){
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
		}
		if(this.getBizInterface().exists(filter)){
			MsgBox.showInfo("名称"+editData.getName()+"已经存在，不能重复。");
			abort();
		}
    }
    
    public void storeFields()
    {
        super.storeFields();
    }

	protected IObjectValue createNewData() {
		return new AttachResTypeInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AttachResTypeFactory.getRemoteInstance();
	}
}