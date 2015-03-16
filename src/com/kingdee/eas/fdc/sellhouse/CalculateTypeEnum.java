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
public class CalculateTypeEnum extends StringEnum
{
    public static final String GENERAL_VALUE = "GENERAL";
    public static final String PARMAMNENT_VALUE = "PARMAMNENT";
    public static final String PATULOUS_VALUE = "PATULOUS";

    public static final CalculateTypeEnum GENERAL = new CalculateTypeEnum("GENERAL", GENERAL_VALUE);
    public static final CalculateTypeEnum PARMAMNENT = new CalculateTypeEnum("PARMAMNENT", PARMAMNENT_VALUE);
    public static final CalculateTypeEnum PATULOUS = new CalculateTypeEnum("PATULOUS", PATULOUS_VALUE);

    /**
     * construct function
     * @param String calculateTypeEnum
     */
    private CalculateTypeEnum(String name, String calculateTypeEnum)
    {
        super(name, calculateTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CalculateTypeEnum getEnum(String calculateTypeEnum)
    {
        return (CalculateTypeEnum)getEnum(CalculateTypeEnum.class, calculateTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CalculateTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CalculateTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CalculateTypeEnum.class);
    }
}