/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractFindTaskUIHandler extends com.kingdee.eas.framework.app.CoreUIObjectHandler

{
	public void handleActionLocate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionLocate(request,response,context);
	}
	protected void _handleActionLocate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClose(request,response,context);
	}
	protected void _handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}