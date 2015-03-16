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
public abstract class AbstractSignManageListUIHandler extends com.kingdee.eas.fdc.sellhouse.app.BaseTransactionListUIHandler

{
	public void handleActionRelatePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelatePurchase(request,response,context);
	}
	protected void _handleActionRelatePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionOnRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOnRecord(request,response,context);
	}
	protected void _handleActionOnRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnOnRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnOnRecord(request,response,context);
	}
	protected void _handleActionUnOnRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionWebMark(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionWebMark(request,response,context);
	}
	protected void _handleActionWebMark(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRelatePrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelatePrePurchase(request,response,context);
	}
	protected void _handleActionRelatePrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}