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
public abstract class AbstractFDCReceivingBillListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReceive(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceive(request,response,context);
	}
	protected void _handleActionReceive(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBatchReceiving(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchReceiving(request,response,context);
	}
	protected void _handleActionBatchReceiving(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCanceReceive(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCanceReceive(request,response,context);
	}
	protected void _handleActionCanceReceive(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAdjust(request,response,context);
	}
	protected void _handleActionAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRetakeReceipt(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRetakeReceipt(request,response,context);
	}
	protected void _handleActionRetakeReceipt(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReceipt(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceipt(request,response,context);
	}
	protected void _handleActionReceipt(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCreateInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCreateInvoice(request,response,context);
	}
	protected void _handleActionCreateInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClearInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClearInvoice(request,response,context);
	}
	protected void _handleActionClearInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}