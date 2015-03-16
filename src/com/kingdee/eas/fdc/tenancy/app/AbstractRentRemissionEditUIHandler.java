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
public abstract class AbstractRentRemissionEditUIHandler extends com.kingdee.eas.fdc.tenancy.app.TenBillBaseEditUIHandler

{
	public void handleActionAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAdd(request,response,context);
	}
	protected void _handleActionAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDel(request,response,context);
	}
	protected void _handleActionDel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}