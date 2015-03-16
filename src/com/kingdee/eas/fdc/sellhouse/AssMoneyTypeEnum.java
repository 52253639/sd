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
public class AssMoneyTypeEnum extends StringEnum
{
    public static final String FITMENTRUBISHCLEAR_VALUE = "FitmentRubishClear";
    public static final String COMMISSIONCHARGE_VALUE = "CommissionCharge";
    public static final String PROPERTYFLOWER_VALUE = "PropertyFlower";
    public static final String TERRATAX_VALUE = "TerraTax";
    public static final String PROPERTYASSIT_VALUE = "PropertyAssit";

    public static final AssMoneyTypeEnum FitmentRubishClear = new AssMoneyTypeEnum("FitmentRubishClear", FITMENTRUBISHCLEAR_VALUE);
    public static final AssMoneyTypeEnum CommissionCharge = new AssMoneyTypeEnum("CommissionCharge", COMMISSIONCHARGE_VALUE);
    public static final AssMoneyTypeEnum PropertyFlower = new AssMoneyTypeEnum("PropertyFlower", PROPERTYFLOWER_VALUE);
    public static final AssMoneyTypeEnum TerraTax = new AssMoneyTypeEnum("TerraTax", TERRATAX_VALUE);
    public static final AssMoneyTypeEnum PropertyAssit = new AssMoneyTypeEnum("PropertyAssit", PROPERTYASSIT_VALUE);

    /**
     * construct function
     * @param String assMoneyTypeEnum
     */
    private AssMoneyTypeEnum(String name, String assMoneyTypeEnum)
    {
        super(name, assMoneyTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AssMoneyTypeEnum getEnum(String assMoneyTypeEnum)
    {
        return (AssMoneyTypeEnum)getEnum(AssMoneyTypeEnum.class, assMoneyTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AssMoneyTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AssMoneyTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AssMoneyTypeEnum.class);
    }
}