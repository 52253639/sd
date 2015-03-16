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
public abstract class AbstractRoomIntentListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionPayPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPayPlan(request,response,context);
	}
	protected void _handleActionPayPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBuyingRoomPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBuyingRoomPlan(request,response,context);
	}
	protected void _handleActionBuyingRoomPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionKeepDown(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionKeepDown(request,response,context);
	}
	protected void _handleActionKeepDown(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSinPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSinPurchase(request,response,context);
	}
	protected void _handleActionSinPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrePurchase(request,response,context);
	}
	protected void _handleActionPrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPurchase(request,response,context);
	}
	protected void _handleActionPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleAcionSignContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAcionSignContract(request,response,context);
	}
	protected void _handleAcionSignContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}