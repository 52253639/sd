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
public abstract class AbstractRoomAreaInputUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmit(request,response,context);
	}
	protected void _handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportExcel(request,response,context);
	}
	protected void _handleActionImportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAreaAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAreaAudit(request,response,context);
	}
	protected void _handleActionAreaAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionActualAreaAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionActualAreaAudit(request,response,context);
	}
	protected void _handleActionActualAreaAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelAreaAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelAreaAudit(request,response,context);
	}
	protected void _handleActionCancelAreaAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelActualAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelActualAudit(request,response,context);
	}
	protected void _handleActionCancelActualAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}