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
public abstract class AbstractSchAdjustReqByGrantEditUIHandler extends com.kingdee.eas.fdc.schedule.app.FDCScheduleBaseEditUIHandler

{
	public void handleActionVersionCompare(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVersionCompare(request,response,context);
	}
	protected void _handleActionVersionCompare(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}