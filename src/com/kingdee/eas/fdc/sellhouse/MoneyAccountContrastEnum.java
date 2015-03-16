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
public class MoneyAccountContrastEnum extends StringEnum
{
    public static final String DEBITSIDE_VALUE = "1DebitSide";
    public static final String CREDITSIDE_VALUE = "2CreditSide";
    public static final String DEBITSUBSTRACTCREDIT_VALUE = "3DebitSubStractCredit";
    public static final String CREDITSUBSTRACTDEBIT_VALUE = "4CreditSubStractDebit";

    public static final MoneyAccountContrastEnum DebitSide = new MoneyAccountContrastEnum("DebitSide", DEBITSIDE_VALUE);
    public static final MoneyAccountContrastEnum CreditSide = new MoneyAccountContrastEnum("CreditSide", CREDITSIDE_VALUE);
    public static final MoneyAccountContrastEnum DebitSubStractCredit = new MoneyAccountContrastEnum("DebitSubStractCredit", DEBITSUBSTRACTCREDIT_VALUE);
    public static final MoneyAccountContrastEnum CreditSubStractDebit = new MoneyAccountContrastEnum("CreditSubStractDebit", CREDITSUBSTRACTDEBIT_VALUE);

    /**
     * construct function
     * @param String moneyAccountContrastEnum
     */
    private MoneyAccountContrastEnum(String name, String moneyAccountContrastEnum)
    {
        super(name, moneyAccountContrastEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MoneyAccountContrastEnum getEnum(String moneyAccountContrastEnum)
    {
        return (MoneyAccountContrastEnum)getEnum(MoneyAccountContrastEnum.class, moneyAccountContrastEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MoneyAccountContrastEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MoneyAccountContrastEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MoneyAccountContrastEnum.class);
    }
}