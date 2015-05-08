package com.kingdee.eas.fdc.schedule.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryCollection;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryFactory;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractAndTaskRelEntryControllerBean extends AbstractContractAndTaskRelEntryControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ContractAndTaskRelEntryControllerBean");

    /**
     * 数据结构：两个Map，一个放任务，一个放工程量填报单
     * taskMap Key:wbsId value:task
     * fillBillMap : Key:taskId  value 
     */
	protected Map _getTaskOtherData(Context ctx, Set wbsIds)
			throws BOSException, EASBizException {
		Map fillBillMap = new HashMap();
		Map taskMap = new HashMap();
//		Map desMap = new HashMap();
		Map planWorkLoadMap = new HashMap() ;
		Set taskIds = getTaskIdsByWBS(ctx, wbsIds);
		Map map = new HashMap();
		fillBillMap = getFillBillMaps(ctx, taskIds);
		map.put("fillBill", fillBillMap);
		taskMap = getTaskMap(ctx, wbsIds);
		map.put("task", taskMap);
//		desMap = getDesMap(ctx, taskIds);
//		map.put("description", desMap);
		planWorkLoadMap = _getPlanWorkLoad(ctx, taskIds);
		map.put("planWorkload", planWorkLoadMap);
		return map;
	}

	private Set getTaskIdsByWBS(Context ctx,Set wbsIds) throws BOSException{
		Set taskIds = new HashSet();
		if(wbsIds.size() > 0){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select tk.fid as TASKID from T_SCH_FDCScheduleTask tk inner join T_SCH_FDCWBS wbs on wbs.fid=tk.Fwbsid" +
			"	inner join T_SCH_FDCSchedule sch on tk.FScheduleID=sch.FID where sch.FIsLatestVer=? and ");
			builder.addParam(Boolean.TRUE);
			builder.appendParam("wbs.FID", wbsIds.toArray());
			IRowSet rs = builder.executeQuery();
			try {
				while(rs.next()){
					taskIds.add(rs.getString("TASKID"));
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		return taskIds;
	}
	
	private Map getDesMap(Context ctx, Set taskIds)throws BOSException {
		Map desMap = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		filter = new FilterInfo();
		sic = new SelectorItemCollection();
		filter.getFilterItems().add(new FilterItemInfo("task.id",taskIds,CompareType.INCLUDE));
		sic.add("id");
		sic.add("description");
		sic.add("task.id");
		sic.add("task.number");
		sic.add("task.name");
		view.setFilter(filter);
		view.setSelector(sic);
		ContractAndTaskRelEntryCollection col = 
			ContractAndTaskRelEntryFactory.getLocalInstance(ctx).getContractAndTaskRelEntryCollection(view);
		for(int i=0;i<col.size();i++){
			ContractAndTaskRelEntryInfo info = col.get(i);
			desMap.put(info.getTask().getId().toString(),info);
		}
		return desMap;
	}
	
	private Map getTaskMap(Context ctx, Set wbsIds)throws BOSException {
		Map taskMap = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		filter = new FilterInfo();
		sic = new SelectorItemCollection();
		filter.getFilterItems().add(new FilterItemInfo("wbs.id",wbsIds,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		sic.add("workLoad");
		sic.add("complete");
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("start");
		sic.add("end");
		sic.add("wbs.id");
		sic.add("wbs.name");
		sic.add("wbs.number");
		sic.add("wbs.longNumber");
		sic.add("wbs.curProject.id");
		sic.add("wbs.curProject.name");
		sic.add("wbs.curProject.number");
		view.setFilter(filter);
		view.setSelector(sic);
		FDCScheduleTaskCollection taskCol = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		for(int i = 0;i<taskCol.size();i++){
			FDCScheduleTaskInfo taskInfo = taskCol.get(i);
			taskMap.put(taskInfo.getWbs().getId().toString(), taskInfo);
		}
		return taskMap;
	}

	private Map getFillBillMaps(Context ctx, Set taskIds) throws BOSException {
		Map fillBillMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select fb.ftaskid as TASKID,sum(fb.fqty) as QTY,sum(fb.fpercent) as PERCENTS from " +
				"t_fpm_projectfillbillentry fb ");
		if(taskIds.size() >0){
			builder.appendSql("	where ");
			builder.appendParam("	fb.ftaskid", taskIds.toArray());
		}else{
			return fillBillMap;
		}
		builder.appendSql("	group by fb.ftaskid");
		IRowSet rowSet = builder.executeQuery();
		try {
			while(rowSet.next()){
				ArrayList sums = new ArrayList();
				sums.add(0, rowSet.getBigDecimal("QTY"));
				sums.add(1, rowSet.getBigDecimal("PERCENTS"));
				fillBillMap.put(rowSet.getString("TASKID"), sums);
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return fillBillMap;
	}

	protected Map _getFillBillMap(Context ctx, Set taskIds)	throws BOSException, EASBizException {
		return getFillBillMaps(ctx, taskIds);
	}

	protected Map _getPlanWorkLoad(Context ctx, Set taskIds)
			throws BOSException, EASBizException {
		Map map = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select task.FID as TASKID, workLoad.FPlanWorkload as PLANWORKLOAD from " +
				"	T_SCH_FDCScheduleTask task" +
				"	inner join T_SCH_FDCScheduleTaskExt taskExt on task.fwbsid=taskExt.FWBSID" +
				"	inner join T_SCH_TaskWorkloadLog workLoad on workLoad.FTaskExtID=taskExt.FID ");
		if(taskIds.size() > 0){
			builder.appendSql(" where");
			builder.appendParam("task.FID", taskIds.toArray());
		}
		IRowSet rowSet = builder.executeQuery();
		try {
			while(rowSet.next()){
				map.put(rowSet.getString("TASKID"), rowSet.getBigDecimal("PLANWORKLOAD"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return map;
	}
}