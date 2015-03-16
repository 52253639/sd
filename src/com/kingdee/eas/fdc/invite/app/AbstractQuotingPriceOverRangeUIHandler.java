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
public abstract class AbstractQuotingPriceOverRangeUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handlePrePrintAction(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handlePrePrintAction(request,response,context);
	}
	protected void _handlePrePrintAction(RequestContext request,ResponseContext response, Context context) throws Exception {
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