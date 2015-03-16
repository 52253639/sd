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
public abstract class AbstractFDCSupplierListUIHandler extends com.kingdee.eas.fdc.supply.app.FDCTreeListUIHandler

{
	public void handleActionLinkCreate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionLinkCreate(request,response,context);
	}
	protected void _handleActionLinkCreate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}