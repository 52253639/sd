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
public abstract class AbstractQuotingPriceEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionImportQuotingExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportQuotingExcel(request,response,context);
	}
	protected void _handleActionImportQuotingExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportErrorInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportErrorInfo(request,response,context);
	}
	protected void _handleActionExportErrorInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}