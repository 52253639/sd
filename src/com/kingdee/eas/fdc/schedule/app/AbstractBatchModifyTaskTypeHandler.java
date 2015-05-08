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
public abstract class AbstractBatchModifyTaskTypeHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionLeftMove(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionLeftMove(request,response,context);
	}
	protected void _handleActionLeftMove(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAllLeftMove(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAllLeftMove(request,response,context);
	}
	protected void _handleActionAllLeftMove(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAllRightMove(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAllRightMove(request,response,context);
	}
	protected void _handleActionAllRightMove(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRightMove(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRightMove(request,response,context);
	}
	protected void _handleActionRightMove(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMakeSure(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMakeSure(request,response,context);
	}
	protected void _handleActionMakeSure(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSaveAndClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSaveAndClose(request,response,context);
	}
	protected void _handleActionSaveAndClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClose(request,response,context);
	}
	protected void _handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}