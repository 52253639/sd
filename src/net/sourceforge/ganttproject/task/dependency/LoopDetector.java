/* LICENSE: GPL
 * This code is provided under the terms of GPL version 2.
 * Please see LICENSE file for details
 * (C) Dmitry Barashev, GanttProject team, 2004-2008
 */
package net.sourceforge.ganttproject.task.dependency;

import java.util.LinkedHashSet;
import java.util.Set;

import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskManager;

/**
 * Loop detector answers whether a dependency will create a loop in the dependency graph
 * @author dbarashev
 * 覆盖控件类方法
 * 
 * 原方法中存在一个BUG
 * 例如：
 * 
 * 1--
 * 	 |
 * 	 V
 * 	 2-----
 * 		|——2.1
 * 
 * 此时操作2.1依赖1，或者1的父级就会出现循环
 * @author 周勇
 * @version 2010-6-17 
 */
public class LoopDetector {
	private final TaskManager myTaskManager;
	public LoopDetector(TaskManager taskManager) {
		myTaskManager = taskManager;
	}
	public boolean isLooping(TaskDependency dep) {
		Set checked = new LinkedHashSet();
		checked.add(dep.getDependee());
		Task superTask = myTaskManager.getTaskHierarchy().getContainer(dep.getDependee());
		addParent(checked,superTask);
		
		boolean result = isLooping(checked, dep.getDependant());
		if(!result){
			Task[] tasks = myTaskManager.getTaskHierarchy().getNestedTasks(dep.getDependant());
			for(int i=0;!result&&i<tasks.length;i++){
				if (!checked.contains(tasks[i])){
					result = isChildLooping(checked,tasks[i]);
				}else{
					return true;
				}
			}
		}
		return result;
	}
	private void addParent(Set checked,Task task) {
		checked.add(task);
		Task supertask = myTaskManager.getTaskHierarchy().getContainer(task);
		if (supertask!=null && myTaskManager.getTaskHierarchy().getRootTask()!=supertask) {
			addParent(checked, supertask);
		}
	}

	private boolean isLooping(Set checked, Task incoming) {
		boolean result = false;
		Set newChecked = new LinkedHashSet(checked);
		newChecked.add(incoming);
		TaskDependency[] nextDeps = incoming.getDependenciesAsDependee().toArray();
		for (int i=0; !result && i<nextDeps.length; i++) {
			if (!newChecked.contains(nextDeps[i].getDependant())) {
				result = isLooping(newChecked, nextDeps[i].getDependant());
			}
			else {
				result = true;
			}
		}
		if (!result) {
			Task supertask = myTaskManager.getTaskHierarchy().getContainer(incoming);
			if (supertask!=null && myTaskManager.getTaskHierarchy().getRootTask()!=supertask) {
				result = isLooping(newChecked, supertask);
			}
		}
		return result;
	}
	private boolean isChildLooping(Set checked, Task incoming) {
		if(checked.contains(incoming)){
			return true;
		}else{
			boolean result = false;
			Set newChecked = new LinkedHashSet(checked);
			newChecked.add(incoming);
			TaskDependency[] nextDeps = incoming.getDependenciesAsDependee().toArray();
			for (int i=0; !result && i<nextDeps.length; i++) {
				if (!newChecked.contains(nextDeps[i].getDependant())) {
					result = isChildLooping(newChecked, nextDeps[i].getDependant());
				}
				else {
					result = true;
				}
			}
			Task[] tasks = myTaskManager.getTaskHierarchy().getNestedTasks(incoming);
			for(int i=0;!result&&i<tasks.length;i++){
				if (!newChecked.contains(tasks[i])) {
					result = isChildLooping(newChecked, tasks[i]);
				}
				else {
					result = true;
				}
				
			}
			return result;
		}
		
	}
}
