package net.sourceforge.ganttproject.task.algorithm;

import net.sourceforge.ganttproject.task.Task;

/**
 * Interface for all critical path finding algorithm.
 * 
 * @author bbaranne
 */
public interface CriticalPathAlgorithm {
    public Task[] getCriticalTasks();
}
