/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractStandardTaskGuideNewListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionPlanLead(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPlanLead(request,response,context);
	}
	protected void _handleActionPlanLead(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}