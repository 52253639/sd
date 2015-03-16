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
public abstract class AbstractRoomPropNoBatchEditUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOK(request,response,context);
	}
	protected void _handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCanceled(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCanceled(request,response,context);
	}
	protected void _handleActionCanceled(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTenancyArea(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTenancyArea(request,response,context);
	}
	protected void _handleActionTenancyArea(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}