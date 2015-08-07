package net.sourceforge.ganttproject.task;

import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;

/**
 * Class with which one can get any properties (even custom) from any task.
 * 
 * @author bbaranne
 * 
 */
public class TaskProperties {

    public static final String ID_TASK_DATES = "taskDates";

    public static final String ID_TASK_NAME = "name";

    public static final String ID_TASK_LENGTH = "length";

    public static final String ID_TASK_ADVANCEMENT = "advancement";

    public static final String ID_TASK_COORDINATOR = "coordinator";

    public static final String ID_TASK_RESOURCES = "resources";

    public static final String ID_TASK_ID = "id";

    public static final String ID_TASK_PREDECESSORS = "predecessors";
    public static final String ID_TASK_WBS = "wbs";

    /**
     * Returns the task property specified by <code>propertyID</code>.
     * 
     * @param task
     *            The task from which we want the property.
     * @param propertyID
     *            The property ID which could be <code>ID_TASK_DATES</code>,
     *            <code>ID_TASK_NAME</code>, ... or a custom column name.
     * @return the task property specified by <code>propertyID</code>. The
     *         result may be <code>null</code>.
     */
    public static Object getProperty(Task task, String propertyID) {
        Object res = null;
        StringBuffer sb = new StringBuffer();
        if (propertyID != null) {
            if (propertyID.equals(ID_TASK_DATES)) {
                sb.append(" [ ");
                sb.append(task.getStart() + " - " + task.getEnd());
                sb.append(" ] ");
                res = sb.toString();
            } else if (propertyID.equals(ID_TASK_WBS)) {//wbs
                sb.append(" " + task.getName() + " ");
                res = sb.toString();
            }else if (propertyID.equals(ID_TASK_NAME)) { //name
                sb.append(" " + task.getCustomValues().getValue(GanttTreeTableModelExt.strColTaskName) + " ");
                res = sb.toString();
            } else if (propertyID.equals(ID_TASK_LENGTH)) {//改成有效工期
                sb.append(" [ ");
                sb.append(((int) task.getDuration().getLength()) + " 天 "
                        //+ GanttLanguage.getInstance().getText("days")
                		);
                sb.append(" ] ");
                res = sb.toString();
            } else if (propertyID.equals(ID_TASK_ADVANCEMENT)) {
                sb.append(" [ ");
                sb.append(task.getCompletionPercentage() + "%");
                sb.append(" ] ");
                res = sb.toString();
            } else if (propertyID.equals(ID_TASK_COORDINATOR)) {
                ResourceAssignment[] assignments = task.getAssignments();
                if (assignments.length > 0) {
                    boolean first = true;
                    boolean close = false;
                    int j = 0;
                    for (int i = 0; i < assignments.length; i++) {
                        if (assignments[i].isCoordinator()) {
                            j++;
                            if (first) {
                                close = true;
                                first = false;
                                sb.append("{");
                            }
                            if (j > 1) {
                                sb.append(", ");
                            }
                            sb.append(assignments[i].getResource().getName());
                        }
                    }
                    if (close)
                        sb.append("}");
                }
                res = sb.toString();
            } else if (propertyID.equals(ID_TASK_RESOURCES)) {
                ResourceAssignment[] assignments = task.getAssignments();
                if (assignments.length > 0) {
                    sb.append(" ");
                    /* Keep coordinators and other resources separate */
                    StringBuffer resources = new StringBuffer();
                    StringBuffer coordinators = new StringBuffer();
                    for (int i = 0; i < assignments.length; i++) {
                        /* Creates list of resources in format {coordinators},resources */
                        if (assignments[i].isCoordinator()) {
                            if (coordinators.length() > 0) {
                                coordinators.append(",");  
                            }
                            coordinators.append(assignments[i].getResource().getName());
                        } else {
                            if (resources.length() > 0) {
                                resources.append(",");  
                            }
                            resources.append(assignments[i].getResource().getName());  
                        }
                    }
                    if (coordinators.length() > 0) {
                        sb.append("{");
                        sb.append(coordinators);
                        sb.append("}");
                        if (resources.length() > 0) {
                            sb.append(",");
                        }
                    }
                    if (resources.length() > 0) {
                        sb.append(resources);
                    }
                    sb.append(" ");
                }
                res = sb.toString();
            } else if (propertyID.equals(ID_TASK_ID)) {
                sb.append("# ").append(task.getTaskID());
                res = sb.toString();
            } else if (propertyID.equals(ID_TASK_PREDECESSORS)) {
                TaskDependency[] dep = task.getDependenciesAsDependant().toArray();
                int i = 0;
                if (dep != null && dep.length > 0) {
                    for (i = 0; i < dep.length - 1; i++)
                        sb.append(dep[i].getDependee().getTaskID() + ", ");
//                    sb.append(dep[i].getDependee().getTaskID());
                    FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) ((KDTask)dep[i].getDependee()).getScheduleTaskInfo();
                    sb.append(taskInfo.getName()); //前置任务用WBS编码表示
                }
                res = sb.toString();
            } else {
                res = task.getCustomValues().getValue(propertyID);
            }
        }
        return res;

    }
}
