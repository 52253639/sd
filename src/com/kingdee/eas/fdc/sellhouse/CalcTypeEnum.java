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
public class CalcTypeEnum extends StringEnum
{
    public static final String PRICEBYBUILDINGAREA_VALUE = "PriceByBuildingArea";
    public static final String PRICEBYROOMAREA_VALUE = "PriceByRoomArea";
    public static final String PRICEBYTOTALAMOUNT_VALUE = "PriceByTotalAmount";

    public static final CalcTypeEnum PriceByBuildingArea = new CalcTypeEnum("PriceByBuildingArea", PRICEBYBUILDINGAREA_VALUE);
    public static final CalcTypeEnum PriceByRoomArea = new CalcTypeEnum("PriceByRoomArea", PRICEBYROOMAREA_VALUE);
    public static final CalcTypeEnum PriceByTotalAmount = new CalcTypeEnum("PriceByTotalAmount", PRICEBYTOTALAMOUNT_VALUE);

    /**
     * construct function
     * @param String calcTypeEnum
     */
    private CalcTypeEnum(String name, String calcTypeEnum)
    {
        super(name, calcTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CalcTypeEnum getEnum(String calcTypeEnum)
    {
        return (CalcTypeEnum)getEnum(CalcTypeEnum.class, calcTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CalcTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CalcTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CalcTypeEnum.class);
    }
}