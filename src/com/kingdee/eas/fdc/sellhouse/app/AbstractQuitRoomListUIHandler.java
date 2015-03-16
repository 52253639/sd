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
public abstract class AbstractQuitRoomListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRefundment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefundment(request,response,context);
	}
	protected void _handleActionRefundment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBalance(request,response,context);
	}
	protected void _handleActionBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQuitRoomChange(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuitRoomChange(request,response,context);
	}
	protected void _handleActionQuitRoomChange(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRetakeCheque(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRetakeCheque(request,response,context);
	}
	protected void _handleActionRetakeCheque(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBlankOut(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBlankOut(request,response,context);
	}
	protected void _handleActionBlankOut(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}