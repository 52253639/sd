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
public abstract class AbstractAFMortgagedEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionApproachAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionApproachAddRow(request,response,context);
	}
	protected void _handleActionApproachAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionApproachInsertRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionApproachInsertRow(request,response,context);
	}
	protected void _handleActionApproachInsertRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionApproachDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionApproachDeleteRow(request,response,context);
	}
	protected void _handleActionApproachDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
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