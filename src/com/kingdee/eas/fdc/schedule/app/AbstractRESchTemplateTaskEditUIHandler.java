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
public abstract class AbstractRESchTemplateTaskEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCTreeBaseDataEditUIHandler

{
	public void handleactionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionAddRow(request,response,context);
	}
	protected void _handleactionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionRemoveRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionRemoveRow(request,response,context);
	}
	protected void _handleactionRemoveRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}