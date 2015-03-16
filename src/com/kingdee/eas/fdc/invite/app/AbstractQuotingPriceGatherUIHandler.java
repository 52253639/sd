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
public abstract class AbstractQuotingPriceGatherUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSave(request,response,context);
	}
	protected void _handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportExcel(request,response,context);
	}
	protected void _handleActionExportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handlePrePrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handlePrePrint(request,response,context);
	}
	protected void _handlePrePrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handlePrintAction(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handlePrintAction(request,response,context);
	}
	protected void _handlePrintAction(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleRefreshAction(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleRefreshAction(request,response,context);
	}
	protected void _handleRefreshAction(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}