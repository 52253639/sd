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
public class SaleBalanceTypeEnum extends StringEnum
{
    public static final String BALANCE_VALUE = "1Balance";
    public static final String UNBALANCE_VALUE = "2UnBalance";

    public static final SaleBalanceTypeEnum Balance = new SaleBalanceTypeEnum("Balance", BALANCE_VALUE);
    public static final SaleBalanceTypeEnum UnBalance = new SaleBalanceTypeEnum("UnBalance", UNBALANCE_VALUE);

    /**
     * construct function
     * @param String saleBalanceTypeEnum
     */
    private SaleBalanceTypeEnum(String name, String saleBalanceTypeEnum)
    {
        super(name, saleBalanceTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SaleBalanceTypeEnum getEnum(String saleBalanceTypeEnum)
    {
        return (SaleBalanceTypeEnum)getEnum(SaleBalanceTypeEnum.class, saleBalanceTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SaleBalanceTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SaleBalanceTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SaleBalanceTypeEnum.class);
    }
}