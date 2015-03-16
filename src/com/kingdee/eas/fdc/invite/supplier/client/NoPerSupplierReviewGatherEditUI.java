package com.kingdee.eas.fdc.invite.supplier.client;

import com.kingdee.bos.ui.face.ItemAction;

public class NoPerSupplierReviewGatherEditUI extends SupplierReviewGatherEditUI{

	public NoPerSupplierReviewGatherEditUI() throws Exception {
		super();
	}
	protected void handlePermissionForItemAction(ItemAction action){
		 String actionName = action.getClass().getName();
		 if(actionName.indexOf("$") >= 0)
	            actionName = actionName.substring(actionName.indexOf("$") + 1);
		 if("ActionOnLoad".equals(actionName)){
			 return;
		 }
		 super.handlePermissionForItemAction(action);
    }
}
