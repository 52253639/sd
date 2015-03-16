/**
 * output package name
 */
package com.kingdee.eas.fdc.market;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ThemeEnum extends StringEnum
{
    public static final String UNSTARTED_VALUE = "1";
    public static final String UNDERWAY_VALUE = "2";
    public static final String FINISH_VALUE = "3";
    public static final String CANCELED_VALUE = "4";

    public static final ThemeEnum UnStarted = new ThemeEnum("UnStarted", UNSTARTED_VALUE);
    public static final ThemeEnum Underway = new ThemeEnum("Underway", UNDERWAY_VALUE);
    public static final ThemeEnum Finish = new ThemeEnum("Finish", FINISH_VALUE);
    public static final ThemeEnum Canceled = new ThemeEnum("Canceled", CANCELED_VALUE);

    /**
     * construct function
     * @param String themeEnum
     */
    private ThemeEnum(String name, String themeEnum)
    {
        super(name, themeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ThemeEnum getEnum(String themeEnum)
    {
        return (ThemeEnum)getEnum(ThemeEnum.class, themeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ThemeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ThemeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ThemeEnum.class);
    }
}