/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractLiquidatedTBOPayUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelect(request,response,context);
	}
	protected void _handleActionSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}