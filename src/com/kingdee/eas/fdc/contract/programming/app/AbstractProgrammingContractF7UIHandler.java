/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractProgrammingContractF7UIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConfirm(request,response,context);
	}
	protected void _handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExit(request,response,context);
	}
	protected void _handleActionExit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}