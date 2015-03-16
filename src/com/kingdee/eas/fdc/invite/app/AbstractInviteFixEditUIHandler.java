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
public abstract class AbstractInviteFixEditUIHandler extends com.kingdee.eas.fdc.invite.app.BaseInviteEditUIHandler

{
	public void handleActionFixALine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFixALine(request,response,context);
	}
	protected void _handleActionFixALine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionFixILine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFixILine(request,response,context);
	}
	protected void _handleActionFixILine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionFixRLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFixRLine(request,response,context);
	}
	protected void _handleActionFixRLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleShowProjects(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleShowProjects(request,response,context);
	}
	protected void _handleShowProjects(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}