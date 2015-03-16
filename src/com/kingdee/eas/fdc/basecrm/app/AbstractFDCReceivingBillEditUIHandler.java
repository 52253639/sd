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
public abstract class AbstractFDCReceivingBillEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionReceive(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceive(request,response,context);
	}
	protected void _handleActionReceive(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}