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
public abstract class AbstractSellProjectSaleStateUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionEndInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEndInit(request,response,context);
	}
	protected void _handleActionEndInit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnInit(request,response,context);
	}
	protected void _handleActionUnInit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAllEndInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAllEndInit(request,response,context);
	}
	protected void _handleActionAllEndInit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAllUnInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAllUnInit(request,response,context);
	}
	protected void _handleActionAllUnInit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInitDataBld(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInitDataBld(request,response,context);
	}
	protected void _handleActionInitDataBld(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInitDataPty(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInitDataPty(request,response,context);
	}
	protected void _handleActionInitDataPty(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}