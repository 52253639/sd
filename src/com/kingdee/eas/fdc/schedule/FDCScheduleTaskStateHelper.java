/*
 * @(#)FDCScheduleTaskStateHelper.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.schedule;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcEntityViewUtil;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectValueUtil;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;
/***
 * 
 * 描述:由于在多处需要进行任务状态更新，所以抽取此方法。
 * @author yuanjun_lan  date:2011-10-21
 * @version EAS6.1
 * 
 */
public class FDCScheduleTaskStateHelper {
	
	private static Logger logger = Logger.getLogger(FDCScheduleTaskStateHelper.class);
	/**
	 * 
	 * 描述：获取相应任务状态
	 * @param ctx
	 * @param schedule
	 * @throws BOSException
	 * @throws EASBizException
	 * 创建人：yuanjun_lan
	 * 创建时间：2011-10-21
	 * @throws SQLException 
	 * 
	 */
	public static void putState(Context ctx, FDCScheduleInfo schedule) throws BOSException, EASBizException, SQLException {
		ICurProject curProjectInterface = null;
		if(ctx == null){
			curProjectInterface = CurProjectFactory.getRemoteInstance();
		}else{
			curProjectInterface = CurProjectFactory.getLocalInstance(ctx);
		}
		CurProjectInfo project = curProjectInterface.getCurProjectInfo(new ObjectUuidPK(schedule.getProject().getId()));

		if (schedule.getTaskEntrys() != null) {
			FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
			putState(ctx, taskEntrys, project);

		}
	}
	/**
	 * 更新任务状态 
	 *    1 启用了需要对任务进行评价才算通过这个参数时，只改写明细项的任务，否则则会部更新
	 * @param ctx
	 * @param cols
	 * @param projectInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException 
	 */
	public static void putState(Context ctx, FDCScheduleTaskCollection cols,CurProjectInfo projectInfo) throws BOSException, EASBizException, SQLException {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map putStateMap = FdcManagementUtil.recodeExeTimeBefore(FDCScheduleTaskStateHelper.class, "putState");
		// ////////////////////////////////////////////////////////////////////////
		
		if(cols.isEmpty()){
			return ;
		}
		ICurProject curProjectInterface = null;
		IFDCSchedule fdcScheduleInterface = null;
		if(ctx == null){
			curProjectInterface = CurProjectFactory.getRemoteInstance();
			fdcScheduleInterface = FDCScheduleFactory.getRemoteInstance();
		}else{
			curProjectInterface = CurProjectFactory.getLocalInstance(ctx);
			fdcScheduleInterface = FDCScheduleFactory.getLocalInstance(ctx);
		}
		CurProjectInfo project = curProjectInterface.getCurProjectInfo(new ObjectUuidPK(projectInfo.getId()));
		
		String passMustEvaluation = ParamManager.getParamValue(ctx, new ObjectUuidPK(project.getCostCenter().getId()), "FDCSH011");
	
	    boolean isNeedEvalutaion = false;
	    FDCScheduleInfo sch = null;
	    if(cols.size()>0 && cols.get(0)!=null){
	    	sch = fdcScheduleInterface.getFDCScheduleInfo(new ObjectUuidPK(cols.get(0).getSchedule().getId()));
	    }
	    isNeedEvalutaion = isMustEvaluation(passMustEvaluation,sch);
	    
	    //1需要评价才算通过时，只对明细任务进行评价
	    FDCScheduleTaskCollection taskEntrys = cols;
		FDCScheduleTaskInfo scheduleTaskInfo = null;
		
	    if(isNeedEvalutaion){
	    	Set idSet = isEvaluationed(ctx, taskEntrys);
	    	for (int i = 0; i < taskEntrys.size(); i++) {
				scheduleTaskInfo = taskEntrys.get(i);
				if(!scheduleTaskInfo.isIsLeaf()){
					continue;
				}
				if (scheduleTaskInfo.getComplete() != null
						&& (scheduleTaskInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED) == 0)) {
					// 提前完成
					if (scheduleTaskInfo.getActualEndDate() != null 
							&& scheduleTaskInfo.getEnd() != null 
							&& DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getActualEndDate()), scheduleTaskInfo.getEnd()) >=0) {
						if (idSet.contains(scheduleTaskInfo.getSrcID().toString())) {
							scheduleTaskInfo.put("state", "0");
						} else {
							scheduleTaskInfo.put("state", "2");
						}
					}else if (scheduleTaskInfo.getActualEndDate() != null && 
							scheduleTaskInfo.getEnd() != null
							&& DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getActualEndDate()), scheduleTaskInfo.getEnd()) < 0) {// 延后完成
						if (idSet.contains(scheduleTaskInfo.getSrcID().toString())) {
							scheduleTaskInfo.put("state", "1");
						} else {
							scheduleTaskInfo.put("state", "2");
						}
					}
				} else if (scheduleTaskInfo.getEnd() != null 
						    &&DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getEnd()), new Date()) > 0) {
					// 延时未完成的任务
					scheduleTaskInfo.put("state", "3");
				} else{
					scheduleTaskInfo.put("state","-1");
				}
				
	    	}
	    	
	    	
	    	
	    }else{
	    	
	    	putStateByNotNeedEvaluation(taskEntrys);
	    }
	    
	        
	        /**
	         * 只要当前进度是需要评价的，那么我们就只需要去改写所有父级任务的状态
	         * 父级任务是不能被评价的
	         * */
	        if(isNeedEvalutaion){
	        	Map resultMap = getMaxChildrenState(cols,ctx);
	        	for (int i = 0; i < taskEntrys.size(); i++) {
	        		scheduleTaskInfo = taskEntrys.get(i);
	        		if(!scheduleTaskInfo.isIsLeaf()){
	        			if (scheduleTaskInfo.getComplete() != null 
	    						&& (scheduleTaskInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED) == 0)) {
	    					// 提前完成
	    					if (scheduleTaskInfo.getActualEndDate() != null 
	    							&& scheduleTaskInfo.getEnd() != null 
	    							&& DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getActualEndDate()), scheduleTaskInfo.getEnd()) >=0 ) {
	    						
	    						if(resultMap.get(scheduleTaskInfo.getId().toString()) != null && Integer.parseInt(resultMap.get(scheduleTaskInfo.getId().toString())+"") == 2 
	    	        					&& scheduleTaskInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED) == 0){
	    	        				scheduleTaskInfo.put("state", "2");
	    	        			}else{
	    	        				scheduleTaskInfo.put("state", "0");
	    	        			}
	    						
	    					}else if (scheduleTaskInfo.getActualEndDate() != null 
	    							&& scheduleTaskInfo.getEnd() != null 
	    							&&DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getActualEndDate()),scheduleTaskInfo.getEnd()) <0
	    							) {// 延后完成
	    						if(resultMap.get(scheduleTaskInfo.getId().toString()) != null && Integer.parseInt(resultMap.get(scheduleTaskInfo.getId().toString())+"") == 2 
	    	        					&& scheduleTaskInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED) == 0){
	    	        				scheduleTaskInfo.put("state", "2");
	    	        			}else{
	    	        				scheduleTaskInfo.put("state", "1");
	    	        			}
	    					}
	    				} else if (scheduleTaskInfo.getEnd() != null 
	    						&& DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getEnd()), DateTimeUtils.truncateDate(new Date())) > 0) {
	    					// 延时未完成的任务
	    					scheduleTaskInfo.put("state", "3");
	    				} 
	        		}
	        		
	        		
	        	}
	        	
	        }

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(FDCScheduleTaskStateHelper.class, "putState", putStateMap);
		// ////////////////////////////////////////////////////////////////////////
	}
	
	/**
	 * 系统参数FDCSCH011不启用时，直接根据完工百分比和实际完工工期来判断
	 * 描述：
	 * @param taskEntrys
	 * 创建人：yuanjun_lan
	 * 创建时间：2011-11-2
	 */
	private static void putStateByNotNeedEvaluation(FDCScheduleTaskCollection taskEntrys) {
		FDCScheduleTaskInfo scheduleTaskInfo;
		for (int i = 0; i < taskEntrys.size(); i++) {
			scheduleTaskInfo = taskEntrys.get(i);
			if (scheduleTaskInfo.getComplete() != null 
					&& (scheduleTaskInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED) == 0)) {
				// 提前完成
				if (scheduleTaskInfo.getActualEndDate() != null 
						&& scheduleTaskInfo.getEnd() != null 
						&& DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getActualEndDate()), scheduleTaskInfo.getEnd()) >=0 ) {
					scheduleTaskInfo.put("state", "0");
				}else if (scheduleTaskInfo.getActualEndDate() != null 
						&& scheduleTaskInfo.getEnd() != null 
						&& DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getActualEndDate()), scheduleTaskInfo.getEnd()) < 0
						) {// 延后完成
					scheduleTaskInfo.put("state", "1");
				}
			} else if (scheduleTaskInfo.getEnd() != null
					    &&DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getEnd()), DateTimeUtils.truncateDate(new Date())) > 0) {
				// 延时未完成的任务
				scheduleTaskInfo.put("state", "3");
			} 
		}
	}
	
	
	
	


	
	
	/**
	 * 
	 * 描述：取出已经对当前任务进行过评价的集合
	 * @param ctx
	 * @param entrys
	 * @return
	 * @throws BOSException
	 * 创建人：yuanjun_lan
	 * 创建时间：2011-10-21
	 * 只有进度评价才影响任务状态
	 * select bill.fsrcrelatetask
     *   from t_sch_taskevaluationbill bill left outer join T_SCH_TaskEvaluation eva on  bill.fevaluationresult = eva.fid
     *   where bill.fsrcrelatetask ='PeiCVC3HTH6QEhTEBWoxtpDM8is=' and isnull(eva.FEVALUATIONPASS,0) = 1 and eva.fevaluationtype = '1SCHEDULE'
	 * @throws SQLException 
	 */
	public static Set isEvaluationed(Context ctx, FDCScheduleTaskCollection entrys) throws BOSException, SQLException {
		ITaskEvaluationBill taskEvaluationBill = null;
		if(ctx == null){
			taskEvaluationBill = TaskEvaluationBillFactory.getRemoteInstance();
		}else{
			taskEvaluationBill = TaskEvaluationBillFactory.getLocalInstance(ctx);
		}
		
		String [] param = new String[entrys.size()];
		Set idSet = new HashSet();
		
		Map evaMap = new HashMap();
		//先注释，可能以后还会用这段代码
//		for(int i=0;i<entrys.size();i++){
//			param[i] = entrys.get(i).getSrcID().toString();
//		}
		for(int i=0;i<entrys.size();i++){
			idSet.add(entrys.get(i).getSrcID().toString());
		}
//		
//		
//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		builder.appendSql("select bill.fsrcrelatetask ");
//		builder.appendSql("from t_sch_taskevaluationbill bill left outer join T_SCH_TaskEvaluation eva on  bill.fevaluationresult = eva.fid  ");
//		builder.appendSql("where bill.fsrcrelatetask in (");
//		builder.appendParam(param);
//		builder.appendSql(" )and isnull(eva.FEVALUATIONPASS,0) = 1 and eva.fevaluationtype = '1SCHEDULE' order by bill.flastupdatetime desc");
////		builder.appendSql(" )and eva.fevaluationtype = '1SCHEDULE' order by bill.flastupdatetime desc");
//		
//		IRowSet rs = builder.executeQuery();
//		logger.info(builder.getTestSql());
//		while(rs.next()){
//			idSet.add(rs.getString(1));
//		}
		
		
		//有可能用户在第一次评价某条任务通过后，又来进行一次评价评价为不通过，这时候怎么处理？
		//现行做法是取出所有相关的评价，比较他们的评价时间，
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("evaluationResult.id");
		view.getSelector().add("evaluationResult.name");
		view.getSelector().add("evaluationResult.number");
		view.getSelector().add("evaluationResult.evaluationPass");
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo sorterInfo = new SorterItemInfo("lastupdateTime");
		sorterInfo.setSortType(SortType.ASCEND);
		sorter.add(sorterInfo);
		view.setSorter(sorter);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcRelateTask",idSet,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("evaluationType",TaskEvaluationTypeEnum.SCHEDULE));
		view.setFilter(filter);
		TaskEvaluationBillCollection cols = taskEvaluationBill.getTaskEvaluationBillCollection(view);
		TaskEvaluationBillInfo evaInfo = null;
		TaskEvaluationBillInfo otherInfo = null;
		for(Iterator it = cols.iterator();it.hasNext();){
			evaInfo = (TaskEvaluationBillInfo) it.next();
			if(evaInfo.getEvaluationResult().isEvaluationPass()){
				if(evaMap.get(evaInfo.getSrcRelateTask())== null){
					evaMap.put(evaInfo.getSrcRelateTask(), evaInfo);
				}
			}else{
				if(evaMap.get(evaInfo.getSrcRelateTask())!= null){
					otherInfo = (TaskEvaluationBillInfo) evaMap.get(evaInfo.getSrcRelateTask());
					if(DateTimeUtils.dateDiff(evaInfo.getLastUpdateTime(), otherInfo.getLastUpdateTime())<0){
						evaMap.remove(evaInfo.getSrcRelateTask());
//						evaMap.put(evaInfo.getSrcRelateTask(),evaInfo);
					}
					
				}
			}
			
		}
		
		evaInfo = null;
		otherInfo = null;
//	    return idSet;
		return evaMap.keySet();
	}
	
	
	public static Map<String, FDCScheduleTaskInfo> getNewstTasks(Context ctx, FDCScheduleTaskCollection entrys) throws BOSException, SQLException {
		Set idSet = new HashSet();
		String scheduleId = null;
		for (int i = 0; i < entrys.size(); i++) {
			if (StringUtils.isEmpty(scheduleId))
			scheduleId = entrys.get(i).getSchedule().getId().toString();
			idSet.add(entrys.get(i).getSrcID().toString());
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcId", idSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("schedule.islatestver", Boolean.TRUE));
		// if (!StringUtils.isEmpty(scheduleId))
			// filter.getFilterItems().add(new FilterItemInfo("schedule.id",
			// scheduleId));
		view.setFilter(filter);
		if (ctx != null) {
			entrys = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		} else {
			entrys = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		}
		
		Map<String, FDCScheduleTaskInfo> ret = new HashMap<String, FDCScheduleTaskInfo>();
		FDCScheduleTaskInfo task = null;
		for (int i = 0; i < entrys.size(); i++) {
			task = entrys.get(i);
			ret.put(task.getSrcID(), task);
		}
		
// ITaskEvaluationBill taskEvaluationBill = null;
		// if (ctx == null) {
		// taskEvaluationBill = TaskEvaluationBillFactory.getRemoteInstance();
		// } else {
		// taskEvaluationBill = TaskEvaluationBillFactory.getLocalInstance(ctx);
		// }
		//
		// String[] param = new String[entrys.size()];
		//
		// Map evaMap = new HashMap();
		// for (int i = 0; i < entrys.size(); i++) {
		// idSet.add(entrys.get(i).getSrcID().toString());
		// }
		//
		// // 有可能用户在第一次评价某条任务通过后，又来进行一次评价评价为不通过，这时候怎么处理？
		// // 现行做法是取出所有相关的评价，比较他们的评价时间，
		// view = new EntityViewInfo();
		// view.getSelector().add("*");
		// view.getSelector().add("evaluationResult.id");
		// view.getSelector().add("evaluationResult.name");
		// view.getSelector().add("evaluationResult.number");
		// view.getSelector().add("evaluationResult.evaluationPass");
		// SorterItemCollection sorter = new SorterItemCollection();
		// SorterItemInfo sorterInfo = new SorterItemInfo("lastupdateTime");
		// sorterInfo.setSortType(SortType.ASCEND);
		// sorter.add(sorterInfo);
		// view.setSorter(sorter);
		// filter = new FilterInfo();
		// filter.getFilterItems().add(new FilterItemInfo("srcRelateTask",
		// idSet, CompareType.INCLUDE));
		// filter.getFilterItems().add(new FilterItemInfo("evaluationType",
		// TaskEvaluationTypeEnum.SCHEDULE));
		// view.setFilter(filter);
		// TaskEvaluationBillCollection cols =
		// taskEvaluationBill.getTaskEvaluationBillCollection(view);
		// TaskEvaluationBillInfo evaInfo = null;
		// TaskEvaluationBillInfo otherInfo = null;
		// for (Iterator it = cols.iterator(); it.hasNext();) {
		// evaInfo = (TaskEvaluationBillInfo) it.next();
		// if (evaInfo.getEvaluationResult().isEvaluationPass()) {
		// if (evaMap.get(evaInfo.getSrcRelateTask()) == null) {
		// evaMap.put(evaInfo.getSrcRelateTask(), evaInfo);
		// }
		// } else {
		// if (evaMap.get(evaInfo.getSrcRelateTask()) != null) {
		// otherInfo = (TaskEvaluationBillInfo)
		// evaMap.get(evaInfo.getSrcRelateTask());
		// if (DateTimeUtils.dateDiff(evaInfo.getLastUpdateTime(),
		// otherInfo.getLastUpdateTime()) < 0) {
		// evaMap.remove(evaInfo.getSrcRelateTask());
		// }
		//
		// }
		// }
		//
		// }
		//
		// evaInfo = null;
		// otherInfo = null;
		return ret;
	}


	/**
	 * 
	 * 描述：根据系统参数判断当前任务是不是需要进行任务确认
	 * @param passStatage
	 * @param schedule
	 * @return
	 * 创建人：yuanjun_lan
	 * 创建时间：2011-10-21
	 */
	public static boolean isMustEvaluation(String passStatage,FDCScheduleInfo schedule){
		//不控制#控制主项任务#控制专项任务#控制主项和专项任务
		if("3".equals(passStatage)){
		   return true;	
		}else if("1".equals(passStatage)){
			if(schedule.getProjectSpecial() != null){
				return false;
			}else {
				return true;
			}
			
		}else if("2".equals(passStatage)){
			if(schedule.getProjectSpecial() != null){
				return true;
			}else {
				return false;
			}
			
		}
		return false;
	}
	
	/**
	 * 获取当前节点的所有兄弟节点，对其进行工程量和状重算
	 * @param resultMap
	 * @param taskEntrys
	 * @param task
	 * @param fdcScheduleTaskInterface
	 * @throws BOSException
	 * @deprecated
	 * @See setParentState(FDCScheduleTaskCollection taskEntrys,FDCScheduleTaskInfo taskInfo)
	 */
	private static void setParentTaskState(Map resultMap,FDCScheduleTaskCollection taskEntrys,FDCScheduleTaskInfo task,IFDCScheduleTask fdcScheduleTaskInterface) throws BOSException{
		if(task.getParent() == null){
			return;
		}
		task = task.getParent();
		int stateNumber =-1;
		if(task.get("state") != null){
			stateNumber =Integer.parseInt(task.get("state")+"");
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent", task.getId().toString()));
		view.setFilter(filter);
		FDCScheduleTaskCollection cols = fdcScheduleTaskInterface.getFDCScheduleTaskCollection(view);
		FDCScheduleTaskInfo cTaskInfo = null;
		FDCScheduleTaskInfo tempTask = null;
		//记录下当前任务的所有子任务
		Set idSet = new HashSet();
		for (Iterator it = cols.iterator(); it.hasNext();) {
			tempTask = (FDCScheduleTaskInfo) it.next();
            idSet.add(tempTask.getId().toString());
		}
		
		for(Iterator it = taskEntrys.iterator(); it.hasNext();){
			cTaskInfo = (FDCScheduleTaskInfo) it.next();
			if(idSet.contains(cTaskInfo.getId().toString()) && cTaskInfo.get("state") != null){
				if(Integer.parseInt(cTaskInfo.get("state")+"")>stateNumber){
				     task.put("state", cTaskInfo.get("state"));
				     stateNumber = Integer.parseInt(cTaskInfo.get("state")+"");
				     resultMap.put(task.getId().toString(), task.get("state"));
				}
			}
		}
		task.put("state", stateNumber+"");

		if (task.getParent() != null) {
			setParentTaskState(resultMap,taskEntrys,task.getParent(), fdcScheduleTaskInterface);
		}
	}
	
	public static void setParentState(FDCScheduleTaskCollection taskEntrys,FDCScheduleTaskInfo taskInfo){
		if(taskInfo.getParent() == null){
			return ;
		}
		int stateNumber = -1;
		String parentId = taskInfo.getParent().getId().toString();
		FDCScheduleTaskInfo parentTask = null;
		/**获取所有兄弟节点*/
		Set sbilingSet = getAllSblibing(taskInfo, taskEntrys);
		FDCScheduleTaskInfo tempTask = null;
		for(Iterator it = taskEntrys.iterator();it.hasNext();){
			tempTask = (FDCScheduleTaskInfo) it.next();
			if(tempTask.getId().toString().equals(parentId)){
				parentTask = tempTask;
			}
			if(sbilingSet.contains(tempTask.getId().toString())){
				if(tempTask.get("state")!= null && Integer.parseInt(tempTask.get("state")+"")>stateNumber){
					stateNumber = Integer.parseInt(tempTask.get("state")+"");
				}
			}
			
		}
		
		if(parentTask != null){
			if(parentTask.getComplete()== null){
				parentTask.setComplete(FDCHelper.ZERO);
			}
			if(parentTask.getComplete().compareTo(FDCHelper.ONE_HUNDRED)==0 ){
				parentTask.put("state", stateNumber+"");
			}else if(parentTask.getComplete().compareTo(FDCHelper.ONE_HUNDRED)<0 && stateNumber > 2){
				parentTask.put("state", stateNumber+"");
			}
		}
		    
		if(taskInfo.getParent() != null){
			setParentState(taskEntrys,taskInfo.getParent());
		}
	}
	
	
	
	public static Set getAllSblibing(FDCScheduleTaskInfo taskInfo,FDCScheduleTaskCollection cols){
		Set slibingSet = new HashSet();
		int level = taskInfo.getLevel();
		BOSUuid parentId = taskInfo.getParent().getId();
		FDCScheduleTaskInfo tempTask = null;
		for(Iterator it = cols.iterator();it.hasNext();){
			tempTask = (FDCScheduleTaskInfo) it.next();
			if(tempTask.getLevel() == level && tempTask.getParent().getId().equals(parentId)){
				slibingSet.add(tempTask.getId().toString());
			}
		}		
		return slibingSet;
		
	}
	
	/**
	 * 取出当前任务列表中父任务所有的子任务的状态最大值
	 * @param list
	 * @return
	 */
	public static Map getMaxChildrenState(FDCScheduleTaskCollection list,Context ctx){
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map getMaxChildrenStateMap = FdcManagementUtil.recodeExeTimeBefore(FDCScheduleTaskStateHelper.class,
				"getMaxChildrenState");
		// ////////////////////////////////////////////////////////////////////////
		
		Map resultMap = new HashMap();
		Object [] colsArray =  list.toArray();
		Arrays.sort(colsArray,new Comparator(){

			public int compare(Object o1, Object o2) {
				if(!(o2 instanceof FDCScheduleTaskInfo)){
					return -1;
				}
				if(o1 == o2){
					return 0;
				}
				FDCScheduleTaskInfo info ,otherInfo;
				if(o1 != null && o2 != null){
					info = (FDCScheduleTaskInfo) o1;
					otherInfo = (FDCScheduleTaskInfo) o2;
					return (info.getLongNumber().compareTo(otherInfo.getLongNumber()))*-1;
				}
				return 0;
			}});

		// ////////////////////////////////////////////////////////////////////////
		
		// 进度性能优化： 性能瓶颈 exeTime: 3.363 S by skyiter_wang 2014-06-11
		// 进度性能优化: 此处是可以优化的 by skyiter_wang 2014-06-11

		// ////////////////////////////////////////////////////////////////////////

		Map allScheduleTaskIdMap = new LinkedHashMap();

		// 优化方案：循环RPC--->批量取数
		FDCScheduleTaskInfo firstScheduleTaskInfo = (FDCScheduleTaskInfo) colsArray[0];
		FDCScheduleInfo scheduleInfo = firstScheduleTaskInfo.getSchedule();
		String scheduleId = (null != scheduleInfo) ? scheduleInfo.getId() + "" : null;

		EntityViewInfo viewInfo = new EntityViewInfo();
		SelectorItemCollection selectors = FdcEntityViewUtil.getSelector(new String[] { "id", "name", "state",
				"parent.id", "parent.name", "parent.state" });
		viewInfo.setSelector(selectors);
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("schedule.id", scheduleId);
		viewInfo.setFilter(filterInfo);

		FDCScheduleTaskCollection scheduleTaskCollection = null;
		IFDCScheduleTask fDCScheduleTask = null;
		try {
			if (null != ctx) {
				fDCScheduleTask = FDCScheduleTaskFactory.getLocalInstance(ctx);
			} else {
				fDCScheduleTask = FDCScheduleTaskFactory.getRemoteInstance();
			}

			// 从数据库中取出最新的数据
			scheduleTaskCollection = fDCScheduleTask.getFDCScheduleTaskCollection(viewInfo);
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			logger.error(e1);
		}

		// 优化方案：缓存Map
		Map scheduleTaskIdMap = FdcObjectCollectionUtil.parseUniqueIdMap(scheduleTaskCollection);
		allScheduleTaskIdMap.putAll(scheduleTaskIdMap);
		
		// ////////////////////////////////////////////////////////////////////////

		Set tempIdSet = new HashSet();
		FDCScheduleTaskInfo tempTask = null;
		for (int i = 0; i < colsArray.length; i++) {
			tempTask = (FDCScheduleTaskInfo) colsArray[i];
			tempIdSet.add(tempTask.getId());
			tempIdSet.add(FdcObjectValueUtil.get(tempTask, "parent.id"));
		}
		tempIdSet.removeAll(scheduleTaskIdMap.keySet());
		tempIdSet.remove(null);

		if (FdcCollectionUtil.isNotEmpty(tempIdSet)) {
			filterInfo.getFilterItems().clear();
			filterInfo.getFilterItems().add(new FilterItemInfo("id", tempIdSet, CompareType.INCLUDE));

			FDCScheduleTaskCollection tempScheduleTaskCollection = null;
			try {
				// 从数据库中取出最新的数据
				tempScheduleTaskCollection = fDCScheduleTask.getFDCScheduleTaskCollection(viewInfo);
			} catch (BOSException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				logger.error(e1);
			}
			// 优化方案：缓存Map
			Map tempScheduleTaskIdMap = FdcObjectCollectionUtil.parseUniqueIdMap(tempScheduleTaskCollection);
			allScheduleTaskIdMap.putAll(tempScheduleTaskIdMap);
		}

		// ////////////////////////////////////////////////////////////////////////
		
		BOSUuid parentId = null;
		BOSUuid parentParentId = null;
		
		for (int i = 0; i < colsArray.length; i++) {
			tempTask = (FDCScheduleTaskInfo) colsArray[i];
			if (tempTask.getParent() != null && tempTask.get("state") != null) {
				if (resultMap.get(tempTask.getParent().getId().toString()) != null) {
					if (Integer.parseInt(resultMap.get(tempTask.getParent().getId().toString()) + "") < Integer
							.parseInt(tempTask.get("state") + "")) {
						resultMap.remove(tempTask.getParent().getId().toString());
						resultMap.put(tempTask.getParent().getId().toString(), tempTask.get("state"));
					}
				} else {
					if (resultMap.get(tempTask.getId().toString()) != null) {
						resultMap.put(tempTask.getParent().getId().toString(), resultMap.get(tempTask.getId()
								.toString()));
					} else {
						parentId = tempTask.getParent().getId();
						FDCScheduleTaskInfo tmpParent = (FDCScheduleTaskInfo) allScheduleTaskIdMap.get(parentId);
						
						if (null != tmpParent && tmpParent.getParent() != null) {
							resultMap.put(parentId.toString(), tempTask.get("state"));

							parentParentId = tmpParent.getParent().getId();
							FDCScheduleTaskInfo innerTmpParent = (FDCScheduleTaskInfo) allScheduleTaskIdMap
									.get(parentParentId);

							if (null != innerTmpParent && innerTmpParent.getParent() != null) {
								resultMap.put(innerTmpParent.getParent().getId().toString(), tempTask.get("state"));
							}
						}
					}
				}
			}

		}
		
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(FDCScheduleTaskStateHelper.class, "getMaxChildrenState",
				getMaxChildrenStateMap);
		// ////////////////////////////////////////////////////////////////////////
        
		return resultMap;
	}
	
	
	
}
