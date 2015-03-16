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
public abstract class AbstractInviteProjectModifyUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionAddPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddPlan(request,response,context);
	}
	protected void _handleActionAddPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemovePlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemovePlan(request,response,context);
	}
	protected void _handleActionRemovePlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInsertPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInsertPlan(request,response,context);
	}
	protected void _handleActionInsertPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConfirm(request,response,context);
	}
	protected void _handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancel2(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancel2(request,response,context);
	}
	protected void _handleActionCancel2(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}