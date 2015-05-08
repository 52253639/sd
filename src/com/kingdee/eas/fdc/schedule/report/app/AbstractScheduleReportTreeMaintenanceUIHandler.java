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
public abstract class AbstractScheduleReportTreeMaintenanceUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleactionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionSave(request,response,context);
	}
	protected void _handleactionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionUpdate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionUpdate(request,response,context);
	}
	protected void _handleactionUpdate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactioRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactioRefresh(request,response,context);
	}
	protected void _handleactioRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}