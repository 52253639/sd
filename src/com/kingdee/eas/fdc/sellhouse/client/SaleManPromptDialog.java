package com.kingdee.eas.fdc.sellhouse.client;

import java.util.List;
import java.util.Map;

import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TranSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.client.CoreUI;

public class SaleManPromptDialog extends KDCommonPromptDialog{
	
	protected IUIWindow classDlg = null;
	protected IUIObject owner;
	protected List saleManEntry;
	protected String state;
	protected BOSUuid billId;
	protected IUIWindow currCommonUI;
	protected SellProjectInfo sellProject;
	
    private CoreUI thisUserUI;
    
	public SaleManPromptDialog(IUIObject owner,List saleManEntry,String state,BOSUuid billId,SellProjectInfo sellProject) throws UIException {
		this.saleManEntry=saleManEntry;
		this.owner=owner;
		this.state=state;
		this.billId=billId;
		this.sellProject=sellProject;
	}
	
	public void show()
	{
		IUIFactory uiFactory = null;
		UIContext uiContext = new UIContext(owner);
		uiContext.put("saleManEntry", saleManEntry);
		uiContext.put("state", state);
		uiContext.put("billId", billId);
		uiContext.put("sellProject", sellProject);
		try {
			 uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	         currCommonUI = uiFactory.create(TranSaleManSelectUI.class.getName(),uiContext);
	         thisUserUI = ((CoreUI)this.currCommonUI.getUIObject());
	         currCommonUI.show();
		} catch (UIException e) {
			e.printStackTrace();
		}
	}
	 /**
     * 获取用户选取的数据
     */
    public Object getData()
    {   
    	//一定要在上下文RetObi中返回选择的对象，    //返回值为空时代表取消操作
    	Map retMap = thisUserUI.getUIContext();
    	List saleManEntry = (List)retMap.get("saleManEntry");
        return saleManEntry.toArray();
    }
    
    public boolean isCanceled() {//返回值为空时代表取消操作
    	return thisUserUI.getUIContext().get("saleManEntry")==null?true:false;
    }
}
