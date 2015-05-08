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
public abstract class AbstractProjectMonthReportEditUIHandler extends com.kingdee.eas.fdc.schedule.app.OpReportBaseUIHandler

{
	public void handleActionExcutePort(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExcutePort(request,response,context);
	}
	protected void _handleActionExcutePort(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewMonthReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewMonthReport(request,response,context);
	}
	protected void _handleActionViewMonthReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrintReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrintReport(request,response,context);
	}
	protected void _handleActionPrintReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}