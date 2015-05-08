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
public class TaskExecuteStatusEnum extends StringEnum
{
    public static final String NONE_VALUE = "0none";
    public static final String NORMAL_VALUE = "1normal";
    public static final String EXECUTING_VALUE = "2executing";
    public static final String DELAY_VALUE = "3delay";

    public static final TaskExecuteStatusEnum NONE = new TaskExecuteStatusEnum("NONE", NONE_VALUE);
    public static final TaskExecuteStatusEnum NORMAL = new TaskExecuteStatusEnum("NORMAL", NORMAL_VALUE);
    public static final TaskExecuteStatusEnum EXECUTING = new TaskExecuteStatusEnum("EXECUTING", EXECUTING_VALUE);
    public static final TaskExecuteStatusEnum DELAY = new TaskExecuteStatusEnum("DELAY", DELAY_VALUE);

    /**
     * construct function
     * @param String taskExecuteStatusEnum
     */
    private TaskExecuteStatusEnum(String name, String taskExecuteStatusEnum)
    {
        super(name, taskExecuteStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TaskExecuteStatusEnum getEnum(String taskExecuteStatusEnum)
    {
        return (TaskExecuteStatusEnum)getEnum(TaskExecuteStatusEnum.class, taskExecuteStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TaskExecuteStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TaskExecuteStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TaskExecuteStatusEnum.class);
    }
}