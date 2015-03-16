/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractTenancyModificationEditUIHandler extends com.kingdee.eas.fdc.tenancy.app.TenBillBaseEditUIHandler

{
	public void handleActionIncAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionIncAdd(request,response,context);
	}
	protected void _handleActionIncAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionIncDel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionIncDel(request,response,context);
	}
	protected void _handleActionIncDel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionFeeAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFeeAdd(request,response,context);
	}
	protected void _handleActionFeeAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionFeeDel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFeeDel(request,response,context);
	}
	protected void _handleActionFeeDel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}