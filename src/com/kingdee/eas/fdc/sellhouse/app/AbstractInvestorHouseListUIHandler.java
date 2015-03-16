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
public abstract class AbstractInvestorHouseListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionTrackRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTrackRecord(request,response,context);
	}
	protected void _handleActionTrackRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelTrackRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelTrackRecord(request,response,context);
	}
	protected void _handleActionDelTrackRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}