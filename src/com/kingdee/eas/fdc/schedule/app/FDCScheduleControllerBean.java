package com.kingdee.eas.fdc.schedule.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.DataAccessException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.basedata.util.FdcMethodUtil;
import com.kingdee.eas.fdc.schedule.FDCSchTaskWithContractCollection;
import com.kingdee.eas.fdc.schedule.FDCSchTaskWithContractFactory;
import com.kingdee.eas.fdc.schedule.FDCSchTaskWithContractInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleCompleteHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskStateHelper;
import com.kingdee.eas.fdc.schedule.FDCWBSCollection;
import com.kingdee.eas.fdc.schedule.HelpDeptEntryCollection;
import com.kingdee.eas.fdc.schedule.HelpPersonEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectSpecialFactory;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqFactory;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqInfo;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqWBSEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleCodingTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCreateReasonEnum;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.eas.fdc.schedule.ScheduleHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerCollection;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryCollection;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryFactory;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerFactory;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo;
import com.kingdee.eas.fdc.schedule.SpecialScheduleUtils;
import com.kingdee.eas.fdc.schedule.TaskTypeFactory;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.framework.util.StopWatch;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

public class FDCScheduleControllerBean extends AbstractFDCScheduleControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.app.FDCScheduleControllerBean");

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		if (model instanceof FDCScheduleInfo) {
			FDCScheduleInfo schedule = (FDCScheduleInfo) model;
			if (schedule != null && schedule.getDispColumns() != null) {
				schedule.getDispColumns().clear();
			}
		}
		super._update(ctx, pk, model);
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		recycleNumber(ctx, pk);
		_delete(ctx, new IObjectPK[] { pk });
	}

	protected IObjectPK[] _delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException {
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo("id"));
		IObjectPK[] pks = getPKList(ctx, filter, sorter);
		for (int i = 0; i < pks.length; i++) {
			recycleNumber(ctx, pks[i]);
		}
		_delete(ctx, pks);
		return pks;
	}

	/**
	 * 
	 * 描述：检查当前操作是否能执行，由于启动了工作流，可以从工作流中审批，而界面仍然是提交状态，如果不做此项检查，可能会造成数据不一致
	 * 
	 * @param ctx
	 * @param info
	 * @return 0:能进行保存c 创建人：yuanjun_lan 创建时间：2011-12-22
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkCanOPerator(Context ctx,BOSUuid id) throws EASBizException, BOSException{
		FDCScheduleInfo newInfo = (FDCScheduleInfo) getValue(ctx, new ObjectUuidPK(id));
		if(newInfo.getState().equals(ScheduleStateEnum.AUDITTED)||newInfo.getState().equals(ScheduleStateEnum.AUDITTING))
			throw new ScheduleException(ScheduleException.WITHMSG,new Object[]{"当前单据为"+newInfo.getState()+",不能进行此操作！"});
		
	}

	protected IObjectPK[] _delete(Context ctx, String oql) throws BOSException, EASBizException {
		IObjectPK[] pks = this.getPKList(ctx, oql);
		for (int i = 0; i < pks.length; i++) {
			recycleNumber(ctx, pks[i]);
		}
		_delete(ctx, pks);
		return pks;
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		// 删除扩展属性
		// 只能删除保存,提交的单据

		// Set idSet=new HashSet();
		String[] arrays = new String[arrayPK.length];
		for (int i = 0; i < arrayPK.length; i++) {
			// idSet.add(arrayPK[i].toString());
			arrays[i] = arrayPK[i].toString();
		}

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule where fstate<>? and fstate<> ? and ");
		builder.addParam(ScheduleStateEnum.SAVED_VALUE);
		builder.addParam(ScheduleStateEnum.SUBMITTED_VALUE);
		builder.appendParam("fid", arrays);
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "保存及提交状态的单据才可以删除!"));
		}
		FilterInfo filter = new FilterInfo();
		String sql = "select c.fid from T_SCH_FDCSchedule a,T_SCH_FDCScheduleTask b,T_Sch_FDCWBS c where a.fid=b.fscheduleid and b.fwbsid=c.fid and  "
				+ FDCSQLBuilder.getInSql("a.fid", arrays) + "";
		filter.getFilterItems().add(new FilterItemInfo("wbs.id", sql, CompareType.INNER));
		FDCScheduleTaskExtFactory.getLocalInstance(ctx).delete(filter);

		// delete ver information
		// / delete only on ver entry

		sql = "select fscheduleid from T_sch_schedulevermanagerentry  where " + FDCSQLBuilder.getInSql("fscheduleid", arrays)
				+ " group by fscheduleid having count(*)=1 ";
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", sql, CompareType.INNER));
		ScheduleVerManagerFactory.getLocalInstance(ctx).delete(filter);

		// delete other
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule.id", new HashSet(Arrays.asList(arrays)), CompareType.INCLUDE));
		ScheduleVerManagerEntryFactory.getLocalInstance(ctx).delete(filter);

		for (int i = 0; i < arrayPK.length; i++) {
			builder.clear();
			builder.appendSql("delete from t_sch_fdcscheduletaskdepend where ftaskid in (select fid from t_sch_fdcscheduletask where fscheduleid=?)");
			builder.addParam(arrayPK[i].toString());
			builder.execute();
			builder.clear();
			builder.appendSql("delete from t_sch_fdcscheduletask where fscheduleid=?");
			builder.addParam(arrayPK[i].toString());
			builder.execute();
		}
		for (int i = 0; i < arrayPK.length; i++) {
			recycleNumber(ctx, arrayPK[i]);
		}
		super._delete(ctx, arrayPK);
	}

	/**
	 * 
	 * 描述：批量修改数据， 采用先删后添得方式，能够有效改善性能
	 * 
	 * @param col
	 *            待修改的实体数据
	 * @param dao
	 * @throws DataAccessException
	 *             创建时间：2010-8-16 创建人：zhiqiao_yang
	 */
	private void batchUpdate(AbstractObjectCollection col, IORMappingDAO dao) throws DataAccessException {
		int size = col.size();
		if (size > 0) {
			for (int i = 0; i < size; ++i) {
				dao.deleteBatch(new ObjectUuidPK(((CoreBaseInfo) col.getObject(i)).getId()));
			}
			dao.executeBatch();
			for (int i = 0; i < size; ++i) {
				dao.addNewBatch(col.getObject(i));
			}
			dao.executeBatch();
		}
	}

	/**
	 * 
	 * 描述：更新计划中的任务及其搭建关系
	 * 
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 *             创建时间：2010-8-16 创建人：zhiqiao_yang
	 */
	private void addTaskAndTaskDepend(Context ctx, FDCScheduleInfo info) throws BOSException {
		FDCScheduleTaskCollection tasks = info.getTaskEntrys();
		if (tasks != null && tasks.size() > 0) {
			FDCScheduleTaskDependCollection depends = new FDCScheduleTaskDependCollection();
			
			HelpPersonEntryCollection pcols=new HelpPersonEntryCollection();
			HelpDeptEntryCollection dcols=new HelpDeptEntryCollection();
			int taskSize = tasks.size();
			for (int i = 0; i < taskSize; ++i) {
				tasks.get(i).setSchedule(info);
				FDCScheduleTaskDependCollection tempDepends = tasks.get(i).getDependEntrys();
				if (tempDepends != null && tempDepends.size() > 0) {
					depends.addCollection(tempDepends);
				}
				
				HelpPersonEntryCollection pcol=tasks.get(i).getHelpPersonEntry();
				if(pcol!=null&&pcol.size()>0){
					pcols.addCollection(pcol);
				}
				
				HelpDeptEntryCollection dcol=tasks.get(i).getHelpDeptEntry();
				if(dcol!=null&&dcol.size()>0){
					dcols.addCollection(dcol);
				}
			}
			IORMappingDAO taskDao = ORMappingDAO.getInstance(tasks.get(0).getBOSType(), ctx, getConnection(ctx));
			batchUpdate(tasks, taskDao);
			if (depends.size() > 0) {
				IORMappingDAO dependDao = ORMappingDAO.getInstance(depends.get(0).getBOSType(), ctx, getConnection(ctx));
				batchUpdate(depends, dependDao);
			}
			if(pcols.size()>0){
				IORMappingDAO dependDao = ORMappingDAO.getInstance(pcols.get(0).getBOSType(), ctx, getConnection(ctx));
				batchUpdate(pcols, dependDao);
			}
			if(dcols.size()>0){
				IORMappingDAO dependDao = ORMappingDAO.getInstance(dcols.get(0).getBOSType(), ctx, getConnection(ctx));
				batchUpdate(dcols, dependDao);
			}
		}
	}

	private IObjectPK _saveOrSubmit(Context ctx, IObjectValue model, ScheduleStateEnum state) throws BOSException, EASBizException {
		// StopWatch sw = new StopWatch();
		// sw.start();
		FDCScheduleInfo info = (FDCScheduleInfo) model;
        if(info.getVersionName()!=null){
        	info.setName(info.getVersionName());
        	info.setNumber(info.getVersionName());
        }
		info.setState(state);
		if (info.containsKey("AdjustParam")) {
			handleAdjustGanttReq(ctx, info);
			return new ObjectUuidPK(info.get("returnPK").toString());
		}
		handBeforeSaveAndSubmit(ctx, info);
		// logger.info("SaveOrSumit handBeforeSaveAndSubmit cost time: " +
		// sw.getLastTime());
		if ((info.getId() != null && !this._exists(ctx, new ObjectUuidPK(info.getId()))) || info.getId() == null) {
			// handleIntermitNumber(ctx, info);
		}
		IObjectPK pk = null;
		if (ScheduleStateEnum.SUBMITTED.equals(state)) {
			pk = super._submit(ctx, model);
		} else {
			pk = super._save(ctx, model);
		}
		
		// 保存关联合同
		saveRelationContract(ctx, info);
		
		// 删除前置关系
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("delete from T_SCH_FDCScheduleTaskDepend where ftaskid in ");
		builder.appendSql("(select fid from T_SCH_FDCScheduleTask where fscheduleid=?)");
		builder.addParam(info.getId().toString());
		logger.info(builder.getTestSql());
		builder.execute();
		
		//删除任务业务类型分录
//		builder.clear();
//		builder.appendSql("delete from T_SCH_ScheduleTaskBizType where fparentid in  (select fid from t_sch_fdcscheduletask where fscheduleid =(select fid from t_sch_fdcschedule where fversion = ? and fprojectid = ?");
//		builder.addParam(Float.valueOf(info.getVersion()-1));
//		builder.addParam(info.getProject().getId().toString());
//		if(info.getProjectSpecial() == null){
//			builder.appendSql(" and FProjectSpecialID is null");
//		}else{
//			builder.appendSql(" and FProjectSpecialID is not null and FProjectSpecialID = ?");
//			builder.addParam(info.getProjectSpecial().getId().toString());
//		}
//	    builder.appendSql("))");
//        logger.info(builder.getTestSql());
//		builder.execute();
		
		// 删除任务
		builder.clear();
		builder.appendSql("delete from T_SCH_FDCScheduleTask where fscheduleid=?");
		builder.addParam(info.getId().toString());
		 logger.info(builder.getTestSql());
		builder.execute();
		
		addTaskAndTaskDepend(ctx, info);
		if (info.getId() == null) {
			info.setId(BOSUuid.read(pk.toString()));
		}

		// logger.info("SaveOrSumit save cost time: " + sw.getLastTime());
		handAfterSaveAndSubmit(ctx, info);
		// logger.info("SaveOrSumit handAfterSaveAndSubmit cost time: " +
		// sw.getLastTime());
		return pk;
	}

	/**
	 * 保存关联合同
	 * 
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void saveRelationContract(Context ctx, FDCScheduleInfo info)
			throws BOSException, EASBizException {
		// 清空旧关系
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("task.schedule.srcID", info.getSrcID()));
		FDCSchTaskWithContractFactory.getLocalInstance(ctx).delete(filter);
		// 保存新关系
		IORMappingDAO dao = ORMappingDAO.getInstance(
				new FDCSchTaskWithContractInfo().getBOSType(), ctx,
				getConnection(ctx));
		for (int i = 0; i < info.getTaskEntrys().size(); i++) {
			List relCon = (List) info.getTaskEntrys().get(i).get("relCon");
			if (relCon != null) {
				for (int j = 0; j < relCon.size(); j++) {
					FDCSchTaskWithContractInfo relConInfo = (FDCSchTaskWithContractInfo) relCon
							.get(j);
					dao.addnew(relConInfo);
				}
			}
		}
		dao.executeBatch();
	}

	private void updateHistoryVersionState(IObjectPK pk, Context ctx, FDCScheduleInfo info) {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set FIsLatestVer='0' ");
		builder.appendSql(" where FProjectID=? and ");
		builder.addParam(info.getProject().getId().toString());
		if (info.getProjectSpecial() == null) {
			builder.appendSql("(FProjectSpecialID is null or FProjectSpecialID = '') and ");
		} else {
			builder.appendParam("FProjectSpecialID", info.getProjectSpecial().getId().toString());
			builder.appendSql(" and ");
		}
		builder.appendSql(" FID<>?");
		builder.addParam(pk.toString());
		try {
			builder.executeUpdate();
		} catch (BOSException e) {
			e.printStackTrace();
		}

	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		FDCScheduleInfo info = (FDCScheduleInfo) model;
		if(info.getId() != null && exists(ctx, new ObjectUuidPK(info.getId())))
		  checkCanOPerator(ctx, info.getId());
		IObjectPK pk = _saveOrSubmit(ctx, model, ScheduleStateEnum.SUBMITTED);
		/** 更新历史版本的状态 **/
