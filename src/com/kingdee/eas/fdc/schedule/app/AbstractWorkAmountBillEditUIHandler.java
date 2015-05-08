/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractWorkAmountBillEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionSelected(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelected(request,response,context);
	}
	protected void _handleActionSelected(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}