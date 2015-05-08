package com.kingdee.eas.fdc.schedule.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.pm.client.QualityCheckPointF7UI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;

public class F7FDCWBSSelectPromptBox extends KDCommonPromptDialog{
	private static final Logger logger = CoreUIObject.getLogger(F7FDCWBSSelectPromptBox.class);
	protected IUIWindow wbsF7Dlg = null;
	Map ctx = new HashMap();
	private IUIObject uiObj;

	public F7FDCWBSSelectPromptBox(IUIObject uiObj) {
		this.uiObj = uiObj;
	}
	
	public Object getData() {
		F7FDCWBSSelectUI wbsF7UI = (F7FDCWBSSelectUI) wbsF7Dlg.getUIObject();
		try {
			if(wbsF7UI.getData().toArray().length > 0)
				return wbsF7UI.getData().toArray();
		} catch (Exception e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		return null;
	}
	public boolean isCanceled() {
		return ((F7FDCWBSSelectUI)wbsF7Dlg.getUIObject()).isIsCancel();
	}
	public void show() {
		 IUIFactory uiFactory = null;
		 if(this.getEntityViewInfo() != null){
			 ctx.put("view", this.getEntityViewInfo());
		 }
	        try  {            
	            uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	            wbsF7Dlg = uiFactory.create(F7FDCWBSSelectUI.class.getName(),ctx,null,OprtState.VIEW,WinStyle.SHOW_ONLYLEFTSTATUSBAR);
	            wbsF7Dlg.show();
	        }    catch (BOSException ex) {
	           ExceptionHandler.handle(ex);
	           SysUtil.abort();
	        }
	}
}
