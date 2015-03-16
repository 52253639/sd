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
public class PriceBillTypeEnum extends StringEnum
{
    public static final String SETPRICE_VALUE = "SetPrice";
    public static final String ADJUSTPRICE_VALUE = "AdjustPrice";

    public static final PriceBillTypeEnum SetPrice = new PriceBillTypeEnum("SetPrice", SETPRICE_VALUE);
    public static final PriceBillTypeEnum AdjustPrice = new PriceBillTypeEnum("AdjustPrice", ADJUSTPRICE_VALUE);

    /**
     * construct function
     * @param String priceBillTypeEnum
     */
    private PriceBillTypeEnum(String name, String priceBillTypeEnum)
    {
        super(name, priceBillTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PriceBillTypeEnum getEnum(String priceBillTypeEnum)
    {
        return (PriceBillTypeEnum)getEnum(PriceBillTypeEnum.class, priceBillTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PriceBillTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PriceBillTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PriceBillTypeEnum.class);
    }
}