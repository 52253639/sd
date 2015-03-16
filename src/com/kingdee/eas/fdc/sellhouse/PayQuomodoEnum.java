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
public class PayQuomodoEnum extends StringEnum
{
    public static final String CASH_VALUE = "Cash";
    public static final String POS_VALUE = "POS";
    public static final String BANK_VALUE = "BANK";
    public static final String CHEQUE_VALUE = "CHEQUE";

    public static final PayQuomodoEnum CASH = new PayQuomodoEnum("CASH", CASH_VALUE);
    public static final PayQuomodoEnum POS = new PayQuomodoEnum("POS", POS_VALUE);
    public static final PayQuomodoEnum BANK = new PayQuomodoEnum("BANK", BANK_VALUE);
    public static final PayQuomodoEnum CHEQUE = new PayQuomodoEnum("CHEQUE", CHEQUE_VALUE);

    /**
     * construct function
     * @param String payQuomodoEnum
     */
    private PayQuomodoEnum(String name, String payQuomodoEnum)
    {
        super(name, payQuomodoEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PayQuomodoEnum getEnum(String payQuomodoEnum)
    {
        return (PayQuomodoEnum)getEnum(PayQuomodoEnum.class, payQuomodoEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PayQuomodoEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PayQuomodoEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PayQuomodoEnum.class);
    }
}