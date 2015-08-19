package com.kingdee.eas.fdc.schedule.framework.ext;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttTask;
import net.sourceforge.ganttproject.cache.ActivityCache;
import net.sourceforge.ganttproject.calendar.GPCalendarActivity;
import net.sourceforge.ganttproject.calendar.ScheduleCalendarImpl;
import net.sourceforge.ganttproject.shape.ShapePaint;
import net.sourceforge.ganttproject.task.CustomColumnsException;
import net.sourceforge.ganttproject.task.CustomColumnsValues;
import net.sourceforge.ganttproject.task.TaskActivityImpl;
import net.sourceforge.ganttproject.task.TaskInfo;
import net.sourceforge.ganttproject.task.TaskLength;
import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.task.TaskMutator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.HelpDeptEntryFactory;
import com.kingdee.eas.fdc.schedule.HelpDeptEntryInfo;
import com.kingdee.eas.fdc.schedule.HelpPersonEntryFactory;
import com.kingdee.eas.fdc.schedule.HelpPersonEntryInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyInfo;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;

public class KDTask extends GanttTask{
	private boolean afterInit=false; 
	private KDCustomColumnsValues customValues;
	
	/**
	 * 日历
	 */
	private ScheduleCalendarImpl calendar = null;

    private KDTask(String name, GanttCalendar start, long length,
            TaskManager taskManager, int taskID) {
        super(name,start,length,taskManager,taskID);
    }

    public KDTask(GanttTask task,ScheduleTaskBaseInfo scheduleTaskInfo) {
    	super(task);
    	customValues=new KDCustomColumnsValues(this,task.getManager().getCustomColumnStorage(),scheduleTaskInfo);
    	
    	// 进度性能优化: 性能瓶颈 29.1% - 25,666 ms - 519 inv by skyiter_wang 2014-06-11
    	setScheduleTaskInfo(scheduleTaskInfo);
    	
//    	setAfterInit(true);
    }
    
    public KDTask(TaskManager taskManager,ScheduleTaskBaseInfo scheduleTaskInfo) {
    	this(taskManager.createTask(),scheduleTaskInfo);
    	TaskMutator mutator = this.createMutator();
    	mutator.setStart(ScheduleParserHelper.parseDateToGanttCalendar(scheduleTaskInfo.getStart()));
    	mutator.setDuration(getManager().createLength(scheduleTaskInfo.getDuration()));
    	mutator.commit();
    }
    
    private ScheduleTaskBaseInfo scheduleTaskInfo=null;
    
