/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractAchievementManagerListUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAdd(request,response,context);
	}
	protected void _handleActionAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDel(request,response,context);
	}
	protected void _handleActionDel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionView(request,response,context);
	}
	protected void _handleActionView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUpdate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpdate(request,response,context);
	}
	protected void _handleActionUpdate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrint(request,response,context);
	}
	protected void _handleActionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrintPreview(request,response,context);
	}
	protected void _handleActionPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}