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
public class ChequeTypeEnum extends StringEnum
{
    public static final String INVOICE_VALUE = "invoice";//alias=∑¢∆±
    public static final String RECEIPT_VALUE = "receipt";//alias= ’æ›

    public static final ChequeTypeEnum invoice = new ChequeTypeEnum("invoice", INVOICE_VALUE);
    public static final ChequeTypeEnum receipt = new ChequeTypeEnum("receipt", RECEIPT_VALUE);

    /**
     * construct function
     * @param String chequeTypeEnum
     */
    private ChequeTypeEnum(String name, String chequeTypeEnum)
    {
        super(name, chequeTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChequeTypeEnum getEnum(String chequeTypeEnum)
    {
        return (ChequeTypeEnum)getEnum(ChequeTypeEnum.class, chequeTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChequeTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChequeTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChequeTypeEnum.class);
    }
}