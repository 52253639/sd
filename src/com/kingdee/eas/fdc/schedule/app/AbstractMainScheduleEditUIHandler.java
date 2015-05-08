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
public abstract class AbstractMainScheduleEditUIHandler extends com.kingdee.eas.fdc.schedule.app.FDCScheduleBaseEditUIHandler

{
	public void handleActionSetCheckVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetCheckVersion(request,response,context);
	}
	protected void _handleActionSetCheckVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}