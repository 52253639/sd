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
public abstract class AbstractVirtualSellControlUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionSimulate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSimulate(request,response,context);
	}
	protected void _handleActionSimulate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionVirtualPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVirtualPurchase(request,response,context);
	}
	protected void _handleActionVirtualPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSign(request,response,context);
	}
	protected void _handleActionSign(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}