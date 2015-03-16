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
public class ReceiptTypeStateEnum extends StringEnum
{
    public static final String NORE_VALUE = "NoRe";
    public static final String FIRSTRE_VALUE = "FirstRe";

    public static final ReceiptTypeStateEnum NoRe = new ReceiptTypeStateEnum("NoRe", NORE_VALUE);
    public static final ReceiptTypeStateEnum FirstRe = new ReceiptTypeStateEnum("FirstRe", FIRSTRE_VALUE);

    /**
     * construct function
     * @param String receiptTypeStateEnum
     */
    private ReceiptTypeStateEnum(String name, String receiptTypeStateEnum)
    {
        super(name, receiptTypeStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ReceiptTypeStateEnum getEnum(String receiptTypeStateEnum)
    {
        return (ReceiptTypeStateEnum)getEnum(ReceiptTypeStateEnum.class, receiptTypeStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ReceiptTypeStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ReceiptTypeStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ReceiptTypeStateEnum.class);
    }
}