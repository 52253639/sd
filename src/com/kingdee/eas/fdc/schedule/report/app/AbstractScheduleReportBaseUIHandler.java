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
public abstract class AbstractScheduleReportBaseUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleactionExportToExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionExportToExcel(request,response,context);
	}
	protected void _handleactionExportToExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}