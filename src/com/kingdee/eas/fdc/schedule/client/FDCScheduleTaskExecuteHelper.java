/*
 * @(#)FDCScheduleTaskExecuteHelper.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleExecuteEnum;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.DateTimeUtils;

/***
 * 
 * 描述:主要对任务执行中的一些校验功能进行抽取
 * @author yuanjun_lan  date:2011-10-21
 * @version EAS6.1
 */
public class FDCScheduleTaskExecuteHelper {
	
	private static Logger logger = Logger.getLogger(FDCScheduleTaskExecuteHelper.class);
	public final static String hasCompletedCantModify = "完成进度已汇报为100%，不能再修改汇报。"; 
	
	public static void beforeOperator(FDCScheduleTaskInfo task,String oprtName){
	   	checkLeafTask(task,oprtName);
	   	checkTaskSuccessState(task,oprtName);
	}
	
	public static void checkLeafTask(FDCScheduleTaskInfo task,String oprtName){
		if(!task.isIsLeaf()) {
			FDCMsgBox.showError("不能对非明细任务进行"+oprtName);
			SysUtil.abort();
		} 
	}
	
	private static void checkTaskSuccessState(FDCScheduleTaskInfo task, String oprtName) {
		if(task.getComplete()==null || !(task.getComplete().compareTo(FDCHelper.ONE_HUNDRED)==0)){
			FDCMsgBox.showError("不能对未完工的任务进行"+oprtName);
			SysUtil.abort();
		}
		
		//检查任务是否已经被评价为通过，如果被评价为通过后，不能对此任务再次进行评价
//		FDCSQLBuilder builder = new FDCSQLBuilder();
//		StringBuffer sql = new StringBuffer();
//		sql.append("select count(bill.fid) from t_sch_taskevaluationbill bill left outer join t_sch_taskevaluation ");
//		sql.append(" eva on bill.FEVALUATIONRESULT = eva.fid where bill.fevaluationtype = '1SCHEDULE' and eva.fevaluationpass = 1 and fsrcrelatetask = ? ");
//		builder.appendSql(sql.toString());
//		builder.addParam(task.getSrcID().toString());
//		try {
//			IRowSet rs = builder.executeQuery();
//			int resultCount = 0;
//			while(rs.next()){
//				resultCount = rs.getInt(1);
//			}
//			if(resultCount>0){
//				FDCMsgBox.showError("不能对已经评价通过的任务进行"+oprtName);
//				SysUtil.abort();
//			}
//		} catch (BOSException e) {
//	        logger.error(e.getMessage());
//		} catch (SQLException e) {
//			 logger.error(e.getMessage());
//		}
		
	}
	/**
	 * 
	 * 描述：如果当前用没有绑定职员就返回当前用户的用户名，如果绑定就返回当前用户的职员名称
	 * @return
	 * 创建人：yuanjun_lan
	 * 创建时间：2011-11-11
	 */
	
	public static String getDisplayPersonInfo(UserInfo user){
		if(user.getPerson() != null && user.getPerson().getName() != null){
			return user.getPerson().getName();
		}else{
			return user.getNumber();
		}
		
	}
	
