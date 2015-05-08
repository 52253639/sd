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
public abstract class AbstractSpecialScheduleExecuteUIHandler extends com.kingdee.eas.fdc.schedule.app.SpecialScheduleEditUIHandler

{
	public void handleactionWeekReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionWeekReport(request,response,context);
	}
	protected void _handleactionWeekReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionMonthReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionMonthReport(request,response,context);
	}
	protected void _handleactionMonthReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddScheduleReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddScheduleReport(request,response,context);
	}
	protected void _handleActionAddScheduleReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddTaskApprise(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddTaskApprise(request,response,context);
	}
	protected void _handleActionAddTaskApprise(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}