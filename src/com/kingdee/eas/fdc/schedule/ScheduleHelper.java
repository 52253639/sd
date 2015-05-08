package com.kingdee.eas.fdc.schedule;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sourceforge.ganttproject.cache.ActivityCache;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependCollection;
import com.kingdee.jdbc.rowset.IRowSet;

public class ScheduleHelper {
	private ScheduleHelper(){}
	
	public static SelectorItemCollection getSelector(){
		SelectorItemCollection selector = new SelectorItemCollection();
	    selector.add("dependEntrys.*");
		selector.add("belongToSpecial.id");
		selector.add("belongToSpecial.name");
		selector.add("belongToSpecial.number");
		selector.add("dependEntrys.task.id");
		selector.add("dependEntrys.task.name");
		selector.add("dependEntrys.task.number");
		selector.add("dependEntrys.task.srcID");
		selector.add("dependEntrys.dependTask.id");
		selector.add("dependEntrys.dependTask.number");
		selector.add("dependEntrys.dependTask.name");
		selector.add("dependEntrys.dependTask.srcID");
		
		
		selector.add("schedule.projectSpecial.id");
		
		selector.add("*");
		selector.add("parent.*");
		/** 追加成果类别 **/
		selector.add("achievementType.id");
		selector.add("achievementType.name");
		selector.add("achievementType.number");

		/** 责任人 **/
		selector.add("adminPerson.id");
		selector.add("adminPerson.name");
		selector.add("adminPerson.number");

		/** 责任部门 **/
		selector.add("adminDept.id");
		selector.add("adminDept.name");
		selector.add("adminDept.number");

		/** 追加标准任务指引 **/
		selector.add("standardTask.id");
		selector.add("standardTask.name");
		selector.add("standardTask.number");

		/** 追加协助人 **/
		selector.add("helpPerson.id");
		selector.add("helpPerson.name");
		selector.add("helpPerson.number");

		/** 追加协助部门 **/
		selector.add("helpDept.id");
		selector.add("helpDept.name");
		selector.add("helpDept.number");

		/** 追加风险负责人 **/
		selector.add("riskResPerson.id");
		selector.add("riskResPerson.name");
		selector.add("riskResPerson.number");

		/** 追加任务日历 **/
		selector.add("calendar.id");
		selector.add("calendar.name");
		selector.add("calendar.number");
		selector.add("calendar.weekendEntrys.weekend");
		selector.add("calendar.weekendEntrys.id");
		selector.add("calendar.holidayEntrys.holidayName");
		selector.add("calendar.holidayEntrys.id");
		selector.add("calendar.holidayEntrys.date");

		/** 追加进度评价人 **/
		selector.add("planEvaluatePerson.id");
		selector.add("planEvaluatePerson.name");
		selector.add("planEvaluatePerson.number");
		/** 追加质量评价人 **/
		selector.add("qualityEvaluatePerson.id");
		selector.add("qualityEvaluatePerson.name");
		selector.add("qualityEvaluatePerson.number");
		
		/** 关联主项任务 */
		selector.add("dependMainTaskID.id");
		selector.add("dependMainTaskID.name");
		selector.add("dependMainTaskID.number");
		selector.add("dependMainTaskID.end");
		selector.add("dependMainTaskID.checkdate");

	
		selector.add("description");
		selector.add("color");
		selector.add("shape");
		selector.add("meeting");
		selector.add("start");
		selector.add("end");
		selector.add("duration");
		selector.add("priority");
		selector.add("expand");
		selector.add("notes");
		selector.add("fixedStart");
		selector.add("webLink");
		selector.add("thirdDate");
		selector.add("thirdDateConstraint");
		selector.add("locked");
		selector.add("seq");
		selector.add("isLeaf");
		selector.add("level");
		selector.add("longNumber");
		selector.add("displayName");
		selector.add("name");
		selector.add("number");
		selector.add("desciption");
		selector.add("simleName");
		selector.add("id");
		selector.add("checkDate");
		selector.add("effectTimes");
		selector.add("isCheckNode");
		selector.add("checkNode.id");
		selector.add("checkNode.name");
		selector.add("checkNode.number");
		selector.add("isKey");
		selector.add("isRefer");
		selector.add("mileStoneStatus");
		selector.add("natureTimes");
		selector.add("srcID");
		selector.add("taskType");
		selector.add("workLoad");
		selector.add("actualStartDate");
		selector.add("actualEndDate");
		selector.add("standardTask.id");
		selector.add("standardTask.name");
		selector.add("standardTask.number");
		selector.add("qualityEvaluatePerson");
		selector.add("planDept.id");
		selector.add("planDept.name");
		selector.add("planDept.number");
		selector.add("bizType.id");
		selector.add("bizType.bizType.id");
		selector.add("bizType.bizType.name");
		selector.add("bizType.bizType.number");
		
		selector.add("helpPersonEntry.person.name");
		selector.add("helpDeptEntry.dept.name");
		return selector;

	}
	
