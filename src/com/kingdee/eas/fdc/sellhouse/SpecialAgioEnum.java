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
public class SpecialAgioEnum extends StringEnum
{
    public static final String DAZHE_VALUE = "DaZhe";
    public static final String YOUHUI_VALUE = "YouHui";

    public static final SpecialAgioEnum DaZhe = new SpecialAgioEnum("DaZhe", DAZHE_VALUE);
    public static final SpecialAgioEnum YouHui = new SpecialAgioEnum("YouHui", YOUHUI_VALUE);

    /**
     * construct function
     * @param String specialAgioEnum
     */
    private SpecialAgioEnum(String name, String specialAgioEnum)
    {
        super(name, specialAgioEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SpecialAgioEnum getEnum(String specialAgioEnum)
    {
        return (SpecialAgioEnum)getEnum(SpecialAgioEnum.class, specialAgioEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SpecialAgioEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SpecialAgioEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SpecialAgioEnum.class);
    }
}