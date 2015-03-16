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
public abstract class AbstractSellStatRoomRptUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionViewPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewPurchase(request,response,context);
	}
	protected void _handleActionViewPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}