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
public class ScheduleTaskFiledEnum extends StringEnum
{
    public static final String ALL_VALUE = "ALL";
    public static final String NUMBER_VALUE = "FNUMBER";
    public static final String NAME_VALUE = "FNAME_L2";

    public static final ScheduleTaskFiledEnum ALL = new ScheduleTaskFiledEnum("ALL", ALL_VALUE);
    public static final ScheduleTaskFiledEnum NUMBER = new ScheduleTaskFiledEnum("NUMBER", NUMBER_VALUE);
    public static final ScheduleTaskFiledEnum NAME = new ScheduleTaskFiledEnum("NAME", NAME_VALUE);

    /**
     * construct function
     * @param String scheduleTaskFiledEnum
     */
    private ScheduleTaskFiledEnum(String name, String scheduleTaskFiledEnum)
    {
        super(name, scheduleTaskFiledEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ScheduleTaskFiledEnum getEnum(String scheduleTaskFiledEnum)
    {
        return (ScheduleTaskFiledEnum)getEnum(ScheduleTaskFiledEnum.class, scheduleTaskFiledEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ScheduleTaskFiledEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ScheduleTaskFiledEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ScheduleTaskFiledEnum.class);
    }
}