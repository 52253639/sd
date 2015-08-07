/*
 * Created on 05.07.2003
 *
 */
package net.sourceforge.ganttproject.task;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sourceforge.ganttproject.GPLogger;
import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttProject;
import net.sourceforge.ganttproject.GanttTask;
import net.sourceforge.ganttproject.GanttTaskRelationship;
import net.sourceforge.ganttproject.IGanttProject;
import net.sourceforge.ganttproject.calendar.CalendarFactory;
import net.sourceforge.ganttproject.calendar.GPCalendar;
import net.sourceforge.ganttproject.resource.HumanResource;
import net.sourceforge.ganttproject.resource.ResourceManager;
import net.sourceforge.ganttproject.task.algorithm.AdjustTaskBoundsAlgorithm;
import net.sourceforge.ganttproject.task.algorithm.AlgorithmCollection;
import net.sourceforge.ganttproject.task.algorithm.CriticalPathAlgorithm;
import net.sourceforge.ganttproject.task.algorithm.CriticalPathAlgorithmImpl;
import net.sourceforge.ganttproject.task.algorithm.FindPossibleDependeesAlgorithm;
import net.sourceforge.ganttproject.task.algorithm.FindPossibleDependeesAlgorithmImpl;
import net.sourceforge.ganttproject.task.algorithm.ProjectBoundsAlgorithm;
import net.sourceforge.ganttproject.task.algorithm.RecalculateTaskCompletionPercentageAlgorithm;
import net.sourceforge.ganttproject.task.algorithm.RecalculateTaskScheduleAlgorithm;
import net.sourceforge.ganttproject.task.dependency.EventDispatcher;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyCollection;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyCollectionImpl;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyConstraint;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyException;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishFinishConstraintImpl;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishStartConstraintImpl;
import net.sourceforge.ganttproject.task.dependency.constraint.StartFinishConstraintImpl;
import net.sourceforge.ganttproject.task.dependency.constraint.StartStartConstraintImpl;
import net.sourceforge.ganttproject.task.event.TaskDependencyEvent;
import net.sourceforge.ganttproject.task.event.TaskHierarchyEvent;
import net.sourceforge.ganttproject.task.event.TaskListener;
import net.sourceforge.ganttproject.task.event.TaskPropertyEvent;
import net.sourceforge.ganttproject.task.event.TaskScheduleEvent;
import net.sourceforge.ganttproject.task.hierarchy.TaskHierarchyManagerImpl;
import net.sourceforge.ganttproject.time.DateFrameable;
import net.sourceforge.ganttproject.time.TimeUnit;

import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;

/**
 * @author bard
 */
public class TaskManagerImpl implements TaskManager {
    private final TaskHierarchyManagerImpl myHierarchyManager;

    private final TaskDependencyCollectionImpl myDependencyCollection;

    private final AlgorithmCollection myAlgorithmCollection;

    private final List myListeners = new ArrayList();

    private int myMaxID = -1;


    private Task myRoot;

    private final TaskManagerConfig myConfig;

    private final TaskContainmentHierarchyFacade.Factory myFacadeFactory;

    private TaskContainmentHierarchyFacade myTaskContainment;

	private boolean areEventsEnabled = true;

    private static class TaskMap {

		private final Map myId2task = new HashMap();

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		// ////////////////////////////////////////////////////////////////////////

		// 起始日期Set: 顺序排序
		private Set startDateSet = new TreeSet();
		// 结束日期Set: 逆序排序
		private Set endDateSet = new TreeSet(new Comparator() {
			public int compare(Object o1, Object o2) {
				Date d1 = (Date) o1;
				Date d2 = (Date) o2;

				return -(d1.compareTo(d2));
			}
		});

		// 最小起始日期
		private Date minStartDate;
		// 最大结束日期
		private Date maxEndDate;

		// ////////////////////////////////////////////////////////////////////////

		private TaskDocumentOrderComparator myComparator;
		private boolean isModified = true;
		private Task[] myArray;
		private final TaskManagerImpl myManager;

