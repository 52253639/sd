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
public abstract class AbstractImportExcelRoomUIForSHEHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionDoImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDoImport(request,response,context);
	}
	protected void _handleActionDoImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImport(request,response,context);
	}
	protected void _handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}