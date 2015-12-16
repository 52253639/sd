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
public abstract class AbstractContractCostSplitEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCSplitBillEditUIHandler

{
	public void handleActionProgrAcctSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProgrAcctSelect(request,response,context);
	}
	protected void _handleActionProgrAcctSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContract(request,response,context);
	}
	protected void _handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}