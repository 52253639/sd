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
public abstract class AbstractProjectInvestPlanListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

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
	public void handleActionPublish(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPublish(request,response,context);
	}
	protected void _handleActionPublish(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRecension(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRecension(request,response,context);
	}
	protected void _handleActionRecension(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}