	public static String getTaskTypeFilterSql(String taskTypeId){
		StringBuffer sql=new StringBuffer("select distinct parent.fid from T_SCH_TaskType parent, T_SCH_TaskType my ");
		sql.append("where my.fid=? and charIndex(parent.flongnumber||'!',my.flongnumber||'!') ");
		
		return sql.toString();
	}
	
	public static String getDeptFilterSql(String taskTypeId){
		StringBuffer sql=new StringBuffer("select distinct parent.fid from T_Org_BaseUnit parent, T_Org_BaseUnit my ");
		sql.append("where my.fid=? and charIndex(parent.flongnumber||'!',my.flongnumber||'!') ");
		
		return sql.toString();
	}
	
	/**
	 * 过滤出指定taskTypeId,adminDeptId对应的WBS节点及他的上级节点
	 * 如果是主项节点则过滤出自己，如果是其他节点，则只按部门做过滤；以后看时候考虑按节点做节点计划
	 * @param taskTypeId
	 * @param adminDeptId
	 * @return
	 * 更新专项取数：专项新增时需要取出同部门的主项任务
	 * @throws BOSException 
	 * @throws SQLException 
	 */
	public static Set getWBSFilterSql(Context ctx,String taskTypeId,String adminDeptId,String prjId) throws BOSException, SQLException{
		//主项节点不隔离  by sxhong 2009-10-16 10:53:08
		if(taskTypeId.equals(TaskTypeInfo.TASKTYPE_MAINTASK)){
			/*StringBuffer sql=new StringBuffer("select fid from T_SCH_FDCWBS where ");
			sql.append(" fcurProjectId='"+prjId+"'");
			sql.append(" and ftaskTypeId='"+TaskTypeInfo.TASKTYPE_MAINTASK+"'");*/
			Set set = new HashSet();
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select fid from T_SCH_FDCWBS where fcurProjectId='"+prjId+"'  and ftaskTypeId='"+TaskTypeInfo.TASKTYPE_MAINTASK+"'");
			IRowSet rowSet = builder.executeQuery();
			while(rowSet.next()){
				String fid = rowSet.getString("fid");
				set.add(fid);
			}
			return set;
		}else{
			//专项计划
			/*StringBuffer sql=new StringBuffer("select distinct parent.fid from T_SCH_FDCWBS parent,T_SCH_FDCWBS sub where");
			sql.append(" parent.fisEnabled=1 ");
			sql.append(" and ((parent.fcurProjectId=sub.fcurProjectId ");
			sql.append(" and charindex(parent.flongnumber||'!',sub.flongnumber||'!')=1 ");
			sql.append(" and sub.fcurProjectId='"+prjId+"'");
			sql.append(" and sub.ftaskTypeId!='"+TaskTypeInfo.TASKTYPE_MAINTASK+"'");
			sql.append(" and parent.fadminDeptId='"+adminDeptId+"')");
			sql.append(" or(parent.fcurProjectId='"+prjId+"'");
			sql.append(" and parent.ftaskTypeId='"+TaskTypeInfo.TASKTYPE_MAINTASK+"'");
			sql.append(" and parent.fadminDeptid='"+adminDeptId+"'))");
			sql.append(" union ");
			sql.append("select distinct sub.fid from T_SCH_FDCWBS parent,T_SCH_FDCWBS sub where sub.fisEnabled=1  and ");
			sql.append("((parent.fcurProjectId=sub.fcurProjectId  and charindex(parent.flongnumber||'!',sub.flongnumber||'!')=1  ");
			sql.append(" and sub.fcurProjectId='"+prjId+"'");
			sql.append("and sub.ftaskTypeId!='Fs627NttSemeAN1knRHeIxN/5DU=' and ");
			sql.append("  parent.fadminDeptid='"+adminDeptId+"'))");
			return sql.toString();*/
			Set set1 = new HashSet();
			Set set2 = new HashSet();
			Set set3 = new HashSet();// not in ids
			Set returnIds = new HashSet();
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			// 先找到某个工程项目下计划部门为XXX的所有的主项任务
			builder.appendSql("select flongnumber,fid from t_sch_fdcwbs where fadmindeptid=? and fcurprojectid=? and fisenabled=1");
			builder.addParam(adminDeptId);
			builder.addParam(prjId);
			IRowSet rowSet = builder.executeQuery();
			while(rowSet.next()){
				String flongnumber = rowSet.getString("flongnumber");
				set1.add(flongnumber);
			}
			for(Iterator iter = set1.iterator();iter.hasNext();){
				String flongnumber = iter.next().toString();
				builder.clear();
				//再找到这些主项任务的所有下级节点
				builder.appendSql("select fid,flongnumber from t_sch_fdcwbs where flongnumber like '"+flongnumber+"!%' and fcurprojectid=?  and fadmindeptid!=? and ftaskTypeId=? ");
				builder.addParam(prjId);
				builder.addParam(adminDeptId);
				builder.addParam(TaskTypeInfo.TASKTYPE_MAINTASK);
				IRowSet rowSet1 = builder.executeQuery();
				while(rowSet1.next()){
					String fln = rowSet1.getString("flongnumber");
//					set2.add(rowSet1.getString("flongnumber"));
					set2.add(fln);
				}
				//从下级节点中将直属上级不为指定计划部门XXX的全都过滤出去
				for(Iterator itSet2 = set2.iterator();itSet2.hasNext();){
					builder.clear();
					String notInLongNumber = (String)itSet2.next();
						builder.appendSql("select fid from t_sch_fdcwbs where  flongnumber like '"+notInLongNumber+"%'");
						IRowSet rowSet3 = builder.executeQuery();
						while(rowSet3.next()){
							String fid = rowSet3.getString("fid");
							set3.add(fid);
						}
					
				}
				builder.clear();
				//根据longNumber找到所有的主项专项，not in 不符合条件的ids
				builder.appendSql("select fid,flongnumber from t_sch_fdcwbs where flongnumber like '"+flongnumber+"%' and fcurprojectid=? ");
				builder.addParam(prjId);
				if(set3.size()>0){
					builder.appendSql(" and ");
					builder.appendParam("fid not ", set3.toArray());
					
				}
				rowSet1 = builder.executeQuery();
				while(rowSet1.next()){
					String fid = rowSet1.getString("fid");
//						set2.add(rowSet1.getString("flongnumber"));
					returnIds.add(fid);
				}
			
			}			
			return returnIds;
		}
	}
	
