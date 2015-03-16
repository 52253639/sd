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
public class RoomAreaChangeTypeEnum extends StringEnum
{
    public static final String ACTUAL_VALUE = "actual";
    public static final String PRESALES_VALUE = "presales";

    public static final RoomAreaChangeTypeEnum ACTUAL = new RoomAreaChangeTypeEnum("ACTUAL", ACTUAL_VALUE);
    public static final RoomAreaChangeTypeEnum PRESALES = new RoomAreaChangeTypeEnum("PRESALES", PRESALES_VALUE);

    /**
     * construct function
     * @param String roomAreaChangeTypeEnum
     */
    private RoomAreaChangeTypeEnum(String name, String roomAreaChangeTypeEnum)
    {
        super(name, roomAreaChangeTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomAreaChangeTypeEnum getEnum(String roomAreaChangeTypeEnum)
    {
        return (RoomAreaChangeTypeEnum)getEnum(RoomAreaChangeTypeEnum.class, roomAreaChangeTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomAreaChangeTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomAreaChangeTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomAreaChangeTypeEnum.class);
    }
}