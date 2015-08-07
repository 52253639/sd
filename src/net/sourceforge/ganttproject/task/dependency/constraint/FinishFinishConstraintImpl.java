package net.sourceforge.ganttproject.task.dependency.constraint;

import java.util.Date;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttTaskRelationship;
import net.sourceforge.ganttproject.calendar.ScheduleCalendarImpl;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskActivity;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyConstraint;
import net.sourceforge.ganttproject.task.dependency.TaskDependency.ActivityBinding;

import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

/**
 * Dependant task finishes not earlier than dependee finishes Created by
 * IntelliJ IDEA. User: bard
 */
public class FinishFinishConstraintImpl extends ConstraintImpl implements
		TaskDependencyConstraint {

	public FinishFinishConstraintImpl() {
		super(GanttTaskRelationship.FF, GanttLanguage.getInstance().getText(
				"finishfinish"));
	}

	public String getSimpleName() {
		return "FF";
	}

	public TaskDependencyConstraint.Collision getCollision() {
		TaskDependencyConstraint.Collision result = null;
		Task dependee = getDependency().getDependee();
		Task dependant = getDependency().getDependant();
		GanttCalendar dependeeEnd = dependee.getEnd();
		GanttCalendar dependantEnd = dependant.getEnd();
		//

		int difference = getDependency().getDifference();
		GanttCalendar comparisonDate = dependeeEnd.Clone();
		
		comparisonDate.add(difference);
		// 不清楚之前啥逻辑， 貌似一天或负一天才返回false。此处直接设置为true
		// boolean isActive =
		// getDependency().getHardness()==TaskDependency.Hardness.RUBBER ?
		// dependantEnd
		// .compareTo(comparisonDate) < 0 : dependantEnd
		// .compareTo(comparisonDate) != 0;
		boolean isActive = true;

		GanttCalendar acceptableStart = dependee.getEnd().Clone();// 此处为GanttProject
		// bug。
		// 应该是前置任务的结束时间

		addDelay(acceptableStart);
		result = new TaskDependencyConstraint.DefaultCollision(acceptableStart,
				TaskDependencyConstraint.Collision.START_LATER_VARIATION,
				isActive);

		return result;
	}

	public ActivityBinding getActivityBinding() {
		TaskActivity[] dependantActivities = getDependency().getDependant()
				.getActivities();
		TaskActivity[] dependeeActivities = getDependency().getDependee()
				.getActivities();
		if (dependantActivities.length < 1 || dependeeActivities.length < 1) {
			return null;
		}
		TaskActivity theDependant = dependantActivities[dependantActivities.length - 1];
		TaskActivity theDependee = dependeeActivities[dependeeActivities.length - 1];
		return new DependencyActivityBindingImpl(theDependant, theDependee,
				new Date[] { theDependant.getEnd(), theDependee.getEnd() });
	}

	public void addDelay(GanttCalendar calendar) {
		int difference = getDependency().getDifference();
		ScheduleCalendarInfo cal = ((FDCScheduleTaskInfo) ((KDTask) getDependency().getDependee()).getScheduleTaskInfo()).getCalendar();
		ScheduleCalendarImpl myCal = new ScheduleCalendarImpl(cal);
		
		GanttCalendar solutionStart = calendar.Clone();
		calendar.add(difference);
		for (int i = 0; i <= difference; i++) {
			if (myCal.isNonWorkingDay(solutionStart.getTime())) {
				calendar.add(1);
				difference++;
			}
			solutionStart.add(1);
		}
		
		/* modified by zhoqin for R140605-0229 on 2014/06/06 start */
		for (int i = 0; i > difference; i--) {
			if (myCal.isNonWorkingDay(solutionStart.getTime())) {
				calendar.add(-1);
				difference--;
			}
			solutionStart.add(-1);
		}
		/* modified by zhoqin for R140605-0229 on 2014/06/06 end */
		
		// 再通过结束时间和工期反算开始时间
		int effectTimes = (int) getDependency().getDependant().getDuration()
				.getLength();
		for (int i = 0; i < effectTimes; i++) {
			if (myCal.isNonWorkingDay(calendar.getTime())) {
				solutionStart.add(-1);
				effectTimes++;
			}
			calendar.add(-1);
		}
		calendar.add(1);
	}
}
