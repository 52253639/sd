/**
 * output package name
 */
package com.kingdee.eas.fdc.market.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractQuestionPaperDefineEditUIHandler extends com.kingdee.eas.framework.app.BillEditUIHandler

{
	public void handleAction_NewSubject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAction_NewSubject(request,response,context);
	}
	abstract protected void _handleAction_NewSubject(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleAction_DeleteSubject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAction_DeleteSubject(request,response,context);
	}
	abstract protected void _handleAction_DeleteSubject(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleAction_SelectSubject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAction_SelectSubject(request,response,context);
	}
	abstract protected void _handleAction_SelectSubject(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleAction_UpdateSubject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAction_UpdateSubject(request,response,context);
	}
	abstract protected void _handleAction_UpdateSubject(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}