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
public abstract class AbstractWorkLoadConfirmBillListUIHandler extends com.kingdee.eas.fdc.contract.app.ContractListBaseUIHandler

{
	public void handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplit(request,response,context);
	}
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}