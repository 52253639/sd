package com.kingdee.eas.fdc.schedule.framework.ext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttTreeTableModel;
import net.sourceforge.ganttproject.Mediator;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskLength;
import net.sourceforge.ganttproject.task.TaskNode;

import com.kingdee.eas.fdc.basedata.FDCHelper;

public class GanttTreeTableModelExt {
	public static String strColWBSNumber = "任务编码";
	public static String strColTaskType = "任务类别";
	public static String strColTaskName = "任务名称";
	public static String strColEffectTimes = "工期(天)";
	public static String strColNatureTimes = "自然工期";
	public static String strColCompletePercent = "完成进度(%)";
	public static String strColPrefixNode = "前置任务";
	public static String strColActualEndDate = "实际完成日期";
	public static String strColPlanDept = "计划编制部门";
	public static String stradminPerson = "责任人";
	public static String strColAdminDept = "责任部门";
	public static String strColBizType = "业务类型";
	public static String strColIsKey = "是否关键任务";
	public static String strColMeeting = "是否里程碑任务";
	public static String strColDescription = "备注";
	public static String strColState = "状态";
	public static String strColCheckDate = "考核日期";
	public static String strColHelpDep = "协助部门";
	public static String strColHelpPerson = "协助人";
	public static String strColPlanEvaPerson = "进度评价人";
	public static String strColQualityEvaPerson = "质量评价人";
	public static String strColTaskGuilde = "任务指引";
	public static String strColAchievementType = "成果类别";
	private final GanttTreeTableModel model;
	// 可编辑列
	private Set canLeafEdit = new HashSet();
	private Set canEdit = new HashSet();

	public GanttTreeTableModelExt(GanttTreeTableModel model) {
		this.model = model;
		
		// 暂时开放（任务名称，工期、计划开始、计划完成、责任人、责任部门）
		// 如果新添加可编辑列，需要初始化对应editor
		canLeafEdit = new HashSet();
		canLeafEdit.add(strColTaskName);
		canLeafEdit.add(strColEffectTimes);
		canLeafEdit.add("计划开始日期");
		canLeafEdit.add("计划完成日期");
		canLeafEdit.add(stradminPerson);
		canLeafEdit.add(strColAdminDept);
		/* modified by zhaoqin for R140424-0128 on 2014/05/30 start */
		//canLeafEdit.add(strColQualityEvaPerson);
		//canLeafEdit.add(strColPlanEvaPerson);
		canEdit = new HashSet();
		canEdit.add(strColTaskName);
		canEdit.add(stradminPerson);
		canEdit.add(strColAdminDept);
		//canEdit.add(strColQualityEvaPerson);
		//canEdit.add(strColPlanEvaPerson);
		/* modified by zhaoqin for R140424-0128 on 2014/05/30 end */
	}

	public void afterChangeLanguage(GanttLanguage ganttLanguage) {
		// 修改表头名称将 名称改成 “WBS编码” 原因是目前只有名称是可以树形展示的 by sxhong 2009-09-17 14:45:30
		GanttTreeTableModel.strColName = strColWBSNumber;
		GanttTreeTableModel.strColBegDate = "计划开始日期";
		GanttTreeTableModel.strColEndDate = "计划完成日期";
		// GanttTreeTableModel.strColDuration="有效工期2";
	}

	/**
	 * KDTask中某个单元格是否可编辑
	 * <p>
	 * 目前默认‘任务名称’、‘工期’、‘开始’、‘结束’、‘责任人’、‘责任部门’可编辑<br>
	 * 当非明细任务时，工期、开始、结束不可编辑<br>
	 * 
	 * @param task
	 * @param column
	 * @return
	 */
	public boolean isKDTaskCellEditable(KDTask task, int column) {
		KDTask kdTask = task;
		if (kdTask.isEditable()) {
			String columnName = model.getColumnName(column);
			if (kdTask.getScheduleTaskInfo().isIsLeaf()) {
				if (canLeafEdit.contains(columnName)) {
					return true;
				}
				return false;
			} else {
				if (canEdit.contains(columnName)) {
					return true;
				}
				return false;
			}
		}
		return false;
	}

	public boolean beforeSetValue(final Object value, final Object node, final int column) {
		if (((TaskNode) node).getUserObject() instanceof KDTask) {
			KDTask task = (KDTask) ((TaskNode) node).getUserObject();
			// ScheduleTaskChangeHelper.handleAfterTaskChange(task);//使用新的控制策略
			// KDTask.isadjustable
			String columnName = model.getColumnName(column);
			if (columnName != null && columnName.equals(GanttTreeTableModelExt.strColEffectTimes)) {
				// 触发任务的变动
				BigDecimal effectTimes = (BigDecimal) value;
				if (effectTimes != null) {
					// 持续=有效工期-1
					// 持续的索引号是6
					if (effectTimes.compareTo(FDCHelper.ONE) < 0) {
						return false;
					}
					int duration = effectTimes.intValue() - KDGPConstants.EFFECTTIMESBALANCE;
					task.setLength(duration);
					// ScheduleCalendarInfo calendar =
					// ((FDCScheduleInfo)task.getScheduleTaskInfo
					// ().getScheduleBase()).getCalendar();
					//task.setEnd(ScheduleParserHelper.parseDateToGanttCalendar(
					// ScheduleCalendarHelper
					// .getEndDate(task.getScheduleTaskInfo().getStart(),
					// effectTimes,calendar)));
					return false;
				}
			}
		}

		return true;
	}

	public void afterSetValue(final Object value, final Object node, final int column) {
		

		Mediator.getGanttProjectSingleton().repaint();

	}

	static class ChangeValues {
		static Map innerMap = new HashMap();

		static void addChangeValues(KDTask task) {
			Object[] values = new Object[] { task.getStart(), task.getEnd(), task.getDuration() };
			innerMap.put(getTaskKey(task), values);
		}

		static GanttCalendar getStartDate(KDTask task) {
			Object[] values = getValues(task);
			if (values != null) {
				return (GanttCalendar) values[0];
			}
			return null;
		}

		static GanttCalendar getEndDate(KDTask task) {
			Object[] values = getValues(task);
			if (values != null) {
				return (GanttCalendar) values[1];
			}
			return null;
		}

		static TaskLength getDuration(KDTask task) {
			Object[] values = getValues(task);
			if (values != null) {
				return (TaskLength) values[2];
			}
			return null;
		}

		static Object[] getValues(KDTask task) {
			return (Object[]) innerMap.get(getTaskKey(task));
		}

		static String getTaskKey(KDTask task) {
			return String.valueOf(task.getTaskID());
		}
	}

	public Object getValueAt(Object node, int column) {
		Object res = null;
		if (!(node instanceof TaskNode))
			return null;
		TaskNode tn = (TaskNode) node;
		Task t = (Task) tn.getUserObject();
		if (!(t instanceof KDTask)) {
			return null;
		}
		KDTask task = (KDTask) t;
		if (task.isScheduleTask()) {
			return null;
		}
		// if(tn.getParent()!=null){
		switch (column) {
		case 4:
			res = task.getMyOldStartDate();
			break;
		case 5:
			res = task.getMyOldEndDate();
			break;
		case 6:
			res = task.getMyOldDuration();
			break;
		default:
			String colName = model.getColumnName(column);
			if (colName == null) {
				return null;
			}
			if (colName.equals(GanttTreeTableModelExt.strColNatureTimes)) {
				res = task.get("myOldNatureTimes");
			} else if (colName.equals(GanttTreeTableModelExt.strColEffectTimes)) {
				res = task.get("myOldEffectTimes");
			}
			break;
		}
		return res;
	}
}
