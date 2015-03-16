/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractWorkLoadConfirmBillEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplit(request,response,context);
	}
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionConPrjBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConPrjBill(request,response,context);
	}
	protected void _handleActionConPrjBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}