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
public abstract class AbstractCRMChequeEditUIHandler extends com.kingdee.eas.fdc.sellhouse.app.CRMChequeBookInUIHandler

{
	public void handleActionMakeInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMakeInvoice(request,response,context);
	}
	protected void _handleActionMakeInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionVC(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVC(request,response,context);
	}
	protected void _handleActionVC(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReback(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReback(request,response,context);
	}
	protected void _handleActionReback(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInvalid(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInvalid(request,response,context);
	}
	protected void _handleActionInvalid(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelInvoice(request,response,context);
	}
	protected void _handleActionCancelInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPick(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPick(request,response,context);
	}
	protected void _handleActionPick(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionChangeInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChangeInvoice(request,response,context);
	}
	protected void _handleActionChangeInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}