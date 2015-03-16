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
public abstract class AbstractBankPaymentEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionCreateCheque(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCreateCheque(request,response,context);
	}
	protected void _handleActionCreateCheque(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClearCheque(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClearCheque(request,response,context);
	}
	protected void _handleActionClearCheque(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}