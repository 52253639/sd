/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.ParamItemCollection;
import com.kingdee.eas.base.param.ParamItemFactory;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述： 报表工具类
 * 
 * @author 杜红明
 * @version EAS7.0
 * @createDate 2011-11-4
 * @see
 */

public class ViewReportHelper {

	public static Map<String, String> getParams(String currFiCost) throws BOSException {
		Map<String, String> ret = new HashMap<String, String>();
		String sql = "select fcostcenterid from t_fdc_curproject where ffullorgunit = '" + currFiCost + "'";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("keyID.number", "FDCSH011"));
		filter.getFilterItems().add(new FilterItemInfo("orgUnitID.id", sql, CompareType.INNER));
		view.setFilter(filter);
		ParamItemCollection cols = ParamItemFactory.getRemoteInstance().getParamItemCollection(view);
		if (cols.size() > 0) {
			ParamItemInfo pi = null;
			for (int i = 0; i < cols.size(); i++) {
				pi = cols.get(i);
				ret.put(pi.getOrgUnitID().getId().toString(), pi.getValue());
			}
		}
		return ret;
	}

	public static Map<String, String> getAllParams() throws BOSException {
		Map<String, String> ret = new HashMap<String, String>();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("keyID.number", "FDCSH011"));
		view.setFilter(filter);
		ParamItemCollection cols = ParamItemFactory.getRemoteInstance().getParamItemCollection(view);
		if (cols.size() > 0) {
			ParamItemInfo pi = null;
			for (int i = 0; i < cols.size(); i++) {
				pi = cols.get(i);
				ret.put(pi.getOrgUnitID().getId().toString(), pi.getValue());
			}
		}
		return ret;
	}

	/**
	 * 
	 * @description 是否进行参数控制
	 * @author 杜红明
	 * @createDate 2011-11-4
	 * @return boolean
	 * @version EAS7.0
	 * @see
	 */
	public static boolean isStartingParamControl(String costCenterId, Object obj) {
		ObjectUuidPK id = new ObjectUuidPK(BOSUuid.read(costCenterId));
		// 控制主项任务
		// 专项任务的计划完成日期大于相关主项任务的计划完成日期
		String fdcParamValue = "";
		try {
			fdcParamValue = ParamControlFactory.getRemoteInstance().getParamValue(id, "FDCSH011");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if (obj instanceof CurProjectInfo) {
			if (fdcParamValue.equals("1") || fdcParamValue.equals("3")) {
				return true;
			}
		} else if (obj instanceof ProjectSpecialInfo) {
			if (fdcParamValue.equals("2") || fdcParamValue.equals("3")) {
				return true;
			}
		}
		return !"0".equals(fdcParamValue);
	}

	public static int getState(boolean isParamControl, FDCScheduleTaskInfo fDCScheduleTaskInfo, Date realEndDate, Date planEndDate, int complete) {
		String scheduleAppraise = isScheduleAppraise(fDCScheduleTaskInfo);
		if (isParamControl) {// 启动参数控制
			if (complete == 100 && scheduleAppraise == null) {
				return 3;// 如果进度为100 没有进行进度评价 待确认
			}
			// 如果进度为100 进行进度评价 评价为合格
			// 根据计划时间和实际时间来判断是按时完成或延时完成(由于为合格进度才为为100,所以这里不再对是否合格进行判断)
			if (complete == 100 && scheduleAppraise != null) {
				if (DateUtils.diffdates(planEndDate, realEndDate) <= 0) {
					return 0;
				}
				return 1;
			}

			if (complete < 100) {
				if (DateUtils.diffdates(new Date(), planEndDate) >= 0) {
					return 4; // 如果进度小于100 计划完成日期》=当前时期 状态为空（执行中）
				}
				if (DateUtils.diffdates(planEndDate, new Date()) > 0) {
					return 2;// 如果进度小于100 计划完成日期《 当前日期 延时未完成
				}
			}
		} else {// 不启动参数控制
			// 如果进度为100 根据计划时间和实际时间来判断是按时完成或延时完成
			if (realEndDate != null && complete == 100) {
				if (DateUtils.diffdates(planEndDate, realEndDate) <= 0) {
					return 0;
				}
				return 1;
			}
			if (complete < 100) {
				if (DateUtils.diffdates(new Date(), planEndDate) >= 0) {
					return 4; // 如果进度小于100 计划完成日期>=当前时期 状态为空（执行中）
				}
				return 2;
			}
		}
		return -1;
	}

	/**
	 * 
	 * 描述：对原有的方法进行重写并修复错误
	 * 
	 * @param isEvalationed
	 *            是否已经评价
	 * @param isStartingParamControl
	 *            是否启用参数控制
	 * @param fDCScheduleTaskInfo
	 *            任务信息
	 * @return 创建人：yuanjun_lan 创建时间：2012-12-6
	 */
	public static int getState(boolean isEvalationed, boolean isStartingParamControl, FDCScheduleTaskInfo fDCScheduleTaskInfo) {
		if (fDCScheduleTaskInfo == null) {
			return -1;
		}
		int complete = fDCScheduleTaskInfo.getComplete().intValue();
		Date planEndDate = fDCScheduleTaskInfo.getEnd();
		Date realEndDate = fDCScheduleTaskInfo.getActualEndDate();

		if (complete == 100) {
			int diffDay = DateUtils.diffdates(planEndDate, realEndDate);
			int result = FDCScheduleConstant.UNDEF;
			if (diffDay <= 0) {
				result = isStartingParamControl ? (isEvalationed ? FDCScheduleConstant.ONTIME : FDCScheduleConstant.CONFIRMED) : FDCScheduleConstant.ONTIME;
				return result;
			} else {
				result = isStartingParamControl ? (isEvalationed ? FDCScheduleConstant.DELAY : FDCScheduleConstant.CONFIRMED) : FDCScheduleConstant.DELAY;
				return result;
			}

		} else {
			if (DateUtils.diffdates(new Date(), planEndDate) >= 0) {
				return FDCScheduleConstant.UNDEF; // 如果进度小于100 计划完成日期》=当前时期
				// 状态为空（执行中）
			}
			if (DateUtils.diffdates(planEndDate, new Date()) > 0) {
				return FDCScheduleConstant.DELAYANDNOTCOMPLETE;// 如果进度小于100
				// 计划完成日期《 当前日期
				// 延时未完成
			}
		}

		return -1;
	}

	/**
	 * 
	 * @description 是否进行进度评价
	 * @author 杜红明
	 * @createDate 2011-11-3
	 * @param fDCScheduleTaskInfo
	 * @return String
	 * @version EAS7.0
	 * @throws BOSException
	 * @throws SQLException
	 * @see
	 */
	private static String isScheduleAppraise(FDCScheduleTaskInfo fDCScheduleTaskInfo) {
		if (fDCScheduleTaskInfo != null && fDCScheduleTaskInfo.getId() != null) {
			FDCSQLBuilder t_sql = new FDCSQLBuilder();
			t_sql.appendSql(" select eval.fevaluationtype from T_SCH_FDCScheduleTask as sche ");
			t_sql.appendSql(" LEFT OUTER JOIN T_SCH_TaskEvaluationbill as eval ");
			t_sql.appendSql(" on sche.fsrcid =eval.fsrcrelatetask where sche.fsrcid = '" + fDCScheduleTaskInfo.getId().toString() + "' ");
			try {
				IRowSet rs = t_sql.executeQuery();
				while (rs.next()) {
					return rs.getString("fevaluationtype") == "" ? null : rs.getString("fevaluationtype");
				}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void initStateCell(boolean isEvaluation, boolean isParamControl, IRow row, Object obj) throws BOSException {

		Object entryInfo = null;
		// 实际完成日期
		Date realEndDate = null;
		// 计划完成日期
		Date planEndDate = null;
		FDCScheduleTaskInfo relateTask = null;
		int completePrecent = 0;
		if (obj instanceof ProjectWeekReportEntryInfo) {
			entryInfo = (ProjectWeekReportEntryInfo) obj;
			realEndDate = ((ProjectWeekReportEntryInfo) entryInfo).getRealEndDate();
			relateTask = ((ProjectWeekReportEntryInfo) entryInfo).getRelateTask();
			planEndDate = relateTask.getEnd();
			completePrecent = ((ProjectWeekReportEntryInfo) entryInfo).getCompletePrecent();
		}
		if (obj instanceof ProjectMonthReportEntryInfo) {
			entryInfo = (ProjectMonthReportEntryInfo) obj;
			entryInfo = (ProjectMonthReportEntryInfo) obj;
			realEndDate = ((ProjectMonthReportEntryInfo) entryInfo).getRealEndDate();
			relateTask = ((ProjectMonthReportEntryInfo) entryInfo).getRelateTask();
			planEndDate = relateTask.getEnd();
			completePrecent = ((ProjectMonthReportEntryInfo) entryInfo).getCompletePrecent();
		}

		int state = getState(isEvaluation, isParamControl, relateTask);
		setStateValue(row, state);
	}

	public static Set sortDateByYearMonth(LinkedHashSet set) {
		if (set == null || set.size() == 0) {
			return new HashSet();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Object[] array = set.toArray();
		for (int i = 0; i < set.size(); i++) {
			for (int j = 0; j < set.size(); j++) {
				if (DateUtils.diffdates((Date) array[j], (Date) array[i]) > 0) {
					Date temp = (Date) array[i];
					array[i] = (Date) array[j];
					array[j] = temp;
				}
			}
		}
		set.clear();
		for (int i = 0; i < array.length; i++) {
			set.add(sdf.format(array[i]));
		}
		return set;
	}

	public static FDCScheduleTaskCollection getNextMonthTasks(FDCScheduleTaskCollection col, String date) throws ParseException {
		Date fromtDate = DateUtils.parse(date, "yyyy-MM");
		Date startOfMonth = DateUtils.startOfMonth(fromtDate);
		Date endOfMonth = DateUtils.endOfMonth(fromtDate);
		startOfMonth = getNextMonthOfYear(startOfMonth);
		endOfMonth = DateUtils.endOfMonth(startOfMonth);
		FDCScheduleTaskCollection currMonthTasks = new FDCScheduleTaskCollection();
		for (int i = 0; i < col.size(); i++) {
			FDCScheduleTaskInfo task = col.get(i);
			addNextMonthTask(startOfMonth, endOfMonth, currMonthTasks, task);
		}
		return currMonthTasks;
	}

	public static FDCScheduleTaskCollection getCurrMonthTasks(FDCScheduleTaskCollection col, String date) throws ParseException {
		Date fromtDate = DateUtils.parse(date, "yyyy-MM");
		Date startOfMonth = DateUtils.startOfMonth(fromtDate);
		Date endOfMonth = DateUtils.endOfMonth(fromtDate);
		Calendar c = Calendar.getInstance();
		c.setTime(fromtDate);
		FDCScheduleTaskCollection currMonthTasks = new FDCScheduleTaskCollection();
		for (int i = 0; i < col.size(); i++) {
			FDCScheduleTaskInfo task = col.get(i);
			addCurrMonthTask(startOfMonth, endOfMonth, currMonthTasks, task);
		}
		return currMonthTasks;
	}
	public static FDCScheduleTaskCollection getNextMonthTasks(FDCScheduleTaskCollection col, String date, CurProjectInfo project) throws ParseException {
		Date fromtDate = DateUtils.parse(date, "yyyy-MM");
		Date startOfMonth = DateUtils.startOfMonth(fromtDate);
		Date endOfMonth = DateUtils.endOfMonth(fromtDate);
		startOfMonth = getNextMonthOfYear(startOfMonth);
		endOfMonth = DateUtils.endOfMonth(startOfMonth);
		FDCScheduleTaskCollection currMonthTasks = new FDCScheduleTaskCollection();
		for (int i = 0; i < col.size(); i++) {
			FDCScheduleTaskInfo task = col.get(i);
			if (project != null) {
				if (!task.getSchedule().getProject().getId().equals(project.getId())) {
					continue;
				}
			}
			addNextMonthTask(startOfMonth, endOfMonth, currMonthTasks, task);
		}
		return currMonthTasks;
	}

	public static FDCScheduleTaskCollection getCurrMonthTasks(FDCScheduleTaskCollection col, String date, CurProjectInfo project) throws ParseException {
		Date fromtDate = DateUtils.parse(date, "yyyy-MM");
		Date startOfMonth = DateUtils.startOfMonth(fromtDate);
		Date endOfMonth = DateUtils.endOfMonth(fromtDate);
		Calendar c = Calendar.getInstance();
		c.setTime(fromtDate);
		FDCScheduleTaskCollection currMonthTasks = new FDCScheduleTaskCollection();
		for (int i = 0; i < col.size(); i++) {
			FDCScheduleTaskInfo task = col.get(i);
			if (project != null) {
				if (!task.getSchedule().getProject().getId().equals(project.getId())) {
					continue;
				}
			}
			addCurrMonthTask(startOfMonth, endOfMonth, currMonthTasks, task);
		}
		return currMonthTasks;
	}

	public static Date getNextMonthOfYear(Date currMonthDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(currMonthDate);
		c.add(Calendar.MONTH, 1);// 减一个月
		c.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		return c.getTime();
	}

	private static void addCurrMonthTask(Date startOfMonth, Date endOfMonth, FDCScheduleTaskCollection currMonthTasks, FDCScheduleTaskInfo task) {
		try {
			// 对结束日期的特殊处理（例如：2011-12-1 00:00:00 与 2011-11-30 23:59:59 相差的天数为0）
			endOfMonth = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(endOfMonth));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 处理了时间,因为从实体获取的时间类型为java.sql.date 而比较的类型为 java.util.date
		// System.err.println(task.getName());
		Date planStartDate = task.getStart();
		if (planStartDate != null) {
			Calendar temp_planStartDate = Calendar.getInstance();
			temp_planStartDate.setTime(planStartDate);
			planStartDate = temp_planStartDate.getTime();// 计划开始
		}
		Date planEndDate = task.getEnd();
		if (planEndDate != null) {
			Calendar temp_planEndDate = Calendar.getInstance();
			temp_planEndDate.setTime(planEndDate);
			planEndDate = temp_planEndDate.getTime();// 计划结束
		}
		Date actualStartDate = task.getActualStartDate();
		if (actualStartDate != null) {
			Calendar temp_actualStartDate = Calendar.getInstance();
			temp_actualStartDate.setTime(actualStartDate);
			actualStartDate = temp_actualStartDate.getTime();// 实际开始
		}
		Date actualEndDate = task.getActualEndDate();
		if (actualEndDate != null) {
			Calendar temp_actualEndDate = Calendar.getInstance();
			temp_actualEndDate.setTime(actualEndDate);
			actualEndDate = temp_actualEndDate.getTime();// 实际结束
		}

		int complete = task.getComplete() == null ? 0 : task.getComplete().intValue();
		// 1、计划完成日期小于本月，并完工百分比小于100
		// if (planEndDate != null && DateTimeUtils.dateDiff(planEndDate,
		// startOfMonth) > 0 && complete < 100) {
		// currMonthTasks.add(task);
		// return;
		// }
		// // 2.1 计划开始日期在本月
		// if (planStartDate != null && DateTimeUtils.dateDiff(startOfMonth,
		// planStartDate) >= 0
		// && DateTimeUtils.dateDiff(endOfMonth, planStartDate) <= 0) {
		// currMonthTasks.add(task);
		// return;
		// }
		// // 2.2 计划结束日期在本月
		// if (planEndDate != null && DateTimeUtils.dateDiff(startOfMonth,
		// planEndDate) >= 0
		// && DateTimeUtils.dateDiff(endOfMonth, planEndDate) <= 0) {
		// currMonthTasks.add(task);
		// return;
		// }
		// // 3计划开始日期在本月，但计划完成日期大于本月
		// if (planStartDate != null && planEndDate != null &&
		// DateTimeUtils.dateDiff(startOfMonth, planStartDate) < 0
		// && DateTimeUtils.dateDiff(endOfMonth, planEndDate) > 0) {
		// currMonthTasks.add(task);
		// return;
		// }
		// // 4 实际完成日期在本月
		// if (actualEndDate != null && DateTimeUtils.dateDiff(startOfMonth,
		// actualEndDate) >= 0
		// && DateTimeUtils.dateDiff(endOfMonth, actualEndDate) <= 0) {
		// currMonthTasks.add(task);
		// return;
		// }
		// // 5实际完成日期在本月
		// if (planStartDate != null && actualStartDate != null &&
		// DateUtils.diffdates(endOfMonth, planStartDate) > 0
		// && DateUtils.diffdates(startOfMonth, actualStartDate) >= 0 &&
		// DateUtils.diffdates(endOfMonth, actualStartDate) <= 0) {
		// currMonthTasks.add(task);
		// return;
		// }
		//		
		// if (planStartDate != null && planEndDate != null &&
		// DateTimeUtils.dateDiff(startOfMonth, planStartDate) < 0
		// && DateTimeUtils.dateDiff(endOfMonth, planEndDate) > 0) {
		// currMonthTasks.add(task);
		// }

		if (planEndDate != null && DateTimeUtils.dateDiff(startOfMonth, planEndDate) < 0 && complete < 100) {
			currMonthTasks.add(task);
		} else if (planStartDate != null && planEndDate != null && DateTimeUtils.dateDiff(startOfMonth, planStartDate) >= 0 && DateTimeUtils.dateDiff(endOfMonth, planEndDate) <= 0) {
			currMonthTasks.add(task);
		} else if (planStartDate != null && planEndDate != null && DateTimeUtils.dateDiff(startOfMonth, planStartDate) < 0 && DateTimeUtils.dateDiff(endOfMonth, planEndDate) > 0) {
			currMonthTasks.add(task);
		} else if (actualStartDate != null && DateTimeUtils.dateDiff(actualStartDate, startOfMonth) <= 0 && DateTimeUtils.dateDiff(actualStartDate, endOfMonth) >= 0) {
			currMonthTasks.add(task);
		} else if (actualEndDate != null && DateTimeUtils.dateDiff(actualEndDate, startOfMonth) <= 0 && DateTimeUtils.dateDiff(actualEndDate, endOfMonth) >= 0) {
			currMonthTasks.add(task);
		} else if (planEndDate != null && DateTimeUtils.dateDiff(startOfMonth, planEndDate) >= 0 && DateTimeUtils.dateDiff(planEndDate, endOfMonth) >= 0) {
			currMonthTasks.add(task);
		} else if (planEndDate != null && DateTimeUtils.dateDiff(startOfMonth, planStartDate) >= 0 && DateTimeUtils.dateDiff(planStartDate, endOfMonth) >= 0) {
			currMonthTasks.add(task);
		}

		//

		// if (isBetweenDateRange(startOfMonth, endOfMonth,
		// task.getActualEndDate())) {
		// currMonthTasks.add(task);
		// return;
		// }
	}

	private static void addNextMonthTask(Date startOfMonth, Date endOfMonth, FDCScheduleTaskCollection currMonthTasks, FDCScheduleTaskInfo task) {
		try {
			// 对结束日期的特殊处理（例如：2011-12-1 00:00:00 与 2011-11-30 23:59:59 相差的天数为0）
			endOfMonth = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(endOfMonth));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 处理了时间,因为从实体获取的时间类型为java.sql.date 而比较的类型为 java.util.date
		Date planStartDate = task.getStart();
		if (planStartDate != null) {
			Calendar temp_planStartDate = Calendar.getInstance();
			temp_planStartDate.setTime(planStartDate);
			planStartDate = temp_planStartDate.getTime();// 计划开始
		}
		Date planEndDate = task.getEnd();
		if (planEndDate != null) {
			Calendar temp_planEndDate = Calendar.getInstance();
			temp_planEndDate.setTime(planEndDate);
			planEndDate = temp_planEndDate.getTime();// 计划结束
		}
		int complete = task.getComplete() == null ? 0 : task.getComplete().intValue();
		if (planEndDate != null && DateUtils.diffdates(startOfMonth, planEndDate) >= 0 && DateUtils.diffdates(endOfMonth, planEndDate) <= 0 && complete < 100) {
			currMonthTasks.add(task);
			return;
		}
		if (planStartDate != null && DateUtils.diffdates(startOfMonth, planStartDate) >= 0 && DateUtils.diffdates(endOfMonth, planStartDate) <= 0 && complete < 100) {
			currMonthTasks.add(task);
			return;
		}
		if (planStartDate != null && planEndDate != null && DateUtils.diffdates(planStartDate, startOfMonth) > 0 && DateUtils.diffdates(endOfMonth, planEndDate) > 0 && complete < 100) {
			currMonthTasks.add(task);
			return;
		}

	}

	private static boolean isBetweenDateRange(final Date startOfMonth, final Date endOfMonth, final Date compareDate) {
		if (compareDate == null) {
			return false;
		}

		if (DateUtils.diffdates(compareDate, startOfMonth) > 0 && DateUtils.diffdates(endOfMonth, compareDate) > 0) {
			return true;
		} else {
			return false;
		}

	}

	public static Date[] getStartAndEndDate(FullOrgUnitInfo info, Map alreadyMap) throws BOSException, SQLException {

		Date[] startAndEndDate = null;
		if (alreadyMap.get(info.getId().toString()) != null) {
			return (Date[]) alreadyMap.get(info.getId().toString());

		} else {

			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select min(task.fstart),max(task.fend) from t_sch_fdcscheduletask task right outer join t_sch_fdcschedule sch on task.fscheduleid = sch.fid");
			builder
					.appendSql(" left outer join t_fdc_curproject prj on prj.fid = sch.fprojectid  where prj.ffullorgunit= ? and sch.fislatestver =1 and (sch.fprojectspecialid is null or fprojectspecialid = '')");
			builder.addParam(info.getId().toString());

			IRowSet rs = builder.executeQuery();
			while (rs.next()) {
				Date startDate = rs.getDate(1);
				Date endDate = rs.getDate(2);
				startAndEndDate = new Date[] { startDate, endDate };
			}
			alreadyMap.put(info.getId().toString(), startAndEndDate);

		}

		return startAndEndDate;

	}

	private static void fillCtrl(KDComboBox yearCtrl, Date startDate, Date endDate) throws BOSException {
		if (startDate != null && endDate != null) {
			Calendar startCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();

			startCal.setTime(startDate);
			endCal.setTime(endDate);

			int startYear = startCal.get(Calendar.YEAR);
			int endYear = endCal.get(Calendar.YEAR);

			int startMonth = startCal.get(Calendar.MONTH);
			int endMonth = endCal.get(Calendar.MONTH);

			for (int i = startYear; i <= endYear; i++) {
				yearCtrl.addItem(Integer.valueOf(i));
			}

		}
	}

	private static void fillCtrl(Date startDate, Date endDate) {
		if (startDate != null && endDate != null) {
			Calendar startCal = new GregorianCalendar();
			Calendar endCal = new GregorianCalendar();

			startCal.setTime(startDate);
			endCal.setTime(endDate);

			int startYear = startCal.get(Calendar.YEAR);
			int endYear = endCal.get(Calendar.YEAR);

			int startMonth = startCal.get(Calendar.MONTH);
			int endMonth = endCal.get(Calendar.MONTH);

			int s = 0;
			int e = 12;

			for (int i = startYear; i <= endYear; i++) {
				if (i == startYear) {
					s = startMonth;
				} else if (i == endYear) {
					e = endMonth;
				}

				for (int j = s; j < e; j++) {
					System.out.println(i + ":" + (j + 1));
				}
				s = 0;
				e = 12;
			}
		}
	}

	public static void initStateCell(boolean isEvaluation, boolean isParamControl, IRow row, FDCScheduleTaskInfo entryInfo) throws BOSException {
		int state = -1;
		state = ViewReportHelper.getState(isEvaluation, isParamControl, entryInfo);
		setStateValue(row, state);
	}

	private static void setStateValue(IRow row, int state) {
		if (row != null) {
			String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
			// 勾
			String achieve = EASResource.getString(rsPath, "achieve");
			// 圈
			String pending = EASResource.getString(rsPath, "pending");
			// 绿
			Color green = new Color(10, 220, 10);
			// 红
			Color red = new Color(245, 0, 0);
			// 橙
			Color orange = new Color(220, 180, 0);
			switch (state) {
			case FDCScheduleConstant.ONTIME:
				row.getCell("state").setValue(achieve);
				row.getCell("state").setUserObject("0");
				row.getCell("state").getStyleAttributes().setBold(true);
				row.getCell("state").getStyleAttributes().setFontColor(green);
				break;
			case FDCScheduleConstant.DELAY:
				row.getCell("state").setValue(achieve);
				row.getCell("state").setUserObject("1");
				row.getCell("state").getStyleAttributes().setBold(true);
				row.getCell("state").getStyleAttributes().setFontColor(red);
				break;
			case FDCScheduleConstant.CONFIRMED:
				row.getCell("state").setValue(pending);
				row.getCell("state").setUserObject("2");
				row.getCell("state").getStyleAttributes().setFontColor(orange);
				break;
			case FDCScheduleConstant.DELAYANDNOTCOMPLETE:
				row.getCell("state").setValue(pending);
				row.getCell("state").setUserObject("3");
				row.getCell("state").getStyleAttributes().setFontColor(red);
				break;
			}
		}
	}

	public static Map<String, ScheduleTaskProgressReportInfo> getScheudleReportData(FDCScheduleTaskCollection list) throws BOSException {
		if (list == null || list.isEmpty()) {
			return null;
		}
		Set<String> srcIds = new HashSet<String>();
		for (int i = 0; i < list.size(); i++) {
			srcIds.add(list.get(i).getSrcID());
		}

		Map<String, ScheduleTaskProgressReportInfo> reportMap = new HashMap<String, ScheduleTaskProgressReportInfo>();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("description");
		view.getSelector().add("intendEndDate");
		view.getSelector().add("completePrecent");
		view.getSelector().add("reportDate");
		view.getSelector().add("srcRelateTask");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcRelateTask", srcIds, CompareType.INCLUDE));
		view.setFilter(filter);

		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo item = new SorterItemInfo("reportDate");
		item.setSortType(SortType.DESCEND);
		sorter.add(item);
		view.setSorter(sorter);

		ScheduleTaskProgressReportCollection reportCol = ScheduleTaskProgressReportFactory.getRemoteInstance().getScheduleTaskProgressReportCollection(view);
		ScheduleTaskProgressReportInfo reportInfo = null;
		ScheduleTaskProgressReportInfo otherInfo = null;
		for (Iterator it = reportCol.iterator(); it.hasNext();) {
			reportInfo = (ScheduleTaskProgressReportInfo) it.next();
			if (reportMap.get(reportInfo.getSrcRelateTask()) == null) {
				reportMap.put(reportInfo.getSrcRelateTask(), reportInfo);
			} else {
				if (reportMap.get(reportInfo.getSrcRelateTask()) != null) {
					otherInfo = reportMap.get(reportInfo.getSrcRelateTask());
					if (DateTimeUtils.dateDiff(reportInfo.getReportDate(), otherInfo.getReportDate()) < 0) {
						reportMap.remove(reportInfo.getSrcRelateTask());
					}
				}

			}
		}
		return reportMap;
	}
}
