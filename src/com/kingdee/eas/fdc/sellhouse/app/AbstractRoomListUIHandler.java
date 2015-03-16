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
public abstract class AbstractRoomListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionEditRoomBind(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditRoomBind(request,response,context);
	}
	protected void _handleActionEditRoomBind(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMerge(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMerge(request,response,context);
	}
	protected void _handleActionMerge(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplit(request,response,context);
	}
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleSeqAdjustmentUIShow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleSeqAdjustmentUIShow(request,response,context);
	}
	protected void _handleSeqAdjustmentUIShow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBatchModifyRoomPropNo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchModifyRoomPropNo(request,response,context);
	}
	protected void _handleActionBatchModifyRoomPropNo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInit(request,response,context);
	}
	protected void _handleActionInit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBatchModifyRoomUpdate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBatchModifyRoomUpdate(request,response,context);
	}
	protected void _handleActionBatchModifyRoomUpdate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}