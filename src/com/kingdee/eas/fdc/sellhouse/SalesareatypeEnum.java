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
public class SalesareatypeEnum extends StringEnum
{
    public static final String ESTIMATE_VALUE = "1";
    public static final String PRESELL_VALUE = "2";
    public static final String NOWSELL_VALUE = "3";

    public static final SalesareatypeEnum estimate = new SalesareatypeEnum("estimate", ESTIMATE_VALUE);
    public static final SalesareatypeEnum presell = new SalesareatypeEnum("presell", PRESELL_VALUE);
    public static final SalesareatypeEnum Nowsell = new SalesareatypeEnum("Nowsell", NOWSELL_VALUE);

    /**
     * construct function
     * @param String salesareatypeEnum
     */
    private SalesareatypeEnum(String name, String salesareatypeEnum)
    {
        super(name, salesareatypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SalesareatypeEnum getEnum(String salesareatypeEnum)
    {
        return (SalesareatypeEnum)getEnum(SalesareatypeEnum.class, salesareatypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SalesareatypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SalesareatypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SalesareatypeEnum.class);
    }
}