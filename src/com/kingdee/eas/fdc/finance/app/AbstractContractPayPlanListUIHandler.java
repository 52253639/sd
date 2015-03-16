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
public abstract class AbstractContractPayPlanListUIHandler extends com.kingdee.eas.fdc.contract.app.ContractListBaseUIHandler

{
	public void handleActionEditPayPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditPayPlan(request,response,context);
	}
	protected void _handleActionEditPayPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}