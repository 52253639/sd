/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractContractWithoutTextEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionViewBgBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewBgBalance(request,response,context);
	}
	protected void _handleActionViewBgBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAttachment(request,response,context);
	}
	protected void _handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionViewBudget(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionViewBudget(request,response,context);
	}
	protected void _handleactionViewBudget(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionProgram(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProgram(request,response,context);
	}
	protected void _handleActionProgram(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewProgramContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewProgramContract(request,response,context);
	}
	protected void _handleActionViewProgramContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAccountView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAccountView(request,response,context);
	}
	protected void _handleActionAccountView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}