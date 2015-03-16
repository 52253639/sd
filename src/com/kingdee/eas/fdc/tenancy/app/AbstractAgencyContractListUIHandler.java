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
public abstract class AbstractAgencyContractListUIHandler extends com.kingdee.eas.fdc.tenancy.app.TenBillBaseListUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	abstract protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}