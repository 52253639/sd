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
public abstract class AbstractProjectCalendarEditUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOK(request,response,context);
	}
	protected void _handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddLine(request,response,context);
	}
	protected void _handleActionAddLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeleteLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteLine(request,response,context);
	}
	protected void _handleActionDeleteLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}