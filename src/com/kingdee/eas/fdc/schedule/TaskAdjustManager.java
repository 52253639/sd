package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;

public class TaskAdjustManager implements Serializable{
	private List taskItems=new ArrayList();
	private final ScheduleCalendarInfo calendarInfo;
	private Map childMap=new HashMap();
	public TaskAdjustManager(FDCScheduleInfo info){
		childMap.clear();
		for(Iterator iter=info.getTaskEntrys().iterator();iter.hasNext();){
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo)iter.next();
			addItem(task);
			addChildTask(task);
		}
		calendarInfo=info.getCalendar();
	}
	
	/**
	 * 子任务节点
	 * @param task
	 * @return
	 */
	private FDCScheduleTaskCollection getChildTasks(FDCScheduleTaskInfo task){
		if(task == null) return new FDCScheduleTaskCollection();
		FDCScheduleTaskCollection childs=(FDCScheduleTaskCollection)childMap.get(task.getLongNumber());
		if(childs==null){
			return new FDCScheduleTaskCollection();
		}
		return childs;
	}
	
	/**
	 * 初始化子任务节点
	 * @param task
	 */
	private void addChildTask(FDCScheduleTaskInfo task){
		if(task==null||task.getLongNumber()==null){
			return;
		}
//		System.out.println("longNumber:"+task.getLongNumber());
		if(task.getLongNumber().indexOf('!')>-1){
			String parentNumber=task.getLongNumber().substring(0, task.getLongNumber().lastIndexOf('!'));
			FDCScheduleTaskCollection childTasks=(FDCScheduleTaskCollection)childMap.get(parentNumber);
			if(childTasks==null){
				childTasks=new FDCScheduleTaskCollection();
				childMap.put(parentNumber, childTasks);
			}
			childTasks.add(task);
		}
	}
	
	public int size(){
		return taskItems.size();
	}
	public TaskAdjustManagerItem get(int i){
		return (TaskAdjustManagerItem)taskItems.get(i);
	}
	private TaskAdjustManagerItem addItem(FDCScheduleTaskInfo oldTask){
		TaskAdjustManagerItem item=new TaskAdjustManagerItem(oldTask);
		taskItems.add(item);
		oldTask.put("taskListItem", item);
		return item;
	}
	/**
	 * update all item's new task 
	 * @return a source List (adjust and new )
	 * sourceItems存放被调整的任务
	 */
	public List updateAll(){
		int size=size();
		List sourceItems=new ArrayList();
		for(int i=size-1;i>=0;i--){
			TaskAdjustManagerItem item=get(i);
			if(item.isDelete){
//				taskItems.remove(i);
				//要实现删除的可逆性
				item.type=TaskAdjustManagerItem.TYPE_NOCHANGE;
				item.newTask=null;
				item.isDelete=false;
				continue;
			}
			
			if(item.type.equals(TaskAdjustManagerItem.TYPE_ADJUSTTASK)||
					item.type.equals(TaskAdjustManagerItem.TYPE_NEWTASK) || 
					item.type.equals(TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK)){
				item.updateNewTask();
				sourceItems.add(item);
			}else{
				//没有调整的及被引起调整的全部更新成null,以便重算
				item.type=TaskAdjustManagerItem.TYPE_NOCHANGE;
				item.newTask=null;
			}
			item.isParentManAdjust=false;
		}
		return sourceItems;
	}
	public void updateItem(String taskId,ScheduleAdjustTaskEntryInfo adjustTaskEntry){
		TaskAdjustManagerItem item=findTaskListItem(taskId);
		if(item!=null){
			item.update(adjustTaskEntry);
//			FDCScheduleTaskDependCollection taskDepCol = item.getOldTask().getDependEntrys();
//			for(Iterator ite = taskDepCol.iterator();ite.hasNext();){
//				FDCScheduleTaskInfo taskInfo = ((FDCScheduleTaskDependInfo)ite.next()).getDependTask();
//				TaskAdjustManagerItem depItem = findTaskListItem(taskInfo.getId().toString());
//				depItem.setType(TaskAdjustManagerItem.TYPE_AFFECTTASK);
//			}
		}
	}
	
	public void addNewItem(String parentTaskId,ScheduleNewTaskEntryInfo newTaskEntry){
		TaskAdjustManagerItem item=new TaskAdjustManagerItem(newTaskEntry);
		int index=getNewItemIndex(newTaskEntry);
		if(index < 0) index = 0;
		taskItems.add(index,item);
		
	}
	public void deleteItem(ScheduleNewTaskEntryInfo newTaskEntry){
		TaskAdjustManagerItem item = findTaskListItem(newTaskEntry.getId().toString());
		if(item!=null){
			item.isDelete=true;
			getChildTasks(item.getNewTask().getParent()).remove(item.getNewTask());
		}
	}
	private TaskAdjustManagerItem findTaskListItem(String taskId){
		for(Iterator iter=taskItems.iterator();iter.hasNext();){
			TaskAdjustManagerItem item=(TaskAdjustManagerItem)iter.next();
			if(item.taskId!=null&&item.taskId.equals(taskId)){
				return item;
			}
		}
		
		return null;
	}
	
	private int getNewItemIndex(ScheduleNewTaskEntryInfo newTaskEntry){
		if(newTaskEntry.getParentTask()==null){
			return taskItems.size();
		}
		String parentTaskId=newTaskEntry.getParentTask().getId().toString();
		int index=-1;
		boolean findParent=false;
		for (int i = 0; i < taskItems.size(); i++) {
			TaskAdjustManagerItem item=(TaskAdjustManagerItem)taskItems.get(i);
		
			if(findParent&&item.getOldTask()!=null&&item.getOldTask().getLevel()<=newTaskEntry.getParentTask().getLevel()){
				//如果已经找到了上节任务，但是下一级的上级任务不是他的时候说明已经是最后一个了
				break;
			}
			if(item.taskId!=null&&item.taskId.equals(parentTaskId)){
				findParent=true;
				//插到它的后面
				index=i+1;
			}
			if(item.parentTaskId!=null&&item.parentTaskId.equals(parentTaskId)){
				//插到它的后面
				index=i+1;
			}
			
		}
		return index;
	}
	
	/**
	 * 进行计划调整
	 */
	public void adjustTasks(){
		List sourceList = updateAll();
		//调整
		setAdjustPrefixTask(true);
		for(Iterator iter=sourceList.iterator();iter.hasNext();){
			TaskAdjustManagerItem item=(TaskAdjustManagerItem)iter.next();
			if(item.type==TaskAdjustManagerItem.TYPE_ADJUSTTASK
					|| item.type == TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK){
				//update parent task and depend
				_adjustTask(item,item.getNewTask());
			}else{
				//update parent task
				FDCScheduleTaskInfo newTask = item.getNewTask();
				FDCScheduleTaskInfo parentTask = newTask.getParent();
				if(parentTask==null){
					continue;
				}
				//new task out of parent task 's bound (start<parent.start and end>parent.end)
				TaskAdjustManagerItem newItem = adjustParentAffectTask(newTask, parentTask);
				if(newItem == null) {
					continue;
				}
				_adjustTask(newItem,newItem.getNewTask());
			}
		}
		
	}

	private boolean isAdjustPrefixTask=false;
	public void setAdjustPrefixTask(boolean isAdjustPrefixTask){
		this.isAdjustPrefixTask=isAdjustPrefixTask;
	}
	
	private void _adjustTask(TaskAdjustManagerItem item,FDCScheduleTaskInfo sourceTask){
		if(item==null){
			return;
		}
		if(item.type==TaskAdjustManagerItem.TYPE_ADJUSTTASK || 
				item.type==TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK){
			item.isParentManAdjust=true;
		}
		
		FDCScheduleTaskInfo oldTask = item.getOldTask();

		//handle parent task
		if(oldTask.getParent()!=null){
			TaskAdjustManagerItem parentItem = adjustParentAffectTask(item.getNewTask(),oldTask.getParent());
			_adjustTask(parentItem,sourceTask);
		}
		
		//handle child task
		FDCScheduleTaskCollection childTasks = getChildTasks(oldTask);
		for(Iterator iter=childTasks.iterator();iter.hasNext();){
			FDCScheduleTaskInfo childTask=(FDCScheduleTaskInfo)iter.next();
			TaskAdjustManagerItem childItem = adjustChildAffectTask(item,childTask);
			_adjustTask(childItem,sourceTask);
		}
		//handle depend task
		for(Iterator iter=oldTask.getDependEntrys().iterator();iter.hasNext();){
			FDCScheduleTaskDependInfo depend=(FDCScheduleTaskDependInfo)iter.next();
			TaskAdjustManagerItem dependItem = adjustDependTaskAffectTask(sourceTask,item.getNewTask(),depend);
			_adjustTask(dependItem,sourceTask);
		}
		
		
		
		//handle prefix task 更新搭接时间,这个操作可以在生成版本数据后进行,单独在一个方法里面
		if(isAdjustPrefixTask){
			FDCScheduleTaskDependCollection depends=getDepends();
			for(int i=0,size=depends.size();i<size;i++){
				FDCScheduleTaskDependInfo depend=depends.get(i);
				if(depend.getDependTask().getId().equals(oldTask.getId())&&item.getNewTask()!=null){
					//update prefix
					if(depend.getType()==TaskLinkTypeEnum.FINISH_START){
						//difference=后置任务开始时间-当前任务的结束时间(注意都是更新后的数据)
//						depend.setDifference(ScheduleCalendarHelper.getEffectTimes(item.getNewTask().getStart(), depend.getTask().getEnd(), calendarInfo).intValue()-1);
						if(getTaskListItem(depend.getTask())!=null&&getTaskListItem(depend.getTask()).getNewTask()!=null)
							depend.setDifference(ScheduleCalendarHelper.getEffectTimes(
								getTaskListItem(depend.getTask()).getNewTask().getEnd(),item.getNewTask().getStart(),  calendarInfo).intValue()-1);
						else
							depend.setDifference(ScheduleCalendarHelper.getEffectTimes(
									depend.getTask().getEnd(),item.getNewTask().getStart(),  calendarInfo).intValue()-1);
					}
					if(depend.getType()==TaskLinkTypeEnum.FINISH_FINISH){
						//difference=newTask.end-end
//						depend.setDifference(ScheduleCalendarHelper.getEffectTimes(item.getNewTask().getEnd(), depend.getTask().getEnd(), calendarInfo).intValue()-1);
						if(getTaskListItem(depend.getTask())!=null&&getTaskListItem(depend.getTask()).getNewTask()!=null)
							depend.setDifference(ScheduleCalendarHelper.getEffectTimes(
								getTaskListItem(depend.getTask()).getNewTask().getEnd(), item.getNewTask().getEnd(), calendarInfo).intValue()-1);
						else
							depend.setDifference(ScheduleCalendarHelper.getEffectTimes(
									depend.getTask().getEnd(), item.getNewTask().getEnd(), calendarInfo).intValue()-1);
					}
//					if(depend.getType()==TaskLinkTypeEnum.START_FINISH){
//						//difference=newTask.end-start
//						depend.setDifference(ScheduleCalendarHelper.getEffectTimes(item.getNewTask().getEnd(), depend.getTask().getStart(), calendarInfo).intValue()-1);
//					}
					if(depend.getType()==TaskLinkTypeEnum.START_START){
						//difference=newTask.start-start
//						depend.setDifference(ScheduleCalendarHelper.getEffectTimes(item.getNewTask().getStart(), depend.getTask().getStart(), calendarInfo).intValue()-1);
						if(getTaskListItem(depend.getTask())!=null&&getTaskListItem(depend.getTask()).getNewTask()!=null)
							depend.setDifference(ScheduleCalendarHelper.getEffectTimes(
								getTaskListItem(depend.getTask()).getNewTask().getStart(),item.getNewTask().getStart(),calendarInfo).intValue()-1);
						else
							depend.setDifference(ScheduleCalendarHelper.getEffectTimes(
									depend.getTask().getStart(),item.getNewTask().getStart(),calendarInfo).intValue()-1);
					}
				}
			}
		}
	}
	
	private FDCScheduleTaskDependCollection depends=null;
	/**
	 * 取得所有后置节点
	 * @return
	 */
	private FDCScheduleTaskDependCollection getDepends(){
		if(depends==null){
			depends=new FDCScheduleTaskDependCollection();
			for(int i=0,size=size();i<size;i++){
				TaskAdjustManagerItem item = get(i);
				if(item.oldTask!=null){
					depends.addCollection(item.oldTask.getDependEntrys());
				}
			}
		}
		return depends;
	}
	
	/**
	 * 调整受影响的上级节点
	 * @param newTask
	 * @param parentTask
	 * @return
	 */
	private TaskAdjustManagerItem adjustParentAffectTask(FDCScheduleTaskInfo newTask, FDCScheduleTaskInfo parentTask) {
		if(newTask==null){
			return null;
		}
		TaskAdjustManagerItem parentItem=getTaskListItem(parentTask);
		if(parentItem==null||!parentItem.type.equals(TaskAdjustManagerItem.TYPE_NOCHANGE)){
			//已经调整过,不再重复,不然会死循环
			return null;
		}
		parentItem.newTask=cloneTask(parentTask);
		if(newTask.getStart().compareTo(parentTask.getStart())<0){
			parentItem.newTask.setStart(newTask.getStart());
		}else{
			/**
			 * 如果下级的开始时间调整到上级任务的开始时间之后则上级任务的开始时间为下级任务中最早的开始时间
			 * 但是这种情况有个例外,即主项控制专项,所以在下级是专项而上级是主项的情况就不需要做调整了
			 * @author xiaohong_shi 2009/12/17 
			 */
			if(parentTask.isMainTask()&&!newTask.isMainTask()){
					//主项控制专项这种情况不处理
			}else{
				//为调整后下级中开始时间最早的一个
				Date childEarliestStartDate = getChildEarliestStartDate(parentTask);
				if(childEarliestStartDate.compareTo(parentTask.getStart())!=0){
					parentItem.newTask.setStart(childEarliestStartDate);
				}
			}
		}
		if(newTask.getEnd().compareTo(parentTask.getEnd())>0){
			parentItem.newTask.setEnd(newTask.getEnd());
		}else{
			/**
			 * 如果下级的开始时间调整到上级任务的开始时间之后则上级任务的开始时间为下级任务中最早的开始时间
			 * 但是这种情况有个例外,即主项控制专项,所以在下级是专项而上级是主项的情况就不需要做调整了
			 * @author xiaohong_shi 2009/12/17 
			 */
			if(parentTask.isMainTask()&&!newTask.isMainTask()){
					//主项控制专项这种情况不处理
			}else{
				//为调整后下级中开始时间最早的一个
//				Date childEarliestEndDate = getChildEarliestEndDate(parentTask);
//				if(childEarliestEndDate.compareTo(parentTask.getEnd())!=0){
//					parentItem.newTask.setEnd(childEarliestEndDate);
//				}
				//应该是调整为下级中开始时间最迟的一个才对		-by neo
				Date childLatestEndDate = getChildLatestEndDate(parentTask);
					if(childLatestEndDate.compareTo(parentTask.getEnd())!=0){
					parentItem.newTask.setEnd(childLatestEndDate);
				}
			}
		}
		
		parentItem.newTask.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(parentItem.newTask.getStart(), parentItem.newTask.getEnd(), calendarInfo));
		if(parentItem!=null){
			if(TaskAdjustManagerItem.TYPE_ADJUSTTASK.equals(parentItem.type)
					|| TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK.equals(parentItem.type)){
				parentItem.type=TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK;
			}else{
				parentItem.type=TaskAdjustManagerItem.TYPE_AFFECTTASK;
			}
		}
		return parentItem;
	}

	/**
	 * 下级最早的开始时间
	 * 添加如下判断：1.下级为主项且上级为主项且下级时间突破上级，则返回下级开始时间
	 * 				2.下级为主项且上级为主项且下级时间未突破上级，则返回上级开始时间
	 * 				3.下级为专项且上级为专项，则上级时间根据下级来定
	 * 				4.下级为专项且上级为主项，则下级不允许突破主项
	 * 				5.如果没有下级，则直接返回当前的endDate
	 * @param parentTask
	 */
	private Date getChildEarliestStartDate(FDCScheduleTaskInfo parentTask) {
		Date earliestDate=null;
		FDCScheduleTaskInfo childInfo = null;
		FDCScheduleTaskCollection childTasks = getChildTasks(parentTask);
		for(Iterator iter=childTasks.iterator();iter.hasNext();){
			FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
			TaskAdjustManagerItem taskItem = getTaskListItem(task);
			if(taskItem!=null&&taskItem.newTask!=null){
				task=taskItem.newTask;
			}
			if(earliestDate==null){
				earliestDate=task.getStart();
				childInfo = task;
			}else if(earliestDate.after(task.getStart())){
				earliestDate=task.getStart();
				childInfo = task;
			}
		}
		if(childInfo == null) return parentTask.getEnd();
		if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(parentTask.getWbs().getTaskType().getId().toString())){			
			if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(childInfo.getWbs().getTaskType().getId().toString())){			
				if(childInfo.getStart().before(parentTask.getStart())){							
					earliestDate = childInfo.getStart();			//上级为主项&&下级为主项&&下级突破上级
				}else{
					earliestDate = parentTask.getStart();			//上级为主项&&下级为主项&&下级未突破上级
				}
			}else if(TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(childInfo.getWbs().getTaskType().getId().toString())){
				if(childInfo.getStart().before(parentTask.getStart())){							
								//上级为主项&&下级为专项&&下级突破上级
				}else{
					earliestDate = parentTask.getStart();			//上级为主项&&下级为专项&&下级未突破上级
				}
			}
		}else if(TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(parentTask.getWbs().getTaskType().getId().toString())){
			if(childInfo.getStart().before(parentTask.getStart())){							
				earliestDate = childInfo.getStart();			//上级为专项&&下级为专项&&下级突破上级
			}else{
				earliestDate = parentTask.getStart();			//上级为专项&&下级为专项&&下级未突破上级
			}
		}
		return earliestDate;
	}
	
	/**
	 * 下级最早的结束时间
	 * @param parentTask
	 */
	private Date getChildEarliestEndDate(FDCScheduleTaskInfo parentTask) {
		Date earliestDate=null;
		FDCScheduleTaskInfo childInfo = null;
		FDCScheduleTaskCollection childTasks = getChildTasks(parentTask);
		for(Iterator iter=childTasks.iterator();iter.hasNext();){
			FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
			TaskAdjustManagerItem taskItem = getTaskListItem(task);
			if(taskItem!=null&&taskItem.newTask!=null){
				task=taskItem.newTask;
			}
			if(earliestDate==null){
				earliestDate=task.getEnd();
			}else if(earliestDate.after(task.getEnd())){
				earliestDate=task.getEnd();
			}
		}
		if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(parentTask.getWbs().getTaskType().getId().toString())){			
			if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(childInfo.getWbs().getTaskType().getId().toString())){			
				if(parentTask.getEnd().before(childInfo.getEnd())){							
					earliestDate = childInfo.getEnd();			//上级为主项&&下级为主项&&下级突破上级
				}else{
					earliestDate = parentTask.getEnd();			//上级为主项&&下级为主项&&下级未突破上级
				}
			}else if(TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(childInfo.getWbs().getTaskType().getId().toString())){
				if(parentTask.getEnd().before(childInfo.getEnd())){							
								//上级为主项&&下级为专项&&下级突破上级
				}else{
					earliestDate = parentTask.getEnd();			//上级为主项&&下级为专项&&下级未突破上级
				}
			}
		}else if(TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(parentTask.getWbs().getTaskType().getId().toString())){
			if(parentTask.getEnd().before(childInfo.getEnd())){							
				earliestDate = childInfo.getEnd();			//上级为专项&&下级为专项&&下级突破上级
			}else{
				earliestDate = parentTask.getEnd();			//上级为专项&&下级为专项&&下级未突破上级
			}
		}
		return earliestDate;
	}
	/**
	 * 下级最迟的结束时间
	 * 添加如下判断：1.下级为主项且上级为主项且下级时间突破上级，则返回下级结束时间
	 * 				2.下级为主项且上级为主项且下级时间未突破上级，则返回上级结束时间
	 * 				3.下级为专项且上级为专项，则上级时间根据下级来定
	 * 				4.下级为专项且上级为主项，则下级不允许突破主项
	 * 				5.如果没有下级，则直接返回当前的endDate
	 * @param parentTask
	 */
	private Date getChildLatestEndDate(FDCScheduleTaskInfo parentTask) {
		Date latestDate=null;
		FDCScheduleTaskCollection childTasks = getChildTasks(parentTask);
		FDCScheduleTaskInfo childInfo = null;
		for(Iterator iter=childTasks.iterator();iter.hasNext();){
			FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
			TaskAdjustManagerItem taskItem = getTaskListItem(task);
			if(taskItem!=null&&taskItem.newTask!=null){
				task=taskItem.newTask;
			}
			if(latestDate==null){
				latestDate=task.getEnd();
				childInfo = task;
			}else if(latestDate.before(task.getEnd())){
				latestDate=task.getEnd();
				childInfo = task;
			}
		}
		if(childInfo == null) return parentTask.getEnd();
		if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(parentTask.getWbs().getTaskType().getId().toString())){			
			if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(childInfo.getWbs().getTaskType().getId().toString())){			
				if(parentTask.getEnd().before(childInfo.getEnd())){							
					latestDate = childInfo.getEnd();			//上级为主项&&下级为主项&&下级突破上级
				}else{
					latestDate = parentTask.getEnd();			//上级为主项&&下级为主项&&下级未突破上级
				}
			}else if(TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(childInfo.getWbs().getTaskType().getId().toString())){
				if(parentTask.getEnd().before(childInfo.getEnd())){							
								//上级为主项&&下级为专项&&下级突破上级
				}else{
					latestDate = parentTask.getEnd();			//上级为主项&&下级为专项&&下级未突破上级
				}
			}
		}else if(TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(parentTask.getWbs().getTaskType().getId().toString())){
			if(parentTask.getEnd().before(childInfo.getEnd())){							
				latestDate = childInfo.getEnd();			//上级为专项&&下级为专项&&下级突破上级
			}else{
				latestDate = parentTask.getEnd();			//上级为专项&&下级为专项&&下级未突破上级
			}
		}
		return latestDate;
	}
	
	/**
	 * 调整受影响的下级节点
	 * 
	 * 调整的算法跟GanttProject一致:上级调整则将下级内不再上级的开始时间/结束时间里面的调整到在这个范围内
	 * @param parentAdjustType 主要用于区分调整是内在的(手工调整),还是外在的(前置任务或上级调整等)
	 * @param newTask
	 * @param childTask
	 * @return
	 */
	private TaskAdjustManagerItem adjustChildAffectTask(TaskAdjustManagerItem parentItem, FDCScheduleTaskInfo childTask) {
		TaskAdjustManagerItem childItem=getTaskListItem(childTask);
		if(childItem==null||!childItem.type.equals(TaskAdjustManagerItem.TYPE_NOCHANGE)){
			//已经调整过,不再重复,不然会死循环
			return null;
		}
		boolean isParentManAdjust=parentItem.isParentManAdjust;
		FDCScheduleTaskInfo newTask=parentItem.newTask;
		FDCScheduleTaskInfo oldTask=parentItem.oldTask;
		if(newTask==null){
			return null;
		}
		if(isParentManAdjust){			
			//工期改变
			if(newTask.getStart().compareTo(childTask.getStart())>0 
					||newTask.getEnd().compareTo(childTask.getEnd())<0){
				childItem.newTask=cloneTask(childTask);
				if(newTask.getStart().compareTo(childTask.getStart())>0){
					childItem.newTask.setStart(newTask.getStart());
				}
				if(newTask.getEnd().compareTo(childTask.getEnd())<0){
					childItem.newTask.setEnd(newTask.getEnd());
				}
				childItem.newTask.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(childItem.newTask.getStart(), childItem.newTask.getEnd(), calendarInfo));
			}else{
				//将以前等于上级开始时间,结束时间的下级任务在调整后也等于上级的开始时间,这样以保证上级是由下级来的
				if(oldTask!=null){
					if(oldTask.getStart().compareTo(childTask.getStart())==0){
						if(childItem.newTask==null){
							childItem.newTask=cloneTask(childTask);
						}
						childItem.newTask.setStart(newTask.getStart());
						childItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(newTask.getStart(), childItem.newTask.getEffectTimes(), calendarInfo));
					}
					if(oldTask.getEnd().compareTo(childTask.getEnd())==0){
						if(childItem.newTask==null){
							childItem.newTask=cloneTask(childTask);
						}
						childItem.newTask.setEnd(newTask.getEnd());
					}
					if(childItem.newTask != null){
						childItem.newTask.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(childItem.newTask.getStart(), childItem.newTask.getEnd(), calendarInfo));
					}
				}
				
			}
			
		}else{
			//工期不变
			if(newTask.getStart().compareTo(childTask.getStart())>0 
					||newTask.getEnd().compareTo(childTask.getEnd())<0){
				childItem.newTask=cloneTask(childTask);
				if(newTask.getStart().compareTo(childTask.getStart())>0){
					childItem.newTask.setStart(newTask.getStart());
					childItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(newTask.getStart(), newTask.getEffectTimes(), calendarInfo));
				}
				if(newTask.getEnd().compareTo(childTask.getEnd())<0){
					childItem.newTask.setEnd(newTask.getEnd());
					childItem.newTask.setStart(ScheduleCalendarHelper.getStartDate(newTask.getEnd(), newTask.getEffectTimes(), calendarInfo));
				}
			}
		}
		if(childItem!=null){
			childItem.isParentManAdjust=isParentManAdjust;
			if(TaskAdjustManagerItem.TYPE_ADJUSTTASK.equals(childItem.type)
					||TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK.equals(childItem.type)){
				childItem.type=TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK;
			}else{
				childItem.type=TaskAdjustManagerItem.TYPE_AFFECTTASK;
			}
		}
		return childItem;
	}
	
	/**
	 * 后置任务要进行调整开始结束的调整
	 * @param newTask 变化后的任务节点
	 * @param depend
	 */
	private TaskAdjustManagerItem adjustDependTaskAffectTask(FDCScheduleTaskInfo sourceTask,FDCScheduleTaskInfo newTask, FDCScheduleTaskDependInfo depend) {
		if(newTask==null){
			return null;
		}
		//only adjust diffecttimes
		FDCScheduleTaskInfo dependTask = depend.getDependTask();
		TaskAdjustManagerItem sourceItem = null;
		for(int i=0;i<taskItems.size();i++){
			TaskAdjustManagerItem tempItem = get(i);
			if(tempItem.getOldTask()!= null && sourceTask.getId().toString().equals(tempItem.getOldTask().getId().toString())){
				sourceItem = tempItem;
			}
		}
		TaskAdjustManagerItem dependItem=getTaskListItem(dependTask);
		if(dependItem==null||dependItem.type.equals(TaskAdjustManagerItem.TYPE_NOCHANGE)){
			//已经调整过,不再重复,不然会死循环
			return null;
		}
		if(dependItem.newTask == null){
			dependItem.newTask=cloneTask(dependTask);
		}else{
//			Object object = dependTask.remove("taskListItem");
//			dependItem.newTask.put("taskListItem", object);
		}
		BigDecimal diff=new BigDecimal(String.valueOf(depend.getDifference()+1)); //相邻的天有效工期为1
		BigDecimal effectedStartDiff = FDCHelper.subtract(ScheduleCalendarHelper.getNatureTimes(
				dependItem.getOldTask().getStart(), dependItem.getNewTask().getStart()),FDCHelper.ONE);
		BigDecimal effectedEndDiff = FDCHelper.subtract(ScheduleCalendarHelper.getNatureTimes(
				dependItem.getOldTask().getEnd(), dependItem.getNewTask().getEnd()),FDCHelper.ONE);
		if(depend.getType()==TaskLinkTypeEnum.FINISH_START){
			//start=newTask.end+difference
			//间隔+1=有效工期
			if(sourceTask.getId().toString().equals(newTask.getId().toString())){
				dependItem.newTask.setStart(ScheduleCalendarHelper.getEndDate(newTask.getEnd(), FDCHelper.add(diff, effectedStartDiff), calendarInfo));
				dependItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(dependItem.newTask.getStart(), FDCHelper.add(effectedEndDiff,dependTask.getEffectTimes()), calendarInfo));
			}else{
				dependItem.newTask.setStart(ScheduleCalendarHelper.getEndDate(
						newTask.getEnd(),FDCHelper.add(diff, FDCHelper.subtract(sourceItem.getOldTask().getStart(), 
								sourceItem.getNewTask().getStart())), calendarInfo));
				dependItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(
						dependItem.newTask.getStart(), dependItem.newTask.getEffectTimes(), calendarInfo));
			}
		}
		if(depend.getType()==TaskLinkTypeEnum.FINISH_FINISH){
			//end=newTask.end+difference
			dependItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(newTask.getEnd(), diff, calendarInfo));
			dependItem.newTask.setStart(ScheduleCalendarHelper.getStartDate(dependItem.newTask.getEnd(), dependTask.getEffectTimes(), calendarInfo));
		}
