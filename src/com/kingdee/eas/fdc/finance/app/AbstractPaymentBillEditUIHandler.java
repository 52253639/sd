/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractPaymentBillEditUIHandler extends com.kingdee.eas.framework.app.BillEditUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAntiAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAntiAudit(request,response,context);
	}
	protected void _handleActionAntiAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTaoPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTaoPrint(request,response,context);
	}
	protected void _handleActionTaoPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPaymentPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPaymentPlan(request,response,context);
	}
	protected void _handleActionPaymentPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefresh(request,response,context);
	}
	protected void _handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClosePayReq(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClosePayReq(request,response,context);
	}
	protected void _handleActionClosePayReq(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelClosePayReq(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelClosePayReq(request,response,context);
	}
	protected void _handleActionCancelClosePayReq(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPay(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPay(request,response,context);
	}
	protected void _handleActionPay(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplit(request,response,context);
	}
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewBgBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewBgBalance(request,response,context);
	}
	protected void _handleActionViewBgBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionChequeReimburse(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChequeReimburse(request,response,context);
	}
	protected void _handleActionChequeReimburse(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionViewBudget(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionViewBudget(request,response,context);
	}
	protected void _handleactionViewBudget(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionApprove(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionApprove(request,response,context);
	}
	protected void _handleActionApprove(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUntiApprove(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUntiApprove(request,response,context);
	}
	protected void _handleActionUntiApprove(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}