		TaskMap(TaskManagerImpl taskManager) {
			myComparator = new TaskDocumentOrderComparator(taskManager);
			myManager = taskManager;
		}

		void addTask(Task task) {
			myId2task.put(new Integer(task.getTaskID()), task);

			// ////////////////////////////////////////////////////////////////////////
			// 进度性能优化 by skyiter_wang 2014-06-11
			// ////////////////////////////////////////////////////////////////////////

			Date startDate = task.getStart().getTime();
			Date endDate = task.getEnd().getTime();
			startDateSet.add(startDate);
			endDateSet.add(endDate);

			// 重设最小起始时间，最大结束时间
			resetStartDateEndDate();

			// ////////////////////////////////////////////////////////////////////////

			isModified = true;
		}

		Task getTask(int id) {
			return (Task) myId2task.get(new Integer(id));
		}

		public Task[] getTasks() {
			if (isModified) {
				myArray = (Task[]) myId2task.values().toArray(new Task[myId2task.size()]);
				Arrays.sort(myArray, myComparator);
				isModified = false;
			}
			return myArray;
		}

		public void clear() {
			myId2task.clear();

			// ////////////////////////////////////////////////////////////////////////
			// 进度性能优化 by skyiter_wang 2014-06-11
			// ////////////////////////////////////////////////////////////////////////

			startDateSet.clear();
			endDateSet.clear();

			// 重设最小起始时间，最大结束时间
			resetStartDateEndDate();

			// ////////////////////////////////////////////////////////////////////////

			isModified = true;
		}

		public void removeTask(Task task) {
			myId2task.remove(new Integer(task.getTaskID()));
			Task[] nestedTasks = myManager.getTaskHierarchy().getNestedTasks(task);
			for (int i = 0; i < nestedTasks.length; i++) {
				removeTask(nestedTasks[i]);
			}

			// ////////////////////////////////////////////////////////////////////////
			// 进度性能优化 by skyiter_wang 2014-06-11
			// ////////////////////////////////////////////////////////////////////////

			Date startDate = task.getStart().getTime();
			Date endDate = task.getEnd().getTime();
			startDateSet.remove(startDate);
			endDateSet.remove(endDate);

			// 重设最小起始时间，最大结束时间
			resetStartDateEndDate();

			// ////////////////////////////////////////////////////////////////////////

			isModified = true;
		}

		public int size() {
			return myId2task.size();
		}

		public boolean isEmpty() {
			return myId2task.isEmpty();
		}

		void setDirty() {
			isModified = true;
		}

		/**
		 * 描述：取得 最小起始日期
		 * 
		 * @return
		 * @author rd_skyiter_wang
		 * @createDate 2014-6-12
		 */
		public Date getMinStartDate() {
			return minStartDate;
		}

		/**
		 * 描述：取得 最大结束日期
		 * 
		 * @return
		 * @author rd_skyiter_wang
		 * @createDate 2014-6-12
		 */
		public Date getMaxEndDate() {
			return maxEndDate;
		}

		/**
		 * 描述：重设最小起始日期，最大结束日期
		 * 
		 * @author rd_skyiter_wang
		 * @createDate 2014-6-11
		 */
		private void resetStartDateEndDate() {
			if (FdcCollectionUtil.isNotEmpty(startDateSet)) {
				// 取第1个元素
				minStartDate = (Date) startDateSet.iterator().next();
			} else {
				minStartDate = null;
			}

			if (FdcCollectionUtil.isNotEmpty(endDateSet)) {
				// 取第1个元素
				maxEndDate = (Date) endDateSet.iterator().next();
			} else {
				maxEndDate = null;
			}
		}
	}
    
    public void setProject(IGanttProject project) {
		this.myProject = project;
	}
    
    private final TaskMap myTaskMap = new TaskMap(this);
    
    private IGanttProject myProject = null;

	private final CustomColumnsStorage myCustomColumnStorage;

