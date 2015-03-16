/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractHeadTypeChooseUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionAllSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAllSelect(request,response,context);
	}
	protected void _handleActionAllSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionNoneSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionNoneSelect(request,response,context);
	}
	protected void _handleActionNoneSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMoveUp(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMoveUp(request,response,context);
	}
	protected void _handleActionMoveUp(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMoveDown(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMoveDown(request,response,context);
	}
	protected void _handleActionMoveDown(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}