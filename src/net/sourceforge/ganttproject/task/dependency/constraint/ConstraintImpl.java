package net.sourceforge.ganttproject.task.dependency.constraint;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;

/**
 * Created by IntelliJ IDEA. User: bard
 */
public class ConstraintImpl implements Cloneable{
    private final int myID;

    private final String myName;

    private TaskDependency myDependency;

    public ConstraintImpl(int myID, String myName) {
        this.myID = myID;
        this.myName = myName;
    }

    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    protected TaskDependency getDependency() {
        return myDependency;
    }

    public void setTaskDependency(TaskDependency dependency) {
        myDependency = dependency;
    }

    public String getName() {
        return myName;
    }

    public int getID() {
        return myID;
    }

    public String toString() {
        return getName();
    }

    public void addDelay(GanttCalendar calendar) {
        int difference = myDependency.getDifference();
        GanttCalendar solutionStart = calendar.Clone();
        calendar.add(difference);
        for (int i = 0; i <= difference; i++) {
            if ((myDependency.getDependant()
                    .getManager().getCalendar()).isNonWorkingDay(solutionStart
                    .getTime())) {
                calendar.add(1);
                difference++;
            }
            solutionStart.add(1);
        }
    }
}
