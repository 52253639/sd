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
public abstract class AbstractFDCBudgetAcctListUIHandler extends com.kingdee.eas.fdc.basedata.app.ProjectTreeListBaseUIHandler

{
	public void handleActionRecension(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRecension(request,response,context);
	}
	protected void _handleActionRecension(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}