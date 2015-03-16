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
public class TenancyPriceTypeEnum extends StringEnum
{
    public static final String BATCHPRICE_VALUE = "BatchPrice";
    public static final String SOLITUDEPRICE_VALUE = "SolitudePrice";

    public static final TenancyPriceTypeEnum BatchPrice = new TenancyPriceTypeEnum("BatchPrice", BATCHPRICE_VALUE);
    public static final TenancyPriceTypeEnum SolitudePrice = new TenancyPriceTypeEnum("SolitudePrice", SOLITUDEPRICE_VALUE);

    /**
     * construct function
     * @param String tenancyPriceTypeEnum
     */
    private TenancyPriceTypeEnum(String name, String tenancyPriceTypeEnum)
    {
        super(name, tenancyPriceTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TenancyPriceTypeEnum getEnum(String tenancyPriceTypeEnum)
    {
        return (TenancyPriceTypeEnum)getEnum(TenancyPriceTypeEnum.class, tenancyPriceTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TenancyPriceTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TenancyPriceTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TenancyPriceTypeEnum.class);
    }
}