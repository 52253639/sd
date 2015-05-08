/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.ganttproject.task.dependency.TaskDependency;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeCollection;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @author		杜红明
 * @version		EAS7.0		
 * @createDate	2011-9-23 
 * @see						
 */

public class FDCScheduleBaseHelper {
	private static final Logger logger = CoreUIObject.getLogger(FDCScheduleBaseEditUI.class);
	private FDCScheduleInfo editData;

	private FDCScheduleBaseEditUI editUI;
	
	public FDCScheduleBaseHelper(FDCScheduleInfo editData, FDCScheduleBaseEditUI editUI) {
		this.editData = editData;
		this.editUI = editUI;
	}
	public FDCScheduleInfo getSaveSchedule() {
		logger.info("getSaveSchedule begin...");
		// 克隆以保证不对业务数据有影响
		FDCScheduleInfo info = (FDCScheduleInfo) editData.clone();
		/** 放入当前的工程项目 **/
		info.setProject((CurProjectInfo) editUI.prmtCurproject.getValue());
		/** 处理本任务的前置任务,其他任务关联到本任务的要保存 **/
		FDCScheduleTaskDependCollection prefixDepends = new FDCScheduleTaskDependCollection();
		/** 清楚所有的任务 **/
		info.getTaskEntrys().clear();
		info.put("dependBaseKeyMap", null);// dependBaseKeyMap持有对FDCScheduleTaskInfo的引用
		/** 从甘特图中获得一个排序的任务列表 **/
		List tasks = editUI.getScheduleGanttProject().getSortKDTask();
		/** 重新设置甘特图任务列表的节点信息 **/
		setIsLeaf(tasks);
		/** 循环整理好的甘特图任务列表 **/
		copyTaskPerperties(info, prefixDepends, tasks);
		/*** 该计划的开始结束时间 **/
		Date prjStartDate = info.getStartDate();
		Date prjEndDate = info.getEndDate();
		
		if (prefixDepends.size() > 0) {
			info.put("prefixDepends", prefixDepends);
		}
		if (prjStartDate != null && prjEndDate != null) {
			BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(info.getStartDate(), info.getEndDate(), this.editData.getCalendar());
			BigDecimal natureTimes = ScheduleCalendarHelper.getNatureTimes(info.getStartDate(), info.getEndDate());
			info.setEffectTimes(effectTimes);
			info.setNatureTimes(natureTimes);
			info.setStartDate(info.getStartDate());
			info.setEndDate(info.getEndDate());
		}
		// TODO version
		if (info.getVersion() > 1f) {
			info.setVersion(editData.getVersion());
		} else {
			info.setVersion(1.0f);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			if (editData.getProject() != null
					&& editData.getProjectSpecial() != null) {
				info.setVersionName(info.getProject().getName()
						+ editData.getProjectSpecial()+ "版" + new Float(info.getVersion()) + "_"  + sdf.format(new Date()) );
			}
			if (editData.getProject() != null
					&& editData.getProjectSpecial() == null) {
				String version = "执行版";
				String stage = info.getScheduleStage()!= null?info.getScheduleStage().getName():"";
				info.setVersionName(info.getProject().getName()+stage
						+version+ new Float(info.getVersion())
						+"_"+sdf.format(new Date()));
			}
		}
		// 再次克隆，避免由于精简数据而对显示数据产生影响
		FDCScheduleInfo ret = (FDCScheduleInfo) info.clone();
		ret.reduceData4Transfer2Remote();
		logger.info("getSaveSchedule end... ");
		try {
			ret.put("wbsTree", editUI.getScheduleGanttProject().getWbsTree(false));
		} catch (Exception e) {
			 logger.warn(e);
		}
		// 同步到editData中
		editData.setVersionName(ret.getVersionName());
		return ret;
	}

