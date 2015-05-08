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
public abstract class AbstractStandardDurationSetUpEditUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOK(request,response,context);
	}
	protected void _handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSaveAndExit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSaveAndExit(request,response,context);
	}
	protected void _handleActionSaveAndExit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddDuration(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddDuration(request,response,context);
	}
	protected void _handleActionAddDuration(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemoveDuration(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemoveDuration(request,response,context);
	}
	protected void _handleActionRemoveDuration(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}