    public KDTask(ScheduleTaskBaseInfo scheduleTaskInfo,GanttCalendar start,TaskManager taskManager){
    	this(scheduleTaskInfo.getName(),start,scheduleTaskInfo.getDuration(),taskManager,-1);
    	customValues=new KDCustomColumnsValues(this,taskManager.getCustomColumnStorage(),scheduleTaskInfo);
    	setScheduleTaskInfo(scheduleTaskInfo);
//    	setAfterInit(true);
    }
    protected void setAfterInit(boolean afterInit){
    	this.afterInit=afterInit;
    	this.customValues.setAfterInit(afterInit);
    }
    public CustomColumnsValues getCustomValues() {
    	return this.customValues;
    }
    public ScheduleTaskBaseInfo getScheduleTaskInfo(){
    	return this.scheduleTaskInfo;
    }
    public void setScheduleTaskInfo(final ScheduleTaskBaseInfo scheduleTaskInfo){
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化: 性能瓶颈 29.1% - 25,666 ms - 519 inv by skyiter_wang 2014-06-11
		// ////////////////////////////////////////////////////////////////////////
		
    	this.scheduleTaskInfo = scheduleTaskInfo;

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		if (FDCHelper.isFDCDebug()) {
			System.out.println("before setScheduleTaskInfo:" + scheduleTaskInfo.getId() + "\t "
					+ scheduleTaskInfo.getNumber() + "\t " + scheduleTaskInfo.getName() + "\t "
					+ scheduleTaskInfo.getStart() + "\t " + scheduleTaskInfo.getEnd() + "\t "
					+ scheduleTaskInfo.get("EffectTimes") + "\t " + scheduleTaskInfo.getDuration() + "\t ");
		}
		// ////////////////////////////////////////////////////////////////////////
    	
//    	this.setName(scheduleTaskInfo.getName());
		// 重新对应到名称
		this.setName(scheduleTaskInfo.getName());
		
		/* modified by zhaoqin for R140417-0395 on 2014/04/23 start */
		// this.setStart 会用到Calendar,因此将Calendar的初始化放到前面
		if (scheduleTaskInfo instanceof FDCScheduleTaskInfo) {
			initFDCCalendar((FDCScheduleTaskInfo) scheduleTaskInfo);
		}
		/* modified by zhaoqin for R140417-0395 on 2014/04/23 end */
		
    	this.setStart(ScheduleParserHelper.parseDateToGanttCalendar(scheduleTaskInfo.getStart()));
    	
    	if (scheduleTaskInfo instanceof FDCScheduleTaskInfo) {
			// 初始化的时候，给task设置一个日历，然后根据开始日期和结束日期，计算有效工期<br>
			// 是的，之前是用有效工期计算结束日期，这会导致有效工期为0，并且日历设置7天都是周末时无限循环下去<br>
			// 所以在属性确定时加一个控制，有效工期不能为0，谁说不操蛋呢<br>
			//initFDCCalendar((FDCScheduleTaskInfo) scheduleTaskInfo);	/* modified by zhaoqin for R140417-0395 on 2014/04/23 */
			setEnd(getEnd());
			if (scheduleTaskInfo.getEnd() != null) {
				// 进度性能优化： 性能瓶颈 19.6% - 17,276 ms - 519 inv by skyiter_wang 2014-06-11
				this.setEndAndCalDur(scheduleTaskInfo.getStart(),
						scheduleTaskInfo.getEnd());
			}
		} else {
			if (scheduleTaskInfo.getDuration() >= 0) {
				this.setLength(scheduleTaskInfo.getDuration());
			}
		}
    	
    	this.setCompletionPercentage(ScheduleParserHelper.parseBigDecimalToInt(scheduleTaskInfo.getComplete()));
    	this.setMilestone(scheduleTaskInfo.isMeeting());
    	this.setColor(ScheduleParserHelper.parseStringToColor(scheduleTaskInfo.getColor()));
    	this.setPriority(scheduleTaskInfo.getPriority());
    	this.setNotes(scheduleTaskInfo.getNotes());
    	this.setExpand(scheduleTaskInfo.isExpand());
    	this.setShape(ScheduleParserHelper.getShape(scheduleTaskInfo.getShape(), this.getColor()));
		// new Thread(new Runnable(){
		//
		// public void run() {

		// 进度性能优化： 性能瓶颈 7.6% - 6,750 ms - 519 inv by skyiter_wang 2014-06-11
		handleCustomProperty(scheduleTaskInfo);

		// }}).start();

		// if(!this.isScheduleTask()){
		// /*
		// this.scheduleTaskInfo.put("myOldStartDate", this.scheduleTaskInfo.getStart());
		// this.scheduleTaskInfo.put("myOldEndDate", this.scheduleTaskInfo.getEnd());
		// this.scheduleTaskInfo.put("myOldDuration", new
		// Integer(this.scheduleTaskInfo.getDuration()));
		// this.scheduleTaskInfo.put("myOldNatureTimes", this.scheduleTaskInfo.get("natureTimes"));
		// this.scheduleTaskInfo.put("myOldEffectTimes", this.scheduleTaskInfo.get("effectTimes"));
		// // this.createMutatorFixingDuration().setIsolationLevel(0);
		// */
		// }

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		if (FDCHelper.isFDCDebug()) {
			System.out.println("after setScheduleTaskInfo:" + scheduleTaskInfo.getId() + "\t "
					+ scheduleTaskInfo.getNumber() + "\t " + scheduleTaskInfo.getName() + "\t "
					+ scheduleTaskInfo.getStart() + "\t " + scheduleTaskInfo.getEnd() + "\t "
					+ scheduleTaskInfo.get("EffectTimes") + "\t " + scheduleTaskInfo.getDuration() + "\t ");
		}
		// ////////////////////////////////////////////////////////////////////////
    }
    
    public GanttCalendar  getMyOldStartDate(){
    	return ScheduleParserHelper.parseDateToGanttCalendar((Date)this.scheduleTaskInfo.get("myOldStartDate"));
    }
    public GanttCalendar  getMyOldEndDate(){
    	return ScheduleParserHelper.parseDateToGanttCalendar((Date)this.scheduleTaskInfo.get("myOldEndDate"));
    }
    
