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
public abstract class AbstractBaseTransactionEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionCustomerSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCustomerSelect(request,response,context);
	}
	protected void _handleActionCustomerSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionChooseAgio(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChooseAgio(request,response,context);
	}
	protected void _handleActionChooseAgio(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReceiveBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReceiveBill(request,response,context);
	}
	protected void _handleActionReceiveBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddCustomer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddCustomer(request,response,context);
	}
	protected void _handleActionAddCustomer(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMemberApply(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMemberApply(request,response,context);
	}
	protected void _handleActionMemberApply(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPointPresent(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPointPresent(request,response,context);
	}
	protected void _handleActionPointPresent(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSubmitAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmitAudit(request,response,context);
	}
	protected void _handleActionSubmitAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}