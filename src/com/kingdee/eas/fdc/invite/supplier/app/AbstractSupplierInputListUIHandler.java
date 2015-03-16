/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSupplierInputListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBaseDataListUIHandler

{
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
	public void handleActionImportFormDataBase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportFormDataBase(request,response,context);
	}
	protected void _handleActionImportFormDataBase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBathAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBathAssign(request,response,context);
	}
	protected void _handleActionBathAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAssign(request,response,context);
	}
	protected void _handleActionViewAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
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
	public void handleActionAddToSysSupplier(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddToSysSupplier(request,response,context);
	}
	protected void _handleActionAddToSysSupplier(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEditLevel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditLevel(request,response,context);
	}
	protected void _handleActionEditLevel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}