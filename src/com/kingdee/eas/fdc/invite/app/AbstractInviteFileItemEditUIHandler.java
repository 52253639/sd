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
public abstract class AbstractInviteFileItemEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionUpLoadFile(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpLoadFile(request,response,context);
	}
	protected void _handleActionUpLoadFile(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewFile(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewFile(request,response,context);
	}
	protected void _handleActionViewFile(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewAttachmentSelf(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAttachmentSelf(request,response,context);
	}
	protected void _handleActionViewAttachmentSelf(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewContentSelf(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContentSelf(request,response,context);
	}
	protected void _handleActionViewContentSelf(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}