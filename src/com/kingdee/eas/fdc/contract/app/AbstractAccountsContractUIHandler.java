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
public abstract class AbstractAccountsContractUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionDisplayAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisplayAll(request,response,context);
	}
	protected void _handleActionDisplayAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDisplayConNoText(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisplayConNoText(request,response,context);
	}
	protected void _handleActionDisplayConNoText(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDisplayContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisplayContract(request,response,context);
	}
	protected void _handleActionDisplayContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}