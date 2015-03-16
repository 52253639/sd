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
public abstract class AbstractRoomTimeAnalyseReportUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionQuery(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuery(request,response,context);
	}
	protected void _handleActionQuery(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrint(request,response,context);
	}
	protected void _handleActionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrePrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrePrint(request,response,context);
	}
	protected void _handleActionPrePrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefresh(request,response,context);
	}
	protected void _handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}