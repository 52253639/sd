/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractF7WorkAmountTaskUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionBtnOk(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnOk(request,response,context);
	}
	protected void _handleActionBtnOk(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBtnCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnCancel(request,response,context);
	}
	protected void _handleActionBtnCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBtnFilter(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnFilter(request,response,context);
	}
	protected void _handleActionBtnFilter(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}