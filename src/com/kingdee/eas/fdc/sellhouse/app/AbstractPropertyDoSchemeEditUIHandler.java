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
public abstract class AbstractPropertyDoSchemeEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionApprAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionApprAddRow(request,response,context);
	}
	protected void _handleActionApprAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionApprInsertRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionApprInsertRow(request,response,context);
	}
	protected void _handleActionApprInsertRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionApprDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionApprDeleteRow(request,response,context);
	}
	protected void _handleActionApprDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDataAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDataAddRow(request,response,context);
	}
	protected void _handleActionDataAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDataInsertRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDataInsertRow(request,response,context);
	}
	protected void _handleActionDataInsertRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDataDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDataDeleteRow(request,response,context);
	}
	protected void _handleActionDataDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}