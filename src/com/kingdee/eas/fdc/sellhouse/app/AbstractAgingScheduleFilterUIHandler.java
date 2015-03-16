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
public abstract class AbstractAgingScheduleFilterUIHandler extends com.kingdee.eas.base.commonquery.app.CustomerQueryPanelHandler

{
	public void handleActionBtnSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnSave(request,response,context);
	}
	protected void _handleActionBtnSave(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBtnDel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnDel(request,response,context);
	}
	protected void _handleActionBtnDel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}