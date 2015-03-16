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
public abstract class AbstractPlanListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

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
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	abstract protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	abstract protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionBlankOut(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBlankOut(request,response,context);
	}
	abstract protected void _handleActionBlankOut(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}