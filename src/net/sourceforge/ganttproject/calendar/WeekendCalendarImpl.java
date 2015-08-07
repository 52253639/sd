/*
 * Created on 18.10.2004
 */
package net.sourceforge.ganttproject.calendar;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttProject;
import net.sourceforge.ganttproject.cache.ActivityCache;
import net.sourceforge.ganttproject.cache.NonWorkingDayCache;
import net.sourceforge.ganttproject.parser.HolidayTagHandler;
import net.sourceforge.ganttproject.task.TaskLength;
import net.sourceforge.ganttproject.time.TimeUnit;
import net.sourceforge.ganttproject.time.gregorian.FramerImpl;

import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

/**
 * @author bard
 */
public class WeekendCalendarImpl extends GPCalendarBase implements GPCalendar {

    private final Calendar myCalendar = CalendarFactory.newCalendar();

    private final FramerImpl myFramer = new FramerImpl(Calendar.DAY_OF_WEEK);

    private DayType[] myTypes = new DayType[7];

    private int myWeekendDaysCount;

    private Set publicHolidaysArray = new LinkedHashSet();

    private final Set myStableHolidays = new LinkedHashSet();
    
    private AlwaysWorkingTimeCalendarImpl myRestlessCalendar = new AlwaysWorkingTimeCalendarImpl();

    public WeekendCalendarImpl() {
        for (int i = 0; i < myTypes.length; i++) {
            myTypes[i] = GPCalendar.DayType.WORKING;
        }
        setWeekDayType(GregorianCalendar.SATURDAY, GPCalendar.DayType.WEEKEND);
        setWeekDayType(GregorianCalendar.SUNDAY, GPCalendar.DayType.WEEKEND);
    }

    /* 
     * 
     * 对getActivities 的要求应该是这样的:
     * 如果有三天,12,13,14,15 其中13,14是周末,应该这样分段:12-12,13-14,15-15
     * @see net.sourceforge.ganttproject.calendar.GPCalendar#getActivities(java.util.Date, java.util.Date)
     */
    public List getActivities(Date startDate, final Date endDate) {
		ActivityCache cache = ActivityCache.getInstance();
		List ret = cache.getActivity(startDate, endDate);
		if (ret != null) {
			return ret;
		}
		if (myWeekendDaysCount == 0 && publicHolidaysArray.isEmpty()) {
			return myRestlessCalendar.getActivities(startDate, endDate);
		}
		List result = new ArrayList();
		Date curDayStart = startDate;
		boolean isWeekendState = isNonWorkingDay(curDayStart);
		while (curDayStart.compareTo(endDate) <= 0) {
			// 获取状态改变的时间点,即周末转工作日、工作日转周末
			Date changeStateDayStart = getBeforeStateChangeDate(curDayStart, endDate, !isWeekendState);
			if (changeStateDayStart.compareTo(endDate) <= 0) {
				result.add(new CalendarActivityImpl(curDayStart, changeStateDayStart, !isWeekendState));
				curDayStart = myFramer.adjustRight(changeStateDayStart);
				isWeekendState = !isWeekendState;
				continue;
			} else {
				result.add(new CalendarActivityImpl(curDayStart, endDate, !isWeekendState));
				break;
			}
		}
		cache.putActivity(startDate, endDate, result);
		return result;
	}
    
	/**
	 * 
	 * @param curDayStart
	 * @return
	 */
	public List getActivities(Date startDate, final Date endDate, KDTask task) {
		ActivityCache cache = ActivityCache.getInstance();
		List ret = cache.getActivity(startDate, endDate);
		if (ret != null) {
			return ret;
		}
		if (myWeekendDaysCount == 0 && publicHolidaysArray.isEmpty()) {
			return myRestlessCalendar.getActivities(startDate, endDate);
		}
		List result = new ArrayList();
		Date curDayStart = startDate;
		boolean isWeekendState = isNonWorkingDay(curDayStart);
		while (curDayStart.compareTo(endDate) <= 0) {
			// 获取状态改变的时间点,即周末转工作日、工作日转周末
			Date changeStateDayStart = getBeforeStateChangeDate(curDayStart,
					endDate, !isWeekendState);
			if (changeStateDayStart.compareTo(endDate) <= 0) {
				result.add(new CalendarActivityImpl(curDayStart,
						changeStateDayStart, !isWeekendState));
				curDayStart = myFramer.adjustRight(changeStateDayStart);
				isWeekendState = !isWeekendState;
				continue;
			} else {
				result.add(new CalendarActivityImpl(curDayStart, endDate,
						!isWeekendState));
				break;
			}
		}
		cache.putActivity(startDate, endDate, result);
		return result;
	}