	static SelectorItemCollection getDefaultSelector(){
		SelectorItemCollection selectors =  new SelectorItemCollection();
		selectors.add("scheduleStage.id");
		selectors.add("scheduleStage.number");
		selectors.add("scheduleStage.name");
		
		selectors.add("project.id");
		selectors.add("project.name");
		selectors.add("project.number");
		selectors.add("project.costCenter.id");

		selectors.add("dispColumns.*");

		selectors.add("calendar.id");
		selectors.add("calendar.name");
		selectors.add("calendar.number");
		selectors.add("calendar.weekendEntrys.weekend");
		selectors.add("calendar.weekendEntrys.id");
		selectors.add("calendar.holidayEntrys.holidayName");
		selectors.add("calendar.holidayEntrys.id");
		selectors.add("calendar.holidayEntrys.date");

		selectors.add("isCheckVersion");
		selectors.add("srcID");
		selectors.add("version");
		selectors.add("startDate");
		selectors.add("totalTimes");
		selectors.add("natureTimes");
		selectors.add("isLatestVer");
		selectors.add("endDate");
		selectors.add("state");
		selectors.add("color");
		selectors.add("name");
		selectors.add("webLike");
		selectors.add("viewDate");
		selectors.add("viewIndex");
		selectors.add("gdivlocation");
		selectors.add("rdivlocation");
		selectors.add("gversion");
		selectors.add("auditTime");
		selectors.add("locked");
		selectors.add("number");
		selectors.add("description");
		selectors.add("effectDegree");
		selectors.add("versionName");
		selectors.add("versionDate");

		selectors.add("scheduleType.id");
		selectors.add("scheduleType.name");
		selectors.add("scheduleType.number");
		selectors.add("scheduleType.level");
		selectors.add("scheduleType.isEnabled");
		selectors.add("scheduleType.isLeaf");
		selectors.add("scheduleType.parent");

		selectors.add("baseVer.id");
		selectors.add("baseVer.name");
		selectors.add("baseVer.number");

		selectors.add("adjustReason.id");
		selectors.add("adjustReason.name");
		selectors.add("adjustReason.id");
		selectors.add("projectSpecial.id");
		selectors.add("projectSpecial.name");
		selectors.add("projectSpecial.number");

		/*
		 * 	selectors.add("taskEntrys.*");
		 *  这句还不能去掉(经自测发现约有5秒性能影响)，否则会影响Gantt图的级次显示，还未找到是哪个字段会影响
		 *  Added by Owen_wen 201312-23
		 */
		selectors.add("taskEntrys.*"); 
		selectors.add("taskEntrys.parent.srcID");
		selectors.add("taskEntrys.dependEntrys.*");
		selectors.add("taskEntrys.dependEntrys.task.id");
		selectors.add("taskEntrys.dependEntrys.task.name");
		selectors.add("taskEntrys.dependEntrys.task.number");
		selectors.add("taskEntrys.dependEntrys.task.srcID");
		selectors.add("taskEntrys.dependEntrys.dependTask.id");
		selectors.add("taskEntrys.dependEntrys.dependTask.number");
		selectors.add("taskEntrys.dependEntrys.dependTask.name");
		selectors.add("taskEntrys.dependEntrys.dependTask.srcID");
		
		/** 追加成果类别 **/
		selectors.add("taskEntrys.intendEndDate");
		selectors.add("taskEntrys.achievementType.id");
		selectors.add("taskEntrys.achievementType.name");
		selectors.add("taskEntrys.achievementType.number");

		/** 责任人 **/
		selectors.add("taskEntrys.adminPerson.id");
		selectors.add("taskEntrys.adminPerson.name");
		selectors.add("taskEntrys.adminPerson.number");

		/** 责任部门 **/
		selectors.add("taskEntrys.adminDept.id");
		selectors.add("taskEntrys.adminDept.name");
		selectors.add("taskEntrys.adminDept.number");

		/** 追加标准任务指引 **/
		selectors.add("taskEntrys.standardTask.id");
		selectors.add("taskEntrys.standardTask.name");
		selectors.add("taskEntrys.standardTask.number");
		selectors.add("taskEntrys.standardTask.taskType");

		/** 追加协助人 **/
		selectors.add("taskEntrys.helpPerson.id");
		selectors.add("taskEntrys.helpPerson.name");
		selectors.add("taskEntrys.helpPerson.number");

		/** 追加协助部门 **/
		selectors.add("taskEntrys.helpDept.id");
		selectors.add("taskEntrys.helpDept.name");
		selectors.add("taskEntrys.helpDept.number");

		/** 追加风险负责人 **/
		selectors.add("taskEntrys.riskResPerson.id");
		selectors.add("taskEntrys.riskResPerson.name");
		selectors.add("taskEntrys.riskResPerson.number");

		/** 追加任务日历 **/
		selectors.add("taskEntrys.calendar.id");
		selectors.add("taskEntrys.calendar.name");
		selectors.add("taskEntrys.calendar.number");
		selectors.add("taskEntrys.calendar.weekendEntrys.weekend");
		selectors.add("taskEntrys.calendar.weekendEntrys.id");
		selectors.add("taskEntrys.calendar.holidayEntrys.holidayName");
		selectors.add("taskEntrys.calendar.holidayEntrys.id");
		selectors.add("taskEntrys.calendar.holidayEntrys.date");

		/** 追加进度评价人 **/
		selectors.add("taskEntrys.planEvaluatePerson.id");
		selectors.add("taskEntrys.planEvaluatePerson.name");
		selectors.add("taskEntrys.planEvaluatePerson.number");
		
		selectors.add("taskEntrys.qualityEvaluatePerson.id");
		selectors.add("taskEntrys.qualityEvaluatePerson.name");
		selectors.add("taskEntrys.qualityEvaluatePerson.number");
		
		/** 关联主项任务 */
		selectors.add("taskEntrys.dependMainTaskID.id");
		selectors.add("taskEntrys.dependMainTaskID.name");
		selectors.add("taskEntrys.dependMainTaskID.number");
		selectors.add("taskEntrys.dependMainTaskID.end");
		selectors.add("taskEntrys.dependMainTaskID.checkdate");
		selectors.add("taskEntrys.dependMainTaskID.srcID");
		
		selectors.add("taskEntrys.belongToSpecial.id");
		selectors.add("taskEntrys.belongToSpecial.number");
		selectors.add("taskEntrys.belongToSpecial.name");

		selectors.add("taskEntrys.description");
		selectors.add("taskEntrys.color");
		selectors.add("taskEntrys.shape");
		selectors.add("taskEntrys.meeting");
		selectors.add("taskEntrys.start");
		selectors.add("taskEntrys.end");
		selectors.add("taskEntrys.duration");
		selectors.add("taskEntrys.complete");
		selectors.add("taskEntrys.priority");
		selectors.add("taskEntrys.expand");
		selectors.add("taskEntrys.notes");
		selectors.add("taskEntrys.fixedStart");
		selectors.add("taskEntrys.webLink");
		selectors.add("taskEntrys.thirdDate");
		selectors.add("taskEntrys.thirdDateConstraint");
		selectors.add("taskEntrys.locked");
		selectors.add("taskEntrys.seq");
		
		selectors.add("taskEntrys.isLeaf");
		selectors.add("taskEntrys.level");
		selectors.add("taskEntrys.longNumber");
		selectors.add("taskEntrys.displayName");
		
		selectors.add("taskEntrys.name");
		selectors.add("taskEntrys.number");
		selectors.add("taskEntrys.desciption");
		selectors.add("taskEntrys.simleName");
		
		selectors.add("taskEntrys.id");
		selectors.add("taskEntrys.checkDate");
		selectors.add("taskEntrys.effectTimes");
		selectors.add("taskEntrys.isCheckNode");
		selectors.add("taskEntrys.checkNode.id");
		selectors.add("taskEntrys.checkNode.name");
		selectors.add("taskEntrys.checkNode.number");
		selectors.add("taskEntrys.isKey");
		selectors.add("taskEntrys.isRefer");
		selectors.add("taskEntrys.mileStoneStatus");
		selectors.add("taskEntrys.natureTimes");
		selectors.add("taskEntrys.srcID");
		selectors.add("taskEntrys.taskType");
		selectors.add("taskEntrys.workLoad");
		selectors.add("taskEntrys.actualStartDate");
		selectors.add("taskEntrys.actualEndDate");
		selectors.add("taskEntrys.qualityEvaluatePerson");
		
		selectors.add("taskEntrys.standardTask.id");
		selectors.add("taskEntrys.standardTask.name");
		selectors.add("taskEntrys.standardTask.number");
		
		selectors.add("taskEntrys.planDept.id");
		selectors.add("taskEntrys.planDept.name");
		selectors.add("taskEntrys.planDept.number");
		
		selectors.add("taskEntrys.bizType.id");
		selectors.add("taskEntrys.bizType.bizType.id");
		selectors.add("taskEntrys.bizType.bizType.name");
		selectors.add("taskEntrys.bizType.bizType.number");
		
		selectors.add("taskEntrys.helpPersonEntry.person.name");
		selectors.add("taskEntrys.helpDeptEntry.dept.name");
		return selectors;
	}
	
