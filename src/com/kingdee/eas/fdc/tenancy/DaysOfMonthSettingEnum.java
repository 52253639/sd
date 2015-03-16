/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class DaysOfMonthSettingEnum extends StringEnum
{
    public static final String AVERDAYSOFMONTH_VALUE = "1DAYSOFMONTH";
    public static final String DAYSOFMONTHBYYEAR_VALUE = "2MONTHBYYEAR";
    public static final String FIXEDDAYS_VALUE = "3FIXEDDAYS";

    public static final DaysOfMonthSettingEnum AVERDAYSOFMONTH = new DaysOfMonthSettingEnum("AVERDAYSOFMONTH", AVERDAYSOFMONTH_VALUE);
    public static final DaysOfMonthSettingEnum DAYSOFMONTHBYYEAR = new DaysOfMonthSettingEnum("DAYSOFMONTHBYYEAR", DAYSOFMONTHBYYEAR_VALUE);
    public static final DaysOfMonthSettingEnum FIXEDDAYS = new DaysOfMonthSettingEnum("FIXEDDAYS", FIXEDDAYS_VALUE);

    /**
     * construct function
     * @param String daysOfMonthSettingEnum
     */
    private DaysOfMonthSettingEnum(String name, String daysOfMonthSettingEnum)
    {
        super(name, daysOfMonthSettingEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DaysOfMonthSettingEnum getEnum(String daysOfMonthSettingEnum)
    {
        return (DaysOfMonthSettingEnum)getEnum(DaysOfMonthSettingEnum.class, daysOfMonthSettingEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DaysOfMonthSettingEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DaysOfMonthSettingEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DaysOfMonthSettingEnum.class);
    }
}