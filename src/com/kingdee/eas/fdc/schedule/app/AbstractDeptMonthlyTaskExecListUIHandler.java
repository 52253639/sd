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
public abstract class AbstractDeptMonthlyTaskExecListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionScheduleReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionScheduleReport(request,response,context);
	}
	protected void _handleActionScheduleReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSelfEvaluation(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelfEvaluation(request,response,context);
	}
	protected void _handleActionSelfEvaluation(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionFinalEvaluation(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFinalEvaluation(request,response,context);
	}
	protected void _handleActionFinalEvaluation(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMonthlyReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMonthlyReport(request,response,context);
	}
	protected void _handleActionMonthlyReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionOutputExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOutputExcel(request,response,context);
	}
	protected void _handleActionOutputExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}