	public static EntityViewInfo getScheduleView() {
		EntityViewInfo view = new EntityViewInfo();
		
		view.setSelector(getDefaultSelector());

		SorterItemCollection sorter = new SorterItemCollection();
		
		/* modified by zhaoqin for R140915-0340 on 2014/10/11 start */
		//SorterItemInfo item = new SorterItemInfo("taskEntrys.longNumber");
		SorterItemInfo item = new SorterItemInfo("taskEntrys.seq");
		/* modified by zhaoqin for R140915-0340 on 2014/10/11 end */
		
		sorter.add(item);
		view.setSorter(sorter);

		return view;
	}
	
	public static EntityViewInfo getScheduleView(String billID) {
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(getDefaultSelector());
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", billID));
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		
		/* modified by zhaoqin for R140915-0340 on 2014/10/11 start */
		//SorterItemInfo item = new SorterItemInfo("taskEntrys.longNumber");
		SorterItemInfo item = new SorterItemInfo("taskEntrys.seq");
		/* modified by zhaoqin for R140915-0340 on 2014/10/11 end */
		
		sorter.add(item);
		view.setSorter(sorter);

		return view;
	}
	
	public static SelectorItemCollection getTaskSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("parent.*");

		/** 追加成果类别 **/
		sic.add("achievementType.id");
		sic.add("achievementType.name");
		sic.add("achievementType.number");

