/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractQuotingPriceSumUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionExportToExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportToExcel(request,response,context);
	}
	protected void _handleActionExportToExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrintPreview(request,response,context);
	}
	protected void _handleActionPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrint(request,response,context);
	}
	protected void _handleActionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefresh(request,response,context);
	}
	protected void _handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}