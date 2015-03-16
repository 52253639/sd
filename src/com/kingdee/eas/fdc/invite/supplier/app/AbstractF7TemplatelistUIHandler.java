/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractF7TemplatelistUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionReturn(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReturn(request,response,context);
	}
	protected void _handleActionReturn(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionComfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionComfirm(request,response,context);
	}
	protected void _handleActionComfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}