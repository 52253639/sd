/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ScheduleTaskStatusEnum extends StringEnum
{
    public static final String NONE_VALUE = "0none";
    public static final String NOSTARTING_VALUE = "1nostarting";
    public static final String EXECUTING_VALUE = "2executing";
    public static final String FINISH_VALUE = "3finish";

    public static final ScheduleTaskStatusEnum NONE = new ScheduleTaskStatusEnum("NONE", NONE_VALUE);
    public static final ScheduleTaskStatusEnum NOSTARTING = new ScheduleTaskStatusEnum("NOSTARTING", NOSTARTING_VALUE);
    public static final ScheduleTaskStatusEnum EXECUTING = new ScheduleTaskStatusEnum("EXECUTING", EXECUTING_VALUE);
    public static final ScheduleTaskStatusEnum FINISH = new ScheduleTaskStatusEnum("FINISH", FINISH_VALUE);

    /**
     * construct function
     * @param String scheduleTaskStatusEnum
     */
    private ScheduleTaskStatusEnum(String name, String scheduleTaskStatusEnum)
    {
        super(name, scheduleTaskStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ScheduleTaskStatusEnum getEnum(String scheduleTaskStatusEnum)
    {
        return (ScheduleTaskStatusEnum)getEnum(ScheduleTaskStatusEnum.class, scheduleTaskStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ScheduleTaskStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ScheduleTaskStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ScheduleTaskStatusEnum.class);
    }
}