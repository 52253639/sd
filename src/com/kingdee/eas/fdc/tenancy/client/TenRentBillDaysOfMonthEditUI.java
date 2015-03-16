/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.math.BigDecimal;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardFactory;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.DaysOfMonthSettingEnum;
import com.kingdee.eas.fdc.tenancy.TenRentBillDaysOfMonthFactory;
import com.kingdee.eas.fdc.tenancy.TenRentBillDaysOfMonthInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public class TenRentBillDaysOfMonthEditUI extends AbstractTenRentBillDaysOfMonthEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(TenRentBillDaysOfMonthEditUI.class);
    
    /**
     * output class constructor
     */
    public TenRentBillDaysOfMonthEditUI() throws Exception
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
    
    public void onLoad() throws Exception {
		super.onLoad();
		//增加枚举值
		this.actionReset.setVisible(false);
		this.actionReset.setEnabled(false);
		this.btnSave.setEnabled(false);
		this.btnSave.setVisible(false);
		this.kDComboBox1.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.DaysOfMonthSettingEnum").toArray());
		SpinnerNumberModel model = null;
		model = new SpinnerNumberModel(365,0,1000,1);
		this.kDSpinner2.setModel(model);
		this.txtDaysOfMonth.setPrecision(2);
		this.actionCancelCancel.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.setUITitle("月天数设置");
		
	}
    
    private void checkNullAndDump() throws Exception {
    	TenRentBillDaysOfMonthInfo info = (TenRentBillDaysOfMonthInfo) this.editData;
		if (info == null) {
			SysUtil.abort();
		}
		String number = this.txtNumber.getText().trim();
		if (number == null || "".equals(number)) {
			MsgBox.showInfo("编码不能为空！");
			SysUtil.abort();
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (TenRentBillDaysOfMonthFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("编码不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("number", number));
				if (TenRentBillDaysOfMonthFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("编码不能重复！");
					SysUtil.abort();
				}
			}

		}
		String name = this.txtName.getText().trim();
		if (name == null || "".equals(name)) {
			MsgBox.showInfo("名称不能为空！");
			SysUtil.abort();
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));

			if (TenRentBillDaysOfMonthFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("名称不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("name", name));
				if (TenRentBillDaysOfMonthFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("名称不能重复！");
					SysUtil.abort();
				}
			}

		}

	}
    
    public void kDComboBox1_itemStateChanged(ItemEvent e)throws Exception{
        //write your code here
    	super.kDComboBox1_itemStateChanged(e);
    	if(DaysOfMonthSettingEnum.AVERDAYSOFMONTH.equals((DaysOfMonthSettingEnum)this.kDComboBox1.getSelectedItem())){
    		//控件变化
    		this.kDLabelContainer1.setVisible(false);
    		this.kDLabelContainer1.setEnabled(false);
    		this.txtDaysOfMonth.setEnabled(false);
    		//显示月天数值
    		this.txtDaysOfMonth.setValue(FDCHelper.divide(new BigDecimal(365),new BigDecimal(12)));
    	}else if(DaysOfMonthSettingEnum.DAYSOFMONTHBYYEAR.equals((DaysOfMonthSettingEnum)this.kDComboBox1.getSelectedItem())){
    		//控件变化
    		this.kDLabelContainer1.setVisible(true);
    		this.kDLabelContainer1.setEnabled(true);
    		this.txtDaysOfMonth.setEnabled(false);
    		
    		//清空值
    		this.txtDaysOfMonth.setValue(FDCHelper.divide(FDCHelper.toBigDecimal(this.kDSpinner2.getValue()), new BigDecimal(12)));
    	}else if(DaysOfMonthSettingEnum.FIXEDDAYS.equals((DaysOfMonthSettingEnum)this.kDComboBox1.getSelectedItem())){
    		//控件变化
    		this.kDLabelContainer1.setVisible(false);
    		this.kDLabelContainer1.setEnabled(false);
    		this.txtDaysOfMonth.setEnabled(true);
    		//得到值
    		this.txtDaysOfMonth.setValue(null);
    	}
    }
    
    
    public void kDSpinner2_stateChanged(javax.swing.event.ChangeEvent e) throws Exception{
        //write your code here
    	super.kDSpinner2_stateChanged(e);
    	this.txtDaysOfMonth.setValue(FDCHelper.divide(FDCHelper.toBigDecimal(this.kDSpinner2.getValue()),new BigDecimal(12)));
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
    	checkNullAndDump();
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {	
    	checkNullAndDump();
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
		TenRentBillDaysOfMonthInfo daysOfMonthInfo = new TenRentBillDaysOfMonthInfo();
		return daysOfMonthInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return TenRentBillDaysOfMonthFactory.getRemoteInstance();
		
	}
	public void initOldData(IObjectValue value){
		
	}
}