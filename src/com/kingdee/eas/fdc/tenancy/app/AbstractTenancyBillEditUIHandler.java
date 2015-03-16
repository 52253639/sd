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
public abstract class AbstractTenancyBillEditUIHandler extends com.kingdee.eas.fdc.tenancy.app.TenBillBaseEditUIHandler

{
	public void handleActionCarryForward(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCarryForward(request,response,context);
	}
	protected void _handleActionCarryForward(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddCollectProtocol(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddCollectProtocol(request,response,context);
	}
	protected void _handleActionAddCollectProtocol(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}