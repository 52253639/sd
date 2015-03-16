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
public abstract class AbstractDefaultAmountQueryUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionArgQuery(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionArgQuery(request,response,context);
	}
	protected void _handleActionArgQuery(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConfirm(request,response,context);
	}
	protected void _handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancels(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancels(request,response,context);
	}
	protected void _handleActionCancels(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}