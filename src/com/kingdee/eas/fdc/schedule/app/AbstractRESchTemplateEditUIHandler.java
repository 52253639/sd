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
public abstract class AbstractRESchTemplateEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCTreeBaseDataEditUIHandler

{
	public void handleActionAddTask(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddTask(request,response,context);
	}
	protected void _handleActionAddTask(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemoveTask(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemoveTask(request,response,context);
	}
	protected void _handleActionRemoveTask(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSetStdDuration(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetStdDuration(request,response,context);
	}
	protected void _handleActionSetStdDuration(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportProject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportProject(request,response,context);
	}
	protected void _handleActionImportProject(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportTemplate(request,response,context);
	}
	protected void _handleActionImportTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionExportProject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionExportProject(request,response,context);
	}
	protected void _handleactionExportProject(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionAddChildTask(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionAddChildTask(request,response,context);
	}
	protected void _handleactionAddChildTask(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}