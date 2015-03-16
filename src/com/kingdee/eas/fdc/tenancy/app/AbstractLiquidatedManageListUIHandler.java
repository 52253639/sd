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
public abstract class AbstractLiquidatedManageListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionAccount(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAccount(request,response,context);
	}
	protected void _handleActionAccount(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBatchAccount(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchAccount(request,response,context);
	}
	protected void _handleActionBatchAccount(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionGenTenBillOtherPay(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionGenTenBillOtherPay(request,response,context);
	}
	protected void _handleActionGenTenBillOtherPay(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}