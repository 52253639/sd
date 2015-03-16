/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class FreeTenancyTypeEnum extends StringEnum
{
    public static final String FREETENNOTMONEY_VALUE = "1FREETENNOTMONEY";
    public static final String FREEMONEYNOTTEN_VALUE = "2FREEMONEYNOTTEN";
    public static final String FREETENANDMONEY_VALUE = "3FREETENANDMONEY";

    public static final FreeTenancyTypeEnum FreeTenNotMoney = new FreeTenancyTypeEnum("FreeTenNotMoney", FREETENNOTMONEY_VALUE);
    public static final FreeTenancyTypeEnum FreeMoneyNotTen = new FreeTenancyTypeEnum("FreeMoneyNotTen", FREEMONEYNOTTEN_VALUE);
    public static final FreeTenancyTypeEnum FreeTenAndMoney = new FreeTenancyTypeEnum("FreeTenAndMoney", FREETENANDMONEY_VALUE);

    /**
     * construct function
     * @param String freeTenancyTypeEnum
     */
    private FreeTenancyTypeEnum(String name, String freeTenancyTypeEnum)
    {
        super(name, freeTenancyTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FreeTenancyTypeEnum getEnum(String freeTenancyTypeEnum)
    {
        return (FreeTenancyTypeEnum)getEnum(FreeTenancyTypeEnum.class, freeTenancyTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FreeTenancyTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FreeTenancyTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FreeTenancyTypeEnum.class);
    }
}