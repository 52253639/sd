/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ScheduleStateEnum extends StringEnum
{
    public static final String SAVED_VALUE = "1SAVED";
    public static final String SUBMITTED_VALUE = "2SUBMITTED";
    public static final String AUDITTING_VALUE = "3AUDITTING";
    public static final String AUDITTED_VALUE = "4AUDITTED";
    public static final String STARTED_VALUE = "5STARTED";
    public static final String EXETING_VALUE = "6EXETING";
    public static final String CLOSED_VALUE = "7CLOSED";
    public static final String UNAUDITED_VALUE = "8UNAUDITED";
    public static final String ADJUSTED_VALUE = "9ADJUSTED";

    public static final ScheduleStateEnum SAVED = new ScheduleStateEnum("SAVED", SAVED_VALUE);
    public static final ScheduleStateEnum SUBMITTED = new ScheduleStateEnum("SUBMITTED", SUBMITTED_VALUE);
    public static final ScheduleStateEnum AUDITTING = new ScheduleStateEnum("AUDITTING", AUDITTING_VALUE);
    public static final ScheduleStateEnum AUDITTED = new ScheduleStateEnum("AUDITTED", AUDITTED_VALUE);
    public static final ScheduleStateEnum STARTED = new ScheduleStateEnum("STARTED", STARTED_VALUE);
    public static final ScheduleStateEnum EXETING = new ScheduleStateEnum("EXETING", EXETING_VALUE);
    public static final ScheduleStateEnum CLOSED = new ScheduleStateEnum("CLOSED", CLOSED_VALUE);
    public static final ScheduleStateEnum UNAUDITED = new ScheduleStateEnum("UNAUDITED", UNAUDITED_VALUE);
    public static final ScheduleStateEnum ADJUSTED = new ScheduleStateEnum("ADJUSTED", ADJUSTED_VALUE);

    /**
     * construct function
     * @param String scheduleStateEnum
     */
    private ScheduleStateEnum(String name, String scheduleStateEnum)
    {
        super(name, scheduleStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ScheduleStateEnum getEnum(String scheduleStateEnum)
    {
        return (ScheduleStateEnum)getEnum(ScheduleStateEnum.class, scheduleStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ScheduleStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ScheduleStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ScheduleStateEnum.class);
    }
}