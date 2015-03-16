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
public abstract class AbstractReceiveGatherListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRevGather(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRevGather(request,response,context);
	}
	protected void _handleActionRevGather(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleAcitonRelateReceivingBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAcitonRelateReceivingBill(request,response,context);
	}
	protected void _handleAcitonRelateReceivingBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCreateReceivingBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCreateReceivingBill(request,response,context);
	}
	protected void _handleActionCreateReceivingBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}