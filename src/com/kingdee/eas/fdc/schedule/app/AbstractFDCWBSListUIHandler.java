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
public abstract class AbstractFDCWBSListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCTreeBaseDataListUIHandler

{
	public void handleActionImportFromTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportFromTemplate(request,response,context);
	}
	protected void _handleActionImportFromTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBatChangeTaskPro(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatChangeTaskPro(request,response,context);
	}
	protected void _handleActionBatChangeTaskPro(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBatChangeRespDept(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatChangeRespDept(request,response,context);
	}
	protected void _handleActionBatChangeRespDept(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionOutputToTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOutputToTemplate(request,response,context);
	}
	protected void _handleActionOutputToTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUp(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUp(request,response,context);
	}
	protected void _handleActionUp(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDown(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDown(request,response,context);
	}
	protected void _handleActionDown(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionForward(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionForward(request,response,context);
	}
	protected void _handleActionForward(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBackward(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBackward(request,response,context);
	}
	protected void _handleActionBackward(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReCalcuNumber(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReCalcuNumber(request,response,context);
	}
	protected void _handleActionReCalcuNumber(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}