/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractNewListingRefPriceUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionRefPrice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefPrice(request,response,context);
	}
	protected void _handleActionRefPrice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportRefPrice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportRefPrice(request,response,context);
	}
	protected void _handleActionExportRefPrice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportExcel(request,response,context);
	}
	protected void _handleActionImportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}