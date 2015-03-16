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
public class RefundmentMoneyTypeEnum extends StringEnum
{
    public static final String APPREVREFUNDMENT_VALUE = "AppRevRefundment";
    public static final String APPREFUNDMENT_VALUE = "AppRefundment";

    public static final RefundmentMoneyTypeEnum AppRevRefundment = new RefundmentMoneyTypeEnum("AppRevRefundment", APPREVREFUNDMENT_VALUE);
    public static final RefundmentMoneyTypeEnum AppRefundment = new RefundmentMoneyTypeEnum("AppRefundment", APPREFUNDMENT_VALUE);

    /**
     * construct function
     * @param String refundmentMoneyTypeEnum
     */
    private RefundmentMoneyTypeEnum(String name, String refundmentMoneyTypeEnum)
    {
        super(name, refundmentMoneyTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RefundmentMoneyTypeEnum getEnum(String refundmentMoneyTypeEnum)
    {
        return (RefundmentMoneyTypeEnum)getEnum(RefundmentMoneyTypeEnum.class, refundmentMoneyTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RefundmentMoneyTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RefundmentMoneyTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RefundmentMoneyTypeEnum.class);
    }
}