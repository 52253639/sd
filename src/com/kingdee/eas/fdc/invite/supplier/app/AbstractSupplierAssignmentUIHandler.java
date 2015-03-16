/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSupplierAssignmentUIHandler extends com.kingdee.eas.basedata.framework.app.DataBaseDAssignmentUIHandler

{
	public void handleActionBatchAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchAssign(request,response,context);
	}
	protected void _handleActionBatchAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}