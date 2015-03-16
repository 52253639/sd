/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class UseTypeEnum extends StringEnum
{
    public static final String DANXUAN_VALUE = "DanXuan";
    public static final String DUOXUAN_VALUE = "DuoXuan";

    public static final UseTypeEnum DanXuan = new UseTypeEnum("DanXuan", DANXUAN_VALUE);
    public static final UseTypeEnum DuoXuan = new UseTypeEnum("DuoXuan", DUOXUAN_VALUE);

    /**
     * construct function
     * @param String useTypeEnum
     */
    private UseTypeEnum(String name, String useTypeEnum)
    {
        super(name, useTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static UseTypeEnum getEnum(String useTypeEnum)
    {
        return (UseTypeEnum)getEnum(UseTypeEnum.class, useTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(UseTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(UseTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(UseTypeEnum.class);
    }
}