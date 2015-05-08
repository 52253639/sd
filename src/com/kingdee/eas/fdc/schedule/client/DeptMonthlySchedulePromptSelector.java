/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.util.client.ExceptionHandler;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述：
 * 
 * @author 李建波
 * @version EAS7.0
 * @createDate 2011-8-20
 * @see
 */

public class DeptMonthlySchedulePromptSelector implements KDPromptSelector {
	protected IUIWindow f7UI;

	protected CoreUIObject ui;

//	private Object[] sltData;
	private Object sltData;

	// 增加个工程项目和日期(为了f7筛选)
	private AdminOrgUnitInfo admininfo;

	private Date selDate;
	
	private FDCScheduleTaskInfo relateTask;

	public DeptMonthlySchedulePromptSelector() {

	}
	
	public DeptMonthlySchedulePromptSelector(AdminOrgUnitInfo admininfo, Date selDate, FDCScheduleTaskInfo relateTask) {
		this.admininfo = admininfo;
		this.selDate = selDate;
		this.relateTask = relateTask;
	}
	
	public void setSltData(Object sltData) {
		this.sltData = sltData;
	}

	public Object getSltData() {
		return sltData;
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */
	public Object getData() {
		return sltData;
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public boolean isCanceled() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void show() {
		UIContext context = new UIContext(ui);
		context.put("CANRESIZE", Boolean.FALSE);
		context.put("slt", this);
		context.put("seladmininfo", admininfo);
		context.put("sltDate", selDate);
		context.put("relateTask", relateTask);
		IUIFactory uiFactory = null;
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			f7UI = uiFactory.create(F7RelatedTaskSelectUI.class.getName(), context, null, OprtState.VIEW);
			f7UI.show();

		} catch (BOSException ex) {
			ExceptionHandler.handle(ui, ex);
		}

	}

}
