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
public abstract class AbstractAchievementManagerEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAdd(request,response,context);
	}
	protected void _handleActionAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDel(request,response,context);
	}
	protected void _handleActionDel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDownLoad(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDownLoad(request,response,context);
	}
	protected void _handleActionDownLoad(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionOpen(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOpen(request,response,context);
	}
	protected void _handleActionOpen(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}