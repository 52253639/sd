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
public abstract class AbstractRESchMainScheduleImportProjectListUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleactionBrowse(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionBrowse(request,response,context);
	}
	protected void _handleactionBrowse(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionImportProject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionImportProject(request,response,context);
	}
	protected void _handleactionImportProject(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}