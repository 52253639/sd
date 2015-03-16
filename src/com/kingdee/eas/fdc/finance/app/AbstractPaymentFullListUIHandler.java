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
public abstract class AbstractPaymentFullListUIHandler extends com.kingdee.eas.framework.app.BillListUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPay(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPay(request,response,context);
	}
	protected void _handleActionPay(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}