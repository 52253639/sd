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
public abstract class AbstractViewGroupMonthReportUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrint(request,response,context);
	}
	protected void _handleActionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrintpreview(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrintpreview(request,response,context);
	}
	protected void _handleActionPrintpreview(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportExcel(request,response,context);
	}
	protected void _handleActionExportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefresh(request,response,context);
	}
	protected void _handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}