    public Integer  getMyOldDuration(){
    	return (Integer)this.scheduleTaskInfo.get("myOldDuration");
    }
    
    public Object  get(String key){
    	return this.scheduleTaskInfo.get(key);
    }
    /**
     * 设置自定义列的值
     * @param scheduleTaskInfo
     */
    protected void handleCustomProperty(ScheduleTaskBaseInfo scheduleTaskInfo){
    	// 进度性能优化： 性能瓶颈 7.6% - 6,750 ms - 519 inv by skyiter_wang 2014-06-11
    	
    	if(scheduleTaskInfo==null){
    		return;
    	}
    	int uiMark = scheduleTaskInfo.getScheduleBase().getInt("uiMark");
		ScheduleTaskPropertyCollection customPropertys = null;
		try {
			customPropertys = scheduleTaskInfo.getCustomPropertys(uiMark);
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
    	if(customPropertys==null){
    		return;
    	}
		for(int i=0;i<customPropertys.size();i++){
			ScheduleTaskPropertyInfo prop = customPropertys.get(i);
    		try {
    			if ("enum".equals(prop.getValueType())) {
    				// 进度性能优化： 性能瓶颈 by skyiter_wang 2014-06-11
					// 进度性能优化: 此处是可以优化的 by skyiter_wang 2014-06-11
					this.getCustomValues().setValue(
							prop.getName(),scheduleTaskInfo.getCustomPropertyEnum(prop.getValueAtt(), prop.getMapKey()));
				}else if("helpDeptEntry".equals(prop.getKey())){
					String helpDeptEntry="";
					if(((FDCScheduleTaskInfo)scheduleTaskInfo).getHelpDeptEntry().size()>0){
						for(int j=0;j<((FDCScheduleTaskInfo)scheduleTaskInfo).getHelpDeptEntry().size();j++){
							HelpDeptEntryInfo info=((FDCScheduleTaskInfo)scheduleTaskInfo).getHelpDeptEntry().get(j);
							if(info!=null){
								info=HelpDeptEntryFactory.getRemoteInstance().getHelpDeptEntryInfo("select dept.* from where id='"+info.getId().toString()+"'");
								if(j==0){
									helpDeptEntry=info.getDept().getName();
								}else{
									helpDeptEntry=helpDeptEntry+";"+info.getDept().getName();
								}
							}
						}
					}else{
						helpDeptEntry=null;
					}
					this.getCustomValues().setValue(prop.getName(),helpDeptEntry);
				}else if("helpPersonEntry".equals(prop.getKey())){
					String helpPersonEntry="";
					if(((FDCScheduleTaskInfo)scheduleTaskInfo).getHelpPersonEntry().size()>0){
						for(int j=0;j<((FDCScheduleTaskInfo)scheduleTaskInfo).getHelpPersonEntry().size();j++){
							HelpPersonEntryInfo info=((FDCScheduleTaskInfo)scheduleTaskInfo).getHelpPersonEntry().get(j);
							if(info!=null){
								info=HelpPersonEntryFactory.getRemoteInstance().getHelpPersonEntryInfo("select person.* from where id='"+info.getId().toString()+"'");
								if(j==0){
									helpPersonEntry=info.getPerson().getName();
								}else{
									helpPersonEntry=helpPersonEntry+";"+info.getPerson().getName();
								}
							}
						}
					}else{
						helpPersonEntry=null;
					}
					this.getCustomValues().setValue(prop.getName(),helpPersonEntry);
				}else {
					// 进度性能优化： 性能瓶颈 by skyiter_wang 2014-06-11
					// 进度性能优化: 此处是可以优化的 by skyiter_wang 2014-06-11
					this.getCustomValues().setValue(
							prop.getName(),scheduleTaskInfo.getCustomPropertyValue(prop.getMapKey()));
				}
			} catch (CustomColumnsException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    }
    

//用set 方法与Info 进行同步    
    public void setColor(Color color) {
    	super.setColor(color);
    	if(afterInit){
    		getScheduleTaskInfo().setColor(ScheduleParserHelper.parseColorToString(color));
    	}
    }
    
    public void setCompletionPercentage(int percentage) {
    	super.setCompletionPercentage(percentage);
    	if(afterInit){
    		getScheduleTaskInfo().setComplete(new BigDecimal(percentage+""));
    	}
    }

	public void setTaskID(int taskID) {
		super.setTaskID(taskID);
	}

	public void setCritical(boolean critical) {
		super.setCritical(critical);
	}


	public void setLength(int l) {
		KDTaskAdjustEvent event=new KDTaskAdjustEvent(this,KDTaskAdjustEvent.CHANGETYPE_DURATION);
		event.setDuration(l);
		if(!afterInit||adjustable(event)){
//			super.setLength(l);
			//重写父类逻辑支持 有效工期1
	        if (l <= 0-KDGPConstants.EFFECTTIMESBALANCE) {
	            throw new IllegalArgumentException(
	                    "Length of task must be >=0. You've passed length=" + l
	                            + " to task=" + this);
	        }
	       
	        TaskMutator mutator = createMutator();

			// TaskDependency dependency =
			// GanttTreeTableModel.getCurrentTaskDependency(this);
			// if (dependency != null && dependency.getConstraint() instanceof
			// FinishFinishConstraintImpl) {
			// ScheduleCalendarInfo mycal = ((FDCScheduleTaskInfo)
			// this.getScheduleTaskInfo()).getCalendar();
			// Date startDate =
			// ScheduleCalendarHelper.getStartDate(this.getEnd().getTime(), new
			// BigDecimal(l), mycal);
			//			
			// }
	       
	        mutator.setDuration(getManager().createLength(
	                getDuration().getTimeUnit(), l));
	        mutator.commit();
	        mutator=null;
		}

	}
	
	public void setDuration(TaskLength length) {
		KDTaskAdjustEvent event=new KDTaskAdjustEvent(this,KDTaskAdjustEvent.CHANGETYPE_DURATION);
		event.setDuration(length.getLength());
		if(!afterInit||adjustable(event)){
			super.setDuration(length);
		}
	}

	public void setStart(GanttCalendar start) {
		KDTaskAdjustEvent event=new KDTaskAdjustEvent(this,KDTaskAdjustEvent.CHANGETYPE_STARTDATE);
		event.setStartDate(ScheduleParserHelper.parseGanttCalendarToDate(start));
		if(!afterInit||adjustable(event)){
			super.setStart(start);
			if(afterInit){
				getScheduleTaskInfo().setStart(ScheduleParserHelper.parseGanttCalendarToDate(start));
			}
		}
	}
	public void setEnd(GanttCalendar end) {
/*		if(!isScheduleTask()){
			return;
		}*/
		Date closestWorkingEnd = getCalendar().findClosestWorkingTime(end.getTime());
//		Date closestWorkingEnd = ((TaskManagerImpl)this.getManager()).findClosestWorkingTime(end.getTime());
		KDTaskAdjustEvent event=new KDTaskAdjustEvent(this,KDTaskAdjustEvent.CHANGETYPE_ENDDATE);
		event.setEndDate(closestWorkingEnd);
		if(!afterInit||adjustable(event)){
			Date oldEnd = myEnd == null ? this.getScheduleTaskInfo().getEnd() : myEnd.getTime();
			super.setEnd(ScheduleParserHelper.parseDateToGanttCalendar(closestWorkingEnd));
			updateCheckDate(closestWorkingEnd, oldEnd);
			if(afterInit){
				getScheduleTaskInfo().setEnd(closestWorkingEnd);
			}
		}
	}
	
	
	private void updateCheckDate(Date currEnd, Date oldEnd) {
		boolean isCheckNode = Boolean.valueOf(this.getScheduleTaskInfo().get("isCheckNode") + "").booleanValue();
		if (!isCheckNode) {
			return;
		}
		// if (!this.getScheduleTaskInfo().isIsLeaf()) {
		// try {
		//this.getCustomValues().setValue(GanttTreeTableModelExt.strColCheckDate
		// , ScheduleParserHelper.parseDateToGanttCalendar(currEnd));
		// getScheduleTaskInfo().put("checkDate", currEnd);
		// } catch (CustomColumnsException e) {
		// e.printStackTrace();
		// }
		// } else {
			
            Date oldCheckDate = null;
			if (getScheduleTaskInfo().get("checkDate") != null)
				oldCheckDate = (Date) getScheduleTaskInfo().get("checkDate");
			if (oldCheckDate == null || oldCheckDate.compareTo(oldEnd) == 0) {
				try {
					this.getCustomValues().setValue(GanttTreeTableModelExt.strColCheckDate, ScheduleParserHelper.parseDateToGanttCalendar(currEnd));
					getScheduleTaskInfo().put("checkDate", currEnd);
				} catch (CustomColumnsException e) {
					/* TODO 自动生成 catch 块 */
					e.printStackTrace();
				}
			}
		// }
	}

	public void setExpand(boolean expand) {
		super.setExpand(expand);
		if(afterInit){
    		getScheduleTaskInfo().setExpand(expand);
    	}
	}

	public void setMilestone(boolean milestone) {
		super.setMilestone(milestone);
		if(afterInit){
    		getScheduleTaskInfo().setMeeting(milestone);
    	}
	}

	public void setNotes(String notes) {
		super.setNotes(notes);
		if(afterInit){
    		getScheduleTaskInfo().setNotes(notes);
    	}
	}

	public void setPriority(int priority) {
		super.setPriority(priority);
		if(afterInit){
    		getScheduleTaskInfo().setPriority(priority);
    	}
	}

	public void setProjectTask(boolean projectTask) {
		super.setProjectTask(projectTask);
	}

	public void setShape(ShapePaint shape) {
		super.setShape(shape);
		if(afterInit){
    		getScheduleTaskInfo().setShape(ScheduleParserHelper.getShapeString(shape));
    	}
	}


	protected void setTaskIDHack(int taskID) {
		super.setTaskIDHack(taskID);
	}

	public void setTaskInfo(TaskInfo taskInfo) {
		super.setTaskInfo(taskInfo);
	}

	public void setThirdDate(GanttCalendar third) {
		super.setThirdDate(third);
		
	}

	public void setThirdDateConstraint(int thirdDateConstraint) {
		super.setThirdDateConstraint(thirdDateConstraint);
	}

	public void setWebLink(String webLink) {
		super.setWebLink(webLink);
		if(afterInit){
    		getScheduleTaskInfo().setWebLink(webLink);
    	}
	}

	public void setName(String name) {
		super.setName(name);
	}
	
	public String getName() {
		return super.getName();
	}
	
	public boolean isImport(){
		ScheduleTaskBaseInfo taskInfo = getScheduleTaskInfo();
		return taskInfo != null && taskInfo.getBoolean("isImport");
	}
	
	/**
	 * 返回该行任务是否可以编辑
	 * <p>
	 * 由于在KDTask中，只能取得到一个taskInfo，所以<br>
	 */
	public boolean isEditable(){
		if(!getScheduleTaskInfo().getScheduleBase().isEditable()){
			return false;
		} else {
			return true;
		}
	}
	
	public void setEditable(boolean isEditable){
		getScheduleTaskInfo().setEditable(isEditable);
	}
	
    public GanttCalendar getBoundStart(){
    	Date boundStart = getScheduleTaskInfo().getBoundStart();
    	if(boundStart!=null){
    		return ScheduleParserHelper.parseDateToGanttCalendar(boundStart);
    	}
    	return null;
    }
    
    public GanttCalendar getBoundEnd(){
    	Date boundEnd=getScheduleTaskInfo().getBoundEnd();
    	if(boundEnd!=null){
    		return ScheduleParserHelper.parseDateToGanttCalendar(boundEnd);
    	}
    	return null;
    }
    
    public boolean isScheduleTask(){
    	ScheduleTaskBaseInfo info = this.getScheduleTaskInfo();
    	if(info==null){
    		return true;
    	}
		return info.isScheduleTask();
    }
    protected void recalculateActivities() {
/*    	if(afterInit){
    		if(!isScheduleTask()){
    			//上级任务不做联动
    			return;
    		}
    	}*/
    	super.recalculateActivities();
    }
    
    /**
	 * KDTask有了Calendar之后，不再使用myManager.getCalendar<br>
	 * 此处重载TaskImpl<br>
	 * by emanon
	 */
	protected void recalculateActivities(List output, Date startDate,
			Date endDate) {
		List activities = getCalendar().getActivities(startDate, endDate);
		output.clear();
		for (int i = 0; i < activities.size(); i++) {
			GPCalendarActivity nextCalendarActivity = (GPCalendarActivity) activities
					.get(i);
			TaskActivityImpl nextTaskActivity;
			if (nextCalendarActivity.isWorkingTime()) {
				nextTaskActivity = new TaskActivityImpl(this,
						nextCalendarActivity.getStart(), nextCalendarActivity
								.getEnd(), 1.0f, true);
			} else if (i > 0 && i + 1 < activities.size()) {
				nextTaskActivity = new TaskActivityImpl(this,
						nextCalendarActivity.getStart(), nextCalendarActivity
								.getEnd(), 0, false);
			} else {
				continue;
			}
			output.add(nextTaskActivity);
		}
	}
    
    
    protected boolean adjustable(KDTaskAdjustEvent event){
    	if(!afterInit){
    		return true;
    	}
    	switch (event.getChangeType()) {
    		
    	}
/*    	最新讨论的结果,不做锁定工期了
    	Object wbs=this.get("wbs");
    	if((wbs instanceof IObjectValue&&((IObjectValue)wbs).getBoolean("isLocked"))
    			&&(event.getChangeType()==KDTaskAdjustEvent.CHANGETYPE_DURATION
    			||event.getChangeType()==KDTaskAdjustEvent.CHANGETYPE_ENDDATE)){
			//锁定了工期的不能修改任务工期及结束时间
			return false;
		}*/
    	boolean adjustable = this.getScheduleTaskInfo().adjustable(event);
    	if(!adjustable){
    		ScheduleBaseInfo info = getScheduleTaskInfo().getScheduleBase();
			if(info.getBoolean("createDependency")){
				info.setBoolean("createDependencyFaile", true);
//				FDCMsgBox.showWarning(FDCClientHelper.getCurrentActiveWindow(), "调整后的开始时间/结束时间超过了可以调整的范围");
    		}else{
    			//FDCMsgBox.showWarning(FDCClientHelper.getCurrentActiveWindow(), "调整后的开始时间/结束时间超过了可以调整的范围");
    		}
//    		throw new IllegalArgumentException("FDCScheduleException001");
			
			
    	}
		return adjustable;
    }
    
    public GanttCalendar getEnd() {
/*    	if(!isScheduleTask()){
			ScheduleParserHelper.parseDateToGanttCalendar(this.getScheduleTaskInfo().getEnd());
		}*/
		// if (myEnd != null && super.getEnd().compareTo(myEnd) != 0) {
		// return myEnd;
		// }
		 GanttCalendar end = super.getEnd();
		// GanttCalendar end = this.myEnd;
    	
    	
    	
//    	Date closestWorkingEnd = ((TaskManagerImpl)this.getManager()).findClosestWorkingTime(end.getTime());
    	Date closestWorkingEnd = getCalendar().findClosestWorkingTime(end.getTime());
    	end=ScheduleParserHelper.parseDateToGanttCalendar(closestWorkingEnd);
		return end;
    }
    
    public GanttCalendar getStart() {
/*    	if(!isScheduleTask()){
			ScheduleParserHelper.parseDateToGanttCalendar(this.getScheduleTaskInfo().getStart());
		}*/
    	return super.getStart();
    }
   
    public TaskLength getDuration() {
/*    	if(!isScheduleTask()){
    		return this.getManager().createLength(this.getScheduleTaskInfo().getDuration());
		}*/
    	return super.getDuration();
    }
    
    
    /**
     * 反正就是明细的是完全拆分颜色，非明细的是白色 edit by emanon
     * @return
     */
    public Color getBackground(){
    	ScheduleTaskBaseInfo task = getScheduleTaskInfo();
    	Object totalSch = task.get("totalSchedule");
		if (totalSch != null) {
			// 总进度计划中的任务，则分主专项设置颜色
			if ("main".equals(totalSch)) {
				if (task.isIsLeaf()) {
					return FDCSplitClientHelper.COLOR_PARTSPLIT;
				} else {
					return FDCSplitClientHelper.COLOR_NOSPLIT;
				}
			} else {
				if (task.isIsLeaf()) {
					return Color.WHITE;
				} else {
					return FDCSplitClientHelper.COLOR_ALLSPLIT;
				}
			}
    	}else{
    		// 普通计划编制或执行中，只按是否明细区分
			if (task.isIsLeaf()) {
				return Color.WHITE;
			} else {
				return FDCSplitClientHelper.COLOR_ALLSPLIT;
			}
    	}
    }
    
    /**
     * 返回任务列表的背景色
     * @return
     */
    public Color getForeground(){
    	return null;
    }
    
 // ----------------------------------------
	// 以下为添加任务细度的日历控制 by emanon
	// ----------------------------------------

	/**
	 * 初始化任务的日历
	 * 
	 * @param taskInfo
	 */
	public void initFDCCalendar(FDCScheduleTaskInfo taskInfo) {
		if (taskInfo != null) {
			ScheduleCalendarInfo taskCal = taskInfo.getCalendar();
			ActivityCache cache = ActivityCache.getInstance();
			ScheduleCalendarInfo defaultCal;
			// 该任务存在日历则取该日历，不存在日历则取项目默认日历，无默认日历，则取默认双休
			if (taskCal != null) {
				defaultCal = cache.getDefaultCal(taskCal.getId().toString());
				// 缓存有值则取缓存，无值则从数据库读取后，再添加到缓存里
				if (defaultCal == null) {
					try {
						defaultCal = ScheduleCalendarFactory
								.getRemoteInstance().getScheduleCalendarInfo(
										new ObjectUuidPK(taskCal.getId()));
						if (defaultCal != null) {
							cache.addScheduleCalendar(defaultCal, true);
						}
					} catch (BOSException e) {
						e.printStackTrace();
					} catch (EASBizException e) {
						e.printStackTrace();
					}
				}
				calendar = new ScheduleCalendarImpl(defaultCal);
			} else {
				String prjID = null;
				if(taskInfo.getSchedule() != null 
						&& taskInfo.getSchedule().getProject()!=null
						&&taskInfo.getSchedule().getProject().getId()!=null){
					prjID = taskInfo.getSchedule().getProject().getId().toString();
				}
				if (!FDCHelper.isEmpty(prjID)) {
					defaultCal = cache.getDefaultCal(prjID);
					// 缓存有值则取缓存，无值则从数据库读取后，再添加到缓存里
					if (defaultCal == null) {
						try {
							defaultCal = ScheduleCalendarFactory
									.getRemoteInstance().getDefaultCal(prjID);
							if (defaultCal != null) {
								cache.addScheduleCalendar(defaultCal);
							}
						} catch (BOSException e) {
							e.printStackTrace();
						} catch (EASBizException e) {
							e.printStackTrace();
						}
					}
					// 给他设置上默认日历
					taskInfo.setCalendar(defaultCal);
					calendar = new ScheduleCalendarImpl(defaultCal);
				} else {
					calendar = new ScheduleCalendarImpl(null);
				}
			}
		}
	}

	/**
	 * 一般情况下返回任务对应的日历<br>
	 * 如构建KDTask时日历为空等特殊情况，返回一个周末双休日历
	 * 
	 * @return
	 */
	public ScheduleCalendarImpl getCalendar() {
		if (calendar == null) {
			return ScheduleCalendarImpl.DEFAULT_CALENDAR;
		} else {
			return calendar;
		}
		
	}

	public void setCalendar(ScheduleCalendarImpl cal) {
		this.calendar = cal;
	}

	/**
	 * KDTask自带日历后，重载此方法，使用自带日历计算
	 */
	protected Date shiftDate(Date input, TaskLength duration) {
		return getCalendar().shiftDate(input, duration);
	}

	public void setEndAndCalDur(Date start, Date end) {
		// 进度性能优化： 性能瓶颈 19.6% - 17,276 ms - 519 inv by skyiter_wang 2014-06-11
		
		setEnd(new GanttCalendar(end));
		
		if (!afterInit) {
			TaskMutator mutator = createMutator();
			long len = mutator.setDateAndCalDur(calendar, start, end);
			
			// 进度性能优化： 性能瓶颈 17.2% - 15,199 ms - 519 inv by skyiter_wang 2014-06-11
			mutator.commit();
			
			mutator = null;
			// 更新甘特图后，同步更新info里有效工期字段，使表格也刷新
			((FDCScheduleTaskInfo) getScheduleTaskInfo())
					.setEffectTimes(new BigDecimal(len));
		}
	}
	
	public void applyThirdDateConstraint() {
		super.applyThirdDateConstraint();
		getScheduleTaskInfo().setStart(getStart().getTime());
		getScheduleTaskInfo().setEnd(getEnd().getTime());
	}

}
