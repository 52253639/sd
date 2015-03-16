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
public abstract class AbstractSincerityPurchaseChangeNameUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionChangeName(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChangeName(request,response,context);
	}
	protected void _handleActionChangeName(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddNewCus(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddNewCus(request,response,context);
	}
	protected void _handleActionAddNewCus(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}