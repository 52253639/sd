/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractInviteListingItemFilterUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionFilter(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFilter(request,response,context);
	}
	protected void _handleActionFilter(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportExcel(request,response,context);
	}
	protected void _handleActionExportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}