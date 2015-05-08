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
public class TaskEvaluationTypeEnum extends StringEnum
{
    public static final String SCHEDULE_VALUE = "1SCHEDULE";
    public static final String QUALITY_VALUE = "2QUALITY";

    public static final TaskEvaluationTypeEnum SCHEDULE = new TaskEvaluationTypeEnum("SCHEDULE", SCHEDULE_VALUE);
    public static final TaskEvaluationTypeEnum QUALITY = new TaskEvaluationTypeEnum("QUALITY", QUALITY_VALUE);

    /**
     * construct function
     * @param String taskEvaluationTypeEnum
     */
    private TaskEvaluationTypeEnum(String name, String taskEvaluationTypeEnum)
    {
        super(name, taskEvaluationTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TaskEvaluationTypeEnum getEnum(String taskEvaluationTypeEnum)
    {
        return (TaskEvaluationTypeEnum)getEnum(TaskEvaluationTypeEnum.class, taskEvaluationTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TaskEvaluationTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TaskEvaluationTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TaskEvaluationTypeEnum.class);
    }
}