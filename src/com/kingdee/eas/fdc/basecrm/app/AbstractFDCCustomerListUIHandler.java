/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractFDCCustomerListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionChangeCusName(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChangeCusName(request,response,context);
	}
	protected void _handleActionChangeCusName(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUpdateCus(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpdateCus(request,response,context);
	}
	protected void _handleActionUpdateCus(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionShare(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionShare(request,response,context);
	}
	protected void _handleActionShare(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMerge(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMerge(request,response,context);
	}
	protected void _handleActionMerge(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImport(request,response,context);
	}
	protected void _handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}