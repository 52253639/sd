package com.kingdee.eas.fdc.schedule.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSCollection;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSTree;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.WBSAdjustManager;
import com.kingdee.eas.fdc.schedule.FDCWBSTree.FDCWBSTreeNode;
import com.kingdee.eas.fdc.schedule.WBSAdjustManager.WBSAdjustManagerItem;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class FDCScheduleTaskControllerBean extends AbstractFDCScheduleTaskControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskControllerBean");

	protected boolean _existPreOrDep(Context ctx, String wbsId1, String wbsId2)
			throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("task.wbs.id",wbsId1));
		filter.getFilterItems().add(new FilterItemInfo("dependTask.wbs.id",wbsId2));
		filter.getFilterItems().add(new FilterItemInfo("task.schedule.isLatestVer",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("dependTask.schedule.isLatestVer",Boolean.TRUE));
		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(new FilterItemInfo("task.wbs.id",wbsId2));
		filter2.getFilterItems().add(new FilterItemInfo("dependTask.wbs.id",wbsId1));
		filter2.getFilterItems().add(new FilterItemInfo("task.schedule.isLatestVer",Boolean.TRUE));
		filter2.getFilterItems().add(new FilterItemInfo("dependTask.schedule.isLatestVer",Boolean.TRUE));
		filter.mergeFilter(filter2, "or");
		return FDCScheduleTaskDependFactory.getLocalInstance(ctx).exists(filter);
	}

	protected Map _reCalWBSNumber(Context ctx, Object adjustManager)throws BOSException, EASBizException {
		return addAdjustManagerItems(ctx, adjustManager);
	}

	private Map addAdjustManagerItems(Context ctx, Object adjustManager)	throws BOSException, EASBizException {
		WBSAdjustManager wbsAdjustManager = (WBSAdjustManager) adjustManager;
		Map oldParentMap = new HashMap();
//		存放已添加到adjustmanager中的ID
		Set addedWBSIds = new HashSet();
		
		String projectId = null;
		if(projectId == null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("curProject.id");
			sic.add("id");
			FDCWBSInfo info = FDCWBSFactory.getLocalInstance(ctx).getFDCWBSInfo(
					new ObjectUuidPK(wbsAdjustManager.get(0).getWbsInfo().getId()),sic);
			projectId = info.getCurProject().getId().toString();
		}
		for(int i=0;i<wbsAdjustManager.size();i++){
			addedWBSIds.add(wbsAdjustManager.get(i).getWbsInfo().getId().toString());
		}
//		校验是否存在未保存的任务,此处不能按照WBS判定，有可能存在WBS存在，但是刚导出未保存的问题
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select count(tk.FID) from t_sch_fdcscheduletask tk inner join t_sch_fdcschedule sch " +
				"	on tk.fscheduleid=sch.fid where sch.FIsLatestVer=1 and");
		builder.appendParam("tk.FWBSID",addedWBSIds.toArray());
		try{
			IRowSet rowSet = builder.executeQuery();
			int wbsCount = 0;
			while(rowSet.next()){
				wbsCount = rowSet.getInt(1);
			}
			if(wbsCount < addedWBSIds.size()){
				throw new EASBizException(new NumericExceptionSubItem("601","请保存新增任务后再重算编码！"));
			}
		}catch (SQLException e){
			throw new EASBizException(new NumericExceptionSubItem("602",e.getMessage()));
		}
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("parent.id");
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("curProject.id",projectId,CompareType.EQUALS));
		view.getSorter().add(new SorterItemInfo("longNumber"));
		FDCWBSCollection wbsCol = FDCWBSFactory.getLocalInstance(ctx).getFDCWBSCollection(view);
		for(int i=0;i<wbsCol.size();i++){
			FDCWBSInfo wbsInfo = wbsCol.get(i);
			if(wbsInfo.getParent() != null){
				oldParentMap.put(wbsInfo.getId().toString(), wbsInfo.getParent().getId().toString());
			}
			if(!addedWBSIds.contains(wbsInfo.getId().toString())){
				FDCWBSInfo sibInfo = null; 
				if(wbsInfo.getParent() != null){
					sibInfo = getSibInfo(wbsAdjustManager, wbsInfo.getParent().getId().toString(),oldParentMap);
				}else{
					sibInfo = getSibInfo(wbsAdjustManager, null, oldParentMap);
				}
				wbsAdjustManager.addItem(wbsInfo, sibInfo);
			}
		}
		FDCWBSFactory.getLocalInstance(ctx).reCalculateNumber(wbsAdjustManager);
		
		SelectorItemCollection sic = new SelectorItemCollection();
		view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		sic.add("*");
		sic.add("wbs.*");
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("wbs.id",addedWBSIds,CompareType.INCLUDE));
		view.setFilter(filter);
		view.setSelector(sic);
		FDCScheduleTaskCollection fdcTaskCol = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		Map fdcTaskMap = new HashMap();
		for(int i=0;i<fdcTaskCol.size();i++){
			FDCScheduleTaskInfo fdcTaskInfo = fdcTaskCol.get(i);
			fdcTaskMap.put(fdcTaskInfo.getId().toString(), fdcTaskInfo);
		}
		return fdcTaskMap;
	}
	private FDCWBSInfo getSibInfo(WBSAdjustManager adjustManager,String parentId,Map oldParentMap){
		FDCWBSInfo sibInfo = null;
		Set sibIds = new HashSet();
		Set childIds = new HashSet();
		for(int i=0;i<adjustManager.size();i++){
			WBSAdjustManagerItem item = adjustManager.get(i);
			String tempParentId = null;
			if(item.getNewParent() != null){
				tempParentId = item.getNewParent().getId().toString();
			}else if(oldParentMap.get(item.getWbsInfo().getId().toString()) != null){
				tempParentId = (String) oldParentMap.get(item.getWbsInfo().getId().toString());
			}
			if(parentId != null && parentId.equals(tempParentId)){
				childIds.add(item.getWbsInfo().getId().toString());
				if(item.getSibInfo() != null){
					sibIds.add(item.getSibInfo().getId().toString());
				}
			}
		}
		sibIds.remove(null);
		childIds.remove(null);
		for(Iterator ite = childIds.iterator();ite.hasNext();){
			String id = ite.next().toString();
			if(!sibIds.contains(id)){
				sibInfo = new FDCWBSInfo();
				sibInfo.setId(BOSUuid.read(id));
			}
		}
		return sibInfo;
	}

	protected Map _reCalWBSNumber(Context ctx, String projectId)
			throws BOSException, EASBizException {
		Map wbsMap = new HashMap();
		Map childMap = new HashMap();
		FDCWBSCollection firstLevChild = new FDCWBSCollection();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("curProject.id",projectId));
		view.getSelector().add("*");
		view.getSelector().add("parent.*");
		view.getSorter().add(new SorterItemInfo("index"));
		FDCWBSCollection wbsCol = FDCWBSFactory.getLocalInstance(ctx).getFDCWBSCollection(view);
		for(int i=0;i<wbsCol.size();i++){
			FDCWBSInfo wbsInfo = wbsCol.get(i);
			FDCWBSInfo wbsParentInfo;
			wbsMap.put(wbsInfo.getId().toString(), wbsInfo);
			wbsParentInfo = wbsInfo.getParent();
			if(wbsParentInfo != null){
				if(childMap.keySet().contains(wbsParentInfo.getId().toString())){
					((ArrayList)childMap.get(wbsParentInfo.getId().toString())).add(wbsInfo);
				}else{
					ArrayList childArray = new ArrayList();
					childArray.add(wbsInfo);
					childMap.put(wbsParentInfo.getId().toString(),childArray);
				}
			}else{
				firstLevChild.add(wbsInfo);
			}
		}
