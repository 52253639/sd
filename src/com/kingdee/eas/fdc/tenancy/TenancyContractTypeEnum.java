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
public class TenancyContractTypeEnum extends StringEnum
{
    public static final String NEWTENANCY_VALUE = "1NewTenancy";
    public static final String CONTINUETENANCY_VALUE = "2ContinueTenancy";
    public static final String REJIGGERTENANCY_VALUE = "3RejiggerTenancy";
    public static final String CHANGENAME_VALUE = "4ChangeName";
    public static final String PRICECHANGE_VALUE = "5PriceChange";

    public static final TenancyContractTypeEnum NewTenancy = new TenancyContractTypeEnum("NewTenancy", NEWTENANCY_VALUE);
    public static final TenancyContractTypeEnum ContinueTenancy = new TenancyContractTypeEnum("ContinueTenancy", CONTINUETENANCY_VALUE);
    public static final TenancyContractTypeEnum RejiggerTenancy = new TenancyContractTypeEnum("RejiggerTenancy", REJIGGERTENANCY_VALUE);
    public static final TenancyContractTypeEnum ChangeName = new TenancyContractTypeEnum("ChangeName", CHANGENAME_VALUE);
    public static final TenancyContractTypeEnum PriceChange = new TenancyContractTypeEnum("PriceChange", PRICECHANGE_VALUE);

    /**
     * construct function
     * @param String tenancyContractTypeEnum
     */
    private TenancyContractTypeEnum(String name, String tenancyContractTypeEnum)
    {
        super(name, tenancyContractTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TenancyContractTypeEnum getEnum(String tenancyContractTypeEnum)
    {
        return (TenancyContractTypeEnum)getEnum(TenancyContractTypeEnum.class, tenancyContractTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TenancyContractTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TenancyContractTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TenancyContractTypeEnum.class);
    }
}