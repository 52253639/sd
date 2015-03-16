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
public class KeyIndexReportFilterUI extends AbstractKeyIndexReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(KeyIndexReportFilterUI.class);
    
    /**
     * output class constructor
     */
    public KeyIndexReportFilterUI() throws Exception
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
		RptParams pp = new RptParams();
		
		return pp;
	}

	public void onInit(RptParams rptparams) throws Exception {
		
	}

	public void setCustomCondition(RptParams rptparams) {
		
	}

}