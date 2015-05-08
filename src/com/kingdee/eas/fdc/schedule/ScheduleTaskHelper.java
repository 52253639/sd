/**
 * 
 */
package com.kingdee.eas.fdc.schedule;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.message.BMCMessageFactory;
import com.kingdee.eas.base.message.BMCMessageInfo;
import com.kingdee.eas.base.message.IBMCMessage;
import com.kingdee.eas.base.message.MsgBizType;
import com.kingdee.eas.base.message.MsgPriority;
import com.kingdee.eas.base.message.MsgStatus;
import com.kingdee.eas.base.message.MsgType;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.receiver.BasReceiverCollection;
import com.kingdee.eas.base.receiver.BasReceiverInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.IContextHelper;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述：
 * 
 * @author 杜红明
 * @version EAS7.0
 * @createDate 2011-11-14
 * @see
 */ 

public class ScheduleTaskHelper {

	/**
	 * 
	 * @description 是否进行参数控制
	 * @author 杜红明
	 * @createDate 2011-11-4
	 * @return boolean
	 * @version EAS7.0
	 * @see
	 */
	public static boolean isStartingParamControl(Context ctx) {
		String companyId = "";
		
		if (null != ContextUtil.getCurrentFIUnit(ctx).getId()) {
			companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		}
		ObjectUuidPK id = new ObjectUuidPK(BOSUuid.read(companyId));
		// 控制主项任务
		// 专项任务的计划完成日期大于相关主项任务的计划完成日期
		String fdcParamValue = "";
		try {
			fdcParamValue = ParamControlFactory.getLocalInstance(ctx).getParamValue(id, "FDCSH011");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return !"0".equals(fdcParamValue);
	}
	
	public static boolean isLatestVer(Context ctx, BOSUuid id) throws SQLException, BOSException {
		FDCSQLBuilder t_sql = new FDCSQLBuilder(ctx);
		t_sql.appendSql(" select sch.fisLatestVer from T_SCH_FDCScheduleTask as sche ");
		t_sql.appendSql(" LEFT OUTER JOIN T_SCH_FDCSchedule as sch ");
		t_sql.appendSql(" on sche.FScheduleID =sch.fid  ");
		t_sql.appendSql(" where sche.fsrcid = '" + id.toString() + "' ");
		t_sql.appendSql(" and sch.fisLatestVer=1 ");
		IRowSet rs = t_sql.executeQuery();
		int isLatestVer = 0;
		if (rs.next()) {
			isLatestVer = rs.getInt(1);
		}
		if (isLatestVer == 1) {
			return true;
		}
		return false;
	}
	
	public static boolean isScheduleAppraise(Context ctx, BOSUuid id) throws SQLException, BOSException {
		if (isStartingParamControl(ctx)) {
			FDCSQLBuilder t_sql = new FDCSQLBuilder(ctx);
			t_sql.appendSql(" select eval.fevaluationtype from T_SCH_FDCScheduleTask as sche ");
			t_sql.appendSql(" LEFT OUTER JOIN T_SCH_TaskEvaluationbill as eval ");
			t_sql.appendSql(" on sche.fsrcid =eval.fsrcrelatetask where sche.fsrcid = '" + id.toString() + "' ");
			IRowSet rs = t_sql.executeQuery();
			String type = "";
			while (rs.next()) {
				type = rs.getString("fevaluationtype");
			}
			if ("".equals(type) || type == null) {
				return false;
			}
		}
		return true;
	}

	/***
	 * 1、启用待评价参数；2、进度=100%；3、未进行进度评价或质量评价 描述：待评价返回true，否则返回false;4子级任务
	 */
	public static boolean isNeedEvaluation(Context ctx, BOSUuid id) throws SQLException, BOSException {
		// 启用待评价参数
		if (isStartingParamControl(ctx)) {
			FDCSQLBuilder t_sql = new FDCSQLBuilder(ctx);
			t_sql.appendSql(" select sch.fVersionname from T_SCH_FDCScheduleTask as sche ");
			t_sql.appendSql(" LEFT OUTER JOIN T_SCH_TaskEvaluationbill as eval ");
			t_sql.appendSql(" on sche.fsrcid =eval.fsrcrelatetask  ");
			t_sql.appendSql(" LEFT OUTER JOIN T_SCH_FDCSchedule as sch ");
			t_sql.appendSql(" on sche.FScheduleID =sch.fid  ");
			t_sql.appendSql(" where sche.fsrcid = '" + id.toString() + "' ");
			t_sql.appendSql(" and sch.fisLatestVer=1 ");
			t_sql.appendSql(" and sche.fcomplete=100 ");
			t_sql.appendSql(" and eval.FEvaluationResult is null ");
			t_sql.appendSql(" and sche.FIsLeaf = 1 ");

			IRowSet rs = t_sql.executeQuery();
			String type = "";
			if (rs.next()) {
				type = rs.getString(1);
			}
			if (type != null && !("".equals(type))) {
				return true;
			}			
		}
		return false;
	}

	/***
	 * 
	 * 描述：当前日期大于计划完成日期返回true，否则返回false
	 */
	public static boolean isDelay(Context ctx, BOSUuid id) throws SQLException, BOSException {
		FDCSQLBuilder t_sql = new FDCSQLBuilder(ctx);
		t_sql.appendSql(" select sche.fend from T_SCH_FDCScheduleTask as sche ");
		t_sql.appendSql(" LEFT OUTER JOIN T_SCH_FDCSchedule as sch ");
		t_sql.appendSql(" on sche.FScheduleID =sch.fid  ");
		t_sql.appendSql(" where sche.fsrcid = '" + id.toString() + "' ");
		t_sql.appendSql(" and sch.fisLatestVer=1 ");
		
		IRowSet rs = t_sql.executeQuery();
		Date end = null;
		if (rs.next()) {
			end = rs.getDate(1);
		}
		if (end != null) {
			Date now = new Date();
			if (days(end, now) > 0) {
				return true;
			}
		}
		return false;
	}
	
	public static int days(Date starDate, Date endDate) {
		long a = truncateDate(endDate).getTime();
		long b = truncateDate(starDate).getTime();
		long c = a - b;
		long y = c / (1000 * 60 * 60 * 24);
		return Integer.parseInt(y + "");
	}
	
	public static Date truncateDate(Date dt) {
		if (dt == null) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			cal.set(cal.get(1), cal.get(2), cal.get(5), 0, 0, 0);
			return new Date((cal.getTimeInMillis() / 1000L) * 1000L);
		}
	}
	/***
	 * 
	 * 描述：延期返回true，否则返回false
	 */
	public static boolean isNotCompleted(Context ctx, BOSUuid id) throws SQLException, BOSException {
		if (isNotComplete(ctx, id) && isDelay(ctx, id)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotComplete(Context ctx, BOSUuid id) throws SQLException, BOSException {
		FDCSQLBuilder t_sql = new FDCSQLBuilder(ctx);
		t_sql.appendSql(" select sche.fcomplete from T_SCH_FDCScheduleTask as sche ");
		t_sql.appendSql(" LEFT OUTER JOIN T_SCH_FDCSchedule as sch ");
		t_sql.appendSql(" on sche.FScheduleID =sch.fid  ");
		t_sql.appendSql(" where sche.fsrcid = '" + id.toString() + "' ");
		t_sql.appendSql(" and sch.fisLatestVer=1 ");
		IRowSet rs = t_sql.executeQuery();
		float complete = 0.0f;
		if (rs.next()) {
			complete = rs.getFloat(1);
		}
		if (complete >= 100) {
			return false;
		}
		return true;
	}
	/***
	 * 
	 * 描述：前后置任务反转
	 * 
	 * @param newTasks
	 * @param taskEntrys
	 * @return 创建人：yuanjun_lan 创建时间：2012-2-14
	 */
	// public static FDCScheduleTaskCollection
	// getRelationReverseTasks(FDCScheduleTaskCollection newTasks,
	// FDCScheduleTaskCollection taskEntrys) {
	// /** 清理了任务中的前置任务后的任务对象 **/
	// for (int i = 0; i < taskEntrys.size(); i++) {
	// FDCScheduleTaskInfo task = taskEntrys.get(i);
	// FDCScheduleTaskInfo clone = (FDCScheduleTaskInfo) task.clone();
	// clone.getDepends().clear();
	// newTasks.add(clone);
	// }
	//
	// for (int i = 0; i < taskEntrys.size(); i++) {
	// FDCScheduleTaskInfo task = taskEntrys.get(i);
	// ScheduleTaskDependCollection depends = task.getDepends();
	// /** 循环任务中的前置任务 **/
	// for (int j = 0; j < depends.size(); j++) {
	// ScheduleTaskDependInfo depend = depends.get(j);
	// ScheduleTaskDependInfo clone = (ScheduleTaskDependInfo) depend.clone();
	// /** 在克隆的前置任务中放置它的：任务和前置任务(此时前置任务中放的是前置任务,前置任务中放的任务) **/
	// clone.put("task", depend.getDependTaskBase());
	// clone.put("dependTask", depend.getTaskBase());
	// newTasks.get(i).getDepends().add(clone);
	// }
	// }
	// return newTasks;
	// }
	// public static void sendBMCMsgInfo(Context ctx, String title, String body,
	// UserInfo user, String billId) throws BOSException {
	// if (user == null) {
	// return;
	// }
	// IBMCMessage iBMCMessage;
	// IContextHelper localInstance;
	// if (ctx == null) {
	// iBMCMessage = BMCMessageFactory.getRemoteInstance();
	// localInstance = ContextHelperFactory.getRemoteInstance();
	// } else {
	// iBMCMessage = BMCMessageFactory.getLocalInstance(ctx);
	// localInstance = ContextHelperFactory.getLocalInstance(ctx);
	//
	// }
	// BMCMessageInfo msgInfo = new BMCMessageInfo();
	// msgInfo.setTitle(title);
	// msgInfo.setBody(body);
	// msgInfo.setPriority(MsgPriority.HIGH);
	// msgInfo.setReceivers(user.getName());
	// msgInfo.setNreceivers(user.getName());
	// msgInfo.setType(MsgType.NOTICE);
	// msgInfo.setBizType(MsgBizType.XITONGOFFICE);
	// msgInfo.setStatus(MsgStatus.UNREADED);
	// msgInfo.setId(BOSUuid.create(msgInfo.getBOSType()));
	// msgInfo.setOrgType(OrgType.ControlUnit);
	// msgInfo.setOrgID(localInstance.getCurrentCtrlUnit().getId().toString());
	// msgInfo.setSender(localInstance.getCurrentUser().getName());
	// // msgInfo.setSourceIDs("|" + billId + "|" + billId + "|" + billId);
	//
	// BasReceiverInfo receiverInfo = new BasReceiverInfo();
	// receiverInfo.setType(ReceiverObjType.STATIC_USER);
	// receiverInfo.setValue(user.getId().toString());
	// receiverInfo.setDesc(user.getName());
	// // 默认在发布的porlet显示
	// receiverInfo.setIsShow(false);
	// BasReceiverCollection receivercoll = new BasReceiverCollection();
	// receivercoll.add(receiverInfo);
	// iBMCMessage.addHandMsg(msgInfo, receivercoll);
	//
	// }
	public static void sendBMCMsgInfo(Context ctx, String title, String body, UserInfo user, String billId) throws BOSException {
		if (user == null)
			return;
		IBMCMessage iBMCMessage;
		IContextHelper localInstance;
		if (ctx == null) {
			iBMCMessage = BMCMessageFactory.getRemoteInstance();
			localInstance = ContextHelperFactory.getRemoteInstance();
		} else {
			iBMCMessage = BMCMessageFactory.getLocalInstance(ctx);
			localInstance = ContextHelperFactory.getLocalInstance(ctx);
		}

		BMCMessageInfo msgInfo = new BMCMessageInfo();
		msgInfo.setTitle(title);
		msgInfo.setBody(body);
		msgInfo.setPriority(MsgPriority.HIGH);
		msgInfo.setReceivers(user.getName());
		msgInfo.setNreceivers(user.getName());
		msgInfo.setType(MsgType.NOTICE);
		msgInfo.setBizType(MsgBizType.XITONGOFFICE);
		msgInfo.setStatus(MsgStatus.UNREADED);
		msgInfo.setId(BOSUuid.create(msgInfo.getBOSType()));
		// 业务消息对象's 预警消息ID字符串,目的放出单据按钮
		msgInfo.setSourceIDs("|" + billId + "|" + billId + "|" + billId);

		msgInfo.setOrgType(OrgType.ControlUnit);
		msgInfo.setOrgID(localInstance.getCurrentCtrlUnit().getId().toString());
		msgInfo.setSender(localInstance.getCurrentUser().getName());

		BasReceiverInfo receiverInfo = new BasReceiverInfo();
		receiverInfo.setType("1");
		receiverInfo.setValue(user.getId().toString());
		receiverInfo.setDesc(user.getName());

		receiverInfo.setIsShow(false);
		BasReceiverCollection receivercoll = new BasReceiverCollection();
		receivercoll.add(receiverInfo);
		iBMCMessage.addHandMsg(msgInfo, receivercoll);
	}
}
