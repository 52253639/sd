/**
 * output package name
 */
package com.kingdee.eas.fdc.market.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractEnterpriseSchemeListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionEvaluation(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEvaluation(request,response,context);
	}
	protected void _handleActionEvaluation(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionShowAllThemes(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionShowAllThemes(request,response,context);
	}
	protected void _handleActionShowAllThemes(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}