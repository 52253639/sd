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
public abstract class AbstractWebMarkSchemaListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBaseDataListUIHandler

{
	public void handleActionFieldRelation(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFieldRelation(request,response,context);
	}
	protected void _handleActionFieldRelation(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}