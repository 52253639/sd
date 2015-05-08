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
public abstract class AbstractDeptMonthlyScheduleEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionExportData(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportData(request,response,context);
	}
	protected void _handleActionExportData(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionView(request,response,context);
	}
	protected void _handleActionView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportProjectPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportProjectPlan(request,response,context);
	}
	protected void _handleActionImportProjectPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportUnFinishTask(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportUnFinishTask(request,response,context);
	}
	protected void _handleActionImportUnFinishTask(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}