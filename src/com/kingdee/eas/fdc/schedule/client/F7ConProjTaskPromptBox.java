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

/**
 * 合同工程量填报中用到的F7任务选择框。
 * @author joey_jia
 *
 */
public class F7ConProjTaskPromptBox  extends KDCommonPromptDialog{
	private FilterInfo filter = null;
	private IUIWindow dlg = null; 
	private UIContext context ;
	private Object  Data;
	private String titleName = "F7进度任务查询";
	
	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public F7ConProjTaskPromptBox() {
		this( null);
	}

    public F7ConProjTaskPromptBox( FilterInfo filter) {
		this.context = new UIContext(this);
		context.put("filter", filter);
	}
	
	
	public void show() {
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
			((JDialog) dlg).setTitle(titleName);
			dlg.show();
		}
	}
	
	public boolean isCanceled() {
			// TODO Auto-generated method stub
			boolean isCancel = true;
			if(getF7ConProjTaskUI() != null){
				isCancel = getF7ConProjTaskUI().isCancel();
			}
			return isCancel;
	}
	public Object getData() {
			Object o = new Object[]{getF7ConProjTaskUI().getData()};
			return o;
	}
	
	
	public F7ProjectAmountTaskUI getF7ConProjTaskUI(){
		if(dlg == null){
			return null;
		}else{
			return (F7ProjectAmountTaskUI) dlg.getUIObject();
		}
	}
	private String getF7UIClassName() {
		return "com.kingdee.eas.fdc.schedule.client.F7ProjectAmountTaskUI";
	}
	
}
