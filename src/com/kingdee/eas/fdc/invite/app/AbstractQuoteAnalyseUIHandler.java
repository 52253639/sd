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
public abstract class AbstractQuoteAnalyseUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionQuoteSet(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuoteSet(request,response,context);
	}
	protected void _handleActionQuoteSet(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionItemFilter(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionItemFilter(request,response,context);
	}
	protected void _handleActionItemFilter(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}