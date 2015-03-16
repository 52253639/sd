/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractTenancyControlUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionReceiveBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceiveBill(request,response,context);
	}
	protected void _handleActionReceiveBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelTenancy(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelTenancy(request,response,context);
	}
	protected void _handleActionCancelTenancy(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionHandleTenancy(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionHandleTenancy(request,response,context);
	}
	protected void _handleActionHandleTenancy(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionApplyTenancy(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionApplyTenancy(request,response,context);
	}
	protected void _handleActionApplyTenancy(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionContinueTenancy(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionContinueTenancy(request,response,context);
	}
	protected void _handleActionContinueTenancy(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRejiggerTenancy(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRejiggerTenancy(request,response,context);
	}
	protected void _handleActionRejiggerTenancy(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQuitTenancy(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuitTenancy(request,response,context);
	}
	protected void _handleActionQuitTenancy(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionKeepRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionKeepRoom(request,response,context);
	}
	protected void _handleActionKeepRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionChangeName(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChangeName(request,response,context);
	}
	protected void _handleActionChangeName(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}