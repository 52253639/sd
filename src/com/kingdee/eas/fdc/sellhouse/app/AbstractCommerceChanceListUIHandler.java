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
public abstract class AbstractCommerceChanceListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionTerminate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTerminate(request,response,context);
	}
	protected void _handleActionTerminate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
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
	public void handleActionExcelBatchImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExcelBatchImport(request,response,context);
	}
	protected void _handleActionExcelBatchImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportantTrack(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportantTrack(request,response,context);
	}
	protected void _handleActionImportantTrack(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelImportantTrack(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelImportantTrack(request,response,context);
	}
	protected void _handleActionCancelImportantTrack(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCustomerAdapter(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCustomerAdapter(request,response,context);
	}
	protected void _handleActionCustomerAdapter(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCustomerShare(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCustomerShare(request,response,context);
	}
	protected void _handleActionCustomerShare(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCustomerCancelShare(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCustomerCancelShare(request,response,context);
	}
	protected void _handleActionCustomerCancelShare(RequestContext request,ResponseContext response, Context context) throws Exception {
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