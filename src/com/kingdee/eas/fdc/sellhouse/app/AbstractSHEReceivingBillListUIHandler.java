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
public abstract class AbstractSHEReceivingBillListUIHandler extends com.kingdee.eas.fdc.basecrm.app.FDCReceivingBillListUIHandler

{
	public void handleActionUpdateSubject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpdateSubject(request,response,context);
	}
	protected void _handleActionUpdateSubject(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCreateBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCreateBill(request,response,context);
	}
	protected void _handleActionCreateBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionBatchCreateBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionBatchCreateBill(request,response,context);
	}
	protected void _handleactionBatchCreateBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleacitonRelatePaymentBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleacitonRelatePaymentBill(request,response,context);
	}
	protected void _handleacitonRelatePaymentBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}