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
public abstract class AbstractPurchaseChangeCustomerListUIHandler extends com.kingdee.eas.fdc.sellhouse.app.PurchaseListUIHandler

{
	public void handleActionChgCheque(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChgCheque(request,response,context);
	}
	protected void _handleActionChgCheque(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}