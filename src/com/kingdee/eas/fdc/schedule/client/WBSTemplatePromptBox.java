package com.kingdee.eas.fdc.schedule.client;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;

import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.schedule.WBSTemplateEntryCollection;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryCollection;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryInfo;
import com.kingdee.eas.rptclient.gr.rptclient.frame.WinStyle;
import com.kingdee.eas.util.client.ComponentUtil;
import com.kingdee.eas.util.client.ExceptionHandler;

public class WBSTemplatePromptBox extends KDCommonPromptDialog {
	protected IUIWindow classDlg = null;
	private KDTable table;
	protected IUIObject owner;
	private boolean isShowPreTaskF7 = false;
	private WBSTemplatePrefixEntryCollection wbsTmpPreTasks;
	private String selectedNum = null;
	private boolean isShowPrefixTaskF7(){
		return isShowPreTaskF7;
	}
	
	public WBSTemplatePromptBox(IUIObject owner,KDTable uiTable,boolean isShowPreTaskF7){
		this.owner = owner;
		table = uiTable;
		this.isShowPreTaskF7 = isShowPreTaskF7;
	}
	private Map getUIContext(){
		HashMap context = new HashMap();
		context.put("table", table);
		context.put("Owner", ComponentUtil.getRootComponent((Component) owner));
		context.put("WBSTMPPreTaskEntrys", wbsTmpPreTasks);
		context.put("selectedNum", selectedNum);
		return context;
	}
	
	public void show() {
		IUIFactory uiFactory = null;
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			if(isShowPrefixTaskF7())
				classDlg = uiFactory.create(
					WBSTemplatePrefixTaskF7UI.class.getName(), getUIContext(),null,null,WinStyle.SHOW_ALL);
			else
				classDlg = uiFactory.create(
						WBSTemplatePrefixEntryF7UI.class.getName(), getUIContext(),null,null,WinStyle.SHOW_ALL);
			((JDialog)classDlg).setResizable(false);
			classDlg.show();
		} catch (UIException e) {
			ExceptionHandler.handle(e);
		}
	}
	
	public boolean isCanceled() {
		if(isShowPrefixTaskF7()){
			return ((WBSTemplatePrefixTaskF7UI) classDlg.getUIObject()).isCancel();	
		}else{
			return ((WBSTemplatePrefixEntryF7UI) classDlg.getUIObject()).isCancel();
		}
		
	}
	
	public Object getData() {
		Object obj = null;
		if(isShowPrefixTaskF7()){
			obj = ((WBSTemplatePrefixTaskF7UI) classDlg.getUIObject()).getData();
			if(obj instanceof WBSTemplatePrefixEntryCollection){
				
			}
		}else{
			obj = ((WBSTemplatePrefixEntryF7UI) classDlg.getUIObject()).getData();
			if(obj instanceof WBSTemplateEntryCollection){
				
			}
		}
		return obj;
	}
	public void setWBSTMPEntry(Object[] wbsTmpPreTasks){
		if(wbsTmpPreTasks != null && wbsTmpPreTasks.length>=1){
			if(this.wbsTmpPreTasks == null){
				this.wbsTmpPreTasks = new WBSTemplatePrefixEntryCollection();
			}
			this.wbsTmpPreTasks.clear();
			for(int i=0;i<wbsTmpPreTasks.length;i++){
				this.wbsTmpPreTasks.add((WBSTemplatePrefixEntryInfo)wbsTmpPreTasks[i]);
			}
		}else{
			this.wbsTmpPreTasks = new WBSTemplatePrefixEntryCollection(); 
		}
	}
	
	public void setSelectedNum(String number){
		this.selectedNum = number;
	}
}
