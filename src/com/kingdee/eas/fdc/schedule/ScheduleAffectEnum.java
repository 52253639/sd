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
public class ScheduleAffectEnum extends StringEnum
{
    public static final String MAINSCHEDULE_VALUE = "1MainAdjust";
    public static final String DEPTSCHEDULE_VALUE = "2DeptSchedule";

    public static final ScheduleAffectEnum MainSchedule = new ScheduleAffectEnum("MainSchedule", MAINSCHEDULE_VALUE);
    public static final ScheduleAffectEnum DeptSchedule = new ScheduleAffectEnum("DeptSchedule", DEPTSCHEDULE_VALUE);

    /**
     * construct function
     * @param String scheduleAffectEnum
     */
    private ScheduleAffectEnum(String name, String scheduleAffectEnum)
    {
        super(name, scheduleAffectEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ScheduleAffectEnum getEnum(String scheduleAffectEnum)
    {
        return (ScheduleAffectEnum)getEnum(ScheduleAffectEnum.class, scheduleAffectEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ScheduleAffectEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ScheduleAffectEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ScheduleAffectEnum.class);
    }
}