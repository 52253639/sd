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
public abstract class AbstractSHEProjectEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionBtnAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnAdd(request,response,context);
	}
	protected void _handleActionBtnAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBtnDelete(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnDelete(request,response,context);
	}
	protected void _handleActionBtnDelete(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRoom(request,response,context);
	}
	protected void _handleActionAddRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeleteRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteRoom(request,response,context);
	}
	protected void _handleActionDeleteRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}