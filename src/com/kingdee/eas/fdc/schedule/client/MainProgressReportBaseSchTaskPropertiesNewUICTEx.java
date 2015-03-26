package com.kingdee.eas.fdc.schedule.client;

import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;

public class MainProgressReportBaseSchTaskPropertiesNewUICTEx extends MainProgressReportBaseSchTaskPropertiesNewUI{

	public MainProgressReportBaseSchTaskPropertiesNewUICTEx() throws Exception {
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
	}
	public void afterLoadInitSeting() {
		super.afterLoadInitSeting();
		FDCScheduleTaskInfo info=(FDCScheduleTaskInfo) this.getSelectedTask().getScheduleTaskInfo();
		if(info.getTaskType().equals(RESchTaskTypeEnum.KEY)||
				info.getTaskType().equals(RESchTaskTypeEnum.MILESTONE)){
			this.txtYes.setEnabled(false);
			this.txtNo.setEnabled(false);
		}
	}
	
}
