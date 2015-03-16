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
public class AmountUnitEnum extends IntEnum
{
    public static final int YUAN_VALUE = 1;
    public static final int QIAN_YUAN_VALUE = 1000;
    public static final int WAN_YUAN_VALUE = 10000;
    public static final int BAI_WAN_YUAN_VALUE = 1000000;
    public static final int QIAN_WAN_YUAN_VALUE = 10000000;
    public static final int YIYI_YUAN_VALUE = 100000000;

    public static final AmountUnitEnum yuan = new AmountUnitEnum("yuan", YUAN_VALUE);
    public static final AmountUnitEnum qian_yuan = new AmountUnitEnum("qian_yuan", QIAN_YUAN_VALUE);
    public static final AmountUnitEnum wan_yuan = new AmountUnitEnum("wan_yuan", WAN_YUAN_VALUE);
    public static final AmountUnitEnum bai_wan_yuan = new AmountUnitEnum("bai_wan_yuan", BAI_WAN_YUAN_VALUE);
    public static final AmountUnitEnum qian_wan_yuan = new AmountUnitEnum("qian_wan_yuan", QIAN_WAN_YUAN_VALUE);
    public static final AmountUnitEnum yiyi_yuan = new AmountUnitEnum("yiyi_yuan", YIYI_YUAN_VALUE);

    /**
     * construct function
     * @param integer amountUnitEnum
     */
    private AmountUnitEnum(String name, int amountUnitEnum)
    {
        super(name, amountUnitEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AmountUnitEnum getEnum(String amountUnitEnum)
    {
        return (AmountUnitEnum)getEnum(AmountUnitEnum.class, amountUnitEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static AmountUnitEnum getEnum(int amountUnitEnum)
    {
        return (AmountUnitEnum)getEnum(AmountUnitEnum.class, amountUnitEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AmountUnitEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AmountUnitEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AmountUnitEnum.class);
    }
}