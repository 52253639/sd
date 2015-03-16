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
public class FiledEnum extends StringEnum
{
    public static final String ENUMSTRING_VALUE = "0";
    public static final String ENUMINT_VALUE = "1";
    public static final String ENUMBOOLEAN_VALUE = "2";

    public static final FiledEnum enumString = new FiledEnum("enumString", ENUMSTRING_VALUE);
    public static final FiledEnum enumInt = new FiledEnum("enumInt", ENUMINT_VALUE);
    public static final FiledEnum enumBoolean = new FiledEnum("enumBoolean", ENUMBOOLEAN_VALUE);

    /**
     * construct function
     * @param String filedEnum
     */
    private FiledEnum(String name, String filedEnum)
    {
        super(name, filedEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FiledEnum getEnum(String filedEnum)
    {
        return (FiledEnum)getEnum(FiledEnum.class, filedEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FiledEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FiledEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FiledEnum.class);
    }
}