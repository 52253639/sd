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
public abstract class AbstractBuyingRoomPlanUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionCalc(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCalc(request,response,context);
	}
	protected void _handleActionCalc(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}