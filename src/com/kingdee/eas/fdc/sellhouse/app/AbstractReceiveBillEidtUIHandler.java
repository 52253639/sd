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
public abstract class AbstractReceiveBillEidtUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionRec(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRec(request,response,context);
	}
	protected void _handleActionRec(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewBill(request,response,context);
	}
	protected void _handleActionViewBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}