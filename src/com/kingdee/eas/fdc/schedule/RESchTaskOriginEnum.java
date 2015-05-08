/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class RESchTaskOriginEnum extends StringEnum
{
    public static final String MAIN_VALUE = "MAIN";
    public static final String SPECIAL_VALUE = "SPECIAL";
    public static final String INPUT_VALUE = "INPUT";

    public static final RESchTaskOriginEnum MAIN = new RESchTaskOriginEnum("MAIN", MAIN_VALUE);
    public static final RESchTaskOriginEnum SPECIAL = new RESchTaskOriginEnum("SPECIAL", SPECIAL_VALUE);
    public static final RESchTaskOriginEnum INPUT = new RESchTaskOriginEnum("INPUT", INPUT_VALUE);

    /**
     * construct function
     * @param String rESchTaskOriginEnum
     */
    private RESchTaskOriginEnum(String name, String rESchTaskOriginEnum)
    {
        super(name, rESchTaskOriginEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RESchTaskOriginEnum getEnum(String rESchTaskOriginEnum)
    {
        return (RESchTaskOriginEnum)getEnum(RESchTaskOriginEnum.class, rESchTaskOriginEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RESchTaskOriginEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RESchTaskOriginEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RESchTaskOriginEnum.class);
    }
}