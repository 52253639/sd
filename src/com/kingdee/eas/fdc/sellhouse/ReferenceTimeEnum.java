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
public class ReferenceTimeEnum extends StringEnum
{
    public static final String 指定时间_VALUE = "指定时间";

    public static final ReferenceTimeEnum 指定时间 = new ReferenceTimeEnum("指定时间", 指定时间_VALUE);

    /**
     * construct function
     * @param String referenceTimeEnum
     */
    private ReferenceTimeEnum(String name, String referenceTimeEnum)
    {
        super(name, referenceTimeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ReferenceTimeEnum getEnum(String referenceTimeEnum)
    {
        return (ReferenceTimeEnum)getEnum(ReferenceTimeEnum.class, referenceTimeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ReferenceTimeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ReferenceTimeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ReferenceTimeEnum.class);
    }
}