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
public abstract class AbstractWBSTemplateEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBaseDataEditUIHandler

{
	public void handleActionAddLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddLine(request,response,context);
	}
	protected void _handleActionAddLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInsertLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInsertLine(request,response,context);
	}
	protected void _handleActionInsertLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeleteLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteLine(request,response,context);
	}
	protected void _handleActionDeleteLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}