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
public abstract class AbstractF7RelatedTaskSelectUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionDown(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDown(request,response,context);
	}
	protected void _handleActionDown(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUpMove(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpMove(request,response,context);
	}
	protected void _handleActionUpMove(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUpAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpAll(request,response,context);
	}
	protected void _handleActionUpAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDownAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDownAll(request,response,context);
	}
	protected void _handleActionDownAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOK(request,response,context);
	}
	protected void _handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancle(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancle(request,response,context);
	}
	protected void _handleActionCancle(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionShowAllTask(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionShowAllTask(request,response,context);
	}
	protected void _handleActionShowAllTask(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}