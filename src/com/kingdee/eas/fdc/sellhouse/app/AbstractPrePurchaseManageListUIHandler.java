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
public abstract class AbstractPrePurchaseManageListUIHandler extends com.kingdee.eas.fdc.sellhouse.app.BaseTransactionListUIHandler

{
	public void handleActionToPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionToPurchase(request,response,context);
	}
	protected void _handleActionToPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionToSign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionToSign(request,response,context);
	}
	protected void _handleActionToSign(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRelatePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelatePurchase(request,response,context);
	}
	protected void _handleActionRelatePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRelateSign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelateSign(request,response,context);
	}
	protected void _handleActionRelateSign(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}