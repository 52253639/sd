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
public abstract class AbstractListingItemListUIHandler extends com.kingdee.eas.fdc.invite.app.HeadListBaseUIHandler

{
	public void handleActionImportPrice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportPrice(request,response,context);
	}
	protected void _handleActionImportPrice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}