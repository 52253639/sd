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
public abstract class AbstractPurchaseManageListUIHandler extends com.kingdee.eas.fdc.sellhouse.app.BaseTransactionListUIHandler

{
	public void handleActionSignContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSignContract(request,response,context);
	}
	protected void _handleActionSignContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRelatePrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelatePrePurchase(request,response,context);
	}
	protected void _handleActionRelatePrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRelateSign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelateSign(request,response,context);
	}
	protected void _handleActionRelateSign(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}