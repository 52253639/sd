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
public class LoanCalcTypeEnum extends StringEnum
{
    public static final String AREAANDPRICE_VALUE = "0areandprice";
    public static final String TOTALMONEY_VALUE = "1totalmoney";

    public static final LoanCalcTypeEnum AreaAndPrice = new LoanCalcTypeEnum("AreaAndPrice", AREAANDPRICE_VALUE);
    public static final LoanCalcTypeEnum TotalMoney = new LoanCalcTypeEnum("TotalMoney", TOTALMONEY_VALUE);

    /**
     * construct function
     * @param String loanCalcTypeEnum
     */
    private LoanCalcTypeEnum(String name, String loanCalcTypeEnum)
    {
        super(name, loanCalcTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static LoanCalcTypeEnum getEnum(String loanCalcTypeEnum)
    {
        return (LoanCalcTypeEnum)getEnum(LoanCalcTypeEnum.class, loanCalcTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(LoanCalcTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(LoanCalcTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(LoanCalcTypeEnum.class);
    }
}