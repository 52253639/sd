/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;

/**
 * output class name
 */
public class ExpressionUI extends AbstractExpressionUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ExpressionUI.class);

	/**
	 * output class constructor
	 */
	public ExpressionUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.txtExpression.setText((String) this.getUIContext().get(
				"expression"));
	}

	public static String showExpressionUI(String expression) throws UIException {
		UIContext uiContext = new UIContext();
		uiContext.put("expression", expression);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(ExpressionUI.class.getName(), uiContext, null,
						"VIEW");
		uiWindow.show();
		return (String) uiWindow.getUIObject().getUIContext().get("expression");
	}

	protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
		super.btnCancel_actionPerformed(e);
		this.getUIContext().put("expression", null);
		this.destroyWindow();
	}

	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		super.btnYes_actionPerformed(e);
		this.getUIContext().put("expression", this.txtExpression.getText());
		this.destroyWindow();
	}
}
