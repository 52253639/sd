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
public abstract class AbstractFDCDepConPayPlanEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionPublish(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPublish(request,response,context);
	}
	protected void _handleActionPublish(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBack(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBack(request,response,context);
	}
	protected void _handleActionBack(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRevise(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRevise(request,response,context);
	}
	protected void _handleActionRevise(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionIntroPre(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionIntroPre(request,response,context);
	}
	protected void _handleActionIntroPre(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContract(request,response,context);
	}
	protected void _handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}