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
public abstract class AbstractLiquidatedManageEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionGenTenBillOtherPay(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionGenTenBillOtherPay(request,response,context);
	}
	protected void _handleActionGenTenBillOtherPay(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}