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
public abstract class AbstractTrackRecordListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionExcelBatchImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExcelBatchImport(request,response,context);
	}
	protected void _handleActionExcelBatchImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}