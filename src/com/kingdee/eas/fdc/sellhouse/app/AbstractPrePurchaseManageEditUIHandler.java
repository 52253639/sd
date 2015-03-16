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
public abstract class AbstractPrePurchaseManageEditUIHandler extends com.kingdee.eas.fdc.sellhouse.app.BaseTransactionEditUIHandler

{
	public void handleActionRaletionPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRaletionPurchase(request,response,context);
	}
	protected void _handleActionRaletionPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRaletionSign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRaletionSign(request,response,context);
	}
	protected void _handleActionRaletionSign(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}