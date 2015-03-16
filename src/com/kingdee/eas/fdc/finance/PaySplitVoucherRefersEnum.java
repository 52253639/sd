/**
 * output package name
 */
package com.kingdee.eas.fdc.finance;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class PaySplitVoucherRefersEnum extends StringEnum
{
    public static final String NOTREFER_VALUE = "0NOTREFER";
    public static final String PAYMENTBILL_VALUE = "1PAYMENT";
    public static final String ADJUSTBILL_VALUE = "1ADJUST";
    public static final String WORKLOADBILL_VALUE = "3WORKLOADBILL";

    public static final PaySplitVoucherRefersEnum NOTREFER = new PaySplitVoucherRefersEnum("NOTREFER", NOTREFER_VALUE);
    public static final PaySplitVoucherRefersEnum PAYMENTBILL = new PaySplitVoucherRefersEnum("PAYMENTBILL", PAYMENTBILL_VALUE);
    public static final PaySplitVoucherRefersEnum ADJUSTBILL = new PaySplitVoucherRefersEnum("ADJUSTBILL", ADJUSTBILL_VALUE);
    public static final PaySplitVoucherRefersEnum WORKLOADBill = new PaySplitVoucherRefersEnum("WORKLOADBill", WORKLOADBILL_VALUE);

    /**
     * construct function
     * @param String paySplitVoucherRefersEnum
     */
    private PaySplitVoucherRefersEnum(String name, String paySplitVoucherRefersEnum)
    {
        super(name, paySplitVoucherRefersEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PaySplitVoucherRefersEnum getEnum(String paySplitVoucherRefersEnum)
    {
        return (PaySplitVoucherRefersEnum)getEnum(PaySplitVoucherRefersEnum.class, paySplitVoucherRefersEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PaySplitVoucherRefersEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PaySplitVoucherRefersEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PaySplitVoucherRefersEnum.class);
    }
}