/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractProjectPeriodStatusListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionCloseInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCloseInit(request,response,context);
	}
	protected void _handleActionCloseInit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCloseProject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCloseProject(request,response,context);
	}
	protected void _handleActionCloseProject(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCloseInitAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCloseInitAll(request,response,context);
	}
	protected void _handleActionCloseInitAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnCloseInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnCloseInit(request,response,context);
	}
	protected void _handleActionUnCloseInit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnCloseProject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnCloseProject(request,response,context);
	}
	protected void _handleActionUnCloseProject(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnCloseInitAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnCloseInitAll(request,response,context);
	}
	protected void _handleActionUnCloseInitAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}