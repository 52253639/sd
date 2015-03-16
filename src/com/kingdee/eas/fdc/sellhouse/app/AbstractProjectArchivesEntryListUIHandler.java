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
public abstract class AbstractProjectArchivesEntryListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionRangeEnactment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRangeEnactment(request,response,context);
	}
	protected void _handleActionRangeEnactment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}