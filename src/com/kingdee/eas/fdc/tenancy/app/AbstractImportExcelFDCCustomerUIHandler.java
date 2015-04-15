/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractImportExcelFDCCustomerUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionImportFromExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportFromExcel(request,response,context);
	}
	protected void _handleActionImportFromExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSaveImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSaveImport(request,response,context);
	}
	protected void _handleActionSaveImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}