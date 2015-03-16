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
public abstract class AbstractBuildingCompensatePlusEditUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionBatchCompensate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchCompensate(request,response,context);
	}
	protected void _handleActionBatchCompensate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}