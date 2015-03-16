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
public class CarryForwardTypeEnum extends StringEnum
{
    public static final String TORENT_VALUE = "ToRent";
    public static final String TOWATERANDELECTRICITYRATE_VALUE = "ToWaterAndElectricityRate";
    public static final String TONEWBILLRENT_VALUE = "ToNewBillRent";
    public static final String TONEWBILLDEPOSIT_VALUE = "ToNewBillDeposit";

    public static final CarryForwardTypeEnum ToRent = new CarryForwardTypeEnum("ToRent", TORENT_VALUE);
    public static final CarryForwardTypeEnum ToWaterAndElectricityRate = new CarryForwardTypeEnum("ToWaterAndElectricityRate", TOWATERANDELECTRICITYRATE_VALUE);
    public static final CarryForwardTypeEnum ToNewBillRent = new CarryForwardTypeEnum("ToNewBillRent", TONEWBILLRENT_VALUE);
    public static final CarryForwardTypeEnum ToNewBillDeposit = new CarryForwardTypeEnum("ToNewBillDeposit", TONEWBILLDEPOSIT_VALUE);

    /**
     * construct function
     * @param String carryForwardTypeEnum
     */
    private CarryForwardTypeEnum(String name, String carryForwardTypeEnum)
    {
        super(name, carryForwardTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CarryForwardTypeEnum getEnum(String carryForwardTypeEnum)
    {
        return (CarryForwardTypeEnum)getEnum(CarryForwardTypeEnum.class, carryForwardTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CarryForwardTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CarryForwardTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CarryForwardTypeEnum.class);
    }
}