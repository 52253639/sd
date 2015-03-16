/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeBalanceObjectEnum;
import com.kingdee.eas.fdc.sellhouse.QuestionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SaleBlanceSetting;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SaleBlanceSettingUI extends AbstractSaleBlanceSettingUI
{
    private static final Logger logger = CoreUIObject.getLogger(SaleBlanceSettingUI.class);
    
    SaleBlanceSetting saleBlanSetting = new SaleBlanceSetting();
    Map isCheckMap = new HashMap();
    public SaleBlanceSettingUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
//    	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
//		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID))
//		{
//			MsgBox.showInfo("非集团用户不能修改销售结算检查设置!");
//			this.abort();
//		}
    	if(saleBlanSetting.getSincerObject()==null)
		{
			this.comboSincer.setSelectedItem(QuestionTypeEnum.prompt);
		}else
		{
			this.comboSincer.setSelectedItem(saleBlanSetting.getSincerObject());
		}	
    	
    	if(saleBlanSetting.getNoAuditPur()==null)
		{
			this.comboNoAuditPur.setSelectedItem(QuestionTypeEnum.prompt);
		}else
		{
			this.comboNoAuditPur.setSelectedItem(saleBlanSetting.getNoAuditPur());
		}
    	
    	if(saleBlanSetting.getNoApplyPrePur()==null)
		{
			this.comboNoApplyPrePur.setSelectedItem(QuestionTypeEnum.prompt);
		}else
		{
			this.comboNoApplyPrePur.setSelectedItem(saleBlanSetting.getNoApplyPrePur());
		}
    	
    	if(saleBlanSetting.getFirstAmountPur()==null)
		{
			this.comboFirstAmountPur.setSelectedItem(QuestionTypeEnum.prompt);
		}else
		{
			this.comboFirstAmountPur.setSelectedItem(saleBlanSetting.getFirstAmountPur());
		}
    	
    	if(saleBlanSetting.getNoAuditQuitRoom()==null)
		{
			this.comboNoAuditQuitRoom.setSelectedItem(QuestionTypeEnum.prompt);
		}else
		{
			this.comboNoAuditQuitRoom.setSelectedItem(saleBlanSetting.getNoAuditQuitRoom());
		}
    	
    	if(saleBlanSetting.getCanRefundmentAmount()==null)
		{
			this.comboCanRefundmentAmount.setSelectedItem(QuestionTypeEnum.prompt);
		}else
		{
			this.comboCanRefundmentAmount.setSelectedItem(saleBlanSetting.getCanRefundmentAmount());
		}
    	
    	if(saleBlanSetting.getNoAuditPurChange()==null)
		{
			this.comboNoAuditPurChange.setSelectedItem(QuestionTypeEnum.prompt);
		}else
		{
			this.comboNoAuditPurChange.setSelectedItem(saleBlanSetting.getNoAuditPurChange());
		}
    	
    	if(saleBlanSetting.getNoAuditChangeRoom()==null)
		{
			this.comboNoAuditChangeRoom.setSelectedItem(QuestionTypeEnum.prompt);
		}else
		{
			this.comboNoAuditChangeRoom.setSelectedItem(saleBlanSetting.getNoAuditChangeRoom());
		}
    	
    	if(saleBlanSetting.getLoanNoRev()==null)
    	{
    		this.comboLoanNoRev.setSelectedItem(QuestionTypeEnum.prompt);
    	}else
    	{
    		this.comboLoanNoRev.setSelectedItem(saleBlanSetting.getLoanNoRev());
    	}
    	
    	if(saleBlanSetting.getAccFundNoRev()==null)
    	{
    		this.comboAccFundNoRev.setSelectedItem(QuestionTypeEnum.prompt);
    	}else
    	{
    		this.comboAccFundNoRev.setSelectedItem(saleBlanSetting.getAccFundNoRev());
    	}

    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnConfirm_actionPerformed(e);
        saleBlanSetting.setSincerObject(this.comboSincer.getSelectedItem());
        saleBlanSetting.setNoAuditPur(this.comboNoAuditPur.getSelectedItem());
        saleBlanSetting.setNoApplyPrePur(this.comboNoApplyPrePur.getSelectedItem());
        saleBlanSetting.setFirstAmountPur(this.comboFirstAmountPur.getSelectedItem());
        saleBlanSetting.setNoAuditQuitRoom(this.comboNoAuditQuitRoom.getSelectedItem());
        saleBlanSetting.setCanRefundmentAmount(this.comboCanRefundmentAmount.getSelectedItem());
        saleBlanSetting.setNoAuditPurChange(this.comboNoAuditPurChange.getSelectedItem());
        saleBlanSetting.setNoAuditChangeRoom(this.comboNoAuditChangeRoom.getSelectedItem());
        saleBlanSetting.setLoanNoRev(this.comboLoanNoRev.getSelectedItem());
        saleBlanSetting.setAccFundNoRev(this.comboAccFundNoRev.getSelectedItem());
		saleBlanSetting.save();
		this.destroyWindow();
    }

    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnCancel_actionPerformed(e);
        this.destroyWindow();
    }

}