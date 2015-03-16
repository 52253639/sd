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
public abstract class AbstractPurchaseListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBlankOut(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBlankOut(request,response,context);
	}
	protected void _handleActionBlankOut(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionWebMark(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionWebMark(request,response,context);
	}
	protected void _handleActionWebMark(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCheckPrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCheckPrePurchase(request,response,context);
	}
	protected void _handleActionCheckPrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUncheckPrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUncheckPrePurchase(request,response,context);
	}
	protected void _handleActionUncheckPrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPurchasePrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPurchasePrint(request,response,context);
	}
	protected void _handleActionPurchasePrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPurchasePrintView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPurchasePrintView(request,response,context);
	}
	protected void _handleActionPurchasePrintView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrePurchase(request,response,context);
	}
	protected void _handleActionPrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionProofOfPayment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProofOfPayment(request,response,context);
	}
	protected void _handleActionProofOfPayment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}