//		if(depend.getType()==TaskLinkTypeEnum.START_FINISH){
//			//end=newTask.start+difference
//			dependItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(newTask.getStart(), diff, calendarInfo));
//			dependItem.newTask.setStart(ScheduleCalendarHelper.getStartDate(dependTask.getEnd(), dependTask.getEffectTimes(), calendarInfo));
//
//		}
		if(depend.getType()==TaskLinkTypeEnum.START_START){
			//start=newTask.start+difference
			dependItem.newTask.setStart(ScheduleCalendarHelper.getEndDate(newTask.getStart(), diff, calendarInfo));
			dependItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(dependItem.newTask.getStart(), dependTask.getEffectTimes(), calendarInfo));
		}
//		dependItem.newTask.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(dependItem.newTask.getStart(), dependItem.newTask.getEnd(), calendarInfo));
		if(dependItem!=null){
			if(TaskAdjustManagerItem.TYPE_ADJUSTTASK.equals(dependItem.type)
					||TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK.equals(dependItem.type)){
				dependItem.type=TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK;
			}else{
				dependItem.type=TaskAdjustManagerItem.TYPE_AFFECTTASK;
			}
		}
		return dependItem;
	
	}

	private TaskAdjustManagerItem getTaskListItem(FDCScheduleTaskInfo task){
		return (TaskAdjustManagerItem)task.get("taskListItem");
	}
	
	private FDCScheduleTaskInfo cloneTask(FDCScheduleTaskInfo task){
		Object object = task.remove("taskListItem");
		FDCScheduleTaskInfo newTask=new FDCScheduleTaskInfo();
		newTask.putAll(task);
		task.put("taskListItem", object);
		return newTask;
	}
	public class TaskAdjustManagerItem implements Serializable{
		/**
		 * 上级节点及上上级节点是否是手工调整
		 * 这个变量影响上级调整下级的时候是否做工期变化
		 */
		private boolean isParentManAdjust=false;
		private String type=TYPE_NOCHANGE;
		/**
		 * 未改变的任务
		 */
		public final static String TYPE_NOCHANGE="nochange";
		/**
		 * 手工调整了的任务
		 */
		public final static String TYPE_ADJUSTTASK="adjustTask";
		/**
		 * 新增的任务
		 */
		public final static String TYPE_NEWTASK="newTask";
		/**
		 * 因为任务调整或新增任务所引起的调整
		 */
		public final static String TYPE_AFFECTTASK="affectTask";
		
		/**
		 * 新增一种类型：手工调整了且被其他的任务所影响的调整
		 */
		public final static String TYPE_ADJUSTANDTAFFECTTASK = "adjustAndAffectTask";
		
		private boolean isDelete=false;
		
		private final FDCScheduleTaskInfo oldTask;
		private FDCScheduleTaskInfo newTask;
		private Object source=null;
		private String taskId=null;
		private String parentTaskId=null;
		private TaskAdjustManagerItem(FDCScheduleTaskInfo task){
			this.oldTask=task;
			taskId=task.getId().toString();
			parentTaskId=task.getParent()!=null?task.getParent().getId().toString():null;
		}
		
		public String getType(){
			return type;
		}
		
		public void setType(String str){
			this.type = str;
		}
		
		private void update(ScheduleAdjustTaskEntryInfo entry){
			if(entry!=null){
				newTask=cloneTask(this.oldTask);
				setNewTaskValue(entry);
				source=entry;
				isDelete=false;
				type=TYPE_ADJUSTTASK;
			}else{
				newTask=null;
				source=null;
				isDelete=true;
				type=TYPE_NOCHANGE;
			}
		}
		
		private TaskAdjustManagerItem(ScheduleNewTaskEntryInfo entry){
			this.oldTask=null;
			newTask=new FDCScheduleTaskInfo();
			setNewTaskValue(entry);
			newTask.setId(BOSUuid.create(newTask.getBOSType()));
			taskId=entry.getId().toString();
			//TODO H 做一些预处理
			parentTaskId=entry.getParentTask()!=null?entry.getParentTask().getId().toString():null;
			source=entry;
			type=TYPE_NEWTASK;
			
			addChildTask(newTask);
		}
		
		public FDCScheduleTaskInfo getOldTask(){
			return this.oldTask;
		}
		
		public FDCScheduleTaskInfo getNewTask(){
			return this.newTask;
		}
		
		private void setNewTaskValue(ScheduleAdjustTaskEntryInfo entry){
			newTask.setStart(entry.getStartDate());
			newTask.setEnd(entry.getEndDate());
			newTask.setEffectTimes(entry.getEffectTimes());
		}
		
		private void setNewTaskValue(ScheduleNewTaskEntryInfo entry){
			newTask.putAll(entry);
			newTask.setLongNumber(entry.getLongNumber());
			if(entry.getParentTask()!=null){
				String taskId=entry.getParentTask().getId().toString();
				for(int i=0,size=size();i<size;i++){
					TaskAdjustManagerItem item = get(i);
					if(item.taskId.equals(taskId)){
						newTask.setParent(item.getOldTask());
						newTask.setSchedule(item.getOldTask().getSchedule());
						break;
					}
				}
			}
			
		}
		private void updateNewTask(){
			if(newTask==null&&oldTask!=null){
				newTask=cloneTask(oldTask);
			}
			if(newTask==null){
				return;
			}
			Object obj=source;
			if(obj instanceof ScheduleNewTaskEntryInfo){
				setNewTaskValue((ScheduleNewTaskEntryInfo)obj);
			}
			if(obj instanceof ScheduleAdjustTaskEntryInfo){
				setNewTaskValue((ScheduleAdjustTaskEntryInfo)obj);
			}
		}
		
	}
}
