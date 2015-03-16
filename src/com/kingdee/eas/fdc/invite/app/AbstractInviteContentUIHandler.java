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
public abstract class AbstractInviteContentUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionView(request,response,context);
	}
	protected void _handleActionView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelete(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelete(request,response,context);
	}
	protected void _handleActionDelete(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEdit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEdit(request,response,context);
	}
	protected void _handleActionEdit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAdd(request,response,context);
	}
	protected void _handleActionAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}