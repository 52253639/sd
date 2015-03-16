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
public abstract class AbstractMaterialInfoListUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionView(request,response,context);
	}
	protected void _handleActionView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}