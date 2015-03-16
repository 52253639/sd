/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractChangeAuditEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionAddSupp(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddSupp(request,response,context);
	}
	protected void _handleActionAddSupp(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelSupp(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelSupp(request,response,context);
	}
	protected void _handleActionDelSupp(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRegister(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRegister(request,response,context);
	}
	protected void _handleActionRegister(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDisPatch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisPatch(request,response,context);
	}
	protected void _handleActionDisPatch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAheadDisPatch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAheadDisPatch(request,response,context);
	}
	protected void _handleActionAheadDisPatch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAttachment(request,response,context);
	}
	protected void _handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPassHistory(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPassHistory(request,response,context);
	}
	protected void _handleActionPassHistory(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}