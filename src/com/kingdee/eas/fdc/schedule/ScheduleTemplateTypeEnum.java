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
public class ScheduleTemplateTypeEnum extends StringEnum
{
    public static final String MAINPLANTEMPLATE_VALUE = "1";
    public static final String OTHERPLANTEMPLATE_VALUE = "2";

    public static final ScheduleTemplateTypeEnum MainPlanTemplate = new ScheduleTemplateTypeEnum("MainPlanTemplate", MAINPLANTEMPLATE_VALUE);
    public static final ScheduleTemplateTypeEnum OtherPlanTemplate = new ScheduleTemplateTypeEnum("OtherPlanTemplate", OTHERPLANTEMPLATE_VALUE);

    /**
     * construct function
     * @param String scheduleTemplateTypeEnum
     */
    private ScheduleTemplateTypeEnum(String name, String scheduleTemplateTypeEnum)
    {
        super(name, scheduleTemplateTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ScheduleTemplateTypeEnum getEnum(String scheduleTemplateTypeEnum)
    {
        return (ScheduleTemplateTypeEnum)getEnum(ScheduleTemplateTypeEnum.class, scheduleTemplateTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ScheduleTemplateTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ScheduleTemplateTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ScheduleTemplateTypeEnum.class);
    }
}