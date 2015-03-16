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
public abstract class AbstractFeesVoucherListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionSetAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetAudit(request,response,context);
	}
	protected void _handleActionSetAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSetUnaudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetUnaudit(request,response,context);
	}
	protected void _handleActionSetUnaudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}