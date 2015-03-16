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
public abstract class AbstractCustomerManagementUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionAddCustomer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddCustomer(request,response,context);
	}
	abstract protected void _handleActionAddCustomer(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddCommerceChance(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddCommerceChance(request,response,context);
	}
	abstract protected void _handleActionAddCommerceChance(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddTrackRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddTrackRecord(request,response,context);
	}
	abstract protected void _handleActionAddTrackRecord(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddTotalAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddTotalAll(request,response,context);
	}
	abstract protected void _handleActionAddTotalAll(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionClueQueryShow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClueQueryShow(request,response,context);
	}
	abstract protected void _handleActionClueQueryShow(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionImportantTrack(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportantTrack(request,response,context);
	}
	abstract protected void _handleActionImportantTrack(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionCancelImportantTrack(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelImportantTrack(request,response,context);
	}
	abstract protected void _handleActionCancelImportantTrack(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionCustomerAdapter(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCustomerAdapter(request,response,context);
	}
	abstract protected void _handleActionCustomerAdapter(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionCustomerShare(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCustomerShare(request,response,context);
	}
	abstract protected void _handleActionCustomerShare(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionCustomerCancelShare(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCustomerCancelShare(request,response,context);
	}
	abstract protected void _handleActionCustomerCancelShare(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}