		/** 责任人 **/
		sic.add("adminPerson.id");
		sic.add("adminPerson.name");
		sic.add("adminPerson.number");

		/** 责任部门 **/
		sic.add("adminDept.id");
		sic.add("adminDept.name");
		sic.add("adminDept.number");

		/** 追加标准任务指引 **/
		sic.add("standardTask.id");
		sic.add("standardTask.name");
		sic.add("standardTask.number");

		/** 追加协助人 **/
		sic.add("helpPerson.id");
		sic.add("helpPerson.name");
		sic.add("helpPerson.number");

		/** 追加协助部门 **/
		sic.add("helpDept.id");
		sic.add("helpDept.name");
		sic.add("helpDept.number");

		/** 追加风险负责人 **/
		sic.add("riskResPerson.id");
		sic.add("riskResPerson.name");
		sic.add("riskResPerson.number");

		/** 追加任务日历 **/
		sic.add("calendar.id");
		sic.add("calendar.name");
		sic.add("calendar.number");

		/** 追加进度评价人 **/
		sic.add("planEvaluatePerson.id");
		sic.add("planEvaluatePerson.name");
		sic.add("planEvaluatePerson.number");
		
		/** 追加质量评价人 **/
		sic.add("qualityEvaluatePerson.id");
		sic.add("qualityEvaluatePerson.name");
		sic.add("qualityEvaluatePerson.number");
		
		/** 追加考核节点 **/
		sic.add("checkNode.id");
		sic.add("checkNode.name");
		sic.add("checkNode.number");
		
		sic.add("dependEntrys.*");

		sic.add("planDept.id");
		sic.add("planDept.name");
		sic.add("planDept.number");

		sic.add("bizType.id");
		sic.add("bizType.bizType.id");
		sic.add("bizType.bizType.name");
		sic.add("bizType.bizType.number");

