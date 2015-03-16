/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractAgioSelectUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionYes(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionYes(request,response,context);
	}
	protected void _handleActionYes(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionNo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionNo(request,response,context);
	}
	protected void _handleActionNo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}