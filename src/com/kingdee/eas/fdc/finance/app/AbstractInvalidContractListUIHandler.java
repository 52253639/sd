/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractInvalidContractListUIHandler extends com.kingdee.eas.fdc.basedata.app.ProjectTreeListBaseUIHandler

{
	public void handleActionTrace(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTrace(request,response,context);
	}
	protected void _handleActionTrace(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClearSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClearSplit(request,response,context);
	}
	protected void _handleActionClearSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAdjust(request,response,context);
	}
	protected void _handleActionViewAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}