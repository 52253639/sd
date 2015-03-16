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
public abstract class AbstractHandleTenancyEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionHandleTenancy(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionHandleTenancy(request,response,context);
	}
	abstract protected void _handleActionHandleTenancy(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}