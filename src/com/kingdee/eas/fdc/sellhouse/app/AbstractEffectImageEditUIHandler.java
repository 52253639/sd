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
public abstract class AbstractEffectImageEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionImage(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImage(request,response,context);
	}
	protected void _handleActionImage(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEditHotspot(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditHotspot(request,response,context);
	}
	protected void _handleActionEditHotspot(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSaveHotspot(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSaveHotspot(request,response,context);
	}
	protected void _handleActionSaveHotspot(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}