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
public abstract class AbstractBasePriceControlEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExceuted(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExceuted(request,response,context);
	}
	protected void _handleActionExceuted(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionWorkFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionWorkFlow(request,response,context);
	}
	protected void _handleActionWorkFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCalc(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCalc(request,response,context);
	}
	protected void _handleActionCalc(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionChooseRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChooseRoom(request,response,context);
	}
	protected void _handleActionChooseRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportPrice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportPrice(request,response,context);
	}
	protected void _handleActionImportPrice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelRoom(request,response,context);
	}
	protected void _handleActionDelRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}