/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述：
 * 
 * @author 杜红明
 * @version EAS7.0
 * @createDate 2011-8-19
 * @see
 */

public class TaskExtProPanelNewHelper implements ITaskPanelHelper {

	private FDCScheduleTaskPropertiesNewUI taskProperties;
	private boolean isFromExecUI = false;
	private final KDTask selectTask;

	public TaskExtProPanelNewHelper(FDCScheduleTaskPropertiesNewUI taskProperties) {
		this.taskProperties = taskProperties;
		selectTask = taskProperties.getSelectedTask();
	}

	public void load(boolean isExecuting) throws EASBizException, BOSException {
		this.isFromExecUI = isExecuting;
	}

	/**
	 * 构造计划编制时的扩展属性
	 * 
	 * @return
	 */
	private Map storeComplieProp() {
		Map map = new HashMap();
		return map;
	}

	/**
	 * 构造计划执行时的扩展属性
	 * 
	 * @return
	 */
	private Map storeExecProp() {
		Map map = new HashMap();
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
		// filter.getFilterItems().add(new FilterItemInfo("wbs.id",
		// getWBS().getId().toString()));
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
			taskProperties.handUIException(e);
		}
	}

	public FDCWBSInfo getWBS() {
		return ((FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo()).getWbs();
	}

	public Map getExtProperties() {
		return new HashMap();
	}

	/**
	 * 设置编辑状态,执行
	 */
	public void setEditUIStatus() {

	}

	/**
	 * 设置执行状态的执行状态
	 */
	public void setExecutingUIStatus() {

	}

	/**
	 * 设置查看状态
	 */
	public void setViewUIStatus() {

	}

	public void load() throws EASBizException, BOSException {
		load(taskProperties.isExecuting());
	}

	public void loadMaterial() throws BOSException {
	}

	public void loadTaskLog() throws BOSException {
	}
}
