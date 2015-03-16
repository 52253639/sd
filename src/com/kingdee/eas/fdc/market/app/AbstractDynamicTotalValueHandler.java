/**
 * output package name
 */
package com.kingdee.eas.fdc.market.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractDynamicTotalValueHandler extends com.kingdee.eas.framework.report.app.CommRptBaseUIHandler

{
	public void handleActionQuickPic(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuickPic(request,response,context);
	}
	protected void _handleActionQuickPic(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionHistory(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionHistory(request,response,context);
	}
	protected void _handleActionHistory(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}