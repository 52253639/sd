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
public class FeeMoneyTypeEnum extends StringEnum
{
    public static final String NOTFEE_VALUE = "NotFee";
    public static final String FEE_VALUE = "Fee";

    public static final FeeMoneyTypeEnum NotFee = new FeeMoneyTypeEnum("NotFee", NOTFEE_VALUE);
    public static final FeeMoneyTypeEnum Fee = new FeeMoneyTypeEnum("Fee", FEE_VALUE);

    /**
     * construct function
     * @param String feeMoneyTypeEnum
     */
    private FeeMoneyTypeEnum(String name, String feeMoneyTypeEnum)
    {
        super(name, feeMoneyTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FeeMoneyTypeEnum getEnum(String feeMoneyTypeEnum)
    {
        return (FeeMoneyTypeEnum)getEnum(FeeMoneyTypeEnum.class, feeMoneyTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FeeMoneyTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FeeMoneyTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FeeMoneyTypeEnum.class);
    }
}