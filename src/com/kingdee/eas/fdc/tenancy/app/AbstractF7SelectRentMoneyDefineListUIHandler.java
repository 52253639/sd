/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractF7SelectRentMoneyDefineListUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelect(request,response,context);
	}
	abstract protected void _handleActionSelect(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionUnselect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnselect(request,response,context);
	}
	abstract protected void _handleActionUnselect(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}