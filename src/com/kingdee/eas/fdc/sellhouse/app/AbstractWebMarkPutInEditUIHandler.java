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
public abstract class AbstractWebMarkPutInEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionSetWeb(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetWeb(request,response,context);
	}
	protected void _handleActionSetWeb(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionNextProc(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionNextProc(request,response,context);
	}
	protected void _handleActionNextProc(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}