//		updateHistoryVersionState(pk, ctx, (FDCScheduleInfo) model);
		if(info.getProjectSpecial() != null){
			submitSpecialSchedule(ctx, info);
		}else{
			submitMainSchedule(ctx, info);
		}
		return pk;
	}

	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		FDCScheduleInfo info = (FDCScheduleInfo) model;
		if(info.getId() != null && exists(ctx, new ObjectUuidPK(info.getId())))
		  checkCanOPerator(ctx, info.getId());
		info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		info.setCreateTime(new Timestamp(System.currentTimeMillis()));
		IObjectPK pk = _saveOrSubmit(ctx, model, ScheduleStateEnum.SAVED);
		/** 更新历史版本的状态 **/
//		updateHistoryVersionState(pk, ctx, (FDCScheduleInfo) model);
		return pk;
	}

	private void handleAdjustGanttReq(Context ctx, FDCScheduleInfo info) throws EASBizException, BOSException {
		Map param = (Map) info.get("AdjustParam");
		ScheduleAdjustGattReqInfo adjustGanttReq = (ScheduleAdjustGattReqInfo) info.get("AdjustInfo");

		/***
		 * R100823-129 结合客户端代码的修改，服务器端代码也做相应的判断；
		 * 目的：是为了避免启动计划编制的工作流，但要能够正常启动计划调整单的工作流
		 */
		if (param.containsKey("isSubmit")) {
			info.setState(ScheduleStateEnum.SUBMITTED);
		}
		String taskTypeId = (String) param.get("taskTypeId");// 任务专业属性

		ScheduleVerManagerInfo adjustVerInfo = info.getBaseVer();
		ScheduleVerManagerInfo newSchVerInfo = null;
		if (adjustVerInfo.getVersion() != 0 && adjustGanttReq.getNewVer() == null) {
			newSchVerInfo = (ScheduleVerManagerInfo) adjustVerInfo.clone();
		} else {
			newSchVerInfo = adjustVerInfo;
		}

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selector = view.getSelector();
		selector.add("id");
		selector.add("parent.project.id");
		selector.add("parent.project.name");
		selector.add("parent.project.number");
		selector.add("parent.project.longNumber");
		selector.add("parent.project.startDate");
		selector.add("parent.project.fullOrgUnit.id");
		selector.add("parent.project.fullOrgUnit.name");
		selector.add("parent.project.fullOrgUnit.number");
		selector.add("*");
		selector.add("schedule.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("parent.id", adjustVerInfo.getId().toString()));
		ScheduleVerManagerEntryCollection verEntrys = ScheduleVerManagerEntryFactory.getLocalInstance(ctx).getScheduleVerManagerEntryCollection(view);

		newSchVerInfo.getEntrys().clear();
		newSchVerInfo.getEntrys().addCollection(verEntrys);

		if (adjustGanttReq.getNewVer() == null) {
			newSchVerInfo.setVersion(FDCHelper.ZERO.floatValue());
			newSchVerInfo.setId(BOSUuid.create(adjustVerInfo.getBOSType()));
			newSchVerInfo.setCreateReason(ScheduleCreateReasonEnum.Adjust);
		}
		newSchVerInfo.setIsLatestVer(false);

		Map schedules = new HashMap();
		for (Iterator it = newSchVerInfo.getEntrys().iterator(); it.hasNext();) {
			ScheduleVerManagerEntryInfo entry = (ScheduleVerManagerEntryInfo) it.next();
			if (adjustGanttReq.getNewVer() == null) {
				entry.setId(BOSUuid.create(entry.getBOSType()));
				entry.setParent(newSchVerInfo);
				entry.setVersion(entry.getVersion() + 1);
			}

			schedules.put(entry.getSchedule().getId().toString(), entry.getSchedule());
		}

		FDCScheduleTaskCollection schduleTaskColl = info.getTaskEntrys();
		FDCScheduleTaskCollection saveScheduleTasks = new FDCScheduleTaskCollection();

		Map oldIdNewTaskMap = new HashMap();
		for (Iterator it = schduleTaskColl.iterator(); it.hasNext();) {
			FDCScheduleTaskInfo fdcSchTask = (FDCScheduleTaskInfo) it.next();
			if (fdcSchTask.containsKey("myScheduleID") && schedules.containsKey(fdcSchTask.get("myScheduleID"))) {
				FDCScheduleInfo fdcSch2 = (FDCScheduleInfo) schedules.get(fdcSchTask.get("myScheduleID"));
				if (!fdcSch2.containsKey("HasNewID")) {
					if (adjustGanttReq.getNewVer() == null) {
						fdcSch2.setId(BOSUuid.create(fdcSch2.getBOSType()));
						fdcSch2.setVersion(FDCHelper.ZERO.floatValue());
						fdcSch2.setIsLatestVer(false);
						fdcSch2.setState(ScheduleStateEnum.SAVED);
						fdcSch2.setBaseVer(newSchVerInfo);
						fdcSch2.setCreateTime(new Timestamp(Calendar.getInstance().getTime().getTime()));
						fdcSch2.setCreateReason(ScheduleCreateReasonEnum.Adjust);
						fdcSch2.setStartDate(null);
						fdcSch2.setEndDate(null);
					}
					fdcSch2.getTaskEntrys().clear();
					fdcSch2.getDispColumns().clear();
					fdcSch2.put("HasNewID", Boolean.TRUE);
				}

				String oldId = fdcSchTask.getId().toString();
				fdcSchTask.put("oldId", oldId);
				if (adjustGanttReq.getNewVer() == null) {
					fdcSchTask.setId(BOSUuid.create(fdcSchTask.getBOSType()));
					fdcSchTask.setSchedule(fdcSch2);
				}
				if (fdcSch2.getStartDate() == null || fdcSch2.getStartDate().after(fdcSchTask.getStart())) {
					fdcSch2.setStartDate(fdcSchTask.getStart());
				}
				if (fdcSch2.getEndDate() == null || fdcSch2.getEndDate().before(fdcSchTask.getEnd())) {
					fdcSch2.setEndDate(fdcSchTask.getEnd());
				}
				fdcSch2.getTaskEntrys().add(fdcSchTask);
				oldIdNewTaskMap.put(oldId, fdcSchTask);

				saveScheduleTasks.add(fdcSchTask);
			}
		}

		/***
		 * [1].[2]取所有计划的任务，及任务之间的前后关系
		 * 
		 */
		FDCScheduleTaskDependCollection taskDepends = new FDCScheduleTaskDependCollection();
		for (Iterator it = schduleTaskColl.iterator(); it.hasNext();) {
			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) it.next();
			if (taskInfo.getParent() != null) {
				if ((FDCScheduleTaskInfo) oldIdNewTaskMap.get(taskInfo.getParent().getId().toString()) != null)
					taskInfo.setParent((FDCScheduleTaskInfo) oldIdNewTaskMap.get(taskInfo.getParent().getId().toString()));
			}

			for (int i = 0; i < taskInfo.getDependEntrys().size(); i++) {
				FDCScheduleTaskDependInfo dependInfo = taskInfo.getDependEntrys().get(i);
				if (adjustGanttReq.getNewVer() == null) {
					dependInfo.setId(BOSUuid.create(dependInfo.getBOSType()));
				}

				// dependInfo.setTask(taskInfo);
				if ((FDCScheduleTaskInfo) oldIdNewTaskMap.get(dependInfo.getDependTask().getId().toString()) != null)
					dependInfo.setDependTask((FDCScheduleTaskInfo) oldIdNewTaskMap.get(dependInfo.getDependTask().getId().toString()));

				taskDepends.add(dependInfo);
			}
		}

		IORMappingDAO dao = ORMappingDAO.getInstance(info.getBOSType(), ctx, getConnection(ctx));
		ScheduleCalendarInfo calendar = info.getCalendar();
		if (calendar == null) {
			ScheduleAppHelper.setCalendar(ctx, info);
			calendar = info.getCalendar();
		}
		for (int i = 0; i < newSchVerInfo.getEntrys().size(); i++) {
			FDCScheduleInfo schedule = newSchVerInfo.getEntrys().get(i).getSchedule();
			BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(schedule.getStartDate(), schedule.getEndDate(), calendar);
			BigDecimal natureTimes = ScheduleCalendarHelper.getNatureTimes(schedule.getStartDate(), schedule.getEndDate());
			schedule.setEffectTimes(effectTimes);
			schedule.setNatureTimes(natureTimes);
			if (!FDCScheduleFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(schedule.getId()))) {
				dao.addNewBatch(schedule);
			} else {
				dao.updateBatch(new ObjectUuidPK(schedule.getId()), schedule);
			}
			addTaskAndTaskDepend(ctx, schedule);
		}
		dao.executeBatch();

		IObjectPK newVerPK = ScheduleVerManagerFactory.getLocalInstance(ctx).save(newSchVerInfo);

		// 更新版本计划的开始,结束等时间
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select min(task.fstart) as fstartDate,max(task.fend) as fendDate from T_SCH_FDCScheduleTask task ");
		builder.appendSql(" inner join T_Sch_FDCSchedule head on head.fid=task.fscheduleid ");
		builder.appendSql(" where head.fprojectid=? and head.fversion=0");

		builder.addParam(info.getProject().getId().toString());
		IRowSet rowSet = builder.executeQuery();
		try {
			if (rowSet.next()) {
				Date startDate = rowSet.getDate("fstartDate");
				Date endDate = rowSet.getDate("fendDate");
				BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(startDate, endDate, calendar);
				BigDecimal natureTimes = ScheduleCalendarHelper.getNatureTimes(startDate, endDate);
				builder.clear();
				builder
						.appendSql("update T_SCH_ScheduleVerManager set fstartDate=?,fendDate=?,feffectTimes=?,fnatureTimes=? where fprojectId=? and fversion=0 ");
				builder.addParam(startDate);
				builder.addParam(endDate);
				builder.addParam(effectTimes);
				builder.addParam(natureTimes);
				builder.addParam(info.getProject().getId().toString());
				builder.execute();
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		IObjectPK reqPk = null;
		if (adjustGanttReq.getNewVer() == null) {
			adjustGanttReq.setBaseVer(adjustVerInfo);
			adjustGanttReq.setNewVer(newSchVerInfo);
		}
		if (ScheduleStateEnum.SUBMITTED.equals(info.getState())) {
			reqPk = ScheduleAdjustGattReqFactory.getLocalInstance(ctx).submit(adjustGanttReq);
		} else {
			reqPk = ScheduleAdjustGattReqFactory.getLocalInstance(ctx).save(adjustGanttReq);
		}

		adjustGanttReq.setId(BOSUuid.read(reqPk.toString()));
		info.put("returnPK", reqPk);
	}

	private void handAfterSaveAndSubmit(Context ctx, FDCScheduleInfo info) throws EASBizException, BOSException {
		StopWatch sw = new StopWatch();
		sw.start();
		// 更新版本计划的开始,结束等时间
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select min(task.fstart) as fstartDate,max(task.fend) as fendDate from T_SCH_FDCScheduleTask task ");
		builder.appendSql(" inner join T_Sch_FDCSchedule head on head.fid=task.fscheduleid ");
		builder.appendSql(" where head.fislatestver=1 and head.fprojectid=? ");

		builder.addParam(info.getProject().getId().toString());
		IRowSet rowSet = builder.executeQuery();
		try {
			if (rowSet.next()) {
				Date startDate = rowSet.getDate("fstartDate");
				Date endDate = rowSet.getDate("fendDate");
				if(null != startDate && null != endDate) {
					ScheduleCalendarInfo calendar = info.getCalendar();
					if (calendar == null) {
						ScheduleAppHelper.setCalendar(ctx, info);
						calendar = info.getCalendar();
					}
					BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(startDate, endDate, calendar);
					BigDecimal natureTimes = ScheduleCalendarHelper.getNatureTimes(startDate, endDate);
					builder.clear();
					builder
							.appendSql("update T_SCH_ScheduleVerManager set fstartDate=?,fendDate=?,feffectTimes=?,fnatureTimes=? where fprojectId=? and fislatestver=1 ");
					builder.addParam(startDate);
					builder.addParam(endDate);
					builder.addParam(effectTimes);
					builder.addParam(natureTimes);
					builder.addParam(info.getProject().getId().toString());
					builder.execute();
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		logger.info("handAfterSaveAndSubmit 3 cost time: " + sw.getLastTime());

		/*
		 * 处理跨计划的前置任务 1.将原有的本计划外的前置任务删除掉 2.新增目前的本计划外的前置任务
		 */
		// delete prefixdepend
		// builder.clear();
		// builder = new FDCSQLBuilder(ctx);
		// builder
		// .appendSql(
		// "delete from T_sch_fdcscheduletaskdepend where exists (select 1 from T_sch_fdcscheduletask where fid=ftaskid and fscheduleid<>?) and exists (select 1 from T_sch_fdcscheduletask where fid=fdependtaskid and fscheduleid=? )"
		// );
		// builder.addParam(info.getId().toString());
		// builder.addParam(info.getId().toString());
		// builder.execute();
		// // save prefixdepend
		// FDCScheduleTaskDependCollection prefixDepends =
		// (FDCScheduleTaskDependCollection) info.get("prefixDepends");
		//
		// // 暂时将之隐藏，避免添加两次后置任务，不明白跨计划的前置任务应该怎么处理，如果需要的话此处添加判断
		// if (prefixDepends != null && prefixDepends.size() > 0) {
		// CoreBaseCollection colls = (CoreBaseCollection)
		// prefixDepends.cast(CoreBaseCollection.class);
		// // FDCScheduleTaskDependFactory.getLocalInstance(ctx).save(colls);
		// }
		logger.info("handAfterSaveAndSubmit 4 cost time: " + sw.getLastTime());

	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		IObjectPK pk = super._addnew(ctx, model);
		FDCScheduleInfo info = (FDCScheduleInfo) model;
		String id = pk.toString();
		if (info.getId() == null) {
			info.setId(BOSUuid.read(id));
		}
		addToScheduleVerManager(ctx, info);
		return pk;
	}

	private void addToScheduleVerManager(Context ctx, FDCScheduleInfo info) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getSelector().add("*");
		view.getSelector().add("project.id");
		view.getSelector().add("entrys.*");
		view.getSelector().add("schedule.id");
		view.getFilter().appendFilterItem("project.id", info.getProject().getId().toString());
		view.getFilter().appendFilterItem("isLatestVer", Boolean.TRUE);
		ScheduleVerManagerCollection coll = ScheduleVerManagerFactory.getLocalInstance(ctx).getScheduleVerManagerCollection(view);
		if (coll.size() > 1) {
			throw new EASBizException(new NumericExceptionSubItem("120", "存在多个最新的总进度版本!"));
		}
		String baseVerId = null;
		if (coll.size() == 1) {
			ScheduleVerManagerInfo verInfo = coll.get(0);
			baseVerId = verInfo.getId().toString();
			boolean hasInsert = false;
			for (Iterator iter = verInfo.getEntrys().iterator(); iter.hasNext();) {
				ScheduleVerManagerEntryInfo entry = (ScheduleVerManagerEntryInfo) iter.next();
				if (entry.getSchedule().getId().equals(info.getId())) {
					hasInsert = true;
					break;
				}
			}

			if (!hasInsert) {
				// 为当前的最新版本新增一个分录
				ScheduleVerManagerEntryInfo entry = new ScheduleVerManagerEntryInfo();
				entry.setParent(verInfo);
				entry.setSchedule(info);
				entry.setVersion(info.getVersion());
				ScheduleVerManagerEntryFactory.getLocalInstance(ctx).addnew(entry);

				// update startDate,endDate,times
				if (verInfo.getStartDate() == null || info.getStartDate().compareTo(verInfo.getStartDate()) < 0) {
					verInfo.setStartDate(info.getStartDate());
				}
				// 之前的代码没有判断info.getEndDate() 是否为
				// null.这样当新增专项但没有增加专项任务的时候，会报空指针异常。 --jiadong
				if (verInfo.getEndDate() == null || info.getEndDate() == null || info.getEndDate().compareTo(verInfo.getEndDate()) > 0) {
					verInfo.setEndDate(info.getEndDate());
				}
				verInfo.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(verInfo.getStartDate(), verInfo.getEndDate(), info.getCalendar()));
				verInfo.setNatureTimes(ScheduleCalendarHelper.getNatureTimes(verInfo.getStartDate(), verInfo.getEndDate()));

				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("startDate");
				selector.add("endDate");
				selector.add("effectTimes");
				selector.add("natureTimes");
				ScheduleVerManagerFactory.getLocalInstance(ctx).updatePartial(verInfo, selector);
			}
		} else {
			// addNew
			ScheduleVerManagerInfo verInfo = new ScheduleVerManagerInfo();
			verInfo.setVersion(info.getVersion());
			verInfo.setProject(info.getProject());
			verInfo.setEffectTimes(info.getEffectTimes());
			verInfo.setNatureTimes(info.getNatureTimes());
			verInfo.setStartDate(info.getStartDate());
			verInfo.setEndDate(info.getEndDate());
			verInfo.setCreateReason(ScheduleCreateReasonEnum.InitVer);
			verInfo.setIsLatestVer(true);
			// entrys
			ScheduleVerManagerEntryInfo entry = new ScheduleVerManagerEntryInfo();
			entry.setParent(verInfo);
			entry.setSchedule(info);
			entry.setVersion(info.getVersion());
			verInfo.getEntrys().add(entry);

			verInfo.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(verInfo.getStartDate(), verInfo.getEndDate(), info.getCalendar()));
			verInfo.setNatureTimes(ScheduleCalendarHelper.getNatureTimes(verInfo.getStartDate(), verInfo.getEndDate()));
			verInfo.setIsLatestVer(true);
			IObjectPK pk = ScheduleVerManagerFactory.getLocalInstance(ctx).addnew(verInfo);
			baseVerId = pk.toString();
		}

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_sch_fdcschedule set fbaseverid=? where fid=?");
		builder.addParam(baseVerId);
		builder.addParam(info.getId().toString());
		builder.execute();
	}

	/**
	 * 数据提交到数据的预处理
	 * 
	 * @param ctx
	 * @param info
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected void handBeforeSaveAndSubmit(Context ctx, FDCScheduleInfo info) throws EASBizException, BOSException {
		if (info.getVersion() < 1f) {
			info.setVersion(1.0f);
		}
		saveExtPropertys(ctx, info);
		info.getScheduleDispColumns().clear();// TODO 暂时不保存
		// 保存时不是最终版本，只有当审批后，才变为最终版本，1.0版也不例外
		info.setIsLatestVer(false);
		info.setCreateReason(ScheduleCreateReasonEnum.InitVer);

		// // 更新所有任务的级次、是否明细
		// FDCScheduleTaskCollection taskEntrys = info.getTaskEntrys();
		// if (taskEntrys != null) {
		// Map key = new HashMap();
		// for (int i = 0; i < taskEntrys.size(); i++) {
		// FDCScheduleTaskInfo taskInfo = taskEntrys.get(i);
		// key.put(taskInfo.getId(), new Integer(i));
		// taskInfo.setIsLeaf(true);
		// if (taskInfo.getParent() == null) {
		// taskInfo.setLevel(0);
		// } else {
		// Integer index = (Integer) key.get(taskInfo.getParent()
		// .getId());
		// FDCScheduleTaskInfo parentInfo = taskEntrys.get(index
		// .intValue());
		// parentInfo.setIsLeaf(false);
		// taskInfo.setLevel(parentInfo.getLevel() + 1);
		// }
		// }
		// }
	}

	/**
	 * 保存任务的扩展属性
	 * 
	 * @param ctx
	 * @param info
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void saveExtPropertys(Context ctx, FDCScheduleInfo info) throws EASBizException, BOSException {
		if (true) {
			return;
		}
		FDCScheduleTaskExtCollection colls = new FDCScheduleTaskExtCollection();
		for (Iterator iter = info.getTaskEntrys().iterator(); iter.hasNext();) {
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) iter.next();
			FDCScheduleTaskExtInfo taskExt = task.getTaskExt();
			colls.add(taskExt);
		}
		if (colls.size() > 0) {
			CoreBaseCollection coll = (CoreBaseCollection) colls.cast(CoreBaseCollection.class);
			FDCScheduleTaskExtFactory.getLocalInstance(ctx).save(coll);
		}

		// 清除任务没有了的属性
		FilterInfo filter = new FilterInfo();
		String filterSql = "select fid from T_sch_fdcscheduleTaskExt ext where not exists (select 1 from T_sch_fdcscheduletask where fid=ext.ftaskid and fscheduleId='"
				+ info.getId().toString() + "')";
		filter.getFilterItems().add(new FilterItemInfo("id", filterSql, CompareType.INNER));
		FDCScheduleTaskExtFactory.getLocalInstance(ctx).delete(filter);
	}

	protected void checkBeforeAddNew(Context ctx, FDCScheduleInfo info, String adminDeptId, String prjId) throws EASBizException, BOSException {
		// 检查是否重复 adminDeptId/prjId/taskTypeid唯一
		String scheduleTypeId = info.getScheduleType().getId().toString();
		if (scheduleTypeId != null && scheduleTypeId.equals(TaskTypeInfo.TASKTYPE_MAINTASK)) {
			// 主项节点检查
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select 1 from T_SCH_FDCSchedule where fprojectId=? and fscheduleTypeId=? ");
			builder.addParam(prjId);
			builder.addParam(TaskTypeInfo.TASKTYPE_MAINTASK);
			if (builder.isExist()) {
				throw new EASBizException(new NumericExceptionSubItem("100", "计划已经编制过，不能再新增！"));
			}

		} else {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select 1 from T_SCH_FDCSchedule where fprojectId=? and fadminDeptId=? and fscheduleTypeId=? ");
			builder.addParam(prjId);
			builder.addParam(adminDeptId);
			builder.addParam(scheduleTypeId);
			if (builder.isExist()) {
				throw new EASBizException(new NumericExceptionSubItem("100", "计划已经编制过，不能再新增！"));
			}
		}
	}

	/*
	 * 跟李涛新讨论的结果是目前只按部门做隔离，任务属性只分作主项及专项
	 * 如果选择的是主项，则带出归口部门是本部门的A（主项WBS）节点及其他部门的A的上级WBS节点
	 * 如果选择的是专项，则带出归口部门是本部门的所有非主项的WBS节点，及它的上级节点，且在做计划之前要检查它的上级节点的计划是否已编制并且已审批完
	 * 
	 * 上述的第二点，专项，的条件修改为
	 * 
	 * 带出所有主项任务，如果一个主项都没有，会提示没有可编辑的任务。
	 */
	protected IObjectValue _getNewData(Context ctx, Map param) throws BOSException, EASBizException {
		FDCScheduleInfo info = new FDCScheduleInfo();

		String adminDeptId = (String) param.get("adminDeptId");// 归口部门
		String prjId = (String) param.get("prjId");// 工程项目
		String taskTypeId = (String) param.get("taskTypeId");// 任务专业属性
		if (param.containsKey("isVersionCompare")) {
			/***
			 * 版本对比取数
			 */
			String baseVerID = (String) param.get("baseVerID");
			String compVerID = (String) param.get("compVerID");
			FDCScheduleInfo baseVerInfo = ScheduleVerManagerFactory.getLocalInstance(ctx).getVerData(baseVerID);
			FDCScheduleInfo compVerInfo = ScheduleVerManagerFactory.getLocalInstance(ctx).getVerData(compVerID);

			return baseVerInfo;

		}

		if (taskTypeId != null && taskTypeId.equals(TaskTypeInfo.TASKTYPE_ADJUST)) {
			return getAdjustScheduleInfo(ctx, param);
		}
		if (taskTypeId != null && taskTypeId.equals(TaskTypeInfo.TASKTYPE_SPECIALTASK)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", prjId, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));

			if (ScheduleAdjustGattReqFactory.getLocalInstance(ctx).exists(filter)) {
				throw new EASBizException(new NumericExceptionSubItem("100", "本计划处于调整过程中(存在未审批的调整申请单),不能新增专项计划！"));
			}

		}

		// info.setScheduleType(FDCScheduleInfo.getScheduleType(taskTypeId));
		info.setScheduleType(TaskTypeFactory.getLocalInstance(ctx).getTaskTypeInfo(new ObjectUuidPK(BOSUuid.read(taskTypeId))));
		// 根据任务属性类型设置任务属性编码类型
		ScheduleCodingTypeInfo scheduleCodingType = new ScheduleCodingTypeInfo();
		if (TaskTypeInfo.TASKTYPE_MAINTASK.equals(info.getScheduleType().getId().toString())) {
			scheduleCodingType.setId(BOSUuid.read(ScheduleCodingTypeInfo.MAINTASK_CODINGID));
			scheduleCodingType.setNumber(ScheduleCodingTypeInfo.MAINTASK_CODINGNUM);
		} else {
			scheduleCodingType.setId(BOSUuid.read(ScheduleCodingTypeInfo.SPECIALTASK_CODINGID));
			scheduleCodingType.setNumber(ScheduleCodingTypeInfo.SPECIALTASK_CODINGNUM);
		}
		info.setScheduleCodingType(scheduleCodingType);

		checkBeforeAddNew(ctx, info, adminDeptId, prjId);

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("name");
		selector.add("codingNumber");
		selector.add("number");
		selector.add("projectPeriod");
		selector.add("longNumber");
		selector.add("startDate");
		CurProjectInfo prj = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(prjId), selector);
		FullOrgUnitInfo adminDept = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new ObjectUuidPK(adminDeptId));

		info.setId(BOSUuid.create(info.getBOSType()));
		info.setProject(prj);
		info.setCreateTime(new Timestamp(System.currentTimeMillis()));
		info.setAdminDept(adminDept);

		info.setOrgUnit(prj.getFullOrgUnit()); // 暂时设置成项目所属组织
		Date date = prj.getStartDate();// 以后取项目日历上的时间
		if (date == null) {
			date = new Date();
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date = cal.getTime();
		info.setStartDate(date);
		info.setViewDate(date);
		info.setGdivlocation(369);
		info.setRdivlocation(322);
		info.setGversion(FDCHelper.toBigDecimal("1.12"));
		info.setViewIndex(0);
		info.setColor("#DCB8F2");
		info.setIsLatestVer(true);
		info.setEditable(true);
		// 项目日历
		ScheduleAppHelper.setCalendar(ctx, info);

		if (info.getCalendar() != null && info.getCalendar().isWeekTime(info.getStartDate())) {
			info.setStartDate(ScheduleCalendarHelper.getNextWorkDay(info.getStartDate(), info.getCalendar()));
		}

		return info;
	}

	/****
	 * 取调整时的总计划
	 * 
	 * 实现方法为： 1、先取得主项计划和专项计划 2、复制主项计划和专项计划，生成新版本，版本号为0，表示调整中
	 * 3、生成一个新总计划版本0，关联相应的总计划，和专项计划 4、返回版本为0的总计划内容，包含主项计划和专项计划
	 * 5、保存时，根据主项计划和专项计划，分别保存
	 * 
	 * @param param
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private IObjectValue getAdjustScheduleInfo(Context ctx, Map param) throws EASBizException, BOSException {
		String taskTypeId = (String) param.get("taskTypeId");// 任务专业属性
		String adjustVerID = (String) param.get("adjustVerID");
		String prjId = (String) param.get("prjId");
		if (adjustVerID == null)
			return null;
		else {

			FDCScheduleInfo fdcSchedule = null;
			if (param.get("adjustReqId") != null) {
				String reqid = param.get("adjustReqId").toString();// 处理计划调整在工作流中保存和提交
				// add by
				// warship
				// at
				// 2010/07
				// /30

				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("*");
				sic.add("adjustReason.*");
				sic.add("adjustReason.name");
				sic.add("adjustReason.number");
				sic.add("baseVer.id");
				sic.add("baseVer.version");
				sic.add("newVer.id");
				sic.add("newVer.version");
				sic.add("entrys.*");
				sic.add("entrys.wbs.*");
				sic.add("creator.id");
				sic.add("creator.name");
				sic.add("creator.number");
				sic.add("handler.id");
				sic.add("handler.name");
				sic.add("handler.number");
				ScheduleAdjustGattReqInfo reqInfo = ScheduleAdjustGattReqFactory.getLocalInstance(ctx).getScheduleAdjustGattReqInfo(
						new ObjectUuidPK(reqid), sic);

				fdcSchedule = ScheduleVerManagerFactory.getLocalInstance(ctx).getVerData(reqInfo.getNewVer().getId().toString());
				FDCScheduleInfo oldfdcSchedule = ScheduleVerManagerFactory.getLocalInstance(ctx).getVerData(reqInfo.getBaseVer().getId().toString());

				Map oldWbsTaskMap = new HashMap();
				for (Iterator it = oldfdcSchedule.getTaskEntrys().iterator(); it.hasNext();) {
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) it.next();
					oldWbsTaskMap.put(task.getWbs().getId().toString(), task);
				}

				Map wbsAdjustEntryMap = new HashMap();
				for (Iterator it = reqInfo.getEntrys().iterator(); it.hasNext();) {
					ScheduleAdjustGattReqWBSEntryInfo entry = (ScheduleAdjustGattReqWBSEntryInfo) it.next();
					wbsAdjustEntryMap.put(entry.getWbs().getId().toString(), entry);
				}
				fdcSchedule.put("wbsAdjustEntryMap", wbsAdjustEntryMap);
				fdcSchedule.put("AdjustInfo", reqInfo);
				for (Iterator it = fdcSchedule.getTaskEntrys().iterator(); it.hasNext();) {
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) it.next();
					task.setIsAdd(wbsAdjustEntryMap.containsKey(task.getWbs().getId().toString()));
					if (oldWbsTaskMap.containsKey(task.getWbs().getId().toString())) {
						FDCScheduleTaskInfo oldTask = (FDCScheduleTaskInfo) oldWbsTaskMap.get(task.getWbs().getId().toString());
						task.put("myOldStartDate", oldTask.getStart());
						task.put("myOldEndDate", oldTask.getEnd());
						task.put("myOldDuration", Integer.valueOf(String.valueOf(oldTask.getDuration())));
					} else {
						task.remove("myOldStartDate");
						task.remove("myOldEndDate");
						task.remove("myOldDuration");
					}
				}
			} else {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("project.id", prjId, CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("scheduleType.id", TaskTypeInfo.TASKTYPE_SPECIALTASK, CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("state", ScheduleStateEnum.SAVED_VALUE, CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("state", ScheduleStateEnum.SUBMITTED_VALUE, CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("state", ScheduleStateEnum.AUDITTING_VALUE, CompareType.EQUALS));
				filter.setMaskString("#0 and #1 and ( #2 or #3 or #4)");

				if (FDCScheduleFactory.getLocalInstance(ctx).exists(filter)) {
					throw new EASBizException(new NumericExceptionSubItem("100", "专项计划正在编制(存在未审批专项计划),不能进行调整！"));
				}
				fdcSchedule = ScheduleVerManagerFactory.getLocalInstance(ctx).getVerData(adjustVerID.toString());
			}
			return fdcSchedule;
		}
	}

	private void createTaskEntrys(Context ctx, FDCScheduleInfo info, FDCWBSCollection wbsColl, String baseVerId) throws BOSException, EASBizException {
		StopWatch sw = new StopWatch();
		sw.start();

	}

	protected IObjectValue _getScheduleInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map _getScheduleInfoMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "_getScheduleInfo");
		// ////////////////////////////////////////////////////////////////////////
		
		EntityViewInfo view = ScheduleHelper.getScheduleView(pk.toString());
		FDCScheduleCollection col = getFDCScheduleCollection(ctx, view);
		FDCScheduleInfo schedule = col.get(0);
		if(schedule != null){
			afterGetSchedule(ctx, schedule);
		}
		
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "_getScheduleInfo", _getScheduleInfoMap);
		// ////////////////////////////////////////////////////////////////////////
		
		return schedule;
	}

	protected Map _cancel(Context ctx, Set ids) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule where (fstate<>? or fislatestver<>1) and ");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		Object[] arrayIds = ids.toArray();
		builder.appendParam("fid", arrayIds);
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "只能反启用执行状态下最新版本的计划"));
		}

		builder.clear();
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? where ");
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.appendParam("fid", arrayIds);
		builder.execute();

		// 将关联的版本变成执行状态
		builder.clear();
		builder.appendSql("update T_SCH_ScheduleVerManager set fstate=? ");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.appendSql(" where fid in (select fparentid from T_SCH_ScheduleVerManagerEntry where  ");
		builder.appendParam(" fscheduleid", arrayIds);
		builder.appendSql(" ) and fislatestver=1 ");
		builder.execute();
		return null;
	}

	/*
	 * add by warship at 2010/07/20 检查计划的状态，只有已审批的状态才可以执行
	 */
	protected void _checkScheduleState(Context ctx, Set ids) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule where (fstate<>? or fislatestver<>1) and ");
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.appendParam("fid", ids.toArray());
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "只能执行审批状态下最新版本的计划"));
		}
	}

	protected Map _cancelCancel(Context ctx, Set ids) throws BOSException, EASBizException {
		// set state to executing

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// builder
		// .appendSql(
		// "select top 1 fid from T_SCH_FDCSchedule where (fstate<>? or fislatestver<>1) and "
		// );
		// builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		// builder.appendParam("fid", ids.toArray());
		// if (builder.isExist()) {
		// throw new EASBizException(new NumericExceptionSubItem("111",
		// "只能执行审批状态下最新版本的计划"));
		// }
		// builder.clear();
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? where ");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.appendParam("fid", ids.toArray());
		builder.execute();

		// 更新最新版本为执行状态
		builder.clear();
		builder.appendSql("update T_SCH_ScheduleVerManager set fstate=? ");
		builder.appendSql(" where fid in (select distinct entry.fparentid from T_SCH_ScheduleVerManagerEntry entry ");
		builder.appendSql(" inner join t_sch_fdcschedule schedule on schedule.fid=entry.fscheduleid ");
		builder.appendSql(" where schedule.fstate=? ");
		builder.appendSql(" ) and fislatestver=1 and (fstate<>? or fstate is null)");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.execute();
		return null;
	}

	protected Map _close(Context ctx, Set ids) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule where fstate<>? and ");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.appendParam("fid", ids.toArray());
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "只能关闭执行中状态下的计划"));
		}

		builder.clear();
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? where fislatestver=1 and ");
		builder.addParam(ScheduleStateEnum.CLOSED_VALUE);
		builder.appendParam("fid", ids.toArray());
		builder.execute();
		return null;
	}

	protected Map _audit(Context ctx, Set ids) throws BOSException, EASBizException {
		checkBeforeAudit(ctx, ids);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		UserInfo currentUserInfo = ContextUtil.getCurrentUserInfo(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? ,fauditorid=? ,fauditTime=? where fislatestver=1 and ");
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.addParam(currentUserInfo.getId().toString());
		builder.addParam(new Date());
		builder.appendParam("fid", ids.toArray());
		builder.execute();
		// 错误提示等信息以后可以通过Map返回
		return null;
	}

	protected Map _unAudit(Context ctx, Set ids) throws BOSException, EASBizException {
		checkBeforeUnAudit(ctx, ids);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fstate=?,fauditorid=null ,fauditTime=null where fislatestver=1  and ");
		builder.addParam(ScheduleStateEnum.SUBMITTED_VALUE);
		builder.appendParam("fid", ids.toArray());
		builder.execute();
		// 错误提示等信息以后可以通过Map返回
		return null;
	}

	private void checkBeforeAudit(Context ctx, Set ids) throws BOSException, EASBizException {
		// must be submit
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule where fstate<>? and ");
		builder.addParam(ScheduleStateEnum.SUBMITTED_VALUE);
		builder.appendParam("fid", ids.toArray());
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "存在不是提交状态的计划,不能审批"));
		}
	}

	private void checkBeforeUnAudit(Context ctx, Set ids) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule t where (fstate<>?  ");
		builder.appendSql("  or exists (select 1 from T_SCH_FDCSchedule where fprojectid=t.fprojectid and fversion=0)) and ");
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.appendParam("fid", ids.toArray());
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "已经存在调整申请单,该计划不允许反审批!"));
		}
		builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule t where (fstate<>?  ");
		builder.appendSql("  or exists (select 1 from T_SCH_FDCSchedule where fprojectid=t.fprojectid and fversion>1)) and ");
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.appendParam("fid", ids.toArray());
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "存在不是审批状态或已经调整过的计划,不能反审批!"));
		}

		// 如果是主项节点,需检查下面是否有专项节点,如果有不让反审批
		builder.clear();
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule t where ");
		builder.appendParam("fid", ids.toArray());
		// /是主项机节点
		builder.appendSql(" and exists (select 1 from T_SCH_FDCSchedule where fscheduletypeid=? and fid=t.fid) ");
		// /有专项节点计划
		builder.appendSql(" and exists (select 1 from T_SCH_FDCSchedule where fscheduletypeid<>? and fprojectid=t.fprojectid )");
		builder.addParam(TaskTypeInfo.TASKTYPE_MAINTASK);
		builder.addParam(TaskTypeInfo.TASKTYPE_MAINTASK);
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("112", "主项节点计划下面有专项任务计划,不能反审批!"));
		}

		// 是否被调整单引用
		builder.clear();
		builder.appendSql("select top 1 schedule.fid from T_SCH_FDCSchedule schedule ");
		builder.appendSql(" inner join T_SCH_FDCScheduleTask task on schedule.fid=task.fscheduleid ");
		builder.appendSql(" where ");
		builder.appendParam("schedule.fid", ids.toArray());
		// /调整工期
		builder.appendSql(" and (");
		builder.appendSql(" exists (select 1 from T_SCH_ScheduleAdjustTaskEntry where ftaskid=task.fid) ");
		// /新增任务
		builder.appendSql(" or exists (select 1 from T_SCH_ScheduleNewTaskEntry where fparentTaskId=task.fid )  )");

		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("113", "计划已经进行过调整不能反审批"));
		}
	}

	protected void _setExecuting(Context ctx, int day) throws BOSException, EASBizException {
		Calendar calendar = Calendar.getInstance();
		// int day=FDCHelper.toBigDecimal(days).intValue();
		calendar.add(Calendar.DAY_OF_MONTH, 1); // 用明天去比较
		calendar.add(Calendar.DAY_OF_MONTH, day * -1);// 提前量
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_Sch_FDCSchedule set fstate=? where fstartDate<=? and fstate=? and fislatestver=1 ");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.addParam(calendar.getTime());
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.execute();

		// 更新最新版本为执行状态
		builder.clear();
		builder.appendSql("update T_SCH_ScheduleVerManager set fstate=? ");
		builder.appendSql(" where fid in (select distinct entry.fparentid from T_SCH_ScheduleVerManagerEntry entry ");
		builder.appendSql(" inner join t_sch_fdcschedule schedule on schedule.fid=entry.fscheduleid ");
		builder.appendSql(" where schedule.fstate=? ");
		builder.appendSql(" ) and fislatestver=1 and (fstate<>? or fstate is null)");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.execute();
	}

	protected void _setStart(Context ctx, int days) throws BOSException, EASBizException {
		// 按李涛的说法,启用就是执行
		_setExecuting(ctx, days);
	}

	protected void _saveNewTask(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		FDCScheduleInfo schedule = (FDCScheduleInfo) model;
		if (schedule == null || schedule.getTaskEntrys().size() == 0) {
			return;
		}
		// save new task
		if (schedule.getCalendar() == null) {
			ScheduleAppHelper.setCalendar(ctx, schedule);
		}
		IORMappingDAO dao = ORMappingDAO.getInstance(schedule.getTaskEntrys().get(0).getBOSType(), ctx, getConnection(ctx));
		for (Iterator iter = schedule.getTaskEntrys().iterator(); iter.hasNext();) {
			FDCScheduleTaskInfo newTask = (FDCScheduleTaskInfo) iter.next();
			if (newTask.getId() != null) {
				dao.deleteBatch(new ObjectUuidPK(newTask.getId()));
			}
			dao.addNewBatch(newTask);
		}
		dao.executeBatch();

		// save new depends...
		// update schedule start/end
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select min(fstart) fstartDate,max(fend) fenddate from T_Sch_FDCScheduleTask where fscheduleId=? ");
		builder.addParam(schedule.getId().toString());
		IRowSet rowSet = builder.executeQuery();
		try {
			if (rowSet.next()) {
				Date startDate = rowSet.getDate("fstartDate");
				Date endDate = rowSet.getDate("fendDate");
				BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(startDate, endDate, schedule.getCalendar());
				BigDecimal natureTimes = ScheduleCalendarHelper.getNatureTimes(startDate, endDate);
				builder.clear();
				builder.appendSql("update T_SCH_FDCSchedule set fstartDate=?,fendDate=?,feffectTimes=?,fnatureTimes=? where fid=?");
				builder.addParam(startDate);
				builder.addParam(endDate);
				builder.addParam(effectTimes);
				builder.addParam(natureTimes);
				builder.addParam(schedule.getId().toString());
				builder.execute();
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
	}

	protected void _audit(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		
		//更改上一版本为非最终版本
		FDCScheduleInfo info = getFDCScheduleInfo(ctx,new ObjectUuidPK(billID));
		float currVer = info.getVersion()-1f;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fislatestver = 0 where fprojectid = ?");
		builder.addParam(info.getProject().getId().toString());
		if(info.getProjectSpecial() != null){
			builder.appendSql(" and fprojectspecialid = ?");
			builder.addParam(info.getProjectSpecial().getId().toString());
		}else{
			builder.appendSql(" and fprojectspecialid is null");
		}
		builder.appendSql(" and fversion = ?");
		builder.addParam(Float.valueOf(currVer));
		builder.execute();
		
		builder.clear();
		builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fstate=?,fislatestver=1 where ");
		builder.addParam(ScheduleStateEnum.AUDITTED.getValue());
		builder.appendParam("fid", billID.toString());
		builder.execute();
		
		syscScheduleTask(ctx, billID);
        //重算全部任务(取得此进度进度中的级次最大的任务，然后进行进度树重算)
		EntityViewInfo view = new EntityViewInfo();
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("parent.id");
		sic.add("parent.name");
		sic.add("parent.number");
		view.setSelector(sic);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule",billID.toString()));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		view.setFilter(filter);
		
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo sort = new SorterItemInfo("level");
		sort.setSortType(SortType.DESCEND);
		sorter.add(sort);
		sort = new SorterItemInfo("longnumber");
		sort.setSortType(SortType.DESCEND);
		sorter.add(sort);
		view.setSorter(sorter);
		
		FDCScheduleTaskCollection taskEntry = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		
		if(!taskEntry.isEmpty()){
			FDCScheduleTaskInfo task = taskEntry.get(0);
			int prevLevel = -1;
			String prevLongNumber = null;
			for(Iterator it = taskEntry.iterator();it.hasNext();){
				task = (FDCScheduleTaskInfo) it.next();
				if(task.getLevel() == -1){
					prevLevel = task.getLevel();
				}
				if(prevLongNumber == null){
					prevLongNumber = task.getLongNumber();
				}
				//一个层级的任务只需要重算一次即可
				if(task.getLevel()  == prevLevel && prevLongNumber.substring(0,prevLongNumber.lastIndexOf('.')).equals(task.getLongNumber().substring(0,task.getLongNumber().lastIndexOf('.'))) )
				{
					continue;
				}
				FDCScheduleCompleteHelper.updateAllTaskCompleteRate(ctx, task);
			}
			
		}
		
		
		// 更新审批人审批时间
		builder.clear();
		builder.appendSql("update t_sch_fdcschedule set fauditorid=?,faudittime = now() where fid = ? ");
		builder.addParam(ContextUtil.getCurrentUserInfo(ctx).getId().toString());
		builder.addParam(billID.toString());
		builder.execute();
		
		updatePayPlanScheduleNode(ctx, billID);
	}

	/**
	 * 资金计划更新进度节点。<br>
	 * 先判断资金计划模块(类PayPlanNewFactory)是否在在，若存在才调其onScheduleChange接口更新资金计划的内容<br>
	 * 等价于直接调用 com.kingdee.eas.fdc.finance.PayPlanNewFactory.onScheduleChange(billID.toString())
	 * @author owen_wen 2014-04-03
	 */
	private void updatePayPlanScheduleNode(Context ctx, BOSUuid billID) throws BOSException {
		try {
			Class.forName("com.kingdee.eas.fdc.finance.PayPlanNewFactory");
			Object obj = BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A633823E"));
			FdcMethodUtil.invokeMethod(obj, "onScheduleChange", new Class[] { String.class }, new String[] { billID.toString() }, true);
		} catch (ClassNotFoundException e) {
			logger.info(e.getMessage());
		} 
	}
	
	/**
	 * @deprecated
	 * 描述：
	 * @param ctx
	 * @param info
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * 创建人：yuanjun_lan
	 * 创建时间：2011-11-28
	 */
	private String getPreviewSchedule(Context ctx,FDCScheduleInfo info) throws BOSException, SQLException{
		String scheduleId = null;
		float currVer = info.getVersion()-1f;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select fid  where fprojectid = ?");
		builder.addParam(info.getProject().getId().toString());
		if(info.getProjectSpecial() != null){
			builder.appendSql(" and fprojectspecialid = ?");
			builder.addParam(info.getProjectSpecial().getId().toString());
		}else{
			builder.appendSql(" and fprojectspecialid is null");
		}
		builder.appendSql(" and fversion = ?");
		builder.addParam(Float.valueOf(currVer));
		IRowSet rs = builder.executeQuery();
		while(rs.next()){
			scheduleId = rs.getString(1);
		}
		return scheduleId;
	}
	/**
	 * @deprecated
	 * 描述：
	 * @param ctx
	 * @param info
	 * @param prevID
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @throws EASBizException
	 * 创建人：yuanjun_lan
	 * 创建时间：2011-11-28
	 */
	private Set getNewTaskComparePreVersion(Context ctx,FDCScheduleInfo info,String prevID) throws BOSException, SQLException, EASBizException{
		Set newTaskSet = new HashSet();
		int newScheduleTaskCount = 0;
		int oldScheduleTaskCount = 0;
		boolean isDelete = false;
		boolean isAdd = false;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select count(*) from t_sch_fdcscheduletask where fscheduleid = ?");
		builder.addParam(info.getId().toString());
		IRowSet rs = builder.executeQuery();
		while(rs.next()){
			newScheduleTaskCount = rs.getInt(1);
		}
		
		builder.appendSql("select count(*) from t_sch_fdcscheduletask where fscheduleid = ?");
		builder.addParam(prevID);
		rs = builder.executeQuery();
		while(rs.next()){
			oldScheduleTaskCount = rs.getInt(1);
		}
		
		if(newScheduleTaskCount>oldScheduleTaskCount){
			isAdd = true;
		}else if(oldScheduleTaskCount>newScheduleTaskCount){
			isDelete = true;
		}
		StringBuffer sql = new StringBuffer("select fid from t_sch_fdcscheduletask where fsrcid in(select fsrcid from t_sch_fdcscheduletask where ");
		if(isAdd){
			sql.append("fscheduleid = '");
			sql.append(info.getId().toString());
			sql.append("'");
			sql.append(" and fsrcid not in( select fsrc from t_sch_fdcscheduletask where ");
			sql.append("scheduleid = '");
			sql.append(prevID);
			sql.append("'))");
			
		}
		
		if(isDelete){
			sql.append("fscheduleid = '");
			sql.append(prevID);
			sql.append("'");
			sql.append(" and fsrcid not in( select fsrc from t_sch_fdcscheduletask where ");
			sql.append("scheduleid = '");
			sql.append(info.getId().toString());
			sql.append("'))");
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule",sql.toString(),CompareType.INNER));
		FDCScheduleTaskCollection cols = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		
		FDCScheduleTaskInfo task = null;
		if(isAdd){
			for(Iterator it = cols.iterator();it.hasNext();){
				task = (FDCScheduleTaskInfo) it.next();
				FDCScheduleCompleteHelper.updateAllTaskCompleteRate(ctx, task);
			}
		}
		
		if(isDelete){
			for(Iterator it = cols.iterator();it.hasNext();){
				task = (FDCScheduleTaskInfo) it.next();
				
			}
		}
		
		return newTaskSet;
	}

	
	
	private void syscScheduleTask(Context ctx, BOSUuid billID) throws EASBizException, BOSException{
		/**把上一版本上的所有任务的实际开工日期，完工工程量同步到新的任务上来，以便执行来汇报及评价*/
		FDCScheduleInfo scheduleInfo = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleInfo(new ObjectUuidPK(billID));
		float lastVersion = scheduleInfo.getVersion() - 1f;
		String projectId = scheduleInfo.getProject().getId().toString();
		if(lastVersion < 1f){
			return ;
		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		StringBuffer sql = new StringBuffer( "update t_sch_fdcscheduletask tartask set fcomplete = isnull((");
		sql.append("select fcomplete  from t_sch_fdcscheduletask task  right outer join t_sch_fdcschedule sch on task.fscheduleid = sch.fid ");
		sql.append("left outer join t_fdc_curproject prj on prj.fid = sch.fprojectid");
		sql.append(" where fversion = ? and sch.fprojectid = ? and tartask.fsrcid = task.fsrcid ");
		builder.appendSql(sql.toString());
		builder.addParam(lastVersion+"");
		builder.addParam(projectId);
		if(scheduleInfo.getProjectSpecial() == null){
			builder.appendSql("and (sch.fprojectspecialid is null or sch.fprojectspecialid = '') ),0)");
		}else{
			builder.appendSql("and sch.fprojectspecialid = ?),0)");
			builder.addParam(scheduleInfo.getProjectSpecial().getId().toString());
		}
		builder.appendSql(" where fscheduleid = ? ");
		builder.addParam(scheduleInfo.getId().toString());
		logger.info(builder.getTestSql());
		builder.execute();
		
		sql = new StringBuffer( "update t_sch_fdcscheduletask tartask set fworkload = isnull((");
		sql.append("select fworkload  from t_sch_fdcscheduletask task  right outer join t_sch_fdcschedule sch on task.fscheduleid = sch.fid ");
		sql.append("left outer join t_fdc_curproject prj on prj.fid = sch.fprojectid");
		sql.append(" where fversion = ? and sch.fprojectid = ? and tartask.fsrcid = task.fsrcid ");
		builder.appendSql(sql.toString());
		builder.addParam(lastVersion+"");
		builder.addParam(projectId);
		if(scheduleInfo.getProjectSpecial() == null){
			builder.appendSql("and (sch.fprojectspecialid is null or sch.fprojectspecialid = '')),0)");
		}else{
			builder.appendSql("and sch.fprojectspecialid = ?),0)");
			builder.addParam(scheduleInfo.getProjectSpecial().getId().toString());
		}
		builder.appendSql(" where fscheduleid = ? ");
		builder.addParam(scheduleInfo.getId().toString());
		logger.info(builder.getTestSql());
		builder.execute();
		
		builder.clear();
		sql = new StringBuffer( "update t_sch_fdcscheduletask tartask set FACTUALENDDATE = (");
		sql.append("select FACTUALENDDATE  from t_sch_fdcscheduletask task  right outer join t_sch_fdcschedule sch on task.fscheduleid = sch.fid ");
		sql.append("left outer join t_fdc_curproject prj on prj.fid = sch.fprojectid");
		sql.append(" where fversion = ? and sch.fprojectid = ? and tartask.fsrcid = task.fsrcid ");
		builder.appendSql(sql.toString());
		builder.addParam(lastVersion+"");
		builder.addParam(projectId);
		if(scheduleInfo.getProjectSpecial() == null){
			builder.appendSql("and (sch.fprojectspecialid is null or sch.fprojectspecialid = ''))");
		}else{
			builder.appendSql("and sch.fprojectspecialid = ?)");
			builder.addParam(scheduleInfo.getProjectSpecial().getId().toString());
		}
		builder.appendSql(" where fscheduleid = ?");
		builder.addParam(scheduleInfo.getId().toString());
		logger.info(builder.getTestSql());
		builder.execute();
		
		
		
		builder.clear();
		sql = new StringBuffer( "update t_sch_fdcscheduletask tartask set FACTUALSTARTDATE = (");
		sql.append("select FACTUALSTARTDATE  from t_sch_fdcscheduletask task  right outer join t_sch_fdcschedule sch on task.fscheduleid = sch.fid ");
		sql.append("left outer join t_fdc_curproject prj on prj.fid = sch.fprojectid");
		sql.append(" where fversion = ? and sch.fprojectid = ? and tartask.fsrcid = task.fsrcid ");
		builder.appendSql(sql.toString());
		builder.addParam(lastVersion+"");
		builder.addParam(projectId);
		if(scheduleInfo.getProjectSpecial() == null){
			builder.appendSql("and (sch.fprojectspecialid is null or sch.fprojectspecialid = ''))");
		}else{
			builder.appendSql("and sch.fprojectspecialid = ?)");
			builder.addParam(scheduleInfo.getProjectSpecial().getId().toString());
		}
		builder.appendSql(" where fscheduleid = ?");
		builder.addParam(scheduleInfo.getId().toString());
		logger.info(builder.getTestSql());
		builder.execute();
		
		
		builder.clear();
		// 不知道为啥要同步，新版本如果做了修改的话，这个地方是不能同步的？？？
		sql = new StringBuffer( "update t_sch_fdcscheduletask tartask set FDEPENDMAINTASKID = (");
		sql.append("select FDEPENDMAINTASKID  from t_sch_fdcscheduletask task  right outer join t_sch_fdcschedule sch on task.fscheduleid = sch.fid ");
		sql.append("left outer join t_fdc_curproject prj on prj.fid = sch.fprojectid");
		sql.append(" where fversion = ? and sch.fprojectid = ? and tartask.fsrcid = task.fsrcid ");
		builder.appendSql(sql.toString());
		builder.addParam(lastVersion+"");
		builder.addParam(projectId);
		if(scheduleInfo.getProjectSpecial() == null){
			builder.appendSql("and (sch.fprojectspecialid is null or sch.fprojectspecialid = ''))");
		}else{
			builder.appendSql("and sch.fprojectspecialid = ?)");
			builder.addParam(scheduleInfo.getProjectSpecial().getId().toString());
		}
		builder.appendSql(" where fscheduleid = ?");
		builder.addParam(scheduleInfo.getId().toString());
		logger.info(builder.getTestSql());
		// builder.execute();
		
	}

	protected void _setAudittingStatus(Context ctx, BOSUuid billID) throws BOSException, EASBizException {

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? where ");
		builder.addParam(ScheduleStateEnum.AUDITTING.getValue());
		builder.appendParam("fid", billID.toString());
		builder.execute();
	}

	protected void _setSubmitStatus(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? where ");
		builder.addParam(ScheduleStateEnum.SUBMITTED.getValue());
		builder.appendParam("fid", billID.toString());
		builder.execute();

	}

	protected void _unAudit(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		FDCScheduleInfo schedule = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleInfo(new ObjectUuidPK(billID));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", schedule.getProject().getId().toString(), CompareType.EQUALS));
		if (ScheduleAdjustGattReqFactory.getLocalInstance(ctx).exists(filter)) {
			throw new EASBizException(new NumericExceptionSubItem("100", "已经存在调整申请单,该计划不允许反审批！"));
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? where ");
		builder.addParam(ScheduleStateEnum.SUBMITTED.getValue());
		builder.appendParam("fid", billID.toString());
		builder.execute();

	}

	protected void _unClose(Context ctx, Set idSet) throws BOSException, EASBizException {

	}

	protected void _setSendMsg(Context ctx, int days) throws BOSException, EASBizException {
		// TODO 任务执行日的消息提醒
	}

	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		FDCScheduleNumberHandler.recycleNumber(ctx, pk, _getValue(ctx, pk));
	}

	/**
	 * 
	 * @description 不断号处理
	 * @author 杜红明
	 * @createDate 2011-9-2
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException void
	 * @version EAS7.0
	 * @see
	 */
	protected void handleIntermitNumber(Context ctx, FDCScheduleInfo info) throws BOSException, CodingRuleException, EASBizException {
		FDCScheduleNumberHandler.execute(ctx, info);
	}

	/**
	 *计划调整，新建，查看时 获取计划数据
	 * 
	 * @param isMainSchedule
	 *            是否主项计划
	 *@param isAdjust
	 *            是否调整（还原）
	 *@param project
	 *            工程项目
	 *@param projectSpecial
	 *            项目专项
	 *@return FDCScheduleInfo
	 * @throws EASBizException
	 */
	protected IObjectValue _getScheduleInfo(Context ctx, boolean isMainSchedule, boolean isAdjust, IObjectValue project, IObjectValue projectSpecial,Map param)
			throws BOSException, EASBizException {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map _getScheduleInfoMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "_getScheduleInfo");
		// ////////////////////////////////////////////////////////////////////////
		
		boolean isFormExecuteUI = false;
		if(param != null && param.get("isFormExecuteUI") != null){
			isFormExecuteUI = Boolean.valueOf(param.get("isFormExecuteUI").toString()).booleanValue();
		}
		boolean isTotalUI = false;
		if (param != null && param.get("isFormTotalUI") != null) {
			isTotalUI = Boolean.valueOf(param.get("isFormTotalUI").toString())
					.booleanValue();
		}
		CurProjectInfo curProject = getProject(ctx, isMainSchedule, (CurProjectInfo) project);
		ProjectSpecialInfo special = isMainSchedule ? null : getSpecial(ctx, (ProjectSpecialInfo) projectSpecial);
		if(!isMainSchedule && special == null){
			return createNewSchedule(ctx, curProject, special);
		}
		if (curProject != null) {
			FDCScheduleInfo schedule = null;
			if (isTotalUI) {
				try {
					schedule = getTotalSchedule(ctx, curProject);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				schedule = getFDCSchedule(ctx, curProject, special,
						isFormExecuteUI);
				if (schedule != null && special != null) {
					SpecialScheduleUtils.reBuilderSpecialSchedule(ctx, curProject, special, schedule);
					
				}
			}
			if (schedule != null) {
				// 处理前置任务
				// handleTasks(schedule);
				// 获取上一版本信息用于版本对本
				afterGetSchedule(ctx,schedule);
				return schedule;
			}
		}
		
		IObjectValue objectValue = createNewSchedule(ctx, curProject, special);

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "_getScheduleInfo", _getScheduleInfoMap);
		// ////////////////////////////////////////////////////////////////////////

		return objectValue;
	}
	
	private void afterGetSchedule(Context ctx,FDCScheduleInfo schedule) throws BOSException, EASBizException{
		
		putOldDateOfTask(ctx, schedule);
		// 业务类型多选时用逗号连接成一个字段
		putBizType(schedule);
		// 执行时需要根据完成情况计算每一条任务的状态
		try {
			FDCScheduleTaskStateHelper.putState(ctx, schedule);
		} catch (SQLException e) {
			throw new BOSException(e.getCause());
		}
		// 取得关联合同
		putReloationContract(ctx, schedule);
	}

	/**
	 * 取总进度计划
	 * <p>
	 * 1、先取最新版本的主项计划<br>
	 * 2、取所有关联了的相关专项及其子集ID<br>
	 * 3、根据ID取得所有Info<br>
	 * 4、将这些任务添加到对应的主项下<br>
	 * 5、重算seq，使序号正常
	 * 
	 * @param ctx
	 * @param curProject
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected FDCScheduleInfo getTotalSchedule(Context ctx,
			CurProjectInfo curProject) throws BOSException, SQLException {
		if (curProject == null) {
			return null;
		}
		// 1、先取最新版本的主项计划
		EntityViewInfo view = ScheduleHelper.getScheduleView();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", curProject.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
		view.setFilter(filter);
		FDCScheduleCollection col = getFDCScheduleCollection(ctx, view);
		if (col != null && col.size() > 0) {
			FDCScheduleInfo scheduleInfo = col.get(0);
			// 2、取所有关联了的相关专项及其子集ID
			FDCScheduleTaskCollection mainScheduleTasks = scheduleInfo.getTaskEntrys();
			Set tasks = new HashSet();
			for (int i = 0; i < mainScheduleTasks.size(); i++) {
				FDCScheduleTaskInfo entryInfo = mainScheduleTasks.get(i);
				entryInfo.put("totalSchedule", "main");
				tasks.add(entryInfo.getId().toString());
			}
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			sql.appendSql(" select task.FID as taskID ");
			sql.appendSql(" from T_SCH_FDCScheduleTask as task ");
			sql.appendSql(" left join ( ");
			sql.appendSql(" select sTask.FScheduleID as schID ");
			sql.appendSql(" ,sTask.FLongNumber as taskNum ");
			sql.appendSql(" from T_SCH_FDCScheduleTask as sTask ");
			sql.appendSql(" left join T_SCH_FDCScheduleTask as mTask ");
			sql.appendSql(" on mTask.FSRCID = sTask.FDependMainTaskID ");
			sql.appendSql(" left join T_SCH_FDCSchedule ssch on ssch.fid = sTask.fscheduleid ");
			sql.appendSql(" where ssch.fislatestver = 1 and  mTask.FID in ");
			sql.appendSql(FMHelper.setTran2String(tasks));
			sql.appendSql(" ) as rel ");
			sql.appendSql(" on task.FScheduleID = rel.SchID ");
			sql.appendSql(" where rel.SchID is not null and ");
			sql.appendSql(" task.FLongNumber like concat(rel.taskNum ,'%') ");
			IRowSet rs = sql.executeQuery();
			tasks.clear();
			while (rs.next()) {
				tasks.add(rs.getString("taskID"));
			}
			// 3、根据ID取得所有Info
			view = new EntityViewInfo();
			view.setSelector(ScheduleHelper.getTaskSelector());
			//有些客户环境排序字段("schedule.id")必须出现在查询字段中，否则排序会失效 Added by Owen_wen 2013-11-12
			view.getSelector().add("schedule.id"); 
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", tasks, CompareType.INCLUDE));
			view.setFilter(filter);
			SorterItemCollection sorter = new SorterItemCollection();
			SorterItemInfo sortSch = new SorterItemInfo("schedule.id");
			SorterItemInfo sortNum = new SorterItemInfo("longNumber");
			sorter.add(sortSch);
			sorter.add(sortNum);
			view.setSorter(sorter);
			FDCScheduleTaskCollection specialTasks = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
			if (specialTasks != null) {
				// 4、将这些任务添加到对应的主项下
				int index = 0;
				for (int i = 0; i < specialTasks.size(); i++) {
					FDCScheduleTaskInfo specialTask = specialTasks.get(i);
					FDCScheduleTaskInfo mainTask = specialTask.getDependMainTaskID();
					if (mainTask != null) {
						// 相关主项不为空，则把该条任务加到这条主项之后
						for (int j = 0; j < mainScheduleTasks.size(); j++) {
							FDCScheduleTaskInfo mainInfo = mainScheduleTasks.get(j);
							if (mainTask.getId() != null && mainTask.getId().toString().equals(mainInfo.getSrcID().toString())) {
								specialTask.setParent(mainInfo);
								specialTask.getDependEntrys().clear();
								specialTask.put("totalSchedule", "special");
								mainScheduleTasks.addObject(j + 1, specialTask);
								index = j + 1;
								break;
							}
						}
					} else {
						// 相关主项为空，说明是上一条相关主项不为空任务的子任务，加到上一条之后
						specialTask.getDependEntrys().clear();
						specialTask.put("totalSchedule", "special");
						mainScheduleTasks.addObject(++index, specialTask);
					}
				}
				// 5、重算Seq，使序号正常
				for (int i = 0; i < mainScheduleTasks.size(); i++) {
					mainScheduleTasks.get(i).setSeq(i + 1);
				}
			}
			return scheduleInfo;
		}
		return null;
	}

	public boolean getCurrentTaskStage(FDCScheduleTaskInfo info ){
		return false;
	}
	

	/**
	 * 
	 * @description 多业务类型处理
	 * @author 杜红明
	 * @createDate 2011-9-26
	 * @param schedule
	 *            void
	 * @version EAS7.0
	 * @see
	 */
	protected void putBizType(FDCScheduleInfo schedule) {
		if (schedule.getTaskEntrys() != null) {
			FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
			for (int i = 0; i < taskEntrys.size(); i++) {
				FDCScheduleTaskInfo scheduleTaskInfo = taskEntrys.get(i);
				ScheduleTaskBizTypeCollection bizTypes = scheduleTaskInfo.getBizType();
				if (bizTypes != null && bizTypes.size() > 0) {
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < bizTypes.size(); j++) {
						ScheduleTaskBizTypeInfo bizType = bizTypes.get(j);
						
						if (j > 0) {
							if(bizType.getBizType() != null && bizType.getBizType().getName()!=null){
								sb.append(","); 
								sb.append(bizType.getBizType().getName());
							}
						} else {
							sb.append(bizType.getBizType()==null?null:bizType.getBizType().getName());
						}
					}
					scheduleTaskInfo.put("taskBizType", sb.toString());
				}
			}
		}
	}

	/**
	 * 
	 * @description 获取处理后的任务
	 * @author 杜红明
	 * @createDate 2011-9-2
	 * @param schedule
	 *            void
	 * @version EAS7.0*
	 * @throws BOSException 
	 * @see
	 */
	private void handleTasks(FDCScheduleInfo schedule) throws BOSException {
		FDCScheduleTaskCollection newTasks = new FDCScheduleTaskCollection();
		FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
		newTasks = getRelationReverseTasks(newTasks, taskEntrys);
		schedule.getTaskEntrys().addCollection(newTasks);
	}



	/**
	 * 
	 * @description 获得上版本任务的日期放入现在的任务对象中
	 * @author 杜红明
	 * @createDate 2011-9-2
	 * @param schedule
	 *            void
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	protected void putOldDateOfTask(Context ctx, FDCScheduleInfo schedule) throws BOSException {
		FDCScheduleInfo oldSchedule = null;
		if (schedule != null && schedule.getVersion() > 1f) {
			oldSchedule = (FDCScheduleInfo) _getSchedule4Compare(ctx, null, schedule.getId().toString());
			FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
			if (oldSchedule != null) {
			FDCScheduleTaskCollection oldTasks = oldSchedule.getTaskEntrys();
			for (int i = 0; i < taskEntrys.size(); i++) {
				String id = taskEntrys.get(i).getSrcID();
				FDCScheduleTaskInfo task = schedule.getTaskEntrys().get(i);
				for (int j = 0; j < oldTasks.size(); j++) {
					FDCScheduleTaskInfo oldTask = oldTasks.get(j);
					if (null != id && null != oldTask && null != oldTask.getSrcID() && oldTask.getSrcID().toString().equals(id)) {
						task.put("myOldStartDate", oldTask.getStart());
						task.put("myOldEndDate", oldTask.getEnd());
						task.put("myOldCheckDate", oldTask.getCheckDate()); 
					}
				}
			}
			}
		}
		
	}

	/**
	 * 任务前后置互换
	 * <p>
	 * 由于甘特图中是按前置任务显示，但是数据库保存是按后置任务存，所以这里要换位
	 * 
	 * @author 杜红明
	 * @createDate 2011-9-2
	 * @param newTasks
	 * @param taskEntrys
	 * @return FDCScheduleTaskCollection
	 * @version EAS7.0
	 */
	private FDCScheduleTaskCollection getRelationReverseTasks(FDCScheduleTaskCollection newTasks, FDCScheduleTaskCollection taskEntrys) {
		/** 清理了任务中的前置任务后的任务对象 **/
		for (int i = 0; i < taskEntrys.size(); i++) {
			FDCScheduleTaskInfo task = taskEntrys.get(i);
			FDCScheduleTaskInfo clone = (FDCScheduleTaskInfo) task.clone();
			clone.getDepends().clear();
			newTasks.add(clone);
		}

		for (int i = 0; i < taskEntrys.size(); i++) {
			FDCScheduleTaskInfo task = taskEntrys.get(i);
			ScheduleTaskDependCollection depends = task.getDepends();
			/** 循环任务中的前置任务 **/
			for (int j = 0; j < depends.size(); j++) {
				ScheduleTaskDependInfo depend = depends.get(j);
				ScheduleTaskDependInfo clone = (ScheduleTaskDependInfo) depend.clone();
				/** 在克隆的前置任务中放置它的：任务和前置任务(此时前置任务中放的是前置任务,前置任务中放的任务) **/
				clone.put("task", depend.getDependTaskBase());
				clone.put("dependTask", depend.getTaskBase());
				newTasks.get(i).getDepends().add(clone);
			}
		}
		return newTasks;
	}

	/**
	 * 取任务关联合同
	 * <p>
	 * 关联合同都绑定到源任务上，所以只取计划源ID与当前计划源ID相同的记录
	 * 
	 * @param ctx
	 * @param schedule
	 * @throws BOSException
	 */
	protected void putReloationContract(Context ctx, FDCScheduleInfo schedule)
			throws BOSException {
		// 取得当前计划的所有合同关系
		String schSrcID = schedule.getSrcID();
		if (FDCHelper.isEmpty(schSrcID)) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("task.id");
		sic.add("contract.id");
		sic.add("contract.number");
		sic.add("contract.name");
		sic.add("contract.originalAmount");
		sic.add("contract.respPerson.id");
		sic.add("contract.respPerson.number");
		sic.add("contract.respPerson.name");
		sic.add("contract.respDept.id");
		sic.add("contract.respDept.number");
		sic.add("contract.respDept.name");
		sic.add("contract.partB.id");
		sic.add("contract.partB.number");
		sic.add("contract.partB.name");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("task.schedule.srcID", schSrcID));
		view.setFilter(filter);
		SorterItemInfo sort = new SorterItemInfo("task.id");
		view.getSorter().add(sort);
		FDCSchTaskWithContractCollection relConCol = FDCSchTaskWithContractFactory
				.getLocalInstance(ctx)
				.getFDCSchTaskWithContractCollection(view);
		// 将关系put到对应任务中
		if (relConCol != null && relConCol.size() > 0) {
			Map relMap = new HashMap();
			for (int i = 0; i < relConCol.size(); i++) {
				FDCSchTaskWithContractInfo relInfo = relConCol.get(i);
				String taskID = relInfo.getTask().getId().toString();
				if (relMap.containsKey(taskID)) {
					List rel = (List) relMap.get(taskID);
					rel.add(relInfo);
				} else {
					List rel = new ArrayList();
					rel.add(relInfo);
					relMap.put(taskID, rel);
				}
			}
			for (int i = 0; i < schedule.getTaskEntrys().size(); i++) {
				FDCScheduleTaskInfo taskInfo = schedule.getTaskEntrys().get(i);
				taskInfo.put("relCon", relMap.get(taskInfo.getId().toString()));
			}
		}
	}

	protected IObjectValue createNewSchedule(Context ctx, CurProjectInfo project, ProjectSpecialInfo projectSpecial) throws BOSException,
			EASBizException {
		FDCScheduleInfo info = new FDCScheduleInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		info.setProject(project);
		info.setProjectSpecial(projectSpecial);
		// 设置默认日历 add by emanon
		if (project != null) {
			info.setCalendar(ScheduleCalendarFactory.getLocalInstance(ctx)
					.getDefaultCal(project.getId().toString()));
		}
		/** 第一次创建的时候设置进度的srcID **/
		info.setSrcID(info.getId().toString());
		// TODO
		/** 设置日历 **/
		// ScheduleAppHelper.setCalendar(ctx, info);
		/** 有可能没有工程项目任务 modfiy by duhongming 2011-08-25 **/
		// if (project == null) {
		info.setViewDate(new Date());
		info.setStartDate(new Date());
		// }
		info.setState(ScheduleStateEnum.SAVED);
		// TODO 设置基本属性
		info.setVersion(1f);
		info.setEditable(true);
		info.setIsLatestVer(false);
		info.setVersionDate(new Date());
		info.setAdminDept((FullOrgUnitInfo) SysContext.getSysContext().getCurrentOrgUnit());
		info.setAdminPerson((PersonInfo) (SysContext.getSysContext().getCurrentUser()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (project != null && projectSpecial != null) {
			info.setVersionName(project.getName() + projectSpecial.getName()
					+ sdf.format(new Date()) + "_"
					+ new Float(info.getVersion()) + "版");
		}
		if (project != null && projectSpecial == null) {
			info.setVersionName(project.getName() + sdf.format(new Date())
					+ "_" + new Float(info.getVersion()) + "版");
		} else {
			info.setVersionName(sdf.format(new Date()) + "_"
					+ new Float(info.getVersion()) + "版");
		}
		if(projectSpecial != null){
			SpecialScheduleUtils.reBuilderSpecialSchedule(ctx, project, projectSpecial, info);
		}
		return info;
	}

	/**
	 * 
	 * @description 获取进度
	 * @author 杜红明
	 * @createDate 2011-9-2
	 * @param ctx
	 * @param project
	 * @param projectSpecial
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 *             FDCScheduleInfo
	 * @version EAS7.0
	 * @see
	 */
	protected FDCScheduleInfo getFDCSchedule(Context ctx, CurProjectInfo project, ProjectSpecialInfo projectSpecial,boolean isFormExecute) throws BOSException,
			EASBizException {
		/** 获得最新版本的进度ID **/
		String billID = getNewestVersionScheduleID(ctx, project, projectSpecial,isFormExecute);
		if (FDCHelper.isEmpty(billID)) {
			return null;
		}
		FDCScheduleCollection col = getScheduleByID(ctx, billID);
		if (col == null || col.isEmpty()) {
			return null;
		} else if (col != null && col.size() == 1) {
			return col.get(0);
		} else {
			// TODO 抛出业务异常。提示"同一版本数据存在多条，请连线系统管理员修复数据"
			throw new EASBizException(EASBizException.CHECKEXIST);
		}
	}

	/**
	 * 
	 * @description 根据ID获取进度
	 * @author 杜红明
	 * @createDate 2011-9-2
	 * @param ctx
	 * @param billID
	 * @return
	 * @throws BOSException
	 *             FDCScheduleCollection
	 * @version EAS7.0
	 * @see
	 */
	private FDCScheduleCollection getScheduleByID(Context ctx, String billID) throws BOSException {
		/** 把查询语句抽出一个类来做,方便添加删除查询字段 modify by duhongming 2011-09-02 **/
		EntityViewInfo view = ScheduleHelper.getScheduleView(billID);
		FDCScheduleCollection col = getFDCScheduleCollection(ctx, view);
		return col;
	}

	/**
	 * 获得最新版本
	 * 
	 * @author 杜红明
	 * @createDate 2011-9-2
	 * @param ctx
	 * @param project
	 * @param projectSpecial
	 * @return String
	 * @throws BOSException
	 * @version EAS7.0
	 */
	private String getNewestVersionScheduleID(Context ctx, CurProjectInfo project, ProjectSpecialInfo projectSpecial, boolean isFormExecute)
			throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid as id from T_SCH_FDCSCHEDULE where ");
		if (projectSpecial == null) {
			builder.appendSql("(FProjectSpecialID is null or FProjectSpecialID = '') ");
		} else {
			builder.appendParam("FProjectSpecialID", projectSpecial.getId().toString());
		}
		if (isFormExecute) {
			builder.appendSql(" and fislatestver = 1");
			builder.appendSql(" and fstate = '4AUDITTED'");
		}
		builder.appendSql(" and FProjectID='" + project.getId().toString() + "'");
		builder.appendSql(" order by fversion desc");
		IRowSet rowSet = builder.executeQuery();
		String billID = "";
		try {
			if (rowSet.next()) {
				billID = rowSet.getString("id");
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return billID;
	}

	protected ProjectSpecialInfo getSpecial(Context ctx, ProjectSpecialInfo projectSpecial) throws EASBizException, BOSException {
		if (projectSpecial == null) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("name");
			selector.add("number");
			String historyProjectId = getAutoRememberProject(ctx, "SpecialScheduleProject");
			if(!StringUtils.isEmpty(historyProjectId) && historyProjectId.indexOf('!')>-1){
				String [] specialProject = historyProjectId.split("!");
				return ProjectSpecialFactory.getLocalInstance(ctx).getProjectSpecialInfo(new ObjectUuidPK(specialProject[1]));
			}else{
				return null;
			}
//			if (FDCHelper.isEmpty(historyProjectId)) {
//				return null;
//			}else{
//			 return (ProjectSpecialInfo) ProjectSpecialFactory.getLocalInstance(ctx).getProjectSpecialInfo(new ObjectStringPK(historyProjectId),selector);}
		}
		return projectSpecial;
	}

	/**
	 * 
	 * @description 查询工程项目
	 * @author 杜红明
	 * @createDate 2011-9-2
	 * @param ctx
	 * @param isMainProject
	 * @param isMainSchedule
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 *             Object
	 * @version EAS7.0
	 * @see
	 */
	private Object queryProject(Context ctx, boolean isMainProject, boolean isMainSchedule) throws BOSException, EASBizException {
		String function = isMainSchedule ? "MainScheduleProject" : "SpecialScheduleProject";
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("name");
		selector.add("number");
		selector.add("costcenter.id");
		selector.add("costcenter.name");
		String historyProjectId = getAutoRememberProject(ctx, function);
		
		if (FDCHelper.isEmpty(historyProjectId)) {
			return null;
		}else if(historyProjectId.indexOf('!')>-1){
			String [] tempStr = historyProjectId.split("!");
			historyProjectId = tempStr[0];
		}
		
		if (isMainProject) {
			selector.add("startDate");
			selector.add("fullOrgUnit");
		}
		return (CurProjectInfo) CurProjectFactory.getLocalInstance(ctx).getProjectInfo(new ObjectStringPK(historyProjectId), selector);
	}

	protected CurProjectInfo getProject(Context ctx, boolean isMainSchedule, CurProjectInfo project) throws EASBizException, BOSException {
		if (project == null) {
			Object obj = queryProject(ctx, true, isMainSchedule);
			if (obj != null) {
				return (CurProjectInfo) obj;
			}
		}
		return project;
	}

	/**
	 * 
	 * @description 获得记忆工程项目
	 * @author 杜红明
	 * @createDate 2011-9-2
	 * @param ctx
	 * @param function
	 * @return
	 * @throws BOSException
	 *             String
	 * @version EAS7.0
	 * @see
	 */
	private String getAutoRememberProject(Context ctx, String function) throws BOSException {
		String userID = ContextUtil.getCurrentUserInfo(ctx).getId().toString();
		String orgUnitID = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		return REAutoRememberFactory.getLocalInstance(ctx).getValue(userID, orgUnitID, function);
	}

	/**
	 * @description 版本对比
	 * @author 杜红明
	 * @createDate 2011-9-13
	 * @version EAS7.0
	 * @param baseVerID 基准版本ID
	 * @param compareVerID 比较版本ID
	 * @see
	 */

	protected IObjectValue _getSchedule4Compare(Context ctx, String baseVerID, String compareVerID) throws BOSException {
		FDCScheduleInfo compareVerSchedule;
		FDCScheduleInfo baseVerSchedule;
		compareVerSchedule = getSchedule4Compare(compareVerID, ctx);// 获取比较版本计划
		if(baseVerID == null&&compareVerSchedule!=null){
		    baseVerID = getBaseVerID(ctx, compareVerSchedule.getSrcID(), compareVerSchedule.getVersion() - 1f);
		}
		baseVerSchedule = getSchedule4Compare(baseVerID, ctx);// 获取基准版本计划

		Map srcId2BaseSchTask = getSrcId2SchTaskMap(baseVerSchedule);//获取任务源id到任务的衍射。下同
		Map srcId2CompareSchTask = getSrcId2SchTaskMap(compareVerSchedule);

		// 基准版本计划任务数量
		for (int i=0; i < srcId2BaseSchTask.size(); ++i){
		// 循环基准版本的map，如果在基准版本里面能找到这一条比较本版数据,说明比较版本对任务的数据变化了，
			// 于是获取比较版本的数据放入到基准本版对象中
			FDCScheduleTaskInfo baseVerScheduleTask = baseVerSchedule.getTaskEntrys().get(i);
			FDCScheduleTaskInfo compareTask = (FDCScheduleTaskInfo) srcId2CompareSchTask.get(baseVerScheduleTask.getSrcID());
			if (compareTask != null) {
				// 基准版本任务
				baseVerScheduleTask.put("MyOldStartDate", compareTask.getStart());
				baseVerScheduleTask.put("MyOldEndDate", compareTask.getEnd());
				baseVerScheduleTask.put("MyOldDuration", new Integer(compareTask.getDuration()));
				baseVerScheduleTask.put("MyOldCheckDate", compareTask.getCheckDate());
			} else {
				baseVerScheduleTask.put("type", "delete");
			}
			
		}
		// 循环比较map,如果在基准版本里面不能获得比较本版任务数据,说明是新增的任务,于是放入到基准版本里面
		for (int i=0; i < srcId2CompareSchTask.size(); ++i){
			FDCScheduleTaskInfo compareVerScheduleTask = compareVerSchedule.getTaskEntrys().get(i);
			if (srcId2BaseSchTask.get(compareVerScheduleTask.getSrcID()) == null) {
				/** 在基准版本添加比较本版新增的任务 **/
				compareVerScheduleTask.put("type", "newTask");
				if (baseVerSchedule != null && baseVerSchedule.getTaskEntrys() != null) {
					baseVerSchedule.getTaskEntrys().add(compareVerScheduleTask);
				}
				/** 删除比较版本计划任务的前置任务 **/
				if (compareVerScheduleTask != null && compareVerScheduleTask.getDependEntrys() != null) {
					compareVerScheduleTask.getDependEntrys().clear();
				}
			}
		}
		return baseVerSchedule;
	}

	/**
	 * @description获取任务源id到任务的衍射
	 * @author 杜红明
	 * @createDate 2011-9-13 void
	 * @version EAS7.0
	 * @see
	 */

	private Map getSrcId2SchTaskMap(FDCScheduleInfo info) {
		Map map = new HashMap();
		if (info != null && info.getTaskEntrys() != null && info.getTaskEntrys().size() > 0) {
			FDCScheduleTaskCollection taskEntrys = info.getTaskEntrys();
			for (int i = 0; i < taskEntrys.size(); i++) {
				map.put(taskEntrys.get(i).getSrcID(), taskEntrys.get(i));
			}
		}
		return map;
	}

	private FDCScheduleInfo getSchedule4Compare(String compareVerID, Context ctx) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("srcID");
		view.getSelector().add("version");
		view.getSelector().add("taskEntrys.id");
		view.getSelector().add("taskEntrys.name");
		view.getSelector().add("taskEntrys.number");
		view.getSelector().add("taskEntrys.longNumber");
		view.getSelector().add("taskEntrys.level");
		view.getSelector().add("taskEntrys.isLeaf");
		view.getSelector().add("taskEntrys.end");
		view.getSelector().add("taskEntrys.start");
		view.getSelector().add("taskEntrys.duration");
		view.getSelector().add("taskEntrys.checkDate");
		view.getSelector().add("taskEntrys.srcID");
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("id", compareVerID);
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo item = new SorterItemInfo("taskEntrys.longNumber");
		sorter.add(item);
		view.setSorter(sorter);
		FDCScheduleCollection scheduleCollection = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleCollection(view);
		if (scheduleCollection != null && scheduleCollection.size() > 0) {
			return scheduleCollection.get(0);
		}
		return null;
	}

	private String getBaseVerID(Context ctx, String srcID, float version) throws BOSException {
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("id");
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("srcID", srcID);
		filter.appendFilterItem("version", new Float(version));
		view.setFilter(filter);
		FDCScheduleCollection scheduleCollection = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleCollection(view);
		if (scheduleCollection != null && scheduleCollection.size() > 0) {
			return scheduleCollection.get(0).getId().toString();
		}
		return null;
	}

	/**
	 * @description 获得最新版本的进度
	 * @author 杜红明
	 * @createDate 2011-9-17
	 * @version EAS7.0
	 * @see
	 */

	protected IObjectValue _getNewestVerSchedule(Context ctx, IObjectValue curProject, IObjectValue projectSpecial) throws BOSException,
			EASBizException {
		  return getNewestVerSchedule(ctx,curProject,projectSpecial,false);
	}
	
	protected IObjectValue getNewestVerSchedule(Context ctx, IObjectValue curProject, IObjectValue projectSpecial,boolean isFromExecute)throws BOSException,
	EASBizException  {
		String scheduleID = getNewestVersionScheduleID(ctx, (CurProjectInfo) curProject, (ProjectSpecialInfo) projectSpecial,isFromExecute);
		return FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleInfo(new ObjectStringPK(scheduleID));
	}

	/**
	 * 由于系统有很多批量修改的需求，抽取此方法
	 * 为了便于以后扩展，将返回参数定义为MAP
	 * 主要完成以下功能：
	 * 1、完成任务的修改
	 * 2、取出修改后的最新内容并返回
	 * @see com.kingdee.eas.fdc.schedule.app.AbstractFDCScheduleControllerBean#_batchChangeTaskProperties(com.kingdee.bos.Context, java.util.Map)
	 */
	protected Map _batchChangeTaskProperties(Context ctx, Map param) throws BOSException, EASBizException {
		boolean isNeedReload = false;
		Map resultMap = new HashMap();
		Set idSet = null;
		BOSUuid scheduleId = null;
		Object [] bizTypes = null;
		RESchTaskTypeEnum taskTypeEnum = null;
		if(param.get("idSet") != null){
			idSet = (Set) param.get("idSet");
		}
		if(param.get("bizTypes") != null){
			bizTypes =  (Object[]) param.get("bizTypes");
		}
		if(param.get("taskType") != null){
			taskTypeEnum = (RESchTaskTypeEnum) param.get("taskType");
		}
		if(param.get("scheduleId") != null){
			scheduleId =  (BOSUuid) param.get("scheduleId");
		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if(taskTypeEnum != null){
			builder.appendSql("update t_sch_fdcscheduletask set ftasktype = ? where " );
			builder.addParam(taskTypeEnum.getValue());
			builder.appendParam("fid", idSet.toArray());
			builder.execute();
			isNeedReload = true;
		}
		//清除旧的业务类型
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",idSet,CompareType.INCLUDE));
		ScheduleTaskBizTypeFactory.getLocalInstance(ctx).delete(filter);
		
		//新增修改后的业务类型 
		
		builder.clear();
		if(bizTypes!= null && bizTypes.length>0){
			
			String sql = "insert into T_SCH_ScheduleTaskBizType(fid,fparentid,FBizTypeID) values(?,?,?)";
			ScheduleBizTypeInfo bizTypeInfo = null;
			List paramList = new ArrayList();
			List detailList = null;
			for(int i=0;i<bizTypes.length;i++){
				bizTypeInfo = (ScheduleBizTypeInfo) bizTypes[i];
				for(Iterator it = idSet.iterator();it.hasNext();){
					detailList = new ArrayList(3);
					detailList.add(BOSUuid.create("0B475AFB").toString());
					detailList.add(it.next().toString());
					detailList.add(bizTypeInfo.getId().toString());
					
					paramList.add(detailList);
					
					detailList = null;
				}
				
			}
			
			builder.executeBatch(sql, paramList);
			isNeedReload = true;
		}
		
		if(isNeedReload){
			resultMap.put("isNeedReload", Boolean.TRUE);
			FDCScheduleCollection  cols =getScheduleByID(ctx, scheduleId.toString());
			if(!cols.isEmpty()){
				resultMap.put("scheduleInfo", cols.get(0));
			}
		}
		
		return resultMap;
	}

	protected void _submitMainSchedule(Context ctx, IObjectValue mainSchedule) throws BOSException, EASBizException {
		/* TODO 自动生成方法存根 */
		
	}

	protected void _submitSpecialSchedule(Context ctx, IObjectValue specialSchedule) throws BOSException, EASBizException {
		/* TODO 自动生成方法存根 */
		
	}
	
	public CoreBaseCollection getCollection(Context ctx, String oql) throws BOSException {
		int index = oql.indexOf("'");
		String billId = oql.substring(index + 1, oql.length() - 1);	
		CoreBaseCollection cols = new CoreBaseCollection();
		IObjectPK pk = new ObjectUuidPK(BOSUuid.read(billId));
		try {
			FDCScheduleInfo info = (FDCScheduleInfo) _getScheduleInfo(ctx, pk);
			cols.add(info);
		} catch (EASBizException e) {
			throw new BOSException(e.getCause());
		}
		return cols;
	}

	/**
	 *设置当前版本为考核版本
	 */
	protected void _setCheckVersion(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		IObjectPK pk =  new ObjectUuidPK(billId);
		// TODO Auto-generated method stub
		if(!exists(ctx, pk)){
			throw new ScheduleException(ScheduleException.WITHMSG,new Object[]{"请先保存计划后，再进行考核版设置操作！"});
		}
		FDCScheduleInfo info =  getFDCScheduleInfo(ctx, pk);
		//更新此版本的考核日期为计划完成日期
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
        builder.appendSql("update t_sch_fdcschedule set FISCHECKVERSION = 0 where fsrcid = ?");
        builder.addParam(info.getSrcID());
        builder.execute();
		builder.clear();
		String sql = "update t_sch_fdcschedule set FISCHECKVERSION = 1 ,fversionName=replace(fversionName,'执行版','考核版') where fid = ?";
		builder.appendSql(sql);
		builder.addParam(info.getId().toString());
		builder.execute();
		//更新计划完成日期为考核日期
		builder.clear();
		
	    sql = "update t_sch_fdcscheduletask set fcheckdate = fend where fscheduleid = ?";
	    builder.appendSql(sql);
	    builder.addParam(billId.toString());
	    builder.execute();
		
		
	}
	
}