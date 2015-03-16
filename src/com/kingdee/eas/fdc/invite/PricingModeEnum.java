/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class PricingModeEnum extends StringEnum
{
    public static final String FIXEDPRICEANDUNITPRICE_VALUE = "1FIXEDPRICEANDUNITPRICE";
    public static final String FIXEDUNITPRICE_VALUE = "2FIXEDUNITPRICE";
    public static final String FIXEDCOSTCONTRACT_VALUE = "3FIXEDCOSTCONTRACT";
    public static final String COSTRATE_VALUE = "4COSTRATE";

    public static final PricingModeEnum FIXEDPRICEANDUNITPRICE = new PricingModeEnum("FIXEDPRICEANDUNITPRICE", FIXEDPRICEANDUNITPRICE_VALUE);
    public static final PricingModeEnum FIXEDUNITPRICE = new PricingModeEnum("FIXEDUNITPRICE", FIXEDUNITPRICE_VALUE);
    public static final PricingModeEnum FIXEDCOSTCONTRACT = new PricingModeEnum("FIXEDCOSTCONTRACT", FIXEDCOSTCONTRACT_VALUE);
    public static final PricingModeEnum COSTRATE = new PricingModeEnum("COSTRATE", COSTRATE_VALUE);

    /**
     * construct function
     * @param String pricingModeEnum
     */
    private PricingModeEnum(String name, String pricingModeEnum)
    {
        super(name, pricingModeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PricingModeEnum getEnum(String pricingModeEnum)
    {
        return (PricingModeEnum)getEnum(PricingModeEnum.class, pricingModeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PricingModeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PricingModeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PricingModeEnum.class);
    }
}