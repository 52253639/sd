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
public abstract class AbstractInviteEntSuppChkBillEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAttachment(request,response,context);
	}
	protected void _handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInviteExecuteInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInviteExecuteInfo(request,response,context);
	}
	protected void _handleActionInviteExecuteInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}