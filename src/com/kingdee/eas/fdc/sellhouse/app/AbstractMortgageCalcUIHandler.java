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
public abstract class AbstractMortgageCalcUIHandler extends com.kingdee.eas.framework.app.BillEditUIHandler

{
	public void handleActionCalc(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCalc(request,response,context);
	}
	protected void _handleActionCalc(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClear(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClear(request,response,context);
	}
	protected void _handleActionClear(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}