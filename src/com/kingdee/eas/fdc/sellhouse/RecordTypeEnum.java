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
public class RecordTypeEnum extends StringEnum
{
    public static final String MAKEOUTRECEIPT_VALUE = "10";
    public static final String CHANGERECEIPT_VALUE = "11";
    public static final String RETAKERECEIPT_VALUE = "12";
    public static final String MAKEOUTINVOICE_VALUE = "20";
    public static final String CHANGEINVOICE_VALUE = "21";
    public static final String RETAKEINVOICE_VALUE = "22";
    public static final String RECEIPTTOINVOICE_VALUE = "30";
    public static final String CLEARINVOICE_VALUE = "31";

    public static final RecordTypeEnum MakeOutReceipt = new RecordTypeEnum("MakeOutReceipt", MAKEOUTRECEIPT_VALUE);
    public static final RecordTypeEnum ChangeReceipt = new RecordTypeEnum("ChangeReceipt", CHANGERECEIPT_VALUE);
    public static final RecordTypeEnum RetakeReceipt = new RecordTypeEnum("RetakeReceipt", RETAKERECEIPT_VALUE);
    public static final RecordTypeEnum MakeOutInvoice = new RecordTypeEnum("MakeOutInvoice", MAKEOUTINVOICE_VALUE);
    public static final RecordTypeEnum ChangeInvoice = new RecordTypeEnum("ChangeInvoice", CHANGEINVOICE_VALUE);
    public static final RecordTypeEnum RetakeInvoice = new RecordTypeEnum("RetakeInvoice", RETAKEINVOICE_VALUE);
    public static final RecordTypeEnum ReceiptToInvoice = new RecordTypeEnum("ReceiptToInvoice", RECEIPTTOINVOICE_VALUE);
    public static final RecordTypeEnum ClearInvoice = new RecordTypeEnum("ClearInvoice", CLEARINVOICE_VALUE);

    /**
     * construct function
     * @param String recordTypeEnum
     */
    private RecordTypeEnum(String name, String recordTypeEnum)
    {
        super(name, recordTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RecordTypeEnum getEnum(String recordTypeEnum)
    {
        return (RecordTypeEnum)getEnum(RecordTypeEnum.class, recordTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RecordTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RecordTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RecordTypeEnum.class);
    }
}