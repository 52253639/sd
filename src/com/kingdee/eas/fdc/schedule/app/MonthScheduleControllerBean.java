package com.kingdee.eas.fdc.schedule.app;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
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
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.MonthScheduleInfo;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.fdc.schedule.param.ScheduleParamHelper;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;

public class MonthScheduleControllerBean extends
		AbstractMonthScheduleControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.schedule.app.MonthScheduleControllerBean");

	/**
	 * 提交时修改状态
	 */
	// protected IObjectPK _submit(Context ctx, IObjectValue model)
	// throws BOSException, EASBizException
	// {
	// MonthScheduleInfo info = (MonthScheduleInfo) model;
	// info.setState(ScheduleStateEnum.SUBMITTED);
	//    	
	// return super._submit(ctx, info);
	// }
	/**
	 * 审核时修改状态
	 */
	protected void _passAudit(Context ctx, IObjectPK pk, IObjectValue model)
			throws EASBizException, BOSException {

		MonthScheduleInfo info = (MonthScheduleInfo) model;
		info.setState(ScheduleStateEnum.AUDITTED);
		super._passAudit(ctx, pk, info);
	}

	/**
	 * 反审核时修改状态
	 * 
	 * @param ctx
	 * @param pk
	 * @param model
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected void _unPassAudit(Context ctx, IObjectPK pk, IObjectValue model)
			throws EASBizException, BOSException {

		MonthScheduleInfo info = (MonthScheduleInfo) model;
		info.setState(ScheduleStateEnum.UNAUDITED);
		super._unpassAudit(ctx, pk, model);
	}

	/**
	 * 获取任务
	 */
	protected ResultSet _fetchTask(Context ctx, String startDate,
			String endDate, IObjectValue adminDept, IObjectValue adminPerson)
			throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("  		  \n");
		sql.append("  	SELECT T.FNAME_L2 FNAME,	  \n");
		sql.append("  	       T.FNUMBER,	  \n");
		sql.append("  	       W.FNUMBER WBSNUMBER,	  \n");
		sql.append("  	       T.FSTART STARTDATE,	  \n");
		sql.append("  	       T.FEND ENDDATE,	  \n");
		sql.append("  	       T.FEFFECTTIMES EFFECTTIMES,	  \n");
		sql.append("  	       T.FNATURETIMES NATURETIMES,	  \n");
		sql.append("  	       T1.FNAME_L2 PARENTTASK,	  \n");
		sql.append("  	       P.FNAME_L2 PROJECT,T.FID TASKID	  \n");
		sql.append("  	  FROM T_SCH_FDCSCHEDULETASK T	  \n");
		sql
				.append("  	  LEFT JOIN T_SCH_FDCSCHEDULETASK T1 ON T.FPARENTID = T1.FID	  \n");
		sql.append("  	  LEFT JOIN T_SCH_FDCWBS W ON T.FWBSID = W.FID	  \n");
		sql
				.append("  	  LEFT JOIN T_FDC_CURPROJECT P ON W.FCURPROJECTID = P.FID	  \n");
		// sql.append(
		// "  	WHERE CONVERT(T.FSTART, {ts'YYYY-MM-DD HH24:MM:SS'}) >= ? AND  CONVERT(T.FEND, {ts'YYYY-mm-DD HH:MM:SS'}) <= ? AND  t.fadmindeptid = ?	  \n"
		// );
		sql
				.append("  	WHERE T.FSTART >= ? AND  T.FEND <= ? AND  t.fadmindeptid = ? AND T1.FISREFER = 1	  \n");

		String start = "CONVERT(DATETIME,{ts'" + startDate.toString()
				+ " 00:00:00'})";// "{ts'" + startDate.toString() +
									// "' 00:00:00}";
		String end = "CONVERT(DATETIME,{ts'" + endDate.toString()
				+ " 00:00:00'})"; // "{ts'" + endDate.toString() +
									// "' 23:59:59}";
		FullOrgUnitInfo orgInfo = (FullOrgUnitInfo) adminDept;

		// Timestamp
		// new Timestamp(DateTimeUtils.truncateDate(startDate).getTime()) );

		ResultSet rs = DbUtil.executeQuery(ctx, sql.toString(), new Object[] {
				start, end, orgInfo.getId().toString() });

		return rs;
	}

	protected IObjectCollection _fetchTask0(Context ctx, Date start, Date end,
			String adminDeptID, IObjectValue adminPerson, boolean isThisMonth)
			throws BOSException, EASBizException {

		// FullOrgUnitInfo orgInfo = (FullOrgUnitInfo)adminDept;
		PersonInfo personInfo = (PersonInfo) adminPerson;
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = view.getSelector();

		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("wbs.number"));
		sic.add(new SelectorItemInfo("start"));
		sic.add(new SelectorItemInfo("end"));
		sic.add(new SelectorItemInfo("effectTimes"));
		sic.add(new SelectorItemInfo("natureTimes"));
		sic.add(new SelectorItemInfo("wbs.curProject.name"));
		sic.add(new SelectorItemInfo("parent.name"));
		sic.add(new SelectorItemInfo("adminDept.name"));
		sic.add(new SelectorItemInfo("adminDept.id"));
		sic.add(new SelectorItemInfo("adminPerson.name"));
		sic.add(new SelectorItemInfo("adminPerson.id"));
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("wbs.name"));
		sic.add(new SelectorItemInfo("wbs.longNumber"));
		sic.add(new SelectorItemInfo("isLeaf"));
		// sic.add(new SelectorItemInfo("schedule.startDate"));
		// sic.add(new SelectorItemInfo("schedule.endDate"));
		sic.add(new SelectorItemInfo("schedule.isLatestVer"));
		sic.add(new SelectorItemInfo("schedule"));
		sic.add(new SelectorItemInfo("complete"));

		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		int count = 0;
		Date lastDateOfMonth = FDCDateHelper.getLastDayOfMonth(start);
		// start<=5.31 end>=5.1 by sxhong
		filter.getFilterItems().add(
				new FilterItemInfo("start", lastDateOfMonth,
						CompareType.LESS_EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("end", DateTimeUtils.truncateDate(start),
						CompareType.GREATER_EQUALS));

		filter.getFilterItems().add(
				new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));

		count++;

		filter.getFilterItems().add(
				new FilterItemInfo("adminDept.id", adminDeptID,
						CompareType.EQUALS));
		logger.info("orgInfo.getId().toString() = " + adminDeptID);
		count++;

		if (personInfo != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("adminPerson.id", personInfo.getId()
							.toString(), CompareType.EQUALS));
			count++;
		}

		if (!isThisMonth) {
			filter.getFilterItems()
					.add(
							new FilterItemInfo("complete", 100 + "",
									CompareType.EQUALS));
			count++;
		}
		if (count == 1) {
			filter.setMaskString("( #0 AND #1 AND #2)");
		} else if (count == 2) {
			filter.setMaskString("( #0 AND #1 AND #2 AND #3)");
		} else if (count == 3) {
			filter.setMaskString("( #0 AND #1 AND #2 AND #3 AND #4)");
		} else {
			filter.setMaskString("( #0 AND #1 AND #2 AND #3 AND #4  AND #5)");
		}
		view.setFilter(filter);
		
		boolean canImportMainTask = ScheduleParamHelper.getSchParamByKey(ctx, null, ScheduleParamHelper.FDCSCH_PARAM_MONTHPLANIMPORTMAINTASK);
		if(!canImportMainTask){
			//通过参数控制是否只导入专项任务任务
			FilterInfo tempFilter=new FilterInfo();
			tempFilter=new FilterInfo();
			tempFilter.getFilterItems().add(new FilterItemInfo("schedule.scheduleType.id",TaskTypeInfo.TASKTYPE_MAINTASK,CompareType.NOTEQUALS));
			filter.mergeFilter(tempFilter, "and");
		}
		FDCScheduleTaskCollection col = FDCScheduleTaskFactory
				.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);

		return col;
	}

	/**
	 * 重载基类校验编码重复代码，改为同一工程项目下的编码不能重复
	 */
	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		MonthScheduleInfo piInfo = (MonthScheduleInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("project.id", piInfo.getProject().getId(),
						CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("number", piInfo.getNumber(),
						CompareType.EQUALS));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("id", piInfo.getId(),
								CompareType.NOTEQUALS));
		if (this._exists(ctx, filter)) {
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { piInfo.getNumber() });
		}
	}

	/**
	 * 重载基类校验名称重复代码，改为同一工程项目下的名称不能重复
	 */
	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		MonthScheduleInfo piInfo = (MonthScheduleInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("project.id", piInfo.getProject().getId(),
						CompareType.EQUALS));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("name", piInfo.getName(),
								CompareType.EQUALS));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("id", piInfo.getId(),
								CompareType.NOTEQUALS));
		if (this._exists(ctx, filter)) {
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { piInfo.getName() });
		}
	}

	protected Map _audit(Context ctx, Set ids, UserInfo auditor)
			throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_SCH_MonthSchedule set fstate=?,fauditorid =? ,faudittime =? where ");
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.addParam(auditor.getId().toString());
		builder.addParam(new Timestamp(System.currentTimeMillis()));
		builder.appendParam("fid", ids.toArray());
		builder.execute();

		// 审核后修改任务是否被引用
		// Object[] id = ids.toArray();
		// for (int i = 0 ; i < id.length; i++){
		//			
		// updateTaskIsRefer(ctx,id[i].toString(),1);
		// }
		//		

		// 错误提示等信息以后可以通过Map返回
		return null;
	}

	protected Map _unAudit(Context ctx, Set ids, UserInfo unAuditor)
			throws BOSException, EASBizException {
		// checkBeforeUnAudit
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_SCH_MonthSchedule set fstate=? ,fauditorid =? ,faudittime =? where ");
		builder.addParam(ScheduleStateEnum.SUBMITTED_VALUE);

		builder.addParam(null);
		builder.addParam(null);

		builder.appendParam("fid", ids.toArray());
		builder.execute();

		// Object[] id = ids.toArray();
		// for (int i = 0 ; i < id.length; i++){
		//			
		// updateTaskIsRefer(ctx,id[i].toString(),0);
		// }

		// 错误提示等信息以后可以通过Map返回
		return null;
	}

	/**
	 * 
	 * @param ctx
	 *            Context
	 * @param coreBaseInfo
	 *            CoreBaseInfo
	 * @throws BOSException
	 * @return IObjectPK
	 */
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		MonthScheduleInfo info = (MonthScheduleInfo) model;
		_checkNumberDup(ctx, model);
		_checkNameDup(ctx, model);
		info.setState(ScheduleStateEnum.SAVED);
		if((info.getId() != null &&!this._exists(ctx, new ObjectUuidPK(info.getId())))
				|| info.getId()==null){
			handleIntermitNumber(ctx, info);
		}
		return super._save(ctx, model);
	}

	/**
	 * 
	 * @param ctx
	 *            Context
	 * @param pk
	 *            IObjectPK
	 * @param coreBaseInfo
	 *            CoreBaseInfo
	 * @throws BOSException
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		MonthScheduleInfo info = (MonthScheduleInfo) model;
		_checkNumberDup(ctx, model);
		_checkNameDup(ctx, model);
		info.setState(ScheduleStateEnum.SUBMITTED);
		if((info.getId() != null &&!this._exists(ctx, new ObjectUuidPK(info.getId())))
				|| info.getId()==null){
			handleIntermitNumber(ctx, info);
		}
		return super._submit(ctx, model);
	}
	// 审批
	protected void _audit(Context ctx, BOSUuid billID) throws BOSException,
			EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_MonthSchedule set fstate=? where ");
		builder.addParam(ScheduleStateEnum.AUDITTED.getValue());
		builder.appendParam("fid", billID.toString());		 
		builder.execute();

		// updateTaskIsRefer(ctx,billID.toString(),1);

	}

	// 审批中
	protected void _setAudittingStatus(Context ctx, BOSUuid billID)
			throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	    
		builder.appendSql("update T_SCH_MonthSchedule set fstate=? where fid=?");
	 	builder.addParam(ScheduleStateEnum.AUDITTING.getValue());
		builder.addParam(billID.toString());
		builder.execute();

	}

	// 设置提交状态
	protected void _setSubmitStatus(Context ctx, BOSUuid billID)
			throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_MonthSchedule set fstate=? where fid=? ");
		 builder.addParam(ScheduleStateEnum.SUBMITTED.getValue());
		builder.addParam(billID.toString());
		builder.execute();

	}

	// 反审批
	protected void _unAudit(Context ctx, BOSUuid billID) throws BOSException,
			EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_MonthSchedule set fstate=? where ");
		builder.addParam(ScheduleStateEnum.SUBMITTED.getValue());
		builder.appendParam("fid", billID);
		builder.execute();
		updateTaskIsRefer(ctx,billID.toString(),0);
	}

	protected IObjectCollection _fetchTask0(Context ctx, Date start, Date end,
			String adminDeptID, IObjectValue adminPerson, boolean isThisMonth,
			IObjectValue curProject) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		PersonInfo personInfo = (PersonInfo) adminPerson;
		CurProjectInfo projectInfo = (CurProjectInfo) curProject;
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = view.getSelector();

		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("wbs.number"));
		sic.add(new SelectorItemInfo("start"));
		sic.add(new SelectorItemInfo("end"));
		sic.add(new SelectorItemInfo("effectTimes"));
		sic.add(new SelectorItemInfo("natureTimes"));
		sic.add(new SelectorItemInfo("wbs.curProject.name"));
		sic.add(new SelectorItemInfo("parent.name"));
		sic.add(new SelectorItemInfo("adminDept.name"));
		sic.add(new SelectorItemInfo("adminDept.id"));
		sic.add(new SelectorItemInfo("adminPerson.name"));
		sic.add(new SelectorItemInfo("adminPerson.id"));
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("wbs.name"));
		sic.add(new SelectorItemInfo("wbs.longNumber"));
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("schedule.isLatestVer"));
		sic.add(new SelectorItemInfo("schedule"));
		sic.add(new SelectorItemInfo("complete"));

		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		int count = 0;
		if(!isThisMonth){
			start = FDCDateHelper.getPreMonth(start);
			end = FDCDateHelper.getPreMonthMaxDate(end);
		}
		Date lastDateOfMonth = FDCDateHelper.getLastDayOfMonth(start);
		// start<=5.31 end>=5.1 by sxhong
		filter.getFilterItems().add(new FilterItemInfo("start", lastDateOfMonth,CompareType.LESS_EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("end", DateTimeUtils.truncateDate(start),CompareType.GREATER_EQUALS));
		
		filter.getFilterItems().add(
				new FilterItemInfo("wbs.curProject", projectInfo.getId().toString(), CompareType.EQUALS));

		filter.getFilterItems().add(
				new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE,CompareType.EQUALS));

		count++;

		filter.getFilterItems().add(
				new FilterItemInfo("adminDept.id", adminDeptID,CompareType.EQUALS));
		logger.info("orgInfo.getId().toString() = " + adminDeptID);
		count++;

		if (personInfo != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("adminPerson.id", personInfo.getId()
							.toString(), CompareType.EQUALS));
			count++;
		}

		if (!isThisMonth) {
			filter.getFilterItems()	.add(new FilterItemInfo("complete", 100 + "",CompareType.NOTEQUALS));
			count++;
			filter.getFilterItems().add(new FilterItemInfo("complete", null,CompareType.EQUALS));
			count++;
		}
		if (count == 1) {
			filter.setMaskString("( #0 AND #1 AND #2 AND #3)");
		} else if (count == 2) {
			filter.setMaskString("( #0 AND #1 AND #2 AND #3 AND #4)");
		} else if (count == 3) {
			filter.setMaskString("( #0 AND #1 AND #2 AND #3 AND #4 AND #5)");
		} else {
			filter.setMaskString("( #0 AND #1 AND #2 AND #3 AND #4  AND (#5 OR #6))");
		}
		view.setFilter(filter);
		boolean canImportMainTask = ScheduleParamHelper.getSchParamByKey(ctx, null, ScheduleParamHelper.FDCSCH_PARAM_MONTHPLANIMPORTMAINTASK);
		if(!canImportMainTask){
			//通过参数控制是否只导入专项任务任务
			FilterInfo tempFilter=new FilterInfo();
			tempFilter=new FilterInfo();
			tempFilter.getFilterItems().add(new FilterItemInfo("schedule.scheduleType.id",TaskTypeInfo.TASKTYPE_MAINTASK,CompareType.NOTEQUALS));
			filter.mergeFilter(tempFilter, "and");
		}
		FDCScheduleTaskCollection col = FDCScheduleTaskFactory
				.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		
		return col;
	}

	/**
	 * 审核或者反审核时修改任务是否被引用
	 * 
	 * @param ctx
	 * @param billID
	 * @param isRefer
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void updateTaskIsRefer(Context ctx, String billID, int isRefer)
			throws BOSException, EASBizException {
		String sql = "UPDATE T_SCH_FDCScheduleTask SET Fisrefer = ? "
				+ " WHERE fid IN "
				+ " ( "
				+ " SELECT T_SCH_FDCScheduleTask.fid FROM T_SCH_FDCScheduleTask "
				+ " INNER JOIN T_SCH_FDCMonthTaskEntry  ON T_SCH_FDCScheduleTask.Fid = T_SCH_FDCMonthTaskEntry.Ftaskid "
				+ " INNER JOIN T_SCH_MonthSchedule ON  T_SCH_MonthSchedule.fid = T_SCH_FDCMonthTaskEntry.Fparentid "
				+ " WHERE T_SCH_MonthSchedule.Fid = ? " + " ) ";

		DbUtil.execute(ctx, sql, new Object[] { isRefer + "", billID });
	}
	protected IObjectPK[] _delete(Context ctx, FilterInfo filter)
			throws BOSException, EASBizException {
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo("id"));
		IObjectPK[] pks = getPKList(ctx, filter, sorter);
		for(int i=0;i<pks.length;i++){
			recycleNumber(ctx, pks[i]);
		}
		_delete(ctx, pks);
		return super._delete(ctx, filter);
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		recycleNumber(ctx, pk);
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		for(int i=0;i<arrayPK.length;i++){
			recycleNumber(ctx, arrayPK[i]);
		}
		super._delete(ctx, arrayPK);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param pk
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 */
	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException{
		MonthScheduleInfo info = (MonthScheduleInfo) _getValue(ctx, pk);
		OrgUnitInfo orgInfo = ContextUtil.getCurrentOrgUnit(ctx);
		String curOrgId = orgInfo.getId().toString();
		if(info.getNumber()!=null&&info.getNumber().length()>0){
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	        if (iCodingRuleManager.isExist(info, curOrgId) 
	        		&& iCodingRuleManager.isUseIntermitNumber(info, curOrgId)) {
	            iCodingRuleManager.recycleNumber(info, curOrgId, info.getNumber());
	        }
		}
	}
	
	/**
	 * 不断号处理
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleIntermitNumber(Context ctx, MonthScheduleInfo info) throws BOSException, CodingRuleException, EASBizException{
		//如果用户在客户端手工选择了断号,则此处不必在抢号
		if(info.getNumber() != null && info.getNumber().length() > 0) return;
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		//对成本中心进行处理
		String orgId= info.getAdminDept().getId().toString();
       if(StringUtils.isEmpty(orgId))	   return;
       boolean isExist = true;
       if (!iCodingRuleManager.isExist(info, orgId)){
    	   orgId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
      		if (!iCodingRuleManager.isExist(info, orgId)){
      			isExist = false; 
           }
      }
      if(isExist){
    	   // 选择了断号支持或者没有选择新增显示,获取并设置编号
           if (iCodingRuleManager.isUseIntermitNumber(info, orgId) 
        		   || !iCodingRuleManager.isAddView(info, orgId)){     // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
               		// 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
               String number = iCodingRuleManager.getNumber(info,orgId);
               info.setNumber(number);
           }
      }
	}
}