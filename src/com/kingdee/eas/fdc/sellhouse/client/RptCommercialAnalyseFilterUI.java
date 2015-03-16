
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;

import com.kingdee.eas.framework.report.util.RptConditionManager;
import com.kingdee.eas.framework.report.util.RptParams;


public class RptCommercialAnalyseFilterUI extends AbstractRptCommercialAnalyseFilterUI
{
	private static final long serialVersionUID = 6321200545641709754L;
	private static final Logger logger = CoreUIObject.getLogger(RptCommercialAnalyseFilterUI.class);

    public RptCommercialAnalyseFilterUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }

	public boolean verify()
	{
		return true;
	}

	public void onInit(RptParams initParams) throws Exception
	{
		
	}

	public RptParams getCustomCondition()
	{

		RptConditionManager rm = new RptConditionManager();
		rm.recordAllStatus(this);
	
		return rm.toRptParams();
	}

	public void setCustomCondition(RptParams params)
	{
		RptConditionManager rm = new RptConditionManager(params);
		rm.restoreAllStatus(this);
	}

}