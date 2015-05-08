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
public abstract class AbstractScheduleTaskLoadUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSave(request,response,context);
	}
	abstract protected void _handleActionSave(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRow(request,response,context);
	}
	abstract protected void _handleActionAddRow(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteRow(request,response,context);
	}
	abstract protected void _handleActionDeleteRow(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}