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
public abstract class AbstractMortagageControlEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionAntiMortagage(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAntiMortagage(request,response,context);
	}
	protected void _handleActionAntiMortagage(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}