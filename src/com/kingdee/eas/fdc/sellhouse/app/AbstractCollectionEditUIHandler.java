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
public abstract class AbstractCollectionEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleactionSure(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionSure(request,response,context);
	}
	protected void _handleactionSure(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionScript(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionScript(request,response,context);
	}
	protected void _handleactionScript(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionClear(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionClear(request,response,context);
	}
	protected void _handleactionClear(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}