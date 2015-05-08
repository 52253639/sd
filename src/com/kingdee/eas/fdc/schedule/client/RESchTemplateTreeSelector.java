/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import javax.swing.JDialog;

import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有<p>		 	
 * 描述：		                              
 *		
 * @author		车忠伟
 * @version		EAS7.0		
 * @createDate	2011-8-12
 * @see						
 */
public class RESchTemplateTreeSelector extends JDialog implements
		KDPromptSelector {
    protected IUIWindow uiWindow;
    
	public RESchTemplateTreeSelector() {
		super();
		
	}
   
	public void show() {
		String editUI = RESchTemplateTreeNewListUI.class.getName();
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("standatrdUI", this);
		IUIFactory uiFactory;
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			uiWindow = uiFactory.create(editUI, uiContext, null,
					OprtState.ADDNEW);
			uiWindow.show();
		} catch (UIException e) {
			e.printStackTrace();
		}
	}

	public boolean isCanceled() {
		return false;
	}

	public Object getData() {
		try {
			RESchTemplateTreeNewListUI taskUIObject = (RESchTemplateTreeNewListUI) uiWindow
					.getUIObject();
			Object obj = taskUIObject.getUIContext().get("template");
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
