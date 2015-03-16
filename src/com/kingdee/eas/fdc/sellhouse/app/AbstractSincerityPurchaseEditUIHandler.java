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
public abstract class AbstractSincerityPurchaseEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRow(request,response,context);
	}
	protected void _handleActionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelRow(request,response,context);
	}
	protected void _handleActionDelRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSincerPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSincerPrint(request,response,context);
	}
	protected void _handleActionSincerPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSincerPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSincerPrintPreview(request,response,context);
	}
	protected void _handleActionSincerPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}