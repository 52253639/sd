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
public abstract class AbstractRoomAreaCompendateNewListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionByHand(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionByHand(request,response,context);
	}
	protected void _handleActionByHand(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionByScheme(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionByScheme(request,response,context);
	}
	protected void _handleActionByScheme(RequestContext request,ResponseContext response, Context context) throws Exception {
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
	public void handleActionReceipt(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceipt(request,response,context);
	}
	protected void _handleActionReceipt(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionStop(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionStop(request,response,context);
	}
	protected void _handleActionStop(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewWorkFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewWorkFlow(request,response,context);
	}
	protected void _handleActionViewWorkFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}