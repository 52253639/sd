/**
 * output package name
 */
package com.kingdee.eas.fdc.market.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractMovementPlanListUIHandler extends com.kingdee.eas.fdc.insider.app.AuditListBaseUIHandler

{
	public void handleActionTDPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTDPrint(request,response,context);
	}
	abstract protected void _handleActionTDPrint(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionTDPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTDPrintPreview(request,response,context);
	}
	abstract protected void _handleActionTDPrintPreview(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	abstract protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionCopy(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCopy(request,response,context);
	}
	abstract protected void _handleActionCopy(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}