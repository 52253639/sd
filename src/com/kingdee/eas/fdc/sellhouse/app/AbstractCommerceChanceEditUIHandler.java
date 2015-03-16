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
public abstract class AbstractCommerceChanceEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionAddTrackRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddTrackRecord(request,response,context);
	}
	protected void _handleActionAddTrackRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionModifyTrackRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionModifyTrackRecord(request,response,context);
	}
	protected void _handleActionModifyTrackRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelTrackRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelTrackRecord(request,response,context);
	}
	protected void _handleActionDelTrackRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCustomerInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCustomerInfo(request,response,context);
	}
	protected void _handleActionCustomerInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQuestionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuestionPrint(request,response,context);
	}
	protected void _handleActionQuestionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}