	/**
	 * 更新所有任务的级次、是否明细 emanon
	 * <p>
	 * 一般用于保存前调用
	 * 
	 * @param tasks
	 */
	// TODO FIXME 设置级次时会报中断
	private void setIsLeaf(List tasks) {
		if (tasks != null) {
			Map key = new HashMap();
			for (int i = 0; i < tasks.size(); i++) {
				KDTask task = (KDTask) tasks.get(i);
				// if (!task.isScheduleTask()) {
				// continue;
				// }
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) task
						.getScheduleTaskInfo();
				key.put(taskInfo.getSrcID(), new Integer(i));
				taskInfo.setIsLeaf(true);
				if (taskInfo.getParent() == null) {
					taskInfo.setLevel(0);
				} else {
					Integer index = (Integer) key.get(taskInfo.getParent()
							.getSrcID());
					 if (index != null) {
						KDTask parentTask = (KDTask) tasks.get(index.intValue());
						FDCScheduleTaskInfo parentInfo = (FDCScheduleTaskInfo) parentTask.getScheduleTaskInfo();
						parentInfo.setIsLeaf(false);
						taskInfo.setLevel(parentInfo.getLevel() + 1);
					 }
				}
			}
		}
	}

	private void copyTaskPerperties(FDCScheduleInfo info, FDCScheduleTaskDependCollection prefixDepends, List tasks) {
		for (int i = 0; i < tasks.size(); i++) {
			KDTask task = (KDTask) tasks.get(i);
			/** 从甘特图任务中获得进度任务，强转为房地产进度任务 **/
			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) task.getScheduleTaskInfo();
			/** 从当前进度计划中获得版本信息,如果本版为1说明为新增的进度任务,那么让进度任务保存当前源ID **/
			if (editData.getVersion() == 1f) {
				taskInfo.setSrcID(taskInfo.getId().toString());
			}
			/** 在进度任务中放入任务名称 **/
			taskInfo.put("name_l2", taskInfo.getName());
			/** 对任务日历为NULL的处理 **/
			if (FDCHelper.isEmpty(taskInfo.getCalendar())) {
				taskInfo.setCalendar(info.getCalendar());
			}
			/** 把整理好的任务列表放入进度中 **/
			info.getTaskEntrys().add(taskInfo);

			/**
			 * 如果进度计划的开始时间为NULL,获得任务的开始赋予进度计划开始时间,如果不为null且大于任务开始结束时间,
			 * 那么获得任务时间赋予计划开始时间
			 **/
			info.setStartDate(getScheduleStartDate(info.getStartDate(), taskInfo));
			info.setEndDate(getScheduleEndDate(info.getEndDate(), taskInfo));
			// 生成前置任务信息
			/** 清空任务的前置任务列表 **/
			// taskInfo.getDependEntrys().clear();
			/** 从根据任务ID从甘特图获取甘特图任务 **/
			KDTask myTask = editUI.getScheduleGanttProject().getKDTaskById(taskInfo.getId().toString());
			/*** 从甘特图任务中获取前置任务列表 **/
			if(myTask == null)
				continue;
			 TaskDependency[] depend = myTask.getDependencies().toArray();
			// // TaskDependency[] depend =
			// // myTask.getDependenciesAsDependee().toArray(); // TODO
			// // System.out.println(depend.length);
			// // H
			// // 取后置节点
			// // 这里可能会有差异
			// /*** 循环甘特图任务的前置任务列表 **/
			setDepndTasks(taskInfo, depend, myTask);
			// // depend = myTask.getDependenciesAsDependant().toArray();
			setDependTasks(prefixDepends, taskInfo, depend, myTask);
			
			for(int j=0;j<taskInfo.getHelpPersonEntry().size();j++){
				taskInfo.getHelpPersonEntry().get(j).setId(BOSUuid.create(taskInfo.getHelpPersonEntry().get(j).getBOSType()));
			}
			for(int j=0;j<taskInfo.getHelpDeptEntry().size();j++){
				taskInfo.getHelpDeptEntry().get(j).setId(BOSUuid.create(taskInfo.getHelpDeptEntry().get(j).getBOSType()));
			}
		}
	}

	private Date getScheduleEndDate(Date prjEndDate, FDCScheduleTaskInfo taskInfo) {
		if (prjEndDate == null) {
			prjEndDate = taskInfo.getEnd();
		} else {
			if (null != taskInfo.getEnd() && prjEndDate.compareTo(taskInfo.getEnd()) < 0) {
				prjEndDate = taskInfo.getEnd();
			}
		}
		return prjEndDate;
	}

	private Date getScheduleStartDate(Date prjStartDate, FDCScheduleTaskInfo taskInfo) {
		if (prjStartDate == null) {
			prjStartDate = taskInfo.getStart();
		} else {
			if (null != taskInfo.getStart() && prjStartDate.compareTo(taskInfo.getStart()) > 0) {
				prjStartDate = taskInfo.getStart();
			}
		}
		return prjStartDate;
	}

	private void setDepndTasks(FDCScheduleTaskInfo taskInfo, TaskDependency[] depend, KDTask task) {
		taskInfo.getDependEntrys().clear();
		for (int j = 0; j < depend.length; j++) {
			/** 创建进度前置任务让他等于甘特图前置任务 **/
			FDCScheduleTaskDependInfo item = new FDCScheduleTaskDependInfo();
			TaskDependency taskDependency = depend[j];
			if (taskDependency.getDependee().getTaskID() != task.getTaskID()) {
				continue;
			}
			/** 设置前置任务其他信息 **/
			item.setId(BOSUuid.create(item.getBOSType()));
			item.setTask(taskInfo);
			item.setDependTask((FDCScheduleTaskInfo) ((KDTask) taskDependency.getDependant()).getScheduleTaskInfo());
			item.setType(ScheduleParserHelper.getTaskLinkTypeByConstraint(taskDependency.getConstraint()));
			int difference = taskDependency.getDifference();
			// 前面一个任务完成,此任务开始,时间间隔就是搭建时间
			// if (taskDependency.getConstraint() instanceof
			// FinishStartConstraintImpl) {
			// // 前置时间的结束
			// Date preEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(taskDependency
			// .getDependee().getEnd());
			// // 后置时间的开始
			// Date nextStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate
			// (taskDependency.getDependant().getStart());
			// // 前置时间的结束-后置任务的开始
			// // 如1-1 到1-2，有效工期是2天，但是搭接时间其实是0，所以最后结果-2
			// difference =
			// Integer.parseInt(ScheduleCalendarHelper.getEffectTimes
			// (preEndDate, nextStartDate, taskInfo.getSchedule().getCalendar())
			// .toString()) - 2;
			// }
			// // 前置任务与后置任务同时开始
			// else if (taskDependency.getConstraint() instanceof
			// StartStartConstraintImpl) {
			// Date preStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(
			// taskDependency.getDependee().getStart());
			// Date nextStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate
			// (taskDependency.getDependant().getStart());
			// difference = Integer.parseInt(ScheduleCalendarHelper
			// .getEffectTimes(preStartDate, nextStartDate,
			// taskInfo.getSchedule().getCalendar()).toString()) - 1;
			// }
			// // 前置任务与后置任务同时完成
			// else if (taskDependency.getConstraint() instanceof
			// FinishFinishConstraintImpl) {
			// Date preEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(taskDependency
			// .getDependee().getEnd());
			// Date nextEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(taskDependency
			// .getDependant().getEnd());
			// difference = Integer.parseInt(ScheduleCalendarHelper
			// .getEffectTimes(preEndDate, nextEndDate,
			// taskInfo.getSchedule().getCalendar())
			// .toString());
			// if (difference < 0) {
			// difference += 1;
			// } else {
			// difference -= 1;
			// }
			// }
			item.setDifference(difference);
			item.setHardness(null);
			taskInfo.getDependEntrys().add(item);
		}
	}

	private void setDependTasks(FDCScheduleTaskDependCollection prefixDepends, FDCScheduleTaskInfo taskInfo, TaskDependency[] depend,
			KDTask task) {
		for (int j = 0; j < depend.length; j++) {
			FDCScheduleTaskDependInfo item = new FDCScheduleTaskDependInfo();
			TaskDependency taskDependency = depend[j];
			if (taskDependency.getDependant().getTaskID() != task.getTaskID()) {
				continue;
			}
			item.setId(BOSUuid.create(item.getBOSType()));
			item.setTask((FDCScheduleTaskInfo) ((KDTask) taskDependency.getDependee()).getScheduleTaskInfo());
			item.setDependTask(taskInfo);
			item.setType(ScheduleParserHelper.getTaskLinkTypeByConstraint(taskDependency.getConstraint()));
			int difference = taskDependency.getDifference();
			// if (taskDependency.getConstraint() instanceof
			// FinishStartConstraintImpl) {
			// Date preEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(taskDependency
			// .getDependee().getEnd());
			// Date nextStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate
			// (taskDependency.getDependant().getStart());
			// difference =
			// Integer.parseInt(ScheduleCalendarHelper.getNatureTimes
			// (preEndDate, nextStartDate).toString()) - 1;
			// } else if (taskDependency.getConstraint() instanceof
			// StartStartConstraintImpl) {
			// Date preStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(
			// taskDependency.getDependee().getStart());
			// Date nextStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate
			// (taskDependency.getDependant().getStart());
			// difference =
			// Integer.parseInt(ScheduleCalendarHelper.getNatureTimes
			// (preStartDate, nextStartDate).toString()) - 1;
			// } else if (taskDependency.getConstraint() instanceof
			// FinishFinishConstraintImpl) {
			// Date preEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(taskDependency
			// .getDependee().getEnd());
			// Date nextEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(taskDependency
			// .getDependant().getEnd());
			// difference =
			// Integer.parseInt(ScheduleCalendarHelper.getNatureTimes
			// (preEndDate, nextEndDate).toString()) - 1;
			// }
			item.setDifference(difference);
			// item.setDifference(taskDependency.getDifference());
			// item.setHardness(item);
			prefixDepends.add(item);
		}
	}

	/**
	 * 带入集团管控节点
	 * 
	 * @return
	 */
	public static GlobalTaskNodeCollection getGlobalTaskNode() {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("isBuidingPlan"));
		sic.add(new SelectorItemInfo("isOperationPlan"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("taskType"));
		sic.add(new SelectorItemInfo("achType.id"));
		sic.add(new SelectorItemInfo("achType.name"));
		sic.add(new SelectorItemInfo("achType.number"));
		sic.add(new SelectorItemInfo("bizType.bizType.id"));
		sic.add(new SelectorItemInfo("bizType.bizType.name"));
		sic.add(new SelectorItemInfo("bizType.bizType.number"));

		view.setSelector(sic);

		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("number");
		si.setSortType(SortType.ASCEND);
		sorter.add(si);

		view.setSorter(sorter);    

		GlobalTaskNodeCollection cols = null;
		try {
			cols = GlobalTaskNodeFactory.getRemoteInstance().getGlobalTaskNodeCollection(view);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cols;
	}
	/**
	 * 带入集团管控节点
	 * 
	 * @return
	 */
	public static RESchTemplateTaskCollection getRESchTemplateTask() {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("longNumber");
		sic.add("orgUnit.id");
		sic.add("orgUnit.name");
		sic.add("orgUnit.number");
		sic.add("state");
		sic.add("templateType");
		sic.add("template.id");
		sic.add("template.name");
		sic.add("template.number");
		sic.add("entry.id");
		sic.add("entry.name");
		sic.add("entry.tasktype");
		sic.add("entry.number");
		sic.add("entry.longNumber");
		sic.add("entry.referenceday");
		sic.add("entry.isLeaf");
		sic.add("entry.level");
		sic.add("entry.srcGroupNode");
		sic.add("entry.parent.id");
		sic.add("entry.parent.name");
		sic.add("entry.parent.number");
		sic.add("entry.parent.level");
		sic.add("entry.parent.isLeaf");
		sic.add("entry.standardTask.id");
		sic.add("entry.standardTask.name");
		sic.add("entry.standardTask.number");

		sic.add("entry.achievementType.id");
		sic.add("entry.achievementType.name");
		sic.add("entry.achievementType.number");

		sic.add("entry.businessType.id");
		sic.add("entry.businessType.bizType.id");
		sic.add("entry.businessType.bizType.number");
		sic.add("entry.businessType.bizType.name");

		sic.add("entry.predecessors.id");
		sic.add("entry.predecessors.predecessorTask.id");
		sic.add("entry.predecessors.predecessorTask.name");
		sic.add("entry.predecessors.predecessorTask.number");
		sic.add("entry.predecessors.differenceDay");
		sic.add("entry.predecessors.predecessorType");
		view.setSelector(sic);

		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("number");
		si.setSortType(SortType.ASCEND);
		sorter.add(si);

		view.setSorter(sorter);    

		RESchTemplateTaskCollection cols = null;
		try {
			cols = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cols;
	}
	
	/**
	 * 带入考核节点
	 * 
	 * @return
	 */
	public static FDCScheduleTaskCollection getFDCSchTask() {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("isCheckNode"); //是否考核节点, modified by zhaoqin on 2014/06/20
		sic.add("schedule.id");	
		sic.add("schedule.versionName");	
		sic.add("schedule.isCheckVersion");
		sic.add("schedule.isLatestVer");
		sic.add("schedule.projectSpecial");
		sic.add("schedule.srcID");
		view.setSelector(sic);

		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("number");
		si.setSortType(SortType.ASCEND);
		sorter.add(si);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule.isCheckVersion",1));
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",1));
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",1));
		filter.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial", null));
		filter.getFilterItems().add(new FilterItemInfo("schedule.srcID", null, CompareType.NOTEQUALS));
		
		/* modified by zhaoqin on 2014/06/20 */
		filter.getFilterItems().add(new FilterItemInfo("isCheckNode", 1));
		//filter.setMaskString("(#0 and #1) or #2 and #3 and #4");
		filter.setMaskString("(#0 and #1) or #2 and #3 and #4 and #5");
		
		view.setFilter(filter);	
		view.setSorter(sorter);    

		FDCScheduleTaskCollection cols = null;
		try {
			cols = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cols;
	}
}
