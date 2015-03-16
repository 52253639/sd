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
public abstract class AbstractSHESellProjectEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionBtnAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnAdd(request,response,context);
	}
	protected void _handleActionBtnAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBtnDel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnDel(request,response,context);
	}
	protected void _handleActionBtnDel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBtnAddRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnAddRoom(request,response,context);
	}
	protected void _handleActionBtnAddRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBtnDelRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnDelRoom(request,response,context);
	}
	protected void _handleActionBtnDelRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}