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
public abstract class AbstractTest2UIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionAddRule(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRule(request,response,context);
	}
	protected void _handleActionAddRule(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelRule(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelRule(request,response,context);
	}
	protected void _handleActionDelRule(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}