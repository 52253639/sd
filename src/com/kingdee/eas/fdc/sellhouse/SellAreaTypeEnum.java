/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class SellAreaTypeEnum extends StringEnum
{
    public static final String PRESELL_VALUE = "presell";
    public static final String PLANNING_VALUE = "planning";
    public static final String ACTUAL_VALUE = "actual";

    public static final SellAreaTypeEnum PRESELL = new SellAreaTypeEnum("PRESELL", PRESELL_VALUE);
    public static final SellAreaTypeEnum PLANNING = new SellAreaTypeEnum("PLANNING", PLANNING_VALUE);
    public static final SellAreaTypeEnum ACTUAL = new SellAreaTypeEnum("ACTUAL", ACTUAL_VALUE);

    /**
     * construct function
     * @param String sellAreaTypeEnum
     */
    private SellAreaTypeEnum(String name, String sellAreaTypeEnum)
    {
        super(name, sellAreaTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SellAreaTypeEnum getEnum(String sellAreaTypeEnum)
    {
        return (SellAreaTypeEnum)getEnum(SellAreaTypeEnum.class, sellAreaTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SellAreaTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SellAreaTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SellAreaTypeEnum.class);
    }
}