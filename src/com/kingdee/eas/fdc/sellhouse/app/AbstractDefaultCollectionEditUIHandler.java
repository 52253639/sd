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
public abstract class AbstractDefaultCollectionEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBaseDataEditUIHandler

{
	public void handleActionScript(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionScript(request,response,context);
	}
	protected void _handleActionScript(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEmpty(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEmpty(request,response,context);
	}
	protected void _handleActionEmpty(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}