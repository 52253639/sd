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
public abstract class AbstractFinanceProjectCostCloseUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAdd(request,response,context);
	}
	protected void _handleActionAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelete(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelete(request,response,context);
	}
	protected void _handleActionDelete(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConfirm(request,response,context);
	}
	protected void _handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancel(request,response,context);
	}
	protected void _handleActionCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleAcionAddAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAcionAddAll(request,response,context);
	}
	protected void _handleAcionAddAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleAcionDelAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAcionDelAll(request,response,context);
	}
	protected void _handleAcionDelAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}