/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractProjectInvestPlanEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionSetCostAccountLevel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetCostAccountLevel(request,response,context);
	}
	protected void _handleActionSetCostAccountLevel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBtnOk(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnOk(request,response,context);
	}
	protected void _handleActionBtnOk(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}