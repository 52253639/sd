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
public abstract class AbstractFDCMonthBudgetAcctEditUIHandler extends com.kingdee.eas.fdc.finance.app.FDCBudgetAcctEditUIHandler

{
	public void handleActionConExecInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConExecInfo(request,response,context);
	}
	protected void _handleActionConExecInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}