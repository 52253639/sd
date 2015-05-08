package com.kingdee.eas.fdc.schedule.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.FDCScheduleCompleteHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskStateHelper;
import com.kingdee.eas.fdc.schedule.ScheduleHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum;
import com.kingdee.eas.util.app.ContextUtil;

public class TaskEvaluationBillControllerBean extends AbstractTaskEvaluationBillControllerBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1257913732259943911L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.TaskEvaluationBillControllerBean");
	/**
	 * 当对当前任务进行进度评价时，如果评价结果为不通过时，自动改写当前任务的完工百分比99%</br>,并在进度汇报中插入一条更改任务进度为99%的纪录
	 * 
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_save(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectValue)
	 */
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		/* TODO 自动生成方法存根 */
		IObjectPK pk = super._save(ctx, model);
		TaskEvaluationBillInfo evaBill= (TaskEvaluationBillInfo) model;
		TaskEvaluationInfo evaluation = evaBill.getEvaluationResult();
		FDCScheduleTaskInfo task = evaBill.getRelateTask();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("versionName");
		sic.add("project.id");
		sic.add("project.number");
		sic.add("project.name");
		sic.add("project.costCenter.id");
		sic.add("project.costCenter.name");
		sic.add("project.costCenter.number");
		sic.add("projectSpecial.id");
		sic.add("projectSpecial.name");
		sic.add("projectSpecial.number");
        FDCScheduleInfo schedule =  FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleInfo(new ObjectUuidPK(task.getSchedule().getId()), sic);
		String passMustEvaluation = ParamManager.getParamValue(ctx, new ObjectUuidPK(schedule.getProject().getCostCenter().getId()), "FDCSH011");
		boolean isMustEvaluation = FDCScheduleTaskStateHelper.isMustEvaluation(passMustEvaluation, schedule);
		if (evaBill.getEvaluationType().equals(TaskEvaluationTypeEnum.SCHEDULE) && !evaluation.isEvaluationPass()) {
				task = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskInfo(new ObjectUuidPK(task.getId()),ScheduleHelper.getTaskSelector());
				if (isMustEvaluation) {
				    task.setSchedule(schedule);
					task.setComplete(new BigDecimal("99"));
					FDCScheduleCompleteHelper.updateRelateTaskComplete(ctx, task);
					FDCScheduleCompleteHelper.updateAllTaskCompleteRate(ctx, task);

					ScheduleTaskProgressReportInfo reportInfo = new ScheduleTaskProgressReportInfo();
					reportInfo.setRelateTask(task);
					reportInfo.setSrcRelateTask(task.getSrcID());
					reportInfo.setAmount(new BigDecimal("-1.0"));
					reportInfo.setCompletePrecent(new BigDecimal("99"));
					reportInfo.setReportDate(new Date());
					reportInfo.setReportor(ContextUtil.getCurrentUserInfo(ctx).getPerson());
					reportInfo.setDescription("进度评价不通过，系统自动调整记录");
					reportInfo.setBizDate(new Date());
					reportInfo.setNumber("系统自动调整" + UUID.randomUUID().toString());
					reportInfo.setName("系统自动调整" + UUID.randomUUID().toString());
					ScheduleTaskProgressReportFactory.getLocalInstance(ctx).addnew(reportInfo);
				}
			//屏蔽以前写死的消息发送代码，改用预警实现 

//			 String title = task.getName() + "评价没有通过,请重新进行汇报!";
//			String body = schedule.getVersionName() + "中的任务项：" + task.getName() + "评价为" + evaluation.getEvaluationResult()
//					+ ",没有通过,请重新进行汇报!";
//			PersonInfo person = task.getAdminPerson();
//			if (person == null) {
//				logger.error("当前任务没有责任人，不能推送预警消息 ！");
//				return pk;
//			}
//			UserCollection userCols = UserFactory.getLocalInstance(ctx).getUserCollection("where person.id='" + person.getId().toString() + "'");
//			if (userCols.size() == 0) {
//				logger.error("当前任务没有责任人没有关联用户，不能推送预警消息 ！");
//				return pk;
//			}
//			UserInfo user = userCols.get(0);
//			if (user == null) {
//				logger.error("当前任务没有责任人没有关联用户，不能推送预警消息 ！");
//				return pk;
//			}
//			ScheduleTaskHelper.sendBMCMsgInfo(ctx, title, body, user, pk.toString());
			
		}
		
		
		
		return pk;
	}
	
	
	
	
}