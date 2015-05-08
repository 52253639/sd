package com.kingdee.eas.fdc.schedule.framework.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Relation;
import net.sf.mpxj.RelationType;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXReader;
import net.sf.mpxj.mspdi.MSPDIReader;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSTree;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.DependHardnessEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.util.KDEntityTree.KDEntityTreeNode;

public class KDProjectFileReader {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.framework.util.KDProjectFileReader");
	private ProjectFile mpx = null;
	private FDCScheduleInfo schedule;
	private FDCScheduleTaskInfo parentTask;
	private FDCWBSTree wbsTree;
	
	/* 责任人、责任部门是否为空 */
	public Map isPersonNulls = null;
	public Map isDeptNulls = null;
	
	/* 责任人、责任部门是否为重复 */
	public Map isPersonRepeats = null;
	public Map isDeptRepeats = null;
	
	/* 所有责任人 */
	private List allPersonList;
	private Map allPersonsMap;
	
	/* 所有责任部门 */
	private List allCostCenterOrgUnitList;
	private Map allCostCenterOrgUnitMap;

	public KDProjectFileReader(FDCScheduleInfo schedule, File file,FDCScheduleTaskInfo parentTask, FDCWBSTree wbsTree) throws MPXJException {
		try {
			this.wbsTree = wbsTree;
			this.schedule = schedule;
			this.parentTask = parentTask;
			isPersonNulls = new HashMap();
			isDeptNulls = new HashMap();
			isPersonRepeats = new HashMap();
			isDeptRepeats = new HashMap();
			allPersonList = new ArrayList();
			allCostCenterOrgUnitList = new ArrayList();
			allPersonsMap = new HashMap();
			allCostCenterOrgUnitMap = new HashMap();
			setDepartmentAndPersonValue();
			mpx = new MPPReader().read(file);
		} catch (Exception e) {
			try {
				mpx = new MPXReader().read(file);
			} catch (Exception ex) {
				mpx = new MSPDIReader().read(file);
			}
		}
	}
	
