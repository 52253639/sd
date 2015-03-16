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
public class SellTypeEnum extends StringEnum
{
    public static final String PRESELL_VALUE = "PreSell";
    public static final String LOCALESELL_VALUE = "LocaleSell";
    public static final String PLANNINGSELL_VALUE = "PlanningSell";

    public static final SellTypeEnum PreSell = new SellTypeEnum("PreSell", PRESELL_VALUE);
    public static final SellTypeEnum LocaleSell = new SellTypeEnum("LocaleSell", LOCALESELL_VALUE);
    public static final SellTypeEnum PlanningSell = new SellTypeEnum("PlanningSell", PLANNINGSELL_VALUE);

    /**
     * construct function
     * @param String sellTypeEnum
     */
    private SellTypeEnum(String name, String sellTypeEnum)
    {
        super(name, sellTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SellTypeEnum getEnum(String sellTypeEnum)
    {
        return (SellTypeEnum)getEnum(SellTypeEnum.class, sellTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SellTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SellTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SellTypeEnum.class);
    }
}