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
public abstract class AbstractInviteFileItemBluePrintUIHandler extends com.kingdee.eas.fdc.invite.app.InviteFileItemListUIHandler

{
	public void handleActionReVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReVersion(request,response,context);
	}
	protected void _handleActionReVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}