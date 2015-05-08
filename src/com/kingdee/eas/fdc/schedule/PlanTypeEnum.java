/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class PlanTypeEnum extends StringEnum
{
    public static final String DEPT_VALUE = "1";
    public static final String INDI_VALUE = "2";

    public static final PlanTypeEnum dept = new PlanTypeEnum("dept", DEPT_VALUE);
    public static final PlanTypeEnum indi = new PlanTypeEnum("indi", INDI_VALUE);

    /**
     * construct function
     * @param String planTypeEnum
     */
    private PlanTypeEnum(String name, String planTypeEnum)
    {
        super(name, planTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PlanTypeEnum getEnum(String planTypeEnum)
    {
        return (PlanTypeEnum)getEnum(PlanTypeEnum.class, planTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PlanTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PlanTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PlanTypeEnum.class);
    }
}