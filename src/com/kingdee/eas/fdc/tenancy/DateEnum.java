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
public class DateEnum extends StringEnum
{
    public static final String YEAR_VALUE = "10";
    public static final String MONTH_VALUE = "20";
    public static final String DAY_VALUE = "30";

    public static final DateEnum YEAR = new DateEnum("YEAR", YEAR_VALUE);
    public static final DateEnum MONTH = new DateEnum("MONTH", MONTH_VALUE);
    public static final DateEnum DAY = new DateEnum("DAY", DAY_VALUE);

    /**
     * construct function
     * @param String dateEnum
     */
    private DateEnum(String name, String dateEnum)
    {
        super(name, dateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DateEnum getEnum(String dateEnum)
    {
        return (DateEnum)getEnum(DateEnum.class, dateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DateEnum.class);
    }
}