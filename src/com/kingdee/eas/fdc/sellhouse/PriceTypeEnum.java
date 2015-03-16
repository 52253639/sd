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
public class PriceTypeEnum extends StringEnum
{
    public static final String MAKEPRICE_VALUE = "makePrice";
    public static final String ADJUSTPRICE_VALUE = "adjustPrice";

    public static final PriceTypeEnum makePrice = new PriceTypeEnum("makePrice", MAKEPRICE_VALUE);
    public static final PriceTypeEnum adjustPrice = new PriceTypeEnum("adjustPrice", ADJUSTPRICE_VALUE);

    /**
     * construct function
     * @param String priceTypeEnum
     */
    private PriceTypeEnum(String name, String priceTypeEnum)
    {
        super(name, priceTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PriceTypeEnum getEnum(String priceTypeEnum)
    {
        return (PriceTypeEnum)getEnum(PriceTypeEnum.class, priceTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PriceTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PriceTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PriceTypeEnum.class);
    }
}