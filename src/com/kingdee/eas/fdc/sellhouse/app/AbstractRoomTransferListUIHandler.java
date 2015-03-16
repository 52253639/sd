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
public abstract class AbstractRoomTransferListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionTransfer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTransfer(request,response,context);
	}
	protected void _handleActionTransfer(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnTransfer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnTransfer(request,response,context);
	}
	protected void _handleActionUnTransfer(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}