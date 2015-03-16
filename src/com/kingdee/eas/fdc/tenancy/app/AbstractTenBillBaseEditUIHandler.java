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
public abstract class AbstractTenBillBaseEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleactionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionAudit(request,response,context);
	}
	abstract protected void _handleactionAudit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	abstract protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}