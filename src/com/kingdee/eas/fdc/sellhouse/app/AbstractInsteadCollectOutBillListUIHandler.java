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
public abstract class AbstractInsteadCollectOutBillListUIHandler extends com.kingdee.eas.fdc.propertymgmt.app.PPMProjectBillListUIHandler

{
	public void handleActionBatchInstead(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchInstead(request,response,context);
	}
	protected void _handleActionBatchInstead(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBtnChoose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnChoose(request,response,context);
	}
	protected void _handleActionBtnChoose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}