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
public class CalcWayEnum extends StringEnum
{
    public static final String SUPREME_VALUE = "00SUPREME";
    public static final String APART_VALUE = "01APART";

    public static final CalcWayEnum SUPREME = new CalcWayEnum("SUPREME", SUPREME_VALUE);
    public static final CalcWayEnum APART = new CalcWayEnum("APART", APART_VALUE);

    /**
     * construct function
     * @param String calcWayEnum
     */
    private CalcWayEnum(String name, String calcWayEnum)
    {
        super(name, calcWayEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CalcWayEnum getEnum(String calcWayEnum)
    {
        return (CalcWayEnum)getEnum(CalcWayEnum.class, calcWayEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CalcWayEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CalcWayEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CalcWayEnum.class);
    }
}