/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class CurrencyEnum extends StringEnum
{
    public static final String RMB_VALUE = "1";
    public static final String MY_VALUE = "2";
    public static final String OY_VALUE = "3";
    public static final String RY_VALUE = "4";
    public static final String HY_VALUE = "5";

    public static final CurrencyEnum RMB = new CurrencyEnum("RMB", RMB_VALUE);
    public static final CurrencyEnum MY = new CurrencyEnum("MY", MY_VALUE);
    public static final CurrencyEnum OY = new CurrencyEnum("OY", OY_VALUE);
    public static final CurrencyEnum RY = new CurrencyEnum("RY", RY_VALUE);
    public static final CurrencyEnum HY = new CurrencyEnum("HY", HY_VALUE);

    /**
     * construct function
     * @param String currencyEnum
     */
    private CurrencyEnum(String name, String currencyEnum)
    {
        super(name, currencyEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CurrencyEnum getEnum(String currencyEnum)
    {
        return (CurrencyEnum)getEnum(CurrencyEnum.class, currencyEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CurrencyEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CurrencyEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CurrencyEnum.class);
    }
}