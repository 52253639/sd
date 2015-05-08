package com.kingdee.eas.fdc.schedule.framework.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttTask;
import net.sourceforge.ganttproject.GanttTaskRelationship;
import net.sourceforge.ganttproject.parser.ParsingListener;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskContainmentHierarchyFacade;
import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyCollection;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyCollectionImpl;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyException;
import net.sourceforge.ganttproject.task.dependency.TaskDependency.Hardness;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishStartConstraintImpl;

import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDPrjInfos;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;


public class TaskHandler extends AbstractKDHandler implements IKDHandler,ParsingListener {
	private final ScheduleGanttProject ganttProject;
	public TaskHandler(ScheduleGanttProject ganttProject) {
		this.ganttProject=ganttProject;
	}
	
	public void handle() {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map handleMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "handle");
		// ////////////////////////////////////////////////////////////////////////

		KDPrjInfos prjInfos = (KDPrjInfos) this.getKDParser().getPrjInfos();
		ScheduleBaseInfo info = prjInfos.getScheduleInfo();
		ScheduleTaskBaseCollection scheduleTasks = info.getScheduleTasks();
		sortTask(scheduleTasks);
		int size = scheduleTasks.size();
		String projctCostCenterID = null;
		if (size > 0) {
			projctCostCenterID = ((CurProjectInfo) info.get("project")).getCostCenter().getId().toString();
		}

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		// ////////////////////////////////////////////////////////////////////////

		ScheduleTaskBaseInfo firstTask = null;
		FDCScheduleTaskInfo fdcScheduleTaskInfo = null;
		if (size > 0) {
			projctCostCenterID = ((CurProjectInfo) info.get("project")).getCostCenter().getId().toString();

			firstTask = scheduleTasks.get(0);
			if (firstTask instanceof FDCScheduleTaskInfo) {
				fdcScheduleTaskInfo = (FDCScheduleTaskInfo) firstTask;
			}
		}

		// ////////////////////////////////////////////////////////////////////////

		try {
			// uiMark根据ScheduleClientHelper.getUIMark取得，在ScheduleBaseUI.load2Gantt中已经设置设置
			int uiMark = info.getInt("uiMark");
			if (null != fdcScheduleTaskInfo) {
				// 初始化任务属性Cache
				fdcScheduleTaskInfo.initScheduleTaskPropertyCache(uiMark);
			}

			ScheduleTaskBaseInfo task = null;
			// 进度性能优化: 性能瓶颈 77.3% - 68,268 ms - 519 inv by skyiter_wang 2014-06-11
			for (int i = 0; i < size; i++) {
				task = scheduleTasks.get(i);
				task.put("projectCostCenterID", projctCostCenterID);
				task.setInt("uiMark", uiMark);
				task.getScheduleBase().setInt("uiMark", uiMark);
				handleTask(task);
				info.setInt("initIndex", i);
			}

		} finally {
			if (null != fdcScheduleTaskInfo) {
				// 重设任务属性Cache
				fdcScheduleTaskInfo.resetScheduleTaskPropertyCache();
			}
		}

