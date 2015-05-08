package com.kingdee.eas.fdc.schedule.app;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleCreateReasonEnum;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerCollection;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryCollection;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerFactory;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ScheduleVerManagerControllerBean extends AbstractScheduleVerManagerControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ScheduleVerManagerControllerBean");
    
    /* 
     * 取得最新版本的项目任务任务按WBS及任务的顺序排列
     * @see com.kingdee.eas.fdc.schedule.app.AbstractScheduleVerManagerControllerBean#_getNewVerData(com.kingdee.bos.Context, java.lang.String)
     */
    protected IObjectValue _getNewVerData(Context ctx, String prjId)throws BOSException, EASBizException
    {
    	if(prjId==null){
    		throw new NullPointerException("prjId can't be null!");
    	}
    	EntityViewInfo view=new EntityViewInfo();
    	view.setFilter(new FilterInfo());
    	view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.project.id",prjId));
    	view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.islatestVer",Boolean.TRUE));
    	view.getSelector().add("*");
    	view.getSelector().add("wbs.id");
    	view.getSelector().add("wbs.number");
    	view.getSelector().add("wbs.name");
    	view.getSelector().add("parent.wbs.id");
    	//TODO H 暂时用longnumber排序
    	view.getSorter().add(new SorterItemInfo("wbs.longNumber"));
    	
    	FDCScheduleTaskCollection tasks = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
    	FDCScheduleInfo info=new FDCScheduleInfo();
    	info.put("taskEntrys", tasks);
    	
    	SelectorItemCollection selector=new SelectorItemCollection();
    	selector.add("id");
    	selector.add("name");
    	selector.add("longNumber");
    	CurProjectInfo prj=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(prjId), selector);
    	info.setProject(prj);
        return info;
    }
    protected IObjectCollection _getVerData(Context ctx, Map paramMap) throws BOSException, EASBizException {
    	Object o = paramMap.get("prjIds");
    	
    	Set prjIds = (HashSet)o;
    	EntityViewInfo view=new EntityViewInfo();
    	view.setFilter(new FilterInfo());
    	view.getSelector().add("id");
    	view.getSelector().add("version");
    	view.getSelector().add("effectTimes");
    	view.getSelector().add("natureTimes");
    	view.getSelector().add("startDate");
    	view.getSelector().add("endDate");
    	view.getSelector().add("createReason");
    	view.getSelector().add("state");
    	view.getSelector().add("number");
    	view.getSelector().add("creator.name");
    	view.getSelector().add("Project.name");
    	view.getSelector().add("Project.number");
    	view.getSelector().add("Project.id");
    	view.getSelector().add("Project.orgUnit.id");
    	view.getSelector().add("Project.orgUnit.name");
    	view.getSelector().add("isLatestVer");
    	
    	view.getFilter().getFilterItems().add(new FilterItemInfo("project.id",prjIds,CompareType.INCLUDE));
    	view.getFilter().getFilterItems().add(new FilterItemInfo("isLatestVer",Boolean.TRUE));
    	if(paramMap.containsKey("isAdjust")){
    		view.getFilter().getFilterItems().add(new FilterItemInfo("state", ScheduleStateEnum.EXETING_VALUE));
    	}
    	ScheduleVerManagerCollection verInfos=ScheduleVerManagerFactory.getLocalInstance(ctx).getScheduleVerManagerCollection(view);
    	return verInfos;
	}
    protected IObjectValue _getVerData(Context ctx, String prjId, float version)throws BOSException, EASBizException
    {
    	EntityViewInfo view=new EntityViewInfo();
    	view.setFilter(new FilterInfo());
    	view.getSelector().add("id");
    	view.getSelector().add("project.id");
    	view.getSelector().add("project.name");
    	view.getSelector().add("project.number");
    	view.getSelector().add("project.longNumber");
    	view.getSelector().add("project.startDate");
    	view.getSelector().add("project.fullOrgUnit.id");
    	view.getSelector().add("project.fullOrgUnit.name");
    	view.getSelector().add("project.fullOrgUnit.number");

    	view.getSelector().add("entrys.schedule.id");
    	view.getFilter().getFilterItems().add(new FilterItemInfo("project.id",prjId));
    	view.getFilter().getFilterItems().add(new FilterItemInfo("version",new Float(version)));
    	
    	ScheduleVerManagerCollection verInfos=ScheduleVerManagerFactory.getLocalInstance(ctx).getScheduleVerManagerCollection(view);
    	
    	ScheduleVerManagerInfo verInfo=verInfos.get(0);
    	return getScheduleInfo(ctx, verInfo);
    }
	private IObjectValue getScheduleInfo(Context ctx,ScheduleVerManagerInfo verInfo) throws BOSException, EASBizException {
		EntityViewInfo view;
		Set scheduleIdSet=new HashSet();
    	for(Iterator iter=verInfo.getEntrys().iterator();iter.hasNext();){
    		ScheduleVerManagerEntryInfo entry=(ScheduleVerManagerEntryInfo)iter.next();
    		scheduleIdSet.add(entry.getSchedule().getId().toString());
    	}
    	//getTask
    	view=new EntityViewInfo();
    	view.setFilter(new FilterInfo());
//    	view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.project.id",prjId));
    	view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.id",scheduleIdSet,CompareType.INCLUDE));
    	if(verInfo.getVersion()>0){
    		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.state",ScheduleStateEnum.SAVED_VALUE,CompareType.NOTEQUALS));
        	view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.state",ScheduleStateEnum.SUBMITTED_VALUE,CompareType.NOTEQUALS));
    	}
    	
		view.getSelector().add("*");
		view.getSelector().add("schedule.id");
		view.getSelector().add("schedule.state");
		view.getSelector().add("schedule.adminDept.id");
		view.getSelector().add("schedule.scheduleType.id");
		view.getSelector().add("parent.id");
		view.getSelector().add("parent.wbs.id");
		view.getSelector().add("parent.wbs.name");
//		view.getSelector().add("wbs.id");
//		view.getSelector().add("wbs.name");
//		view.getSelector().add("wbs.curProject");
//		view.getSelector().add("wbs.curProject.id");
//		view.getSelector().add("wbs.curProject.name");
//		view.getSelector().add("wbs.curProject.number");
//		view.getSelector().add("wbs.taskType.id");
//		view.getSelector().add("wbs.longNumber");
//		view.getSelector().add("wbs.adminDept.id");
//		view.getSelector().add("wbs.adminDept.number");
//		view.getSelector().add("wbs.adminDept.name");
//		view.getSelector().add("wbs.respDept.id");
//		view.getSelector().add("wbs.respDept.number");
//		view.getSelector().add("wbs.respDept.name");
//		view.getSelector().add("wbs.adminPerson.id");
//		view.getSelector().add("wbs.adminPerson.number");
//		view.getSelector().add("wbs.adminPerson.name");
//		view.getSelector().add("wbs.isleaf");
		view.getSelector().add("wbs.*");
		view.getSelector().add("wbs.curProject.id");
		view.getSelector().add("wbs.curProject.name");
		view.getSelector().add("wbs.curProject.number");
		view.getSelector().add("wbs.parent.id");
		view.getSelector().add("wbs.taskType.id");
		view.getSelector().add("wbs.taskType.name");
		view.getSelector().add("wbs.taskType.longNumber");
		view.getSelector().add("wbs.adminPerson.id");
		view.getSelector().add("wbs.adminPerson.name");
		view.getSelector().add("wbs.adminPerson.number");
		view.getSelector().add("wbs.adminDept.id");
		view.getSelector().add("wbs.adminDept.name");
		view.getSelector().add("wbs.adminDept.number");		
		view.getSelector().add("wbs.respDept.id");
		view.getSelector().add("wbs.respDept.name");
		view.getSelector().add("wbs.respDept.number");
		
		view.getSelector().add("adminPerson.id");
		view.getSelector().add("adminPerson.number");
		view.getSelector().add("adminPerson.name");
		view.getSelector().add("adminDept.id");
		view.getSelector().add("adminDept.number");
		view.getSelector().add("adminDept.name");
		view.getSelector().add("adminDept.cu");
		view.getSelector().add("adminDept.cu.id");
		view.getSelector().add("planDept.id");
		view.getSelector().add("planDept.number");
		view.getSelector().add("planDept.name");
		view.getSelector().add("dependEntrys.task.id");
		view.getSelector().add("dependEntrys.task.wbs.id");
		view.getSelector().add("administrator.id");
		view.getSelector().add("administrator.name");
		view.getSelector().add("administrator.number");
		
		view.getSelector().add("manager.id");
		view.getSelector().add("manager.name");
		view.getSelector().add("manager.number");
		
		view.getSelector().add("noter.id");
		view.getSelector().add("noter.name");
		view.getSelector().add("noter.number");
		
		view.getSelector().add("dependEntrys.*");
		view.getSelector().add("dependEntrys.task.id");
		view.getSelector().add("dependEntrys.dependTask.id");
		view.getSelector().add("dependEntrys.dependTask.wbs.id");
		
		view.getSelector().add("bizType.id");
		view.getSelector().add("bizType.number");
		view.getSelector().add("bizType.name");
		
		
    	view.getSorter().add(new SorterItemInfo("wbs.longNumber"));
    	view.getSorter().add(new SorterItemInfo("wbs.index"));
    	
    	FDCScheduleTaskCollection tasks = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
    	
    	FDCScheduleInfo info=new FDCScheduleInfo();
//    	info.setId(BOSUuid.create(info.getBOSType()));
    	CurProjectInfo project = verInfo.getProject();
		info.setProject(project);
    	info.setCreateTime(new Timestamp(System.currentTimeMillis()));
    	info.setAdminDept(project.getFullOrgUnit());
    	
    	info.setOrgUnit(project.getFullOrgUnit()); //暂时设置成项目所属组织
    	
    	Date date=project.getStartDate();//以后取项目日历上的时间
    	if(date==null){
    		date=new Date();
    	}
    	info.setStartDate(date);
    	info.setViewDate(date);
    	info.setGdivlocation(369);
    	info.setRdivlocation(322);
    	info.setGversion(FDCHelper.toBigDecimal("1.12"));
    	info.setViewIndex(0);
//    	因为这个是拼装起来的FDCSchedule，所以添加基本版本
    	info.setBaseVer(verInfo);
    	//项目日历
    	ScheduleAppHelper.setCalendar(ctx,info);
    	
//    	info.put("taskEntrys", tasks);
//    	view = new EntityViewInfo();
//    	view.setFilter(new FilterInfo());
//    	view.getFilter().getFilterItems().add(new FilterItemInfo("SCHVerManager.id",verInfo.getId().toString(),CompareType.EQUALS));
//    	ProjectScheduleCollection prjSchCol = ProjectScheduleFactory.getLocalInstance(ctx).getProjectScheduleCollection(view);
//    	if(prjSchCol.size() > 0 && prjSchCol.get(0).getScheduleId() != null && prjSchCol.get(0).getScheduleId().length() > 0){
//    		info.setId(BOSUuid.read(prjSchCol.get(0).getScheduleId()));
//    	}else{
//    		info.setId(BOSUuid.create(info.getBOSType()));
//    		info.setName("项目总进度计划_"+project.getName());
//    	}
//    	FDCScheduleCollection scheduleCol = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleCollection(view);
//    	if(scheduleCol.size() > 0){
//    		info.setId(scheduleCol.get(0).getId());
//    	}else{
//    		
//    	}
    	info.setId(BOSUuid.create(info.getBOSType()));
		info.setName("项目总进度计划");
    	createTaskEntrys(ctx, info, tasks);
    	
        return info;
	}
	protected IObjectValue _getVerData(Context ctx, String verId) throws BOSException, EASBizException {
		SelectorItemCollection selector=new SelectorItemCollection();
    	selector.add("id");
    	selector.add("version");
    	selector.add("project.id");
    	selector.add("project.name");
    	selector.add("project.number");
    	selector.add("project.longNumber");
    	selector.add("project.startDate");
    	selector.add("project.fullOrgUnit.id");
    	selector.add("project.fullOrgUnit.name");
    	selector.add("project.fullOrgUnit.number");
    	selector.add("entrys.id"); 
    	selector.add("entrys.schedule.id"); 
//    	selector.add("entrys.bizType.id");
//    	selector.add("entrys.bizType.name");
//    	selector.add("entrys.bizType.number");
    	ScheduleVerManagerInfo verInfo=ScheduleVerManagerFactory.getLocalInstance(ctx).getScheduleVerManagerInfo(new ObjectUuidPK(verId), selector);
		IObjectValue scheduleInfo = getScheduleInfo(ctx, verInfo);
//		ScheduleVerManagerInfo retValue=new ScheduleVerManagerInfo();
//		retValue.put("scheduleInfo", scheduleInfo);
		return scheduleInfo;
	}
	
	private void createTaskEntrys(Context ctx,FDCScheduleInfo info,FDCScheduleTaskCollection tasks) throws BOSException,EASBizException{
    	info.getTaskEntrys().clear();
    	info.setState(ScheduleStateEnum.SAVED);
    	Map taskMap=new HashMap();
    	FDCScheduleTaskInfo task=new FDCScheduleTaskInfo();
    	Map adminDeptSpecScheduleMap = new HashMap();
    	
    	
    	/***
    	 * 如果版本为0，说明是调整中的进度，需要取得最新版本的所有的时间
    	 * 
    	 * 以便客户端，计算影响情况
    	 */
    	Map oldTaskMap = new HashMap();
    	if(info.getBaseVer().getVersion()==0){
    		EntityViewInfo view=new EntityViewInfo();
        	view.setFilter(new FilterInfo());
        	view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.project.id",info.getProject().getId().toString()));
        	view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
        	view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.version",FDCHelper.ZERO,CompareType.NOTEQUALS));
    		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.state",ScheduleStateEnum.SAVED_VALUE,CompareType.NOTEQUALS));
        	view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.state",ScheduleStateEnum.SUBMITTED_VALUE,CompareType.NOTEQUALS));
        	
        	
    		view.getSelector().add("id");
    		view.getSelector().add("start");
    		view.getSelector().add("end");
    		view.getSelector().add("duration");
    		view.getSelector().add("wbs.id");
    		view.getSelector().add("schedule.id");
        	FDCScheduleTaskCollection oldTasks = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
        	
        	for(Iterator it = oldTasks.iterator();it.hasNext();){
        		FDCScheduleTaskInfo oldTask = (FDCScheduleTaskInfo)it.next();
        		if(oldTask.getWbs().getId()!=null){
        			oldTaskMap.put(oldTask.getWbs().getId().toString(), oldTask);
        		}
        	}
    	}
    	
    	
    	
    	
    	//处理上级任务，保证上级任务是本Collecton的对象
    	
    	for(Iterator iter=tasks.iterator();iter.hasNext();){
    		task=(FDCScheduleTaskInfo)iter.next();
    		if(task.getParent()!=null){
    			task.setParent((FDCScheduleTaskInfo)taskMap.get(task.getParent().getWbs().getId()));
    		}
    		/**
    		 * 处理oldStart和oldEnd
    		 */
        	if(info.getBaseVer().getVersion()==0){
        		String wbsid = task.getWbs().getId().toString();
        		if(oldTaskMap.containsKey(wbsid)){
        			FDCScheduleTaskInfo oldTask = (FDCScheduleTaskInfo)oldTaskMap.get(wbsid);
        			task.put("myOldStartDate", oldTask.getStart());
        			task.put("myOldEndDate", oldTask.getEnd());
        			task.put("myOldDuration", Integer.valueOf(String.valueOf(oldTask.getDuration())));
        			if(info.getStartDate()==null||info.getStartDate().after(oldTask.getStart())){
            			info.setStartDate(oldTask.getStart());
            			info.put("myOldStartDate", oldTask.getStart());
            		}
        			if(info.getEndDate()==null||info.getEndDate().before(oldTask.getEnd())){
            			info.setEndDate(oldTask.getEnd());
            			info.put("myOldEndDate", oldTask.getEnd());
            		}
        		}
        		
        	}else{
        		task.put("myOldStartDate", task.getStart());
        		task.put("myOldEndDate", task.getEnd());
        		task.put("myOldDuration", Integer.valueOf(String.valueOf(task.getDuration())));
        		if(info.getStartDate()==null||info.getStartDate().after(task.getStart())){
        			info.setStartDate(task.getStart());
        			info.put("myOldStartDate", task.getStart());
        		}
    			if(info.getEndDate()==null||info.getEndDate().before(task.getEnd())){
        			info.setEndDate(task.getEnd());
        			info.put("myOldEndDate", task.getEnd());
        		}
        	}
    		task.setExpand(true);//默认展开所有节点
    		if(task.getSchedule()!=null){
    			task.setString("myScheduleId", task.getSchedule().getId().toString());
    			if(task.getSchedule().getAdminDept()!=null && 
    					task.getSchedule().getScheduleType()!=null &&
    					task.getSchedule().getScheduleType().getId().toString().equals(TaskTypeInfo.TASKTYPE_SPECIALTASK)){
    				adminDeptSpecScheduleMap.put(task.getSchedule().getAdminDept().getId().toString(), task.getSchedule().getId().toString());
    			}
    			
    			if(task.getSchedule().getState()!=null&&task.getSchedule().getState().equals(ScheduleStateEnum.EXETING)){
//    				info.setState(ScheduleStateEnum.EXETING);
    				task.put("ScheduleState",ScheduleStateEnum.EXETING);
    			}
    		}
    		task.setSchedule(info);
    		info.getTaskEntrys().add(task);
    		taskMap.put(task.getWbs().getId(), task);
    	}
    	
    	info.put("adminDeptSpecScheduleMap", adminDeptSpecScheduleMap);
    	
    	//处理WBS前置任务生成后置任务
    	for(Iterator iter=tasks.iterator();iter.hasNext();){
    		task=(FDCScheduleTaskInfo)iter.next();
			//已有任务，做对象替换
    		for(Iterator iter2=task.getDependEntrys().iterator();iter2.hasNext();){
    			FDCScheduleTaskDependInfo depend=(FDCScheduleTaskDependInfo)iter2.next();
    			depend.setTask(task);
    			depend.setDependTask((FDCScheduleTaskInfo)taskMap.get(depend.getDependTask().getWbs().getId()));
    		}

    	}
    }
	
	
	public IObjectPK _createNewVer(Context ctx,String prjId) throws BOSException,EASBizException{
		FDCScheduleInfo testSchedule=new FDCScheduleInfo();
		CurProjectInfo prj=new CurProjectInfo();
		prj.setId(BOSUuid.read(prjId));
		testSchedule.setProject(prj);
		ScheduleAppHelper.setCalendar(ctx,testSchedule);
		ScheduleCalendarInfo calendarInfo=testSchedule.getCalendar();
		//get latest ver
		EntityViewInfo view=new EntityViewInfo();
		SelectorItemCollection selector=view.getSelector();
    	selector.add("*");
    	selector.add("project.id");
    	selector.add("project.name");
    	selector.add("project.number");
    	selector.add("project.longNumber");
    	selector.add("project.startDate");
    	selector.add("project.fullOrgUnit.id");
    	selector.add("project.fullOrgUnit.name");
    	selector.add("project.fullOrgUnit.number");
    	selector.add("entrys.id"); 
    	selector.add("entrys.schedule.id"); 
    	
    	view.setFilter(new FilterInfo());
    	view.getFilter().appendFilterItem("isLatestVer", Boolean.TRUE);
    	view.getFilter().appendFilterItem("project.id", prjId);
		ScheduleVerManagerCollection coll=ScheduleVerManagerFactory.getLocalInstance(ctx).getScheduleVerManagerCollection(view);
		if(coll.size()!=1){
			throw new EASBizException(new NumericExceptionSubItem("100","版本有问题,存在多个版本最新的版本"));
		}
		ScheduleVerManagerInfo oldVerInfo=coll.get(0);
		ScheduleVerManagerInfo info=new ScheduleVerManagerInfo();
		info.setIsLatestVer(true);
		info.setProject(oldVerInfo.getProject());
		info.setCreateReason(ScheduleCreateReasonEnum.DepAdjust);
		info.setVersion(oldVerInfo.getVersion()+1);
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select min(task.fstart) as fstartDate,max(task.fend) as fendDate from T_SCH_FDCScheduleTask task ");
		builder.appendSql(" inner join T_Sch_FDCSchedule head on head.fid=task.fscheduleid ");
		builder.appendSql(" where head.fislatestver=1 and head.fprojectid=? ");
		
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		try{
			if(rowSet.next()){
				info.setStartDate(rowSet.getDate("fstartDate"));
				info.setEndDate(rowSet.getDate("fendDate"));
				info.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(info.getStartDate(), info.getEndDate(), calendarInfo));
				info.setNatureTimes(ScheduleCalendarHelper.getNatureTimes(info.getStartDate(), info.getEndDate()));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		builder.clear();
		builder.appendSql("select fid,fversion,fstate from T_Sch_FDCSchedule head ");
		builder.appendSql(" where head.fislatestver=1 and head.fprojectid=? ");
		builder.addParam(prjId);
		rowSet=builder.executeQuery();
		try{
			while(rowSet.next()){
				String id=rowSet.getString("fid");
				float version=rowSet.getFloat("fversion");
				String state=rowSet.getString("fstate");
				FDCScheduleInfo schedule=new FDCScheduleInfo();
				schedule.setId(BOSUuid.read(id));
				ScheduleVerManagerEntryInfo entry=new ScheduleVerManagerEntryInfo();
				entry.setSchedule(schedule);
				entry.setVersion(version);
				info.getEntrys().add(entry);
				if(state!=null&&state.equals(ScheduleStateEnum.EXETING_VALUE)){
					info.setState(ScheduleStateEnum.EXETING);
				}
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
		IObjectPK pk = ScheduleVerManagerFactory.getLocalInstance(ctx).addnew(info);
		
		builder.clear();
		builder.appendSql("update T_SCH_ScheduleVerManager set fisLatestVer=0 where fid=? ");
		builder.addParam(oldVerInfo.getId().toString());
		builder.execute();
		
		return pk;
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,	EASBizException {
		Set scheduleIds = new HashSet();
		EntityViewInfo view  = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		filter.getFilterItems().add(new FilterItemInfo("id",billId.toString(),CompareType.EQUALS));
		sic.add("entrys.schedule.id");
		sic.add("entrys.schedule.state");
		sic.add("entrys.schedule.number");
		sic.add("entrys.schedule.name");
		view.setFilter(filter);
		view.setSelector(sic);
		ScheduleVerManagerCollection schVerCol = getScheduleVerManagerCollection(ctx, view);
		if(schVerCol.size() > 0){
			ScheduleVerManagerInfo schVerInfo = schVerCol.get(0);
			ScheduleVerManagerEntryCollection schVerEntCol = schVerInfo.getEntrys();
			for(int i=0;i<schVerEntCol.size();i++){
				FDCScheduleInfo scheduleInfo = schVerEntCol.get(i).getSchedule();
    			if(!ScheduleStateEnum.SUBMITTED.equals(scheduleInfo.getState())){
    				if(!ScheduleStateEnum.AUDITTED.equals(scheduleInfo.getState())){
    					throw new EASBizException(new NumericExceptionSubItem("580","存在不是提交状态的计划，不能审批！"));
    				}
    			}else{
    				scheduleIds.add(scheduleInfo.getId().toString());
    			}
			}
			if(scheduleIds.size() > 0){
    			FDCScheduleFactory.getLocalInstance(ctx).audit(scheduleIds);
    		}
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("update T_SCH_ScheduleVerManager set FState=? where FID=?");
			builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
			builder.addParam(billId.toString());
			builder.executeUpdate();
		}
	}
	protected void _setAudittingStatus(Context ctx, BOSUuid billId)	throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_ScheduleVerManager set FState=? where FID=?");
		builder.addParam(ScheduleStateEnum.AUDITTING_VALUE);
		builder.addParam(billId.toString());
		builder.executeUpdate();
	}
	protected void _setSubmitStatus(Context ctx, BOSUuid billId)throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_ScheduleVerManager set FState=? where FID=?");
		builder.addParam(ScheduleStateEnum.SUBMITTED_VALUE);
		builder.addParam(billId.toString());
		builder.executeUpdate();
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_ScheduleVerManager set FState=? where FID=?");
		builder.addParam(ScheduleStateEnum.SUBMITTED_VALUE);
		builder.addParam(billId.toString());
		builder.executeUpdate();
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		FDCScheduleInfo info = (FDCScheduleInfo) model;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_ScheduleVerManager set FState=? where FID=?");
		builder.addParam(ScheduleStateEnum.SUBMITTED_VALUE);
		builder.addParam(info.getBaseVer().getId().toString());
		builder.executeUpdate();
		return new ObjectUuidPK(info.getBaseVer().getId());
	}
	
}