    TaskManagerImpl(
            TaskContainmentHierarchyFacade.Factory containmentFacadeFactory,
            TaskManagerConfig config, CustomColumnsStorage columnStorage) {
    	myCustomColumnStorage = columnStorage==null ?
    			new CustomColumnsStorage() : columnStorage;
        myConfig = config;
        myHierarchyManager = new TaskHierarchyManagerImpl();
        EventDispatcher dispatcher = new EventDispatcher() {
            public void fireDependencyAdded(TaskDependency dep) {
                TaskManagerImpl.this.fireDependencyAdded(dep);
            }

            public void fireDependencyRemoved(TaskDependency dep) {
                TaskManagerImpl.this.fireDependencyRemoved(dep);
            }
        };
        myDependencyCollection = new TaskDependencyCollectionImpl(containmentFacadeFactory, dispatcher) {
            private TaskContainmentHierarchyFacade myTaskHierarchy;

            protected TaskContainmentHierarchyFacade getTaskHierarchy() {
                if (myTaskHierarchy == null) {
                    myTaskHierarchy = TaskManagerImpl.this.getTaskHierarchy();
                }
                return myTaskHierarchy;
            }
        };
        myFacadeFactory = containmentFacadeFactory == null ? new FacadeFactoryImpl()
                : containmentFacadeFactory;
        // clear();
        {
            Calendar c = CalendarFactory.newCalendar();
            Date today = c.getTime();
            myRoot = new GanttTask(null, new GanttCalendar(today), 1, this, -1);
            myRoot.setStart(new GanttCalendar(today));
            myRoot.setDuration(createLength(getConfig().getTimeUnitStack()
                    .getDefaultTimeUnit(), 1));
            myRoot.setExpand(true);

        }

        FindPossibleDependeesAlgorithm alg1 = new FindPossibleDependeesAlgorithmImpl() {
            protected TaskContainmentHierarchyFacade createContainmentFacade() {
                return TaskManagerImpl.this.getTaskHierarchy();
            }

        };
        AdjustTaskBoundsAlgorithm alg3 = new AdjustTaskBoundsAlgorithm() {
            protected TaskContainmentHierarchyFacade createContainmentFacade() {
                return TaskManagerImpl.this.getTaskHierarchy();
            }
        };
        RecalculateTaskScheduleAlgorithm alg2 = new RecalculateTaskScheduleAlgorithm(
                alg3) {
            protected TaskContainmentHierarchyFacade createContainmentFacade() {
                return TaskManagerImpl.this.getTaskHierarchy();
            }
        };
        RecalculateTaskCompletionPercentageAlgorithm alg4 = new RecalculateTaskCompletionPercentageAlgorithm() {
            protected TaskContainmentHierarchyFacade createContainmentFacade() {
                return TaskManagerImpl.this.getTaskHierarchy();
            }
        };
        ProjectBoundsAlgorithm alg5 = new ProjectBoundsAlgorithm();
        myAlgorithmCollection = new AlgorithmCollection(alg1, alg2, alg3, alg4,
                alg5);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.sourceforge.ganttproject.task.TaskManager#getTask(int)
     */
    public GanttTask getTask(int taskId) {
        return (GanttTask) myTaskMap.getTask(taskId);
    }

    public Task getRootTask() {
        if (myRoot == null) {
        }
        return myRoot;
    }

    public Task[] getTasks() {
    	return myTaskMap.getTasks();
        //return (Task[]) myId2task.values().toArray(new Task[myId2task.size()]);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.sourceforge.ganttproject.task.TaskManager#clear()
     */
    public void clear() {
        myTaskMap.clear();
        setMaxID(-1);
        myDependencyCollection.clear();
        {
            Calendar c = CalendarFactory.newCalendar();
            Date today = c.getTime();
            myRoot = new GanttTask(null, new GanttCalendar(today), 1, this, -1);
            myRoot.setStart(new GanttCalendar(today));
            myRoot.setDuration(createLength(getConfig().getTimeUnitStack()
                    .getDefaultTimeUnit(), 1));
            myRoot.setExpand(true);

        }
    }

    public void deleteTask(Task tasktoRemove) {
        myTaskMap.removeTask(tasktoRemove);
        // TaskDependency[] dep =
        // myDependencyCollection.getDependencies(tasktoRemove);
        // for(int i=0;i<dep.length; i++){
        // dep[i].delete();
        // }
        // todo REMOVE TASK FROM TASKHIERARCHY
        // fireTaskRemoved(tasktoRemove,myTaskContainment.getContainer(tasktoRemove));
    }

    public GanttTask createTask() {
        GanttTask result = createTask(-1);
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.sourceforge.ganttproject.task.TaskManager#createTask(int)
     */
    public GanttTask createTask(int taskID) {
        GanttTask result = new GanttTask("", new GanttCalendar(), 1, this,
                taskID);
        if (result.getTaskID() >= getMaxID()) {
            setMaxID(result.getTaskID() + 1);
        }
        // result.setTaskID(taskID);
        // getTaskHierarchy().move(result, getRootTask());
        // result.move(getRootTask());
        fireTaskAdded(result);
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.sourceforge.ganttproject.task.TaskManager#registerTask(net.sourceforge.ganttproject.GanttTask)
     */
    public void registerTask(Task task) {
        int taskID = task.getTaskID();
        if (myTaskMap.getTask(taskID) == null) { // if the taskID is
            // not in the map
            myTaskMap.addTask(task);
            if (getMaxID() < taskID) {
                setMaxID(taskID + 1);
            }
        } else { // taskID has been in the map. the newTask will not be added
            throw new RuntimeException(
                    "There is a task that already has the ID " + taskID);
        }
       
    }

	boolean isRegistered(TaskImpl task) {
		return myTaskMap.getTask(task.getTaskID())!=null;
	}

    public int getTaskCount() {
        return myTaskMap.size();
    }

    public TaskLength getProjectLength() {
		if (myTaskMap.isEmpty()) {
			return createLength(getConfig().getTimeUnitStack().getDefaultTimeUnit(), 0);
		}
		// Result result = getAlgorithmCollection().getProjectBoundsAlgorithm()
		// .getBounds(Arrays.asList(myTaskMap.getTasks()));
		//        
		// return createLength(
		// getConfig().getTimeUnitStack().getDefaultTimeUnit(),
		// result.lowerBound, result.upperBound);

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		// ////////////////////////////////////////////////////////////////////////

		Date minStartDate = myTaskMap.getMinStartDate();
		Date maxEndDate = myTaskMap.getMaxEndDate();

		return createLength(getConfig().getTimeUnitStack().getDefaultTimeUnit(), minStartDate, maxEndDate);

		// ////////////////////////////////////////////////////////////////////////
	}

	public Date getProjectStart() {
		if (myTaskMap.isEmpty()) {
			return myRoot.getStart().getTime();
		}
		// Result result = getAlgorithmCollection().getProjectBoundsAlgorithm()
		// .getBounds(Arrays.asList(myTaskMap.getTasks()));
		// return result.lowerBound;

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		// ////////////////////////////////////////////////////////////////////////

		Date minStartDate = myTaskMap.getMinStartDate();
		
		/* modified by zhaoqin for R140919-0204 on 2014/10/22 */
		if(null == minStartDate)
			minStartDate = myRoot.getStart().getTime();

		// ////////////////////////////////////////////////////////////////////////

		return minStartDate;
	}

	public Date getProjectEnd() {
		if (myTaskMap.isEmpty()) {
			return myRoot.getStart().getTime();
		}
		// Result result = getAlgorithmCollection().getProjectBoundsAlgorithm()
		// .getBounds(Arrays.asList(myTaskMap.getTasks()));
		// return result.upperBound;

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		// ////////////////////////////////////////////////////////////////////////

		Date maxEndDate = myTaskMap.getMaxEndDate();
		
		/* modified by zhaoqin for R140919-0204 on 2014/10/22 */
		if(null == maxEndDate)
			maxEndDate = myRoot.getStart().getTime();
		
		// ////////////////////////////////////////////////////////////////////////

		return maxEndDate;
	}

    public TaskLength createLength(TimeUnit unit, float length) {
        return new TaskLengthImpl(unit, length);
    }

    public TaskLength createLength(long count) {
        return new TaskLengthImpl(getConfig().getTimeUnitStack()
                .getDefaultTimeUnit(), count);
    }

    public TaskLength createLength(TimeUnit timeUnit, Date startDate,
            Date endDate) {
        TaskLength result;
        int sign = 1;
        if (endDate.before(startDate)) {
            sign = -1;
            Date temp = endDate;
            endDate = startDate;
            startDate = temp;
        }
        if (timeUnit instanceof DateFrameable) {
            DateFrameable df = (DateFrameable) timeUnit;
            int unitCount = 0;
/*           原有逻辑 
            for (; startDate.before(endDate); unitCount++) {
                startDate = df.adjustRight(startDate);
            }*/
            
            for (; !startDate.after(endDate);) {
            	if(!getCalendar().isNonWorkingDay(startDate)){
            		//工期应该计算的是工作日才对,因为工期支持1 所以应该从startdate算起
            		unitCount++;
            	}
                startDate = df.adjustRight(startDate);
            }
            
            result = new TaskLengthImpl(timeUnit, unitCount*sign);
        } else {
            throw new IllegalArgumentException("Time unit=" + timeUnit
                    + " is not date frameable");
        }
        return result;
    }

    public TaskDependencyCollection getDependencyCollection() {
        return myDependencyCollection;
    }

    public AlgorithmCollection getAlgorithmCollection() {
        return myAlgorithmCollection;
    }

    public TaskHierarchyManagerImpl getHierarchyManager() {
        return myHierarchyManager;
    }

    public TaskDependencyConstraint createConstraint(final int constraintID) {
        TaskDependencyConstraint result;
        switch (constraintID) {
        case GanttTaskRelationship.FS: {
            result = new FinishStartConstraintImpl();
            break;
        }
        case GanttTaskRelationship.FF: {
            result = new FinishFinishConstraintImpl();
            break;
        }
        case GanttTaskRelationship.SF: {
            result = new StartFinishConstraintImpl();
            break;
        }
        case GanttTaskRelationship.SS: {
            result = new StartStartConstraintImpl();
            break;
        }
        default: {
            throw new IllegalArgumentException("Unknown constraint ID="
                    + constraintID);
        }
        }
        return result;
    }

    public int getMaxID() {
        return myMaxID;
    }

    private void setMaxID(int id) {
        myMaxID = id;
    }

    void increaseMaxID() {
        myMaxID++;
    }

    public void addTaskListener(TaskListener listener) {
        myListeners.add(listener);
    }

    public GPCalendar getCalendar() {
        return getConfig().getCalendar();
    }

    public void fireTaskProgressChanged(Task changedTask) {
    	if (areEventsEnabled) {
	        TaskPropertyEvent e = new TaskPropertyEvent(changedTask);
	        for (int i = 0; i < myListeners.size(); i++) {
	            TaskListener next = (TaskListener) myListeners.get(i);
	            next.taskProgressChanged(e);
	        }
    	}
    }

    void fireTaskScheduleChanged(Task changedTask, GanttCalendar oldStartDate,
            GanttCalendar oldFinishDate) {
    	if (areEventsEnabled) {
	        TaskScheduleEvent e = new TaskScheduleEvent(changedTask, oldStartDate,
	                oldFinishDate, changedTask.getStart(), changedTask.getEnd());
	        // List copy = new ArrayList(myListeners);
	        // myListeners.clear();
	        for (int i = 0; i < myListeners.size(); i++) {
	            TaskListener next = (TaskListener) myListeners.get(i);
	            next.taskScheduleChanged(e);
	        }
    	}
    	fireTaskChange();
    }

    private void fireDependencyAdded(TaskDependency newDependency) {
    	if (areEventsEnabled) {
	        TaskDependencyEvent e = new TaskDependencyEvent(
	                getDependencyCollection(), newDependency);
	        for (int i = 0; i < myListeners.size(); i++) {
	            TaskListener next = (TaskListener) myListeners.get(i);
	            next.dependencyAdded(e);
	        }
    	}
    }

    private void fireDependencyRemoved(TaskDependency dep) {
    	if (areEventsEnabled) {
	        TaskDependencyEvent e = new TaskDependencyEvent(
	                getDependencyCollection(), dep);
	        for (int i = 0; i < myListeners.size(); i++) {
	            TaskListener next = (TaskListener) myListeners.get(i);
	            next.dependencyRemoved(e);
	        }
    	}
    }

    private void fireTaskAdded(Task task) {
    	if (areEventsEnabled) {
	        TaskHierarchyEvent e = new TaskHierarchyEvent(this, task, null,
	                getTaskHierarchy().getContainer(task));
	        for (int i = 0; i < myListeners.size(); i++) {
	            TaskListener next = (TaskListener) myListeners.get(i);
	            next.taskAdded(e);
	        }
    	}
    	
    	
    }

    private void fireTaskRemoved(Task task, Task oldSupertask) {
        TaskHierarchyEvent e = new TaskHierarchyEvent(this, task, oldSupertask,
                null);
        for (int i = 0; i < myListeners.size(); i++) {
            TaskListener next = (TaskListener) myListeners.get(i);
            next.taskRemoved(e);
        }
        
    }

    void fireTaskPropertiesChanged(Task task) {
    	if (areEventsEnabled) {
	        TaskPropertyEvent e = new TaskPropertyEvent(task);
	        for (int i = 0; i < myListeners.size(); i++) {
	            TaskListener next = (TaskListener) myListeners.get(i);
	            next.taskPropertiesChanged(e);
	        }
    	}
    }

    public TaskManagerConfig getConfig() {
        return myConfig;
    }

    private final class FacadeImpl implements
            TaskContainmentHierarchyFacade {
        //private final Task myRoot;

        private List myPathBuffer = new ArrayList();

//        public FacadeImpl(Task root) {
//            myRoot = root;
//        }

        public Task[] getNestedTasks(Task container) {
            return container.getNestedTasks();
        }

        public boolean hasNestedTasks(Task container) {
            return myRoot==null ? false: myRoot.isMilestone();
        }

        public Task getRootTask() {
            return TaskManagerImpl.this.getRootTask();
        }

        public Task getContainer(Task nestedTask) {
            return nestedTask.getSupertask();
        }

        public boolean areUnrelated(Task first, Task second) {
            myPathBuffer.clear();
            for (Task container = getContainer(first); container != null; container = getContainer(container)) {
                myPathBuffer.add(container);
            }
            if (myPathBuffer.contains(second)) {
                return false;
            }
            myPathBuffer.clear();
            for (Task container = getContainer(second); container != null; container = getContainer(container)) {
                myPathBuffer.add(container);
            }
            if (myPathBuffer.contains(first)) {
                return false;
            }
            return true;
        }

        public void move(Task whatMove, Task whereMove) {
            whatMove.move(whereMove);
        }

        public int getDepth(Task task) {
            int depth = 0;
            while (task != myRoot) {
                task = task.getSupertask();
                depth++;
            }
            return depth;
        }

        public int compareDocumentOrder(Task task1, Task task2) {
        	if (task1==task2) {
        		return 0;
        	}
        	List buffer1 = new ArrayList();
            for (Task container = task1; container != null; container = getContainer(container)) {
                buffer1.add(0,container);
            }
        	List buffer2 = new ArrayList();
            for (Task container = task2; container != null; container = getContainer(container)) {
                buffer2.add(0,container);
            }
            if (buffer1.get(0)!=getRootTask() && buffer2.get(0)==getRootTask()) {
            	return -1;
            }
            if (buffer1.get(0)==getRootTask() && buffer2.get(0)!=getRootTask()) {
            	return 1;
            }

            int result = 0;
        	int i=0;
        	Task commonRoot = null;
        	while (true) {
        		if (i==buffer1.size()) {
        			return -1;
        		}
        		if (i==buffer2.size()) {
        			return 1;
        		}
        		Task root1 = (Task) buffer1.get(i);
        		Task root2 = (Task) buffer2.get(i);
        		if (root1!=root2) {
//        			assert commonRoot!=null : "Failure comparing task="+task1+" and task="+task2+"\n. Path1="+buffer1+"\nPath2="+buffer2;
        			Task[] nestedTasks = commonRoot.getNestedTasks();
        			for (int j=0; j<nestedTasks.length; j++) {
        				if (nestedTasks[j]==root1) {
        					return -1;
        				}
        				if (nestedTasks[j]==root2) {
        					return 1;
        				}
        			}
        			throw new IllegalStateException("We should not be here");
        		}
        		i++;
        		commonRoot = root1;
        	}
        }

        public boolean contains(Task task) {
            throw new UnsupportedOperationException();
        }

    }

    private class FacadeFactoryImpl implements
            TaskContainmentHierarchyFacade.Factory {
//        private final Task myRoot;
//
//        FacadeFactoryImpl(Task root) {
//            myRoot = root;
//        }

        public TaskContainmentHierarchyFacade createFacede() {
            return new FacadeImpl();
        }

    }

    public TaskContainmentHierarchyFacade getTaskHierarchy() {
        // if (myTaskContainment==null) {
        return myFacadeFactory.createFacede();
        // }
        // return myTaskContainment;
    }

    public TaskManager emptyClone() {
        return new TaskManagerImpl(null, myConfig, null);
    }

    public Map importData(TaskManager taskManager) {
        Task importRoot = taskManager.getRootTask();
        Map original2imported = new HashMap();
        importData(importRoot, getRootTask(), original2imported);
        TaskDependency[] deps = taskManager.getDependencyCollection()
                .getDependencies();
        for (int i = 0; i < deps.length; i++) {
            Task nextDependant = deps[i].getDependant();
            Task nextDependee = deps[i].getDependee();
            Task importedDependant = (Task) original2imported
                    .get(nextDependant);
            Task importedDependee = (Task) original2imported.get(nextDependee);
            try {
                TaskDependency dependency = getDependencyCollection()
                        .createDependency(importedDependant, importedDependee,
                                new FinishStartConstraintImpl());
                dependency.setConstraint(deps[i].getConstraint());
                dependency.setDifference(deps[i].getDifference());
                dependency.setHardness(deps[i].getHardness());
            } catch (TaskDependencyException e) {
            	if (!GPLogger.log(e)) {
            		e.printStackTrace(System.err);
            	}
            }
        }
        return original2imported;
    }

    private void importData(Task importRoot, Task root, Map original2imported) {
        Task[] nested = importRoot.getManager().getTaskHierarchy()
                .getNestedTasks(importRoot);
        for (int i = nested.length - 1; i >= 0; i--) {
            Task nextImported = createTask();
            registerTask(nextImported);
            nextImported.setName(nested[i].getName());
            nextImported.setStart(nested[i].getStart().Clone());
            nextImported.setDuration(nested[i].getDuration());
            nextImported.setMilestone(nested[i].isMilestone());
            nextImported.setColor(nested[i].getColor());
            nextImported.setShape(nested[i].getShape());
            nextImported.setCompletionPercentage(nested[i]
                    .getCompletionPercentage());
            nextImported.setNotes(nested[i].getNotes());
            nextImported.setTaskInfo(nested[i].getTaskInfo());
            nextImported.setExpand(nested[i].getExpand());
            if (nested[i].getThird() != null) {
                nextImported.setThirdDate(nested[i].getThird().Clone());
                nextImported.setThirdDateConstraint(nested[i]
                        .getThirdDateConstraint());
            }

            myCustomColumnStorage.processNewTask(nextImported);
            CustomColumnsValues customValues = nested[i].getCustomValues();
            Collection customColums = myCustomColumnStorage.getCustomColums();
            for (Iterator it=customColums.iterator(); it.hasNext();) {
            	CustomColumn nextColumn = (CustomColumn) it.next();
            	Object value = customValues.getValue(nextColumn.getName());
            	if (value!=null) {
            		try {
						nextImported.getCustomValues().setValue(nextColumn.getName(), value);
					} catch (CustomColumnsException e) {
			        	if (!GPLogger.log(e)) {
			        		e.printStackTrace(System.err);
			        	}
					}
            	}
            }
            // System.out.println ("Import : " + nextImported.getTaskID() + "
            // -->> " + nextImported.getName());

            original2imported.put(nested[i], nextImported);
            // nextImported.move(root);
            getTaskHierarchy().move(nextImported, root);
            importData(nested[i], nextImported, original2imported);
        }
    }

    public Date findClosestWorkingTime(Date time) {
        return getCalendar().findClosestWorkingTime(time);
    }

    public void processCriticalPath(TaskNode root) {
        try {
            myAlgorithmCollection.getRecalculateTaskScheduleAlgorithm().run();
        } catch (TaskDependencyException e) {
        	if (!GPLogger.log(e)) {
        		e.printStackTrace(System.err);
        	}
        }
        CriticalPathAlgorithm criticAlgo = new CriticalPathAlgorithmImpl(root,
                getCalendar());
        Task tasks[] = criticAlgo.getCriticalTasks();
        resetCriticalPath(root);
        for (int i = 0; i < tasks.length; i++)
            tasks[i].setCritical(true);
    }

    private void resetCriticalPath(TaskNode root) {
        Enumeration en = root.preorderEnumeration();
        while (en.hasMoreElements()) {
            Object next = en.nextElement();
            if (!(next instanceof TaskNode))
                continue;
            Task t = (Task) ((TaskNode) next).getUserObject();
            t.setCritical(false);
        }
    }

    public void importAssignments(TaskManager importedTaskManager,
            ResourceManager hrManager, Map original2importedTask,
            Map original2importedResource) {
        Task[] tasks = importedTaskManager.getTasks();
        for (int i = 0; i < tasks.length; i++) {
            ResourceAssignment[] assignments = tasks[i].getAssignments();
            for (int j = 0; j < assignments.length; j++) {
                Task task = getTask(((Task) original2importedTask.get(tasks[i]))
                        .getTaskID());
                ResourceAssignment assignment = task.getAssignmentCollection()
                        .addAssignment(
                                (HumanResource) original2importedResource
                                        .get(assignments[j].getResource()));
                assignment.setLoad(assignments[j].getLoad());
                assignment.setCoordinator(assignments[j].isCoordinator());
            }
        }
    }

	void onTaskMoved(TaskImpl task) {
		if (!isRegistered(task)) {
			registerTask(task);
		}
		myTaskMap.setDirty();
	}

	public void setEventsEnabled(boolean enabled) {
		areEventsEnabled = enabled;
	}

	public CustomColumnsStorage getCustomColumnStorage() {
		return myCustomColumnStorage;
	}

	public URL getProjectDocument() {
		return myConfig.getProjectDocumentURL();
	}
	
	public void fireTaskChange() {
		// 进度性能优化: 性能瓶颈 15.6% - 13,812 ms - 519 inv by skyiter_wang 2014-06-11
		
		// 进度性能优化: 此处是可以优化的 by skyiter_wang 2014-06-11
		// 优化方案：从缓存Map中取数
		// 详见：TaskManagerImpl.TaskMap.addTask、getMinStartDate;TaskManagerImpl.getProjectStart
		Date start = getProjectStart();
		Date end = getProjectEnd();
          
		Integer max = Integer.parseInt((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24) + "");
		((GanttProject) myProject).setFromDate(start);
		((GanttProject) myProject).setToDate(end);
		((GanttProject) myProject).initSlider(max.intValue());
	}
}
