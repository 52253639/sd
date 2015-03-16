/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractRoomAreaCompensateNewUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionChooseRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChooseRoom(request,response,context);
	}
	protected void _handleActionChooseRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeleteRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteRoom(request,response,context);
	}
	protected void _handleActionDeleteRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCalc(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCalc(request,response,context);
	}
	protected void _handleActionCalc(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionWorkFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionWorkFlow(request,response,context);
	}
	protected void _handleActionWorkFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}