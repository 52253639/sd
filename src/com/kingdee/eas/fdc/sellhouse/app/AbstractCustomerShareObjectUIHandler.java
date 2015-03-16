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
public abstract class AbstractCustomerShareObjectUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleactionExeQuery(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionExeQuery(request,response,context);
	}
	protected void _handleactionExeQuery(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}