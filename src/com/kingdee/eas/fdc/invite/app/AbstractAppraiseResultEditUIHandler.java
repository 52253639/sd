/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractAppraiseResultEditUIHandler extends com.kingdee.eas.framework.app.BillEditUIHandler

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
	public void handleActionPreHit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPreHit(request,response,context);
	}
	protected void _handleActionPreHit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionHit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionHit(request,response,context);
	}
	protected void _handleActionHit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnPreHit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnPreHit(request,response,context);
	}
	protected void _handleActionUnPreHit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnHit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnHit(request,response,context);
	}
	protected void _handleActionUnHit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionVirtualPreSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVirtualPreSplit(request,response,context);
	}
	protected void _handleActionVirtualPreSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAttachment(request,response,context);
	}
	protected void _handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAssoViewBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAssoViewBill(request,response,context);
	}
	protected void _handleActionAssoViewBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInviteExecuteInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInviteExecuteInfo(request,response,context);
	}
	protected void _handleActionInviteExecuteInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContract(request,response,context);
	}
	protected void _handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}