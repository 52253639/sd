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
public abstract class AbstractProjectImageEditUIHandler extends com.kingdee.eas.framework.app.BillEditUIHandler

{
	public void handleActionAddImage(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddImage(request,response,context);
	}
	protected void _handleActionAddImage(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeleteImage(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteImage(request,response,context);
	}
	protected void _handleActionDeleteImage(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSavePic(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSavePic(request,response,context);
	}
	protected void _handleActionSavePic(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}