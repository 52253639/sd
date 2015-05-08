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
public class RESchTaskEffectDegreeEnum extends StringEnum
{
    public static final String ALLTASK_VALUE = "1AllTask";//alias=所有任务
    public static final String MILESTONETASK_VALUE = "2MilestoneTask";//alias=里程碑任务
    public static final String KEYTASK_VALUE = "3KeyTask";//alias=关键任务
    public static final String NORMALTASK_VALUE = "4NormalTask";//alias=一般任务
    public static final String DAILYTASK_VALUE = "5DailyTask";//alias=日常工作

    public static final RESchTaskEffectDegreeEnum AllTask = new RESchTaskEffectDegreeEnum("AllTask", ALLTASK_VALUE);
    public static final RESchTaskEffectDegreeEnum MilestoneTask = new RESchTaskEffectDegreeEnum("MilestoneTask", MILESTONETASK_VALUE);
    public static final RESchTaskEffectDegreeEnum KeyTask = new RESchTaskEffectDegreeEnum("KeyTask", KEYTASK_VALUE);
    public static final RESchTaskEffectDegreeEnum NormalTask = new RESchTaskEffectDegreeEnum("NormalTask", NORMALTASK_VALUE);
    public static final RESchTaskEffectDegreeEnum DailyTask = new RESchTaskEffectDegreeEnum("DailyTask", DAILYTASK_VALUE);

    /**
     * construct function
     * @param String rESchTaskEffectDegreeEnum
     */
    private RESchTaskEffectDegreeEnum(String name, String rESchTaskEffectDegreeEnum)
    {
        super(name, rESchTaskEffectDegreeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RESchTaskEffectDegreeEnum getEnum(String rESchTaskEffectDegreeEnum)
    {
        return (RESchTaskEffectDegreeEnum)getEnum(RESchTaskEffectDegreeEnum.class, rESchTaskEffectDegreeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RESchTaskEffectDegreeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RESchTaskEffectDegreeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RESchTaskEffectDegreeEnum.class);
    }
}