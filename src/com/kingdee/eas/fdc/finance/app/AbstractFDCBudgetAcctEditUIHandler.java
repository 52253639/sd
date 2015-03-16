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
public abstract class AbstractFDCBudgetAcctEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionShowBlankRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionShowBlankRow(request,response,context);
	}
	protected void _handleActionShowBlankRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionHideBlankRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionHideBlankRow(request,response,context);
	}
	protected void _handleActionHideBlankRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQuery(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuery(request,response,context);
	}
	protected void _handleActionQuery(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewCost(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewCost(request,response,context);
	}
	protected void _handleActionViewCost(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}