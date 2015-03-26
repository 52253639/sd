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
public abstract class AbstractTenancyRevListUIHandler extends com.kingdee.eas.fdc.basecrm.app.FDCReceivingBillListUIHandler

{
	public void handleActionTDPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTDPrint(request,response,context);
	}
	protected void _handleActionTDPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTDPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTDPrintPreview(request,response,context);
	}
	protected void _handleActionTDPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
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
}