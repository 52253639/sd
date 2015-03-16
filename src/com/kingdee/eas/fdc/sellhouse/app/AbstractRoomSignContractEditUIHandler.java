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
public abstract class AbstractRoomSignContractEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionIntegral(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionIntegral(request,response,context);
	}
	protected void _handleActionIntegral(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}