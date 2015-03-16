/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.report;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSupplierStockAddressReportUIHandler extends com.kingdee.eas.framework.report.app.CommRptBaseUIHandler

{
	public void handleActionSend(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSend(request,response,context);
	}
	protected void _handleActionSend(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}