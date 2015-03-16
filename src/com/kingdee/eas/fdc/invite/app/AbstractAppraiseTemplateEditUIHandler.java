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
public abstract class AbstractAppraiseTemplateEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

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
	public void handleActionRemoveEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemoveEntry(request,response,context);
	}
	protected void _handleActionRemoveEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}