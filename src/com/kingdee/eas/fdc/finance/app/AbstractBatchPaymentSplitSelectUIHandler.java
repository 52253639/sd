/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractBatchPaymentSplitSelectUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionSelectAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectAll(request,response,context);
	}
	protected void _handleActionSelectAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClearAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClearAll(request,response,context);
	}
	protected void _handleActionClearAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplit(request,response,context);
	}
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}