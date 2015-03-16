/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class RevBizTypeEnum extends StringEnum
{
    public static final String CUSTOMER_VALUE = "customer";
    public static final String PURCHASE_VALUE = "purchase";
    public static final String SINCERITY_VALUE = "sincerity";
    public static final String AREACOMPENSATE_VALUE = "areaCompensate";
    public static final String OBLIGATE_VALUE = "obligate";
    public static final String TENANCY_VALUE = "tenancy";
    public static final String MANAGE_VALUE = "manage";

    public static final RevBizTypeEnum customer = new RevBizTypeEnum("customer", CUSTOMER_VALUE);
    public static final RevBizTypeEnum purchase = new RevBizTypeEnum("purchase", PURCHASE_VALUE);
    public static final RevBizTypeEnum sincerity = new RevBizTypeEnum("sincerity", SINCERITY_VALUE);
    public static final RevBizTypeEnum areaCompensate = new RevBizTypeEnum("areaCompensate", AREACOMPENSATE_VALUE);
    public static final RevBizTypeEnum obligate = new RevBizTypeEnum("obligate", OBLIGATE_VALUE);
    public static final RevBizTypeEnum tenancy = new RevBizTypeEnum("tenancy", TENANCY_VALUE);
    public static final RevBizTypeEnum manage = new RevBizTypeEnum("manage", MANAGE_VALUE);

    /**
     * construct function
     * @param String revBizTypeEnum
     */
    private RevBizTypeEnum(String name, String revBizTypeEnum)
    {
        super(name, revBizTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RevBizTypeEnum getEnum(String revBizTypeEnum)
    {
        return (RevBizTypeEnum)getEnum(RevBizTypeEnum.class, revBizTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RevBizTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RevBizTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RevBizTypeEnum.class);
    }
}