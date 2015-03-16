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
public abstract class AbstractCustomerRptEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionAddLinkman(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddLinkman(request,response,context);
	}
	protected void _handleActionAddLinkman(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemoveLinkman(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemoveLinkman(request,response,context);
	}
	protected void _handleActionRemoveLinkman(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCheckAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCheckAll(request,response,context);
	}
	protected void _handleActionCheckAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}