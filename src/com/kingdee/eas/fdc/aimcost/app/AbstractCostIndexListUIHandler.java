/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractCostIndexListUIHandler extends com.kingdee.eas.fdc.contract.app.ContractListBaseUIHandler

{
	public void handleActionModify(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionModify(request,response,context);
	}
	protected void _handleActionModify(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}