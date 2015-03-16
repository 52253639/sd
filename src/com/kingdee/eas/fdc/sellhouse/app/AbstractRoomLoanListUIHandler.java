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
public abstract class AbstractRoomLoanListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionBatchLoan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchLoan(request,response,context);
	}
	protected void _handleActionBatchLoan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBatchReceiveBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchReceiveBill(request,response,context);
	}
	protected void _handleActionBatchReceiveBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionStopTransact(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionStopTransact(request,response,context);
	}
	protected void _handleActionStopTransact(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCalling(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCalling(request,response,context);
	}
	protected void _handleActionCalling(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBankLoan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBankLoan(request,response,context);
	}
	protected void _handleActionBankLoan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionLoanProcess(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionLoanProcess(request,response,context);
	}
	protected void _handleActionLoanProcess(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUpdateLoanAmount(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpdateLoanAmount(request,response,context);
	}
	protected void _handleActionUpdateLoanAmount(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}