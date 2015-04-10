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
public abstract class AbstractInvoiceBillEditUIHandler extends com.kingdee.eas.fdc.tenancy.app.TenBillBaseEditUIHandler

{
	public void handleActionSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelect(request,response,context);
	}
	protected void _handleActionSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSubmitAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmitAudit(request,response,context);
	}
	protected void _handleActionSubmitAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}