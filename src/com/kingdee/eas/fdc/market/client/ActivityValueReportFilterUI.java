/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class ActivityValueReportFilterUI extends AbstractActivityValueReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ActivityValueReportFilterUI.class);
    
    /**
     * output class constructor
     */
    public ActivityValueReportFilterUI() throws Exception
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

	public RptParams getCustomCondition() {
		return new RptParams();
	}

	public void onInit(RptParams arg0) throws Exception {
		
	}

	public void setCustomCondition(RptParams arg0) {
		
	}

}