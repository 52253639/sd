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
public class ScheduleAdjustTypeEnum extends StringEnum
{
    public static final String ADDDELNEWTASK_VALUE = "1AddDelNewTask";
    public static final String ADJUSTTASK_VALUE = "2AdjustTask";

    public static final ScheduleAdjustTypeEnum ADDDELNEWTASK = new ScheduleAdjustTypeEnum("ADDDELNEWTASK", ADDDELNEWTASK_VALUE);
    public static final ScheduleAdjustTypeEnum AdjustTask = new ScheduleAdjustTypeEnum("AdjustTask", ADJUSTTASK_VALUE);

    /**
     * construct function
     * @param String scheduleAdjustTypeEnum
     */
    private ScheduleAdjustTypeEnum(String name, String scheduleAdjustTypeEnum)
    {
        super(name, scheduleAdjustTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ScheduleAdjustTypeEnum getEnum(String scheduleAdjustTypeEnum)
    {
        return (ScheduleAdjustTypeEnum)getEnum(ScheduleAdjustTypeEnum.class, scheduleAdjustTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ScheduleAdjustTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ScheduleAdjustTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ScheduleAdjustTypeEnum.class);
    }
}