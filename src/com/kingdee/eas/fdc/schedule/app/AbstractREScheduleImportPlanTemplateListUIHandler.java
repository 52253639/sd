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
public abstract class AbstractREScheduleImportPlanTemplateListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleactionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionConfirm(request,response,context);
	}
	protected void _handleactionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionClose(request,response,context);
	}
	protected void _handleactionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}