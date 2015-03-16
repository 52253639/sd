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
public abstract class AbstractMarketingUnitListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionDeleteControler(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteControler(request,response,context);
	}
	protected void _handleActionDeleteControler(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddControler(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddControler(request,response,context);
	}
	protected void _handleActionAddControler(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}