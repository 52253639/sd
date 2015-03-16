/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractAimCostCompareUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionChart(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChart(request,response,context);
	}
	protected void _handleActionChart(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}