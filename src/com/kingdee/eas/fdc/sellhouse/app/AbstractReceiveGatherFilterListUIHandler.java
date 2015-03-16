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
public abstract class AbstractReceiveGatherFilterListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionCondition(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCondition(request,response,context);
	}
	protected void _handleActionCondition(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}