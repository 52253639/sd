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
public abstract class AbstractWebMarkSchemaEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBaseDataEditUIHandler

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
	public void handleActionDelLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelLine(request,response,context);
	}
	protected void _handleActionDelLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}