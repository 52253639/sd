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
public abstract class AbstractMarketManageEditUIHandler extends com.kingdee.eas.framework.app.BillEditUIHandler

{
	public void handleActionAddCustomer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddCustomer(request,response,context);
	}
	abstract protected void _handleActionAddCustomer(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionDelCustomer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelCustomer(request,response,context);
	}
	abstract protected void _handleActionDelCustomer(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddMedia(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddMedia(request,response,context);
	}
	abstract protected void _handleActionAddMedia(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionDelMedia(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelMedia(request,response,context);
	}
	abstract protected void _handleActionDelMedia(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddCharge(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddCharge(request,response,context);
	}
	abstract protected void _handleActionAddCharge(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionDelCharge(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelCharge(request,response,context);
	}
	abstract protected void _handleActionDelCharge(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionPayment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPayment(request,response,context);
	}
	abstract protected void _handleActionPayment(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}