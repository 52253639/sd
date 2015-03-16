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
public abstract class AbstractPaymentSplitEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCSplitBillEditUIHandler

{
	public void handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplit(request,response,context);
	}
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAutoMatchSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAutoMatchSplit(request,response,context);
	}
	protected void _handleActionAutoMatchSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewPayment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewPayment(request,response,context);
	}
	protected void _handleActionViewPayment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContract(request,response,context);
	}
	protected void _handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSplitBaseOnProp(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplitBaseOnProp(request,response,context);
	}
	protected void _handleActionSplitBaseOnProp(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}