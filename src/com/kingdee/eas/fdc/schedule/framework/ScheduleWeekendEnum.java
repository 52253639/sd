/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class ScheduleWeekendEnum extends IntEnum
{
    public static final int MONDAY_VALUE = 1;
    public static final int TUESDAY_VALUE = 2;
    public static final int WEDNESDAY_VALUE = 3;
    public static final int THURSDAY_VALUE = 4;
    public static final int FRIDAY_VALUE = 5;
    public static final int SATURDAY_VALUE = 6;
    public static final int SUNDAY_VALUE = 7;
    public static final int NONE_VALUE = 8;

    public static final ScheduleWeekendEnum Monday = new ScheduleWeekendEnum("Monday", MONDAY_VALUE);
    public static final ScheduleWeekendEnum Tuesday = new ScheduleWeekendEnum("Tuesday", TUESDAY_VALUE);
    public static final ScheduleWeekendEnum Wednesday = new ScheduleWeekendEnum("Wednesday", WEDNESDAY_VALUE);
    public static final ScheduleWeekendEnum Thursday = new ScheduleWeekendEnum("Thursday", THURSDAY_VALUE);
    public static final ScheduleWeekendEnum Friday = new ScheduleWeekendEnum("Friday", FRIDAY_VALUE);
    public static final ScheduleWeekendEnum Saturday = new ScheduleWeekendEnum("Saturday", SATURDAY_VALUE);
    public static final ScheduleWeekendEnum Sunday = new ScheduleWeekendEnum("Sunday", SUNDAY_VALUE);
    public static final ScheduleWeekendEnum None = new ScheduleWeekendEnum("None", NONE_VALUE);

    /**
     * construct function
     * @param integer scheduleWeekendEnum
     */
    private ScheduleWeekendEnum(String name, int scheduleWeekendEnum)
    {
        super(name, scheduleWeekendEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ScheduleWeekendEnum getEnum(String scheduleWeekendEnum)
    {
        return (ScheduleWeekendEnum)getEnum(ScheduleWeekendEnum.class, scheduleWeekendEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static ScheduleWeekendEnum getEnum(int scheduleWeekendEnum)
    {
        return (ScheduleWeekendEnum)getEnum(ScheduleWeekendEnum.class, scheduleWeekendEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ScheduleWeekendEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ScheduleWeekendEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ScheduleWeekendEnum.class);
    }
}