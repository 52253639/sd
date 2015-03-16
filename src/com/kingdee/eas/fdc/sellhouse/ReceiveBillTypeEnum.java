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
public class ReceiveBillTypeEnum extends StringEnum
{
    public static final String GATHERING_VALUE = "gathering";
    public static final String REFUNDMENT_VALUE = "refundment";
    public static final String SETTLEMENT_VALUE = "settlement";
    public static final String TRANSFER_VALUE = "transfer";
    public static final String ADJUST_VALUE = "adjust";
    public static final String PARTIAL_VALUE = "partial";

    public static final ReceiveBillTypeEnum gathering = new ReceiveBillTypeEnum("gathering", GATHERING_VALUE);
    public static final ReceiveBillTypeEnum refundment = new ReceiveBillTypeEnum("refundment", REFUNDMENT_VALUE);
    public static final ReceiveBillTypeEnum settlement = new ReceiveBillTypeEnum("settlement", SETTLEMENT_VALUE);
    public static final ReceiveBillTypeEnum transfer = new ReceiveBillTypeEnum("transfer", TRANSFER_VALUE);
    public static final ReceiveBillTypeEnum adjust = new ReceiveBillTypeEnum("adjust", ADJUST_VALUE);
    public static final ReceiveBillTypeEnum partial = new ReceiveBillTypeEnum("partial", PARTIAL_VALUE);

    /**
     * construct function
     * @param String receiveBillTypeEnum
     */
    private ReceiveBillTypeEnum(String name, String receiveBillTypeEnum)
    {
        super(name, receiveBillTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ReceiveBillTypeEnum getEnum(String receiveBillTypeEnum)
    {
        return (ReceiveBillTypeEnum)getEnum(ReceiveBillTypeEnum.class, receiveBillTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ReceiveBillTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ReceiveBillTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ReceiveBillTypeEnum.class);
    }
}