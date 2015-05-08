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
public abstract class AbstractRESchCompareUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionCompareVer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCompareVer(request,response,context);
	}
	protected void _handleActionCompareVer(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}