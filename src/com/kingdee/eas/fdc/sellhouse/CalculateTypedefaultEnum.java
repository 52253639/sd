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
public class CalculateTypedefaultEnum extends StringEnum
{
    public static final String GENERAL_VALUE = "GENERAL";
    public static final String PATULOUS_VALUE = "PATULOUS";

    public static final CalculateTypedefaultEnum GENERAL = new CalculateTypedefaultEnum("GENERAL", GENERAL_VALUE);
    public static final CalculateTypedefaultEnum PATULOUS = new CalculateTypedefaultEnum("PATULOUS", PATULOUS_VALUE);

    /**
     * construct function
     * @param String calculateTypedefaultEnum
     */
    private CalculateTypedefaultEnum(String name, String calculateTypedefaultEnum)
    {
        super(name, calculateTypedefaultEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CalculateTypedefaultEnum getEnum(String calculateTypedefaultEnum)
    {
        return (CalculateTypedefaultEnum)getEnum(CalculateTypedefaultEnum.class, calculateTypedefaultEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CalculateTypedefaultEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CalculateTypedefaultEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CalculateTypedefaultEnum.class);
    }
}