package com.kingdee.eas.fdc.schedule.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskSynchronize;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.eas.fdc.schedule.ScheduleFacadeFactory;
import com.kingdee.eas.fdc.schedule.WorkAmountBillFactory;
import com.kingdee.eas.fdc.schedule.WorkAmountBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

public class WorkAmountBillControllerBean extends AbstractWorkAmountBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.WorkAmountBillControllerBean");

	protected Map _initTaskInfo(Context ctx, String projectId)
			throws BOSException {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		if(projectId == null || projectId.length() == 0){
			return null;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		BigDecimal [] data = null;
		builder.appendSql("select ftaskid,isnull(sum(fconfirmamount),0),isnull(sum(fconfirmpercent),0) from t_sch_workamountentry where fparentid in(select fid from t_sch_workamountbill where  fprojectid = ?) group by ftaskid");
		//builder.appendSql("select ftaskid,isnull(sum(fconfirmamount),0),isnull(sum(fconfirmpercent),0) from t_sch_workamountentry where fparentid in(select fid from t_sch_workamountbill where fstate in('2SUBMITTED','4AUDITTED') and fprojectid = ?) group by ftaskid");
		builder.addParam(projectId);
		RowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				data = new BigDecimal[]{rs.getBigDecimal(2),rs.getBigDecimal(3)};
				map.put(rs.getString(1), data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		WorkAmountBillInfo info = WorkAmountBillFactory.getLocalInstance(ctx).getWorkAmountBillInfo(new ObjectUuidPK(billId));
		if(info.getState() == null || info.getState().equals(FDCBillStateEnum.SAVED )|| info.getState().equals(FDCBillStateEnum.AUDITTED)){
			throw new ScheduleException(ScheduleException.CANNTOPERATION);
		}else{
			super._audit(ctx, billId);
		}
		synchronous2Task(ctx, billId);
	}

	private void synchronous2Task(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		Set taskIds = new HashSet();
		Set wbsIds = new HashSet();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select distinct FTaskID,FWBSID from T_SCH_WorkAmountEntry where fparentid=?");
		builder.addParam(billId.toString());
		IRowSet rowSet = builder.executeQuery();
		try {
			while(rowSet.next()){
				taskIds.add(rowSet.getString("FTaskID"));
				wbsIds.add(rowSet.getString("FWBSID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		FDCScheduleTaskSynchronize.modifyByWorkAmount(ctx, wbsIds);
	}
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		WorkAmountBillInfo info = WorkAmountBillFactory.getLocalInstance(ctx).getWorkAmountBillInfo(new ObjectUuidPK(billId));
		if(!info.getState().equals(FDCBillStateEnum.AUDITTED)){
			throw new ScheduleException(ScheduleException.CANNTOPERATION);
		}
		super._unAudit(ctx, billId);
		synchronous2Task(ctx, billId);
	}
	
	protected Map _getSelectedTask(Context ctx, Map param) throws BOSException {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		Date sDate = null;
		Date eDate = null;
		String projectId = null;
		String adminDept = null;
		if(param.get("sDate") != null){
			sDate = (Date) param.get("sDate");
			sDate = DateTimeUtils.truncateDate(sDate);
		}
		
		if(param.get("eDate") != null){
			eDate = (Date) param.get("eDate");
			eDate.setHours(23);
			eDate.setSeconds(59);
			eDate.setMinutes(59);
		}
		
		if(param.get("projectId") != null){
			projectId = (String) param.get("projectId");
		}
		
		if(param.get("adminDept") != null){
			adminDept = (String) param.get("adminDept");
		}
		Object o = param.get("creator");
		Set idSet = new HashSet();
		FDCSQLBuilder b = new FDCSQLBuilder(ctx);
		b.appendSql("select task.fid from t_sch_fdcscheduletask task join t_sch_fdcschedule sch on task.fscheduleid = sch.fid 	left join  t_org_baseunit org on task.fadmindeptid = org.fid left join t_sch_fdcwbs w on task.fwbsid = w.fid");
		b.appendSql(" where sch.fstate ='6EXETING' and sch.fislatestver =1  and sch.fprojectid = ?   ");
		b.appendSql(" and w.fisleaf = 1 and (Fcomplete is null or fcomplete<100)  and task.fadmindeptid = ?");
		b.appendSql(" and task.fstart <= ?");
		b.appendSql(" and task.fend >= ?");
		b.addParam(projectId);
		b.addParam(adminDept);
		b.addParam(eDate);
		b.addParam(sDate);
		if (o != null) {
			b.appendSql(" and task.FAdminPersonID=?  ");
			b.addParam(o.toString());
		}
		RowSet rs = b.executeQuery();
		try {
			while (rs.next()){
				idSet.add(rs.getString("fid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.INCLUDE));
		evi.setFilter(filter);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("adminPerson.name"));
		selector.add(new SelectorItemInfo("adminPerson.id"));
		evi.setSelector(selector);
		
		FDCScheduleTaskCollection cols = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(evi);
		
		map.put("taskCols",cols);
		
		return map;
	}
	
	protected void trimName(FDCBillInfo billInfo) {
		// TODO Auto-generated method stub
//		super.trimName(billInfo);
	}
}