	/**
	 * @discription  <为责任人、责任部门赋值>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/08/24> <p>
	 * @param        <空>
	 * @return       <返回值描述>
	 * 
	 * modifier      <空> <p>
	 * modifyDate    <空> <p>
	 * modifyInfo	 <空> <p>
	 * @see          <相关的类>
	 */
	public void setDepartmentAndPersonValue(){
		try {
			PersonCollection personCollection = PersonFactory.getRemoteInstance().getPersonCollection();
			for(int k = 0; k < personCollection.size(); k ++){
				PersonInfo personInfo = personCollection.get(k);
				allPersonList.add(personInfo.getName());
				allPersonsMap.put(personInfo.getName().toString(), personInfo);
			}
			
			CostCenterOrgUnitCollection costCenterOrgUnitCollection = CostCenterOrgUnitFactory.getRemoteInstance().getCostCenterOrgUnitCollection();
			for(int k = 0; k < costCenterOrgUnitCollection.size(); k ++){
				CostCenterOrgUnitInfo costCenterOrgUnitInfo = costCenterOrgUnitCollection.get(k);
				allCostCenterOrgUnitList.add(costCenterOrgUnitInfo.getName());
				allCostCenterOrgUnitMap.put(costCenterOrgUnitInfo.getName().toString(), costCenterOrgUnitInfo);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @discription  <根据名称和类型获得责任人或责任部门的个数>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/08/24> <p>
	 * @param        <空>
	 * @return       <返回值描述>
	 * 
	 * modifier      <空> <p>
	 * modifyDate    <空> <p>
	 * modifyInfo	 <空> <p>
	 * @see          <相关的类>
	 */
	public int getCountByName(String name,String type){
		int count = 0;
		if("person".equals(type)){
			for(int k = 0; k < allPersonList.size(); k ++){
				if(null != allPersonList.get(k) && name.equals(allPersonList.get(k).toString().trim())){
					++ count; 
				}
			}
		}else{
			for(int k = 0; k < allCostCenterOrgUnitList.size(); k ++){
				if(null != allCostCenterOrgUnitList.get(k) && name.equals(allCostCenterOrgUnitList.get(k).toString().trim())){
					++ count; 
				}
			}
		}
		return count;
	}
	
	/**
	 * 所有任务是否在指定的时间范围内
	 * @param begin
	 * @param end
	 * @return
	 */
	public boolean verifyDateRange(Date begin, Date end){
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Task rootTask = (Task) mpx.getChildTasks().get(0);
		return df.format(rootTask.getStart()).compareTo(df.format(begin)) >= 0 && df.format(rootTask.getFinish()).compareTo(df.format(end)) <= 0;
		
	}
	public String getOnlyRootName(){
		Task first = (Task)mpx.getChildTasks().get(0);
		if(first.getChildTasks().size() == 1){
			return ((Task)first.getChildTasks().get(0)).getName();
		}
		return null;
	}
	public boolean isEmptyTask(){
		return mpx.getAllTasks().size() <= 1;
	}
	public boolean existEmptyName(){
		List projectTasks = mpx.getAllTasks();
		for (int i = 1; i < projectTasks.size(); ++i) {
			Task task = (Task) projectTasks.get(i);
			if(task.getName() == null || task.getName().trim().length() == 0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 描述： 校验Project日历和项目工程日历是否一致
	 * @return
	 * 创建时间：2010-7-30
	 * 创建人：zhiqiao_yang
	 */
//	public boolean verifyCalendar(){
//		ScheduleCalendarInfo scheduleCalendar = schedule.getCalendar();
//		ProjectCalendar projectCalendar = mpx.getBaseCalendar(mpx.getProjectHeader().getCalendarName());
//		System.out.println(mpx.getProjectHeader().getCalendarName());
//		if(scheduleCalendar != null  ){
//			if(projectCalendar != null){
//				if(!equalCalendar(scheduleCalendar,projectCalendar)){
//					return false;
//				}
//			}
//			List projectTasks = mpx.getAllTasks();
//			for (int i = 1; i < projectTasks.size(); ++i) {//过滤掉第一条任务，是根任务不用比较
//				Task task = (Task) projectTasks.get(i);
//				ProjectCalendar taskCalendar = task.getCalendar();
//				if(taskCalendar != null){
//					if(!equalCalendar(scheduleCalendar,taskCalendar)){
//						return false;
//					}
//				}				
//			}
//			return true;
//		}		
//		return false;
//	}
//	private boolean equalCalendar(ScheduleCalendarInfo scheduleCalendar, ProjectCalendar projectCalendar){
//		//当schedule比project多时检查不到
//		//scheduleCalendar.isWeekTime(date)
//		//String.valueOf((date.getYear()+1900)*10000+(date.getMonth()+1)*100+date.getDate());
//		//以ScheduleCalendar为标准，比较周末是否相同
//		DefaultWeekendEntryCollection weekendEntry =(DefaultWeekendEntryCollection)scheduleCalendar.getWeekendEntrys();
//		for(Iterator it =weekendEntry.iterator();it.hasNext();){
//			DefaultWeekendEntryInfo weekend = (DefaultWeekendEntryInfo)it.next();
//			ScheduleWeekendEnum schWeekend = (ScheduleWeekendEnum)weekend.getWeekend();
//			//ScheduleCalendar的星期一为休息日，而ProjectCalendar的星期一是工作日
//			if(schWeekend.equals(ScheduleWeekendEnum.Monday) && projectCalendar.isWorkingDay(Day.MONDAY)){
//				FDCMsgBox.showError("星期一的日历设置不一致！");
//				return false;
//			}
//			//ScheduleCalendar的星期二为休息日，而ProjectCalendar的星期二是工作日
//			if(schWeekend.equals(ScheduleWeekendEnum.Tuesday) && projectCalendar.isWorkingDay(Day.TUESDAY)){
//				FDCMsgBox.showError("星期二的日历设置不一致！");
//				return false;
//			}
//			//ScheduleCalendar的星期三为休息日，而ProjectCalendar的星期三是工作日
//			if(schWeekend.equals(ScheduleWeekendEnum.Wednesday) && projectCalendar.isWorkingDay(Day.WEDNESDAY)){
//				FDCMsgBox.showError("星期三的日历设置不一致！");
//				return false;
//			}
//			//ScheduleCalendar的星期四为休息日，而ProjectCalendar的星期四是工作日
//			if(schWeekend.equals(ScheduleWeekendEnum.Thursday) && projectCalendar.isWorkingDay(Day.THURSDAY)){
//				FDCMsgBox.showError("星期四的日历设置不一致！");
//				return false;
//			}
//			//ScheduleCalendar的星期五为休息日，而ProjectCalendar的星期五是工作日
//			if(schWeekend.equals(ScheduleWeekendEnum.Friday) && projectCalendar.isWorkingDay(Day.FRIDAY)){
//				FDCMsgBox.showError("星期五的日历设置不一致！");
//				return false;
//			}
//			//ScheduleCalendar的星期六为休息日，而ProjectCalendar的星期六是工作日
//			if(schWeekend.equals(ScheduleWeekendEnum.Saturday) && projectCalendar.isWorkingDay(Day.SATURDAY)){
//				FDCMsgBox.showError("星期六的日历设置不一致！");
//				return false;
//			}
//			//ScheduleCalendar的星期日为休息日，而ProjectCalendar的星期日是工作日
//			if(schWeekend.equals(ScheduleWeekendEnum.Sunday) && projectCalendar.isWorkingDay(Day.SUNDAY)){
//				FDCMsgBox.showError("星期日的日历设置不一致！");
//				return false;
//			}
//		}
//		
//		//以ProjectCalendar为标准，比较休息日是否相同
//		List exceptions = projectCalendar.getCalendarExceptions();//周末
//		for(int i = 0; i < exceptions.size(); ++i){
//			ProjectCalendarException exception = (ProjectCalendarException) exceptions.get(i);
//			//每个都是休息日（是时间段）
//			if(!exception.getWorking()){
//				Date fromDate = exception.getFromDate();
//				Date toDate =exception.getToDate();
//				for(Date date=fromDate;!toDate.before(date);){
//					//对应的时间在ScheduleCalendar中是工作日
//					if(!scheduleCalendar.isWeekTime(date)){
//						FDCMsgBox.showError(date.toString().substring(date.toString().length()-4)+"/"+(date.getMonth()+1)+"/"+date.getDate()+"日历设置不一致！");
//						return false;
//					}
//					date.setDate(date.getDate()+1); 
//
//				}
//			}			
//		}
//			
//		//以ScheduleCalendar为标准，比较节假日是否相同
//		HolidayEntryCollection holidayEntry =(HolidayEntryCollection)scheduleCalendar.getHolidayEntrys();
//		for(Iterator it =holidayEntry.iterator();it.hasNext();){
//			HolidayEntryInfo holiday = (HolidayEntryInfo)it.next();
//			Date holidayDate = holiday.getDate();
//			//ScheduleCalendar中的每天节假日在ProjectCalendar中为工作日
//			if(!projectCalendar.isWorkingDate(holidayDate)){
//				FDCMsgBox.showError(holidayDate+"节假日设置不一致！");
//				return false;
//			}
//		}
//			
//
//		
//		return true;
//	}
	private void removeScheduleWbs() {
		if (!isSpecialSchedule()) {
			Map deleteWbs = null;
			if (schedule.containsKey("delete_wbs")) {
				deleteWbs = (HashMap) schedule.get("delete_wbs");
			} else {
				deleteWbs = new HashMap();
				schedule.put("delete_wbs", deleteWbs);
			}
			if (wbsTree != null) {
				deleteWbs.putAll(wbsTree.getId2WBSMap());
			} else {
				FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
				for (int i = 0; i < taskEntrys.size(); ++i) {
					FDCScheduleTaskInfo taskInfo = taskEntrys.get(i);
					taskInfo.getWbs().put("isDelete", Boolean.TRUE);

					deleteWbs.put(taskInfo.getWbs().getId().toString(), taskInfo.getWbs());
				}
			}
			if (deleteWbs.isEmpty()) {
				schedule.remove("delete_wbs");
			}
			wbsTree.clear();
			schedule.getTaskEntrys().clear();

		}
	}

	//创建WBS
	private FDCWBSInfo createWBS(Integer rootId,Task task, Map projectTaskId2FdcTask){
		FDCWBSInfo wbs = new FDCWBSInfo();
		wbs.setId(BOSUuid.create(wbs.getBOSType()));
		Integer parentId = task.getParentTask().getUniqueID();
		if (!rootId.equals(parentId)) {
			FDCScheduleTaskInfo parent = (FDCScheduleTaskInfo) projectTaskId2FdcTask.get(parentId); 
			wbs.setParent(parent.getWbs());
		}else if (isSpecialSchedule()) {
			wbs.setParent(parentTask.getWbs());
		}		
		wbs.setIsEnabled(true);
		wbs.setBoolean("isNew", true);
		wbs.setIsFromTemplate(false);
		wbs.setCurProject(schedule.getProject());
		wbs.setName(task.getName());		
		wbs.setTaskType(schedule.getScheduleType());
		if (isSpecialSchedule()) {
			//专项则继承已选节点的责任人和责任部门
			wbs.setAdminPerson(parentTask.getWbs().getAdminPerson());
			wbs.setRespDept(parentTask.getWbs().getRespDept());
		}else{			
			//主项则设置计划部门为登陆用户的成本中心
			wbs.setAdminDept(SysContext.getSysContext().getCurrentCostUnit().castToFullOrgUnitInfo());
		}
		wbs.setIsUnVisible(false);
		BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(task.getStart(), task.getFinish(), schedule.getCalendar());
		wbs.setEstimateDays(effectTimes.intValue());
		
		return wbs;
	}
	private Date clearTime(Date date){
		Calendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MILLISECOND, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		return gc.getTime();
	}
	/**
	 * 描述：将Project文件中的任务及关系转换为FDC进度计划的数据模型
	 * @param isRemoveRoot 若Project文件中存在单独的根任务，是否删除此根任务
	 * @return 
	 * 创建时间：2010-7-22
	 * 创建人：zhiqiao_yang
	 */
	public FDCScheduleTaskCollection getFDCScheduleTask(boolean isRemoveRoot) {
		//删除计划中已有的WBS
		removeScheduleWbs();
		List projectTasks = mpx.getAllTasks();
		FDCScheduleTaskCollection fdcTasks = new FDCScheduleTaskCollection();
		//Project任务id映射FDC任务，便于在创建FDC搭建关系时使用
		Map projectTaskId2FdcTask = new HashMap();
		//处理任务
		processTasks(isRemoveRoot, projectTasks, projectTaskId2FdcTask, fdcTasks);
		//处理关系
		processDepends(projectTasks, projectTaskId2FdcTask);
		schedule.setInt("initIndex", schedule.getTaskEntrys().size());
		return fdcTasks;
	}
	private void processTasks(boolean isRemoveRoot, List projectTasks, Map projectTaskId2FdcTask, FDCScheduleTaskCollection fdcTasks) {
		ScheduleCalendarInfo calendarInfo = schedule.getCalendar();
		//必存在第一个任务，且其任务名称为Project文件名, 需忽略此任务
		final int begin = isRemoveRoot? 2 : 1;
		Integer rootId = ((Task) projectTasks.get(begin - 1)).getUniqueID();
		for (int i = begin; i < projectTasks.size(); ++i) {
			Task task = (Task) projectTasks.get(i);
			Integer id = task.getUniqueID();					
			FDCScheduleTaskInfo fdcTask = new FDCScheduleTaskInfo();
			fdcTask.setId(BOSUuid.create(fdcTask.getBOSType()));
			projectTaskId2FdcTask.put(id, fdcTask);		
			FDCWBSInfo wbs = createWBS(rootId, task, projectTaskId2FdcTask);
			fillTaskBaseInfo(wbs, fdcTask, task, projectTaskId2FdcTask);
			fdcTasks.add(fdcTask);		
			fillTaskTimeInfo(fdcTask, task, calendarInfo);
			fdcTask.setExpand(true);
			Integer parentId = task.getParentTask().getUniqueID();
			setParent(parentId, rootId, fdcTask, projectTaskId2FdcTask);
		}
		sortFDCTask(fdcTasks);
		reCalculateCode(fdcTasks);		
	}
	//设置FDC任务的parent及边界信息
	private void setParent(Integer parentId, Integer rootId,FDCScheduleTaskInfo fdcTask, Map projectTaskId2FdcTask){
		boolean isSpecialSchedule = isSpecialSchedule();
		if (rootId.equals(parentId)) {
			fdcTask.setParent(isSpecialSchedule? this.parentTask : null);
			if(isSpecialSchedule){
				fdcTask.setBoundStart(parentTask.getStart());
				fdcTask.setBoundEnd(parentTask.getEnd());	
			}
		} else {
			FDCScheduleTaskInfo parentTask = (FDCScheduleTaskInfo) projectTaskId2FdcTask.get(parentId);
			fdcTask.setParent(parentTask);
			fdcTask.setBoundStart(parentTask.getStart());
			fdcTask.setBoundEnd(parentTask.getEnd());		
		}
	}
	//重算WBS及任务编码
	private void reCalculateCode(FDCScheduleTaskCollection fdcTasks){
		FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
		for(int i = 0; i < fdcTasks.size(); ++i){		
			wbsTree.addChild(fdcTasks.get(i).getWbs());
		}
		wbsTree.testPrint();
		wbsTree.reCalculateCode();
		for(int i = 0; i < taskEntrys.size(); ++i){			
			FDCScheduleTaskInfo fdcTask =taskEntrys.get(i);
			FDCWBSInfo wbs = fdcTask.getWbs();
			fdcTask.setIsLeaf(wbs.isIsLeaf());
			fdcTask.setLongNumber(wbs.getLongNumber());
			fdcTask.setNumber(wbs.getLongNumber().replace('!', '.'));
			fdcTask.setLevel(wbs.getLevel());
			fdcTask.setSeq(wbs.getIndex());
		}
	}
	//任务排序，便于加载到计划中
	private void sortFDCTask(FDCScheduleTaskCollection fdcTasks){
		boolean isSpecialSchedule = isSpecialSchedule();
		if(isSpecialSchedule){
			FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
			String parentTaskId = parentTask.getId().toString();
			KDEntityTree entityTree = new KDEntityTree(schedule.getTaskEntrys(), null);
			KDEntityTreeNode node = entityTree.getNodeById(parentTaskId);
			List brothers = null;
			String nextInsertTaskId = null;
			while(true){
				if(node.getParent() != null){
					brothers = node.getParent().getChildren();
				}else{
					brothers = entityTree.getRoots();
				}
				for(int i = 0; i < brothers.size(); ++i){
					if(node.equals(brothers.get(i))){
						if(i + 1 < brothers.size()){
							nextInsertTaskId = ((KDEntityTreeNode)brothers.get(i+1)).getId();
						}	
					}
				}
				if(node.getParent() == null || nextInsertTaskId != null){
					break;
				}
				node = node.getParent();
			}
			if(nextInsertTaskId == null){
				schedule.getTaskEntrys().addCollection(fdcTasks);
			}else{
				int insertIndex = 0;
				for(int i = 0; i < schedule.getTaskEntrys().size(); ++i){
					if(nextInsertTaskId.equals(schedule.getTaskEntrys().get(i).getId().toString())){
						insertIndex = i;
						break;
					}
				}
				for(int i = 0; i < fdcTasks.size(); ++i){
					taskEntrys.addObject(insertIndex + i, fdcTasks.get(i));
				}
			}
		}else{
			schedule.getTaskEntrys().addCollection(fdcTasks);
		}
	}

	//设置FDC任务基本属性
	private void fillTaskBaseInfo(FDCWBSInfo wbs, FDCScheduleTaskInfo fdcTask, Task task, Map projectTaskId2FdcTask){
		fdcTask.setWbs(wbs);
		fdcTask.setAdminDept(wbs.getRespDept());
		fdcTask.setPlanDept(wbs.getAdminDept());
		fdcTask.setAdminPerson(wbs.getAdminPerson());
		fdcTask.setName(wbs.getName());
		fdcTask.setSchedule(schedule);
		fdcTask.setLocked(wbs.isIsLocked());
		fdcTask.setIsAdd(true);
		fdcTask.setIsScheduleTask(true);
		fdcTask.setPriority(1);
		validatePersonAndDept(fdcTask ,task,task.getText2(),task.getText3());
	}
	
	/**
	 * @discription  <验证责任人或责任部门是否重复>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/08/24> <p>
	 * @param        <空>
	 * @return       <返回值描述>
	 * 
	 * modifier      <空> <p>
	 * modifyDate    <空> <p>
	 * modifyInfo	 <空> <p>
	 * @see          <相关的类>
	 */
	public void validatePersonAndDept(FDCScheduleTaskInfo fdcTask, Task task, String person, String dept){
		if(null == person || "".equals(person.trim())){
			isPersonNulls.put(fdcTask.getId().toString(), "责任人___" + task.getUniqueID());
		}else{
			/* 判断责任人是否重复 */
			if(getCountByName(person, "person") > 1){
				isPersonRepeats.put(fdcTask.getId().toString(), "责任人___" + task.getUniqueID());
			}else{
				 if(null != allPersonsMap.get(person)){	
					 fdcTask.setAdminPerson((PersonInfo)allPersonsMap.get(person)); 
				 }
			}
		}
		if(null == dept || "".equals(dept.trim())){
			isDeptNulls.put(fdcTask.getId().toString(), "责任部门___" + task.getUniqueID());
		}else{
			/* 判断责任部门是否重复 */
			if(getCountByName(dept, "dept") > 1){
				isDeptRepeats.put(fdcTask.getId().toString(), "责任部门___" + task.getUniqueID());
			}else{
				if(null != allCostCenterOrgUnitMap.get(dept)){	
					 fdcTask.setAdminDept((FullOrgUnitInfo)allCostCenterOrgUnitMap.get(dept)); 
				 }
			}
		}
	}
	
	//设置时间工期等属性		
	private void fillTaskTimeInfo(FDCScheduleTaskInfo fdcTask, Task task, ScheduleCalendarInfo calendarInfo){
		fdcTask.setName(task.getName());
		Date start = task.getStart();
		start = clearTime(start);
		Date end = task.getFinish();
		end = clearTime(end);
		fdcTask.setStart(start);
		fdcTask.setEnd(end);
		BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(fdcTask.getStart(), fdcTask.getEnd(), calendarInfo);
		fdcTask.setEffectTimes(effectTimes);
		fdcTask.setBoolean("isImport", true);
		if(effectTimes.intValue() == 0){
			logger.error("error: duration=0; name="+fdcTask.getName()+"; startDate="+fdcTask.getStart()+"; endDate="+fdcTask.getEnd());
			fdcTask.setDuration(1);
		}else{
			fdcTask.setDuration(effectTimes.intValue());
		}
		fdcTask.setNatureTimes(ScheduleCalendarHelper.getNatureTimes(fdcTask.getStart(), fdcTask.getEnd()));
	}
	private void processDepends(List projectTasks, Map projectTaskId2FdcTask) {
		ScheduleCalendarInfo calendarInfo = schedule.getCalendar();
		for (int i = 1; i < projectTasks.size(); ++i) {
			Task task = (Task) projectTasks.get(i);
			Integer id = task.getUniqueID();
			List pres = task.getPredecessors();
			if (pres != null) {
				for (Iterator iter = pres.iterator(); iter.hasNext();) {
					Relation rel = (Relation) iter.next();
					if (!RelationType.START_FINISH.equals(rel.getType())) {
						Integer preId = rel.getSourceTask().getUniqueID();
						FDCScheduleTaskInfo preFDCTask = (FDCScheduleTaskInfo) projectTaskId2FdcTask.get(preId);
						FDCScheduleTaskInfo fdcTask = (FDCScheduleTaskInfo) projectTaskId2FdcTask.get(id);
						FDCScheduleTaskDependCollection depends = preFDCTask.getDependEntrys();
						FDCScheduleTaskDependInfo depend = new FDCScheduleTaskDependInfo();
						depends.add(depend);
						depend.setTask(preFDCTask);
						depend.setDependTask(fdcTask);
						depend.setType(getFDCLinkType(rel.getType()));
						
						depend.setDifference(getFDCLinkDays(depend, calendarInfo));
						depend.setHardness(DependHardnessEnum.Rubber);
					}
				}
			}
		}
	}

	private int getFDCLinkDays(FDCScheduleTaskDependInfo depend, ScheduleCalendarInfo calendarInfo) {
		FDCScheduleTaskInfo preFDCTask = depend.getTask();
		FDCScheduleTaskInfo fdcTask = depend.getDependTask();
		TaskLinkTypeEnum type = depend.getType();
		int ret = 0;
		if(TaskLinkTypeEnum.FINISH_FINISH.equals(type)){
			ret = ScheduleCalendarHelper.getEffectTimes(preFDCTask.getEnd(), fdcTask.getEnd(), calendarInfo).intValue();
			if(ret > 0){
				ret = ret - 1;
			}
		}else if(TaskLinkTypeEnum.FINISH_START.equals(type)){
			ret =  ScheduleCalendarHelper.getEffectTimes(preFDCTask.getEnd(), fdcTask.getStart(), calendarInfo).intValue();
			if(ret > 0){
				ret = ret - 2;
			}
		}else if(TaskLinkTypeEnum.START_START.equals(type)){
			ret =  ScheduleCalendarHelper.getEffectTimes(preFDCTask.getStart(), fdcTask.getStart(), calendarInfo).intValue();
			if(ret > 0){
				ret = ret - 1;
			}
		}	
		return ret;
	}

	private TaskLinkTypeEnum getFDCLinkType(RelationType relationType) {
		if (RelationType.FINISH_FINISH.equals(relationType)) {
			return TaskLinkTypeEnum.FINISH_FINISH;
		} else if (RelationType.START_START.equals(relationType)) {
			return TaskLinkTypeEnum.START_START;
		} else if (RelationType.FINISH_START.equals(relationType)) {
			return TaskLinkTypeEnum.FINISH_START;
		}
		return null;
	}
	private boolean isSpecialSchedule(){
		if(null != schedule.getScheduleType()){
			return TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(schedule.getScheduleType().getId().toString());
		}else{
			return false;
		}
	}
	
	
}
