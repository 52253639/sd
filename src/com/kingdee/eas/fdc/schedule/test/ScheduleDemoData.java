package com.kingdee.eas.fdc.schedule.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sourceforge.ganttproject.GanttCalendar;

import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.DefaultWeekendEntryInfo;
import com.kingdee.eas.fdc.schedule.framework.DependHardnessEnum;
import com.kingdee.eas.fdc.schedule.framework.HolidayEntryInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleWeekendEnum;

public class ScheduleDemoData {
	public ScheduleDemoData(){
		
	}
	public List getListItems(){
		List itemList=new ArrayList();
		itemList.add(new ScheduleDataItem("1.主项节点计划", getMainSchedule()));
		itemList.add(new ScheduleDataItem("2.专项节点计划", getSpecialSchedule()));
		itemList.add(new ScheduleDataItem("3.计划查看", getViewSchedule()));
		return itemList;
	}
	
	/**
	 * 计划查看
	 * @return
	 */
	private ScheduleBaseInfo getViewSchedule() {
		return null;
	}
	
	/**
	 * 专项
	 * @return
	 */
	private ScheduleBaseInfo getSpecialSchedule() {

		FDCScheduleInfo info=(FDCScheduleInfo)getScheduleHead();
		FDCScheduleTaskCollection taskEntrys = info.getTaskEntrys();
		FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
		taskInfo.setId(BOSUuid.create(taskInfo.getBOSType()));
		taskInfo.setLongNumber("001");
		taskInfo.setName("一级");
		taskInfo.setColor("#99ccff");
		taskInfo.setMeeting(false);
		taskInfo.setStart(getDate(2009, 12, 5));
		taskInfo.setDuration(26);
		taskInfo.setComplete(FDCHelper.toBigDecimal("75"));
		taskInfo.setPriority(1);
		taskInfo.setExpand(true);
		taskInfo.setSchedule(info);
		taskInfo.setParent(null);
		taskInfo.setEffectTimes(FDCHelper.ONE);
		taskInfo.setNatureTimes(FDCHelper.TEN);
		FullOrgUnitInfo org=new FullOrgUnitInfo();
		org.setName("深圳分公司");
		taskInfo.setAdminDept(org);
		PersonInfo person=new PersonInfo();
		person.setName("时小鸿");
		taskInfo.setAdminPerson(person);
		FDCWBSInfo wbs=new FDCWBSInfo();
		wbs.setName("一级");
		wbs.setLongNumber("001");
		taskInfo.setWbs(wbs);
		taskInfo.setEditable(true);
		taskInfo.setBoundStart(getDate(2009, 12, 5));
		taskInfo.setBoundEnd(getDate(2009, 12, 25));
		taskInfo.setIsScheduleTask(false); /////////
		taskEntrys.add(taskInfo);
		//001.001
		taskInfo = new FDCScheduleTaskInfo();
		taskInfo.setId(BOSUuid.create(taskInfo.getBOSType()));
		taskInfo.setLongNumber("001.001");
		taskInfo.setName("一级的下级");
		taskInfo.setColor("#99ccff");
		taskInfo.setMeeting(false);
		taskInfo.setStart(getDate(2009, 12, 9));
		taskInfo.setDuration(10);
		taskInfo.setBoundStart(getDate(2009, 12, 7));
		taskInfo.setBoundEnd(getDate(2009, 12, 25));
		taskInfo.setComplete(FDCHelper.toBigDecimal("100"));
		taskInfo.setPriority(1);
		taskInfo.setExpand(true);
		taskInfo.setSchedule(info);
		taskInfo.setParent(taskEntrys.get(0));
		taskInfo.put("WBS", "图纸设计");
		UserInfo user=new UserInfo();
		user.setId(BOSUuid.create(user.getBOSType()));
		user.setName("时小鸿");
		user.setNumber("sxhong");
		taskInfo.put("AdminUser", user);
		person=new PersonInfo();
		person.setId(BOSUuid.create(person.getBOSType()));
		person.setName("我");
		person.setNumber("001");
		taskInfo.setAdminPerson(person);
		FullOrgUnitInfo adminDept=new FullOrgUnitInfo();
		adminDept.setId(BOSUuid.create(adminDept.getBOSType()));
		adminDept.setName("中国");
		adminDept.setNumber("002");
		taskInfo.setAdminDept(adminDept);
		taskInfo.setIsScheduleTask(true); /////////
		taskEntrys.add(taskInfo);
		//001.001.001
		taskInfo = new FDCScheduleTaskInfo();
		taskInfo.setId(BOSUuid.create(taskInfo.getBOSType()));
		taskInfo.setLongNumber("001.001.001");
		taskInfo.setName("001.001的下级");
		taskInfo.setColor("#99ccff");
		taskInfo.setMeeting(false);
		taskInfo.setStart(getDate(2009, 12, 9));
		taskInfo.setDuration(10);
		taskInfo.setBoundStart(getDate(2009, 12, 7));
		taskInfo.setBoundEnd(getDate(2009, 12, 25));
		taskInfo.setComplete(FDCHelper.toBigDecimal("100"));
		taskInfo.setPriority(1);
		taskInfo.setExpand(true);
		taskInfo.setSchedule(info);
		taskInfo.setParent(taskEntrys.get(1));
		taskInfo.put("WBS", "图纸设计");
		user=new UserInfo();
		user.setId(BOSUuid.create(user.getBOSType()));
		user.setName("时小鸿");
		user.setNumber("sxhong");
		taskInfo.put("AdminUser", user);
		person=new PersonInfo();
		person.setId(BOSUuid.create(person.getBOSType()));
		person.setName("我");
		person.setNumber("001");
		taskInfo.setAdminPerson(person);
		adminDept=new FullOrgUnitInfo();
		adminDept.setId(BOSUuid.create(adminDept.getBOSType()));
		adminDept.setName("中国");
		adminDept.setNumber("002");
		taskInfo.setAdminDept(adminDept);
		taskInfo.setIsScheduleTask(true); /////////
		taskEntrys.add(taskInfo);
		//001.002
		taskInfo = new FDCScheduleTaskInfo();
		taskInfo.setId(BOSUuid.create(taskInfo.getBOSType()));
		taskInfo.setLongNumber("001.002");
		taskInfo.setName("一级的下级2");
		taskInfo.setColor("#99ccff");
		taskInfo.setMeeting(false);
		taskInfo.setStart(getDate(2009, 12, 9));
		taskInfo.setDuration(10);
		taskInfo.setBoundStart(getDate(2009, 12, 7));
		taskInfo.setBoundEnd(getDate(2009, 12, 25));
		taskInfo.setComplete(FDCHelper.toBigDecimal("100"));
		taskInfo.setPriority(1);
		taskInfo.setExpand(true);
		taskInfo.setSchedule(info);
		taskInfo.setParent(taskEntrys.get(0));
		taskInfo.put("WBS", "图纸设计");
		user=new UserInfo();
		user.setId(BOSUuid.create(user.getBOSType()));
		user.setName("时小鸿");
		user.setNumber("sxhong");
		taskInfo.put("AdminUser", user);
		person=new PersonInfo();
		person.setId(BOSUuid.create(person.getBOSType()));
		person.setName("我");
		person.setNumber("001");
		taskInfo.setAdminPerson(person);
		adminDept=new FullOrgUnitInfo();
		adminDept.setId(BOSUuid.create(adminDept.getBOSType()));
		adminDept.setName("中国");
		adminDept.setNumber("002");
		taskInfo.setAdminDept(adminDept);
		taskInfo.setIsScheduleTask(true); /////////
		taskEntrys.add(taskInfo);

		//003
		taskInfo = new FDCScheduleTaskInfo();
		taskInfo.setId(BOSUuid.create(taskInfo.getBOSType()));
		taskInfo.setLongNumber("003");
		taskInfo.setName("一级2");
		taskInfo.setColor("#99ccff");
		taskInfo.setMeeting(false);
		taskInfo.setStart(getDate(2009, 12, 7));
		taskInfo.setDuration(7);
		taskInfo.setComplete(FDCHelper.toBigDecimal("50"));
		taskInfo.setPriority(1);
		taskInfo.setExpand(true);
		taskInfo.setSchedule(info);
		taskInfo.setParent(null);
		taskInfo.setIsScheduleTask(true); /////////
		taskEntrys.add(taskInfo);
		//004 
		taskInfo = new FDCScheduleTaskInfo();
		taskInfo.setId(BOSUuid.create(taskInfo.getBOSType()));
		taskInfo.setLongNumber("004");
		taskInfo.setName("004");
		taskInfo.setColor("#99ccff");
		taskInfo.setMeeting(false);
		taskInfo.setStart(getDate(2009, 12, 16));
		taskInfo.setDuration(10);
		taskInfo.setComplete(FDCHelper.toBigDecimal("33"));
		taskInfo.setPriority(1);
		taskInfo.setExpand(true);
		taskEntrys.add(taskInfo);
		taskInfo.setSchedule(info);
		taskInfo.setIsScheduleTask(false);
		taskInfo.setParent(null);
		
/*		for(int i=0;i<taskEntrys.size();i++){
			taskEntrys.get(i).getStart().setYear(2009);
		}*/
		FDCScheduleTaskDependInfo depend = new FDCScheduleTaskDependInfo();
/*		depend.setTask(taskEntrys.get(0));
		depend.setDependTask(taskEntrys.get(1));
		taskEntrys.get(0).getDependEntrys().add(depend);
		
		depend = new FDCScheduleTaskDependInfo();
		depend.setTask(taskEntrys.get(0));
		depend.setDependTask(taskEntrys.get(2));
		taskEntrys.get(0).getDependEntrys().add(depend);*/
		
/*		depend = new FDCScheduleTaskDependInfo();
		depend.setTask(taskEntrys.get(2));
		depend.setDependTask(taskEntrys.get(3));
		depend.setHardness(DependHardnessEnum.Strong);
		depend.setDifference(2);
		depend.setType(TaskLinkTypeEnum.FINISH_START);
		taskEntrys.get(2).getDependEntrys().add(depend);*/
		
		
		for(int i=0;i<taskEntrys.size();i++){
//			taskEntrys.get(i).setIsScheduleTask(true);
//			taskEntrys.get(i).setIsAdd(true);
			taskEntrys.get(i).setNumber(taskEntrys.get(i).getLongNumber());
		}
		return info;
	}
	/**
	 * 主项
	 * @return
	 */
	private ScheduleBaseInfo getMainSchedule() {
		FDCScheduleInfo info=(FDCScheduleInfo)getScheduleHead();
		FDCScheduleTaskCollection taskEntrys = info.getTaskEntrys();
		FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
		taskInfo.setId(BOSUuid.create(taskInfo.getBOSType()));
		taskInfo.setLongNumber("001");
		taskInfo.setName("Architectural design");
		taskInfo.setColor("#99ccff");
		taskInfo.setMeeting(false);
		taskInfo.setStart(getDate(2009, 12, 9));
		taskInfo.setDuration(26);
		taskInfo.setComplete(FDCHelper.toBigDecimal("75"));
		taskInfo.setPriority(1);
		taskInfo.setExpand(true);
		taskInfo.setSchedule(info);
		taskInfo.setParent(null);
		taskInfo.setEffectTimes(FDCHelper.ONE);
		taskInfo.setNatureTimes(FDCHelper.TEN);
		FullOrgUnitInfo org=new FullOrgUnitInfo();
		org.setName("深圳分公司");
		taskInfo.setAdminDept(org);
		PersonInfo person=new PersonInfo();
		person.setName("时小鸿");
		taskInfo.setAdminPerson(person);
		FDCWBSInfo wbs=new FDCWBSInfo();
		wbs.setName("设计变更");
		wbs.setLongNumber("5001!01!03");
		taskInfo.setWbs(wbs);
		taskInfo.setEditable(true);
		taskEntrys.add(taskInfo);
		taskInfo.setBoundStart(getDate(2009, 12, 7));
		taskInfo.setBoundEnd(getDate(2009, 12, 25));
		taskInfo.setIsScheduleTask(false);
		//WBS
		
		// <task id="9" name="Create draft of architecture" color="#99ccff"
		// meeting="false" start="2006-01-09" duration="10" complete="100"
		taskInfo = new FDCScheduleTaskInfo();
		taskInfo.setId(BOSUuid.create(taskInfo.getBOSType()));
		taskInfo.setLongNumber("002");
		taskInfo.setName("Create draft of architecture");
		taskInfo.setColor("#99ccff");
		taskInfo.setMeeting(false);
		taskInfo.setStart(getDate(2009, 12, 9));
		taskInfo.setDuration(10);
		taskInfo.setBoundStart(getDate(2009, 12, 7));
		taskInfo.setBoundEnd(getDate(2009, 12, 25));
		
		taskInfo.setComplete(FDCHelper.toBigDecimal("100"));
		taskInfo.setPriority(1);
		taskInfo.setExpand(true);
		taskInfo.setSchedule(info);
		taskInfo.setParent(taskEntrys.get(0));
		taskInfo.put("WBS", "图纸设计");
		UserInfo user=new UserInfo();
		user.setId(BOSUuid.create(user.getBOSType()));
		user.setName("时小鸿");
		user.setNumber("sxhong");
		taskInfo.put("AdminUser", user);
		person=new PersonInfo();
		person.setId(BOSUuid.create(person.getBOSType()));
		person.setName("我");
		person.setNumber("001");
		taskInfo.setAdminPerson(person);
		
		FullOrgUnitInfo adminDept=new FullOrgUnitInfo();
		adminDept.setId(BOSUuid.create(adminDept.getBOSType()));
		adminDept.setName("中国");
		adminDept.setNumber("002");
		taskInfo.setAdminDept(adminDept);
		
		taskEntrys.add(taskInfo);

		// <task id="9" name="Create draft of architecture" color="#99ccff"
		// meeting="false" start="2006-01-09" duration="10" complete="100"
		taskInfo = new FDCScheduleTaskInfo();
		taskInfo.setId(BOSUuid.create(taskInfo.getBOSType()));
		taskInfo.setLongNumber("003");
		taskInfo.setName("Create draft of architecture");
		taskInfo.setColor("#99ccff");
		taskInfo.setMeeting(false);
		taskInfo.setStart(getDate(2009, 12, 7));
		taskInfo.setDuration(7);
		taskInfo.setComplete(FDCHelper.toBigDecimal("50"));
		taskInfo.setPriority(1);
		taskInfo.setExpand(true);
		taskInfo.setSchedule(info);
		taskInfo.setParent(null);;
		taskEntrys.add(taskInfo);
		// <task id="11" name="Interior design" color="#99ccff" meeting="false"
		// start="2006-01-23" duration="10" complete="33" priority="1"
		// expand="true">
		taskInfo = new FDCScheduleTaskInfo();
		taskInfo.setId(BOSUuid.create(taskInfo.getBOSType()));
		taskInfo.setLongNumber("004");
		taskInfo.setName("Interior design");
		taskInfo.setColor("#99ccff");
		taskInfo.setMeeting(false);
		taskInfo.setStart(getDate(2009, 12, 16));
		taskInfo.setDuration(10);
		taskInfo.setComplete(FDCHelper.toBigDecimal("33"));
		taskInfo.setPriority(1);
		taskInfo.setExpand(true);
		taskEntrys.add(taskInfo);
		taskInfo.setSchedule(info);
		taskInfo.setIsScheduleTask(true);
		taskInfo.setParent(null);
		
/*		for(int i=0;i<taskEntrys.size();i++){
			taskEntrys.get(i).getStart().setYear(2009);
		}*/
		FDCScheduleTaskDependInfo depend = new FDCScheduleTaskDependInfo();
/*		depend.setTask(taskEntrys.get(0));
		depend.setDependTask(taskEntrys.get(1));
		taskEntrys.get(0).getDependEntrys().add(depend);
		
		depend = new FDCScheduleTaskDependInfo();
		depend.setTask(taskEntrys.get(0));
		depend.setDependTask(taskEntrys.get(2));
		taskEntrys.get(0).getDependEntrys().add(depend);*/
		
		depend = new FDCScheduleTaskDependInfo();
		depend.setTask(taskEntrys.get(2));
		depend.setDependTask(taskEntrys.get(3));
		depend.setHardness(DependHardnessEnum.Strong);
		depend.setDifference(2);
		depend.setType(TaskLinkTypeEnum.FINISH_START);
		taskEntrys.get(2).getDependEntrys().add(depend);
		
		
		for(int i=0;i<taskEntrys.size();i++){
			taskEntrys.get(i).setIsScheduleTask(true);
			taskEntrys.get(i).setIsAdd(true);
			taskEntrys.get(i).setNumber(taskEntrys.get(i).getLongNumber());
		}
		return info;
	}

