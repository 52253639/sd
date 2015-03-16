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
public abstract class AbstractExpertQualifyEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleActionInviteExecute(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInviteExecute(request,response,context);
	}
	protected void _handleActionInviteExecute(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}