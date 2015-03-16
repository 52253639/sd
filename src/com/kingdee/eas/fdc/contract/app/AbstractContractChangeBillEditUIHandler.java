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
public abstract class AbstractContractChangeBillEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionDisPatch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisPatch(request,response,context);
	}
	protected void _handleActionDisPatch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplit(request,response,context);
	}
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionVisa(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVisa(request,response,context);
	}
	protected void _handleActionVisa(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewChangeAudtiAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewChangeAudtiAttachment(request,response,context);
	}
	protected void _handleActionViewChangeAudtiAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAttachment(request,response,context);
	}
	protected void _handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}