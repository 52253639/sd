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
public abstract class AbstractHousingRentalUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionDayprice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDayprice(request,response,context);
	}
	protected void _handleActionDayprice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}