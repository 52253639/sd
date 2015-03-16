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
public abstract class AbstractRoomJoinListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionBatchJoin(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchJoin(request,response,context);
	}
	protected void _handleActionBatchJoin(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddBatch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddBatch(request,response,context);
	}
	protected void _handleActionAddBatch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionJoinAlert(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionJoinAlert(request,response,context);
	}
	protected void _handleActionJoinAlert(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}