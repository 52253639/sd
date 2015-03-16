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
public abstract class AbstractRoomRentListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmit(request,response,context);
	}
	abstract protected void _handleActionSubmit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionSchemePrice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSchemePrice(request,response,context);
	}
	abstract protected void _handleActionSchemePrice(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}