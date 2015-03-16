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
public abstract class AbstractChooseRoomListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPurchase(request,response,context);
	}
	protected void _handleActionPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSignContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSignContract(request,response,context);
	}
	protected void _handleActionSignContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrePurchase(request,response,context);
	}
	protected void _handleActionPrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPurchaseChange(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPurchaseChange(request,response,context);
	}
	protected void _handleActionPurchaseChange(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelChooseRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelChooseRoom(request,response,context);
	}
	protected void _handleActionCancelChooseRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}