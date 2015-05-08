/*
 * @(#)ScheduleReportSearchCondition.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.schedule;

import java.util.Map;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;

/**
 * 描述:进度报告搜索条件对象
 * @author adward_huang  date:2012-9-25
 * @version EAS6.1
 */
public class ScheduleReportSearchCondition {
	
	// 工程ID
	private String projectId;
	
	// 工程名称
	private String userId;
	
	// 开始日期
	private String startDate;
	
	// 结束日期
	private String endDate;
	
	// 搜索数据
	private Map<String, Map<String, String>> dataMap;
	
	// 选择的树节点
	private DefaultKingdeeTreeNode selectNode;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public DefaultKingdeeTreeNode getSelectNode() {
		return selectNode;
	}

	public void setSelectNode(DefaultKingdeeTreeNode selectNode) {
		this.selectNode = selectNode;
	}

	public Map<String, Map<String, String>> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Map<String, String>> dataMap) {
		this.dataMap = dataMap;
	}

}
