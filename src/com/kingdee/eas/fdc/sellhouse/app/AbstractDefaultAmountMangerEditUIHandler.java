/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractDefaultAmountMangerEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionRelevance(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelevance(request,response,context);
	}
	protected void _handleActionRelevance(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}