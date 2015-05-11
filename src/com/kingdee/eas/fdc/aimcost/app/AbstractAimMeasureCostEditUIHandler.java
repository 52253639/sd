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
public abstract class AbstractAimMeasureCostEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
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
	public void handleActionImportApportion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportApportion(request,response,context);
	}
	protected void _handleActionImportApportion(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportTemplate(request,response,context);
	}
	protected void _handleActionImportTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportAllToExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportAllToExcel(request,response,context);
	}
	protected void _handleActionExportAllToExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportData(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportData(request,response,context);
	}
	protected void _handleActionImportData(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCompare(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCompare(request,response,context);
	}
	protected void _handleActionCompare(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportConstructTable(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportConstructTable(request,response,context);
	}
	protected void _handleActionImportConstructTable(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportConstructTable(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportConstructTable(request,response,context);
	}
	protected void _handleActionExportConstructTable(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}