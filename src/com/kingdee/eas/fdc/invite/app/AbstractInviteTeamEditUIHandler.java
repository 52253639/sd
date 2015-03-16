/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractInviteTeamEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionAddPerson(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddPerson(request,response,context);
	}
	protected void _handleActionAddPerson(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDeletePerson(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeletePerson(request,response,context);
	}
	protected void _handleActionDeletePerson(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}