/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractTENBatchReceivingUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionCancelBtn(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelBtn(request,response,context);
	}
	protected void _handleActionCancelBtn(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionConfirmBtn(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConfirmBtn(request,response,context);
	}
	protected void _handleActionConfirmBtn(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRoomAddLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRoomAddLine(request,response,context);
	}
	protected void _handleActionRoomAddLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRoomDeleteLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRoomDeleteLine(request,response,context);
	}
	protected void _handleActionRoomDeleteLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSelectFundConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectFundConfirm(request,response,context);
	}
	protected void _handleActionSelectFundConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReceiveAddLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceiveAddLine(request,response,context);
	}
	protected void _handleActionReceiveAddLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReceiveDeleteLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceiveDeleteLine(request,response,context);
	}
	protected void _handleActionReceiveDeleteLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSelectedFundDeleteLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectedFundDeleteLine(request,response,context);
	}
	protected void _handleActionSelectedFundDeleteLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}