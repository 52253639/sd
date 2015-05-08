package com.kingdee.eas.fdc.schedule.framework.parser;

import net.sourceforge.ganttproject.calendar.GPCalendar;

import com.kingdee.eas.fdc.schedule.framework.DefaultWeekendEntryCollection;
import com.kingdee.eas.fdc.schedule.framework.HolidayEntryCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;

public class CalendarHandler  extends AbstractKDHandler implements IKDHandler {

    private GPCalendar myGPCalendar;
/*
    private Calendar myCalendar = GregorianCalendar.getInstance(Locale.CHINESE);

    private SimpleDateFormat myShortFormat = new SimpleDateFormat("EEE",
            Locale.ENGLISH);*/

    public CalendarHandler(GPCalendar calendar) {
        myGPCalendar = calendar;
        myGPCalendar.getPublicHolidays().clear();
        for (int i = 1; i <= 7; i++) {
            myGPCalendar.setWeekDayType(i, GPCalendar.DayType.WORKING);
        }
    }

    
    private void loadDefaultWeekend(ScheduleBaseInfo info) {
    	if (info.getCalendar() == null) {
			return;
		}
    	DefaultWeekendEntryCollection weekendEntrys = info.getCalendar().getWeekendEntrys();
    	for(int i=0;i<weekendEntrys.size();i++){
    		int value=weekendEntrys.get(i).getWeekend().getValue();
    		if(value>=1&&value<=7){
    			//1=ÐÇÆÚÌì  7=ÐÇÆÚÁù  1-7
    			myGPCalendar.setWeekDayType((value%7)+1, GPCalendar.DayType.WEEKEND);
    		}
    	}
    	
    }
    
    private void loadHoliday(ScheduleBaseInfo info) {
    	if (info.getCalendar() == null) {
			return;
		}
    	HolidayEntryCollection holidayEntrys = info.getCalendar().getHolidayEntrys();
    	for(int i=0;i<holidayEntrys.size();i++){
    		this.myGPCalendar.setPublicHoliDayType(holidayEntrys.get(i).getDate());
    	}
    }
    
    public void handle() {
    	ScheduleBaseInfo info = getScheduleBaseInfo();
    	loadDefaultWeekend(info);
    	loadHoliday(info);
    }
}
