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
public class OrderSourceClassEnum extends StringEnum
{
    public static final String SELL_VALUE = "1Sell";
    public static final String AUCTION_VALUE = "2Auction";
    public static final String TENANCY_VALUE = "3Tenancy";

    public static final OrderSourceClassEnum Sell = new OrderSourceClassEnum("Sell", SELL_VALUE);
    public static final OrderSourceClassEnum Auction = new OrderSourceClassEnum("Auction", AUCTION_VALUE);
    public static final OrderSourceClassEnum Tenancy = new OrderSourceClassEnum("Tenancy", TENANCY_VALUE);

    /**
     * construct function
     * @param String orderSourceClassEnum
     */
    private OrderSourceClassEnum(String name, String orderSourceClassEnum)
    {
        super(name, orderSourceClassEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static OrderSourceClassEnum getEnum(String orderSourceClassEnum)
    {
        return (OrderSourceClassEnum)getEnum(OrderSourceClassEnum.class, orderSourceClassEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(OrderSourceClassEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(OrderSourceClassEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(OrderSourceClassEnum.class);
    }
}