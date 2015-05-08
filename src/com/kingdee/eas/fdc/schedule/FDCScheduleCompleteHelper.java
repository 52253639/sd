/*
 * @(#)FDCScheduleCompleteHelper.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.schedule;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.util.DateTimeUtils;

public class FDCScheduleCompleteHelper {
	
	 /**
     * 
     * 描述：更新关联任务的完工工程量
     * @param ctx
     * @param scheduleInfo
     * @throws BOSException
     * 创建人：yuanjun_lan
     * 创建时间：2011-10-24
     */
    public static  void updateRelateTaskComplete(Context ctx,FDCScheduleTaskInfo scheduleInfo) throws BOSException{
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	/* modified by zhaoqin for 2014/08/19 */
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	builder.appendSql("update t_sch_fdcscheduletask set fcomplete = ?,"); 
    	builder.addParam(scheduleInfo.getComplete());
    	if (scheduleInfo.getIntendEndDate() != null) {
			builder.appendSql("FIntendEndDate = ?,");
			builder.addParam(scheduleInfo.getIntendEndDate());
		}
    	if(scheduleInfo.getWorkLoad()!=null && scheduleInfo.getWorkLoad().compareTo(FDCHelper.ZERO)>0){
    		builder.appendSql("fworkload = ? ,");
    		builder.addParam((scheduleInfo.getWorkLoad())== null ? FDCHelper.ZERO:scheduleInfo.getWorkLoad());
    	}
    	builder.appendSql(" FActualStartDate={ts'"
				+ sdf.format(scheduleInfo.getActualStartDate() == null ? scheduleInfo.getStart() : scheduleInfo.getActualStartDate())
				+ "'}");
    	if(scheduleInfo.getComplete() != null && scheduleInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED)==0 ){
    		//FActualEndDate
    		builder.appendSql(", FActualEndDate = {ts'"+sdf.format(scheduleInfo.getActualEndDate())+"'}");
    	}
    	builder.appendSql(" where fsrcid = ?");
    	builder.appendSql(" and fscheduleid  = ?");
    	builder.addParam(scheduleInfo.getSrcID().toString());
    	builder.addParam(scheduleInfo.getSchedule().getId().toString());
    	builder.execute();
    }
    
    /**
     * 
     * 描述：只更新对应任务的工程量和完工百比
     * @param ctx
     * @param scheduleTaskInfo
     * @throws BOSException
     * 创建人：yuanjun_lan
     * 创建时间：2011-11-11
     */
    public static void updateRelateTask(Context ctx,FDCScheduleTaskInfo scheduleTaskInfo) throws BOSException{
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	/**
		 * 删除汇报时，任务中的完工百分比更新规则改变：
		 * 之前是按增量来反写，现在删除汇报后，再取已有汇报中最大的完工百分比更新到任务中去
		 * 若删除全部汇报，更新为0 Added by Owen_wen 2013-12-10
		 */ 
    	builder.appendSql("update t_sch_fdcscheduletask set fcomplete = ");
		builder
				.appendSql("(select isNull(max(FCompletePrecent), 0) from T_SCH_SchTaskProgressReport where FRelateTaskID = t_sch_fdcscheduletask.fid)");
		builder.appendSql(" where t_sch_fdcscheduletask.fid = '" + scheduleTaskInfo.getId() + "'");
		builder.executeUpdate();

		builder.clear();
    	builder.appendSql("update t_sch_fdcscheduletask set "); 
    	if(scheduleTaskInfo.getWorkLoad().compareTo(FDCHelper.ZERO)>= 0){
    		builder.appendSql(" fworkload = ? ");
    		builder.addParam((scheduleTaskInfo.getWorkLoad())== null ? FDCHelper.ZERO:scheduleTaskInfo.getWorkLoad());
    	}
    	/**当完工百分比小于100%时，删除当前任务上的实际完成日期和实际开始日期 */
    	if(scheduleTaskInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED)<0){
    		builder.appendSql(" , factualEnddate = null ");
    	}
    	if(scheduleTaskInfo.getComplete().compareTo(FDCHelper.ZERO) == 0){
    		builder.appendSql(", factualstartdate = null ");
    	}
    	builder.appendSql(" where fsrcid = ?");
    	builder.appendSql(" and fscheduleid  = ?");
    	builder.addParam(scheduleTaskInfo.getSrcID().toString());
    	builder.addParam(scheduleTaskInfo.getSchedule().getId().toString());
    	builder.execute();
    }
    
    /**
     * 
     * 描述：更新所有上级任务的完成比率
     *       计算公式为：
     *       假设： A 有 A1（工期T 10,完成比率P：100）和A2（工期T 20天，完成比率P：20）两个子任务，则A计算公式 为(p*10+p*20)/10+20
     * 创建人：yuanjun_lan
     * 创建时间：2011-10-24
     * @throws BOSException 
     * @throws EASBizException 
     */
    public static void  updateAllTaskCompleteRate(Context ctx,FDCScheduleTaskInfo task) throws BOSException, EASBizException{
    	/**获取父任务的所有子类*/
    	if(task.getParent() == null){
    		return ;
    	}
    	task = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskInfo(new ObjectUuidPK(task.getParent().getId()));
    	Date parentTaskCompelteDate = null;
    	Date parentTaskStartDate = null;
    	BigDecimal realCompleteRate = FDCHelper.ZERO;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("parent",task.getId().toString()));
    	view.setFilter(filter);
    	FDCScheduleTaskCollection cols = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
    	/**计算当前节点的完工比率 */
    	FDCScheduleTaskInfo cTaskInfo = null;
    	BigDecimal completePercent = FDCHelper.ZERO;
    	BigDecimal totalTimes = FDCHelper.ZERO;
    	BigDecimal workLoad = FDCHelper.ZERO;
    	for(Iterator it = cols.iterator();it.hasNext();){
    		cTaskInfo = (FDCScheduleTaskInfo) it.next();
            if(parentTaskCompelteDate == null && cTaskInfo.getActualEndDate() != null){
            	parentTaskCompelteDate = cTaskInfo.getActualEndDate();
    		}
    		
    		
            if(parentTaskStartDate == null && cTaskInfo.getActualStartDate() != null){
            	parentTaskStartDate = cTaskInfo.getActualStartDate();
            }
            
            //记录所有子任务中最后完成时间来改写父任务完成时间
    		totalTimes = FDCHelper.add(totalTimes, cTaskInfo.getEffectTimes());
    		if(parentTaskStartDate != null 
    				&& cTaskInfo.getActualStartDate() != null 
    				&& DateTimeUtils.dateDiff(cTaskInfo.getActualStartDate(), parentTaskStartDate)>0)
    		    parentTaskStartDate = cTaskInfo.getActualStartDate();
    		if(cTaskInfo.getComplete()!=null   
    				      && cTaskInfo.getEffectTimes().compareTo(FDCHelper.ZERO)>0){
    			completePercent = FDCHelper.add(completePercent, FDCHelper.multiply(cTaskInfo.getComplete(), cTaskInfo.getEffectTimes()));
    			if(cTaskInfo.getActualEndDate() != null
    					&& cTaskInfo.getActualStartDate() != null 
    					&& DateTimeUtils.dateDiff(cTaskInfo.getActualEndDate(), parentTaskCompelteDate) < 0)
    		    	   parentTaskCompelteDate = cTaskInfo.getActualEndDate();
    		}
    		
    		if(cTaskInfo.getWorkLoad() != null){
    			workLoad = FDCHelper.add(workLoad, cTaskInfo.getWorkLoad());
    		}
    	}
    	/**更新最新完工百分比到任务上,如果全部任务都已完成，则应反写任务的实际开始日期、实际完工日期**/
    	realCompleteRate =FDCHelper.divide(completePercent, totalTimes);
    	if(realCompleteRate == null){
    		realCompleteRate = FDCHelper.ZERO;
    	}
    	if(workLoad == null){
    		workLoad = FDCHelper.ZERO;
    	}
    		
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("update t_sch_fdcscheduletask set fcomplete = ?,fworkload = ?");
    	if(parentTaskStartDate != null){
    		builder.appendSql(",Factualstartdate = {ts' "+sdf.format(parentTaskStartDate)+"'}");
    	}else{
    		builder.appendSql(",Factualstartdate = null");
    	}
    	builder.addParam(realCompleteRate);
    	builder.addParam(workLoad);
    	if(realCompleteRate.compareTo(FDCHelper.ONE_HUNDRED)==0){//任务全部完工
    		builder.appendSql(",factualenddate = {ts' "+sdf.format(parentTaskCompelteDate)+"'}");
    	} else {
			builder.appendSql(",factualenddate = null");
    	}
    	builder.appendSql(" where fsrcid = ?");
    	builder.appendSql(" and fscheduleid = ?");
    	builder.addParam(task.getSrcID().toString());
    	builder.addParam(task.getSchedule().getId().toString());
    	builder.execute();
    	/**循环更新所有父类节点的完工比率 */
    	if(task.getParent() != null){
    		updateAllTaskCompleteRate(ctx, task);
    	}
    	
    }

}
