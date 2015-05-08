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
public class TaskExecuteStatusItemEnum extends StringEnum
{
    public static final String NORMALUNSTART_VALUE = "11normalunstart";
    public static final String NORMALFINISHED_VALUE = "12normalfinished";
    public static final String DELAYFINISHED_VALUE = "13delayfinished";
    public static final String NORMALEXECUTING_VALUE = "21normalexecuting";
    public static final String DELAYUNFINISHED_VALUE = "31delayunfinished";
    public static final String DELAYUNSTART_VALUE = "32delayunstart";

    public static final TaskExecuteStatusItemEnum NORMALUNSTART = new TaskExecuteStatusItemEnum("NORMALUNSTART", NORMALUNSTART_VALUE);
    public static final TaskExecuteStatusItemEnum NORMALFINISHED = new TaskExecuteStatusItemEnum("NORMALFINISHED", NORMALFINISHED_VALUE);
    public static final TaskExecuteStatusItemEnum DELAYFINISHED = new TaskExecuteStatusItemEnum("DELAYFINISHED", DELAYFINISHED_VALUE);
    public static final TaskExecuteStatusItemEnum NORMALEXECUTING = new TaskExecuteStatusItemEnum("NORMALEXECUTING", NORMALEXECUTING_VALUE);
    public static final TaskExecuteStatusItemEnum DELAYUNFINISHED = new TaskExecuteStatusItemEnum("DELAYUNFINISHED", DELAYUNFINISHED_VALUE);
    public static final TaskExecuteStatusItemEnum DELAYUNSTART = new TaskExecuteStatusItemEnum("DELAYUNSTART", DELAYUNSTART_VALUE);

    /**
     * construct function
     * @param String taskExecuteStatusItemEnum
     */
    private TaskExecuteStatusItemEnum(String name, String taskExecuteStatusItemEnum)
    {
        super(name, taskExecuteStatusItemEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TaskExecuteStatusItemEnum getEnum(String taskExecuteStatusItemEnum)
    {
        return (TaskExecuteStatusItemEnum)getEnum(TaskExecuteStatusItemEnum.class, taskExecuteStatusItemEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TaskExecuteStatusItemEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TaskExecuteStatusItemEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TaskExecuteStatusItemEnum.class);
    }
}