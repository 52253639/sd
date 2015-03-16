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
public abstract class AbstractSaleBalanceEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionBalanceCheck(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBalanceCheck(request,response,context);
	}
	protected void _handleActionBalanceCheck(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBalanceConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBalanceConfirm(request,response,context);
	}
	protected void _handleActionBalanceConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}