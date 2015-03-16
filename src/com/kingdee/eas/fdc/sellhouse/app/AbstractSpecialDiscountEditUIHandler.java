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
public abstract class AbstractSpecialDiscountEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleAuditAction(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAuditAction(request,response,context);
	}
	protected void _handleAuditAction(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleUnAuditAction(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleUnAuditAction(request,response,context);
	}
	protected void _handleUnAuditAction(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRoomSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRoomSelect(request,response,context);
	}
	protected void _handleActionRoomSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRoomDelete(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRoomDelete(request,response,context);
	}
	protected void _handleActionRoomDelete(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}