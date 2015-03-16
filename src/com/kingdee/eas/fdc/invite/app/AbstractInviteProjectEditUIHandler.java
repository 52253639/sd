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
public abstract class AbstractInviteProjectEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

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
	public void handleActionRelate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelate(request,response,context);
	}
	protected void _handleActionRelate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSupplierALine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSupplierALine(request,response,context);
	}
	protected void _handleActionSupplierALine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSupplierILine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSupplierILine(request,response,context);
	}
	protected void _handleActionSupplierILine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSupplierRLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSupplierRLine(request,response,context);
	}
	protected void _handleActionSupplierRLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}