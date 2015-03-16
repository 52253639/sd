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
public abstract class AbstractWebMarkFieldEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionAddMeta(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddMeta(request,response,context);
	}
	protected void _handleActionAddMeta(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelMeta(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelMeta(request,response,context);
	}
	protected void _handleActionDelMeta(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUpMeta(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpMeta(request,response,context);
	}
	protected void _handleActionUpMeta(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDownMeta(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDownMeta(request,response,context);
	}
	protected void _handleActionDownMeta(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRefreshMate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefreshMate(request,response,context);
	}
	protected void _handleActionRefreshMate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRefreshWeb(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefreshWeb(request,response,context);
	}
	protected void _handleActionRefreshWeb(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRow(request,response,context);
	}
	protected void _handleActionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInsertRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInsertRow(request,response,context);
	}
	protected void _handleActionInsertRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelRow(request,response,context);
	}
	protected void _handleActionDelRow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}