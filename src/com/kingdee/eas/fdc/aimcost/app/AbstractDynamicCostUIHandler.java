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
public abstract class AbstractDynamicCostUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmit(request,response,context);
	}
	protected void _handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRow(request,response,context);
	}
	protected void _handleActionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteRow(request,response,context);
	}
	protected void _handleActionDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRecense(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRecense(request,response,context);
	}
	protected void _handleActionRecense(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRevert(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRevert(request,response,context);
	}
	protected void _handleActionRevert(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExpression(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExpression(request,response,context);
	}
	protected void _handleActionExpression(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}