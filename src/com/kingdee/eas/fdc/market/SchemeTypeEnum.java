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
public class SchemeTypeEnum extends StringEnum
{
    public static final String INDEPENDENTSCHEME_VALUE = "0";
    public static final String PLANSCHEME_VALUE = "1";

    public static final SchemeTypeEnum INDEPENDENTSCHEME = new SchemeTypeEnum("INDEPENDENTSCHEME", INDEPENDENTSCHEME_VALUE);
    public static final SchemeTypeEnum PLANSCHEME = new SchemeTypeEnum("PLANSCHEME", PLANSCHEME_VALUE);

    /**
     * construct function
     * @param String schemeTypeEnum
     */
    private SchemeTypeEnum(String name, String schemeTypeEnum)
    {
        super(name, schemeTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SchemeTypeEnum getEnum(String schemeTypeEnum)
    {
        return (SchemeTypeEnum)getEnum(SchemeTypeEnum.class, schemeTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SchemeTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SchemeTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SchemeTypeEnum.class);
    }
}