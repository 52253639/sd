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
public class PriceSetSchemenTypeEnum extends StringEnum
{
    public static final String DING_VALUE = "Ding";
    public static final String TIAO_VALUE = "Tiao";

    public static final PriceSetSchemenTypeEnum Ding = new PriceSetSchemenTypeEnum("Ding", DING_VALUE);
    public static final PriceSetSchemenTypeEnum Tiao = new PriceSetSchemenTypeEnum("Tiao", TIAO_VALUE);

    /**
     * construct function
     * @param String priceSetSchemenTypeEnum
     */
    private PriceSetSchemenTypeEnum(String name, String priceSetSchemenTypeEnum)
    {
        super(name, priceSetSchemenTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PriceSetSchemenTypeEnum getEnum(String priceSetSchemenTypeEnum)
    {
        return (PriceSetSchemenTypeEnum)getEnum(PriceSetSchemenTypeEnum.class, priceSetSchemenTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PriceSetSchemenTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PriceSetSchemenTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PriceSetSchemenTypeEnum.class);
    }
}