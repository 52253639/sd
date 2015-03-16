/**
 * output package name
 */
package com.kingdee.eas.fdc.market.report;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class EnterpriseDevReportFilterUI extends AbstractEnterpriseDevReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(EnterpriseDevReportFilterUI.class);
    
    /**
     * output class constructor
     */
    public EnterpriseDevReportFilterUI() throws Exception
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

	/* (non-Javadoc)
	 * @see com.kingdee.eas.base.commonquery.client.CustomerQueryPanel#onLoad()
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		this.pkfromBeginDate.setValue(null);
		this.pktoBeginDate.setValue(null);
		this.pkfromEndDate.setValue(null);
		this.pktoEndDate.setValue(null);
	}

	public RptParams getCustomCondition() {
		RptParams pp = new RptParams();
		//实际开始时间
		if(this.pkfromBeginDate.getValue()!=null){
			pp.setObject("fromBeginDate", this.pkfromBeginDate.getSqlDate());
		}else{
			pp.setObject("fromBeginDate", null);
		}
		if(this.pktoBeginDate.getValue()!=null){
			pp.setObject("toBeginDate", this.pktoBeginDate.getSqlDate());
		}else{
			pp.setObject("toBeginDate", null);
		}
       //实际结束时间
        if(this.pkfromEndDate.getValue()!=null){
        	pp.setObject("fromEndDate", this.pkfromEndDate.getSqlDate());
		}else{
			pp.setObject("fromEndDate", null);
		}
		if(this.pktoEndDate.getValue()!=null){
			pp.setObject("toEndDate", this.pktoEndDate.getSqlDate());
		}else{
		   pp.setObject("toEndDate", null);
		}
		return pp;
	}

	public void onInit(RptParams arg0) throws Exception {
		
		
	}

	public void setCustomCondition(RptParams rptparams) {
		this.pkfromBeginDate.setValue(rptparams.getObject("fromBeginDate"));
		this.pktoBeginDate.setValue(rptparams.getObject("toBeginDate"));
		
		this.pkfromEndDate.setValue(rptparams.getObject("fromEndDate"));
		this.pktoEndDate.setValue(rptparams.getObject("toEndDate"));
	}

}