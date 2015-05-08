package com.kingdee.eas.fdc.schedule.client;

import javax.swing.JDialog;

import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;

public class F7WorkAmountTaskPromptBox extends KDCommonPromptDialog {
	private FilterInfo filter = null;
	private IUIWindow dlg = null; 
	private UIContext context ;
	private Object  Data;
	
	public void setData(Object data) {
		Data = data;
	}

	public F7WorkAmountTaskPromptBox() {
		// TODO Auto-generated constructor stub
	}
	
	public F7WorkAmountTaskPromptBox(FilterInfo filter) {
		// TODO Auto-generated constructor stub
		this.filter = new FilterInfo();
		context = new UIContext(this);
		context.put("filter", filter);
//		isHistoryRecordEnabled = false;
	}
	
	public FilterInfo getFilter() {
		return filter;
	}
	public void show() {
		// TODO Auto-generated method stub
		
		
		try {
			IUIFactory factory = (IUIFactory) UIFactory.createUIFactory(UIFactoryName.MODEL);
			dlg = factory.create(getF7UIClassName(),context, null,null,WinStyle.SHOW_ONLYLEFTSTATUSBAR);
		} catch (UIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dlg instanceof JDialog){
			((JDialog) dlg).setResizable(false);
		}
		if(dlg!=null){
			((JDialog) dlg).setTitle("项目任务");
			dlg.show();
		}
		
	}
	
	public boolean isCanceled() {
		// TODO Auto-generated method stub
		boolean isCancel = true;
		if(getF7WorkAmountTaskUI() != null){
			isCancel = getF7WorkAmountTaskUI().isCancel();
		}
		return isCancel;
	}
	
	public UIContext getMyUIContext(){
		UIContext context = new UIContext(this);
		return context;
	}
	
	public F7WorkAmountTaskUI getF7WorkAmountTaskUI(){
		if(dlg == null){
			return null;
		}else{
			return (F7WorkAmountTaskUI) dlg.getUIObject();
		}
	}
	
	public Object getData() {
		// TODO Auto-generated method stub
		Object o = new Object[]{getF7WorkAmountTaskUI().getData()};
		return o;
	}
	public String getF7UIClassName(){
		return "com.kingdee.eas.fdc.schedule.client.F7WorkAmountTaskUI";
	}
}