	private ScheduleBaseInfo getScheduleHead(){
    	FDCScheduleInfo info=new FDCScheduleInfo();
    	info.setId(BOSUuid.create(info.getBOSType()));
    	info.setName("My house building project");
    	FullOrgUnitInfo company=new FullOrgUnitInfo();
    	company.setNumber("0773");
    	company.setNumber("桂林分公司");
    	info.setOrgUnit(company);
    	info.setWebLink("www.myselfllc.ne");
    	Date date=getDate(2009, 12, 1);
    	info.setViewDate(date);
    	info.setGdivlocation(369);
    	info.setRdivlocation(322);
    	info.setGversion(FDCHelper.toBigDecimal("1.12"));
    	info.setViewIndex(0);
		// info.setTaskPropertys(ScheduleTaskPropertyInfo
		// .getDefaultScheduleTaskProperty(0));
    	info.setColor("#99ccff");
		// FDCScheduleTaskInfo.setCustomPropertysDebug(0);
    	info.setEditable(true);
    	info.setDisplayColumn(new String[]{"tpc0","tpd3","tpd4","tpd5","tpc1","tpc2","tpc3","tpc5","tpc6","tpc7"});
    	setCalendar(info);
    	return info;
	}
	
	public static class ScheduleDataItem{
		private String name;
		private ScheduleBaseInfo info;
		
