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
public class VersionTypeEnum extends StringEnum
{
    public static final String CHECK_VALUE = "30";
    public static final String ADJUST_VALUE = "10";
    public static final String YEAR_VALUE = "20";

    public static final VersionTypeEnum check = new VersionTypeEnum("check", CHECK_VALUE);
    public static final VersionTypeEnum adjust = new VersionTypeEnum("adjust", ADJUST_VALUE);
    public static final VersionTypeEnum year = new VersionTypeEnum("year", YEAR_VALUE);

    /**
     * construct function
     * @param String versionTypeEnum
     */
    private VersionTypeEnum(String name, String versionTypeEnum)
    {
        super(name, versionTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static VersionTypeEnum getEnum(String versionTypeEnum)
    {
        return (VersionTypeEnum)getEnum(VersionTypeEnum.class, versionTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(VersionTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(VersionTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(VersionTypeEnum.class);
    }
}