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
public class TaskPropertyTypeEnum extends StringEnum
{
    public static final String DEFAULT_VALUE = "default";
    public static final String CUSTOM_VALUE = "custom";

    public static final TaskPropertyTypeEnum DEFAULT = new TaskPropertyTypeEnum("DEFAULT", DEFAULT_VALUE);
    public static final TaskPropertyTypeEnum CUSTOM = new TaskPropertyTypeEnum("CUSTOM", CUSTOM_VALUE);

    /**
     * construct function
     * @param String taskPropertyTypeEnum
     */
    private TaskPropertyTypeEnum(String name, String taskPropertyTypeEnum)
    {
        super(name, taskPropertyTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TaskPropertyTypeEnum getEnum(String taskPropertyTypeEnum)
    {
        return (TaskPropertyTypeEnum)getEnum(TaskPropertyTypeEnum.class, taskPropertyTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TaskPropertyTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TaskPropertyTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TaskPropertyTypeEnum.class);
    }
}