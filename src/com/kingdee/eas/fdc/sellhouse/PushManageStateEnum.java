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
public class PushManageStateEnum extends StringEnum
{
    public static final String NOPUSH_VALUE = "nopush";
    public static final String PUSH_VALUE = "push";
    public static final String PULL_VALUE = "pull";

    public static final PushManageStateEnum NOPUSH = new PushManageStateEnum("NOPUSH", NOPUSH_VALUE);
    public static final PushManageStateEnum PUSH = new PushManageStateEnum("PUSH", PUSH_VALUE);
    public static final PushManageStateEnum PULL = new PushManageStateEnum("PULL", PULL_VALUE);

    /**
     * construct function
     * @param String pushManageStateEnum
     */
    private PushManageStateEnum(String name, String pushManageStateEnum)
    {
        super(name, pushManageStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PushManageStateEnum getEnum(String pushManageStateEnum)
    {
        return (PushManageStateEnum)getEnum(PushManageStateEnum.class, pushManageStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PushManageStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PushManageStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PushManageStateEnum.class);
    }
}