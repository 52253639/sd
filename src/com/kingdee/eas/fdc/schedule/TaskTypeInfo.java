package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import java.util.ArrayList;

public class TaskTypeInfo extends AbstractTaskTypeInfo implements Serializable {
	/**
	 * 主项节点任务
	 */
	public static final String TASKTYPE_MAINTASK = "Fs627NttSemeAN1knRHeIxN/5DU=";
	/**
	 * 设计类任务
	 */
	public static final String TASKTYPE_DESIGNTASK = "Ynpzzs0IR/WE7upNi633IhN/5DU=";
	/**
	 * 采购招标类任务
	 */
	public static final String TASKTYPE_PURCHASEASK = "MOMaPpNcTweLh0TQgT1HaBN/5DU=";
	/**
	 * 专项任务节点
	 */
	public static final String TASKTYPE_SPECIALTASK = "tEDzW8QTSH+AKnvy4WmbvBN/5DU=";
	/**
	 * 楼栋施工类任务
	 */
	public static final String TASKTYPE_BUILDINGTASK = "pPEI1DjMRkq0Jzp5KyTnpBN/5DU=";
	/**
	 * 施工类任务
	 */
	public static final String TASKTYPE_CONSTRACTIONTASK = "1+bbn9ljSwqON6vTZ9YIgRN/5DU=";
	
	/**
	 * 计划调整
	 */
	public static final String TASKTYPE_ADJUST = "l5BW2mSKRAyOXyuxnwWGUhN/5DU=";
	
	/**
	 * 系统预设类型数组
	 */
	public static final String[] SYSPREDESIGN = {
		TASKTYPE_MAINTASK,TASKTYPE_DESIGNTASK,TASKTYPE_PURCHASEASK,
		TASKTYPE_SPECIALTASK,TASKTYPE_BUILDINGTASK,TASKTYPE_CONSTRACTIONTASK
	};
	
	public TaskTypeInfo() {
		super();
	}

	protected TaskTypeInfo(String pkField) {
		super(pkField);
	}
}