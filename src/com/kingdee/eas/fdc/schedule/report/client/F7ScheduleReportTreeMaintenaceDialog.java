/*
 * @(#)F7ScheduleReportTreeMaintenaceDialog.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.schedule.report.client;

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
 * 描述:进度报表自定义组织结构树窗体
 * @author adward_huang  date:2012-10-16
 * @version EAS6.1
 */
public class F7ScheduleReportTreeMaintenaceDialog extends KDCommonPromptDialog {

	private static final long serialVersionUID = -1091881743655099145L;
	private static final Logger logger = CoreUIObject.getLogger(F7ScheduleReportTreeMaintenaceDialog.class);
	private final CoreUI ui;
	private IUIWindow classDlg;
	private boolean canceled = true;

	public F7ScheduleReportTreeMaintenaceDialog(CoreUI ui) {
		this.ui = ui;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public boolean isCanceled() {
		return canceled;
	}

	@SuppressWarnings("unchecked")
	public void show() {
		//		 super.show();
		Map context = getUIContext();
		context.put("F7Dialog", this);
		IUIFactory uiFactory = null;
		if (classDlg == null) {
			try {
				uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
				classDlg = uiFactory.create(getUIName(), context, null, null, WinStyle.SHOW_ONLYLEFTSTATUSBAR);
				if (classDlg instanceof JDialog) {
					((JDialog) classDlg).setResizable(false);
				}
				((CoreUI) classDlg.getUIObject()).setUITitle("组织视图维护");
				classDlg.show();
			} catch (BOSException ex) {
				ExceptionHandler.handle(this, ex);
				return;
			}
		} else {
			classDlg.show();
		}
	}

	public ScheduleReportTreeMaintenanceDialogUI getScheduleReportTreeMaintenanceDialogUI() {
		if (classDlg == null)
			return null;
		return (ScheduleReportTreeMaintenanceDialogUI) classDlg.getUIObject();
	}

	public Object getData() {
		return getScheduleReportTreeMaintenanceDialogUI().getData();
	}

	public void setData(Object map) {
		getScheduleReportTreeMaintenanceDialogUI().setData(map);
	}

	private UIContext getUIContext() {
		UIContext ctx = new UIContext(this.ui);
		return ctx;
	}

	private String getUIName() {
		return "com.kingdee.eas.fdc.schedule.report.client.ScheduleReportTreeMaintenanceDialogUI";
	}
}
