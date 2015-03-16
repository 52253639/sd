/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractProgrammingEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImport(request,response,context);
	}
	protected void _handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportPro(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportPro(request,response,context);
	}
	protected void _handleActionExportPro(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefresh(request,response,context);
	}
	protected void _handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEditInvite(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditInvite(request,response,context);
	}
	protected void _handleActionEditInvite(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionComAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionComAddRow(request,response,context);
	}
	protected void _handleActionComAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionComInsertRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionComInsertRow(request,response,context);
	}
	protected void _handleActionComInsertRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionComRemoveRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionComRemoveRow(request,response,context);
	}
	protected void _handleActionComRemoveRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCompare(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCompare(request,response,context);
	}
	protected void _handleActionCompare(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewAmount(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAmount(request,response,context);
	}
	protected void _handleActionViewAmount(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}