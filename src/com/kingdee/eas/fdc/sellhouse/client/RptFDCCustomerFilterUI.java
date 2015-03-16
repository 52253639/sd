
package com.kingdee.eas.fdc.sellhouse.client;


import java.awt.event.ItemEvent;
import java.util.Date;

import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;

import com.kingdee.eas.base.permission.UserInfo;

import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;

import com.kingdee.eas.framework.report.util.RptConditionManager;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.client.MsgBox;


public class RptFDCCustomerFilterUI extends AbstractRptFDCCustomerFilterUI
{
	private static final long serialVersionUID = -5807771673441734683L;
	private static final Logger logger = CoreUIObject.getLogger(RptFDCCustomerFilterUI.class);
    
    public RptFDCCustomerFilterUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }

	public boolean verify()
	{

		Date bookedDateFrom = (Date)this.bookedDateFrom.getValue();
		Date bookedDateTo = (Date)this.bookedDateTo.getValue();
		
	
	    if ((bookedDateFrom != null && bookedDateTo != null) && bookedDateTo.before(bookedDateFrom))
		{
			MsgBox.showInfo("登记时间范围不正确。");
			this.bookedDateFrom.requestFocus();
			return false;
		} 
		return true;
	}

	public void onInit(RptParams initParams) throws Exception
	{
		
	}

	public RptParams getCustomCondition()
	{
		RptConditionManager rm = new RptConditionManager();
		rm.recordAllStatus(this);
		
		//用户过滤
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
//		Set personIds = FDCCustomerHelper.getChildPersonIdsOfCurrentUser();	
//		rm.setProperty("personIds",personIds);
	
		rm.setProperty("currentUserInfo",currentUserInfo);
		
		return rm.toRptParams();
	}

	public void setCustomCondition(RptParams params)
	{
		RptConditionManager rm = new RptConditionManager(params);
		rm.restoreAllStatus(this);
		
	}
	public void onLoad() throws Exception
	{
		super.onLoad();
		
		this.tradeTime.setMinimumValue(FDCHelper.ZERO);
	
		this.comboxSex.insertItemAt(new String("全部"),0);
		this.comboxSex.setSelectedIndex(0);
	
		this.tradeTime.setSupportedEmpty(true);
		
		this.tradeTime.setHorizontalAlignment(JTextField.RIGHT);
	
		this.tradeTime.setRemoveingZeroInEdit(false);
		
		this.cbCustomerType.setSelectedIndex(1);
	}
	protected void cbCustomerType_itemStateChanged(ItemEvent e) throws Exception
	{
		 CustomerTypeEnum type = (CustomerTypeEnum)this.cbCustomerType.getSelectedItem();
		 
		 this.chageUI(type);
	}
	
	private void chageUI(CustomerTypeEnum type)
	{
		if(CustomerTypeEnum.EnterpriceCustomer.equals(type))
		{
			this.lcIndustry.setVisible(true);
			this.f7Industry.setValue(null);
			this.comboxSex.setSelectedIndex(0);
			this.lcSex.setVisible(false);
			this.rbEnterpriceSize.setVisible(true);
			this.jrbFamillyEarning.setVisible(false);
			this.rbIndustry.setVisible(true);
			this.jrbSex.setVisible(false);
		
		}
		else if(CustomerTypeEnum.PersonalCustomer.equals(type))
		{
			this.lcIndustry.setVisible(false);
			this.f7Industry.setValue(null);
			this.comboxSex.setSelectedIndex(0);
			this.lcSex.setVisible(true);
			
			this.rbIndustry.setVisible(false);
			this.rbEnterpriceSize.setVisible(false);
			this.jrbFamillyEarning.setVisible(true);
			this.jrbSex.setVisible(true);
		}
	}

}