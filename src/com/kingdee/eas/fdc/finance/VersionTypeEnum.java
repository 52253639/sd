/**
 * output package name
 */
package com.kingdee.eas.fdc.finance;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class VersionTypeEnum extends StringEnum
{
    public static final String REPORT_VALUE = "REPORT";
    public static final String EXECUTE_VALUE = "EXECUTE";

    public static final VersionTypeEnum REPORT = new VersionTypeEnum("REPORT", REPORT_VALUE);
    public static final VersionTypeEnum EXECUTE = new VersionTypeEnum("EXECUTE", EXECUTE_VALUE);

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