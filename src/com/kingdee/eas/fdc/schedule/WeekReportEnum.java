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
public class WeekReportEnum extends StringEnum
{
    public static final String YES_VALUE = "yes";
    public static final String NO_VALUE = "no";

    public static final WeekReportEnum YES = new WeekReportEnum("YES", YES_VALUE);
    public static final WeekReportEnum NO = new WeekReportEnum("NO", NO_VALUE);

    /**
     * construct function
     * @param String weekReportEnum
     */
    private WeekReportEnum(String name, String weekReportEnum)
    {
        super(name, weekReportEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static WeekReportEnum getEnum(String weekReportEnum)
    {
        return (WeekReportEnum)getEnum(WeekReportEnum.class, weekReportEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(WeekReportEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(WeekReportEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(WeekReportEnum.class);
    }
}