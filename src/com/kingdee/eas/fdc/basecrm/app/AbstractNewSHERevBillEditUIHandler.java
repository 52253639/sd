/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractNewSHERevBillEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMakeInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMakeInvoice(request,response,context);
	}
	protected void _handleActionMakeInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRecycle(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRecycle(request,response,context);
	}
	protected void _handleActionRecycle(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSubmitAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmitAudit(request,response,context);
	}
	protected void _handleActionSubmitAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}