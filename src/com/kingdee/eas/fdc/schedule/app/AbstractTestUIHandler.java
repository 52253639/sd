/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractTestUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionUpload(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpload(request,response,context);
	}
	protected void _handleActionUpload(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewAtt(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAtt(request,response,context);
	}
	protected void _handleActionViewAtt(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}