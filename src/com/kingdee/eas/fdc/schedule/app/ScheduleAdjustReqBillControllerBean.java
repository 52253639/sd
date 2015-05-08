package com.kingdee.eas.fdc.schedule.app;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.backport.Arrays;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustPrefixTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillInfo;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustTaskEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleCreateReasonEnum;
import com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerFactory;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo;
import com.kingdee.eas.fdc.schedule.TaskAdjustManager;
import com.kingdee.eas.fdc.schedule.TaskAdjustManager.TaskAdjustManagerItem;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ScheduleAdjustReqBillControllerBean extends AbstractScheduleAdjustReqBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ScheduleAdjustReqBillControllerBean");
    protected void _createNewVerData(Context ctx, IObjectValue model)throws BOSException, EASBizException
    {
    	ScheduleAdjustReqBillInfo info=(ScheduleAdjustReqBillInfo)model;
    	FDCScheduleInfo scheduleInfo=(FDCScheduleInfo)info.get("schedule");
    	TaskAdjustManager taskList=new TaskAdjustManager(scheduleInfo);
    	taskList.setAdjustPrefixTask(true);
		for(Iterator iter=info.getAdjustEntrys().iterator();iter.hasNext();){
			ScheduleAdjustTaskEntryInfo entry=(ScheduleAdjustTaskEntryInfo)iter.next();
			taskList.updateItem(entry.getTask().getId().toString(), entry);
		}
		
		for(Iterator iter=info.getNewTaskEntrys().iterator();iter.hasNext();){
			ScheduleNewTaskEntryInfo entry=(ScheduleNewTaskEntryInfo)iter.next();
			//create wbs node
			//调整单此时已有WBSID，但是没有对应的WBS，此处新生成WBS
			//若已有wbs，则不重新建立，可能导致的问题是一个wbs对应多个扩展属性
			
			FDCWBSInfo wbs=null;
			if(entry.getWbs()!=null){
				wbs=entry.getWbs();
			}else{
				wbs=new FDCWBSInfo();
				wbs.setId(BOSUuid.create(wbs.getBOSType()));
			}
    		wbs.setNumber(entry.getNumber()!=null&&entry.getNumber().indexOf('.')>0?entry.getNumber().substring(entry.getNumber().lastIndexOf('.')+1):entry.getNumber());
    		wbs.setLongNumber(entry.getLongNumber());
    		wbs.setName(entry.getName());
    		wbs.setLevel(entry.getLevel());
    		wbs.setIsLeaf(true);
    		wbs.setAdminDept(entry.getAdminDept());
    		wbs.setRespDept(entry.getRespDept());
    		wbs.setAdminPerson(entry.getAdminPerson());
    		wbs.setCurProject(info.getProject());
    		wbs.setTaskType(entry.getParentTask().getWbs().getTaskType());
    		wbs.setParent(entry.getParentTask().getWbs());
    		wbs.setEstimateDays(entry.getEffectTimes().intValue());
    		wbs.setIsEnabled(true);
    		/*if(entry.getWbs() != null && entry.getWbs().getId() != null){
    			FDCScheduleTaskExtFactory.getLocalInstance(ctx).copyFromAdjuestBill(entry.getWbs().getId().toString(), wbs.getId().toString());
    		}*/
    		entry.setWbs(wbs);
    		
			taskList.addNewItem(entry.getParentTask()!=null?entry.getParentTask().getId().toString():null, entry);
		}
		taskList.adjustTasks();
		
    	//找出变化的计划 放到changeTaskMap
		Set changeScheduleSet=new HashSet(); 
		Map newTaskMap=new HashMap(); //新增任务列表,按计划做隔离
		Map changeTaskMap=new HashMap();  //变化的任务列表:包括调整的以及影响的
		Set soureceAdjustScheduleIds=new HashSet(); //自己任务调整引起的
		for(int i=0;i<taskList.size();i++){
			TaskAdjustManagerItem item=taskList.get(i);
			if(item.getNewTask()!=null){
				//change
				String myScheduleId=null;
				if(item.getOldTask()!=null){
					myScheduleId=item.getOldTask().getString("myScheduleId");
					item.getOldTask().setStart(item.getNewTask().getStart());
					item.getOldTask().setEnd(item.getNewTask().getEnd());
					item.getOldTask().setEffectTimes(item.getNewTask().getEffectTimes());
					changeTaskMap.put(item.getOldTask().getId(), item.getOldTask());
				}else{
					myScheduleId=item.getNewTask().getParent().getString("myScheduleId");
//					newTaskList.add(item.getNewTask());
					FDCScheduleTaskCollection tasks=(FDCScheduleTaskCollection)newTaskMap.get(myScheduleId);
					if(tasks==null){
						tasks=new FDCScheduleTaskCollection();
						newTaskMap.put(myScheduleId,tasks);
					}
					tasks.add(item.getNewTask());
				}
				changeScheduleSet.add(myScheduleId);
				if(item.getType()!=null&&!item.getType().equals(TaskAdjustManagerItem.TYPE_AFFECTTASK)){
					soureceAdjustScheduleIds.add(myScheduleId);
				}
			}
		}
		
		//将变化的数据更新形成新的计划版本
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("id",changeScheduleSet,CompareType.INCLUDE));
		view.getSelector().add("*");
		view.getSelector().add("project.id");
		view.getSelector().add("orgUnit.id");
		view.getSelector().add("creator.id");
		view.getSelector().add("auditor.id");
		view.getSelector().add("scheduleType.id");
		view.getSelector().add("adminDept.id");
		view.getSelector().add("calendar.id");
		view.getSelector().add("dispColumns.id");
		view.getSelector().add("CU.id");
		view.getSelector().add("adminPerson.id");
		view.getSelector().add("taskEntrys.*");
		view.getSelector().add("taskEntrys.schedule.id");
		view.getSelector().add("taskEntrys.parent.id");
		view.getSelector().add("taskEntrys.wbs.id");
		view.getSelector().add("taskEntrys.wbs.id");
		view.getSelector().add("taskEntrys.adminPerson.id");
		view.getSelector().add("taskEntrys.adminDept.id");
		view.getSelector().add("taskEntrys.dependEntrys.task.id");
		view.getSelector().add("taskEntrys.administrator.id");
		view.getSelector().add("taskEntrys.manager.id");
		view.getSelector().add("taskEntrys.noter.id");
		view.getSelector().add("taskEntrys.dependEntrys.*");
		view.getSelector().add("taskEntrys.dependEntrys.task.id");
		view.getSelector().add("taskEntrys.dependEntrys.task.wbs.id");
		view.getSelector().add("taskEntrys.dependEntrys.dependTask.id");
		view.getSelector().add("taskEntrys.dependEntrys.dependTask.wbs.id");
		
		FDCScheduleCollection scheduleColl=FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleCollection(view);
		Map taskMap=new HashMap(); //更新依赖关系用,用于存放生成的taskInfo
		
		Set newScheduleSet=new HashSet();
		for(int i=0;i<scheduleColl.size();i++){
			FDCScheduleInfo fdcScheduleInfo = scheduleColl.get(i);
			fdcScheduleInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
			String oldScheduleid = fdcScheduleInfo.getId().toString();
			if(soureceAdjustScheduleIds.contains(oldScheduleid)){
				fdcScheduleInfo.setCreateReason(ScheduleCreateReasonEnum.DepAdjust);
			}else{
				fdcScheduleInfo.setCreateReason(ScheduleCreateReasonEnum.OtherDepAdjust);
			}
			fdcScheduleInfo.setId(BOSUuid.create(fdcScheduleInfo.getBOSType()));
			newScheduleSet.add(fdcScheduleInfo.getId().toString());
			fdcScheduleInfo.setVersion(fdcScheduleInfo.getVersion()+1);
			//update change task 
			for(Iterator iter=fdcScheduleInfo.getTaskEntrys().iterator();iter.hasNext();){
				FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
				FDCScheduleTaskInfo newTask = (FDCScheduleTaskInfo)changeTaskMap.get(task.getId());
				if(newTask!=null){
					task.setStart(newTask.getStart());
					task.setEnd(newTask.getEnd());
					task.setEffectTimes(newTask.getEffectTimes());
					task.setDuration(newTask.getEffectTimes().intValue());
					task.setNatureTimes(ScheduleCalendarHelper.getNatureTimes(task.getStart(), task.getEnd()));
					logger.debug(task.getName()+"-----start:"+task.getStart()+"-----end:"+task.getEnd());
				}
				task.setId(BOSUuid.create(task.getBOSType()));
				taskMap.put(task.getWbs().getId(), task);
			}
			fdcScheduleInfo.setIsLatestVer(true);
			//add newTask
			FDCScheduleTaskCollection tasks=(FDCScheduleTaskCollection)newTaskMap.get(oldScheduleid);
			if(tasks!=null){
				for(Iterator iter=tasks.iterator();iter.hasNext();){
					FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
					task.setSchedule(fdcScheduleInfo);
					if(task.getLongNumber()!=null){
						task.setLongNumber(task.getLongNumber().replace('.', '!'));
					}
//					task.setParent(taskMap.get(task.getWbs().getId().toString()));
					task.setId(BOSUuid.create(task.getBOSType()));
					task.setNatureTimes(ScheduleCalendarHelper.getNatureTimes(task.getStart(), task.getEnd()));
					task.setDuration(task.getEffectTimes().intValue());
					fdcScheduleInfo.getTaskEntrys().add(task);
				}
			}
		}
		
		//update depends 
		for(int i=0;i<scheduleColl.size();i++){
			FDCScheduleInfo fdcScheduleInfo = scheduleColl.get(i);
			for(Iterator iter=fdcScheduleInfo.getTaskEntrys().iterator();iter.hasNext();){
				FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
				if(task.getDependEntrys()==null){
					continue;
				}
				for(int j=task.getDependEntrys().size()-1;j>=0;j--){
					FDCScheduleTaskDependInfo depend = (FDCScheduleTaskDependInfo)task.getDependEntrys().get(j);
					depend.setId(BOSUuid.create(depend.getBOSType()));
					FDCScheduleTaskInfo item = (FDCScheduleTaskInfo)taskMap.get(depend.getDependTask().getWbs().getId());
					if(item==null){
						//跨版本依赖且依赖的数据没有变化这种关系暂时不处理,直接去掉依赖关系
						task.getDependEntrys().removeObject(j);
					}else{
						depend.setDependTask(item);
					}
				}
			}
		}
		
		//save new ver Data
		IORMappingDAO dao = ORMappingDAO.getInstance(scheduleInfo.getBOSType(), ctx, getConnection(ctx));
		for(int i=0;i<scheduleColl.size();i++){
			dao.addNewBatch(scheduleColl.get(i));
		}
		dao.executeBatch();
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fislatestVer=0 where ");
		builder.appendParam("fid", changeScheduleSet.toArray());
		builder.execute();
		//处理新增节点的前置任务,根据WBS节点生成新的任务信息
		handleNewTask(ctx,info);
		//create new ver Data
		IObjectPK baseVerId = ScheduleVerManagerFactory.getLocalInstance(ctx).createNewVer(info.getProject().getId().toString());
		if(baseVerId!=null){
			builder.clear();
			builder.appendSql("update t_sch_fdcschedule set fbaseverid=? where ");
	    	builder.addParam(baseVerId.toString());
	    	builder.appendParam("fid", newScheduleSet.toArray());
	    	builder.execute();
		}
    }
    
    /**
     * 处理新增节点的前置任务,根据WBS节点生成新的任务信息
     */
    private void handleNewTask(Context ctx,ScheduleAdjustReqBillInfo info) throws BOSException,EASBizException{
    	if(info.getNewTaskEntrys()==null||info.getNewTaskEntrys().size()==0){
    		return;
    	}
    	
    	Set set=new HashSet();
    	//get wbs
    	CoreBaseCollection wbsColls=new CoreBaseCollection();
    	for(Iterator iter=info.getNewTaskEntrys().iterator();iter.hasNext();){
    		ScheduleNewTaskEntryInfo entry=(ScheduleNewTaskEntryInfo)iter.next();
    		for(Iterator iter2=entry.getPrefixEntrys().iterator();iter2.hasNext();){
    			ScheduleAdjustPrefixTaskInfo prefixInfo=(ScheduleAdjustPrefixTaskInfo)iter2.next();
    			set.add(prefixInfo.getPrefixTask().getWbs().getId().toString());
    		}
    		FDCWBSInfo wbs=entry.getWbs();
    		set.add(wbs.getId().toString());
    		wbsColls.add(wbs);
    	}
    	
    	String []strs=new String[set.size()];
    	int i=0;
    	for(Iterator iter=set.iterator();iter.hasNext();){
//    		System.out.println(iter.next().getClass());
    		Object obj=iter.next();
    		if(obj==null){
    			continue;
    		}
    		strs[i++]=obj.toString();
    	}
    	//create depend 
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.appendSql("select task.fwbsid,task.fid from T_SCH_FDCScheduleTask task ");
    	builder.appendSql("inner join T_SCH_FDCSchedule schedule on schedule.fid=task.fscheduleid where schedule.fislatestver=1 and ");
    	builder.appendParam("task.fwbsid", strs);
    	IRowSet rowSet=builder.executeQuery();
/*    	
    	EntityViewInfo view=new EntityViewInfo();
    	view.getSelector().add("id");
    	view.getSelector().add("wbs.id");
    	view.setFilter(new FilterInfo());
    	view.getFilter().getFilterItems().add(new FilterItemInfo("wbs.id",set,CompareType.INCLUDE));
    	view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
    	FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);*/
    	try{
    		Map tempMap=new HashMap();
    		while(rowSet.next()){
    			tempMap.put(rowSet.getString("fwbsid"), rowSet.getString("fid"));
    		}
    		
    		String sql="insert into T_SCH_FDCScheduleTaskDepend (fid,ftaskid,fdependtaskid,ftype,fdifference) values (?,?,?,?,?) ";
    		
    		List paramList=new ArrayList();
    		BOSObjectType bosType = new FDCScheduleTaskDependInfo().getBOSType();
        	for(Iterator iter=info.getNewTaskEntrys().iterator();iter.hasNext();){
        		ScheduleNewTaskEntryInfo entry=(ScheduleNewTaskEntryInfo)iter.next();
        		FDCWBSInfo wbs=(FDCWBSInfo)entry.getWbs();
        		for(Iterator iter2=entry.getPrefixEntrys().iterator();iter2.hasNext();){
        			ScheduleAdjustPrefixTaskInfo prefixInfo=(ScheduleAdjustPrefixTaskInfo)iter2.next();
        			String newId=BOSUuid.create(bosType).toString();
        			paramList.add(Arrays.asList(new Object[]{newId,tempMap.get(prefixInfo.getPrefixTask().getWbs().getId().toString()),tempMap.get(wbs.getId().toString()),prefixInfo.getType().getValue(),new Integer(prefixInfo.getDifference())}));
        		}
        	}
        	
        	builder.executeBatch(sql, paramList);
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	
      	//create new task
    	if(wbsColls.size()>0){
/*    		for(int j=0;j<wbsColls.size();j++){
    		}
*/    		FDCWBSFactory.getLocalInstance(ctx).addnew(wbsColls);
    	}
    	
    }
	protected IObjectValue _getNewData(Context ctx, Map param) throws BOSException, EASBizException {
		String prjId=(String)param.get("projectId");
		if(prjId==null){
			throw new NullPointerException("project id can't be null");
		}
		checkIsScheduleClose(ctx,null,prjId);
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		//本项目有未审批的计划不让新增
		builder.appendSql("select top 1 1 from T_sch_scheduleadjustreqbill ");
		builder.appendSql("where fprojectid=? and fstate<>? ");
		builder.addParam(prjId);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		if(builder.isExist()){
			throw new EASBizException(new NumericExceptionSubItem("100", "本项目存在未审批的进度计划调整单,不能新增"));
		}

		builder.clear();
		builder.appendSql("select fid from T_SCH_ScheduleVerManager where fprojectid=? and fislatestVer=1");
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		String verId=null;
		try{
			if(rowSet.next()){
				verId=rowSet.getString("fid");
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
		if(verId==null){
			throw new EASBizException(new NumericExceptionSubItem("100", "没有最新的进度数据，不能新增调整申请单！"));
		}
		FDCScheduleInfo scheduleInfo = getScheduleInfo(ctx, verId);
		
		ScheduleAdjustReqBillInfo info=new ScheduleAdjustReqBillInfo();
		info.setCreateTime(new Timestamp(System.currentTimeMillis()));
		info.put("schedule", scheduleInfo);
		CurProjectInfo prj=new CurProjectInfo();
		prj.setId(BOSUuid.read(prjId));
		info.setProject(prj);
		
		ScheduleVerManagerInfo verInfo=new ScheduleVerManagerInfo();
		verInfo.setId(BOSUuid.read(verId));
		info.setBaseVer(verInfo);
		
		return info;
	}
	protected IObjectValue _getValue2(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException {
		selector.add("baseVer.id");
		ScheduleAdjustReqBillInfo info = getScheduleAdjustReqBillInfo(ctx, pk, selector);
		info.put("schedule", getScheduleInfo(ctx, info.getBaseVer().getId().toString()));
		return info;
	}
	
	private FDCScheduleInfo getScheduleInfo(Context ctx,String verId) throws EASBizException, BOSException{
		FDCScheduleInfo info = ScheduleVerManagerFactory.getLocalInstance(ctx).getVerData(verId);
		return info;
	}
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		checkIsScheduleClose(ctx,billId,null);
		super._audit(ctx, billId);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("id"));
		selector.add(new SelectorItemInfo("number"));
		selector.add(new SelectorItemInfo("name"));
		selector.add(new SelectorItemInfo("adjustEntrys.*"));
		selector.add(new SelectorItemInfo("adjustEntrys.task.*"));
		selector.add(new SelectorItemInfo("newTaskEntrys.*"));
		selector.add(new SelectorItemInfo("newTaskEntrys.parentTask.*"));
		selector.add(new SelectorItemInfo("newTaskEntrys.parentTask.wbs.id"));
		selector.add(new SelectorItemInfo("newTaskEntrys.parentTask.wbs.taskType.id"));
		selector.add(new SelectorItemInfo("newTaskEntrys.adminDept.id"));
		selector.add(new SelectorItemInfo("newTaskEntrys.respDept.id"));
		selector.add(new SelectorItemInfo("newTaskEntrys.adminPerson.id"));
		selector.add("newTaskEntrys.prefixEntrys.*");
		selector.add("newTaskEntrys.prefixEntrys.prefixTask.id");
		selector.add("newTaskEntrys.prefixEntrys.prefixTask.wbs.id");
		selector.add("newTaskEntrys.prefixEntrys.prefixTask.name");
		selector.add("newTaskEntrys.prefixEntrys.prefixTask.longNumber");
		selector.add("newTaskEntrys.prefixEntrys.prefixTask.number");
		selector.add("newTaskEntrys.wbs.id");
		selector.add(new SelectorItemInfo("project.id"));
		selector.add(new SelectorItemInfo("baseVer.id"));
		IObjectValue info = _getValue2(ctx, new ObjectUuidPK(billId), selector);
		
		// create new date
		_createNewVerData(ctx,info);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param billId 调整单ID
	 * @throws EASBizException 
	 */
	protected void checkIsScheduleClose(Context ctx,BOSUuid billId,String prjId) throws BOSException,EASBizException{
		//关闭之后不允许审批
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		if(prjId!=null){
			builder.appendSql("select 1 from t_sch_fdcschedule where fprojectid =? and fislatestver=1 and fstate=? ");
			builder.addParam(prjId);
		}else{
			builder.appendSql("select 1 from t_sch_fdcschedule where fprojectid in (select fprojectid from T_sch_scheduleadjustReqbill where fid=?) and fislatestver=1 and fstate=? ");
			builder.addParam(billId.toString());
		}
		builder.addParam(ScheduleStateEnum.CLOSED_VALUE);
		if(builder.isExist()){
			throw new EASBizException(new NumericExceptionSubItem("111", "工程项目中存在已关闭的计划不能执行此操作!"));
		}
	}
}