/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCRoomPromptDialog;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class ContractBillPayPlanReportFilterUI extends AbstractContractBillPayPlanReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractBillPayPlanReportFilterUI.class);
    public ContractBillPayPlanReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		this.spSYear.setModel(new SpinnerNumberModel(year,1,10000,1));
		this.spSMonth.setModel(new SpinnerNumberModel(month,1,12,1));
		
		this.spEYear.setModel(new SpinnerNumberModel(year,1,10000,1));
		this.spEMonth.setModel(new SpinnerNumberModel(month,1,12,1));
    }
    public boolean verify()
    {
    	if(this.spEYear.getIntegerVlaue().intValue()<this.spSYear.getIntegerVlaue().intValue()||
    			(this.spEYear.getIntegerVlaue().intValue()==this.spSYear.getIntegerVlaue().intValue()&&
    					this.spEMonth.getIntegerVlaue().intValue()<this.spSMonth.getIntegerVlaue().intValue())){
    		 FDCMsgBox.showWarning("开始日期不能晚于结束日期！");
             return false;
    	}
        return true;
    }
	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         pp.setObject("syear", this.spSYear.getIntegerVlaue());
         pp.setObject("smonth", this.spSMonth.getIntegerVlaue());
         pp.setObject("eyear", this.spEYear.getIntegerVlaue());
         pp.setObject("emonth", this.spEMonth.getIntegerVlaue());
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.spSYear.setValue(params.getObject("syear"));
		this.spSMonth.setValue(params.getObject("smonth"));
		this.spEYear.setValue(params.getObject("eyear"));
		this.spEMonth.setValue(params.getObject("emonth"));
	}
}