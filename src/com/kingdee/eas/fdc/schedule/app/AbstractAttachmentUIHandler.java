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
public abstract class AbstractAttachmentUIHandler extends com.kingdee.eas.framework.app.CoreUIObjectHandler

{
	public void handleActionAttachmentManager(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAttachmentManager(request,response,context);
	}
	protected void _handleActionAttachmentManager(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}