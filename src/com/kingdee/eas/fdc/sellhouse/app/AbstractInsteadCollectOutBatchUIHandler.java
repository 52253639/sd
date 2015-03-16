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
public abstract class AbstractInsteadCollectOutBatchUIHandler extends com.kingdee.eas.framework.app.CoreUIObjectHandler

{
	public void handleActionBatchInstead(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchInstead(request,response,context);
	}
	protected void _handleActionBatchInstead(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancel(request,response,context);
	}
	protected void _handleActionCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBtnRemove(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnRemove(request,response,context);
	}
	protected void _handleActionBtnRemove(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}