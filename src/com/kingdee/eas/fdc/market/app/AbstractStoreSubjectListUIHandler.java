/**
 * output package name
 */
package com.kingdee.eas.fdc.market.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractStoreSubjectListUIHandler extends com.kingdee.eas.framework.app.TreeDetailListUIHandler

{
	public void handleSaveSubNumberAction(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleSaveSubNumberAction(request,response,context);
	}
	protected void _handleSaveSubNumberAction(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}