		public ScheduleDataItem(String name,ScheduleBaseInfo info){
			this.name=name;
			this.info=info;
		}
		
		public String getName(){
			return this.name;
		}
		public ScheduleBaseInfo getInfo(){
			return this.info;
		}
		public String toString() {
			return name;
		}
	}
	
	private Date getDate(int year,int month,int day){
    	GanttCalendar cale=new GanttCalendar(year,month-1,day); 
    	return cale.getTime();
    }
	
	private void setCalendar(FDCScheduleInfo info){
		ScheduleCalendarInfo calendar=new ScheduleCalendarInfo();
		calendar.setId(BOSUuid.create(calendar.getBOSType()));
		
		HolidayEntryInfo holidayEntry=new HolidayEntryInfo();
		holidayEntry.setParent(calendar);
		holidayEntry.setDate(getDate(2009, 7, 10));
		calendar.getHolidayEntrys().add(holidayEntry);
		
		holidayEntry=new HolidayEntryInfo();
		holidayEntry.setParent(calendar);
		holidayEntry.setDate(getDate(2009, 7, 6));
		calendar.getHolidayEntrys().add(holidayEntry);
		
		DefaultWeekendEntryInfo weekendEntry=new DefaultWeekendEntryInfo();
		weekendEntry.setParent(calendar);
		weekendEntry.setWeekend(ScheduleWeekendEnum.Sunday);
		calendar.getWeekendEntrys().add(weekendEntry);
		weekendEntry=new DefaultWeekendEntryInfo();
		weekendEntry.setParent(calendar);
		weekendEntry.setWeekend(ScheduleWeekendEnum.Saturday);
		calendar.getWeekendEntrys().add(weekendEntry);
		
		info.setCalendar(calendar);
	}
}
