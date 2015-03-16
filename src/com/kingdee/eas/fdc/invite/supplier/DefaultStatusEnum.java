/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class DefaultStatusEnum extends IntEnum
{
    public static final int YES_VALUE = 1;
    public static final int NO_VALUE = 0;

    public static final DefaultStatusEnum YES = new DefaultStatusEnum("YES", YES_VALUE);
    public static final DefaultStatusEnum NO = new DefaultStatusEnum("NO", NO_VALUE);

    /**
     * construct function
     * @param integer defaultStatusEnum
     */
    private DefaultStatusEnum(String name, int defaultStatusEnum)
    {
        super(name, defaultStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DefaultStatusEnum getEnum(String defaultStatusEnum)
    {
        return (DefaultStatusEnum)getEnum(DefaultStatusEnum.class, defaultStatusEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static DefaultStatusEnum getEnum(int defaultStatusEnum)
    {
        return (DefaultStatusEnum)getEnum(DefaultStatusEnum.class, defaultStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DefaultStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DefaultStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DefaultStatusEnum.class);
    }
}