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
public class BelongSystemEnum extends StringEnum
{
    public static final String SELLHOUSE_VALUE = "0";
    public static final String LEASING_VALUE = "1";
    public static final String PROPERTY_VALUE = "2";

    public static final BelongSystemEnum SELLHOUSE = new BelongSystemEnum("SELLHOUSE", SELLHOUSE_VALUE);
    public static final BelongSystemEnum LEASING = new BelongSystemEnum("LEASING", LEASING_VALUE);
    public static final BelongSystemEnum PROPERTY = new BelongSystemEnum("PROPERTY", PROPERTY_VALUE);

    /**
     * construct function
     * @param String belongSystemEnum
     */
    private BelongSystemEnum(String name, String belongSystemEnum)
    {
        super(name, belongSystemEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BelongSystemEnum getEnum(String belongSystemEnum)
    {
        return (BelongSystemEnum)getEnum(BelongSystemEnum.class, belongSystemEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BelongSystemEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BelongSystemEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BelongSystemEnum.class);
    }
}