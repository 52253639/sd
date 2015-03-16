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
public class BooleanEnum extends StringEnum
{
    public static final String YES_VALUE = "1YES";
    public static final String NO_VALUE = "2NO";

    public static final BooleanEnum YES = new BooleanEnum("YES", YES_VALUE);
    public static final BooleanEnum NO = new BooleanEnum("NO", NO_VALUE);

    /**
     * construct function
     * @param String booleanEnum
     */
    private BooleanEnum(String name, String booleanEnum)
    {
        super(name, booleanEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BooleanEnum getEnum(String booleanEnum)
    {
        return (BooleanEnum)getEnum(BooleanEnum.class, booleanEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BooleanEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BooleanEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BooleanEnum.class);
    }
}