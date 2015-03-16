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
public abstract class AbstractRoomLoanProecessUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionReserve(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReserve(request,response,context);
	}
	protected void _handleActionReserve(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReclaimRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReclaimRoom(request,response,context);
	}
	protected void _handleActionReclaimRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQuitOrder(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuitOrder(request,response,context);
	}
	protected void _handleActionQuitOrder(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRefundment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefundment(request,response,context);
	}
	protected void _handleActionRefundment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBillAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBillAdjust(request,response,context);
	}
	protected void _handleActionBillAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionZhuanKuan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionZhuanKuan(request,response,context);
	}
	protected void _handleActionZhuanKuan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionShowRoomDetail(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionShowRoomDetail(request,response,context);
	}
	protected void _handleActionShowRoomDetail(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSimulate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSimulate(request,response,context);
	}
	protected void _handleActionSimulate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}