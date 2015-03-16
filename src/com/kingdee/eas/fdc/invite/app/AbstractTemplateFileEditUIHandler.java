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
public abstract class AbstractTemplateFileEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionAddCategory(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddCategory(request,response,context);
	}
	protected void _handleActionAddCategory(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemoveCategory(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemoveCategory(request,response,context);
	}
	protected void _handleActionRemoveCategory(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEditCategory(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditCategory(request,response,context);
	}
	protected void _handleActionEditCategory(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}