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
public abstract class AbstractScheduleReportQueryPanelUIHandler extends com.kingdee.eas.base.commonquery.app.CustomerQueryPanelHandler

{
	public void handleactionSelectOrg(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionSelectOrg(request,response,context);
	}
	protected void _handleactionSelectOrg(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}