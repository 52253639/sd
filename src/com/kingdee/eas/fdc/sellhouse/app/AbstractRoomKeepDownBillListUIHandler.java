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
public abstract class AbstractRoomKeepDownBillListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelKeepDown(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelKeepDown(request,response,context);
	}
	protected void _handleActionCancelKeepDown(RequestContext request,ResponseContext response, Context context) throws Exception {
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
	public void handleActionTransSign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTransSign(request,response,context);
	}
	protected void _handleActionTransSign(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}