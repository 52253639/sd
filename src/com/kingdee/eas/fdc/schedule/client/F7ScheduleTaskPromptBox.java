package com.kingdee.eas.fdc.schedule.client;

import java.util.Map;
import java.util.Set;

import javax.swing.JDialog;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.ExceptionHandler;

public class F7ScheduleTaskPromptBox extends KDCommonPromptDialog {
	private final CoreUI ui;
	private IUIWindow classDlg;
	private final ScheduleBaseInfo info;
	private final FDCScheduleTaskInfo curTask;
	private final Set filterIdSet;

	public F7ScheduleTaskPromptBox(CoreUI ui, ScheduleBaseInfo info, Set filterIdSet, FDCScheduleTaskInfo curTask) {
		this.ui = ui;
		this.curTask = curTask;
		// Window windows = SwingUtilities.getWindowAncestor(ui);
		this.info = info;
		this.filterIdSet = filterIdSet;

	}

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
				((CoreUI) classDlg.getUIObject()).setUITitle("«∞÷√»ŒŒÒ");
				classDlg.show();
			} catch (BOSException ex) {
				ExceptionHandler.handle(this, ex);
				return;
			}
		} else {
			classDlg.show();
		}
	}

	public F7ScheduleTaskUI getF7ScheduleTaskUI() {
		if (classDlg == null)
			return null;
		return (F7ScheduleTaskUI) classDlg.getUIObject();
	}

	public Object getData() {
		return new Object[] { getF7ScheduleTaskUI().getData() };
	}

	public boolean isCanceled() {
		return getF7ScheduleTaskUI().isCancel();
	}

	private UIContext getUIContext() {
		UIContext ctx = new UIContext(this.ui);
		ctx.put(F7ScheduleTaskUI.SCHEUDLEINFO, this.info);
		ctx.put(F7ScheduleTaskUI.FILTERTASK, this.filterIdSet);
		ctx.put(F7ScheduleTaskUI.CURTASK, curTask);
		return ctx;
	}

	private String getUIName() {
		return "com.kingdee.eas.fdc.schedule.client.F7ScheduleTaskUI";
	}
}
