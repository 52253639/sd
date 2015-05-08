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
public abstract class AbstractDeptMonthlyTaskExecEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionSchReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSchReport(request,response,context);
	}
	protected void _handleActionSchReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSchDelete(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSchDelete(request,response,context);
	}
	protected void _handleActionSchDelete(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}