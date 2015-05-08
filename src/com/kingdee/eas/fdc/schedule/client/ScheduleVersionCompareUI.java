/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.ganttproject.GanttPreviousStateTask;
import net.sourceforge.ganttproject.task.Task;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

/**
 * output class name
 */
public class ScheduleVersionCompareUI extends AbstractScheduleVersionCompareUI {
	private static final Logger logger = CoreUIObject.getLogger(ScheduleVersionCompareUI.class);

	/**
	 * output class constructor
	 */
	public ScheduleVersionCompareUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		Map param = new HashMap();

		param.put("isVersionCompare", Boolean.TRUE);
		param.put("baseVerID", this.getUIContext().get("baseVerID"));
		param.put("compVerID", this.getUIContext().get("compVerID"));
		FDCScheduleInfo fdcSchedule = FDCScheduleFactory.getRemoteInstance().getNewData(param);
		fdcSchedule.put("isVersionCompare", Boolean.TRUE);
		return fdcSchedule;
	}

	protected void afterOnload() {
		super.afterOnload();
		// this.contNumber.setVisible(false);
		// this.contName.setVisible(false);
		contCreator.setVisible(false);
		this.prmtCreator.setVisible(false);
		this.prmtAdminPerson.setVisible(false);

		// this.txtNumber.setEnabled(false);
		// this.txtNumber.setRequired(false);
		// this.txtName.setRequired(false);
		this.prmtAdminPerson.setRequired(false);
		this.prmtCreator.setRequired(false);
		this.actionProperty.setEnabled(false);
		this.actionProperty.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionAttachment.setVisible(false);

	}

	protected FDCScheduleInfo getNewData() throws EASBizException, BOSException {

		Map param = new HashMap();

		param.put("isVersionCompare", Boolean.TRUE);
		param.put("baseVerID", this.getUIContext().get("baseVerID"));
		param.put("compVerID", this.getUIContext().get("compVerID"));
		FDCScheduleInfo fdcSchedule = FDCScheduleFactory.getRemoteInstance().getNewData(param);
		fdcSchedule.put("isVersionCompare", Boolean.TRUE);

		return fdcSchedule;
	}

	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (editData != null) {
			Task[] tTasks = getScheduleGanttProject().getTaskManager().getTasks();
			ArrayList tasks = new ArrayList();
			for (int i = 0; i < tTasks.length; i++) {
				if (tTasks[i] instanceof KDTask) {
					KDTask tTask = (KDTask) tTasks[i];
					if (tTask.getMyOldStartDate() == null)
						continue;
					GanttPreviousStateTask task = new GanttPreviousStateTask(tTask.getTaskID(), tTask.getMyOldStartDate(), tTask.getMyOldDuration()
							.intValue(), false, false);
					tasks.add(task);
				}
			}

			getScheduleGanttProject().getArea().setPreviousStateTasks(tasks);
			getScheduleGanttProject().getArea().repaint();
		}
	}

	protected void validateTree() {
		super.validateTree();

	}


}