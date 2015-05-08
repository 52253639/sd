package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JDialog;

import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.schedule.report.client.F7ScheduleReportDialog;
import com.kingdee.eas.fdc.schedule.report.client.ScheduleReportOrgQueryUI;

public class ScheduleRptOrgPromptBox extends KDCommonPromptDialog {
	private FilterInfo filter = null;
	private IUIWindow dlg = null; 
	private UIContext context ;
	private Object  Data;
	
	public void setData(Object data) {
		Data = data;
	}

	public ScheduleRptOrgPromptBox() {
	}
	
	public ScheduleRptOrgPromptBox(FilterInfo filter) {
		this.filter = new FilterInfo();
		context = new UIContext(this);
		context.put("filter", filter);
//		isHistoryRecordEnabled = false;
	}
	
	public FilterInfo getFilter() {
		return filter;
	}
	public void show() {
		
		
		try {
			IUIFactory factory = (IUIFactory) UIFactory.createUIFactory(UIFactoryName.MODEL);
			dlg = factory.create(getF7UIClassName(),context, null,null,WinStyle.SHOW_ONLYLEFTSTATUSBAR);
		} catch (UIException e) {
			e.printStackTrace();
		}
		if(dlg instanceof JDialog){
			((JDialog) dlg).setResizable(false);
		}
		if(dlg!=null){
			((JDialog) dlg).setTitle("≤È—Ø∑∂Œß—°‘Ò");
			dlg.show();
		}
		
	}
	
	public boolean isCanceled() {
		boolean isCancel = true;
		if(getScheduleReportOrgQueryUI() != null){
			isCancel = getScheduleReportOrgQueryUI().isCancel();
		}
		return isCancel;
	}
	
	public UIContext getMyUIContext(){
		UIContext context = new UIContext(this);
		return context;
	}
	
	public ScheduleReportOrgQueryUI getScheduleReportOrgQueryUI(){
		if(dlg == null){
			return null;
		}else{
			return (ScheduleReportOrgQueryUI) dlg.getUIObject();
		}
	}


	public Object getData() {
		Object o = new Object[]{getScheduleReportOrgQueryUI().getData()};
		return o;
	}
	
	
	
	public String getF7UIClassName(){
		return ScheduleReportOrgQueryUI.class.getName();
	}
}
