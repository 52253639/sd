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
public abstract class AbstractCommerceChanceImportUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionExcelImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExcelImport(request,response,context);
	}
	protected void _handleActionExcelImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExcelSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExcelSave(request,response,context);
	}
	protected void _handleActionExcelSave(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}