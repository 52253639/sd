/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class FeeTypeEnum extends StringEnum
{
    public static final String FEE_VALUE = "FEE";//alias=费用报销类
    public static final String TRA_VALUE = "TRA";//alias=差旅费报销类

    public static final FeeTypeEnum FEE = new FeeTypeEnum("FEE", FEE_VALUE);
    public static final FeeTypeEnum TRA = new FeeTypeEnum("TRA", TRA_VALUE);

    /**
     * construct function
     * @param String feeTypeEnum
     */
    private FeeTypeEnum(String name, String feeTypeEnum)
    {
        super(name, feeTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FeeTypeEnum getEnum(String feeTypeEnum)
    {
        return (FeeTypeEnum)getEnum(FeeTypeEnum.class, feeTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FeeTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FeeTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FeeTypeEnum.class);
    }
}