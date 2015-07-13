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
public abstract class AbstractPayRequestBillListUIHandler extends com.kingdee.eas.fdc.contract.app.ContractListBaseUIHandler

{
	public void handleActionSelectDeduct(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectDeduct(request,response,context);
	}
	protected void _handleActionSelectDeduct(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionProjectAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProjectAttachment(request,response,context);
	}
	protected void _handleActionProjectAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTranceUp2(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTranceUp2(request,response,context);
	}
	protected void _handleActionTranceUp2(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionContractAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionContractAttachment(request,response,context);
	}
	protected void _handleActionContractAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}