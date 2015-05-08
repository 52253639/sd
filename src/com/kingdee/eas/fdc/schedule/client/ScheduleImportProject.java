/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;

import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCSCHUtils;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchCalendar;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchTaskCreator;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchTaskPredecessorCreator;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTask;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTaskPredecessor;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述： 导入project
 * 
 * @author 杜红明
 * @version EAS7.0
 * @createDate 2011-9-23
 * @see
 */

public class ScheduleImportProject {
	
	private FDCScheduleBaseEditUI editUI;
	private FDCScheduleInfo editData;
	private CoreBaseCollection wbsCoreBaseCollection;
	
	public ScheduleImportProject(FDCScheduleInfo editData, FDCScheduleBaseEditUI editUI) {
		this.editData = editData;
		this.editUI = editUI;
	}

	public void execude() throws Exception {
		/* 存储当前任务 */
		CoreBaseCollection currentAllTaskCollection = new CoreBaseCollection();
		CoreBaseCollection currentWbsCollection = new CoreBaseCollection();
		CoreBaseCollection currentDependTaskCollection = new CoreBaseCollection();
		for (int k = 0; k < this.editData.getTaskEntrys().size(); k++) {
			FDCScheduleTaskInfo fDCScheduleTaskInfo = this.editData.getTaskEntrys().get(k);
			currentAllTaskCollection.add(fDCScheduleTaskInfo);
			if (null != fDCScheduleTaskInfo.getWbs()) {
				currentWbsCollection.add(fDCScheduleTaskInfo.getWbs());
			}
			FDCScheduleTaskDependCollection fDCScheduleTaskDependCollection = fDCScheduleTaskInfo.getDependEntrys();
			for (int m = 0; m < fDCScheduleTaskDependCollection.size(); m++) {
				currentDependTaskCollection.add(fDCScheduleTaskDependCollection.get(m));
			}
		}

		if (this.editData.getTaskEntrys().size() > 0) {
			int result = FDCMsgBox.showConfirm2("此操作将删除现有任务，是否继续");
			/* result==0 删除并新增任务 result==2 不执行导入操作 */
			if (result == 2) {
				return;
			}
		}

		/*
		 * 执行导入project操作
		 */
		JFileChooser chooser = new JFileChooser(new File("c:\\"));
		/* 设置过虑器 */
		chooser.setFileFilter(new FilterType());
		/* 弹出对话框 */
		chooser.showDialog(null, "导入Project");
		/* 判断是否选中文件 */
		if (chooser.getSelectedFile() == null) {
			return;
		}
		/* 获得选取文件路径 */
		String filePath = chooser.getSelectedFile().getPath();
		File file = new File(filePath);

		/* 清空表格数据 */
		int entryCount = this.editData.getTaskEntrys().size();
		for (int k = 0; k < entryCount; k++) {
			this.editData.getTaskEntrys().remove(this.editData.getTaskEntrys().get(0));
		}

		/* 取数据 */
		List projectList = RESchMSProjectReader.pasreMSProject(file,
				new IRESchTaskCreator() {
					public IRESchTask createSchTask() {
						return (IRESchTask) new FDCScheduleTaskInfo();
					}
				}, new IRESchTaskPredecessorCreator() {
					public IRESchTaskPredecessor createSchTaskPredecessor() {
						return new FDCScheduleTaskDependInfo();
					}
				}, new IRESchCalendar() {
					public ScheduleCalendarInfo getSchedule() {
						return editData.getCalendar();
					}
				});

		CoreBaseCollection newDependTaskCollection = new CoreBaseCollection();
		/* 存入以本级任务id为键，本级任务为值。以便后面构建层次结构 */
		Map projectMap = new HashMap();
		for (int k = 0; k < projectList.size(); k++) {
			if (null != projectList.get(k)) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) projectList.get(k);
				if (null != fDCScheduleTaskInfo.getMsProjectId()) {
					projectMap.put(fDCScheduleTaskInfo.getMsProjectId().toString(), fDCScheduleTaskInfo);
				}
				/* 处理后置任务 */
				FDCScheduleTaskDependCollection fDCScheduleTaskDependCollection = fDCScheduleTaskInfo.getDependEntrys();
				for (int j = 0; j < fDCScheduleTaskDependCollection.size(); j++) {
					FDCScheduleTaskDependInfo fDCScheduleTaskDependInfo = fDCScheduleTaskDependCollection.get(j);
					fDCScheduleTaskDependInfo.setId(BOSUuid.create((new FDCScheduleTaskDependInfo()).getBOSType()));
					newDependTaskCollection.add(fDCScheduleTaskDependInfo);
				}
			}
		}

		wbsCoreBaseCollection = new CoreBaseCollection();
		/* 存入模板任务数据，并设置层次结构 */
		CoreBaseCollection newTaskCollection = new CoreBaseCollection();
		for (int k = 0; k < projectList.size(); k++) {
			if (null != projectList.get(k)) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) projectList.get(k);
				Object projectObj = projectMap.get(String.valueOf(null == fDCScheduleTaskInfo.getMsParentPrjId() ? "" : fDCScheduleTaskInfo
						.getMsParentPrjId()));
				if (null != projectObj && !"".equals(projectObj.toString().trim())) {
					if (projectObj instanceof FDCScheduleTaskInfo) {
						fDCScheduleTaskInfo.setParent((FDCScheduleTaskInfo) projectObj);
					}
				}
				/* 创建WBS */
				fDCScheduleTaskInfo.setWbs(createWBS(fDCScheduleTaskInfo, fDCScheduleTaskInfo.getParent()));
				/* 设置有效工期 */
				BigDecimal defaultDuration = FDCSCHUtils.getDefaultDuration();
				BigDecimal effDay = BigDecimal.valueOf(com.kingdee.eas.fdc.schedule.framework.DateUtils.diffdates(fDCScheduleTaskInfo.getPlanStart(),
						fDCScheduleTaskInfo.getPlanEnd()));
				fDCScheduleTaskInfo.setDuration(effDay.intValue());
				fDCScheduleTaskInfo.setEffectTimes(effDay);
				fDCScheduleTaskInfo.setSchedule(this.editData);
				fDCScheduleTaskInfo.setCalendar(this.editData.getCalendar());
				fDCScheduleTaskInfo.setNatureTimes(BigDecimal.valueOf(0));
				fDCScheduleTaskInfo.setIsScheduleTask(true);
				fDCScheduleTaskInfo.setPriority(1);
				fDCScheduleTaskInfo.setIsAdd(true);
				fDCScheduleTaskInfo.setStart(fDCScheduleTaskInfo.getPlanStart());
				fDCScheduleTaskInfo.setEnd(fDCScheduleTaskInfo.getPlanEnd());
				if (fDCScheduleTaskInfo.getParent() != null) {
					fDCScheduleTaskInfo.setLongNumber(fDCScheduleTaskInfo.getParent().getLongNumber() + "!" + fDCScheduleTaskInfo.getNumber());
					fDCScheduleTaskInfo.setLevel(fDCScheduleTaskInfo.getLevel() + 1);
				} else {
					fDCScheduleTaskInfo.setLongNumber(fDCScheduleTaskInfo.getNumber());
					fDCScheduleTaskInfo.setLevel(0);
				}
				if (null != fDCScheduleTaskInfo.getWbs()) {
					fDCScheduleTaskInfo.setLocked(fDCScheduleTaskInfo.getWbs().isIsLocked());
				}
				newTaskCollection.add((CoreBaseInfo) fDCScheduleTaskInfo);
				this.editData.getTaskEntrys().add(fDCScheduleTaskInfo);
				// RESchTemplateTaskFactory.getRemoteInstance().addnew(
				// schTemplateTaskInfo);
			}
		}

		// runSave();

		// FDCScheduleTaskFactory.getRemoteInstance().importTasks(
		// currentAllTaskCollection, newTaskCollection, currentWbsCollection,
		// wbsCoreBaseCollection, currentDependTaskCollection,
		// newDependTaskCollection);
		/* 刷新表 */
		/* 重新加载数据 */
		try {
			// editUI.setDataObject(this.editData);
			// editUI.load2GanttAfter(info)
			// this.setInitEnd(false);
			// editUI.setMessageText("有" + newTaskCollection.size() +
			// "条数据被成功导入！");
			// editUI.showMessage();
			// editUI.weakOnShow();
		} catch (Exception ex) {
		}
	}

	// 创建WBS
	private FDCWBSInfo createWBS(FDCScheduleTaskInfo task, FDCScheduleTaskInfo parentTask) {
		if (null == parentTask) {
			parentTask = new FDCScheduleTaskInfo();
		}
		FDCWBSInfo wbs = new FDCWBSInfo();
		wbs.setId(BOSUuid.create(wbs.getBOSType()));
		wbs.setParent(parentTask.getWbs());
		wbs.setIsEnabled(true);
		wbs.setBoolean("isNew", true);
		wbs.setIsFromTemplate(false);
		wbs.setCurProject(this.editData.getProject());
		wbs.setName(task.getName());
		wbs.setTaskType(this.editData.getScheduleType());
		if (editUI.isSpecialSchedule()) {
			// 专项则继承已选节点的责任人和责任部门
			if (null != parentTask.getWbs()) {
				wbs.setAdminPerson(parentTask.getWbs().getAdminPerson());
				wbs.setRespDept(parentTask.getWbs().getRespDept());
			}
		} else {
			// 主项则设置计划部门为登陆用户的成本中心
			wbs.setAdminDept(SysContext.getSysContext().getCurrentCostUnit().castToFullOrgUnitInfo());
		}
		wbs.setIsUnVisible(false);
		BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(task.getStart(), task.getEnd(), this.editData.getCalendar());
		wbs.setEstimateDays(effectTimes.intValue());
		wbsCoreBaseCollection.add(wbs);
		return wbs;
	}
}
