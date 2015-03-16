/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractProgrammingTemplateListUIHandler extends com.kingdee.eas.basedata.framework.app.DataBaseSIListUIHandler

{
	public void handleActionCopy(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCopy(request,response,context);
	}
	protected void _handleActionCopy(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}