		return sic;
	}
	
	//里程碑地铁图
	public static SelectorItemCollection getRptTaskSelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("description");
		selectors.add("color");
		selectors.add("shape");
		selectors.add("meeting");
		selectors.add("start");
		selectors.add("end");
		selectors.add("duration");
		selectors.add("priority");
		selectors.add("expand");
		selectors.add("notes");
		selectors.add("fixedStart");
		selectors.add("webLink");
		selectors.add("thirdDate");
		selectors.add("thirdDateConstraint");
		selectors.add("locked");
		selectors.add("seq");
		selectors.add("isLeaf");
		selectors.add("level");
		selectors.add("longNumber");
		selectors.add("displayName");
		selectors.add("name");
		selectors.add("number");
		selectors.add("desciption");
		selectors.add("simleName");
		selectors.add("id");
		selectors.add("checkDate");
		selectors.add("effectTimes");
		selectors.add("isCheckNode");
		selectors.add("checkNode.id");
		selectors.add("checkNode.name");
		selectors.add("checkNode.number");
		selectors.add("isKey");
		selectors.add("isRefer");
		selectors.add("mileStoneStatus");
		selectors.add("natureTimes");
		selectors.add("srcID");
		selectors.add("taskType");
		selectors.add("workLoad");
		selectors.add("actualStartDate");
		selectors.add("actualEndDate");
		selectors.add("standardTask.id");
		selectors.add("standardTask.name");
		selectors.add("standardTask.number");
		selectors.add("qualityEvaluatePerson");
		selectors.add("planDept.id");
		selectors.add("planDept.name");
		selectors.add("planDept.number");
		selectors.add("bizType.id");
		selectors.add("bizType.bizType.id");
		selectors.add("bizType.bizType.name");
		selectors.add("bizType.bizType.number");
		selectors.add("projectStep");
		selectors.add("projectVersionType");
		selectors.add("schedule.project.id");
		selectors.add("schedule.project.costcenter.id");
		selectors.add("schedule.project.id");
		selectors.add("schedule.project.name");
		selectors.add("schedule.project.number");
		
		
		
		selectors.add("*");
		selectors.add("parent.*");
		/** 追加成果类别 **/
		selectors.add("achievementType.id");
		selectors.add("achievementType.name");
		selectors.add("achievementType.number");

		/** 责任人 **/
		selectors.add("adminPerson.id");
		selectors.add("adminPerson.name");
		selectors.add("adminPerson.number");

		/** 责任部门 **/
		selectors.add("adminDept.id");
		selectors.add("adminDept.name");
		selectors.add("adminDept.number");

		/** 追加标准任务指引 **/
		selectors.add("standardTask.id");
		selectors.add("standardTask.name");
		selectors.add("standardTask.number");

		/** 追加协助人 **/
		selectors.add("helpPerson.id");
		selectors.add("helpPerson.name");
		selectors.add("helpPerson.number");

		/** 追加协助部门 **/
		selectors.add("helpDept.id");
		selectors.add("helpDept.name");
		selectors.add("helpDept.number");

		/** 追加风险负责人 **/
		selectors.add("riskResPerson.id");
		selectors.add("riskResPerson.name");
		selectors.add("riskResPerson.number");

		/** 追加任务日历 **/
		selectors.add("calendar.id");
		selectors.add("calendar.name");
		selectors.add("calendar.number");
		selectors.add("calendar.weekendEntrys.weekend");
		selectors.add("calendar.weekendEntrys.id");
		selectors.add("calendar.holidayEntrys.holidayName");
		selectors.add("calendar.holidayEntrys.id");
		selectors.add("calendar.holidayEntrys.date");

		/** 追加进度评价人 **/
		selectors.add("planEvaluatePerson.id");
		selectors.add("planEvaluatePerson.name");
		selectors.add("planEvaluatePerson.number");
		/**追加质量评价人*/
		selectors.add("qualityEvaluatePerson.id");
		selectors.add("qualityEvaluatePerson.name");
		selectors.add("qualityEvaluatePerson.number");
		/**追加其他质量评价人*/
		selectors.add("qualityEvaluatePersonOther.id");
		selectors.add("qualityEvaluatePersonOther.name");
		selectors.add("qualityEvaluatePersonOther.number");
		
		/** 追加考核节点 **/
		selectors.add("checkNode.id");
		selectors.add("checkNode.name");
		selectors.add("checkNode.number");
		
		/** 关联主项任务 */
		selectors.add("dependMainTaskID.id");
		selectors.add("dependMainTaskID.name");
		selectors.add("dependMainTaskID.number");
		selectors.add("dependMainTaskID.end");
		selectors.add("dependMainTaskID.checkdate");

		selectors.add("dependMainTaskID.intendEndDate");
		selectors.add("dependMainTaskID.checkDate");
		selectors.add("duration");
		
