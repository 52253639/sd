package com.kingdee.eas.fdc.schedule.test;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import net.sourceforge.ganttproject.GanttCalendar;

import com.kingdee.bos.ctrl.common.CtrlUIEnv;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.FDCSchTaskDispColumnsInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.DefaultWeekendEntryInfo;
import com.kingdee.eas.fdc.schedule.framework.DependHardnessEnum;
import com.kingdee.eas.fdc.schedule.framework.HolidayEntryInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleWeekendEnum;
import com.kingdee.eas.fdc.schedule.framework.TaskPropertyTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;

public class ScheduleDemo {
    public static void main(String[] args){
//    	boot.run(config, home, args)
    	CtrlUIEnv.setKingdeeLAF();
    	UIDefaults lookAndFeelDefaults = UIManager.getLookAndFeelDefaults();
//    	UIManager.put("Tree.hash", new ColorUIResource(255,255,0));
//    	lookAndFeelDefaults.put("TabbedPaneUI", "com.kingdee.bos.ctrl.swing.plaf.KingdeeTabbedPaneUI");
//    	UIManager.getLookAndFeelDefaults().put("SplitPaneUI", KingdeeSplitPaneUI.class.getName());
    	try {
    		final ScheduleDemo demo=new ScheduleDemo();
//    		SchedulePluginManager.resolvePlugins();
    		final ScheduleGanttProject baseUI=new ScheduleGanttProject(false);
//    		baseUI.getTree().getJTree().
    		baseUI.setEditData(demo.getTestData());
			baseUI.loadData(0);
			baseUI.setVisible(true);
//			KDFrame frame=new KDFrame("my Test");
//			frame.setContentPane(baseUI.getContentPane());
//			frame.setVisible(true);
			baseUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			baseUI.getTree().registerKeyboardAction(new AbstractAction() {
				
				public void actionPerformed(ActionEvent e) {
					ScheduleDemoDataSelectUI ui=new ScheduleDemoDataSelectUI(baseUI);
			    	ui.show();
			    	if(ui.getRetValue()!=null){
			    		baseUI.setEditData(ui.getRetValue());
			    		try {
			    			baseUI.loadData(0);
			    		} catch (Exception e1) {
			    			e1.printStackTrace();
			    		}
			    	}
			    	
				}
			},KeyStroke.getKeyStroke("ctrl shift F12"), JComponent.WHEN_IN_FOCUSED_WINDOW);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    
    public FDCScheduleInfo getTestData(){
    	FDCScheduleInfo info=new FDCScheduleInfo();
    	info.setId(BOSUuid.create(info.getBOSType()));
    	info.setName("My house building project");
    	FullOrgUnitInfo company=new FullOrgUnitInfo();
    	company.setNumber("0773");
    	company.setNumber("桂林分公司");
    	info.setOrgUnit(company);
    	info.setWebLink("www.myselfllc.ne");
    	Date date=getDate(2006, 1, 1);
    	info.setViewDate(date);
    	info.setGdivlocation(369);
    	info.setRdivlocation(322);
    	info.setGversion(FDCHelper.toBigDecimal("1.12"));
    	info.setViewIndex(0);
//taskProperty

/*    	info.setTaskPropertys(getTaskPropertys());
    	addCustomPropertys(info);*/
		// info.setTaskPropertys(ScheduleTaskPropertyInfo
		// .getDefaultScheduleTaskProperty(0));
    	info.setColor("#99ccff");
//tasks    	
		// FDCScheduleTaskInfo.setCustomPropertysDebug(0);
    	setTaskEntrys(info);
    	info.setEditable(true);
//    	setDisplayColumns(info);
//    	info.setDisplayColumn(new String[]{"tpd3","tpd4","tpd5","tpc1","tpc2","tpc3","tpc4","tpc5","tpc6","tpc7"});
    	info.setDisplayColumn(new String[]{"tpc0","tpd3","tpd4","tpd5","tpc1","tpc2","tpc3","tpc5","tpc6","tpc7"});
//    	info.setDisplayColumn(new String[]{"tpd4","tpd5","tpc0","tpc1","tpc2","tpc3","tpc5","tpc6","tpc7"});
    	setCalendar(info);
    	return info;
    }
	private void setDisplayColumns(FDCScheduleInfo info) {
		/*
        <displaycolumn property-id="tpd3" order="0" width="117" />
        <displaycolumn property-id="tpd4" order="1" width="92" />
        <displaycolumn property-id="tpd5" order="2" width="90" />
		*/
		FDCSchTaskDispColumnsInfo item=new FDCSchTaskDispColumnsInfo();
		item.setId(BOSUuid.create(item.getBOSType()));
		item.setProperty(getTaskProperty(info, "tpd3"));
		item.setOrder(0);
		item.setWidth(117);
		info.getDispColumns().add(item);
		
		item=new FDCSchTaskDispColumnsInfo();
		item.setId(BOSUuid.create(item.getBOSType()));
		item.setProperty(getTaskProperty(info, "tpd4"));
		item.setOrder(1);
		item.setWidth(92);
		info.getDispColumns().add(item);
		
		item=new FDCSchTaskDispColumnsInfo();
		item.setId(BOSUuid.create(item.getBOSType()));
		item.setProperty(getTaskProperty(info, "tpd5"));
		item.setOrder(2);
		item.setWidth(90);
		info.getDispColumns().add(item);
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
		weekendEntry.setWeekend(ScheduleWeekendEnum.Friday);
		calendar.getWeekendEntrys().add(weekendEntry);
		weekendEntry=new DefaultWeekendEntryInfo();
		weekendEntry.setParent(calendar);
		weekendEntry.setWeekend(ScheduleWeekendEnum.Saturday);
		calendar.getWeekendEntrys().add(weekendEntry);
		
		info.setCalendar(calendar);
	}
	private ScheduleTaskPropertyInfo getTaskProperty(FDCScheduleInfo info,String properityId){
		for(Iterator iter=info.getTaskPropertys().iterator();iter.hasNext();){
			ScheduleTaskPropertyInfo item=(ScheduleTaskPropertyInfo)iter.next();
			if(item.getPropertyId().equals(properityId)){
				return item;
			}
		}
		return null;
	}
	private FDCScheduleTaskCollection setTaskEntrys(FDCScheduleInfo info) {
		FDCScheduleTaskCollection taskEntrys = info.getTaskEntrys();
		FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
		// <task id="0" name="Architectural design" color="#99ccff"
		// meeting="false" start="2006-01-09" duration="26" complete="75"
		// priority="1" expand="true">
		taskInfo.setId(BOSUuid.create(taskInfo.getBOSType()));
		taskInfo.setLongNumber("001");
		taskInfo.setName("Architectural design");
		taskInfo.setColor("#99ccff");
		taskInfo.setMeeting(false);
		taskInfo.setStart(getDate(2006, 1, 9));
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
		taskInfo.setBoundStart(getDate(2006, 1, 7));
		taskInfo.setBoundEnd(getDate(2006, 1, 25));
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
		taskInfo.setStart(getDate(2006, 1, 9));
		taskInfo.setDuration(10);
		taskInfo.setBoundStart(getDate(2006, 1, 7));
		taskInfo.setBoundEnd(getDate(2006, 1, 25));
		
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
		taskInfo.setStart(getDate(2006, 1, 7));
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
		taskInfo.setStart(getDate(2006, 1, 16));
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
		
		for(int i=taskEntrys.size()-1;i>=1;i--){
			//只保留一个测试节点
			taskEntrys.removeObject(i);
		}
		taskEntrys.get(0).setIsScheduleTask(true);
		return taskEntrys;
	}

    private Date getDate(int year,int month,int day){
    	GanttCalendar cale=new GanttCalendar(year,month-1,day); 
    	return cale.getTime();
    }
    
    private ScheduleTaskPropertyCollection getTaskPropertys(){
    	/*
    	<taskproperty id="tpd0" name="type" type="default" valuetype="icon" />      
        <taskproperty id="tpd1" name="priority" type="default" valuetype="icon" />
        <taskproperty id="tpd2" name="info" type="default" valuetype="icon" />
        <taskproperty id="tpd3" name="name" type="default" valuetype="text" />
        <taskproperty id="tpd4" name="begindate" type="default" valuetype="date" />
        <taskproperty id="tpd5" name="enddate" type="default" valuetype="date" />
        <taskproperty id="tpd6" name="duration" type="default" valuetype="int" />
        <taskproperty id="tpd7" name="completion" type="default" valuetype="int" />
        <taskproperty id="tpd8" name="coordinator" type="default" valuetype="text" />
        <taskproperty id="tpd9" name="predecessors" type="default" valuetype="text" />
        */
    	ScheduleTaskPropertyCollection taskPropertys=new ScheduleTaskPropertyCollection();
    	ScheduleTaskPropertyInfo propertyInfo=new ScheduleTaskPropertyInfo();
    	propertyInfo.setId(BOSUuid.create(propertyInfo.getBOSType()));
    	propertyInfo.setPropertyId("tpd0");
    	propertyInfo.setName("type");
    	propertyInfo.setType(TaskPropertyTypeEnum.DEFAULT);
    	propertyInfo.setValueType("icon");
    	taskPropertys.add(propertyInfo);
    	
    	propertyInfo=new ScheduleTaskPropertyInfo();
    	propertyInfo.setId(BOSUuid.create(propertyInfo.getBOSType()));
    	propertyInfo.setPropertyId("tpd1");
    	propertyInfo.setName("priority");
    	propertyInfo.setType(TaskPropertyTypeEnum.DEFAULT);
    	propertyInfo.setValueType("icon");
    	taskPropertys.add(propertyInfo);
    	
    	propertyInfo=new ScheduleTaskPropertyInfo();
    	propertyInfo.setId(BOSUuid.create(propertyInfo.getBOSType()));
    	propertyInfo.setPropertyId("tpd2");
    	propertyInfo.setName("info");
    	propertyInfo.setType(TaskPropertyTypeEnum.DEFAULT);
    	propertyInfo.setValueType("icon");
    	taskPropertys.add(propertyInfo);
    	
    	propertyInfo=new ScheduleTaskPropertyInfo();
    	propertyInfo.setId(BOSUuid.create(propertyInfo.getBOSType()));
    	propertyInfo.setPropertyId("tpd3");
    	propertyInfo.setName("name");
    	propertyInfo.setType(TaskPropertyTypeEnum.DEFAULT);
    	propertyInfo.setValueType("text");
    	taskPropertys.add(propertyInfo);
    	
    	propertyInfo=new ScheduleTaskPropertyInfo();
    	propertyInfo.setId(BOSUuid.create(propertyInfo.getBOSType()));
    	propertyInfo.setPropertyId("tpd4");
    	propertyInfo.setName("begindate");
    	propertyInfo.setType(TaskPropertyTypeEnum.DEFAULT);
    	propertyInfo.setValueType("date");
    	taskPropertys.add(propertyInfo);
    	
    	propertyInfo=new ScheduleTaskPropertyInfo();
    	propertyInfo.setId(BOSUuid.create(propertyInfo.getBOSType()));
    	propertyInfo.setPropertyId("tpd5");
    	propertyInfo.setName("enddate");
    	propertyInfo.setType(TaskPropertyTypeEnum.DEFAULT);
    	propertyInfo.setValueType("date");
    	taskPropertys.add(propertyInfo);
    	
    	propertyInfo=new ScheduleTaskPropertyInfo();
    	propertyInfo.setId(BOSUuid.create(propertyInfo.getBOSType()));
    	propertyInfo.setPropertyId("tpd6");
    	propertyInfo.setName("duration");
    	propertyInfo.setType(TaskPropertyTypeEnum.DEFAULT);
    	propertyInfo.setValueType("int");
    	taskPropertys.add(propertyInfo);
    	
    	propertyInfo=new ScheduleTaskPropertyInfo();
    	propertyInfo.setId(BOSUuid.create(propertyInfo.getBOSType()));
    	propertyInfo.setPropertyId("tpd7");
    	propertyInfo.setName("completion");
    	propertyInfo.setType(TaskPropertyTypeEnum.DEFAULT);
    	propertyInfo.setValueType("int");
    	taskPropertys.add(propertyInfo);
    	
    	propertyInfo=new ScheduleTaskPropertyInfo();
    	propertyInfo.setId(BOSUuid.create(propertyInfo.getBOSType()));
    	propertyInfo.setPropertyId("tpd8");
    	propertyInfo.setName("coordinator");
    	propertyInfo.setType(TaskPropertyTypeEnum.DEFAULT);
    	propertyInfo.setValueType("text");
    	taskPropertys.add(propertyInfo);
    	
    	propertyInfo=new ScheduleTaskPropertyInfo();
    	propertyInfo.setId(BOSUuid.create(propertyInfo.getBOSType()));
    	propertyInfo.setPropertyId("tpd9");
    	propertyInfo.setName("predecessors");
    	propertyInfo.setType(TaskPropertyTypeEnum.DEFAULT);
    	propertyInfo.setValueType("text");
    	taskPropertys.add(propertyInfo);
    	return taskPropertys;
    }
    
    private void addCustomPropertys(FDCScheduleInfo info){
    	ScheduleTaskPropertyCollection taskPropertys=info.getTaskPropertys();
    	ScheduleTaskPropertyInfo propertyInfo=new ScheduleTaskPropertyInfo();
    	propertyInfo.setId(BOSUuid.create(propertyInfo.getBOSType()));
    	propertyInfo.setPropertyId("tpc0");
    	propertyInfo.setName("WBS编码");
    	propertyInfo.setType(TaskPropertyTypeEnum.CUSTOM);
    	propertyInfo.setValueType("text");
    	taskPropertys.add(propertyInfo);
    	
    	FDCSchTaskDispColumnsInfo item=new FDCSchTaskDispColumnsInfo();
		item.setId(BOSUuid.create(item.getBOSType()));
		item.setProperty(propertyInfo);
		item.setOrder(3);
		item.setWidth(90);
		info.getDispColumns().add(item);
		
    	propertyInfo=new ScheduleTaskPropertyInfo();
    	propertyInfo.setId(BOSUuid.create(propertyInfo.getBOSType()));
    	propertyInfo.setPropertyId("tpc1");
    	propertyInfo.setName("AdminUser");
    	propertyInfo.setType(TaskPropertyTypeEnum.CUSTOM);
    	propertyInfo.setValueType("text");
    	taskPropertys.add(propertyInfo);
    	
    	item=new FDCSchTaskDispColumnsInfo();
		item.setId(BOSUuid.create(item.getBOSType()));
		item.setProperty(propertyInfo);
		item.setOrder(4);
		item.setWidth(90);
		info.getDispColumns().add(item);
		
		
    }
}
