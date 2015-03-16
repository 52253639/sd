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
public abstract class AbstractPurchaseChangeCustomerNameUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionChgCheque(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChgCheque(request,response,context);
	}
	protected void _handleActionChgCheque(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRelationPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelationPurchase(request,response,context);
	}
	protected void _handleActionRelationPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}