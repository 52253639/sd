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
public class QuotingPriceTypeEnum extends StringEnum
{
    public static final String ORIGINAL_VALUE = "0";
    public static final String MODIFY_VALUE = "1";
    public static final String HISTORY_VALUE = "2";

    public static final QuotingPriceTypeEnum original = new QuotingPriceTypeEnum("original", ORIGINAL_VALUE);
    public static final QuotingPriceTypeEnum modify = new QuotingPriceTypeEnum("modify", MODIFY_VALUE);
    public static final QuotingPriceTypeEnum history = new QuotingPriceTypeEnum("history", HISTORY_VALUE);

    /**
     * construct function
     * @param String quotingPriceTypeEnum
     */
    private QuotingPriceTypeEnum(String name, String quotingPriceTypeEnum)
    {
        super(name, quotingPriceTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static QuotingPriceTypeEnum getEnum(String quotingPriceTypeEnum)
    {
        return (QuotingPriceTypeEnum)getEnum(QuotingPriceTypeEnum.class, quotingPriceTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(QuotingPriceTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(QuotingPriceTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(QuotingPriceTypeEnum.class);
    }
}