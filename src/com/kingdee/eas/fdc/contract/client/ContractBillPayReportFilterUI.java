/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCRoomPromptDialog;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class ContractBillPayReportFilterUI extends AbstractContractBillPayReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractBillPayReportFilterUI.class);
    public ContractBillPayReportFilterUI() throws Exception
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
		
		this.spYear.setModel(new SpinnerNumberModel(year,1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(month,1,12,1));
    }
    public boolean verify()
    {
        return true;
    }
	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         pp.setObject("year", this.spYear.getIntegerVlaue());
         pp.setObject("month", this.spMonth.getIntegerVlaue());
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.spYear.setValue(params.getObject("year"));
		this.spMonth.setValue(params.getObject("month"));
	}
}