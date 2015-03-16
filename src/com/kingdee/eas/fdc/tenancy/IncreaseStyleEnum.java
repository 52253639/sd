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
public class IncreaseStyleEnum extends StringEnum
{
    public static final String RENTANDPERIOD_VALUE = "RentAndPeriod";
    public static final String RENT_VALUE = "Rent";

    public static final IncreaseStyleEnum rentAndPeriod = new IncreaseStyleEnum("rentAndPeriod", RENTANDPERIOD_VALUE);
    public static final IncreaseStyleEnum Rent = new IncreaseStyleEnum("Rent", RENT_VALUE);

    /**
     * construct function
     * @param String increaseStyleEnum
     */
    private IncreaseStyleEnum(String name, String increaseStyleEnum)
    {
        super(name, increaseStyleEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static IncreaseStyleEnum getEnum(String increaseStyleEnum)
    {
        return (IncreaseStyleEnum)getEnum(IncreaseStyleEnum.class, increaseStyleEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(IncreaseStyleEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(IncreaseStyleEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(IncreaseStyleEnum.class);
    }
}