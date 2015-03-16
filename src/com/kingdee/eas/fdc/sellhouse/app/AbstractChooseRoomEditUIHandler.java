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
public abstract class AbstractChooseRoomEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionCancelChooseRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelChooseRoom(request,response,context);
	}
	protected void _handleActionCancelChooseRoom(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAffirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAffirm(request,response,context);
	}
	protected void _handleActionAffirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrintChooseRoomInform(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrintChooseRoomInform(request,response,context);
	}
	protected void _handleActionPrintChooseRoomInform(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTransPrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTransPrePurchase(request,response,context);
	}
	protected void _handleActionTransPrePurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTransPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTransPurchase(request,response,context);
	}
	protected void _handleActionTransPurchase(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTransSign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTransSign(request,response,context);
	}
	protected void _handleActionTransSign(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}