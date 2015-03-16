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
public abstract class AbstractPTEEditUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmit(request,response,context);
	}
	protected void _handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAttachment(request,response,context);
	}
	protected void _handleActionAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}