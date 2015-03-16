/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSalePlanBillUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionExcelImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExcelImport(request,response,context);
	}
	protected void _handleActionExcelImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExcelExport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExcelExport(request,response,context);
	}
	protected void _handleActionExcelExport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}