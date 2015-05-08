/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractScheduleReportTreeBaseUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleactionExportToExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionExportToExcel(request,response,context);
	}
	protected void _handleactionExportToExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionPrint(request,response,context);
	}
	protected void _handleactionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionPrePrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionPrePrint(request,response,context);
	}
	protected void _handleactionPrePrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionSearch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionSearch(request,response,context);
	}
	protected void _handleactionSearch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}