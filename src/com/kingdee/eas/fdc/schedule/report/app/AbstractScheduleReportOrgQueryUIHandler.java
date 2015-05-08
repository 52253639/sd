/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractScheduleReportOrgQueryUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleactionSearch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionSearch(request,response,context);
	}
	protected void _handleactionSearch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionConfirm(request,response,context);
	}
	protected void _handleactionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionCancel(request,response,context);
	}
	protected void _handleactionCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionAllCheck(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionAllCheck(request,response,context);
	}
	protected void _handleactionAllCheck(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionUnAllCheck(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionUnAllCheck(request,response,context);
	}
	protected void _handleactionUnAllCheck(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}