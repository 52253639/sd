/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class YesOrNoEnum extends StringEnum
{
    public static final String YES_VALUE = "1";//alias=ÊÇ
    public static final String NO_VALUE = "0";//alias=·ñ

    public static final YesOrNoEnum YES = new YesOrNoEnum("YES", YES_VALUE);
    public static final YesOrNoEnum NO = new YesOrNoEnum("NO", NO_VALUE);

    /**
     * construct function
     * @param String yesOrNoEnum
     */
    private YesOrNoEnum(String name, String yesOrNoEnum)
    {
        super(name, yesOrNoEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static YesOrNoEnum getEnum(String yesOrNoEnum)
    {
        return (YesOrNoEnum)getEnum(YesOrNoEnum.class, yesOrNoEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(YesOrNoEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(YesOrNoEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(YesOrNoEnum.class);
    }
}