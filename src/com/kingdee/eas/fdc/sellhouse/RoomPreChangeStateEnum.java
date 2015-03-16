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
public class RoomPreChangeStateEnum extends StringEnum
{
    public static final String NOCHANGE_VALUE = "noChange";
    public static final String CHANGE_VALUE = "change";

    public static final RoomPreChangeStateEnum NOCHANGE = new RoomPreChangeStateEnum("NOCHANGE", NOCHANGE_VALUE);
    public static final RoomPreChangeStateEnum CHANGE = new RoomPreChangeStateEnum("CHANGE", CHANGE_VALUE);

    /**
     * construct function
     * @param String roomPreChangeStateEnum
     */
    private RoomPreChangeStateEnum(String name, String roomPreChangeStateEnum)
    {
        super(name, roomPreChangeStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RoomPreChangeStateEnum getEnum(String roomPreChangeStateEnum)
    {
        return (RoomPreChangeStateEnum)getEnum(RoomPreChangeStateEnum.class, roomPreChangeStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RoomPreChangeStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RoomPreChangeStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RoomPreChangeStateEnum.class);
    }
}