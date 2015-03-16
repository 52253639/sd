package com.kingdee.eas.fdc.basecrm.client;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.fdc.customerservice.client.FDCStdQuestionPromptBox;

public class FDCCommonPromptBox extends KDCommonPromptDialog {
	
	private static final Logger logger = Logger.getLogger(FDCStdQuestionPromptBox.class);	
	protected IUIWindow currCommonUI;
	
    private CoreUI thisUserUI;
    
    private UIContext uiContext = new UIContext(this);
    private String thisClassName;
    
	
    public FDCCommonPromptBox(String uiClassName){
    	thisClassName = uiClassName;
        uiContext.put(UIContext.OWNER,this);
    }
    
    public FDCCommonPromptBox(String uiClassName,Object owner){
    	thisClassName = uiClassName;
        uiContext.put(UIContext.OWNER,this);
        
        uiContext.put("ParentObject", owner);
    }    
    
    
    /**
     * 操作F7控件时弹出界面
     */
    public void show()
    {
        IUIFactory uiFactory = null;
        try
        {            
            uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
            currCommonUI = uiFactory.create(thisClassName,uiContext);
            thisUserUI = ((CoreUI)this.currCommonUI.getUIObject());
            currCommonUI.show();
        }
        catch (BOSException ex)
        {
        	logger.error(ex.getMessage());
        }
    }
    
    /**
     * 获取用户选取的数据
     */
    public Object getData()
    {   
    	//一定要在上下文RetObi中返回选择的对象，    //返回值为空时代表取消操作
    	Map retMap = thisUserUI.getUIContext();
    	Object retObj = retMap.get("RetObj");
    	Object[] objs = new Object[1];
    	objs[0] = retObj;    	
        return objs;
    }
    
    public boolean isCanceled() {//返回值为空时代表取消操作
    	return thisUserUI.getUIContext().get("RetObj")==null?true:false;
    }


}
