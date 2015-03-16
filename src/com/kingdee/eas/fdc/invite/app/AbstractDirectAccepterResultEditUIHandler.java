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
public abstract class AbstractDirectAccepterResultEditUIHandler extends com.kingdee.eas.fdc.invite.app.BaseInviteEditUIHandler

{
	public void handleShowProjects(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleShowProjects(request,response,context);
	}
	protected void _handleShowProjects(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}