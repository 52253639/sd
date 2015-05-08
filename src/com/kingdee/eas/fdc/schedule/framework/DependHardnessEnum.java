/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class DependHardnessEnum extends StringEnum
{
    public static final String STRONG_VALUE = "1Strong";
    public static final String RUBBER_VALUE = "2Rubber";

    public static final DependHardnessEnum Strong = new DependHardnessEnum("Strong", STRONG_VALUE);
    public static final DependHardnessEnum Rubber = new DependHardnessEnum("Rubber", RUBBER_VALUE);

    /**
     * construct function
     * @param String dependHardnessEnum
     */
    private DependHardnessEnum(String name, String dependHardnessEnum)
    {
        super(name, dependHardnessEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DependHardnessEnum getEnum(String dependHardnessEnum)
    {
        return (DependHardnessEnum)getEnum(DependHardnessEnum.class, dependHardnessEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DependHardnessEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DependHardnessEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DependHardnessEnum.class);
    }
}