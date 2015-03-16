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
public abstract class AbstractBuildingNewListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionCopy(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCopy(request,response,context);
	}
	protected void _handleActionCopy(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRelateBanBaseData(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRelateBanBaseData(request,response,context);
	}
	protected void _handleActionRelateBanBaseData(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}