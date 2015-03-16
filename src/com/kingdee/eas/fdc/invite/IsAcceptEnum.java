/**
 * output package name
 */
package com.kingdee.eas.fdc.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class IsAcceptEnum extends StringEnum
{


    /**
     * construct function
     * @param String isAcceptEnum
     */
    private IsAcceptEnum(String name, String isAcceptEnum)
    {
        super(name, isAcceptEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static IsAcceptEnum getEnum(String isAcceptEnum)
    {
        return (IsAcceptEnum)getEnum(IsAcceptEnum.class, isAcceptEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(IsAcceptEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(IsAcceptEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(IsAcceptEnum.class);
    }
}