/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractFeesWarrantListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionSetAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetAudit(request,response,context);
	}
	protected void _handleActionSetAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSetUnaudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetUnaudit(request,response,context);
	}
	protected void _handleActionSetUnaudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionDelVoucher(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionDelVoucher(request,response,context);
	}
	protected void _handleactionDelVoucher(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionVoucher(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionVoucher(request,response,context);
	}
	protected void _handleactionVoucher(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}