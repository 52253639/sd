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
public class PlanIndexFormulaTypeEnum extends StringEnum
{
    public static final String NULL_VALUE = "NULL";//alias=
    public static final String NORMAL_VALUE = "NORMAL";//alias=普通公式
    public static final String PRODUCTTYPE_VALUE = "PRODUCTTYPE";//alias=业态求和公式

    public static final PlanIndexFormulaTypeEnum NULL = new PlanIndexFormulaTypeEnum("NULL", NULL_VALUE);
    public static final PlanIndexFormulaTypeEnum NORMAL = new PlanIndexFormulaTypeEnum("NORMAL", NORMAL_VALUE);
    public static final PlanIndexFormulaTypeEnum PRODUCTTYPE = new PlanIndexFormulaTypeEnum("PRODUCTTYPE", PRODUCTTYPE_VALUE);

    /**
     * construct function
     * @param String planIndexFormulaTypeEnum
     */
    private PlanIndexFormulaTypeEnum(String name, String planIndexFormulaTypeEnum)
    {
        super(name, planIndexFormulaTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PlanIndexFormulaTypeEnum getEnum(String planIndexFormulaTypeEnum)
    {
        return (PlanIndexFormulaTypeEnum)getEnum(PlanIndexFormulaTypeEnum.class, planIndexFormulaTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PlanIndexFormulaTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PlanIndexFormulaTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PlanIndexFormulaTypeEnum.class);
    }
}