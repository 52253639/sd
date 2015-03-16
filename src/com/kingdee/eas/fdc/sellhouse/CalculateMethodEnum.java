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
public class CalculateMethodEnum extends StringEnum
{
    public static final String DIFFERENCE_VALUE = "Difference";
    public static final String COEFFICIENT_VALUE = "Coefficient";

    public static final CalculateMethodEnum Difference = new CalculateMethodEnum("Difference", DIFFERENCE_VALUE);
    public static final CalculateMethodEnum Coefficient = new CalculateMethodEnum("Coefficient", COEFFICIENT_VALUE);

    /**
     * construct function
     * @param String calculateMethodEnum
     */
    private CalculateMethodEnum(String name, String calculateMethodEnum)
    {
        super(name, calculateMethodEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CalculateMethodEnum getEnum(String calculateMethodEnum)
    {
        return (CalculateMethodEnum)getEnum(CalculateMethodEnum.class, calculateMethodEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CalculateMethodEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CalculateMethodEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CalculateMethodEnum.class);
    }
}