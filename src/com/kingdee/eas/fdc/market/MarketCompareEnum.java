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
public class MarketCompareEnum extends StringEnum
{
    public static final String GREATETHAN_VALUE = ">";
    public static final String GREATEEQUALS_VALUE = ">=";
    public static final String EQUALS_VALUE = "=";
    public static final String LESSTHAN_VALUE = "<";
    public static final String LESSEQUALS_VALUE = "<=";

    public static final MarketCompareEnum GREATETHAN = new MarketCompareEnum("GREATETHAN", GREATETHAN_VALUE);
    public static final MarketCompareEnum GREATEEQUALS = new MarketCompareEnum("GREATEEQUALS", GREATEEQUALS_VALUE);
    public static final MarketCompareEnum EQUALS = new MarketCompareEnum("EQUALS", EQUALS_VALUE);
    public static final MarketCompareEnum LESSTHAN = new MarketCompareEnum("LESSTHAN", LESSTHAN_VALUE);
    public static final MarketCompareEnum LESSEQUALS = new MarketCompareEnum("LESSEQUALS", LESSEQUALS_VALUE);

    /**
     * construct function
     * @param String marketCompareEnum
     */
    private MarketCompareEnum(String name, String marketCompareEnum)
    {
        super(name, marketCompareEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MarketCompareEnum getEnum(String marketCompareEnum)
    {
        return (MarketCompareEnum)getEnum(MarketCompareEnum.class, marketCompareEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MarketCompareEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MarketCompareEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MarketCompareEnum.class);
    }
}