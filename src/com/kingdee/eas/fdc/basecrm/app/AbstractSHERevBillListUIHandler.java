/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSHERevBillListUIHandler extends com.kingdee.eas.framework.app.CoreBillListUIHandler

{
	public void handleActionTDPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTDPrint(request,response,context);
	}
	protected void _handleActionTDPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTDPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTDPrintPreview(request,response,context);
	}
	protected void _handleActionTDPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMakeInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMakeInvoice(request,response,context);
	}
	protected void _handleActionMakeInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRecycle(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRecycle(request,response,context);
	}
	protected void _handleActionRecycle(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCreateAllUp(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCreateAllUp(request,response,context);
	}
	protected void _handleActionCreateAllUp(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImport(request,response,context);
	}
	protected void _handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMultiSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMultiSubmit(request,response,context);
	}
	protected void _handleActionMultiSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}