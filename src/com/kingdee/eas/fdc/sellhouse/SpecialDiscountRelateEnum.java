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
public class SpecialDiscountRelateEnum extends StringEnum
{
    public static final String COMPANY_VALUE = "COMPANY";
    public static final String SUPPLIER_VALUE = "SUPPLIER";
    public static final String RELATE_VALUE = "RELATE";
    public static final String MARKET_VALUE = "MARKET";

    public static final SpecialDiscountRelateEnum COMPANY = new SpecialDiscountRelateEnum("COMPANY", COMPANY_VALUE);
    public static final SpecialDiscountRelateEnum SUPPLIER = new SpecialDiscountRelateEnum("SUPPLIER", SUPPLIER_VALUE);
    public static final SpecialDiscountRelateEnum RELATE = new SpecialDiscountRelateEnum("RELATE", RELATE_VALUE);
    public static final SpecialDiscountRelateEnum MARKET = new SpecialDiscountRelateEnum("MARKET", MARKET_VALUE);

    /**
     * construct function
     * @param String specialDiscountRelateEnum
     */
    private SpecialDiscountRelateEnum(String name, String specialDiscountRelateEnum)
    {
        super(name, specialDiscountRelateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SpecialDiscountRelateEnum getEnum(String specialDiscountRelateEnum)
    {
        return (SpecialDiscountRelateEnum)getEnum(SpecialDiscountRelateEnum.class, specialDiscountRelateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SpecialDiscountRelateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SpecialDiscountRelateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SpecialDiscountRelateEnum.class);
    }
}