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
	 * ���Ե�ǰ������н�������ʱ��������۽��Ϊ��ͨ��ʱ���Զ���д��ǰ������깤�ٷֱ�99%</br>,���ڽ��Ȼ㱨�в���һ�������������Ϊ99%�ļ�¼
	 * 
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_save(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectValue)
	 */
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		/* TODO �Զ����ɷ������ */
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
					reportInfo.setDescription("�������۲�ͨ����ϵͳ�Զ�������¼");
					reportInfo.setBizDate(new Date());
					reportInfo.setNumber("ϵͳ�Զ�����" + UUID.randomUUID().toString());
					reportInfo.setName("ϵͳ�Զ�����" + UUID.randomUUID().toString());
					ScheduleTaskProgressReportFactory.getLocalInstance(ctx).addnew(reportInfo);
				}
			//������ǰд������Ϣ���ʹ��룬����Ԥ��ʵ�� 

//			 String title = task.getName() + "����û��ͨ��,�����½��л㱨!";
//			String body = schedule.getVersionName() + "�е������" + task.getName() + "����Ϊ" + evaluation.getEvaluationResult()
//					+ ",û��ͨ��,�����½��л㱨!";
//			PersonInfo person = task.getAdminPerson();
//			if (person == null) {
//				logger.error("��ǰ����û�������ˣ���������Ԥ����Ϣ ��");
//				return pk;
//			}
//			UserCollection userCols = UserFactory.getLocalInstance(ctx).getUserCollection("where person.id='" + person.getId().toString() + "'");
//			if (userCols.size() == 0) {
//				logger.error("��ǰ����û��������û�й����û�����������Ԥ����Ϣ ��");
//				return pk;
//			}
//			UserInfo user = userCols.get(0);
//			if (user == null) {
//				logger.error("��ǰ����û��������û�й����û�����������Ԥ����Ϣ ��");
//				return pk;
//			}
//			ScheduleTaskHelper.sendBMCMsgInfo(ctx, title, body, user, pk.toString());
			
		}
		
		
		
		return pk;
	}
	
	
	
	
}