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
public abstract class AbstractSpecialDiscountListUIHandler extends com.kingdee.eas.framework.app.CoreBillListUIHandler

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
	public void handleAuditAction(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAuditAction(request,response,context);
	}
	protected void _handleAuditAction(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleUnAuditAction(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleUnAuditAction(request,response,context);
	}
	protected void _handleUnAuditAction(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}