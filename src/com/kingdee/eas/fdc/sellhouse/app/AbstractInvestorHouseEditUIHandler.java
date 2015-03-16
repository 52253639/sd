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
public abstract class AbstractInvestorHouseEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionAddLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddLine(request,response,context);
	}
	protected void _handleActionAddLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelLine(request,response,context);
	}
	protected void _handleActionDelLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}