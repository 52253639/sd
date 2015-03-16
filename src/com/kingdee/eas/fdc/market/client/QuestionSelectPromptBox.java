package com.kingdee.eas.fdc.market.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.util.HashMap;

import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.insider.InsiderCollection;
import com.kingdee.eas.fdc.insider.client.InsiderMutiF7UI;
import com.kingdee.eas.fdc.market.QuestionBaseCollection;
import com.kingdee.eas.util.client.ExceptionHandler;

public class QuestionSelectPromptBox extends KDCommonPromptDialog {
	
	protected IUIWindow currOrgTreeDialog;
	HashMap ctx = new HashMap();

	public QuestionSelectPromptBox() {
		super();
		// TODO 自动生成构造函数存根
	}

	public QuestionSelectPromptBox(Dialog owner) {
		super(owner);
		// TODO 自动生成构造函数存根
	}

	public QuestionSelectPromptBox(Frame owner) {
		super(owner);
		// TODO 自动生成构造函数存根
	}

	public QuestionSelectPromptBox(Dialog owner, String title) {
		super(owner, title);
		// TODO 自动生成构造函数存根
	}

	public QuestionSelectPromptBox(Frame owner, String title) {
		super(owner, title);
		// TODO 自动生成构造函数存根
	}


	
	public void show()
	{
		IUIFactory uiFactory = null;		
		try
		{   
			ctx.put("isPrompt","true");
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			currOrgTreeDialog = uiFactory.create(QuestionListUI.class.getName(), ctx);
			currOrgTreeDialog.show();
		}
		catch (Exception ex)
		{
			ExceptionHandler.handle(ex);
		}
	}

//	public boolean isCanceled()
//	{
//		if(currOrgTreeDialog == null)
//		{
//			return true;
//		}else
//		{
////			return ((QuestionListUI)currOrgTreeDialog.getUIObject()).isCanceled;	
//		}		
//	}

	public Object getData()
	{
        
            QuestionBaseCollection questionCollection = ((QuestionListUI) currOrgTreeDialog.getUIObject()).questionCollection;
            return questionCollection.toArray();
       
	}

}