//		添加到中间类
		WBSAdjustManager wbsAdjManager = new WBSAdjustManager();
		for(int i=0;i<wbsCol.size();i++){
			FDCWBSInfo wbsInfo = wbsCol.get(i);
			wbsAdjManager.addItem(wbsInfo, getSibInfo(wbsInfo, wbsMap,childMap,firstLevChild));
		}
//		重新算编码
		for(int i=0;i<wbsAdjManager.size();i++){
			WBSAdjustManagerItem item = wbsAdjManager.get(i);
			wbsAdjManager.reCalNumber(item);
			System.out.println("name:"+item.getWbsInfo().getName() +";oldLongNum:"+item.getWbsInfo().getLongNumber()+";newLongNum:"+item.getLongNumber());
		}
		FDCWBSFactory.getLocalInstance(ctx).reCalculateNumber(wbsAdjManager);
		view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getSelector().add("isLeaf");
		view.getSelector().add("level");
		view.getSelector().add("name");
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("longNumber");
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.project.id",projectId));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		FDCScheduleTaskCollection taskCol = getFDCScheduleTaskCollection(ctx, view);
		for(int i=0;i<taskCol.size();i++){
			FDCScheduleTaskInfo fdcTaskInfo = taskCol.get(i);
			wbsMap.put(fdcTaskInfo.getId().toString(), fdcTaskInfo);
		}
		return wbsMap;
	}
	
	private FDCWBSInfo getSibInfo(FDCWBSInfo info,Map wbsMap,Map childMap,FDCWBSCollection firstLevChild){
		FDCWBSInfo sibInfo = null;
		FDCWBSInfo parentInfo = info.getParent();
		if(parentInfo != null){
			ArrayList childArray = (ArrayList) childMap.get(parentInfo.getId().toString());
			for(int i=0;i<childArray.size();i++){
				FDCWBSInfo tempSibInfo = (FDCWBSInfo) childArray.get(i);
				if(tempSibInfo.getId().toString().equals(info.getId().toString())){
					if(i>0) return (FDCWBSInfo) childArray.get(i - 1);
					else return null;
				}
			}
		}else{
			for(int i=0;i<firstLevChild.size();i++){
				FDCWBSInfo tempSibInfo = firstLevChild.get(i);
				if(tempSibInfo.getId().toString().equals(info.getId().toString())){
					if(i>0) return firstLevChild.get(i-1);
					else return null;
				}
			}
		}
		return sibInfo;
	}

	protected Map _addTask(Context ctx, IObjectValue task)throws BOSException, EASBizException {
		//添加时， 无需重算编码
		FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) task;
		boolean isScheduleExist = false;
		if(taskInfo.getSchedule() != null && taskInfo.getSchedule().getId() != null){
			isScheduleExist = FDCScheduleFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(taskInfo.getSchedule().getId()));
		}
		try {
			initWBSByTask(taskInfo);
			FDCWBSFactory.getLocalInstance(ctx).save(taskInfo.getWbs());
			if(isScheduleExist){
				FDCScheduleInfo schedule = taskInfo.getSchedule();
				if(schedule!= null && schedule.getScheduleType()!=null 
						&& TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(schedule.getScheduleType().getId().toString())){
					this._save(ctx, taskInfo);
				}else{
					FDCScheduleFactory.getLocalInstance(ctx).save(taskInfo.getSchedule());					
				}
			}
			FDCWBSTree tree = FDCWBSTree.getTreeFromDB(ctx, taskInfo.getWbs().getCurProject().getId().toString());
			updateWBS(ctx, tree);
			return tree.getId2WBSMap();
		} catch (EASBizException e) {
			throw new BOSException(e);
		}
	}
	private void initWBSByTask(FDCScheduleTaskInfo taskInfo){
		if (taskInfo.getWbs() != null) {
			taskInfo.getWbs().setIsEnabled(true);
			if (taskInfo.getWbs().getTaskType() == null) {
				TaskTypeInfo taskTypeInfo = new TaskTypeInfo();
				taskTypeInfo.setId(BOSUuid.read(TaskTypeInfo.TASKTYPE_MAINTASK));
				taskInfo.getWbs().setTaskType(taskTypeInfo);
			}
			taskInfo.getWbs().setName(taskInfo.getName());
			// 注意在FDCScheduleTaskInfo实体上adminDept是责任部门字段，计划编制部门字段是planDept;
			// 而在FDCWBS实体上respDept是责任部门字段，计划编制部门字段是adminDept by cassiel_peng
			// 2010-04-22
			if (taskInfo.get("planDept") != null) {
				// 主项才有计划标志部门，专项没有 by cassiel 2010-06-24
				if (TaskTypeInfo.TASKTYPE_MAINTASK.equals(taskInfo.getWbs().getTaskType().getId().toString())) {
					if (taskInfo.get("planDept") instanceof CostCenterOrgUnitInfo) {
						taskInfo.getWbs().setAdminDept(((CostCenterOrgUnitInfo) taskInfo.get("planDept")).castToFullOrgUnitInfo());
					} else {
						taskInfo.getWbs().setAdminDept(taskInfo.getPlanDept());
					}
				}
			}
			if (taskInfo.get("adminDept") != null) {
				if (taskInfo.get("adminDept") instanceof CostCenterOrgUnitInfo) {
					taskInfo.getWbs().setRespDept(((CostCenterOrgUnitInfo) taskInfo.get("adminDept")).castToFullOrgUnitInfo());
				} else {
					taskInfo.getWbs().setRespDept(taskInfo.getAdminDept());
				}
			}
			taskInfo.getWbs().setAdminPerson(taskInfo.getAdminPerson());
			taskInfo.getWbs().setLevel(taskInfo.getLevel());
			if(taskInfo.getParent()!=null)
				taskInfo.getWbs().setParent(taskInfo.getParent().getWbs());
			else
				taskInfo.getWbs().setParent(null);
			if(taskInfo.getEffectTimes()!=null)
				taskInfo.getWbs().setEstimateDays(taskInfo.getEffectTimes().intValue());
			else
				taskInfo.getWbs().setEstimateDays(1);
		}
	}
	
	protected Map _deleteTask(Context ctx, String[] wbsIds, String projectId) throws BOSException, EASBizException {
		FDCWBSTree tree = FDCWBSTree.getTreeFromDB(ctx, projectId);
		List paramList = new ArrayList();
		for(int i = 0; i < wbsIds.length; ++i){
			tree.removeWBS(wbsIds[i]);
			fillAllChildrenWBSId(paramList, wbsIds[i], tree);
		}
		updateWBS(ctx, tree);
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String deleteWBSSql = "delete T_SCH_FDCWBS where fid=?";		
		builder.executeBatch(deleteWBSSql, paramList);
		
		builder.clear();
		String deleteTaskSql = "delete T_sch_fdcscheduletask where fwbsid=?";
		builder.executeBatch(deleteTaskSql, paramList);
		
		return	tree.getId2WBSMap();
	}
	/**
	 * 添加所有子孙WBS的id到参数列表中， 以便当删除非叶子WBS时， 同时删除所有的子孙WBS
	 * @param paramList
	 * @param wbsId
	 * @param tree
	 */
	private void fillAllChildrenWBSId(List paramList, String wbsId, FDCWBSTree tree){
		paramList.add(Arrays.asList(new Object[]{wbsId}));
		FDCWBSTreeNode node = tree.getNode(wbsId);
		if(node != null){
			List children = node.getChildren();
			for(int i = 0; i < children.size(); ++i){
				FDCWBSTreeNode child = (FDCWBSTreeNode) children.get(i);
				fillAllChildrenWBSId(paramList, child.getEntity().getId().toString(), tree);
			}	
		}
	}
	protected Map _moveTask(Context ctx, String[] wbsId, String operateType, String projectId)
			throws BOSException, EASBizException {
		FDCWBSTree tree = FDCWBSTree.getTreeFromDB(ctx, projectId);
		for(int i = 0; i < wbsId.length; ++i){
			if("up".equals(operateType)){
				tree.moveUp(wbsId[i]);
			}else if("down".equals(operateType)){
				tree.moveDown(wbsId[i]);
			}else if("indent".equals(operateType)){
				tree.degrade(wbsId[i]);
			}else if("dedent".equals(operateType)){
				tree.upgrade(wbsId[i]);
			} 
		}	
		updateWBS(ctx, tree);
		return tree.getId2WBSMap();
	}
	private void updateWBS(Context ctx, FDCWBSTree tree) throws BOSException, EASBizException{		
		FDCWBSFactory.getLocalInstance(ctx).saveOrderWBS(tree, false);
	}

	protected Set _getProjectByTask(Context ctx, String adminDeptId, String adminPersonId) throws BOSException, EASBizException {

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if(adminDeptId !=null && adminPersonId == null){
			builder.appendSql("select project.fid from T_fdc_curProject project,T_SCH_FDCSchedule schedule,T_SCH_FDCScheduleTask task "+
				" where project.fid=schedule.FProjectID and schedule.fid=task.fscheduleid "+
				" and schedule.FIsLatestVer=1 and task.FAdminDeptID=?");
			builder.addParam(adminDeptId);
		}else if(adminDeptId ==null && adminPersonId != null){
			builder.appendSql("select tk.fid from t_sch_fdcscheduletask tk inner join t_sch_fdcschedule sch " +
			"	on tk.fscheduleid=sch.fid where sch.FIsLatestVer=1 and tk.FAdminPersonID=? ");
			builder.addParam(adminPersonId);
		}
		Set proIds =new HashSet();
		try{
			IRowSet rowSet = builder.executeQuery();
			int wbsCount = 0;
			while(rowSet.next()){
				String proId = rowSet.getString(1);
				proIds.add(proId);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return proIds;
	}

	protected List _getTaskByProject(Context ctx, Set projectID, int taskState) throws BOSException, EASBizException {
		List taskSet= new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getSelector().add("*");
		view.getSelector().add("isLeaf");
		view.getSelector().add("level");
		view.getSelector().add("name");
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("longNumber");
		view.getSelector().add("adminDept");
		view.getSelector().add("adminDept.name");
		view.getSelector().add("adminPerson");
		view.getSelector().add("adminPerson.name");
		view.getSelector().add("schedule");
		view.getSelector().add("schedule.project");
		view.getSelector().add("schedule.project.id");
		view.getSelector().add("schedule.project.name");
		view.getSelector().add("wbs.id");
		view.getSelector().add("wbs.name");
		view.getSelector().add("dependEntrys");
		view.getSelector().add("dependEntrys.type");
		view.getSelector().add("dependEntrys.difference");
		view.getSelector().add("dependEntrys.task");
		view.getSelector().add("dependEntrys.task.id");
		view.getSelector().add("dependEntrys.task.name");
		view.getSelector().add("dependEntrys.dependTask");
		view.getSelector().add("dependEntrys.dependTask.id");
		view.getSelector().add("dependEntrys.dependTask.name");
		
		
		view.getSelector().add("wbs.number");
		view.getSelector().add("wbs.parent");
		
		view.getSelector().add("wbs.longNumber");
		view.getSelector().add("wbs.isLeaf");
		view.getSelector().add("wbs.level");
		
		view.getSelector().add("wbs.taskType");
		view.getSelector().add("wbs.taskType.id");
		view.getSelector().add("wbs.name");
		
		
		
		
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.project.id",projectID,CompareType.INCLUDE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		
		if(taskState==0){//全部任务
		}else if(taskState==1){//累计百分比为0
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete",FDCConstants.ZERO,CompareType.EQUALS));
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete", null, CompareType.EQUALS));
			view.getFilter().getFilterItems().add(new FilterItemInfo("actualStartDate", null, CompareType.EQUALS));
			view.getFilter().setMaskString(" #0 and #1 and (#2 or #3 and #4)");
		}else if(taskState==2){//0<累计百分比<1
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete",FDCConstants.ZERO,CompareType.GREATER));
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete",FDCConstants.ONE_HUNDRED,CompareType.LESS));
			view.getFilter().getFilterItems().add(new FilterItemInfo("actualStartDate", null, CompareType.NOTEQUALS));
			view.getFilter().setMaskString(" #0 and #1 and ((#2 and #3) or #4)");
		}else if(taskState==3){//累计百分比等于1
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete",FDCConstants.ONE_HUNDRED,CompareType.EQUALS));
			view.getFilter().setMaskString(" #0 and #1 and #2");
		}
		view.getSorter().add(new SorterItemInfo("longNumber"));
		FDCScheduleTaskCollection taskCol = getFDCScheduleTaskCollection(ctx, view);
		if(taskCol!=null && taskCol.size()>0){
			for(int i=0;i<taskCol.size();i++){
				FDCScheduleTaskInfo task =(FDCScheduleTaskInfo)taskCol.get(i);
				taskSet.add(task);
			}
		}
		return taskSet;
	}

	protected List _getDependantTask(Context ctx, String TaskId) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if(TaskId !=null){
			builder.appendSql(" select task.flongnumber,task.fname_l2,depend.ftype,depend.fdifference "+
					" from T_SCH_FDCScheduleTask task,T_SCH_FDCScheduleTaskDepend depend "+
					" where task.fid=depend.ftaskid and fdependtaskid=? ");
			builder.addParam(TaskId);
		}
		List taskLists =new ArrayList();
		try{
			IRowSet rowSet = builder.executeQuery();
			int wbsCount = 0;
			while(rowSet.next()){
				FDCScheduleTaskInfo task =new FDCScheduleTaskInfo();
				String taskLongNubmer = rowSet.getString(1);
				task.setLongNumber(taskLongNubmer);
				String taskName = rowSet.getString(2);
				task.setName(taskName);
				String type = rowSet.getString(3);
				task.put("type", type);
				int difference = rowSet.getInt(4);
				task.put("difference", new Integer(difference));
				
				taskLists.add(task);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return taskLists;
	}

	protected List _getTaskByAdminDemp(Context ctx, Set projectID, int taskState, String adminDempID) throws BOSException, EASBizException {
		List taskSet= new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getSelector().add("*");
		view.getSelector().add("isLeaf");
		view.getSelector().add("level");
		view.getSelector().add("name");
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("longNumber");
		view.getSelector().add("adminDept");
		view.getSelector().add("adminDept.name");
		view.getSelector().add("adminPerson");
		view.getSelector().add("adminPerson.name");
		view.getSelector().add("schedule");
		view.getSelector().add("schedule.project");
		view.getSelector().add("schedule.project.id");
		view.getSelector().add("schedule.project.name");
		view.getSelector().add("wbs.id");
		view.getSelector().add("wbs.name");
		view.getSelector().add("dependEntrys");
		view.getSelector().add("dependEntrys.type");
		view.getSelector().add("dependEntrys.difference");
		view.getSelector().add("dependEntrys.task");
		view.getSelector().add("dependEntrys.task.id");
		view.getSelector().add("dependEntrys.task.name");
		view.getSelector().add("dependEntrys.dependTask");
		view.getSelector().add("dependEntrys.dependTask.id");
		view.getSelector().add("dependEntrys.dependTask.name");
		
		
		view.getSelector().add("wbs.number");
		view.getSelector().add("wbs.parent");
		
		view.getSelector().add("wbs.longNumber");
		view.getSelector().add("wbs.isLeaf");
		view.getSelector().add("wbs.level");
		
		view.getSelector().add("wbs.taskType");
		view.getSelector().add("wbs.taskType.id");
		view.getSelector().add("wbs.name");
		
		
		
		
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.project.id",projectID,CompareType.INCLUDE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("adminDept.id",adminDempID,CompareType.EQUALS));
		
		if(taskState==0){//全部任务
		}else if(taskState==1){//累计百分比为0
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete",FDCConstants.ZERO,CompareType.EQUALS));
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete", null, CompareType.EQUALS));
			view.getFilter().getFilterItems().add(new FilterItemInfo("actualStartDate", null, CompareType.EQUALS));
			view.getFilter().setMaskString(" #0 and #1 and #2 and (#3 or #4 and #5)");
		}else if(taskState==2){//0<累计百分比<1
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete",FDCConstants.ZERO,CompareType.GREATER));
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete",FDCConstants.ONE_HUNDRED,CompareType.LESS));
			view.getFilter().getFilterItems().add(new FilterItemInfo("actualStartDate", null, CompareType.NOTEQUALS));
			view.getFilter().setMaskString(" #0 and #1 and #2 and ((#3 and #4) or #5)");
		}else if(taskState==3){//累计百分比等于1
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete",FDCConstants.ONE_HUNDRED,CompareType.EQUALS));
			view.getFilter().setMaskString(" #0 and #1 and #2 and #3");
		}
		view.getSorter().add(new SorterItemInfo("longNumber"));
		FDCScheduleTaskCollection taskCol = getFDCScheduleTaskCollection(ctx, view);
		if(taskCol!=null && taskCol.size()>0){
			for(int i=0;i<taskCol.size();i++){
				FDCScheduleTaskInfo task =(FDCScheduleTaskInfo)taskCol.get(i);
				taskSet.add(task);
			}
		}
		return taskSet;
	}

	protected List _getTaskByAdminPerson(Context ctx, Set projectID, int taskState, String adminPersonID) throws BOSException, EASBizException {
		List taskSet= new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getSelector().add("*");
		view.getSelector().add("isLeaf");
		view.getSelector().add("level");
		view.getSelector().add("name");
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("longNumber");
		view.getSelector().add("adminDept");
		view.getSelector().add("adminDept.name");
		view.getSelector().add("adminPerson");
		view.getSelector().add("adminPerson.name");
		view.getSelector().add("schedule");
		view.getSelector().add("schedule.project");
		view.getSelector().add("schedule.project.id");
		view.getSelector().add("schedule.project.name");
		view.getSelector().add("wbs.id");
		view.getSelector().add("wbs.name");
		view.getSelector().add("dependEntrys");
		view.getSelector().add("dependEntrys.type");
		view.getSelector().add("dependEntrys.difference");
		view.getSelector().add("dependEntrys.task");
		view.getSelector().add("dependEntrys.task.id");
		view.getSelector().add("dependEntrys.task.name");
		view.getSelector().add("dependEntrys.dependTask");
		view.getSelector().add("dependEntrys.dependTask.id");
		view.getSelector().add("dependEntrys.dependTask.name");
		
		
		view.getSelector().add("wbs.number");
		view.getSelector().add("wbs.parent");
		
		view.getSelector().add("wbs.longNumber");
		view.getSelector().add("wbs.isLeaf");
		view.getSelector().add("wbs.level");
		
		view.getSelector().add("wbs.taskType");
		view.getSelector().add("wbs.taskType.id");
		view.getSelector().add("wbs.name");
		
		
		
		
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.project.id",projectID,CompareType.INCLUDE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("adminPerson.id",adminPersonID,CompareType.EQUALS));
		
		if(taskState==0){//全部任务
		}else if(taskState==1){//累计百分比为0
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete",FDCConstants.ZERO,CompareType.EQUALS));
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete", null, CompareType.EQUALS));
			view.getFilter().getFilterItems().add(new FilterItemInfo("actualStartDate", null, CompareType.EQUALS));
			view.getFilter().setMaskString(" #0 and #1 and #2 and (#3 or #4 and #5)");
		}else if(taskState==2){//0<累计百分比<1
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete",FDCConstants.ZERO,CompareType.GREATER));
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete",FDCConstants.ONE_HUNDRED,CompareType.LESS));
			view.getFilter().getFilterItems().add(new FilterItemInfo("actualStartDate", null, CompareType.NOTEQUALS));
			view.getFilter().setMaskString(" #0 and #1 and #2 and ((#3 and #4) or #5)");
		}else if(taskState==3){//累计百分比等于1
			view.getFilter().getFilterItems().add(new FilterItemInfo("complete",FDCConstants.ONE_HUNDRED,CompareType.EQUALS));
			view.getFilter().setMaskString(" #0 and #1 and #2 and #3");
		}
		view.getSorter().add(new SorterItemInfo("longNumber"));
		FDCScheduleTaskCollection taskCol = getFDCScheduleTaskCollection(ctx, view);
		if(taskCol!=null && taskCol.size()>0){
			for(int i=0;i<taskCol.size();i++){
				FDCScheduleTaskInfo task =(FDCScheduleTaskInfo)taskCol.get(i);
				taskSet.add(task);
			}
		}
		return taskSet;
	}
	
	protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
//		super._checkNumberDup(ctx, model);
	}

	protected void _importTasks(Context ctx, CoreBaseCollection currentTaskCollection, CoreBaseCollection newTaskCollection, CoreBaseCollection currentWbsCollection,
			CoreBaseCollection newWbsCollectioin, CoreBaseCollection currentDependTaskCollection, CoreBaseCollection newDependTaskCollection) throws BOSException, EASBizException {
		/* 批量增加任务 */
		for(int i = 0; i < newTaskCollection.size(); i ++){
			addnew(ctx, newTaskCollection.get(i));
		}
		
		/* 批量增加wbs */
		for(int i = 0; i < newWbsCollectioin.size(); i ++){
			FDCWBSFactory.getLocalInstance(ctx).addnew(newWbsCollectioin.get(i));
		}
		
		/* 批量增加后置任务 */
		for(int i = 0; i < newDependTaskCollection.size(); i ++){
			FDCScheduleTaskDependFactory.getLocalInstance(ctx).addnew(newDependTaskCollection.get(i));
		}
		
		/* 删除原有模板任务 */
		IObjectPK[] pks = new IObjectPK[currentTaskCollection.size()];
		for(int f = 0; f < currentTaskCollection.size(); f ++){
			FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) currentTaskCollection.get(f);
			pks[f] = new ObjectUuidPK(fDCScheduleTaskInfo.getId()); 
		}
		deleteBatchData(ctx,pks);
		
		/* 删除原有wbs */
		IObjectPK[] wbspks = new IObjectPK[currentWbsCollection.size()];
		for(int f = 0; f < currentWbsCollection.size(); f ++){
			FDCWBSInfo fDCWBSInfo = (FDCWBSInfo) currentWbsCollection.get(f);
			pks[f] = new ObjectUuidPK(fDCWBSInfo.getId()); 
		}
		FDCWBSFactory.getLocalInstance(ctx).deleteBatchData(wbspks);
		
		/* 删除原有后置任务 */
		IObjectPK[] dependpks = new IObjectPK[currentDependTaskCollection.size()];
		for(int f = 0; f < currentDependTaskCollection.size(); f ++){
			FDCScheduleTaskDependInfo fDCScheduleTaskDependInfo = (FDCScheduleTaskDependInfo) currentDependTaskCollection.get(f);
			dependpks[f] = new ObjectUuidPK(fDCScheduleTaskDependInfo.getId()); 
		}
		FDCScheduleTaskDependFactory.getLocalInstance(ctx).deleteBatchData(dependpks);
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.schedule.app.AbstractFDCScheduleTaskControllerBean#getFDCScheduleTaskCollection(com.kingdee.bos.Context,
	 *      com.kingdee.bos.metadata.entity.EntityViewInfo)
	 */
	public FDCScheduleTaskCollection getFDCScheduleTaskCollection(Context ctx, EntityViewInfo view) throws BOSException {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map getFDCScheduleTaskCollectionMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(),
				"getFDCScheduleTaskCollection");
		// ////////////////////////////////////////////////////////////////////////

		// TODO Auto-generated method stub
		FDCScheduleTaskCollection cols = super.getFDCScheduleTaskCollection(ctx, view);

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "getFDCScheduleTaskCollection",
				getFDCScheduleTaskCollectionMap);
		// ////////////////////////////////////////////////////////////////////////

		return cols;
	}
}