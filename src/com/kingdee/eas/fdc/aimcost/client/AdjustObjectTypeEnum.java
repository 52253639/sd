/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class AdjustObjectTypeEnum extends IntEnum
{
    public static final int HASAIM_VALUE = 10;
    public static final int NOAIM_VALUE = 20;
    public static final int ADJUST_VALUE = 30;

    public static final AdjustObjectTypeEnum HASAIM = new AdjustObjectTypeEnum("HASAIM", HASAIM_VALUE);
    public static final AdjustObjectTypeEnum NOAIM = new AdjustObjectTypeEnum("NOAIM", NOAIM_VALUE);
    public static final AdjustObjectTypeEnum ADJUST = new AdjustObjectTypeEnum("ADJUST", ADJUST_VALUE);

    /**
     * construct function
     * @param integer adjustObjectTypeEnum
     */
    private AdjustObjectTypeEnum(String name, int adjustObjectTypeEnum)
    {
        super(name, adjustObjectTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AdjustObjectTypeEnum getEnum(String adjustObjectTypeEnum)
    {
        return (AdjustObjectTypeEnum)getEnum(AdjustObjectTypeEnum.class, adjustObjectTypeEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static AdjustObjectTypeEnum getEnum(int adjustObjectTypeEnum)
    {
        return (AdjustObjectTypeEnum)getEnum(AdjustObjectTypeEnum.class, adjustObjectTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AdjustObjectTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AdjustObjectTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AdjustObjectTypeEnum.class);
    }
}