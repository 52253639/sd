/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSubstituteAdjustEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleActionCalculate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCalculate(request,response,context);
	}
	protected void _handleActionCalculate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTransfTo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTransfTo(request,response,context);
	}
	protected void _handleActionTransfTo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}