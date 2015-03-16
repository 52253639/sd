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
public abstract class AbstractPaymentBillListUIHandler extends com.kingdee.eas.fdc.contract.app.ContractListBaseUIHandler

{
	public void handleActionProjectAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProjectAttachment(request,response,context);
	}
	protected void _handleActionProjectAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPaymentPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPaymentPlan(request,response,context);
	}
	protected void _handleActionPaymentPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPay(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPay(request,response,context);
	}
	protected void _handleActionPay(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewBgBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewBgBalance(request,response,context);
	}
	protected void _handleActionViewBgBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionWriteOff(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionWriteOff(request,response,context);
	}
	protected void _handleActionWriteOff(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCommitToBE(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCommitToBE(request,response,context);
	}
	protected void _handleActionCommitToBE(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEnrolNote(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEnrolNote(request,response,context);
	}
	protected void _handleActionEnrolNote(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEndorseOut(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEndorseOut(request,response,context);
	}
	protected void _handleActionEndorseOut(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCommitSettle(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCommitSettle(request,response,context);
	}
	protected void _handleActionCommitSettle(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelPay(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelPay(request,response,context);
	}
	protected void _handleActionCancelPay(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplit(request,response,context);
	}
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}