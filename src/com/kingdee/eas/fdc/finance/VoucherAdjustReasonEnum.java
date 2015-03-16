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
public class VoucherAdjustReasonEnum extends StringEnum
{
    public static final String INVALID_VALUE = "1INVALID";
    public static final String STATUSGET_VALUE = "2STATUSGET";
    public static final String STATUSFLOW_VALUE = "3STATUSFLOW";
    public static final String STATUSANTIGET_VALUE = "4STATUSANTIGET";
    public static final String STATUSANTIFLOW_VALUE = "4STATUSANTIFLOW";

    public static final VoucherAdjustReasonEnum INVALID = new VoucherAdjustReasonEnum("INVALID", INVALID_VALUE);
    public static final VoucherAdjustReasonEnum STATUSGET = new VoucherAdjustReasonEnum("STATUSGET", STATUSGET_VALUE);
    public static final VoucherAdjustReasonEnum STATUSFLOW = new VoucherAdjustReasonEnum("STATUSFLOW", STATUSFLOW_VALUE);
    public static final VoucherAdjustReasonEnum STATUSANTIGET = new VoucherAdjustReasonEnum("STATUSANTIGET", STATUSANTIGET_VALUE);
    public static final VoucherAdjustReasonEnum STATUSANTIFLOW = new VoucherAdjustReasonEnum("STATUSANTIFLOW", STATUSANTIFLOW_VALUE);

    /**
     * construct function
     * @param String voucherAdjustReasonEnum
     */
    private VoucherAdjustReasonEnum(String name, String voucherAdjustReasonEnum)
    {
        super(name, voucherAdjustReasonEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static VoucherAdjustReasonEnum getEnum(String voucherAdjustReasonEnum)
    {
        return (VoucherAdjustReasonEnum)getEnum(VoucherAdjustReasonEnum.class, voucherAdjustReasonEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(VoucherAdjustReasonEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(VoucherAdjustReasonEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(VoucherAdjustReasonEnum.class);
    }
}