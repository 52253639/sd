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
public abstract class AbstractRoomBasePriceListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmit(request,response,context);
	}
	protected void _handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportExcel(request,response,context);
	}
	protected void _handleActionImportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPriceAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPriceAudit(request,response,context);
	}
	protected void _handleActionPriceAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}