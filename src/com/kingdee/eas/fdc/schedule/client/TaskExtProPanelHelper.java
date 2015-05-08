package com.kingdee.eas.fdc.schedule.client;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo;

public class TaskExtProPanelHelper implements ITaskPanelHelper {
	private final FDCScheduleTaskPropertiesUI taskPropertiesUI;
	private CostInfoPanelHelper costInfoHelper = null;
	private WorkloadLogPanelHelper workloadLogHelper = null;
	private WorkResultPanelHelper workResultHelper = null;
	private MaterialPlanPanelHelper materialPlanHelper = null;
	private QualityPlanPanelHelper qualityPlanHelper = null;
	private TaskScheduleLogHelper taskScheduleLogHelper = null;
	private Map extProperties;
	private boolean isFromExecUI = false;
	private FDCScheduleTaskExtInfo taskExtInfo = null;
	

	public FDCScheduleTaskExtInfo getTaskExtInfo() {
		return taskExtInfo;
	}

	public void setTaskExtInfo(FDCScheduleTaskExtInfo taskExtInfo) {
		this.taskExtInfo = taskExtInfo;
	}

	public TaskExtProPanelHelper(FDCScheduleTaskPropertiesUI ui) {
		super();
		this.taskPropertiesUI = ui;
	}

	public void load(boolean isExecuting) throws EASBizException, BOSException {
		this.isFromExecUI = isExecuting;
		if (extProperties == null) {
			extProperties = new HashMap();
		}
		extProperties = FDCScheduleTaskExtFactory.getRemoteInstance().getProByWBSID(getWBS().getId().toString());
		//获取扩展对象
		if(extProperties.get("taskExtInfo") != null){
			setTaskExtInfo((FDCScheduleTaskExtInfo) extProperties.get("taskExtInfo"));
		}
		// 加载工程量日志
		if (workloadLogHelper == null) {
			workloadLogHelper = new WorkloadLogPanelHelper(this);
		}
		this.workloadLogHelper.load();
		// 加载成本信息
		if (costInfoHelper == null) {
			costInfoHelper = new CostInfoPanelHelper(this);
		}
		this.costInfoHelper.load();
		// 加载物资计划
//		if (materialPlanHelper == null) {
//			materialPlanHelper = new MaterialPlanPanelHelper(this);
//		}
//		materialPlanHelper.load();
		// 加载质量计划
		if (qualityPlanHelper == null) {
			qualityPlanHelper = new QualityPlanPanelHelper(this);
		}
		qualityPlanHelper.load();
		// 加载工作成果
		if (workResultHelper == null) {
			workResultHelper = new WorkResultPanelHelper(this);
		}
		workResultHelper.load();
		//	任务日志
		if(taskScheduleLogHelper == null){
			taskScheduleLogHelper = new TaskScheduleLogHelper(this);
		}
		taskScheduleLogHelper.load();
	}

	/**
	 * 构造计划编制时的扩展属性
	 * 
	 * @return
	 */
	private Map storeComplieProp() {
		Map map = new HashMap();
		map.putAll(workloadLogHelper.store());
		map.putAll(costInfoHelper.store());
		map.putAll(qualityPlanHelper.store(isFromExecUI));
		return map;
	}

	/**
	 * 构造计划执行时的扩展属性
	 * 
	 * @return
	 */
	private Map storeExecProp() {
		Map map = new HashMap();
		map.putAll(workloadLogHelper.store());
		map.putAll(costInfoHelper.store());
		map.putAll(qualityPlanHelper.store(isFromExecUI));
		map.putAll(workResultHelper.store());
		return map;
	}

	public void commit() {
		Map map = new HashMap();
		if (isFromExecUI) {
			map = storeExecProp();
		} else {
			map = storeComplieProp();
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("wbs.id", getWBS().getId().toString()));
		try {
			map.put("wbsInfo", getWBS());
			if (FDCScheduleTaskExtFactory.getRemoteInstance().exists(filter)) {
				if (isFromExecUI)
					FDCScheduleTaskExtFactory.getRemoteInstance().updateExeProp(map);
				else
					FDCScheduleTaskExtFactory.getRemoteInstance().updateCompleProp(map);
			} else {
				FDCScheduleTaskExtFactory.getRemoteInstance().saveCompileProp(map);
			}
		} catch (Exception e) {
			taskPropertiesUI.handUIException(e);
		}
	}

	public FDCScheduleTaskPropertiesUI getFDCSCHTaskPropUI() {
		return taskPropertiesUI;
	}

	public ScheduleTaskBaseInfo getTaskInfo() {
		return taskPropertiesUI.getSelectedTask().getScheduleTaskInfo();
	}

	public FDCWBSInfo getWBS() {
		return ((FDCScheduleTaskInfo) taskPropertiesUI.getSelectedTask().getScheduleTaskInfo()).getWbs();
	}

	public Map getExtProperties() {
		return extProperties;
	}

	/**
	 * 设置编辑状态,执行
	 */
	public void setEditUIStatus() {
		workloadLogHelper.setEditStatus();
		costInfoHelper.setEditStatus();
//		materialPlanHelper.setEditStatus();
		qualityPlanHelper.setEditStatus();
		if(materialPlanHelper !=null)
		materialPlanHelper.setEditStatus();
		workResultHelper.setEditStatus();
		taskScheduleLogHelper.setEditStatus();
	}

	/**
	 * 设置执行状态的执行状态
	 */
	public void setExecutingUIStatus() {
		workloadLogHelper.setExecutingStatus();
		costInfoHelper.setExecutingStatus();
//		materialPlanHelper.setExecutingStatus();
		qualityPlanHelper.setExecutingStatus();
		if(materialPlanHelper !=null)
		materialPlanHelper.setExecutingStatus();
		workResultHelper.setExecutingStatus();
		taskScheduleLogHelper.setExecutingStatus();
	}
	/**
	 * 设置查看状态
	 */
	public void setViewUIStatus() {
		workloadLogHelper.setViewStatus();
		costInfoHelper.setViewStatus();
//		materialPlanHelper.setViewStatus();
		qualityPlanHelper.setViewStatus();
		if(materialPlanHelper !=null)
		materialPlanHelper.setViewStatus();
		workResultHelper.setViewStatus();
		taskScheduleLogHelper.setViewStatus();
	}

	public void load() throws EASBizException, BOSException {
		load(taskPropertiesUI.isExecuting());
	}
	public void loadMaterial() throws BOSException{
		materialPlanHelper.reload();
	}
	
	public void loadTaskLog() throws BOSException{
		taskScheduleLogHelper.relaod();
	}
}
