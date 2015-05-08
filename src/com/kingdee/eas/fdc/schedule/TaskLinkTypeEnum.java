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
public class TaskLinkTypeEnum extends StringEnum
{
    public static final String FINISH_START_VALUE = "1finish_start";//alias=完成-开始
    public static final String START_START_VALUE = "2start_start";//alias=开始-开始
    public static final String FINISH_FINISH_VALUE = "3finish_finish";//alias=完成-完成

    public static final TaskLinkTypeEnum FINISH_START = new TaskLinkTypeEnum("FINISH_START", FINISH_START_VALUE);
    public static final TaskLinkTypeEnum START_START = new TaskLinkTypeEnum("START_START", START_START_VALUE);
    public static final TaskLinkTypeEnum FINISH_FINISH = new TaskLinkTypeEnum("FINISH_FINISH", FINISH_FINISH_VALUE);

    /**
     * construct function
     * @param String taskLinkTypeEnum
     */
    private TaskLinkTypeEnum(String name, String taskLinkTypeEnum)
    {
        super(name, taskLinkTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TaskLinkTypeEnum getEnum(String taskLinkTypeEnum)
    {
        return (TaskLinkTypeEnum)getEnum(TaskLinkTypeEnum.class, taskLinkTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TaskLinkTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TaskLinkTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TaskLinkTypeEnum.class);
    }
}