/**
 * output package name
 */
package com.kingdee.eas.fdc.market.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractPlanEditUIHandler extends com.kingdee.eas.framework.app.BillEditUIHandler

{
	public void handleActionDelMovement(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelMovement(request,response,context);
	}
	abstract protected void _handleActionDelMovement(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddMovement(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddMovement(request,response,context);
	}
	abstract protected void _handleActionAddMovement(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}