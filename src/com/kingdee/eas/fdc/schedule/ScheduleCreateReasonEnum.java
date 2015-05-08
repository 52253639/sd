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
public class ScheduleCreateReasonEnum extends StringEnum
{
    public static final String INITVER_VALUE = "1initver";
    public static final String DEPADJUST_VALUE = "2DepAdjust";
    public static final String OTHERDEPADJUST_VALUE = "3OtherDepAdjust";
    public static final String ADJUST_VALUE = "4Adjust";

    public static final ScheduleCreateReasonEnum InitVer = new ScheduleCreateReasonEnum("InitVer", INITVER_VALUE);
    public static final ScheduleCreateReasonEnum DepAdjust = new ScheduleCreateReasonEnum("DepAdjust", DEPADJUST_VALUE);
    public static final ScheduleCreateReasonEnum OtherDepAdjust = new ScheduleCreateReasonEnum("OtherDepAdjust", OTHERDEPADJUST_VALUE);
    public static final ScheduleCreateReasonEnum Adjust = new ScheduleCreateReasonEnum("Adjust", ADJUST_VALUE);

    /**
     * construct function
     * @param String scheduleCreateReasonEnum
     */
    private ScheduleCreateReasonEnum(String name, String scheduleCreateReasonEnum)
    {
        super(name, scheduleCreateReasonEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ScheduleCreateReasonEnum getEnum(String scheduleCreateReasonEnum)
    {
        return (ScheduleCreateReasonEnum)getEnum(ScheduleCreateReasonEnum.class, scheduleCreateReasonEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ScheduleCreateReasonEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ScheduleCreateReasonEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ScheduleCreateReasonEnum.class);
    }
}