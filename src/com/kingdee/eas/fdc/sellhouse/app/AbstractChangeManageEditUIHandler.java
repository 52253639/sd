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
public abstract class AbstractChangeManageEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionSelectCustomer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectCustomer(request,response,context);
	}
	protected void _handleActionSelectCustomer(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionChooseAgio(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChooseAgio(request,response,context);
	}
	protected void _handleActionChooseAgio(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}