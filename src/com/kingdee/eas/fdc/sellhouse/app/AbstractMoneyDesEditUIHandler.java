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
public abstract class AbstractMoneyDesEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionSettlementShow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSettlementShow(request,response,context);
	}
	protected void _handleActionSettlementShow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSettlementDel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSettlementDel(request,response,context);
	}
	protected void _handleActionSettlementDel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}