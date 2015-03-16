/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineFactory;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class AppraiseGuideLineEditUI extends AbstractAppraiseGuideLineEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AppraiseGuideLineEditUI.class);
    private OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
    
    private AppraiseGuideLineTypeInfo typeInfo = new AppraiseGuideLineTypeInfo();
    /**
     * output class constructor
     */
    public AppraiseGuideLineEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
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
        
        this.prmtGuideLineType.setValue(typeInfo);
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
		// TODO 自动生成方法存根
		AppraiseGuideLineInfo object = new AppraiseGuideLineInfo();		
		AppraiseGuideLineTypeInfo type = (AppraiseGuideLineTypeInfo)this.getUIContext().get("type");
		object.setGuideLineType(type);
		object.setIsEnable(true);
		return object;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return AppraiseGuideLineFactory.getRemoteInstance();
		
	}

	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();
		boolean canAdd = false;
		if(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			canAdd = true;
		}
		if(!canAdd)
		{
			this.actionAddNew.setEnabled(canAdd);
			this.actionRemove.setEnabled(canAdd);
			this.actionEdit.setEnabled(canAdd);
			this.actionCancel.setEnabled(canAdd);
			this.actionCancelCancel.setEnabled(canAdd);
			this.actionCancel.setVisible(canAdd);
			this.actionCancelCancel.setVisible(canAdd);
		}
		this.txtName.setRequired(true);
		this.txtNumber.setRequired(true);
		this.prmtGuideLineType.setEnabled(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionSave.setVisible(false);
		
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.txtName.setMaxLength(80);
		this.txtNumber.setMaxLength(80);
		
		this.kDTextArea1.setMaxLength(1000);
		
		if(editData != null && editData.getGuideLineType() != null)
		{
			typeInfo = editData.getGuideLineType();
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.verifyInput(e);
		if(editData.getNumber()==null||editData.getNumber().trim().length()==0){
			FDCMsgBox.showWarning(this,"编码不能为空");
			abort();
		}
		if(editData.getName()==null||editData.getName().trim().length()==0){
			FDCMsgBox.showWarning(this,"名称不能为空");
			abort();
		}
		if(editData.getDescription()!=null&&editData.getDescription().length()>1000){
			FDCMsgBox.showWarning(this,"考核指标说明不能超过1000个字");
			abort();
			
		}
	}

	public SelectorItemCollection getSelectors() {
		// TODO 自动生成方法存根
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("*"));
        sic.add(new SelectorItemInfo("guideLineType.*"));
        sic.add(new SelectorItemInfo("unit.*"));
        return sic;
	}

}