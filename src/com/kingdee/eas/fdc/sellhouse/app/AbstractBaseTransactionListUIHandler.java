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
public abstract class AbstractBaseTransactionListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

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
	public void handleActionInvalid(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInvalid(request,response,context);
	}
	protected void _handleActionInvalid(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReceiveBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceiveBill(request,response,context);
	}
	protected void _handleActionReceiveBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionChangeName(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChangeName(request,response,context);
	}
	protected void _handleActionChangeName(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQuitRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuitRoom(request,response,context);
	}
	protected void _handleActionQuitRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionChangeRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChangeRoom(request,response,context);
	}
	protected void _handleActionChangeRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPriceChange(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPriceChange(request,response,context);
	}
	protected void _handleActionPriceChange(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMultiSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMultiSubmit(request,response,context);
	}
	protected void _handleActionMultiSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImport(request,response,context);
	}
	protected void _handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}