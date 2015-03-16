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
public abstract class AbstractSignManageEditUIHandler extends com.kingdee.eas.fdc.sellhouse.app.BaseTransactionEditUIHandler

{
	public void handleActionRelatePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelatePurchase(request,response,context);
	}
	protected void _handleActionRelatePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRelatePrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelatePrePurchase(request,response,context);
	}
	protected void _handleActionRelatePrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}