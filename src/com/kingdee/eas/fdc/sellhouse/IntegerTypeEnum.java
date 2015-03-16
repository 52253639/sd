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
public class IntegerTypeEnum extends StringEnum
{
    public static final String ROUND_VALUE = "ROUND";
    public static final String LASTINTERCEPT_VALUE = "LASTINTERCEPT";
    public static final String INTEGER_VALUE = "INTEGER";

    public static final IntegerTypeEnum ROUND = new IntegerTypeEnum("ROUND", ROUND_VALUE);
    public static final IntegerTypeEnum LASTINTERCEPT = new IntegerTypeEnum("LASTINTERCEPT", LASTINTERCEPT_VALUE);
    public static final IntegerTypeEnum INTEGER = new IntegerTypeEnum("INTEGER", INTEGER_VALUE);

    /**
     * construct function
     * @param String integerTypeEnum
     */
    private IntegerTypeEnum(String name, String integerTypeEnum)
    {
        super(name, integerTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static IntegerTypeEnum getEnum(String integerTypeEnum)
    {
        return (IntegerTypeEnum)getEnum(IntegerTypeEnum.class, integerTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(IntegerTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(IntegerTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(IntegerTypeEnum.class);
    }
}