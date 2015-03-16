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
public class RevBillTypeEnum extends StringEnum
{
    public static final String GATHERING_VALUE = "gathering";
    public static final String REFUNDMENT_VALUE = "refundment";
    public static final String TRANSFER_VALUE = "transfer";
    public static final String ADJUST_VALUE = "adjust";

    public static final RevBillTypeEnum gathering = new RevBillTypeEnum("gathering", GATHERING_VALUE);
    public static final RevBillTypeEnum refundment = new RevBillTypeEnum("refundment", REFUNDMENT_VALUE);
    public static final RevBillTypeEnum transfer = new RevBillTypeEnum("transfer", TRANSFER_VALUE);
    public static final RevBillTypeEnum adjust = new RevBillTypeEnum("adjust", ADJUST_VALUE);

    /**
     * construct function
     * @param String revBillTypeEnum
     */
    private RevBillTypeEnum(String name, String revBillTypeEnum)
    {
        super(name, revBillTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RevBillTypeEnum getEnum(String revBillTypeEnum)
    {
        return (RevBillTypeEnum)getEnum(RevBillTypeEnum.class, revBillTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RevBillTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RevBillTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RevBillTypeEnum.class);
    }
}