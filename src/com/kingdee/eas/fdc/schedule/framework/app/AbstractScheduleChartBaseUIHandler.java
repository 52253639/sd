/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractScheduleChartBaseUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrint(request,response,context);
	}
	protected void _handleActionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrintPreview(request,response,context);
	}
	protected void _handleActionPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportIMG(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportIMG(request,response,context);
	}
	protected void _handleActionExportIMG(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}