/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractPayRequestFullListUIHandler extends com.kingdee.eas.framework.app.CoreBillListUIHandler

{
	public void handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClose(request,response,context);
	}
	protected void _handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnClose(request,response,context);
	}
	protected void _handleActionUnClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewBg(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewBg(request,response,context);
	}
	protected void _handleActionViewBg(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}