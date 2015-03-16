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
public abstract class AbstractRoomPropertyBookListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionBatchBook(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchBook(request,response,context);
	}
	protected void _handleActionBatchBook(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddBatch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddBatch(request,response,context);
	}
	protected void _handleActionAddBatch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBookAlert(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBookAlert(request,response,context);
	}
	protected void _handleActionBookAlert(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}