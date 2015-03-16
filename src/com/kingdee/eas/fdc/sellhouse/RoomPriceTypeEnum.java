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
public class RoomPriceTypeEnum extends StringEnum
{
    public static final String BATCHPRICE_VALUE = "BatchPrice";
    public static final String SOLITUDEPRICE_VALUE = "SolitudePrice";

    public static final RoomPriceTypeEnum BatchPrice = new RoomPriceTypeEnum("BatchPrice", BATCHPRICE_VALUE);
    public static final RoomPriceTypeEnum SolitudePrice = new RoomPriceTypeEnum("SolitudePrice", SOLITUDEPRICE_VALUE);

    /**
     * construct function
     * @param String roomPriceTypeEnum
     */
    private RoomPriceTypeEnum(String name, String roomPriceTypeEnum)
    {
        super(name, roomPriceTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomPriceTypeEnum getEnum(String roomPriceTypeEnum)
    {
        return (RoomPriceTypeEnum)getEnum(RoomPriceTypeEnum.class, roomPriceTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomPriceTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomPriceTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomPriceTypeEnum.class);
    }
}