 package net.sourceforge.ganttproject.task.algorithm;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.calendar.GPCalendar;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskNode;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;

/**
 * Algorithm to retrieve critical tasks. This algorithm starts from the last
 * tasks (the last to end) and go back to check if predecessors end is the same
 * as its starts. It is recursive.
 * 
 * @author bbaranne
 */
public class CriticalPathAlgorithmImpl implements CriticalPathAlgorithm {
    TaskNode root = null;

    private GPCalendar calendar = null;

    public CriticalPathAlgorithmImpl(TaskNode root, GPCalendar cal) {
        this.root = root;
        this.calendar = cal;
    }

    /**
     * Returns a list of the last-ended tasks.
     * 
     * @return A list of the last-ended tasks.
     */
    private List getLastTasks() {
        List res = new ArrayList();
        Enumeration en = this.root.preorderEnumeration();
        GanttCalendar end = null;
        while (en.hasMoreElements()) {
            Object next = en.nextElement(); 
            if(!(next instanceof TaskNode))
                continue;
            TaskNode tn = (TaskNode) next;
            if (tn.isRoot())
                continue;
            GanttCalendar tnEnd = tn.getEnd();
            if (end == null || end.before(tnEnd)) {
                res.clear();
                end = tnEnd.Clone();
                res.add(tn.getUserObject());
            } else if (end.compareTo(tnEnd) == 0)
                res.add(tn.getUserObject());
        }
        return res;
    }

    /**
     * Returns an array containing the critical tasks.
     * 
     * @return An array containing the critical tasks.
     */
    public Task[] getCriticalTasks() {
//        System.out.println("getCriticalTasks");
        List lastTasks = getLastTasks();
        List res = new ArrayList(lastTasks);
//        System.out.println("lastTasks " + lastTasks);;
        Iterator it = lastTasks.iterator();
        while (it.hasNext()) {
            Task t = (Task) it.next();
            res.add(t);
            checkCriticalTasks(t, res);
        }
        Task[] r = new Task[res.size()];
        res.toArray(r);
        return r;
    }

    /**
     * Recursive method that adds critical task among predecessors of
     * <code>successor</code> in <code>criticalTasksList</code>
     * 
     * @param successor
     *            The start task.
     * @param criticalTasksList
     *            The list in which critical tasks should be added.
     */
    private void checkCriticalTasks(Task successor, List criticalTasksList) {
        TaskDependency[] rel = successor.getDependenciesAsDependant().toArray();
        for (int i = 0; i < rel.length; i++) {
            Task t = rel[i].getDependee();
            if (t.getEnd().compareTo(successor.getStart()) == 0
                    || isIntervalNonWorking(t.getEnd(), successor.getStart(),
                            calendar)) {
                criticalTasksList.add(t);
                checkCriticalTasks(t, criticalTasksList);
//                System.out.println("critical " + t);
            }
//            else
//                System.out.println("non critical " + t);
        }
    }

    private static boolean isIntervalNonWorking(GanttCalendar d1,
            GanttCalendar d2, GPCalendar cal) {
        while (d1.before(d2)
                && cal.getWeekDayType(d1.getDayWeek()) != GPCalendar.DayType.WORKING)
            d1.add(1);
        return d1.equals(d2);
    }
}
