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
public abstract class AbstractProofOfPaymentListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionPaymentPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPaymentPrint(request,response,context);
	}
	protected void _handleActionPaymentPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPaymentPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPaymentPrintPreview(request,response,context);
	}
	protected void _handleActionPaymentPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}