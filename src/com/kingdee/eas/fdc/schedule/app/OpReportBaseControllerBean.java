package com.kingdee.eas.fdc.schedule.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.OpReportBaseHelper;
import com.kingdee.eas.fdc.schedule.OpReportEntryBaseInfo;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo;
import com.kingdee.eas.util.app.ContextUtil;

public class OpReportBaseControllerBean extends AbstractOpReportBaseControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.OpReportBaseControllerBean");
    

	public static final String WEEK_DESC = "周报告汇报记录:";
	public static final String MONTH_DESC = "月报告汇报记录:";
	
	/**
	 * 处理进度周/月报的数据反写
	 */
	protected void processReport(Context ctx, boolean isWeek, OpReportEntryBaseInfo reportEntryInfo) throws EASBizException, BOSException {
		Date realEndDate = reportEntryInfo.getRealEndDate();
		Date intendEndDate = reportEntryInfo.getIntendEndDate();
		boolean isComplete = reportEntryInfo.isIsComplete();
		BigDecimal completePrecent = FDCNumberHelper.toBigDecimal(reportEntryInfo.getCompletePrecent());
		BigDecimal completeAmount = reportEntryInfo.getCompleteAmount();

		FDCScheduleTaskInfo task = reportEntryInfo.getRelateTask();
		task.setActualStartDate(reportEntryInfo.getPlanStartDate());

		Date realEndDate_ = task.getActualEndDate();
		Date intendEndDate_ = task.getIntendEndDate();
		BigDecimal completePrecent_ = task.getComplete();

		boolean taskChanged = false; // 任务是否有改变
		boolean reportChanged = false; //报告是否有改变
		// 是否100%完成
		boolean isComplete_ = FDCNumberHelper.ONE_HUNDRED.compareTo(task.getComplete()) == 0;

		// 数据比对
		if (!OpReportBaseHelper.dateCompare(realEndDate_, realEndDate)) {
			task.setActualEndDate(realEndDate);
			taskChanged = true;
			reportChanged = true;
		}

		if (!OpReportBaseHelper.dateCompare(intendEndDate_, intendEndDate)) {
			task.setIntendEndDate(intendEndDate);
			taskChanged = true;
			reportChanged = true;
		}
		if (!OpReportBaseHelper.bigDecimalCompare(completePrecent_, completePrecent)) {
			taskChanged = true;
			reportChanged = true;
		}
		if (completeAmount != null && completeAmount.compareTo(FDCNumberHelper.ZERO) > 0) {
			reportChanged = true;
			taskChanged = true;
			task.setWorkLoad(FDCNumberHelper.add(completeAmount, task.getWorkLoad()));
		}
		if (isComplete_ != isComplete) {
			reportChanged = true;
		}

		updateTaskReport(ctx, reportEntryInfo, task, taskChanged, reportChanged, isWeek);
	}

	/**
	 * @description 根据变化情况,更新任务数据
	 * @author 杜红明
	 * @param reportChanged 如果周/月报告有变化
	 * @createDate 2011-12-29
	 */
	private void updateTaskReport(Context ctx, OpReportEntryBaseInfo baseInfo, FDCScheduleTaskInfo task, boolean taskChanged,
			boolean reportChanged, boolean isWeek) throws EASBizException, BOSException {
		if (reportChanged) {// 如果有任意一个字段变化,就新增一条汇报记录
			boolean isMainSchedule = false;
			if (baseInfo instanceof ProjectWeekReportEntryInfo) {
				ProjectWeekReportEntryInfo pwre = (ProjectWeekReportEntryInfo) baseInfo;
				isMainSchedule = (pwre.getHead().getProjectSpecial() == null);//专项为null时，就是主项
			} else if (baseInfo instanceof ProjectMonthReportEntryInfo) {
				ProjectMonthReportEntryInfo pmre = (ProjectMonthReportEntryInfo) baseInfo;
				isMainSchedule = (pmre.getHead().getProjectSpecial() == null);
			}
			
			addNewScheduleTaskReport(ctx, baseInfo, isWeek, isMainSchedule);
			// if (taskChanged) {// 当进度、完成情况说明、实际完成日期变化,就更新任务的进度
			// FDCScheduleTaskFactory.getRemoteInstance().update(new ObjectStringPK(task.getId().toString()), task);
			// }
		}
	}

	/**
	 * @description 增加一个进度汇报
	 * @param isWeek true 是周报，fasle为月报
	 * @author 杜红明
	 */
	private void addNewScheduleTaskReport(Context ctx, OpReportEntryBaseInfo reportEntryInfo, boolean isWeek, boolean isMainSchedule)
			throws EASBizException, BOSException {

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule.islatestver", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("srcid", "select fsrcid from t_sch_fdcscheduletask where fid='"
						+ reportEntryInfo.getRelateTask().getId().toString() + "'", CompareType.INNER));
		if (isMainSchedule) {
			filter.getFilterItems().add(new FilterItemInfo("schedule.projectspecial is null"));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("schedule.projectspecial is not null"));
		}
		view.setFilter(filter);
		FDCScheduleTaskInfo relateTask = (FDCScheduleTaskInfo) FDCScheduleTaskFactory.getLocalInstance(ctx).getCollection(view).get(0);
		relateTask.setActualStartDate(reportEntryInfo.getPlanStartDate());

		// 需要新建的进度汇报
		ScheduleTaskProgressReportInfo stprInfo = new ScheduleTaskProgressReportInfo();
		stprInfo.setIsComplete(reportEntryInfo.isIsComplete());
		stprInfo.setIntendEndDate(reportEntryInfo.getIntendEndDate());
		stprInfo.setRealEndDate(reportEntryInfo.getRealEndDate());
		relateTask.setActualEndDate(reportEntryInfo.getRealEndDate());
		stprInfo.setTaskName(relateTask.getName());
		BigDecimal complete = new BigDecimal(reportEntryInfo.getCompletePrecent());
		BigDecimal workAmount = FDCHelper.toBigDecimal(reportEntryInfo.getCompleteAmount());
		if (complete != null) {
			relateTask.setComplete(complete);
		}
		if (workAmount != null) {
			relateTask.setWorkLoad(workAmount);
		}

		stprInfo.setCompleteAmount(workAmount);
		stprInfo.setCompletePrecent(complete);
		stprInfo.setReportor(reportEntryInfo.getRespPerson());
		stprInfo.setReportDate(new Date());
		stprInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		stprInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
		stprInfo.setTaskNumber(relateTask.getNumber());
		stprInfo.setPlanEndDate(relateTask.getPlanEnd());
		stprInfo.setRelateTask(relateTask);
		stprInfo.setSrcRelateTask(relateTask.getSrcID());
		stprInfo.setId(BOSUuid.create(stprInfo.getBOSType()));
		stprInfo.setRealStartDate(reportEntryInfo.getPlanStartDate());
		stprInfo.setReportor(ContextUtil.getCurrentUserInfo(ctx).getPerson());
		String desc = reportEntryInfo.getDescription();
		if (desc == null) {
			desc = "";
		}
		if (isWeek) {
			desc = WEEK_DESC + desc;
		} else {
			desc = MONTH_DESC + desc;
		}
		if (desc.length() > 80) {
			desc = desc.substring(0, 79);
		}
		stprInfo.setDescription(desc);
		ScheduleTaskProgressReportFactory.getLocalInstance(ctx).save(stprInfo);
	}	

}