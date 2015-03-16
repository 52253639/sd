/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractAccountReportUIHandler extends com.kingdee.eas.framework.report.app.CommRptBaseUIHandler

{
	public void handleActionOverdue(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOverdue(request,response,context);
	}
	protected void _handleActionOverdue(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionGenDefault(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionGenDefault(request,response,context);
	}
	protected void _handleActionGenDefault(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}