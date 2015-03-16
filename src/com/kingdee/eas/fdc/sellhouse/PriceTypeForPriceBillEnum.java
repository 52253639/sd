/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class PriceTypeForPriceBillEnum extends IntEnum
{
    public static final int USEBUILDINGAREA_VALUE = 0;
    public static final int USEROOMAREA_VALUE = 1;
    public static final int USESTANDPRICE_VALUE = 2;

    public static final PriceTypeForPriceBillEnum UseBuildingArea = new PriceTypeForPriceBillEnum("UseBuildingArea", USEBUILDINGAREA_VALUE);
    public static final PriceTypeForPriceBillEnum UseRoomArea = new PriceTypeForPriceBillEnum("UseRoomArea", USEROOMAREA_VALUE);
    public static final PriceTypeForPriceBillEnum UseStandPrice = new PriceTypeForPriceBillEnum("UseStandPrice", USESTANDPRICE_VALUE);

    /**
     * construct function
     * @param integer priceTypeForPriceBillEnum
     */
    private PriceTypeForPriceBillEnum(String name, int priceTypeForPriceBillEnum)
    {
        super(name, priceTypeForPriceBillEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PriceTypeForPriceBillEnum getEnum(String priceTypeForPriceBillEnum)
    {
        return (PriceTypeForPriceBillEnum)getEnum(PriceTypeForPriceBillEnum.class, priceTypeForPriceBillEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static PriceTypeForPriceBillEnum getEnum(int priceTypeForPriceBillEnum)
    {
        return (PriceTypeForPriceBillEnum)getEnum(PriceTypeForPriceBillEnum.class, priceTypeForPriceBillEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PriceTypeForPriceBillEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PriceTypeForPriceBillEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PriceTypeForPriceBillEnum.class);
    }
}