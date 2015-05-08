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
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSCollection;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
//import com.kingdee.eas.fdc.schedule.PrefixWBSEntryCollection;
//import com.kingdee.eas.fdc.schedule.PrefixWBSEntryInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSTree;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.eas.fdc.schedule.TaskTypeFactory;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.WBSAdjustManager;
import com.kingdee.eas.fdc.schedule.WBSCodeRuleFactory;
import com.kingdee.eas.fdc.schedule.WBSTemplateEntryCollection;
import com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryCollection;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSTree.FDCWBSTreeNode;
import com.kingdee.eas.fdc.schedule.WBSAdjustManager.WBSAdjustManagerItem;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.util.FilterUtility;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class FDCWBSControllerBean extends AbstractFDCWBSControllerBean {
	private static final String UPDATE_WBSNUM_SQL = "update t_sch_fdcwbs set fnumber=?,flongnumber=?,flevel=?,fisleaf=?,fparentid=? where fid=?";
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.schedule.app.FDCWBSControllerBean");

	/**
	 * 重载基类校验编码重复代码，改为同一工程项目下的编码不能重复
	 */
	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		FDCWBSInfo wbsInfo = (FDCWBSInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",wbsInfo.getCurProject().getId(),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("longNumber",wbsInfo.getLongNumber(),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("id",wbsInfo.getId(),CompareType.EQUALS));
		if(this._exists(ctx, filter)){
			throw new EASBizException(EASBizException.CHECKDUPLICATED,	new Object[] { wbsInfo.getLongNumber() });
		}
	}

	/**
	 * 先把分录做好，再做分录中的前置任务分录
	 */
	protected void _importWBSTemplate(Context ctx, Map param)throws BOSException, EASBizException {
		CurProjectInfo prjInfo = (CurProjectInfo) param.get("prj4Import");
		FDCWBSInfo allParentInfo = (FDCWBSInfo) param.get("parent4Import");
		Map templateMap = (Map) param.get("selectedWBS");
		Map FDCWBSMap = new HashMap();
		// 使用这个参数使树有序，保证先处理父节点再处理孩子结点
		ArrayList keyNums = (ArrayList) param.get("keys");
		for (int i = 0; i < keyNums.size(); i++) {
			FDCWBSInfo wbsInfo = new FDCWBSInfo();
			FDCWBSInfo parentInfo;
			WBSTemplateEntryInfo templateInfo = (WBSTemplateEntryInfo) templateMap.get(keyNums.get(i));
			WBSTemplateEntryInfo templateParentInfo = findTemplateParent(templateMap, templateInfo.getNumber());
			wbsInfo.setCurProject(prjInfo);
			wbsInfo.setName(templateInfo.getName());
			wbsInfo.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
			wbsInfo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
			wbsInfo.setCreateTime(FDCDateHelper.getServerTimeStamp());
			wbsInfo.setId(BOSUuid.create(wbsInfo.getBOSType()));
			wbsInfo.setIsFromTemplate(true);
			if (templateParentInfo == null) {
				wbsInfo.setParent(allParentInfo);
			} else {
				parentInfo = (FDCWBSInfo) FDCWBSMap.get(templateParentInfo.getNumber());
				wbsInfo.setParent(parentInfo);
			}
			if (wbsInfo.getParent() == null) {
				wbsInfo.setLevel(1);
				wbsInfo.setLongNumber(wbsInfo.getNumber());
			} else {
				wbsInfo.setLevel(wbsInfo.getParent().getLevel() + 1);
				wbsInfo.setLongNumber(wbsInfo.getParent().getLongNumber() + "!"	+ wbsInfo.getNumber());
			}
			String newNumber = WBSCodeRuleFactory.getLocalInstance(ctx).createNewNumber(wbsInfo.getLevel(),
					findWBSMaxSibNum(FDCWBSMap, templateMap,templateParentInfo,allParentInfo,wbsInfo, keyNums,ctx) + 1);
			wbsInfo.setNumber(newNumber);
			wbsInfo.setIsLocked(templateInfo.isIsLocked());
			//导入的WBS预估工期不能大于父WBS的预估工期    add by warship at 2010/07/08
			if(allParentInfo != null){
				if(allParentInfo.getEstimateDays() == 0)
					wbsInfo.setEstimateDays(0);
				else if(allParentInfo.getEstimateDays()<templateInfo.getEstimateDays())
					wbsInfo.setEstimateDays(allParentInfo.getEstimateDays());
				else 
					wbsInfo.setEstimateDays(templateInfo.getEstimateDays());
			}else{
				wbsInfo.setEstimateDays(templateInfo.getEstimateDays());
			}
			//通过模板引入的项目WBS任务的isUnVisible属性默认为false,也就是不隐藏，即显示 by cassiel 210-06-16
			wbsInfo.setIsUnVisible(false);
			FDCWBSMap.put(templateInfo.getNumber(), wbsInfo);
		}
		// 开始设置编码、父节点、前置任务分录
//		for (int i = 0; i < keyNums.size(); i++) {
//			WBSTemplateEntryInfo templateInfo = (WBSTemplateEntryInfo) templateMap.get(keyNums.get(i));
//			for (int j = 0; j < templateInfo.getPrefixEntrys().size(); j++) {
//				PrefixWBSEntryInfo preTaskInfo = new PrefixWBSEntryInfo();
//				WBSTemplatePrefixEntryInfo templatePreTaskInfo = templateInfo.getPrefixEntrys().get(j);
//				preTaskInfo.setLinkDay(templatePreTaskInfo.getLinkDay());
//				preTaskInfo.setLinkType(templatePreTaskInfo.getLinkType());
//				preTaskInfo.setSeq(templatePreTaskInfo.getSeq());
//				preTaskInfo.setParent((FDCWBSInfo) FDCWBSMap.get(templatePreTaskInfo.getParent().getNumber()));
//				preTaskInfo.setPrefixWBS((FDCWBSInfo) FDCWBSMap.get(templatePreTaskInfo.getPrefixNode().getNumber()));
//				((FDCWBSInfo) FDCWBSMap.get(keyNums.get(i))).getEntrys().add(preTaskInfo);
//			}
//		}
		// FDCWBSCollection fdcwbsCol = new FDCWBSCollection();

		for (int i = 0; i < keyNums.size(); i++) {
			// fdcwbsCol.add((FDCWBSInfo)FDCWBSMap.get(keyNums.get(i)));
			_addnew(ctx, (FDCWBSInfo) FDCWBSMap.get(keyNums.get(i)));
		}

	}

	private WBSTemplateEntryInfo findTemplateParent(Map templateEntry,
			String childNum) {
		Object[] entrys = templateEntry.keySet().toArray();
		for (int i = 0; i < entrys.length; i++) {
			String tempNumber = ((WBSTemplateEntryInfo) templateEntry.get(entrys[i])).getNumber();
			if (childNum.lastIndexOf("!") > 0
					&& tempNumber.equals(childNum.substring(0,childNum.lastIndexOf('!')))
					&& !(childNum.equals(entrys[i]))) {
				return (WBSTemplateEntryInfo) templateEntry.get(entrys[i]);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param wbsMap
	 *            number-fdcwbs对应关系
	 * @param parentInfo
	 *            模板父节点
	 *            需要考虑的情况比较多
	 * @return
	 * @throws BOSException 
	 */
	private int findWBSMaxSibNum(Map wbsMap, Map templateMap,WBSTemplateEntryInfo tempParentInfo,
			FDCWBSInfo allParentInfo,FDCWBSInfo presentNode, ArrayList keys,Context ctx) throws BOSException {
		int max = 0;
		String str = new String();
		int exitsMaxSib = 0;
		//在已有下级节点的节点下添加，要查询该节点最大孩子结点number
		if(allParentInfo != null){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select  top 1 fnumber from t_sch_fdcwbs where flongnumber=(" +
					"	select max(flongnumber) longnumber from t_sch_fdcwbs where fparentid=?" +
					"	and FCurProjectID=?)") ;
			builder.addParam(allParentInfo.getId().toString());
			builder.addParam(presentNode.getCurProject().getId().toString());
			IRowSet rs = builder.executeQuery();
			try {
				while(rs.next()){
					exitsMaxSib = Integer.parseInt(rs.getString("fnumber"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select top 1 fnumber from t_sch_fdcwbs where flongnumber=(" +
					"	select max(flongnumber) longnumber from t_sch_fdcwbs where flevel=? " +
					"	and FCurProjectID=?)");
			builder.addParam(new Integer(1));
			builder.addParam(presentNode.getCurProject().getId().toString());
			IRowSet rs = builder.executeQuery();
			try {
				while(rs.next()){
					exitsMaxSib = Integer.parseInt(rs.getString("fnumber"));
					if(presentNode.getLevel()>1)
						exitsMaxSib = 0;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < keys.size(); i++) {
			WBSTemplateEntryInfo tempChildInfo = (WBSTemplateEntryInfo) templateMap
					.get(keys.get(i));
			FDCWBSInfo wbsInfo = (FDCWBSInfo) wbsMap.get(keys.get(i));
			if (wbsInfo == null)
				continue;
			//添加选中节点的顶级节点
			if (tempParentInfo == null) {
				//没有所选节点的孩子结点
				if(allParentInfo == null && wbsInfo.getLevel()==1){
					int childNum = Integer.parseInt(wbsInfo.getNumber());
					if (childNum > max) {
						max = childNum;
						str = tempChildInfo.getNumber();
					}
				}else if(allParentInfo != null && wbsInfo.getLevel()-1 == allParentInfo.getLevel()){
					int childNum = Integer.parseInt(wbsInfo.getNumber());
					childNum = childNum>exitsMaxSib ? childNum:exitsMaxSib;
					if (childNum > max) {
						max = childNum;
						str = tempChildInfo.getNumber();
					}
				}
			} else if ((tempChildInfo.getLevel() - 1 == tempParentInfo.getLevel())
					&& tempChildInfo.getNumber().startsWith(tempParentInfo.getNumber())) {
				int childNum = Integer.parseInt(wbsInfo.getNumber());
				if (childNum > max) {
					max = childNum;
					str = tempChildInfo.getNumber();
				}
			}
		}
		if (wbsMap.get(str) != null) {
			((FDCWBSInfo) wbsMap.get(str)).getLongNumber();
			return Integer.parseInt(((FDCWBSInfo) wbsMap.get(str)).getNumber());
		} else
			return exitsMaxSib;
	}

	protected void checkLNForTree(Context ctx, TreeBaseInfo treeBaseInfo)
			throws BOSException, EASBizException, TreeBaseException {
		FilterInfo lNfilter = new FilterInfo();
		lNfilter.getFilterItems().add(
				new FilterItemInfo(IFWEntityStruct.tree_LongNumber,
						treeBaseInfo.getLongNumber()));
		lNfilter.getFilterItems().add(
				new FilterItemInfo(IFWEntityStruct.coreBase_ID, treeBaseInfo
						.getId().toString(), CompareType.NOTEQUALS));
		lNfilter.getFilterItems().add(
				new FilterItemInfo("curProject", ((FDCWBSInfo) treeBaseInfo)
						.getCurProject().getId().toString()));
		lNfilter.setMaskString("#0 AND #1 AND #2");

		// CU隔离
		FilterInfo filterCU = getFilterForDefaultCU(ctx, treeBaseInfo);
		if (FilterUtility.hasFilterItem(filterCU)) {
			lNfilter.mergeFilter(filterCU, "AND");
		}

		if (exists(ctx, lNfilter)) {
//			throw new TreeBaseException(
//					TreeBaseException.CHECKNUMBERDUPLICATED,
//					new Object[] { treeBaseInfo.getNumber() });
		}
	}

	private void setNodeValue(String keyNum, Map nodeMap, Map templateMap,
			Context ctx) throws ScheduleException, BOSException {
		if (nodeMap.get(keyNum) != null)
			return;
		WBSTemplateEntryInfo parentTemplateNode = findTemplateParent(
				templateMap, keyNum);
		if (parentTemplateNode == null) {
			FDCWBSInfo nodeInfo = new FDCWBSInfo();
			nodeInfo.setId(BOSUuid.create(nodeInfo.getBOSType()));
			nodeInfo.setParent((FDCWBSInfo) nodeMap.get(keyNum));
			// String newNum =
			// WBSCodeRuleFactory.getLocalInstance(ctx).createNewNumber(
			// parentTemplateNode.getLevel()-1, findWBSMaxSibNum(nodeMap,
			// templateMap, parentTemplateNode)+1);
			FDCWBSInfo nodeParent = (FDCWBSInfo) nodeMap.get(parentTemplateNode
					.getNumber());
			// nodeInfo.setNumber(newNum);
			nodeInfo.setParent(nodeParent);
			nodeMap.put(keyNum, nodeInfo);
		} else {
			setNodeValue(parentTemplateNode.getNumber(), nodeMap, templateMap,
					ctx);
		}
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		FDCWBSInfo wbsInfo = null;			
		if(model != null && model instanceof FDCWBSInfo){
			wbsInfo = (FDCWBSInfo) model;
		}
		if(wbsInfo.getId()!=null){
			//新增的wbs不做校验  by sxhong
			checkEstDay(ctx, wbsInfo);
			checkTaskType(ctx, wbsInfo);
//			checkAdminDept(ctx, wbsInfo);
		}else{
			//新增的wbs要校验输入预估工期不能大于父类的预估工期 add by warship at 2010/07/02
			checkEstDay(ctx, wbsInfo);
		}
		IObjectPK pk = super._submit(ctx, model);
		
//		同步任务名称，责任部门等
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		Set taskIds = new HashSet();
		Set states = new HashSet();
		states.add(ScheduleStateEnum.SAVED_VALUE);
		states.add(ScheduleStateEnum.SUBMITTED_VALUE);
		EntityViewInfo view  = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("wbs.id",pk.toString(),CompareType.INCLUDE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.state",states,CompareType.INCLUDE));
		FDCScheduleTaskCollection taskCol = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		for(int i=0;i<taskCol.size();i++){
			taskIds.add(taskCol.get(i).getId().toString());
		}
		builder.clear();
		if(taskIds!=null&&taskIds.size()>0){
			String tmp = null;
			builder.appendSql("update t_sch_fdcscheduletask set FPlanDeptID=?,FAdminDeptID=?,FAdminPersonID=?,FName_l2=? where ");
			//对本级责任人、责任部门等做是否为空的判断.. --jiadong
			if(wbsInfo.getAdminDept() !=null){
				tmp = wbsInfo.getAdminDept().getId().toString();
			}
			builder.addParam(tmp);
			
			if(wbsInfo.getRespDept() !=null){
				tmp = wbsInfo.getRespDept().getId().toString();
			}
			builder.addParam(tmp);
			
			if(wbsInfo.getAdminPerson()!=null){
				tmp = wbsInfo.getAdminPerson().getId().toString();
			}
			builder.addParam(tmp);
			
			builder.addParam(wbsInfo.getName().toString());
			builder.appendParam("FID", taskIds.toArray());
			builder.executeUpdate();
		}
		
		return pk;
	
		/*if(wbsInfo.getTaskType()!= null&&wbsInfo.getTaskType().getId().toString().equals(TaskTypeInfo.TASKTYPE_MAINTASK)){
			PrefixWBSEntryCollection entryCol = wbsInfo.getEntrys();
			Set prefixIDs = new HashSet();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			for(int i=0;i<entryCol.size();i++){
				prefixIDs.add(entryCol.get(i).getPrefixWBS().getId().toString());
			}
			filter.getFilterItems().add(new FilterItemInfo("id",prefixIDs,CompareType.INCLUDE));
			sic.add("taskType.*");
			view.setFilter(filter);
			view.setSelector(sic);
			FDCWBSCollection wbsInfoCol = getFDCWBSCollection(ctx, view);
			for(int i=0;i<wbsInfoCol.size();i++){
				TaskTypeInfo taskTypeInfo = wbsInfoCol.get(i).getTaskType();
				if(taskTypeInfo != null
						&& TaskTypeInfo.TASKTYPE_MAINTASK.equals(taskTypeInfo.getId().toString())){
					prefixIDs.remove(wbsInfoCol.get(i).getId().toString());
				}
			}
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("delete from T_SCH_PrefixWBSEntry where FParentID=?" +
					"	and ");
			builder.addParam(pk.toString());
			if(prefixIDs.size() > 0){
				builder.appendParam("FPrefixWBSID",prefixIDs.toArray());
				builder.executeUpdate();
			}
		}*/
	}
	/**
	 * 校验任务专业属性不能低于子节点的任务专业属性
	 * @param ctx
	 * @param wbsInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkTaskType(Context ctx, FDCWBSInfo wbsInfo)
			throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select min(t1.flevel) flevel from t_sch_tasktype t1"+
				"	inner join t_sch_fdcwbs wbs on wbs.ftasktypeid=t1.fid"+
				"	where wbs.fparentid=? and t1.flevel is not null");
		builder.addParam(wbsInfo.getId().toString());
		IRowSet rowSet = builder.executeQuery();
		if(wbsInfo.getTaskType()==null||wbsInfo.getTaskType().getId()!=null){
			return;
		}
		int level = TaskTypeFactory.getLocalInstance(ctx).getTaskTypeInfo(
				new ObjectUuidPK(BOSUuid.read(wbsInfo.getTaskType().getId().toString()))).getLevel();
		int childLevel = 1000;
		try {
			while(rowSet.next()){
				childLevel = rowSet.getInt("flevel");
			}
		} catch (SQLException e) {
			throw new EASBizException(new NumericExceptionSubItem("307","任务级别不能低于子节点的任务级别！"));
		}
		if(childLevel!=0&&level > childLevel){
			throw new EASBizException(new NumericExceptionSubItem("307","任务级别不能低于子节点的任务级别！"));
		}
	}

	/**
	 * 校验归口部门不能低于子节点的归口部门
	 * @param ctx
	 * @param wbsInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkAdminDept(Context ctx, FDCWBSInfo wbsInfo)
			throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select min(org.flevel) flevel from t_org_baseunit org " +
				"	inner join t_sch_fdcwbs wbs on wbs.fadmindeptid=org.fid " +
				"	where wbs.fparentid=? and org.flevel is not null");
		builder.addParam(wbsInfo.getId().toString());
		IRowSet rowSet = builder.executeQuery();
		
		int level = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(
				new ObjectUuidPK(BOSUuid.read(wbsInfo.getAdminDept().getId().toString()))).getLevel();
		int childMinLevel = 1000;
		try {
			while(rowSet.next()){
				childMinLevel = rowSet.getInt("flevel");
			}
		} catch (SQLException e) {
			throw new EASBizException(new NumericExceptionSubItem("306","归口部门级别不能低于子节点归口部门级别！"));
		}
		if(childMinLevel!=0&&level > childMinLevel){
			throw new EASBizException(new NumericExceptionSubItem("306","归口部门级别不能低于子节点归口部门级别！"));
		}
	}

	/**
	 * 校验预估工期不能小于子节点的预估工期，且不能大于父节点的预估工期
	 * @param ctx
	 * @param wbsInfo
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void checkEstDay(Context ctx, FDCWBSInfo wbsInfo)
			throws EASBizException, BOSException {
		int estDays = wbsInfo.getEstimateDays();
		if( wbsInfo.getParent() != null && estDays > wbsInfo.getParent().getEstimateDays()){
			throw new EASBizException(new NumericExceptionSubItem("305","子节点预估工期不能大于父节点预估工期")); 
		}
		if(wbsInfo.getId() != null){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select max(FEstimateDays) estDay from t_sch_fdcwbs where fparentid=? and FEstimateDays is not null");
			builder.addParam(wbsInfo.getId().toString());
			IRowSet rowSet = builder.executeQuery();
			int maxChildExtDay = 2;
			try {
				while(rowSet.next()){
					maxChildExtDay = rowSet.getInt("estDay");
				}
			} catch (SQLException e) {
				throw new EASBizException(new NumericExceptionSubItem("306","本节点预估工期不能小于子节点中最大预估工期"));
			}
			if(estDays < maxChildExtDay){
				throw new EASBizException(new NumericExceptionSubItem("306","本节点预估工期不能小于子节点中最大预估工期"));
			}
		}
	}
	
	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		IObjectPK pk = super._addnew(ctx, model);
		//update index
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCWBS set findex=(select isnull(max(a.findex),0)+1 from T_SCH_FDCWBS a ,T_SCH_FDCWBS b where a.fcurprojectid=b.fcurprojectid and a.flongnumber<b.flongNumber and b.fid=T_SCH_FDCWBS.fid ) where fid=? ");
		builder.addParam(pk.toString());
		builder.execute();
		builder.clear();
		//更新后续的索引号
		builder.appendSql("update T_SCH_FDCWBS set findex=findex+1  where fid in (select a.fid from T_SCH_FDCWBS a ,T_SCH_FDCWBS b where a.fcurprojectid=b.fcurprojectid and a.findex>=b.findex and a.fid<>b.fid and b.fid=? )");
		builder.addParam(pk.toString());
		builder.execute();
		return pk;
	}
	//批量修改责任部门 by cassiel_peng 2010-04-21
	protected void _batChangeRespDept(Context ctx, Set wbsIds, String respDeptId) throws BOSException, EASBizException {
		//需求规约：取消现有计划部门的上级任务对下级任务的计划部门的控制逻辑 by cassiel_peng 2010-04-21
//		try {
//			batVerifyAdminDept(wbsIds, respDeptId,ctx);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_sch_fdcwbs set FRespDeptID=? where");
		builder.addParam(respDeptId);
		builder.appendParam("fid", wbsIds.toArray());
		builder.executeUpdate();
		
		Set taskIds = new HashSet();
		Set states = new HashSet();
		states.add(ScheduleStateEnum.SAVED_VALUE);
		states.add(ScheduleStateEnum.SUBMITTED_VALUE);
		EntityViewInfo view  = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("wbs.id",wbsIds,CompareType.INCLUDE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.state",states,CompareType.INCLUDE));
		FDCScheduleTaskCollection taskCol = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		for(int i=0;i<taskCol.size();i++){
			taskIds.add(taskCol.get(i).getId().toString());
		}
		if(taskIds!=null&&taskIds.size()>0){
			builder.clear();
			builder.appendSql("update t_sch_fdcscheduletask set FAdminDeptID=? where ");
			builder.addParam(respDeptId);
			builder.appendParam("FID", taskIds.toArray());
			builder.executeUpdate();
		}
	}
	protected void _batChangeAdminDept(Context ctx, Set wbsIDs,String adminDeptID) throws BOSException, EASBizException {
//		try {
//			batVerifyAdminDept(wbsIDs, adminDeptID,ctx);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_sch_fdcwbs set FAdminDeptID=? where");
		builder.addParam(adminDeptID);
		builder.appendParam("fid", wbsIDs.toArray());
		builder.executeUpdate();
		
		Set taskIds = new HashSet();
		Set states = new HashSet();
		states.add(ScheduleStateEnum.SAVED_VALUE);
		states.add(ScheduleStateEnum.SUBMITTED_VALUE);
		EntityViewInfo view  = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("wbs.id",wbsIDs,CompareType.INCLUDE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.state",states,CompareType.INCLUDE));
		FDCScheduleTaskCollection taskCol = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		for(int i=0;i<taskCol.size();i++){
			taskIds.add(taskCol.get(i).getId().toString());
		}
		if(taskIds!=null&&taskIds.size()>0){
			builder.clear();
			builder.appendSql("update t_sch_fdcscheduletask set FPlanDeptID=? where ");
			builder.addParam(adminDeptID);
			builder.appendParam("FID", taskIds.toArray());
			builder.executeUpdate();
		}
		
	}

	protected void _batChangeTaskPro(Context ctx, Set wbsIDs, String taskProID)	throws BOSException, EASBizException {
		try {
			batVerifyTaskPro(wbsIDs, taskProID,ctx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_sch_fdcwbs set FTaskTypeID=? where");
		builder.addParam(taskProID);
		builder.appendParam("fid", wbsIDs.toArray());
		builder.executeUpdate();
	}
	
	/**
	 * 先使用orMapp实现，有时间使用sql进行脚本优化
	 * @param wbsIDs
	 * @param taskProID
	 * @param ctx
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException 
	 */
	private void batVerifyTaskPro(Set wbsIDs,String taskProID,Context ctx) throws BOSException, EASBizException, SQLException{
		String result1 = "所选节点中的一级节点任务级别必须为主项节点任务！";
		String result2 = "所选节点的上级节点任务级别未设置或所选任务级别非上级节点对应的任务级别的直接下级属性！";
		TaskTypeInfo taskType;
		Set parentTaskTypes = new HashSet();
		taskType = TaskTypeFactory.getLocalInstance(ctx).getTaskTypeInfo(new ObjectUuidPK(BOSUuid.read(taskProID)));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",wbsIDs,CompareType.INCLUDE));
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("parent.id");
		sic.add("parent.taskType.id");
		sic.add("parent.taskType.level");
		sic.add("parent.taskType.longNumber");
		view.setFilter(filter);
		view.setSelector(sic);
		FDCWBSCollection wbsCol = FDCWBSFactory.getLocalInstance(ctx).getFDCWBSCollection(view);
		for(int i=0;i<wbsCol.size();i++){
			String wbsID = null;
			if(wbsCol.get(i).getParent() != null){
				wbsID = wbsCol.get(i).getParent().getId().toString();
				if(!wbsIDs.contains(wbsID)){
					if(wbsCol.get(i).getParent().getTaskType() == null)
						throw new EASBizException(new NumericExceptionSubItem("302",result2));
					parentTaskTypes.add(wbsCol.get(i).getParent().getTaskType());
				}
			}else{
				if(!TaskTypeInfo.TASKTYPE_MAINTASK.equals(taskProID)){
					throw new EASBizException(new NumericExceptionSubItem("301",result1));	
				}
			}
		}
		parentTaskTypes.remove(null);
		for(Iterator ite = parentTaskTypes.iterator();ite.hasNext();){
			TaskTypeInfo parentTaskType = (TaskTypeInfo) ite.next();
			if(((parentTaskType.getLevel()+1) != taskType.getLevel())
					&& (parentTaskType.getLevel()!= taskType.getLevel())
					|| (!taskType.getLongNumber().startsWith(parentTaskType.getLongNumber()))){
				throw new EASBizException(new NumericExceptionSubItem("302",result2));
			}
		}
		int level = TaskTypeFactory.getLocalInstance(ctx).getTaskTypeInfo(
				new ObjectUuidPK(BOSUuid.read(taskProID))).getLevel();
		int childLevel = 1000;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select min(t1.flevel) level2 from t_sch_tasktype t1"+
				"	inner join t_sch_fdcwbs wbs on wbs.ftasktypeid=t1.fid"+
				"	where t1.flevel is not null and ");
		builder.appendParam("wbs.fparentid",wbsIDs.toArray());
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next()){
			childLevel = rowSet.getInt("level2");
		}
		if(childLevel!=0&&level > childLevel){
			//没有子节点的不用判断 childLevel=0 的时候  by sxhong
			throw new EASBizException(new NumericExceptionSubItem("308","任务级别不能低于子节点任务级别！"));
		}
	}
	/**
	 * 先使用orMapp实现，有时间使用sql进行脚本优化
	 * @param wbsIDs
	 * @param taskProID
	 * @param ctx
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException 
	 */
	private void batVerifyAdminDept(Set wbsIDs,String adminDeptID,Context ctx) throws BOSException, EASBizException, SQLException{
		String result1 = "所选节点的上级节点归口部门未设置或所选归口部门非上级节点对应归口部门的下级部门！";
//		String result2 = "所选归口部门不能高于上级节点归口部门！";
		Set parentAdminDepts = new HashSet();
		FullOrgUnitInfo adminDept;
		adminDept = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new ObjectUuidPK(BOSUuid.read(adminDeptID)));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",wbsIDs,CompareType.INCLUDE));
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("parent.id");
		sic.add("parent.adminDept.id");
		sic.add("parent.adminDept.level");
		sic.add("parent.adminDept.longNumber");
		view.setFilter(filter);
		view.setSelector(sic);
		FDCWBSCollection wbsCol = FDCWBSFactory.getLocalInstance(ctx).getFDCWBSCollection(view);
		for(int i=0;i<wbsCol.size();i++){
			String wbsID = null;
			if(wbsCol.get(i).getParent() != null){
				wbsID = wbsCol.get(i).getParent().getId().toString();
				if( !wbsIDs.contains(wbsID)){
					if(wbsCol.get(i).getParent().getAdminDept() == null)
						throw new EASBizException(new NumericExceptionSubItem("304",result1)); 
					parentAdminDepts.add(wbsCol.get(i).getParent().getAdminDept());
				}
			}
		}
		parentAdminDepts.remove(null);
		for(Iterator ite = parentAdminDepts.iterator();ite.hasNext();){
			FullOrgUnitInfo parentAdminDept = (FullOrgUnitInfo) ite.next();
			if(parentAdminDept.getLevel() > adminDept.getLevel()
					||(!adminDept.getLongNumber().startsWith(parentAdminDept.getLongNumber()))){
				throw new EASBizException(new NumericExceptionSubItem("304",result1));
			}
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select min(org.flevel) flevel from t_org_baseunit org " +
				"	inner join t_sch_fdcwbs wbs on wbs.fadmindeptid=org.fid " +
				"	where org.flevel is not null and ");
		builder.appendParam("wbs.fparentid", wbsIDs.toArray());
		IRowSet rowSet = builder.executeQuery();
		int level = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(
				new ObjectUuidPK(BOSUuid.read(adminDeptID))).getLevel();
		int childLevel = 1000;
		while(rowSet.next()){
			childLevel = rowSet.getInt("flevel");
		}
		if(childLevel!=0&&level > childLevel){
			//同专业属性 by sxhong
			throw new EASBizException(new NumericExceptionSubItem("307","归口部门任务级别不能低于子节点归口部门任务级别！"));
		}
	}

	protected void _batChangeAdminPerson(Context ctx, Set wbsIds,String adminPersonID) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCWBS set FAdminPersonID=? where ");
		builder.addParam(adminPersonID);
		builder.appendParam("FID",wbsIds.toArray());
		builder.executeUpdate();
		
		Set taskIds = new HashSet();
		Set states = new HashSet();
		states.add(ScheduleStateEnum.SAVED_VALUE);
		states.add(ScheduleStateEnum.SUBMITTED_VALUE);
		EntityViewInfo view  = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("wbs.id",wbsIds,CompareType.INCLUDE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.state",states,CompareType.INCLUDE));
		FDCScheduleTaskCollection taskCol = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		for(int i=0;i<taskCol.size();i++){
			taskIds.add(taskCol.get(i).getId().toString());
		}
		if(taskIds!=null&&taskIds.size()>0){
			builder.clear();
			builder.appendSql("update t_sch_fdcscheduletask set FAdminPersonID=? where ");
			builder.addParam(adminPersonID);
			builder.appendParam("FID", taskIds.toArray());
			builder.executeUpdate();
		}
	}

	protected Map _getTemplateFromFDCWBS(Context ctx, String prjId)	throws BOSException, EASBizException {
		Map map = new HashMap();
//		以下两个Map为设置前置节点使用(key为FDCWBS的ID)
		Map fdcWBSMap = new HashMap();
		Map wbsTemplateMap = new HashMap();
		WBSTemplateEntryCollection tempCol = new WBSTemplateEntryCollection();
		
		EntityViewInfo view = new EntityViewInfo();
		SorterItemCollection soc = new SorterItemCollection();
		SelectorItemCollection sic = new SelectorItemCollection();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",prjId,CompareType.EQUALS));
		view.setFilter(filter);
		soc.add(new SorterItemInfo("longNumber"));
		view.setSorter(soc);
		sic.add("*");
		sic.add("entrys.*");
		view.setSelector(sic);
		
		FDCWBSCollection wbsCol = FDCWBSFactory.getLocalInstance(ctx).getFDCWBSCollection(view);
		WBSTemplateEntryInfo lastWBSTempNode = null;
//		第一次循环，设置除前置任务之外的属性
		for(int i = 0;i<wbsCol.size();i++){
			FDCWBSInfo wbsInfo = wbsCol.get(i);
			WBSTemplateEntryInfo templateEntryInfo = new WBSTemplateEntryInfo();
			templateEntryInfo.setId(BOSUuid.create(templateEntryInfo.getBOSType()));
			templateEntryInfo.setName(wbsInfo.getName());
			templateEntryInfo.setLevel(wbsInfo.getLevel());
			templateEntryInfo.setEstimateDays(wbsInfo.getEstimateDays());
			String wbsTemplateNum = null;
//			作如下判断：
//			1.第一个节点；
//			2.同级节点；
//			3.非同级节点:
//				(1)last级别 < 当前级别(添加下级)
//				(2)last级别 > 当前级别(需获取上一同等级节点);
			if(lastWBSTempNode == null){
				wbsTemplateNum = WBSCodeRuleFactory.getLocalInstance(ctx).createNewNumber(1, 1);
			}else if(lastWBSTempNode.getLevel() == templateEntryInfo.getLevel()){
				String lastNodeNum = lastWBSTempNode.getNumber();
				String newNum = WBSCodeRuleFactory.getLocalInstance(ctx).createNewNumber(
						lastWBSTempNode.getLevel(), getSLMaxNumByLongNum(lastNodeNum) + 1);
				if(lastWBSTempNode.getLevel() == 1){		//第一级特殊处理
					wbsTemplateNum = newNum;
				}else{
					wbsTemplateNum = lastNodeNum.substring(0,lastNodeNum.lastIndexOf("!")) + "!" +newNum;
				}
			}else if(lastWBSTempNode.getLevel() < templateEntryInfo.getLevel()){
				wbsTemplateNum = lastWBSTempNode.getNumber() + "!" + WBSCodeRuleFactory.getLocalInstance(ctx).
					createNewNumber(templateEntryInfo.getLevel(), 1);
			}else if(lastWBSTempNode.getLevel() > templateEntryInfo.getLevel()){
//				WBSTemplateEntryInfo sibInfo = findTemplateParent(wbsTemplateMap, lastWBSTempNode.getNumber());
				String parentNum = getParentNumByLongNum(templateEntryInfo.getLevel(), lastWBSTempNode.getNumber());
				String newNum = WBSCodeRuleFactory.getLocalInstance(ctx).createNewNumber(
						templateEntryInfo.getLevel(), getSLMaxNumByLevel(templateEntryInfo.getLevel(), wbsTemplateMap, parentNum)+1);
				if(templateEntryInfo.getLevel() >1){
					wbsTemplateNum = parentNum + "!" + newNum;
				}else{
					wbsTemplateNum = newNum;
				}
			}
			templateEntryInfo.setNumber(wbsTemplateNum);
			lastWBSTempNode = templateEntryInfo;
			fdcWBSMap.put(wbsInfo.getId().toString(), wbsInfo);
			wbsTemplateMap.put(wbsInfo.getId().toString(), templateEntryInfo);
			tempCol.add(templateEntryInfo);
		}
//		第二次循环，设置前置任务分录
		/*for(int i = 0;i<wbsCol.size();i++){
			FDCWBSInfo wbsParentInfo = wbsCol.get(i);
			if(wbsParentInfo.getEntrys().size() < 1) continue;
			PrefixWBSEntryCollection wbsEntryCol = wbsParentInfo.getEntrys();
			WBSTemplateEntryInfo tempParentInfo = (WBSTemplateEntryInfo) wbsTemplateMap.get(wbsParentInfo.getId().toString());
			WBSTemplatePrefixEntryCollection tempParentPreEntry = tempParentInfo.getPrefixEntrys();
			for(int j=0;j<wbsParentInfo.getEntrys().size();j++){
				PrefixWBSEntryInfo wbsEntryInfo = wbsEntryCol.get(j);
				WBSTemplatePrefixEntryInfo tempPreEntryInfo = new WBSTemplatePrefixEntryInfo();
				tempPreEntryInfo.setId(BOSUuid.create(tempPreEntryInfo.getBOSType()));
				tempPreEntryInfo.setLinkType(wbsEntryInfo.getLinkType());
				tempPreEntryInfo.setLinkDay(wbsEntryInfo.getLinkDay());
				tempPreEntryInfo.setPrefixNode((WBSTemplateEntryInfo) wbsTemplateMap.get(wbsEntryInfo.getPrefixWBS().getId().toString()));
				tempPreEntryInfo.setParent(tempParentInfo);
				tempParentPreEntry.add(tempPreEntryInfo);
			}
		}*/
		map.put("tempWBSEntrys", tempCol);
		return map;
	}
//	根据编码获取同级的最大编号
	private int getSLMaxNumByLongNum(String longNumber){
		int maxNum = 0;
		if(longNumber != null){
			String []numStrs = longNumber.split("!");
			maxNum = Integer.parseInt(numStrs[numStrs.length -1]);
		}
		return maxNum;
	}
//	根据级别获取同级的最大编号(注意需要判断是同一上级)
	private int getSLMaxNumByLevel(int sibLevel, Map templateMap,String parentNum){
		int maxNum = 0;
		
		Iterator templateIte = templateMap.values().iterator();
		while(templateIte.hasNext()){
			WBSTemplateEntryInfo entryInfo = (WBSTemplateEntryInfo) templateIte.next();
			if(entryInfo.getNumber().startsWith(parentNum) 
					&& entryInfo.getLevel() == sibLevel){
				int childNum = 0;
				if(sibLevel != 1){
					String []tempStrs = entryInfo.getNumber().split("!");
					childNum = Integer.parseInt(tempStrs[tempStrs.length - 1]);
				}else{
					childNum = Integer.parseInt(entryInfo.getNumber());
				}
				if(childNum > maxNum) maxNum = childNum;
			}
		}
		return maxNum;
	}
	private String getParentNumByLongNum(int level,String longNum){
		String []splitStr = longNum.split("!");
		String parentNum = "";
		for(int i = 0;i<level - 1;i++){
			if(i == 0){
				parentNum = parentNum + splitStr[i];
			}else{
				parentNum = parentNum +"!"+ splitStr[i];
			}
		}
		
		return parentNum;
	}

	protected Map _getTaskWBSByProjectId(Context ctx, String projectId)	throws BOSException, EASBizException {
		Map map = new HashMap();
		Map taskMap = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		SorterItemCollection soc = new SorterItemCollection();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",projectId,CompareType.EQUALS));
		soc.add(new SorterItemInfo("longNumber"));
		sic.add("*");
		sic.add("adminDept.id");
		sic.add("adminDept.name");
		sic.add("adminDept.number");
		view.setFilter(filter);
		view.setSelector(sic);
		view.setSorter(soc);
		FDCWBSCollection wbsCol = FDCWBSFactory.getLocalInstance(ctx).getFDCWBSCollection(view);
		map.put("wbs", wbsCol);
		view = new EntityViewInfo();
		filter = new FilterInfo();
		sic = new SelectorItemCollection();
		filter.getFilterItems().add(new FilterItemInfo("schedule.project.id",projectId));
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		sic.add("*");
		sic.add("wbs.id");
		sic.add("wbs.name");
		sic.add("wbs.number");
		view.setFilter(filter);
		view.setSelector(sic);
		FDCScheduleTaskCollection taskCol = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		for(int i=0;i<taskCol.size();i++){
			FDCScheduleTaskInfo taskInfo = taskCol.get(i);
			taskMap.put(taskInfo.getWbs().getId().toString(), taskInfo);
		}
		map.put("task", taskMap);
		return map;
	}

	/**
	 * 注意只修改最新版本的任务编码
	 */
	protected void _reCalculateNumber(Context ctx, Object wbsAdjustManager) throws BOSException, EASBizException {
		WBSAdjustManager adjustManager = (WBSAdjustManager) wbsAdjustManager;
		for(int i=0;i<adjustManager.size();i++){
			WBSAdjustManagerItem item = adjustManager.get(i);
			adjustManager.reCalNumber(item);
			System.out.println("name:"+item.getWbsInfo().getName() +";oldLongNum:"+item.getWbsInfo().getLongNumber()+";newLongNum:"+item.getLongNumber());
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		Set wbsIds = new HashSet();
		builder.setPrepareStatementSql(UPDATE_WBSNUM_SQL);
		builder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
		for(int i=0;i<adjustManager.size();i++){
			WBSAdjustManagerItem item = adjustManager.get(i);
			wbsIds.add(item.getWbsInfo().getId().toString());
			builder.addParam(item.getNumber());
			builder.addParam(item.getLongNumber());
			builder.addParam(new Integer(item.getLevel()));
			builder.addParam(Boolean.valueOf(item.isLeaf()));
			if(item.getNewParent() == null){
				builder.addParam(null);
			}else{
				builder.addParam(item.getNewParent().getId().toString());
			}
			builder.addParam(item.getWbsInfo().getId().toString());
			builder.addBatch();
		}
		
		builder.executeBatch();
		synchronize2Task(ctx,wbsIds);
	}
	private void synchronize2Task(Context ctx,Set wbsIds) throws BOSException, EASBizException{
		Map wbsTaskMap = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("name");
		view.getSelector().add("parent");
		view.getSelector().add("number");
		view.getSelector().add("longNumber");
		view.getSelector().add("isLeaf");
		view.getSelector().add("level");
		view.getSelector().add("wbs.id");
		view.getSelector().add("wbs.name");
		view.getSelector().add("wbs.parent");
		view.getSelector().add("wbs.number");
		view.getSelector().add("wbs.longNumber");
		view.getSelector().add("wbs.isLeaf");
		view.getSelector().add("wbs.level");
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("wbs.id",wbsIds,CompareType.INCLUDE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		FDCScheduleTaskCollection taskCol = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		int size = taskCol.size();
		for(int i=0;i<size;i++){
			FDCScheduleTaskInfo taskInfo = taskCol.get(i);
			FDCWBSInfo wbsInfo = taskInfo.getWbs();
			wbsTaskMap.put(wbsInfo.getId().toString(), taskInfo);
			taskInfo.setNumber(wbsInfo.getLongNumber().replace('!', '.'));
			taskInfo.setLongNumber(wbsInfo.getLongNumber());
			taskInfo.setLevel(wbsInfo.getLevel());
			taskInfo.setIsLeaf(wbsInfo.isIsLeaf());
		}
		for(int i=0;i<size;i++){
			FDCScheduleTaskInfo taskInfo = taskCol.get(i);
			FDCWBSInfo parWBSInfo = taskInfo.getWbs().getParent();
			if(parWBSInfo != null && wbsTaskMap.get(parWBSInfo.getId().toString()) != null){
				taskInfo.setParent((FDCScheduleTaskInfo) wbsTaskMap.get(parWBSInfo.getId().toString()));
			}
		}
		if(taskCol.size()>0){
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("number");
			selector.add("longNumber");
			selector.add("isLeaf");
			selector.add("level");
			selector.add("parent");
			IORMappingDAO dao = ORMappingDAO.getInstance(taskCol.get(0).getBOSType(), ctx, getConnection(ctx));
			for (Iterator iter = taskCol.iterator(); iter.hasNext();) {
				FDCScheduleTaskInfo newTask = (FDCScheduleTaskInfo) iter.next();
				dao.updatePartial(newTask, selector);
			}
			dao.executeBatch();
		}
//		删除前置任务不符合的节点：上下级关系的节点也为前/后置关系
		/**
		 * 此SQL编写复杂，某些oracle数据库不支持；小版本问题
		 * 周勇
		 * 根据SQL逻辑判断，目前应该没有问题，主要是为了避免出现循环的依赖关系
		 * 目前已在界面逐步完善
		 */
//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		builder.appendSql("delete from T_SCH_FDCScheduleTaskDepend where fid in( select distinct dep.fid " +
//				"	from t_sch_fdcscheduletask tk1 " +
//				"	inner join T_SCH_FDCScheduleTaskDepend dep on tk1.fid=dep.ftaskid " +
//				"	inner join t_sch_fdcscheduletask tk2 on tk2.fid=dep.fdependtaskid " +
//				"	where tk1.fid=tk2.fparentid or tk2.fid=tk1.fparentid)");
//		builder.executeUpdate();
	}

	protected void _handleCancleCancle(Context ctx, Map paramMap) throws BOSException, EASBizException {
		Object o = paramMap.get("selectedIds");// 选中WBS的ID
		Set selectedIds = (Set) o;

		Object o3 = paramMap.get("selfAndChildLgNums");// 选中WBS的所有子节点及其本身长编码
		Set selfAndChildLgNums = (Set) o3;

		Object o4 = paramMap.get("parentLongNums");// //选中WBS的所有父节点长编码
		Set parentLongNums = (Set) o4;

		Object o5 = paramMap.get("prjID");// 工程项目
		String prjID = (String) o5;

		Object o6 = paramMap.get("selectedLongNum");// 选中WBS的长编码
		Set selectedLongNums = (Set) o6;

		Set allLongNums = new HashSet();
		allLongNums.addAll(selfAndChildLgNums);
		allLongNums.addAll(parentLongNums);

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select flongnumber,count(*) jishu from t_sch_fdcwbs where FTaskTypeID is null and ");
		builder.appendSql(" FCurProjectID=? ");
		builder.addParam(prjID);
		builder.appendParam("  and  flongnumber", allLongNums.toArray());
		builder.appendSql(" group by flongnumber");
		IRowSet rowSet = builder.executeQuery();
		if (rowSet != null && rowSet.size() > 0) {
			int count;
			try {
				rowSet.next();
				count = rowSet.getInt("jishu");
				if (count > 0) {
					throw new EASBizException(new NumericExceptionSubItem("306", "有部分项目WBS的任务级别为空！"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		builder.clear();
		builder.appendSql("select flongnumber,count(*) jishu from t_sch_fdcwbs where FAdminPersonID is null and ");
		builder.appendSql(" FCurProjectID=? ");
		builder.addParam(prjID);
		builder.appendParam("  and  flongnumber", allLongNums.toArray());
		builder.appendSql(" group by flongnumber");
		IRowSet rowSet1 = builder.executeQuery();
		if (rowSet1 != null && rowSet1.size() > 0) {
			int count;
			try {
				rowSet1.next();
				count = rowSet1.getInt("jishu");
				if (count > 0) {
					throw new EASBizException(new NumericExceptionSubItem("306", "有部分项目WBS的责任人为空！"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		builder.clear();
		builder.appendSql("select flongnumber,count(*) jishu from t_sch_fdcwbs where FRespDeptID is null and ");
		builder.appendSql(" FCurProjectID=? ");
		builder.addParam(prjID);
		builder.appendParam("  and  flongnumber", allLongNums.toArray());
		builder.appendSql(" group by flongnumber");
		IRowSet rowSet2 = builder.executeQuery();
		if (rowSet2 != null && rowSet2.size() > 0) {
			int count;
			try {
				rowSet2.next();
				count = rowSet2.getInt("jishu");
				if (count > 0) {
					throw new EASBizException(new NumericExceptionSubItem("306", "有部分项目WBS的责任部门为空！"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 专门处理计划部门 by cassiel_peng 2010-04-29
		// 在启用任务级别为专项节点任务的WBS时，任务级别都需要校验，但是计划部门只需要校验其上级的计划部门不能为空，
		// 下级及其本身无需校验
		// 在启用任务级别为主项节点任务的WBS时，任务级别都需要校验，但是计划部门需要校验其上级及其本身的计划部门不能为空，下级无需校验
		builder.clear();
		builder.appendSql("select flongnumber,count(*) jishu from t_sch_fdcwbs where FAdminDeptID is null and ");
		builder.appendSql(" FCurProjectID=? ");
		builder.addParam(prjID);
		builder.appendSql(" and FTaskTypeID=? ");
		builder.addParam("Fs627NttSemeAN1knRHeIxN/5DU=");
		builder.appendParam("  and  flongnumber", allLongNums.toArray());
		builder.appendSql(" group by flongnumber");
		IRowSet rowSet3 = builder.executeQuery();
		if (rowSet3 != null && rowSet3.size() > 0) {
			int count;
			try {
				rowSet3.next();
				count = rowSet3.getInt("jishu");
				if (count > 0) {
					throw new EASBizException(new NumericExceptionSubItem("306", "有部分任务级别为主项任务的项目WBS的计划部门为空！"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	protected Map _saveOrderWBS(Context ctx, FDCWBSTree tree, boolean isRetMap) throws BOSException, EASBizException {
		updateWBS(ctx, tree);
		if(isRetMap){
			return tree.getId2WBSMap();
		}
		return null;
	}

	private void updateWBS(Context ctx, FDCWBSTree tree) throws BOSException, EASBizException{
		tree.reCalculateCode();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		List paramList = new java.util.ArrayList();
		addChildrenParams(paramList, tree.getRoot());
		String sql = "update T_SCH_FDCWBS set FIndex=?, FLevel=?, FIsLeaf=?, FNumber=?, FLongNumber=?, FParentId=? where fid=?";
		builder.executeBatch(sql, paramList);		
		builder.clear();
		updateTask(ctx, tree);		
		
	}
	private void updateTask(Context ctx, FDCWBSTree tree)throws BOSException, EASBizException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("parent");
		view.getSelector().add("number");
		view.getSelector().add("longNumber");
		view.getSelector().add("isLeaf");
		view.getSelector().add("level");
		view.getSelector().add("wbs");
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.project.id", tree.getProjectId()));
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		FDCScheduleTaskCollection taskCol  = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		Map id2WBS = tree.getId2WBSMap();
		Map wbsId2Task = new HashMap();
		for(int i = 0; i < taskCol.size(); ++i){
			FDCScheduleTaskInfo task = taskCol.get(i);
			String wbsId = task.getWbs().getId().toString();
			FDCWBSInfo wbs = (FDCWBSInfo) id2WBS.get(wbsId);
			if(wbs != null){//项目wbs与计划编制不同步可能导致wbs为空，下步将考虑此问题
				wbsId2Task.put(wbsId, task);
				task.setNumber(wbs.getLongNumber().replace('!', '.'));
				task.setLevel(wbs.getLevel());
				task.setIsLeaf(wbs.isIsLeaf());
				task.setLongNumber(wbs.getLongNumber());
			}
		}
		for(int i=0;i<taskCol.size();i++){
			FDCScheduleTaskInfo task = taskCol.get(i);
			String wbsId = task.getWbs().getId().toString();
			FDCWBSInfo wbs = (FDCWBSInfo) id2WBS.get(wbsId);
			if(wbs != null){//项目wbs与计划编制不同步可能导致wbs为空，下步将考虑此问题
				FDCWBSInfo parWBSInfo = wbs.getParent();
				if(parWBSInfo != null && wbsId2Task.get(parWBSInfo.getId().toString()) != null){
					task.setParent((FDCScheduleTaskInfo) wbsId2Task.get(parWBSInfo.getId().toString()));
				}
			}
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String sql = "update t_sch_fdcscheduletask set FLevel=?, FIsLeaf=?, FNumber=?, FLongNumber=?, FParentId=? where fid=?";
		List paramList = new ArrayList();
		for(int i = 0; i < taskCol.size(); ++i){
			FDCScheduleTaskInfo task = taskCol.get(i);
			List params = new ArrayList();
			params.add(new Integer(task.getLevel()));
			params.add(new Integer(task.isIsLeaf()?1:0));
			params.add(task.getNumber());
			params.add(task.getLongNumber());
			if(task.getParent()!=null){
				params.add(task.getParent().getId().toString());
			}else{
				params.add(null);
			}
			params.add(task.getId().toString());
			paramList.add(params);
		}
		builder.executeBatch(sql, paramList);		
		builder.clear();
		
	}
	private void addChildrenParams(List paramList, List childrenList){		
		int size = childrenList.size();
		for(int i = 0; i < size; ++i){
			FDCWBSTreeNode child = (FDCWBSTreeNode) childrenList.get(i);
			FDCWBSInfo wbs = child.getEntity();
			List params = new ArrayList();
			params.add(new Integer(wbs.getIndex()));
			params.add(new Integer(wbs.getLevel()));
			params.add(new Integer(wbs.isIsLeaf()?1:0));
			params.add(wbs.getNumber());
			params.add(wbs.getLongNumber());
			if(wbs.getParent()!=null){
				params.add(wbs.getParent().getId().toString());
			}else{
				params.add(null);
			}
			params.add(wbs.getId().toString());
			paramList.add(params);
			addChildrenParams(paramList, child.getChildren());
		}
	}

}