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
public class RentStartTypeEnum extends StringEnum
{
    public static final String SETTLEDSTARTDATE_VALUE = "SettledStartDate";
    public static final String DYNAMICSTARTDATE_VALUE = "DynamicStartDate";

    public static final RentStartTypeEnum SettledStartDate = new RentStartTypeEnum("SettledStartDate", SETTLEDSTARTDATE_VALUE);
    public static final RentStartTypeEnum DynamicStartDate = new RentStartTypeEnum("DynamicStartDate", DYNAMICSTARTDATE_VALUE);

    /**
     * construct function
     * @param String rentStartTypeEnum
     */
    private RentStartTypeEnum(String name, String rentStartTypeEnum)
    {
        super(name, rentStartTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RentStartTypeEnum getEnum(String rentStartTypeEnum)
    {
        return (RentStartTypeEnum)getEnum(RentStartTypeEnum.class, rentStartTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RentStartTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RentStartTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RentStartTypeEnum.class);
    }
}