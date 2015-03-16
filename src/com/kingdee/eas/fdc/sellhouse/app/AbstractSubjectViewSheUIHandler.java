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
public abstract class AbstractSubjectViewSheUIHandler extends com.kingdee.eas.framework.app.BillEditUIHandler

{
	public void handleActionSure(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSure(request,response,context);
	}
	protected void _handleActionSure(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddFillLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddFillLine(request,response,context);
	}
	protected void _handleActionAddFillLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}