/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractQuotingCompositorUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefresh(request,response,context);
	}
	protected void _handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportToDB(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportToDB(request,response,context);
	}
	protected void _handleActionExportToDB(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportToExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportToExcel(request,response,context);
	}
	protected void _handleActionExportToExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAcceptBid(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAcceptBid(request,response,context);
	}
	protected void _handleActionAcceptBid(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnacceptBid(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnacceptBid(request,response,context);
	}
	protected void _handleActionUnacceptBid(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}