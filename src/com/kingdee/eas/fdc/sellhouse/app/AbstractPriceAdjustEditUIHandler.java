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
public abstract class AbstractPriceAdjustEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionAddRoomByList(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRoomByList(request,response,context);
	}
	protected void _handleActionAddRoomByList(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}