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
public abstract class AbstractProjectWeekReportEditUIHandler extends com.kingdee.eas.fdc.schedule.app.OpReportBaseUIHandler

{
	public void handleActionHistory(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionHistory(request,response,context);
	}
	protected void _handleActionHistory(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrintReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrintReport(request,response,context);
	}
	protected void _handleActionPrintReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportExcel(request,response,context);
	}
	protected void _handleActionExportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}