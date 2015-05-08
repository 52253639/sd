/*
 * @(#)F7ScheduleReportDialog.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.util.List;
import java.util.Map;

import javax.swing.JDialog;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.ExceptionHandler;

/**
 * 描述:进度报表组织结构树窗体
 * @author adward_huang  date:2012-9-18
 * @version EAS6.1
 */
public class F7ScheduleReportDialog extends KDCommonPromptDialog {

	private static final long serialVersionUID = -1091881743655099146L;
	private static final Logger logger = CoreUIObject.getLogger(F7ScheduleReportDialog.class);
	private final CoreUI ui;
	private IUIWindow classDlg;

	public F7ScheduleReportDialog(CoreUI ui) {
		this.ui = ui;
	}

	@SuppressWarnings("unchecked")
	public void show() {
		// super.show();
		Map context = getUIContext();
		IUIFactory uiFactory = null;
		if (classDlg == null) {
			try {
				uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
				classDlg = uiFactory.create(getUIName(), context, null, null, WinStyle.SHOW_ONLYLEFTSTATUSBAR);
				if (classDlg instanceof JDialog) {
					((JDialog) classDlg).setResizable(false);
				}
				((CoreUI) classDlg.getUIObject()).setUITitle("查询范围选择");
				classDlg.show();
			} catch (BOSException ex) {
				ExceptionHandler.handle(this, ex);
				return;
			}
		} else {
			classDlg.show();
		}
	}

	public ScheduleReportOrgQueryUI getScheduleReportOrgQueryUI() {
		if (classDlg == null)
			return null;
		return (ScheduleReportOrgQueryUI) classDlg.getUIObject();
	}

	public Object getData() {
		return getScheduleReportOrgQueryUI().getData();
	}

	public void setData(Object map) {
		getScheduleReportOrgQueryUI().setData(map);
	}
	
	public String getProjectName() {
		return getScheduleReportOrgQueryUI().getProjectName();
	}

	public void setProjectName(String name) {
		getScheduleReportOrgQueryUI().setProjectName(name);
	}

	public List<String> getSelectProAndOrgList() {
		return getScheduleReportOrgQueryUI().getSelectProAndOrgList();
	}

	public void setSelectProAndOrgList(List<String> list) {
		getScheduleReportOrgQueryUI().setSelectProAndOrgList(list);
	}

	public boolean isCanceled() {
		return getScheduleReportOrgQueryUI().isCancel();
	}

	private UIContext getUIContext() {
		UIContext ctx = new UIContext(this.ui);
		return ctx;
	}

	private String getUIName() {
		return "com.kingdee.eas.fdc.schedule.client.report.ScheduleReportOrgQueryUI";
	}
}
