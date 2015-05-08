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
public abstract class AbstractPlanEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionSearch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSearch(request,response,context);
	}
	protected void _handleActionSearch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionScheduleReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionScheduleReport(request,response,context);
	}
	protected void _handleActionScheduleReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportProject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportProject(request,response,context);
	}
	protected void _handleActionExportProject(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionProperty(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProperty(request,response,context);
	}
	protected void _handleActionProperty(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefresh(request,response,context);
	}
	protected void _handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}