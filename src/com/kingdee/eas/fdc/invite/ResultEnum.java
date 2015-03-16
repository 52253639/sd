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
public class ResultEnum extends StringEnum
{
    public static final String YES_VALUE = "YES";
    public static final String NO_VALUE = "NO";

    public static final ResultEnum YES = new ResultEnum("YES", YES_VALUE);
    public static final ResultEnum NO = new ResultEnum("NO", NO_VALUE);

    /**
     * construct function
     * @param String resultEnum
     */
    private ResultEnum(String name, String resultEnum)
    {
        super(name, resultEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ResultEnum getEnum(String resultEnum)
    {
        return (ResultEnum)getEnum(ResultEnum.class, resultEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ResultEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ResultEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ResultEnum.class);
    }
}