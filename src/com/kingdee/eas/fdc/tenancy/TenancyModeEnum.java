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
public class TenancyModeEnum extends StringEnum
{
    public static final String TENANCYUNITPRICEMODEL_VALUE = "1TENANCYUNITPRICEMODEL";
    public static final String TENANCYRENTMODEL_VALUE = "3TENANCYRENTMODEL";

    public static final TenancyModeEnum TenancyUnitPriceModel = new TenancyModeEnum("TenancyUnitPriceModel", TENANCYUNITPRICEMODEL_VALUE);
    public static final TenancyModeEnum TenancyRentModel = new TenancyModeEnum("TenancyRentModel", TENANCYRENTMODEL_VALUE);

    /**
     * construct function
     * @param String tenancyModeEnum
     */
    private TenancyModeEnum(String name, String tenancyModeEnum)
    {
        super(name, tenancyModeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TenancyModeEnum getEnum(String tenancyModeEnum)
    {
        return (TenancyModeEnum)getEnum(TenancyModeEnum.class, tenancyModeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TenancyModeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TenancyModeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TenancyModeEnum.class);
    }
}