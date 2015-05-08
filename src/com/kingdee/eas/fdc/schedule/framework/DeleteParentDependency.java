/*
 * @(#)DeleteDepency.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.schedule.framework;

import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyCollectionMutator;

import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

public class DeleteParentDependency implements Runnable {

	private KDTask parentTask;

	public DeleteParentDependency(KDTask task) {
		parentTask = task;
	}

	public void run() {
		if (parentTask != null) {
			TaskDependencyCollectionMutator mutator = parentTask.getManager().getDependencyCollection().createMutator();
			TaskDependency[] depArray = parentTask.getDependencies().toArray();
			for (TaskDependency dc : depArray) {
				mutator.deleteDependency(dc);
			}
			mutator.commit();
		}

	}

}