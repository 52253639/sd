/*
 * @(#)ContractTypePromptSelector.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.invite.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.util.client.ExceptionHandler;

/**
 * 
 * 描述:招标类型F7 Selector
 * 
 * @author 肖飙彪_金蝶深圳分公司:2007-5-10
 *         <p>
 * @version EAS5.3
 */
public class HeadTypePromptSelector implements KDPromptSelector {

	protected IUIWindow f7UI;

	protected CoreUIObject ui;

	public HeadTypePromptSelector(CoreUIObject ui) {
		super();
		this.ui = ui;
	}

	public void show() {

		UIContext context = new UIContext(ui);
		context.put("CANRESIZE", Boolean.FALSE);

		IUIFactory uiFactory = null;
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			f7UI = uiFactory.create(HeadTypeF7UI.class.getName(), context,
					null, OprtState.VIEW);
			f7UI.show();

		} catch (BOSException ex) {
			ExceptionHandler.handle(ui, ex);
		}

	}

	public boolean isCanceled() {
		HeadTypeF7UI f7UIObject = (HeadTypeF7UI) f7UI.getUIObject();

		return f7UIObject.isCancel();
	}

	public Object getData() {

		try {
			HeadTypeF7UI f7UIObject = (HeadTypeF7UI) f7UI.getUIObject();
			return f7UIObject.getData();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
