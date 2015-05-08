/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractTaskLoadUIHandler extends com.kingdee.eas.framework.app.CoreBillListUIHandler

{
	public void handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSave(request,response,context);
	}
	protected void _handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRow(request,response,context);
	}
	protected void _handleActionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteRow(request,response,context);
	}
	protected void _handleActionDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEntryAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEntryAttachment(request,response,context);
	}
	protected void _handleActionEntryAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}