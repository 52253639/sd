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
public abstract class AbstractWBSTemplateListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBaseDataListUIHandler

{
	public void handleActionAddTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddTemplate(request,response,context);
	}
	protected void _handleActionAddTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEditTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditTemplate(request,response,context);
	}
	protected void _handleActionEditTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelTemplate(request,response,context);
	}
	protected void _handleActionDelTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewTemplate(request,response,context);
	}
	protected void _handleActionViewTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionLocateTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionLocateTemplate(request,response,context);
	}
	protected void _handleActionLocateTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}