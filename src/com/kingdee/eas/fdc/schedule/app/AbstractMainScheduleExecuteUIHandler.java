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
public abstract class AbstractMainScheduleExecuteUIHandler extends com.kingdee.eas.fdc.schedule.app.FDCScheduleBaseEditUIHandler

{
	public void handleActionWeekReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionWeekReport(request,response,context);
	}
	protected void _handleActionWeekReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMonthReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMonthReport(request,response,context);
	}
	protected void _handleActionMonthReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTaskApprise(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTaskApprise(request,response,context);
	}
	protected void _handleActionTaskApprise(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionScheduleReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionScheduleReport(request,response,context);
	}
	protected void _handleActionScheduleReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}