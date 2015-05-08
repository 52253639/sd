package com.kingdee.eas.fdc.schedule.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.pm.ProjectFillBillEntryCollection;
import com.kingdee.eas.fdc.pm.ProjectFillBillEntryFactory;
import com.kingdee.eas.fdc.pm.QualityCheckPointInfo;
import com.kingdee.eas.fdc.pm.QualityTaskAssignCollection;
import com.kingdee.eas.fdc.pm.QualityTaskAssignFactory;
import com.kingdee.eas.fdc.pm.QualityTaskAssignInfo;
import com.kingdee.eas.fdc.pm.QualityTaskCheckEntryCollection;
import com.kingdee.eas.fdc.pm.QualityTaskCheckEntryFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.ScheduleFacadeFactory;
import com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryCollection;
import com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryFactory;
import com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryInfo;
import com.kingdee.eas.fdc.schedule.TaskCostInfoCollection;
import com.kingdee.eas.fdc.schedule.TaskCostInfoFactory;
import com.kingdee.eas.fdc.schedule.TaskCostInfoInfo;
import com.kingdee.eas.fdc.schedule.TaskLoadEntryCollection;
import com.kingdee.eas.fdc.schedule.TaskLoadEntryFactory;
import com.kingdee.eas.fdc.schedule.TaskMaterialPlanCollection;
import com.kingdee.eas.fdc.schedule.TaskMaterialPlanFactory;
import com.kingdee.eas.fdc.schedule.TaskMaterialPlanInfo;
import com.kingdee.eas.fdc.schedule.TaskQualityPlanCollection;
import com.kingdee.eas.fdc.schedule.TaskQualityPlanFactory;
import com.kingdee.eas.fdc.schedule.TaskQualityPlanInfo;
import com.kingdee.eas.fdc.schedule.TaskQualityPreventionEntryCollection;
import com.kingdee.eas.fdc.schedule.TaskQualityPreventionEntryInfo;
import com.kingdee.eas.fdc.schedule.TaskScheduleLogCollection;
import com.kingdee.eas.fdc.schedule.TaskScheduleLogFactory;
import com.kingdee.eas.fdc.schedule.TaskWorkResultCollection;
import com.kingdee.eas.fdc.schedule.TaskWorkResultFactory;
import com.kingdee.eas.fdc.schedule.TaskWorkResultInfo;
import com.kingdee.eas.fdc.schedule.TaskWorkloadLogCollection;
import com.kingdee.eas.fdc.schedule.TaskWorkloadLogFactory;
import com.kingdee.eas.fdc.schedule.TaskWorkloadLogInfo;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryCollection;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.Result;

public class FDCScheduleTaskExtControllerBean extends AbstractFDCScheduleTaskExtControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskExtControllerBean");
    
    public Result save(Context ctx, CoreBaseCollection colls) throws BOSException, EASBizException {
    	//delete first
    	FDCScheduleTaskExtInfo extInfo=new FDCScheduleTaskExtInfo();
    	IORMappingDAO dao = ORMappingDAO.getInstance(extInfo.getBOSType(), ctx, getConnection(ctx));
    	for(Iterator iter=colls.iterator();iter.hasNext();){
    		extInfo=(FDCScheduleTaskExtInfo)iter.next();
    		dao.deleteBatch(new ObjectUuidPK(extInfo.getId()));
    		dao.addNewBatch(extInfo);
    	}
    	dao.executeBatch();
    	
    	for(Iterator iter=colls.iterator();iter.hasNext();){
    		extInfo=(FDCScheduleTaskExtInfo)iter.next();
    		dao.addNewBatch(extInfo);
    	}
    	dao.executeBatch();
    	return super.save(ctx, colls);
    }
	//获取工程量日志
	private Map getWorkloadLog(Context ctx,String wbsID) throws BOSException{
		Map map = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("taskExt.wbs.id",wbsID));
		view.setFilter(filter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("measureUnit.id");
		sic.add("measureUnit.name");
		sic.add("measureUnit.number");
		sic.add("isPlanPercent");
		sic.add("planWorkload");
		sic.add("completePercent");
		sic.add("id");
		sic.add("name");
		sic.add("*");
		sic.add("taskExt");
		view.setSelector(sic);
		TaskWorkloadLogCollection workLogInfos = 
			TaskWorkloadLogFactory.getLocalInstance(ctx).getTaskWorkloadLogCollection(view);
		if(workLogInfos.size()>0)
			map.put("workloadLog", workLogInfos.get(0));
		else 
			map.put("workloadLog", null);
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("task.wbs.id",wbsID,CompareType.EQUALS));
		view.setFilter(filter);
		sic.clear();
		sic.add("id");
		sic.add("task");
		sic.add("qty");
		sic.add("percent");
