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
public class ReceiptStatusEnum extends StringEnum
{
    public static final String UNMAKE_VALUE = "0";
    public static final String HASMAKE_VALUE = "1";
    public static final String HASRECOVER_VALUE = "2";
    public static final String HASINVOICE_VALUE = "3";

    public static final ReceiptStatusEnum UnMake = new ReceiptStatusEnum("UnMake", UNMAKE_VALUE);
    public static final ReceiptStatusEnum HasMake = new ReceiptStatusEnum("HasMake", HASMAKE_VALUE);
    public static final ReceiptStatusEnum HasRecover = new ReceiptStatusEnum("HasRecover", HASRECOVER_VALUE);
    public static final ReceiptStatusEnum HasInvoice = new ReceiptStatusEnum("HasInvoice", HASINVOICE_VALUE);

    /**
     * construct function
     * @param String receiptStatusEnum
     */
    private ReceiptStatusEnum(String name, String receiptStatusEnum)
    {
        super(name, receiptStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ReceiptStatusEnum getEnum(String receiptStatusEnum)
    {
        return (ReceiptStatusEnum)getEnum(ReceiptStatusEnum.class, receiptStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ReceiptStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ReceiptStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ReceiptStatusEnum.class);
    }
}