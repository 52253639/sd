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
public abstract class AbstractQuestionPaperDefineListUIHandler extends com.kingdee.eas.framework.app.BillListUIHandler

{
	public void handleActionJumpSet(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionJumpSet(request,response,context);
	}
	protected void _handleActionJumpSet(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionGuideAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionGuideAdd(request,response,context);
	}
	protected void _handleActionGuideAdd(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSimulateAnswer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSimulateAnswer(request,response,context);
	}
	protected void _handleActionSimulateAnswer(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}