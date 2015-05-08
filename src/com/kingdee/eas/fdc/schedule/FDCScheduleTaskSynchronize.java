package com.kingdee.eas.fdc.schedule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 计划任务同步， 主要同步任务的完成时间和完成百分比, fcomplete, factualenddate, FWorkLoad
 * @author zhiqiao_yang
 * 2010-6-22 上午11:37:22
 * TODO: 性能有待优化， 还需同步workLoad
 * 
 */
public class FDCScheduleTaskSynchronize {
	/**
	 * 通过工程量录入来同步
	 * @param ctx
	 * @param wbsIds
	 * @throws BOSException 
	 */
	public static void modifyByTaskLoad(Context ctx, Set wbsIds) throws BOSException{		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);		
		modifyLeafByTaskLoad(wbsIds, builder);	
		modifyParent(ctx, wbsIds);	
	}
	/**
	 * 通过工程量录入来同步非明细节点的实际结束日期和完成百分比
	 * @param wbsIds
	 * @param builder
	 * @throws BOSException
	 */
	public static void modifyParent(Context ctx, Set wbsIds) throws BOSException{
		//获取所有祖先节点
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);		
		builder.appendSql("select a.fid from T_SCH_FDCWBS a ,T_SCH_FDCWBS b where a.fcurProjectid=b.fcurProjectid and charindex(a.flongnumber||'!',b.flongnumber)=1 ");
		if(wbsIds != null && !wbsIds.isEmpty()){
			builder.appendParam("and b.fid", wbsIds.toArray());
		}
		builder.appendSql(" order by a.fcurProjectid, a.flongnumber desc");
		IRowSet rowSet=builder.executeQuery();
		builder.clear();
		List parent2WbsIds=new ArrayList();
		List parent3WbsIds=new ArrayList();
		try{
			while(rowSet.next()){
				String fid = rowSet.getString("fid");
				parent2WbsIds.add(Arrays.asList(new Object[]{fid,fid}));
				parent3WbsIds.add(Arrays.asList(new Object[]{fid,fid,fid}));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		//更新所有祖先节点的完工比例
		StringBuffer updateComplete=new StringBuffer();
		updateComplete.append("update t_sch_fdcscheduletask set fcomplete=( ");
		updateComplete.append("		select sum(feffecttimes*fcomplete)/sum(feffecttimes) from t_sch_fdcscheduletask ");
		updateComplete.append("		where fwbsid in (select fid from T_SCH_FDCWBS where  fparentid=? ");
		updateComplete.append(")) where fwbsid=? ");
		updateComplete.append("and exists (select 1 from T_sch_fdcschedule where fid=t_sch_fdcscheduletask.fscheduleid and fislatestver=1 )"); 
		builder.executeBatch(updateComplete.toString(), parent2WbsIds);
		builder.clear();
		//更新所有祖先节点的完工时间
		StringBuffer updateActualEndDate=new StringBuffer();
		updateActualEndDate.append("update t_sch_fdcscheduletask set factualenddate=");
		updateActualEndDate.append("case when ");
//		updateActualEndDate.append("    (exists (select 1 from t_sch_fdcscheduletask where (fcomplete<100 or fcomplete is null) and ");
//		updateActualEndDate.append("    fwbsid in (select fid from T_SCH_FDCWBS where  fparentid=? )))");
//modify by warship at 2010/09/01 修复和DB2兼容问题		
		updateActualEndDate.append("    ( (select count(1) from t_sch_fdcscheduletask where (fcomplete<100 or fcomplete is null) and ");
		updateActualEndDate.append("    fwbsid in (select fid from T_SCH_FDCWBS where  fparentid=? ))>0)");
		updateActualEndDate.append("then null ");
		updateActualEndDate.append("else ");
		updateActualEndDate.append("    (select max(factualenddate) from t_sch_fdcscheduletask where ");
		updateActualEndDate.append("    fwbsid in (select fid from T_SCH_FDCWBS where  fparentid=? ))");
		updateActualEndDate.append("end ");
		updateActualEndDate.append("where fwbsid=? ");
		updateActualEndDate.append("and exists (select 1 from T_sch_fdcschedule where fid=t_sch_fdcscheduletask.fscheduleid and fislatestver=1 )"); 
		builder.executeBatch(updateActualEndDate.toString(), parent3WbsIds);
		builder.clear();
	}
	/**
	 * 通过工程量录入来同步明细节点的实际结束日期和完成百分比
	 * @param wbsIds
	 * @param builder
	 * @throws BOSException
	 */
	private static void modifyLeafByTaskLoad( Set wbsIds, FDCSQLBuilder builder) throws BOSException{
		builder.appendSql("update t_sch_fdcscheduletask set FWorkLoad=(  ");
		builder.appendSql("    select sum(T_SCH_TaskLoadEntry.FLoad) from T_SCH_TaskLoadEntry ");
		builder.appendSql("	   where T_SCH_TaskLoadEntry.FWBSId=t_sch_fdcscheduletask.FWBSId and T_SCH_TaskLoadEntry.FIsConfirm=1) ");
		builder.appendSql("where FIsLeaf=1 ");
		if (wbsIds != null && !wbsIds.isEmpty()) {
			builder.appendParam("and FWBSId", wbsIds.toArray());
		}
		builder.appendSql(" and FScheduleID in (");
		builder.appendSql("    select sch.fid from T_SCH_FDCSchedule sch, T_SCH_ScheduleVerManager vm ");
		builder.appendSql("    where sch.fid=T_SCH_FDCScheduleTask.FScheduleId ");
		builder.appendSql("    and vm.fid=sch.fbaseVerid and vm.fislatestVer=1)");
		builder.executeUpdate();
		builder.clear();		
		//更新明细节点的完成百分比
		builder.appendSql("update t_sch_fdcscheduletask set fcomplete=(  ");
		builder.appendSql("    select sum(T_SCH_TaskLoadEntry.FPercent) from T_SCH_TaskLoadEntry ");
		builder.appendSql("	   where T_SCH_TaskLoadEntry.FWBSId=t_sch_fdcscheduletask.FWBSId and T_SCH_TaskLoadEntry.FIsConfirm=1) ");
		builder.appendSql("where FIsLeaf=1 ");
		if(wbsIds != null && !wbsIds.isEmpty()){
			builder.appendParam("and FWBSId", wbsIds.toArray());
		}
		builder.appendSql(" and FScheduleID in (");
		builder.appendSql("    select sch.fid from T_SCH_FDCSchedule sch, T_SCH_ScheduleVerManager vm ");
		builder.appendSql("    where sch.fid=T_SCH_FDCScheduleTask.FScheduleId ");
		builder.appendSql("    and vm.fid=sch.fbaseVerid and vm.fislatestVer=1)");
		builder.executeUpdate();
		builder.clear();		
		//如果FDCScheduleTask的complete大于等于100则设置actualEndData为TaskLoadEntry中completeDate的最大值，否则设置为null
		builder.appendSql("update T_Sch_FDCScheduleTask set factualenddate=");
		builder.appendSql("case when fcomplete>=100 then ");
		builder.appendSql("	   (select max(T_SCH_TaskLoadEntry.FCOMPLETEDATE) from T_SCH_TaskLoadEntry ");
		builder.appendSql("    where T_SCH_TaskLoadEntry.FWBSID=T_Sch_FDCScheduleTask.FWBSID and T_SCH_TaskLoadEntry.FIsConfirm=1) ");
		builder.appendSql("else null end ");
		builder.appendSql("where FIsLeaf=1 ");
		if(wbsIds != null && !wbsIds.isEmpty()){
			builder.appendParam("and FWBSId", wbsIds.toArray());
		}
		builder.appendSql(" and FScheduleID in (");
		builder.appendSql("    select sch.fid from T_SCH_FDCSchedule sch, T_SCH_ScheduleVerManager vm ");
		builder.appendSql("    where sch.fid=T_SCH_FDCScheduleTask.FScheduleId ");
		builder.appendSql("    and vm.fid=sch.fbaseVerid and vm.fislatestVer=1)");
		builder.executeUpdate();
		builder.clear();	
	}
	
	/**
	 * 任务工程量录入
	 * @param ctx
	 * @param wbsIds
	 * @throws BOSException 
	 */
	public static void modifyByWorkAmount(Context ctx, Set wbsIds) throws BOSException{
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);		
		
		builder.appendSql("update t_sch_fdcscheduletask set FWorkLoad=");
		builder.appendSql("    (select sum(FConfirmAmount) from T_SCH_WorkAmountEntry ent inner join T_SCH_WorkAmountBill head");
		builder.appendSql("    on head.fid=ent.fparentid where head.fstate=? and ent.FWbsID=T_SCH_FDCScheduleTask.FwbsID)");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		builder.appendSql("where FIsLeaf=1  ");
		if (wbsIds != null && !wbsIds.isEmpty()) {
			builder.appendParam("and FWBSId", wbsIds.toArray());
		}
		builder.appendSql(" and FScheduleID in (");
		builder.appendSql("    select sch.fid from T_SCH_FDCSchedule sch, T_SCH_ScheduleVerManager vm ");
		builder.appendSql("    where sch.fid=T_SCH_FDCScheduleTask.FScheduleId ");
		builder.appendSql("    and vm.fid=sch.fbaseVerid and vm.fislatestVer=1)");
		builder.executeUpdate();
		builder.clear();
		
		builder.appendSql("update t_sch_fdcscheduletask set fcomplete=");
		builder.appendSql("    (select sum(FconfirmPercent) from T_SCH_WorkAmountEntry ent inner join T_SCH_WorkAmountBill head");
		builder.appendSql("    on head.fid=ent.fparentid where head.fstate=? and ent.FWbsID=T_SCH_FDCScheduleTask.FwbsID)");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		builder.appendSql("where FIsLeaf=1  ");
		if(wbsIds != null && !wbsIds.isEmpty()){
			builder.appendParam("and FWBSId", wbsIds.toArray());
		}
		builder.appendSql(" and FScheduleID in (");
		builder.appendSql("    select sch.fid from T_SCH_FDCSchedule sch, T_SCH_ScheduleVerManager vm ");
		builder.appendSql("    where sch.fid=T_SCH_FDCScheduleTask.FScheduleId ");
		builder.appendSql("    and vm.fid=sch.fbaseVerid and vm.fislatestVer=1)");
		builder.executeUpdate();
		builder.clear();
		
		builder.appendSql("update T_Sch_FDCScheduleTask set factualenddate=");
		builder.appendSql("case when fcomplete>=100 then ");
		builder.appendSql("	   (select max(t_sch_workamountentry.FCOMPLETEDATE) ");
		builder.appendSql("    from t_sch_workamountentry where t_sch_workamountentry.FWBSID=T_Sch_FDCScheduleTask.FWBSID) ");
		builder.appendSql("else null end ");
		builder.appendSql("where FIsLeaf=1 ");
		if(wbsIds != null && !wbsIds.isEmpty()){
			builder.appendParam("and FWBSId", wbsIds.toArray());
		}
		builder.appendSql(" and FScheduleID in (");
		builder.appendSql("    select sch.fid from T_SCH_FDCSchedule sch, T_SCH_ScheduleVerManager vm ");
		builder.appendSql("    where sch.fid=T_SCH_FDCScheduleTask.FScheduleId ");
		builder.appendSql("    and vm.fid=sch.fbaseVerid and vm.fislatestVer=1)");
		builder.executeUpdate();
		builder.clear();		

		modifyParent(ctx, wbsIds);	
	}
	
	/**
	 * 合同工程量录入
	 * @param ctx
	 * @param wbsIds
	 * @throws BOSException 
	 */
	public static void modifyByContract(Context ctx, Set wbsIds) throws BOSException{
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);		
		builder.appendSql("update t_sch_fdcscheduletask set fcomplete=");
		builder.appendSql("    (select sum(Fpercent) from T_FPM_ProjectFillBillEntry ent inner join T_FPM_ProjectFillBill head");
		builder.appendSql("    on head.fid=ent.fbillid where head.fstate=? and ent.ftaskid=T_SCH_FDCScheduleTask.fid)");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		builder.appendSql("where FIsLeaf=1  ");
		if(wbsIds != null && !wbsIds.isEmpty()){
			builder.appendParam("and FWBSId", wbsIds.toArray());
		}
		builder.appendSql(" and FScheduleID in (");
		builder.appendSql("    select sch.fid from T_SCH_FDCSchedule sch, T_SCH_ScheduleVerManager vm ");
		builder.appendSql("    where sch.fid=T_SCH_FDCScheduleTask.FScheduleId ");
		builder.appendSql("    and vm.fid=sch.fbaseVerid and vm.fislatestVer=1)");
		builder.executeUpdate();
		builder.clear();
		
		builder.appendSql("update T_Sch_FDCScheduleTask set factualenddate=");
		builder.appendSql("case when fcomplete>=100 then ");
		builder.appendSql("	   (select max(T_FPM_ProjectFillBillEntry.FCOMPLETEDATE) ");
		builder.appendSql("    from T_FPM_ProjectFillBillEntry where T_FPM_ProjectFillBillEntry.FTaskID=T_Sch_FDCScheduleTask.FID) ");
		builder.appendSql("else null end ");
		builder.appendSql("where FIsLeaf=1 ");
		if(wbsIds != null && !wbsIds.isEmpty()){
			builder.appendParam("and FWBSId", wbsIds.toArray());
		}
		builder.appendSql(" and FScheduleID in (");
		builder.appendSql("    select sch.fid from T_SCH_FDCSchedule sch, T_SCH_ScheduleVerManager vm ");
		builder.appendSql("    where sch.fid=T_SCH_FDCScheduleTask.FScheduleId ");
		builder.appendSql("    and vm.fid=sch.fbaseVerid and vm.fislatestVer=1)");
		builder.executeUpdate();
		builder.clear();		

		modifyParent(ctx, wbsIds);	
	}
	
	
	
}
