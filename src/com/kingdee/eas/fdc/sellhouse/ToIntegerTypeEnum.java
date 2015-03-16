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
public class ToIntegerTypeEnum extends StringEnum
{
    public static final String ABNEGATEZERO_VALUE = "AbnegateZero";
    public static final String CARRY_VALUE = "Carry";
    public static final String ROUND_VALUE = "Round";

    public static final ToIntegerTypeEnum AbnegateZero = new ToIntegerTypeEnum("AbnegateZero", ABNEGATEZERO_VALUE);
    public static final ToIntegerTypeEnum Carry = new ToIntegerTypeEnum("Carry", CARRY_VALUE);
    public static final ToIntegerTypeEnum Round = new ToIntegerTypeEnum("Round", ROUND_VALUE);

    /**
     * construct function
     * @param String toIntegerTypeEnum
     */
    private ToIntegerTypeEnum(String name, String toIntegerTypeEnum)
    {
        super(name, toIntegerTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ToIntegerTypeEnum getEnum(String toIntegerTypeEnum)
    {
        return (ToIntegerTypeEnum)getEnum(ToIntegerTypeEnum.class, toIntegerTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ToIntegerTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ToIntegerTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ToIntegerTypeEnum.class);
    }
}