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
public abstract class AbstractFeesWarrantEditUIHandler extends com.kingdee.eas.framework.app.BillEditUIHandler

{
	public void handleActionAddEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddEntry(request,response,context);
	}
	protected void _handleActionAddEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSetAccount(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetAccount(request,response,context);
	}
	protected void _handleActionSetAccount(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSetunAccount(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetunAccount(request,response,context);
	}
	protected void _handleActionSetunAccount(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}