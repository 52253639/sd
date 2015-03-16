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
public abstract class AbstractInviteBaseFileEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionEditLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditLine(request,response,context);
	}
	protected void _handleActionEditLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}