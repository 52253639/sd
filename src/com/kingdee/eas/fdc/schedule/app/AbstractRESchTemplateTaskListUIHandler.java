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
public abstract class AbstractRESchTemplateTaskListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOK(request,response,context);
	}
	protected void _handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionNo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionNo(request,response,context);
	}
	protected void _handleActionNo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}