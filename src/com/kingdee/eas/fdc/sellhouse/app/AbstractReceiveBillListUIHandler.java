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
public abstract class AbstractReceiveBillListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionCreateInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCreateInvoice(request,response,context);
	}
	protected void _handleActionCreateInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionClearInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionClearInvoice(request,response,context);
	}
	protected void _handleactionClearInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRec(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRec(request,response,context);
	}
	protected void _handleActionRec(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReceiveBillPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceiveBillPrint(request,response,context);
	}
	protected void _handleActionReceiveBillPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReceiveBillPrintView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceiveBillPrintView(request,response,context);
	}
	protected void _handleActionReceiveBillPrintView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewBill(request,response,context);
	}
	protected void _handleActionViewBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBatchRec(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchRec(request,response,context);
	}
	protected void _handleActionBatchRec(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRefundment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefundment(request,response,context);
	}
	protected void _handleActionRefundment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBatchSett(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchSett(request,response,context);
	}
	protected void _handleActionBatchSett(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAdjust(request,response,context);
	}
	protected void _handleActionAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelRec(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelRec(request,response,context);
	}
	protected void _handleActionCancelRec(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReceipt(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceipt(request,response,context);
	}
	protected void _handleActionReceipt(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRetakeReceipt(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRetakeReceipt(request,response,context);
	}
	protected void _handleActionRetakeReceipt(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}