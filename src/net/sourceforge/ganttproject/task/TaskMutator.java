package net.sourceforge.ganttproject.task;

import java.util.Date;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.calendar.ScheduleCalendarImpl;

/**
 * Created by IntelliJ IDEA.
 * 
 * @author bard Date: 27.01.2004
 */
public interface TaskMutator extends MutableTask {
	int READ_UNCOMMITED = 0;

	int READ_COMMITED = 1;

	void setIsolationLevel(int level);

	void commit();

	void shift(float unitCount);

	int getCompletionPercentage();

	void setThird(GanttCalendar third, int thirdDateConstraint);

	long setDateAndCalDur(ScheduleCalendarImpl calendar, Date start, Date end);

}