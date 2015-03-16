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
public abstract class AbstractErrorCheckListUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionPurDistillUpdate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPurDistillUpdate(request,response,context);
	}
	protected void _handleActionPurDistillUpdate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPurAddMarket(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPurAddMarket(request,response,context);
	}
	protected void _handleActionPurAddMarket(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionProjectDataBaseUpdate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProjectDataBaseUpdate(request,response,context);
	}
	protected void _handleActionProjectDataBaseUpdate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}