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
public class PriceAccountTypeEnum extends StringEnum
{
    public static final String PRICESETSTAND_VALUE = "PriceSetStand";
    public static final String STANDSETPRICE_VALUE = "StandSetPrice";

    public static final PriceAccountTypeEnum PriceSetStand = new PriceAccountTypeEnum("PriceSetStand", PRICESETSTAND_VALUE);
    public static final PriceAccountTypeEnum StandSetPrice = new PriceAccountTypeEnum("StandSetPrice", STANDSETPRICE_VALUE);

    /**
     * construct function
     * @param String priceAccountTypeEnum
     */
    private PriceAccountTypeEnum(String name, String priceAccountTypeEnum)
    {
        super(name, priceAccountTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PriceAccountTypeEnum getEnum(String priceAccountTypeEnum)
    {
        return (PriceAccountTypeEnum)getEnum(PriceAccountTypeEnum.class, priceAccountTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PriceAccountTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PriceAccountTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PriceAccountTypeEnum.class);
    }
}