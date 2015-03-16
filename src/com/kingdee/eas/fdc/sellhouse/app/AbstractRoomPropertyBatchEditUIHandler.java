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
public abstract class AbstractRoomPropertyBatchEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionAddRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRoom(request,response,context);
	}
	protected void _handleActionAddRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemoveRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemoveRoom(request,response,context);
	}
	protected void _handleActionRemoveRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}