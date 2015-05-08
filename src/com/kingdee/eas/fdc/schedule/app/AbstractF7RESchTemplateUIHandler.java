/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractF7RESchTemplateUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionSelectAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectAll(request,response,context);
	}
	protected void _handleActionSelectAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClearAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClearAll(request,response,context);
	}
	protected void _handleActionClearAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionOk(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOk(request,response,context);
	}
	protected void _handleActionOk(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQuit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuit(request,response,context);
	}
	protected void _handleActionQuit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}