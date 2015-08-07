/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractContractBillExecuteUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCRptBaseUIHandler

{
	public void handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContract(request,response,context);
	}
	protected void _handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewPayment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewPayment(request,response,context);
	}
	protected void _handleActionViewPayment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExpand(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExpand(request,response,context);
	}
	protected void _handleActionExpand(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionShorten(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionShorten(request,response,context);
	}
	protected void _handleActionShorten(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewPayPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewPayPlan(request,response,context);
	}
	protected void _handleActionViewPayPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDisplayAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisplayAll(request,response,context);
	}
	protected void _handleActionDisplayAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDisplayConNoText(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisplayConNoText(request,response,context);
	}
	protected void _handleActionDisplayConNoText(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDisplayContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisplayContract(request,response,context);
	}
	protected void _handleActionDisplayContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}