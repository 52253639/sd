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
public abstract class AbstractProjectImageListUIHandler extends com.kingdee.eas.fdc.schedule.app.FDCScheduleBaseListUIHandler

{
	public void handleactionDownImage(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionDownImage(request,response,context);
	}
	protected void _handleactionDownImage(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}