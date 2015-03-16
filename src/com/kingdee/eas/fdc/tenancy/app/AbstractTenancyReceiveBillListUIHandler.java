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
public abstract class AbstractTenancyReceiveBillListUIHandler extends com.kingdee.eas.fdc.tenancy.app.TenBillBaseListUIHandler

{
	public void handleActionCreateInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCreateInvoice(request,response,context);
	}
	abstract protected void _handleActionCreateInvoice(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleactionClearInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionClearInvoice(request,response,context);
	}
	abstract protected void _handleactionClearInvoice(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionRec(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRec(request,response,context);
	}
	abstract protected void _handleActionRec(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	abstract protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionReceiveBillPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceiveBillPrint(request,response,context);
	}
	abstract protected void _handleActionReceiveBillPrint(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionReceiveBillPrintView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceiveBillPrintView(request,response,context);
	}
	abstract protected void _handleActionReceiveBillPrintView(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}