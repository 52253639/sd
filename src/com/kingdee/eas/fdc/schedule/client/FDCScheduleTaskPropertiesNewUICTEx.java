package com.kingdee.eas.fdc.schedule.client;

import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;

public class FDCScheduleTaskPropertiesNewUICTEx extends FDCScheduleTaskPropertiesNewUI{

	public FDCScheduleTaskPropertiesNewUICTEx() throws Exception {
		super();
	}
	public void load() {
		super.load();
		FDCScheduleTaskInfo info=(FDCScheduleTaskInfo) this.getSelectedTask().getScheduleTaskInfo();
		if(info.getTaskType().equals(RESchTaskTypeEnum.KEY)||
				info.getTaskType().equals(RESchTaskTypeEnum.MILESTONE)){
			this.txtYes.setSelected(true);
			this.txtYes.setEnabled(false);
			this.txtNo.setEnabled(false);
		}
		this.cbTaskType.setEnabled(false);
		this.prmtCheckNode.setEnabled(false);
	}
	public void afterLoadInitSeting() {
		super.afterLoadInitSeting();
		FDCScheduleTaskInfo info=(FDCScheduleTaskInfo) this.getSelectedTask().getScheduleTaskInfo();
		if(info.getTaskType().equals(RESchTaskTypeEnum.KEY)||
				info.getTaskType().equals(RESchTaskTypeEnum.MILESTONE)){
			this.txtYes.setEnabled(false);
			this.txtNo.setEnabled(false);
		}
		this.cbTaskType.setEnabled(false);
		this.prmtCheckNode.setEnabled(false);
	}
}
