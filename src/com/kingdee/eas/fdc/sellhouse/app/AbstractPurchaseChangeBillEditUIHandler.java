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
public abstract class AbstractPurchaseChangeBillEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionSelectRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectRoom(request,response,context);
	}
	protected void _handleActionSelectRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAssoPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAssoPurchase(request,response,context);
	}
	protected void _handleActionAssoPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}