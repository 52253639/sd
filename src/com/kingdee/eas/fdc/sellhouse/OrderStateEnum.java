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
public class OrderStateEnum extends StringEnum
{
    public static final String ALL_VALUE = "all";
    public static final String YES_VALUE = "yes";
    public static final String NO_VALUE = "no";

    public static final OrderStateEnum all = new OrderStateEnum("all", ALL_VALUE);
    public static final OrderStateEnum yes = new OrderStateEnum("yes", YES_VALUE);
    public static final OrderStateEnum no = new OrderStateEnum("no", NO_VALUE);

    /**
     * construct function
     * @param String orderStateEnum
     */
    private OrderStateEnum(String name, String orderStateEnum)
    {
        super(name, orderStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static OrderStateEnum getEnum(String orderStateEnum)
    {
        return (OrderStateEnum)getEnum(OrderStateEnum.class, orderStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(OrderStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(OrderStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(OrderStateEnum.class);
    }
}