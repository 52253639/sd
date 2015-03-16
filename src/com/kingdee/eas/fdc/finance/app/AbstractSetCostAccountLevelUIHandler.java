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
public abstract class AbstractSetCostAccountLevelUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionOk(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOk(request,response,context);
	}
	protected void _handleActionOk(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelSelect(request,response,context);
	}
	protected void _handleActionCancelSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}