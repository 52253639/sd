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
public abstract class AbstractSellOrderEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionAddRoomByList(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRoomByList(request,response,context);
	}
	protected void _handleActionAddRoomByList(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}