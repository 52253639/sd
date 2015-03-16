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
public abstract class AbstractNewListTempletListUIHandler extends com.kingdee.eas.fdc.invite.app.InviteListBaseUIHandler

{
	public void handleActionDistribute(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDistribute(request,response,context);
	}
	protected void _handleActionDistribute(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}