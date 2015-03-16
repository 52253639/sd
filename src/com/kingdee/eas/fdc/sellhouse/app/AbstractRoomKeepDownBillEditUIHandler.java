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
public abstract class AbstractRoomKeepDownBillEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionKeepDown(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionKeepDown(request,response,context);
	}
	protected void _handleActionKeepDown(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelKeepDown(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelKeepDown(request,response,context);
	}
	protected void _handleActionCancelKeepDown(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}