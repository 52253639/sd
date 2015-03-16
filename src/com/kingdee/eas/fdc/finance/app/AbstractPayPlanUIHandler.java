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
public abstract class AbstractPayPlanUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmit(request,response,context);
	}
	protected void _handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPayRequest(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPayRequest(request,response,context);
	}
	protected void _handleActionPayRequest(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRow(request,response,context);
	}
	protected void _handleActionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteRow(request,response,context);
	}
	protected void _handleActionDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
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
	public void handleActionEditContractPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditContractPlan(request,response,context);
	}
	protected void _handleActionEditContractPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEntryCellDown(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEntryCellDown(request,response,context);
	}
	protected void _handleActionEntryCellDown(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}