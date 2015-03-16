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
public abstract class AbstractTenancyIntentListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionPayPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPayPlan(request,response,context);
	}
	abstract protected void _handleActionPayPlan(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}