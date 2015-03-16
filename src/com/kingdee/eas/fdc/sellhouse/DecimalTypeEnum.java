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
public class DecimalTypeEnum extends StringEnum
{
    public static final String ROUND_HALF_UP_VALUE = "4";
    public static final String ROUND_UP_VALUE = "0";
    public static final String ROUND_DOWN_VALUE = "1";

    public static final DecimalTypeEnum ROUND_HALF_UP = new DecimalTypeEnum("ROUND_HALF_UP", ROUND_HALF_UP_VALUE);
    public static final DecimalTypeEnum ROUND_UP = new DecimalTypeEnum("ROUND_UP", ROUND_UP_VALUE);
    public static final DecimalTypeEnum ROUND_DOWN = new DecimalTypeEnum("ROUND_DOWN", ROUND_DOWN_VALUE);

    /**
     * construct function
     * @param String decimalTypeEnum
     */
    private DecimalTypeEnum(String name, String decimalTypeEnum)
    {
        super(name, decimalTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DecimalTypeEnum getEnum(String decimalTypeEnum)
    {
        return (DecimalTypeEnum)getEnum(DecimalTypeEnum.class, decimalTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DecimalTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DecimalTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DecimalTypeEnum.class);
    }
}