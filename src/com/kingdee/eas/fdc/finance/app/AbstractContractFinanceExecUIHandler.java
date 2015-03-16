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
public abstract class AbstractContractFinanceExecUIHandler extends com.kingdee.eas.fdc.contract.app.ContractListBaseUIHandler

{
	public void handleActionProjectAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProjectAttachment(request,response,context);
	}
	protected void _handleActionProjectAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContent(request,response,context);
	}
	protected void _handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddContent(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddContent(request,response,context);
	}
	protected void _handleActionAddContent(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionStore(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionStore(request,response,context);
	}
	protected void _handleActionStore(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionConMove(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConMove(request,response,context);
	}
	protected void _handleActionConMove(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContract(request,response,context);
	}
	protected void _handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSynOldContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSynOldContract(request,response,context);
	}
	protected void _handleActionSynOldContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}