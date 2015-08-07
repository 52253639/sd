package net.sourceforge.ganttproject.task.dependency.constraint;

import java.util.Date;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttTaskRelationship;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskActivity;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyConstraint;
import net.sourceforge.ganttproject.task.dependency.TaskDependency.ActivityBinding;

/**
 * Dependant task starts not earlier than dependee starts Created by IntelliJ
 * IDEA. User: bard
 */
public class StartStartConstraintImpl extends ConstraintImpl implements
		TaskDependencyConstraint {

	public StartStartConstraintImpl() {
		super(GanttTaskRelationship.SS, GanttLanguage.getInstance().getText(
				"startstart"));
	}

	public String getSimpleName() {
		return "SS";
	}

	public TaskDependencyConstraint.Collision getCollision() {
		TaskDependencyConstraint.Collision result = null;
		Task dependee = getDependency().getDependee();
		Task dependant = getDependency().getDependant();
		GanttCalendar dependeeStart = dependee.getStart();
		GanttCalendar dependantStart = dependant.getStart();
		//
		int difference = getDependency().getDifference();
		GanttCalendar comparisonDate = dependeeStart.Clone();
		comparisonDate.add(difference);

		// boolean isActive = getDependency().getHardness() ==
		// TaskDependency.Hardness.RUBBER ? dependantStart
		// .compareTo(comparisonDate) < 0
		// : dependantStart.compareTo(comparisonDate) != 0;
		boolean isActive = true;
		// GanttCalendar acceptableStart = dependee.getStart();
		GanttCalendar acceptableStart = dependee.getStart().Clone();
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
		if (dependantActivities.length > 0 && dependeeActivities.length > 0) {
			TaskActivity theDependant = dependantActivities[0];
			TaskActivity theDependee = dependeeActivities[0];
			return new DependencyActivityBindingImpl(theDependant, theDependee,
					new Date[] { theDependant.getStart(),
							theDependee.getStart() });
		} else {
			return null;
		}
	}

}
