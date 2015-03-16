package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;

public class NoPerSupplierReviewGatherListUI extends SupplierReviewGatherListUI{

	public NoPerSupplierReviewGatherListUI() throws Exception {
		super();
	}
	protected void handlePermissionForItemAction(ItemAction action)
    {
		 String actionName = action.getClass().getName();
		 if(actionName.indexOf("$") >= 0)
	            actionName = actionName.substring(actionName.indexOf("$") + 1);
		 if("ActionOnLoad".equals(actionName)){
			 return;
		 }
		 super.handlePermissionForItemAction(action);
    }
	protected void tblMain_tableClicked(KDTMouseEvent e)throws Exception{
		if(e.getClickCount() == 2){
	         if(e.getType() == 0)
	             return;
	         checkSelected();
	         UIContext uiContext = new UIContext(this);
	         uiContext.put("ID", getSelectedKeyValue());
	         prepareUIContext(uiContext, new ActionEvent(btnView, 0, "Double Clicked"));
	         IUIWindow window = getUIWindow();
	         if(window != null && (window instanceof UIModelDialog))
	             uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(NoPerSupplierReviewGatherEditUI.class.getName(), uiContext, null, OprtState.VIEW);
	         else
	             uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(NoPerSupplierReviewGatherEditUI.class.getName(), uiContext, null, OprtState.VIEW);
	         uiWindow.show();
	    } else {
	    	super.tblMain_tableClicked(e);
	    }
	}
}
