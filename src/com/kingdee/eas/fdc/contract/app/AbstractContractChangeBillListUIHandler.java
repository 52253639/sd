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
public abstract class AbstractContractChangeBillListUIHandler extends com.kingdee.eas.fdc.contract.app.ContractListBaseUIHandler

{
	public void handleActionDisPatch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisPatch(request,response,context);
	}
	protected void _handleActionDisPatch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionVisa(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVisa(request,response,context);
	}
	protected void _handleActionVisa(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSettlement(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSettlement(request,response,context);
	}
	protected void _handleActionSettlement(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionVisaBatch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVisaBatch(request,response,context);
	}
	protected void _handleActionVisaBatch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnVisa(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnVisa(request,response,context);
	}
	protected void _handleActionUnVisa(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnDispatch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnDispatch(request,response,context);
	}
	protected void _handleActionUnDispatch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewChangeAudtiAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewChangeAudtiAttachment(request,response,context);
	}
	protected void _handleActionViewChangeAudtiAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewSettlement(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewSettlement(request,response,context);
	}
	protected void _handleActionViewSettlement(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}