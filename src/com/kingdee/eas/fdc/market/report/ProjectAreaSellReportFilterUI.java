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
public class ProjectAreaSellReportFilterUI extends AbstractProjectAreaSellReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectAreaSellReportFilterUI.class);
    
    public ProjectAreaSellReportFilterUI() throws Exception
    {
        super();
    }

	public void onLoad() throws Exception {
		super.onLoad();
		Long obj = new Long(2012);
		year.setValue(obj);
	}
	
	public RptParams getCustomCondition() {
		RptParams pp = new RptParams();
        if(this.year.getValue()!=null){
   		pp.setObject("year", this.year.getValue());
        	pp.setObject("year", this.year.getValue());
        }
		return pp;
	}

	public void onInit(RptParams arg0) throws Exception {
		
	}

	public void setCustomCondition(RptParams arg0) {
		
	}

}