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
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class OrgUnitMonthPGReportFilterUI extends AbstractOrgUnitMonthPGReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(OrgUnitMonthPGReportFilterUI.class);
    public OrgUnitMonthPGReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.spYear.setModel(new SpinnerNumberModel(this.spYear.getIntegerVlaue().intValue(),1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(this.spMonth.getIntegerVlaue().intValue(),1,12,1));
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
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(FDCDateHelper.getNextMonth(now));
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		this.spYear.setValue(year);
		this.spMonth.setValue(month);
	}
	public void setCustomCondition(RptParams params) {
		this.spYear.setValue(params.getObject("year"));
		this.spMonth.setValue(params.getObject("month"));
	}
}