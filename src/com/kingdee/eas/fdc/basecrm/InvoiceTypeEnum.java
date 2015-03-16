/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class InvoiceTypeEnum extends StringEnum
{
    public static final String INVOICE_VALUE = "Invoice";
    public static final String RECEIPT_VALUE = "Receipt";
    public static final String CHANGE_VALUE = "Change";
    public static final String QUIT_VALUE = "Quit";
    public static final String OTHER_VALUE = "Other";

    public static final InvoiceTypeEnum INVOICE = new InvoiceTypeEnum("INVOICE", INVOICE_VALUE);
    public static final InvoiceTypeEnum RECEIPT = new InvoiceTypeEnum("RECEIPT", RECEIPT_VALUE);
    public static final InvoiceTypeEnum CHANGE = new InvoiceTypeEnum("CHANGE", CHANGE_VALUE);
    public static final InvoiceTypeEnum QUIT = new InvoiceTypeEnum("QUIT", QUIT_VALUE);
    public static final InvoiceTypeEnum OTHER = new InvoiceTypeEnum("OTHER", OTHER_VALUE);

    /**
     * construct function
     * @param String invoiceTypeEnum
     */
    private InvoiceTypeEnum(String name, String invoiceTypeEnum)
    {
        super(name, invoiceTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static InvoiceTypeEnum getEnum(String invoiceTypeEnum)
    {
        return (InvoiceTypeEnum)getEnum(InvoiceTypeEnum.class, invoiceTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(InvoiceTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(InvoiceTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(InvoiceTypeEnum.class);
    }
}