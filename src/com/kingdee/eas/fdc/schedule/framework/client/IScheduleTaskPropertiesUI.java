package com.kingdee.eas.fdc.schedule.framework.client;

import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

public interface IScheduleTaskPropertiesUI {
	public void showUI() throws Exception;
	public KDTask[] getRetureTasks();
}
