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
public class DecorateEnum extends StringEnum
{
    public static final String BLANK_VALUE = "BLANK";
    public static final String HARDCOVER_VALUE = "HARDCOVER";

    public static final DecorateEnum BLANK = new DecorateEnum("BLANK", BLANK_VALUE);
    public static final DecorateEnum HARDCOVER = new DecorateEnum("HARDCOVER", HARDCOVER_VALUE);

    /**
     * construct function
     * @param String decorateEnum
     */
    private DecorateEnum(String name, String decorateEnum)
    {
        super(name, decorateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DecorateEnum getEnum(String decorateEnum)
    {
        return (DecorateEnum)getEnum(DecorateEnum.class, decorateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DecorateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DecorateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DecorateEnum.class);
    }
}