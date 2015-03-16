/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractWorkLoadSplitListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCSplitBillListUIHandler

{
	public void handleActionViewInvalid(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewInvalid(request,response,context);
	}
	protected void _handleActionViewInvalid(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewWorkLoad(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewWorkLoad(request,response,context);
	}
	protected void _handleActionViewWorkLoad(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContract(request,response,context);
	}
	protected void _handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}