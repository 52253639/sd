/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractAcceptanceLetterListUIHandler extends com.kingdee.eas.fdc.invite.app.InviteListBaseUIHandler

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
	public void handleActionCreateContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCreateContract(request,response,context);
	}
	protected void _handleActionCreateContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRevise(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRevise(request,response,context);
	}
	protected void _handleActionRevise(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInviteExecuteInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInviteExecuteInfo(request,response,context);
	}
	protected void _handleActionInviteExecuteInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}