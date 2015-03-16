/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSupplierPerformEvalulationEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionSupplier(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSupplier(request,response,context);
	}
	protected void _handleActionSupplier(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionContract(request,response,context);
	}
	protected void _handleActionContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTemplet(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTemplet(request,response,context);
	}
	protected void _handleActionTemplet(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}