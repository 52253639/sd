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
public abstract class AbstractAimCostProductSplitSettingUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionSelectAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectAll(request,response,context);
	}
	protected void _handleActionSelectAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSelectNone(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectNone(request,response,context);
	}
	protected void _handleActionSelectNone(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplit(request,response,context);
	}
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}