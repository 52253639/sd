package com.kingdee.eas.fdc.schedule.framework.util;

import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;

public interface IRESchTaskPredecessor {
	
	/**
	 * 前置任务
	 * 
	 * @return
	 */
	public IRESchTask getPredecessor();

	public void setPredecessor(IRESchTask task);
	/**
	 * 当前任务
	 * 
	 * @return
	 */
	public IRESchTask getCurrentTask();

	public void setCurrentTask(IRESchTask task);
	/**
	 * 搭建时间
	 * 
	 * @return
	 */
	public int getDifferenceDay();

	public void setDifferenceDay(int item);
	/**
	 * 前置类型
	 * 
	 * @return
	 */
	public TaskLinkTypeEnum getPredecessorType();

	public void setPredecessorType(TaskLinkTypeEnum item);	

}
