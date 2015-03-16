package com.kingdee.eas.fdc.sellhouse.client;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioParam;
public class AgioPromptDialog extends KDCommonPromptDialog{
	
	protected IUIWindow classDlg = null;
	protected AgioEntryCollection agios=null;
	protected AgioParam agioParam=null;
	protected IObjectValue objectValue=null;
	protected String roomId=null;
	protected IUIObject owner;
	protected boolean isAgioListCanEdit=true;
	
	public AgioPromptDialog(IUIObject owner,String roomId,
			AgioEntryCollection agios, AgioParam agioParam, IObjectValue objectValue,boolean isAgioListCanEdit) throws UIException {
		this.agios=agios;
		this.agioParam=agioParam;
		this.objectValue=objectValue;
		this.roomId=roomId;
		this.owner=owner;
		this.isAgioListCanEdit=isAgioListCanEdit;
	}
	
	public String getUITitle()
	{
		return new String("房间选择");
	}

	public void show()
	{

		UIContext uiContext = new UIContext(owner);
		uiContext.put("roomId", roomId);
		uiContext.put("agios", agios);
		uiContext.put("agioParam", agioParam);
		uiContext.put("objectValue", objectValue);
		uiContext.put("isAgioListCanEdit", new Boolean(isAgioListCanEdit));
		uiContext.put("isAttach", new Boolean(true));
		uiContext.put("isVirtual", new Boolean(false));
		try {
			classDlg = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(AgioSelectUI.class.getName(), uiContext, null, "View");
			classDlg.show();
//			agioParam = (AgioParam) uiWindow.getUIObject().getUIContext().get("agioParam");
		} catch (UIException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 判断是否点击取消按钮，决定是否调用取值函数
	 * @author laiquan_luo
	 */
	public boolean isCanceled()
	{

        if(classDlg!=null)
        {
            return ((AgioSelectUI)classDlg.getUIObject()).isCanceled ;
        }
        else
        {
            return true;
        }
	}

	public Object getData()
	{
		AgioSelectUI ui = (AgioSelectUI) classDlg.getUIObject();
		return ui.getReturnValue();
	}

}
