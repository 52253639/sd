package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.util.DateTimeUtils;

public class StatusTaskResult implements Serializable {
	private final List list=new ArrayList();
	/**
	 *今天,截断时间
	 */
	private final Date today=DateTimeUtils.truncateDate(new Date());
	/**
	 * 明天,截断时间
	 */
	private final Date tomorrow=DateTimeUtils.truncateDate(FDCDateHelper.getNextDay(today));
	private final boolean isIncludeTask;
	private int maxLevel=0;
	/**
	 * 
	 * @param isIncludeTask 是否包含任务
	 */
	public StatusTaskResult(boolean isIncludeTask){
		this.isIncludeTask=isIncludeTask;
	}
	public void addItem(FDCScheduleInfo info){
		addItem(new StatusTaskResultItem(info));
	}
	
	private void addItem(StatusTaskResultItem item){
		if(item.getMaxLevel()>maxLevel){
			maxLevel=item.getMaxLevel();
		}
		list.add(item);
	}
	
	public StatusTaskResultItem getItem(int index){
		return (StatusTaskResultItem)list.get(index);
	}
	
	public int size(){
		return list.size();
	}

	public int getMaxLevel(){
		return maxLevel;
	}
	
	public class StatusTaskResultItem implements Serializable {
		private final FDCScheduleInfo info;
		private StatusTaskItem normalUnStartItem=new StatusTaskItem(TaskExecuteStatusItemEnum.NORMALUNSTART);
		private StatusTaskItem normalFinishedItem=new StatusTaskItem(TaskExecuteStatusItemEnum.NORMALFINISHED);
		private StatusTaskItem delayFinishedItem=new StatusTaskItem(TaskExecuteStatusItemEnum.DELAYFINISHED);
		private StatusTaskItem normalExcecutingItem=new StatusTaskItem(TaskExecuteStatusItemEnum.NORMALEXECUTING);
		private StatusTaskItem delayUnFinishedItem=new StatusTaskItem(TaskExecuteStatusItemEnum.DELAYUNFINISHED);
		private StatusTaskItem delayUnStartItem=new StatusTaskItem(TaskExecuteStatusItemEnum.DELAYUNSTART);
		int maxLevel=0;
		public StatusTaskResultItem(FDCScheduleInfo info){
			this.info=info;
			build();
		}
		
		public int getMaxLevel(){
			return maxLevel;
		}
		public void build(){
			for(Iterator iter=info.getTaskEntrys().iterator();iter.hasNext();){
				FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
				if(task.getLevel()>maxLevel){
					maxLevel=task.getLevel();
				}
				
				//正常未开始任务 
				if(task.getStart().compareTo(tomorrow)>0){
					normalUnStartItem.addTask(task);
					continue;
				}
				//存在任务大于100%的情况
				if(FDCHelper.subtract(task.getComplete(),FDCHelper.ONE_HUNDRED).signum()>=0){
					//正常已完成任务
					if(task.getActualEndDate()!=null&&
							DateTimeUtils.truncateDate(task.getEnd()).compareTo(DateTimeUtils.truncateDate(task.getActualEndDate()))>=0){
						normalFinishedItem.addTask(task);
					}else{
						//延误已完成任务
						delayFinishedItem.addTask(task);
					}
					continue;
					
				}
				
				//正常进行中任务
				if(task.getEnd().compareTo(today)>=0 
						&& FDCHelper.subtract(task.getComplete(),FDCHelper.ONE_HUNDRED).signum()<0
						&& FDCHelper.subtract(task.getComplete(),FDCHelper.ONE).signum()>0){
					normalExcecutingItem.addTask(task);
					continue;
				}
				
				//延误未完成任务
				if(task.getEnd().compareTo(today)<0 && FDCHelper.subtract(task.getComplete(),FDCHelper.ONE_HUNDRED).signum()<0){
					delayUnFinishedItem.addTask(task);
					continue;
				}
				
				//延误未开始任务
				if(task.getStart().compareTo(today)<0 && FDCHelper.toBigDecimal(task.getComplete()).signum()==0){
					delayUnStartItem.addTask(task);
					continue;
				}
				
			}
			//不需要了
			this.info.getTaskEntrys().clear();
		}
		
		
		
		
		public FDCScheduleInfo getScheduleInfo(){
			return info;
		}


		public StatusTaskItem getNormalUnStartItem() {
			return normalUnStartItem;
		}

		public StatusTaskItem getNormalFinishedItem() {
			return normalFinishedItem;
		}

		public StatusTaskItem getDelayFinishedItem() {
			return delayFinishedItem;
		}

		public StatusTaskItem getNormalExcecutingItem() {
			return normalExcecutingItem;
		}

		public StatusTaskItem getDelayUnFinishedItem() {
			return delayUnFinishedItem;
		}

		public StatusTaskItem getDelayUnStartItem() {
			return delayUnStartItem;
		}
	}
	
	public class StatusTaskItem implements Serializable {
		private final List list=new ArrayList();
		int size=-1;
		private final TaskExecuteStatusItemEnum type;
		public StatusTaskItem(TaskExecuteStatusItemEnum itemType){
			type=itemType;
		}
		public TaskExecuteStatusItemEnum getTaskExecuteStatusItemEnum(){
			return type;
		}
		public TaskExecuteStatusEnum getTaskExecuteStatus(){
			if(type==TaskExecuteStatusItemEnum.NORMALUNSTART
					||type==TaskExecuteStatusItemEnum.NORMALFINISHED
					||type==TaskExecuteStatusItemEnum.DELAYFINISHED){
				return TaskExecuteStatusEnum.NORMAL;
			}
			
			if(type==TaskExecuteStatusItemEnum.NORMALEXECUTING){
				return TaskExecuteStatusEnum.EXECUTING;
			}
			
			if(type==TaskExecuteStatusItemEnum.DELAYUNFINISHED
					||type==TaskExecuteStatusItemEnum.DELAYUNSTART){
				return TaskExecuteStatusEnum.DELAY;
			}
			return TaskExecuteStatusEnum.NONE; 
		}
		
		public void addTask(FDCScheduleTaskInfo task){
			if(isIncludeTask){
				list.add(task);
			}else{
				addTask();
			}
			
		}
		
		private void addTask(){
			if(size==-1){
				size=0;
			}
			size++;
		}
		
		public FDCScheduleTaskInfo getTask(int index){
			return (FDCScheduleTaskInfo)list.get(index);
		}
/*		public void setSize(int size){
			this.size=size;
		}*/
		
		public int getSize(){
			if(size==-1){
				size=list.size();
			}
			return size;
		}
	}
}
