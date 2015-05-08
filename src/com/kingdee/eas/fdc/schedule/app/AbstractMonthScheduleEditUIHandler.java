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
public abstract class AbstractMonthScheduleEditUIHandler extends com.kingdee.eas.framework.app.BillEditUIHandler

{
	public void handleActionInputMonthTask(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInputMonthTask(request,response,context);
	}
	protected void _handleActionInputMonthTask(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInputUltimoTask(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInputUltimoTask(request,response,context);
	}
	protected void _handleActionInputUltimoTask(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeleteTask(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteTask(request,response,context);
	}
	protected void _handleActionDeleteTask(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionNewTempTask(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionNewTempTask(request,response,context);
	}
	protected void _handleActionNewTempTask(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeleteTempTask(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteTempTask(request,response,context);
	}
	protected void _handleActionDeleteTempTask(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}