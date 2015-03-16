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
public abstract class AbstractInviteFileItemListUIHandler extends com.kingdee.eas.fdc.invite.app.InviteListBaseUIHandler

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
	public void handleActionUpLoadFile(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpLoadFile(request,response,context);
	}
	protected void _handleActionUpLoadFile(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewFile(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewFile(request,response,context);
	}
	protected void _handleActionViewFile(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReversion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReversion(request,response,context);
	}
	protected void _handleActionReversion(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}