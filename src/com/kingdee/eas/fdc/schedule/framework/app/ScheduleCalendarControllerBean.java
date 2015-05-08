package com.kingdee.eas.fdc.schedule.framework.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sourceforge.ganttproject.calendar.GPCalendarActivity;
import net.sourceforge.ganttproject.calendar.ScheduleCalendarImpl;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.app.ScheduleAppHelper;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ScheduleCalendarControllerBean extends
		AbstractScheduleCalendarControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.schedule.framework.app.ScheduleCalendarControllerBean");

	protected Map _getCalendar(Context ctx, String calendarId)
			throws BOSException, EASBizException {
		Map map = new HashMap();
		ScheduleCalendarInfo calendarInfo = new ScheduleCalendarInfo();
		CurProjectInfo project = new CurProjectInfo();
		calendarInfo = ScheduleCalendarFactory.getLocalInstance(ctx)
				.getScheduleCalendarInfo(
						new ObjectUuidPK(BOSUuid.read(calendarId)));
		map.put("calendarInfo", calendarInfo);
		if (calendarInfo.getObjectId() != null) {
			project = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(
							new ObjectUuidPK(BOSUuid.read(calendarInfo
									.getObjectId())));
			;
		}
		map.put("projectInfo", project);
		return map;
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		try {
			verify(model, ctx);
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		super._submit(ctx, model);
		ScheduleCalendarInfo info = (ScheduleCalendarInfo) model;
		info.isIsSys();
		BOSUuid id = info.getId();

		String strID = id.toString();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_sch_schedulecalendar set fisSys = ? where fid= ?");
		// 是默认日历
		if (info.isIsSys()) {
			Integer i = new Integer(1);
			builder.addParam(i);
			builder.addParam(id.toString());
			// 默认项目日历只能有一个，所以必须将以前是的修改成不是。
			FDCSQLBuilder b1 = new FDCSQLBuilder(ctx);
			b1
					.appendSql("update t_sch_schedulecalendar set fisSys = 0 where fisSys= 1");
			b1.executeUpdate();
		} else {
			// 不是默认日历
			Integer i = new Integer(0);
			builder.addParam(i);
			builder.addParam(id.toString());
		}
		builder.executeUpdate();
		return null;
	}

	private void verify(IObjectValue model, Context ctx)
			throws EASBizException, BOSException, SQLException {
		ScheduleCalendarInfo calendarInfo = (ScheduleCalendarInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", calendarInfo.getName(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("number", calendarInfo.getNumber(), CompareType.EQUALS));
		if (calendarInfo.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", calendarInfo.getId().toString(), CompareType.NOTEQUALS));
			filter.setMaskString("(#0 or #1) and #2");
		} else {
			filter.setMaskString("#0 or #1");
		}
		if (FDCHelper.isEmpty(calendarInfo.getId())) {
			if (_exists(ctx, filter)) {
				throw new EASBizException(new NumericExceptionSubItem("0011", "编码或名称重复！"));
			}
		}
		//一个项目只能有一个标准日历
		if(calendarInfo.getObjectId() != null){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select count(*) from T_SCH_ScheduleCalendar where FObjectID = ? ");
			if(calendarInfo.getId() != null){
				builder.appendSql(" and fobjectid <> ?");
				builder.addParam(calendarInfo.getId().toString());
			}
			builder.addParam(calendarInfo.getObjectId());
			IRowSet rs = builder.executeQuery();
			int count = 0;
			while(rs.next()){
				count = rs.getInt(1);
			}
			if(count>0){
				throw new EASBizException(new NumericExceptionSubItem("0012", "一个项目只能存在一个日历！"));
			}
		}
		
		//整个系统必须有一个默认日历
		if(!calendarInfo.isIsSys()){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select count(*) from t_sch_ScheduleCalendar where fisSys = 1 ");
			if (calendarInfo.getId() != null) {
				builder.appendSql("and fid <> ?");
				builder.addParam(calendarInfo.getId().toString());
			}
			IRowSet rs = builder.executeQuery();
			int count = 0;
			rs =builder.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
			
			if(count==0){
				throw new EASBizException(new NumericExceptionSubItem("0013", "全集团必须有一个默认日历！"));
			}
		}
		
		// ScheduleCalendarInfo calendarInfo = (ScheduleCalendarInfo) model;
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(
		// new FilterItemInfo("name", calendarInfo.getName(),
		// CompareType.EQUALS));
		//		
		// if (calendarInfo.getId() != null) {
		// filter.getFilterItems().add(new FilterItemInfo("id",
		// calendarInfo.getId().toString(), CompareType.NOTEQUALS));
		// filter.setMaskString("#0 and #1");
		// }
		// if (_exists(ctx, filter)) {
		// throw new EASBizException(new NumericExceptionSubItem("0011",
		// "名称不能重复！"));
		// }
		//		
		// FilterInfo filter2 = new FilterInfo();
		// filter2.getFilterItems().add(
		// new FilterItemInfo("number", calendarInfo.getNumber(),
		// CompareType.EQUALS));
		// if (calendarInfo.getId() != null) {
		// filter2.getFilterItems().add(
		// new FilterItemInfo("id", calendarInfo.getId().toString(),
		// CompareType.NOTEQUALS));
		// filter2.setMaskString("(#0 and #1");
		// }
		// if (_exists(ctx, filter2)) {
		// throw new EASBizException(new NumericExceptionSubItem("0011",
		// "编码不能重复！"));
		// }

	}

	/**
	 * 取得项目默认日历
	 * @throws EASBizException 
	 */
	protected IObjectValue _getDefaultCal(Context ctx, String prjID)
			throws BOSException, EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("holidayEntrys.*");
		sic.add("weekendEntrys.*");
		// 如果工程项目为空，则直接返回系统默认日历
		if (FDCHelper.isEmpty(prjID)) {
			return getSysDefaultCal(ctx, sic);
		}
        // 如果工程项目不为空，则返回项目日历
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("objectId", prjID));
//		filter.getFilterItems().add(new FilterItemInfo("isSys", Boolean.TRUE));
		view.setFilter(filter);
	
		ScheduleCalendarCollection col = getScheduleCalendarCollection(ctx,
				view);
		if (col != null && col.size() > 0) {
			return col.get(0);
		} else {
			
			return getSysDefaultCal(ctx, sic);
			
//			return null;
		}
	}

	private IObjectValue getSysDefaultCal(Context ctx,
			SelectorItemCollection sic) throws BOSException, EASBizException {
		// 项目没默认日历，则返回集团默认日历（周六日休息）
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isSys",Boolean.TRUE));
		view.setFilter(filter);
		ScheduleCalendarCollection cols = ScheduleCalendarFactory.getLocalInstance(ctx).getScheduleCalendarCollection(view);
		if(cols.isEmpty()){
			throw new EASBizException(new NumericExceptionSubItem("114","当前系统没有设置默认日历，请在以下进度管理的基础资料中增加日历，并设置为默认日历！"));
		}
		return cols.getObject(0);
//		return getScheduleCalendarInfo(ctx, new ObjectUuidPK(
//				"L5WgOM5+TDCr/oCKWmYL0IclCXs="), sic);
	}

	protected boolean _isquote(Context ctx, String[] idstr) throws BOSException {
		// 判断是否被引用
		for (int i = 0; i < idstr.length; i++) {
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			// sql.appendSql(
			// " select distinct head.FID as id,prj.FName_l2 as name ");
			// sql.appendSql(" from T_SCH_FDCScheduleTask as task ");
			// sql.appendSql(" left join T_SCH_FDCSchedule as head ");
			// sql.appendSql(" on head.FID = task.FScheduleID ");
			// sql.appendSql(" left join T_FDC_CurProject as prj ");
			// sql.appendSql(" on prj.FID = head.FProjectID ");
			// sql.appendSql(" where task.FScheduleID = ? ");
			// String param = idstr[i].toString();
			// sql.addParam(param);
			sql
					.appendSql("select * from t_sch_fdcscheduletask where FCalendarID = ?");
			sql.addParam(idstr[i]);
			IRowSet set = sql.executeQuery();
			// 如果有返回行 则证明被引用
			try {
				if (set.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 提交并更新了日历以后，需要同时更新使用了该日历的计划<br>
	 * 
	 * 传入所有需要更新的进度计划id<br>
	 * 则循环每个计划的任务分录，当他有其他日历时，不改变<br>
	 * 当他是本日历，或者日历为空且当前日历是项目默认日历且计划的项目就是当前项目，更新之<br>
	 * 更新步骤如下：<br>
	 * 1、首先将其本身开始、结束、有效工期、自然工期计算，<br>
	 * 开始结束只根据周末浮动，再根据开始结束计算工期<br>
	 * 2、然后更新其所有后置任务，用搭接关系确定后置任务时间<br>
	 * 由于必然会遍历所有任务，所以更新后置任务不必循环迭代<br>
	 * 3、然后更新其所有上级任务，使开始时间、结束时间不小于其期间<br>
	 * 
	 * 所有的更新最终都是更新taskEntrys这个集合，最后使用这个集合循环update数据库
	 * 
	 * @author emanon
	 */
	protected void _reCalculateSchedule(Context ctx, Set scheduleIDSet,
			IObjectValue calendar) throws BOSException,EASBizException{
		// 1.先将修改后的日历提交
	
		_submit(ctx, calendar);
	
		// _submit(ctx, calendar);

		// 2.然后更新计划
		if (scheduleIDSet == null || scheduleIDSet.isEmpty()) {
			return;
		}
		// 拿到当前日历的对象

		ScheduleCalendarInfo cal = (ScheduleCalendarInfo) calendar;
		ScheduleCalendarImpl impl = new ScheduleCalendarImpl(cal);
		// 日历的ID
		String calID = cal.getId().toString();
		// 日历的工程项目
		String prjID = cal.getObjectId();
		// 日历是否是工程项目默认日历
		boolean isDefault = cal.isIsSys();

		// 取得需更新计划的集合
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("project.id");
		sic.add("project.number");
		sic.add("project.name");
		sic.add("taskEntrys.id");
		sic.add("taskEntrys.isLeaf");
		sic.add("taskEntrys.longNumber");
		sic.add("taskEntrys.start");
		sic.add("taskEntrys.end");
		sic.add("taskEntrys.natureTimes");
		sic.add("taskEntrys.effectTimes");
		sic.add("taskEntrys.parent.id");
		sic.add("taskEntrys.dependEntrys.id");
		sic.add("taskEntrys.dependEntrys.type");
		sic.add("taskEntrys.dependEntrys.difference");
		// sic.add("taskEntrys.dependEntrys.hardness");
		sic.add("taskEntrys.dependEntrys.dependTask.id");
		// sic.add("taskEntrys.dependEntrys.dependTask.isLeaf");
		// sic.add("taskEntrys.dependEntrys.dependTask.longNumber");
		// sic.add("taskEntrys.dependEntrys.dependTask.start");
		// sic.add("taskEntrys.dependEntrys.dependTask.end");
		// sic.add("taskEntrys.dependEntrys.dependTask.natureTimes");
		// sic.add("taskEntrys.dependEntrys.dependTask.effectTimes");
		sic.add("taskEntrys.scheduleCalendar.id");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", scheduleIDSet, CompareType.INCLUDE));
		view.setFilter(filter);
		FDCScheduleCollection scheduleCol = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleCollection(view);

		// 逐个更新计划
		for (int i = 0; i < scheduleCol.size(); i++) {
			FDCScheduleInfo scheduleInfo = scheduleCol.get(i);
			String curPrjID = scheduleInfo.getProject().getId().toString();
			// 逐个更新分录
			FDCScheduleTaskCollection taskEntrys = scheduleInfo.getTaskEntrys();
			Map updateMap = new HashMap();
			logger.info("start to init");
			for (int j = 0; j < taskEntrys.size(); j++) {
				FDCScheduleTaskInfo taskInfo = taskEntrys.get(j);

				String curCalID = null;
				if (taskInfo.getCalendar() != null) {
					curCalID = taskInfo.getCalendar().getId().toString();
				}
				// 本日历 或者 当前日历是默认日历 &&日历为空&&计划的项目是当前项目
				boolean isNeetUpdate = (calID.equals(curCalID)) || (isDefault && curCalID == null && curPrjID.equals(prjID));
				if (isNeetUpdate) {
					// 1、首先将其本身开始、结束、有效工期、自然工期计算
					// 将需要修改的任务分录先设置该日历对象
					taskInfo.setCalendar(cal);
					calcSelf(impl, taskInfo, updateMap);
					// 2、然后更新其所有后置任务，用搭接关系确定后置任务时间
					updateDepend(impl, taskInfo, taskEntrys, updateMap);
					// 3、然后更新其所有上级任务，使开始时间、结束时间不小于其期间
					updateSuper(taskInfo, taskEntrys, updateMap);
				}
			}
			// 写到数据库
			logger.info("start update DB");
			updateToDB(ctx, updateMap, scheduleInfo);
			logger.info(" update DB finish");
		}
	}

	/**
	 * 更新其所有后置任务，用搭接关系确定后置任务时间<br>
	 * 由于必然会遍历所有任务，所以更新后置任务不必循环迭代<br>
	 * 用后置任务的ID从taskEntrys里面找，没有说明是别的项目计划的task，不更新
	 * 
	 * @param impl
	 * @param taskInfo
	 * @param taskEntrys
	 * @param updateMap
	 */
	private void updateDepend(ScheduleCalendarImpl impl, FDCScheduleTaskInfo taskInfo, FDCScheduleTaskCollection taskEntrys, Map updateMap) {
		FDCScheduleTaskDependCollection dependEntrys = taskInfo.getDependEntrys();
		FDCScheduleTaskInfo tmpInfo;
		for (int i = 0; i < dependEntrys.size(); i++) {
			FDCScheduleTaskDependInfo dependInfo = dependEntrys.get(i);
			TaskLinkTypeEnum linkType = dependInfo.getType();
			int difference = dependInfo.getDifference();
			for (int j = 0; j < taskEntrys.size(); j++) {
				String curID = taskEntrys.get(j).getId().toString();
				if (curID.equals(dependInfo.getId().toString())) {
					tmpInfo = taskEntrys.get(j);
					Date start = tmpInfo.getStart();
					Date end = tmpInfo.getEnd();
					if (linkType.equals(TaskLinkTypeEnum.FINISH_START)) {
						Date preEnd = taskInfo.getEnd();
						Date newStart = FDCDateHelper.addDays(preEnd, difference);
						newStart = impl.findClosestWorkingTime(newStart);
						int diff = FDCDateHelper.getDiffDays(start, newStart);
						Date newEnd = FDCDateHelper.addDays(end, diff);
						setTask(impl, tmpInfo, newStart, newEnd);
					} else if (linkType.equals(TaskLinkTypeEnum.START_START)) {
						Date preStart = taskInfo.getStart();
						Date newStart = FDCDateHelper.addDays(preStart, difference);
						newStart = impl.findClosestWorkingTime(newStart);
						int diff = FDCDateHelper.getDiffDays(start, newStart);
						Date newEnd = FDCDateHelper.addDays(end, diff);
						setTask(impl, tmpInfo, newStart, newEnd);
					} else if (linkType.equals(TaskLinkTypeEnum.FINISH_FINISH)) {
						Date preEnd = taskInfo.getEnd();
						Date newEnd = FDCDateHelper.addDays(preEnd, difference);
						newEnd = impl.findClosestWorkingTime(newEnd);
						int diff = FDCDateHelper.getDiffDays(end, newEnd);
						Date newStart = FDCDateHelper.addDays(start, diff);
						setTask(impl, tmpInfo, newStart, newEnd);
					}
					updateMap.put(tmpInfo.getId().toString(), tmpInfo);
					// // 更新好后设置回分录
					// taskEntrys.set(j, tmpInfo);
				}
			}
		}
	}

	/**
	 * 首先将其本身开始、结束、有效工期、自然工期计算<br>
	 * 开始结束只根据周末浮动，再根据开始结束计算工期
	 * 
	 * @param impl
	 * @param taskInfo
	 * @param updateMap
	 */
	private void calcSelf(ScheduleCalendarImpl impl, FDCScheduleTaskInfo taskInfo, Map updateMap) {
		Date newStart = impl.findClosestWorkingTime(taskInfo.getStart());
		Date newEnd = impl.findClosestWorkingTime(taskInfo.getEnd());
		setTask(impl, taskInfo, newStart, newEnd);
		updateMap.put(taskInfo.getId().toString(), taskInfo);
	}

	/**
	 * 对一个task设置新的开始结束日期，并据此计算新的有效、自然工期
	 * 
	 * @param impl
	 * @param taskInfo
	 * @param newStart
	 * @param newEnd
	 */
	private void setTask(ScheduleCalendarImpl impl, FDCScheduleTaskInfo taskInfo, Date newStart, Date newEnd) {
		BigDecimal newNatureTimes = FDCHelper.ZERO;
		BigDecimal newEffectTimes = FDCHelper.ZERO;
		List activities = impl.getActivities(newStart, newEnd);
		for (int i = 0; i < activities.size(); i++) {
			GPCalendarActivity nextCalendarActivity = (GPCalendarActivity) activities.get(i);
			int diff = FDCDateHelper.getDiffDays(nextCalendarActivity.getStart(), nextCalendarActivity.getEnd());
			BigDecimal len = new BigDecimal(diff);
			if (nextCalendarActivity.isWorkingTime()) {
				newEffectTimes = newEffectTimes.add(len);
			}
			newNatureTimes = newNatureTimes.add(len);
		}
		taskInfo.setStart(newStart);
		taskInfo.setEnd(newEnd);
		taskInfo.setNatureTimes(newNatureTimes);
		taskInfo.setEffectTimes(newEffectTimes);
	}

	/**
	 * 更新其所有上级任务，使开始时间、结束时间不小于其期间<br>
	 * 这个会循环迭代更新父任务，直到最顶层
	 * 
	 * @param impl
	 * @param taskInfo
	 * @param taskEntrys
	 * @param updateMap
	 */
	private void updateSuper(FDCScheduleTaskInfo taskInfo, FDCScheduleTaskCollection taskEntrys, Map updateMap) {
		FDCScheduleTaskInfo parent = taskInfo.getParent();
		if (parent != null) {
			Date start = taskInfo.getStart();
			Date end = taskInfo.getEnd();
			String prtID = parent.getId().toString();
			for (int i = 0; i < taskEntrys.size(); i++) {
				FDCScheduleTaskInfo tmpInfo = taskEntrys.get(i);
				if (!tmpInfo.isIsLeaf() && prtID.equals(tmpInfo.getId().toString())) {
					parent = tmpInfo;
					Date pStart = parent.getStart();
					Date pEnd = parent.getEnd();
					if (start.compareTo(pStart) < 0 || end.compareTo(pEnd) > 0) {
						// update by libing
						ScheduleCalendarImpl impl = new ScheduleCalendarImpl(parent.getCalendar());
						pStart = impl.findClosestWorkingTime(start);
						pEnd = impl.findClosestWorkingTime(end);
						setTask(impl, parent, pStart, pEnd);
						taskEntrys.set(i, parent);
						updateMap.put(parent.getId().toString(), parent);
						break;
					}
				}
			}
			// 循环迭代更新父任务
			updateSuper(parent, taskEntrys, updateMap);
		}
	}

	/**
	 * 根据map里面的任务分录，更新数据库开始、结束、有效、自然<br>
	 * 使用批量更新，提高效率，一片抵过去五片
	 * 
	 * @param ctx
	 * 
	 * @param updateSet
	 * @param scheduleInfo
	 * @throws BOSException
	 */
	private void updateToDB(Context ctx, Map updateSet,
			FDCScheduleInfo scheduleInfo) throws BOSException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		if (updateSet != null && !updateSet.isEmpty()) {
			// 更新任务
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			String head = "update T_SCH_FDCScheduleTask set FStart = ?,FEnd = ?,FEffectTimes = ?,FNatureTimes = ? where FID = ?";
			sql.setPrepareStatementSql(head);
			sql.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
			List params = new ArrayList();
			boolean hasSql = false;
			Iterator it = updateSet.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Entry) it.next();
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) entry.getValue();
				String id = taskInfo.getId().toString();
				Timestamp start = new Timestamp(taskInfo.getStart().getTime());
				Timestamp end = new Timestamp(taskInfo.getEnd().getTime());
				BigDecimal effectTimes = taskInfo.getEffectTimes();
				BigDecimal natureTimes = taskInfo.getNatureTimes();
				sql.addParam(start);
				sql.addParam(end);
				sql.addParam(effectTimes);
				sql.addParam(natureTimes);
				sql.addParam(id);
				sql.addBatch();
				hasSql =true;
				
			}
			if(hasSql){
				sql.executeBatch();
			}

			// 更新表头及版本计划的开始,结束等时间
			sql.clear();
			sql
					.appendSql("select min(task.fstart) as fstart,max(task.fend) as fend ");
			sql.appendSql(" from T_SCH_FDCScheduleTask task ");
			sql.appendSql(" inner join T_Sch_FDCSchedule head ");
			sql.appendSql(" on head.fid=task.fscheduleid ");
			sql.appendSql(" where head.fid = ? ");

			sql.addParam(scheduleInfo.getId().toString());
			IRowSet rowSet = sql.executeQuery();
			try {
				if (rowSet.next()) {
					Date startDate = rowSet.getDate("fstart");
					Date endDate = rowSet.getDate("fend");
					ScheduleCalendarInfo calendar = scheduleInfo.getCalendar();
					if (calendar == null) {
						ScheduleAppHelper.setCalendar(ctx, scheduleInfo);
						calendar = scheduleInfo.getCalendar();
					}
					BigDecimal effectTimes = ScheduleCalendarHelper
							.getEffectTimes(startDate, endDate, calendar);
					BigDecimal natureTimes = ScheduleCalendarHelper
							.getNatureTimes(startDate, endDate);
					sql.clear();
					sql
							.appendSql(" update T_Sch_FDCSchedule set fstartDate=?,fendDate=?,feffectTimes=?,fnatureTimes=? where fId=? ");
					sql.addParam(startDate);
					sql.addParam(endDate);
					sql.addParam(effectTimes);
					sql.addParam(natureTimes);
					sql.addParam(scheduleInfo.getId().toString());
					sql.execute();
					sql.clear();
					sql
							.appendSql(" update T_SCH_ScheduleVerManager set fstartDate=?,fendDate=?,feffectTimes=?,fnatureTimes=? where fprojectId=? and fislatestver=1 ");
					sql.addParam(startDate);
					sql.addParam(endDate);
					sql.addParam(effectTimes);
					sql.addParam(natureTimes);
					sql.addParam(scheduleInfo.getProject().getId().toString());
					sql.execute();
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
	}

	protected String _verifyremote(Context ctx, IObjectValue model)
			throws BOSException {
		ScheduleCalendarInfo calendarInfo = (ScheduleCalendarInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("name", calendarInfo.getName(),
						CompareType.EQUALS));
		if (!FDCHelper.isEmpty(calendarInfo.getId())) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", calendarInfo.getId().toString(),
							CompareType.EQUALS));
			filter.setMaskString("#0 and #1");
		}
		try {
			if (_exists(ctx, filter)) {
				return "1";
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(
				new FilterItemInfo("number", calendarInfo.getNumber(),
						CompareType.EQUALS));
		if (!FDCHelper.isEmpty(calendarInfo.getId())) {
			filter2.getFilterItems().add(
					new FilterItemInfo("id", calendarInfo.getId().toString(),
							CompareType.EQUALS));
			filter2.setMaskString("#0 and #1");
		}
		try {
			if (_exists(ctx, filter2)) {
				return "2";
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "3";
	}

	protected String _verifyremoteupdate(Context ctx, IObjectValue model)
			throws BOSException {
		ScheduleCalendarInfo calendarInfo = (ScheduleCalendarInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("name", calendarInfo.getName(),
						CompareType.EQUALS));

		if (calendarInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", calendarInfo.getId().toString(),
							CompareType.NOTEQUALS));
			filter.setMaskString("#0 and #1");
		}
		try {
			if (_exists(ctx, filter)) {
				// throw new EASBizException(new NumericExceptionSubItem("0011",
				// "名称不能重复！"));
				return "1";
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(
				new FilterItemInfo("number", calendarInfo.getNumber(),
						CompareType.EQUALS));
		if (calendarInfo.getId() != null) {
			filter2.getFilterItems().add(
					new FilterItemInfo("id", calendarInfo.getId().toString(),
							CompareType.NOTEQUALS));
			filter2.setMaskString("#0 and #1");
		}
		try {
			if (_exists(ctx, filter2)) {
				// throw new EASBizException(new NumericExceptionSubItem("0011",
				// "编码不能重复！"));
				return "2";
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "3";
	}

	protected String _addOnetoNewNum(Context ctx) throws BOSException {
		// TODO Auto-generated method stub
		String strNum = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// 直接写SQL查询出最大编码
		builder
				.appendSql("select max(fnumber) as fnumber from T_SCH_ScheduleCalendar");
		IRowSet rowSet = builder.executeQuery();
		try {
			// 是否有值
			while (rowSet.next()) {
				strNum = rowSet.getString("fnumber");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (strNum == "" || strNum == null) {
			strNum = "001";
		}
		return strNum;
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ScheduleCalendarInfo info = (ScheduleCalendarInfo) getValue(ctx, pk);
		if (info.isIsSys() == true) {
			throw new EASBizException(new NumericExceptionSubItem("115", "当前日历为默认日历，不允许删除！如果需要删除当前日历，必须先将当前日历设置为非默认日历。"));
		}
		super._delete(ctx, pk);
	}
}
