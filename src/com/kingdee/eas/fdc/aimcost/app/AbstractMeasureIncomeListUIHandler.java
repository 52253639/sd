/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractMeasureIncomeListUIHandler extends com.kingdee.eas.framework.app.CoreBillListUIHandler

{
	public void handleActionExportToAim(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportToAim(request,response,context);
	}
	protected void _handleActionExportToAim(RequestContext request,ResponseContext response, Context context) throws Exception {
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
	public void handleActionExportIndex(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportIndex(request,response,context);
	}
	protected void _handleActionExportIndex(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVersion(request,response,context);
	}
	protected void _handleActionVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}