//		selectors.add("dependEntrys.task.id");
//		selectors.add("dependEntrys.task.name");
//		selectors.add("dependEntrys.task.number");
//		selectors.add("dependEntrys.task.srcID");
//		selectors.add("dependEntrys.dependTask.id");
//		selectors.add("dependEntrys.dependTask.number");
//		selectors.add("dependEntrys.dependTask.name");
//		selectors.add("dependEntrys.dependTask.srcID");
	
		
		
		return selectors;
	}
	
	public static FDCScheduleTaskInfo computeTaskDate(FDCScheduleTaskInfo currTask, FDCScheduleTaskInfo task, TaskLinkTypeEnum type,
			int diff, Set needProcess) throws ContractException {
	    // 处理多个搭接关系
		ScheduleCalendarInfo calendarInfo = currTask.getCalendar();
		FDCScheduleTaskDependCollection cols = currTask.getDependEntrys();
		FDCScheduleTaskDependInfo dep = null;
		int size = cols.size();
		int baseIndex = -1;
		if (size > 1) {
			Date newDate = null;
			Date currDate = null;
			for (int i = 0; i < size; i++) {
				dep = cols.get(i);
				if (dep.getType() != null && dep.getType().equals(TaskLinkTypeEnum.FINISH_FINISH)
						|| dep.getType().equals(TaskLinkTypeEnum.FINISH_START)) {
					if (dep.getTask().getPlanEnd() != null) {
						currDate = ScheduleCalendarHelper
								.getEndDate(dep.getTask().getPlanEnd(), FDCHelper.toBigDecimal(diff), calendarInfo);

					} else {
						needProcess.add(currTask);
						continue;
					}
					
				} else if (dep.getType() != null && dep.getType().equals(TaskLinkTypeEnum.START_START)) {
					currDate = ScheduleCalendarHelper.getEndDate(dep.getTask().getPlanStart(), FDCHelper.toBigDecimal(diff), calendarInfo);
				}
				if (newDate == null) {
					newDate = currDate;
					baseIndex = i;
				}
				if (newDate.compareTo(currDate) < 0) {
					baseIndex = i;
				}
			}
		}
		if (baseIndex > 0) {
			dep = cols.get(baseIndex);
			type = dep.getType();
			task = dep.getTask();
		}
		FDCScheduleTaskInfo newTask = currTask;
	    if(calendarInfo == null)
	    	 throw new ContractException(ContractException.WITHMSG,new Object[]{"项目日历为空！"});
	     calendarInfo = ActivityCache.getInstance().getCalendar(calendarInfo.getId().toString());
		if (TaskLinkTypeEnum.FINISH_START.equals(type)) {
			Date preF = task.getPlanEnd();
			Date nxtS = ScheduleCalendarHelper.getEndDate(preF, new BigDecimal(diff + 1), calendarInfo);
			newTask.setPlanStart(nxtS);
			BigDecimal dur = new BigDecimal(newTask.getDuration()+"");
			Date nxtF = ScheduleCalendarHelper.getEndDate(nxtS, dur.subtract(FDCHelper.ONE), calendarInfo);
			newTask.setPlanEnd(nxtF);
		} else if (TaskLinkTypeEnum.START_START.equals(type)) {
			Date preS = task.getPlanStart();
			Date nxtS = ScheduleCalendarHelper.getEndDate(preS, new BigDecimal(diff), calendarInfo);
			newTask.setPlanStart(nxtS);
			BigDecimal dur = new BigDecimal(newTask.getDuration());
			Date nxtF = ScheduleCalendarHelper.getEndDate(nxtS, dur.subtract(FDCHelper.ONE), calendarInfo);
			newTask.setPlanEnd(nxtF);
		} else if (TaskLinkTypeEnum.FINISH_FINISH.equals(type)) {
			Date preF = task.getPlanEnd();
			Date nxtF = ScheduleCalendarHelper.getEndDate(preF, new BigDecimal(diff), calendarInfo);
			newTask.setPlanEnd(nxtF);
			BigDecimal dur = new BigDecimal(newTask.getDuration()+"");
			Date nxtS = ScheduleCalendarHelper.getStartDate(nxtF, dur.subtract(FDCHelper.ONE), calendarInfo);
			newTask.setPlanStart(nxtS);
		}
		return newTask;
	}
	
	public static FDCScheduleTaskInfo computeTaskDate(FDCScheduleTaskInfo currTask, FDCScheduleTaskInfo task, TaskLinkTypeEnum type,
			int diff) throws ContractException {
		// 处理多个搭接关系
		ScheduleCalendarInfo calendarInfo = currTask.getCalendar();
		FDCScheduleTaskDependCollection cols = currTask.getDependEntrys();
		FDCScheduleTaskDependInfo dep = null;
		int size = cols.size();
		int baseIndex = -1;
		if (size > 1) {
			Date newDate = null;
			Date currDate = null;
			for (int i = 0; i < size; i++) {
				dep = cols.get(i);
				if (dep.getType().equals(TaskLinkTypeEnum.FINISH_FINISH) || dep.getType().equals(TaskLinkTypeEnum.FINISH_START)) {
					currDate = ScheduleCalendarHelper.getEndDate(dep.getTask().getPlanEnd(), FDCHelper.toBigDecimal(diff), calendarInfo);
				} else if (dep.getType().equals(TaskLinkTypeEnum.START_START)) {
					currDate = ScheduleCalendarHelper.getEndDate(dep.getTask().getPlanStart(), FDCHelper.toBigDecimal(diff), calendarInfo);
				}
				if (newDate == null) {
					newDate = currDate;
					baseIndex = i;
				}
				if (newDate.compareTo(currDate) < 0) {
					baseIndex = i;
				}
			}
		}
		if (baseIndex > 0) {
			dep = cols.get(baseIndex);
			type = dep.getType();
			task = dep.getTask();
		}
		FDCScheduleTaskInfo newTask = currTask;
		if (calendarInfo == null)
			throw new ContractException(ContractException.WITHMSG, new Object[] { "项目日历为空！" });
		calendarInfo = ActivityCache.getInstance().getCalendar(calendarInfo.getId().toString());
		if (TaskLinkTypeEnum.FINISH_START.equals(type)) {
			Date preF = task.getPlanEnd();
			Date nxtS = ScheduleCalendarHelper.getEndDate(preF, new BigDecimal(diff + 1), calendarInfo);
			newTask.setPlanStart(nxtS);
			BigDecimal dur = new BigDecimal(newTask.getDuration() + "");
			Date nxtF = ScheduleCalendarHelper.getEndDate(nxtS, dur.subtract(FDCHelper.ONE), calendarInfo);
			newTask.setPlanEnd(nxtF);
		} else if (TaskLinkTypeEnum.START_START.equals(type)) {
			Date preS = task.getPlanStart();
			Date nxtS = ScheduleCalendarHelper.getEndDate(preS, new BigDecimal(diff), calendarInfo);
			newTask.setPlanStart(nxtS);
			BigDecimal dur = new BigDecimal(newTask.getDuration());
			Date nxtF = ScheduleCalendarHelper.getEndDate(nxtS, dur.subtract(FDCHelper.ONE), calendarInfo);
			newTask.setPlanEnd(nxtF);
		} else if (TaskLinkTypeEnum.FINISH_FINISH.equals(type)) {
			Date preF = task.getPlanEnd();
			Date nxtF = ScheduleCalendarHelper.getEndDate(preF, new BigDecimal(diff), calendarInfo);
			newTask.setPlanEnd(nxtF);
			BigDecimal dur = new BigDecimal(newTask.getDuration() + "");
			Date nxtS = ScheduleCalendarHelper.getStartDate(nxtF, dur.subtract(FDCHelper.ONE), calendarInfo);
			newTask.setPlanStart(nxtS);
		}
		return newTask;
	}
	
	/**
	 * 
	 * 描述：获取所有的上级节点
	 * @param task
	 * @param resultSet
	 * 创建人：yuanjun_lan
	 * 创建时间：2011-12-29
	 */
	public static void getAllRootIDs(FDCScheduleTaskInfo task,Set resultSet){
		FDCScheduleTaskInfo pTask = null;
		if(task.getParent()!=null){
			pTask = task.getParent();
		}
		if(pTask != null && pTask.getId()!=null)
		   resultSet.add(pTask.getId().toString());
		if(pTask!=null &&pTask.getParent()!=null){
			getAllRootIDs(pTask, resultSet);
		}
		
	}
	
	public static void getAllRootSrcIDs(FDCScheduleTaskInfo task,Set resultSet){
		FDCScheduleTaskInfo pTask = null;
		if(task.getParent()!=null){
			pTask = task.getParent();
		}
		if(pTask != null && pTask.getId()!=null)
			resultSet.add(pTask.getSrcID());
		if(pTask!=null &&pTask.getParent()!=null){
			getAllRootIDs(pTask, resultSet);
		}
		
	}
	
	
	/**
	 * 
	 * 描述：获取所有已经依赖上的节点
	 * @param task
	 * @param resultSet
	 * 创建人：yuanjun_lan
	 * 创建时间：2011-12-29
	 */
	public static void getAlreadyDepend(FDCScheduleTaskInfo task,Set resultSet){
		ScheduleTaskDependCollection cols = task.getDepends();
		FDCScheduleTaskDependInfo dependInfo = null;
		for(Iterator it = cols.iterator();it.hasNext();){
			dependInfo = (FDCScheduleTaskDependInfo) it.next();
			resultSet.add(dependInfo.getDependTask().getId().toString());
		}
	}
	/**
	 * 
	 * 描述：
	 * @param task
	 * @param resultSet
	 * 创建人：yuanjun_lan
	 * 创建时间：2011-12-29
	 */
	public boolean  getAllRelatedMainTask(FDCScheduleTaskInfo task, Set resultSet){
		
		return false;
	}
	
	
	public static FDCScheduleTaskCollection getAlreayRelateMainTask(FDCScheduleTaskInfo task,Set parentIDSet ) throws BOSException{
		return getAlreayRelateMainTask(task,parentIDSet,null);
	}
	
	/**
	 * 
	 * 描述：返回当前专项的任务关联的主项任务
	 * @return
	 * 创建人：yuanjun_lan
	 * 创建时间：2011-12-30
	 * @throws BOSException 
	 */
	
	public static FDCScheduleTaskCollection getAlreayRelateMainTask(FDCScheduleTaskInfo task,Set parentIDSet,Set excludeId) throws BOSException{
		FDCScheduleTaskCollection cols = null;
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("dependMainTaskID.name"));
		sic.add(new SelectorItemInfo("dependMainTaskID.number"));
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longnumber",task.getLongNumber()));
		filter.getFilterItems().add(new FilterItemInfo("longnumber",task.getLongNumber()+"!%",CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("dependMainTaskID",null,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("schedule",task.getSchedule().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("id",task.getId().toString(),CompareType.NOTEQUALS));
		if(parentIDSet.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("id",parentIDSet,CompareType.INCLUDE));
			filter.setMaskString("(#0 or #1 or  #5) and #2 and #3  and #4");
		}else{
			filter.setMaskString("(#0 or #1) and #2 and #3 and #4");
		}
		
		// 去除自己和已经删除的
		if(excludeId != null && excludeId.size() > 0){
			FilterInfo excludeFilter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",excludeId,CompareType.NOTINCLUDE));
			filter.mergeFilter(excludeFilter, "and");
		}
		
		view.setFilter(filter);
		cols = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		return cols;
	}
	
	
	
	
	
}
