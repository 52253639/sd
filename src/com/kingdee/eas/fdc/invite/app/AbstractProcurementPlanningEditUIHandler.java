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
public abstract class AbstractProcurementPlanningEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionAddEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddEntry(request,response,context);
	}
	protected void _handleActionAddEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInsertEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInsertEntry(request,response,context);
	}
	protected void _handleActionInsertEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeleteEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteEntry(request,response,context);
	}
	protected void _handleActionDeleteEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddAttachment(request,response,context);
	}
	protected void _handleActionAddAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInsertAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInsertAttachment(request,response,context);
	}
	protected void _handleActionInsertAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeleteAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteAttachment(request,response,context);
	}
	protected void _handleActionDeleteAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}