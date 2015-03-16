/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class RangeEnum extends StringEnum
{
    public static final String INNER_VALUE = "INNER";
    public static final String OUTTER_VALUE = "OUTTER";

    public static final RangeEnum INNER = new RangeEnum("INNER", INNER_VALUE);
    public static final RangeEnum OUTTER = new RangeEnum("OUTTER", OUTTER_VALUE);

    /**
     * construct function
     * @param String rangeEnum
     */
    private RangeEnum(String name, String rangeEnum)
    {
        super(name, rangeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RangeEnum getEnum(String rangeEnum)
    {
        return (RangeEnum)getEnum(RangeEnum.class, rangeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RangeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RangeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RangeEnum.class);
    }
}