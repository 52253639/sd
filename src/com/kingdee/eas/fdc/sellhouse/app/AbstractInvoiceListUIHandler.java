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
public abstract class AbstractInvoiceListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionInvoicePrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInvoicePrint(request,response,context);
	}
	protected void _handleActionInvoicePrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInvoicePrintView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInvoicePrintView(request,response,context);
	}
	protected void _handleActionInvoicePrintView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionChangeInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChangeInvoice(request,response,context);
	}
	protected void _handleActionChangeInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}