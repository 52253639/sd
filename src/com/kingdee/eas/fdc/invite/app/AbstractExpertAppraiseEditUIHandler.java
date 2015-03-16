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
public abstract class AbstractExpertAppraiseEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleActionInviteAllInformation(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInviteAllInformation(request,response,context);
	}
	protected void _handleActionInviteAllInformation(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}