		// ////////////////////////////////////////////////////////////////////////

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "handle", handleMap);
		// ////////////////////////////////////////////////////////////////////////
	}
	
	
	/**
	 * 排序，以保证顺序的一致
	 * @param scheduleTasks
	 */
	private void sortTask(ScheduleTaskBaseCollection scheduleTasks) {
//		FDCHelper.sortObjectCollection(scheduleTasks, "longNumber");
	}

	private Map kdTaskMap=new HashMap();
	
	protected void handleTask(ScheduleTaskBaseInfo scheduleTaskInfo) {
		TaskManager taskManager = getTaskManager();
		//scheduleTaskInfo 的信息在实例化得时候就已经创建，并复制到Task
//		GanttTask task = taskManager.createTask();
		
		 // 进度性能优化： 性能瓶颈 44.4% - 39,207 ms - 519 inv by skyiter_wang 2014-06-11
		KDTask task = new KDTask(taskManager, scheduleTaskInfo);
		setTaskInfos(task, scheduleTaskInfo);
		taskManager.registerTask(task);
		TaskContainmentHierarchyFacade taskHierarchy = taskManager.getTaskHierarchy();
		// 将当前节点放到他的父节点下面

		// 进度性能优化： 性能瓶颈 31.4% - 27,724 ms - 519 inv by skyiter_wang 2014-06-11
		taskHierarchy.move(task, getParentTask((KDTask) task));

		// 设置计划完成时间 add by luoxiaolong 2011-09-15
        if(null != scheduleTaskInfo.getEnd() && !"".equals(scheduleTaskInfo.getEnd().toString())){
        	task.setEnd(GanttCalendar.parseXMLDate(FDCDateHelper
					.formatDate2(scheduleTaskInfo.getEnd())));
        }
        
        kdTaskMap.put(scheduleTaskInfo.getId().toString(), task);
        FDCScheduleInfo schedule = (FDCScheduleInfo)scheduleTaskInfo.getScheduleBase();
        Map dependBaseKeyMap = null;
        if(schedule.containsKey("dependBaseKeyMap")){
        	dependBaseKeyMap = (HashMap)schedule.get("dependBaseKeyMap");
        }else{
        	dependBaseKeyMap = new HashMap();
        	schedule.put("dependBaseKeyMap",dependBaseKeyMap);
        }
        
        ScheduleTaskDependCollection depends = scheduleTaskInfo.getDepends();
        for(Iterator iter=depends.iterator();iter.hasNext();){
        	ScheduleTaskDependInfo dependInfo = (ScheduleTaskDependInfo)iter.next();
        	if(dependInfo.getDependTaskBase()!=null&&dependBaseKeyMap!=null){
        		List dependeeList = null;
        		if(dependBaseKeyMap.containsKey(dependInfo.getDependTaskBase().getId().toString())){
        			dependeeList = (ArrayList)dependBaseKeyMap.get(dependInfo.getDependTaskBase().getId().toString());
        		}else{
        			dependeeList = new ArrayList();
        			dependBaseKeyMap.put(dependInfo.getDependTaskBase().getId().toString(), dependeeList);
        		}
        		dependeeList.add(dependInfo);
        	}
        	loadDependency(dependInfo);
        }
	}
	
	protected void setTaskInfos(GanttTask task,ScheduleTaskBaseInfo scheduleTaskInfo){
		
	}
	
	private KDTask getParentTask(KDTask task){
		if(task.getScheduleTaskInfo().getObjectValue("parent")==null){
			return null;
		}
			
		String parentId=task.getScheduleTaskInfo().getObjectValue("parent").getString("id");
		return (KDTask)kdTaskMap.get(parentId);
	}
	
	private KDTask getKDTask(String scheduleTaskId){
		return (KDTask)kdTaskMap.get(scheduleTaskId);
	}
	
	private int getKDTaskId(String scheduleTaskId){
		KDTask task=(KDTask)getKDTask(scheduleTaskId);
		if(task==null){
			return -1;
		}else{
			return task.getTaskID();
		}
	}
	
    public void parsingFinished() {
    	TaskManager myTaskManager = getTaskManager();
    	
        for (int i = 0; i < getDependencies().size(); i++) {
            GanttDependStructure ds = (GanttDependStructure) getDependencies()
                    .get(i);
            //可以直接取到，没有必要再通过TaskManager 进行取值 by sxhong
			Task dependee = getKDTask(ds.scheduleTaskID);// myTaskManager.getTask(getKDTaskId(ds.scheduleTaskID)); //前置
            Task dependant =getKDTask(ds.scheduleSuccessorTaskID);// myTaskManager.getTask(getKDTask); //后置
            if (dependee == null || dependant == null) {
                continue;
            }

            try {
            	//TODO 还应该支持其他的关联类型
                TaskDependencyCollection dependencyCollection = myTaskManager.getDependencyCollection();
                if(dependencyCollection instanceof TaskDependencyCollectionImpl){
                	TaskDependencyCollectionImpl dependencyColl=(TaskDependencyCollectionImpl)dependencyCollection;
		            // 如果后置任务有固定的开始，那么他们的关系应该是Rubber
					if (isFixedStartTask((KDTask)dependant)) {
		            	ds.myHardness=TaskDependency.Hardness.RUBBER;
		            }
					dependencyColl.createDependency(dependant, dependee,
							myTaskManager.createConstraint(ds.dependType),ds.myHardness,ds.difference);
					
	            }else{
					TaskDependency dep = dependencyCollection
	                        .createDependency(dependant, dependee,
	                                new FinishStartConstraintImpl());
	                dep.setConstraint(myTaskManager.createConstraint(ds.dependType));
	                dep.setDifference(ds.difference);
	                // 如果后置任务有固定的开始，那么他们的关系应该是Rubber
					if (isFixedStartTask((KDTask)dependant)) {
	                	dep.setHardness(TaskDependency.Hardness.RUBBER);
	                }
	                else {
	                	dep.setHardness(ds.myHardness);
	                }
	                dep.setHardness(ds.myHardness);
	            }
            } catch (TaskDependencyException e) {
                getKDParser().getUiFacade().logErrorMessage(e);
            }
        }
        
        this.ganttProject.setKDTaskMap(this.kdTaskMap);
    }

    private boolean isFixedStartTask(KDTask task){
    	if(task==null||task.getScheduleTaskInfo()==null){
    		return false;
    	}
    	return task.getScheduleTaskInfo().isFixedStart();
    }
    protected void loadDependency(ScheduleTaskDependInfo dependInfo) {
    	/*
		 * if (true) { return;//debug }
		 */
        if (dependInfo != null&&dependInfo.getDependTaskBase()!=null&&dependInfo.getTaskBase()!=null) {
            GanttDependStructure gds = new GanttDependStructure();
            //应该特殊处理，不然加载不到
			gds.setTaskID(dependInfo.getTaskBase().getId().toString());
			gds.setDependTaskID(dependInfo.getDependTaskBase().getId().toString());
			// gds.setTaskID(dependInfo.getDependTaskBase().getId().toString());
			// gds.setDependTaskID(dependInfo.getTaskBase().getId().toString());
            TaskLinkTypeEnum type = dependInfo.getType();
//			String dependencyTypeAsString = type;
            int difference= dependInfo.getDifference();
            String hardnessAsString = dependInfo.getHardness()!=null?dependInfo.getHardness().getName():null;
            gds.setDependType(ScheduleParserHelper.getTaskLinkTypeInt(type));
            gds.setDifference(difference);
            if (hardnessAsString!=null) {
            	TaskDependency.Hardness hardness = TaskDependency.Hardness.parse(hardnessAsString);
            	gds.setHardness(hardness);
            }
            getDependencies().add(gds);
        }
    }

    
    private class GanttDependStructure {
    	//进度实体的ID，而不是gantt task 的id
        public String scheduleTaskID, scheduleSuccessorTaskID;

        public int difference = 0;

        public int dependType = GanttTaskRelationship.FS; //

		private Hardness myHardness = TaskDependency.Hardness.STRONG;

		public GanttDependStructure() {
		}
        public void setHardness(Hardness hardness) {
        	myHardness = hardness;
		}

        public void setTaskID(String scheduleTaskID) {
            this.scheduleTaskID=scheduleTaskID;
        }

        public void setDifference(int difference) {
            this.difference = difference;
        }

        public void setDependTaskID(String scheduleSuccessorTaskID) {
            this.scheduleSuccessorTaskID = scheduleSuccessorTaskID;
        }

        public void setDependType(int dependType) {
            this.dependType = dependType;
        }
    }
    
    
    private List getDependencies() {
        return myDependencies;
    }

    private List myDependencies = new ArrayList();
    
    private TaskManager getTaskManager(){
    	TaskManager taskManager = this.getKDParser().getTaskManager();
    	return taskManager;
    }


	public void parsingStarted() {
		
	}
	
	public void reHandle() {
		super.reHandle();
		clear();
		handle();
		parsingFinished();
	}
	
	private void clear(){
		getTaskManager().clear();
		this.kdTaskMap.clear();
		this.ganttProject.getTree().clearTree();
	}
}
