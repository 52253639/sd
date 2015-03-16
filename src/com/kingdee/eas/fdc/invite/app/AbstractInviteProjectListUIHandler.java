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
public abstract class AbstractInviteProjectListUIHandler extends com.kingdee.eas.fdc.invite.app.InviteListBaseUIHandler

{
	public void handleActionRelate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelate(request,response,context);
	}
	protected void _handleActionRelate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleGrantAuthor(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleGrantAuthor(request,response,context);
	}
	protected void _handleGrantAuthor(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}