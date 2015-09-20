/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractChangeAuditListUIHandler extends com.kingdee.eas.fdc.basedata.app.ProjectTreeListBaseUIHandler

{
	public void handleActionDisPatch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisPatch(request,response,context);
	}
	protected void _handleActionDisPatch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAheadDisPatch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAheadDisPatch(request,response,context);
	}
	protected void _handleActionAheadDisPatch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSetRespite(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetRespite(request,response,context);
	}
	protected void _handleActionSetRespite(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelRespite(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelRespite(request,response,context);
	}
	protected void _handleActionCancelRespite(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPassHistory(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPassHistory(request,response,context);
	}
	protected void _handleActionPassHistory(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}