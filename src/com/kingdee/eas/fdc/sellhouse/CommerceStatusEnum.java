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
public class CommerceStatusEnum extends StringEnum
{
    public static final String CLEW_VALUE = "Clew";
    public static final String LATENCY_VALUE = "Latency";
    public static final String INTENT_VALUE = "Intent";
    public static final String SINCERITY_VALUE = "Sincerity";
    public static final String SALEDESTINE_VALUE = "SaleDestine";
    public static final String SALEPURCHASE_VALUE = "SalePurchase";
    public static final String SALESIGN_VALUE = "SaleSign";
    public static final String TENANCYSIGN_VALUE = "TenancySign";
    public static final String FINISH_VALUE = "Finish";

    public static final CommerceStatusEnum Clew = new CommerceStatusEnum("Clew", CLEW_VALUE);
    public static final CommerceStatusEnum Latency = new CommerceStatusEnum("Latency", LATENCY_VALUE);
    public static final CommerceStatusEnum Intent = new CommerceStatusEnum("Intent", INTENT_VALUE);
    public static final CommerceStatusEnum Sincerity = new CommerceStatusEnum("Sincerity", SINCERITY_VALUE);
    public static final CommerceStatusEnum SaleDestine = new CommerceStatusEnum("SaleDestine", SALEDESTINE_VALUE);
    public static final CommerceStatusEnum SalePurchase = new CommerceStatusEnum("SalePurchase", SALEPURCHASE_VALUE);
    public static final CommerceStatusEnum SaleSign = new CommerceStatusEnum("SaleSign", SALESIGN_VALUE);
    public static final CommerceStatusEnum TenancySign = new CommerceStatusEnum("TenancySign", TENANCYSIGN_VALUE);
    public static final CommerceStatusEnum Finish = new CommerceStatusEnum("Finish", FINISH_VALUE);

    /**
     * construct function
     * @param String commerceStatusEnum
     */
    private CommerceStatusEnum(String name, String commerceStatusEnum)
    {
        super(name, commerceStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CommerceStatusEnum getEnum(String commerceStatusEnum)
    {
        return (CommerceStatusEnum)getEnum(CommerceStatusEnum.class, commerceStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CommerceStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CommerceStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CommerceStatusEnum.class);
    }
}