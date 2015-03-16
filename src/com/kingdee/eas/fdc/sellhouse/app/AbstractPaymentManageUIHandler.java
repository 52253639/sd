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
public abstract class AbstractPaymentManageUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelect(request,response,context);
	}
	protected void _handleActionSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReceiveAmount(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceiveAmount(request,response,context);
	}
	protected void _handleActionReceiveAmount(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQuitAmount(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuitAmount(request,response,context);
	}
	protected void _handleActionQuitAmount(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTranAmount(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTranAmount(request,response,context);
	}
	protected void _handleActionTranAmount(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInvoice(request,response,context);
	}
	protected void _handleActionInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRecycleInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRecycleInvoice(request,response,context);
	}
	protected void _handleActionRecycleInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionFinDeal(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFinDeal(request,response,context);
	}
	protected void _handleActionFinDeal(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMark(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMark(request,response,context);
	}
	protected void _handleActionMark(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRelateSaleBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelateSaleBill(request,response,context);
	}
	protected void _handleActionRelateSaleBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddBill(request,response,context);
	}
	protected void _handleActionAddBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEditBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditBill(request,response,context);
	}
	protected void _handleActionEditBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemoveBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemoveBill(request,response,context);
	}
	protected void _handleActionRemoveBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAuditBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAuditBill(request,response,context);
	}
	protected void _handleActionAuditBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAuditBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAuditBill(request,response,context);
	}
	protected void _handleActionUnAuditBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionGenTotalFin(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionGenTotalFin(request,response,context);
	}
	protected void _handleActionGenTotalFin(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionVoucher(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVoucher(request,response,context);
	}
	protected void _handleActionVoucher(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelVoucher(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelVoucher(request,response,context);
	}
	protected void _handleActionDelVoucher(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}