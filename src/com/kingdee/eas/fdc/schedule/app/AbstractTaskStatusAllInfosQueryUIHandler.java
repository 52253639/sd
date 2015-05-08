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
public abstract class AbstractTaskStatusAllInfosQueryUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleSerchAction(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleSerchAction(request,response,context);
	}
	protected void _handleSerchAction(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleExportExcelAction(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleExportExcelAction(request,response,context);
	}
	protected void _handleExportExcelAction(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrint(request,response,context);
	}
	protected void _handleActionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPreview(request,response,context);
	}
	protected void _handleActionPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}