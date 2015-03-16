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
public abstract class AbstractRoomAreaCompensateListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionBatchCompensate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchCompensate(request,response,context);
	}
	protected void _handleActionBatchCompensate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSingleScheme(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSingleScheme(request,response,context);
	}
	protected void _handleActionSingleScheme(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBatchEdit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchEdit(request,response,context);
	}
	protected void _handleActionBatchEdit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSubmitWorkFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmitWorkFlow(request,response,context);
	}
	protected void _handleActionSubmitWorkFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExecute(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExecute(request,response,context);
	}
	protected void _handleActionExecute(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSubmitRoomWorkFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmitRoomWorkFlow(request,response,context);
	}
	protected void _handleActionSubmitRoomWorkFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}