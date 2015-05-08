/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractRESchTemplateTwoListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCTreeBaseDataListUIHandler

{
	public void handleactionAddTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionAddTemplate(request,response,context);
	}
	protected void _handleactionAddTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionRemoveTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionRemoveTemplate(request,response,context);
	}
	protected void _handleactionRemoveTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionModifyTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionModifyTemplate(request,response,context);
	}
	protected void _handleactionModifyTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionViewTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionViewTemplate(request,response,context);
	}
	protected void _handleactionViewTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}