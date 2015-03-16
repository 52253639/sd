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
public class QuoteStandEnum extends StringEnum
{
    public static final String BIDDERMINPRICE_VALUE = "1";
    public static final String QUOTINGPRICEAVG_VALUE = "2";
    public static final String CONSULTPRICE_VALUE = "3";
    public static final String LOWESTTOTALPRICE_VALUE = "4";

    public static final QuoteStandEnum BidderMinPrice = new QuoteStandEnum("BidderMinPrice", BIDDERMINPRICE_VALUE);
    public static final QuoteStandEnum QuotingPriceAvg = new QuoteStandEnum("QuotingPriceAvg", QUOTINGPRICEAVG_VALUE);
    public static final QuoteStandEnum ConsultPrice = new QuoteStandEnum("ConsultPrice", CONSULTPRICE_VALUE);
    public static final QuoteStandEnum LowestTotalPrice = new QuoteStandEnum("LowestTotalPrice", LOWESTTOTALPRICE_VALUE);

    /**
     * construct function
     * @param String quoteStandEnum
     */
    private QuoteStandEnum(String name, String quoteStandEnum)
    {
        super(name, quoteStandEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static QuoteStandEnum getEnum(String quoteStandEnum)
    {
        return (QuoteStandEnum)getEnum(QuoteStandEnum.class, quoteStandEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(QuoteStandEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(QuoteStandEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(QuoteStandEnum.class);
    }
}