/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class CustomerVisitSumReportFilterUI extends AbstractCustomerVisitSumReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerVisitSumReportFilterUI.class);
    
    /**
     * output class constructor
     */
    public CustomerVisitSumReportFilterUI() throws Exception
    {
        super();
    }

    /* (non-Javadoc)
	 * @see com.kingdee.eas.base.commonquery.client.CustomerQueryPanel#onLoad()
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		this.pkBeginDate.setValue(null);
		this.pkEndDate.setValue(null);
	}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         if(this.pkBeginDate.getValue()!=null){
    		 pp.setObject("beginDate", this.pkBeginDate.getSqlDate());
         }else{
        	 pp.setObject("beginDate", null);
         }
         if(this.pkEndDate.getValue()!=null){
    		 pp.setObject("endDate", this.pkEndDate.getSqlDate());
         }else{
        	 pp.setObject("endDate", null);
         }
		 return pp;
	}

	public void onInit(RptParams rptparams) throws Exception {
		
	}

	public void setCustomCondition(RptParams rptparams) {
		this.pkBeginDate.setValue(rptparams.getObject("beginDate"));
		this.pkEndDate.setValue(rptparams.getObject("endDate"));
	}

}