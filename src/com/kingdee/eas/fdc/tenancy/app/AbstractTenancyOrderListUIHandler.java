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
public abstract class AbstractTenancyOrderListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionSetEnable(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetEnable(request,response,context);
	}
	abstract protected void _handleActionSetEnable(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}