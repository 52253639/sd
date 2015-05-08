/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class RESchTaskTypeEnum extends StringEnum
{
    public static final String COMMONLY_VALUE = "commonly";
    public static final String KEY_VALUE = "key";
    public static final String MILESTONE_VALUE = "milestone";
    public static final String NORMAL_VALUE = "normal";

    public static final RESchTaskTypeEnum COMMONLY = new RESchTaskTypeEnum("COMMONLY", COMMONLY_VALUE);
    public static final RESchTaskTypeEnum KEY = new RESchTaskTypeEnum("KEY", KEY_VALUE);
    public static final RESchTaskTypeEnum MILESTONE = new RESchTaskTypeEnum("MILESTONE", MILESTONE_VALUE);
    public static final RESchTaskTypeEnum NORMAL = new RESchTaskTypeEnum("NORMAL", NORMAL_VALUE);

    /**
     * construct function
     * @param String rESchTaskTypeEnum
     */
    private RESchTaskTypeEnum(String name, String rESchTaskTypeEnum)
    {
        super(name, rESchTaskTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RESchTaskTypeEnum getEnum(String rESchTaskTypeEnum)
    {
        return (RESchTaskTypeEnum)getEnum(RESchTaskTypeEnum.class, rESchTaskTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RESchTaskTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RESchTaskTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RESchTaskTypeEnum.class);
    }
}