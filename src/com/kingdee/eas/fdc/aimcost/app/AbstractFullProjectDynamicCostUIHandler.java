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
public abstract class AbstractFullProjectDynamicCostUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionChart(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChart(request,response,context);
	}
	protected void _handleActionChart(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSave(request,response,context);
	}
	protected void _handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMonthSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMonthSave(request,response,context);
	}
	protected void _handleActionMonthSave(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}