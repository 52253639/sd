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
public abstract class AbstractFDCScheduleBaseEditUIHandler extends com.kingdee.eas.fdc.schedule.framework.app.ScheduleBaseUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClose(request,response,context);
	}
	protected void _handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnClose(request,response,context);
	}
	protected void _handleActionUnClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDisplayAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisplayAll(request,response,context);
	}
	protected void _handleActionDisplayAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionHideOther(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionHideOther(request,response,context);
	}
	protected void _handleActionHideOther(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSaveNewTask(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSaveNewTask(request,response,context);
	}
	protected void _handleActionSaveNewTask(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportProject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportProject(request,response,context);
	}
	protected void _handleActionImportProject(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportProject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportProject(request,response,context);
	}
	protected void _handleActionExportProject(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBatChangeRespDept(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatChangeRespDept(request,response,context);
	}
	protected void _handleActionBatChangeRespDept(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAdjust(request,response,context);
	}
	protected void _handleActionAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRestore(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRestore(request,response,context);
	}
	protected void _handleActionRestore(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionExportPlanTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionExportPlanTemplate(request,response,context);
	}
	protected void _handleactionExportPlanTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionImportPlanTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionImportPlanTemplate(request,response,context);
	}
	protected void _handleactionImportPlanTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCompareVer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCompareVer(request,response,context);
	}
	protected void _handleActionCompareVer(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}