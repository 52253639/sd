package com.kingdee.eas.fdc.schedule.app;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.DataAccessException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskSynchronize;
import com.kingdee.eas.fdc.schedule.FDCWBSCollection;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSTree;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.util.StopWatch;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ScheduleFacadeControllerBean extends AbstractScheduleFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ScheduleFacadeControllerBean");

	protected void _reCalcParentTaskComplete(Context ctx, Set wbsIds) throws BOSException, EASBizException {
		if (wbsIds == null || wbsIds.size() == 0) {
			return;
		}
		FDCScheduleTaskSynchronize.modifyParent(ctx, wbsIds);
	}

	protected IObjectValue _getOtherDeptSchedule(Context ctx, String scheduleId) throws BOSException, EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("*");
		selector.add("scheduleType.*");
		selector.add("project.id");
		selector.add("project.longNumber");
		selector.add("project.name");
		selector.add("adminPerson.id");
		selector.add("adminPerson.name");
		selector.add("adminPerson.number");

		selector.add("adminDept.id");
		selector.add("adminDept.name");
		selector.add("adminDept.number");

		selector.add("creator.id");
		selector.add("creator.name");
		selector.add("creator.number");

		selector.add("auditor.id");
		selector.add("auditor.name");
		selector.add("auditor.number");
		selector.add("baseVer.id");

		// selector.add("dispColumns.*");
		/*
		 * selector.add("calendar.*"); selector.add("calendar.holidayEntrys.*");
		 * selector.add("calendar.weekendEntrys.*");
		 */
		FDCScheduleInfo info = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleInfo(new ObjectUuidPK(scheduleId), selector);
		// 项目日历
		ScheduleAppHelper.setCalendar(ctx, info);
		if (info.getBaseVer() == null) {
			throw new EASBizException(new NumericExceptionSubItem("120", "计划的总进度版本不存在"));
		}

		setScheduleInfo(ctx, info);
		return info;
	}

	private void setScheduleInfo(Context ctx, FDCScheduleInfo info) throws BOSException, EASBizException {
		EntityViewInfo view;
		// 取已审批的进度及自己
		String sql = "select fid from T_sch_fdcschedule where (fprojectid='" + info.getProject().getId().toString() + "' and fstate in ('" + ScheduleStateEnum.AUDITTED_VALUE + "','"
				+ ScheduleStateEnum.EXETING_VALUE + "') and fislatestver=1) or fid='" + info.getId().toString() + "'";
		// getTask
		view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		// view.getFilter().getFilterItems().add(new
		// FilterItemInfo("schedule.project.id",prjId));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.id", sql, CompareType.INNER));
		view.getSelector().add("*");
		view.getSelector().add("schedule.id");
		view.getSelector().add("schedule.state");
		view.getSelector().add("parent.id");
		view.getSelector().add("parent.wbs.id");
		view.getSelector().add("parent.wbs.name");
		view.getSelector().add("wbs.id");
		view.getSelector().add("wbs.name");
		view.getSelector().add("wbs.taskType.id");
		view.getSelector().add("wbs.longNumber");
		view.getSelector().add("wbs.isleaf");
		view.getSelector().add("adminPerson.id");
		view.getSelector().add("adminPerson.number");
		view.getSelector().add("adminPerson.name");
		view.getSelector().add("adminDept.id");
		view.getSelector().add("adminDept.number");
		view.getSelector().add("adminDept.name");
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

		view.getSorter().add(new SorterItemInfo("wbs.longNumber"));
		view.getSorter().add(new SorterItemInfo("wbs.index"));
		FDCScheduleTaskCollection tasks = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);

		// 项目日历
		ScheduleAppHelper.setCalendar(ctx, info);
		createTaskEntrys(ctx, info, tasks);
	}

	private void createTaskEntrys(Context ctx, FDCScheduleInfo info, FDCScheduleTaskCollection tasks) throws BOSException, EASBizException {
		info.getTaskEntrys().clear();
		info.setState(ScheduleStateEnum.SAVED);
		Map taskMap = new HashMap();
		FDCScheduleTaskInfo task = new FDCScheduleTaskInfo();
		// 处理上级任务，保证上级任务是本Collecton的对象
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			task = (FDCScheduleTaskInfo) iter.next();
			if (task.getParent() != null) {
				task.setParent((FDCScheduleTaskInfo) taskMap.get(task.getParent().getWbs().getId()));
			}
			task.setExpand(true);// 默认展开所有节点
			if (task.getSchedule() != null) {
				if (task.getSchedule().getId().equals(info.getId())) {
					task.setIsScheduleTask(true);
				} else {
					task.setIsScheduleTask(false);
					task.setEditable(false);// 主项任务和其他部门计划不能编辑
				}
				task.setString("myScheduleId", task.getSchedule().getId().toString());
			}
			if (task.isScheduleTask() && task.getParent() != null) {
				FDCScheduleTaskInfo parent = task.getParent();
				if (parent.isScheduleTask()) {
					// 上级是本计划的节点，则边界值相通
					task.setBoundEnd(parent.getBoundEnd());
					task.setBoundStart(parent.getBoundStart());
				} else {
					// 上级是上级计划的节点，则边界值为其开始、结束时间
					task.setBoundStart(parent.getStart());
					task.setBoundEnd(parent.getEnd());
				}
			}
			task.setSchedule(info);
			info.getTaskEntrys().add(task);
			taskMap.put(task.getWbs().getId(), task);

			if (task.isScheduleTask() && task.getParent() != null) {
				FDCScheduleTaskInfo parent = task.getParent();
				if (parent.isScheduleTask()) {
					// 上级是本计划的节点，则边界值相通
					task.setBoundEnd(parent.getBoundEnd());
					task.setBoundStart(parent.getBoundStart());
				} else {
					// 上级是上级计划的节点，则边界值为其开始、结束时间
					task.setBoundStart(parent.getStart());
					task.setBoundEnd(parent.getEnd());
				}
			}

		}

		// 处理后置任务
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			task = (FDCScheduleTaskInfo) iter.next();
			// 已有任务，做对象替换
			for (Iterator iter2 = task.getDependEntrys().iterator(); iter2.hasNext();) {
				FDCScheduleTaskDependInfo depend = (FDCScheduleTaskDependInfo) iter2.next();
				depend.setTask(task);
				depend.setDependTask((FDCScheduleTaskInfo) taskMap.get(depend.getDependTask().getWbs().getId()));
			}

		}
	}

	/**
	 * 反写明细节点及反算非明细节点的完工工程量和完工百分比
	 */
	protected void _reCalLoadFromTaskLoad(Context ctx, String wbsId) throws BOSException, EASBizException {
		Set wbsIds = new HashSet();
		wbsIds.add(wbsId);
		FDCScheduleTaskSynchronize.modifyByTaskLoad(ctx, wbsIds);
	}

	/**
	 * 重新计算费明细节点的完工工程量和完工百分比
	 * 
	 * @param ctx
	 * @param wbsId
	 * @throws BOSException
	 */
	private void reCalParentLoadFromTaskLoad(Context ctx, Set wbsIds) throws BOSException {
		if (wbsIds == null || wbsIds.size() == 0) {
			return;
		}
		// 1. get all parent wbs node sort by flongnumber desc
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select a.fid from T_SCH_FDCWBS a ,T_SCH_FDCWBS b where a.fcurProjectid=b.fcurProjectid and charindex(a.flongnumber||'!',b.flongnumber)=1 and ");
		builder.appendParam("b.fid", wbsIds.toArray());
		builder.appendSql(" order by a.flongnumber desc");

		// 2.create batch param
		IRowSet rowSet = builder.executeQuery();
		List paramList = new ArrayList();// batch param
		try {
			while (rowSet.next()) {
				String fid = rowSet.getString("fid");
				paramList.add(Arrays.asList(new Object[] { fid, fid }));
				wbsIds.add(fid);
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		// 3.update parent task node workload by batch sequence param order by
		// wbs.flongnumber desc
		StringBuffer sql = new StringBuffer();
		sql.append("	update t_sch_fdcscheduletask set fworkload=( ");
		sql.append("			select sum(fworkload) from t_sch_fdcscheduletask where fwbsid in (select fid from T_SCH_FDCWBS where  fparentid=? ");
		sql.append("	)) where fwbsid=? ");
		sql.append("	and exists (select 1 from T_sch_fdcschedule where fid=t_sch_fdcscheduletask.fscheduleid and fislatestver=1 )");
		builder.executeBatch(sql.toString(), paramList);
	}

	/**
	 * 1.工程量录入，则同步TaskLoad中的数据(注意是确认状态的)；
	 * 2.基于任务填报，同步WorkAmountEntry中的数据(注意是审批状态的)
	 * 3.基于合同填报，同步ProjectFillBillEntry中的数据(注意是审批状态的)
	 * 此处采取传入taskIds以及wbsIds的方式，其实也可以不用传入这些Id
	 * (采用in，超过2000可能会出性能中断)，直接全部更新(数据库方面效率会比较低)
	 */
	protected void _synchronizeTask(Context ctx, Set taskIds, Set wbsIds, String param) throws BOSException, EASBizException {
		if (FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT.equals(param)) {
			FDCScheduleTaskSynchronize.modifyByContract(ctx, wbsIds);
		} else if (FDCConstants.FDCSCH_PARAM_BASEONTASK.equals(param)) {
			FDCScheduleTaskSynchronize.modifyByWorkAmount(ctx, wbsIds);
		} else {
			FDCScheduleTaskSynchronize.modifyByTaskLoad(ctx, wbsIds);
		}

	}

	protected void _upateCompleteDate(Context ctx, Set wbsIds, String param) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		if (param.equals(FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT)) {

		} else if (param.equals(FDCConstants.FDCSCH_PARAM_BASEONTASK)) {
			// 因为任务工程量填报都是明细的任务项，如果达到百分百的直接反写任务的完工日期
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("update T_Sch_FDCScheduleTask set factualenddate=(");
			builder.appendSql(" select max(t_sch_workamountentry.FCOMPLETEDATE)  ");
			builder.appendSql(" from t_sch_workamountentry where t_sch_workamountentry.FWBSID=T_Sch_FDCScheduleTask.FWBSID ");
			builder.appendSql(") where ");
			builder.appendParam("fwbsid", wbsIds.toArray());
			builder.appendSql(" and fcomplete>=100 and factualenddate is null and");
			builder.appendSql("	exists (select 1 from T_sch_fdcschedule where fid=t_sch_fdcscheduletask.fscheduleid and fislatestver=1 )");
			builder.execute();

		} else {
			_reCalcParentTaskComplete(ctx, wbsIds);
		}
	}

	protected IObjectValue _importTasks(Context ctx, String scheduleId, String projectId, String taskTypeId, String parentWbsId, IObjectCollection tasks) throws BOSException, EASBizException {
		StopWatch sw = new StopWatch();
		sw.start();
		FDCScheduleInfo schedule = null;
		if (scheduleId != null) {
			try {
				schedule = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleInfo(new ObjectStringPK(scheduleId));
			} catch (ObjectNotFoundException e) {
				logger.debug(e.getMessage(), e);
			}
		}
		logger.info("getFDCScheduleInfo cost times : "+sw.getLastTime());
		FDCScheduleInfo ret = null;
		if (TaskTypeInfo.TASKTYPE_MAINTASK.equals(taskTypeId)) {
			if (schedule == null) {
				ret = importMainTasks(ctx, projectId, (FDCScheduleTaskCollection) tasks);
			} else {
				ret = importMainTasks(ctx, schedule, projectId, (FDCScheduleTaskCollection) tasks);
			}
		} else {
			if (schedule == null) {
				ret = importSpecialTasks(ctx, parentWbsId, (FDCScheduleTaskCollection) tasks);
			} else {
				ret = importSpecialTasks(ctx, schedule, parentWbsId, (FDCScheduleTaskCollection) tasks);
			}
		}
		logger.info("importTask cost times : "+sw.getLastTime());
		logger.info("importTask cost total times : "+sw.getTotalTime());
		return ret;
	}

	/**
	 * 主项计划已存在时的导入处理
	 * 
	 * @param schedule
	 * @param projectId
	 * @param tasks
	 * @throws BOSException, EASBizException 
	 */
	private FDCScheduleInfo importMainTasks(Context ctx, FDCScheduleInfo schedule, String projectId, FDCScheduleTaskCollection tasks) throws BOSException, EASBizException {
		StopWatch sw = new StopWatch();
		sw.start();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("delete from T_SCH_FDCScheduleTask where FScheduleID=");
		builder.appendParam(schedule.getId().toString());
		builder.executeUpdate();
		Map wbsId2Task = handleWBS(ctx, projectId, tasks);
		logger.info("importExistMain handle wbs time:"+sw.getLastTime());
		FDCScheduleInfo ret = FDCScheduleFactory.getLocalInstance(ctx).getScheduleInfo(new ObjectUuidPK(schedule.getId()));
		logger.info("importExistMain getSchedule time:"+sw.getLastTime());
		FDCScheduleTaskCollection newTasks = ret.getTaskEntrys();
		handleTasks(newTasks, wbsId2Task);
		logger.info("importExistMain handle task time:"+sw.getLastTime());
		FDCScheduleFactory.getLocalInstance(ctx).save(ret);
		logger.info("importExistMain save schedule time:"+sw.getLastTime());
		//计划保存后其任务的schedule会被自动设置为计划的BOSUuid，这里重新设置
		for(int i = 0; i < ret.getTaskEntrys().size(); ++i){
			ret.getTaskEntrys().get(i).put("schedule", ret);
		}
		return ret;
	}
	/**
	 * 主项计划不存在时的导入处理
	 * 
	 * @param projectId
	 * @param tasks
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private FDCScheduleInfo importMainTasks(Context ctx, String projectId, FDCScheduleTaskCollection tasks) throws BOSException, EASBizException {
		
		Map wbsId2Task = handleWBS(ctx, projectId, tasks);
		Map param = new HashMap();
		param.put("adminDeptId", tasks.get(0).getPlanDept().getId().toString());
		param.put("prjId", projectId);
		param.put("taskTypeId", TaskTypeInfo.TASKTYPE_MAINTASK);
		FDCScheduleInfo ret = FDCScheduleFactory.getLocalInstance(ctx).getNewData(param);
		FDCScheduleTaskCollection newTasks = ret.getTaskEntrys();
		handleTasks(newTasks, wbsId2Task);
		return ret;
	}
	private void handleTasks(FDCScheduleTaskCollection newTasks, Map wbsId2Task ){
		Map oldTaskId2newTask = new HashMap();
		for(int i = 0; i < newTasks.size(); ++i){
			FDCScheduleTaskInfo newTask = newTasks.get(i);
			FDCScheduleTaskInfo oldTask = (FDCScheduleTaskInfo) wbsId2Task.get(newTask.getWbs().getId().toString());
			if(oldTask != null){
				oldTaskId2newTask.put(oldTask.getId().toString(), newTask);
			}
		}
		Set keys = oldTaskId2newTask.keySet();
		for(Iterator iter = keys.iterator(); iter.hasNext(); ){
			FDCScheduleTaskInfo newTask = (FDCScheduleTaskInfo) oldTaskId2newTask.get(iter.next());
			FDCScheduleTaskInfo oldTask = (FDCScheduleTaskInfo) wbsId2Task.get(newTask.getWbs().getId().toString());
			newTask.setEnd(oldTask.getEnd());
			newTask.setStart(oldTask.getStart());
			FDCScheduleTaskDependCollection depends = oldTask.getDependEntrys();
			if(depends != null){
				for(int j = 0; j < depends.size(); ++j){
					FDCScheduleTaskDependInfo depend = depends.get(j);
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) oldTaskId2newTask.get(depend.getTask().getId().toString());
					FDCScheduleTaskInfo dependTask = (FDCScheduleTaskInfo) oldTaskId2newTask.get(depend.getDependTask().getId().toString());
					depend.setTask(task);
					depend.setDependTask(dependTask);
					newTask.getDepends().add(depend);
				}
			}
		}
	}
	private Map handleWBS(Context ctx, String projectId, FDCScheduleTaskCollection tasks) throws BOSException{
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("delete from T_SCH_FDCWBS where FCURPROJECTID=");
		builder.appendParam(projectId);
		builder.executeUpdate();

		TaskTypeInfo taskType = new TaskTypeInfo();
		taskType.setId(BOSUuid.read(TaskTypeInfo.TASKTYPE_MAINTASK));
		return addWBS(ctx,taskType, projectId, null, tasks);
	}
	private Map addWBS(Context ctx, TaskTypeInfo taskType, String projectId, FDCWBSInfo parentWBS, FDCScheduleTaskCollection tasks)throws BOSException{
		CurProjectInfo curProject = null;
		if(parentWBS != null){
			curProject = parentWBS.getCurProject();
			projectId = curProject.getId().toString();
		}else{
			curProject = new CurProjectInfo();
			curProject.setId(BOSUuid.read(projectId));
		}
		FDCWBSCollection wbsCol = new FDCWBSCollection();
		Map taskId2Wbs = new HashMap();
		Map wbsId2Task = new HashMap();
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		for (int i = 0; i < tasks.size(); ++i) {
			FDCScheduleTaskInfo task = tasks.get(i);
			FDCWBSInfo wbs = new FDCWBSInfo();
			wbsCol.add(wbs);
			wbs.setId(BOSUuid.create(wbs.getBOSType()));
			taskId2Wbs.put(task.getId().toString(), wbs);
			wbsId2Task.put(wbs.getId().toString(), task);
			wbs.setCurProject(curProject);
			wbs.setName(task.getName());
			wbs.setIsEnabled(true);
			if(taskType.getId().toString().equals(TaskTypeInfo.TASKTYPE_MAINTASK)){
				wbs.setAdminDept(task.getPlanDept());
			}
			wbs.setTaskType(taskType);
			wbs.setEstimateDays(task.getNatureTimes().intValue());
			wbs.setCreator(task.getCreator());
			wbs.setCreateTime(ts);
			wbs.setCU(task.getCU());
			wbs.setLastUpdateUser(task.getLastUpdateUser());
			wbs.setLastUpdateTime(ts);
			wbs.setIsFromTemplate(false);
			wbs.setIsUnVisible(false);
			if(parentWBS != null){
				wbs.setAdminPerson(parentWBS.getAdminPerson());
				wbs.setRespDept(parentWBS.getRespDept());
			}
			if (task.getParent() != null) {
				wbs.setParent((FDCWBSInfo) taskId2Wbs.get(task.getParent().getId().toString()));
			}else{
				wbs.setParent(parentWBS);
			}
		}
		FDCWBSTree wbsTree = null;
		if(parentWBS == null){
			wbsTree = FDCWBSTree.getTreeFromCollection(wbsCol, projectId);
		}else{
			wbsTree = FDCWBSTree.getTreeFromDB(ctx, projectId);
			wbsTree.addChildren( wbsCol);
		}
		wbsTree.reCalculateCode();
		IORMappingDAO dao = ORMappingDAO.getInstance(wbsCol.get(0).getBOSType(), ctx, getConnection(ctx));
		for (int i = 0; i < wbsCol.size(); ++i) {
			dao.addNewBatch(wbsCol.get(i));
		}
		dao.executeBatch();
		return wbsId2Task;
	}
	/**
	 * 专项计划不存在时的导入处理
	 * 
	 * @param parentWbsId
	 * @param tasks
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private FDCScheduleInfo importSpecialTasks(Context ctx, String parentWbsId, FDCScheduleTaskCollection tasks) throws EASBizException, BOSException {
		FDCWBSInfo parentWBS = FDCWBSFactory.getLocalInstance(ctx).getFDCWBSInfo(new ObjectStringPK(parentWbsId));
		TaskTypeInfo taskType = new TaskTypeInfo();
		taskType.setId(BOSUuid.read(TaskTypeInfo.TASKTYPE_SPECIALTASK));
		Map wbsId2Task = addWBS(ctx,taskType, null, parentWBS, tasks);
		Map param = new HashMap();
		param.put("adminDeptId", tasks.get(0).getPlanDept().getId().toString());
		param.put("prjId", parentWBS.getCurProject().getId().toString());
		param.put("taskTypeId", TaskTypeInfo.TASKTYPE_SPECIALTASK);
		FDCScheduleInfo ret = FDCScheduleFactory.getLocalInstance(ctx).getNewData(param);
		FDCScheduleTaskCollection newTasks = ret.getTaskEntrys();
		handleTasks(newTasks, wbsId2Task);
		return ret;
	}

	/**
	 * 专项计划已存在时的导入处理
	 * 
	 * @param schedule
	 * @param parentWbsId
	 * @param tasks
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private FDCScheduleInfo importSpecialTasks(Context ctx, FDCScheduleInfo schedule, String parentWbsId, FDCScheduleTaskCollection tasks) throws EASBizException, BOSException {
		StopWatch sw = new StopWatch();
		sw.start();
		FDCWBSInfo parentWBS = getParentWBS(ctx, parentWbsId);
		TaskTypeInfo taskType = new TaskTypeInfo();
		taskType.setId(BOSUuid.read(TaskTypeInfo.TASKTYPE_SPECIALTASK));
		Map wbsId2Task = addWBS(ctx,taskType, null, parentWBS, tasks);
		logger.info("importExistSpecial handle wbs time:"+sw.getLastTime());
		FDCScheduleInfo ret = FDCScheduleFactory.getLocalInstance(ctx).getScheduleInfo(new ObjectUuidPK(schedule.getId()));
		logger.info("importExistSpecial getSchedule time:"+sw.getLastTime());
		FDCScheduleTaskCollection newTasks = ret.getTaskEntrys();
		handleTasks(newTasks, wbsId2Task);
		logger.info("importExistSpecial handle task time:"+sw.getLastTime());
		FDCScheduleTaskCollection tasksCopy = new FDCScheduleTaskCollection();
		tasksCopy.addCollection(ret.getTaskEntrys());
		ret.getTaskEntrys().clear();
		for(int i = 0; i < tasksCopy.size(); ++i){
			FDCScheduleTaskInfo task = tasksCopy.get(i);
			if(task.isScheduleTask()){
				ret.getTaskEntrys().add(task);
			}
		}
		FDCScheduleFactory.getLocalInstance(ctx).save(ret);
		logger.info("importExistSpecial save schedule time:"+sw.getLastTime());
		//计划保存后其任务的schedule会被自动设置为计划的BOSUuid，这里重新设置
		for(int i = 0; i < ret.getTaskEntrys().size(); ++i){
			ret.getTaskEntrys().get(i).put("schedule", ret);
		}
		ret.getTaskEntrys().clear();
		ret.getTaskEntrys().addCollection(tasksCopy);
		return ret;
	}
	
	private FDCWBSInfo getParentWBS(Context ctx, String parentWbsId) throws EASBizException, BOSException{
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("adminPerson");
		selector.add("adminPerson.id");
		selector.add("adminPerson.name");
		selector.add("respDept");
		selector.add("respDept.id");
		selector.add("respDept.name");
		selector.add("curProject");
		selector.add("curProject.id");
		return FDCWBSFactory.getLocalInstance(ctx).getFDCWBSInfo(new ObjectStringPK(parentWbsId), selector);
	}


}