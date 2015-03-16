/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractBasePriceControlListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExctue(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExctue(request,response,context);
	}
	protected void _handleActionExctue(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionWorkFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionWorkFlow(request,response,context);
	}
	protected void _handleActionWorkFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}