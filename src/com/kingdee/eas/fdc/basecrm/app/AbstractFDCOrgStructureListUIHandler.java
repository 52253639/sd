/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractFDCOrgStructureListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionViewAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAll(request,response,context);
	}
	protected void _handleActionViewAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewPart(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewPart(request,response,context);
	}
	protected void _handleActionViewPart(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSave(request,response,context);
	}
	protected void _handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}