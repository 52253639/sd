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
public abstract class AbstractF7WorkAmountConfirmUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionChoose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChoose(request,response,context);
	}
	protected void _handleActionChoose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancleChoose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancleChoose(request,response,context);
	}
	protected void _handleActionCancleChoose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}