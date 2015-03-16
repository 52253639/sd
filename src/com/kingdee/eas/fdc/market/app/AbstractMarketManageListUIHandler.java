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
public abstract class AbstractMarketManageListUIHandler extends com.kingdee.eas.framework.app.BillListUIHandler

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
	public void handleActionEvaluate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEvaluate(request,response,context);
	}
	abstract protected void _handleActionEvaluate(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionIntegral(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionIntegral(request,response,context);
	}
	abstract protected void _handleActionIntegral(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionClueCustomer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClueCustomer(request,response,context);
	}
	abstract protected void _handleActionClueCustomer(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionLatencyCustomer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionLatencyCustomer(request,response,context);
	}
	abstract protected void _handleActionLatencyCustomer(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionIntentCustomer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionIntentCustomer(request,response,context);
	}
	abstract protected void _handleActionIntentCustomer(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionOldCustomer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOldCustomer(request,response,context);
	}
	abstract protected void _handleActionOldCustomer(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}