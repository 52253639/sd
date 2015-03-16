/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractProductDynamicCostUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmit(request,response,context);
	}
	protected void _handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionApportion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionApportion(request,response,context);
	}
	protected void _handleActionApportion(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRevert(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRevert(request,response,context);
	}
	protected void _handleActionRevert(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}