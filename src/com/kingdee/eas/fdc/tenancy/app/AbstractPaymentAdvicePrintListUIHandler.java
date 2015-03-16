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
public abstract class AbstractPaymentAdvicePrintListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionPrintMoney(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrintMoney(request,response,context);
	}
	protected void _handleActionPrintMoney(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrintMoneyView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrintMoneyView(request,response,context);
	}
	protected void _handleActionPrintMoneyView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInform(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInform(request,response,context);
	}
	protected void _handleActionInform(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}