//		sic.add("description");
		sic.add("completeDate");
		sic.add("description");
		sic.add("bill.id");
		sic.add("bill.state");
		sic.add("bill.creator.name");
		sic.add("bill.createTime");
		sic.add("bill.auditor.name");
		sic.add("bill.auditTime");
		sic.add("bill.description");
		view.setSelector(sic);
		ProjectFillBillEntryCollection workloadLogEntrys = 
			ProjectFillBillEntryFactory.getLocalInstance(ctx).getProjectFillBillEntryCollection(view);
		if(workloadLogEntrys.size()>0)
			map.put("workloadLogEntrys", workloadLogEntrys);
		else
			map.put("workloadLogEntrys", null);
		view = new EntityViewInfo();
		filter = new FilterInfo();
		sic = new SelectorItemCollection();
		filter.getFilterItems().add(new FilterItemInfo("wbs.id",wbsID));
		sic.add("*");
		sic.add("creator.id");
		sic.add("creator.name");
		sic.add("creator.number");
		sic.add("confirmer.id");
		sic.add("confirmer.name");
		sic.add("confirmer.number");
		sic.add("completeDate");
		view.setFilter(filter);
		view.setSelector(sic);
		TaskLoadEntryCollection taskLoadCol = TaskLoadEntryFactory.getLocalInstance(ctx).getTaskLoadEntryCollection(view);
		if(taskLoadCol.size() > 0)
			map.put("taskLoadEntrys", taskLoadCol);
		else
			map.put("taskLoadEntrys", null);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("wbs.id",wbsID));
		filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.AUDITTED));
		sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("confirmAmount"));
		sic.add(new SelectorItemInfo("confirmPercent"));
		sic.add(new SelectorItemInfo("parent.creator.name"));
		sic.add(new SelectorItemInfo("parent.creator.id"));
		sic.add(new SelectorItemInfo("parent.auditor.name"));
		sic.add(new SelectorItemInfo("parent.auditor.id"));
		sic.add(new SelectorItemInfo("completeDate"));
		sic.add(new SelectorItemInfo("parent.bizDate"));
		sic.add(new SelectorItemInfo("parent.auditTime"));
		sic.add(new SelectorItemInfo("completeDate"));
		sic.add(new SelectorItemInfo("parent.state"));
		sic.add(new SelectorItemInfo("remark"));
		view.setSelector(sic);
		view.setFilter(filter);
		WorkAmountEntryCollection workamountCols = WorkAmountEntryFactory.getLocalInstance(ctx).getWorkAmountEntryCollection(view);
		if(workamountCols.size()>0){
			map.put("workamountEntrys", workamountCols);
		}else{
			map.put("workamountEntrys", null);
		}
		return map;
	}
	//获取成本信息
	private Map getCostInfo(Context ctx,String wbsID) throws BOSException{
		Map map = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("taskExt.wbs.id",wbsID));
		view.setFilter(filter);
		TaskCostInfoCollection costInfos = TaskCostInfoFactory.getLocalInstance(ctx).getTaskCostInfoCollection(view);
		if(costInfos.size() > 0)
			map.put("costInfo", costInfos.get(0));
		else
			map.put("costInfo", null);
		return map;
	}
	//获取物资计划
	private Map getMaterialPlan(Context ctx,String wbsID) throws BOSException{
		Map map = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("taskExt.wbs.id",wbsID));
		view.setFilter(filter);
		TaskMaterialPlanCollection materialPlanInfos = 
			TaskMaterialPlanFactory.getLocalInstance(ctx).getTaskMaterialPlanCollection(view);
		if(materialPlanInfos.size()>0)
			map.put("materialPlan", materialPlanInfos);
		else
			map.put("materialPlan", null);
		return map;
	}
	//获取质量计划
	private Map getQualityPlan(Context ctx,String wbsID) throws BOSException{
		Map map = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("taskExt.wbs.id",wbsID));
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("eventEntrys.*");
		sic.add("eventEntrys.directAdminPerson.name");
		sic.add("eventEntrys.directAdminPerson.id");
		sic.add("eventEntrys.directAdminPerson.number");
		sic.add("eventEntrys.indirectAdminPerson.name");
		sic.add("eventEntrys.indirectAdminPerson.id");
		sic.add("eventEntrys.indirectAdminPerson.number");
		sic.add("preventionEntrys.id");
		sic.add("preventionEntrys.name");
		sic.add("preventionEntrys.prevention");
		view.setSelector(sic);
		view.setFilter(filter);
		TaskQualityPlanCollection qtyPlanInfos = 
			TaskQualityPlanFactory.getLocalInstance(ctx).getTaskQualityPlanCollection(view);
		if(qtyPlanInfos.size()>0)
			map.put("qualityPlan", qtyPlanInfos.get(0));
		else 
			map.put("qualityPlan", null);
		
		try {
			initData(wbsID,ctx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("qualityCheckPoint", headMap);
		map.put("qualityCheckPointEntrys", qualityDataMap);
		return map;
	}
	//获取工作成果
	private Map getWorkResult(Context ctx,String wbsID) throws BOSException{
		Map map = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("taskExt.wbs.id",wbsID));
		view.setFilter(filter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("entrys.*");
		sic.add("entrys.file.name");
		sic.add("entrys.file.id");
		sic.add("entrys.adminDept.id");
		sic.add("entrys.adminDept.name");
		sic.add("entrys.adminDept.number");
		sic.add("entrys.creator.id");
		sic.add("entrys.creator.name");
		sic.add("entrys.creator.number");
		view.setSelector(sic);
		TaskWorkResultCollection workResultCol = 
			TaskWorkResultFactory.getLocalInstance(ctx).getTaskWorkResultCollection(view);
		if(workResultCol.size()>0)
			map.put("workResult", workResultCol.get(0));
		else
			map.put("workResult",null);
		return map;
	}

	//获取任务日志
	private Map getTaskLog(Context ctx,String wbsID) throws BOSException{
		Map map = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("taskExt.wbs.id",wbsID));
		view.setFilter(filter);
		TaskScheduleLogCollection taskLogCol = 
			TaskScheduleLogFactory.getLocalInstance(ctx).getTaskScheduleLogCollection(view);
		if(taskLogCol.size() > 0){
			map.put("taskScheduleLog", taskLogCol);
		}else{
			map.put("taskScheduleLog", null);
		}
		return map;
	}
	
	//获取扩展属性对象
	private FDCScheduleTaskExtInfo getTaskExtInfo(Context ctx,String wbsID) throws EASBizException, BOSException{
		FDCScheduleTaskExtCollection infoCol = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("wbs.id",wbsID));
		view.setFilter(filter);
		
		infoCol = FDCScheduleTaskExtFactory.getLocalInstance(ctx).getFDCScheduleTaskExtCollection(view);
		if(infoCol.size() > 0){
			return infoCol.get(0);
		}
		return null;
	}
	//获取质量检查结果分录
	private Map getQtyResultEntrys(Context ctx,String wbsID) throws BOSException{
		Map map = new HashMap();
		QualityTaskCheckEntryCollection qtyResultEntrys = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		filter.getFilterItems().add(new FilterItemInfo("head.wbs.id",wbsID));
		sic.add("id");
		sic.add("value");
		sic.add("checkProp");
		sic.add("result");
		sic.add("description");
		sic.add("checkPoint.id");
		sic.add("checkPoint.name");
//		sic.add("checkPoint.number");
		sic.add("specialPost.id");
		sic.add("specialPost.name");
//		sic.add("specialPost.number");
		view.setFilter(filter);
		view.setSelector(sic);
		qtyResultEntrys = QualityTaskCheckEntryFactory.getLocalInstance(ctx).getQualityTaskCheckEntryCollection(view);
		if(qtyResultEntrys.size()>0)
			map.put("qualityResultEntrys", qtyResultEntrys);
		else
			map.put("qualityResultEntrys", null);
		return map;
	}
	
	protected void _deletePropByTaskExtID(Context ctx, String taskExtID)
			throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("taskExt.id",taskExtID));
		
		TaskWorkloadLogFactory.getLocalInstance(ctx).delete(filter);
		TaskCostInfoFactory.getLocalInstance(ctx).delete(filter);
		TaskMaterialPlanFactory.getLocalInstance(ctx).delete(filter);
		TaskQualityPlanFactory.getLocalInstance(ctx).delete(filter);
		TaskWorkResultFactory.getLocalInstance(ctx).delete(filter);
	}

	protected Map _getProByTaskExtID(Context ctx, String taskExtID)
			throws BOSException, EASBizException {
		Map map = new HashMap();
		FDCScheduleTaskExtInfo taskExtInfo = this.getFDCScheduleTaskExtInfo(ctx, new ObjectUuidPK(BOSUuid.read(taskExtID)));
		map = _getProByWBSID(ctx, taskExtInfo.getWbs().getId().toString());
		return map;
	}


	protected void _saveCompileProp(Context ctx, Map extCompileProperties)
			throws BOSException, EASBizException {
		if(extCompileProperties.get("wbsInfo") != null){
			FDCWBSInfo wbsInfo = (FDCWBSInfo) extCompileProperties.get("wbsInfo");
			FDCScheduleTaskExtInfo taskExtInfo = new FDCScheduleTaskExtInfo();
			taskExtInfo.setWbs(wbsInfo);
			taskExtInfo.setId(BOSUuid.read(
					FDCScheduleTaskExtFactory.getLocalInstance(ctx).addnew(taskExtInfo).toString()));
			TaskWorkloadLogInfo workloadLogInfo;
			TaskCostInfoInfo costInfo;
			TaskQualityPlanInfo qualityPlanInfo;
			if(extCompileProperties.get("workloadLog") != null){
				workloadLogInfo = (TaskWorkloadLogInfo) extCompileProperties.get("workloadLog");
				workloadLogInfo.setTaskExt(taskExtInfo);
				TaskWorkloadLogFactory.getLocalInstance(ctx).addnew(workloadLogInfo);
				Set wbsIDs = new HashSet();
				//传入的需是String ID by sxhong
				wbsIDs.add(wbsInfo.getId().toString());
				ScheduleFacadeFactory.getLocalInstance(ctx).reCalcParentTaskComplete(wbsIDs);
			}
			if(extCompileProperties.get("costInfo") != null){
				costInfo = (TaskCostInfoInfo) extCompileProperties.get("costInfo");
				costInfo.setTaskExt(taskExtInfo);
				TaskCostInfoFactory.getLocalInstance(ctx).addnew(costInfo);
			}
			if(extCompileProperties.get("qualityPlan") != null){
				qualityPlanInfo = (TaskQualityPlanInfo) extCompileProperties.get("qualityPlan");
				qualityPlanInfo.setTaskExt(taskExtInfo);
				TaskQualityPlanFactory.getLocalInstance(ctx).addnew(qualityPlanInfo);
			}
		}
	}
	/**
	 * 保存执行时的扩展属性
	 */
	protected void _saveExeProp(Context ctx, Map extExeProperties)
			throws BOSException, EASBizException {
		TaskCostInfoInfo costInfo;
		TaskWorkloadLogInfo workloadLogInfo;
		TaskQualityPlanInfo qualityPlanInfo;
		TaskWorkResultInfo workResultInfo;
		FDCWBSInfo wbsInfo = (FDCWBSInfo) extExeProperties.get("wbsInfo");
		FDCScheduleTaskExtInfo taskExtInfo = new FDCScheduleTaskExtInfo();
		taskExtInfo.setWbs(wbsInfo);
		if(extExeProperties.get("workloadLog") != null){
			workloadLogInfo = (TaskWorkloadLogInfo) extExeProperties.get("workloadLog");
			TaskWorkloadLogFactory.getLocalInstance(ctx).addnew(workloadLogInfo);
			Set wbsIDs = new HashSet();
			//传入的需是String ID by sxhong
			wbsIDs.add(wbsInfo.getId().toString());
			ScheduleFacadeFactory.getLocalInstance(ctx).reCalcParentTaskComplete(wbsIDs);
		}
		if(extExeProperties.get("costInfo") != null){
			costInfo = (TaskCostInfoInfo) extExeProperties.get("costInfo");
			TaskCostInfoFactory.getLocalInstance(ctx).addnew(costInfo);
		}
		if(extExeProperties.get("qualityPlan") != null){
			qualityPlanInfo = (TaskQualityPlanInfo) extExeProperties.get("qualityPlan");
			TaskQualityPlanFactory.getLocalInstance(ctx).addnew(qualityPlanInfo);
		}
		if(extExeProperties.get("workResult") != null){
			workResultInfo = (TaskWorkResultInfo) extExeProperties.get("workResult");
			workResultInfo.setTaskExt(taskExtInfo);
			TaskWorkResultFactory.getLocalInstance(ctx).addnew(workResultInfo);
		}
	}
	
	/**
	 * 编制时修改的属性
	 */
	protected void _updateCompleProp(Context ctx, Map extProperties)
			throws BOSException, EASBizException {
		FDCWBSInfo wbsInfo = null;
		FDCScheduleTaskExtInfo extInfo = null;
		if(extProperties.get("wbsInfo") != null){
			wbsInfo = (FDCWBSInfo) extProperties.get("wbsInfo");
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("wbs.id",wbsInfo.getId().toString()));
			view.setFilter(filter);
			FDCScheduleTaskExtCollection extCol;
			extCol = FDCScheduleTaskExtFactory.getLocalInstance(ctx).getFDCScheduleTaskExtCollection(view);
			if(extCol.size() > 0){
				extInfo = extCol.get(0);
			}
		}
		if(extProperties.get("workloadLog") != null){
			TaskWorkloadLogInfo workloadLogInfo = (TaskWorkloadLogInfo) extProperties.get("workloadLog");
			if(workloadLogInfo.getTaskExt() == null ||
					workloadLogInfo.getTaskExt().getId() == null){
				workloadLogInfo.setTaskExt(extInfo);
				TaskWorkloadLogFactory.getLocalInstance(ctx).addnew(workloadLogInfo);
			}else{
				TaskWorkloadLogFactory.getLocalInstance(ctx).save(workloadLogInfo);
			}
		}
		if(extProperties.get("costInfo") != null){
			TaskCostInfoInfo costInfo = (TaskCostInfoInfo) extProperties.get("costInfo");
			if(costInfo.getTaskExt() == null ||
					costInfo.getTaskExt().getId() == null){
				costInfo.setTaskExt(extInfo);
				TaskCostInfoFactory.getLocalInstance(ctx).addnew(costInfo);
			}else{
				TaskCostInfoFactory.getLocalInstance(ctx).save(costInfo);
			}
		}
		if(extProperties.get("qualityPlan") != null){
			TaskQualityPlanInfo qualityPlanInfo = (TaskQualityPlanInfo) extProperties.get("qualityPlan");
			if(qualityPlanInfo.getTaskExt() == null ||
					qualityPlanInfo.getTaskExt().getId() == null){
				qualityPlanInfo.setTaskExt(extInfo);
				TaskQualityPlanFactory.getLocalInstance(ctx).addnew(qualityPlanInfo);
			}else{
				TaskQualityPlanFactory.getLocalInstance(ctx).save(qualityPlanInfo);
			}
		}
	}

	/**
	 * 执行时修改的属性
	 */
	protected void _updateExeProp(Context ctx, Map extProperties)
			throws BOSException, EASBizException {
		FDCScheduleTaskExtInfo taskExtInfo = null;
		FDCWBSInfo wbsInfo = null;
		if(extProperties.get("wbsInfo") != null){
			wbsInfo = (FDCWBSInfo) extProperties.get("wbsInfo");
			taskExtInfo = FDCScheduleTaskExtFactory.getLocalInstance(ctx).
					getFDCScheduleTaskExtInfo(" where wbs.id='"+wbsInfo.getId().toString()+"'");
		}
		if(extProperties.get("workloadLog") != null){
			TaskWorkloadLogInfo workloadLogInfo = (TaskWorkloadLogInfo) extProperties.get("workloadLog");
			TaskWorkloadLogFactory.getLocalInstance(ctx).save(workloadLogInfo);
		}
		if(extProperties.get("costInfo") != null){
			TaskCostInfoInfo costInfo = (TaskCostInfoInfo) extProperties.get("costInfo");
			TaskCostInfoFactory.getLocalInstance(ctx).save(costInfo);
		}
		if(extProperties.get("qualityPlan") != null){
			TaskQualityPlanInfo qualityPlanInfo = (TaskQualityPlanInfo) extProperties.get("qualityPlan");
			TaskQualityPlanFactory.getLocalInstance(ctx).save( qualityPlanInfo);
		}
		if(extProperties.get("workResult") != null){
			TaskWorkResultInfo workResultInfo = (TaskWorkResultInfo) extProperties.get("workResult");
			if(workResultInfo.getId() == null){
				workResultInfo.setTaskExt(taskExtInfo);
			}
			TaskWorkResultFactory.getLocalInstance(ctx).save(workResultInfo);
		}
	}
	protected void _deletePropByWBSID(Context ctx, String wbsID)
			throws BOSException, EASBizException {
		FDCScheduleTaskExtInfo taskExtInfo = FDCScheduleTaskExtFactory.getLocalInstance(ctx).
				getFDCScheduleTaskExtInfo(" where wbs.id='"+wbsID+"'");
		_deletePropByTaskExtID(ctx, taskExtInfo.getId().toString());
	}
	protected Map _getProByWBSID(Context ctx, String WBSID)
			throws BOSException, EASBizException {
		Map map = new HashMap();
		map.putAll(getWorkloadLog(ctx, WBSID));
		map.putAll(getCostInfo(ctx, WBSID));
		map.putAll(getMaterialPlan(ctx, WBSID));
		map.putAll(getQualityPlan(ctx, WBSID));
		map.putAll(getWorkResult(ctx, WBSID));
		map.putAll(getQtyResultEntrys(ctx, WBSID));
		map.putAll(getTaskLog(ctx, WBSID));
		map.put("taskExtInfo", getTaskExtInfo(ctx, WBSID));
		return map;
	}
	
	Map qualityDataMap = new HashMap();
	//把 数据填充到qualityDataMap里
	private void initData(String wbsID,Context ctx) throws Exception{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("wbs.id", wbsID));
		view.setFilter(filter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("checkPointEntry.head.longNumber");
		sic.add("checkPointEntry.head.name");
		sic.add("checkPointEntry.head.description");
		sic.add("checkPointEntry.head.id");
		sic.add("checkPointEntry.head.checkCriterion");
		sic.add("checkPointEntry.checkPost.name");
		sic.add("checkPointEntry.checkPost.id");
		sic.add("checkPointEntry.checkProp");
		sic.add("checkPerson.*");
		sic.add("checkCount");
		view.setSelector(sic);
		QualityTaskAssignCollection coll = QualityTaskAssignFactory.getLocalInstance(ctx).getQualityTaskAssignCollection(view);
		
		for(int i=0;i<coll.size();i++){
			QualityTaskAssignInfo qualityTaskAssignInfo = coll.get(i);
			if(qualityTaskAssignInfo!=null && qualityTaskAssignInfo.getCheckPointEntry()!=null
					&& qualityTaskAssignInfo.getCheckPointEntry().getHead()!=null){
				QualityCheckPointInfo head = qualityTaskAssignInfo.getCheckPointEntry().getHead();
				QualityTaskAssignCollection subQualityTaskAssignCollection = getSubQualityTaskAssignCollection(head);
				subQualityTaskAssignCollection.add(qualityTaskAssignInfo);
			}			
		}
	}	
	//传入质量检查项 获取到 专业岗位 职员 下集合
	private Map headMap=new HashMap();
	private QualityTaskAssignCollection getSubQualityTaskAssignCollection(QualityCheckPointInfo head){
		if(headMap.get(head.getId())!=null){
			head=(QualityCheckPointInfo)headMap.get(head.getId());
		}else{
			headMap.put(head.getId(), head);
		}
		QualityTaskAssignCollection subColl=(QualityTaskAssignCollection)qualityDataMap.get(head.getId());
		if(subColl==null){
			subColl=new QualityTaskAssignCollection();
			qualityDataMap.put(head.getId(), subColl);
		}
		return subColl;
	}
	protected void _deletePropByTaskExtIDs(Context ctx, Set extIDs)
			throws BOSException, EASBizException {
		Object[] IDs = extIDs.toArray(); 
		for(int i=0;i<IDs.length;i++){
			_deletePropByTaskExtID(ctx, IDs[i].toString());
		}
	}
	protected void _deletePropByWBSIDs(Context ctx, Set wbsIDs)
			throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("wbs.id",wbsIDs,CompareType.INCLUDE));
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("wbs.id");
		view.setFilter(filter);
		view.setSelector(sic);
		ScheduleNewTaskEntryCollection newEntryCol = ScheduleNewTaskEntryFactory.getLocalInstance(ctx).getScheduleNewTaskEntryCollection(view);
		for(int i=0;i<newEntryCol.size();i++){
			wbsIDs.remove(newEntryCol.get(i).getWbs().getId().toString());
		}
		view = new EntityViewInfo();
		filter.getFilterItems().clear();
		sic.clear();
		filter.getFilterItems().add(new FilterItemInfo("wbs.id",wbsIDs,CompareType.INCLUDE));
		sic.add("wbs.id");
		view.setFilter(filter);
		view.setSelector(sic);
		FDCScheduleTaskExtCollection extCol = FDCScheduleTaskExtFactory.getLocalInstance(ctx).getFDCScheduleTaskExtCollection(view);
		for(int i=0;i<extCol.size();i++){
			_deletePropByWBSID(ctx,extCol.get(i).getWbs().getId().toString());
		}
	}
	protected void _copyFromAdjuestBill(Context ctx, String oldWBSID,String newWBSID) throws BOSException, EASBizException {
		Map map = _getProByWBSID(ctx, oldWBSID);
		FDCWBSInfo wbsInfo = new FDCWBSInfo();
		wbsInfo.setId(BOSUuid.read(newWBSID));
		FDCScheduleTaskExtInfo extInfo = new FDCScheduleTaskExtInfo();
		extInfo.setWbs(wbsInfo);
		extInfo.setId(BOSUuid.read(
				FDCScheduleTaskExtFactory.getLocalInstance(ctx).addnew(extInfo).toString()));
		if(map.get("workloadLog") != null){
			TaskWorkloadLogInfo oldWorkLoadLog = (TaskWorkloadLogInfo) map.get("workloadLog");
			if(oldWorkLoadLog != null){
				TaskWorkloadLogInfo newWorkLoadLog = new TaskWorkloadLogInfo();
				newWorkLoadLog.setId(BOSUuid.create(newWorkLoadLog.getBOSType()));
				newWorkLoadLog.setTaskExt(extInfo);
				newWorkLoadLog.setName(oldWorkLoadLog.getName());
				newWorkLoadLog.setSeq(oldWorkLoadLog.getSeq());
				newWorkLoadLog.setCompletePercent(oldWorkLoadLog.getCompletePercent());
				newWorkLoadLog.setPlanWorkload(oldWorkLoadLog.getPlanWorkload());
				newWorkLoadLog.setMeasureUnit(oldWorkLoadLog.getMeasureUnit());
				newWorkLoadLog.setSeq(oldWorkLoadLog.getSeq());
				TaskWorkloadLogFactory.getLocalInstance(ctx).addnew(newWorkLoadLog);
			}
		}
		if(map.get("costInfo") != null){
			TaskCostInfoInfo oldCostInfo = (TaskCostInfoInfo) map.get("costInfo");
			if(oldCostInfo != null){
				TaskCostInfoInfo newCostInfo = new TaskCostInfoInfo();
				newCostInfo.setId(BOSUuid.create(newCostInfo.getBOSType()));
				newCostInfo.setTaskExt(extInfo);
				newCostInfo.setName(oldCostInfo.getName());
				newCostInfo.setSeq(oldCostInfo.getSeq());
				newCostInfo.setAimCost(oldCostInfo.getAimCost());
				newCostInfo.setActCost(oldCostInfo.getActCost());
				newCostInfo.setCostDeviation(oldCostInfo.getCostDeviation());
				newCostInfo.setCostMemo(oldCostInfo.getCostMemo());
				TaskCostInfoFactory.getLocalInstance(ctx).addnew(newCostInfo);
			}
		}
		if(map.get("materialPlan") != null){
			TaskMaterialPlanInfo oldMaterialPlan = (TaskMaterialPlanInfo) map.get("materialPlan");
			if(oldMaterialPlan != null){
				TaskMaterialPlanInfo newMaterialPlan = new TaskMaterialPlanInfo();
				newMaterialPlan.setId(BOSUuid.create(newMaterialPlan.getBOSType()));
				newMaterialPlan.setTaskExt(extInfo);
				newMaterialPlan.setSeq(oldMaterialPlan.getSeq());
				newMaterialPlan.setName(oldMaterialPlan.getName());
				newMaterialPlan.setDate(oldMaterialPlan.getDate());
				newMaterialPlan.setDescription(oldMaterialPlan.getDescription());
				TaskMaterialPlanFactory.getLocalInstance(ctx).addnew(newMaterialPlan);
			}
		}
		if(map.get("qualityPlan") != null){
			TaskQualityPlanInfo oldQtyPlanInfo = (TaskQualityPlanInfo) map.get("qualityPlan");
			if(oldQtyPlanInfo != null){
				TaskQualityPlanInfo newQtyPlanInfo = new TaskQualityPlanInfo();
				newQtyPlanInfo.setId(BOSUuid.create(oldQtyPlanInfo.getBOSType()));
				newQtyPlanInfo.setTaskExt(extInfo);
				newQtyPlanInfo.setName(oldQtyPlanInfo.getName());
				newQtyPlanInfo.setSeq(oldQtyPlanInfo.getSeq());
				TaskQualityPreventionEntryCollection oldEntryCol = oldQtyPlanInfo.getPreventionEntrys();
				TaskQualityPreventionEntryCollection newEntryCol = new TaskQualityPreventionEntryCollection();
				for(int i=0;i<oldEntryCol.size();i++){
					TaskQualityPreventionEntryInfo oldEntryInfo = oldEntryCol.get(i);
					TaskQualityPreventionEntryInfo newEntryInfo = new TaskQualityPreventionEntryInfo();
					newEntryInfo.setId(BOSUuid.create(newEntryInfo.getBOSType()));
					newEntryInfo.setSeq(oldEntryInfo.getSeq());
					newEntryInfo.setParent(newQtyPlanInfo);
					newEntryInfo.setPrevention(oldEntryInfo.getPrevention());
					newEntryInfo.setName(oldEntryInfo.getName());
					newEntryCol.add(newEntryInfo);
				}
				newQtyPlanInfo.getPreventionEntrys().addCollection(newEntryCol);
				TaskQualityPlanFactory.getLocalInstance(ctx).addnew(newQtyPlanInfo);
			}
		}
	}
}