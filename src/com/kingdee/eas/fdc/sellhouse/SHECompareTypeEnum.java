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
public class SHECompareTypeEnum extends StringEnum
{
    public static final String LESS_VALUE = "<";
    public static final String EQUAL_VALUE = "=";
    public static final String LESSEQUAL_VALUE = "<=";

    public static final SHECompareTypeEnum LESS = new SHECompareTypeEnum("LESS", LESS_VALUE);
    public static final SHECompareTypeEnum EQUAL = new SHECompareTypeEnum("EQUAL", EQUAL_VALUE);
    public static final SHECompareTypeEnum LESSEQUAL = new SHECompareTypeEnum("LESSEQUAL", LESSEQUAL_VALUE);

    /**
     * construct function
     * @param String sHECompareTypeEnum
     */
    private SHECompareTypeEnum(String name, String sHECompareTypeEnum)
    {
        super(name, sHECompareTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SHECompareTypeEnum getEnum(String sHECompareTypeEnum)
    {
        return (SHECompareTypeEnum)getEnum(SHECompareTypeEnum.class, sHECompareTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SHECompareTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SHECompareTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SHECompareTypeEnum.class);
    }
}