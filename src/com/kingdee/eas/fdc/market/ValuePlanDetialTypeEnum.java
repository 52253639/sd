/**
 * output package name
 */
package com.kingdee.eas.fdc.market;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ValuePlanDetialTypeEnum extends StringEnum
{
    public static final String YEAR_VALUE = "YEAR";
    public static final String MONTH_VALUE = "MONTH";
    public static final String QUARTER_VALUE = "QUARTER";

    public static final ValuePlanDetialTypeEnum YEAR = new ValuePlanDetialTypeEnum("YEAR", YEAR_VALUE);
    public static final ValuePlanDetialTypeEnum MONTH = new ValuePlanDetialTypeEnum("MONTH", MONTH_VALUE);
    public static final ValuePlanDetialTypeEnum QUARTER = new ValuePlanDetialTypeEnum("QUARTER", QUARTER_VALUE);

    /**
     * construct function
     * @param String valuePlanDetialTypeEnum
     */
    private ValuePlanDetialTypeEnum(String name, String valuePlanDetialTypeEnum)
    {
        super(name, valuePlanDetialTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ValuePlanDetialTypeEnum getEnum(String valuePlanDetialTypeEnum)
    {
        return (ValuePlanDetialTypeEnum)getEnum(ValuePlanDetialTypeEnum.class, valuePlanDetialTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ValuePlanDetialTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ValuePlanDetialTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ValuePlanDetialTypeEnum.class);
    }
}