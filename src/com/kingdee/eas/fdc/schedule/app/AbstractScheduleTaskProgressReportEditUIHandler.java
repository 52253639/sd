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
public abstract class AbstractScheduleTaskProgressReportEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleAchievementAddNew(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAchievementAddNew(request,response,context);
	}
	protected void _handleAchievementAddNew(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleAchievementDel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAchievementDel(request,response,context);
	}
	protected void _handleAchievementDel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQualityAddNew(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQualityAddNew(request,response,context);
	}
	protected void _handleActionQualityAddNew(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQualityDelete(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQualityDelete(request,response,context);
	}
	protected void _handleActionQualityDelete(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}