	public static EntityViewInfo getTaskFilterEntityView(FDCScheduleExecuteEnum enumType,String fscheduleID) throws BOSException{
		BOSUuid currUser = (SysContext.getSysContext().getCurrentUserInfo().getPerson() == null ? null : SysContext.getSysContext()
				.getCurrentUserInfo().getPerson().getId());
		Timestamp nowTime = FDCDateHelper.getServerTimeStamp();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("schedule.id", fscheduleID);
		Date[] nextWeekDate = getNextDayOfWeek(nowTime);
		if (enumType == FDCScheduleExecuteEnum.TASK_ALL) {
			// 所有任务
		} else if (enumType == FDCScheduleExecuteEnum.TASK_OVERDUE) {
			/*
			 * 逾期任务：计划完成日期小于当前日期
			 */
			filter.getFilterItems().add(new FilterItemInfo("end", FDCDateHelper.addDays(nowTime, 1), CompareType.LESS));
			filter.getFilterItems().add(new FilterItemInfo("complete", FDCHelper.ONE_HUNDRED, CompareType.LESS));
		} else if (enumType == FDCScheduleExecuteEnum.TASK_MYTREADO) {
			/*
			 * 我的待办任务：责任人为当前用户,且当前日期在计划开始日期和完成日期之间
			 */
			if (currUser == null) {
				FDCMsgBox.showError("当前用户没有关联职员，无法根据责任人进行过滤！");
				SysUtil.abort();
			}
			filter.getFilterItems().add(new FilterItemInfo("complete", FDCHelper.ONE_HUNDRED,CompareType.LESS));
			filter.getFilterItems().add(new FilterItemInfo("adminPerson.id", currUser, CompareType.EQUALS));
//			filter.getFilterItems().add(new FilterItemInfo("start", nowTime, CompareType.LESS));
//			filter.getFilterItems().add(new FilterItemInfo("end", nowTime, CompareType.GREATER));
//			filter.getFilterItems().add(new FilterItemInfo("end", nowTime, CompareType.LESS));
//			
//			FilterInfo orFilter = new FilterInfo();
//			orFilter.getFilterItems().add(new FilterItemInfo("complete",null,CompareType.EQUALS));
//			orFilter.getFilterItems().add(new FilterItemInfo("adminPerson.id", currUser, CompareType.EQUALS));
//			filter.mergeFilter(orFilter, "or");
			
		} else if (enumType == FDCScheduleExecuteEnum.TASK_WEEKEND) {
			/*
			 * 本周应完成任务：计划完成日期在本周周日之前的未完成任务
			 */
			Date theWeekEnd  = DateTimeUtils.truncateDate(DateTimeUtils.addDay(getLastDayOfWeek(nowTime), 1L));
			filter.getFilterItems().add(new FilterItemInfo("end",theWeekEnd, CompareType.LESS));
			filter.getFilterItems().add(new FilterItemInfo("complete", FDCHelper.ONE_HUNDRED, CompareType.LESS));
		} else if (enumType == FDCScheduleExecuteEnum.TASK_WEEKBEGIN) {
			// 下周应开始：显示计划开始日期在下周之间的未完成任务
			filter.getFilterItems().add(new FilterItemInfo("start", nextWeekDate[0], CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("start", nextWeekDate[1], CompareType.LESS_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("complete", FDCHelper.ONE_HUNDRED, CompareType.LESS));
		} else if (enumType == FDCScheduleExecuteEnum.TASK_MYEVALUATE) {
			// 我的待评价任务：进度评价人和质量评价人为当前用户
			if (currUser == null) {
				FDCMsgBox.showError("当前用户没有关联职员，无法根据责任人进行过滤！");
				SysUtil.abort();
			}
			filter.getFilterItems().add(new FilterItemInfo("planEvaluatePerson", currUser, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("qualityEvaluatePerson", currUser, CompareType.EQUALS));
			
			/* modified by zhaoqin for 联发进度任务 on 2014/10/14 */
			filter.getFilterItems().add(new FilterItemInfo("complete", FDCHelper.ONE_HUNDRED));
			
			/* 已经汇报的数据不应显示: modified by zhaoqin for R141027-0038 on 2014/10/14
			 * 1. 如果"进度评价人"与"质量评价人"都是当前用户,则已进行了"进度评价"与"质量评价"的任务不显示
			 * 2. 如果"进度评价人"是当前用户,则已进行了"进度评价"的任务不显示
			 * 3. 如果"质量评价人"是当前用户,则已进行了"质量评价"的任务不显示
			 */
			String curUserId = currUser.toString();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT t.fid FROM T_SCH_FDCSchedule s \n");
			sb.append("join T_SCH_FDCScheduleTask t ON t.FScheduleID = s.fid \n");
			sb.append("join (select e1.FRelateTaskID from T_SCH_TaskEvaluationBill e1 \n");
			sb.append("	join T_SCH_TaskEvaluationBill e2 on e2.FRelateTaskID = e1.FRelateTaskID \n");
			sb.append("	where e1.FEvaluationType = '1SCHEDULE' and e2.FEvaluationType = '2QUALITY' \n");
			sb.append(") e on e.FRelateTaskID = t.fid \n");
			sb.append("WHERE t.FPlanEvaluatePersonID = '").append(curUserId).append("' \n");
			sb.append("and t.FQualityEvaluatePersonID = '").append(curUserId).append("' \n");
			sb.append("and s.fid = '").append(fscheduleID).append("' \n");
			sb.append("union \n");
			sb.append("SELECT t.fid FROM T_SCH_FDCSchedule s \n");
			sb.append("join T_SCH_FDCScheduleTask t ON t.FScheduleID = s.fid \n");
			sb.append("join T_SCH_TaskEvaluationBill e on e.FRelateTaskID = t.fid \n");
			sb.append("WHERE t.FPlanEvaluatePersonID = '").append(curUserId).append("' \n");
			sb.append("and t.FQualityEvaluatePersonID <> '").append(curUserId).append("' \n");
			sb.append("and e.FEvaluationType = '1SCHEDULE' \n");
			sb.append("and s.fid = '").append(fscheduleID).append("' \n");
			sb.append("union \n");
			sb.append("SELECT t.fid FROM T_SCH_FDCSchedule s \n");
			sb.append("join T_SCH_FDCScheduleTask t ON t.FScheduleID = s.fid \n");
			sb.append("join T_SCH_TaskEvaluationBill e on e.FRelateTaskID = t.fid \n");
			sb.append("WHERE t.FPlanEvaluatePersonID <> '").append(curUserId).append("' \n");
			sb.append("and t.FQualityEvaluatePersonID = '").append(curUserId).append("' \n");
			sb.append("and e.FEvaluationType = '2QUALITY' \n");
			sb.append("and s.fid = '").append(fscheduleID).append("' \n");
			filter.getFilterItems().add(new FilterItemInfo("id", sb.toString(), CompareType.NOTINNER));

			filter.setMaskString("#0 and (#1 or #2) and #3 and #4");

		} else if (enumType == FDCScheduleExecuteEnum.TASK_MILEPOST) {
			// 里程碑任务
			filter.getFilterItems().add(new FilterItemInfo("taskType", RESchTaskTypeEnum.MILESTONE, CompareType.EQUALS));

		} else if (enumType == FDCScheduleExecuteEnum.TASK_HINGE) {
			// 关键任务
			filter.getFilterItems().add(new FilterItemInfo("taskType", RESchTaskTypeEnum.KEY, CompareType.EQUALS));
		}

		// EntityViewInfo taskView =
		// ScheduleHelper.getScheduleView(fscheduleID);
		EntityViewInfo taskView = new EntityViewInfo();
		taskView.getSelector().add(new SelectorItemInfo("*"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.type"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.difference"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.id"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.hardness"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.task.id"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.task.name"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.task.number"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.dependTask.id"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.dependTask.name"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.dependTask.number"));
		taskView.getSelector().add(new SelectorItemInfo("adminPerson.name"));
		taskView.getSelector().add(new SelectorItemInfo("adminPerson.number"));
		taskView.getSelector().add(new SelectorItemInfo("adminPerson.id"));
		taskView.getSelector().add(new SelectorItemInfo("adminDept.id"));
		taskView.getSelector().add(new SelectorItemInfo("adminDept.name"));
		taskView.getSelector().add(new SelectorItemInfo("adminDept.number"));
		taskView.getSelector().add(new SelectorItemInfo("bizType.bizType.name"));
		taskView.getSelector().add(new SelectorItemInfo("bizType.bizType.id"));
		taskView.getSelector().add(new SelectorItemInfo("bizType.bizType.number"));
		taskView.getSelector().add(new SelectorItemInfo("schedule.project.id"));
		taskView.getSelector().add(new SelectorItemInfo("schedule.project.number"));
		taskView.getSelector().add(new SelectorItemInfo("schedule.project.name"));
		taskView.getSelector().add(new SelectorItemInfo("schedule.project.costCenter.id"));
		taskView.getSelector().add(new SelectorItemInfo("qualityEvaluatePerson.id"));
		taskView.getSelector().add(new SelectorItemInfo("qualityEvaluatePerson.name"));
		taskView.getSelector().add(new SelectorItemInfo("qualityEvaluatePerson.number"));
		taskView.getSelector().add(new SelectorItemInfo("qualityEvaluatePersonOther.id"));
		taskView.getSelector().add(new SelectorItemInfo("qualityEvaluatePersonOther.name"));
		taskView.getSelector().add(new SelectorItemInfo("qualityEvaluatePersonOther.number"));
		/** 追加进度评价人 **/
		taskView.getSelector().add(new SelectorItemInfo("planEvaluatePerson.id"));
		taskView.getSelector().add(new SelectorItemInfo("planEvaluatePerson.name"));
		taskView.getSelector().add(new SelectorItemInfo("planEvaluatePerson.number"));
		
		/* modified by zhaoqin for R140925-0073 on 2015/01/19 */
		taskView.getSelector().addObjectCollection(ScheduleHelper.getTaskSelector());
		
		SorterItemCollection sorts = new SorterItemCollection();
		SorterItemInfo sortItem  = new SorterItemInfo("longnumber");
		sorts.add(sortItem);
		taskView.setSorter(sorts);

		taskView.setFilter(filter);
		
		return taskView;
	}
	
	
	public static Date getFirstDayOfWeek(Date currTime) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(currTime);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c.getTime();
	}

	/**
	 * @description 	根据当前日期时间计算当前周最后一天日期时间
	 * @author 			周航健
	 * @createDate 		2011-08-29
	 * @param 			currTime
	 * @return 			Date
	 * 
	 * @version 		EAS7.0
	 */
	public static Date getLastDayOfWeek(Date currTime) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(currTime);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
		return c.getTime();
	}

	/**
	 * @description 	根据当前周的最后一天,计算出下一周的第一天和最后一天日期时间
	 * @author 			周航健
	 * @createDate 		2011-08-29
	 * @param 			currTime
	 *            		<系统当前日期>
	 * @return 			Date[]
	 * 
	 * @version 		EAS7.0
	 */
	public static Date[] getNextDayOfWeek(Date currTime) {
		Date[] nextWeek = new Date[2];
		Date lastDayofWeek = getLastDayOfWeek(currTime);
		Calendar c = new GregorianCalendar();
		c.setTime(lastDayofWeek);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date nextWeekBgDay = DateUtils.addDay(c.getTime(), 1);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		Date nextWeekEdDay = DateUtils.addDay(c.getTime(), 7);
		
		nextWeek[0] = nextWeekBgDay;
		nextWeek[1] = nextWeekEdDay;

		return nextWeek;

	}

	/**
	 * 进度汇报前的检查是否需要做额外的提示或处理，需求描述如下：<p>
	 * 
	 * <b>进度汇报为100%后的控制</b><br>
	 * 
		<p>如果参数“通过任务评价控制任务完成状态机其可控制策略”为“不控制”<br>
		当任务汇报为100%后，控制不能再进行汇报，给出相应的提示信息
		入口1.点击工具栏上的“进度汇报”按钮时，弹出提示信息，“完成进度已汇报为100%，不能修改”
		入口2.双击任务打开的属性界面中，“实际开始时间”，“实际/预计完成时间”，“完成进度”字段灰显，
		入口3.当点击表体“进度汇报”页签中的按钮“进度汇报”“删除”时，弹出提示信息，“完成进度已汇报为100%，不能修改”</p>
	
		<p>如果参数“通过任务评价控制任务完成状态机其可控制策略”为“控制”<br>
		当任务汇报为100%后，并且做了任务评价后（也就是Gantt图上任务状态为按时完成或延时完成），控制不能再进行汇报，给出相应的提示信息
		入口1.点击工具栏上的“进度汇报”按钮时，弹出提示信息，“完成进度已汇报为100%，不能修改”
		入口2.双击任务打开的属性界面中，“实际开始时间”，“实际完成时间”，“完成进度”字段灰显，
		入口3.当点击属性面签的进度汇报的表体“进度汇报”页签中的按钮“进度汇报”“删除”时，弹出提示信息，“完成进度已汇报为100%，不能修改”</p>
	 * @author owen_wen 2013-7-25
	 */
	public static boolean checkShouldBeControledBeforeProgressReport(FDCScheduleTaskInfo task) throws EASBizException,
			BOSException {
		if (FDCNumberHelper.isLessThan(task.getComplete(), FDCConstants.ONE_HUNDRED)) {
			return false; // 未完成，可以继续汇报等操作
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcRelateTask", task.getSrcID()));
		filter.getFilterItems().add(new FilterItemInfo("isComplete", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("completePrecent", 100));
		boolean isComplete = ScheduleTaskProgressReportFactory.getRemoteInstance().exists(filter);
		if (!isComplete) {
			return false; // 未完成，可以继续汇报等操作
		} // 若已汇报100%完成，还要进行下面的判断
		
		String FDCSH011_param_value = ParamManager.getParamValue(null, new ObjectUuidPK(SysContext.getSysContext().getCurrentCostUnit().getId()),
				"FDCSH011");
		
		// 0代表不控制 	1 代表控制主项任务	2代表控制专项任务	3代表控制主项和专项任务
		
		if (isComplete && "0".equals(FDCSH011_param_value)) { // 参数不控制 且 100%完成
			return true;
		}
		
		boolean isMainSchedule = task.getSchedule().getProjectSpecial() == null;
		if (isMainSchedule) { // 主项计划汇报
			if ("1".equals(FDCSH011_param_value) || "3".equals(FDCSH011_param_value)) {
				// 看有没有对任务做评价，留给调用者处理，参考MainScheduleExecuteUI中的处理
				return true;
			}
		} else {//专项计划汇报 
			if ("2".equals(FDCSH011_param_value) || "3".equals(FDCSH011_param_value)) {
				return true;
			}
		}
		
		return false;
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * 检查是否要根据完成情况来给出警告信息，并中断退出
	 * 
	 * @param task
	 *            进度任务
	 * @author owen_wen 2013-7-25
	 */
	public static void checkAndShowMsgInfoWhenCompleted(FDCScheduleTaskInfo task) throws EASBizException, BOSException {
		/* modified by zhaoqin for R141022-0210 on 2014/12/10 */
		//checkAndShowMsgInfoWhenCompleted(task, FDCScheduleTaskExecuteHelper.hasCompletedCantModify, false);
		checkAndShowMsgInfoWhenCompleted(task, FDCScheduleTaskExecuteHelper.hasCompletedCantModify, true);
	}

	/**
	 * 描述： 检查是否要根据完成情况来给出警告信息，并中断退出
	 * 
	 * @param task
	 *            进度任务
	 * @param msg
	 *            提示信息
	 * @param isCheckTaskEvaluation
	 *            是否检查任务评价
	 * @throws EASBizException
	 * @throws BOSException
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-30
	 */
	public static void checkAndShowMsgInfoWhenCompleted(FDCScheduleTaskInfo task, String msg,
			boolean isCheckTaskEvaluation) throws EASBizException, BOSException {
		if (isCheckTaskEvaluation) {
			boolean hasPramControl = true;
			String paramValue = ParamManager.getParamValue(null, new ObjectUuidPK(SysContext.getSysContext()
					.getCurrentCostUnit().getId()), "FDCSH011");
			// 0代表不控制 1 代表控制主项任务 2代表控制专项任务 3代表控制主项和专项任务
			if ("0".equals(paramValue)) { // 参数不控制
				hasPramControl = false;
			}
			if (!hasPramControl) {
				return;
			}
		}

		boolean flag = isProgressReportCompleted(task);
		if (flag) {
			FDCMsgBox.showWarning(msg);
			SysUtil.abort();
		}
	}

	/**
	 * 描述：任务汇报是否已经完成
	 * 
	 * @param task
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-30
	 */
	public static boolean isProgressReportCompleted(FDCScheduleTaskInfo task) throws EASBizException, BOSException {
		boolean flag = false;

		boolean shouldBeControled = checkShouldBeControledBeforeProgressReport(task);
		if (shouldBeControled) {// 需要控制，那就给出提示
			String state = task.getString("state");
			// 任务状态说明请看GanttGraphicArea.getToolTipText(MouseEvent event)中的说明
			if ("0".equals(state) || "1".equals(state)) { // 按时完成、延时完成
				flag = true;
			}
		}

		return flag;
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////
}
