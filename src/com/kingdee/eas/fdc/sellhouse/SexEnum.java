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
public class SexEnum extends StringEnum
{
    public static final String MANKIND_VALUE = "1Mankind";
    public static final String WOMENFOLK_VALUE = "2Womenfolk";

    public static final SexEnum Mankind = new SexEnum("Mankind", MANKIND_VALUE);
    public static final SexEnum Womenfolk = new SexEnum("Womenfolk", WOMENFOLK_VALUE);

    /**
     * construct function
     * @param String sexEnum
     */
    private SexEnum(String name, String sexEnum)
    {
        super(name, sexEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SexEnum getEnum(String sexEnum)
    {
        return (SexEnum)getEnum(SexEnum.class, sexEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SexEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SexEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SexEnum.class);
    }
}