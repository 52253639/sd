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
public abstract class AbstractSellHouseEndPeriodUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionEndSale(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEndSale(request,response,context);
	}
	protected void _handleActionEndSale(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnEndSale(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnEndSale(request,response,context);
	}
	protected void _handleActionUnEndSale(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}