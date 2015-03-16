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
public abstract class AbstractRoomTimeFilterUIHandler extends com.kingdee.eas.base.commonquery.app.CustomerQueryPanelHandler

{
	public void handleActionAddNew(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddNew(request,response,context);
	}
	protected void _handleActionAddNew(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeleteLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteLine(request,response,context);
	}
	protected void _handleActionDeleteLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}