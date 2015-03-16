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
public abstract class AbstractSettlementTypeListUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConfirm(request,response,context);
	}
	protected void _handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQuit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuit(request,response,context);
	}
	protected void _handleActionQuit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}