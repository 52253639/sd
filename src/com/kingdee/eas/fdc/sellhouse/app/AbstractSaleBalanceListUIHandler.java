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
public abstract class AbstractSaleBalanceListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionSaleBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSaleBalance(request,response,context);
	}
	protected void _handleActionSaleBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnSaleBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnSaleBalance(request,response,context);
	}
	protected void _handleActionUnSaleBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInitDataBld(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInitDataBld(request,response,context);
	}
	protected void _handleActionInitDataBld(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInitDataPty(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInitDataPty(request,response,context);
	}
	protected void _handleActionInitDataPty(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}