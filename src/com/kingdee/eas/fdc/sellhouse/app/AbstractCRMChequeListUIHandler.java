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
public abstract class AbstractCRMChequeListUIHandler extends com.kingdee.eas.framework.app.CoreBillListUIHandler

{
	public void handleActionBookIn(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBookIn(request,response,context);
	}
	protected void _handleActionBookIn(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPick(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPick(request,response,context);
	}
	protected void _handleActionPick(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionVC(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVC(request,response,context);
	}
	protected void _handleActionVC(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}