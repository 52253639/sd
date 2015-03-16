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
public abstract class AbstractHopedRoomsAnalyseRptUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionUnion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnion(request,response,context);
	}
	protected void _handleActionUnion(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}