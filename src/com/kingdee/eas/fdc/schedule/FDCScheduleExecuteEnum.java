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
public class FDCScheduleExecuteEnum extends StringEnum
{
    public static final String TASK_ALL_VALUE = "TASK0";
    public static final String TASK_OVERDUE_VALUE = "TASK1";
    public static final String TASK_MYTREADO_VALUE = "TASK2";
    public static final String TASK_WEEKEND_VALUE = "TASK3";
    public static final String TASK_WEEKBEGIN_VALUE = "TASK4";
    public static final String TASK_MYEVALUATE_VALUE = "TASK5";
    public static final String TASK_MILEPOST_VALUE = "TASK6";
    public static final String TASK_HINGE_VALUE = "TASK7";

    public static final FDCScheduleExecuteEnum TASK_ALL = new FDCScheduleExecuteEnum("TASK_ALL", TASK_ALL_VALUE);
    public static final FDCScheduleExecuteEnum TASK_OVERDUE = new FDCScheduleExecuteEnum("TASK_OVERDUE", TASK_OVERDUE_VALUE);
    public static final FDCScheduleExecuteEnum TASK_MYTREADO = new FDCScheduleExecuteEnum("TASK_MYTREADO", TASK_MYTREADO_VALUE);
    public static final FDCScheduleExecuteEnum TASK_WEEKEND = new FDCScheduleExecuteEnum("TASK_WEEKEND", TASK_WEEKEND_VALUE);
    public static final FDCScheduleExecuteEnum TASK_WEEKBEGIN = new FDCScheduleExecuteEnum("TASK_WEEKBEGIN", TASK_WEEKBEGIN_VALUE);
    public static final FDCScheduleExecuteEnum TASK_MYEVALUATE = new FDCScheduleExecuteEnum("TASK_MYEVALUATE", TASK_MYEVALUATE_VALUE);
    public static final FDCScheduleExecuteEnum TASK_MILEPOST = new FDCScheduleExecuteEnum("TASK_MILEPOST", TASK_MILEPOST_VALUE);
    public static final FDCScheduleExecuteEnum TASK_HINGE = new FDCScheduleExecuteEnum("TASK_HINGE", TASK_HINGE_VALUE);

    /**
     * construct function
     * @param String fDCScheduleExecuteEnum
     */
    private FDCScheduleExecuteEnum(String name, String fDCScheduleExecuteEnum)
    {
        super(name, fDCScheduleExecuteEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FDCScheduleExecuteEnum getEnum(String fDCScheduleExecuteEnum)
    {
        return (FDCScheduleExecuteEnum)getEnum(FDCScheduleExecuteEnum.class, fDCScheduleExecuteEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FDCScheduleExecuteEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FDCScheduleExecuteEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FDCScheduleExecuteEnum.class);
    }
}