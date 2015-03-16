/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class PlanIndexTypeEnum extends StringEnum
{
    public static final String HOUSE_VALUE = "1house";
    public static final String PUBLICBUILD_VALUE = "2publicBuild";
    public static final String PARKING_VALUE = "3parking";
    public static final String BUSINESS_VALUE = "2business";

    public static final PlanIndexTypeEnum house = new PlanIndexTypeEnum("house", HOUSE_VALUE);
    public static final PlanIndexTypeEnum publicBuild = new PlanIndexTypeEnum("publicBuild", PUBLICBUILD_VALUE);
    public static final PlanIndexTypeEnum parking = new PlanIndexTypeEnum("parking", PARKING_VALUE);
    public static final PlanIndexTypeEnum business = new PlanIndexTypeEnum("business", BUSINESS_VALUE);

    /**
     * construct function
     * @param String planIndexTypeEnum
     */
    private PlanIndexTypeEnum(String name, String planIndexTypeEnum)
    {
        super(name, planIndexTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PlanIndexTypeEnum getEnum(String planIndexTypeEnum)
    {
        return (PlanIndexTypeEnum)getEnum(PlanIndexTypeEnum.class, planIndexTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PlanIndexTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PlanIndexTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PlanIndexTypeEnum.class);
    }
}