/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractFDCProDepSplitEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionSptCostAccount(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSptCostAccount(request,response,context);
	}
	protected void _handleActionSptCostAccount(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelLine(request,response,context);
	}
	protected void _handleActionDelLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSptAccountView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSptAccountView(request,response,context);
	}
	protected void _handleActionSptAccountView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}