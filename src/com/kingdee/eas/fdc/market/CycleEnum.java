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
public class CycleEnum extends StringEnum
{
    public static final String YEAR_VALUE = "1";
    public static final String MONTH_VALUE = "2";
    public static final String OTHER_VALUE = "3";

    public static final CycleEnum Year = new CycleEnum("Year", YEAR_VALUE);
    public static final CycleEnum Month = new CycleEnum("Month", MONTH_VALUE);
    public static final CycleEnum Other = new CycleEnum("Other", OTHER_VALUE);

    /**
     * construct function
     * @param String cycleEnum
     */
    private CycleEnum(String name, String cycleEnum)
    {
        super(name, cycleEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CycleEnum getEnum(String cycleEnum)
    {
        return (CycleEnum)getEnum(CycleEnum.class, cycleEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CycleEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CycleEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CycleEnum.class);
    }
}