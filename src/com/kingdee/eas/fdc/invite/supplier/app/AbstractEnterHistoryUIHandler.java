/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractEnterHistoryUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleactionView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionView(request,response,context);
	}
	protected void _handleactionView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}