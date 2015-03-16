package com.kingdee.eas.fdc.market.client;

import java.util.List;
import java.util.Map;

import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.TranSaleManSelectUI;
import com.kingdee.eas.framework.client.CoreUI;

public class ContractPromptDialog extends KDCommonPromptDialog{

	protected IUIWindow classDlg = null;
	protected IUIObject owner;
	protected IUIWindow currCommonUI;
	protected FilterInfo filter;
    private CoreUI thisUserUI;
    
	public ContractPromptDialog(IUIObject owner,FilterInfo filter) throws UIException {
		this.filter=filter;
		this.owner=owner;
	}
	
	public void show()
	{
		IUIFactory uiFactory = null;
		UIContext uiContext = new UIContext(owner);
		uiContext.put("filter", filter);
		try {
			 uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	         currCommonUI = uiFactory.create(ContractSelectUI.class.getName(),uiContext);
	         thisUserUI = ((CoreUI)this.currCommonUI.getUIObject());
	         currCommonUI.show();
		} catch (UIException e) {
			e.printStackTrace();
		}
	}
    public Object getData()
    {   
    	Map retMap = thisUserUI.getUIContext();
    	List value = (List)retMap.get("contract");
        return value.toArray();
    }
    
    public boolean isCanceled() {//返回值为空时代表取消操作
    	return thisUserUI.getUIContext().get("contract")==null?true:false;
    }
}
