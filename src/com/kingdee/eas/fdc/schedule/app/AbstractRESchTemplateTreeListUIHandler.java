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
public abstract class AbstractRESchTemplateTreeListUIHandler extends com.kingdee.eas.framework.app.TreeDetailListUIHandler

{
	public void handleActionOk(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOk(request,response,context);
	}
	protected void _handleActionOk(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionNO(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionNO(request,response,context);
	}
	protected void _handleActionNO(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}