    public boolean isWeekend(Date curDayStart) {
        myCalendar.setTime(curDayStart);
        int dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek > 0 && dayOfWeek <= 7)
        	return myTypes[dayOfWeek - 1] == GPCalendar.DayType.WEEKEND;
        else
        	return true;
    }

    /**
     * 以前的方法,用于取得节假日改变后的第一天,如有12,13,14,15 且 13,14是节假日,则会取15
     * @param startDate
     * @param changeToWeekend
     * @return
     */
    private Date getStateChangeDate(Date startDate, boolean changeToWeekend) {
        Date nextDayStart = myFramer.adjustRight(startDate);
        if (!(changeToWeekend ^ isNonWorkingDay(nextDayStart))) {//两个表达式相同时返回
            return nextDayStart;
        } else {
            return getStateChangeDate(nextDayStart, changeToWeekend);
        }
    }

    /**
     * 以前的方法,用于取得节假日改变后的第一天,如有12,13,14,15 且 13,14是节假日,则会取14
     * @param startDate
     * @param changeToWeekend
     * @return
     */
    private Date getBeforeStateChangeDate(Date startDate, Date endDate, boolean changeToWeekend) {
    	if (startDate.after(endDate)) {
			return endDate;
		}
        Date nextDayStart = myFramer.adjustRight(startDate);
        if (!(changeToWeekend ^ isNonWorkingDay(nextDayStart))) {//两个表达式相同时返回
//            return nextDayStart;
        	return startDate; //工期调整成1的后遗症
        } else {
            return getBeforeStateChangeDate(nextDayStart, endDate, changeToWeekend);
        }
    }
    private Date getStateChangeDate(Date startDate, TimeUnit timeUnit,
            boolean changeToWeekend, boolean moveRightNotLeft) {
        Date nextUnitStart = moveRightNotLeft ? timeUnit.adjustRight(startDate)
                : timeUnit.jumpLeft(startDate);
        if (!(changeToWeekend ^ isNonWorkingDay(nextUnitStart))) {
            return nextUnitStart;
        } else {
            return getStateChangeDate(nextUnitStart, timeUnit, changeToWeekend,
                    moveRightNotLeft);
        }

    }

    /**
     * 返回状态变化前的日期
     * @param startDate
     * @param timeUnit
     * @param changeToWeekend
     * @param moveRightNotLeft
     * @return
     */
    private Date getBeforeStateChangeDate(Date startDate, TimeUnit timeUnit,
            boolean changeToWeekend, boolean moveRightNotLeft) {
        Date nextUnitStart = moveRightNotLeft ? timeUnit.adjustRight(startDate)
                : timeUnit.jumpLeft(startDate);
        if (!(changeToWeekend ^ isNonWorkingDay(nextUnitStart))) {
            return startDate;//返回状态变化前的日期
        } else {
            return getBeforeStateChangeDate(nextUnitStart, timeUnit, changeToWeekend,
                    moveRightNotLeft);
        }

    }
    
    /* duration 就是有效工期 unitCount=1 表示当天到当天  传入 12,13,14,15,16 其中13,14 是假期的话应该返回 12-12,13-14,15-15,16-16 
     * @see net.sourceforge.ganttproject.calendar.GPCalendarBase#getActivitiesForward(java.util.Date, net.sourceforge.ganttproject.time.TimeUnit, long)
     */
    protected List getActivitiesForward(Date startDate, TimeUnit timeUnit,
            long unitCount) {
//    	TimeTools.getInstance().msValuePrintln("week!!!!!!!!!!!!!!!!!!getActivitiesForward_S");
        List result = new ArrayList();
        Date unitStart = startDate;//timeUnit.adjustLeft(startDate);
        while (unitCount > 0) {
            boolean isWeekendState = isNonWorkingDay(unitStart);
            if (isWeekendState) {
                Date workingUnitStart = getBeforeStateChangeDate(unitStart, timeUnit,
                        false, true);
                result.add(new CalendarActivityImpl(unitStart,
                        workingUnitStart, false));
                unitStart = timeUnit.adjustRight(workingUnitStart);
                continue;
            }
            
            result.add(new CalendarActivityImpl(unitStart, unitStart,true));
            unitCount--;
            unitStart = timeUnit.adjustRight(unitStart);;//往下渐进
            
        }
//        TimeTools.getInstance().msValuePrintln("week!!!!!!!!!!!!!!!!!!getActivitiesForward_E");
        return result;
    }

    protected List getActivitiesBackward(Date startDate, TimeUnit timeUnit,
            long unitCount) {
//    	TimeTools.getInstance().msValuePrintln("week!!!!!!!!!!!!!!!!!!getActivitiesBackward_S");
        List result = new LinkedList();
        Date unitStart = startDate;//timeUnit.adjustLeft(startDate);
        while (unitCount >= 0) {
            boolean isWeekendState = isNonWorkingDay(unitStart);
            if (isWeekendState) {
            	//前一个工作日
                Date lastWorkingUnitStart = getStateChangeDate(unitStart,timeUnit, false, false);
                //调整到工作日的后一天,也即是非工作日的终点
                Date firstWeekendUnitStart = timeUnit.adjustRight(lastWorkingUnitStart);
                Date lastWeekendUnitEnd = unitStart;
                result.add(0, new CalendarActivityImpl(lastWorkingUnitStart,lastWeekendUnitEnd, false));
                unitStart = firstWeekendUnitStart;
                unitCount --;
            } else {
                result.add(0, new CalendarActivityImpl(unitStart,unitStart, true));
                unitCount--;
                unitStart = timeUnit.jumpLeft(unitStart);
            }
        }
//        TimeTools.getInstance().msValuePrintln("week!!!!!!!!!!!!!!!!!!getActivitiesBackward_E");
        return result;
    }

    public void setWeekDayType(int day, DayType type) {
        if (type != myTypes[day - 1]) {
            myWeekendDaysCount += (type == DayType.WEEKEND ? 1 : -1);
        }
        myTypes[day - 1] = type;
    }

    public DayType getWeekDayType(int day) {
        return myTypes[day - 1];
    }

    public Date findClosestWorkingTime(Date time) {
    	if (myWeekendDaysCount == 0 && publicHolidaysArray.isEmpty()) {
            return time;
        }
        if (!isNonWorkingDay(time)) {
            return time;
        }
        return getStateChangeDate(time, false);
    }

    public void setPublicHoliDayType(int month, int date) {
    	Date time = new GanttCalendar(1, month - 1, date).getTime();
		setPublicHoliDayType(time);
		myStableHolidays.add(time);
    }

    public void setPublicHoliDayType(Date curDayStart) {
        publicHolidaysArray.add(curDayStart);
    }

    public boolean isPublicHoliDay(Date curDayStart) {
        boolean result = publicHolidaysArray.contains(curDayStart);
        if (!result) {
            result = myStableHolidays.contains(new GanttCalendar(1, curDayStart.getMonth(), curDayStart.getDate()).getTime()); 
        }
        return result;
    }

    public DayType getDayTypeDate(Date curDayStart) {
        myCalendar.setTime(curDayStart);
        int dayOfYear = myCalendar.get(Calendar.DAY_OF_YEAR);
        myCalendar.setTime(curDayStart);
        int dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK);
        if ((!isPublicHoliDay(curDayStart))
                && (getWeekDayType(dayOfWeek) == GPCalendar.DayType.WORKING))
            return GPCalendar.DayType.WORKING;
        else if (isPublicHoliDay(curDayStart))
            return GPCalendar.DayType.HOLIDAY;
        else
            return GPCalendar.DayType.WEEKEND;
    }

    public boolean isNonWorkingDay(Date curDayStart) {
    	NonWorkingDayCache cache = NonWorkingDayCache.getInstance();
    	Boolean b = cache.get(curDayStart);
    	if(b != null){
    		return b.booleanValue();
    	}
    	boolean ret = false;
        if (isWeekend(curDayStart) || isPublicHoliDay(curDayStart)){
            ret = true;
        }
        cache.put(curDayStart, ret);
        return ret;
    }

    public void setPublicHolidays(URL calendar, GanttProject gp) {
        publicHolidaysArray.clear();
        if (calendar != null) {
            XMLCalendarOpen opener = new XMLCalendarOpen();

            HolidayTagHandler dependencyHandler = new HolidayTagHandler(gp);

            opener.addTagHandler(dependencyHandler);
            opener.addParsingListener(dependencyHandler);
            try {
                opener.load(calendar.openStream());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public Collection getPublicHolidays() {
        return publicHolidaysArray;
    }

    public List getActivities(Date startingFrom, TaskLength period) {
        return getActivities(startingFrom, period.getTimeUnit(), period
                .getLength());
    }

    /* 
     * realized by sxhong 
     * @see net.sourceforge.ganttproject.calendar.GPCalendarBase#shiftDate(java.util.Date, net.sourceforge.ganttproject.task.TaskLength)
     */
    public Date shiftDate(Date input, TaskLength shift) {
        List activities = getActivities(input, shift);
        if (activities.isEmpty()) {
        	return input;
/*            throw new RuntimeException(
                    "FIXME: Failed to compute calendar activities in time period="
                            + shift + " starting from " + input);*/
        }
        Date result;
        if (shift.getValue() >= 0) {
            GPCalendarActivity lastActivity = (GPCalendarActivity) activities
                    .get(activities.size() - 1);
            result = lastActivity.getEnd();
        } else {
            GPCalendarActivity firstActivity = (GPCalendarActivity) activities
                    .get(0);
            result = firstActivity.getStart();
        }
        return result;

    }
}
