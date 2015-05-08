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
public abstract class AbstractRESchTemplateTreeNewListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOK(request,response,context);
	}
	protected void _handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionON(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionON(request,response,context);
	}
	protected void _handleActionON(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}