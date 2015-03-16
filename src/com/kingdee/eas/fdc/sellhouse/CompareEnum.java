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
public class CompareEnum extends StringEnum
{
    public static final String MORE_VALUE = ">";
    public static final String MOREANDEQUAL_VALUE = ">=";
    public static final String LESS_VALUE = "<";
    public static final String LESSANDEQUAL_VALUE = "<=";
    public static final String NOTEQUAL_VALUE = "!=";
    public static final String EQUAL_VALUE = "=";

    public static final CompareEnum MORE = new CompareEnum("MORE", MORE_VALUE);
    public static final CompareEnum MOREANDEQUAL = new CompareEnum("MOREANDEQUAL", MOREANDEQUAL_VALUE);
    public static final CompareEnum LESS = new CompareEnum("LESS", LESS_VALUE);
    public static final CompareEnum LESSANDEQUAL = new CompareEnum("LESSANDEQUAL", LESSANDEQUAL_VALUE);
    public static final CompareEnum NOTEQUAL = new CompareEnum("NOTEQUAL", NOTEQUAL_VALUE);
    public static final CompareEnum EQUAL = new CompareEnum("EQUAL", EQUAL_VALUE);

    /**
     * construct function
     * @param String compareEnum
     */
    private CompareEnum(String name, String compareEnum)
    {
        super(name, compareEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CompareEnum getEnum(String compareEnum)
    {
        return (CompareEnum)getEnum(CompareEnum.class, compareEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CompareEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CompareEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CompareEnum.class);
    }
}