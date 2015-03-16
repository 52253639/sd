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
public abstract class AbstractInviteListingEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionColumnSetting(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionColumnSetting(request,response,context);
	}
	protected void _handleActionColumnSetting(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportTemplet(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportTemplet(request,response,context);
	}
	protected void _handleActionImportTemplet(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportExcel(request,response,context);
	}
	protected void _handleActionImportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUpLevel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpLevel(request,response,context);
	}
	protected void _handleActionUpLevel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDownLevel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDownLevel(request,response,context);
	}
	protected void _handleActionDownLevel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportRefPrice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportRefPrice(request,response,context);
	}
	protected void _handleActionImportRefPrice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportRefPrice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportRefPrice(request,response,context);
	}
	protected void _handleActionExportRefPrice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportExcel(request,response,context);
	}
	protected void _handleActionExportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}