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
public class PercentageEnum extends StringEnum
{
    public static final String TWO_VALUE = "2";
    public static final String THREE_VALUE = "3";
    public static final String FOUR_VALUE = "4";
    public static final String FIVE_VALUE = "5";
    public static final String SIX_VALUE = "6";
    public static final String SEVEN_VALUE = "7";
    public static final String EIGHT_VALUE = "8";
    public static final String NINE_VALUE = "9";

    public static final PercentageEnum two = new PercentageEnum("two", TWO_VALUE);
    public static final PercentageEnum three = new PercentageEnum("three", THREE_VALUE);
    public static final PercentageEnum four = new PercentageEnum("four", FOUR_VALUE);
    public static final PercentageEnum five = new PercentageEnum("five", FIVE_VALUE);
    public static final PercentageEnum six = new PercentageEnum("six", SIX_VALUE);
    public static final PercentageEnum seven = new PercentageEnum("seven", SEVEN_VALUE);
    public static final PercentageEnum eight = new PercentageEnum("eight", EIGHT_VALUE);
    public static final PercentageEnum nine = new PercentageEnum("nine", NINE_VALUE);

    /**
     * construct function
     * @param String percentageEnum
     */
    private PercentageEnum(String name, String percentageEnum)
    {
        super(name, percentageEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PercentageEnum getEnum(String percentageEnum)
    {
        return (PercentageEnum)getEnum(PercentageEnum.class, percentageEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PercentageEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PercentageEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PercentageEnum.class);
    }
}