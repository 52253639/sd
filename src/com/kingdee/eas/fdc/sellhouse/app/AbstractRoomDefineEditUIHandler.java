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
public abstract class AbstractRoomDefineEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionCreateRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCreateRoom(request,response,context);
	}
	protected void _handleActionCreateRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}